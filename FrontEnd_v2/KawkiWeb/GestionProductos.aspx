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
                    <p>Administra los productos del inventario (zapatos con múltiples variantes)</p>
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
                <asp:GridView ID="gvProductos" runat="server" AutoGenerateColumns="False" 
                    CssClass="table-usuarios" DataKeyNames="ProductoId" 
                    OnRowCommand="gvProductos_RowCommand">
                    <Columns>
                        <asp:BoundField DataField="ProductoId" HeaderText="ID" />
                        <asp:BoundField DataField="Descripcion" HeaderText="Descripción" />
                        <asp:BoundField DataField="Categoria" HeaderText="Categoría" />
                        <asp:BoundField DataField="Estilo" HeaderText="Estilo" />
                        <asp:BoundField DataField="Precio" HeaderText="Precio (S/.)" 
                            DataFormatString="{0:N2}" />
                        <asp:BoundField DataField="CantidadVariantes" HeaderText="# Variantes" />

                        <asp:BoundField DataField="IdCategoria" Visible="false" />
                        <asp:BoundField DataField="IdEstilo" Visible="false" />
                        
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <asp:LinkButton runat="server" 
                                    CommandName="Editar" 
                                    CommandArgument='<%# Eval("ProductoId") %>'
                                    CssClass="btn-editar"
                                    CausesValidation="false">
                                    <i class="fas fa-edit"></i> Editar
                                </asp:LinkButton>

                                <asp:LinkButton runat="server" 
                                    CommandName="VerVariantes" 
                                    CommandArgument='<%# Eval("ProductoId") %>'
                                    CssClass="btn-variantes-sm"
                                    CausesValidation="false">
                                    <i class="fas fa-palette"></i> Variantes
                                </asp:LinkButton>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                    <EmptyDataTemplate>
                        <div class="text-center p-4">
                            <i class="fas fa-box-open fa-3x text-muted mb-3"></i>
                            <p class="text-muted">No hay productos registrados</p>
                        </div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>
    </div>

    <!-- Modal de Registro/Edición -->
    <div id="modalProducto" class="modal-kawki">
        <div class="modal-content-kawki">
            <div class="modal-header-kawki">
                <h5 id="tituloModal"><i class="fas fa-plus me-2"></i>Registrar nuevo producto</h5>
            </div>

            <asp:HiddenField ID="hfProductoId" runat="server" Value="0" />

            <div class="mb-3">
                <label class="form-label">Descripción del Producto *</label>
                <asp:TextBox ID="txtDescripcion" runat="server" CssClass="form-control" 
                    placeholder="Ej: Zapato Oxford Clásico en Cuero Premium" />
                <asp:Label ID="lblErrorDescripcion" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <div class="row">
                <div class="col-md-4 mb-3">
                    <label class="form-label">Categoría *</label>
                    <asp:DropDownList ID="ddlCategoria" runat="server" CssClass="form-select">
                    </asp:DropDownList>
                    <div><asp:Label ID="lblErrorCategoria" runat="server" CssClass="text-danger small" /></div>
                </div>
    
                <div class="col-md-4 mb-3">
                    <label class="form-label">Estilo *</label>
                    <asp:DropDownList ID="ddlEstilo" runat="server" CssClass="form-select">
                    </asp:DropDownList>
                    <div><asp:Label ID="lblErrorEstilo" runat="server" CssClass="text-danger small" /></div>
                </div>
            
                <div class="col-md-4 mb-3">
                    <label class="form-label">Precio (S/.) *</label>
                    <asp:TextBox ID="txtPrecio" runat="server" CssClass="form-control" 
                        placeholder="0.00" type="number" step="0.01"/>
                    <asp:Label ID="lblErrorPrecio" runat="server" CssClass="text-danger small d-block mt-1" />
                </div>
            </div>

            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i>
                <strong>Nota:</strong> Los colores, tallas y stock se gestionan en las 
                <strong>Variantes de Producto</strong> después de crear el producto base.
            </div>

            <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

            <div class="text-end">
                <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">
                    Cancelar
                </button>
                <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                    Text="Registrar producto" OnClick="btnGuardar_Click" CausesValidation="false" />
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script>
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

        // Para abrir modal NUEVO (limpia datos)
        function abrirModalRegistro() {
            limpiarFormulario();
            document.getElementById("modalProducto").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo producto';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar producto";
        }

        // Para abrir modal EDITAR (limpia datos)
        function abrirModalEditar() {
            document.getElementById("modalProducto").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar producto';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar producto";
        }

        // ⭐ NUEVAS: Para reabrir con datos después de error
        function reabrirModalRegistro() {
            document.getElementById("modalProducto").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo producto';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar producto";
            // NO LIMPIAR - mantener datos
        }

        function reabrirModalEditar() {
            document.getElementById("modalProducto").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar producto';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar producto";
            // NO LIMPIAR - mantener datos
        }

        function cerrarModal() {
            document.getElementById("modalProducto").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfProductoId.ClientID %>").value = "0";
            document.getElementById("<%= txtDescripcion.ClientID %>").value = "";
            document.getElementById("<%= txtPrecio.ClientID %>").value = "";
            document.getElementById("<%= ddlCategoria.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= ddlEstilo.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorDescripcion.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorCategoria.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorEstilo.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorPrecio.ClientID %>").innerText = "";
        }

    </script>
</asp:Content>
