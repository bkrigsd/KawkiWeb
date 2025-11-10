using System;
using System.Data;
using System.Linq;
using System.Security.Claims;
using System.Text.RegularExpressions;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class RegistroUsuario : Page
    {
        // 🧠 Tabla en memoria (simula la base de datos)
        private static DataTable usuariosMemoria = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Solo los administradores pueden acceder
                if (Session["Rol"] == null || Session["Rol"].ToString() != "admin")
                {
                    Response.Redirect("Login.aspx");
                    return;
                }

                CargarUsuarios();
            }
        }

        // =====================================================
        // 🔹 Carga inicial
        // =====================================================
        private void CargarUsuarios()
        {
            gvUsuarios.DataSource = ObtenerUsuariosSimulados();
            gvUsuarios.DataBind();
        }

        private DataTable ObtenerUsuariosSimulados()
        {
            if (usuariosMemoria == null)
            {
                usuariosMemoria = new DataTable();
                usuariosMemoria.Columns.Add("IdUsuario", typeof(int));
                usuariosMemoria.Columns.Add("Nombre", typeof(string));
                usuariosMemoria.Columns.Add("ApellidoPaterno", typeof(string));
                usuariosMemoria.Columns.Add("DNI", typeof(string));
                usuariosMemoria.Columns.Add("Usuario", typeof(string));
                usuariosMemoria.Columns.Add("Email", typeof(string));
                usuariosMemoria.Columns.Add("Telefono", typeof(string));
                usuariosMemoria.Columns.Add("Rol", typeof(string));
                usuariosMemoria.Columns.Add("Activo", typeof(bool));
                usuariosMemoria.Columns.Add("Clave", typeof(string));

                // Datos iniciales
                usuariosMemoria.Rows.Add(1, "Juan", "Pérez", "1234567", "juanp", "juan@kawki.com", "987654321", "vendedor", true, "clave123");
                usuariosMemoria.Rows.Add(2, "María", "García", "7654321", "mariag", "maria@kawki.com", "912345678", "admin", true, "admin123");
            }

            return usuariosMemoria;
        }

        // =====================================================
        // 🔹 Botón Guardar / Registrar o Actualizar
        // =====================================================
        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                bool esEdicion = hfIdUsuario.Value != "0";
                string nombre = txtNombre.Text.Trim();
                string apellido = txtApellidoPaterno.Text.Trim();
                string dni = txtDNI.Text.Trim();
                string usuario = txtUsuario.Text.Trim();
                string email = txtEmail.Text.Trim();
                string telefono = txtTelefono.Text.Trim();
                string clave = txtClave.Text.Trim();
                string rol = ddlRol.SelectedValue;

                // === VALIDACIONES ===
                if (string.IsNullOrEmpty(nombre))
                {
                    lblMensaje.Text = "El nombre es obligatorio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (string.IsNullOrEmpty(apellido))
                {
                    lblMensaje.Text = "El apellido es obligatorio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (string.IsNullOrEmpty(usuario))
                {
                    lblMensaje.Text = "El nombre de usuario es obligatorio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!Regex.IsMatch(dni, @"^\d{7}$"))
                {
                    lblMensaje.Text = "El DNI debe tener 7 dígitos numéricos.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!Regex.IsMatch(email, @"^[\w\.-]+@([\w-]+\.)+[\w-]{2,}$"))
                {
                    lblMensaje.Text = "Ingrese un correo electrónico válido.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!Regex.IsMatch(telefono, @"^\d{9}$"))
                {
                    lblMensaje.Text = "El teléfono debe tener 9 dígitos.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                // ✅ Contraseña obligatoria solo en registro
                if (!esEdicion && string.IsNullOrEmpty(clave))
                {
                    lblMensaje.Text = "La contraseña es obligatoria para registrar un usuario nuevo.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                else if (!string.IsNullOrEmpty(clave) && !Regex.IsMatch(clave, @"^.{6,}$"))
                {
                    lblMensaje.Text = "La contraseña debe tener al menos 6 caracteres.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (string.IsNullOrEmpty(rol))
                {
                    lblMensaje.Text = "Seleccione un rol.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                DataTable dt = ObtenerUsuariosSimulados();

                if (esEdicion)
                {
                    int idEditar = Convert.ToInt32(hfIdUsuario.Value);
                    DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<int>("IdUsuario") == idEditar);

                    if (fila != null)
                    {
                        // Verificar usuario duplicado
                        if (dt.AsEnumerable().Any(r =>
                            r.Field<string>("Usuario").Equals(usuario, StringComparison.OrdinalIgnoreCase)
                            && r.Field<int>("IdUsuario") != idEditar))
                        {
                            lblMensaje.Text = "El nombre de usuario ya está registrado.";
                            MantenerModalAbierto(true);
                            return;
                        }

                        fila["Nombre"] = nombre;
                        fila["ApellidoPaterno"] = apellido;
                        fila["DNI"] = dni;
                        fila["Usuario"] = usuario;
                        fila["Email"] = email;
                        fila["Telefono"] = telefono;
                        fila["Rol"] = rol;
                        fila["Clave"] = clave;

                        // ✅ Solo actualiza la contraseña si se ingresó
                        if (!string.IsNullOrEmpty(clave))
                        {
                            // fila["Clave"] = clave; // ejemplo si usas BD real
                        }
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario actualizado correctamente.";
                }
                else
                {
                    // === REGISTRAR ===
                    if (dt.AsEnumerable().Any(r => r.Field<string>("Usuario").Equals(usuario, StringComparison.OrdinalIgnoreCase)))
                    {
                        lblMensaje.Text = "El nombre de usuario ya está registrado.";
                        MantenerModalAbierto(false);
                        return;
                    }

                    int nuevoId = dt.AsEnumerable().Select(r => r.Field<int>("IdUsuario")).DefaultIfEmpty(0).Max() + 1;
                    dt.Rows.Add(nuevoId, nombre, apellido, dni, usuario, email, telefono, rol, true, clave);

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario registrado correctamente.";
                }

                LimpiarFormulario();
                CargarUsuarios();
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModal", "cerrarModal();", true);
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

        protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        {

            try
            {
                // 1. Obtenemos el ID del usuario a eliminar desde el campo oculto
                int idUsuario = Convert.ToInt32(hfIdEliminar.Value);

                // 2. Llamamos al método que elimina al usuario de la "base de datos"
                EliminarUsuario(idUsuario);

                // 3. Recargamos la tabla para que el cambio se refleje en la pantalla
                CargarUsuarios();

                // 4. Cerramos el modal y mostramos un mensaje de éxito usando JavaScript
                //    ScriptManager.RegisterStartupScript ejecuta este código DESPUÉS de que la página se ha recargado.
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModalEliminar",
                    "cerrarModalConfirmacion(); mostrarMensajeExito('Usuario eliminado correctamente');", true);
            }
            catch (Exception ex)
            {
                // Si algo sale mal, mostramos un mensaje de error
                ScriptManager.RegisterStartupScript(this, GetType(), "ErrorEliminar",
                    "cerrarModalConfirmacion(); mostrarMensajeError('Error al eliminar: " + ex.Message.Replace("'", "\\'") + "');", true);
            }
        }

        private void EliminarUsuario(int id)
        {
            // Obtenemos la tabla
            DataTable dt = ObtenerUsuariosSimulados();

            // Buscamos la fila que corresponde al ID
            DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<int>("IdUsuario") == id);

            if (fila != null)
            {
                // Eliminamos la fila
                dt.Rows.Remove(fila);
            }
            else
            {
                // Si no encontramos la fila, lanzamos una excepción para que el método anterior lo capture
                throw new Exception("No se encontró el usuario con ID: " + id);
            }
        }

        private void CargarUsuarioParaEditar(int idUsuario)
        {
            DataTable dt = ObtenerUsuariosSimulados();
            DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<int>("IdUsuario") == idUsuario);

            if (fila != null)
            {
                hfIdUsuario.Value = fila["IdUsuario"].ToString();
                txtNombre.Text = fila["Nombre"].ToString();
                txtApellidoPaterno.Text = fila["ApellidoPaterno"].ToString();
                txtDNI.Text = fila["DNI"].ToString();
                txtUsuario.Text = fila["Usuario"].ToString();
                txtEmail.Text = fila["Email"].ToString();
                txtTelefono.Text = fila["Telefono"].ToString();
                ddlRol.SelectedValue = fila["Rol"].ToString();
                txtClave.Text = fila["Clave"].ToString();
                Response.Write("Clave cargada: " + fila["Clave"].ToString());


                lblMensaje.Text = "";
                ScriptManager.RegisterStartupScript(this, GetType(), "AbrirModalEditar", "abrirModalEditar();", true);
            }
        }

        // =====================================================
        // 🔹 Auxiliares
        // =====================================================
        private void LimpiarFormulario()
        {
            hfIdUsuario.Value = "0";
            txtNombre.Text = "";
            txtApellidoPaterno.Text = "";
            txtDNI.Text = "";
            txtUsuario.Text = "";
            txtEmail.Text = "";
            txtTelefono.Text = "";
            txtClave.Text = "";
            ddlRol.SelectedIndex = 0;
        }
    }
}