using System;
using System.Web.UI;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSUsuarios;

namespace KawkiWeb
{
    public partial class Login : Page
    {
        private UsuarioBO usuarioBO = new UsuarioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var rol = (Session["Rol"] as string) ?? string.Empty;

                if (rol.Equals("admin", StringComparison.OrdinalIgnoreCase) ||
                    rol.Equals("vendedor", StringComparison.OrdinalIgnoreCase))
                {
                    Response.Redirect("Productos.aspx");
                }
            }
        }

        protected void btnLogin_Click(object sender, EventArgs e)
        {
            string usuario = txtUsuario.Text.Trim();
            string clave = txtClave.Text.Trim();

            // Validación básica
            if (string.IsNullOrEmpty(usuario) || string.IsNullOrEmpty(clave))
            {
                lblMensaje.Text = "Por favor, ingresa tus credenciales.";
                return;
            }

            try
            {
                var usuarioBO = new UsuarioBO();
                var usuarioDTO = usuarioBO.AutenticarUsuario(usuario, clave);

                if (usuarioDTO != null)
                {
                    // Determinar rol desde el tipoUsuario del backend
                    string rol = "";
                    if (usuarioDTO.tipoUsuario != null)
                    {
                        string nombreRol = usuarioDTO.tipoUsuario.nombre?.Trim().ToLower();
                        if (nombreRol == "administrador")
                            rol = "admin";
                        else if (nombreRol == "vendedor")
                            rol = "vendedor";
                    }

                    // Si no hay tipoUsuario, lo tratamos como usuario sin rol
                    if (string.IsNullOrEmpty(rol))
                    {
                        lblMensaje.Text = "Error: El usuario no tiene rol asignado.";
                        return;
                    }

                    // Guardar sesión
                    Session["Usuario"] = usuarioDTO.nombreUsuario;
                    Session["Rol"] = rol;
                    Session["UsuarioNombreCompleto"] = usuarioDTO.nombre + " " + usuarioDTO.apePaterno;
                    Session["Email"] = usuarioDTO.correo ?? "";

                    // Redirección según el rol
                    if (rol == "admin")
                    {
                        Response.Redirect("Productos.aspx");
                    }
                    else if (rol == "vendedor")
                    {
                        Response.Redirect("Productos.aspx");
                    }
                    else
                    {
                        lblMensaje.Text = "Rol no reconocido. Contacte al administrador.";
                    }
                }
                else
                {
                    lblMensaje.Text = "Usuario o contraseña incorrectos.";
                }
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al iniciar sesión: " + ex.Message;
            }
        }
    }
}