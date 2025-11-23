using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSTallas;

namespace KawkiWeb
{
    public partial class Tallas : System.Web.UI.Page
    {
        private TallasBO tallasBO = new TallasBO();
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarTallas();
            }
        }

        private void CargarTallas()
        {
            try
            {
                var tallas = tallasBO.ListarTodosTalla();

                // Crear lista personalizada para el GridView
                var tallasGrid = tallas.Select(t => new
                {
                    TallasId = t.talla_id,
                    Numero = t.numero,
                }).ToList();

                gvTallas.DataSource = tallasGrid;
                gvTallas.DataBind();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar las tallas: " + ex.Message);
            }
        }

        protected void gvTallas_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Editar")
            {
                try
                {
                    int tallaId = Convert.ToInt32(e.CommandArgument);
                    CargarTallaParaEditar(tallaId);
                    ScriptManager.RegisterStartupScript(this, this.GetType(), "abrirModalEditar",
                        "abrirModalEditar();", true);
                }
                catch (Exception ex)
                {
                    MostrarError("Error al cargar la talla: " + ex.Message);
                }
            }
        }

        private void CargarTallaParaEditar(int tallaId)
        {
            try
            {
                tallasDTO talla = tallasBO.ObtenerPorIdTalla(tallaId);

                if (talla != null)
                {
                    hfTallaId.Value = talla.talla_id.ToString();
                    txtNumero.Text = talla.numero.ToString();
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error al obtener la talla: " + ex.Message);
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            if (ValidarFormulario())
            {
                try
                {
                    int tallaId = Convert.ToInt32(hfTallaId.Value);
                    int numero = Convert.ToInt32(txtNumero.Text);

                    if (tallaId == 0)
                    {
                        // Insertar nueva talla
                        if (ExisteTalla(numero))
                        {
                            lblErrorNumero.Text = "Ya existe una talla con este valor";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalRegistro();", true);
                            return;
                        }

                        int resultado = tallasBO.InsertarTalla(numero);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarTallas();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Talla registrada exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al registrar la talla");
                        }
                    }
                    else
                    {
                        // Modificar talla existente
                        // Verificar si el numero ya existe en otra talla
                        if (ExisteTalla(numero, tallaId))
                        {
                            lblErrorNumero.Text = "Ya existe otra talla con este valor";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalEditar();", true);
                            return;
                        }

                        int resultado = tallasBO.ModificarTalla(tallaId, numero);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarTallas();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Talla actualizada exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al actualizar la talla");
                        }
                    }
                }
                catch (Exception ex)
                {
                    MostrarError("Error: " + ex.Message);
                    ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                        "reabrirModalRegistro();", true);
                }
            }
            else
            {
                ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                    "reabrirModalRegistro();", true);
            }
        }

        private bool ValidarFormulario()
        {
            bool esValido = true;
            lblErrorNumero.Text = "";

            // Validar numero
            if (string.IsNullOrWhiteSpace(txtNumero.Text))
            {
                lblErrorNumero.Text = "La talla es requerida";
                esValido = false;
            }

            return esValido;
        }

        /// <summary>
        /// Verifica si existe una talla con el mismo valor 
        /// </summary>
        private bool ExisteTalla(int numero, int? excluirTallaId = null)
        {
            try
            {
                var tallas = tallasBO.ListarTodosTalla();

                foreach (var tal in tallas)
                {
                    // Si se pasa un ID para excluir (edición), no comparar con esa categoría
                    if (excluirTallaId.HasValue && tal.talla_id == excluirTallaId.Value)
                        continue;
                    // Comparar tallas
                    if (tal.numero==numero)
                        return true;
                }

                return false;
            }
            catch (Exception ex)
            {
                MostrarError("Error al validar la talla: " + ex.Message);
                return false;
            }
        }

        private void LimpiarFormulario()
        {
            hfTallaId.Value = "0";
            txtNumero.Text = "";
            lblErrorNumero.Text = "";
            lblMensaje.Text = "";
        }

        private void MostrarError(string mensaje)
        {
            lblMensaje.Text = mensaje;
            lblMensaje.CssClass = "d-block mb-3 alert alert-danger";
        }
    }
}