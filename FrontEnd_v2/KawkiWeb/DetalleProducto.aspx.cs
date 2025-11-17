using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSProductos;
// Importar el namespace de variantes con alias para sus tipos
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO;
using coloresDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO;
using tallasDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO;

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
        private int ProductoId
        {
            get { return ViewState["ProductoId"] != null ? (int)ViewState["ProductoId"] : 0; }
            set { ViewState["ProductoId"] = value; }
        }

        private productosDTO ProductoActual
        {
            get
            {
                if (ProductoId == 0) return null;
                return productosBO.ObtenerPorId(ProductoId);
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
            productosDTO producto = productosBO.ObtenerPorId(productoId);

            if (producto == null)
            {
                Response.Redirect("Productos.aspx");
                return;
            }

            string nombreProducto = producto.descripcion;
            Page.Title = nombreProducto + " - Kawki";

            // Llenar controles básicos
            lblBreadcrumb.Text = nombreProducto;
            lblNombreProducto.Text = nombreProducto;
            lblDescripcion.Text = nombreProducto; // O agregar un campo descripción larga si lo tienes
            lblPrecio.Text = string.Format("{0:N2}", producto.precio_venta);

            string categoria = producto.categoria?.nombre ?? "Sin categoría";
            lblCategoriaTag.Text = categoria.ToUpper();
            lblCategoriaBadge.Text = categoria.ToUpper();

            // Cargar imagen de la variante
            if (producto.variantes != null && producto.variantes.Length > 0)
            {
                imgProductoPrincipal.ImageUrl = producto.variantes[0].url_imagen;
                imgProductoPrincipal.AlternateText = nombreProducto;
            }

            // Cargar variantes con stock
            CargarTallasDisponibles(producto);

            // Calcular y mostrar stock total
            MostrarStockTotal(producto);
        }

        private void CargarTallasDisponibles(productosDTO producto)
        {
            rblTallas.Items.Clear();

            if (producto.variantes == null || producto.variantes.Length == 0)
            {
                pnlMensaje.Visible = true;
                lblMensaje.Text = "Este producto no tiene variantes disponibles.";
                pnlMensaje.CssClass = "mensaje-validacion info";
                return;
            }

            // Agrupar variantes por talla(
            var variantesPorTalla = producto.variantes
                .Where(v => v.talla != null) // Filtrar variantes sin talla
                .GroupBy(v => v.talla.talla_id)
                .Select(g => new
                {
                    TallaId = g.Key,
                    TallaNombre = g.First().talla.numero, // Es int
                    StockTotal = g.Sum(v => v.stock),
                    PrimeraVariante = g.First()
                })
                .OrderBy(x => x.TallaNombre) // Ordenar por número (int)
                .ToList();

            for (int i = 0; i < variantesPorTalla.Count; i++)
            {
                var grupo = variantesPorTalla[i];

                // Convertir TallaNombre (int) a string
                string textoTalla = $"Talla {grupo.TallaNombre.ToString()}";

                if (grupo.StockTotal > 0)
                {
                    textoTalla += $" ({grupo.StockTotal} disponibles)";
                }
                else
                {
                    textoTalla += " (Agotado)";
                }

                ListItem item = new ListItem(textoTalla, grupo.PrimeraVariante.prod_variante_id.ToString());
                item.Enabled = grupo.StockTotal > 0;

                if (grupo.StockTotal == 0)
                {
                    item.Attributes.Add("class", "talla-agotada");
                }

                rblTallas.Items.Add(item);
            }

        }

        private void MostrarStockTotal(productosDTO producto)
        {
            int stockTotal = productosBO.CalcularStockTotal(producto.producto_id);

            if (stockTotal == 0)
            {
                pnlMensaje.Visible = true;
                lblMensaje.Text = "Este producto está temporalmente agotado en todas las tallas.";
                pnlMensaje.CssClass = "mensaje-validacion info";
            }
            else if (stockTotal <= 10)
            {
                pnlMensaje.Visible = true;
                lblMensaje.Text = $"Solo quedan {stockTotal} en total.";
                pnlMensaje.CssClass = "mensaje-validacion info";
            }
        }

        protected void rblTallas_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ProductoActual == null || string.IsNullOrEmpty(rblTallas.SelectedValue))
                return;

            int varianteId = Convert.ToInt32(rblTallas.SelectedValue);
            productosVariantesDTO variante = productosVariantesBO.ObtenerPorId(varianteId);

            if (variante != null)
            {
                MostrarInfoVariante(variante);
            }
        }

        private void MostrarInfoVariante(productosVariantesDTO variante)
        {
            lblSKU.Text = variante.SKU;
            lblSKUVariante.Text = variante.SKU;
            // Convertir int a string
            lblTallaSeleccionada.Text = variante.talla != null
                ? variante.talla.numero.ToString()
                : "N/A";
            lblStockDisponible.Text = variante.stock.ToString();

            // Actualizar alerta de stock para esta variante
            ActualizarAlertaStock(variante.stock, variante.stock_minimo);

            pnlInfoStock.Visible = true;
            hdnVarianteId.Value = variante.prod_variante_id.ToString();

            // Actualizar imagen si es necesario
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