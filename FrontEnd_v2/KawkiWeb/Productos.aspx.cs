using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Productos : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarProductos();
            }
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarProductos();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // Limpiar todos los filtros
            ddlCategoria.SelectedIndex = 0;
            ddlTalla.SelectedIndex = 0;
            ddlEstilo.SelectedIndex = 0;
            ddlColor.SelectedIndex = 0;
            txtBuscar.Text = "";

            // Recargar productos sin filtros
            CargarProductos();
        }

        // Evento para redirigir al detalle del producto
        protected void lnkProducto_Click(object sender, EventArgs e)
        {
            LinkButton lnk = (LinkButton)sender;
            string productoId = lnk.CommandArgument;

            // Redirigir a la página de detalle con el ID del producto
            Response.Redirect("DetalleProducto.aspx?id=" + productoId);
        }

        // Método para tallas disponibles
        protected string MostrarTallas(string tallas)
        {
            if (string.IsNullOrEmpty(tallas))
                return "";

            string[] tallasArray = tallas.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                                         .Select(t => t.Trim())
                                         .OrderBy(t => int.Parse(t))
                                         .ToArray();

            if (tallasArray.Length == 0)
                return "";

            // Si tiene todas las tallas de 35 a 39
            if (tallasArray.Length == 5 &&
                tallasArray[0] == "35" &&
                tallasArray[4] == "39")
            {
                return "<span class='producto-talla-rango'>Todas las tallas (35-39)</span>";
            }

            // Si son tallas consecutivas, mostrar como rango
            bool sonConsecutivas = true;
            for (int i = 0; i < tallasArray.Length - 1; i++)
            {
                if (int.Parse(tallasArray[i + 1]) - int.Parse(tallasArray[i]) != 1)
                {
                    sonConsecutivas = false;
                    break;
                }
            }

            if (sonConsecutivas && tallasArray.Length > 2)
            {
                return $"<span class='producto-talla-rango'>{tallasArray[0]} - {tallasArray[tallasArray.Length - 1]}</span>";
            }

            // Si no son consecutivas, mostrar individualmente
            string html = "";
            foreach (string talla in tallasArray)
            {
                html += $"<span class='producto-talla'>{talla}</span>";
            }

            return html;
        }

        private void CargarProductos()
        {
            //datos filtros:
            string categoria = ddlCategoria.SelectedValue;
            string estilo = ddlEstilo.SelectedValue;
            string color = ddlColor.SelectedValue;
            string talla = ddlTalla.SelectedValue;
            string busqueda = txtBuscar.Text.Trim();

            DataTable dtProductos = ObtenerProductosSimulados();
            DataView dv = dtProductos.DefaultView;
            List<string> filtros = new List<string>();

            if (!string.IsNullOrEmpty(categoria))
            {
                filtros.Add($"Categoria = '{categoria}'");
            }

            if (!string.IsNullOrEmpty(estilo))
            {
                filtros.Add($"Estilo = '{estilo}'");
            }

            if (!string.IsNullOrEmpty(color))
            {
                filtros.Add($"Color = '{color}'");
            }

            if (!string.IsNullOrEmpty(busqueda))
            {
                filtros.Add($"Nombre LIKE '%{busqueda}%'");
            }

            if (filtros.Count > 0)
            {
                dv.RowFilter = string.Join(" AND ", filtros);
            }

            // Filtrar por talla si se seleccionó una
            if (!string.IsNullOrEmpty(talla))
            {
                DataTable dtFiltrado = dv.ToTable();
                DataTable dtResultado = dtFiltrado.Clone();

                foreach (DataRow row in dtFiltrado.Rows)
                {
                    string tallasDisponibles = row["TallasDisponibles"].ToString();
                    if (tallasDisponibles.Contains(talla))
                    {
                        dtResultado.ImportRow(row);
                    }
                }

                dv = dtResultado.DefaultView;
            }

            if (dv.Count > 0)
            {
                rptProductos.DataSource = dv;
                rptProductos.DataBind();
                lblResultados.Text = $"{dv.Count} producto(s) encontrado(s)";
                pnlSinProductos.Visible = false;
            }
            else
            {
                rptProductos.DataSource = null;
                rptProductos.DataBind();
                lblResultados.Text = "0 productos encontrados";
                pnlSinProductos.Visible = true;
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
    }
}