using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Serialization;
using System.Web.UI;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSMovimientosInventario;


namespace KawkiWeb
{
    public partial class ReporteStock : Page
    {
        // JSON que usará Chart.js
        public string JsonSinStock { get; set; } = "{}";
        public string JsonStockBajo { get; set; } = "{}";
        public string JsonStockCategoria { get; set; } = "{}";
        //public string JsonBajaRotacion { get; set; } = "{}";

        protected void Page_Load(object sender, EventArgs e)
        {
            var m = Master as KawkiWeb;
            if (m != null)
            {
                try { m.SetActive("ReporteStock"); } catch { }
            }

            if (!IsPostBack)
            {
                // Inputs VACÍOS al entrar
                txtFechaInicio.Text = string.Empty;
                txtFechaFin.Text = string.Empty;

                // Rango automático según movimientos
                DateTime fechaInicio, fechaFin;
                ObtenerRangoAutomatico(out fechaInicio, out fechaFin);

                // Generar reporte inicial
                GenerarReporte(fechaInicio, fechaFin);
            }
        }

        private void ObtenerRangoAutomatico(out DateTime fechaInicio, out DateTime fechaFin)
        {
            DateTime hoy = DateTime.Today;
            fechaInicio = hoy;
            fechaFin = hoy.AddDays(1).AddSeconds(-1);

            try
            {
                var movBO = new KawkiWebBusiness.MovimientosInventarioBO();
                var movimientos = movBO.ListarTodosMovInventario()
                                  ?? new List<KawkiWebBusiness.KawkiWebWSMovimientosInventario.movimientosInventarioDTO>();

                var fechas = movimientos
                    .Select(mv =>
                    {
                        DateTime f;
                        return DateTime.TryParse(mv.fecha_hora_mov, out f) ? f : DateTime.MinValue;
                    })
                    .Where(f => f != DateTime.MinValue)
                    .ToList();

                if (fechas.Any())
                {
                    var min = fechas.Min().Date;
                    var max = fechas.Max().Date;

                    fechaInicio = min;
                    fechaFin = max.AddDays(1).AddSeconds(-1); // fin del día
                }
            }
            catch
            {
                // Si hay error, nos quedamos con hoy (ya seteado arriba)
            }
        }

        protected void btnGenerar_Click(object sender, EventArgs e)
        {
            lblErrorFechas.Visible = false;
            lblErrorFechas.Text = string.Empty;

            // Si los campos están vacíos, usamos el rango automático
            if (string.IsNullOrWhiteSpace(txtFechaInicio.Text) ||
                string.IsNullOrWhiteSpace(txtFechaFin.Text))
            {
                DateTime fIni, fFin;
                ObtenerRangoAutomatico(out fIni, out fFin);
                GenerarReporte(fIni, fFin);
                return;
            }

            // Si hay texto, validar formato y rango
            DateTime fechaInicio, fechaFin;
            if (!DateTime.TryParse(txtFechaInicio.Text, out fechaInicio) ||
                !DateTime.TryParse(txtFechaFin.Text, out fechaFin))
            {
                lblErrorFechas.Visible = true;
                lblErrorFechas.Text = "Las fechas ingresadas no son válidas.";
                return;
            }

            if (fechaInicio > fechaFin)
            {
                lblErrorFechas.Visible = true;
                lblErrorFechas.Text = "La fecha de inicio no puede ser mayor que la fecha fin.";
                return;
            }

            // CAMBIO CRÍTICO: Asegurar que tomamos TODO el día fin
            fechaInicio = fechaInicio.Date; // 00:00:00
            fechaFin = fechaFin.Date.AddDays(1).AddTicks(-1); // 23:59:59.9999999

            GenerarReporte(fechaInicio, fechaFin);
        }

        private void GenerarReporte(DateTime fechaInicio, DateTime fechaFin)
        {
            var productosBO = new KawkiWebBusiness.ProductosBO();
            var variantesBO = new KawkiWebBusiness.ProductosVariantesBO();
            var movBO = new KawkiWebBusiness.MovimientosInventarioBO();
            var js = new JavaScriptSerializer();

            // === 1) Cargar datos base ===
            var productos = productosBO.ListarTodosProducto()
                           ?? new List<KawkiWebBusiness.KawkiWebWSProductos.productosDTO>();

            var variantesTodas = variantesBO.ListarTodos()
                               ?? new List<KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO>();

            var movimientos = movBO.ListarTodosMovInventario()
                             ?? new List<KawkiWebBusiness.KawkiWebWSMovimientosInventario.movimientosInventarioDTO>();

            // Diccionario de productos por id
            var productosPorId = productos
                .Where(p => p.producto_idSpecified)
                .ToDictionary(p => p.producto_id, p => p);

            // === 2) Guardar el stock ACTUAL de cada variante ===
            var stockActualPorVariante = variantesTodas
                .ToDictionary(v => v.prod_variante_id, v => v.stock);

            // === 3) Obtener movimientos DESPUÉS de fechaFin ===
            var movimientosDespuesDeFechaFin = movimientos
                .Select(mv =>
                {
                    DateTime f;
                    bool parsed = DateTime.TryParse(mv.fecha_hora_mov, out f);
                    return new
                    {
                        Mov = mv,
                        Fecha = parsed ? f : DateTime.MinValue,
                        Parsed = parsed
                    };
                })
                .Where(x => x.Parsed && x.Fecha > fechaFin) // Movimientos DESPUÉS de fechaFin
                .OrderByDescending(x => x.Fecha) // Del más reciente al más antiguo
                .Select(x => x.Mov)
                .ToList();

            // === 4) Calcular stock en fechaFin retrocediendo desde hoy ===
            var stockEnFechaFinPorVariante = new Dictionary<int, int>();

            // Inicializar con stock actual
            foreach (var v in variantesTodas)
            {
                stockEnFechaFinPorVariante[v.prod_variante_id] = v.stock;
            }

            // Revertir los movimientos posteriores a fechaFin
            foreach (var mv in movimientosDespuesDeFechaFin)
            {
                if (mv.prod_variante == null) continue;

                int varId = mv.prod_variante.prod_variante_id;
                int cantidad = mv.cantidad;

                string tipoNombre = mv.tipo_movimiento?.nombre?.Trim() ?? "";
                int factor = 0;

                // IMPORTANTE: Invertimos la lógica porque vamos hacia atrás
                if (tipoNombre.Equals("Ingreso", StringComparison.OrdinalIgnoreCase))
                    factor = -1; // Si hubo ingreso después, restamos para volver atrás
                else if (tipoNombre.Equals("Salida", StringComparison.OrdinalIgnoreCase))
                    factor = 1;  // Si hubo salida después, sumamos para volver atrás
                else if (tipoNombre.Equals("Ajuste", StringComparison.OrdinalIgnoreCase))
                    factor = -1; // Ajustar según tu lógica

                if (factor == 0) continue;

                int cantidadFirmada = factor * cantidad;

                if (!stockEnFechaFinPorVariante.ContainsKey(varId))
                    stockEnFechaFinPorVariante[varId] = 0;

                stockEnFechaFinPorVariante[varId] += cantidadFirmada;
            }

            // === 5) Clasificación de variantes con el stock a fechaFin ===
            int stockTotal = 0;

            var variantesSinStock = new List<KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO>();
            var variantesStockBajo = new List<KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO>();

            foreach (var v in variantesTodas)
            {
                int stockEnFecha = 0;
                stockEnFechaFinPorVariante.TryGetValue(v.prod_variante_id, out stockEnFecha);

                // Crear una copia para no modificar el original
                var vCopia = new KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO
                {
                    prod_variante_id = v.prod_variante_id,
                    producto_id = v.producto_id,
                    color = v.color,
                    talla = v.talla,
                    stock = stockEnFecha, // Stock histórico
                    stock_minimo = v.stock_minimo
                };

                stockTotal += stockEnFecha;

                if (stockEnFecha == 0)
                {
                    variantesSinStock.Add(vCopia);
                }
                else if (v.stock_minimo > 0 && stockEnFecha <= v.stock_minimo)
                {
                    variantesStockBajo.Add(vCopia);
                }
            }

            int sinStock = variantesSinStock.Count;
            int stockBajo = variantesStockBajo.Count;

            // === 6) Tabla de estado crítico ===
            var tablaCriticos = new System.Data.DataTable();
            tablaCriticos.Columns.Add("Producto");
            tablaCriticos.Columns.Add("Variante");
            tablaCriticos.Columns.Add("Stock");
            tablaCriticos.Columns.Add("StockMinimo");

            foreach (var v in variantesSinStock.Concat(variantesStockBajo))
            {
                productosPorId.TryGetValue(v.producto_id, out var prod);
                string nombre = prod != null ? prod.descripcion : $"Prod {v.producto_id}";
                string variante = "";

                if (v.color != null) variante += v.color.nombre;
                if (v.talla != null) variante += $" T{v.talla.numero}";
                if (string.IsNullOrWhiteSpace(variante)) variante = "(N/A)";

                tablaCriticos.Rows.Add(
                    nombre,
                    variante,
                    v.stock.ToString(),
                    v.stock_minimo.ToString()
                );
            }

            gvCriticos.DataSource = tablaCriticos;
            gvCriticos.DataBind();

            // === 7) Datos para gráficos ===
            JsonSinStock = js.Serialize(new
            {
                labels = variantesSinStock.Select(v =>
                {
                    productosPorId.TryGetValue(v.producto_id, out var prod);
                    string txt = prod != null ? prod.descripcion : $"Prod {v.producto_id}";
                    if (v.color != null) txt += $" - {v.color.nombre}";
                    if (v.talla != null) txt += $" T{v.talla.numero}";
                    return txt;
                }).ToList(),
                data = variantesSinStock.Select(x => 0).ToList()
            });

            JsonStockBajo = js.Serialize(new
            {
                labels = variantesStockBajo.Select(v =>
                {
                    productosPorId.TryGetValue(v.producto_id, out var prod);
                    string txt = prod != null ? prod.descripcion : $"Prod {v.producto_id}";
                    if (v.color != null) txt += $" - {v.color.nombre}";
                    if (v.talla != null) txt += $" T{v.talla.numero}";
                    return txt;
                }).ToList(),
                data = variantesStockBajo.Select(x => x.stock).ToList()
            });

            // Stock por categoría usando el stock histórico
            var stockCat = new Dictionary<string, int>();
            foreach (var v in variantesTodas)
            {
                int stockHistorico = 0;
                stockEnFechaFinPorVariante.TryGetValue(v.prod_variante_id, out stockHistorico);

                productosPorId.TryGetValue(v.producto_id, out var prod);
                string categoria = prod?.categoria?.nombre ?? "Sin categoría";

                if (!stockCat.ContainsKey(categoria))
                    stockCat[categoria] = 0;

                stockCat[categoria] += stockHistorico;
            }

            JsonStockCategoria = js.Serialize(new
            {
                labels = stockCat.Keys.ToList(),
                data = stockCat.Values.ToList()
            });

            // === 8) KPIs ===
            lblSinStock.Text = sinStock.ToString();
            lblStockBajo.Text = stockBajo.ToString();
            lblStockTotal.Text = stockTotal.ToString();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // limpiar textos
            txtFechaInicio.Text = string.Empty;
            txtFechaFin.Text = string.Empty;

            // ocultar errores
            lblErrorFechas.Visible = false;
            lblErrorFechas.Text = string.Empty;

            // volver al rango automático
            DateTime fIni, fFin;
            ObtenerRangoAutomatico(out fIni, out fFin);
            GenerarReporte(fIni, fFin);
        }

        protected void btnExportarPDF_Click(object sender, EventArgs e)
        {
            try
            {
                // 1) Obtener fechas del TextBox
                string fechaInicioStr = txtFechaInicio.Text.Trim();
                string fechaFinStr = txtFechaFin.Text.Trim();

                // 2) Si ambas están vacías o ambas tienen valor
                bool ambasVacias = string.IsNullOrWhiteSpace(fechaInicioStr) && string.IsNullOrWhiteSpace(fechaFinStr);
                bool ambasLlenas = !string.IsNullOrWhiteSpace(fechaInicioStr) && !string.IsNullOrWhiteSpace(fechaFinStr);

                if (!ambasVacias && !ambasLlenas)
                {
                    lblErrorFechas.Visible = true;
                    lblErrorFechas.Text = "No se puede generar un reporte con solo una fecha";
                    return;
                }

                if (ambasLlenas)
                {
                    DateTime fechaInicio, fechaFin;

                    // Intentar convertir a DateTime
                    if (!DateTime.TryParse(fechaInicioStr, out fechaInicio))
                    {
                        lblErrorFechas.Visible = true;
                        lblErrorFechas.Text = "Fecha inicio inválida";
                        return;
                    }

                    if (!DateTime.TryParse(fechaFinStr, out fechaFin))
                    {
                        lblErrorFechas.Visible = true;
                        lblErrorFechas.Text = "Fecha fin inválida";
                        return;
                    }

                    // Comparar fechas: inicio debe ser MENOR O IGUAL a fin
                    if (fechaInicio > fechaFin)
                    {
                        lblErrorFechas.Visible = true;
                        lblErrorFechas.Text = "La fecha de inicio debe ser menor o igual a la fecha fin";
                        return;
                    }

                    // Convertir a formato ISO 8601
                    fechaInicioStr = fechaInicio.ToString("yyyy-MM-ddTHH:mm:ss");
                    fechaFinStr = fechaFin.ToString("yyyy-MM-ddTHH:mm:ss");
                }
                else
                {
                    // Si ambas están vacías
                    fechaInicioStr = null;
                    fechaFinStr = null;
                }

                // 5) Llamar al BO para generar el PDF
                var reportesBO = new ReportesBO();
                byte[] reportePDF = reportesBO.GenerarReporteEstadoStocks(fechaInicioStr, fechaFinStr);

                // 6) Validar que se generó correctamente
                if (reportePDF == null || reportePDF.Length == 0)
                {
                    lblErrorFechas.Visible = true;
                    lblErrorFechas.Text = "ERROR al generar el reporte, intente de nuevo";
                    return;
                }

                // 7) Descargar el PDF al navegador
                Response.Clear();
                Response.ContentType = "application/pdf";
                Response.AddHeader("content-disposition", "attachment;filename=ReporteVentas.pdf");
                Response.BinaryWrite(reportePDF);
                Response.End();
            }
            catch (Exception ex)
            {
                lblErrorFechas.Visible = true;
                lblErrorFechas.Text = "Error: " + ex.Message;
                System.Diagnostics.Debug.WriteLine("Error al exportar PDF: " + ex.Message);
            }
        }
    }
}
