using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Carrito : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // 3 productos 
                var items = ObtenerCarrito();
                if (items.Count == 0)
                {
                    items.AddRange(new[]
                    {
                        new CartItem { Id=1, Sku="OXF-CLA-BEI", Nombre="Oxford Clásico Beige Talla 36",
                            Precio=150.90m, Cantidad=1 },
                        new CartItem { Id=2, Sku="DER-CHA-CRE", Nombre="Derby Charol Crema Talla 36",
                            Precio=210.90m, Cantidad=2 },
                        new CartItem { Id=3, Sku="DER-ELE-MAR", Nombre="Derby Elegante Marrón",
                            Precio=215.90m, Cantidad=1 },
                    });
                    Session["Carrito"] = items;
                }
                CargarCarrito();
            }
        }

        private List<CartItem> ObtenerCarrito()
        {
            var list = Session["Carrito"] as List<CartItem>;
            if (list == null)
            {
                list = new List<CartItem>();
                Session["Carrito"] = list;
            }
            return list;
        }

        private void CargarCarrito()
        {
            var items = ObtenerCarrito();
            int count = items.Sum(i => i.Cantidad);
            lblCount.Text = count.ToString();

            bool vacio = items.Count == 0;
            pnlVacio.Visible = vacio;
            pnlConItems.Visible = !vacio;
            if (vacio) return;

            rpCarrito.DataSource = items;
            rpCarrito.DataBind();

            decimal subtotal = items.Sum(i => i.Precio * i.Cantidad);
            decimal descuento = (decimal)(Session["CarritoDescuento"] ?? 0m);
            decimal envio = subtotal >= 5000m ? 0m : 12.9m; // Envío gratis desde S/5000
            decimal total = Math.Max(0, subtotal - descuento) + envio;

            lblSubtotal.Text = subtotal.ToString("C");
            lblDescuento.Text = descuento.ToString("C");
            lblEnvio.Text = envio.ToString("C");
            lblTotal.Text = total.ToString("C");
        }

        protected void rpCarrito_ItemCommand(object source, System.Web.UI.WebControls.RepeaterCommandEventArgs e)
        {
            var items = ObtenerCarrito();
            int id = Convert.ToInt32(e.CommandArgument);
            var it = items.FirstOrDefault(x => x.Id == id);
            if (it == null) { CargarCarrito(); return; }

            switch (e.CommandName)
            {
                case "mas": it.Cantidad++; break;
                case "menos": it.Cantidad = Math.Max(1, it.Cantidad - 1); break;
                case "eliminar": items.Remove(it); break;
            }

            Session["Carrito"] = items;
            CargarCarrito();
        }

        protected void btnAplicarCupon_Click(object sender, EventArgs e)
        {
            string cupon = txtCupon.Text.Trim().ToUpperInvariant();
            decimal descuento = 0m;

            if (cupon == "PATITOLINUX")
            {
                // 10% sobre subtotal
                var items = ObtenerCarrito();
                var subtotal = items.Sum(i => i.Precio * i.Cantidad);
                descuento = Math.Round(subtotal * 0.10m, 2);
                lblCuponMsg.CssClass = "small d-block mt-1 text-success";
                lblCuponMsg.Text = "Cupón aplicado: 10% de descuento.";
            }
            else if (!string.IsNullOrEmpty(cupon))
            {
                lblCuponMsg.CssClass = "small d-block mt-1 text-danger";
                lblCuponMsg.Text = "Cupón inválido.";
            }
            Session["CarritoDescuento"] = descuento;
            CargarCarrito();
        }

        protected void btnPagar_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Checkout.aspx");
        }
    }

    [Serializable]
    public class CartItem
    {
        public int Id { get; set; }
        public string Sku { get; set; }
        public string Nombre { get; set; }
        public string ImagenUrl { get; set; } = "/img/placeholder.png";
        public decimal Precio { get; set; }
        public int Cantidad { get; set; }
    }
}