using System;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
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
            // Deshabilitar cache agresivamente
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.Now.AddSeconds(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.AddHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            Response.AddHeader("Pragma", "no-cache");
            Response.AddHeader("Expires", "0");

            if (!IsPostBack)
            {
                var rol = Session["Rol"] as string;
                if (string.IsNullOrEmpty(rol))
                {
                    //Response.Redirect("Login.aspx", false);  // Sin cachear
                    // Redirigir a página de error en lugar de 404 manual
                    Response.Redirect("Error404.aspx", false);
                    return;
                }

                if (rol == "vendedor")
                {
                    // Un vendedor no debe ver el registro de usuarios
                    Response.Redirect("Productos.aspx", false);
                    return;
                }

                CargarUsuarios();
            }
        }

        // Cargar usuarios desde el servicio SOAP
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

        //  Guardar (insertar o modificar)
        protected void btnGuardar_Click(object sender, EventArgs e)
        {
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
                // Removidas las llamadas a usuarioBO.validar* porque no existen en el WS.
                // Implementa validaciones locales aquí si es necesario, o agrégalas al WS.
                // Ejemplo: Validaciones básicas con Regex (ya están en el código original).

                if (!Regex.IsMatch(dni, @"^\d{8}$"))
                {
                    lblErrorDNI.Text = "El DNI debe tener 8 dígitos numéricos.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!Regex.IsMatch(email, @"^[\w\.-]+@([\w-]+\.)+[\w-]{2,}$"))
                {
                    lblErrorEmail.Text = "Ingrese un correo electrónico válido.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!Regex.IsMatch(telefono, @"^\d{9}$"))
                {
                    lblErrorTelefono.Text = "El teléfono debe tener 9 dígitos.";
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
                    tipoUsuario.tipoUsuarioId = 1;
                    tipoUsuario.nombre = "Vendedor";
                }

                tipoUsuario.tipoUsuarioIdSpecified = true;

                // Determinar 'activo'
                bool activo = true; // Por defecto para nuevos usuarios
                if (esEdicion)
                {
                    // Para ediciones, obtener el valor actual (puedes cambiarlo si agregas un checkbox en ASPX)
                    var usuarioExistente = usuarioBO.ObtenerPorIdUsuario(Convert.ToInt32(hfIdUsuario.Value));
                    if (usuarioExistente != null)
                    {
                        activo = usuarioExistente.activo; // Asume que 'activo' es un bool en usuariosDTO
                    }
                }

                // Guardar
                if (esEdicion)
                {
                    int idUsuario = Convert.ToInt32(hfIdUsuario.Value);
                    if (string.IsNullOrEmpty(clave))
                    {
                        var usuarioExistente = usuarioBO.ObtenerPorIdUsuario(idUsuario);
                        if (usuarioExistente != null)
                        {
                            clave = usuarioExistente.contrasenha;
                        }
                    }
                    // Actualizado: Agregado 'activo'
                    int? resultado = usuarioBO.ModificarUsuario(idUsuario, nombre, apellido, dni, telefono, email, usuario, clave, tipoUsuario, activo);

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "❌ No se pudo actualizar el usuario.";
                        MantenerModalAbierto(true);
                        return;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario actualizado correctamente.";
                }
                else
                {
                    // Actualizado: Agregado 'activo'
                    int? resultado = usuarioBO.InsertarUsuario(nombre, apellido, dni, telefono, email, usuario, clave, tipoUsuario, activo);

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "❌ Error: No se pudo registrar el usuario.";
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