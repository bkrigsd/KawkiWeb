<%@ Page Title="Reporte de Stock" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="ReporteStock.aspx.cs" Inherits="KawkiWeb.ReporteStock" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/gestionproductos.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="Content/Stylo/reportStock.css" rel="stylesheet" />
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

        <div class="card-kawki mb-4">
            <div class="card-header">
                <div class="card-title"><i class="fas fa-filter"></i> Filtros de período</div>
            </div>
            <div class="card-body">
                <div class="row g-3 align-items-end">

                    <div class="col-md-4">
                        <label class="form-label">Fecha Inicio</label>
                        <asp:TextBox ID="txtFechaInicio" runat="server" TextMode="Date" CssClass="form-control" />
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Fecha Fin</label>
                        <asp:TextBox ID="txtFechaFin" runat="server" TextMode="Date" CssClass="form-control" />
                    </div>

                    <div class="col-md-4">
                        <div class="d-flex gap-2">
                            <asp:Button ID="btnGenerar" runat="server" Text="Generar Reporte"
                                CssClass="btn-kawki-primary flex-fill" OnClick="btnGenerar_Click" />

                            <asp:Button ID="btnLimpiar" runat="server" Text="Limpiar filtros"
                                CssClass="btn-kawki-secondary flex-fill" OnClick="btnLimpiar_Click" />
                        </div>
                    </div>
                </div>

                <!-- NUEVO: Botón para exportar PDF -->
                <div class="row g-3 mt-2">
                    <div class="col-md-12">
                        <asp:Button ID="btnExportarPDF" runat="server" 
                            Text="Descargar como PDF" 
                            CssClass="btn btn-kawki-outline me-2" 
                            OnClick="btnExportarPDF_Click" 
                            Style="width: 100%; font-weight: bold;" />
                    </div>
                </div>

                <!-- MENSAJE DE ERROR DE FECHAS -->
                <asp:Label ID="lblErrorFechas" runat="server"
                           CssClass="text-danger small mt-2 d-block"
                           Visible="false" />

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

            <%--<div class="col-md-3">
                <div class="card-kawki text-center p-3">
                    <h6 class="totales-label">Baja rotación</h6>
                    <asp:Label ID="lblBajaRotacion" runat="server" CssClass="totales-valor" Text="0" />
                </div>
            </div>--%>

        </div>

        <!-- FILA 1: GRÁFICO PRINCIPAL + BARRA LATERAL (Misma anchura) -->
        <div class="row g-4">
            <!-- IZQUIERDA: gráfico principal -->
            <div class="col-lg-6">
                <div class="card-kawki mb-4">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-cubes"></i> Panorama general de stock
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="chart-main">
                            <canvas id="chartStockCategoria"></canvas>
                        </div>
                    </div>
                </div>
            </div>

            <!-- DERECHA: barra lateral de gráficos pequeños -->
            <div class="col-lg-6">
                <div class="sidebar-stats d-flex flex-column gap-3">

                    <div class="card-kawki card-mini-chart">
                        <div class="card-header py-2">
                            <div class="card-title mb-0">
                                <i class="fas fa-box-open"></i> Sin stock
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="chart-mini">
                                <canvas id="chartSinStock"></canvas>
                            </div>
                        </div>
                    </div>

                    <div class="card-kawki card-mini-chart">
                        <div class="card-header py-2">
                            <div class="card-title mb-0">
                                <i class="fas fa-level-down-alt"></i> Stock bajo
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="chart-mini">
                                <canvas id="chartStockBajo"></canvas>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- FILA 2: TABLA AL 100% DE ANCHO -->
        <div class="row g-4 mt-2">
            <div class="col-12">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-exclamation-triangle"></i> Productos en estado crítico
                        </div>
                    </div>
                    <div class="card-body">
                        <asp:GridView ID="gvCriticos" runat="server" CssClass="table table-sm table-striped"
                            AutoGenerateColumns="false">
                            <Columns>
                                <asp:BoundField DataField="Producto" HeaderText="Producto" />
                                <asp:BoundField DataField="Variante" HeaderText="Variante" />
                                <asp:BoundField DataField="Stock" HeaderText="Stock actual" />
                                <asp:BoundField DataField="StockMinimo" HeaderText="Stock mínimo" />
                                <%--<asp:BoundField DataField="Cobertura" HeaderText="% Cobertura" />--%>
                            </Columns>
                        </asp:GridView>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- CHART JS -->
    <script type="text/javascript">
        // Datos generados en el servidor
        const sinStockData = <%= JsonSinStock %>;
        const stockBajoData = <%= JsonStockBajo %>;
        const stockCategoriaData = <%= JsonStockCategoria %>;
    <%--const bajaRotacionData = <%= JsonBajaRotacion %>;--%>

        function renderCharts() {

            // OPCIONAL: animación global por defecto
            if (window.Chart && Chart.defaults) {
                Chart.defaults.animation.duration = 1000;   // 1 segundo
                Chart.defaults.animation.easing = 'easeOutQuart';
            }

            // Productos sin stock (barras) 
            var ctxSinStock = document.getElementById("chartSinStock");
            if (ctxSinStock && sinStockData && sinStockData.labels) {
                new Chart(ctxSinStock, {
                    type: "bar",
                    data: {
                        labels: sinStockData.labels,
                        datasets: [{
                            label: "Unidades agotadas",
                            data: sinStockData.data
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        animation: {
                            duration: 1000,
                            easing: 'easeOutCubic'
                        },
                        animations: {
                            y: {
                                from: 0 // barras suben desde 0
                            }
                        },
                        plugins: { legend: { display: false } },
                        scales: {
                            y: { beginAtZero: true }
                        }
                    }
                });
            }

            //  Productos con stock bajo (barras) 
            var ctxStockBajo = document.getElementById("chartStockBajo");
            if (ctxStockBajo && stockBajoData && stockBajoData.labels) {
                new Chart(ctxStockBajo, {
                    type: "bar",
                    data: {
                        labels: stockBajoData.labels,
                        datasets: [{
                            label: "Unidades disponibles",
                            data: stockBajoData.data
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        animation: {
                            duration: 1000,
                            easing: 'easeOutCubic'
                        },
                        animations: {
                            y: {
                                from: 0
                            }
                        },
                        plugins: { legend: { display: false } },
                        scales: {
                            y: { beginAtZero: true }
                        }
                    }
                });
            }

            //  Stock total por categoría (doughnut) 
            var ctxStockCategoria = document.getElementById("chartStockCategoria");
            if (ctxStockCategoria && stockCategoriaData && stockCategoriaData.labels) {
                new Chart(ctxStockCategoria, {
                    type: "doughnut",
                    data: {
                        labels: stockCategoriaData.labels,
                        datasets: [{
                            data: stockCategoriaData.data
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false, // usa todo el alto de .chart-main
                        cutout: '55%',
                        animation: {
                            duration: 1200,
                            easing: 'easeOutBack',
                            animateRotate: true,
                            animateScale: true
                        },
                        plugins: {
                            legend: {
                                display: true,
                                position: 'bottom'
                            }
                        }
                    }
                });
            }
            /*
            var ctxBajaRot = document.getElementById("chartBajaRotacion");
            if (ctxBajaRot && bajaRotacionData && bajaRotacionData.labels) {
                new Chart(ctxBajaRot, {
                    type: "line",
                    data: {
                        labels: bajaRotacionData.labels,
                        datasets: [{
                            label: "Días en almacén",
                            data: bajaRotacionData.data,
                            tension: 0.4,
                            fill: false
                        }]
                    },
                    options: {
                        responsive: true,
                        animation: {
                            duration: 1000,
                            easing: 'easeOutQuart'
                        }
                    }
                });
            }
            */
        }

        // Ejecutar cuando el DOM esté listo
        document.addEventListener("DOMContentLoaded", renderCharts);
    </script>

</asp:Content>
