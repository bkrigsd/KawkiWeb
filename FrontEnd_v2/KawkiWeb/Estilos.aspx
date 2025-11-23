<%@ Page Title="Estilos" Language="C#" MasterPageFile="~/KawkiWeb.master" AutoEventWireup="true" CodeBehind="Estilos.aspx.cs" Inherits="KawkiWeb.Estilos" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrouser.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="usuarios-container">
        <!-- Header -->
        <div class="usuarios-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-tags me-2"></i>Gestión de Estilos</h1>
                    <p>Administra los estilos </p>
                </div>
                <button type="button" class="btn-kawki-primary" onclick="abrirModalRegistro()">
                    <i class="fas fa-plus me-1"></i> Nuevo Estilo
                </button>
            </div>
        </div>

        <!-- Card con tabla -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="card-title">
                    <i class="fas fa-list"></i>
                    <span>Estilos Registrados</span>
                </div>
            </div>
            <div class="card-body">
                <asp:GridView ID="gvEstilos" runat="server" AutoGenerateColumns="False" 
                    CssClass="table-usuarios" DataKeyNames="EstiloId" >
                    <Columns>
                        <asp:BoundField DataField="EstiloId" HeaderText="ID" />
                        <asp:BoundField DataField="Nombre" HeaderText="Nombre" />
                        
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <button type='button' class='btn-editar'
                                    onclick='cargarDatosEdicion(<%# Eval("EstiloId") %>, "<%# Eval("Nombre") %>"); return false;'>
                                    <i class="fas fa-edit"></i> Editar
                                </button>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                    <EmptyDataTemplate>
                        <div class="text-center p-4">
                            <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                            <p class="text-muted">No hay estilos registrados</p>
                        </div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>
    </div>

    <!-- Modal de Registro/Edición -->
    <div id="modalEstilo" class="modal-kawki">
        <div class="modal-content-kawki">
            <div class="modal-header-kawki">
                <h5 id="tituloModal"><i class="fas fa-plus me-2"></i>Registrar nuevo estilo</h5>
            </div>

            <asp:HiddenField ID="hfEstiloId" runat="server" Value="0" />

            <div class="mb-3">
                <label class="form-label">Nombre del Estilo *</label>
                <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" 
                    placeholder="Ej: Clásicos" />
                <asp:Label ID="lblErrorNombre" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

            <div class="text-end">
                <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">
                    Cancelar
                </button>
                <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                    Text="Registrar estilo" OnClick="btnGuardar_Click" CausesValidation="false" />
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
            document.getElementById("modalEstilo").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo estilo';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar estilo";
        }

        // Para abrir modal EDITAR
        function abrirModalEditar() {
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorNombre.ClientID %>").innerText = "";

            document.getElementById("modalEstilo").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar estilo';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar estilo";
        }

        // Para reabrir con datos después de error
        function reabrirModalRegistro() {
            document.getElementById("modalEstilo").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-plus me-2"></i>Registrar nuevo estilo';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar estilo";
        }

        function reabrirModalEditar() {
            document.getElementById("modalEstilo").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar estilo';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar estilo";
        }

        function cerrarModal() {
            document.getElementById("modalEstilo").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfEstiloId.ClientID %>").value = "0";
            document.getElementById("<%= txtNombre.ClientID %>").value = "";
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorNombre.ClientID %>").innerText = "";
        }

        function cargarDatosEdicion(estiloId, nombre) {
            // Limpiar errores primero
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorNombre.ClientID %>").innerText = "";

            // Cargar datos en el formulario
            document.getElementById("<%= hfEstiloId.ClientID %>").value = estiloId;
            document.getElementById("<%= txtNombre.ClientID %>").value = nombre;
    
            // Configurar modal para edición
            document.getElementById("modalEstilo").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar estilo';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar estilo";
        }
    </script>
</asp:Content>