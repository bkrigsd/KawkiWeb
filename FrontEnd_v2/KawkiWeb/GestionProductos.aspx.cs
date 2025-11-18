using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
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
                var productos = productosBO.ListarTodos();

                // Crear lista personalizada para el GridView
                var productosGrid = productos.Select(p => new
                {
                    ProductoId = p.producto_id,
                    Descripcion = p.descripcion ?? "",
                    Categoria = p.categoria?.nombre ?? "Sin categoría",
                    Estilo = p.estilo?.nombre ?? "Sin estilo",
                    Precio = p.precio_venta,
                    StockTotal = productosBO.CalcularStockTotal(p.producto_id),
                    TieneStock = productosBO.TieneStockDisponible(p.producto_id),
                    CantidadVariantes = p.variantes?.Length ?? 0,

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

        // =====================================================
        // 🔹 Guardar producto (nuevo o editar)
        // =====================================================
        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                bool esEdicion = hfProductoId.Value != "0";

                string descripcion = txtDescripcion.Text.Trim();
                int categoriaId = Convert.ToInt32(ddlCategoria.SelectedValue);
                int estiloId = Convert.ToInt32(ddlEstilo.SelectedValue);
                double precio = Convert.ToDouble(txtPrecio.Text.Trim());

                // === VALIDACIONES ===
                if (string.IsNullOrEmpty(descripcion))
                {
                    lblMensaje.Text = "La descripción del producto es obligatoria.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (categoriaId == 0)
                {
                    lblMensaje.Text = "Debe seleccionar una categoría.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (estiloId == 0)
                {
                    lblMensaje.Text = "Debe seleccionar un estilo.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (precio <= 0)
                {
                    lblMensaje.Text = "El precio debe ser mayor a 0.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                var usuario = ObtenerUsuarioSesion();

                // === AQUÍ VAN LOS DTO CORRECTOS ===
                var categoria = new categoriasDTO { categoria_id = categoriaId };
                var estilo = new estilosDTO { estilo_id = estiloId };

                if (esEdicion)
                {
                    // === EDITAR ===
                    int productoId = Convert.ToInt32(hfProductoId.Value);

                    productosBO.Modificar(
                        productoId,
                        descripcion,
                        categoria,
                        estilo,
                        precio,
                        usuario
                    );

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Producto actualizado correctamente.";
                }
                else
                {
                    // === NUEVO ===
                    productosBO.Insertar(
                        descripcion,
                        categoria,
                        estilo,
                        precio,
                        usuario
                    );

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Producto registrado correctamente.";
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
                lblMensaje.Text = "Error: " + ex.Message;
                MantenerModalAbierto(hfProductoId.Value != "0");
            }
        }


        private void MantenerModalAbierto(bool esEdicion)
        {
            string script = esEdicion ? "abrirModalEditar();" : "abrirModalRegistro();";
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
                        // Redirigir a página de gestión de variantes
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
                var producto = productosBO.ObtenerPorId(productoId);
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

        // =====================================================
        // 🔹 Filtros (opcionales)
        // =====================================================
        //protected void btnFiltrar_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        int categoriaId = Convert.ToInt32(ddlFiltroCategoria.SelectedValue);
        //        int estiloId = Convert.ToInt32(ddlFiltroEstilo.SelectedValue);

        //        IList<productosDTO> productos;

        //        if (categoriaId > 0)
        //        {
        //            productos = productosBO.ListarPorCategoria(categoriaId);
        //        }
        //        else if (estiloId > 0)
        //        {
        //            productos = productosBO.ListarPorEstilo(estiloId);
        //        }
        //        else
        //        {
        //            productos = productosBO.ListarTodos();
        //        }

        //        var productosGrid = productos.Select(p => new
        //        {
        //            ProductoId = p.productoId,
        //            Descripcion = p.descripcion ?? "",
        //            Categoria = p.categoria?.nombre ?? "Sin categoría",
        //            Estilo = p.estilo?.nombre ?? "Sin estilo",
        //            Precio = p.precioVenta,
        //            StockTotal = productosBO.CalcularStockTotal(p.productoId),
        //            TieneStock = productosBO.TieneStockDisponible(p.productoId),
        //            CantidadVariantes = p.variantes?.Length ?? 0
        //        }).ToList();

        //        gvProductos.DataSource = productosGrid;
        //        gvProductos.DataBind();
        //    }
        //    catch (Exception ex)
        //    {
        //        MostrarError("Error al filtrar: " + ex.Message);
        //    }
        //}

        //protected void btnLimpiarFiltro_Click(object sender, EventArgs e)
        //{
        //    ddlFiltroCategoria.SelectedIndex = 0;
        //    ddlFiltroEstilo.SelectedIndex = 0;
        //    CargarProductos();
        //}
    }
}
