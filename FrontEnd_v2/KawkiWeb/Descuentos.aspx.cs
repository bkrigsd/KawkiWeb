using System;
using System.Data;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web.UI;

namespace KawkiWeb
{
    public partial class Descuentos : Page
    {
        private static DataTable descuentosMemoria = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarDescuentos();
            }
        }

        private void CargarDescuentos()
        {
            gvDescuentos.DataSource = ObtenerDescuentosSimulados();
            gvDescuentos.DataBind();
        }

        private DataTable ObtenerDescuentosSimulados()
        {
            if (descuentosMemoria == null)
            {
                descuentosMemoria = new DataTable();
                descuentosMemoria.Columns.Add("IdDescuento", typeof(int));
                descuentosMemoria.Columns.Add("Nombre", typeof(string));
                descuentosMemoria.Columns.Add("Porcentaje", typeof(decimal));
                descuentosMemoria.Columns.Add("FechaInicio", typeof(DateTime));
                descuentosMemoria.Columns.Add("FechaFin", typeof(DateTime));
                descuentosMemoria.Columns.Add("Descripcion", typeof(string));
                descuentosMemoria.Columns.Add("Activo", typeof(bool));

                descuentosMemoria.Rows.Add(1, "Descuento Verano", 10.0m, DateTime.Now.AddDays(-2), DateTime.Now.AddDays(10), "Promoción especial de verano.", true);
                descuentosMemoria.Rows.Add(2, "Liquidación Derby", 25.5m, DateTime.Now.AddDays(-5), DateTime.Now.AddDays(3), "Descuento por liquidación.", false);
            }

            return descuentosMemoria;
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                bool esEdicion = hfIdDescuento.Value != "0";
                string nombre = txtNombre.Text.Trim();
                string porcentajeTxt = txtPorcentaje.Text.Trim();
                string inicioTxt = txtFechaInicio.Text.Trim();
                string finTxt = txtFechaFin.Text.Trim();
                string descripcion = txtDescripcion.Text.Trim();

                // Validaciones
                if (string.IsNullOrEmpty(nombre))
                {
                    lblMensaje.Text = "El nombre es obligatorio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!decimal.TryParse(porcentajeTxt, out decimal porcentaje) || porcentaje < 0 || porcentaje > 100)
                {
                    lblMensaje.Text = "Ingrese un porcentaje válido (0-100).";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!DateTime.TryParse(inicioTxt, out DateTime fechaInicio))
                {
                    lblMensaje.Text = "Ingrese una fecha de inicio válida.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (!DateTime.TryParse(finTxt, out DateTime fechaFin))
                {
                    lblMensaje.Text = "Ingrese una fecha de fin válida.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                if (fechaFin < fechaInicio)
                {
                    lblMensaje.Text = "La fecha fin no puede ser anterior a la fecha inicio.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                DataTable dt = ObtenerDescuentosSimulados();

                if (esEdicion)
                {
                    int idEditar = Convert.ToInt32(hfIdDescuento.Value);
                    DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<int>("IdDescuento") == idEditar);
                    if (fila != null)
                    {
                        fila["Nombre"] = nombre;
                        fila["Porcentaje"] = porcentaje;
                        fila["FechaInicio"] = fechaInicio;
                        fila["FechaFin"] = fechaFin;
                        fila["Descripcion"] = descripcion;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Descuento actualizado correctamente.";
                }
                else
                {
                    int nuevoId = dt.AsEnumerable().Select(r => r.Field<int>("IdDescuento")).DefaultIfEmpty(0).Max() + 1;
                    dt.Rows.Add(nuevoId, nombre, porcentaje, fechaInicio, fechaFin, descripcion, true);
                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Descuento registrado correctamente.";
                }

                LimpiarFormulario();
                CargarDescuentos();
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModal", "cerrarModal();", true);
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error: " + ex.Message;
            }
        }

        protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        {
            try
            {
                int id = Convert.ToInt32(hfIdEliminar.Value);
                DataTable dt = ObtenerDescuentosSimulados();
                DataRow fila = dt.AsEnumerable().FirstOrDefault(r => r.Field<int>("IdDescuento") == id);

                if (fila != null)
                    dt.Rows.Remove(fila);

                CargarDescuentos();
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModalEliminar",
                    "cerrarModalConfirmacion(); mostrarMensajeExito('Descuento eliminado correctamente');", true);
            }
            catch (Exception ex)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "ErrorEliminar",
                    $"cerrarModalConfirmacion(); mostrarMensajeError('Error: {ex.Message.Replace("'", "\\'")}');", true);
            }
        }

        private void MantenerModalAbierto(bool esEdicion)
        {
            string script = esEdicion ? "abrirModalEditar();" : "abrirModalRegistro();";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", script, true);
        }

        private void LimpiarFormulario()
        {
            hfIdDescuento.Value = "0";
            txtNombre.Text = "";
            txtPorcentaje.Text = "";
            txtFechaInicio.Text = "";
            txtFechaFin.Text = "";
            txtDescripcion.Text = "";
        }
    }
}
