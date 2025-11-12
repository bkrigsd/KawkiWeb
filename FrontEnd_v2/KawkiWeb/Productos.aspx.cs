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
            try
            {
                // Obtener TODOS los productos inicialmente
                IList<productosDTO> productos = productosBO.ListarTodos();

                System.Diagnostics.Debug.WriteLine("========== INICIO DEBUG ==========");
                System.Diagnostics.Debug.WriteLine($"Productos iniciales: {productos?.Count ?? 0}");

                if (productos == null || productos.Count == 0)
                {
                    MostrarSinProductos();
                    return;
                }

                // Datos de filtros
                string categoria = ddlCategoria.SelectedValue;
                string estilo = ddlEstilo.SelectedValue;
                string color = ddlColor.SelectedValue;
                string talla = ddlTalla.SelectedValue;
                string busqueda = txtBuscar.Text.Trim();

                System.Diagnostics.Debug.WriteLine($"Filtros seleccionados:");
                System.Diagnostics.Debug.WriteLine($"  - Categoria: '{categoria}'");
                System.Diagnostics.Debug.WriteLine($"  - Estilo: '{estilo}'");
                System.Diagnostics.Debug.WriteLine($"  - Color: '{color}'");
                System.Diagnostics.Debug.WriteLine($"  - Talla: '{talla}'");
                System.Diagnostics.Debug.WriteLine($"  - Busqueda: '{busqueda}'");

                // Crear una lista para filtrar
                List<productosDTO> productosFiltrados = productos.ToList();

                // FILTRO 1: Categoría
                if (!string.IsNullOrEmpty(categoria))
                {
                    int categoriaId = ObtenerCategoriaIdPorNombre(categoria);
                    System.Diagnostics.Debug.WriteLine($"CategoriaId obtenido: {categoriaId}");

                    if (categoriaId > 0)
                    {
                        productosFiltrados = productosFiltrados
                            .Where(p => p.categoria != null && p.categoria.categoria_id == categoriaId)
                            .ToList();
                    }
                    System.Diagnostics.Debug.WriteLine($"Después filtro categoría: {productosFiltrados.Count}");
                }

                // FILTRO 2: Estilo
                if (!string.IsNullOrEmpty(estilo))
                {
                    int estiloId = ObtenerEstiloIdPorNombre(estilo);
                    System.Diagnostics.Debug.WriteLine($"EstiloId obtenido: {estiloId}");

                    if (estiloId > 0)
                    {
                        productosFiltrados = productosFiltrados
                            .Where(p => p.estilo != null && p.estilo.estilo_id == estiloId)
                            .ToList();
                    }
                    System.Diagnostics.Debug.WriteLine($"Después filtro estilo: {productosFiltrados.Count}");
                }

                // FILTRO 3: Búsqueda por texto
                if (!string.IsNullOrEmpty(busqueda))
                {
                    productosFiltrados = productosFiltrados
                        .Where(p => p.descripcion != null &&
                                   p.descripcion.ToLower().Contains(busqueda.ToLower()))
                        .ToList();
                    System.Diagnostics.Debug.WriteLine($"Después filtro búsqueda: {productosFiltrados.Count}");
                }

                // FILTRO 4: Color
                if (!string.IsNullOrEmpty(color))
                {
                    int colorId = ObtenerColorIdPorNombre(color);
                    System.Diagnostics.Debug.WriteLine($"ColorId obtenido: {colorId}");

                    if (colorId > 0)
                    {
                        productosFiltrados = productosFiltrados
                            .Where(p => p.variantes != null &&
                                       p.variantes.Any(v => v.color != null && v.color.color_id == colorId))
                            .ToList();
                    }
                    System.Diagnostics.Debug.WriteLine($"Después filtro color: {productosFiltrados.Count}");
                }

                // FILTRO 5: Talla
                if (!string.IsNullOrEmpty(talla))
                {
                    int tallaId = ObtenerTallaIdPorNumero(talla);
                    System.Diagnostics.Debug.WriteLine($"TallaId obtenido: {tallaId}");

                    if (tallaId > 0)
                    {
                        productosFiltrados = productosFiltrados
                            .Where(p => p.variantes != null &&
                                       p.variantes.Any(v => v.talla != null && v.talla.talla_id == tallaId))
                            .ToList();
                    }
                    System.Diagnostics.Debug.WriteLine($"Después filtro talla: {productosFiltrados.Count}");
                }

                System.Diagnostics.Debug.WriteLine($"Productos filtrados FINAL: {productosFiltrados.Count}");

                // Ver info del primer producto si existe
                if (productosFiltrados.Count > 0)
                {
                    var p = productosFiltrados[0];
                    System.Diagnostics.Debug.WriteLine($"Primer producto:");
                    System.Diagnostics.Debug.WriteLine($"  - ID: {p.producto_id}");
                    System.Diagnostics.Debug.WriteLine($"  - Descripcion: {p.descripcion}");
                    System.Diagnostics.Debug.WriteLine($"  - Categoria: {p.categoria?.nombre} (ID: {p.categoria?.categoria_id})");
                    System.Diagnostics.Debug.WriteLine($"  - Estilo: {p.estilo?.nombre} (ID: {p.estilo?.estilo_id})");
                    System.Diagnostics.Debug.WriteLine($"  - Variantes: {p.variantes?.Length ?? 0}");
                }

                // Mostrar resultados
                if (productosFiltrados.Count > 0)
                {
                    DataTable dtProductos = ConvertirProductosADataTable(productosFiltrados);

                    System.Diagnostics.Debug.WriteLine($"Filas en DataTable: {dtProductos.Rows.Count}");

                    if (dtProductos.Rows.Count > 0)
                    {
                        rptProductos.DataSource = dtProductos;
                        rptProductos.DataBind();
                        lblResultados.Text = $"{dtProductos.Rows.Count} producto(s) encontrado(s)";
                        pnlSinProductos.Visible = false;
                    }
                    else
                    {
                        System.Diagnostics.Debug.WriteLine("ERROR: DataTable vacío pero hay productos filtrados");
                        MostrarSinProductos();
                    }
                }
                else
                {
                    System.Diagnostics.Debug.WriteLine("No hay productos después de filtros");
                    MostrarSinProductos();
                }

                System.Diagnostics.Debug.WriteLine("========== FIN DEBUG ==========");
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"EXCEPCION: {ex.Message}");
                System.Diagnostics.Debug.WriteLine($"StackTrace: {ex.StackTrace}");
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

                // agrupar variantes por color
                var variantesPorColor = producto.variantes
                    .GroupBy(v => v.color?.nombre ?? "Sin color");

                foreach (var grupoColor in variantesPorColor)
                {
                    string colorNombre = grupoColor.Key;
                    string imagen = grupoColor.FirstOrDefault()?.url_imagen ?? "~/Images/no-image.jpg";

                    // obtener tallas y stocks de ese color
                    var tallasDict = new Dictionary<string, int>();
                    foreach (var variante in grupoColor)
                    {
                        if (variante.talla != null)
                        {
                            string talla = variante.talla.numero.ToString();
                            tallasDict[talla] = variante.stock;
                        }
                    }

                    string tallas = string.Join(", ", tallasDict.Keys.OrderBy(t => int.Parse(t)));
                    string stocks = string.Join(",", tallasDict.Values);

                    string categoriaNombre = producto.categoria.nombre;
                    string estiloNombre = producto.estilo.nombre;

                    string nombreProducto = $"{categoriaNombre} {estiloNombre} {colorNombre}".Trim();
                    nombreProducto = char.ToUpper(nombreProducto[0]) + nombreProducto.Substring(1);

                    dt.Rows.Add(
                        producto.producto_id,
                        nombreProducto,
                        producto.descripcion,
                        producto.precio_venta,
                        tallas,
                        categoriaNombre,
                        estiloNombre,
                        colorNombre,
                        stocks,
                        imagen
                    );
                }
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
            switch (nombre)
            {
                case "Derby": return 1;
                case "Oxford": return 2;
                default: return 0;
            }
        }

        private int ObtenerEstiloIdPorNombre(string nombre)
        {
            switch (nombre)
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
            switch (nombre)
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