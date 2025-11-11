using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

namespace KawkiWeb
{
    public partial class DetalleProducto : System.Web.UI.Page
    {
        private int ProductoId
        {
            get { return ViewState["ProductoId"] != null ? (int)ViewState["ProductoId"] : 0; }
            set { ViewState["ProductoId"] = value; }
        }

        private DataRow ProductoActual
        {
            get
            {
                if (ProductoId == 0) return null;

                // Vuelves a obtener la tabla simulada y buscar el producto
                DataTable dt = ObtenerProductosSimulados();
                return dt.AsEnumerable()
                         .FirstOrDefault(p => p.Field<int>("ProductoId") == ProductoId);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string idParam = Request.QueryString["id"];

                if (string.IsNullOrEmpty(idParam) || !int.TryParse(idParam, out int productoId))
                {
                    Response.Redirect("Productos.aspx");
                    return;
                }

                ProductoId = productoId;
                CargarDetalleProducto(productoId);
            }
        }

        private void CargarDetalleProducto(int productoId)
        {
            DataTable dtProductos = ObtenerProductosSimulados();
            DataRow producto = dtProductos.AsEnumerable()
                .FirstOrDefault(p => p.Field<int>("ProductoId") == productoId);

            if (producto == null)
            {
                Response.Redirect("Productos.aspx");
                return;
            }
            string nombreProducto = producto["Nombre"].ToString();
            Page.Title = nombreProducto + " - Kawki";

            // Llenar controles básicos
            lblBreadcrumb.Text = producto["Nombre"].ToString();
            lblNombreProducto.Text = producto["Nombre"].ToString();
            lblDescripcion.Text = producto["Descripcion"].ToString();
            lblPrecio.Text = string.Format("{0:N2}", producto["Precio"]);

            string categoria = producto["Categoria"].ToString();
            lblCategoriaTag.Text = categoria.ToUpper();
            lblCategoriaBadge.Text = categoria.ToUpper();

            imgProductoPrincipal.ImageUrl = producto["ImagenUrl"].ToString();
            imgProductoPrincipal.AlternateText = producto["Nombre"].ToString();

            // Cargar tallas con stock
            CargarTallasDisponibles(producto);

            // Calcular y mostrar stock total
            MostrarStockTotal(producto);
        }

        private void CargarTallasDisponibles(DataRow producto)
        {
            rblTallas.Items.Clear();

            string tallasDisponibles = producto["TallasDisponibles"].ToString();
            string[] tallas = tallasDisponibles.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                               .Select(t => t.Trim())
                                               .ToArray();

            string stockString = producto["Stock"].ToString();
            string[] stocks = stockString.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                         .Select(s => s.Trim())
                                         .ToArray();

            // Crear un item por cada talla
            for (int i = 0; i < tallas.Length; i++)
            {
                int stock = 0;
                if (i < stocks.Length)
                {
                    int.TryParse(stocks[i], out stock);
                }

                string textoTalla = $"Talla {tallas[i]}";
                if (stock > 0)
                {
                    textoTalla += $" ({stock} disponibles)";
                }
                else
                {
                    textoTalla += " (Agotado)";
                }

                ListItem item = new ListItem(textoTalla, i.ToString());
                item.Enabled = stock > 0;

                if (stock == 0)
                {
                    item.Attributes.Add("class", "talla-agotada");
                }

                rblTallas.Items.Add(item);
            }

        }

        private void MostrarStockTotal(DataRow producto)
        {
            string stockString = producto["Stock"].ToString();
            string[] stocks = stockString.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries);

            int stockTotal = 0;
            foreach (string s in stocks)
            {
                if (int.TryParse(s.Trim(), out int stockPorTalla))
                {
                    stockTotal += stockPorTalla;
                }
            }

            // Mostrar mensaje informativo sobre el stock total (opcional)
            if (stockTotal == 0)
            {
                pnlMensaje.Visible = true;
                lblMensaje.Text = "Este producto está temporalmente agotado en todas las tallas.";
                pnlMensaje.CssClass = "mensaje-validacion info";
            }
            else if (stockTotal <= 10)
            {
                pnlMensaje.Visible = true;
                lblMensaje.Text = $"¡Últimas unidades disponibles! Solo quedan {stockTotal} en total.";
                pnlMensaje.CssClass = "mensaje-validacion info";
            }
        }

        protected void rblTallas_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ProductoActual == null || string.IsNullOrEmpty(rblTallas.SelectedValue))
                return;

            int indice = Convert.ToInt32(rblTallas.SelectedValue);

            string tallasDisponibles = ProductoActual["TallasDisponibles"].ToString();
            string[] tallas = tallasDisponibles.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                               .Select(t => t.Trim())
                                               .ToArray();

            string stockString = ProductoActual["Stock"].ToString();
            string[] stocks = stockString.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                         .Select(s => s.Trim())
                                         .ToArray();

            if (indice >= 0 && indice < tallas.Length && indice < stocks.Length)
            {
                string tallaSeleccionada = tallas[indice];
                int stockTalla = 0;
                int.TryParse(stocks[indice], out stockTalla);

                // Mostrar información de la talla seleccionada
                MostrarInfoVariante(tallaSeleccionada, stockTalla);
            }
        }

        private void MostrarInfoVariante(string talla, int stock)
        {
            // Generar SKU
            string categoria = ProductoActual["Categoria"].ToString().ToUpper().Substring(0, 3);
            string color = ProductoActual["Color"].ToString().ToUpper().Substring(0, 3);
            string sku = $"{categoria}-{color}-{talla}";

            lblSKU.Text = sku;
            lblSKUVariante.Text = sku;
            lblTallaSeleccionada.Text = talla;
            lblStockDisponible.Text = stock.ToString();

            // Actualizar alerta de stock para esta talla
            ActualizarAlertaStock(stock, 5);

            pnlInfoStock.Visible = true;
            hdnVarianteId.Value = talla; // Guardamos la talla seleccionada
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

        private DataTable ObtenerProductosSimulados()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ProductoId", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Descripcion", typeof(string));
            dt.Columns.Add("Precio", typeof(decimal));
            dt.Columns.Add("TallasDisponibles", typeof(string));
            dt.Columns.Add("Categoria", typeof(string));
            dt.Columns.Add("Estilo", typeof(string));
            dt.Columns.Add("Color", typeof(string));
            dt.Columns.Add("Stock", typeof(string));
            dt.Columns.Add("ImagenUrl", typeof(string));

            // Productos tipo Oxford
            dt.Rows.Add(1, "Oxford Clásico Beige", "Zapato oxford de cuero genuino con acabado premium, ideal para ocasiones formales y uso diario.", 150.90m, "35, 36, 39", "oxford", "clasico", "beige", "15,5,9", "~/Images/OxfordClasicoBeige.jpg");
            dt.Rows.Add(2, "Oxford Premium Negro", "Zapato oxford de diseño moderno y sofisticado, perfecto para eventos importantes.", 250.90m, "35, 36, 37, 38, 39", "oxford", "charol", "negro", "15,12,0,5,9", "~/Images/OxfordPremiumNegro.jpg");
            dt.Rows.Add(3, "Oxford Bicolor Café", "Zapato oxford con elegante combinación de tonos café, estilo vintage refinado.", 160.90m, "36, 37, 38, 39", "oxford", "combinado", "marron", "15,0,5,9", "~/Images/OxfordBicolorCafe.jpg");

            // Productos tipo Derby
            dt.Rows.Add(4, "Derby Elegante Marrón", "Derby de cuero con diseño tejido elegante, versátil para cualquier ocasión.", 215.90m, "35, 36, 37, 38, 39", "derby", "clasico", "marron", "15,12,0,5,9", "~/Images/DerbyClasicoMarron.jpg");
            dt.Rows.Add(5, "Derby Charol Crema", "Derby charol con suela gruesa y diseño moderno, máxima comodidad.", 210.90m, "35, 36, 37, 38, 39", "derby", "charol", "crema", "15,12,0,5,9", "~/Images/DerbyClasicoCrema.jpg");
            dt.Rows.Add(6, "Derby Clasico Negro", "Derby clasico con suela de goma antideslizante, ideal para caminar.", 169.90m, "36, 37, 38, 39", "derby", "clasico", "negro", "12,0,5,9", "~/Images/DerbyClasicoNegro.jpg");

            return dt;
        }

        protected void btnAgregarCarrito_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(rblTallas.SelectedValue))
            {
                // Mostrar mensaje de que debe seleccionar una talla
                ScriptManager.RegisterStartupScript(this, GetType(), "alert",
                    "alert('Por favor selecciona una talla');", true);
                return;
            }

            // TODO: Implementar lógica para agregar al carrito
            string tallaSeleccionada = lblTallaSeleccionada.Text;
            string productoNombre = lblNombreProducto.Text;

            ScriptManager.RegisterStartupScript(this, GetType(), "alert",
                $"alert('Producto agregado al carrito:\\n{productoNombre}\\nTalla: {tallaSeleccionada}');", true);
        }
    }
}