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



        protected void Page_Load(object sender, EventArgs e)
        {
            var m = Master as KawkiWeb;
            if (m != null)
            {
                try { m.SetActive("ReporteVentas"); } catch { }
            }

            if (!IsPostBack)
            {
                txtFechaInicio.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
                txtFechaFin.Text = DateTime.Now.ToString("yyyy-MM-dd");

                GenerarReporte();
            }
        }

        public class ProductoVentaResumen
        {
            public string Producto { get; set; }
            public int Cantidad { get; set; }
            public double Participacion { get; set; }
        }


        protected void btnGenerar_Click(object sender, EventArgs e)
        {
            GenerarReporte();
        }

        private void GenerarReporte()
        {
            // 1) Leer fechas
            DateTime fechaInicio, fechaFin;

            if (!DateTime.TryParse(txtFechaInicio.Text, out fechaInicio))
                fechaInicio = DateTime.Now.AddDays(-7);

            if (!DateTime.TryParse(txtFechaFin.Text, out fechaFin))
                fechaFin = DateTime.Now;

            // Normalizamos solo fecha (sin hora) para validar
            fechaInicio = fechaInicio.Date;
            fechaFin = fechaFin.Date;

            // Validación: Desde no puede ser mayor que Hasta
            if (fechaInicio > fechaFin)
            {
                lblMensajeFechas.Visible = true;
                lblMensajeFechas.Text = "La fecha de Inicio debe ser menor o igual que la fecha Fin.";

                // Dejamos los totales en cero y no calculamos nada
                lblVentasTotales.Text = "S/. 0.00";
                lblProductosVendidos.Text = "0";
                lblClientes.Text = "0";
                lblPromedioDiario.Text = "S/. 0.00";

                // Vaciar datos de gráficos para no mostrar información vieja
                ChartProductosLabelsJson = "[]";
                ChartProductosDataJson = "[]";
                ChartCategoriasLabelsJson = "[]";
                ChartCategoriasDataJson = "[]";
                ChartColoresLabelsJson = "[]";
                ChartColoresDataJson = "[]";
                ChartTallasLabelsJson = "[]";
                ChartTallasDataJson = "[]";
                //ChartVariacionLabelsJson = "[]";
                //ChartVariacionDataJson = "[]";
                ChartTopClientesLabelsJson = "[]";
                ChartTopClientesDataJson = "[]";


                gvTopProductos.DataSource = null;
                gvTopProductos.DataBind();

                return;
            }

            // Si las fechas son válidas, ocultamos el mensaje
            lblMensajeFechas.Visible = false;

            // Incluir todo el día final (23:59:59)
            fechaFin = fechaFin.AddDays(1).AddTicks(-1);

            var serializer = new JavaScriptSerializer();

            // 2) Instanciar BO
            var ventasBO = new VentasBO();
            var detalleBO = new DetalleVentasBO();
            var prodVarBO = new ProductosVariantesBO();
            var productosBO = new ProductosBO();
            var comprobantesBO = new ComprobantesPagoBO();

            // 3) Ventas válidas dentro del rango
            // 3) Ventas dentro del rango (primero sin esValida para probar)
            var ventas = ventasBO.ListarTodosVenta()
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

            // DEBUG rápido
            System.Diagnostics.Debug.WriteLine($"Ventas en rango: {ventas.Count}");

            if (!ventas.Any())
            {
                lblVentasTotales.Text = "S/. 0.00";
                lblProductosVendidos.Text = "0";
                lblClientes.Text = "0";
                lblPromedioDiario.Text = "S/. 0.00";
                return;
            }

            var ventasIds = ventas.Select(v => v.venta_id).ToList();

            // 4) Detalles de esas ventas
            var detalles = detalleBO.ListarTodos()
                .Where(d => ventasIds.Contains(d.venta_id))
                .ToList();

            System.Diagnostics.Debug.WriteLine($"Detalles en rango: {detalles.Count}");

            // 5) Totales
            double totalVentas = ventas.Sum(v => v.total);
            int productosVendidos = detalles.Sum(d => d.cantidad);
            int diasPeriodo = (fechaFin.Date - fechaInicio.Date).Days + 1;
            double promedioDiario = diasPeriodo > 0 ? totalVentas / diasPeriodo : 0;

            // Clientes distintos desde comprobantes de las ventas del rango
            var comprobantes = comprobantesBO.ListarTodosComprobantes()
                .Where(c => c.venta != null && ventasIds.Contains(c.venta.venta_id))
                .ToList();

            int clientesDistintos = comprobantes
                .Select(c =>
                {
                    // priorizamos DNI; si está vacío, usamos RUC
                    var doc = !string.IsNullOrWhiteSpace(c.dni_cliente)
                                ? c.dni_cliente
                                : c.ruc_cliente;
                    return doc;
                })
                .Where(doc => !string.IsNullOrWhiteSpace(doc))
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
                // agrupamos por cliente + documento por si tiene varios comprobantes
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

            // Serializamos para el gráfico
            ChartTopClientesLabelsJson = serializer.Serialize(topClientes.Select(x => x.Cliente));
            ChartTopClientesDataJson = serializer.Serialize(topClientes.Select(x => x.Total));

            lblVentasTotales.Text = $"S/. {totalVentas:N2}";
            lblProductosVendidos.Text = productosVendidos.ToString();
            lblClientes.Text = clientesDistintos.ToString();
            lblPromedioDiario.Text = $"S/. {promedioDiario:N2}";


            // 6) Cargar productos y variantes una sola vez
            var productos = productosBO.ListarTodosProducto().ToList();
            var variantes = prodVarBO.ListarTodos().ToList();

            // ========== GRÁFICO 1: Productos más vendidos (para gráfico pastel) ==========
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

            // DEBUG opcional
            //System.Diagnostics.Debug.WriteLine("=== Ventas por producto ===");
            //foreach (var v in ventasPorProducto)
            //{
            //    System.Diagnostics.Debug.WriteLine($"{v.Producto}: {v.Cantidad}");
            //}

            // === TABLA: detalle de productos del período ===
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
                          group d by d.prodVariante.talla.numero into g   // cambia 'numero' si tu propiedad se llama distinto
                          select new
                          {
                              Talla = g.Key.ToString(),
                              Cantidad = g.Sum(x => x.cantidad)
                          })
                          .OrderBy(x => x.Talla)
                          .ToList();

            ChartTallasLabelsJson = serializer.Serialize(tallas.Select(x => x.Talla));
            ChartTallasDataJson = serializer.Serialize(tallas.Select(x => x.Cantidad));

            //// ========== GRÁFICO 4: Variación de ventas (diario) ==========

            //// Proyectamos ventas a un objeto con Fecha (DateTime) + Total
            //var ventasConFecha = ventas
            //    .Select(v =>
            //    {
            //        DateTime fecha;
            //        DateTime.TryParse(v.fecha_hora_creacion, out fecha);

            //        return new
            //        {
            //            Fecha = fecha,
            //            Total = v.total
            //        };
            //    })
            //    .Where(x => x.Fecha != DateTime.MinValue)
            //    .ToList();

            //var serie = ventasConFecha
            //    .GroupBy(x => x.Fecha.Date)
            //    .Select(g => new
            //    {
            //        Label = g.Key.ToString("dd/MM"),
            //        Total = g.Sum(x => x.Total)
            //    })
            //    .OrderBy(x => x.Label)
            //    .ToList();

            //ChartVariacionLabelsJson = serializer.Serialize(serie.Select(x => x.Label));
            //ChartVariacionDataJson = serializer.Serialize(serie.Select(x => x.Total));

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

                    // Intentar convertir a DateTime
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

                    // Comparar fechas: inicio debe ser MENOR O IGUAL a fin
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
                    // Si ambas están vacías
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
