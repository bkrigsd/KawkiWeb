using System;
using System.Web.UI;

namespace KawkiWeb
{
    public partial class ReporteVentas : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            var m = Master as KawkiWeb;
            if (m != null)
            {
                try { m.SetActive("ReporteVentas"); } catch { }
            }

            if (!IsPostBack)
            {
                txtFechaInicio.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
                txtFechaFin.Text = DateTime.Now.ToString("yyyy-MM-dd");
            }
        }

        protected void btnGenerar_Click(object sender, EventArgs e)
        {
            // Ejemplo: aquí se cargarían los datos de ventas desde BD
            // y luego se asignarían a los controles o gráficos.

            // Datos simulados:
            lblVentasTotales.Text = "S/. 12,450.00";
            lblProductosVendidos.Text = "320";
            lblClientes.Text = "85";
            lblPromedioDiario.Text = "S/. 1,778.57";
        }
    }
}
