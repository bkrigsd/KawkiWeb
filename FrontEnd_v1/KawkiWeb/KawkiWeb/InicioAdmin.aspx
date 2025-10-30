<%@ Page Title="Inicio Administrador" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="InicioAdmin.aspx.cs" Inherits="KawkiWeb.InicioAdmin" %>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="welcome-section">
        <h2>Panel de Administrador</h2>
        <p>Bienvenido, <b>admin</b></p>
    </div>

    <div class="features-section">
        <div class="features-row">
            <div class="feature-card" style="border-top:4px solid #ED6B7F;">
                <h3 style="color:#ED6B7F;"><i class="fas fa-gauge me-2"></i>Dashboard</h3>
                <p>Visualiza un resumen general de la actividad del sistema.</p>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-user-tie me-2"></i>Gestión de Vendedores</h3>
                <p>Administra las cuentas de vendedores registrados en la plataforma.</p>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-boxes-stacked me-2"></i>Inventario</h3>
                <p>Controla el stock y la disponibilidad de los productos.</p>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-sliders me-2"></i>Configuración</h3>
                <p>Ajusta parámetros generales y preferencias del sistema.</p>
            </div>
        </div>
    </div>
</asp:Content>

