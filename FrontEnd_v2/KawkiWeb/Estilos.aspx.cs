using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSEstilos;

namespace KawkiWeb
{
    public partial class Estilos : System.Web.UI.Page
    {
        private EstilosBO estiloBO = new EstilosBO();
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarEstilos();
            }
        }

        private void CargarEstilos()
        {
            try
            {
                var estilos = estiloBO.ListarTodosEstilo();

                // Crear lista personalizada para el GridView
                var estilosGrid = estilos.Select(e => new
                {
                    EstiloId = e.estilo_id,
                    Nombre = e.nombre
                }).ToList();

                gvEstilos.DataSource = estilosGrid;
                gvEstilos.DataBind();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar los estilos: " + ex.Message);
            }
        }

        protected void gvEstilos_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Editar")
            {
                try
                {
                    int estiloId = Convert.ToInt32(e.CommandArgument);
                    CargarEstiloParaEditar(estiloId);
                    ScriptManager.RegisterStartupScript(this, this.GetType(), "abrirModalEditar",
                        "abrirModalEditar();", true);
                }
                catch (Exception ex)
                {
                    MostrarError("Error al cargar el estilo: " + ex.Message);
                }
            }
        }

        private void CargarEstiloParaEditar(int estiloId)
        {
            try
            {
                estilosDTO estilo = estiloBO.ObtenerPorIdEstilos(estiloId);

                if (estilo != null)
                {
                    hfEstiloId.Value = estilo.estilo_id.ToString();
                    txtNombre.Text = estilo.nombre;
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error al obtener el estilo: " + ex.Message);
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            if (ValidarFormulario())
            {
                try
                {
                    int estiloId = Convert.ToInt32(hfEstiloId.Value);
                    string nombre = NormalizarNombre(txtNombre.Text.Trim());

                    if (estiloId == 0)
                    {
                        // Insertar nuevo estilo
                        if (ExisteEstilo(nombre))
                        {
                            lblErrorNombre.Text = "Ya existe un estilo con este nombre";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalRegistro();", true);
                            return;
                        }

                        int resultado = estiloBO.InsertarEstilo(nombre);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarEstilos();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Estilo registrado exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al registrar el estilo");
                        }
                    }
                    else
                    {
                        // Modificar estilo existente
                        // Verificar si el nombre ya existe en otro estilo
                        if (ExisteEstilo(nombre, estiloId))
                        {
                            lblErrorNombre.Text = "Ya existe otro estilo con este nombre";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalEditar();", true);
                            return;
                        }

                        int resultado = estiloBO.ModificarEstilo(estiloId, nombre);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarEstilos();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Estilo actualizado exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al actualizar el estilo");
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
            lblErrorNombre.Text = "";

            // Validar nombre
            if (string.IsNullOrWhiteSpace(txtNombre.Text))
            {
                lblErrorNombre.Text = "El nombre del estilo es requerido";
                esValido = false;
            }
            else if (txtNombre.Text.Length < 3)
            {
                lblErrorNombre.Text = "El nombre debe tener al menos 3 caracteres";
                esValido = false;
            }
            else if (txtNombre.Text.Length > 100)
            {
                lblErrorNombre.Text = "El nombre no puede exceder 100 caracteres";
                esValido = false;
            }

            return esValido;
        }

        /// <summary>
        /// Normaliza el nombre: primera letra mayúscula, resto minúscula
        /// </summary>
        private string NormalizarNombre(string nombre)
        {
            if (string.IsNullOrWhiteSpace(nombre))
                return nombre;

            return char.ToUpper(nombre[0]) + nombre.Substring(1).ToLower();
        }

        /// <summary>
        /// Verifica si existe un estilo con el mismo nombre (ignorando mayúsculas/minúsculas)
        /// </summary>
        private bool ExisteEstilo(string nombre, int? excluirEstiloId = null)
        {
            try
            {
                var estilos = estiloBO.ListarTodosEstilo();

                foreach (var est in estilos)
                {

                    // Si se pasa un ID para excluir (edición), no comparar con ese estilo
                    if (excluirEstiloId.HasValue && est.estilo_id == excluirEstiloId.Value)
                        continue;

                    // Comparar sin importar mayúsculas/minúsculas
                    if (est.nombre.Equals(nombre, StringComparison.OrdinalIgnoreCase))
                        return true;
                }

                return false;
            }
            catch (Exception ex)
            {
                MostrarError("Error al validar el estilo: " + ex.Message);
                return false;
            }
        }

        private void LimpiarFormulario()
        {
            hfEstiloId.Value = "0";
            txtNombre.Text = "";
            lblErrorNombre.Text = "";
            lblMensaje.Text = "";
        }

        private void MostrarError(string mensaje)
        {
            lblMensaje.Text = mensaje;
            lblMensaje.CssClass = "d-block mb-3 alert alert-danger";
        }
    }
}