using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class KawkiWeb : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Evitar que las páginas se guarden en caché
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.UtcNow.AddMinutes(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.Cache.SetAllowResponseInBrowserHistory(false);

            var rol = (Session["Rol"] as string) ?? string.Empty;
            var usuario = (Session["Usuario"] as string) ?? string.Empty;

            bool logueado = !string.IsNullOrEmpty(usuario);
            bool esVendedor = rol.Equals("vendedor", StringComparison.OrdinalIgnoreCase);
            bool esAdmin = rol.Equals("admin", StringComparison.OrdinalIgnoreCase);

            // Menús por rol
            phMenuVendedor.Visible = esVendedor;
            phMenuAdmin.Visible = esAdmin;

            // Botón Cerrar sesión al final del sidebar: visible solo si hay sesión
            phCerrarSesion.Visible = logueado;

            // Top-bar: muestra nombre usuario o "Iniciar Sesión" 
            phSesionPublico.Visible = !logueado;
            phSesionUsuario.Visible = logueado;

            if (!IsPostBack)
            {

                string rolUser = Convert.ToString(Session["Rol"])?.ToLowerInvariant();
                string inicioUrl = "~/Login.aspx"; // Público o cliente

                if (rolUser == "vendedor")
                    inicioUrl = "~/Productos.aspx";
                else if (rolUser == "admin")
                    inicioUrl = "~/Productos.aspx";

                lnkInicio.HRef = ResolveUrl(inicioUrl);

                // Control de visibilidad
                phMenuVendedor.Visible = (rolUser == "vendedor");
                phMenuAdmin.Visible = (rolUser == "admin");
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);
            MarcarMenuActivo();
        }

        private void MarcarMenuActivo()
        {
            // Ej: "~/Nosotros.aspx"
            string current = VirtualPathUtility.ToAppRelative(Request.Path).ToLowerInvariant();

            // Trata raíz como Inicio
            if (current == "~/" || current == "~/default.aspx")
                current = "~/Productos.aspx";

            foreach (var a in EnumerarAnchors(sidebarMenu))
            {
                // Normaliza el HRef del anchor (ej: "~/Nosotros.aspx")
                string href = VirtualPathUtility.ToAppRelative(ResolveUrl(a.HRef)).ToLowerInvariant();

                // Limpia estado previo
                var cls = a.Attributes["class"] ?? string.Empty;
                cls = cls.Replace("active", "").Trim();

                if (href == current)
                {
                    a.Attributes["class"] = string.IsNullOrEmpty(cls) ? "active" : (cls + " active");
                }
                else
                {
                    if (string.IsNullOrEmpty(cls)) a.Attributes.Remove("class");
                    else a.Attributes["class"] = cls;
                }
            }
        }

        public void SetActive(string pageName)
        {
            // Busca los botones o enlaces del menú y les pone la clase "active"
            var item = FindControl("lnk" + pageName) as WebControl;
            if (item != null)
            {
                item.CssClass += " active";
            }
        }

        private static IEnumerable<HtmlAnchor> EnumerarAnchors(System.Web.UI.Control root)
        {
            foreach (System.Web.UI.Control c in root.Controls)
            {
                if (c is HtmlAnchor a) yield return a;
                foreach (var child in EnumerarAnchors(c)) yield return child;
            }
        }
        protected void btnPerfilUsuario_Click(object sender, EventArgs e)
        {
            if (Session["Usuario"] != null)
            {
                Response.Redirect("Perfil.aspx");
            }
            else
            {
                Response.Redirect("Login.aspx");
            }
        }
    }
}