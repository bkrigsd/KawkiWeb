using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class InicioAdmin : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var rol = (Session["Rol"] as string) ?? "";
                if (!rol.Equals("admin", StringComparison.OrdinalIgnoreCase))
                    Response.Redirect("Login.aspx");
            }
        }
    }
}