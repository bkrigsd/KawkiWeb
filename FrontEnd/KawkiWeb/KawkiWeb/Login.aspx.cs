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

            // VENDEDOR (lo que ya tenías)
            if (usuario.Equals("vendedor", StringComparison.OrdinalIgnoreCase) &&
                clave.Equals("vendedor", StringComparison.OrdinalIgnoreCase))
            {
                Session["Rol"] = "vendedor";
                Session["Usuario"] = "vendedor";
                Response.Redirect("InicioVendedor.aspx");
                return;
            }

            // CLIENTE
            if (usuario.Equals("cliente", StringComparison.OrdinalIgnoreCase) &&
                clave.Equals("cliente", StringComparison.OrdinalIgnoreCase))
            {
                Session["Rol"] = "cliente";
                Session["Usuario"] = "cliente";
                // Puede ir al mismo inicio público
                Response.Redirect("Inicio.aspx");
                return;
            }

            lblMensaje.Text = "Usuario o contraseña incorrectos.";
        }
    }
}