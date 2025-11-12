using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSProductos;

namespace KawkiWeb
{
    public partial class Productos : System.Web.UI.Page
    {
        private ProductosBO productosBO;
        private ProductosVariantesBO variantesBO;

        public Productos()
        {
            this.productosBO = new ProductosBO();
            this.variantesBO = new ProductosVariantesBO();
        }

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
            try { 
                //datos filtros:
                string categoria = ddlCategoria.SelectedValue;
                string estilo = ddlEstilo.SelectedValue;
                string color = ddlColor.SelectedValue;
                string talla = ddlTalla.SelectedValue;
                string busqueda = txtBuscar.Text.Trim();

                // Obtener productos desde el Web Service
                IList<productosDTO> productos = null;

                if (!string.IsNullOrEmpty(categoria))
                {
                    // Necesitas mapear el nombre a ID (esto depende de tu BD)
                    int categoriaId = ObtenerCategoriaIdPorNombre(categoria);
                    if (categoriaId > 0)
                    {
                        productos = productosBO.ListarPorCategoria(categoriaId);
                    }
                }

                if (!string.IsNullOrEmpty(estilo))
                {
                    int estiloId = ObtenerEstiloIdPorNombre(estilo);
                    if (estiloId > 0)
                    {
                        productos = productosBO.ListarPorEstilo(estiloId);
                    }
                }

                if (!string.IsNullOrEmpty(color))
                {
                    int colorId = ObtenerColorIdPorNombre(color);
                    if (colorId > 0)
                    {
                        // Obtener variantes por color
                        var variantesPorColor = variantesBO.ListarPorColor(colorId);

                        // Filtrar productos que tengan esas variantes
                        var productosIdsConColor = variantesPorColor
                            .Select(v => v.producto_id)
                            .Distinct()
                            .ToList();

                        productos = productos
                            .Where(p => productosIdsConColor.Contains(p.producto_id))
                            .ToList();
                    }
                }

                if (!string.IsNullOrEmpty(busqueda))
                {
                    productos = productos
                            .Where(p => p.descripcion.ToLower().Contains(busqueda.ToLower()))
                            .ToList();
                }
                // Filtrar por talla si se seleccionó una
                if (!string.IsNullOrEmpty(talla))
                {
                    int tallaId = ObtenerTallaIdPorNumero(talla);
                    if (tallaId > 0)
                    {
                        // Obtener variantes por talla
                        var variantesPorTalla = variantesBO.ListarPorTalla(tallaId);

                        // Filtrar productos que tengan esas variantes
                        var productosIdsConTalla = variantesPorTalla
                            .Select(v => v.producto_id)
                            .Distinct()
                            .ToList();

                        productos = productos
                            .Where(p => productosIdsConTalla.Contains(p.producto_id))
                            .ToList();
                    }
                }

                // 5. Convertir a DataTable y mostrar
                if (productos!= null && productos.Count > 0)
                {
                    DataTable dtProductos = ConvertirProductosADataTable(productos);

                    rptProductos.DataSource = dtProductos;
                    rptProductos.DataBind();
                    lblResultados.Text = $"{productos.Count} producto(s) encontrado(s)";
                    pnlSinProductos.Visible = false;
                }
                else
                {
                    MostrarSinProductos();
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al cargar productos: {ex.Message}");
                MostrarSinProductos();
            }
        }

        private DataTable ConvertirProductosADataTable(IList<productosDTO> productos)
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

            foreach (var producto in productos)
            {
                if (producto.variantes == null || producto.variantes.Length == 0)
                    continue;

                // Obtener tallas únicas y sus stocks
                var tallasDict = new Dictionary<string, int>();

                foreach (var variante in producto.variantes)
                {
                    if (variante.talla != null)
                    {
                        string talla = variante.talla.numero.ToString();

                        if (!tallasDict.ContainsKey(talla))
                        {
                            tallasDict[talla] = variante.stock;
                        }
                        else
                        {
                            tallasDict[talla] += variante.stock;
                        }
                    }
                }

                // Ordenar tallas
                var tallasOrdenadas = tallasDict.OrderBy(t => int.Parse(t.Key)).ToList();

                string tallas = string.Join(", ", tallasOrdenadas.Select(t => t.Key));
                string stocks = string.Join(",", tallasOrdenadas.Select(t => t.Value));

                // Obtener color principal (primera variante)
                string color = producto.variantes[0]?.color?.nombre ?? "Sin color";

                // Obtener imagen principal (primera variante)
                string imagen = producto.variantes[0]?.url_imagen ?? "~/Images/no-image.jpg";

                dt.Rows.Add(
                    producto.producto_id,
                    producto.descripcion,
                    producto.descripcion, 
                    producto.precio_venta,
                    tallas,
                    producto.categoria?.nombre ?? "Sin categoría",
                    producto.estilo?.nombre ?? "Sin estilo",
                    color,
                    stocks,
                    imagen
                );
            }

            return dt;
        }

        private void MostrarSinProductos()
        {
            rptProductos.DataSource = null;
            rptProductos.DataBind();
            lblResultados.Text = "0 productos encontrados";
            pnlSinProductos.Visible = true;
        }

        // Métodos auxiliares para mapear nombres a IDs
        private int ObtenerCategoriaIdPorNombre(string nombre)
        {
            switch (nombre.ToLower())
            {
                case "Derby": return 1;
                case "Oxford": return 2;
                default: return 0;
            }
        }

        private int ObtenerEstiloIdPorNombre(string nombre)
        {
            switch (nombre.ToLower())
            {
                case "Charol": return 1;
                case "Clasico": return 2;
                case "Combinados": return 3;
                case "Metalizados": return 4;
                default: return 0;
            }
        }

        private int ObtenerColorIdPorNombre(string nombre)
        {
            switch (nombre.ToLower())
            {
                case "Blanco": return 1;
                case "Camel": return 2;
                case "Marron": return 3;
                case "Piel": return 4;
                case "Celeste": return 5;
                case "Crema": return 6;
                case "Beige": return 7;
                case "Negro": return 8;
                case "Amarillo": return 9;
                case "Plata": return 10;
                case "Azul": return 11;
                case "Rosado": return 12;
                case "Gris": return 13;
                case "Rojo": return 14;
                case "Turquesa": return 15;
                case "Acero": return 16;
                case "Verde": return 17;
                default: return 0;
            }
        }

        private int ObtenerTallaIdPorNumero(string numero)
        {
            switch (numero)
            {
                case "35": return 1;
                case "36": return 2;
                case "37": return 3;
                case "38": return 4;
                case "39": return 5;
                default: return 0;
            }
        }

        // Método para mostrar alertas de stock bajo por tallas
        protected string MostrarAlertaStockBajo(string tallasDisponibles, string stockString)
        {
            if (string.IsNullOrEmpty(tallasDisponibles) || string.IsNullOrEmpty(stockString))
                return "";

            var tallas = tallasDisponibles.Split(',').Select(t => t.Trim()).ToArray();
            var stocks = stockString.Split(',').Select(s => s.Trim()).ToArray();

            if (tallas.Length != stocks.Length)
                return "";

            List<string> tallasAgotadas = new List<string>();
            List<string> tallasStockBajo = new List<string>();

            for (int i = 0; i < tallas.Length; i++)
            {
                if (int.TryParse(stocks[i], out int stock))
                {
                    if (stock == 0)
                    {
                        tallasAgotadas.Add(tallas[i]);
                    }
                    else if (stock <= 5) // Stock bajo si tiene 5 o menos unidades
                    {
                        tallasStockBajo.Add($"{tallas[i]} ({stock})");
                    }
                }
            }

            string html = "";

            // Mostrar tallas agotadas
            if (tallasAgotadas.Count > 0)
            {
                html += $@"<div class='producto-stock-alerta producto-stock-alerta-agotado'>
                            <i class='fas fa-times-circle'></i>
                            <strong>Agotado:</strong> Talla(s) {string.Join(", ", tallasAgotadas)}
                         </div>";
            }

            // Mostrar tallas con stock bajo
            if (tallasStockBajo.Count > 0)
            {
                html += $@"<div class='producto-stock-alerta'>
                            <i class='fas fa-exclamation-triangle'></i>
                            <strong>Stock bajo:</strong> Talla(s) {string.Join(", ", tallasStockBajo)}
                         </div>";
            }

            return html;
        }
    }
}