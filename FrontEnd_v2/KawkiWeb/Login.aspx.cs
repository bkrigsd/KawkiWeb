using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Login : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Si ya está logueado, redirige según el rol
                var rol = (Session["Rol"] as string) ?? string.Empty;

                if (rol.Equals("admin", StringComparison.OrdinalIgnoreCase))
                {
                    Response.Redirect("InicioAdmin.aspx");
                }
                else if (rol.Equals("vendedor", StringComparison.OrdinalIgnoreCase))
                {
                    Response.Redirect("InicioVendedor.aspx");
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

            // ADMIN
            if (usuario.Equals("admin", StringComparison.OrdinalIgnoreCase) &&
                clave.Equals("admin", StringComparison.OrdinalIgnoreCase))
            {
                Session["Rol"] = "admin";
                Session["Usuario"] = "admin";
                Response.Redirect("InicioAdmin.aspx");
                return;
            }

            // VENDEDOR
            if (usuario.Equals("vendedor", StringComparison.OrdinalIgnoreCase) &&
                clave.Equals("vendedor", StringComparison.OrdinalIgnoreCase))
            {
                Session["Rol"] = "vendedor";
                Session["Usuario"] = "vendedor";
                Response.Redirect("InicioVendedor.aspx");
                return;
            }

            // Si más adelante agregas validación real, la pones aquí

            lblMensaje.Text = "Usuario o contraseña incorrectos.";
        }
    }
}