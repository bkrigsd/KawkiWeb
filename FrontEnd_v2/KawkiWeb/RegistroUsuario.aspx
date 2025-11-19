<%@ Page Title="Registrar Usuario" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="RegistroUsuario.aspx.cs" Inherits="KawkiWeb.RegistroUsuario" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrouser.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="usuarios-container">
        <!-- Header -->
        <div class="usuarios-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-users me-2"></i>Gestión de Usuarios</h1>
                    <p>Administra los usuarios del sistema Kawki</p>
                </div>
                <button type="button" class="btn-kawki-primary" onclick="abrirModalRegistro()">
                    <i class="fas fa-user-plus me-1"></i> Nuevo Usuario
                </button>
            </div>
        </div>

        <!-- Card con tabla -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="card-title">
                    <i class="fas fa-list"></i>
                    <span>Usuarios Registrados</span>
                </div>
            </div>
            <div class="card-body">
                <asp:GridView ID="gvUsuarios" runat="server" AutoGenerateColumns="False"
                    CssClass="table-usuarios" DataKeyNames="usuarioId">
                    <Columns>
                        <asp:BoundField DataField="usuarioId" HeaderText="ID" />
                        <asp:BoundField DataField="nombre" HeaderText="Nombre" />
                        <asp:BoundField DataField="apePaterno" HeaderText="Apellido" />
                        <asp:BoundField DataField="dni" HeaderText="DNI" />
                        <asp:BoundField DataField="nombreUsuario" HeaderText="Usuario" />
                        <asp:BoundField DataField="correo" HeaderText="Email" />
                        <asp:BoundField DataField="telefono" HeaderText="Teléfono" />

                        <%-- Rol (objeto anidado tipoUsuario) --%>
                        <asp:TemplateField HeaderText="Rol">
                            <ItemTemplate>
                                <%# (Eval("tipoUsuario") != null) 
                                    ? ((KawkiWebBusiness.KawkiWebWSUsuarios.tiposUsuarioDTO)Eval("tipoUsuario")).nombre 
                                    : "" %>
                            </ItemTemplate>
                        </asp:TemplateField>

                        <%-- Acciones --%>
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <button type="button" class="btn-editar"
                                    onclick='<%# "editarUsuario(" 
                                        + Eval("usuarioId") + ", \"" 
                                        + Eval("nombre") + "\", \"" 
                                        + Eval("apePaterno") + "\", \"" 
                                        + Eval("dni") + "\", \"" 
                                        + Eval("nombreUsuario") + "\", \"" 
                                        + Eval("correo") + "\", \"" 
                                        + Eval("telefono") + "\", \"" 
                                        + ((Eval("tipoUsuario") != null) 
                                            ? ((KawkiWebBusiness.KawkiWebWSUsuarios.tiposUsuarioDTO)Eval("tipoUsuario")).nombre 
                                            : "") + "\", \"" 
                                        + Eval("contrasenha") + "\")" %>'>
                                    Editar
                                </button>
                                <button type="button" class="btn-eliminar" 
                                    onclick='<%# "abrirModalConfirmacion(" + Eval("usuarioId") + ")" %>'>
                                    Eliminar
                                </button>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                </asp:GridView>

            </div>
        </div>

        <!-- Modal de Registro/Edición -->
        <div id="modalUsuario" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h5 id="tituloModal"><i class="fas fa-user-plus me-2"></i>Registrar nuevo usuario</h5>
                </div>

                <asp:HiddenField ID="hfIdUsuario" runat="server" Value="0" />

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Nombre *</label>
                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvNombre" runat="server" ControlToValidate="txtNombre"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Apellido Paterno *</label>
                        <asp:TextBox ID="txtApellidoPaterno" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvApellido" runat="server" ControlToValidate="txtApellidoPaterno"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">DNI *</label>
                        <asp:TextBox ID="txtDNI" runat="server" CssClass="form-control" MaxLength="8" />
                        <asp:RequiredFieldValidator ID="rfvDNI" runat="server" ControlToValidate="txtDNI"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revDNI" runat="server" ControlToValidate="txtDNI"
                            ValidationExpression="^\d{8}$" ErrorMessage="Debe tener 8 dígitos"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Usuario *</label>
                        <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvUsuario" runat="server" ControlToValidate="txtUsuario"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email *</label>
                    <asp:TextBox ID="txtEmail" runat="server" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="rfvEmail" runat="server" ControlToValidate="txtEmail"
                        ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    <asp:RegularExpressionValidator ID="revEmail" runat="server" ControlToValidate="txtEmail"
                        ValidationExpression="^[\w\.-]+@([\w-]+\.)+[\w-]{2,4}$"
                        ErrorMessage="Correo inválido" CssClass="text-danger" Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Teléfono *</label>
                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control" MaxLength="9" />
                    <asp:RequiredFieldValidator ID="rfvTelefono" runat="server" ControlToValidate="txtTelefono"
                        ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    <asp:RegularExpressionValidator ID="revTelefono" runat="server" ControlToValidate="txtTelefono"
                        ValidationExpression="^\d{9}$" ErrorMessage="Debe tener 9 dígitos"
                        CssClass="text-danger" Display="Dynamic" />
                </div>

                <div class="mb-3 position-relative">
                    <label class="form-label">Contraseña *</label>

                    <div class="input-group">
                        <asp:TextBox ID="txtClave" runat="server" TextMode="Password" CssClass="form-control" />
                        <button type="button" id="btnToggleClave" class="btn btn-outline-secondary" onclick="togglePassword()" title="Mostrar/Ocultar">
                            <i id="iconoClave" class="fas fa-eye"></i>
                        </button>
                    </div>

                    <asp:RequiredFieldValidator ID="rfvClave" runat="server"
                        ControlToValidate="txtClave"
                        ErrorMessage="Campo requerido"
                        CssClass="text-danger"
                        Display="Dynamic" />
                    <asp:RegularExpressionValidator ID="revClave" runat="server"
                        ControlToValidate="txtClave"
                        ValidationExpression="^.{8,}$"
                        ErrorMessage="Mínimo 8 caracteres"
                        CssClass="text-danger"
                        Display="Dynamic" />


                    <small id="lblInfoClave" class="text-muted d-none">
                        Deja la contraseña si no deseas cambiarla.
                    </small>
                </div>

                <div id="grupoRol" class="mb-3">
                    <label class="form-label">Rol *</label>
                    <asp:DropDownList ID="ddlRol" runat="server" CssClass="form-select">
                        <asp:ListItem Text="-- Seleccione --" Value="" />
                        <asp:ListItem Text="Administrador" Value="admin" />
                        <asp:ListItem Text="Vendedor" Value="vendedor" />
                    </asp:DropDownList>
                    <asp:RequiredFieldValidator ID="rfvRol" runat="server" ControlToValidate="ddlRol"
                        InitialValue="" ErrorMessage="Seleccione un rol" CssClass="text-danger" Display="Dynamic" />
                </div>

                <!-- Campo solo lectura visible en edición -->
                <div id="grupoRolTexto" class="mb-3 d-none">
                    <label class="form-label">Rol</label>
                    <input type="text" id="txtRolLectura" class="form-control" readonly />
                </div>


                <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">Cancelar</button>
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                        Text="Registrar usuario" OnClick="btnGuardar_Click" />
                </div>
            </div>
        </div>

        <!-- Modal de confirmación de eliminación -->
        <div id="modalConfirmacion" class="modal-confirmacion">
            <div class="modal-content-kawki">
                <div class="modal-icon">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
                <h5>¿Confirmar eliminación?</h5>
                <p>Esta acción no se puede deshacer</p>
                <asp:HiddenField ID="hfIdEliminar" runat="server" Value="0" />
                <div>
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalConfirmacion()">Cancelar</button>
                    <asp:Button ID="btnConfirmarEliminar" runat="server" CssClass="btn-kawki-primary" style="background-color: #dc3545;"
                        Text="Eliminar" OnClick="btnConfirmarEliminar_Click" CausesValidation="false" UseSubmitBehavior="true" />
                </div>
            </div>
        </div>
    </div>

    <script>
        function abrirModalRegistro() {
            document.getElementById("modalUsuario").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-user-plus me-2"></i>Registrar nuevo usuario';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar usuario";
            limpiarFormulario();
            document.getElementById("lblInfoClave").classList.add("d-none");
        }

        function abrirModalEditar() {
            document.getElementById("modalUsuario").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar usuario';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar usuario";
            document.getElementById("lblInfoClave").classList.remove("d-none");
        }

        function editarUsuario(id, nombre, apellido, dni, usuario, email, telefono, rol, clave) {
            document.getElementById("<%= hfIdUsuario.ClientID %>").value = id;
            document.getElementById("<%= txtNombre.ClientID %>").value = nombre;
            document.getElementById("<%= txtApellidoPaterno.ClientID %>").value = apellido;
            document.getElementById("<%= txtDNI.ClientID %>").value = dni;
            document.getElementById("<%= txtUsuario.ClientID %>").value = usuario;
            document.getElementById("<%= txtEmail.ClientID %>").value = email;
            document.getElementById("<%= txtTelefono.ClientID %>").value = telefono;
            document.getElementById("<%= ddlRol.ClientID %>").value = rol;
            document.getElementById("<%= txtClave.ClientID %>").value = clave;
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            
            abrirModalEditar();
        }

        function cerrarModal() {
            document.getElementById("modalUsuario").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfIdUsuario.ClientID %>").value = "0";
            document.getElementById("<%= txtNombre.ClientID %>").value = "";
            document.getElementById("<%= txtApellidoPaterno.ClientID %>").value = "";
            document.getElementById("<%= txtDNI.ClientID %>").value = "";
            document.getElementById("<%= txtUsuario.ClientID %>").value = "";
            document.getElementById("<%= txtEmail.ClientID %>").value = "";
            document.getElementById("<%= txtTelefono.ClientID %>").value = "";
            document.getElementById("<%= txtClave.ClientID %>").value = "";
            document.getElementById("<%= ddlRol.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
        }

        function abrirModalConfirmacion(idUsuario) {
            document.getElementById("<%= hfIdEliminar.ClientID %>").value = idUsuario;
            document.getElementById("modalConfirmacion").classList.add("show");
        }

        function cerrarModalConfirmacion() {
            document.getElementById("modalConfirmacion").classList.remove("show");
        }

        function mostrarMensajeExito(mensaje) {
            var mensajeDiv = document.createElement('div');
            mensajeDiv.className = 'alert alert-success alert-dismissible fade show position-fixed';
            mensajeDiv.style.top = '20px';
            mensajeDiv.style.right = '20px';
            mensajeDiv.style.zIndex = '9999';
            mensajeDiv.innerHTML = mensaje + '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';

            // Agregar al cuerpo del documento
            document.body.appendChild(mensajeDiv);

            // Eliminar después de 3 segundos
            setTimeout(function () {
                mensajeDiv.remove();
            }, 3000);
        }

        function mostrarMensajeError(mensaje) {
            // Crear un elemento div para el mensaje
            var mensajeDiv = document.createElement('div');
            mensajeDiv.className = 'alert alert-danger alert-dismissible fade show position-fixed';
            mensajeDiv.style.top = '20px';
            mensajeDiv.style.right = '20px';
            mensajeDiv.style.zIndex = '9999';
            mensajeDiv.innerHTML = mensaje + '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';

            // Agregar al cuerpo del documento
            document.body.appendChild(mensajeDiv);

            // Eliminar después de 5 segundos
            setTimeout(function () {
                mensajeDiv.remove();
            }, 5000);
        }

        function togglePassword() {
            const input = document.getElementById("<%= txtClave.ClientID %>");
            const icono = document.getElementById("iconoClave");

            if (input.type === "password") {
                input.type = "text";
                icono.classList.remove("fa-eye");
                icono.classList.add("fa-eye-slash");
            } else {
                input.type = "password";
                icono.classList.remove("fa-eye-slash");
                icono.classList.add("fa-eye");
            }
        }

    </script>
<<<<<<< HEAD

    <script>
        function validarCampoUnico(campo, valor, labelErrorId) {
            var idActual = document.getElementById("<%= hfIdUsuario.ClientID %>").value;

            if (valor.trim() === "") {
                document.getElementById(labelErrorId).innerText = "";
                return;
            }

            $.ajax({
                type: "POST",
                url: "RegistroUsuario.aspx/ValidarUnicoAjax",
                data: JSON.stringify({ campo: campo, valor: valor, idActual: parseInt(idActual) }),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (response) {
                    var esUnico = response.d;
                    if (!esUnico) {
                        document.getElementById(labelErrorId).innerText = campo + " ya existe.";
                    } else {
                        document.getElementById(labelErrorId).innerText = "";
                    }
                }
            });
        }
    </script>

<<<<<<< HEAD
</asp:Content>
=======
</asp:Content>
>>>>>>> parent of 0c7614b (guardar)
=======
</asp:Content>
>>>>>>> parent of 33b2d43 (Merge remote-tracking branch 'origin/Fabio' into Angelina)
