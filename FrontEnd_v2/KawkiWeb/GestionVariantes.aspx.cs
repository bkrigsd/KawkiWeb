using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSProductosVariantes;
using KawkiWebBusiness.KawkiWebWSTallas;
using coloresDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO;
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
        private int productoId = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            variantesBO = new ProductosVariantesBO();
            productosBO = new ProductosBO();
            coloresBO = new ColoresBO();
            tallasBO = new TallasBO();

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

            // Para el modal principal de agregar variante
            ddlTalla.DataSource = lista;
            ddlTalla.DataTextField = "numero";
            ddlTalla.DataValueField = "talla_id";
            ddlTalla.DataBind();
            ddlTalla.Items.Insert(0, new ListItem("-- Seleccione una talla --", "0"));

            // Para el modal de agregar talla a color existente
            ddlTallaAgregar.DataSource = lista;
            ddlTallaAgregar.DataTextField = "numero";
            ddlTallaAgregar.DataValueField = "talla_id";
            ddlTallaAgregar.DataBind();
            ddlTallaAgregar.Items.Insert(0, new ListItem("-- Seleccione una talla --", "0"));
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

        // ⭐ AGREGAR ESTE MÉTODO NUEVO:
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
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                int colorId = Convert.ToInt32(ddlColor.SelectedValue);
                int tallaId = Convert.ToInt32(ddlTalla.SelectedValue);
                string urlImagen = txtUrlImagen.Text.Trim();
                bool disponible = Request.Form["hdnDisponible"] == "true";
                var usuario = ObtenerUsuarioSesion();

                if (usuario == null || usuario.usuarioId <= 0)
                {
                    lblMensaje.Text = "Error: No hay sesión de usuario válida.";
                    MantenerModalAbierto();
                    return;
                }

                var usuarioProductosVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };

                // Validar color
                if (colorId == 0)
                {
                    lblMensaje.Text = "Debe seleccionar un color.";
                    MantenerModalAbierto();
                    return;
                }

                // Validar talla
                if (tallaId == 0)
                {
                    lblMensaje.Text = "Debe seleccionar una talla.";
                    MantenerModalAbierto();
                    return;
                }

                // Validar stock
                if (!int.TryParse(txtStock.Text.Trim(), out int stock))
                {
                    lblMensaje.Text = "El stock debe ser un número válido.";
                    MantenerModalAbierto();
                    return;
                }

                if (stock < 0)
                {
                    lblMensaje.Text = "El stock no puede ser negativo.";
                    MantenerModalAbierto();
                    return;
                }

                // Validar stock mínimo
                if (!int.TryParse(txtStockMinimo.Text.Trim(), out int stockMinimo))
                {
                    stockMinimo = 5; // Valor por defecto
                }

                if (stockMinimo < 0)
                {
                    lblMensaje.Text = "El stock mínimo no puede ser negativo.";
                    MantenerModalAbierto();
                    return;
                }

                // Verificar si ya existe esta combinación color-talla
                var variantes = variantesBO.ListarPorProducto(productoId);
                if (variantes.Any(v => v.color?.color_id == colorId && v.talla?.talla_id == tallaId))
                {
                    lblMensaje.Text = "Esta combinación de color y talla ya existe. Seleccione otra.";
                    MantenerModalAbierto();
                    return;
                }

                // Obtener color
                var coloresProducto = coloresBO.ObtenerPorIdColor(colorId);
                var color = new KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO
                {
                    color_id = coloresProducto.color_id,
                    nombre = coloresProducto.nombre,
                    color_idSpecified = true
                };

                // Obtener talla
                var tallaVariante = tallasBO.ObtenerPorIdTalla(tallaId);
                var talla = new KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO
                {
                    talla_id = tallaVariante.talla_id,
                    numero = tallaVariante.numero,
                    talla_idSpecified = true
                };

                // Preparar URL de imagen
                if (string.IsNullOrEmpty(urlImagen))
                {
                    urlImagen = "";
                }
                else
                {
                    urlImagen = "/Images/Productos/" + urlImagen;
                }

                // Insertar variante
                int? resultado = variantesBO.Insertar(
                    stock,
                    stockMinimo,
                    productoId,
                    color,
                    talla,
                    urlImagen,
                    disponible,
                    usuarioProductosVar
                );

                if (resultado == null || resultado <= 0)
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "No se pudo crear la variante. Verifique que los datos sean válidos.";
                    MantenerModalAbierto();
                    return;
                }

                // Éxito
                lblMensaje.CssClass = "text-success d-block mb-2";
                lblMensaje.Text = "✓ Variante creada correctamente.";
                LimpiarFormulario();
                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "CerrarModal",
                    "cerrarModal();",
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

        protected void btnGuardarStock_Click(object sender, EventArgs e)
        {
            lblMensajeStock.Text = "";
            lblMensajeStock.CssClass = "text-warning d-block mb-2";

            try
            {
                int varianteId = Convert.ToInt32(hfVarianteId.Value);

                string stockText = txtStockEditar.Text?.Trim() ?? "";
                string stockMinimoText = txtStockMinimoEditar.Text?.Trim() ?? "";

                if (string.IsNullOrEmpty(stockText))
                {
                    lblMensajeStock.Text = "Debe ingresar un valor para el stock.";
                    return;
                }

                if (string.IsNullOrEmpty(stockMinimoText))
                {
                    lblMensajeStock.Text = "Debe ingresar un valor para el stock mínimo.";
                    return;
                }

                if (!int.TryParse(stockText, out int nuevoStock))
                {
                    lblMensajeStock.Text = "El stock debe ser un número válido.";
                    return;
                }

                if (!int.TryParse(stockMinimoText, out int nuevoStockMinimo))
                {
                    lblMensajeStock.Text = "El stock mínimo debe ser un número válido.";
                    return;
                }

                var usuario = ObtenerUsuarioSesion();
                // Mapea explicitamente al DTO que espera el servicio de Productos
                var usuarioProductosVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };

                if (nuevoStock < 0)
                {
                    lblMensajeStock.Text = "El stock no puede ser negativo.";
                    return;
                }

                if (nuevoStockMinimo < 0)
                {
                    lblMensajeStock.Text = "El stock mínimo no puede ser negativo.";
                    return;
                }

                var variante = variantesBO.ObtenerPorId(varianteId);
                if (variante == null)
                {
                    lblMensajeStock.Text = "Variante no encontrada.";
                    return;
                }

                // Capturar y verificar el resultado
                int? resultado = variantesBO.Modificar(
                    varianteId,
                    nuevoStock,
                    nuevoStockMinimo,
                    variante.producto_id,
                    variante.color,
                    variante.talla,
                    variante.url_imagen,
                    variante.disponible,
                    usuarioProductosVar
                );

                if (resultado == null || resultado <= 0)
                {
                    lblMensajeStock.CssClass = "text-warning d-block mb-2";
                    lblMensajeStock.Text = "No se pudo actualizar el stock. Verifique que los datos sean válidos.";
                    return;
                }

                lblMensajeStock.CssClass = "text-success d-block mb-2";
                lblMensajeStock.Text = "Stock actualizado correctamente.";

                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "CerrarModalStock",
                    "cerrarModalStock();",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensajeStock.CssClass = "text-danger d-block mb-2";
                lblMensajeStock.Text = "Error: " + ex.Message;
            }
        }

        protected void btnGuardarTalla_Click(object sender, EventArgs e)
        {
            // Limpiar mensajes de error
            lblMensajeTalla.Text = "";
            lblMensajeTalla.CssClass = "text-danger d-block mb-2";

            try
            {
                int colorId = Convert.ToInt32(hfColorIdTalla.Value);
                int tallaId = Convert.ToInt32(ddlTallaAgregar.SelectedValue);

                string stockText = txtStockTalla.Text.Trim();
                string stockMinimoText = txtStockMinimoTalla.Text.Trim();

                // Validar talla
                if (tallaId == 0)
                {
                    lblMensajeTalla.Text = "Seleccione una talla";
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                // Validar stock
                if (string.IsNullOrEmpty(stockText))
                {
                    lblMensajeTalla.Text = "Ingrese el stock inicial";
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                if (!int.TryParse(stockText, out int stock) || stock < 0)
                {
                    lblMensajeTalla.Text = "El stock no puede ser negativo";
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                // Validar stock mínimo
                if (string.IsNullOrEmpty(stockMinimoText))
                {
                    lblMensajeTalla.Text = "Ingrese el stock mínimo";
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
                    MantenerModalTallaAbierto(colorId);
                    return;
                }

                if (!int.TryParse(stockMinimoText, out int stockMinimo) || stockMinimo < 0)
                {
                    lblMensajeTalla.Text = "El stock mínimo no puede ser negativo";
                    lblMensajeTalla.CssClass = "text-warning d-block mb-2";
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
                    nombre = colorVar.nombre,
                    color_idSpecified = true
                };

                var tallaVar = tallasBO.ObtenerPorIdTalla(tallaId);
                var talla = new KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO
                {
                    talla_id = tallaVar.talla_id,
                    numero = tallaVar.numero,
                    talla_idSpecified = true
                };

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
                    lblMensajeTalla.CssClass = "text-danger d-block mb-2";
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

        // ⭐ AGREGAR ESTOS DOS MÉTODOS NUEVOS AL FINAL DE LA CLASE
        private void MantenerModalTallaAbierto(int colorId)
        {
            string colorNombre = "";
            try
            {
                var color = coloresBO.ObtenerPorIdColor(colorId);
                colorNombre = color?.nombre ?? "";
            }
            catch { }

            string script = $"abrirModalAgregarTalla('{colorId}', '{colorNombre}');";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModalTalla", script, true);
        }

        private void LimpiarFormularioTalla()
        {
            ddlTallaAgregar.SelectedIndex = 0;
            txtStockTalla.Text = "";
            txtStockMinimoTalla.Text = "";
            lblMensajeTalla.Text = "";
        }

        protected void btnVolver_Click(object sender, EventArgs e)
        {
            Response.Redirect("GestionProductos.aspx");
        }

        private void MantenerModalAbierto()
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", "abrirModalRegistro();", true);
        }

        private void LimpiarFormulario()
        {
            ddlColor.SelectedIndex = 0;
            ddlTalla.SelectedIndex = 0;
            txtStock.Text = "";
            txtStockMinimo.Text = "";
            txtUrlImagen.Text = "";
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
                        : (talla.Stock < talla.StockMinimo
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
                <button type='button' class='btn-editar btn-sm' 
                    onclick='abrirModalEditarStock(""{0}"", ""{1}"", ""{2}"")'>
                    <i class='fas fa-edit'></i> Editar
                </button>
            </td>",
                    talla.VarianteId,
                    talla.ColorNombre,
                    talla.TallaNombre
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
}