using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class HistorialVentasAdmin : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Verificar que el usuario sea administrador
                if (Session["Rol"] == null || Session["Rol"].ToString() != "admin")
                {
                    Response.Redirect("Login.aspx");
                    return;
                }

                CargarVendedores();
                CargarVentas();
                ActualizarEstadisticas();
            }
        }

        /// <summary>
        /// Carga la lista de vendedores en el dropdown
        /// </summary>
        private void CargarVendedores()
        {
            // TODO: Reemplazar con consulta real a la base de datos
            // Ejemplo de datos simulados
            ddlVendedor.Items.Clear();
            ddlVendedor.Items.Add(new ListItem("Todos los vendedores", ""));
            ddlVendedor.Items.Add(new ListItem("Juan Pérez", "1"));
            ddlVendedor.Items.Add(new ListItem("María García", "2"));
            ddlVendedor.Items.Add(new ListItem("Carlos López", "3"));
        }

        /// <summary>
        /// Carga las ventas según los filtros aplicados
        /// </summary>
        private void CargarVentas()
        {
            try
            {
                // TODO: Reemplazar con consulta real a la base de datos
                DataTable dt = ObtenerVentasSimuladas();

                // Aplicar filtros si existen
                DateTime fechaInicio, fechaFin;
                if (DateTime.TryParse(txtFechaInicio.Text, out fechaInicio))
                {
                    dt = dt.AsEnumerable()
                        .Where(row => row.Field<DateTime>("Fecha") >= fechaInicio)
                        .CopyToDataTable();
                }

                if (DateTime.TryParse(txtFechaFin.Text, out fechaFin))
                {
                    fechaFin = fechaFin.AddDays(1).AddSeconds(-1); // Incluir todo el día
                    dt = dt.AsEnumerable()
                        .Where(row => row.Field<DateTime>("Fecha") <= fechaFin)
                        .CopyToDataTable();
                }

                if (!string.IsNullOrEmpty(ddlVendedor.SelectedValue))
                {
                    string vendedor = ddlVendedor.SelectedItem.Text;
                    dt = dt.AsEnumerable()
                        .Where(row => row.Field<string>("Vendedor") == vendedor)
                        .CopyToDataTable();
                }

                gvVentas.DataSource = dt;
                gvVentas.DataBind();

                lblContador.Text = $"{dt.Rows.Count} ventas encontradas";
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"Error al cargar ventas: {ex.Message}";
            }
        }

        /// <summary>
        /// Actualiza las estadísticas generales
        /// </summary>
        private void ActualizarEstadisticas()
        {
            try
            {
                // TODO: Reemplazar con consulta real a la base de datos
                DataTable dt = ObtenerVentasSimuladas();

                int totalVentas = dt.Rows.Count;
                decimal montoTotal = 0m;

                foreach (DataRow row in dt.Rows)
                {
                    montoTotal += row.Field<decimal>("MontoTotal");
                }

                decimal promedio = totalVentas > 0 ? montoTotal / totalVentas : 0m;

                lblTotalVentas.Text = totalVentas.ToString();
                lblMontoTotal.Text = $"S/ {montoTotal:0.00}";
                lblPromedio.Text = $"S/ {promedio:0.00}";
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"Error al calcular estadísticas: {ex.Message}";
            }
        }

        /// <summary>
        /// Datos simulados de ventas (reemplazar con consulta real)
        /// </summary>
        private DataTable ObtenerVentasSimuladas()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("IdVenta", typeof(int));
            dt.Columns.Add("Fecha", typeof(DateTime));
            dt.Columns.Add("Cliente", typeof(string));
            dt.Columns.Add("Vendedor", typeof(string));
            dt.Columns.Add("Canal", typeof(string));
            dt.Columns.Add("CantidadProductos", typeof(int));
            dt.Columns.Add("MontoTotal", typeof(decimal));

            // Datos de ejemplo
            dt.Rows.Add(1, DateTime.Now.AddDays(-5), "Ana Torres", "Juan Pérez", "Instagram", 3, 150.50m);
            dt.Rows.Add(2, DateTime.Now.AddDays(-4), "Luis Ramos", "María García", "WhatsApp", 2, 89.90m);
            dt.Rows.Add(3, DateTime.Now.AddDays(-3), "Carmen Soto", "Juan Pérez", "Facebook", 5, 275.00m);
            dt.Rows.Add(4, DateTime.Now.AddDays(-2), "Pedro Vega", "Carlos López", "TikTok", 1, 45.00m);
            dt.Rows.Add(5, DateTime.Now.AddDays(-1), "Rosa Díaz", "María García", "Instagram", 4, 320.75m);
            dt.Rows.Add(6, DateTime.Now, "Miguel Ruiz", "Juan Pérez", "WhatsApp", 2, 120.00m);

            return dt;
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
                dt.Rows.Add("Producto C", 1, 45.90m, 45.90m);
                dt.Rows.Add("Producto D", 1, 44.00m, 44.00m);
            }
            else
            {
                dt.Rows.Add("Producto X", 1, 100.00m, 100.00m);
            }

            return dt;
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarVentas();
            ActualizarEstadisticas();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFechaInicio.Text = string.Empty;
            txtFechaFin.Text = string.Empty;
            ddlVendedor.SelectedIndex = 0;
            CargarVentas();
            ActualizarEstadisticas();
            lblMensaje.Text = string.Empty;
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
        /// Muestra el detalle de una venta específica
        /// </summary>
        private void MostrarDetalle(int idVenta)
        {
            try
            {
                // TODO: Reemplazar con consulta real a la base de datos
                DataTable ventas = ObtenerVentasSimuladas();
                DataRow venta = ventas.AsEnumerable()
                    .FirstOrDefault(row => row.Field<int>("IdVenta") == idVenta);

                if (venta != null)
                {
                    lblIdVentaDetalle.Text = idVenta.ToString();
                    lblClienteDetalle.Text = venta["Cliente"].ToString();
                    lblVendedorDetalle.Text = venta["Vendedor"].ToString();
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