using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Perfil : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Usuario"] == null)
            {
                Response.Redirect("Login.aspx");
                return;
            }

            if (!IsPostBack)
            {
                string usuario = Session["Usuario"].ToString();
                string rol = Session["Rol"]?.ToString() ?? "Cliente Kawki";

                lblUsuario.Text = usuario;
                lblUsuario2.Text = usuario;
                lblEmail.Text = usuario + "@kawki.com"; // temporal
                lblRol.Text = rol;
                lblInicial.Text = usuario.Substring(0, 1).ToUpper();
            
                bool esCliente = rol.Equals("cliente",StringComparison.OrdinalIgnoreCase);
                bool esVendedor = rol.Equals("vendedor", StringComparison.OrdinalIgnoreCase);

                var historialDiv = FindControlRecursive(this, "historialDeComprasDiv");
                if (historialDiv != null)
                {
                    historialDiv.Visible = esCliente;
                }
                btnEliminarCuenta.Visible = esCliente;

            }
        }

        private Control FindControlRecursive(Control root, string id)
        {
            if (root.ID == id) return root;
            foreach (Control c in root.Controls)
            { 
                Control found = FindControlRecursive(c, id);
                if(found != null) return found;
            }
            return null;
        }

        protected void btnCerrarSesion_Click(object sender, EventArgs e)
        {
            Session.Clear();
            Session.Abandon();
            Response.Redirect("Inicio.aspx");
        }

        protected void btnEliminarCuenta_Click(object sender, EventArgs e)
        {
            string usuario = Session["Usuario"] as string;
            string rol = Session["Rol"] as string;

            // Solo los clientes pueden eliminar cuenta
            if (rol == null || !rol.Equals("cliente", StringComparison.OrdinalIgnoreCase))
            {
                Response.Redirect("Inicio.aspx");
                return;
            }
            Session.Clear();
            Session.Abandon();

            Response.Redirect("Inicio.aspx?msg=CuentaEliminada");
        }

    }
}