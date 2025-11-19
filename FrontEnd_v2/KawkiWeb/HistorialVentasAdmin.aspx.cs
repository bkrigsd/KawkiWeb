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
    public partial class HistorialVentasAdmin : Page
    {
        private VentasBO ventasBO = new VentasBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            // ==========================================================
            // ANTI-CACHE (igual que RegistroUsuario)
            // ==========================================================
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.Now.AddSeconds(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.AddHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            Response.AddHeader("Pragma", "no-cache");
            Response.AddHeader("Expires", "0");

            // ==========================================================
            // SOLO EJECUTAR EN FIRST LOAD
            // ==========================================================
            if (!IsPostBack)
            {
                string rol = Session["Rol"] as string;

                // ==========================================================
                // VALIDACIÓN DE SESIÓN (misma lógica que RegistroUsuario)
                // ==========================================================
                if (string.IsNullOrEmpty(rol))
                {
                    Response.Redirect("Error404.aspx", false);
                    return;
                }

                // Si no es admin → redirigir
                if (rol.ToLower() == "vendedor")
                {
                    Response.Redirect("Productos.aspx", false);
                    return;
                }

                // En este punto SÍ es admin → cargar datos
                try
                {
                    CargarVendedores();
                    CargarVentas();
                    ActualizarEstadisticas();
                }
                catch (Exception ex)
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Error al cargar la página: " + ex.Message;
                }
            }
        }

        // ==========================================================
        // Cargar lista de vendedores reales
        // ==========================================================
        private void CargarVendedores()
        {
            try
            {
                var ventas = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

                var vendedores = ventas
                    .Where(v => v.usuario != null)
                    .Select(v => v.usuario.nombreUsuario)
                    .Distinct()
                    .OrderBy(x => x)
                    .ToList();

                ddlVendedor.Items.Clear();
                ddlVendedor.Items.Add(new ListItem("Todos los vendedores", ""));

                foreach (var ven in vendedores)
                    ddlVendedor.Items.Add(new ListItem(ven, ven));
            }
            catch
            {
                ddlVendedor.Items.Clear();
                ddlVendedor.Items.Add(new ListItem("Todos los vendedores", ""));
            }
        }

        // ==========================================================
        // Cargar ventas reales (usando objetos, NO DataTables)
        // ==========================================================
        private void CargarVentas()
        {
            try
            {
                var lista = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

                // ----------------------------------
                // Filtro por fecha inicio
                // ----------------------------------
                if (DateTime.TryParse(txtFechaInicio.Text, out DateTime inicio))
                {
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) >= inicio).ToList();
                }

                // ----------------------------------
                // Filtro por fecha fin
                // ----------------------------------
                if (DateTime.TryParse(txtFechaFin.Text, out DateTime fin))
                {
                    fin = fin.AddDays(1).AddSeconds(-1);
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) <= fin).ToList();
                }

                // ----------------------------------
                // Filtro por vendedor
                // ----------------------------------
                if (!string.IsNullOrEmpty(ddlVendedor.SelectedValue))
                {
                    string vendedor = ddlVendedor.SelectedValue;
                    lista = lista.Where(v => v.usuario?.nombreUsuario == vendedor).ToList();
                }

                // ----------------------------------
                // Transformar a modelo para GridView
                // ----------------------------------
                var gvLista = lista.Select(v => new
                {
                    IdVenta = v.venta_id,
                    Fecha = DateTime.Parse(v.fecha_hora_creacion),

                    // VENDEDOR = nombreUsuario (login)
                    Vendedor = v.usuario?.nombreUsuario ?? "N/A",

                    Canal = v.redSocial?.nombre,

                    // NUEVAS COLUMNAS
                    //Descuento = v.descuento?.descripcion,
                    //EsValida = v.esValida ? "Sí" : "No",

                    MontoTotal = v.total
                }).ToList();

                gvVentas.DataSource = gvLista;
                gvVentas.DataBind();

                lblContador.Text = $"{gvLista.Count} ventas encontradas";
                lblMensaje.Text = "";
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar ventas: " + ex.Message);
            }
        }

        // ==========================================================
        // Estadísticas
        // ==========================================================
        private void ActualizarEstadisticas()
        {
            try
            {
                var ventas = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

                if (!ventas.Any())
                {
                    lblTotalVentas.Text = "0";
                    lblMontoTotal.Text = "S/ 0.00";
                    lblPromedio.Text = "S/ 0.00";
                    return;
                }

                decimal montoTotal = ventas.Sum(v => Convert.ToDecimal(v.total));
                int cantidad = ventas.Count;

                lblTotalVentas.Text = cantidad.ToString();
                lblMontoTotal.Text = $"S/ {montoTotal:N2}";
                lblPromedio.Text = $"S/ {(montoTotal / cantidad):N2}";
            }
            catch (Exception ex)
            {
                MostrarError("Error en estadísticas: " + ex.Message);
            }
        }

        // ==========================================================
        // Botones
        // ==========================================================
        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarVentas();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFechaInicio.Text = "";
            txtFechaFin.Text = "";
            ddlVendedor.SelectedIndex = 0;

            CargarVentas();
            ActualizarEstadisticas();
        }

        // ==========================================================
        // Ver detalle
        // ==========================================================
        protected void gvVentas_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalle")
            {
                int id = Convert.ToInt32(e.CommandArgument);
                MostrarDetalle(id);
            }
        }

        private void MostrarDetalle(int idVenta)
        {
            try
            {
                var venta = ventasBO.ObtenerPorIdVenta(idVenta);

                lblIdVentaDetalle.Text = venta.venta_id.ToString();
                //lblClienteDetalle.Text = $"{venta.usuario?.nombre} {venta.usuario?.apePaterno}";
                lblVendedorDetalle.Text = venta.usuario?.nombreUsuario;
                lblFechaDetalle.Text = DateTime.Parse(venta.fecha_hora_creacion).ToString("dd/MM/yyyy HH:mm");
                lblCanalDetalle.Text = venta.redSocial?.nombre;
                lblTotalDetalle.Text = $"S/ {venta.total:N2}";

                //var lista = venta.detalles.Select(d => new
                //{
                //    Producto = d.prodVariante?.nombre ?? "N/D",
                //    Cantidad = d.cantidad,
                //    PrecioUnitario = d.precio_unitario,
                //    Subtotal = d.subtotal
                //}).ToList();

                //gvDetalleProductos.DataSource = lista;
                //gvDetalleProductos.DataBind();

                //pnlDetalle.Visible = true;
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

        // ==========================================================
        // Utilidad
        // ==========================================================
        private void MostrarError(string msg)
        {
            lblMensaje.CssClass = "text-danger d-block mb-2";
            lblMensaje.Text = msg;
        }
    }
}
