<%@ Page Title="Reporte de Ventas" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="ReporteVentas.aspx.cs" Inherits="KawkiWeb.ReporteVentas" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/gestionproductos.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="ventas-container">

        <!-- ENCABEZADO -->
        <div class="ventas-header d-flex justify-content-between align-items-center">
            <div>
                <h1><i class="fas fa-chart-line me-2"></i>Reporte de Ventas</h1>
                <p>Analiza el comportamiento de ventas y las preferencias de tus clientes.</p>
            </div>
        </div>

        <!-- FILTROS -->
        <div class="card-kawki mb-4">
            <div class="card-header">
                <div class="card-title"><i class="fas fa-filter"></i> Filtros de período</div>
            </div>
            <div class="card-body">
                <div class="row g-3 align-items-end">
                    <div class="col-md-3">
                        <label class="form-label">Tipo de período</label>
                        <asp:DropDownList ID="ddlPeriodo" runat="server" CssClass="form-select">
                            <asp:ListItem Text="Diario" Value="diario" />
                            <asp:ListItem Text="Semanal" Value="semanal" />
                            <asp:ListItem Text="Mensual" Value="mensual" />
                            <asp:ListItem Text="Personalizado" Value="personalizado" />
                        </asp:DropDownList>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Desde</label>
                        <asp:TextBox ID="txtFechaInicio" runat="server" TextMode="Date" CssClass="form-control" />
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Hasta</label>
                        <asp:TextBox ID="txtFechaFin" runat="server" TextMode="Date" CssClass="form-control" />
                    </div>
                    <div class="col-md-3">
                        <asp:Button ID="btnGenerar" runat="server" Text="Generar Reporte"
                            CssClass="btn-kawki-primary w-100" OnClick="btnGenerar_Click" />
                    </div>
                </div>
            </div>
        </div>

        <!-- RESUMEN -->
        <div class="row g-3 mb-4">
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Ventas Totales</h6>
                    <asp:Label ID="lblVentasTotales" runat="server" CssClass="totales-valor" Text="S/. 0.00" />
                </div>
            </div>
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Productos Vendidos</h6>
                    <asp:Label ID="lblProductosVendidos" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Clientes Atendidos</h6>
                    <asp:Label ID="lblClientes" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Promedio Diario</h6>
                    <asp:Label ID="lblPromedioDiario" runat="server" CssClass="totales-valor" Text="S/. 0.00" />
                </div>
            </div>
        </div>

        <!-- GRÁFICOS -->
        <div class="row g-4">
            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-cube"></i> Productos más vendidos</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartProductos"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-palette"></i> Categorías y colores más demandados</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartCategorias"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-ruler-combined"></i> Comparativa por talla</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartTallas"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-chart-area"></i> Variación de ventas</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartVariacion"></canvas>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- JS: CARGA DE GRÁFICOS -->
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            renderCharts();
        });

        function renderCharts() {
            const ctxProductos = document.getElementById("chartProductos");
            new Chart(ctxProductos, {
                type: "bar",
                data: {
                    labels: ["Oxford Clásico", "Derby Elegante", "Casual Negro"],
                    datasets: [{
                        label: "Unidades vendidas",
                        data: [120, 90, 70],
                        backgroundColor: "#ED6B7F"
                    }]
                },
                options: { responsive: true, plugins: { legend: { display: false } } }
            });

            const ctxCategorias = document.getElementById("chartCategorias");
            new Chart(ctxCategorias, {
                type: "doughnut",
                data: {
                    labels: ["Oxford", "Derby", "Casual"],
                    datasets: [{
                        data: [50, 30, 20],
                        backgroundColor: ["#ED6B7F", "#f9a8b1", "#ffc3ca"]
                    }]
                },
                options: { responsive: true }
            });

            const ctxTallas = document.getElementById("chartTallas");
            new Chart(ctxTallas, {
                type: "bar",
                data: {
                    labels: ["35", "36", "37", "38", "39", "40"],
                    datasets: [{
                        label: "Ventas por talla",
                        data: [10, 15, 30, 25, 20, 12],
                        backgroundColor: "#ed6b7f"
                    }]
                },
                options: { responsive: true, plugins: { legend: { display: false } } }
            });

            const ctxVariacion = document.getElementById("chartVariacion");
            new Chart(ctxVariacion, {
                type: "line",
                data: {
                    labels: ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"],
                    datasets: [{
                        label: "Ventas diarias",
                        data: [400, 600, 450, 700, 800, 650, 900],
                        borderColor: "#ed6b7f",
                        tension: 0.4,
                        fill: false
                    }]
                },
                options: { responsive: true, plugins: { legend: { display: false } } }
            });
        }
    </script>
</asp:Content>
