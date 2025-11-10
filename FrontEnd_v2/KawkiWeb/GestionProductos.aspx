<%@ Page Title="Gestión de Productos" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="GestionProductos.aspx.cs" Inherits="KawkiWeb.GestionProductos" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrouser.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="usuarios-container">
        <!-- Header -->
        <div class="usuarios-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-boxes-stacked me-2"></i>Gestión de Productos</h1>
                    <p>Administra, crea y actualiza los productos del inventario del sistema Kawki</p>
                </div>
                <button type="button" class="btn-kawki-primary" onclick="abrirModalRegistro()">
                    <i class="fas fa-plus me-1"></i> Nuevo Producto
                </button>
            </div>
        </div>

        <!-- Card con tabla -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="card-title">
                    <i class="fas fa-list"></i>
                    <span>Productos Registrados</span>
                </div>
            </div>
            <div class="card-body">
                <asp:GridView ID="gvProductos" runat="server" AutoGenerateColumns="False" CssClass="table-usuarios" DataKeyNames="Codigo" OnRowCommand="gvProductos_RowCommand">
                    <Columns>
                        <asp:BoundField DataField="Codigo" HeaderText="Código" />
                        <asp:BoundField DataField="Nombre" HeaderText="Nombre" />
                        <asp:BoundField DataField="Categoria" HeaderText="Categoría" />
                        <asp:BoundField DataField="Color" HeaderText="Color" />
                        <asp:BoundField DataField="Precio" HeaderText="Precio (S/.)" DataFormatString="{0:0.00}" />
                        <asp:BoundField DataField="Stock" HeaderText="Stock" />
                        <asp:TemplateField HeaderText="Estado">
                            <ItemTemplate>
                                <span class='<%# Convert.ToBoolean(Eval("Activo")) ? "badge-rol badge-activo" : "badge-rol badge-inactivo" %>'>
                                    <%# Convert.ToBoolean(Eval("Activo")) ? "Activo" : "Inactivo" %>
                                </span>
                            </ItemTemplate>
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <button type="button" class="btn-editar"
                                    onclick='editarProducto("<%# Eval("Codigo") %>", "<%# Eval("Nombre") %>", "<%# Eval("Categoria") %>", "<%# Eval("Color") %>", "<%# Eval("Precio") %>", "<%# Eval("Stock") %>", "<%# Eval("Descripcion") %>", "<%# Eval("Activo") %>")'>
                                    Editar
                                </button>
                                <button type="button" class="btn-eliminar" onclick='abrirModalConfirmacion("<%# Eval("Codigo") %>")'>
                                    Eliminar
                                </button>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                </asp:GridView>
            </div>
        </div>

        <!-- Modal de Registro/Edición -->
        <div id="modalProducto" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h5 id="tituloModal"><i class="fas fa-plus me-2"></i>Registrar nuevo producto</h5>
                </div>

                <asp:HiddenField ID="hfCodigo" runat="server" Value="0" />

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Nombre *</label>
                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvNombre" runat="server" ControlToValidate="txtNombre"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Precio (S/.) *</label>
                        <asp:TextBox ID="txtPrecio" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvPrecio" runat="server" ControlToValidate="txtPrecio"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revPrecio" runat="server" ControlToValidate="txtPrecio"
                            ValidationExpression="^\d+(\.\d{1,2})?$" ErrorMessage="Ingrese un número válido"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Categoría *</label>
                        <asp:DropDownList ID="ddlCategoria" runat="server" CssClass="form-select">
                            <asp:ListItem Text="-- Seleccione --" Value="" />
                            <asp:ListItem>Oxford</asp:ListItem>
                            <asp:ListItem>Derby</asp:ListItem>
                            <asp:ListItem>Casual</asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="rfvCategoria" runat="server" ControlToValidate="ddlCategoria"
                            InitialValue="" ErrorMessage="Seleccione una categoría" CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Color *</label>
                        <asp:TextBox ID="txtColor" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvColor" runat="server" ControlToValidate="txtColor"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Stock *</label>
                        <asp:TextBox ID="txtStock" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvStock" runat="server" ControlToValidate="txtStock"
                            ErrorMessage="Campo requerido" CssClass="text-danger" Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revStock" runat="server" ControlToValidate="txtStock"
                            ValidationExpression="^\d+$" ErrorMessage="Debe ingresar un número entero"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <asp:TextBox ID="txtDescripcion" runat="server" TextMode="MultiLine"
                        CssClass="form-control" Rows="3" />
                </div>

                <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">Cancelar</button>
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                        Text="Registrar producto" OnClick="btnGuardar_Click" />
                </div>
            </div>
        </div>

        <!-- Modal Confirmación -->
        <div id="modalConfirmacion" class="modal-confirmacion">
            <div class="modal-content-kawki">
                <div class="modal-icon">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
                <h5>¿Confirmar eliminación?</h5>
                <p>Esta acción no se puede deshacer</p>
                <asp:HiddenField ID="hfCodigoEliminar" runat="server" Value="0" />
                <div>
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalConfirmacion()">Cancelar</button>
                    <asp:Button ID="btnConfirmarEliminar" runat="server" CssClass="btn-kawki-primary"
                        style="background-color:#dc3545;" Text="Eliminar"
                        OnClick="btnConfirmarEliminar_Click" CausesValidation="false" UseSubmitBehavior="true" />
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script>
        function abrirModalRegistro() {
            document.getElementById("modalProducto").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo producto';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar producto";
            limpiarFormulario();
        }

        function abrirModalEditar() {
            document.getElementById("modalProducto").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar producto';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar producto";
        }

        function cerrarModal() {
            document.getElementById("modalProducto").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfCodigo.ClientID %>").value = "0";
            document.getElementById("<%= txtNombre.ClientID %>").value = "";
            document.getElementById("<%= txtPrecio.ClientID %>").value = "";
            document.getElementById("<%= ddlCategoria.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= txtColor.ClientID %>").value = "";
            document.getElementById("<%= txtStock.ClientID %>").value = "";
            document.getElementById("<%= txtDescripcion.ClientID %>").value = "";
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
        }

        function abrirModalConfirmacion(codigo) {
            document.getElementById("<%= hfCodigoEliminar.ClientID %>").value = codigo;
            document.getElementById("modalConfirmacion").classList.add("show");
        }

        function cerrarModalConfirmacion() {
            document.getElementById("modalConfirmacion").classList.remove("show");
        }

        function editarProducto(codigo, nombre, categoria, color, precio, stock, descripcion) {
            document.getElementById("<%= hfCodigo.ClientID %>").value = codigo;
            document.getElementById("<%= txtNombre.ClientID %>").value = nombre;
            document.getElementById("<%= ddlCategoria.ClientID %>").value = categoria;
            document.getElementById("<%= txtColor.ClientID %>").value = color;
            document.getElementById("<%= txtPrecio.ClientID %>").value = precio;
            document.getElementById("<%= txtStock.ClientID %>").value = stock;
            document.getElementById("<%= txtDescripcion.ClientID %>").value = descripcion;
            abrirModalEditar();
        }

        function mostrarMensajeExito(mensaje) {
            var mensajeDiv = document.createElement('div');
            mensajeDiv.className = 'alert alert-success alert-dismissible fade show position-fixed';
            mensajeDiv.style.top = '20px';
            mensajeDiv.style.right = '20px';
            mensajeDiv.style.zIndex = '9999';
            mensajeDiv.innerHTML = mensaje + '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';
            document.body.appendChild(mensajeDiv);
            setTimeout(function () { mensajeDiv.remove(); }, 3000);
        }

        function mostrarMensajeError(mensaje) {
            var mensajeDiv = document.createElement('div');
            mensajeDiv.className = 'alert alert-danger alert-dismissible fade show position-fixed';
            mensajeDiv.style.top = '20px';
            mensajeDiv.style.right = '20px';
            mensajeDiv.style.zIndex = '9999';
            mensajeDiv.innerHTML = mensaje + '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';
            document.body.appendChild(mensajeDiv);
            setTimeout(function () { mensajeDiv.remove(); }, 5000);
        }
    </script>
</asp:Content>
