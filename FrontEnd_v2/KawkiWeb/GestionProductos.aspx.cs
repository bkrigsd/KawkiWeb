using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class GestionProductos : System.Web.UI.Page
    {
        private static List<ItemProducto> _items = new List<ItemProducto>();

        protected void Page_Load(object sender, EventArgs e)
        {
            var m = Master as KawkiWeb;
            if (m != null)
            {
               try { m.SetActive("GestionProductos"); } catch { /* ignora si no existe */ }
            }

            if (!IsPostBack)
            {
                if (_items.Count == 0)
                {
                    _items.Add(new ItemProducto("#0001", "Oxford Clásico Beige", "Oxford", "Beige", 289.90m, 25, "Cuero genuino"));
                    _items.Add(new ItemProducto("#0002", "Derby Elegante Marrón", "Derby", "Marrón", 259.90m, 18, "Cuero"));
                    _items.Add(new ItemProducto("#0003", "Oxford Premium Negro", "Oxford", "Negro", 319.90m, 8, "Piel"));
                }
                BindGrid();
            }
        }

        private IEnumerable<ItemProducto> Filtrar()
        {
            var q = _items.AsEnumerable();

            if (!string.IsNullOrWhiteSpace(txtBuscar.Text))
            {
                var s = txtBuscar.Text.Trim().ToLower();
                q = q.Where(x => x.Codigo.ToLower().Contains(s)
                              || x.Nombre.ToLower().Contains(s)
                              || x.Color.ToLower().Contains(s));
            }
            if (!string.IsNullOrEmpty(ddlCategoria.SelectedValue))
                q = q.Where(x => x.Categoria == ddlCategoria.SelectedValue);

            return q;
        }

        private void BindGrid()
        {
            gvProductos.DataSource = _items;
            gvProductos.DataBind();
        }

        protected void btnAplicarFiltros_Click(object sender, EventArgs e) => BindGrid();

        protected void btnNuevo_Click(object sender, EventArgs e)
        {
            litTituloModal.Text = "Nuevo Producto";
            hfEditIndex.Value = string.Empty;
            LimpiarForm();
            maskModal.Visible = true;
        }


        protected void btnCerrarModal_Click(object sender, EventArgs e)
        {
            maskModal.Visible = false;
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            decimal precio = 0; int stock = 0; int.TryParse(txtStock.Text, out stock);
            decimal.TryParse(txtPrecio.Text.Replace(",", "."), out precio);

            if (string.IsNullOrWhiteSpace(hfEditIndex.Value))
            {
                // Crear
                string codigo = "#" + ((_items.Count + 1).ToString("0000"));
                var it = new ItemProducto(
                    codigo,
                    txtNombre.Text,
                    ddlCatForm.SelectedValue,
                    ddlColor.SelectedValue,
                    precio,
                    stock,
                    txtMaterial.Text
                );
                it.PrecioOriginal = ParseDecimal(txtPrecioOriginal.Text);
                it.DescuentoPorc = ParseDecimal(txtDescuento.Text);
                it.Tallas = txtTallas.Text;
                it.UrlImagen = txtImagen.Text;
                it.Descripcion = txtDescripcion.Text;

                _items.Add(it);
            }
            else
            {
                // Editar
                int idx = int.Parse(hfEditIndex.Value);
                var it = _items[idx];
                it.Nombre = txtNombre.Text;
                it.Categoria = ddlCatForm.SelectedValue;
                it.Color = ddlColor.SelectedValue;
                it.Precio = precio;
                it.Stock = stock;
                it.PrecioOriginal = ParseDecimal(txtPrecioOriginal.Text);
                it.DescuentoPorc = ParseDecimal(txtDescuento.Text);
                it.Tallas = txtTallas.Text;
                it.UrlImagen = txtImagen.Text;
                it.Descripcion = txtDescripcion.Text;
                it.Material = txtMaterial.Text;
            }

            maskModal.Visible = false;
            BindGrid();
        }

        protected void gvProductos_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            int index = Convert.ToInt32(e.CommandArgument);

            if (e.CommandName == "Eliminar")
            {
                _items.RemoveAt(index);
                BindGrid();
            }
            else if (e.CommandName == "Vender")
            {
                var it = _items[index];
                if (it.Stock > 0) it.Stock -= 1;
                BindGrid();
            }
            else if (e.CommandName == "Editar")
            {
                var it = _items[index];
                litTituloModal.Text = "Editar Producto";
                hfEditIndex.Value = index.ToString();

                txtNombre.Text = it.Nombre;
                txtPrecio.Text = it.Precio.ToString("0.##");
                txtPrecioOriginal.Text = it.PrecioOriginal?.ToString("0.##") ?? "";
                ddlCatForm.SelectedValue = it.Categoria;
                ddlColor.SelectedValue = it.Color;
                txtStock.Text = it.Stock.ToString();
                txtTallas.Text = it.Tallas;
                txtDescuento.Text = it.DescuentoPorc?.ToString("0.##") ?? "";
                txtImagen.Text = it.UrlImagen;
                txtDescripcion.Text = it.Descripcion;
                txtMaterial.Text = it.Material;

                maskModal.Visible = true;
            }
        }

        protected void gvProductos_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType != DataControlRowType.DataRow) return;

            var it = (ItemProducto)e.Row.DataItem;

            var sp = (HtmlGenericControl)e.Row.FindControl("spStock");
            if (sp != null)
            {
                sp.InnerText = $"{it.Stock} unidades";
                if (it.Stock <= 8) sp.Attributes["class"] = "stock-low"; // pinta en rojo
            }
        }


        private void LimpiarForm()
        {
            txtNombre.Text = txtPrecio.Text = txtPrecioOriginal.Text =
            txtStock.Text = txtTallas.Text = txtDescuento.Text =
            txtImagen.Text = txtDescripcion.Text = txtMaterial.Text = "";
            ddlCatForm.SelectedIndex = 0;
            ddlColor.SelectedIndex = 0;
            ddlEstilo.SelectedIndex = 0;
        }

        private decimal? ParseDecimal(string s)
        {
            if (string.IsNullOrWhiteSpace(s)) return null;
            decimal v; if (decimal.TryParse(s.Replace(",", "."), out v)) return v;
            return null;
        }

        [Serializable]
        public class ItemProducto
        {
            public string Codigo { get; set; }
            public string Nombre { get; set; }
            public string Categoria { get; set; }
            public string Color { get; set; }
            public decimal Precio { get; set; }
            public int Stock { get; set; }

            public string Material { get; set; }
            public string Descripcion { get; set; }
            public string Tallas { get; set; }
            public decimal? PrecioOriginal { get; set; }
            public decimal? DescuentoPorc { get; set; }
            public string UrlImagen { get; set; }

            public ItemProducto() { }
            public ItemProducto(string codigo, string nombre, string categoria, string color, decimal precio, int stock, string material)
            {
                Codigo = codigo; Nombre = nombre; Categoria = categoria; Color = color;
                Precio = precio; Stock = stock; Material = material;
            }
        }
    }
}