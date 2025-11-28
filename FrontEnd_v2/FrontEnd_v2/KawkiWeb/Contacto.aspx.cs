using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Contacto : Page
    {
        protected void btnEnviar_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;

            // Aquí podrías enviar correo o guardar en BD.
            // Por ahora solo mostramos el mensaje de éxito.
            pnOk.CssClass = "alert alert-success";
            pnOk.Visible = true;

            // Limpia campos
            txtNombre.Text = txtEmail.Text = txtAsunto.Text = txtMensaje.Text = string.Empty;
        }
    }
}