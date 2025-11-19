using KawkiWebBusiness;
using KawkiWebBusiness.BO;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;
using KawkiWebBusiness.KawkiWebWSVentas;
using System;
using System.Collections.Generic;
using System.Data;
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
                gvDetalle.DataSource = DetalleVentas;
                gvDetalle.DataBind();
                ActualizarTotales();
            }
        }
        private DataTable ObtenerProductos()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Id", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Descripcion", typeof(string));
            dt.Columns.Add("Precio", typeof(decimal));
            dt.Columns.Add("Tallas", typeof(string));
            dt.Columns.Add("Tipo", typeof(string));
            dt.Columns.Add("Material", typeof(string));
            dt.Columns.Add("Color", typeof(string));
            dt.Columns.Add("Stock", typeof(string));
            dt.Columns.Add("Imagen", typeof(string));

            // Productos tipo Oxford
            dt.Rows.Add(1, "Oxford Clásico Beige", "Zapato oxford de cuero genuino con acabado premium, ideal para ocasiones formales y uso diario.", 150.90m, "35, 36, 39", "oxford", "clasico", "beige", "15,5,9", "~/Images/OxfordClasicoBeige.jpg");
            dt.Rows.Add(2, "Oxford Premium Negro", "Zapato oxford de diseño moderno y sofisticado, perfecto para eventos importantes.", 250.90m, "35, 36, 37, 38, 39", "oxford", "charol", "negro", "15,12,0,5,9", "~/Images/OxfordPremiumNegro.jpg");
            dt.Rows.Add(3, "Oxford Bicolor Café", "Zapato oxford con elegante combinación de tonos café, estilo vintage refinado.", 160.90m, "36, 37, 38, 39", "oxford", "combinado", "marron", "15,0,5,9", "~/Images/OxfordBicolorCafe.jpg");

            // Productos tipo Derby
            dt.Rows.Add(4, "Derby Elegante Marrón", "Derby de cuero con diseño tejido elegante, versátil para cualquier ocasión.", 215.90m, "35, 36, 37, 38, 39", "derby", "clasico", "marron", "15,12,0,5,9", "~/Images/DerbyClasicoMarron.jpg");
            dt.Rows.Add(5, "Derby Charol Crema", "Derby charol con suela gruesa y diseño moderno, máxima comodidad.", 210.90m, "35, 36, 37, 38, 39", "derby", "charol", "crema", "15,12,0,5,9", "~/Images/DerbyClasicoCrema.jpg");
            dt.Rows.Add(6, "Derby Clasico Negro", "Derby clásico con suela de goma antideslizante, ideal para caminar.", 169.90m, "36, 37, 38, 39", "derby", "clasico", "negro", "12,0,5,9", "~/Images/DerbyClasicoNegro.jpg");

            return dt;
        }
        private void CargarProductos()
        {
            var productos = ObtenerProductos();
            ddlProducto.DataSource = productos;
            ddlProducto.DataTextField = "Nombre";
            ddlProducto.DataValueField = "Precio"; // autocompleta con el precio
            ddlProducto.DataBind();

            ddlProducto.Items.Insert(0, new ListItem("-- Selecciona un producto --", ""));
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
                mensajeError = "El teléfono es obligatorio.";
                return false;
            }

            // Remover espacios y guiones
            telefono = telefono.Replace(" ", "").Replace("-", "");

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

            if (!decimal.TryParse(precioTexto, out precio))
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
        private bool ValidarDescuento(string descuentoTexto, decimal subtotal, out decimal descuento, out string mensajeError)
        {
            descuento = 0m;
            mensajeError = string.Empty;

            if (!decimal.TryParse(descuentoTexto, out descuento))
            {
                mensajeError = "El descuento debe ser un número válido.";
                return false;
            }

            if (descuento < 0)
            {
                mensajeError = "El descuento no puede ser negativo.";
                return false;
            }

            if (descuento > subtotal)
            {
                mensajeError = "El descuento no puede ser mayor al subtotal.";
                return false;
            }

            return true;
        }

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
            // Validar selección de producto
            if (string.IsNullOrEmpty(ddlProducto.SelectedValue))
            {
                lblMensaje.Text = "Selecciona un producto antes de agregar.";
                lblMensaje.CssClass = "text-danger";
                return;
            }

            string producto = ddlProducto.SelectedItem.Text;
            int cantidad;
            decimal precioUnitario;

            // Validaciones básicas
            if (!int.TryParse(txtCantidad.Text, out cantidad) || cantidad <= 0)
            {
                lblMensaje.Text = "La cantidad debe ser un número mayor que 0.";
                lblMensaje.CssClass = "text-danger";
                return;
            }

            if (!decimal.TryParse(txtPrecioUnitario.Text, out precioUnitario) || precioUnitario <= 0)
            {
                lblMensaje.Text = "El precio unitario debe ser un número válido.";
                lblMensaje.CssClass = "text-danger";
                return;
            }

            decimal subtotal = cantidad * precioUnitario;

            // Aquí asumes que tienes una lista o DataTable en memoria (ejemplo)
            DataTable dt = ViewState["DetalleVentas"] as DataTable;
            if (dt == null)
            {
                dt = new DataTable();
                dt.Columns.Add("Producto");
                dt.Columns.Add("Cantidad", typeof(int));
                dt.Columns.Add("PrecioUnitario", typeof(decimal));
                dt.Columns.Add("Subtotal", typeof(decimal));
            }

            dt.Rows.Add(producto, cantidad, precioUnitario, subtotal);
            ViewState["DetalleVentas"] = dt;

            gvDetalle.DataSource = dt;
            gvDetalle.DataBind();

            // Actualizar totales
            ActualizarTotales();

            // Limpiar campos
            ddlProducto.SelectedIndex = 0;
            txtCantidad.Text = "1";
            txtPrecioUnitario.Text = "";

            lblMensaje.Text = "Producto agregado correctamente.";
            lblMensaje.CssClass = "text-success";
        }

        protected void ddlProducto_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(ddlProducto.SelectedValue))
            {
                // Rellenar el precio según la selección
                txtPrecioUnitario.Text = ddlProducto.SelectedValue;
            }
            else
            {
                txtPrecioUnitario.Text = string.Empty;
            }
        }
        protected void ddlComprobante_SelectedIndexChanged(object sender, EventArgs e)
        {
            string tipo = ddlComprobante.SelectedValue;

            // Ocultar todo por defecto
            grupoDNI.Visible = false;
            grupoRUC.Visible = false;
            grupoRazonSocial.Visible = false;
            grupoDireccionFiscal.Visible = false;
            txtTelefono.Visible = true;
            txtNombreCliente.Visible = true;

            // Limpiar campos específicos
            txtDNI.Text = "";
            txtRUC.Text = "";
            txtRazonSocial.Text = "";
            txtDireccionFiscal.Text = "";

            switch (tipo)
            {
                case "boleta-simple":
                    txtTelefono.Visible = true;
                    break;

                case "boleta-dni":
                    grupoDNI.Visible = true;
                    txtTelefono.Visible = true;
                    break;

                case "factura":
                    // Para factura no se pide nombre personal
                    txtNombreCliente.Visible = false;
                    txtTelefono.Visible = false;

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
            decimal subtotal = 0m;
            foreach (DataRow row in DetalleVentas.Rows)
            {
                subtotal += row.Field<decimal>("Subtotal");
            }

            decimal descuento = 0m;
            string mensajeError;

            // Validar descuento
            if (!ValidarDescuento(txtDescuento.Text, subtotal, out descuento, out mensajeError))
            {
                // Si hay error, mostrar y usar 0
                if (!string.IsNullOrEmpty(mensajeError))
                {
                    lblMensaje.CssClass = "text-warning mb-2 d-block";
                    lblMensaje.Text = mensajeError;
                    descuento = 0;
                    txtDescuento.Text = "0.00";
                }
            }

            decimal total = subtotal - descuento;
            if (total < 0) total = 0;

            lblSubtotal.Text = $"S/ {subtotal:0.00}";
            lblDescuento.Text = $"S/ {descuento:0.00}";
            lblTotal.Text = $"S/ {total:0.00}";
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
            if (!decimal.TryParse(lblTotal.Text.Replace("S/", "").Trim(), out total) || total <= 0)
            {
                lblMensaje.Text = "El total de la venta debe ser mayor a cero.";
                return;
            }

            try
            {
                // 1️⃣ Recoger datos del formulario
                string nombreCliente = txtNombreCliente.Text.Trim();
                string telefono = txtTelefono.Text.Trim();
                string canal = ddlCanal.SelectedValue;
                string comprobante = ddlComprobante.SelectedValue;
                string metodoPago = ddlMetodoPago.SelectedValue;
                string notas = txtNotas.Text.Trim();

                string ruc = txtRUC.Text.Trim();
                string razonSocial = txtRazonSocial.Text.Trim();
                string direccion = txtDireccionFiscal.Text.Trim();
                string dni = txtDNI.Text.Trim();

                // 2️ CREAR OBJETOS DTO PARA EL WS
                // Usuario
                var usuario = new KawkiWebBusiness.KawkiWebWSVentas.usuariosDTO
                {
                    nombre = nombreCliente,
                    telefono = telefono,
                    dni = dni,
                };

                // Descuento (aún no usas, enviamos null)
                descuentosDTO descuento = null;

                // Canal = red social
                var redSocial = new redesSocialesDTO
                {
                    nombre = canal
                };


                // 3️⃣ INSERTAR VENTA Y OBTENER EL ID NUEVO

                VentasBO ventasBO = new VentasBO();

                int ventaId = ventasBO.InsertarVenta(usuario, (double)total, descuento, redSocial);

                if (ventaId <= 0)
                {
                    lblMensaje.Text = "No se pudo registrar la venta.";
                    return;
                }

                // 4️ REGISTRAR DETALLES DE VENTA

                DetalleVentasBO detalleBO = new DetalleVentasBO();

                foreach (GridViewRow row in DetalleVentas.Rows)
                {
                    int prodVarId = Convert.ToInt32(row.Cells[0].Text);
                    int cantidad = Convert.ToInt32(row.Cells[2].Text);
                    double precio = Convert.ToDouble(row.Cells[3].Text);
                    double subtotal = Convert.ToDouble(row.Cells[4].Text);

                    // Crear objeto productosVariantesDTO
                    var productoVar = new KawkiWebBusiness.KawkiWebWSDetalleVentas.productosVariantesDTO
                    {
                        prod_variante_id = prodVarId
                    };

                    detalleBO.InsertarDetalleVenta(productoVar, ventaId, cantidad, precio, subtotal);
                }

                // 5 TODO OK → MENSAJE

                lblMensaje.CssClass = "text-success mb-2 d-block";
                lblMensaje.Text = "✓ Venta registrada correctamente.";

                LimpiarFormulario();
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger mb-2 d-block";
                lblMensaje.Text = $"Error al registrar la venta: {ex.Message}";
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
            txtDescuento.Text = "0.00";
            txtNotas.Text = string.Empty;

            // Limpiar detalle
            DetalleVentas.Clear();
            gvDetalle.DataSource = DetalleVentas;
            gvDetalle.DataBind();

            ActualizarTotales();

        }
    }
}