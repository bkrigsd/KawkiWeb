<%@ Page Title="Registrar Usuario" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="RegistroUsuario.aspx.cs" Inherits="KawkiWeb.RegistroUsuario" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <%--<link href="Content/Stylo/registrouser.css" rel="stylesheet" />--%>
    <link href="Content/Stylo/registrodescuent.css" rel="stylesheet" />
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

        <!-- Filtros -->
        <div class="card-kawki mb-3 filtros-card">
            <div class="card-header">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <h5 class="card-title mb-0 d-flex align-items-center">
                        <i class="fas fa-filter me-2"></i> Filtros de búsqueda
                    </h5>

                    <!-- Ordenamiento pequeño a la derecha -->
                    <div class="filtros-orden d-flex align-items-center">
                        <span class="orden-label me-1">Ordenar:</span>

                        <asp:DropDownList ID="ddlOrdenarPor" runat="server"
                            CssClass="form-select form-select-sm orden-select me-1"
                            AutoPostBack="true"
                            OnSelectedIndexChanged="ActualizarOrden">
                            <asp:ListItem Text="ID"        Value="usuarioId" />
                            <asp:ListItem Text="Nombre"    Value="nombre" />
                            <asp:ListItem Text="Apellido"  Value="apePaterno" />
                            <asp:ListItem Text="DNI"       Value="dni" />
                            <asp:ListItem Text="Usuario"   Value="nombreUsuario" />
                            <asp:ListItem Text="Email"     Value="correo" />
                            <asp:ListItem Text="Teléfono"  Value="telefono" />
                            <asp:ListItem Text="Rol"       Value="tipoUsuario.nombre" />
                            <asp:ListItem Text="Activo"    Value="activo" />
                        </asp:DropDownList>

                        <asp:DropDownList ID="ddlDireccion" runat="server"
                            CssClass="form-select form-select-sm orden-select"
                            AutoPostBack="true"
                            OnSelectedIndexChanged="ActualizarOrden">
                            <asp:ListItem Text="Asc" Value="ASC" />
                            <asp:ListItem Text="Desc" Value="DESC" />
                        </asp:DropDownList>
                    </div>
                </div>
            </div>

            <div class="card-body">
                <div class="row g-3 align-items-end filtros-row">
                    <div class="col-lg-3 col-md-6 filtros-descripcion">
                        <label class="form-label">
                            <i class="fas fa-user me-1"></i>Nombre / Apellido
                        </label>
                        <asp:TextBox ID="txtFiltroNombre" runat="server" CssClass="form-control"
                            placeholder="Buscar por nombre..." />
                    </div>

                    <div class="col-lg-2 col-md-6">
                        <label class="form-label">DNI</label>
                        <asp:TextBox ID="txtFiltroDNI" runat="server" CssClass="form-control"
                            MaxLength="8" AutoPostBack="true" />
                    </div>

                    <div class="col-lg-2 col-md-6">
                        <label class="form-label">Usuario</label>
                        <asp:TextBox ID="txtFiltroUsuario" runat="server" CssClass="form-control"
                            placeholder="Nombre usuario" />
                    </div>

                    <div class="col-lg-2 col-md-6">
                        <label class="form-label">Rol</label>
                        <asp:DropDownList ID="ddlFiltroRol" runat="server" CssClass="form-select">
                            <asp:ListItem Text="-- Todos --" Value="" />
                            <asp:ListItem Text="Administrador" Value="Administrador" />
                            <asp:ListItem Text="Vendedor"      Value="Vendedor" />
                        </asp:DropDownList>
                    </div>

                    <div class="col-lg-2 col-md-6 filtros-estado">
                        <label class="form-label">Estado</label>
                        <asp:DropDownList ID="ddlFiltroEstado" runat="server" CssClass="form-select">
                            <asp:ListItem Text="-- Todos --" Value="" />
                            <asp:ListItem Text="Activo"  Value="true" />
                            <asp:ListItem Text="Inactivo" Value="false" />
                        </asp:DropDownList>
                    </div>

                    <div class="col-lg-2 col-md-6 ms-auto filtros-botones d-flex justify-content-end gap-2">
                        <asp:Button ID="btnBuscar" runat="server"
                            CssClass="btn btn-kawki-primary"
                            Text="Buscar"
                            OnClick="btnBuscar_Click"
                            CausesValidation="false" />

                        <asp:Button ID="btnLimpiar" runat="server"
                            CssClass="btn btn-kawki-outline"
                            Text="Limpiar"
                            OnClick="btnLimpiar_Click"
                            CausesValidation="false" />
                    </div>
                </div>
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

                        <%-- Nuevo: Columna para Activo --%>
                        <asp:TemplateField HeaderText="Activo">
                            <%--<ItemStyle HorizontalAlign="Center" />--%>
                            <ItemTemplate>
                                <%# Convert.ToBoolean(Eval("activo")) 
                                    ? "<i class='fas fa-check-circle' style='color: #2ecc71; font-size:20px;'></i>" 
                                    : "<i class='fas fa-times-circle' style='color: #e74c3c; font-size:20px;'></i>" %>
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
                                            + (
                                                (Eval("tipoUsuario") != null)
                                                    ? (((KawkiWebBusiness.KawkiWebWSUsuarios.tiposUsuarioDTO)Eval("tipoUsuario")).nombre == "Administrador" ? "admin" : "vendedor")
                                                    : ""
                                              )
                                            + "\", \"" 
                                            + Eval("contrasenha") + "\", " 
                                            + Eval("activo").ToString().ToLower() 
                                            + ")" %>'>
                                    Editar
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
                        <asp:Label ID="lblErrorNombre" runat="server" CssClass="text-danger" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server"
                            ControlToValidate="txtNombre"
                            ErrorMessage="Campo requerido"
                            CssClass="text-danger"
                            Display="Dynamic" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Apellido Paterno *</label>
                        <asp:TextBox ID="txtApellidoPaterno" runat="server" CssClass="form-control" />
                        <asp:Label ID="lblErrorApellido" runat="server" CssClass="text-danger" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server"
                            ControlToValidate="txtApellidoPaterno"
                            ErrorMessage="Campo requerido"
                            CssClass="text-danger"
                            Display="Dynamic" />
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">DNI *</label>
                        <asp:TextBox ID="txtDNI" runat="server" CssClass="form-control" MaxLength="8"
                            onkeypress="return soloNumeros(event);"
                            onkeyup="if(this.value.length==8){ validarCampoUnico('DNI', this.value, '<%= lblErrorDNI.ClientID %>'); }" />
                        <asp:Label ID="lblErrorDNI" runat="server" CssClass="text-danger" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server"
                            ControlToValidate="txtDNI"
                            ErrorMessage="Campo requerido"
                            CssClass="text-danger"
                            Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revDNI" runat="server" ControlToValidate="txtDNI"
                            ValidationExpression="^\d{8}$" ErrorMessage="Debe tener 8 dígitos"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Usuario *</label>
                        <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control"
                            onblur="validarCampoUnico('NOMBRE_USUARIO', this.value, '<%= lblErrorUsuario.ClientID %>')" />
                        <asp:Label ID="lblErrorUsuario" runat="server" CssClass="text-danger" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server"
                            ControlToValidate="txtUsuario"
                            ErrorMessage="Campo requerido"
                            CssClass="text-danger"
                            Display="Dynamic" />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email *</label>
                    <asp:TextBox ID="txtEmail" runat="server" CssClass="form-control"
                        onblur="validarCampoUnico('CORREO', this.value, '<%= lblErrorEmail.ClientID %>')" />
                    <asp:Label ID="lblErrorEmail" runat="server" CssClass="text-danger" />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server"
                        ControlToValidate="txtEmail"
                        ErrorMessage="Campo requerido"
                        CssClass="text-danger"
                        Display="Dynamic" />
                    <asp:RegularExpressionValidator ID="revEmail" runat="server" ControlToValidate="txtEmail"
                        ValidationExpression="^[\w\.-]+@([\w-]+\.)+[\w-]{2,4}$"
                        ErrorMessage="Correo inválido" CssClass="text-danger" Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Teléfono *</label>
                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control"
                        MaxLength="9"
                        onkeypress="return soloNumeros(event);"
                        onkeyup="if(this.value.length==9){ validarCampoUnico('TELEFONO', this.value, '<%= lblErrorTelefono.ClientID %>'); }" />
    
                    <asp:Label ID="lblErrorTelefono" runat="server" CssClass="text-danger" />

                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server"
                        ControlToValidate="txtTelefono"
                        ErrorMessage="Campo requerido"
                        CssClass="text-danger"
                        Display="Dynamic" />

                    <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server"
                        ControlToValidate="txtTelefono"
                        ValidationExpression="^9\d{8}$"
                        ErrorMessage="Debe iniciar en 9 y tener 9 dígitos"
                        CssClass="text-danger"
                        Display="Dynamic" />
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

                <!-- Nuevo: Checkbox para Activo -->
                <div class="mb-3">
                    <asp:CheckBox ID="chkActivo" runat="server" Text="Usuario activo" Checked="true" />
                </div>

                <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cancelarUsuario()">Cancelar</button>
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                        Text="Registrar usuario" OnClick="btnGuardar_Click" />
                </div>
            </div>
        </div>

    </div>

    <script>

        function abrirModalRegistroSinLimpiar() {
            document.getElementById("modalUsuario").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-user-plus me-2"></i>Registrar nuevo usuario';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar usuario";
            document.getElementById("lblInfoClave").classList.add("d-none");
        }

        function abrirModalRegistro() {
            document.getElementById("modalUsuario").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-user-plus me-2"></i>Registrar nuevo usuario';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar usuario";
            document.getElementById("lblInfoClave").classList.add("d-none");
            limpiarFormulario();
            resetearValidadoresASP();
        }

        function editarUsuario(id, nombre, apellido, dni, usuario, email, telefono, rol, clave, activo) {
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
            document.getElementById("<%= chkActivo.ClientID %>").checked = (activo === true || activo === "true");

            abrirModalEditar();
        }

        function abrirModalEditar() {
            document.getElementById("modalUsuario").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar usuario';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar usuario";
            document.getElementById("lblInfoClave").classList.remove("d-none");
            resetearValidadoresASP();
        }

        function cancelarUsuario() {
            document.getElementById("modalUsuario").classList.remove("show");
            limpiarFormulario();
            resetModalUsuario()
            //// Cierra el modal
            //cerrarModal();
            //// Hace un REFRESH limpio sin POST ni datos previos
            /*window.location.href = "RegistroUsuario.aspx";*/
        }

        function cerrarModal() {
            document.getElementById("modalUsuario").classList.remove("show");
            limpiarFormulario();
        }

        //function cerrarModalYLimpiar() {
        //    cerrarModal();
        //    limpiarFormulario();
        //}

        function soloNumeros(e) {
            var charCode = e.which ? e.which : e.keyCode;

            // permitir backspace, delete, flechas
            if (charCode === 8 || charCode === 46 || charCode === 37 || charCode === 39) return true;

            // permitir solo números 0–9
            if (charCode < 48 || charCode > 57) return false;

            return true;
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

            document.getElementById("<%= lblErrorDNI.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorEmail.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorTelefono.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorUsuario.ClientID %>").innerText = "";
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";

        }

        <%--function abrirModalConfirmacion(idUsuario) {
            document.getElementById("<%= hfIdEliminar.ClientID %>").value = idUsuario;
            document.getElementById("modalConfirmacion").classList.add("show");
        }--%>

        function cerrarModalConfirmacion() {
            document.getElementById("modalConfirmacion").classList.remove("show");
            limpiarFormulario();
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

        function limpiarFiltros() {
            document.getElementById("<%= txtFiltroNombre.ClientID %>").value = "";
            document.getElementById("<%= txtFiltroDNI.ClientID %>").value = "";
            document.getElementById("<%= txtFiltroUsuario.ClientID %>").value = "";
            document.getElementById("<%= ddlFiltroRol.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= ddlFiltroEstado.ClientID %>").selectedIndex = 0;
            __doPostBack('', '');
        }

        function resetearValidadoresASP() {
            // 1. Hacer que ASP.NET considere que todo está validado
            if (typeof (Page_Validators) !== "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    Page_Validators[i].isvalid = true;

                    // Restaurar display normal sin borrar HTML
                    ValidatorUpdateDisplay(Page_Validators[i]);
                }
            }

            if (typeof (Page_IsValid) !== "undefined") {
                Page_IsValid = true;
            }
        }

        function resetModalUsuario() {

            // 1. Ocultar modal
            document.getElementById("modalUsuario").classList.remove("show");

            // 2. Limpiar formulario
            limpiarFormulario();

            // 3. Ocultar todos los mensajes de validación ASP.NET
            document.querySelectorAll(".text-danger").forEach(x => {
                x.classList.remove("val-hidden");
                x.style.display = "none";
            });

            // 4. Quitar estilos de error en campos
            document.querySelectorAll(".input-validation-error").forEach(x => {
                x.classList.remove("input-validation-error");
            });

            // 5. Resetear validadores ASP.NET sin romperlos
            if (typeof (Page_Validators) !== "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    Page_Validators[i].isvalid = true;
                    ValidatorUpdateDisplay(Page_Validators[i]);
                }
            }
            if (typeof (Page_IsValid) !== "undefined") {
                Page_IsValid = true;
            }

            // 6. Borrar labels de validación AJAX
            document.getElementById("<%= lblErrorDNI.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorEmail.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorTelefono.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorUsuario.ClientID %>").innerText = "";

            // 7. Esconder info clave
            document.getElementById("lblInfoClave").classList.add("d-none");
        }

    </script>

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

</asp:Content>
