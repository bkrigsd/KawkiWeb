<%@ Page Title="Contacto" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Contacto.aspx.cs" Inherits="KawkiWeb.Contacto" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        .contacto-wrap { margin: 30px; }
        .card-soft {
            background:#fff; border-radius:12px; padding:28px;
            box-shadow:0 2px 12px rgba(0,0,0,.06); border:1px solid #eee;
        }
        .card-soft h4{ margin-bottom:18px; }
        .form-label i{ margin-right:8px; color:#ED6B7F; }
        .form-control:focus{ box-shadow:0 0 0 .2rem rgba(237,107,127,.25); border-color:#ED6B7F; }
        .btn-send { background:#ED6B7F; border:none; }
        .btn-send:hover{ background:#d85769; }
        .info-item{ display:flex; gap:12px; align-items:flex-start; margin-bottom:14px; color:#555; }
        .info-item i{ color:#ED6B7F; font-size:18px; margin-top:4px; }
        .badge-soft{ background:#fff0f2; color:#d85769; border:1px solid #ffd6dc; }
        .success-msg{ display:none; }
    </style>
</asp:Content>

<asp:Content ID="Main" ContentPlaceHolderID="MainContent" runat="server">
    <div class="contacto-wrap">
        <h3>Contacto</h3>

        <div class="row g-4">
            <!-- Formulario -->
            <div class="col-lg-7">
                <div class="card-soft">
                    <h4><i class="fas fa-paper-plane me-2"></i>Envíanos un mensaje</h4>

                    <asp:ValidationSummary ID="valSummary" runat="server"
                        CssClass="alert alert-danger mb-3" HeaderText="Por favor corrige:"
                        DisplayMode="BulletList" />

                    <div class="mb-3">
                        <label class="form-label" for="txtNombre"><i class="fas fa-user"></i>Nombre completo</label>
                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Escribe tu nombre" />
                        <asp:RequiredFieldValidator ID="reqNom" runat="server" ControlToValidate="txtNombre"
                            ErrorMessage="El nombre es obligatorio." Display="Dynamic" CssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="txtEmail"><i class="fas fa-envelope"></i>Correo electrónico</label>
                        <asp:TextBox ID="txtEmail" runat="server" CssClass="form-control" TextMode="Email" placeholder="tu@email.com" />
                        <asp:RequiredFieldValidator ID="reqMail" runat="server" ControlToValidate="txtEmail"
                            ErrorMessage="El correo es obligatorio." Display="Dynamic" CssClass="text-danger small" />
                        <asp:RegularExpressionValidator ID="valMail" runat="server" ControlToValidate="txtEmail"
                            ValidationExpression="^[^\s@]+@[^\s@]+\.[^\s@]+$"
                            ErrorMessage="Formato de correo no válido." Display="Dynamic" CssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="txtAsunto"><i class="fas fa-tag"></i>Asunto (opcional)</label>
                        <asp:TextBox ID="txtAsunto" runat="server" CssClass="form-control" placeholder="¿Sobre qué trata?" />
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="txtMensaje"><i class="fas fa-comment-dots"></i>Mensaje</label>
                        <asp:TextBox ID="txtMensaje" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="6"
                                     placeholder="Cuéntanos en qué podemos ayudarte" />
                        <asp:RequiredFieldValidator ID="reqMsg" runat="server" ControlToValidate="txtMensaje"
                            ErrorMessage="El mensaje es obligatorio." Display="Dynamic" CssClass="text-danger small" />
                    </div>

                    <asp:Panel ID="pnOk" runat="server" CssClass="alert alert-success success-msg" role="alert">
                        <i class="fas fa-circle-check me-1"></i> ¡Gracias! Recibimos tu mensaje y te responderemos pronto.
                    </asp:Panel>

                    <asp:Button ID="btnEnviar" runat="server" Text="Enviar Mensaje" CssClass="btn btn-send px-4"
                                OnClick="btnEnviar_Click" />
                </div>
            </div>

            <!-- Información -->
            <div class="col-lg-5">
                <div class="card-soft">
                    <h4><i class="fas fa-store me-2"></i>Información de contacto</h4>

                    <div class="info-item"><i class="fas fa-envelope"></i>
                        <div>
                            <div class="fw-semibold">Email</div>
                            <div>hola@kawki.pe</div>
                        </div>
                    </div>

                    <div class="info-item"><i class="fas fa-phone"></i>
                        <div>
                            <div class="fw-semibold">Teléfono</div>
                            <div>+51 999 888 777</div>
                        </div>
                    </div>

                    <div class="info-item"><i class="fas fa-location-dot"></i>
                        <div>
                            <div class="fw-semibold">Dirección</div>
                            <div>Lima, Perú</div>
                        </div>
                    </div>

                    <span class="badge badge-soft px-3 py-2"><i class="fas fa-clock me-1"></i> L–V 9:00–18:00</span>
                </div>
            </div>
        </div>
    </div>
</asp:Content>
