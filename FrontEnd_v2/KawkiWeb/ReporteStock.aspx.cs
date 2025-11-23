using System;
using System.Web.UI;

namespace KawkiWeb
{
    public partial class ReporteStock : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            var m = Master as KawkiWeb;
            if (m != null)
            {
                try { m.SetActive("ReporteStock"); } catch { }
            }

            if (!IsPostBack)
            {
                txtFechaInicio.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
                txtFechaFin.Text = DateTime.Now.ToString("yyyy-MM-dd");
            }
        }

        protected void btnGenerar_Click(object sender, EventArgs e)
        {
            // Simulación de datos (en una implementación real, estos vendrían desde la BD)
            lblSinStock.Text = "5";
            lblStockBajo.Text = "8";
            lblStockTotal.Text = "285";
            lblBajaRotacion.Text = "12";
        }
    }
}
