using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class DetalleProducto : System.Web.UI.Page
    {
        private int ProductoId
        {
            get { return ViewState["ProductoId"] != null ? (int)ViewState["ProductoId"] : 0; }
            set { ViewState["ProductoId"] = value; }
        }

        private int StockDisponible
        {
            get { return ViewState["Stock"] != null ? (int)ViewState["Stock"] : 0; }
            set { ViewState["Stock"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string idParam = Request.QueryString["id"];

                if (string.IsNullOrEmpty(idParam) || !int.TryParse(idParam, out int productoId))
                {
                    // Si no hay ID válido, redirigir a productos
                    Response.Redirect("Productos.aspx");
                    return;
                }

                ProductoId = productoId;
                CargarDetalleProducto(productoId);
            }
        }

        private void CargarDetalleProducto(int productoId)
        {
            // Obtener el producto de la base de datos simulada
            DataTable dtProductos = ObtenerProductosSimulados();
            DataRow producto = dtProductos.AsEnumerable()
                .FirstOrDefault(p => p.Field<int>("ProductoId") == productoId);

            if (producto == null)
            {
                // Producto no encontrado
                Response.Redirect("Productos.aspx");
                return;
            }

            string nombreProducto = producto["Nombre"].ToString();

            // Aquí se establece el título de la página para que varía de acuerdo al producto seleccionado:
            Page.Title = nombreProducto + " - Kawki";

            // Llenar los controles con la información del producto
            lblBreadcrumb.Text = producto["Nombre"].ToString();
            lblNombreProducto.Text = producto["Nombre"].ToString();
            lblDescripcion.Text = producto["Descripcion"].ToString();
            lblPrecio.Text = string.Format("{0:N2}", producto["Precio"]);

            string categoria = producto["Categoria"].ToString();
            lblCategoriaTag.Text = categoria.ToUpper();
            lblCategoriaBadge.Text = categoria.ToUpper();

            imgProductoPrincipal.ImageUrl = producto["ImagenUrl"].ToString();
            imgProductoPrincipal.AlternateText = producto["Nombre"].ToString();

            // Stock
            int stock = Convert.ToInt32(producto["Stock"]);
            StockDisponible = stock;
            lblStock.Text = $"{stock} disponibles";

            if (stock <= 5)
            {
                lblStock.CssClass = "stock-disponible stock-bajo";
                lblStock.Text = $"¡Solo quedan {stock}!";
            }

            // Cargar tallas disponibles
            string tallasDisponibles = producto["TallasDisponibles"].ToString();
            CargarTallas(tallasDisponibles);
        }

        protected void btnAumentar_Click(object sender, EventArgs e)
        {
            int cantidadActual = int.Parse(lblCantidad.Text);

            if (cantidadActual < StockDisponible)
            {
                cantidadActual++;
                lblCantidad.Text = cantidadActual.ToString();
                OcultarMensajeCantidad();
            }
            else
            {
                MostrarMensajeCantidad("No hay más stock disponible", "error");
            }

            // Actualizar solo el UpdatePanel
            upCantidad.Update();
        }

        protected void btnDisminuir_Click(object sender, EventArgs e)
        {
            int cantidadActual = int.Parse(lblCantidad.Text);

            if (cantidadActual > 1)
            {
                cantidadActual--;
                lblCantidad.Text = cantidadActual.ToString();
                OcultarMensajeCantidad();
            }

            // Actualizar solo el UpdatePanel
            upCantidad.Update();
        }

        private void MostrarMensajeCantidad(string mensaje, string tipo)
        {
            pnlMensajeCantidad.Visible = true;
            pnlMensajeCantidad.CssClass = $"mensaje-validacion {tipo}";
            lblMensajeCantidad.Text = mensaje;
        }

        private void OcultarMensajeCantidad()
        {
            pnlMensajeCantidad.Visible = false;
        }

        protected void btnAgregarCarrito_Click(object sender, EventArgs e)
        {
            if (!ValidarSeleccion())
            {
                return;
            }

            // Obtener la talla seleccionada
            string tallaSeleccionada = ObtenerTallaSeleccionada();
            int cantidad = int.Parse(lblCantidad.Text);

            // Aquí iría la lógica para agregar al carrito

            MostrarMensaje($"¡Producto agregado al carrito! Talla: {tallaSeleccionada}, Cantidad: {cantidad}", "exito");

        }

        protected void btnComprarAhora_Click(object sender, EventArgs e)
        {
            if (!ValidarSeleccion())
            {
                return;
            }

            // Obtener la talla seleccionada
            string tallaSeleccionada = ObtenerTallaSeleccionada();
            int cantidad = int.Parse(lblCantidad.Text);

            // Agregar al carrito
            // ... (lógica de agregar al carrito)

            // Redirigir directamente al checkout
            Response.Redirect("Checkout.aspx");
        }

        private bool ValidarSeleccion()
        {
            // Validar que se haya seleccionado una talla
            string tallaSeleccionada = ObtenerTallaSeleccionada();

            if (string.IsNullOrEmpty(tallaSeleccionada))
            {
                MostrarMensaje("Por favor, selecciona una talla antes de continuar", "error");
                return false;
            }

            // Validar cantidad
            int cantidad = int.Parse(lblCantidad.Text);
            if (cantidad <= 0 || cantidad > StockDisponible)
            {
                MostrarMensaje("La cantidad seleccionada no es válida", "error");
                return false;
            }

            return true;
        }

        private void CargarTallas(string tallasStr)
        {
            if (string.IsNullOrEmpty(tallasStr))
            {
                return;
            }

            string[] tallas = tallasStr.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                       .Select(t => t.Trim())
                                       .OrderBy(t => int.Parse(t))
                                       .ToArray();

            rblTallas.Items.Clear();
            foreach (string talla in tallas)
            {
                rblTallas.Items.Add(new ListItem(talla, talla));
            }
        }

        private string ObtenerTallaSeleccionada()
        {
            if (rblTallas.SelectedItem != null)
            {
                return rblTallas.SelectedValue;
            }
            return null;
        }

        private void MostrarMensaje(string mensaje, string tipo)
        {
            pnlMensaje.Visible = true;
            pnlMensaje.CssClass = $"mensaje-validacion {tipo}";
            lblMensaje.Text = mensaje;
        }

        private void OcultarMensaje()
        {
            pnlMensaje.Visible = false;
        }

        // Mismo método que en Productos.aspx
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
            dt.Columns.Add("Stock", typeof(int));
            dt.Columns.Add("ImagenUrl", typeof(string));

            // Productos tipo Oxford
            dt.Rows.Add(1, "Oxford Clásico Beige", "Zapato oxford de cuero genuino con acabado premium, ideal para ocasiones formales y uso diario.", 150.90m, "35, 36, 39", "oxford", "clasico", "beige", 15, "~/Images/OxfordClasicoBeige.jpg");
            dt.Rows.Add(2, "Oxford Premium Negro", "Zapato oxford de diseño moderno y sofisticado, perfecto para eventos importantes.", 250.90m, "35, 36, 37, 38, 39", "oxford", "charol", "negro", 8, "~/Images/OxfordPremiumNegro.jpg");
            dt.Rows.Add(3, "Oxford Bicolor Café", "Zapato oxford con elegante combinación de tonos café, estilo vintage refinado.", 160.90m, "36, 37, 38, 39", "oxford", "combinado", "marron", 6, "~/Images/OxfordBicolorCafe.jpg");

            // Productos tipo Derby
            dt.Rows.Add(4, "Derby Elegante Marrón", "Derby de cuero con diseño tejido elegante, versátil para cualquier ocasión.", 215.90m, "35, 36, 37, 38, 39", "derby", "clasico", "marron", 10, "~/Images/DerbyClasicoMarron.jpg");
            dt.Rows.Add(5, "Derby Charol Crema", "Derby charol con suela gruesa y diseño moderno, máxima comodidad.", 210.90m, "35, 36, 37, 38, 39", "derby", "charol", "crema", 12, "~/Images/DerbyClasicoCrema.jpg");
            dt.Rows.Add(6, "Derby Clasico Negro", "Derby clasico con suela de goma antideslizante, ideal para caminar.", 169.90m, "36, 37, 38, 39", "derby", "clasico", "negro", 4, "~/Images/DerbyClasicoNegro.jpg");

            return dt;
        }

        protected void rptTallas_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                RadioButton rb = (RadioButton)e.Item.FindControl("rbTalla");
                if (rb != null)
                {
                    string talla = e.Item.DataItem.ToString();

                    // Asignar ID único para que el label funcione
                    rb.ID = "rbTalla_" + e.Item.ItemIndex;

                    // Agregar atributo data-talla
                    rb.Attributes.Add("data-talla", talla);

                    // El InputAttributes permite que el CSS funcione correctamente
                    rb.InputAttributes.Add("id", "rbTalla_" + e.Item.ItemIndex);
                }
            }
        }
    }
}