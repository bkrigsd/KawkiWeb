using KawkiWebBusiness.KawkiWebWSVentas;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class HistorialVentasVendedor : Page
    {
        private string VendedorActual
        {
            get { return Session["Usuario"] != null ? Session["Usuario"].ToString() : ""; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Verificar que el usuario sea vendedor
                if (Session["Rol"] == null || Session["Rol"].ToString() != "vendedor")
                {
                    Response.Redirect("Login.aspx");
                    return;
                }

                // Mostrar nombre del vendedor
                lblNombreVendedor.Text = VendedorActual;

                CargarVentas();
            }
        }
        private void ActualizarEstadisticas()
        {
            int totalVentas = gvVentas.Rows.Count;
            int totalProductos = 0;
            decimal totalMonto = 0;

            foreach (GridViewRow row in gvVentas.Rows)
            {
                // Cantidad de productos (columna 4)
                int cantidad = int.Parse(row.Cells[4].Text);
                totalProductos += cantidad;

                // Obtener el monto desde DataKeys o desde un HiddenField
                HiddenField hfMonto = (HiddenField)row.FindControl("hfMontoTotal");
                if (hfMonto != null)
                {
                    decimal monto = decimal.Parse(hfMonto.Value);
                    totalMonto += monto;
                }
            }

            lblMontoTotal.Text = $"S/ {totalMonto:0.00}";
            lblTotalVentas.Text = totalVentas.ToString();
            lblPromedio.Text = totalVentas > 0
                ? $"S/ {(totalMonto / totalVentas):0.00}"
                : "S/ 0.00";
        }


        /// <summary>
        /// Carga las ventas del vendedor según los filtros aplicados
        /// </summary>
        private void CargarVentas()
        {
            try
            {
                var cliente = new KawkiWebBusiness.KawkiWebWSVentas.VentasClient();
                var respuesta = cliente.listarTodosVenta();

                if (respuesta == null || respuesta.Length == 0)
                {
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    lblContador.Text = "0 ventas encontradas";
                    lblMensaje.Text = "No tienes ventas registradas.";
                    return;
                }

                var lista = respuesta.Select(v =>
                {
                    // Convertimos fecha string → DateTime
                    DateTime fecha = DateTime.MinValue;
                    DateTime.TryParse(v.fecha_hora_creacion, out fecha);

                    return new
                    {
                        v.venta_id,
                        Fecha = fecha,  // lo guardamos como DateTime REAL
                        v.descuento,
                        v.redSocial,
                        CantidadProductos = v.detalles?.Length ?? 0,
                        MontoTotal = v.total
                    };
                }).ToList();

                // =========================
                // FILTRO POR FECHAS
                // =========================
                if (DateTime.TryParse(txtFechaInicio.Text, out DateTime fechaInicio))
                {
                    lista = lista.Where(v => v.Fecha >= fechaInicio).ToList();
                }

                if (DateTime.TryParse(txtFechaFin.Text, out DateTime fechaFin))
                {
                    fechaFin = fechaFin.AddDays(1).AddSeconds(-1);
                    lista = lista.Where(v => v.Fecha <= fechaFin).ToList();
                }

                gvVentas.DataSource = lista;
                gvVentas.DataBind();

                lblContador.Text = $"{lista.Count} ventas encontradas";
                lblMensaje.Text = lista.Count == 0
                    ? "No tienes ventas registradas en este rango de fechas."
                    : "";
                ActualizarEstadisticas();
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"Error al cargar ventas: {ex.Message}";
            }
        }

        /// <summary>
        /// Obtiene el detalle de productos de una venta (simulado)
        /// </summary>
        private DataTable ObtenerDetalleVenta(int idVenta)
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Producto", typeof(string));
            dt.Columns.Add("Cantidad", typeof(int));
            dt.Columns.Add("PrecioUnitario", typeof(decimal));
            dt.Columns.Add("Subtotal", typeof(decimal));

            // Datos de ejemplo según el ID
            if (idVenta == 1)
            {
                dt.Rows.Add("Producto A", 2, 50.25m, 100.50m);
                dt.Rows.Add("Producto B", 1, 50.00m, 50.00m);
            }
            else if (idVenta == 2)
            {
                dt.Rows.Add("Producto C", 3, 45.00m, 135.00m);
                dt.Rows.Add("Producto D", 2, 70.00m, 140.00m);
            }
            else
            {
                dt.Rows.Add("Producto X", 1, 100.00m, 100.00m);
                dt.Rows.Add("Producto Y", 1, 50.00m, 50.00m);
            }

            return dt;
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

            // Datos de ejemplo
            dt.Rows.Add(1, DateTime.Now.AddDays(-7), "Ana Torres", "987654321", "Av. Principal 123, Lima", "Instagram", 150.50m);
            dt.Rows.Add(2, DateTime.Now.AddDays(-5), "Carmen Soto", "912345678", "Jr. Los Olivos 456, Surco", "Facebook", 275.00m);
            dt.Rows.Add(3, DateTime.Now.AddDays(-3), "Miguel Ruiz", "998877665", "Calle Las Flores 789, Miraflores", "WhatsApp", 120.00m);

            return dt.AsEnumerable().FirstOrDefault(row => row.Field<int>("IdVenta") == idVenta);
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarVentas();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFechaInicio.Text = string.Empty;
            txtFechaFin.Text = string.Empty;
            lblMensaje.Text = string.Empty;
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
                // Obtener datos de la venta
                DataRow venta = ObtenerDatosVenta(idVenta);

                if (venta != null)
                {
                    lblIdVentaDetalle.Text = idVenta.ToString();
                    lblClienteDetalle.Text = venta["Cliente"].ToString();
                    lblTelefonoDetalle.Text = venta["Telefono"].ToString();
                    lblDireccionDetalle.Text = venta["Direccion"].ToString();
                    lblFechaDetalle.Text = Convert.ToDateTime(venta["Fecha"]).ToString("dd/MM/yyyy HH:mm");
                    lblCanalDetalle.Text = venta["Canal"].ToString();
                    lblTotalDetalle.Text = $"S/ {venta["MontoTotal"]:0.00}";

                    // Cargar detalle de productos
                    DataTable detalle = ObtenerDetalleVenta(idVenta);
                    gvDetalleProductos.DataSource = detalle;
                    gvDetalleProductos.DataBind();

                    pnlDetalle.Visible = true;

                    // Scroll automático al detalle
                    ScriptManager.RegisterStartupScript(this, GetType(), "ScrollToDetail",
                        "$('html, body').animate({ scrollTop: $('#" + pnlDetalle.ClientID + "').offset().top - 100 }, 500);", true);
                }
                else
                {
                    lblMensaje.CssClass = "text-warning mb-2 d-block";
                    lblMensaje.Text = "No se encontró la venta solicitada.";
                }
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"Error al cargar detalle: {ex.Message}";
            }
        }

        protected void btnCerrarDetalle_Click(object sender, EventArgs e)
        {
            pnlDetalle.Visible = false;
        }
    }
}
