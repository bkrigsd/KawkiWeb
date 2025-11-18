using System;
using System.Linq;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSProductos;
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO;

namespace KawkiWeb
{
    public partial class DetalleProducto : System.Web.UI.Page
    {
        private ProductosBO productosBO;
        private ProductosVariantesBO productosVariantesBO;

        public DetalleProducto()
        {
            productosBO = new ProductosBO();
            productosVariantesBO = new ProductosVariantesBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Intentar obtener de Session primero (más rápido)
                if (Session["DetalleProductoId"] != null)
                {
                    CargarDesdeSession();
                }
                else
                {
                    // Fallback: obtener de URL si no hay Session
                    string idParam = Request.QueryString["id"];
                    string colorParam = Request.QueryString["color"];

                    if (string.IsNullOrEmpty(idParam) || !int.TryParse(idParam, out int productoId))
                    {
                        Response.Redirect("Productos.aspx");
                        return;
                    }

                    CargarDesdeBaseDatos(productoId, colorParam);
                }
            }
        }

        private void CargarDesdeSession()
        {
            try
            {
                // Obtener TODO de Session (sin consultas a BD)
                int productoId = Convert.ToInt32(Session["DetalleProductoId"]);
                string color = Session["DetalleColor"].ToString();
                string nombre = Session["DetalleNombre"].ToString();
                decimal precio = Convert.ToDecimal(Session["DetallePrecio"]);
                string descripcion = Session["DetalleDescripcion"].ToString();
                string imagen = Session["DetalleImagen"].ToString();
                string tallasParam = Session["DetalleTallas"].ToString();
                string stocksParam = Session["DetalleStocks"].ToString();

                // Llenar página (sin consultas)
                Page.Title = nombre + " - Kawki";
                lblBreadcrumb.Text = nombre;
                lblNombreProducto.Text = nombre;
                lblDescripcion.Text = descripcion;
                lblPrecio.Text = $"S/ {precio:N2}";

                // Extraer categoría del nombre 
                string categoria = nombre.Split(' ')[0];
                lblCategoriaTag.Text = categoria.ToUpper();
                lblCategoriaBadge.Text = categoria.ToUpper();

                imgProductoPrincipal.ImageUrl = imagen;
                imgProductoPrincipal.AlternateText = nombre;

                // Cargar tallas SIN consultar BD
                CargarTallasRapido(productoId, color, tallasParam, stocksParam);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error en Session: {ex.Message}");
                Response.Redirect("Productos.aspx");
            }
        }

        private void CargarTallasRapido(int productoId, string color, string tallasParam, string stocksParam)
        {
            rblTallas.Items.Clear();

            var tallas = tallasParam.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                    .Select(t => t.Trim())
                                    .ToArray();

            var stocks = stocksParam.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                    .Select(s => int.Parse(s.Trim()))
                                    .ToArray();

            if (tallas.Length != stocks.Length)
            {
                MostrarMensaje("Error en los datos de tallas.", "error");
                return;
            }

            // Solo consultar variantes si seleccionan una talla
            // Guardar el mapeo talla -> stock en ViewState
            var tallaStockMap = new System.Collections.Generic.Dictionary<string, int>();

            int stockTotal = 0;
            int tallasAgotadas = 0;

            for (int i = 0; i < tallas.Length; i++)
            {
                string talla = tallas[i];
                int stock = stocks[i];
                stockTotal += stock;

                tallaStockMap[talla] = stock;

                string textoTalla = $"Talla {talla}";

                if (stock > 0)
                {
                    textoTalla += $" - {stock} disponibles";
                    if (stock <= 5)
                        textoTalla += " ⚠️";
                }
                else
                {
                    textoTalla += " - AGOTADO ❌";
                    tallasAgotadas++;
                }

                // Solo buscaremos el prod_variante_id cuando seleccionen
                ListItem item = new ListItem(textoTalla, talla);
                item.Enabled = stock > 0;

                if (stock == 0)
                    item.Attributes.Add("class", "talla-agotada");
                else if (stock <= 5)
                    item.Attributes.Add("class", "talla-stock-bajo");

                rblTallas.Items.Add(item);
            }

            // Guardar info en ViewState para cuando seleccionen
            ViewState["ProductoId"] = productoId;
            ViewState["Color"] = color;
            ViewState["TallaStockMap"] = tallaStockMap;

            // Alertas
            if (stockTotal == 0)
            {
                MostrarMensaje("❌ Producto agotado en todas las tallas.", "error");
            }
            else if (stockTotal <= 10)
            {
                MostrarMensaje($"⚠️ Stock bajo: Solo {stockTotal} unidades disponibles en total.", "warning");
            }
            else if (tallasAgotadas > 0)
            {
                MostrarMensaje($"ℹ️ {tallasAgotadas} talla(s) agotada(s).", "info");
            }
        }

        private void CargarDesdeBaseDatos(int productoId, string color)
        {
            // Fallback: si no hay Session, hacer la consulta completa
            productosDTO producto = productosBO.ObtenerPorId(productoId);

            if (producto == null)
            {
                Response.Redirect("Productos.aspx");
                return;
            }

            var variantes = productosVariantesBO.ListarPorProducto(productoId)
                .Where(v => v.color != null && v.color.nombre.Equals(color, StringComparison.OrdinalIgnoreCase))
                .ToList();

            if (variantes.Count == 0)
            {
                MostrarMensaje("No hay variantes disponibles.", "info");
                return;
            }

            string nombreProducto = $"{producto.categoria?.nombre} {producto.estilo?.nombre} {color}".Trim();
            if (!string.IsNullOrEmpty(nombreProducto))
            {
                nombreProducto = char.ToUpper(nombreProducto[0]) + nombreProducto.Substring(1);
            }

            Page.Title = nombreProducto + " - Kawki";
            lblBreadcrumb.Text = nombreProducto;
            lblNombreProducto.Text = nombreProducto;
            lblDescripcion.Text = producto.descripcion ?? nombreProducto;
            lblPrecio.Text = $"S/ {producto.precio_venta:N2}";

            string categoria = producto.categoria?.nombre ?? "Sin categoría";
            lblCategoriaTag.Text = categoria.ToUpper();
            lblCategoriaBadge.Text = categoria.ToUpper();

            if (variantes.Count > 0 && !string.IsNullOrEmpty(variantes[0].url_imagen))
            {
                imgProductoPrincipal.ImageUrl = variantes[0].url_imagen;
                imgProductoPrincipal.AlternateText = nombreProducto;
            }

            CargarTallasDesdeVariantes(variantes);
        }

        private void CargarTallasDesdeVariantes(System.Collections.Generic.List<productosVariantesDTO> variantes)
        {
            rblTallas.Items.Clear();

            var grupos = variantes
                .Where(v => v.talla != null)
                .GroupBy(v => v.talla.numero)
                .OrderBy(g => g.Key);

            int stockTotal = 0;
            int tallasAgotadas = 0;

            foreach (var grupo in grupos)
            {
                int talla = grupo.Key;
                int stock = grupo.Sum(v => v.stock);
                var variante = grupo.First();

                stockTotal += stock;

                string textoTalla = $"Talla {talla}";

                if (stock > 0)
                {
                    textoTalla += $" - {stock} disponibles";
                    if (stock <= 5)
                        textoTalla += " ⚠️";
                }
                else
                {
                    textoTalla += " - AGOTADO ❌";
                    tallasAgotadas++;
                }

                ListItem item = new ListItem(textoTalla, variante.prod_variante_id.ToString());
                item.Enabled = stock > 0;

                if (stock == 0)
                    item.Attributes.Add("class", "talla-agotada");
                else if (stock <= 5)
                    item.Attributes.Add("class", "talla-stock-bajo");

                rblTallas.Items.Add(item);
            }

            if (stockTotal == 0)
            {
                MostrarMensaje("❌ Producto agotado en todas las tallas.", "error");
            }
            else if (stockTotal <= 10)
            {
                MostrarMensaje($"⚠️ Stock bajo: Solo {stockTotal} unidades disponibles en total.", "warning");
            }
            else if (tallasAgotadas > 0)
            {
                MostrarMensaje($"ℹ️ {tallasAgotadas} talla(s) agotada(s).", "info");
            }
        }

        private void MostrarMensaje(string mensaje, string tipo)
        {
            pnlMensaje.Visible = true;
            lblMensaje.Text = mensaje;
            pnlMensaje.CssClass = $"mensaje-validacion {tipo}";
        }

        protected void rblTallas_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(rblTallas.SelectedValue))
                return;

            // ✅ AHORA SÍ consultar la variante específica
            int productoId = (int)ViewState["ProductoId"];
            string color = ViewState["Color"].ToString();
            string tallaSeleccionada = rblTallas.SelectedValue;

            // Buscar la variante con esta talla y color
            var variante = productosVariantesBO.ListarPorProducto(productoId)
                .FirstOrDefault(v => v.color != null &&
                                    v.color.nombre.Equals(color, StringComparison.OrdinalIgnoreCase) &&
                                    v.talla != null &&
                                    v.talla.numero.ToString() == tallaSeleccionada);

            if (variante != null)
            {
                MostrarInfoVariante(variante);
            }
        }

        private void MostrarInfoVariante(productosVariantesDTO variante)
        {
            lblSKU.Text = variante.SKU;
            lblSKUVariante.Text = variante.SKU;
            lblTallaSeleccionada.Text = variante.talla?.numero.ToString() ?? "N/A";
            lblStockDisponible.Text = variante.stock.ToString();

            ActualizarAlertaStock(variante.stock, variante.stock_minimo);

            pnlInfoStock.Visible = true;
            hdnVarianteId.Value = variante.prod_variante_id.ToString();

            if (!string.IsNullOrEmpty(variante.url_imagen))
            {
                imgProductoPrincipal.ImageUrl = variante.url_imagen;
            }
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