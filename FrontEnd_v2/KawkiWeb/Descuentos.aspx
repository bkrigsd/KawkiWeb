<%@ Page Title="Gestión de Descuentos" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Descuentos.aspx.cs" Inherits="KawkiWeb.Descuentos" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrodescuent.css" rel="stylesheet" />
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
                    CssClass="table-usuarios" DataKeyNames="IdDescuento"
                    OnRowCommand="gvDescuentos_RowCommand">
                    <Columns>
                        <asp:BoundField DataField="IdDescuento" HeaderText="ID" />
                        <asp:BoundField DataField="Descripcion" HeaderText="Descripción" />

                        <asp:BoundField DataField="TipoCondicion" HeaderText="Tipo Condición" />
                        <asp:BoundField DataField="ValorCondicion" HeaderText="Valor Condición" />
                        <asp:BoundField DataField="TipoBeneficio" HeaderText="Tipo Beneficio" />
                        <asp:BoundField DataField="ValorBeneficio" HeaderText="Valor Beneficio" />

                        <%--<asp:BoundField DataField="Porcentaje" HeaderText="Porcentaje (%)" />--%>
                        <asp:BoundField DataField="FechaInicio" HeaderText="Inicio" DataFormatString="{0:dd/MM/yyyy}" />
                        <asp:BoundField DataField="FechaFin" HeaderText="Fin" DataFormatString="{0:dd/MM/yyyy}" />

                        <%-- Nuevo: Columna para Activo --%>
                        <asp:TemplateField HeaderText="Activo">
                            <%--<ItemStyle HorizontalAlign="Center" />--%>
                            <ItemTemplate>
                                <%# Convert.ToBoolean(Eval("activo")) 
                                    ? "<i class='fas fa-check-circle' style='color: #2ecc71; font-size:20px;'></i>" 
                                    : "<i class='fas fa-times-circle' style='color: #e74c3c; font-size:20px;'></i>" %>
                            </ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <button type="button" class="btn-editar"
                                    onclick='<%# "editarDescuento(" 
                                        + Eval("IdDescuento") + ", \"" 
                                        + Eval("Descripcion") + "\", \"" 
                                        + Eval("TipoCondicionId") + "\", \""
                                        + Eval("ValorCondicion") + "\", \"" 
                                        + Eval("TipoBeneficioId") + "\", \"" 
                                        + Eval("ValorBeneficio") + "\", \"" 
                                        + String.Format("{0:yyyy-MM-dd}", Eval("FechaInicio")) + "\", \"" 
                                        + String.Format("{0:yyyy-MM-dd}", Eval("FechaFin")) + "\", " 
                                        + Eval("Activo").ToString().ToLower()
                                        + ")" %>'>
                                    Editar
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

                <!-- Descripción -->
                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <asp:TextBox ID="txtDescripcion" runat="server" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="rfvDescripcion" runat="server"
                        ControlToValidate="txtDescripcion" ErrorMessage="Campo requerido"
                        CssClass="text-danger" Display="Dynamic" />
                </div>

                <!-- Fila 1: Tipo condición + Tipo beneficio -->
                <div class="row">

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tipo de condición *</label>
                        <asp:DropDownList ID="ddlTipoCondicion" runat="server" CssClass="form-select" />
                        <asp:RequiredFieldValidator ID="rfvTipoCondicion" runat="server"
                            ControlToValidate="ddlTipoCondicion"
                            InitialValue=""
                            ErrorMessage="Seleccione un tipo de condición"
                            CssClass="text-danger"
                            Display="Dynamic" />
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tipo de beneficio *</label>
                        <asp:DropDownList ID="ddlTipoBeneficio" runat="server" CssClass="form-select" />
                        <asp:RequiredFieldValidator ID="rfvTipoBeneficio" runat="server"
                            ControlToValidate="ddlTipoBeneficio"
                            InitialValue=""
                            ErrorMessage="Seleccione un tipo de beneficio"
                            CssClass="text-danger"
                            Display="Dynamic" />
                    </div>

                </div>

                <!-- Fila 2: Valor condición + Valor beneficio -->
                <div class="row">

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Valor condición *</label>
                        <asp:TextBox ID="txtValorCondicion" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="rfvValorCond" runat="server"
                            ControlToValidate="txtValorCondicion" ErrorMessage="Campo requerido"
                            CssClass="text-danger" Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revValorCond" runat="server"
                            ControlToValidate="txtValorCondicion"
                            ValidationExpression="^\d+$"
                            ErrorMessage="Ingrese un número entero"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Valor Beneficio *</label>
                        <asp:TextBox ID="txtPorcentaje" runat="server" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server"
                            ControlToValidate="txtPorcentaje" ErrorMessage="Campo requerido"
                            CssClass="text-danger" Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revValorBen" runat="server"
                            ControlToValidate="txtPorcentaje"
                            ValidationExpression="^(100(\.0{1,2})?|[0-9]{1,2}(\.[0-9]{1,2})?)$"
                            ErrorMessage="Ingrese un número entre 0 y 100 (máx. 2 decimales)"
                            CssClass="text-danger" Display="Dynamic" />
                    </div>

                </div>


                <!-- Fechas -->
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

                <!-- Activo -->
                <div class="mb-3">
                    <asp:CheckBox ID="chkActivo" runat="server" Text="Descuento activo" Checked="true" />
                </div>

                <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cancelarDescuento()">Cancelar</button>
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                        Text="Registrar descuento" OnClick="btnGuardar_Click" />
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
        function abrirModalRegistroSinLimpiar() {
            document.getElementById("modalDescuento").classList.add("show");
            document.getElementById("tituloModal").innerHTML =
                '<i class="fas fa-tag me-2"></i>Registrar nuevo descuento';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar descuento";
        }

        function abrirModalRegistro() {
            document.getElementById("modalDescuento").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-tag me-2"></i>Registrar nuevo descuento';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Registrar descuento";
            limpiarFormulario();
            resetearValidadoresASP();
        }

        function editarDescuento(id, descripcion, tipoCond, valorCond, tipoBen, valorBen, fechaInicio, fechaFin, activo) {

            document.getElementById("<%= hfIdDescuento.ClientID %>").value = id;
            document.getElementById("<%= txtDescripcion.ClientID %>").value = descripcion;
            document.getElementById("<%= ddlTipoCondicion.ClientID %>").value = tipoCond;
            document.getElementById("<%= txtValorCondicion.ClientID %>").value = valorCond;
            document.getElementById("<%= ddlTipoBeneficio.ClientID %>").value = tipoBen;
            document.getElementById("<%= txtPorcentaje.ClientID %>").value = valorBen;
            document.getElementById("<%= txtFechaInicio.ClientID %>").value = fechaInicio;
            document.getElementById("<%= txtFechaFin.ClientID %>").value = fechaFin;
            document.getElementById("<%= chkActivo.ClientID %>").checked = (activo === true);
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            actualizarValorBeneficio();
            abrirModalEditar();
        }

        function abrirModalEditar() {
            document.getElementById("modalDescuento").classList.add("show");
            document.getElementById("tituloModal").innerHTML = '<i class="fas fa-edit me-2"></i>Editar descuento';
            document.getElementById("<%= btnGuardar.ClientID %>").value = "Actualizar descuento";
            resetearValidadoresASP();
        }

        function cancelarDescuento() {
            // Cierra el modal
            //cerrarModal();
            document.getElementById("modalDescuento").classList.remove("show");
            limpiarFormulario();
            resetModalDescuento() 
            // Hace un REFRESH limpio sin POST ni datos previos
            /*window.location.href = "Descuentos.aspx";*/
        }

        // Ejecutar cuando cambie el tipo de beneficio
        // --- GLOBAL: accesible desde abrir/editar ---
        function actualizarValorBeneficio() {
            const ddlBenef = document.getElementById("<%= ddlTipoBeneficio.ClientID %>");
            const txtValorBen = document.getElementById("<%= txtPorcentaje.ClientID %>");

            const selectedText = ddlBenef.options[ddlBenef.selectedIndex].text.toLowerCase();

            if (selectedText.includes("envío gratis") || selectedText.includes("envio gratis")) {
                txtValorBen.value = "0";
                txtValorBen.setAttribute("readonly", true);
            } else {
                txtValorBen.removeAttribute("readonly");
            }
        }

        // Ejecutar cuando cambia el dropdown
        document.addEventListener("DOMContentLoaded", function () {
            const ddlBenef = document.getElementById("<%= ddlTipoBeneficio.ClientID %>");
            ddlBenef.addEventListener("change", actualizarValorBeneficio);
        });

        function cerrarModal() {
            document.getElementById("modalDescuento").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= hfIdDescuento.ClientID %>").value = "0";
            
            document.getElementById("<%= txtDescripcion.ClientID %>").value = "";
            document.getElementById("<%= txtValorCondicion.ClientID %>").value = "";
            document.getElementById("<%= txtPorcentaje.ClientID %>").value = "";
            document.getElementById("<%= txtFechaInicio.ClientID %>").value = "";
            document.getElementById("<%= txtFechaFin.ClientID %>").value = "";
            document.getElementById("<%= ddlTipoCondicion.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= ddlTipoBeneficio.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= chkActivo.ClientID %>").checked = true;
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
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

        function resetModalDescuento() {

            // 1. Cerrar modal
            document.getElementById("modalDescuento").classList.remove("show");

            // 2. Limpiar formulario
            limpiarFormulario();

            // 3. Limpiar validadores sin romperlos
            document.querySelectorAll(".text-danger").forEach(x => {
                x.classList.remove("val-hidden"); // por si lo usabas antes
                x.style.display = "none";
            });

            // 4. Quitar estilos de error en inputs ASP.NET
            document.querySelectorAll(".input-validation-error").forEach(x => {
                x.classList.remove("input-validation-error");
            });

            // 5. Restablecer menú de beneficio y su input
            const ddlBenef = document.getElementById("<%= ddlTipoBeneficio.ClientID %>");
            const txtValorBen = document.getElementById("<%= txtPorcentaje.ClientID %>");
            txtValorBen.removeAttribute("readonly");
            txtValorBen.value = "";
            ddlBenef.selectedIndex = 0;

            // 6. Marcar que no hay mensajes pendientes
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
        }

        function cerrarModalConfirmacion() {
            document.getElementById("modalConfirmacion").classList.remove("show");
        }
    </script>
</asp:Content>
