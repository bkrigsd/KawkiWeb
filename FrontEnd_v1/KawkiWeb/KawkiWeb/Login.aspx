<%@ Page Title="Iniciar Sesión / Crear Cuenta" Language="C#" MasterPageFile="~/KawkiWeb.master" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="KawkiWeb.Login" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/LoginModern/login-modern.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="PageMain" ContentPlaceHolderID="MainContent" runat="server">

    <div class="contenedor" id="contenedor">
        <!-- Panel lateral con botón que redirige a Registro.aspx -->
        <div class="panel" id="panel">
            <div class="panel-contenido">
                <i class="fas fa-user-plus icono-panel"></i>
                <h2>¿No tienes cuenta?</h2>
                <p>Crea una cuenta para comprar más rápido.</p>
                <a class="boton-panel" href="Registro.aspx">Regístrate aquí</a>
            </div>
        </div>

        <!-- Área de formularios -->
        <div class="formulario-area">
            <div class="formulario activo">
                <h2><i class="fas fa-user-check icono-form me-2"></i>Iniciar sesión</h2>

                <label for="txtUsuario"><i class="fas fa-id-card icono-label me-2"></i>Usuario / Email:</label>
                <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control mb-3" placeholder="Escribe tu usuario o email" />

                <label for="txtClave"><i class="fas fa-lock icono-label me-2"></i>Contraseña:</label>
                <div class="position-relative mb-3">
                    <asp:TextBox ID="txtClave" runat="server" TextMode="Password"
                        CssClass="form-control pe-5"
                        placeholder="Escribe tu contraseña" />
                    <i id="toggleClave" class="fas fa-eye position-absolute"
                       style="right:12px; top:50%; transform:translateY(-50%); cursor:pointer; color:#777;"
                       title="Mostrar contraseña"></i>
                </div>

                <div class="text-end mb-3">
                    <a href="RecuperarClave.aspx" class="text-decoration-none" style="color:#ED6B7F; font-weight:500;">
                        ¿Olvidaste tu contraseña?
                    </a>
                </div>

                <asp:Label ID="lblMensaje" runat="server" CssClass="text-danger mb-2 d-block" />

                <button type="button" class="btn btn-primary w-100 fw-bold"
                        onclick="document.getElementById('<%= btnLogin.ClientID %>').click();">
                    Iniciar sesión
                </button>
                <asp:Button ID="btnLogin" runat="server" Text="HiddenLogin" OnClick="btnLogin_Click" Style="display:none;" />
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const txt = document.getElementById("<%= txtClave.ClientID %>");
            const toggle = document.getElementById("toggleClave");

            toggle.addEventListener("click", () => {
                const mostrando = txt.getAttribute("type") === "text";
                txt.setAttribute("type", mostrando ? "password" : "text");
                toggle.classList.toggle("fa-eye");
                toggle.classList.toggle("fa-eye-slash");
                toggle.style.color = mostrando ? "#777" : "#ED6B7F"; // cambia color cuando activo
                toggle.setAttribute("title", mostrando ? "Mostrar contraseña" : "Ocultar contraseña");
            });
        });
    </script>

</asp:Content>