using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSUsuarios;
using System;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class RegistroUsuario : Page
    {
        private UsuarioBO usuarioBO = new UsuarioBO();

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
                if (Session["MantenerModal"] != null)
                {
                    string modo = Session["MantenerModal"].ToString();
                    Session["MantenerModal"] = null;

                    if (modo == "editar")
                        ScriptManager.RegisterStartupScript(this, GetType(), "AbrirEditar", "abrirModalEditar();", true);
                    else
                        ScriptManager.RegisterStartupScript(this, GetType(), "AbrirRegistrar", "abrirModalRegistroSinLimpiar();", true);
                }

                var rol = Session["Rol"] as string;

                // Verificación de sesión
                if (string.IsNullOrEmpty(rol))
                {
                    Response.Redirect("Error404.aspx", false);
                    return;
                }

                if (rol == "vendedor")
                {
                    Response.Redirect("Productos.aspx", false);
                    return;
                }

                CargarUsuarios();
            }
        }

        // Cargar usuarios desde el servicio SOAP
        private void CargarUsuarios()
        {
            try
            {
                var lista = usuarioBO.ListarTodosUsuario();
                lblMensaje.Text = $"Usuarios devueltos: {(lista == null ? 0 : lista.Count)}";
                if (!string.IsNullOrEmpty(txtFiltroNombre.Text) ||
                    !string.IsNullOrEmpty(txtFiltroDNI.Text) ||
                    !string.IsNullOrEmpty(txtFiltroUsuario.Text) ||
                    !string.IsNullOrEmpty(ddlFiltroRol.SelectedValue) ||
                    !string.IsNullOrEmpty(ddlFiltroEstado.SelectedValue))
                {
                    AplicarFiltros(null, null);
                }
                else
                {
                    gvUsuarios.DataSource = lista;
                    gvUsuarios.DataBind();
                }
            }
            catch (Exception ex)
            {
                lblMensaje.Text = "Error al cargar usuarios: " + ex.Message;
                lblMensaje.CssClass = "text-danger d-block mb-2";
            }
        }

        protected void AplicarFiltros(object sender, EventArgs e)
        {
            var lista = usuarioBO.ListarTodosUsuario();

            if (lista == null) return;

            string filtroNombre = txtFiltroNombre.Text.Trim().ToLower();
            string filtroDNI = txtFiltroDNI.Text.Trim();
            string filtroUsuario = txtFiltroUsuario.Text.Trim().ToLower();
            string filtroRol = ddlFiltroRol.SelectedValue;
            string filtroEstado = ddlFiltroEstado.SelectedValue;

            var filtrado = lista.AsEnumerable();

            if (!string.IsNullOrEmpty(filtroNombre))
            {
                filtrado = filtrado.Where(x =>
                    (x.nombre + " " + x.apePaterno).ToLower().Contains(filtroNombre));
            }

            if (!string.IsNullOrEmpty(filtroDNI))
            {
                filtrado = filtrado.Where(x => x.dni.Contains(filtroDNI));
            }

            if (!string.IsNullOrEmpty(filtroUsuario))
            {
                filtrado = filtrado.Where(x => x.nombreUsuario.ToLower().Contains(filtroUsuario));
            }

            if (!string.IsNullOrEmpty(filtroRol))
            {
                filtrado = filtrado.Where(x => x.tipoUsuario != null && x.tipoUsuario.nombre == filtroRol);
            }

            if (!string.IsNullOrEmpty(filtroEstado))
            {
                bool estado = filtroEstado == "true";
                filtrado = filtrado.Where(x => x.activo == estado);
            }

            // APLICAR ORDEN
            if (!string.IsNullOrEmpty(SortField))
            {
                if (SortDirection == "ASC")
                    filtrado = filtrado.OrderBy(x => ObtenerValorOrden(x, SortField));
                else
                    filtrado = filtrado.OrderByDescending(x => ObtenerValorOrden(x, SortField));
            }

            gvUsuarios.DataSource = filtrado.ToList();
            gvUsuarios.DataBind();

        }

        //  Guardar (insertar o modificar)
        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            // Limpiar mensajes antiguos
            lblErrorDNI.Text = "";
            lblErrorEmail.Text = "";
            lblErrorTelefono.Text = "";
            lblErrorUsuario.Text = "";
            lblMensaje.Text = "";
            try
            {
                bool esEdicion = hfIdUsuario.Value != "0";
                string nombre = txtNombre.Text.Trim();
                string apellido = txtApellidoPaterno.Text.Trim();
                string dni = txtDNI.Text.Trim();
                string usuario = txtUsuario.Text.Trim();
                string email = txtEmail.Text.Trim();
                string telefono = txtTelefono.Text.Trim();
                string clave = txtClave.Text.Trim();
                string rol = ddlRol.SelectedValue;

                // === VALIDACIONES ===
                // Removidas las llamadas a usuarioBO.validar* porque no existen en el WS.
                // Implementa validaciones locales aquí si es necesario, o agrégalas al WS.
                // Ejemplo: Validaciones básicas con Regex (ya están en el código original).

                if (!Regex.IsMatch(dni, @"^\d{8}$"))
                {
                    lblErrorDNI.Text = "El DNI debe tener 8 dígitos numéricos.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!Regex.IsMatch(email, @"^[\w\.-]+@([\w-]+\.)+[\w-]{2,}$"))
                {
                    lblErrorEmail.Text = "Ingrese un correo electrónico válido.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!Regex.IsMatch(telefono, @"^\d{9}$"))
                {
                    lblErrorTelefono.Text = "El teléfono debe tener 9 dígitos.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (!esEdicion && string.IsNullOrEmpty(clave))
                {
                    lblMensaje.Text = "La contraseña es obligatoria para registrar un usuario nuevo.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }
                else if (!string.IsNullOrEmpty(clave) && clave.Length < 6)
                {
                    lblMensaje.Text = "La contraseña debe tener al menos 6 caracteres.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                if (string.IsNullOrEmpty(rol))
                {
                    lblMensaje.Text = "Seleccione un rol.";
                    MantenerModalAbierto(esEdicion);
                    return;
                }

                // VERIFICACIÓN DE UNICIDAD

                int idActual = esEdicion ? Convert.ToInt32(hfIdUsuario.Value) : 0;

                // Llamar a tu método de negocio (NO directamente al WS)
                bool[] unicidad = usuarioBO.VerificarUnicidad(
                    email.Trim().ToLower(),
                    usuario.Trim().ToLower(),
                    dni.Trim(),
                    idActual
                );

                // resultado: [correoExiste, usuarioExiste, dniExiste]
                bool correoExiste = unicidad[0];
                bool usuarioExiste = unicidad[1];
                bool dniExiste = unicidad[2];
                bool hayError = false;

                if (correoExiste)
                {
                    lblErrorEmail.Text = "❌ El correo ya está registrado.";
                    hayError = true;
                }

                if (usuarioExiste)
                {
                    lblErrorUsuario.Text = "❌ El nombre de usuario ya está registrado.";
                    hayError = true;
                }

                if (dniExiste)
                {
                    lblErrorDNI.Text = "❌ El DNI ya está registrado.";
                    hayError = true;
                }

                if (hayError)
                {
                    RestaurarPassword(clave);
                    MantenerModalAbierto(esEdicion);
                    return;
                }


                // Crear objeto tipoUsuario
                var tipoUsuario = new tiposUsuarioDTO();
                string rolSeleccionado = ddlRol.SelectedValue.Trim().ToLower();

                if (rolSeleccionado == "admin" || rolSeleccionado == "administrador")
                {
                    tipoUsuario.tipoUsuarioId = 2;
                    tipoUsuario.nombre = "Administrador";
                }
                else if (rolSeleccionado == "vendedor")
                {
                    tipoUsuario.tipoUsuarioId = 1;
                    tipoUsuario.nombre = "Vendedor";
                }
                else
                {
                    tipoUsuario.tipoUsuarioId = 1;
                    tipoUsuario.nombre = "Vendedor";
                }

                tipoUsuario.tipoUsuarioIdSpecified = true;

                // Determinar 'activo'
                // Obtener estado del checkbox
                bool activo = chkActivo.Checked;

                // Guardar
                if (esEdicion)
                {
                    int idUsuario = Convert.ToInt32(hfIdUsuario.Value);
                    if (string.IsNullOrEmpty(clave))
                    {
                        var usuarioExistente = usuarioBO.ObtenerPorIdUsuario(idUsuario);
                        if (usuarioExistente != null)
                        {
                            clave = usuarioExistente.contrasenha;
                        }
                    }
                    // Actualizado: Agregado 'activo'
                    int? resultado = usuarioBO.ModificarUsuario(idUsuario, nombre, apellido, dni, telefono, email, usuario, clave, tipoUsuario, activo);

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "❌ No se pudo actualizar el usuario.";
                        Session["MantenerModal"] = esEdicion ? "editar" : "registrar";
                        return;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario actualizado correctamente.";
                }
                else
                {
                    // Actualizado: Agregado 'activo'
                    int? resultado = usuarioBO.InsertarUsuario(nombre, apellido, dni, telefono, email, usuario, clave, tipoUsuario, activo);

                    if (resultado == null || resultado <= 0)
                    {
                        lblMensaje.CssClass = "text-danger d-block mb-2";
                        lblMensaje.Text = "❌ Error: No se pudo registrar el usuario.";
                        Session["MantenerModal"] = esEdicion ? "editar" : "registrar";
                        return;
                    }

                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = "✓ Usuario registrado correctamente.";
                }

                CargarUsuarios();
                ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModal", "cerrarModal();", true);
            }
            catch (Exception ex)
            {
                string error = ex.Message.ToUpper();

                bool esEdicion = hfIdUsuario.Value != "0";

                if (error.Contains("CORREO_EXISTE"))
                {
                    lblErrorEmail.Text = "❌ El correo ya está registrado.";
                    Session["MantenerModal"] = esEdicion ? "editar" : "registrar";
                    return;
                }

                if (error.Contains("USUARIO_EXISTE"))
                {
                    lblErrorUsuario.Text = "❌ El nombre de usuario ya está registrado.";
                    Session["MantenerModal"] = esEdicion ? "editar" : "registrar";
                    return;
                }

                if (error.Contains("DNI_EXISTE"))
                {
                    lblErrorDNI.Text = "❌ El DNI ya está registrado.";
                    Session["MantenerModal"] = esEdicion ? "editar" : "registrar";
                    return;
                }

                lblMensaje.Text = "❌ Error inesperado: " + ex.Message;
                MantenerModalAbierto(esEdicion);
            }
        }

        // =====================================================
        // 🔹 Eliminar usuario
        // =====================================================
        //protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        int idUsuario = Convert.ToInt32(hfIdEliminar.Value);
        //        usuarioBO.EliminarUsuario(idUsuario);
        //        CargarUsuarios();

        //        ScriptManager.RegisterStartupScript(this, GetType(), "CerrarModalEliminar",
        //            "cerrarModalConfirmacion(); mostrarMensajeExito('Usuario eliminado correctamente');", true);
        //    }
        //    catch (Exception ex)
        //    {
        //        ScriptManager.RegisterStartupScript(this, GetType(), "ErrorEliminar",
        //            "cerrarModalConfirmacion(); mostrarMensajeError('Error al eliminar: " + ex.Message.Replace("'", "\\'") + "');", true);
        //    }
        //}

        // =====================================================
        // 🔹 Utilidades
        // =====================================================
        private void MantenerModalAbierto(bool esEdicion)
        {
            string script = esEdicion ? "abrirModalEditar();" : "abrirModalRegistroSinLimpiar();";
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", script, true);
        }

        private void RestaurarPassword(string clave)
        {
            txtClave.Attributes["value"] = clave;
        }

        private string SortField
        {
            get => ViewState["SortField"]?.ToString() ?? "";
            set => ViewState["SortField"] = value;
        }

        private string SortDirection
        {
            get => ViewState["SortDirection"]?.ToString() ?? "ASC";
            set => ViewState["SortDirection"] = value;
        }

        private object ObtenerValorOrden(object obj, string campo)
        {
            try
            {
                if (campo.Contains("."))
                {
                    var partes = campo.Split('.');
                    var valor = obj.GetType().GetProperty(partes[0]).GetValue(obj, null);

                    if (valor == null) return "";

                    return valor.GetType().GetProperty(partes[1]).GetValue(valor, null);
                }
                else
                {
                    return obj.GetType().GetProperty(campo).GetValue(obj, null);
                }
            }
            catch
            {
                return "";
            }
        }

        protected void ActualizarOrden(object sender, EventArgs e)
        {
            SortField = ddlOrdenarPor.SelectedValue;
            SortDirection = ddlDireccion.SelectedValue;

            AplicarFiltros(null, null); // se vuelve a filtrar y ordenar
        }

    }
}
