using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Security.Cryptography;
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
        private string VendedorActual
        {
            get { return Session["Usuario"] != null ? Session["Usuario"].ToString() : ""; }
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

                // Si no es vendedor → redirigir
                if (rol.ToLower() == "admin")
                {
                    Response.Redirect("Productos.aspx", false);
                    return;
                }

                // En este punto SÍ es vendedor → cargar datos
                try
                {
                    CargarVentas();
                }
                catch (Exception ex)
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Error al cargar la página: " + ex.Message;
                }
            }
        }

        /// <summary>
        /// Carga las ventas del vendedor según los filtros aplicados
        /// </summary>
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
                                v.usuario.nombreUsuario.Equals(VendedorActual, StringComparison.OrdinalIgnoreCase))
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
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"Error al cargar ventas: {ex.Message}";
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

        /// <summary>
        /// Obtiene los datos completos de una venta (simulado)
        /// </summary>
        private DataRow ObtenerDatosVenta(int idVenta)
        {
            // TODO: Reemplazar con consulta real que incluya todos los datos
            DataTable dt = new DataTable();
            dt.Columns.Add("IdVenta", typeof(int));
            dt.Columns.Add("Fecha", typeof(DateTime));
            dt.Columns.Add("Cliente", typeof(string));
            dt.Columns.Add("Telefono", typeof(string));
            dt.Columns.Add("Direccion", typeof(string));
            dt.Columns.Add("Canal", typeof(string));
            dt.Columns.Add("MontoTotal", typeof(decimal));

            return dt.AsEnumerable().FirstOrDefault(row => row.Field<int>("IdVenta") == idVenta);
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
        /// Muestra el detalle completo de una venta específica
        /// </summary>
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

                lblIdVentaDetalle.Text = venta.venta_id.ToString();
                lblClienteDetalle.Text = "Cliente de la venta"; // Si no tienes cliente en el modelo
                lblTelefonoDetalle.Text = "N/D";
                lblDireccionDetalle.Text = "N/D";
                lblFechaDetalle.Text = DateTime.Parse(venta.fecha_hora_creacion).ToString("dd/MM/yyyy HH:mm");
                lblCanalDetalle.Text = venta.redSocial?.nombre ?? "N/D";
                lblTotalDetalle.Text = $"S/ {venta.total:N2}";

                // Cargar detalle de productos
                if (venta.detalles != null && venta.detalles.Length > 0)
                {
                    var detalles = venta.detalles.Select(d => new
                    {
                        Producto = d.prodVariante?.nombre ?? "N/D",
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

