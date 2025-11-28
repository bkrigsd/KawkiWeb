using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSCategorias;

namespace KawkiWeb
{
    public partial class Categorias : System.Web.UI.Page
    {
        private CategoriasBO categoriaBO = new CategoriasBO();
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarCategorias();
            }
        }

        private void CargarCategorias()
        {
            try
            {
                var categorias = categoriaBO.ListarTodosCategoria();

                // Crear lista personalizada para el GridView
                var categoriasGrid = categorias.Select(c => new
                {
                    CategoriaId = c.categoria_id,
                    Nombre = c.nombre
                }).ToList();

                gvCategorias.DataSource = categoriasGrid;
                gvCategorias.DataBind();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar las categorías: " + ex.Message);
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            if (ValidarFormulario())
            {
                try
                {
                    int categoriaId = Convert.ToInt32(hfCategoriaId.Value);
                    string nombre = NormalizarNombre(txtNombre.Text.Trim());

                    if (categoriaId == 0)
                    {
                        // Insertar nueva categoría
                        if (ExisteCategoria(nombre))
                        {
                            lblErrorNombre.Text = "Ya existe una categoría con este nombre";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalRegistro();", true);
                            return;
                        }

                        int resultado = categoriaBO.InsertarCategoria(nombre);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarCategorias();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Categoría registrada exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al registrar la categoría");
                        }
                    }
                    else
                    {
                        // Modificar categoría existente
                        // Verificar si el nombre ya existe en otra categoría
                        if (ExisteCategoria(nombre, categoriaId))
                        {
                            lblErrorNombre.Text = "Ya existe otra categoría con este nombre";
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "reabrirModal",
                                "reabrirModalEditar();", true);
                            return;
                        }

                        int resultado = categoriaBO.ModificarCategoria(categoriaId, nombre);

                        if (resultado > 0)
                        {
                            LimpiarFormulario();
                            CargarCategorias();
                            ScriptManager.RegisterStartupScript(this, this.GetType(), "cerrarYMostrar",
                                "cerrarModal(); mostrarMensajeExito('Categoría actualizada exitosamente');", true);
                        }
                        else
                        {
                            MostrarError("Error al actualizar la categoría");
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
                lblErrorNombre.Text = "El nombre de la categoría es requerido";
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
        /// Verifica si existe una categoría con el mismo nombre (ignorando mayúsculas/minúsculas)
        /// </summary>
        private bool ExisteCategoria(string nombre, int? excluirCategoriaId = null)
        {
            try
            {
                var categorias = categoriaBO.ListarTodosCategoria();

                foreach (var cat in categorias)
                {
                    // Si se pasa un ID para excluir (edición), no comparar con esa categoría
                    if (excluirCategoriaId.HasValue && cat.categoria_id == excluirCategoriaId.Value)
                        continue;

                    // Comparar sin importar mayúsculas/minúsculas
                    if (cat.nombre.Equals(nombre, StringComparison.OrdinalIgnoreCase))
                        return true;
                }

                return false;
            }
            catch (Exception ex)
            {
                MostrarError("Error al validar categoría: " + ex.Message);
                return false;
            }
        }

        private void LimpiarFormulario()
        {
            hfCategoriaId.Value = "0";
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