using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSEstilos;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSProductosVariantes;
using usuariosDTO = KawkiWebBusiness.KawkiWebWSProductos.usuariosDTO;

namespace KawkiWeb
{
    public partial class GestionProductos : Page
    {
        private ProductosBO productosBO;
        private ProductosVariantesBO variantesBO;
        private CategoriasBO categoriasBO;
        private EstilosBO estilosBO;

        protected void Page_Load(object sender, EventArgs e)
        {
            productosBO = new ProductosBO();
            variantesBO = new ProductosVariantesBO();
            estilosBO = new EstilosBO();
            categoriasBO = new CategoriasBO();

            if (!IsPostBack)
            {
                CargarCategorias();
                CargarEstilos();
                CargarProductos();
            }
        }

        private void CargarCategorias()
        {
            var lista = categoriasBO.ListarTodosCategoria();

            ddlCategoria.DataSource = lista;
            ddlCategoria.DataTextField = "Nombre";   // lo que verá el usuario
            ddlCategoria.DataValueField = "categoria_Id";      // el ID real de la BD
            ddlCategoria.DataBind();

            ddlCategoria.Items.Insert(0, new ListItem("-- Seleccione --", "0"));
        }

        private void CargarEstilos()
        {
            var lista = estilosBO.ListarTodosEstilo();

            ddlEstilo.DataSource = lista;
            ddlEstilo.DataTextField = "Nombre";
            ddlEstilo.DataValueField = "estilo_Id";
            ddlEstilo.DataBind();

            ddlEstilo.Items.Insert(0, new ListItem("-- Seleccione --", "0"));
        }


        private void CargarProductos()
        {
            try
            {
                var productos = productosBO.ListarTodosProducto();

                // Crear lista personalizada para el GridView
                var productosGrid = productos.Select(p => new
                {
                    ProductoId = p.producto_id,
                    Descripcion = p.descripcion ?? "",
                    Categoria = p.categoria?.nombre ?? "Sin categoría",
                    Estilo = p.estilo?.nombre ?? "Sin estilo",
                    Precio = p.precio_venta,
                    CantidadVariantes = ObtenerCantidadVariantes(p.producto_id),

                    IdCategoria = p.categoria.categoria_id,
                    IdEstilo = p.estilo.estilo_id
                }).ToList();

                gvProductos.DataSource = productosGrid;
                gvProductos.DataBind();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar productos: " + ex.Message);
            }
        }

        // Método auxiliar para contar variantes (colores únicos)
        private int ObtenerCantidadVariantes(int productoId)
        {
            try
            {
                var variantes = variantesBO.ListarPorProducto(productoId);

                // Contar colores únicos
                return variantes
                    .GroupBy(v => v.color?.color_id ?? 0)
                    .Count();
            }
            catch
            {
                return 0;
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblErrorDescripcion.Text = "";
            lblErrorCategoria.Text = "";
            lblErrorEstilo.Text = "";
            lblErrorPrecio.Text = "";
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                bool esEdicion = hfProductoId.Value != "0";
                bool hayErrores = false;
                string descripcion = txtDescripcion.Text.Trim();

                if (string.IsNullOrEmpty(descripcion))
                {
                    lblErrorDescripcion.Text = "La descripción es obligatoria";
                    hayErrores = true;
                }

                int categoriaId = Convert.ToInt32(ddlCategoria.SelectedValue);

                if (categoriaId == 0)
                {
                    lblMensaje.Text = "Debe seleccionar una categoría.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                int estiloId = Convert.ToInt32(ddlEstilo.SelectedValue);
                if (estiloId == 0)
                {
                    lblMensaje.Text = "Debe seleccionar un estilo.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                double precio_Venta;
                if (!double.TryParse(txtPrecio.Text.Trim(), out precio_Venta) || precio_Venta <= 0)
                {
                    lblErrorPrecio.Text = "Ingrese un precio válido mayor a 0";
                    hayErrores = true;
                }

                // Si hay errores, mantener modal abierto
                if (hayErrores)
                {
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                // VALIDAR PRODUCTO DUPLICADO
                int productoIdActual = esEdicion ? Convert.ToInt32(hfProductoId.Value) : 0;
                if (ExisteProductoDuplicado(categoriaId, estiloId, productoIdActual))
                {
                    lblMensaje.CssClass = "text-warning d-block mb-2";
                    lblMensaje.Text = "<i class='fas fa-exclamation-triangle'></i> Ya existe un producto con esta categoría y estilo. Use 'Editar Variantes' para agregar más colores o tallas.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                var usuario = ObtenerUsuarioSesion();

                if (usuario == null || usuario.usuarioId <= 0)
                {
                    lblMensaje.CssClass = "text-warning d-block mb-2";
                    lblMensaje.Text = "Error: No hay sesión de usuario válida.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                // Mapea explicitamente al DTO que espera el servicio de Productos
                var usuarioProductos = new KawkiWebBusiness.KawkiWebWSProductos.usuariosDTO
                {
                    usuarioId = usuario.usuarioId,
                    usuarioIdSpecified = true
                };

                var categoriaProducto = categoriasBO.ObtenerPorIdCategoria(categoriaId);
                var estiloProducto = estilosBO.ObtenerPorIdEstilos(estiloId);
                // === DTOs ===
                // Convertir categoría y estilo al tipo que espera ProductosBO
                var categoria = new KawkiWebBusiness.KawkiWebWSProductos.categoriasDTO
                {
                    categoria_id = categoriaProducto.categoria_id,
                    nombre = categoriaProducto.nombre
                };
                var estilo = new KawkiWebBusiness.KawkiWebWSProductos.estilosDTO
                {
                    estilo_id = estiloProducto.estilo_id,
                    nombre = estiloProducto.nombre
                };

                //Asegurar que se pasen los ids:
                categoria.categoria_idSpecified = true;
                estilo.estilo_idSpecified = true;
                usuario.usuarioIdSpecified = true;
                usuarioProductos.usuarioIdSpecified = true;

                if (esEdicion)
                {
                    // === EDITAR ===
                    int productoId = Convert.ToInt32(hfProductoId.Value);

                    // CAPTURAR Y VERIFICAR EL RESULTADO
                    int? resultado = productosBO.ModificarProducto(
                        productoId,
                        descripcion,
                        categoria,
                        estilo,
                        precio_Venta,
                        usuarioProductos
                    );

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-warning d-block mb-2";
                        lblMensaje.Text = "No se pudo actualizar el producto. Verifique que los datos sean válidos.";
                        MantenerModalAbierto(esEdicion);
                        return;
                    }

                    LimpiarFormulario();
                    CargarProductos();
                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "Producto actualizado correctamente.";
                }
                else
                {
                    // CAPTURAR Y VERIFICAR EL RESULTADO
                    int? resultado = productosBO.InsertarProducto(
                        descripcion,
                        categoria,
                        estilo,
                        precio_Venta,
                        usuarioProductos
                    );

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "No se pudo registrar el producto. Verifique que los datos sean válidos.";
                        MantenerModalAbierto(esEdicion);
                        return;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "Producto registrado correctamente.";
                }

                LimpiarFormulario();
                CargarProductos();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "CerrarModal",
                    "cerrarModal(); mostrarMensajeExito('Operación exitosa');",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger d-block mb-2";
                lblMensaje.Text = "Error: " + ex.Message;
                MantenerModalAbierto(hfProductoId.Value != "0");
            }
        }

        private bool ExisteProductoDuplicado(int categoriaId, int estiloId, int productoIdActual = 0)
        {
            try
            {
                var todosProductos = productosBO.ListarTodosProducto();

                return todosProductos.Any(p =>
                    p.producto_id != productoIdActual && // Excluir el producto actual si es edición
                    p.categoria.categoria_id == categoriaId &&
                    p.estilo.estilo_id == estiloId
                );
            }
            catch
            {
                return false;
            }
        }
        private void MantenerModalAbierto(bool esEdicion)
        {
            // ⭐ Usar funciones que NO limpian
            string script = esEdicion ? "reabrirModalEditar();" : "reabrirModalRegistro();";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", script, true);
        }

        // =====================================================
        // 🔹 Manejo de comandos del GridView
        // =====================================================
        protected void gvProductos_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            try
            {
                int productoId = Convert.ToInt32(e.CommandArgument);

                switch (e.CommandName)
                {
                    case "VerVariantes":
                        Response.Redirect($"GestionVariantes.aspx?productoId={productoId}");
                        break;

                    case "Editar":
                        CargarProductoParaEdicion(productoId);
                        break;
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error: " + ex.Message);
            }
        }

        private void CargarProductoParaEdicion(int productoId)
        {
            try
            {
                var producto = productosBO.ObtenerPorIdProducto(productoId);
                if (producto != null)
                {
                    hfProductoId.Value = producto.producto_id.ToString();
                    txtDescripcion.Text = producto.descripcion;
                    ddlCategoria.SelectedValue = producto.categoria.categoria_id.ToString();
                    ddlEstilo.SelectedValue = producto.estilo.estilo_id.ToString();
                    txtPrecio.Text = producto.precio_venta.ToString("F2");

                    ScriptManager.RegisterStartupScript(this, GetType(), "AbrirEditar",
                        "abrirModalEditar();", true);
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar producto: " + ex.Message);
            }
        }

        // =====================================================
        // 🔹 Auxiliares
        // =====================================================
        private void LimpiarFormulario()
        {
            hfProductoId.Value = "0";
            txtDescripcion.Text = "";
            txtPrecio.Text = "";
            ddlCategoria.SelectedIndex = 0;
            ddlEstilo.SelectedIndex = 0;
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

    }
}
