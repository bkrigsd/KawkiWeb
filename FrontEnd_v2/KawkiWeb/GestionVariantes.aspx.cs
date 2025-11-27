using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSMovimientosInventario;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSProductosVariantes;
using KawkiWebBusiness.KawkiWebWSTallas;
using coloresDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO;
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO;
using tallasDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO;
using usuariosDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO;

namespace KawkiWeb
{
    public partial class GestionVariantes : Page
    {
        private ProductosVariantesBO variantesBO;
        private ProductosBO productosBO;
        private ColoresBO coloresBO;
        private TallasBO tallasBO;
        private MovimientosInventarioBO movimientosBO;
        private int productoId = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            variantesBO = new ProductosVariantesBO();
            productosBO = new ProductosBO();
            coloresBO = new ColoresBO();
            tallasBO = new TallasBO();
            movimientosBO = new MovimientosInventarioBO();

            if (string.IsNullOrEmpty(Request.QueryString["productoId"]))
            {
                Response.Redirect("GestionProductos.aspx");
                return;
            }

            productoId = Convert.ToInt32(Request.QueryString["productoId"]);
            hfProductoId.Value = productoId.ToString();

            if (IsPostBack)
            {
                string eventTarget = Request["__EVENTTARGET"];
                string eventArgument = Request["__EVENTARGUMENT"];

                if (eventTarget == "UpdateDisponibilidad" && !string.IsNullOrEmpty(eventArgument))
                {
                    ActualizarDisponibilidad(eventArgument);
                    return;
                }
            }

            if (!IsPostBack)
            {
                CargarDatosIniciales();
            }
        }

        private void CargarDatosIniciales()
        {
            try
            {
                var producto = productosBO.ObtenerPorIdProducto(productoId);
                if (producto == null)
                {
                    MostrarError("Producto no encontrado");
                    Response.Redirect("GestionProductos.aspx");
                    return;
                }

                lblTituloProducto.Text = $"{producto.categoria?.nombre ?? ""} {producto.estilo?.nombre ?? ""}".Trim();
                lblProductoInfo.Text = $"📦 {producto.descripcion} | Precio: S/. {producto.precio_venta:N2}";
                lblProductoModal.Text = producto.descripcion;

                CargarColores();
                CargarTallas();
                CargarVariantes();
                CargarTallasEditar();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar datos: " + ex.Message);
            }
        }

        private void CargarColores()
        {
            var lista = coloresBO.ListarTodosColor();
            ddlColor.DataSource = lista;
            ddlColor.DataTextField = "nombre";
            ddlColor.DataValueField = "color_id";
            ddlColor.DataBind();
            ddlColor.Items.Insert(0, new ListItem("-- Seleccione un color --", "0"));
        }

        private void CargarTallas()
        {
            var lista = tallasBO.ListarTodosTalla();
            ddlTalla.DataSource = lista;
            ddlTalla.DataTextField = "numero";
            ddlTalla.DataValueField = "talla_id";
            ddlTalla.DataBind();
            ddlTalla.Items.Insert(0, new ListItem("-- Seleccione una talla --", "0"));
        }

        private void CargarVariantes()
        {
            try
            {
                var variantes = variantesBO.ListarPorProducto(productoId);

                var variantesAgrupadas = variantes
                    .GroupBy(v => v.color?.color_id ?? 0)
                    .Select(g => new
                    {
                        ColorId = g.Key,
                        ColorNombre = g.First().color?.nombre ?? "Sin color",
                        UrlImagen = g.First().url_imagen ?? "",
                        Tallas = g.Select(t => new
                        {
                            VarianteId = t.prod_variante_id,
                            ColorNombre = t.color?.nombre ?? "Sin color",
                            ColorId = t.color?.color_id ?? 0,
                            TallaId = t.talla?.talla_id ?? 0,
                            TallaNombre = t.talla?.numero ?? 0,
                            Stock = t.stock,
                            StockMinimo = t.stock_minimo,
                            AlertaStock = t.alerta_stock,
                            Disponible = t.disponible,
                            UrlImagen = t.url_imagen ?? ""
                        }).OrderBy(t => t.TallaNombre).ToList()
                    }).ToList();

                rptColores.DataSource = variantesAgrupadas;
                rptColores.DataBind();

                lblTotalVariantes.Text = variantesAgrupadas.Count.ToString();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar variantes: " + ex.Message);
            }
        }
        private void ActualizarDisponibilidad(string data)
        {
            try
            {
                var partes = data.Split('|');
                if (partes.Length != 2) return;

                int varianteId = Convert.ToInt32(partes[0]);
                bool disponible = partes[1].ToLower() == "true";

                var variante = variantesBO.ObtenerPorId(varianteId);
                if (variante == null) return;

                var usuario = ObtenerUsuarioSesion();

                if (usuario == null || usuario.usuarioId <= 0)
                {
                    lblMensaje.Text = "Error: No hay sesión de usuario válida.";
                    MantenerModalAbierto();
                    return;
                }

                // Mapea explicitamente al DTO que espera el servicio de Productos
                var usuarioProductosVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };
                usuario.usuarioIdSpecified = true;
                usuarioProductosVar.usuarioIdSpecified = true;

                int? resultado = variantesBO.Modificar(
                    varianteId,
                    variante.stock,
                    variante.stock_minimo,
                    variante.producto_id,
                    variante.color,
                    variante.talla,
                    variante.url_imagen,
                    disponible,
                    usuarioProductosVar
                );

                if (resultado != null && resultado > 0)
                {
                    CargarVariantes();
                    ScriptManager.RegisterStartupScript(
                        this,
                        GetType(),
                        "Success",
                        "mostrarMensajeExito('Disponibilidad actualizada correctamente');",
                        true
                    );
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error al actualizar disponibilidad: " + ex.Message);
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblErrorColor.Text = "";
            lblErrorTallas.Text = "";
            lblErrorStocks.Text = "";
            lblErrorStocksMinimos.Text = "";
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                int colorId = Convert.ToInt32(ddlColor.SelectedValue);
                string urlImagen = txtUrlImagen.Text.Trim();
                bool disponible = Request.Form["hdnDisponible"] == "true";
                var usuario = ObtenerUsuarioSesion();

                bool hayErrores = false;

                if (usuario == null || usuario.usuarioId <= 0)
                {
                    lblMensaje.Text = "Error: No hay sesión de usuario válida.";
                    MantenerModalAbierto();
                    return;
                }

                // Mapea explicitamente al DTO que espera el servicio de Productos
                var usuarioProductosVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };

                usuario.usuarioIdSpecified = true;
                usuarioProductosVar.usuarioIdSpecified = true;

                if (colorId == 0)
                {
                    lblErrorColor.Text = "Debe seleccionar un color";
                    hayErrores = true;
                }
                else
                {
                    // Verificar si ya existe este color para el producto
                    var variantes = variantesBO.ListarPorProducto(productoId);
                    if (variantes != null && variantes.Any(v => v.color?.color_id == colorId))
                    {
                        lblErrorColor.Text = "Este color ya existe para este producto. Seleccione otro color.";
                        hayErrores = true;
                    }
                }

                string tallasText = txtTallas.Text.Trim();
                string stocksText = txtStocks.Text.Trim();
                string stocksMinimoText = txtStocksMinimos.Text.Trim();

                // VALIDAR TALLAS
                if (string.IsNullOrEmpty(tallasText))
                {
                    lblErrorTallas.Text = "Debe ingresar al menos una talla";
                    hayErrores = true;
                }

                // VALIDAR STOCKS
                if (string.IsNullOrEmpty(stocksText))
                {
                    lblErrorStocks.Text = "Debe ingresar los stocks";
                    hayErrores = true;
                }

                // VALIDACIÓN COMPLETA DE TALLAS Y STOCKS
                var resultadoValidacion = ValidarTallasYStocks(tallasText, stocksText, stocksMinimoText);

                if (!resultadoValidacion.EsValido)
                {
                    // Mostrar errores específicos en cada campo
                    if (resultadoValidacion.TallasInvalidas.Count > 0)
                    {
                        lblErrorTallas.Text = $"Talla(s) no válida(s): {string.Join(", ", resultadoValidacion.TallasInvalidas)}";
                        hayErrores = true;
                    }

                    if (resultadoValidacion.StocksInvalidos.Count > 0)
                    {
                        lblErrorStocks.Text = $"Stock(s) no válido(s): {string.Join(", ", resultadoValidacion.StocksInvalidos)}";
                        hayErrores = true;
                    }

                    if (resultadoValidacion.StocksMinimoInvalidos.Count > 0)
                    {
                        lblErrorStocksMinimos.Text = $"Stock(s) mínimo(s) no válido(s): {string.Join(", ", resultadoValidacion.StocksMinimoInvalidos)}";
                        hayErrores = true;
                    }

                    //MantenerModalAbierto();
                    //return;
                }

                // Si hay errores básicos, detener aquí
                if (hayErrores)
                {
                    MantenerModalAbierto();
                    return;
                }

                var tallasStocks = resultadoValidacion.TallasStocks;

                var coloresProducto = coloresBO.ObtenerPorIdColor(colorId);
                // Convertir categoría al tipo que espera ProductosBO
                var color = new KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO
                {
                    color_id = coloresProducto.color_id,
                    nombre = coloresProducto.nombre
                };
                color.color_idSpecified = true;

                if (string.IsNullOrEmpty(urlImagen))
                {
                    urlImagen = "";
                }

                string urlCompleta = "/Images/Productos/" + urlImagen;

                int insertadas = 0;
                List<string> errores = new List<string>();

                foreach (var item in tallasStocks)
                {
                    var tallaVariante = tallasBO.ObtenerPorIdTalla(item.TallaId);
                    var talla = new KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO
                    {
                        talla_id = tallaVariante.talla_id,
                        numero = tallaVariante.numero,
                        talla_idSpecified = true,
                        numeroSpecified = true
                    };

                    int? resultado = variantesBO.Insertar(
                        item.Stock,
                        item.StockMinimo,
                        productoId,
                        color,
                        talla,
                        urlCompleta,
                        disponible,
                        usuarioProductosVar
                    );

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "No se pudo crear la variante.<br/>" +
                                        string.Join("<br/>", errores);
                        MantenerModalAbierto();
                        return;
                    }
                    insertadas++;
                }

                LimpiarFormulario();
                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "SuccessVariante",
                    "cerrarModal(); mostrarMensajeExito('✓ " + insertadas + " variante(s) creada(s) correctamente');",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger d-block mb-2";
                lblMensaje.Text = "Error: " + ex.Message;
                MantenerModalAbierto();
            }
        }

        private void CargarTallasEditar()
        {
            var lista = tallasBO.ListarTodosTalla();
            ddlTallaModif.DataSource = lista;
            ddlTallaModif.DataTextField = "numero";
            ddlTallaModif.DataValueField = "talla_id";
            ddlTallaModif.DataBind();
            ddlTallaModif.Items.Insert(0, new ListItem("-- Seleccione una talla --", "0"));
        }

        protected void btnModificaciones_Click(object sender, EventArgs e)
        {
            lblErrorStockEditar.Text = "";
            lblErrorStockMinimoEditar.Text = "";
            lblErrorTallaModif.Text = "";
            lblMensajeModificaciones.Text = "";
            lblMensajeModificaciones.CssClass = "text-warning d-block mb-2";

            try
            {
                int varianteId = Convert.ToInt32(hfVarianteId.Value);

                string stockText = txtStockEditar.Text?.Trim() ?? "";
                string stockMinimoText = txtStockMinimoEditar.Text?.Trim() ?? "";
                string nuevoUrl = txtUrlImagenModif.Text?.Trim() ?? "";
                int nuevaTallaId = Convert.ToInt32(ddlTallaModif.SelectedValue);
                bool hayErrores = false;
                int nuevoStock = 0;
                int nuevoStockMinimo = 0;

                // VALIDAR STOCK
                if (string.IsNullOrEmpty(stockText))
                {
                    lblErrorStockEditar.Text = "Debe ingresar un valor para el stock";
                    hayErrores = true;
                }
                else if (!int.TryParse(stockText, out nuevoStock))
                {
                    lblErrorStockEditar.Text = "El stock debe ser un número válido";
                    hayErrores = true;
                }
                else if (nuevoStock < 0)
                {
                    lblErrorStockEditar.Text = "El stock no puede ser negativo";
                    hayErrores = true;
                }

                // VALIDAR STOCK MÍNIMO
                if (string.IsNullOrEmpty(stockMinimoText))
                {
                    lblErrorStockMinimoEditar.Text = "Debe ingresar un valor para el stock mínimo";
                    hayErrores = true;
                }
                else if (!int.TryParse(stockMinimoText, out nuevoStockMinimo))
                {
                    lblErrorStockMinimoEditar.Text = "El stock mínimo debe ser un número válido";
                    hayErrores = true;
                }
                else if (nuevoStockMinimo < 0)
                {
                    lblErrorStockMinimoEditar.Text = "El stock mínimo no puede ser negativo";
                    hayErrores = true;
                }

                // VALIDAR TALLA
                if (nuevaTallaId == 0)
                {
                    lblErrorTallaModif.Text = "Debe seleccionar una talla";
                    hayErrores = true;
                }

                // Si hay errores, mantener modal abierto
                if (hayErrores)
                {
                    MantenerModalModificacionesAbierto(varianteId);
                    return;
                }

                var usuario = ObtenerUsuarioSesion();
                // Mapea explicitamente al DTO que espera el servicio de Productos
                var usuarioProductosVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };

                var variante = variantesBO.ObtenerPorId(varianteId);
                if (variante == null)
                {
                    lblMensajeModificaciones.Text = "Variante no encontrada.";
                    return;
                }

                // Verificar si existe otra variante con el mismo color y la nueva talla
                var variantes = variantesBO.ListarPorProducto(productoId);
                var existeDuplicado = variantes.Any(v =>
                    v.prod_variante_id != varianteId &&
                    v.color?.color_id == variante.color?.color_id &&
                    v.talla?.talla_id == nuevaTallaId
                );

                if (existeDuplicado)
                {
                    lblErrorTallaModif.Text = "Ya existe una variante con este color y talla";
                    MantenerModalModificacionesAbierto(varianteId);
                    return;
                }

                // Obtener la nueva talla
                var tallaVar = tallasBO.ObtenerPorIdTalla(nuevaTallaId);
                var nuevaTalla = new KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO
                {
                    talla_id = tallaVar.talla_id,
                    numero = tallaVar.numero,
                    talla_idSpecified = true,
                    numeroSpecified = true
                };

                string urlCompleta;

                if (string.IsNullOrEmpty(nuevoUrl))
                {
                    urlCompleta = "";
                }
                else
                {
                    urlCompleta = "/Images/Productos/" + nuevoUrl;
                }

                // Capturar y verificar el resultado
                int? resultado = variantesBO.Modificar(
                    varianteId,
                    nuevoStock,
                    nuevoStockMinimo,
                    variante.producto_id,
                    variante.color,
                    nuevaTalla,
                    urlCompleta,
                    variante.disponible,
                    usuarioProductosVar
                );

                if (resultado == null || resultado <= 0)
                {
                    lblMensajeModificaciones.CssClass = "text-warning d-block mb-2";
                    lblMensajeModificaciones.Text = "No se pudo actualizar la variante. Verifique que los datos sean válidos.";
                    return;
                }

                lblMensajeModificaciones.CssClass = "text-success d-block mb-2";
                lblMensajeModificaciones.Text = "Variante actualizada correctamente.";

                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "CerrarModalModificaciones",
                    "cerrarModalModificaciones();",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensajeModificaciones.CssClass = "text-danger d-block mb-2";
                lblMensajeModificaciones.Text = "Error: " + ex.Message;
                MantenerModalModificacionesAbierto(Convert.ToInt32(hfVarianteId.Value));
            }
        }

        protected void btnGuardarAbast_Click(object sender, EventArgs e)
        {
            try
            {
                // Obtener datos del formulario
                int varianteId = int.Parse(hfVarianteIdAbast.Value);
                string tipoMovimiento = Request.Form["hdnTipoMovimiento"];
                string descripcion = txtDescripcionAbast.Text.Trim();

                // Validar que se seleccionó tipo de movimiento
                if (string.IsNullOrEmpty(tipoMovimiento))
                {
                    lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Debe seleccionar un tipo de movimiento</span>";
                    ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                        "mantenerModalAbastecimientoAbierto();", true);
                    return;
                }

                // Obtener usuario actual
                usuariosDTO usuario = ObtenerUsuario();
                if (usuario == null)
                {
                    lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Error: Usuario no identificado</span>";
                    ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                        "mantenerModalAbastecimientoAbierto();", true);
                    return;
                }

                // === DTOs ===
                // Convertir usuario al tipo que espera MovInventarioBO
                var usuarioActual = new KawkiWebBusiness.KawkiWebWSMovimientosInventario.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                };
                usuarioActual.usuarioIdSpecified = true;
                usuario.usuarioIdSpecified = true;

                productosVariantesDTO variante = ObtenerVarianteProducto(varianteId);
                if (variante == null)
                {
                    lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> No se encontró la variante</span>";
                    ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                        "mantenerModalAbastecimientoAbierto();", true);
                    return;
                }

                // === DTOs ===
                // Convertir variante al tipo que espera MovInventarioBO
                var varianteActual = new KawkiWebBusiness.KawkiWebWSMovimientosInventario.productosVariantesDTO
                {
                    prod_variante_id = variante.prod_variante_id,
                };
                varianteActual.prod_variante_idSpecified = true;

                int resultado = 0;

                if (tipoMovimiento == "INGRESO")
                {
                    // Validar cantidad
                    if (!int.TryParse(txtCantidadIngreso.Text, out int cantidad) || cantidad <= 0)
                    {
                        lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Ingrese una cantidad mayor a 0</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                            "mantenerModalAbastecimientoAbierto();", true);
                        return;
                    }

                    // Registrar ingreso (la descripción es opcional)
                    resultado = movimientosBO.RegistrarIngresoMovInventario(
                        varianteActual,
                        cantidad,
                        descripcion,
                        usuarioActual
                    );

                    if (resultado > 0)
                    {
                        lblMensajeAbast.Text = "<span style='color: #28a745;'><i class='fas fa-check-circle'></i> Ingreso registrado exitosamente (+" + cantidad + " unidades)</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "exito",
                            "mostrarMensajeExito('Ingreso de " + cantidad + " unidades registrado correctamente');", true);
                    }
                    else
                    {
                        lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Error al registrar el ingreso</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                            "mantenerModalAbastecimientoAbierto();", true);
                        return;
                    }
                }
                else if (tipoMovimiento == "AJUSTE")
                {
                    // Validar nuevo stock
                    if (!int.TryParse(txtNuevoStock.Text, out int nuevoStock) || nuevoStock < 0)
                    {
                        lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Ingrese un nuevo stock válido (mayor o igual a 0)</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                            "mantenerModalAbastecimientoAbierto();", true);
                        return;
                    }

                    // Validar descripción obligatoria para AJUSTE
                    if (string.IsNullOrWhiteSpace(descripcion))
                    {
                        lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> La descripción es obligatoria para ajustes. Explique el motivo del cambio</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                            "mantenerModalAbastecimientoAbierto();", true);
                        return;
                    }

                    // Crear ajuste
                    resultado = movimientosBO.CrearAjusteInventarioMovInventario(
                        varianteActual,
                        nuevoStock,
                        descripcion,
                        usuarioActual
                    );

                    if (resultado > 0)
                    {
                        lblMensajeAbast.Text = "<span style='color: #28a745;'><i class='fas fa-check-circle'></i> Ajuste de inventario registrado exitosamente</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "exito",
                            "mostrarMensajeExito('Ajuste de inventario registrado correctamente');", true);
                    }
                    else
                    {
                        lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Error al registrar el ajuste</span>";
                        ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                            "mantenerModalAbastecimientoAbierto();", true);
                        return;
                    }
                }

                // Si fue exitoso, recarga la tabla y cierra el modal
                if (resultado > 0)
                {
                    CargarVariantes(); // Usa tu método existente para recargar
                    ScriptManager.RegisterStartupScript(this, GetType(), "cerrarModal",
                        "setTimeout(function(){ cerrarModalAbastecimiento(); }, 1500);", true);
                }
            }
            catch (Exception ex)
            {
                lblMensajeAbast.Text = "<span style='color: #dc3545;'><i class='fas fa-exclamation-circle'></i> Error: " + ex.Message + "</span>";
                ScriptManager.RegisterStartupScript(this, GetType(), "mantenerModal",
                    "mantenerModalAbastecimientoAbierto();", true);
            }
        }

        /// <summary>
        /// Obtiene el usuario actual de la sesión
        /// </summary>
        private usuariosDTO ObtenerUsuario()
        {
            try
            {
                // Intenta obtener del objeto de sesión
                if (Session["UsuarioActual"] != null)
                {
                    return (usuariosDTO)Session["UsuarioActual"];
                }

                // Si no existe, intenta crear uno con el ID de la sesión
                if (Session["UsuarioId"] != null && int.TryParse(Session["UsuarioId"].ToString(), out int usuarioId))
                {
                    return new usuariosDTO { usuarioId = usuarioId };
                }

                return null;
            }
            catch
            {
                return null;
            }
        }

        /// <summary>
        /// Obtiene una variante específica del producto por su ID
        /// </summary>
        private productosVariantesDTO ObtenerVarianteProducto(int varianteId)
        {
            try
            {
                // Obtener variante
                var varianteBO = new ProductosVariantesBO();

                return varianteBO.ObtenerPorId(varianteId);
            }
            catch
            {
                return null;
            }
        }

        // MODAL ABIERTO
        // Cuando hay ERRORES, solo mantener visible
        private void MantenerModalModificacionesAbierto(int varianteId)
        {
            string script = "mantenerModalModificacionesAbierto();";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModalModificaciones", script, true);
        }

        // LIMPIA FORMULARIOS
        private void LimpiarFormularioModificaciones()
        {
            txtStockEditar.Text = "";
            txtStockMinimoEditar.Text = "";
            lblErrorStockEditar.Text = "";
            lblErrorStockMinimoEditar.Text = "";
            lblErrorTallaModif.Text = "";
            lblMensajeModificaciones.Text = "";
        }

        protected void btnGuardarTalla_Click(object sender, EventArgs e)
        {
            // Limpiar TODOS los mensajes de error
            lblErrorTallaSelect.Text = "";
            lblErrorStockTalla.Text = "";
            lblErrorStockMinimoTalla.Text = "";
            lblMensajeTalla.Text = "";
            lblMensajeTalla.CssClass = "text-warning d-block mb-2";

            try
            {
                int colorId = Convert.ToInt32(hfColorIdTalla.Value);
                int tallaId = Convert.ToInt32(ddlTalla.SelectedValue);
                string stockText = txtStockTalla.Text.Trim();
                string stockMinimoText = txtStockMinimoTalla.Text.Trim();
                bool hayErrores = false;
                int stock = 0;
                int stockMinimo = 0;

                // Validar talla
                if (tallaId == 0)
                {
                    lblErrorTallaSelect.Text = "Seleccione una talla";
                    hayErrores = true;
                }

                // Validar stock
                if (string.IsNullOrEmpty(stockText))
                {
                    lblErrorStockTalla.Text = "Ingrese el stock inicial";
                    hayErrores = true;
                }
                else if (!int.TryParse(stockText, out stock) || stock < 0)
                {
                    lblErrorStockTalla.Text = "El stock no puede ser negativo";
                    hayErrores = true;
                }

                // VALIDAR STOCK MÍNIMO
                if (string.IsNullOrEmpty(stockMinimoText))
                {
                    lblErrorStockMinimoTalla.Text = "Ingrese el stock mínimo";
                    hayErrores = true;
                }
                else if (!int.TryParse(stockMinimoText, out stockMinimo) || stockMinimo < 0)
                {
                    lblErrorStockMinimoTalla.Text = "El stock mínimo no puede ser negativo";
                    hayErrores = true;
                }

                // Si hay errores, mantener modal abierto
                if (hayErrores)
                {
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                var usuario = ObtenerUsuarioSesion();

                // Mapea explicitamente al DTO que espera el servicio de Productos
                var usuarioProductosVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };

                var variantes = variantesBO.ListarPorProducto(productoId);

                // Verificar si ya existe esta combinación
                if (variantes.Any(v => v.color?.color_id == colorId && v.talla?.talla_id == tallaId))
                {
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
                    lblMensajeTalla.Text = "Esta talla ya existe para este color. Seleccione otra talla.";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                // Obtener la URL de imagen del color existente
                var varianteConMismoColor = variantes.FirstOrDefault(v => v.color?.color_id == colorId);
                string urlImagen = varianteConMismoColor?.url_imagen ?? "";

                if (string.IsNullOrEmpty(urlImagen))
                {
                    lblMensajeTalla.CssClass = "text-danger d-block mb-2";
                    lblMensajeTalla.Text = "No se encontró URL de imagen para este color.";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                var colorVar = coloresBO.ObtenerPorIdColor(colorId);
                var color = new KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO
                {
                    color_id = colorVar.color_id,
                    nombre = colorVar.nombre
                };
                var tallaVar = tallasBO.ObtenerPorIdTalla(tallaId);
                var talla = new KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO
                {
                    talla_id = tallaVar.talla_id,
                    numero = tallaVar.numero
                };
                talla.talla_idSpecified = true;
                talla.numeroSpecified = true;
                color.color_idSpecified = true;
                usuario.usuarioIdSpecified = true;
                usuarioProductosVar.usuarioIdSpecified = true;

                // Capturar y verificar el resultado
                int? resultado = variantesBO.Insertar(
                    stock,
                    stockMinimo,
                    productoId,
                    color,
                    talla,
                    urlImagen,
                    true,
                    usuarioProductosVar
                );

                if (resultado == null || resultado <= 0)
                {
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
                    lblMensajeTalla.Text = "No se pudo agregar la talla. Verifique que los datos sean válidos.";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                // ÉXITO - Cerrar modal y recargar
                LimpiarFormularioTalla();
                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "SuccessTalla",
                    "cerrarModalTalla(); mostrarMensajeExito('Talla agregada correctamente');",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensajeTalla.CssClass = "text-danger d-block mb-2";
                lblMensajeTalla.Text = "Error: " + ex.Message;
                MantenerModalTallaAbierto(Convert.ToInt32(hfColorIdTalla.Value));
            }
        }

        // MODAL ABIERTO
        private void MantenerModalTallaAbierto(int colorId)
        {
            string colorNombre = "";
            try
            {
                var color = coloresBO.ObtenerPorIdColor(colorId);
                colorNombre = color?.nombre ?? "";
            }
            catch { }

            string script = $"MantenerModalTallaAbierto('{colorId}', '{colorNombre}');";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModalTallaAbierto", script, true);
        }

        private void LimpiarFormularioTalla()
        {
            ddlTalla.SelectedIndex = 0;
            txtStockTalla.Text = "";
            txtStockMinimoTalla.Text = "";
            lblMensajeTalla.Text = "";
        }

        private ValidacionResult ValidarTallasYStocks(string tallasText, string stocksText, string stocksMinimoText)
        {
            var resultado = new ValidacionResult
            {
                EsValido = true,
                TallasStocks = new List<TallaStockItem>(),
                TallasInvalidas = new List<string>(),
                StocksInvalidos = new List<string>(),
                StocksMinimoInvalidos = new List<string>()
            };

            var tallas = tallasText.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
            var stocks = stocksText.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
            var stocksMinimos = string.IsNullOrEmpty(stocksMinimoText)
                ? new string[0]
                : stocksMinimoText.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries);

            var tallasValidas = tallasBO.ListarTodosTalla();
            if (tallasValidas == null)
            {
                resultado.EsValido = false;
                resultado.TallasInvalidas.Add("Error al cargar las tallas del sistema");
                return resultado;
            }

            // Lista para detectar duplicados
            var tallasYaProcesadas = new HashSet<int>();

            // Validar que haya la misma cantidad de tallas y stocks
            if (tallas.Length != stocks.Length)
            {
                resultado.EsValido = false;
                if (tallas.Length > stocks.Length)
                {
                    resultado.TallasInvalidas.Add($"cantidad no coincide (tallas: {tallas.Length}, stocks: {stocks.Length})");
                }
                else
                {
                    resultado.StocksInvalidos.Add($"cantidad no coincide (tallas: {tallas.Length}, stocks: {stocks.Length})");
                }
                return resultado;
            }

            for (int i = 0; i < tallas.Length; i++)
            {
                string tallaStr = tallas[i].Trim();
                string stockStr = i < stocks.Length ? stocks[i].Trim() : "";
                string stockMinimoStr = i < stocksMinimos.Length ? stocksMinimos[i].Trim() : "5";

                bool tallaValida = true;
                bool stockValido = true;
                bool stockMinimoValido = true;

                int tallaNum = 0;
                int stock = 0;
                int stockMinimo = 5;
                int tallaId = 0;

                // VALIDAR TALLA (independiente)
                if (!int.TryParse(tallaStr, out tallaNum))
                {
                    resultado.TallasInvalidas.Add($"{tallaStr} (no es número)");
                    tallaValida = false;
                }
                else
                {
                    var tallaBD = tallasValidas.FirstOrDefault(t => t.numero == tallaNum);
                    if (tallaBD == null)
                    {
                        resultado.TallasInvalidas.Add($"{tallaStr} (no existe)");
                        tallaValida = false;
                    }
                    else
                    {
                        tallaId = tallaBD.talla_id;

                        // VALIDAR DUPLICADOS
                        if (tallasYaProcesadas.Contains(tallaNum))
                        {
                            resultado.TallasInvalidas.Add($"{tallaStr} (duplicada)");
                            tallaValida = false;
                        }
                        else
                        {
                            tallasYaProcesadas.Add(tallaNum);
                        }
                    }
                }

                // VALIDAR STOCK (independiente, siempre se ejecuta)
                if (string.IsNullOrEmpty(stockStr))
                {
                    resultado.StocksInvalidos.Add($"talla {tallaStr} (vacío)");
                    stockValido = false;
                }
                else if (!int.TryParse(stockStr, out stock))
                {
                    resultado.StocksInvalidos.Add($"talla {tallaStr} ('{stockStr}' no es número)");
                    stockValido = false;
                }
                else if (stock < 0)
                {
                    resultado.StocksInvalidos.Add($"talla {tallaStr} ({stock} es negativo)");
                    stockValido = false;
                }

                // VALIDAR STOCK MÍNIMO (independiente, siempre se ejecuta)
                if (!string.IsNullOrEmpty(stockMinimoStr))
                {
                    if (!int.TryParse(stockMinimoStr, out stockMinimo))
                    {
                        resultado.StocksMinimoInvalidos.Add($"talla {tallaStr} ('{stockMinimoStr}' no es número)");
                        stockMinimoValido = false;
                    }
                    else if (stockMinimo < 0)
                    {
                        resultado.StocksMinimoInvalidos.Add($"talla {tallaStr} ({stockMinimo} es negativo)");
                        stockMinimoValido = false;
                    }
                }

                // ⭐ Solo agregar si TODO es válido
                if (tallaValida && stockValido && stockMinimoValido && tallaId > 0)
                {
                    resultado.TallasStocks.Add(new TallaStockItem
                    {
                        TallaId = tallaId,
                        Stock = stock,
                        StockMinimo = stockMinimo
                    });
                }
            }

            // Si hay algún error, marcar como no válido
            if (resultado.TallasInvalidas.Count > 0 || resultado.StocksInvalidos.Count > 0 || resultado.StocksMinimoInvalidos.Count > 0)
            {
                resultado.EsValido = false;
            }

            return resultado;
        }

        protected void btnVolver_Click(object sender, EventArgs e)
        {
            Response.Redirect("GestionProductos.aspx");
        }

        private void MantenerModalAbierto()
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", "MantenerModal();", true);
        }

        private void LimpiarFormulario()
        {
            ddlColor.SelectedIndex = 0;
            txtTallas.Text = "";
            txtStocks.Text = "";
            txtStocksMinimos.Text = "";
            txtUrlImagen.Text = "";
            lblErrorColor.Text = "";
            lblErrorTallas.Text = "";
            lblErrorStocks.Text = "";
            lblErrorStocksMinimos.Text = "";
            lblMensaje.Text = "";
        }

        private usuariosDTO ObtenerUsuarioSesion()
        {
            return new usuariosDTO
            {
                usuarioId = Convert.ToInt32(Session["UsuarioId"])
            };
        }

        private void MostrarError(string mensaje)
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "Error",
                $"mostrarMensajeError('{mensaje.Replace("'", "\\'")}');", true);
        }

        private void MostrarExito(string mensaje)
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "Exito",
                $"mostrarMensajeExito('{mensaje.Replace("'", "\\'")}');", true);
        }

        protected string RenderVariantesConRowspan(object dataItem)
        {
            var grupo = dataItem as dynamic;
            var tallas = grupo.Tallas as System.Collections.IEnumerable;

            if (tallas == null) return "";

            var tallasList = tallas.Cast<dynamic>().ToList();
            int rowspan = tallasList.Count;

            System.Text.StringBuilder html = new System.Text.StringBuilder();

            for (int i = 0; i < tallasList.Count; i++)
            {
                var talla = tallasList[i];

                html.Append("<tr>");

                if (i == 0)
                {
                    string urlImagen = string.IsNullOrEmpty(talla.UrlImagen.ToString())
                        ? "images/no-image.png"
                        : talla.UrlImagen.ToString();

                    html.AppendFormat(@"
                <td rowspan='{0}' style='text-align: center;'>
                    <img src='{1}' alt='{2}' class='img-color' />
                </td>
                <td rowspan='{0}'>
                    <div style='display: flex; justify-content: space-between; align-items: center; gap: 10px;'>
                        <span>{2}</span>
                        <button type='button' class='btn-agregar btn-sm' 
                            onclick='abrirModalAgregarTalla(""{3}"", ""{2}"")' 
                            title='Agregar otra talla a este color'>
                            <i class='fas fa-plus'></i>
                        </button>
                    </div>
                </td>",
                        rowspan,
                        urlImagen,
                        talla.ColorNombre,
                        grupo.ColorId
                    );
                }

                html.AppendFormat(@"
                    <td><strong>{0}</strong></td>
                    <td style='text-align: center;'><strong>{1}</strong></td>
                    <td style='text-align: center;'>{2}</td>
                    <td style='text-align: center;'>
                        {3}
                    </td>",
                    talla.TallaNombre,
                    talla.Stock,
                    talla.StockMinimo,
                    talla.Stock == 0
                        ? "<span class='badge-alerta badge-agotado'>🚫 Agotado</span>"
                        : (talla.Stock <= talla.StockMinimo
                            ? "<span class='badge-alerta badge-bajo'>⚠ Bajo</span>"
                            : "")
                );

                string activeNo = !(bool)talla.Disponible ? "active" : "";
                string activeSi = (bool)talla.Disponible ? "active" : "";

                html.AppendFormat(@"
            <td style='text-align: center;'>
                <div class='toggle-disponible'>
                    <button type='button' class='btn-no {0}'
                        onclick='cambiarDisponibilidad(this, ""{1}"", false)'>
                        <i class='fas fa-times'></i> No
                    </button>
                    <button type='button' class='btn-si {2}'
                        onclick='cambiarDisponibilidad(this, ""{1}"", true)'>
                        <i class='fas fa-check'></i> Sí
                    </button>
                </div>
            </td>",
                    activeNo,
                    talla.VarianteId,
                    activeSi
                );

                html.AppendFormat(@"
            <td>
                    <button type='button' class='btn-abastecimiento btn-sm' 
                        onclick='abrirModalAbastecimiento(""{0}"", ""{1} - Talla: {2}"", ""{5}"")' 
                        title='Registrar movimiento de inventario'>
                        <i class='fas fa-plus-circle'></i> Abastecimiento
                </button>

                <button type='button' class='btn-editar btn-sm' 
                    onclick='abrirModalModificaciones(""{0}"", ""{1}"", ""{2}"", ""{3}"", ""{4}"", ""{5}"", ""{6}"")'>
                    <i class='fas fa-edit'></i> Editar
                </button>
            </td>",
                    talla.VarianteId,
                    talla.ColorNombre,
                    talla.TallaNombre,
                    talla.TallaId,
                    talla.UrlImagen,
                    talla.Stock,
                    talla.StockMinimo
                );

                html.Append("</tr>");
            }

            return html.ToString();
        }
    }

    public class TallaStockItem
    {
        public int TallaId { get; set; }
        public int Stock { get; set; }
        public int StockMinimo { get; set; }
    }

    public class ValidacionResult
    {
        public bool EsValido { get; set; }
        public List<TallaStockItem> TallasStocks { get; set; }
        public List<string> TallasInvalidas { get; set; }
        public List<string> StocksInvalidos { get; set; }
        public List<string> StocksMinimoInvalidos { get; set; }
    }
}
