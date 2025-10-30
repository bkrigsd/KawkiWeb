<%@ Page Title="Registro" Language="C#" MasterPageFile="~/KawkiWeb.master" AutoEventWireup="true" CodeBehind="Registro.aspx.cs" Inherits="KawkiWeb.Registro" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/LoginModern/login-modern.css" rel="stylesheet" />

</asp:Content>

<asp:Content ID="Main" ContentPlaceHolderID="MainContent" runat="server">
    <div class="contenedor" id="contenedor">
        <!-- Panel lateral con atajo a Login -->
        <div class="panel" id="panelIzquierdo" runat="server">
            <div class="panel-contenido">
                <i class="fas fa-user-check icono-panel"></i>
                <h2>¿Ya tienes cuenta?</h2>
                <p>Puedes iniciar sesión con tus credenciales.</p>
                <a class="boton-panel" href="Login.aspx">Inicia sesión</a>
            </div>
        </div>

        <!-- Formulario de registro -->
        <div class="formulario-area">
            <div class="formulario activo" id="formularioRegistro" runat="server">
                <h2><i class="fas fa-user-plus icono-form me-2"></i>Registro</h2>

                <label for="txtDni"><i class="fas fa-id-card icono-label me-2"></i>DNI:</label>
                <asp:TextBox ID="txtDni" runat="server" CssClass="form-control mb-3" placeholder="Ingrese su DNI" />

                <label for="txtNombre"><i class="fas fa-user icono-label me-2"></i>Nombre completo:</label>
                <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control mb-3" placeholder="Ingrese su nombre" />

                <label for="txtCorreo"><i class="fas fa-envelope icono-label me-2"></i>Correo electrónico:</label>
                <asp:TextBox ID="txtCorreo" runat="server" CssClass="form-control mb-3" placeholder="Ingrese su correo" TextMode="Email" />

                <label for="txtDireccion"><i class="fas fa-map-marker-alt icono-label me-2"></i>Dirección:</label>
                <asp:TextBox ID="txtDireccion" runat="server" CssClass="form-control mb-3" placeholder="Ingrese su dirección" />

                <label for="txtTelefono"><i class="fas fa-phone icono-label me-2"></i>Teléfono:</label>
                <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control mb-3" placeholder="Ingrese su teléfono" />

                <label for="txtPassword"><i class="fas fa-lock icono-label me-2"></i>Contraseña:</label>
                <asp:TextBox ID="txtPassword" runat="server" TextMode="Password" CssClass="form-control mb-4" placeholder="Ingrese su contraseña" />

                <button type="button" class="btn btn-success w-100 fw-bold"
                        onclick="document.getElementById('<%= btnRegistro.ClientID %>').click();">
                    Registrarse
                </button>

                <asp:Label ID="lblError" runat="server" CssClass="text-danger mb-2 d-block fw-semibold"></asp:Label>


                <asp:Button ID="btnRegistro" runat="server" Text="HiddenRegister" OnClick="btnRegistro_Click" Style="display:none;" />
            </div>
        </div>

        <!-- Tarjeta de éxito centrada -->
        <asp:Panel ID="pnlExito" runat="server" Visible="false">
            <div class="exito-wrap">
                <div class="exito-card">
                    <div class="exito-icon">✔</div>
                    <h2>¡Registro exitoso!</h2>
                    <p>Tu cuenta fue creada correctamente.</p>

                    <div class="exito-botones">
                        <a href="Inicio.aspx" class="btn-exito-principal">Ir al inicio</a>
                        <a href="Logout.aspx?next=Login.aspx" class="btn-exito-secundario">Iniciar sesión</a>
                    </div>
                </div>
            </div>

            <style>
                .exito-wrap {
                    position: fixed;
                    top: 0; left: 0;
                    width: 100%; height: 100%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    background: #f8f9fa;
                    z-index: 9999;
                }

                .exito-card {
                    background: #ED6B7F;
                    color: #fff;
                    border-radius: 16px;
                    padding: 40px 60px;
                    text-align: center;
                    box-shadow: 0 8px 25px rgba(0,0,0,0.2);
                    max-width: 420px;
                    width: 90%;
                    animation: fadeIn 0.5s ease-in-out;
                }

                .exito-icon {
                    font-size: 72px;
                    margin-bottom: 12px;
                    line-height: 1;
                }

                .exito-card h2 {
                    font-weight: 700;
                    margin-bottom: 10px;
                }

                .exito-card p {
                    color: #fff;
                    opacity: .95;
                    margin-bottom: 28px;
                    font-size: 1.05rem;
                }

                .exito-botones a {
                    display: block;
                    width: 100%;
                    border-radius: 10px;
                    font-weight: 600;
                    text-decoration: none;
                    padding: 10px;
                    transition: .2s;
                }

                .btn-exito-principal {
                    background: #fff;
                    color: #ED6B7F;
                    margin-bottom: 10px;
                }

                .btn-exito-principal:hover {
                    background: #f9f9f9;
                }

                .btn-exito-secundario {
                    border: 2px solid #fff;
                    color: #fff;
                }

                .btn-exito-secundario:hover {
                    background: rgba(255,255,255,0.15);
                }

                @keyframes fadeIn {
                    from { opacity: 0; transform: scale(0.95); }
                    to { opacity: 1; transform: scale(1); }
                }
            </style>
        </asp:Panel>

    </div>
</asp:Content>
