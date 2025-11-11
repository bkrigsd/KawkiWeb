using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;

namespace KawkiWeb
{
    public partial class DetalleProducto : System.Web.UI.Page
    {
        private int ProductoId
        {
            get
            {
                if (ViewState["ProductoId"] != null)
                    return (int)ViewState["ProductoId"];
                return 0;
            }
            set { ViewState["ProductoId"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (Request.QueryString["id"] != null)
                {
                    ProductoId = Convert.ToInt32(Request.QueryString["id"]);
                    CargarDetalleProducto();
                }
                else
                {
                    Response.Redirect("Productos.aspx");
                }
            }
        }

        private void CargarDetalleProducto()
        {
            // Por ahora, datos de ejemplo:
            lblNombreProducto.Text = "Oxford Clásico Beige";
            lblBreadcrumb.Text = "Oxford Clásico Beige";
            lblCategoriaTag.Text = "OXFORD";
            lblCategoriaBadge.Text = "OXFORD";
            lblPrecio.Text = "189.90";
            lblDescripcion.Text = "Elegante zapato Oxford de cuero genuino, perfecto para ocasiones formales y uso diario.";
            imgProductoPrincipal.ImageUrl = "~/Images/OxfordClasicoBeige.jpg";

            // Cargar colores y tallas desde la base de datos
            CargarTallasDisponibles();
        }

        private void CargarTallasDisponibles()
        {
            // Datos de ejemplo:
            rblTallas.Items.Clear();

            // Tallas con stock
            rblTallas.Items.Add(new ListItem("35", "101") { Enabled = true });
            rblTallas.Items.Add(new ListItem("36", "102") { Enabled = true });

            // Talla agotada
            ListItem tallaAgotada = new ListItem("37", "103") { Enabled = false };
            tallaAgotada.Attributes.Add("class", "agotado");
            rblTallas.Items.Add(tallaAgotada);

            rblTallas.Items.Add(new ListItem("38", "104") { Enabled = true });
            rblTallas.Items.Add(new ListItem("39", "105") { Enabled = true });
        }

        protected void rblTallas_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(rblTallas.SelectedValue))
            {
                // Obtener información de la variante seleccionada
                int varianteId = Convert.ToInt32(rblTallas.SelectedValue);
                MostrarInfoVariante(varianteId);
            }
        }

        private void MostrarInfoVariante(int varianteId)
        {
            // Datos de ejemplo:
            string sku = "OXF-BEI-" + rblTallas.SelectedItem.Text;
            int stock = Convert.ToInt32(rblTallas.SelectedValue) % 2 == 0 ? 5 : 15; // Simulación

            lblSKU.Text = sku;
            lblSKUVariante.Text = sku;
            lblTallaSeleccionada.Text = rblTallas.SelectedItem.Text;
            lblStockDisponible.Text = stock.ToString();

            ActualizarAlertaStock(stock, 5);

            pnlInfoStock.Visible = true;
            hdnVarianteId.Value = varianteId.ToString();
        }

        private void ActualizarAlertaStock(int stock, int stockMinimo)
        {
            if (stock == 0)
            {
                pnlStockAlert.CssClass = "stock-alert-detalle stock-agotado-detalle";
                lblStockAlert.Text = "⚠️ Producto agotado";
            }
            else if (stock <= stockMinimo)
            {
                pnlStockAlert.CssClass = "stock-alert-detalle stock-bajo-detalle";
                lblStockAlert.Text = $"⚠️ Stock bajo - Solo {stock} unidades disponibles";
            }
            else
            {
                pnlStockAlert.CssClass = "stock-alert-detalle stock-disponible-detalle";
                lblStockAlert.Text = $"✓ Stock disponible - {stock} unidades";
            }
        }
    }
}