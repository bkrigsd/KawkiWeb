using KawkiWebBusiness;
using KawkiWebBusiness.BO;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSVentas;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Globalization;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


namespace KawkiWeb
{
    public partial class RegistrarVentas : Page
    {
        // Tabla de detalle en ViewState
        private DataTable DetalleVentas
        {
            get
            {
                if (ViewState["DetalleVentas"] == null)
                {
                    var dt = new DataTable();
                    dt.Columns.Add("ProductoId", typeof(int));
                    dt.Columns.Add("Producto", typeof(string));
                    dt.Columns.Add("Cantidad", typeof(int));
                    dt.Columns.Add("PrecioUnitario", typeof(decimal));
                    dt.Columns.Add("Subtotal", typeof(decimal));
                    ViewState["DetalleVentas"] = dt;
                }
                return (DataTable)ViewState["DetalleVentas"];
            }
            set { ViewState["DetalleVentas"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Primera carga
                CargarProductos();
                CargarDescuentos();
                CargarCanalesVenta();
                CargarMetodosPago();
                gvDetalle.DataSource = DetalleVentas;
                gvDetalle.DataBind();
                ActualizarTotales();
            }
        }

        private void CargarProductos()
        {
            try
            {
                var cliente = new KawkiWebBusiness.KawkiWebWSProductosVariantes.ProductosVariantesClient();
                var respuesta = cliente.listarTodosProdVariante();

                ddlProducto.Items.Clear();

                if (respuesta == null || respuesta.Length == 0)
                {
                    ddlProducto.Items.Add(new ListItem("-- No hay productos --", ""));
                    return;
                }

                var lista = respuesta.Select(p => new
                {
                    id = p.prod_variante_id,
                    texto = $"{p.SKU}"
                }).ToList();

                ddlProducto.DataSource = lista;
                ddlProducto.DataTextField = "texto";
                ddlProducto.DataValueField = "id";
                ddlProducto.DataBind();

                ddlProducto.Items.Insert(0, new ListItem("-- Selecciona --", ""));
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al cargar productos: " + ex.Message;
                lblMensaje.CssClass = "text-danger";
            }
        }
        private void CargarDescuentos()
        {
            var cliente = new KawkiWebBusiness.KawkiWebWSDescuentos.DescuentosClient();
            var lista = cliente.listarActivasDescuento();

            ddlDescuentos.DataSource = lista;
            ddlDescuentos.DataTextField = "descripcion";   // columna DESCRIPCION
            ddlDescuentos.DataValueField = "descuento_id"; // columna DESCUENTO_ID
            ddlDescuentos.DataBind();

            ddlDescuentos.Items.Insert(0, new ListItem("-- Seleccione --", ""));
        }
        private void CargarCanalesVenta()
        {
            try
            {
                var cliente = new KawkiWebBusiness.KawkiWebWSRedesSociales.RedesSocialesClient();
                var lista = cliente.listarTodosRedSocial();

                ddlCanal.Items.Clear();

                if (lista == null || lista.Length == 0)
                {
                    ddlCanal.Items.Add(new ListItem("-- No hay canales --", ""));
                    return;
                }

                ddlCanal.DataSource = lista;
                ddlCanal.DataTextField = "nombre";        // ✔ EXISTE
                ddlCanal.DataValueField = "redSocialId";  // ✔ EXISTE
                ddlCanal.DataBind();

                ddlCanal.Items.Insert(0, new ListItem("-- Seleccione --", ""));
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al cargar canales: " + ex.Message;
                lblMensaje.CssClass = "text-danger";
            }
        }

        private void CargarMetodosPago()
        {
            try
            {
                var cliente = new KawkiWebBusiness.KawkiWebWSMetodosPago.MetodosPagoClient();
                var lista = cliente.listarTodosMetodoPago(); // Ajustar al nombre real

                ddlMetodoPago.Items.Clear();

                if (lista == null || lista.Length == 0)
                {
                    ddlMetodoPago.Items.Add(new ListItem("-- No hay métodos --", ""));
                    return;
                }

                ddlMetodoPago.DataSource = lista;
                ddlMetodoPago.DataTextField = "nombre";  // NOMBRE
                ddlMetodoPago.DataValueField = "metodo_pago_id"; // ID
                ddlMetodoPago.DataBind();

                ddlMetodoPago.Items.Insert(0, new ListItem("-- Seleccione --", ""));
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al cargar métodos de pago: " + ex.Message;
                lblMensaje.CssClass = "text-danger";
            }
        }

        #region Métodos de Validación

        /// <summary>
        /// Valida que el teléfono sea celular (9 dígitos empezando por 9) o fijo (7 dígitos)
        /// </summary>
        private bool ValidarTelefono(string telefono, out string mensajeError)
        {
            mensajeError = string.Empty;

            if (string.IsNullOrWhiteSpace(telefono))
            {
                //mensajeError = "El teléfono es obligatorio.";
                //return false;
                return true;
            }

            // Remover espacios y guiones
            telefono = telefono.Replace(" ", "").Replace("-", "");

            if (!string.IsNullOrWhiteSpace(telefono))
            {
                // Validar celular: 9 dígitos empezando por 9
                if (Regex.IsMatch(telefono, @"^9\d{8}$"))
                {
                    return true;
                }

                // Validar teléfono fijo: 7 dígitos (puede empezar con 01 para Lima)
                if (Regex.IsMatch(telefono, @"^(01)?\d{7}$"))
                {
                    return true;
                }
            }

            mensajeError = "Formato de teléfono inválido. Debe ser celular (9 dígitos empezando por 9) o fijo (7 dígitos).";
            return false;
        }

        /// <summary>
        /// Valida que el email tenga formato correcto
        /// </summary>
        private bool ValidarEmail(string email, out string mensajeError)
        {
            mensajeError = string.Empty;

            // Email es opcional
            if (string.IsNullOrWhiteSpace(email))
            {
                return true;
            }

            // Validar formato de email
            string patron = @"^[\w\.-]+@[\w\.-]+\.\w{2,}$";
            if (!Regex.IsMatch(email, patron))
            {
                mensajeError = "El formato del email no es válido.";
                return false;
            }

            return true;
        }

        /// <summary>
        /// Valida que el nombre contenga solo letras y espacios
        /// </summary>
        private bool ValidarNombre(string nombre, out string mensajeError)
        {
            mensajeError = string.Empty;

            if (string.IsNullOrWhiteSpace(nombre))
            {
                mensajeError = "El nombre es obligatorio.";
                return false;
            }

            if (nombre.Length < 3)
            {
                mensajeError = "El nombre debe tener al menos 3 caracteres.";
                return false;
            }

            // Validar que solo contenga letras, espacios y acentos
            if (!Regex.IsMatch(nombre, @"^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$"))
            {
                mensajeError = "El nombre solo debe contener letras y espacios.";
                return false;
            }

            return true;
        }

        /// <summary>
        /// Valida que la dirección no esté vacía y tenga longitud adecuada
        /// </summary>
        private bool ValidarDireccion(string direccion, out string mensajeError)
        {
            mensajeError = string.Empty;

            if (string.IsNullOrWhiteSpace(direccion))
            {
                mensajeError = "La dirección es obligatoria.";
                return false;
            }

            if (direccion.Length < 10)
            {
                mensajeError = "La dirección debe ser más específica (mínimo 10 caracteres).";
                return false;
            }

            if (direccion.Length > 200)
            {
                mensajeError = "La dirección es demasiado larga (máximo 200 caracteres).";
                return false;
            }

            return true;
        }

        /// <summary>
        /// Valida que la cantidad sea un número positivo
        /// </summary>
        private bool ValidarCantidad(string cantidadTexto, out int cantidad, out string mensajeError)
        {
            cantidad = 0;
            mensajeError = string.Empty;

            if (!int.TryParse(cantidadTexto, out cantidad))
            {
                mensajeError = "La cantidad debe ser un número entero.";
                return false;
            }

            if (cantidad <= 0)
            {
                mensajeError = "La cantidad debe ser mayor a cero.";
                return false;
            }

            if (cantidad > 100)
            {
                mensajeError = "La cantidad no puede ser mayor a 100 unidades.";
                return false;
            }

            return true;
        }

        /// <summary>
        /// Valida que el precio sea un número decimal positivo
        /// </summary>
        private bool ValidarPrecio(string precioTexto, out decimal precio, out string mensajeError)
        {
            precio = 0m;
            mensajeError = string.Empty;

            if (!decimal.TryParse(precioTexto, NumberStyles.Any, CultureInfo.InvariantCulture, out precio))
            {
                mensajeError = "El precio debe ser un número válido.";
                return false;
            }

            if (precio < 0)
            {
                mensajeError = "El precio no puede ser negativo.";
                return false;
            }

            if (precio > 100000)
            {
                mensajeError = "El precio no puede ser mayor a S/ 100,000.";
                return false;
            }

            // Validar que tenga máximo 2 decimales
            if (Math.Round(precio, 2) != precio)
            {
                mensajeError = "El precio solo puede tener hasta 2 decimales.";
                return false;
            }

            return true;
        }

        /// <summary>
        /// Valida que el descuento sea válido
        /// </summary>


        /// <summary>
        /// Valida todos los campos del cliente antes de registrar la venta
        /// </summary>
        private bool ValidarDatosCliente(out string mensajeError)
        {
            mensajeError = string.Empty;

            // Validar nombre
            if (!ValidarNombre(txtNombreCliente.Text.Trim(), out mensajeError))
            {
                return false;
            }

            // Validar teléfono
            if (!ValidarTelefono(txtTelefono.Text.Trim(), out mensajeError))
            {
                return false;
            }
            string tipo = ddlComprobante.SelectedValue;

            // BOLETA SIMPLE
            if (tipo == "boleta-simple")
            {
                if (!ValidarNombre(txtNombreCliente.Text.Trim(), out mensajeError))
                    return false;

                // Teléfono es opcional → no validar si está vacío
                if (!string.IsNullOrWhiteSpace(txtTelefono.Text))
                {
                    if (!ValidarTelefono(txtTelefono.Text.Trim(), out mensajeError))
                        return false;
                }

                return true;
            }

            // BOLETA CON DNI
            if (tipo == "boleta-dni")
            {
                if (!ValidarNombre(txtNombreCliente.Text.Trim(), out mensajeError))
                    return false;

                if (string.IsNullOrWhiteSpace(txtDNI.Text) || txtDNI.Text.Length != 8)
                {
                    mensajeError = "El DNI debe tener 8 dígitos.";
                    return false;
                }

                if (!string.IsNullOrWhiteSpace(txtTelefono.Text))
                {
                    if (!ValidarTelefono(txtTelefono.Text.Trim(), out mensajeError))
                        return false;
                }

                return true;
            }

            // FACTURA
            if (tipo == "factura")
            {
                if (string.IsNullOrWhiteSpace(txtRUC.Text) || txtRUC.Text.Length != 11)
                {
                    mensajeError = "El RUC debe tener 11 dígitos.";
                    return false;
                }

                if (string.IsNullOrWhiteSpace(txtRazonSocial.Text))
                {
                    mensajeError = "La razón social es obligatoria.";
                    return false;
                }

                if (string.IsNullOrWhiteSpace(txtDireccionFiscal.Text))
                {
                    mensajeError = "La dirección fiscal es obligatoria.";
                    return false;
                }

                return true;
            }

            return true;
        }

        #endregion
        protected void btnAgregarProducto_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(ddlProducto.SelectedValue))
            {
                lblMensaje.Text = "Selecciona un producto antes de agregar.";
                lblMensaje.CssClass = "text-danger";
                return;
            }

            string producto = ddlProducto.SelectedItem.Text;
            int cantidad;
            decimal precioUnitario;

            if (!int.TryParse(txtCantidad.Text, out cantidad) || cantidad <= 0)
            {
                lblMensaje.Text = "La cantidad debe ser un número mayor que 0.";
                lblMensaje.CssClass = "text-danger";
                return;
            }

            if (!decimal.TryParse(txtPrecioUnitario.Text, out precioUnitario) || precioUnitario <= 0)
            {
                lblMensaje.Text = "El precio unitario debe ser válido.";
                lblMensaje.CssClass = "text-danger";
                return;
            }

            decimal subtotal = cantidad * precioUnitario;

            DataTable dt = ViewState["DetalleVentas"] as DataTable;
            if (dt == null)
            {
                dt = new DataTable();
                dt.Columns.Add("ProductoId", typeof(int));      // Aquí guardamos prod_variante_id
                dt.Columns.Add("Producto", typeof(string));
                dt.Columns.Add("Cantidad", typeof(int));
                dt.Columns.Add("PrecioUnitario", typeof(decimal));
                dt.Columns.Add("Subtotal", typeof(decimal));
            }

            int varianteId = Convert.ToInt32(ddlProducto.SelectedValue);

            dt.Rows.Add(varianteId, producto, cantidad, precioUnitario, subtotal);

            ViewState["DetalleVentas"] = dt;

            gvDetalle.DataSource = dt;
            gvDetalle.DataBind();

            ActualizarTotales();

            ddlProducto.SelectedIndex = 0;
            txtCantidad.Text = "1";
            txtPrecioUnitario.Text = "";

            lblMensaje.Text = "Producto agregado correctamente.";
            lblMensaje.CssClass = "text-success";
        }


        protected void ddlProducto_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(ddlProducto.SelectedValue))
            {
                txtPrecioUnitario.Text = "";
                return;
            }

            int varianteId = Convert.ToInt32(ddlProducto.SelectedValue);

            try
            {
                // 1. Obtener la variante
                var clienteVar = new KawkiWebBusiness.KawkiWebWSProductosVariantes.ProductosVariantesClient();
                var variante = clienteVar.obtenerPorIdProdVariante(varianteId);

                if (variante == null)
                {
                    lblMensaje.Text = "No se encontró la variante seleccionada.";
                    return;
                }

                // 2. Obtener el producto usando producto_id
                var clienteProd = new KawkiWebBusiness.KawkiWebWSProductos.ProductosClient();
                var producto = clienteProd.obtenerPorIdProducto(variante.producto_id);

                if (producto == null)
                {
                    lblMensaje.Text = "No se encontró el producto vinculado.";
                    return;
                }

                // 3. Mostrar precioVenta real
                decimal precio = (decimal)producto.precio_venta;
                txtPrecioUnitario.Text = precio.ToString("0.00");
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error cargando precio: " + ex.Message;
                lblMensaje.CssClass = "text-danger";
            }
        }
        protected void ddlDescuentos_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(ddlDescuentos.SelectedValue))
                return;

            int idDescuento = int.Parse(ddlDescuentos.SelectedValue);

            var cliente = new KawkiWebBusiness.KawkiWebWSDescuentos.DescuentosClient();
            var d = cliente.obtenerPorIdDescuento(idDescuento);

            if (d == null) return;

            // Recuperar el tipo de beneficio
            int tipoBeneficio = d.tipo_beneficio.tipo_beneficio_id;
            int valor = d.valor_beneficio;

            switch (tipoBeneficio)
            {
                case 1: // Descuento por porcentaje
                    lblMensaje.Text = $"Descuento aplicado: {valor}%";
                    break;

                case 2: // Descuento fijo
                    lblMensaje.Text = $"Descuento aplicado: S/ {valor:0.00}";
                    break;

                case 3: // Envío gratis
                    lblMensaje.Text = "Beneficio aplicado: Envío gratis";
                    break;
            }

            lblMensaje.CssClass = "text-info";

            ActualizarTotales();
        }


        protected void ddlComprobante_SelectedIndexChanged(object sender, EventArgs e)
        {
            string tipo = ddlComprobante.SelectedValue;

            // Ocultar todo por defecto
            grupoDNI.Visible = false;
            grupoRUC.Visible = false;
            grupoRazonSocial.Visible = false;
            grupoDireccionFiscal.Visible = false;
            grupoTelefono.Visible = false;
            grupoNombreCliente.Visible = false;

            // Limpiar campos específicos
            txtDNI.Text = "";
            txtRUC.Text = "";
            txtRazonSocial.Text = "";
            txtDireccionFiscal.Text = "";

            switch (tipo)
            {
                case "boleta-simple":
                    grupoNombreCliente.Visible = true;
                    grupoTelefono.Visible = true;
                    break;

                case "boleta-dni":
                    grupoNombreCliente.Visible = true;
                    grupoDNI.Visible = true;
                    grupoTelefono.Visible = true;
                    break;

                case "factura":
                    // Para factura no se pide nombre personal
                    grupoNombreCliente.Visible = false;
                    grupoTelefono.Visible = false;

                    grupoRUC.Visible = true;
                    grupoRazonSocial.Visible = true;
                    grupoDireccionFiscal.Visible = true;
                    break;
            }
        }

        protected void gvDetalle_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Eliminar")
            {
                int index = Convert.ToInt32(e.CommandArgument);
                var dt = DetalleVentas;
                if (index >= 0 && index < dt.Rows.Count)
                {
                    dt.Rows.RemoveAt(index);
                    DetalleVentas = dt;
                    gvDetalle.DataSource = dt;
                    gvDetalle.DataBind();
                    ActualizarTotales();

                    lblMensaje.CssClass = "text-info mb-2 d-block";
                    lblMensaje.Text = "Producto eliminado correctamente.";
                }
            }
        }

        protected void txtDescuento_TextChanged(object sender, EventArgs e)
        {
            ActualizarTotales();
        }

        private void ActualizarTotales()
        {
            DataTable dt = ViewState["DetalleVentas"] as DataTable;
            if (dt == null) return;

            decimal subtotal = dt.AsEnumerable().Sum(r => r.Field<decimal>("Subtotal"));
            decimal descuentoAplicado = 0;

            // Si hay un descuento seleccionado
            if (!string.IsNullOrEmpty(ddlDescuentos.SelectedValue))
            {
                int idDescuento = int.Parse(ddlDescuentos.SelectedValue);

                var cliente = new KawkiWebBusiness.KawkiWebWSDescuentos.DescuentosClient();
                var d = cliente.obtenerPorIdDescuento(idDescuento);

                if (d != null)
                {
                    int tipo = d.tipo_beneficio.tipo_beneficio_id;
                    int valor = d.valor_beneficio;

                    switch (tipo)
                    {
                        case 1: // %
                            descuentoAplicado = subtotal * (valor / 100m);
                            break;

                        case 2: // monto fijo
                            descuentoAplicado = valor;
                            break;

                        case 3: // envío gratis — NO descuenta subtotal
                            descuentoAplicado = 0;
                            break;
                    }
                }
            }

            decimal total = subtotal - descuentoAplicado;

            lblSubtotal.Text = subtotal.ToString("0.00");
            lblDescuento.Text = descuentoAplicado.ToString("0.00");
            lblTotal.Text = total.ToString("0.00");
        }


        protected void btnRegistrarVenta_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = string.Empty;
            lblMensaje.CssClass = "text-danger mb-2 d-block";

            // Validar que haya productos
            if (DetalleVentas.Rows.Count == 0)
            {
                lblMensaje.Text = "Agrega al menos un producto antes de registrar la venta.";
                return;
            }

            // Validar datos del cliente
            string mensajeError;
            if (!ValidarDatosCliente(out mensajeError))
            {
                lblMensaje.Text = mensajeError;
                return;
            }

            // Validar total
            decimal total = 0m;
            if (!decimal.TryParse(lblTotal.Text.Replace("S/", "").Trim(),NumberStyles.Any,CultureInfo.InvariantCulture,out total) || total <= 0)
            {
                lblMensaje.Text = "El total de la venta debe ser mayor a cero.";
                return;
            }

            try
            {
                // 1. Validar sesión y obtener el usuario del backend
                if (Session["UsuarioId"] == null)
                {
                    lblMensaje.Text = "La sesión expiró. Inicie sesión nuevamente.";
                    return;
                }

                int usuarioId = Convert.ToInt32(Session["UsuarioId"]);

                // 2. Crear el usuariosDTO del WSDL de Ventas
                var usuarioVendedor = new KawkiWebBusiness.KawkiWebWSVentas.usuariosDTO
                {
                    usuarioId = usuarioId,
                    usuarioIdSpecified = true,

                    activo = true,           // O el valor real del usuario
                    activoSpecified = true
                };

                // 3. Obtener datos del formulario
                string nombreCliente = txtNombreCliente.Text.Trim();
                string telefono = txtTelefono.Text.Trim();

                string canal = ddlCanal.SelectedValue;
                string notas = txtNotas.Text.Trim();

                if (string.IsNullOrEmpty(ddlCanal.SelectedValue))
                {
                    lblMensaje.Text = "Selecciona un canal de venta.";
                    lblMensaje.CssClass = "text-danger mb-2 d-block";
                    return;
                }

                var redSocial = new KawkiWebBusiness.KawkiWebWSVentas.redesSocialesDTO
                {
                    redSocialId = Convert.ToInt32(ddlCanal.SelectedValue),
                    redSocialIdSpecified = true,

                    nombre = ddlCanal.SelectedItem.Text
                };

                
                // CREAR DESCUENTO SI SELECCIONÓ UNO
                KawkiWebBusiness.KawkiWebWSVentas.descuentosDTO descuento = null;

                if (!string.IsNullOrEmpty(ddlDescuentos.SelectedValue))
                {
                    int idDescuento = int.Parse(ddlDescuentos.SelectedValue);

                    descuento = new KawkiWebBusiness.KawkiWebWSVentas.descuentosDTO
                    {
                        descuento_id = idDescuento,
                        descuento_idSpecified = true
                    };
                }


                // 4. Insertar venta
                VentasBO ventasBO = new VentasBO();
                int ventaId = ventasBO.InsertarVenta(usuarioVendedor, (double)total, descuento, redSocial);

                if (ventaId <= 0)
                {
                    lblMensaje.Text = "No se pudo registrar la venta.";
                    return;
                }
                // 5. Registrar comprobante de pago
                try
                {
                    var comprobanteBO = new KawkiWebBusiness.ComprobantesPagoBO();

                    string tipo = ddlComprobante.SelectedValue;

                    // 1. Tipo de comprobante
                    var tipoComprobante = new KawkiWebBusiness.KawkiWebWSComprobantesPago.tiposComprobanteDTO
                    {
                        tipo_comprobante_id = (tipo == "factura" ? 2 : 1),
                        tipo_comprobante_idSpecified = true,
                        nombre = (tipo == "factura" ? "Factura" : "Boleta")
                    };

                    // 2. Venta asociada
                    var ventaDTO = new KawkiWebBusiness.KawkiWebWSComprobantesPago.ventasDTO
                    {
                        venta_id = ventaId,
                        venta_idSpecified = true
                    };

                    // 3. Método de pago
                    var metodoPago = new KawkiWebBusiness.KawkiWebWSComprobantesPago.metodosPagoDTO
                    {
                        metodo_pago_id = Convert.ToInt32(ddlMetodoPago.SelectedValue),
                        metodo_pago_idSpecified = true
                    };

                    // 4. Datos del cliente (según tipo)
                    string dni = null;
                    string nombre = null;
                    string ruc = null;
                    string razon = null;
                    string direccionFiscal = null;
                    string telef = null;

                    if (tipo == "boleta-simple")
                    {
                        nombre = txtNombreCliente.Text.Trim();
                    }
                    else if (tipo == "boleta-dni")
                    {
                        nombre = txtNombreCliente.Text.Trim();
                        dni = txtDNI.Text.Trim();
                    }
                    else // factura
                    {
                        razon = txtRazonSocial.Text.Trim();
                        ruc = txtRUC.Text.Trim();
                        direccionFiscal = txtDireccionFiscal.Text.Trim();
                        telef = txtTelefono.Text.Trim();
                    }

                    // 5. Inserción 
                    int comprobanteId = comprobanteBO.InsertarComprobantePago(
                        tipoComprobante,
                        dni,
                        nombre,
                        ruc,
                        razon,
                        direccionFiscal,
                        telef,
                        (double)total,
                        ventaDTO,
                        metodoPago
                    );

                    if (comprobanteId <= 0)
                    {
                        lblMensaje.Text = "La venta se registró, pero el comprobante NO.";
                        return;
                    }
                }
                catch (Exception ex)
                {
                    lblMensaje.Text = "Error registrando comprobante: " + ex.Message;
                    return;
                }

                // 6. Registrar detalles de venta
                DetalleVentasBO detalleBO = new DetalleVentasBO();

                foreach (DataRow row in DetalleVentas.Rows)
                {
                    int prodVarId = Convert.ToInt32(row["ProductoId"]);
                    int cantidad = Convert.ToInt32(row["Cantidad"]);
                    double precio = double.Parse(
                        row["PrecioUnitario"].ToString().Replace("S/", "").Trim(),
                        CultureInfo.InvariantCulture
                    );

                    double subtotal = double.Parse(
                        row["Subtotal"].ToString().Replace("S/", "").Trim(),
                        CultureInfo.InvariantCulture
                    );


                    // Crear objeto productosVariantesDTO para DetalleVentasService
                    var productoVar = new KawkiWebBusiness.KawkiWebWSDetalleVentas.productosVariantesDTO
                    {
                        prod_variante_id = prodVarId,
                        prod_variante_idSpecified = true
                    };

                    //lblMensaje.Text =
                    //$"Debug:<br/>" +
                    //$"ProductoId = {prodVarId}<br/>" +
                    //$"Cantidad = {cantidad}<br/>" +
                    //$"Precio = '{row["PrecioUnitario"]}'<br/>" +
                    //$"Subtotal = '{row["Subtotal"]}'<br/>" +
                    //$"PrecioParseado = {precio}<br/>" +
                    //$"SubtotalParseado = {subtotal}<br/>" +
                    //$"Canal = '{ddlCanal.SelectedValue}'";
                    

                    detalleBO.InsertarDetalleVenta(productoVar, ventaId, cantidad, precio, subtotal);

                    //return;
                }

                // 6. Todo OK
                lblMensaje.CssClass = "text-success mb-2 d-block";
                lblMensaje.Text = "✓ Venta registrada correctamente.";

                LimpiarFormulario();
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text =
                    "ERROR → " + ex.Message +
                    "<br/>INNER → " + (ex.InnerException?.Message ?? "[sin inner]") +
                    "<br/>STACK → " + ex.StackTrace;
            }

        }


        /// <summary>
        /// Limpia todos los campos del formulario
        /// </summary>
        private void LimpiarFormulario()
        {
            // Limpiar datos del cliente
            txtNombreCliente.Text = string.Empty;
            txtTelefono.Text = string.Empty;
            //txtEmail.Text = string.Empty;
            //txtDireccion.Text = string.Empty;
            ddlCanal.SelectedIndex = 0;

            // Limpiar productos
            //txtProducto.Text = string.Empty;
            ddlProducto.SelectedIndex = 0;
            txtCantidad.Text = "1";
            txtPrecioUnitario.Text = string.Empty;

            // Limpiar comprobante y pago
            ddlComprobante.SelectedIndex = 0;
            ddlMetodoPago.SelectedIndex = 0;
            txtNotas.Text = string.Empty;

            // Limpiar detalle
            DetalleVentas.Clear();
            gvDetalle.DataSource = DetalleVentas;
            gvDetalle.DataBind();

            ActualizarTotales();

        }
    }
}
