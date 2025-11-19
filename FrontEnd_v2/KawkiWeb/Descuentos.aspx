<%@ Page Title="Gestión de Descuentos" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Descuentos.aspx.cs" Inherits="KawkiWeb.Descuentos" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrouser.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="usuarios-container">
        <!-- Header -->
        <div class="usuarios-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-tags me-2"></i>Gestión de Descuentos</h1>
                    <p>Administra, crea e inhabilita los descuentos del sistema Kawki.</p>
                </div>
                <button type="button" class="btn-kawki-primary" onclick="abrirModalRegistro()">
                    <i class="fas fa-plus me-1"></i> Nuevo Descuento
                </button>
            </div>
        </div>

        <!-- Tabla de descuentos -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="card-title">
                    <i class="fas fa-list"></i> Lista de Descuentos
                </div>
            </div>
            <div class="card-body">
                <asp:GridView ID="gvDescuentos" runat="server" AutoGenerateColumns="False"
                    CssClass="table-usuarios" DataKeyNames="IdDescuento">
                    <Columns>
                        <asp:BoundField DataField="IdDescuento" HeaderText="ID" />
                        <asp:BoundField DataField="Nombre" HeaderText="Nombre" />
                        <asp:BoundField DataField="Porcentaje" HeaderText="Porcentaje (%)" />
                        <asp:BoundField DataField="FechaInicio" HeaderText="Inicio" DataFormatString="{0:dd/MM/yyyy}" />
                        <asp:BoundField DataField="FechaFin" HeaderText="Fin" DataFormatString="{0:dd/MM/yyyy}" />
                        <asp:TemplateField HeaderText="Estado">
                            <ItemTemplate>
                                <span class='<%# Convert.ToBoolean(Eval("Activo")) ? "badge-rol badge-activo" : "badge-rol badge-inactivo" %>'>
                                    <%# Convert.ToBoolean(Eval("Activo")) ? "Activo" : "Inactivo" %>
                                </span>
                            </ItemTemplate>
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <asp:LinkButton ID="lnkCambiarEstado" runat="server" 
                                    CommandArgument='<%# Eval("IdDescuento") %>'
                                    OnCommand="lnkCambiarEstado_Command"
                                    CausesValidation="false"
                                    style="display: inline-block; position: relative; width: 50px; height: 24px; margin-right: 10px; vertical-align: middle; text-decoration: none;">
                                    <span style="display: block; position: absolute; cursor: pointer; top: 0; left: 0; right: 0; bottom: 0; background-color: <%# Convert.ToBoolean(Eval("Activo")) ? "#28a745" : "#ccc" %>; transition: .4s; border-radius: 34px;">
                                        <span style="display: block; position: absolute; height: 18px; width: 18px; left: 3px; bottom: 3px; background-color: white; transition: .4s; border-radius: 50%; transform: <%# Convert.ToBoolean(Eval("Activo")) ? "translateX(26px)" : "translateX(0)" %>;"></span>
                                    </span>
                                </asp:LinkButton>
                                <button type="button" class="btn-editar"
                                    onclick='editarDescuento(<%# Eval("IdDescuento") %>, "<%# Eval("Nombre") %>", "<%# Eval("Porcentaje") %>", "<%# Eval("FechaInicio","{0:yyyy-MM-dd}") %>", "<%# Eval("FechaFin","{0:yyyy-MM-dd}") %>", "<%# Eval("Descripcion") %>")'>
                                    Editar
                                </button>
                                <button type="button" class="btn-eliminar" onclick="abrirModalConfirmacion(<%# Eval("IdDescuento") %>)">
                                    Eliminar
                                </button>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                </asp:GridView>
            </div>
        </div>

        <!-- Modal Registro / Edición -->
        <div id="modalDescuento" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h5 id="tituloModal"><i class="fas fa-tag me-2"></i>Registrar nuevo descuento</h5>
                </div>

                <asp:HiddenField ID="hfIdDescuento" runat="server" Value="0" />

                <div class="mb-3">
                    <label class="form-label">Nombre *</label>
                    <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="rfvNombre" runat="server"
                        ControlToValidate="txtNombre" ErrorMessage="Campo requerido"
                        CssClass="text-danger" Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Porcentaje (%) *</label>
                    <asp:TextBox ID="txtPorcentaje" runat="server" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="rfvPorcentaje" runat="server"
                        ControlToValidate="txtPorcentaje" ErrorMessage="Campo requerido"
                        CssClass="text-danger" Display="Dynamic" />
                    <asp:RegularExpressionValidator ID="revPorcentaje" runat="server"
                        ControlToValidate="txtPorcentaje"
                        ValidationExpression="^(100(\.0{1,2})?|[0-9]{1,2}(\.[0-9]{1,2})?)$"
                        ErrorMessage="Ingrese un número entre 0 y 100 (máx. 2 decimales)"
                        CssClass="text-danger" Display="Dynamic" />
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Fecha inicio *</label>
                        <asp:TextBox ID="txtFechaInicio" runat="server" TextMode="Date" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvInicio" runat="server"
                            ControlToValidate="txtFechaInicio" ErrorMessage="Campo requerido"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Fecha fin *</label>
                        <asp:TextBox ID="txtFechaFin" runat="server" TextMode="Date" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvFin" runat="server"
                            ControlToValidate="txtFechaFin" ErrorMessage="Campo requerido"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <asp:TextBox ID="txtDescripcion" runat="server" TextMode="MultiLine" Rows="3" CssClass="form-control" />
                </div>

                <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">Cancelar</button>
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                        Text="Registrar descuento" OnClick="btnGuardar_Click" />
                </div>
            </div>
        </div>

        <!-- Modal Confirmación -->
        <div id="modalConfirmacion" class="modal-confirmacion">
            <div class="modal-content-kawki">
                <div class="modal-icon"><i class="fas fa-exclamation-triangle"></i></div>
                <h5>¿Confirmar eliminación?</h5>
                <p>Esta acción no se puede deshacer</p>
                <asp:HiddenField ID="hfIdEliminar" runat="server" Value="0" />
                <div>
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalConfirmacion()">Cancelar</button>
                    <asp:Button ID="btnConfirmarEliminar" runat="server" CssClass="btn-kawki-primary"
                        style="background-color:#dc3545;" Text="Eliminar"
                        OnClick="btnConfirmarEliminar_Click" CausesValidation="false" />
                </div>
            </div>
        </div>
    </div>

    <style>
        .switch input:checked + .slider {
            background-color: #28a745;
        }

        .switch input:checked + .slider span {
            transform: translateX(26px);
        }
    </style>

    <script>
        function abrirModalRegistro() {
            document.getElementById("modalDescuento").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-tag me-2"></i>Registrar nuevo descuento';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar descuento";
            limpiarFormulario();
        }

        function abrirModalEditar() {
            document.getElementById("modalDescuento").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar descuento';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar descuento";
        }

        function editarDescuento(id, nombre, porcentaje, inicio, fin, descripcion) {
            document.getElementById("<%= hfIdDescuento.ClientID %>").value = id;
            document.getElementById("<%= txtNombre.ClientID %>").value = nombre;
            document.getElementById("<%= txtPorcentaje.ClientID %>").value = porcentaje;
            document.getElementById("<%= txtFechaInicio.ClientID %>").value = inicio;
            document.getElementById("<%= txtFechaFin.ClientID %>").value = fin;
            document.getElementById("<%= txtDescripcion.ClientID %>").value = descripcion;
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            abrirModalEditar();
        }

        function cerrarModal() {
            document.getElementById("modalDescuento").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfIdDescuento.ClientID %>").value = "0";
            document.getElementById("<%= txtNombre.ClientID %>").value = "";
            document.getElementById("<%= txtPorcentaje.ClientID %>").value = "";
            document.getElementById("<%= txtFechaInicio.ClientID %>").value = "";
            document.getElementById("<%= txtFechaFin.ClientID %>").value = "";
            document.getElementById("<%= txtDescripcion.ClientID %>").value = "";
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
        }

        function abrirModalConfirmacion(id) {
            document.getElementById("<%= hfIdEliminar.ClientID %>").value = id;
            document.getElementById("modalConfirmacion").classList.add("show");
        }

        function cerrarModalConfirmacion() {
            document.getElementById("modalConfirmacion").classList.remove("show");
        }
    </script>
</asp:Content>
