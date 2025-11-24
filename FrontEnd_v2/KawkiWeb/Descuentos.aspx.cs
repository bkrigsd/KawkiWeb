using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSDescuentos;

// Alias obligatorios para evitar conflictos de nombres entre DTOs
using DTOCond = KawkiWebBusiness.KawkiWebWSTiposCondicion.tiposCondicionDTO;
using DTOBenef = KawkiWebBusiness.KawkiWebWSTiposBeneficio.tiposBeneficioDTO;

// BO reales
using KawkiWebBusiness.KawkiWebWSTiposCondicion;
using KawkiWebBusiness.KawkiWebWSTiposBeneficio;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class Descuentos : Page
    {
        private DescuentosBO descuentosBO = new DescuentosBO();
        private TiposCondicionBO tiposCondicionBO = new TiposCondicionBO();
        private TiposBeneficioBO tiposBeneficioBO = new TiposBeneficioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            // Anti-cache
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            Response.Cache.SetNoStore();
            Response.Cache.SetExpires(DateTime.Now.AddSeconds(-1));
            Response.Cache.SetRevalidation(HttpCacheRevalidation.AllCaches);
            Response.AddHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            Response.AddHeader("Pragma", "no-cache");
            Response.AddHeader("Expires", "0");

            if (!IsPostBack)
            {
                // Restaurar modal si venimos de un PostBack (mismo patrón que RegistroUsuario)
                if (Session["MantenerModal"] != null)
                {
                    string modo = Session["MantenerModal"].ToString();
                    Session["MantenerModal"] = null;

                    if (modo == "editar")
                        ScriptManager.RegisterStartupScript(this, GetType(), "AbrirEditar", "abrirModalEditar();", true);
                    else
                        ScriptManager.RegisterStartupScript(this, GetType(), "AbrirRegistrar", "abrirModalRegistroSinLimpiar();", true);
                }

                // Validación de sesión / rol
                string rol = Session["Rol"] as string;

                if (string.IsNullOrEmpty(rol))
                {
                    Response.Redirect("Error404.aspx", false);
                    return;
                }

                if (rol.ToLower() == "vendedor")
                {
                    Response.Redirect("Productos.aspx", false);
                    return;
                }

                try
                {

                    CargarCombosTipos();

                    // valores por defecto de orden
                    SortField = "IdDescuento";
                    SortDirection = "ASC";

                    CargarDescuentos();



                    //CargarCombosTipos();
                    //CargarDescuentos();
                }
                catch (Exception ex)
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Error al cargar descuentos: " + ex.Message;
                }
            }
        }

        private void CargarCombosTipos()
        {
            try
            {
                var listaCond = tiposCondicionBO.listarTodosTipoCondicion() ?? new List<DTOCond>();
                ddlTipoCondicion.DataSource = listaCond;
                ddlTipoCondicion.DataTextField = "nombre";
                ddlTipoCondicion.DataValueField = "tipo_condicion_id";
                ddlTipoCondicion.DataBind();
                ddlTipoCondicion.Items.Insert(0, new ListItem("-- Seleccione --", ""));

                var listaBenef = tiposBeneficioBO.listarTodosTipoBeneficio() ?? new List<DTOBenef>();
                ddlTipoBeneficio.DataSource = listaBenef;
                ddlTipoBeneficio.DataTextField = "nombre";
                ddlTipoBeneficio.DataValueField = "tipo_beneficio_id";
                ddlTipoBeneficio.DataBind();
                ddlTipoBeneficio.Items.Insert(0, new ListItem("-- Seleccione --", ""));

                ddlFiltroTipoCondicion.DataSource = listaCond;
                ddlFiltroTipoCondicion.DataTextField = "nombre";
                ddlFiltroTipoCondicion.DataValueField = "tipo_condicion_id";
                ddlFiltroTipoCondicion.DataBind();
                ddlFiltroTipoCondicion.Items.Insert(0, new ListItem("-- Todos --", ""));

                ddlFiltroTipoBeneficio.DataSource = listaBenef;
                ddlFiltroTipoBeneficio.DataTextField = "nombre";
                ddlFiltroTipoBeneficio.DataValueField = "tipo_beneficio_id";
                ddlFiltroTipoBeneficio.DataBind();
                ddlFiltroTipoBeneficio.Items.Insert(0, new ListItem("-- Todos --", ""));

            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger d-block mb-2";
                lblMensaje.Text = "Error al cargar tipos: " + ex.Message;
            }
        }

        // ===========================================================
        // CARGAR LISTA DESDE EL WS
        // ===========================================================
        private void CargarDescuentos()
        {
            var listaWS = descuentosBO.listarTodosDescuento() ?? new List<descuentosDTO>();

            // Mapeo original
            var listaGrid = listaWS.Select(x => new
            {
                IdDescuento = x.descuento_id,
                Descripcion = x.descripcion,

                TipoCondicion = x.tipo_condicion?.nombre,
                TipoCondicionId = x.tipo_condicion?.tipo_condicion_id,

                ValorCondicion = x.valor_condicion,

                TipoBeneficio = x.tipo_beneficio?.nombre,
                TipoBeneficioId = x.tipo_beneficio?.tipo_beneficio_id,

                ValorBeneficio = x.valor_beneficio,

                FechaInicio = DateTime.Parse(x.fecha_inicio),
                FechaFin = DateTime.Parse(x.fecha_fin),
                Activo = x.activo
            }).ToList();

            // ==========================
            // APLICAR FILTROS
            // ==========================

            // Filtro descripción
            if (!string.IsNullOrWhiteSpace(txtFiltroDescripcion.Text))
            {
                string filtro = txtFiltroDescripcion.Text.ToLower();
                listaGrid = listaGrid
                    .Where(d => d.Descripcion != null && d.Descripcion.ToLower().Contains(filtro))
                    .ToList();
            }

            // Filtro tipo condición
            if (!string.IsNullOrEmpty(ddlFiltroTipoCondicion.SelectedValue))
            {
                int idCond = int.Parse(ddlFiltroTipoCondicion.SelectedValue);
                listaGrid = listaGrid.Where(d => d.TipoCondicionId == idCond).ToList();
            }

            // Filtro tipo beneficio
            if (!string.IsNullOrEmpty(ddlFiltroTipoBeneficio.SelectedValue))
            {
                int idBen = int.Parse(ddlFiltroTipoBeneficio.SelectedValue);
                listaGrid = listaGrid.Where(d => d.TipoBeneficioId == idBen).ToList();
            }

            if (!string.IsNullOrEmpty(txtFiltroFechaInicio.Text))
            {
                if (DateTime.TryParse(txtFiltroFechaInicio.Text, out DateTime fechaIni))
                {
                    listaGrid = listaGrid
                        .Where(d => d.FechaInicio >= fechaIni)
                        .ToList();
                }
            }

            if (!string.IsNullOrEmpty(txtFiltroFechaFin.Text))
            {
                if (DateTime.TryParse(txtFiltroFechaFin.Text, out DateTime fechaFin))
                {
                    listaGrid = listaGrid
                        .Where(d => d.FechaFin <= fechaFin)
                        .ToList();
                }
            }

            if (!string.IsNullOrEmpty(ddlFiltroActivo.SelectedValue))
            {
                bool estado = ddlFiltroActivo.SelectedValue == "true";
                listaGrid = listaGrid.Where(d => d.Activo == estado).ToList();
            }



            // ORDENAMIENTO
            if (!string.IsNullOrEmpty(SortField))
            {
                if (SortDirection == "ASC")
                {
                    listaGrid = listaGrid
                        .OrderBy(d => d.GetType().GetProperty(SortField).GetValue(d))
                        .ToList();
                }
                else
                {
                    listaGrid = listaGrid
                        .OrderByDescending(d => d.GetType().GetProperty(SortField).GetValue(d))
                        .ToList();
                }
            }

            gvDescuentos.DataSource = listaGrid;
            gvDescuentos.DataBind();


            //gvDescuentos.DataSource = listaGrid;
            //gvDescuentos.DataBind();
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            // Limpiar mensajes de error de fechas
            lblErrorFechaInicio.Visible = false;
            lblErrorFechaInicio.Text = "";
            lblErrorFechaFin.Visible = false;
            lblErrorFechaFin.Text = "";

            DateTime? fechaInicio = null;
            DateTime? fechaFin = null;

            // Validar FECHA INICIO (si el usuario escribió algo)
            if (!string.IsNullOrWhiteSpace(txtFiltroFechaInicio.Text))
            {
                if (!DateTime.TryParse(txtFiltroFechaInicio.Text, out var fi))
                {
                    lblErrorFechaInicio.Visible = true;
                    lblErrorFechaInicio.Text = "La fecha de inicio no es válida.";
                    return;
                }
                fechaInicio = fi;
            }

            // Validar FECHA FIN (si el usuario escribió algo)
            if (!string.IsNullOrWhiteSpace(txtFiltroFechaFin.Text))
            {
                if (!DateTime.TryParse(txtFiltroFechaFin.Text, out var ff))
                {
                    lblErrorFechaFin.Visible = true;
                    lblErrorFechaFin.Text = "La fecha de fin no es válida.";
                    return;
                }
                fechaFin = ff;
            }

            // Validar RANGO: inicio <= fin (solo si ambas tienen valor)
            if (fechaInicio.HasValue && fechaFin.HasValue && fechaInicio.Value > fechaFin.Value)
            {
                lblErrorFechaInicio.Visible = true;
                lblErrorFechaInicio.Text = "La fecha inicio debe ser menor o igual que la fecha fin.";

                lblErrorFechaFin.Visible = true;
                lblErrorFechaFin.Text = "La fecha fin debe ser mayor o igual que la fecha inicio.";

                return;
            }

            // Si todo está OK, aplicamos filtros normales
            CargarDescuentos();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFiltroDescripcion.Text = "";
            ddlFiltroTipoCondicion.SelectedIndex = 0;
            ddlFiltroTipoBeneficio.SelectedIndex = 0;
            txtFiltroFechaInicio.Text = "";
            txtFiltroFechaFin.Text = "";
            ddlFiltroActivo.SelectedIndex = 0;

            // Limpiar mensajes de error de fechas
            lblErrorFechaInicio.Visible = false;
            lblErrorFechaInicio.Text = "";
            lblErrorFechaFin.Visible = false;
            lblErrorFechaFin.Text = "";

            CargarDescuentos();
        }

        // ===========================================================
        // GUARDAR (INSERTAR / ACTUALIZAR)
        // ===========================================================
        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            bool esEdicion = hfIdDescuento.Value != "0";

            try
            {
                //string nombre = txtNombre.Text.Trim();
                string descripcion = txtDescripcion.Text.Trim();
                string txtValorCond = txtValorCondicion.Text.Trim();
                string txtPorc = txtPorcentaje.Text.Trim();
                string fechaInicioStr = txtFechaInicio.Text.Trim();
                string fechaFinStr = txtFechaFin.Text.Trim();

                // Validaciones básicas adicionales (sobre los Required/Regex ya del front)
                if (!int.TryParse(txtValorCond, out int valorCondicion))
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "El valor de condición debe ser numérico.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!int.TryParse(txtPorc, out int valorBeneficio))
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "El porcentaje debe ser numérico.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!DateTime.TryParse(fechaInicioStr, out DateTime fechaInicio))
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Fecha de inicio inválida.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!DateTime.TryParse(fechaFinStr, out DateTime fechaFin))
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Fecha de fin inválida.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (fechaFin < fechaInicio)
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "La fecha fin no puede ser anterior a la fecha inicio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (string.IsNullOrEmpty(ddlTipoCondicion.SelectedValue) ||
                    string.IsNullOrEmpty(ddlTipoBeneficio.SelectedValue))
                {
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "Seleccione tipo de condición y tipo de beneficio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                int tipoCondId = int.Parse(ddlTipoCondicion.SelectedValue);
                int tipoBenefId = int.Parse(ddlTipoBeneficio.SelectedValue);

                bool activo = chkActivo.Checked;

                // DTOs de tipos
                // Convertirlo al tipo QUE PIDE insertarDescuento:
                // 1. Obtener el DTO original desde el WS TiposCondicion
                var dtoCondWS = tiposCondicionBO.obtenerPorIdTipoCondicion(Convert.ToInt32(ddlTipoCondicion.SelectedValue));

                // 2. Convertirlo al tipo correcto que EXIGE el WS Descuentos
                var tipoCondicion = new KawkiWebBusiness.KawkiWebWSDescuentos.tiposCondicionDTO
                {
                    tipo_condicion_id = dtoCondWS.tipo_condicion_id,
                    tipo_condicion_idSpecified = true,
                    nombre = dtoCondWS.nombre
                };

                var dtoBenWS = tiposBeneficioBO.obtenerPorIdTipoBeneficio(Convert.ToInt32(ddlTipoBeneficio.SelectedValue));

                var tipoBeneficio = new KawkiWebBusiness.KawkiWebWSDescuentos.tiposBeneficioDTO
                {
                    tipo_beneficio_id = dtoBenWS.tipo_beneficio_id,
                    tipo_beneficio_idSpecified = true,
                    nombre = dtoBenWS.nombre
                };

                // El backend solo tiene un campo "descripcion" → usamos nombre o descripción larga
                string descripcionFinal = descripcion;

                if (!esEdicion)
                {
                    descuentosBO.insertarDescuento(
                        descripcionFinal,
                        tipoCondicion,
                        valorCondicion,
                        tipoBeneficio,
                        valorBeneficio,
                        fechaInicio,
                        fechaFin,
                        activo
                    );

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Descuento registrado correctamente.";
                }
                else
                {
                    int id = Convert.ToInt32(hfIdDescuento.Value);

                    descuentosBO.modificarDescuento(
                        id,
                        descripcionFinal,
                        tipoCondicion,
                        valorCondicion,
                        tipoBeneficio,
                        valorBeneficio,
                        fechaInicio,
                        fechaFin,
                        activo
                    );

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Descuento actualizado correctamente.";
                }

                CargarDescuentos();
                ScriptManager.RegisterStartupScript(this, GetType(), "Cerrar", "cerrarModal();", true);
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger d-block mb-2";
                lblMensaje.Text = "Error: " + ex.Message;

                Session["MantenerModal"] = esEdicion ? "editar" : "registrar";
                MantenerModalAbierto(esEdicion);
            }
        }

        private void MantenerModalAbierto(bool esEdicion)
        {
            string script = esEdicion ? "abrirModalEditar();" : "abrirModalRegistroSinLimpiar();";
            ScriptManager.RegisterStartupScript(this, GetType(), "Mantener", script, true);
        }

        // ===========================================================
        // EDITAR (desde el Grid)
        // ===========================================================
        protected void gvDescuentos_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "EditarDescuento")
            {
                int id = Convert.ToInt32(e.CommandArgument);

                // Aseguramos combos cargados
                CargarCombosTipos();

                var d = descuentosBO.obtenerPorIdDescuento(id);

                hfIdDescuento.Value = id.ToString();

                txtDescripcion.Text = d.descripcion; // puedes dejarlo así o diferenciar luego

                // Valor condición
                txtValorCondicion.Text = d.valor_condicion.ToString();
                // Tipo condición
                if (d.tipo_condicion != null)
                {
                    string valCond = d.tipo_condicion.tipo_condicion_id.ToString();
                    if (ddlTipoCondicion.Items.FindByValue(valCond) != null)
                        ddlTipoCondicion.SelectedValue = valCond;
                }

                // Porcentaje (valor_beneficio)
                txtPorcentaje.Text = d.valor_beneficio.ToString();
                // Tipo beneficio
                if (d.tipo_beneficio != null)
                {
                    string valBen = d.tipo_beneficio.tipo_beneficio_id.ToString();
                    if (ddlTipoBeneficio.Items.FindByValue(valBen) != null)
                        ddlTipoBeneficio.SelectedValue = valBen;
                }

                // Fechas
                txtFechaInicio.Text = DateTime.Parse(d.fecha_inicio).ToString("yyyy-MM-dd");
                txtFechaFin.Text = DateTime.Parse(d.fecha_fin).ToString("yyyy-MM-dd");

                // Activo
                chkActivo.Checked = d.activo;

                ScriptManager.RegisterStartupScript(this, GetType(), "AbrirEditar", "abrirModalEditar();", true);
            }
        }

        // ===========================================================
        // ACTIVAR / DESACTIVAR (switch)
        // ===========================================================
        protected void lnkCambiarEstado_Command(object sender, CommandEventArgs e)
        {
            try
            {
                int id = Convert.ToInt32(e.CommandArgument);
                var dto = descuentosBO.obtenerPorIdDescuento(id);

                if (dto.activo)
                    descuentosBO.desactivarDescuento(id);
                else
                    descuentosBO.activarDescuento(id);

                CargarDescuentos();
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger d-block mb-2";
                lblMensaje.Text = "Error al cambiar estado: " + ex.Message;
            }
        }



        private string SortField
        {
            get => ViewState["SortField"] as string ?? "";
            set => ViewState["SortField"] = value;
        }

        private string SortDirection
        {
            get => ViewState["SortDirection"] as string ?? "ASC";
            set => ViewState["SortDirection"] = value;
        }


        protected void OrdenChanged(object sender, EventArgs e)
        {
            SortField = ddlOrdenarPor.SelectedValue;
            SortDirection = ddlDireccion.SelectedValue;
            CargarDescuentos();
        }




        // Si en el futuro agregas eliminar en backend, aquí iría btnConfirmarEliminar_Click
        // por ahora el botón está comentado en el .aspx
    }
}
