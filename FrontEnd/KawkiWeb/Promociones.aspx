<%@ Page Title="Promociones" Language="C#" MasterPageFile="~/KawkiWeb.master" AutoEventWireup="true" CodeBehind="Promociones.aspx.cs" Inherits="KawkiWeb.Promociones" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        .page-wrap { padding: 28px 34px; }
        .hstack { display:flex; align-items:center; gap:12px; }
        .spacer { flex:1; }
        .btn-primary-soft {
            background:#ef5668; color:#fff; border:none; border-radius:10px; padding:10px 16px;
        }
        .btn-icon { border:none; background:#fff; padding:8px; border-radius:10px; box-shadow:0 1px 6px rgba(0,0,0,.08);}
        .btn-icon:hover{ filter:brightness(.96); }
        .badge { font-size:12px; padding:3px 8px; border-radius:999px; border:1px solid #e5e7eb; color:#6b7280; }
        .badge.on  { background:#fde8ec; color:#ef5668; border-color:#f9cdd5; }
        .badge.off { background:#f3f4f6; color:#6b7280; }
        .card-soft {
            background:#fff; border-radius:14px; padding:22px; border:1px solid #ececec;
            box-shadow:0 2px 12px rgba(0,0,0,.05);
        }
        .promo-title { font-weight:600; font-size:18px; margin-right:10px; }
        .promo-percent { color:#ef5668; font-weight:600; }
        .muted { color:#6b7280; font-size:14px; }
        .meta { color:#6b7280; font-size:13px; }
        .stack-sm { display:grid; gap:6px; }
        .grid-2 { display:grid; grid-template-columns: 1fr 1fr; gap:18px; }
        .list { display:grid; gap:16px; }
        /* Switch visual (no JS extra) */
        .switch { position:relative; display:inline-block; width:42px; height:24px; }
        /* antes: .switch input { display:none; } */
        .switch input {
            position:absolute;
            opacity:0;          /* visualmente oculto, pero clickeable a través del label */
            width:0; height:0;
        }
        .slider {
            position:absolute; cursor:pointer; top:0; left:0; right:0; bottom:0;
            background:#e5e7eb; transition:.2s; border-radius:999px;
        }
        .slider:before {
            position:absolute; content:""; height:18px; width:18px; left:3px; top:3px;
            background:white; transition:.2s; border-radius:50%; box-shadow:0 1px 4px rgba(0,0,0,.2);
        }
        input:checked + .slider { background:#ef5668; }
        input:checked + .slider:before { transform:translateX(18px); }
        /* Modal simple (sin ScriptManager) */
        .modal-mask {
            position:fixed; inset:0; background:rgba(17,24,39,.45); display:flex;
            align-items:flex-start; justify-content:center; padding-top:60px; z-index:60;
        }
        .modal-card {
            background:#fff; border-radius:14px; width:min(740px, 92vw);
            padding:22px; border:1px solid #ececec; box-shadow:0 10px 30px rgba(0,0,0,.18);
        }
        .form-grid { display:grid; grid-template-columns:1fr 1fr; gap:14px; }
        .form-grid .full { grid-column:1 / -1; }
        .form-label { font-size:13px; color:#374151; margin-bottom:6px; display:block; }
        .form-input, .form-select, .form-text {
            width:100%; border:1px solid #e5e7eb; border-radius:10px; padding:10px 12px; font-size:14px;
        }
        .actions { display:flex; gap:10px; }
        .btn-secondary { background:#f3f4f6; border:1px solid #e5e7eb; border-radius:10px; padding:10px 14px; }
        .danger { color:#b91c1c; }
    </style>
</asp:Content>

<asp:Content ID="Body" ContentPlaceHolderID="MainContent" runat="server">
    <div class="page-wrap">
        <div class="hstack" style="margin-bottom:16px;">
            <h3 style="margin:0;">Gestión de Promociones</h3>
            <span class="spacer"></span>
            <asp:Button ID="btnNueva" runat="server" CssClass="btn-primary-soft" Text="➕  Nueva Promoción"
                OnClick="btnNueva_Click" />
        </div>

        <!-- Lista de promociones -->
        <asp:Repeater ID="rptPromos" runat="server" OnItemCommand="rptPromos_ItemCommand">
            <ItemTemplate>
                <div class="card-soft">
                    <div class="hstack" style="margin-bottom:6px;">
                        <span class="promo-title"><%# Eval("Nombre") %></span>
                        <span class='badge <%# (bool)Eval("Activa") ? "on" : "off" %>'>
                            <%# (bool)Eval("Activa") ? "Activa" : "Inactiva" %>
                        </span>
                        <span class="promo-percent"><%# Eval("EtiquetaDescuento") %></span>
                        <span class="spacer"></span>

                        <!-- switch activar/inactivar -->
                        <asp:HiddenField ID="hfId" runat="server" Value='<%# Eval("Id") %>' />

                            <label class="switch" style="margin:0;">
                                <asp:CheckBox ID="chkActivaItem" runat="server"
                                    AutoPostBack="true"
                                    OnCheckedChanged="chkActivaItem_CheckedChanged"
                                    Checked='<%# (bool)Eval("Activa") %>' />
                                <span class="slider"></span>
                            </label>

                        <span class='badge <%# (bool)Eval("Activa") ? "on" : "off" %>'>
                            <%# (bool)Eval("Activa") ? "Activa" : "Inactiva" %>
                        </span>

                        <!-- editar -->
                        <asp:LinkButton runat="server" CssClass="btn-icon" CommandName="Edit"
                            CommandArgument='<%# Eval("Id") %>' ToolTip="Editar">✏️</asp:LinkButton>

                        <!-- eliminar -->
                        <asp:LinkButton runat="server" CssClass="btn-icon" CommandName="Delete"
                            OnClientClick="return confirm('¿Eliminar la promoción?');"
                            CommandArgument='<%# Eval("Id") %>' ToolTip="Eliminar">
                            🗑️
                        </asp:LinkButton>
                    </div>

                    <div class="stack-sm muted" style="margin-bottom:10px;">
                        <span><%# Eval("DescripcionResumen") %></span>
                    </div>

                    <div class="grid-2">
                        <div class="stack-sm">
                            <span class="meta"><b>Descuento:</b> <%# Eval("DescuentoTexto") %></span>
                            <span class="meta"><b>Aplicable a:</b> <%# Eval("AplicableA") %></span>
                        </div>
                        <div class="stack-sm" style="justify-self:end; text-align:right;">
                            <span class="meta"><b>Vigencia:</b> <%# Eval("VigenciaTexto") %></span>
                            <span class="meta"><b>Usos:</b> <%# Eval("UsosTexto") %></span>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>

        <!-- Modal Crear/Editar -->
        <asp:Panel ID="pnlModal" runat="server" Visible="false" CssClass="modal-mask">
            <div class="modal-card">
                <div class="hstack" style="margin-bottom:10px;">
                    <h4 style="margin:0;"><asp:Literal ID="litTituloModal" runat="server" Text="Nueva Promoción" /></h4>
                    <span class="spacer"></span>
                    <asp:Button ID="btnCerrarX" runat="server" CssClass="btn-icon" Text="✖"
                        OnClick="btnCancelar_Click" />
                </div>

                <div class="form-grid">
                    <div class="full">
                        <label class="form-label">Nombre de la promoción</label>
                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-input" placeholder="Ej: Descuento de Verano" />
                    </div>

                    <div class="full">
                        <label class="form-label">Descripción</label>
                        <asp:TextBox ID="txtDescripcion" runat="server" TextMode="MultiLine" Rows="3"
                            CssClass="form-text" placeholder="Descripción de la promoción..." />
                    </div>

                    <div>
                        <label class="form-label">Tipo de descuento</label>
                        <asp:DropDownList ID="ddlTipo" runat="server" CssClass="form-select">
                            <asp:ListItem Text="Porcentaje (%)" Value="porcentaje" />
                            <asp:ListItem Text="Monto fijo (S/)" Value="monto" />
                            <asp:ListItem Text="Cupón" Value="cupon" />
                        </asp:DropDownList>
                    </div>
                    <div>
                        <label class="form-label">Porcentaje (%)</label>
                        <asp:TextBox ID="txtPorcentaje" runat="server" CssClass="form-input" Text="15" />
                    </div>

                    <div>
                        <label class="form-label">Compra mínima (S/) - Opcional</label>
                        <asp:TextBox ID="txtMinCompra" runat="server" CssClass="form-input" Text="100" />
                    </div>
                    <div>
                        <label class="form-label">Descuento máximo (S/) - Opcional</label>
                        <asp:TextBox ID="txtMaxDesc" runat="server" CssClass="form-input" Text="200" />
                    </div>

                    <div>
                        <label class="form-label">Fecha de inicio</label>
                        <asp:TextBox ID="txtInicio" runat="server" CssClass="form-input" placeholder="dd/mm/aaaa" />
                    </div>
                    <div>
                        <label class="form-label">Fecha de fin</label>
                        <asp:TextBox ID="txtFin" runat="server" CssClass="form-input" placeholder="dd/mm/aaaa" />
                    </div>

                    <div class="full">
                        <label class="form-label">Aplicable a</label>
                        <asp:DropDownList ID="ddlAplicable" runat="server" CssClass="form-select">
                            <asp:ListItem Text="Todos los productos" Value="Todos los productos" />
                            <asp:ListItem Text="Oxford" Value="Oxford" />
                            <asp:ListItem Text="Zapatillas" Value="Zapatillas" />
                        </asp:DropDownList>
                    </div>

                    <div>
                        <label class="form-label">Límite de usos - Opcional</label>
                        <asp:TextBox ID="txtLimite" runat="server" CssClass="form-input" Text="100" />
                    </div>
                    <div>
                        <label class="form-label">Cupón (si aplica)</label>
                        <asp:TextBox ID="txtCupon" runat="server" CssClass="form-input" placeholder="NUEVO2024" />
                    </div>

                    <div class="full hstack" style="margin-top:6px;">
                        <asp:CheckBox ID="chkActiva" runat="server" />
                        <label for="<%= chkActiva.ClientID %>" class="muted">Promoción activa</label>
                    </div>
                </div>

                <div class="hstack" style="margin-top:18px;">
                    <span class="spacer"></span>
                    <asp:Button ID="btnCancelar" runat="server" CssClass="btn-secondary" Text="Cancelar" OnClick="btnCancelar_Click" />
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-primary-soft" Text="Crear Promoción"
                        OnClick="btnGuardar_Click" />
                </div>

                <asp:HiddenField ID="hdnEditId" runat="server" />
            </div>
        </asp:Panel>
    </div>
</asp:Content>
