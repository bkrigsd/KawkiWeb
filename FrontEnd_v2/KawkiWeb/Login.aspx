<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="KawkiWeb.Login" %>

<!DOCTYPE html>
<html lang="es">
<head runat="server">

    <meta charset="utf-8" />
    <title>Iniciar sesión - Kawki Panel</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap -->
    <link href="Content/bootstrap.min.css" rel="stylesheet" />
    <link href="Content/bootstrap-icons-1.13.1/bootstrap-icons.css" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="Content/Fonts/css/all.min.css" rel="stylesheet" />
    <!-- JS: jQuery + Bootstrap -->
    <script src="Scripts/jquery-3.7.1.min.js"></script>
    <script src="Scripts/bootstrap.bundle.min.js"></script>
    <!-- ESTILOS DEL LOGIN -->
    <link href="Content/LoginModern/estiloLogin.css" rel="stylesheet" />
    <!-- JS DEL LOGIN -->
    <script src="Content/LoginModern/animation.js"></script>

</head>
    <script>
    document.addEventListener("DOMContentLoaded", function () {
        const txt = document.getElementById("<%= txtClave.ClientID %>");
        const toggle = document.getElementById("toggleClave");

        if (!txt || !toggle) return;

        toggle.addEventListener("click", function () {
            const mostrando = txt.type === "text";
            txt.type = mostrando ? "password" : "text";
            toggle.classList.toggle("fa-eye");
            toggle.classList.toggle("fa-eye-slash");
        });
    });
    </script>
<body>
<form id="form1" runat="server">
    <div class="login-wrapper">

        <!-- Izquierda -->
        <div class="login-info">
            <div class="login-info-icon">
                <img src="Images/kawki.png" alt="Kawki" />
            </div>
            <p class="login-info-text">
                Plataforma interna para la gestión de productos, pedidos y reportes de Kawki.
            </p>

            <ul class="login-benefits">
                <li>
                    <i class="fas fa-check-circle"></i>
                    Control de inventario y catálogo en tiempo real.
                </li>
                <li>
                    <i class="fas fa-check-circle"></i>
                    Reportes para administración y toma de decisiones.
                </li>
            </ul>
        </div>

        <!-- Derecha -->
        <div class="login-card text-center">
            <h2>Iniciar sesión</h2>
            <p class="login-subtitle">
                Accede con tu cuenta de <strong>vendedor</strong> o <strong>administrador</strong>.
            </p>
            <!--
            <div class="role-switch" id="roleSwitch">
                <button type="button" class="role-pill active" data-role="vendedor">
                    <i class="fas fa-user-tag me-1"></i> Vendedor
                </button>
                <button type="button" class="role-pill" data-role="admin">
                    <i class="fas fa-user-shield me-1"></i> Administrador
                </button>
            </div>
            -->
            <div class="mt-4">
                <label for="<%= txtUsuario.ClientID %>" class="form-label">
                    <i class="fas fa-id-card me-2"></i>Usuario:
                </label>
                <asp:TextBox ID="txtUsuario" runat="server"
                    CssClass="form-control mb-3"
                    placeholder="Escribe tu usuario" />

                <label for="<%= txtClave.ClientID %>" class="form-label">
                    <i class="fas fa-lock me-2"></i>Contraseña:
                </label>
                <div class="position-relative mb-2">
                    <asp:TextBox ID="txtClave" runat="server" TextMode="Password"
                        CssClass="form-control pe-5"
                        placeholder="Escribe tu contraseña" />
                    <i id="toggleClave" class="fas fa-eye toggle-pass-icon"
                       title="Mostrar contraseña"></i>
                </div>

                <div class="text-end mb-2">
                    <a href="RecuperarClave.aspx" class="link-recuperar">
                        ¿Olvidaste tu contraseña?
                    </a>
                </div>

                <asp:Label ID="lblMensaje" runat="server"
                    CssClass="text-danger mb-2 d-block" />

                <button type="button"
                        class="btn btn-login w-100 fw-bold"
                        onclick="document.getElementById('<%= btnLogin.ClientID %>').click();">
                    Iniciar sesión
                </button>
                <asp:Button ID="btnLogin" runat="server" Text="HiddenLogin"
                    OnClick="btnLogin_Click" Style="display:none;" />
            </div>
        </div>
    </div>
</form>
</body>
</html>