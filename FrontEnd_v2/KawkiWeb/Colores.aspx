<%@ Page Title="Colores" Language="C#" MasterPageFile="~/KawkiWeb.master" AutoEventWireup="true" CodeBehind="Colores.aspx.cs" Inherits="KawkiWeb.Colores" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrouser.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="usuarios-container">
        <!-- Header -->
        <div class="usuarios-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-tags me-2"></i>Gestión de Colores</h1>
                    <p>Administra los colores </p>
                </div>
                <button type="button" class="btn-kawki-primary" onclick="abrirModalRegistro()">
                    <i class="fas fa-plus me-1"></i> Nuevo Color
                </button>
            </div>
        </div>

        <!-- Card con tabla -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="card-title">
                    <i class="fas fa-list"></i>
                    <span>Colores Registrados</span>
                </div>
            </div>
            <div class="card-body">
                <asp:GridView ID="gvColores" runat="server" AutoGenerateColumns="False" 
                    CssClass="table-usuarios" DataKeyNames="ColorId" 
                    OnRowCommand="gvColores_RowCommand">
                    <Columns>
                        <asp:BoundField DataField="ColorId" HeaderText="ID" />
                        <asp:BoundField DataField="Nombre" HeaderText="Color" />
                        
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <asp:LinkButton runat="server" 
                                    CommandName="Editar" 
                                    CommandArgument='<%# Eval("ColorId") %>'
                                    CssClass="btn-editar"
                                    CausesValidation="false">
                                    <i class="fas fa-edit"></i> Editar
                                </asp:LinkButton>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                    <EmptyDataTemplate>
                        <div class="text-center p-4">
                            <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                            <p class="text-muted">No hay colores registrados</p>
                        </div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>
    </div>

    <!-- Modal de Registro/Edición -->
    <div id="modalColor" class="modal-kawki">
        <div class="modal-content-kawki">
            <div class="modal-header-kawki">
                <h5 id="tituloModal"><i class="fas fa-plus me-2"></i>Registrar nuevo color</h5>
            </div>

            <asp:HiddenField ID="hfColorId" runat="server" Value="0" />

            <div class="mb-3">
                <label class="form-label">Nombre del color *</label>
                <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" 
                    placeholder="Ej: Blanco" />
                <asp:Label ID="lblErrorNombre" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

            <div class="text-end">
                <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">
                    Cancelar
                </button>
                <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                    Text="Registrar color" OnClick="btnGuardar_Click" CausesValidation="false" />
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
            document.getElementById("modalColor").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo color';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar color";
        }

        // Para abrir modal EDITAR
        function abrirModalEditar() {
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorNombre.ClientID %>").innerText = "";

            document.getElementById("modalColor").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar color';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar color";
        }

        // Para reabrir con datos después de error
        function reabrirModalRegistro() {
            document.getElementById("modalColor").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo color';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar color";
        }

        function reabrirModalEditar() {
            document.getElementById("modalColor").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar color';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar color";
        }

        function cerrarModal() {
            document.getElementById("modalColor").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfColorId.ClientID %>").value = "0";
            document.getElementById("<%= txtNombre.ClientID %>").value = "";
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorNombre.ClientID %>").innerText = "";
        }
    </script>
</asp:Content>