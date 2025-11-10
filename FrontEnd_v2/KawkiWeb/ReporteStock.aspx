<%@ Page Title="Reporte de Stock" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="ReporteStock.aspx.cs" Inherits="KawkiWeb.ReporteStock" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/gestionproductos.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="ventas-container">

        <!-- HEADER -->
        <div class="ventas-header d-flex justify-content-between align-items-center">
            <div>
                <h1><i class="fas fa-warehouse me-2"></i>Reporte de Stock</h1>
                <p>Gestiona y analiza el inventario del sistema Kawki según el periodo seleccionado.</p>
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
                    <h6 class="totales-label">Productos sin stock</h6>
                    <asp:Label ID="lblSinStock" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Productos con stock bajo</h6>
                    <asp:Label ID="lblStockBajo" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Stock total</h6>
                    <asp:Label ID="lblStockTotal" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>
            <div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Baja rotación</h6>
                    <asp:Label ID="lblBajaRotacion" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>
        </div>

        <!-- GRÁFICOS Y TABLAS -->
        <div class="row g-4">
            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-box-open"></i> Productos sin stock</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartSinStock"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-level-down-alt"></i> Productos con stock bajo</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartStockBajo"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-cubes"></i> Stock total por categoría</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartStockCategoria"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-hourglass-half"></i> Productos con baja rotación</div>
                    </div>
                    <div class="card-body">
                        <canvas id="chartBajaRotacion"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- CHART JS -->
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            renderCharts();
        });

        function renderCharts() {
            // Productos sin stock
            new Chart(document.getElementById("chartSinStock"), {
                type: "bar",
                data: {
                    labels: ["Oxford Negro", "Derby Marrón", "Casual Beige"],
                    datasets: [{
                        label: "Unidades agotadas",
                        data: [0, 0, 0],
                        backgroundColor: "#ed6b7f"
                    }]
                },
                options: { responsive: true, plugins: { legend: { display: false } } }
            });

            // Productos con stock bajo
            new Chart(document.getElementById("chartStockBajo"), {
                type: "bar",
                data: {
                    labels: ["Oxford Negro", "Derby Marrón", "Casual Azul"],
                    datasets: [{
                        label: "Unidades disponibles",
                        data: [3, 5, 2],
                        backgroundColor: "#f9a8b1"
                    }]
                },
                options: { responsive: true, plugins: { legend: { display: false } } }
            });

            // Stock total por categoría
            new Chart(document.getElementById("chartStockCategoria"), {
                type: "doughnut",
                data: {
                    labels: ["Oxford", "Derby", "Casual"],
                    datasets: [{
                        data: [80, 60, 40],
                        backgroundColor: ["#ed6b7f", "#fcb4be", "#ffd3db"]
                    }]
                },
                options: { responsive: true }
            });

            // Productos con baja rotación
            new Chart(document.getElementById("chartBajaRotacion"), {
                type: "line",
                data: {
                    labels: ["Ene", "Feb", "Mar", "Abr", "May", "Jun"],
                    datasets: [{
                        label: "Días en almacén",
                        data: [50, 70, 85, 100, 90, 120],
                        borderColor: "#ed6b7f",
                        tension: 0.4,
                        fill: false
                    }]
                },
                options: { responsive: true }
            });
        }
    </script>
</asp:Content>
