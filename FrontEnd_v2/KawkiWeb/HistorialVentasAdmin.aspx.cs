using KawkiWebBusiness;
using KawkiWebBusiness.BO;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSProductosVariantes;
using KawkiWebBusiness.KawkiWebWSUsuarios;
using KawkiWebBusiness.KawkiWebWSVentas;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class HistorialVentasAdmin : Page
    {
        private VentasBO ventasBO = new VentasBO();

        private List<productosDTO> ListaProductos;

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

                // Si no es admin → redirigir
                if (rol.ToLower() == "vendedor")
                {
                    Response.Redirect("Productos.aspx", false);
                    return;
                }

                // En este punto SÍ es admin → cargar datos
                try
                {
                    CargarVendedores();
                    CargarProductos();
                    CargarVentas();
                    ActualizarEstadisticas();
                }
                catch (Exception ex)
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Error al cargar la página: " + ex.Message;
                }
            }
        }

        private void CargarProductos()
        {
            var client = new ProductosClient();
            var productos = client.listarTodosProducto();
            ListaProductos = productos.ToList();

            Session["Productos"] = ListaProductos;
        }

        // Cargar lista de vendedores reales
        private void CargarVendedores()
        {
            try
            {
                // Llamamos al WS de usuarios
                var client = new UsuariosClient();

                // Obtenemos TODOS los usuarios
                var usuarios = client.listarTodosUsuario();

                // Filtramos solo los vendedores activos
                var vendedores = usuarios
                    .Where(u => u.tipoUsuario != null
                             && u.tipoUsuario.tipoUsuarioId == 1
                             && u.activo == true)
                    .OrderBy(u => u.nombre)
                    .ToList();

                ddlVendedor.Items.Clear();
                ddlVendedor.Items.Add(new ListItem("Todos los vendedores", "0"));

                foreach (var u in vendedores)
                {
                    string nombre = $"{u.nombre} {u.apePaterno}";
                    ddlVendedor.Items.Add(new ListItem(nombre, nombre));
                }
            }
            catch
            {
                ddlVendedor.Items.Clear();
                ddlVendedor.Items.Add(new ListItem("Todos los vendedores", "0"));
            }
        }

        // Cargar ventas reales (usando objetos, NO DataTables)
        private void CargarVentas()
        {
            try
            {
                var lista = ventasBO.ListarTodosVenta() ?? new List<ventasDTO>();
                lblMensaje.Text = "VENTAS TOTALES DTO: " + lista.Count;
                //System.Diagnostics.Debug.WriteLine("Ventas totales encontradas: " + lista.Count);
                //lblMensaje.Text = "Ventas totales: " + lista.Count;  // temporal

                // Filtro por fecha inicio
                if (DateTime.TryParse(txtFechaInicio.Text, out DateTime inicio))
                {
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) >= inicio).ToList();
                }

                // Filtro por fecha fin
                if (DateTime.TryParse(txtFechaFin.Text, out DateTime fin))
                {
                    fin = fin.AddDays(1).AddSeconds(-1);
                    lista = lista.Where(v => DateTime.Parse(v.fecha_hora_creacion) <= fin).ToList();
                }

                // Filtro por vendedor
                if (ddlVendedor.SelectedValue != "0")
                {
                    string vendedor = ddlVendedor.SelectedItem.Text;

                    lista = lista.Where(v => v.usuario != null && $"{v.usuario.nombre} {v.usuario.apePaterno}" == vendedor).ToList();
                }
                else
                {
                    // Quitar ventas sin usuario para estadísticas correctas
                    lista = lista.Where(v => v.usuario != null).ToList();
                }

                // Transformar a modelo para GridView
                var gvLista = lista.Select(v => new
                {
                    IdVenta = v.venta_id,
                    Fecha = DateTime.Parse(v.fecha_hora_creacion).ToString("dd/MM/yyyy"),

                    Vendedor = $"{v.usuario?.nombre} {v.usuario?.apePaterno}",

                    Canal = v.redSocial?.nombre,

                    // NUEVAS COLUMNAS
                    //Descuento = v.descuento?.descripcion,
                    //EsValida = v.esValida ? "Sí" : "No",

                    MontoTotal = v.total
                }).ToList();

                // ORDENAMIENTOvent
                if (!string.IsNullOrEmpty(SortField))
                {
                    if (SortDirection == "ASC")
                        gvLista = gvLista.OrderBy(x => x.GetType().GetProperty(SortField).GetValue(x)).ToList();
                    else
                        gvLista = gvLista.OrderByDescending(x => x.GetType().GetProperty(SortField).GetValue(x)).ToList();
                }

                gvVentas.DataSource = gvLista;
                gvVentas.DataBind();

                Session["VentasFiltradas"] = lista;

                lblContador.Text = $"{gvLista.Count} ventas encontradas";
                lblMensaje.Text = "";

                ActualizarEstadisticas();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar ventas: " + ex.Message);
            }
        }

        // Estadísticas
        private void ActualizarEstadisticas()
        {
            try
            {
                // Recuperamos la lista REAL de ventasDTO
                var ventas = Session["VentasFiltradas"] as List<ventasDTO>;

                // Si no hay ventas, mostramos 0
                if (ventas == null || ventas.Count == 0)
                {
                    lblTotalVentas.Text = "0";
                    lblMontoTotal.Text = "S/ 0.00";
                    lblPromedio.Text = "S/ 0.00";
                    return;
                }

                // Calculamos estadísticas
                decimal montoTotal = ventas.Sum(v => (decimal)v.total);
                int cantidad = ventas.Count;
                decimal promedio = cantidad > 0 ? montoTotal / cantidad : 0;

                // Mostramos estadísticas
                lblTotalVentas.Text = cantidad.ToString();
                lblMontoTotal.Text = $"S/ {montoTotal:N2}";
                lblPromedio.Text = $"S/ {promedio:N2}";
            }
            catch (Exception ex)
            {
                MostrarError("Error en estadísticas: " + ex.Message);
            }
        }

        // Botones
        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            lblErrorFiltros.Visible = false;  // limpiamos antes de validar
            lblErrorFiltros.Text = "";

            // Validar fechas
            if (DateTime.TryParse(txtFechaInicio.Text, out DateTime inicio) &&
                DateTime.TryParse(txtFechaFin.Text, out DateTime fin))
            {
                if (inicio > fin)
                {
                    lblErrorFiltros.Visible = true;
                    lblErrorFiltros.Text = "La fecha de inicio debe ser menor que la fecha de fin.";
                    return; // Cancelar búsqueda
                }
            }

            CargarVentas();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFechaInicio.Text = "";
            txtFechaFin.Text = "";
            ddlVendedor.SelectedIndex = 0;
            lblErrorFiltros.Visible = false;
            lblErrorFiltros.Text = "";
            CargarVentas();
            ActualizarEstadisticas();
        }

        // Ver detalle
        protected void gvVentas_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalle")
            {
                int id = Convert.ToInt32(e.CommandArgument);
                MostrarDetalle(id);
            }
        }

        private void MostrarDetalle(int idVenta)
        {
            try
            {
                // ==============================
                //   1. OBTENER VENTA
                // ==============================
                var venta = ventasBO.ObtenerPorIdVenta(idVenta);
                if (venta == null)
                {
                    MostrarError("No se encontró la venta seleccionada.");
                    return;
                }

                // ==============================
                //   2. TRAER COMPROBANTE
                // ==============================
                var compBO = new ComprobantesPagoBO();
                var comprobantes = compBO.ListarTodosComprobantes();

                var comprobante = comprobantes
                    .FirstOrDefault(c => c.venta != null && c.venta.venta_id == idVenta);

                // ==============================
                //   3. DATOS GENERALES VENTA
                // ==============================
                lblIdVentaDetalle.Text = venta.venta_id.ToString();

                if (venta.usuario != null)
                    lblVendedorDetalle.Text = venta.usuario.nombre + " " + venta.usuario.apePaterno;
                else
                    lblVendedorDetalle.Text = "-";

                // Parseo de fecha seguro
                DateTime fecha;
                if (!string.IsNullOrEmpty(venta.fecha_hora_creacion) &&
                    venta.fecha_hora_creacion.Length >= 10 &&
                    DateTime.TryParseExact(
                        venta.fecha_hora_creacion.Substring(0, 10),
                        "yyyy-MM-dd",
                        System.Globalization.CultureInfo.InvariantCulture,
                        System.Globalization.DateTimeStyles.None,
                        out fecha))
                {
                    lblFechaDetalle.Text = fecha.ToString("dd/MM/yyyy");
                }
                else
                {
                    lblFechaDetalle.Text = "-";
                }

                lblCanalDetalle.Text = (venta.redSocial != null) ? venta.redSocial.nombre : "-";

                // ==============================
                //   4. MOSTRAR DATOS COMPROBANTE
                // ==============================
                if (comprobante != null)
                {
                    lblTipoComprobante.Text = (comprobante.tipo_comprobante != null) ? comprobante.tipo_comprobante.nombre : "-";
                    lblNumeroSerie.Text = comprobante.numero_serie;
                    lblNombreCliente.Text = comprobante.nombre_cliente;
                    lblDniCliente.Text = comprobante.dni_cliente;
                    lblRucCliente.Text = comprobante.ruc_cliente;
                    lblDireccionFiscal.Text = comprobante.direccion_fiscal_cliente;
                    lblMetodoPago.Text = (comprobante.metodo_pago != null) ? comprobante.metodo_pago.nombre : "-";
                    lblSubtotal.Text = "S/ " + comprobante.subtotal.ToString("N2");
                    lblIgv.Text = "S/ " + comprobante.igv.ToString("N2");
                    lblTotalComprobante.Text = "S/ " + comprobante.total.ToString("N2");
                }
                else
                {
                    lblTipoComprobante.Text = "-";
                    lblNumeroSerie.Text = "-";
                    lblNombreCliente.Text = "-";
                    lblDniCliente.Text = "-";
                    lblRucCliente.Text = "-";
                    lblDireccionFiscal.Text = "-";
                    lblMetodoPago.Text = "-";
                    lblSubtotal.Text = "-";
                    lblIgv.Text = "-";
                    lblTotalComprobante.Text = "-";
                }

                // ==============================
                //   5. DETALLES DE PRODUCTO (CON DEBUG)
                // ==============================
                var detBO = new DetalleVentasBO();
                var listaDet = detBO.ListarPorVentaId(idVenta);

                if (listaDet == null || !listaDet.Any())
                {
                    gvDetalleProductos.DataSource = null;
                    gvDetalleProductos.DataBind();
                    ScriptManager.RegisterStartupScript(this, GetType(), "AbrirModalVenta", "abrirDetalleVenta();", true);
                    return;
                }

                // 🔍 DEBUG: Ver qué contiene prodVariante
                System.Diagnostics.Debug.WriteLine("=== INICIO DEBUG PRODUCTOS ===");
                foreach (var det in listaDet)
                {
                    System.Diagnostics.Debug.WriteLine($"Detalle ID: {det.detalle_venta_id}");
                    if (det.prodVariante != null)
                    {
                        System.Diagnostics.Debug.WriteLine($"  - ProdVariante ID: {det.prodVariante.prod_variante_id}");
                        System.Diagnostics.Debug.WriteLine($"  - Producto ID: {det.prodVariante.producto_id}");
                        System.Diagnostics.Debug.WriteLine($"  - SKU: {det.prodVariante.SKU}");
                        System.Diagnostics.Debug.WriteLine($"  - Color: {(det.prodVariante.color != null ? det.prodVariante.color.nombre : "NULL")}");
                        System.Diagnostics.Debug.WriteLine($"  - Talla: {(det.prodVariante.talla != null ? det.prodVariante.talla.numero.ToString() : "NULL")}");
                    }
                    else
                    {
                        System.Diagnostics.Debug.WriteLine("  - ProdVariante es NULL");
                    }
                }
                System.Diagnostics.Debug.WriteLine("=== FIN DEBUG INICIAL ===");

                // 🔹 ESTRATEGIA ALTERNATIVA: Listar TODAS las variantes y hacer match
                // (Temporal hasta saber el método correcto)
                System.Diagnostics.Debug.WriteLine("=== OBTENIENDO VARIANTES ===");

                var diccionarioProductosPorVariante = new Dictionary<int, productosDTO>();

                try
                {
                    // Intenta listar todas las variantes
                    var prodVarBO = new ProductosVariantesBO();
                    var todasVariantes = prodVarBO.ListarTodos(); // O el método que tengas

                    if (todasVariantes != null)
                    {
                        System.Diagnostics.Debug.WriteLine($"Total variantes en sistema: {todasVariantes.Count()}");

                        // Filtrar solo las variantes que necesitamos
                        var variantesIds = listaDet
                            .Where(d => d.prodVariante != null && d.prodVariante.prod_variante_id > 0)
                            .Select(d => d.prodVariante.prod_variante_id)
                            .Distinct()
                            .ToList();

                        System.Diagnostics.Debug.WriteLine($"Variantes necesarias: {string.Join(", ", variantesIds)}");

                        var prodClient = new ProductosClient();

                        foreach (var varId in variantesIds)
                        {
                            // Buscar la variante en la lista completa
                            var variante = todasVariantes.FirstOrDefault(v => v.prod_variante_id == varId);

                            if (variante != null && variante.producto_id > 0)
                            {
                                System.Diagnostics.Debug.WriteLine($"Variante {varId} -> producto_id: {variante.producto_id}");

                                try
                                {
                                    var producto = prodClient.obtenerPorIdProducto(variante.producto_id);
                                    if (producto != null)
                                    {
                                        System.Diagnostics.Debug.WriteLine($"  ✓ Producto: {producto.descripcion}");
                                        diccionarioProductosPorVariante[varId] = producto;
                                    }
                                }
                                catch (Exception ex)
                                {
                                    System.Diagnostics.Debug.WriteLine($"  ✗ Error obteniendo producto: {ex.Message}");
                                }
                            }
                            else
                            {
                                System.Diagnostics.Debug.WriteLine($"Variante {varId} no encontrada o sin producto_id");
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine($"Error al listar variantes: {ex.Message}");
                }

                System.Diagnostics.Debug.WriteLine($"Total productos obtenidos: {diccionarioProductosPorVariante.Count}");

                // 🔹 Mapear los detalles con los productos obtenidos
                var lista = listaDet.Select(d =>
                {
                    string nombreProducto = "Producto no encontrado";
                    string color = "-";
                    string talla = "-";
                    string sku = "-";
                    string debugInfo = "";

                    if (d.prodVariante != null)
                    {
                        int varianteId = d.prodVariante.prod_variante_id;
                        debugInfo = $"[VarID:{varianteId}]";

                        // Buscar el producto usando el ID de la variante
                        if (varianteId > 0 && diccionarioProductosPorVariante.TryGetValue(varianteId, out var producto))
                        {
                            nombreProducto = producto.descripcion ?? "Sin descripción";
                        }

                        // Obtener color, talla y SKU
                        color = d.prodVariante.color?.nombre ?? "-";
                        talla = d.prodVariante.talla?.numero.ToString() ?? "-";
                        sku = d.prodVariante.SKU ?? "-";
                    }
                    else
                    {
                        nombreProducto = "Variante NULL";
                    }

                    return new
                    {
                        Producto = nombreProducto,
                        Color = color,
                        Talla = talla,
                        SKU = sku,
                        Cantidad = d.cantidad,
                        PrecioUnitario = d.precio_unitario,
                        Subtotal = d.subtotal
                    };
                }).ToList();

                System.Diagnostics.Debug.WriteLine("=== FIN DEBUG PRODUCTOS ===");

                gvDetalleProductos.DataSource = lista;
                gvDetalleProductos.DataBind();

                // Abrir modal
                ScriptManager.RegisterStartupScript(
                    this, GetType(), "AbrirModalVenta",
                    "abrirDetalleVenta();", true);

                // ==============================
                //   6. DESCUENTO APLICADO
                // ==============================
                seccionDescuento.Visible = false;
                lblDescuento.Text = "S/ 0.00";

                try
                {
                    if (venta.descuento != null && comprobante != null)
                    {
                        var descuento = venta.descuento;
                        var descBO = new DescuentosBO();

                        double montoDescuento = descBO.calcularDescuentoDescuento(
                            descuento.descuento_id,
                            comprobante.subtotal
                        );

                        lblDescuento.Text = "- S/ " + montoDescuento.ToString("N2");
                        lblDescDescripcion.Text = descuento.descripcion;

                        lblDescCondicion.Text =
                            (descuento.tipo_condicion != null ? descuento.tipo_condicion.nombre : "") +
                            " " + descuento.valor_condicion;

                        lblDescBeneficio.Text =
                            (descuento.tipo_beneficio != null ? descuento.tipo_beneficio.nombre : "") +
                            " " + descuento.valor_beneficio;

                        seccionDescuento.Visible = true;
                    }
                }
                catch
                {
                    lblDescuento.Text = "S/ 0.00";
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar detalle: " + ex.Message);
            }
        }

        //protected void btnCerrarDetalle_Click(object sender, EventArgs e)
        //{
        //    pnlDetalle.Visible = false;
        //}

        // Utilidad
        private void MostrarError(string msg)
        {
            lblMensaje.CssClass = "text-danger d-block mb-2";
            lblMensaje.Text = msg;
        }

        private string SortField
        {
            get => ViewState["SortField"]?.ToString() ?? "";
            set => ViewState["SortField"] = value;
        }

        private string SortDirection
        {
            get => ViewState["SortDirection"]?.ToString() ?? "ASC";
            set => ViewState["SortDirection"] = value;
        }

        protected void ActualizarOrden(object sender, EventArgs e)
        {
            SortField = ddlOrdenarPor.SelectedValue;
            SortDirection = ddlDireccion.SelectedValue;
            CargarVentas(); // Se vuelve a aplicar filtros y orden
        }

    }
}
