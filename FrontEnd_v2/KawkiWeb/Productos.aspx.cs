using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSProductos;
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO;
using coloresDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO;
using tallasDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO;

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
                //// Obtener TODOS los productos inicialmente
                //IList<productosDTO> productos = productosBO.ListarTodos();

                //// Traer todas las variantes desde la tabla productos_variantes
                //var todasLasVariantes = variantesBO.ListarTodos();

                //if (productos == null || productos.Count == 0)
                //{
                //    MostrarSinProductos();
                //    return;
                //}

                //// 2. Para cada producto, obtener sus variantes usando el método específico
                //var productosConVariantes = new Dictionary<productosDTO, List<productosVariantesDTO>>();

                //foreach (var producto in productos)
                //{
                //    // Llamada eficiente: solo las variantes de ESTE producto
                //    var variantes = variantesBO.ListarPorProducto(producto.producto_id);

                //    if (variantes != null && variantes.Count > 0)
                //    {
                //        productosConVariantes[producto] = variantes.ToList();
                //    }
                //}

                //DataTable dtProductos = ConvertirProductosADataTable(productosConVariantes);

                //if (dtProductos.Rows.Count > 0)
                //{
                //    rptProductos.DataSource = dtProductos;
                //    rptProductos.DataBind();
                //    lblResultados.Text = $"{dtProductos.Rows.Count} producto(s) encontrado(s)";
                //    pnlSinProductos.Visible = false;
                //}
                //else
                //{
                //    MostrarSinProductos();
                //}

                // Obtener TODOS los productos
                IList<productosDTO> productos = productosBO.ListarTodos();

                if (productos == null || productos.Count == 0)
                {
                    MostrarSinProductos();
                    return;
                }

                // Obtener valores de los filtros
                string categoriaValue = ddlCategoria.SelectedValue;
                string estiloValue = ddlEstilo.SelectedValue;
                string colorValue = ddlColor.SelectedValue;
                string tallaValue = ddlTalla.SelectedValue;
                string busqueda = txtBuscar.Text.Trim();

                // Verificar si hay algún filtro activo
                bool hayFiltros = !string.IsNullOrEmpty(categoriaValue) ||
                                  !string.IsNullOrEmpty(estiloValue) ||
                                  !string.IsNullOrEmpty(colorValue) ||
                                  !string.IsNullOrEmpty(tallaValue) ||
                                  !string.IsNullOrEmpty(busqueda);

                System.Diagnostics.Debug.WriteLine($"¿Hay filtros activos? {hayFiltros}");

                var productosConVariantes = new Dictionary<productosDTO, List<productosVariantesDTO>>();

                foreach (var producto in productos)
                {
                    // Si hay filtros, aplicarlos
                    if (hayFiltros)
                    {
                        // FILTRO 1: Categoría
                        if (!string.IsNullOrEmpty(categoriaValue))
                        {
                            if (producto.categoria == null ||
                                producto.categoria.nombre == null ||
                                !producto.categoria.nombre.Equals(categoriaValue, StringComparison.OrdinalIgnoreCase))
                            {
                                continue; // Saltar este producto
                            }
                        }

                        // FILTRO 2: Estilo
                        if (!string.IsNullOrEmpty(estiloValue))
                        {
                            if (producto.estilo == null ||
                                producto.estilo.nombre == null ||
                                !producto.estilo.nombre.Equals(estiloValue, StringComparison.OrdinalIgnoreCase))
                            {
                                continue; // Saltar este producto
                            }
                        }
                    }

                    // Obtener variantes del producto
                    var variantes = variantesBO.ListarPorProducto(producto.producto_id);

                    if (variantes == null || variantes.Count == 0)
                    {
                        continue; // Producto sin variantes, saltar
                    }

                    // Si hay filtros, aplicar filtros a las variantes
                    if (hayFiltros)
                    {
                        List<productosVariantesDTO> variantesFiltradas = variantes.ToList();

                        // FILTRO 3: Color
                        if (!string.IsNullOrEmpty(colorValue))
                        {
                            variantesFiltradas = variantesFiltradas
                                .Where(v => v.color != null &&
                                           v.color.nombre != null &&
                                           v.color.nombre.Equals(colorValue, StringComparison.OrdinalIgnoreCase))
                                .ToList();
                        }

                        // FILTRO 4: Talla
                        if (!string.IsNullOrEmpty(tallaValue))
                        {
                            int numeroTalla;
                            if (int.TryParse(tallaValue, out numeroTalla))
                            {
                                variantesFiltradas = variantesFiltradas
                                    .Where(v => v.talla != null && v.talla.numero == numeroTalla)
                                    .ToList();
                            }
                        }

                        // FILTRO 5: Búsqueda por texto (busca en nombre completo: categoría + estilo + color)
                        if (!string.IsNullOrEmpty(busqueda))
                        {
                            variantesFiltradas = variantesFiltradas
                                .Where(v =>
                                {
                                    string nombreCompleto = $"{producto.categoria?.nombre} {producto.estilo?.nombre} {v.color?.nombre}";
                                    return nombreCompleto.ToLower().Contains(busqueda.ToLower());
                                })
                                .ToList();
                        }

                        // Si después de filtrar variantes aún quedan, agregar
                        if (variantesFiltradas.Count > 0)
                        {
                            productosConVariantes[producto] = variantesFiltradas;
                        }
                    }
                    else
                    {
                        // Sin filtros, agregar todas las variantes
                        productosConVariantes[producto] = variantes.ToList();
                    }
                }

                System.Diagnostics.Debug.WriteLine($"Productos con variantes: {productosConVariantes.Count}");

                // Convertir a DataTable
                DataTable dtProductos = ConvertirProductosADataTable(productosConVariantes);

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
                    MostrarSinProductos();
                }

            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"EXCEPCION: {ex.Message}");
                System.Diagnostics.Debug.WriteLine($"StackTrace: {ex.StackTrace}");
                MostrarSinProductos();
            }
        } 

        private DataTable ConvertirProductosADataTable(Dictionary<productosDTO, List<productosVariantesDTO>> productosConVariantes)
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

            foreach (var kvp in productosConVariantes)
            {
                var producto = kvp.Key;
                var variantes = kvp.Value;

                // Validar que el producto tenga los datos básicos
                if (producto.categoria == null || producto.estilo == null)
                {
                    System.Diagnostics.Debug.WriteLine($"Producto {producto.producto_id} sin categoría o estilo");
                    continue;
                }

                // Agrupar variantes por color
                var variantesPorColor = variantes
                    .Where(v => v.color != null) // Solo variantes con color
                    .GroupBy(v => v.color.nombre);

                foreach (var grupoColor in variantesPorColor)
                {
                    string colorNombre = grupoColor.Key;

                    // Obtener la primera imagen del grupo de color
                    string imagen = grupoColor.FirstOrDefault()?.url_imagen;
                    if (string.IsNullOrEmpty(imagen))
                    {
                        imagen = "~/Images/no-image.jpg";
                    }

                    // Obtener tallas y stocks de ese color
                    var tallasDict = new Dictionary<int, int>(); // talla_numero => stock

                    foreach (var variante in grupoColor)
                    {
                        if (variante.talla != null)
                        {
                            int numeroTalla = variante.talla.numero; // Es un INT
                            tallasDict[numeroTalla] = variante.stock;
                        }
                    }

                    // Crear el string de tallas ordenadas
                    string tallas = string.Join(", ",
                        tallasDict.Keys.OrderBy(t => t).Select(t => t.ToString()));

                    // Crear el string de stocks en el mismo orden
                    string stocks = string.Join(",",
                        tallasDict.OrderBy(kvp2 => kvp2.Key).Select(kvp2 => kvp2.Value));

                    // Obtener nombres
                    string categoriaNombre = producto.categoria.nombre ?? "";
                    string estiloNombre = producto.estilo.nombre ?? "";

                    // Construir el nombre completo: "Oxford Clásico Beige"
                    string nombreProducto = $"{categoriaNombre} {estiloNombre} {colorNombre}".Trim();

                    // Capitalizar primera letra
                    if (!string.IsNullOrEmpty(nombreProducto))
                    {
                        nombreProducto = char.ToUpper(nombreProducto[0]) + nombreProducto.Substring(1);
                    }

                    // Agregar fila al DataTable
                    dt.Rows.Add(
                        producto.producto_id,
                        nombreProducto,
                        producto.descripcion ?? "",
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