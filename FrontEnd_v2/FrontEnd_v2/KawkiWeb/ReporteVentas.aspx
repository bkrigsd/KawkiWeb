<%@ Page Title="Reporte de Ventas" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="ReporteVentas.aspx.cs" Inherits="KawkiWeb.ReporteVentas" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/gestionproductos.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        /* Contenedores de charts con alturas más cómodas */
        .chart-pie {
            min-height: 260px;
        }

        .chart-small {
            min-height: 220px;
        }

        .chart-medium {
            min-height: 240px;
        }

        .chart-container canvas {
            width: 100% !important;
            height: 100% !important;
        }

        /* Tabla del detalle más compacta */
        .table-detalle-productos {
            font-size: 0.9rem;
        }

        .table-detalle-productos thead {
            background-color: #f7f7f7;
            font-weight: 600;
        }

        /* Categorías más grande */
        .chart-categorias {
            min-height: 280px !important;
        }

        /* Colores más pequeño o normal */
        .chart-colores {
            min-height: 200px !important;
        }

    </style>
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
                    <div class="col-md-4">
                        <label class="form-label">Fecha Inicio</label>
                        <asp:TextBox ID="txtFechaInicio" runat="server"
                            TextMode="Date" CssClass="form-control" />
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Fecha Fin</label>
                        <asp:TextBox ID="txtFechaFin" runat="server"
                            TextMode="Date" CssClass="form-control" />
                    </div>

                    <div class="col-md-4">
                        <div class="d-flex gap-2">
                            <asp:Button ID="btnGenerar" runat="server" Text="Generar Reporte"
                                CssClass="btn-kawki-primary flex-fill" OnClick="btnGenerar_Click" />

                            <asp:Button ID="btnLimpiar" runat="server" Text="Limpiar filtros"
                                CssClass="btn-kawki-secondary flex-fill" OnClick="btnLimpiar_Click" />
                        </div>
                    </div>

                   <%-- <div class="col-md-4">
                        <asp:Button ID="btnGenerar" runat="server" Text="Generar Reporte"
                            CssClass="btn-kawki-primary w-100" OnClick="btnGenerar_Click" />
                    </div>--%>
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

                <!-- Mensaje de error de fechas -->
                <div class="mt-2">
                    <asp:Label ID="lblMensajeFechas" runat="server"
                        CssClass="text-danger" Visible="false" />
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

            <!-- Productos más vendidos (más compacto) -->
            <div class="col-xl-4 col-lg-5">
                <div class="card-kawki h-100">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-cube"></i> Productos más vendidos
                        </div>
                    </div>
                    <div class="card-body chart-md">
                        <canvas id="chartProductos"></canvas>
                    </div>
                </div>
            </div>

            <!-- Categorías y colores -->
            <div class="col-xl-8 col-lg-7">
                <div class="card-kawki h-100">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-palette"></i> Categorías y colores más demandados
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row g-3">

                            <div class="col-md-6 chart-categorias">
                                <h6 class="mb-2">Categorías</h6>
                                <canvas id="chartCategorias"></canvas>
                            </div>

                            <div class="col-md-6 chart-colores">
                                <h6 class="mb-2">Colores</h6>
                                <canvas id="chartColores"></canvas>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="row g-4 mt-1">

            <!-- Comparativa por talla -->
            <div class="col-lg-6">
                <div class="card-kawki h-100">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-ruler-combined"></i> Comparativa por talla
                        </div>
                    </div>
                    <div class="card-body chart-md">
                        <canvas id="chartTallas"></canvas>
                    </div>
                </div>
            </div>

            <!-- Variación de ventas -->
            <%--<div class="col-lg-6">
                <div class="card-kawki h-100">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-chart-area"></i> Variación de ventas
                        </div>
                    </div>
                    <div class="card-body chart-md">
                        <canvas id="chartVariacion"></canvas>
                    </div>
                </div>
            </div>--%>

            <!-- Top 3 clientes que más compraron -->
            <div class="col-lg-6">
                <div class="card-kawki h-100">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-user-tie"></i> Top 3 clientes que más compraron
                        </div>
                    </div>
                    <div class="card-body chart-md">
                        <canvas id="chartTopClientes"></canvas>
                    </div>
                </div>
            </div>

        </div>

        <!-- NUEVO: tabla resumen de productos del período -->
        <div class="row g-4 mt-1">
            <div class="col-12">
                <div class="card-kawki">
                    <div class="card-header">
                        <div class="card-title">
                            <i class="fas fa-list-ul"></i> Detalle de productos del período
                        </div>
                    </div>
                    <div class="card-body px-4 py-2">
                        <asp:GridView ID="gvTopProductos" runat="server"
                            CssClass="table table-striped table-hover kawki-grid"
                            AutoGenerateColumns="False"
                            GridLines="None">
    
                            <HeaderStyle CssClass="kawki-grid-header" />
                            <RowStyle CssClass="kawki-grid-row" />
                            <AlternatingRowStyle CssClass="kawki-grid-row-alt" />

                            <Columns>
                                <asp:BoundField DataField="Producto" HeaderText="Producto" />
                                <asp:BoundField DataField="Cantidad" HeaderText="Unidades" />
                                <asp:BoundField DataField="Participacion"
                                    HeaderText="% participación"
                                    DataFormatString="{0:N1} %" />
                            </Columns>
                        </asp:GridView>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function () {
            renderCharts();
        });

        function renderCharts() {
            var prodLabels = <%= ChartProductosLabelsJson %> || [];
        var prodData = <%= ChartProductosDataJson %> || [];

        var catLabels = <%= ChartCategoriasLabelsJson %> || [];
        var catData = <%= ChartCategoriasDataJson %> || [];

        var colorLabels = <%= ChartColoresLabelsJson %> || [];
        var colorData   = <%= ChartColoresDataJson %> || [];

        var tallaLabels = <%= ChartTallasLabelsJson %> || [];
        var tallaData   = <%= ChartTallasDataJson %> || [];

        var topCliLabels = <%= ChartTopClientesLabelsJson %> || [];
        var topCliData = <%= ChartTopClientesDataJson %> || [];

            // ====== OPCIONAL: animación global por defecto ======
            if (window.Chart && Chart.defaults) {
                Chart.defaults.animation.duration = 1000;   // 1 segundo
                Chart.defaults.animation.easing = 'easeOutQuart';
            }

            // ====== Productos: gráfico pastel ======
            var ctxProductos = document.getElementById("chartProductos");
            if (ctxProductos && prodLabels.length > 0) {
                new Chart(ctxProductos, {
                    type: "pie",
                    data: {
                        labels: prodLabels,
                        datasets: [{
                            data: prodData,
                            backgroundColor: [
                                "#ED6B7F", "#f9a8b1", "#ffc3ca", "#fca5a5", "#fecaca",
                                "#f97373", "#fb7185", "#fda4af", "#fed7e2", "#fecdd3"
                            ]
                        }]
                    },
                    options: {
                        responsive: true,
                        animation: {
                            duration: 1300,
                            easing: 'easeOutBack',  // entrada suave
                        },
                        plugins: {
                            legend: { display: true, position: "bottom" }
                        }
                    }
                });
            }

            // ====== Categorías (doughnut) ======
            var ctxCategorias = document.getElementById("chartCategorias");
            if (ctxCategorias && catLabels.length > 0) {
                new Chart(ctxCategorias, {
                    type: "doughnut",
                    data: {
                        labels: catLabels,
                        datasets: [{
                            data: catData,
                            backgroundColor: ["#ED6B7F", "#f9a8b1", "#ffc3ca", "#fca5a5", "#fecaca"]
                        }]
                    },
                    options: {
                        responsive: true,
                        cutout: '55%',
                        animation: {
                            duration: 1200,
                            easing: 'easeOutQuart',
                            animateRotate: true,
                            animateScale: true
                        },
                        plugins: {
                            legend: { display: true, position: "bottom" }
                        }
                    }
                });
            }

            // ====== Colores (barras) ======
            var ctxColores = document.getElementById("chartColores");
            if (ctxColores && colorLabels.length > 0) {
                new Chart(ctxColores, {
                    type: "bar",
                    data: {
                        labels: colorLabels,
                        datasets: [{
                            label: "Unidades",
                            data: colorData,
                            backgroundColor: "#ED6B7F"
                        }]
                    },
                    options: {
                        responsive: true,
                        animation: {
                            duration: 1000,
                            easing: 'easeOutCubic'
                        },
                        animations: {
                            y: {
                                from: 0   // las barras suben desde 0
                            }
                        },
                        plugins: { legend: { display: false } },
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            }

            // ====== Tallas (barras) ======
            var ctxTallas = document.getElementById("chartTallas");
            if (ctxTallas && tallaLabels.length > 0) {
                new Chart(ctxTallas, {
                    type: "bar",
                    data: {
                        labels: tallaLabels,
                        datasets: [{
                            label: "Ventas por talla",
                            data: tallaData,
                            backgroundColor: "#ED6B7F"
                        }]
                    },
                    options: {
                        responsive: true,
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
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            }

            // ====== Top 3 clientes (barras) ======
            var ctxTopCli = document.getElementById("chartTopClientes");
            if (ctxTopCli && topCliLabels.length > 0) {
                new Chart(ctxTopCli, {
                    type: "bar",
                    data: {
                        labels: topCliLabels,
                        datasets: [{
                            label: "Total comprado (S/.)",
                            data: topCliData,
                            backgroundColor: "#ED6B7F"
                        }]
                    },
                    options: {
                        responsive: true,
                        animation: {
                            duration: 1200,
                            easing: 'easeOutQuart'
                        },
                        animations: {
                            y: {
                                from: 0
                            }
                        },
                        plugins: {
                            legend: { display: false },
                            tooltip: {
                                callbacks: {
                                    label: function (ctx) {
                                        return "S/. " + ctx.parsed.y.toFixed(2);
                                    }
                                }
                            }
                        },
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    callback: function (value) {
                                        return "S/. " + value;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    </script>

</asp:Content>
