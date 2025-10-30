using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Promociones : System.Web.UI.Page
    {
        // ---- Modelo simple para simular en memoria ----
        [Serializable]
        public class Promo
        {
            public Guid Id { get; set; } = Guid.NewGuid();
            public string Nombre { get; set; }
            public string Descripcion { get; set; }
            public string Tipo { get; set; } // porcentaje | monto | cupon
            public decimal? Porcentaje { get; set; }
            public decimal? Monto { get; set; }
            public decimal? MinCompra { get; set; }
            public decimal? MaxDesc { get; set; }
            public DateTime? Inicio { get; set; }
            public DateTime? Fin { get; set; }
            public string AplicableA { get; set; }
            public int? LimiteUsos { get; set; }
            public int Usos { get; set; }
            public bool Activa { get; set; }
            public string Cupon { get; set; }

            // ---- Propiedades calculadas para UI ----
            public string DescripcionResumen
                => string.IsNullOrWhiteSpace(Descripcion) ? "" : Descripcion;

            public string EtiquetaDescuento
            {
                get
                {
                    if (Tipo == "porcentaje" && Porcentaje.HasValue) return $"% {Porcentaje.Value:0}";
                    if (Tipo == "monto" && Monto.HasValue) return $"S/ {Monto.Value:0.##}";
                    if (Tipo == "cupon" && !string.IsNullOrEmpty(Cupon)) return $"🏷️ {Cupon}";
                    return "";
                }
            }

            public string DescuentoTexto
            {
                get
                {
                    switch (Tipo)
                    {
                        case "porcentaje": return $"{Porcentaje:0.#}% de descuento";
                        case "monto": return $"S/ {Monto:0.##} de descuento";
                        case "cupon": return $"Cupón: {Cupon}";
                        default: return "-";
                    }
                }
            }

            public string VigenciaTexto
            {
                get
                {
                    var a = Inicio?.ToString("d/M/yyyy");
                    var b = Fin?.ToString("d/M/yyyy");
                    return (a != null && b != null) ? $"{a} - {b}" : "—";
                }
            }

            public string UsosTexto
                => LimiteUsos.HasValue ? $"{Usos} / {LimiteUsos}" : $"{Usos}";
        }

        private List<Promo> Lista
        {
            get
            {
                if (Session["__PROMOS"] == null)
                {
                    Session["__PROMOS"] = Seed();
                }
                return (List<Promo>)Session["__PROMOS"];
            }
            set { Session["__PROMOS"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                
            }
        }

        protected void chkActivaItem_CheckedChanged(object sender, EventArgs e)
        {
            var chk = (CheckBox)sender;
            var item = (RepeaterItem)chk.NamingContainer;
            var hf = (HiddenField)item.FindControl("hfId");

            if (hf != null && Guid.TryParse(hf.Value, out var id))
            {
                var promo = Lista.FirstOrDefault(x => x.Id == id);
                if (promo != null)
                    promo.Activa = chk.Checked;   // refleja el estado del switch
            }
            // No necesitas llamar Bind(); Page_PreRender lo hará.
        }

        // ¡Clave! Enlazar SIEMPRE justo antes de renderizar
        protected void Page_PreRender(object sender, EventArgs e)
        {
            if (Lista == null || Lista.Count == 0)
                Lista = Seed();     // garantiza datos iniciales
            Bind();                 // pinta la lista siempre
        }

        private void Bind()
        {
            rptPromos.DataSource = Lista.OrderByDescending(p => p.Activa).ThenBy(p => p.Nombre).ToList();
            rptPromos.DataBind();
        }

        private List<Promo> Seed()
        {
            // Crea dos ejemplos similares a tus capturas
            var ls = new List<Promo>
            {
                new Promo{
                    Nombre="Descuento de Otoño",
                    Descripcion="15% de descuento en todos los productos Oxford",
                    Tipo="porcentaje", Porcentaje=15,
                    AplicableA="Oxford",
                    Inicio=ParseDMY("30/09/2024"), Fin=ParseDMY("30/10/2024"),
                    Usos=23, Activa=false
                },
                new Promo{
                    Nombre="Cupón NUEVO2024",
                    Descripcion="S/ 50 de descuento en compras mayores a S/ 300",
                    Tipo="cupon", Cupon="NUEVO2024", Monto=50, MinCompra=300,
                    AplicableA="Todos los productos",
                    Inicio=ParseDMY("14/09/2024"), Fin=ParseDMY("30/12/2024"),
                    LimiteUsos=100, Usos=12, Activa=true
                }
            };
            return ls;
        }

        // ---- Acciones UI ----
        protected void btnNueva_Click(object sender, EventArgs e)
        {
            ClearForm();
            litTituloModal.Text = "Nueva Promoción";
            btnGuardar.Text = "Crear Promoción";
            hdnEditId.Value = string.Empty;
            pnlModal.Visible = true;
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            pnlModal.Visible = false;
            ClearForm();
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            // leer formulario
            var tipo = ddlTipo.SelectedValue;
            decimal? pct = TryDec(txtPorcentaje.Text);
            decimal? min = TryDec(txtMinCompra.Text);
            decimal? maxd = TryDec(txtMaxDesc.Text);
            decimal? monto = (tipo == "monto" || tipo == "cupon") ? TryDec(txtPorcentaje.Text) : null; // permite reutilizar campo

            var p = new Promo
            {
                Nombre = txtNombre.Text?.Trim(),
                Descripcion = txtDescripcion.Text?.Trim(),
                Tipo = tipo,
                Porcentaje = (tipo == "porcentaje") ? pct : null,
                Monto = (tipo == "monto") ? monto : null,
                Cupon = (tipo == "cupon") ? (string.IsNullOrWhiteSpace(txtCupon.Text) ? "CUPON" : txtCupon.Text.Trim()) : txtCupon.Text?.Trim(),
                MinCompra = min,
                MaxDesc = maxd,
                Inicio = ParseDMY(txtInicio.Text),
                Fin = ParseDMY(txtFin.Text),
                AplicableA = ddlAplicable.SelectedValue,
                LimiteUsos = TryInt(txtLimite.Text),
                Activa = chkActiva.Checked,
                Usos = 0
            };

            if (string.IsNullOrEmpty(hdnEditId.Value))
            {
                // crear
                Lista.Add(p);
            }
            else
            {
                // actualizar
                var id = Guid.Parse(hdnEditId.Value);
                var ex = Lista.FirstOrDefault(x => x.Id == id);
                if (ex != null)
                {
                    p.Id = ex.Id; // conserva Id
                    p.Usos = ex.Usos; // conserva usos
                    // reemplazo
                    var idx = Lista.FindIndex(x => x.Id == id);
                    Lista[idx] = p;
                }
            }

            pnlModal.Visible = false;
            ClearForm();
            Bind();
        }

        protected void rptPromos_ItemCommand(object source, System.Web.UI.WebControls.RepeaterCommandEventArgs e)
        {
            if (!Guid.TryParse((string)e.CommandArgument, out var id)) return;

            var list = Lista;
            var item = list.FirstOrDefault(x => x.Id == id);
            if (item == null) return;

            switch (e.CommandName)
            {
                case "Toggle":
                    item.Activa = !item.Activa;
                    break;

                case "Delete":
                    list.Remove(item);
                    break;

                case "Edit":
                    // cargar al formulario y abrir modal
                    hdnEditId.Value = item.Id.ToString();
                    txtNombre.Text = item.Nombre;
                    txtDescripcion.Text = item.Descripcion;
                    ddlTipo.SelectedValue = item.Tipo;
                    txtPorcentaje.Text = item.Tipo == "porcentaje" ? (item.Porcentaje ?? 0).ToString("0.##")
                                       : (item.Monto ?? 0).ToString("0.##");
                    txtMinCompra.Text = item.MinCompra?.ToString("0.##") ?? "";
                    txtMaxDesc.Text = item.MaxDesc?.ToString("0.##") ?? "";
                    txtInicio.Text = item.Inicio?.ToString("dd/MM/yyyy") ?? "";
                    txtFin.Text = item.Fin?.ToString("dd/MM/yyyy") ?? "";
                    ddlAplicable.SelectedValue = string.IsNullOrWhiteSpace(item.AplicableA) ? "Todos los productos" : item.AplicableA;
                    txtLimite.Text = item.LimiteUsos?.ToString() ?? "";
                    txtCupon.Text = item.Cupon ?? "";
                    chkActiva.Checked = item.Activa;

                    litTituloModal.Text = "Editar Promoción";
                    btnGuardar.Text = "Guardar cambios";
                    pnlModal.Visible = true;
                    break;
            }

            Bind();
        }

        // ---- Helpers ----
        private void ClearForm()
        {
            txtNombre.Text = "";
            txtDescripcion.Text = "";
            ddlTipo.SelectedValue = "porcentaje";
            txtPorcentaje.Text = "15";
            txtMinCompra.Text = "100";
            txtMaxDesc.Text = "200";
            txtInicio.Text = "";
            txtFin.Text = "";
            ddlAplicable.SelectedValue = "Todos los productos";
            txtLimite.Text = "100";
            txtCupon.Text = "";
            chkActiva.Checked = false;
        }

        private static DateTime? ParseDMY(string s)
        {
            if (DateTime.TryParseExact(s?.Trim(), "dd/MM/yyyy", CultureInfo.InvariantCulture,
                DateTimeStyles.None, out var d)) return d;
            return null;
        }

        private static decimal? TryDec(string s)
            => decimal.TryParse((s ?? "").Replace(",", "."), NumberStyles.Any, CultureInfo.InvariantCulture, out var v) ? v : (decimal?)null;

        private static int? TryInt(string s)
            => int.TryParse((s ?? "").Trim(), out var v) ? v : (int?)null;
    } 
}