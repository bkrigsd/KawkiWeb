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
            var rol = (Session["Rol"] as string) ?? string.Empty;
            var usuario = (Session["Usuario"] as string) ?? string.Empty;

            bool logueado = !string.IsNullOrEmpty(usuario);
            bool esCliente = rol.Equals("cliente", StringComparison.OrdinalIgnoreCase);
            bool esVendedor = rol.Equals("vendedor", StringComparison.OrdinalIgnoreCase);
            bool esAdmin = rol.Equals("admin", StringComparison.OrdinalIgnoreCase);

            // Menús por rol
            phMenuVendedor.Visible = esVendedor;
            phMenuAdmin.Visible = esAdmin;

            // Nosotros/Contacto: visible solo si no hay sesión o si es cliente
            phNosYContacto.Visible = !logueado || esCliente;

            // Botón Cerrar sesión al final del sidebar: visible solo si hay sesión
            phCerrarSesion.Visible = logueado;

            // Carrito: visible SOLO para público (no logueado) o cliente
            lnkCarrito.Visible = (!logueado) || esCliente;

            // Top-bar: muestra nombre usuario o "Iniciar Sesión" 
            phSesionPublico.Visible = !logueado;
            phSesionUsuario.Visible = logueado;

            if (!IsPostBack)
            {
                ConfigurarWhatsApp();

                string rolUser = Convert.ToString(Session["Rol"])?.ToLowerInvariant();
                string inicioUrl = "~/Inicio.aspx"; // Público o cliente

                if (rolUser == "vendedor")
                    inicioUrl = "~/InicioVendedor.aspx";
                else if (rolUser == "admin")
                    inicioUrl = "~/InicioAdmin.aspx";

                lnkInicio.HRef = ResolveUrl(inicioUrl);

                // Control de visibilidad
                phMenuVendedor.Visible = (rolUser == "vendedor");
                phMenuAdmin.Visible = (rolUser == "admin");
                phNosYContacto.Visible = string.IsNullOrEmpty(rolUser) || rolUser == "cliente";
                lnkCarrito.Visible = string.IsNullOrEmpty(rolUser) || rolUser == "cliente";
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);
            MarcarMenuActivo();
        }

        private void ConfigurarWhatsApp()
        {
            var phone = "51910755121";
            var defaultText = "Hola, tengo una consulta sobre los productos de Kawki.";
            var url = $"https://wa.me/{phone}?text={HttpUtility.UrlEncode(defaultText)}";

            var page = System.IO.Path.GetFileName(Request.Path).ToLowerInvariant();
            bool esInicio = page == "inicio.aspx" || page == "default.aspx";
            bool esProductos = page == "productos.aspx";
            bool esNosotros = page == "nosotros.aspx";
            bool esContacto = page == "contacto.aspx";

            // Mostrar WhatsApp si:
            // NO es la página de Contacto y (Inicio, Productos, Nosotros o Cliente logueado)
            // phWhatsApp.Visible = !esContacto && (esInicio || esProductos || esNosotros || esCliente);

            if (phWhatsApp.Visible)
                ((HtmlAnchor)waLink).HRef = url;
        }

        private void MarcarMenuActivo()
        {
            // Ej: "~/Nosotros.aspx"
            string current = VirtualPathUtility.ToAppRelative(Request.Path).ToLowerInvariant();

            // Trata raíz como Inicio
            if (current == "~/" || current == "~/default.aspx")
                current = "~/inicio.aspx";

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