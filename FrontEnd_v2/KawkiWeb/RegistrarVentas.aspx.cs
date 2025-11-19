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
        /// Valida que el nombre del producto sea válido
        /// </summary>
        private bool ValidarProducto(string producto, out string mensajeError)
        {
            mensajeError = string.Empty;

            if (string.IsNullOrWhiteSpace(producto))
            {
                mensajeError = "El nombre del producto es obligatorio.";
                return false;
            }

            if (producto.Length < 3)
            {
                mensajeError = "El nombre del producto debe tener al menos 3 caracteres.";
                return false;
            }

            if (producto.Length > 100)
            {
                mensajeError = "El nombre del producto es demasiado largo (máximo 100 caracteres).";
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

            if (cantidad > 1000)
            {
                mensajeError = "La cantidad no puede ser mayor a 1000 unidades.";
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

            // Validar email (opcional)
            if (!ValidarEmail(txtEmail.Text.Trim(), out mensajeError))
            {
                return false;
            }

            // Validar dirección
            if (!ValidarDireccion(txtDireccion.Text.Trim(), out mensajeError))
            {
                return false;
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

        //protected void btnAgregarProducto_Click(object sender, EventArgs e)
        //{
        //    lblMensaje.Text = string.Empty;
        //    lblMensaje.CssClass = "text-danger mb-2 d-block";

        //    string producto = txtProducto.Text.Trim();
        //    string mensajeError;

        //    // Validar producto
        //    if (!ValidarProducto(producto, out mensajeError))
        //    {
        //        lblMensaje.Text = mensajeError;
        //        return;
        //    }

        //    // Validar cantidad
        //    int cantidad;
        //    if (!ValidarCantidad(txtCantidad.Text, out cantidad, out mensajeError))
        //    {
        //        lblMensaje.Text = mensajeError;
        //        return;
        //    }

        //    // Validar precio
        //    decimal precio;
        //    if (!ValidarPrecio(txtPrecioUnitario.Text, out precio, out mensajeError))
        //    {
        //        lblMensaje.Text = mensajeError;
        //        return;
        //    }

        //    // Validar que el producto no esté duplicado
        //    var dt = DetalleVentas;
        //    foreach (DataRow row in dt.Rows)
        //    {
        //        if (row["Producto"].ToString().Equals(producto, StringComparison.OrdinalIgnoreCase))
        //        {
        //            lblMensaje.Text = "Este producto ya está en la lista. Elimínalo si deseas agregarlo nuevamente.";
        //            return;
        //        }
        //    }

        //    // Agregar producto
        //    var newRow = dt.NewRow();
        //    newRow["Producto"] = producto;
        //    newRow["Cantidad"] = cantidad;
        //    newRow["PrecioUnitario"] = precio;
        //    newRow["Subtotal"] = cantidad * precio;
        //    dt.Rows.Add(newRow);
        //    DetalleVentas = dt;

        //    gvDetalle.DataSource = dt;
        //    gvDetalle.DataBind();

        //    // Limpiar campos
        //    txtProducto.Text = string.Empty;
        //    txtCantidad.Text = "1";
        //    txtPrecioUnitario.Text = string.Empty;

        //    ActualizarTotales();

        //    // Mensaje de éxito
        //    lblMensaje.CssClass = "text-success mb-2 d-block";
        //    lblMensaje.Text = "Producto agregado correctamente.";
        //}
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

            // Validar que el total sea mayor a 0
            decimal total = 0m;
            if (!decimal.TryParse(lblTotal.Text.Replace("S/", "").Trim(), out total) || total <= 0)
            {
                lblMensaje.Text = "El total de la venta debe ser mayor a cero.";
                return;
            }

            try
            {
                // TODO: Aquí iría la lógica real de guardado en BD
                // Ejemplo:
                // string nombreCliente = txtNombreCliente.Text.Trim();
                // string telefono = txtTelefono.Text.Trim();
                // string email = txtEmail.Text.Trim();
                // string direccion = txtDireccion.Text.Trim();
                // string canal = ddlCanal.SelectedValue;
                // string comprobante = ddlComprobante.SelectedValue;
                // string metodoPago = ddlMetodoPago.SelectedValue;
                // string notas = txtNotas.Text.Trim();

                // VentasDAO.RegistrarVenta(nombreCliente, telefono, email, direccion, 
                //                         canal, comprobante, metodoPago, notas, 
                //                         DetalleVentas, total);

                lblMensaje.CssClass = "text-success mb-2 d-block";
                lblMensaje.Text = "✓ Venta registrada correctamente.";

                // Limpiar formulario después de registrar
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
            txtEmail.Text = string.Empty;
            txtDireccion.Text = string.Empty;
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