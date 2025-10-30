<%@ Page Title="Recuperar Contraseña" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="RecuperarClave.aspx.cs" Inherits="KawkiWeb.RecuperarClave" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">

    <style>
        .btn-rosa {
            background-color: #ED6B7F;
            border: none;
            color: #fff;
            transition: 0.2s ease-in-out;
        }

        .btn-rosa:hover {
            background-color: #e25b73;
        }
    </style>

</asp:Content>

<asp:Content ID="Main" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container py-5" style="max-width:480px;">
        <h3 class="mb-4 text-center">Recuperar Contraseña</h3>
        <p class="text-muted text-center mb-4">Ingresa tu correo electrónico y te enviaremos un enlace para restablecer tu contraseña.</p>

        <asp:TextBox ID="txtCorreo" runat="server" CssClass="form-control mb-2" placeholder="Correo electrónico" />
        <asp:Label ID="lblError" runat="server" CssClass="text-danger mb-2 d-block fw-semibold"></asp:Label>
        <asp:Button ID="btnRecuperar" runat="server"
            CssClass="btn w-100 fw-bold btn-rosa"
            Text="Enviar enlace de recuperación"
            OnClick="btnRecuperar_Click" />

        <div class="text-center mt-3">
            <a href="Login.aspx" class="text-decoration-none" style="color:#ED6B7F;">Volver a iniciar sesión</a>
        </div>
    </div>
</asp:Content>