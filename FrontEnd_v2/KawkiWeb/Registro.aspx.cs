using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Registro : Page
    {
        protected void btnRegistro_Click(object sender, EventArgs e)
        {
            lblError.Text = ""; // limpia

            if (string.IsNullOrWhiteSpace(txtDni.Text) ||
                string.IsNullOrWhiteSpace(txtNombre.Text) ||
                string.IsNullOrWhiteSpace(txtCorreo.Text) ||
                string.IsNullOrWhiteSpace(txtPassword.Text))
            {
                lblError.Text = "Completa los campos obligatorios.";
                return;
            }

            if (!Regex.IsMatch(txtCorreo.Text.Trim(), @"^[^@\s]+@[^@\s]+\.[^@\s]+$"))
            {
                lblError.Text = "Ingresa un correo válido.";
                return;
            }

            // Simulación de "guardar" usuario
            Session["Usuario"] = txtNombre.Text.Trim();
            Session["Rol"] = "Cliente";

            // Ocultar el panel izquierdo y formulario, mostrar éxito
            panelIzquierdo.Visible = false;
            formularioRegistro.Visible = false;
            pnlExito.Visible = true;
        }
    }
}