using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class GestionProductos : Page
    {
        private static DataTable productosMemoria = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarProductos();
            }
        }

        // =====================================================
        // 🔹 Inicializar lista de productos (simulada)
        // =====================================================
        private void CargarProductos()
        {
            gvProductos.DataSource = ObtenerProductosSimulados();
            gvProductos.DataBind();
        }

        private DataTable ObtenerProductosSimulados()
        {
            if (productosMemoria == null)
            {
                productosMemoria = new DataTable();
                productosMemoria.Columns.Add("Codigo", typeof(string));
                productosMemoria.Columns.Add("Nombre", typeof(string));
                productosMemoria.Columns.Add("Categoria", typeof(string));
                productosMemoria.Columns.Add("Color", typeof(string));
                productosMemoria.Columns.Add("Precio", typeof(decimal));
                productosMemoria.Columns.Add("Stock", typeof(int));
                productosMemoria.Columns.Add("Descripcion", typeof(string));
                productosMemoria.Columns.Add("Activo", typeof(bool));

                // Productos iniciales
                productosMemoria.Rows.Add("#0001", "Oxford Clásico", "Oxford", "Negro", 289.90m, 15, "Zapato de cuero clásico", true);
                productosMemoria.Rows.Add("#0002", "Derby Elegante", "Derby", "Marrón", 259.90m, 5, "Zapato marrón formal", true);
            }

            return productosMemoria;
        }

        // =====================================================
        // 🔹 Guardar producto (nuevo o editar)
        // =====================================================
        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                bool esEdicion = hfCodigo.Value != "0";

                string nombre = txtNombre.Text.Trim();
                string categoria = ddlCategoria.SelectedValue;
                string color = txtColor.Text.Trim();
                string precioTexto = txtPrecio.Text.Trim();
                string stockTexto = txtStock.Text.Trim();
                string descripcion = txtDescripcion.Text.Trim();

                // === VALIDACIONES ===
                if (string.IsNullOrEmpty(nombre))
                {
                    lblMensaje.Text = "El nombre del producto es obligatorio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (string.IsNullOrEmpty(categoria))
                {
                    lblMensaje.Text = "Debe seleccionar una categoría.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (string.IsNullOrEmpty(color))
                {
                    lblMensaje.Text = "Debe ingresar un color.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!Regex.IsMatch(precioTexto, @"^\d+(\.\d{1,2})?$"))
                {
                    lblMensaje.Text = "Ingrese un precio válido (número o decimal).";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!Regex.IsMatch(stockTexto, @"^\d+$"))
                {
                    lblMensaje.Text = "El stock debe ser un número entero.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                decimal precio = decimal.Parse(precioTexto);
                int stock = int.Parse(stockTexto);

                DataTable dt = ObtenerProductosSimulados();

                if (esEdicion)
                {
                    // === EDITAR ===
                    string codigo = hfCodigo.Value;
                    DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<string>("Codigo") == codigo);
                    if (fila != null)
                    {
                        fila["Nombre"] = nombre;
                        fila["Categoria"] = categoria;
                        fila["Color"] = color;
                        fila["Precio"] = precio;
                        fila["Stock"] = stock;
                        fila["Descripcion"] = descripcion;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Producto actualizado correctamente.";
                }
                else
                {
                    // === NUEVO ===
                    string nuevoCodigo = "#" + (dt.Rows.Count + 1).ToString("0000");
                    dt.Rows.Add(nuevoCodigo, nombre, categoria, color, precio, stock, descripcion, true);

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Producto registrado correctamente.";
                }

                LimpiarFormulario();
                CargarProductos();
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModal", "cerrarModal(); mostrarMensajeExito('Operación exitosa');", true);
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error: " + ex.Message;
            }
        }

        private void MantenerModalAbierto(bool esEdicion)
        {
            string script = esEdicion ? "abrirModalEditar();" : "abrirModalRegistro();";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", script, true);
        }

        // =====================================================
        // 🔹 Confirmar eliminación
        // =====================================================
        protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        {
            try
            {
                string codigo = hfCodigoEliminar.Value;
                EliminarProducto(codigo);
                CargarProductos();

                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModalEliminar",
                    "cerrarModalConfirmacion(); mostrarMensajeExito('Producto eliminado correctamente');", true);
            }
            catch (Exception ex)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "ErrorEliminar",
                    $"cerrarModalConfirmacion(); mostrarMensajeError('Error al eliminar: {ex.Message.Replace("'", "\\'")}');", true);
            }
        }

        protected void gvProductos_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            string codigo = e.CommandArgument.ToString();
            DataTable dt = ObtenerProductosSimulados();
            DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<string>("Codigo") == codigo);

            if (fila == null) return;

            switch (e.CommandName)
            {
                case "Abastecer":
                    fila["Stock"] = Convert.ToInt32(fila["Stock"]) + 10;
                    CargarProductos();
                    ScriptManager.RegisterStartupScript(this, GetType(), "msgAbastecer", "mostrarMensajeExito('Stock aumentado en 10 unidades');", true);
                    break;

                case "CambiarEstado":
                    bool activo = Convert.ToBoolean(fila["Activo"]);
                    fila["Activo"] = !activo;
                    CargarProductos();
                    string estado = (bool)fila["Activo"] ? "activado" : "inactivado";
                    ScriptManager.RegisterStartupScript(this, GetType(), "msgEstado", $"mostrarMensajeExito('Producto {estado} correctamente');", true);
                    break;
            }
        }


        private void EliminarProducto(string codigo)
        {
            DataTable dt = ObtenerProductosSimulados();
            DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<string>("Codigo") == codigo);
            if (fila != null)
            {
                dt.Rows.Remove(fila);
            }
            else
            {
                throw new Exception("No se encontró el producto con código: " + codigo);
            }
        }

        // =====================================================
        // 🔹 Auxiliares
        // =====================================================
        private void LimpiarFormulario()
        {
            hfCodigo.Value = "0";
            txtNombre.Text = "";
            txtPrecio.Text = "";
            ddlCategoria.SelectedIndex = 0;
            txtColor.Text = "";
            txtStock.Text = "";
            txtDescripcion.Text = "";
        }
    }
}
