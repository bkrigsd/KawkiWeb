using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class RecuperarClave : System.Web.UI.Page
    {
        protected void btnRecuperar_Click(object sender, EventArgs e)
        {
            lblError.Text = ""; // limpia mensaje anterior

            string correo = txtCorreo.Text.Trim();
            if (string.IsNullOrEmpty(correo) || !Regex.IsMatch(correo, @"^[^@\s]+@[^@\s]+\.[^@\s]+$"))
            {
                lblError.Text = "Debe ingresar un correo válido.";
                return;
            }

            // Si es válido, simula envío
            Response.Write("<script>alert('Se ha enviado un enlace de recuperación al correo ingresado.');</script>");
        }
    }
}