using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Logout : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Limpiar sesión
            Session.Clear();
            Session.Abandon();

            // (Opcional) si usas FormsAuthentication
            FormsAuthentication.SignOut();

            // Evitar caché de esta página también
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.UtcNow.AddMinutes(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.Cache.SetAllowResponseInBrowserHistory(false);

            Response.Redirect("Login.aspx");
        }
    }
}