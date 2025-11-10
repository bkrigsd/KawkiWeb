<%@ Page Title="Inicio Vendedor" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="InicioVendedor.aspx.cs" Inherits="KawkiWeb.InicioVendedor" %>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="welcome-section">
        <h2>Bienvenido, vendedor</h2>
        <p>Desde este panel puedes gestionar tus productos, revisar ventas y controlar tu stock.</p>
    </div>

    <div class="features-section">
        <div class="features-row">
            <div class="feature-card" style="border-top: 4px solid #ED6B7F;">
                <h3 style="color:#ED6B7F;"><i class="fas fa-box-open me-2"></i>Gestión de Productos</h3>
                <p>Agrega, edita o elimina productos disponibles en la tienda.</p>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-tags me-2"></i>Promociones</h3>
                <p>Administra descuentos y promociones activas.</p>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-chart-line me-2"></i>Reporte de Ventas</h3>
                <p>Consulta las ventas realizadas y los ingresos generados.</p>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-boxes-stacked me-2"></i>Reporte de Stock</h3>
                <p>Supervisa la disponibilidad de tus productos.</p>
            </div>
        </div>
    </div>
</asp:Content>

