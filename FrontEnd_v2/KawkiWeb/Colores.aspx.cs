using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSColores;

namespace KawkiWeb
{
    public partial class Colores : System.Web.UI.Page
    {
        private ColoresBO coloresBO = new ColoresBO();
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarColores();
            }
        }

        private void CargarColores()
        {
            try
            {
                var colores = coloresBO.ListarTodosColor();

                // Crear lista personalizada para el GridView
                var coloresGrid = colores.Select(c => new
                {
                    ColorId = c.color_id,
                    Nombre = c.nombre
                }).ToList();

                gvColores.DataSource = coloresGrid;
                gvColores.DataBind();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar los colores: " + ex.Message);
            }
        }

        protected void gvColores_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Editar")
            {
                try
                {
                    int colorId = Convert.ToInt32(e.CommandArgument);
                    CargarColorParaEditar(colorId);
                    ScriptManager.RegisterStartupScript(this, this.GetType(), "abrirModalEditar",
                        "abrirModalEditar();", true);
                }
                catch (Exception ex)
                {
                    MostrarError("Error al cargar el color: " + ex.Message);
                }
            }
        }

        private void CargarColorParaEditar(int colorId)
        {
            try
            {
                coloresDTO color = coloresBO.ObtenerPorIdColor(colorId);

                if (color != null)
                {
                    hfColorId.Value = color.color_id.ToString();
                    txtNombre.Text = color.nombre;
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error al obtener el color: " + ex.Message);
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            if (ValidarFormulario())
            {
                try
                {
                    int colorId = Convert.ToInt32(hfColorId.Value);
                    string nombre = NormalizarNombre(txtNombre.Text.Trim());

                    if (colorId == 0)
                    {
                        // Insertar nuevo color
                        if (ExisteColor(nombre))
                        {
                            lblErrorNombre.Text = "Ya existe un color con este nombre";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalRegistro();", true);
                            return;
                        }

                        int resultado = coloresBO.InsertarColor(nombre);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarColores();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Color registrado exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al registrar el color");
                        }
                    }
                    else
                    {
                        // Modificar color existente
                        // Verificar si el nombre ya existe en otro color
                        if (ExisteColor(nombre, colorId))
                        {
                            lblErrorNombre.Text = "Ya existe otro color con este nombre";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalEditar();", true);
                            return;
                        }

                        int resultado = coloresBO.ModificarColor(colorId, nombre);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarColores();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Color actualizado exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al actualizar el color");
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
                lblErrorNombre.Text = "El nombre del color es requerido";
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
        /// Verifica si existe un color con el mismo nombre (ignorando mayúsculas/minúsculas)
        /// </summary>
        private bool ExisteColor(string nombre, int? excluirColorId = null)
        {
            try
            {
                var colores = coloresBO.ListarTodosColor();

                foreach (var col in colores)
                {
                    // Si se pasa un ID para excluir (edición), no comparar con ese color
                    if (excluirColorId.HasValue && col.color_id == excluirColorId.Value)
                        continue;
                    // Comparar sin importar mayúsculas/minúsculas
                    if (col.nombre.Equals(nombre, StringComparison.OrdinalIgnoreCase))
                        return true;
                }

                return false;
            }
            catch (Exception ex)
            {
                MostrarError("Error al validar color: " + ex.Message);
                return false;
            }
        }

        private void LimpiarFormulario()
        {
            hfColorId.Value = "0";
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