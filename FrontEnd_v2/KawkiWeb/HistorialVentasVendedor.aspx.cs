//using System;
//using System.Collections.Generic;
//using System.Data;
//using System.Linq;
//using System.Security.Cryptography;
//using System.Web;
//using System.Web.UI;
//using System.Web.UI.WebControls;
//using KawkiWebBusiness;
//using KawkiWebBusiness.BO;
//using KawkiWebBusiness.KawkiWebWSVentas;

//namespace KawkiWeb
//{
//    public partial class HistorialVentasVendedor : Page
//    {
//        private VentasBO ventasBO = new VentasBO();
//        private DetalleVentasBO detalleVentasBO = new DetalleVentasBO();
//        private string VendedorActual
//        {
//            get { return Session["Usuario"] != null ? Session["Usuario"].ToString() : ""; }
//        }

//        protected void Page_Load(object sender, EventArgs e)
//        {
//            // AntiCaché
//            Response.Cache.SetCacheability(HttpCacheability.NoCache);
//            Response.Cache.SetNoStore();
//            Response.Cache.SetExpires(DateTime.Now.AddSeconds(-1));
//            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
//            Response.AddHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            Response.AddHeader("Pragma", "no-cache");
//            Response.AddHeader("Expires", "0");

//            if (!IsPostBack)
//            {
//                string rol = Session["Rol"] as string;

//                // VALIDACIÓN DE SESIÓN
//                if (string.IsNullOrEmpty(rol))
//                {
//                    Response.Redirect("Error404.aspx", false);
//                    return;
//                }

//                // Si no es vendedor → redirigir
//                if (rol.ToLower() == "admin")
//                {
//                    Response.Redirect("Productos.aspx", false);
//                    return;
//                }

//                // En este punto SÍ es vendedor → cargar datos
//                try
//                {
//                    CargarVentas();
//                }
//                catch (Exception ex)
//                {
//                    lblMensaje.CssClass = "text-danger d-block mb-2";
//                    lblMensaje.Text = "Error al cargar la página: " + ex.Message;
//                }
//            }
//        }

//        /// <summary>
//        /// Carga las ventas del vendedor según los filtros aplicados
//        /// </summary>
//        private void CargarVentas()
//        {
//            try
//            {
//                // Obtener TODAS las ventas
//                var todasLasVentas = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

//                // Filtrar solo las ventas del vendedor actual
//                var ventasVendedor = todasLasVentas
//                    .Where(v => v.usuario != null &&
//                                v.usuario.nombreUsuario != null &&
//                                v.usuario.nombreUsuario.Equals(VendedorActual, StringComparison.OrdinalIgnoreCase))
//                    .ToList();

//                if (!ventasVendedor.Any())
//                {
//                    gvVentas.DataSource = null;
//                    gvVentas.DataBind();
//                    lblContador.Text = "0 ventas encontradas";
//                    lblMensaje.Text = "No tienes ventas registradas.";
//                    ResetearEstadisticas();
//                    return;
//                }

//                var lista = ventasVendedor;
//                // Determinar si hay filtros activos
//                bool hayFiltros = !string.IsNullOrEmpty(txtFechaInicio.Text) ||
//                                  !string.IsNullOrEmpty(txtFechaFin.Text);

//                // Guardar lista completa del vendedor ANTES de filtrar
//                if (!hayFiltros)
//                {
//                    Session["VentasVendedor"] = new List<ventasDTO>(lista);
//                }

//                // Aplicar filtros de fecha
//                if (DateTime.TryParse(txtFechaInicio.Text, out DateTime fechaInicio))
//                {
//                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) >= fechaInicio).ToList();
//                }

//                if (DateTime.TryParse(txtFechaFin.Text, out DateTime fechaFin))
//                {
//                    fechaFin = fechaFin.AddDays(1).AddSeconds(-1);
//                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) <= fechaFin).ToList();
//                }

//                // Si HAY filtros, actualizar sesión con datos filtrados
//                if (hayFiltros)
//                {
//                    Session["VentasVendedor"] = lista;
//                }

//                // Transformar para GridView
//                var gvLista = lista.Select(v => new
//                {
//                    venta_id = v.venta_id,
//                    Fecha = DateTime.Parse(v.fecha_hora_creacion),
//                    descuento = v.descuento?.descripcion ?? "Sin descuento",
//                    redSocial = v.redSocial?.nombre ?? "N/D",
//                    CantidadProductos = v.detalles?.Length ?? 0,
//                    MontoTotal = v.total
//                }).OrderByDescending(x => x.Fecha).ToList();

//                gvVentas.DataSource = gvLista;
//                gvVentas.DataBind();

//                lblContador.Text = $"{gvLista.Count} ventas encontradas";
//                lblMensaje.Text = gvLista.Count == 0
//                    ? "No tienes ventas en este rango de fechas."
//                    : "";

//                ActualizarEstadisticas();
//            }
//            catch (Exception ex)
//            {
//                lblMensaje.CssClass = "text-danger mb-2 d-block";
//                lblMensaje.Text = $"Error al cargar ventas: {ex.Message}";
//            }
//        }

//        private void ActualizarEstadisticas()
//        {
//            try
//            {
//                var ventas = Session["VentasVendedor"] as List<ventasDTO>;

//                if (ventas == null || !ventas.Any())
//                {
//                    ResetearEstadisticas();
//                    return;
//                }

//                decimal montoTotal = ventas.Sum(v => Convert.ToDecimal(v.total));
//                int cantidad = ventas.Count;
//                decimal promedio = montoTotal / cantidad;

//                lblTotalVentas.Text = cantidad.ToString();
//                lblMontoTotal.Text = $"S/ {montoTotal:N2}";
//                lblPromedio.Text = $"S/ {promedio:N2}";
//            }
//            catch (Exception ex)
//            {
//                MostrarError("Error en estadísticas: " + ex.Message);
//                ResetearEstadisticas();
//            }
//        }

//        private void ResetearEstadisticas()
//        {
//            lblTotalVentas.Text = "0";
//            lblMontoTotal.Text = "S/ 0.00";
//            lblPromedio.Text = "S/ 0.00";
//        }

//        /// <summary>
//        /// Obtiene los datos completos de una venta (simulado)
//        /// </summary>
//        private DataRow ObtenerDatosVenta(int idVenta)
//        {
//            // TODO: Reemplazar con consulta real que incluya todos los datos
//            DataTable dt = new DataTable();
//            dt.Columns.Add("IdVenta", typeof(int));
//            dt.Columns.Add("Fecha", typeof(DateTime));
//            dt.Columns.Add("Cliente", typeof(string));
//            dt.Columns.Add("Telefono", typeof(string));
//            dt.Columns.Add("Direccion", typeof(string));
//            dt.Columns.Add("Canal", typeof(string));
//            dt.Columns.Add("MontoTotal", typeof(decimal));

//            return dt.AsEnumerable().FirstOrDefault(row => row.Field<int>("IdVenta") == idVenta);
//        }

//        protected void btnBuscar_Click(object sender, EventArgs e)
//        {
//            lblMensaje.Text = "";
//            CargarVentas();
//        }

//        protected void btnLimpiar_Click(object sender, EventArgs e)
//        {
//            txtFechaInicio.Text = "";
//            txtFechaFin.Text = "";
//            lblMensaje.Text = "";
//            CargarVentas();
//        }

//        protected void gvVentas_RowCommand(object sender, GridViewCommandEventArgs e)
//        {
//            if (e.CommandName == "VerDetalle")
//            {
//                int idVenta = Convert.ToInt32(e.CommandArgument);
//                MostrarDetalle(idVenta);
//            }
//        }

//        /// <summary>
//        /// Muestra el detalle completo de una venta específica
//        /// </summary>
//        private void MostrarDetalle(int idVenta)
//        {
//            try
//            {
//                var venta = ventasBO.ObtenerPorIdVenta(idVenta);

//                if (venta == null)
//                {
//                    MostrarError("No se encontró la venta.");
//                    return;
//                }

//                // Verificar que la venta pertenezca al vendedor actual
//                if (venta.usuario?.nombreUsuario != VendedorActual)
//                {
//                    MostrarError("No tienes permiso para ver esta venta.");
//                    return;
//                }

//                lblIdVentaDetalle.Text = venta.venta_id.ToString();
//                lblClienteDetalle.Text = venta.usuario.nombre;
//                lblTelefonoDetalle.Text = venta.usuario.telefono;
//                lblDireccionDetalle.Text = ;
//                lblFechaDetalle.Text = DateTime.Parse(venta.fecha_hora_creacion).ToString("dd/MM/yyyy HH:mm");
//                lblCanalDetalle.Text = venta.redSocial?.nombre ?? "N/D";
//                lblTotalDetalle.Text = $"S/ {venta.total:N2}";

//                // Cargar detalle de productos
//                if (venta.detalles != null && venta.detalles.Length > 0)
//                {
//                    var detalles = venta.detalles.Select(d => new
//                    {
//                        Producto = d.prodVariante?.SKU ?? "N/D",
//                        Cantidad = d.cantidad,
//                        PrecioUnitario = d.precio_unitario,
//                        Subtotal = d.subtotal
//                    }).ToList();

//                    gvDetalleProductos.DataSource = detalles;
//                    gvDetalleProductos.DataBind();
//                }
//                else
//                {
//                    gvDetalleProductos.DataSource = null;
//                    gvDetalleProductos.DataBind();
//                }

//                pnlDetalle.Visible = true;

//                // Scroll al detalle
//                ScriptManager.RegisterStartupScript(this, GetType(), "ScrollToDetail",
//                    "$('html, body').animate({ scrollTop: $('#" + pnlDetalle.ClientID + "').offset().top - 100 }, 500);",
//                    true);
//            }
//            catch (Exception ex)
//            {
//                MostrarError("Error al cargar detalle: " + ex.Message);
//            }
//        }

//        protected void btnCerrarDetalle_Click(object sender, EventArgs e)
//        {
//            pnlDetalle.Visible = false;
//        }

//        private void MostrarError(string mensaje)
//        {
//            lblMensaje.CssClass = "text-danger mb-2 d-block";
//            lblMensaje.Text = mensaje;
//        }
//    }
//}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSVentas;

namespace KawkiWeb
{
    public partial class HistorialVentasVendedor : Page
    {
        private VentasBO ventasBO = new VentasBO();
        private ComprobantesPagoBO comprobantesBO = new ComprobantesPagoBO();
        private ProductosBO productosBO = new ProductosBO();

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
                // Obtener TODAS las ventas
                var todasLasVentas = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

                // Filtrar solo las ventas del vendedor actual
                var ventasVendedor = todasLasVentas
                    .Where(v => v.usuario != null &&
                                v.usuario.nombreUsuario != null &&
                                v.usuario.nombreUsuario.Equals(UsuarioActual, StringComparison.OrdinalIgnoreCase))
                    .ToList();

                if (!ventasVendedor.Any())
                {
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    lblContador.Text = "0 ventas encontradas";
                    lblMensaje.Text = "No tienes ventas registradas.";
                    ResetearEstadisticas();
                    return;
                }

                var lista = ventasVendedor;

                // Determinar si hay filtros activos
                bool hayFiltros = !string.IsNullOrEmpty(txtFechaInicio.Text) ||
                                  !string.IsNullOrEmpty(txtFechaFin.Text);

                // Guardar lista completa del vendedor ANTES de filtrar
                if (!hayFiltros)
                {
                    Session["VentasVendedor"] = new List<ventasDTO>(lista);
                }

                // Aplicar filtros de fecha
                if (DateTime.TryParse(txtFechaInicio.Text, out DateTime fechaInicio))
                {
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) >= fechaInicio).ToList();
                }

                if (DateTime.TryParse(txtFechaFin.Text, out DateTime fechaFin))
                {
                    fechaFin = fechaFin.AddDays(1).AddSeconds(-1);
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) <= fechaFin).ToList();
                }

                // Si HAY filtros, actualizar sesión con datos filtrados
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
                lblMensaje.Text = gvLista.Count == 0
                    ? "No tienes ventas en este rango de fechas."
                    : "";

                ActualizarEstadisticas();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar ventas: " + ex.Message);
                gvVentas.DataSource = null;
                gvVentas.DataBind();
                ResetearEstadisticas();
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

                // Verificar que la venta pertenezca al vendedor actual
                if (venta.usuario?.nombreUsuario != UsuarioActual)
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
                        : comprobante.razon_social_cliente ?? "N/D";

                    lblTelefonoDetalle.Text = comprobante.telefono_cliente ?? "N/D";
                    lblDireccionDetalle.Text = comprobante.direccion_fiscal_cliente ?? "N/D";
                }
                else
                {
                    lblClienteDetalle.Text = "N/D";
                    lblTelefonoDetalle.Text = "N/D";
                    lblDireccionDetalle.Text = "N/D";
                }

                // Cargar detalle de productos con nombre completo
                if (venta.detalles != null && venta.detalles.Length > 0)
                {
                    var detalles = venta.detalles.Select(d => new
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
                MostrarError("Error al cargar detalle: " + ex.Message);
            }
        }

        /// <summary>
        /// Construye el nombre completo del producto: "Categoría Estilo Color - Talla X"
        /// Ejemplo: "Oxford Clásico Beige - Talla 42"
        /// </summary>
        private string ObtenerNombreProductoCompleto(productosVariantesDTO variante)
        {
            if (variante == null) return "N/D";

            try
            {
                // Obtener el producto padre
                var producto = productosBO.ObtenerPorIdProducto(variante.producto_id);

                if (producto == null) return "Producto N/D";

                // Construir nombre: Categoría + Estilo + Color
                string categoria = producto.categoria?.nombre ?? "";
                string estilo = producto.estilo?.nombre ?? "";
                string color = variante.color?.nombre ?? "";
                string talla = variante.talla?.numero.ToString() ?? "";

                // Formato: "Oxford Clásico Beige - Talla 42"
                string nombreBase = $"{categoria} {estilo} {color}".Trim();
                string nombreCompleto = !string.IsNullOrEmpty(talla)
                    ? $"{nombreBase} - Talla {talla}"
                    : nombreBase;

                return nombreCompleto;
            }
            catch
            {
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

