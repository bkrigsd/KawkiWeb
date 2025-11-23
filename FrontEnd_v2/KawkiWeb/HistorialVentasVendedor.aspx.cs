using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.BO;
using KawkiWebBusiness.KawkiWebWSVentas;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSDetalleVentas.productosVariantesDTO;

namespace KawkiWeb
{
    public partial class HistorialVentasVendedor : Page
    {
        private VentasBO ventasBO = new VentasBO();
        private ComprobantesPagoBO comprobantesBO = new ComprobantesPagoBO();
        private ProductosBO productosBO = new ProductosBO();
        private DetalleVentasBO detalleVentasBO = new DetalleVentasBO();
        private ProductosVariantesBO productosVariantesBO = new ProductosVariantesBO();

        private string UsuarioActual
        {
            get { return Session["Usuario"]?.ToString() ?? ""; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // AntiCaché
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.Now.AddSeconds(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.AddHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            Response.AddHeader("Pragma", "no-cache");
            Response.AddHeader("Expires", "0");

            if (!IsPostBack)
            {
                string rol = Session["Rol"] as string;

                // VALIDACIÓN DE SESIÓN
                if (string.IsNullOrEmpty(rol))
                {
                    Response.Redirect("Error404.aspx", false);
                    return;
                }

                // Si es admin → redirigir
                if (rol.ToLower() == "admin")
                {
                    Response.Redirect("HistorialVentasAdmin.aspx", false);
                    return;
                }

                try
                {
                    lblNombreVendedor.Text = UsuarioActual;
                    CargarVentas();
                }
                catch (Exception ex)
                {
                    MostrarError("Error al cargar la página: " + ex.Message);
                }
            }
        }

        private void CargarVentas()
        {
            try
            {
                // Obtener el ID del usuario directamente de la sesión (ya está guardado en Login)
                int idUsuarioActual = 0;

                if (Session["UsuarioId"] != null)
                {
                    idUsuarioActual = Convert.ToInt32(Session["UsuarioId"]);
                }
                else
                {
                    lblMensaje.CssClass = "text-danger mb-2 d-block";
                    lblMensaje.Text = "Error: No se encontró ID de usuario en sesión. Por favor, inicia sesión nuevamente.";
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    ResetearEstadisticas();
                    return;
                }

                string usuarioLogin = UsuarioActual;
                string usuarioNombreCompleto = Session["UsuarioNombreCompleto"]?.ToString() ?? "Usuario desconocido";

                lblMensaje.CssClass = "text-info mb-2 d-block";

                // Obtener todas las ventas
                var todasLasVentas = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

                // Filtrar por usuarioId
                var ventasVendedor = todasLasVentas
                    .Where(v => v.usuario != null && v.usuario.usuarioId == idUsuarioActual)
                    .ToList();

                if (!ventasVendedor.Any())
                {
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    lblContador.Text = "0 ventas encontradas";
                    ResetearEstadisticas();
                    return;
                }

                var lista = ventasVendedor;

                // Aplicar filtros de fecha
                bool hayFiltros = !string.IsNullOrEmpty(txtFechaInicio.Text) ||
                                  !string.IsNullOrEmpty(txtFechaFin.Text);

                if (!hayFiltros)
                {
                    Session["VentasVendedor"] = new List<ventasDTO>(lista);
                }

                if (DateTime.TryParse(txtFechaInicio.Text, out DateTime fechaInicio))
                {
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) >= fechaInicio).ToList();
                }

                if (DateTime.TryParse(txtFechaFin.Text, out DateTime fechaFin))
                {
                    fechaFin = fechaFin.AddDays(1).AddSeconds(-1);
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) <= fechaFin).ToList();
                }

                if (hayFiltros)
                {
                    Session["VentasVendedor"] = lista;
                }

                // Transformar para GridView
                var gvLista = lista.Select(v => new
                {
                    venta_id = v.venta_id,
                    Fecha = DateTime.Parse(v.fecha_hora_creacion),
                    descuento = v.descuento?.descripcion ?? "Sin descuento",
                    redSocial = v.redSocial?.nombre ?? "N/D",
                    CantidadProductos = v.detalles?.Length ?? 0,
                    MontoTotal = v.total
                }).OrderByDescending(x => x.Fecha).ToList();

                gvVentas.DataSource = gvLista;
                gvVentas.DataBind();

                lblContador.Text = $"{gvLista.Count} ventas encontradas";

                ActualizarEstadisticas();
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"<strong>ERROR:</strong> {ex.Message}";
                gvVentas.DataSource = null;
                gvVentas.DataBind();
                ResetearEstadisticas();
            }
        }



        // También actualiza MostrarDetalle:
        private void MostrarDetalle(int idVenta)
        {
            try
            {
                var venta = ventasBO.ObtenerPorIdVenta(idVenta);

                if (venta == null)
                {
                    MostrarError("No se encontró la venta.");
                    return;
                }

                // Obtener el ID del usuario actual de la sesión
                int idUsuarioActual = 0;
                if (Session["UsuarioId"] != null)
                {
                    idUsuarioActual = Convert.ToInt32(Session["UsuarioId"]);
                }

                // Verificar que la venta pertenece al usuario actual
                if (venta.usuario?.usuarioId != idUsuarioActual)
                {
                    MostrarError("No tienes permiso para ver esta venta.");
                    return;
                }

                // Buscar el comprobante asociado a esta venta
                var todosComprobantes = comprobantesBO.ListarTodosComprobantes();
                var comprobante = todosComprobantes?.FirstOrDefault(c => c.venta?.venta_id == idVenta);

                // Datos de la venta
                lblIdVentaDetalle.Text = venta.venta_id.ToString();
                lblFechaDetalle.Text = DateTime.Parse(venta.fecha_hora_creacion).ToString("dd/MM/yyyy HH:mm");
                lblCanalDetalle.Text = venta.redSocial?.nombre ?? "N/D";
                lblTotalDetalle.Text = $"S/ {venta.total:N2}";

                // Datos del cliente desde el comprobante
                if (comprobante != null)
                {
                    lblClienteDetalle.Text = !string.IsNullOrEmpty(comprobante.nombre_cliente)
                        ? comprobante.nombre_cliente
                        : (comprobante.razon_social_cliente ?? "N/D");

                    lblTelefonoDetalle.Text = !string.IsNullOrEmpty(comprobante.telefono_cliente)
                        ? comprobante.telefono_cliente
                        : "N/D";

                    lblDireccionDetalle.Text = !string.IsNullOrEmpty(comprobante.direccion_fiscal_cliente)
                        ? comprobante.direccion_fiscal_cliente
                        : "N/D";
                }
                else
                {
                    lblClienteDetalle.Text = "N/D - Sin comprobante";
                    lblTelefonoDetalle.Text = "N/D";
                    lblDireccionDetalle.Text = "N/D";
                }

                // Obtener los detalles POR SEPARADO
                var detallesVenta = detalleVentasBO.ListarPorVentaId(idVenta);

                if (detallesVenta != null && detallesVenta.Count > 0)
                {
                    var detalles = detallesVenta.Select(d => new
                    {
                        Producto = ObtenerNombreProductoCompleto(d.prodVariante),
                        Cantidad = d.cantidad,
                        PrecioUnitario = d.precio_unitario,
                        Subtotal = d.subtotal
                    }).ToList();

                    gvDetalleProductos.DataSource = detalles;
                    gvDetalleProductos.DataBind();
                }
                else
                {
                    gvDetalleProductos.DataSource = null;
                    gvDetalleProductos.DataBind();
                }

                pnlDetalle.Visible = true;

                // Scroll al detalle
                ScriptManager.RegisterStartupScript(this, GetType(), "ScrollToDetail",
                    "$('html, body').animate({ scrollTop: $('#" + pnlDetalle.ClientID + "').offset().top - 100 }, 500);",
                    true);
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar detalle: " + ex.Message + "<br/>" + ex.StackTrace);
            }
        }

        private void ActualizarEstadisticas()
        {
            try
            {
                var ventas = Session["VentasVendedor"] as List<ventasDTO>;

                if (ventas == null || !ventas.Any())
                {
                    ResetearEstadisticas();
                    return;
                }

                decimal montoTotal = ventas.Sum(v => Convert.ToDecimal(v.total));
                int cantidad = ventas.Count;
                decimal promedio = montoTotal / cantidad;

                lblTotalVentas.Text = cantidad.ToString();
                lblMontoTotal.Text = $"S/ {montoTotal:N2}";
                lblPromedio.Text = $"S/ {promedio:N2}";

                // Animación (opcional)
                ScriptManager.RegisterStartupScript(
                    this, GetType(),
                    "AnimarDashboard",
                    $"animarDashboard({cantidad}, {montoTotal}, {promedio});",
                    true
                );
            }
            catch (Exception ex)
            {
                MostrarError("Error en estadísticas: " + ex.Message);
                ResetearEstadisticas();
            }
        }

        private void ResetearEstadisticas()
        {
            lblTotalVentas.Text = "0";
            lblMontoTotal.Text = "S/ 0.00";
            lblPromedio.Text = "S/ 0.00";
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            CargarVentas();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFechaInicio.Text = "";
            txtFechaFin.Text = "";
            lblMensaje.Text = "";
            CargarVentas();
        }

        protected void gvVentas_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalle")
            {
                int idVenta = Convert.ToInt32(e.CommandArgument);
                MostrarDetalle(idVenta);
            }
        }
   
        /// <summary>
        /// Construye el nombre completo del producto: "Categoría Estilo Color"
        /// Ejemplo: "Oxford Clásico Beige"
        /// </summary>
        private string ObtenerNombreProductoCompleto(productosVariantesDTO variante)
        {
            if (variante == null) return "N/D";

            try
            {
                int productoId = variante.producto_id;

                if (productoId == 0) return "Producto N/D";

                // Obtener el producto padre
                var producto = productosBO.ObtenerPorIdProducto(productoId);

                if (producto == null) return "Producto N/D";

                // Construir nombre: Categoría + Estilo + Color
                string categoria = producto.categoria?.nombre ?? "";
                string estilo = producto.estilo?.nombre ?? "";
                string color = variante.color?.nombre ?? "";

                // Formato: "Oxford Clásico Beige"
                string nombreCompleto = $"{categoria} {estilo} {color}".Trim();

                return nombreCompleto;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en ObtenerNombreProductoCompleto: {ex.Message}");
                return "N/D";
            }
        }

        protected void btnCerrarDetalle_Click(object sender, EventArgs e)
        {
            pnlDetalle.Visible = false;
        }

        private void MostrarError(string mensaje)
        {
            lblMensaje.CssClass = "text-danger mb-2 d-block";
            lblMensaje.Text = mensaje;
        }
    }
}
