using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.BO;
using KawkiWebBusiness.KawkiWebWSVentas;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.productosVariantesDTO;

namespace KawkiWeb
{
    public partial class HistorialVentasVendedor : Page
    {
        private VentasBO ventasBO = new VentasBO();
        private ComprobantesPagoBO comprobantesBO = new ComprobantesPagoBO();
        private ProductosBO productosBO = new ProductosBO();
        private DetalleVentasBO detalleVentasBO = new DetalleVentasBO();
        private ProductosVariantesBO productosVariantesBO = new ProductosVariantesBO();

        private string UsuarioActual
        {
            get { return Session["Usuario"]?.ToString() ?? ""; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // AntiCaché
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.Now.AddSeconds(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.AddHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            Response.AddHeader("Pragma", "no-cache");
            Response.AddHeader("Expires", "0");

            if (!IsPostBack)
            {
                string rol = Session["Rol"] as string;

                // VALIDACIÓN DE SESIÓN
                if (string.IsNullOrEmpty(rol))
                {
                    Response.Redirect("Error404.aspx", false);
                    return;
                }

                // Si es admin → redirigir
                if (rol.ToLower() == "admin")
                {
                    Response.Redirect("HistorialVentasAdmin.aspx", false);
                    return;
                }

                try
                {
                    lblNombreVendedor.Text = UsuarioActual;
                    CargarVentas();
                }
                catch (Exception ex)
                {
                    MostrarError("Error al cargar la página: " + ex.Message);
                }
            }
        }

        private void CargarVentas()
        {
            try
            {
                // Obtener el ID del usuario directamente de la sesión (ya está guardado en Login)
                int idUsuarioActual = 0;

                if (Session["UsuarioId"] != null)
                {
                    idUsuarioActual = Convert.ToInt32(Session["UsuarioId"]);
                }
                else
                {
                    lblMensaje.CssClass = "text-danger mb-2 d-block";
                    lblMensaje.Text = "Error: No se encontró ID de usuario en sesión. Por favor, inicia sesión nuevamente.";
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    ResetearEstadisticas();
                    return;
                }

                string usuarioLogin = UsuarioActual;
                string usuarioNombreCompleto = Session["UsuarioNombreCompleto"]?.ToString() ?? "Usuario desconocido";

                lblMensaje.CssClass = "text-info mb-2 d-block";

                // Obtener todas las ventas
                var todasLasVentas = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();

                // Filtrar por usuarioId
                var ventasVendedor = todasLasVentas
                    .Where(v => v.usuario != null && v.usuario.usuarioId == idUsuarioActual)
                    .ToList();

                if (!ventasVendedor.Any())
                {
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    lblContador.Text = "0 ventas encontradas";
                    ResetearEstadisticas();
                    return;
                }

                var lista = ventasVendedor;

                // Aplicar filtros de fecha
                bool hayFiltros = !string.IsNullOrEmpty(txtFechaInicio.Text) ||
                                  !string.IsNullOrEmpty(txtFechaFin.Text);

                if (!hayFiltros)
                {
                    Session["VentasVendedor"] = new List<ventasDTO>(lista);
                }

                DateTime? fechaInicioVal = null;
                DateTime? fechaFinVal = null;

                // Filtro por fecha inicio
                if (!string.IsNullOrEmpty(txtFechaInicio.Text) && DateTime.TryParse(txtFechaInicio.Text, out DateTime fechaInicio))
                {
                    fechaInicioVal = fechaInicio;
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) >= fechaInicio).ToList();
                }

                // Filtro por fecha fin
                if (!string.IsNullOrEmpty(txtFechaFin.Text) && DateTime.TryParse(txtFechaFin.Text, out DateTime fechaFin))
                {
                    fechaFinVal = fechaFin.AddDays(1).AddSeconds(-1);
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) <= fechaFinVal).ToList();
                }

                // Validar que fecha inicio no sea mayor a fecha fin
                if (fechaInicioVal.HasValue && fechaFinVal.HasValue && fechaInicioVal > fechaFinVal)
                {
                    lblErrorFiltros.CssClass = "text-danger d-block mt-2";
                    lblErrorFiltros.Text = "La fecha de inicio debe ser menor que la fecha de fin.";
                    lblErrorFiltros.Visible = true;
                    gvVentas.DataSource = null;
                    gvVentas.DataBind();
                    ResetearEstadisticas();
                    return; // Cancelar búsqueda
                }

                if (hayFiltros)
                {
                    Session["VentasVendedor"] = lista;
                }

                // Transformar para GridView
                var gvLista = new List<dynamic>();

                foreach (var v in lista)
                {
                    try
                    {
                        // Obtener los detalles de ESTA venta para contar TOTAL DE UNIDADES
                        var detallesVenta = detalleVentasBO.ListarPorVentaId(v.venta_id);
                        int cantidadProductos = detallesVenta != null ? detallesVenta.Sum(d => d.cantidad) : 0;

                        gvLista.Add(new
                        {
                            venta_id = v.venta_id,
                            Fecha = DateTime.Parse(v.fecha_hora_creacion),
                            descuento = v.descuento?.descripcion ?? "Sin descuento",
                            redSocial = v.redSocial?.nombre ?? "N/D",
                            CantidadProductos = cantidadProductos,  // ← Ahora desde DetalleVentas
                            MontoTotal = v.total
                        });
                    }
                    catch (Exception ex)
                    {
                        System.Diagnostics.Debug.WriteLine($"Error en venta {v.venta_id}: {ex.Message}");
                    }
                }

                gvVentas.DataSource = gvLista;
                gvVentas.DataBind();

                lblContador.Text = $"{gvLista.Count} ventas encontradas";

                ActualizarEstadisticas();
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"<strong>ERROR:</strong> {ex.Message}";
                gvVentas.DataSource = null;
                gvVentas.DataBind();
                ResetearEstadisticas();
            }
        }



        // También actualiza MostrarDetalle:
        private void MostrarDetalle(int idVenta)
        {
            try
            {
                var venta = ventasBO.ObtenerPorIdVenta(idVenta);

                if (venta == null)
                {
                    MostrarError("No se encontró la venta.");
                    return;
                }

                // Obtener el ID del usuario actual de la sesión
                int idUsuarioActual = 0;
                if (Session["UsuarioId"] != null)
                {
                    idUsuarioActual = Convert.ToInt32(Session["UsuarioId"]);
                }

                // Verificar que la venta pertenece al usuario actual
                if (venta.usuario?.usuarioId != idUsuarioActual)
                {
                    MostrarError("No tienes permiso para ver esta venta.");
                    return;
                }

                // Buscar el comprobante asociado a esta venta
                var todosComprobantes = comprobantesBO.ListarTodosComprobantes();
                var comprobante = todosComprobantes?.FirstOrDefault(c => c.venta?.venta_id == idVenta);

                // Datos de la venta
                lblIdVentaDetalle.Text = venta.venta_id.ToString();
                lblFechaDetalle.Text = DateTime.Parse(venta.fecha_hora_creacion).ToString("dd/MM/yyyy HH:mm");
                lblCanalDetalle.Text = venta.redSocial?.nombre ?? "N/D";
                lblTotalDetalle.Text = $"S/ {venta.total:N2}";

                // Datos del cliente desde el comprobante
                if (comprobante != null)
                {
                    lblClienteDetalle.Text = !string.IsNullOrEmpty(comprobante.nombre_cliente)
                        ? comprobante.nombre_cliente
                        : (comprobante.razon_social_cliente ?? "N/D");

                    lblTelefonoDetalle.Text = !string.IsNullOrEmpty(comprobante.telefono_cliente)
                        ? comprobante.telefono_cliente
                        : "N/D";

                    lblDireccionDetalle.Text = !string.IsNullOrEmpty(comprobante.direccion_fiscal_cliente)
                        ? comprobante.direccion_fiscal_cliente
                        : "N/D";
                }
                else
                {
                    lblClienteDetalle.Text = "N/D - Sin comprobante";
                    lblTelefonoDetalle.Text = "N/D";
                    lblDireccionDetalle.Text = "N/D";
                }

                // Obtener los detalles POR SEPARADO
                var detallesVenta = detalleVentasBO.ListarPorVentaId(idVenta);

                if (detallesVenta != null && detallesVenta.Count > 0)
                {
                    var detalles = new List<dynamic>();

                    foreach (var d in detallesVenta)
                    {
                        // d.prodVariante es un int (el ID de la variante)
                        System.Diagnostics.Debug.WriteLine($"DEBUG: d.prodVariante = {d.prodVariante} (tipo: {d.prodVariante?.GetType().Name})");

                        // Obtener el DTO completo
                        var varianteCompleta = productosVariantesBO.ObtenerPorId(d.prodVariante.prod_variante_id);

                        System.Diagnostics.Debug.WriteLine($"DEBUG: varianteCompleta es NULL? {varianteCompleta == null}");

                        if (varianteCompleta != null)
                        {
                            System.Diagnostics.Debug.WriteLine($"DEBUG: varianteCompleta.producto_id = {varianteCompleta.producto_id}");
                            System.Diagnostics.Debug.WriteLine($"DEBUG: varianteCompleta.color?.nombre = '{varianteCompleta.color?.nombre}'");
                            System.Diagnostics.Debug.WriteLine($"DEBUG: varianteCompleta.talla?.nombre = '{varianteCompleta.talla?.numero}'");
                        }

                        string nombreProducto = ObtenerNombreProductoCompleto(varianteCompleta);

                        detalles.Add(new
                        {
                            Producto = nombreProducto,
                            Cantidad = d.cantidad,
                            PrecioUnitario = d.precio_unitario,
                            Subtotal = d.subtotal
                        });
                    }

                    gvDetalleProductos.DataSource = detalles;
                    gvDetalleProductos.DataBind();
                }
                else
                {
                    gvDetalleProductos.DataSource = null;
                    gvDetalleProductos.DataBind();
                }

                pnlDetalle.Visible = true;

                // Scroll al detalle
                ScriptManager.RegisterStartupScript(this, GetType(), "ScrollToDetail",
                    "$('html, body').animate({ scrollTop: $('#" + pnlDetalle.ClientID + "').offset().top - 100 }, 500);",
                    true);
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar detalle: " + ex.Message + "<br/>" + ex.StackTrace);
            }
        }

        private void ActualizarEstadisticas()
        {
            try
            {
                var ventas = Session["VentasVendedor"] as List<ventasDTO>;

                if (ventas == null || !ventas.Any())
                {
                    ResetearEstadisticas();
                    return;
                }

                decimal montoTotal = ventas.Sum(v => Convert.ToDecimal(v.total));
                int cantidad = ventas.Count;
                decimal promedio = montoTotal / cantidad;

                lblTotalVentas.Text = cantidad.ToString();
                lblMontoTotal.Text = $"S/ {montoTotal:N2}";
                lblPromedio.Text = $"S/ {promedio:N2}";

                // Animación (opcional)
                ScriptManager.RegisterStartupScript(
                    this, GetType(),
                    "AnimarDashboard",
                    $"animarDashboard({cantidad}, {montoTotal}, {promedio});",
                    true
                );
            }
            catch (Exception ex)
            {
                MostrarError("Error en estadísticas: " + ex.Message);
                ResetearEstadisticas();
            }
        }

        private void ResetearEstadisticas()
        {
            lblTotalVentas.Text = "0";
            lblMontoTotal.Text = "S/ 0.00";
            lblPromedio.Text = "S/ 0.00";
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            CargarVentas();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFechaInicio.Text = "";
            txtFechaFin.Text = "";
            lblMensaje.Text = "";
            CargarVentas();
        }

        protected void gvVentas_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalle")
            {
                int idVenta = Convert.ToInt32(e.CommandArgument);
                MostrarDetalle(idVenta);
            }
        }

        private string ObtenerNombreProductoCompleto(productosVariantesDTO variante)
        {
            if (variante == null)
            {
                System.Diagnostics.Debug.WriteLine("DEBUG: variante es NULL");
                return "N/D - Variante nula";
            }

            try
            {
                int productoId = variante.producto_id;
                System.Diagnostics.Debug.WriteLine($"DEBUG: productoId = {productoId}");

                if (productoId == 0)
                {
                    System.Diagnostics.Debug.WriteLine("DEBUG: productoId es 0");
                    return "Producto N/D - ID inválido";
                }

                // Obtener el producto padre
                var producto = productosBO.ObtenerPorIdProducto(productoId);
                System.Diagnostics.Debug.WriteLine($"DEBUG: Producto encontrado? {(producto != null ? "SÍ" : "NO")}");

                if (producto == null)
                {
                    System.Diagnostics.Debug.WriteLine($"DEBUG: Producto ID {productoId} NO encontrado en BD");
                    return $"Producto N/D - ID {productoId} no existe";
                }

                // Construir nombre: Categoría + Estilo + Color
                string categoria = producto.categoria?.nombre ?? "";
                string estilo = producto.estilo?.nombre ?? "";
                string color = variante.color?.nombre ?? "";

                System.Diagnostics.Debug.WriteLine($"DEBUG: categoria='{categoria}', estilo='{estilo}', color='{color}'");

                // Formato: "Oxford Clásico Beige M"
                string nombreCompleto = $"{categoria} {estilo} {color}".Trim();

                System.Diagnostics.Debug.WriteLine($"DEBUG: nombreCompleto = '{nombreCompleto}'");

                return nombreCompleto;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"ERROR en ObtenerNombreProductoCompleto: {ex.Message}");
                System.Diagnostics.Debug.WriteLine($"ERROR StackTrace: {ex.StackTrace}");
                return $"ERROR: {ex.Message}";
            }
        }

        protected void btnCerrarDetalle_Click(object sender, EventArgs e)
        {
            pnlDetalle.Visible = false;
        }

        private void MostrarError(string mensaje)
        {
            lblMensaje.CssClass = "text-danger mb-2 d-block";
            lblMensaje.Text = mensaje;
        }
    }
}

