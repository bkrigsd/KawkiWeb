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
            Response.Headers.Add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            Response.Headers.Add("Pragma", "no-cache");
            Response.Headers.Add("Expires", "0");

            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.UtcNow.AddMinutes(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.Cache.SetAllowResponseInBrowserHistory(false);

            var rol = (Session["Rol"] as string) ?? string.Empty;
            var usuario = (Session["Usuario"] as string) ?? string.Empty;

            if (Session["Usuario"] != null)
                lnkPerfil.NavigateUrl = "Perfil.aspx";
            else
                lnkPerfil.NavigateUrl = "Login.aspx";

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

                // Si no hay usuario logueado, ocultamos el enlace de Productos
                //if (Session["Usuario"] == null)
                //{
                //    lnkInicio.Visible = false;
                //}
                //else
                //{
                //    lnkInicio.Visible = true;
                //}

                if (rolUser == "vendedor")
                    inicioUrl = "~/Productos.aspx";
                else if (rolUser == "admin")
                    inicioUrl = "~/Productos.aspx";

                //lnkInicio.HRef = ResolveUrl(inicioUrl);

                // Control de visibilidad
                phMenuVendedor.Visible = (rolUser == "vendedor");
                phMenuAdmin.Visible = (rolUser == "admin");

                //VerificarPaginaSubmenu();
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);
            MarcarMenuActivo();
        }

        private void MarcarMenuActivo()
        {
            string current = VirtualPathUtility.ToAppRelative(Request.Path).ToLowerInvariant();

            if (current == "~/" || current == "~/default.aspx")
                current = "~/productos.aspx";

            // Páginas del submenú
            string[] paginasSubmenu = {
                "~/gestionproductos.aspx",
                "~/categorias.aspx",
                "~/estilos.aspx",
                "~/colores.aspx",
                "~/tallas.aspx"
            };

            bool esPaginaSubmenu = paginasSubmenu.Contains(current);

            // Recorrer todos los anchors del menú
            foreach (var a in EnumerarAnchors(sidebarMenu))
            {
                string href = VirtualPathUtility.ToAppRelative(ResolveUrl(a.HRef)).ToLowerInvariant();

                var cls = a.Attributes["class"] ?? string.Empty;
                cls = cls.Replace("active", "").Trim();

                // Lógica especial: si estamos en Productos.aspx (Catálogo), 
                // NO marcar como activo ningún enlace del submenú
                if (current == "~/productos.aspx" && paginasSubmenu.Contains(href))
                {
                    // No marcar este enlace del submenú
                    if (string.IsNullOrEmpty(cls)) a.Attributes.Remove("class");
                    else a.Attributes["class"] = cls;
                    continue;
                }

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

        //private void VerificarPaginaSubmenu()
        //{
        //    string paginaActual = System.IO.Path.GetFileName(Request.PhysicalPath).ToLower();

        //    // Lista de páginas que pertenecen al submenú "Gestión de Productos"
        //    string[] paginasSubmenu = {
        //        "Gestionproductos.aspx",
        //        "Categorias.aspx",
        //        "Estilos.aspx",
        //        "Colores.aspx",
        //        "Tallas.aspx"
        //    };

        //    // Si estamos en alguna página del submenú, registrar script usando Page.ClientScript
        //    if (paginasSubmenu.Contains(paginaActual))
        //    {
        //        string script = $@"
        //            $(document).ready(function() {{

        //                var pagina = '{paginaActual}';

        //                // Mantener abierto el submenú
        //                $('.submenu-container').addClass('open');
        //                $('.submenu-btn').addClass('activo-padre');

        //                // Marcar activo el enlace hijo correcto
        //                $('.submenu-list a').each(function () {{
        //                    var href = $(this).attr('href').toLowerCase();
        //                    if (href.includes(pagina)) {{
        //                        $(this).addClass('active');
        //                    }}
        //                }});
        //            }});
        //        ";

        //        // Usar Page.ClientScript en lugar de ClientScript
        //        if (this.Page != null)
        //        {
        //            ScriptManager.RegisterStartupScript(
        //                this.Page,
        //                this.Page.GetType(),
        //                "MantenerSubmenuAbierto",
        //                script,
        //                true
        //            );
        //        }
        //    }
        //}

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
        protected void btnCerrarSesion_Click(object sender, EventArgs e)
        {
            Session.Clear();
            Session.Abandon();
            Response.Redirect("Login.aspx", true);
        }
    }
}
