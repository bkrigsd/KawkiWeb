using System;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web.UI;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSUsuarios;

namespace KawkiWeb
{
    public partial class RegistroUsuario : Page
    {
        private UsuarioBO usuarioBO = new UsuarioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var rol = Session["Rol"] as string;
                if (string.IsNullOrEmpty(rol))
                {
                    Response.Redirect("Login.aspx");
                    return;
                }

                if (rol == "vendedor")
                {
                    // Un vendedor no debe ver el registro de usuarios
                    Response.Redirect("Productos.aspx");
                    return;
                }

                CargarUsuarios();
            }
        }

        // =====================================================
        // 🔹 Cargar usuarios desde el servicio SOAP
        // =====================================================
        private void CargarUsuarios()
        {
            try
            {
                var lista = usuarioBO.ListarTodosUsuario();
                lblMensaje.Text = $"Usuarios devueltos: {(lista == null ? 0 : lista.Count)}";
                gvUsuarios.DataSource = lista;
                gvUsuarios.DataBind();
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al cargar usuarios: " + ex.Message;
                lblMensaje.CssClass = "text-danger d-block mb-2";
            }
        }

        // =====================================================
        // 🔹 Guardar (insertar o modificar)
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
                bool estado = false;

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
                if (!Regex.IsMatch(dni, @"^\d{8}$"))
                {
                    lblMensaje.Text = "El DNI debe tener 8 dígitos numéricos.";
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

                if (!esEdicion && string.IsNullOrEmpty(clave))
                {
                    lblMensaje.Text = "La contraseña es obligatoria para registrar un usuario nuevo.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                else if (!string.IsNullOrEmpty(clave) && clave.Length < 6)
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

                // Crear objeto tipoUsuario
                // === Crear objeto tipoUsuario ===
                var tipoUsuario = new tiposUsuarioDTO();

                string rolSeleccionado = ddlRol.SelectedValue.Trim().ToLower();

                if (rolSeleccionado == "admin" || rolSeleccionado == "administrador")
                {
                    tipoUsuario.tipoUsuarioId = 2;
                    tipoUsuario.nombre = "Administrador";
                }
                else if (rolSeleccionado == "vendedor")
                {
                    tipoUsuario.tipoUsuarioId = 1;
                    tipoUsuario.nombre = "Vendedor";
                }
                else
                {
                    // Valor por defecto si no se detecta correctamente
                    tipoUsuario.tipoUsuarioId = 1;
                    tipoUsuario.nombre = "Vendedor";
                }

                tipoUsuario.tipoUsuarioIdSpecified = true;



                // Guardar
                if (esEdicion)
                {
                    int idUsuario = Convert.ToInt32(hfIdUsuario.Value);
                    if (string.IsNullOrEmpty(clave))
                    {
                        // Si no se escribió una nueva contraseña, se conserva la actual
                        var usuarioExistente = usuarioBO.ObtenerPorIdUsuario(idUsuario);
                        if (usuarioExistente != null)
                        {
                            clave = usuarioExistente.contrasenha;
                        }
                    }
                    int? resultado = usuarioBO.ModificarUsuario(idUsuario, nombre, apellido, dni, telefono, email, usuario, clave, tipoUsuario, estado);

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "❌ No se pudo actualizar el usuario. "
                            + "Verifique que los datos sean válidos o que la contraseña tenga al menos 8 caracteres.";
                        MantenerModalAbierto(true);
                        return;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario actualizado correctamente.";

                }
                else
                {
                    int? resultado = usuarioBO.InsertarUsuario(nombre, apellido, dni, telefono,email, usuario, clave, tipoUsuario, estado);

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "❌ Error: No se pudo registrar el usuario. "
                            + "Verifique que el correo, usuario o DNI no estén duplicados.";
                        MantenerModalAbierto(false);
                        return;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario registrado correctamente.";

                }

                CargarUsuarios();
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModal", "cerrarModal();", true);
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al guardar: " + ex.Message;
            }
        }

        // =====================================================
        // 🔹 Eliminar usuario
        // =====================================================
        //protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        int idUsuario = Convert.ToInt32(hfIdEliminar.Value);
        //        usuarioBO.EliminarUsuario(idUsuario);
        //        CargarUsuarios();

        //        ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModalEliminar",
        //            "cerrarModalConfirmacion(); mostrarMensajeExito('Usuario eliminado correctamente');", true);
        //    }
        //    catch (Exception ex)
        //    {
        //        ScriptManager.RegisterStartupScript(this, GetType(), "ErrorEliminar",
        //            "cerrarModalConfirmacion(); mostrarMensajeError('Error al eliminar: " + ex.Message.Replace("'", "\\'") + "');", true);
        //    }
        //}

        // =====================================================
        // 🔹 Utilidades
        // =====================================================
        private void MantenerModalAbierto(bool esEdicion)
        {
            string script = esEdicion ? "abrirModalEditar();" : "abrirModalRegistro();";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", script, true);
        }
    }
}
