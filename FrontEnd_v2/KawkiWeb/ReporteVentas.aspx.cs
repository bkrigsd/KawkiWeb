using KawkiWebBusiness;
using KawkiWebBusiness.BO;
using KawkiWebBusiness.KawkiWebWSComprobantesPago;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSProductosVariantes;
using KawkiWebBusiness.KawkiWebWSVentas;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Serialization;
using System.Web.UI;

namespace KawkiWeb
{
    public partial class ReporteVentas : Page
    {
        // Propiedades que usará el .aspx para los gráficos
        public string ChartProductosLabelsJson { get; set; } = "[]";
        public string ChartProductosDataJson { get; set; } = "[]";
        public string ChartTallasLabelsJson { get; set; } = "[]";
        public string ChartTallasDataJson { get; set; } = "[]";

        public string ChartTopClientesLabelsJson { get; set; } = "[]";
        public string ChartTopClientesDataJson { get; set; } = "[]";

        public string ChartCategoriasLabelsJson { get; set; } = "[]";
        public string ChartCategoriasDataJson { get; set; } = "[]";

        public string ChartColoresLabelsJson { get; set; } = "[]";
        public string ChartColoresDataJson { get; set; } = "[]";

        public class ProductoVentaResumen
        {
            public string Producto { get; set; }
            public int Cantidad { get; set; }
            public double Participacion { get; set; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            var m = Master as KawkiWeb;
            if (m != null)
            {
                try { m.SetActive("ReporteVentas"); } catch { }
            }

            if (!IsPostBack)
            {
                // Si quieres que salgan vacías, descomenta estas dos líneas:
                txtFechaInicio.Text = string.Empty;
                txtFechaFin.Text = string.Empty;

                // Rango automático según ventas registradas
                DateTime fechaInicio, fechaFin;
                ObtenerRangoAutomatico(out fechaInicio, out fechaFin);

                // Para TextMode="Date" → formato yyyy-MM-dd
                //txtFechaInicio.Text = fechaInicio.ToString("yyyy-MM-dd");
                //txtFechaFin.Text = fechaFin.Date.ToString("yyyy-MM-dd");

                // Generar reporte inicial
                GenerarReporte(fechaInicio, fechaFin);
            }
        }

        /// <summary>
        /// Obtiene el rango [primera venta, última venta] según la tabla de ventas.
        /// Si hay error o no hay ventas, usa hoy como rango.
        /// </summary>
        private void ObtenerRangoAutomatico(out DateTime fechaInicio, out DateTime fechaFin)
        {
            DateTime hoy = DateTime.Today;
            fechaInicio = hoy;
            fechaFin = hoy.AddDays(1).AddSeconds(-1); // fin del día

            try
            {
                var ventasBO = new VentasBO();
                var ventas = ventasBO.ListarTodosVenta()
                             ?? new List<KawkiWebBusiness.KawkiWebWSVentas.ventasDTO>();

                var fechas = ventas
                    .Select(v =>
                    {
                        DateTime f;
                        return DateTime.TryParse(v.fecha_hora_creacion, out f) ? f : DateTime.MinValue;
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
                // Si hay error, nos quedamos con hoy
            }
        }

        protected void btnGenerar_Click(object sender, EventArgs e)
        {
            lblMensajeFechas.Visible = false;
            lblMensajeFechas.Text = string.Empty;

            // Si algún campo está vacío, usamos el rango automático
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
                lblMensajeFechas.Visible = true;
                lblMensajeFechas.Text = "Las fechas ingresadas no son válidas.";
                return;
            }

            if (fechaInicio > fechaFin)
            {
                lblMensajeFechas.Visible = true;
                lblMensajeFechas.Text = "La fecha de Inicio no puede ser mayor que la fecha Fin.";
                return;
            }

            // Asegurar que tomamos TODO el día fin
            fechaInicio = fechaInicio.Date;                       // 00:00:00
            fechaFin = fechaFin.Date.AddDays(1).AddTicks(-1);  // 23:59:59.999...

            GenerarReporte(fechaInicio, fechaFin);
        }

        private void GenerarReporte(DateTime fechaInicio, DateTime fechaFin)
        {
            lblMensajeFechas.Visible = false;
            lblMensajeFechas.Text = string.Empty;

            var serializer = new JavaScriptSerializer();
            var ventasBO = new VentasBO();
            var detalleBO = new DetalleVentasBO();
            var prodVarBO = new ProductosVariantesBO();
            var productosBO = new ProductosBO();
            var comprobantesBO = new ComprobantesPagoBO();

            // 1) Ventas dentro del rango
            var ventas = (ventasBO.ListarTodosVenta()
                          ?? new List<KawkiWebBusiness.KawkiWebWSVentas.ventasDTO>())   // ← defensa contra null
                .Select(v =>
                {
                    DateTime fecha;
                    DateTime.TryParse(v.fecha_hora_creacion, out fecha);

                    return new
                    {
                        Venta = v,
                        Fecha = fecha
                    };
                })
                .Where(x => x.Fecha >= fechaInicio && x.Fecha <= fechaFin)
                .Select(x => x.Venta)
                .ToList();

            System.Diagnostics.Debug.WriteLine($"Ventas en rango: {ventas.Count}");

            if (!ventas.Any())
            {
                // Reset de totales
                lblVentasTotales.Text = "S/. 0.00";
                lblProductosVendidos.Text = "0";
                lblClientes.Text = "0";
                lblPromedioDiario.Text = "S/. 0.00";

                // Reset de gráficos
                ChartProductosLabelsJson = "[]";
                ChartProductosDataJson = "[]";
                ChartCategoriasLabelsJson = "[]";
                ChartCategoriasDataJson = "[]";
                ChartColoresLabelsJson = "[]";
                ChartColoresDataJson = "[]";
                ChartTallasLabelsJson = "[]";
                ChartTallasDataJson = "[]";
                ChartTopClientesLabelsJson = "[]";
                ChartTopClientesDataJson = "[]";

                // Reset de tabla
                gvTopProductos.DataSource = null;
                gvTopProductos.DataBind();

                return;
            }

            var ventasIds = ventas.Select(v => v.venta_id).ToList();

            // 2) Detalles de esas ventas
            var detalles = detalleBO.ListarTodos()
                .Where(d => ventasIds.Contains(d.venta_id))
                .ToList();

            System.Diagnostics.Debug.WriteLine($"Detalles en rango: {detalles.Count}");

            // 3) Totales
            double totalVentas = ventas.Sum(v => v.total);
            int productosVendidos = detalles.Sum(d => d.cantidad);
            int diasPeriodo = (fechaFin.Date - fechaInicio.Date).Days + 1;
            double promedioDiario = diasPeriodo > 0 ? totalVentas / diasPeriodo : 0;

            // 4) Clientes distintos según comprobantes asociados a esas ventas
            var comprobantes = comprobantesBO.ListarTodosComprobantes()
                .Where(c => c.venta != null && ventasIds.Contains(c.venta.venta_id))
                .ToList();

            int clientesDistintos = comprobantes
                .Select(c =>
                {
                    // tipo_comprobante_id:
                    // 1 = Boleta simple
                    // 2 = Boleta con DNI
                    // 3 = Factura
                    int tipoId = (c.tipo_comprobante != null)
                                    ? c.tipo_comprobante.tipo_comprobante_id
                                    : 0;

                    string key = null;

                    switch (tipoId)
                    {
                        case 3: // FACTURA
                            if (!string.IsNullOrWhiteSpace(c.ruc_cliente))
                            {
                                // clave principal por RUC
                                key = "RUC|" + c.ruc_cliente.Trim();
                            }
                            else if (!string.IsNullOrWhiteSpace(c.razon_social_cliente))
                            {
                                // fallback por razón social
                                var rs = c.razon_social_cliente.Trim().ToUpper();
                                key = "RS|" + rs;
                            }
                            break;

                        case 2: // BOLETA CON DNI
                            if (!string.IsNullOrWhiteSpace(c.dni_cliente))
                            {
                                key = "DNI|" + c.dni_cliente.Trim();
                            }
                            else if (!string.IsNullOrWhiteSpace(c.nombre_cliente))
                            {
                                // por si acaso hay algún dato mal grabado
                                var nom = System.Text.RegularExpressions.Regex
                                    .Replace(c.nombre_cliente.Trim().ToUpper(), @"\s+", " ");
                                key = "NOM_DNI_FALTANTE|" + nom;
                            }
                            break;

                        case 1: // BOLETA SIMPLE (sin DNI, con nombre obligatorio)
                        default:
                            if (!string.IsNullOrWhiteSpace(c.nombre_cliente))
                            {
                                var nom = System.Text.RegularExpressions.Regex
                                    .Replace(c.nombre_cliente.Trim().ToUpper(), @"\s+", " ");
                                key = "NOM|" + nom;
                            }
                            break;
                    }

                    // DEBUG opcional:
                    // System.Diagnostics.Debug.WriteLine($"Comprobante {c.comprobante_id} → tipo={tipoId}, key={key}");

                    return key;
                })
                .Where(k => !string.IsNullOrWhiteSpace(k))
                .Distinct()
                .Count();

            // ========== TOP 3 CLIENTES QUE MÁS COMPRARON ==========
            var topClientes = comprobantes
                .Select(c =>
                {
                    // Nombre (si existe) o razón social
                    string nombre = !string.IsNullOrWhiteSpace(c.nombre_cliente)
                                    ? c.nombre_cliente
                                    : c.razon_social_cliente;

                    // Documento (DNI o RUC)
                    string doc = !string.IsNullOrWhiteSpace(c.dni_cliente)
                                    ? c.dni_cliente
                                    : c.ruc_cliente;

                    return new
                    {
                        Nombre = string.IsNullOrWhiteSpace(nombre) ? "(Sin nombre)" : nombre,
                        Documento = string.IsNullOrWhiteSpace(doc) ? "" : doc,
                        Total = c.total
                    };
                })
                .GroupBy(x => new { x.Nombre, x.Documento })
                .Select(g => new
                {
                    Cliente = string.IsNullOrWhiteSpace(g.Key.Documento)
                                ? g.Key.Nombre
                                : $"{g.Key.Nombre} ({g.Key.Documento})",
                    Total = g.Sum(x => x.Total)
                })
                .OrderByDescending(x => x.Total)
                .Take(3)
                .ToList();

            ChartTopClientesLabelsJson = serializer.Serialize(topClientes.Select(x => x.Cliente));
            ChartTopClientesDataJson = serializer.Serialize(topClientes.Select(x => x.Total));

            // KPIs
            lblVentasTotales.Text = $"S/. {totalVentas:N2}";
            lblProductosVendidos.Text = productosVendidos.ToString();
            lblClientes.Text = clientesDistintos.ToString();
            lblPromedioDiario.Text = $"S/. {promedioDiario:N2}";

            // 5) Cargar productos y variantes una sola vez
            var productos = productosBO.ListarTodosProducto().ToList();
            var variantes = prodVarBO.ListarTodos().ToList();

            // ========== GRÁFICO 1: Productos más vendidos ==========
            var ventasPorProducto = (from d in detalles
                                     where d.prodVariante != null
                                     join v in variantes
                                         on d.prodVariante.prod_variante_id equals v.prod_variante_id
                                     join p in productos
                                         on v.producto_id equals p.producto_id
                                     group d by p.descripcion into g
                                     select new
                                     {
                                         Producto = g.Key,
                                         Cantidad = g.Sum(x => x.cantidad)
                                     })
                                     .Where(x => x.Cantidad > 0)
                                     .OrderByDescending(x => x.Cantidad)
                                     .ToList();

            var tablaProductos = ventasPorProducto
                .Select(x => new ProductoVentaResumen
                {
                    Producto = x.Producto,
                    Cantidad = x.Cantidad,
                    Participacion = productosVendidos > 0
                                    ? (x.Cantidad * 100.0 / productosVendidos)
                                    : 0
                })
                .ToList();

            gvTopProductos.DataSource = tablaProductos;
            gvTopProductos.DataBind();

            ChartProductosLabelsJson = serializer.Serialize(
                ventasPorProducto.Select(x => x.Producto)
            );
            ChartProductosDataJson = serializer.Serialize(
                ventasPorProducto.Select(x => x.Cantidad)
            );

            // ========== GRÁFICO 2A: Categorías más demandadas ==========
            var categorias = (from d in detalles
                              where d.prodVariante != null
                              join v in variantes
                                  on d.prodVariante.prod_variante_id equals v.prod_variante_id
                              join p in productos
                                  on v.producto_id equals p.producto_id
                              group d by p.categoria.nombre into g
                              select new
                              {
                                  Categoria = g.Key,
                                  Cantidad = g.Sum(x => x.cantidad)
                              })
                              .Where(x => x.Cantidad > 0)
                              .OrderByDescending(x => x.Cantidad)
                              .ToList();

            ChartCategoriasLabelsJson = serializer.Serialize(categorias.Select(x => x.Categoria));
            ChartCategoriasDataJson = serializer.Serialize(categorias.Select(x => x.Cantidad));

            // ========== GRÁFICO 2B: Colores más demandados ==========
            var colores = (from d in detalles
                           where d.prodVariante != null
                           join v in variantes
                               on d.prodVariante.prod_variante_id equals v.prod_variante_id
                           where v.color != null
                           group d by v.color.nombre into g
                           select new
                           {
                               Color = g.Key,
                               Cantidad = g.Sum(x => x.cantidad)
                           })
                           .Where(x => x.Cantidad > 0)
                           .OrderByDescending(x => x.Cantidad)
                           .ToList();

            ChartColoresLabelsJson = serializer.Serialize(colores.Select(x => x.Color));
            ChartColoresDataJson = serializer.Serialize(colores.Select(x => x.Cantidad));

            // ========== GRÁFICO 3: Comparativa por talla ==========
            var tallas = (from d in detalles
                          where d.prodVariante != null && d.prodVariante.talla != null
                          group d by d.prodVariante.talla.numero into g
                          select new
                          {
                              Talla = g.Key.ToString(),
                              Cantidad = g.Sum(x => x.cantidad)
                          })
                          .OrderBy(x => x.Talla)
                          .ToList();

            ChartTallasLabelsJson = serializer.Serialize(tallas.Select(x => x.Talla));
            ChartTallasDataJson = serializer.Serialize(tallas.Select(x => x.Cantidad));
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // limpiar textos
            txtFechaInicio.Text = string.Empty;
            txtFechaFin.Text = string.Empty;

            // ocultar errores
            lblMensajeFechas.Visible = false;
            lblMensajeFechas.Text = string.Empty;

            // volver al rango automático y regenerar
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
                    lblMensajeFechas.Visible = true;
                    lblMensajeFechas.Text = "No se puede generar un reporte con solo una fecha";
                    return;
                }

                if (ambasLlenas)
                {
                    DateTime fechaInicio, fechaFin;

                    if (!DateTime.TryParse(fechaInicioStr, out fechaInicio))
                    {
                        lblMensajeFechas.Visible = true;
                        lblMensajeFechas.Text = "Fecha inicio inválida";
                        return;
                    }

                    if (!DateTime.TryParse(fechaFinStr, out fechaFin))
                    {
                        lblMensajeFechas.Visible = true;
                        lblMensajeFechas.Text = "Fecha fin inválida";
                        return;
                    }

                    if (fechaInicio > fechaFin)
                    {
                        lblMensajeFechas.Visible = true;
                        lblMensajeFechas.Text = "La fecha de inicio debe ser menor o igual a la fecha fin";
                        return;
                    }

                    // Convertir a formato ISO 8601
                    fechaInicioStr = fechaInicio.ToString("yyyy-MM-ddTHH:mm:ss");
                    fechaFinStr = fechaFin.ToString("yyyy-MM-ddTHH:mm:ss");
                }
                else
                {
                    // Si ambas están vacías, mandamos null → rango completo en el reporte
                    fechaInicioStr = null;
                    fechaFinStr = null;
                }

                // 5) Llamar al BO para generar el PDF
                var reportesBO = new ReportesBO();
                byte[] reportePDF = reportesBO.GenerarReporteVentasYTendencias(fechaInicioStr, fechaFinStr);

                // 6) Validar que se generó correctamente
                if (reportePDF == null || reportePDF.Length == 0)
                {
                    lblMensajeFechas.Visible = true;
                    lblMensajeFechas.Text = "ERROR al generar el reporte, intente de nuevo";
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
                lblMensajeFechas.Visible = true;
                lblMensajeFechas.Text = "Error: " + ex.Message;
                System.Diagnostics.Debug.WriteLine("Error al exportar PDF: " + ex.Message);
            }
        }
    }
}
