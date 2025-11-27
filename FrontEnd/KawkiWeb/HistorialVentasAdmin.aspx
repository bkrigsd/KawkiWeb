<%@ Page Title="Historial Ventas" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="HistorialVentasAdmin.aspx.cs" Inherits="KawkiWeb.HistorialVentasAdmin" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/historialventaadmin.css" rel="stylesheet" />
    <link href="Content/Stylo/detallevent.css" rel="stylesheet" />
    <%--<link href="Content/Stylo/registrodescuent.css" rel="stylesheet" />--%>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid historial-container">
        <!-- Encabezado -->
        <div class="historial-header">
            <h1>
                <i class="fas fa-chart-line me-2"></i>Historial de Ventas
            </h1>
            <p>Consulta el historial completo de ventas con filtros avanzados por fecha y vendedor.</p>
        </div>

        <!-- Estadísticas -->
        <div class="card-kawki">
            <div class="card-body">
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-label">Total Ventas</div>
                        <asp:Label ID="lblTotalVentas" runat="server" ClientIDMode="Static" CssClass="stat-value" Text="0" />
                    </div>
                    <div class="stat-card green">
                        <div class="stat-label">Monto Total</div>
                        <asp:Label ID="lblMontoTotal" runat="server" ClientIDMode="Static" CssClass="stat-value" Text="S/ 0.00" />
                    </div>
                    <div class="stat-card blue">
                        <div class="stat-label">Promedio por Venta</div>
                        <asp:Label ID="lblPromedio" runat="server" ClientIDMode="Static" CssClass="stat-value" Text="S/ 0.00" />
                    </div>
                </div>
            </div>
        </div>

        <!-- Filtros + Ordenamiento -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <h5 class="card-title mb-0 d-flex align-items-center">
                        <i class="fas fa-filter me-2"></i> Filtros de búsqueda
                    </h5>

                    <!-- Ordenamiento pequeño a la derecha -->
                    <div class="filtros-orden d-flex align-items-center">
                        <span class="orden-label me-1">Ordenar:</span>

                        <asp:DropDownList ID="ddlOrdenarPor" runat="server"
                            CssClass="form-select form-select-sm orden-select me-1"
                            AutoPostBack="true"
                            OnSelectedIndexChanged="ActualizarOrden">
                            <asp:ListItem Text="ID"          Value="IdVenta" />
                            <asp:ListItem Text="Fecha"       Value="Fecha" />
                            <asp:ListItem Text="Vendedor"    Value="Vendedor" />
                            <asp:ListItem Text="Canal"       Value="Canal" />
                            <asp:ListItem Text="Monto Total" Value="MontoTotal" />
                        </asp:DropDownList>

                        <asp:DropDownList ID="ddlDireccion" runat="server"
                            CssClass="form-select form-select-sm orden-select"
                            AutoPostBack="true"
                            OnSelectedIndexChanged="ActualizarOrden">
                            <asp:ListItem Text="Asc"  Value="ASC" />
                            <asp:ListItem Text="Desc" Value="DESC" />
                        </asp:DropDownList>
                    </div>
                </div>
            </div>

            <div class="card-body">
                <div class="row g-3 align-items-end">
                    <div class="col-md-3">
                        <label class="form-label" for="<%= txtFechaInicio.ClientID %>">
                            <i class="fas fa-calendar-alt me-1"></i>Fecha inicio
                        </label>
                        <asp:TextBox ID="txtFechaInicio" runat="server" 
                            CssClass="form-control" TextMode="Date" />
                    </div>
                    <div class="col-md-3">
                        <label class="form-label" for="<%= txtFechaFin.ClientID %>">
                            <i class="fas fa-calendar-alt me-1"></i>Fecha fin
                        </label>
                        <asp:TextBox ID="txtFechaFin" runat="server" 
                            CssClass="form-control" TextMode="Date" />
                    </div>
                    <div class="col-md-3">
                        <label class="form-label" for="<%= ddlVendedor.ClientID %>">
                            <i class="fas fa-user me-1"></i>Vendedor
                        </label>
                        <asp:DropDownList ID="ddlVendedor" runat="server" CssClass="form-select">
                            <asp:ListItem Text="Todos los vendedores" Value="0" />
                        </asp:DropDownList>
                    </div>
                    <div class="col-md-3">
                        <asp:Button ID="btnBuscar" runat="server" 
                            CssClass="btn btn-kawki-primary w-100"
                            Text="Buscar"
                            OnClick="btnBuscar_Click" />
                        <asp:Button ID="btnLimpiar" runat="server" 
                            CssClass="btn btn-kawki-outline w-100 mt-2"
                            Text="Limpiar filtros"
                            OnClick="btnLimpiar_Click" 
                            CausesValidation="false" />
                    </div>
                </div>

                <div class="col-12">
                    <asp:Label ID="lblErrorFiltros" runat="server"
                        CssClass="text-danger d-block mt-2"
                        Visible="false" />
                </div>
            </div>
        </div>

        <!-- Tabla de ventas -->
        <div class="card-kawki">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="card-title mb-0">
                    <i class="fas fa-receipt"></i> Listado de Ventas
                </h5>
                <span class="badge badge-vendedor">
                    <asp:Label ID="lblContador" runat="server" Text="0 ventas encontradas" />
                </span>
            </div>
            <div class="card-body">
                <asp:Label ID="lblMensaje" runat="server" CssClass="text-info mb-2 d-block" />

                <asp:GridView ID="gvVentas" runat="server" 
                    AutoGenerateColumns="False"
                    CssClass="table table-historial"
                    GridLines="None" 
                    ShowHeaderWhenEmpty="True"
                    OnRowCommand="gvVentas_RowCommand">
                    <Columns>
                        <asp:BoundField DataField="IdVenta" HeaderText="ID" />
                        <asp:BoundField DataField="Fecha" HeaderText="Fecha" DataFormatString="{0:dd/MM/yyyy}" />
                        <asp:BoundField DataField="Vendedor" HeaderText="Vendedor" />
                        <asp:BoundField DataField="Canal" HeaderText="Canal" />
                        <%--<asp:BoundField DataField="Descuento" HeaderText="Descuento" />--%>
                        <%--<asp:BoundField DataField="EsValida" HeaderText="Válida" />--%>
                        <asp:TemplateField HeaderText="Monto Total">
                            <ItemTemplate>
                                <span class="badge badge-monto">
                                    <%# String.Format("S/ {0:0.00}", Eval("MontoTotal")) %>
                                </span>
                            </ItemTemplate>
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Acciones">
                            <ItemTemplate>
                                <asp:Button runat="server" 
                                    CommandName="VerDetalle" 
                                    CommandArgument='<%# Eval("IdVenta") %>'
                                    Text="Ver detalle"
                                    CssClass="btn btn-detalle"
                                    CausesValidation="false" />
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                    <EmptyDataTemplate>
                        <div class="text-center py-4">
                            <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                            <p class="text-muted-small">No se encontraron ventas con los filtros aplicados.</p>
                        </div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>

    </div>

    <!-- Modal Detalle Venta -->
    <div id="modalDetalleVenta" class="modal-kawki">
        <div class="modal-content-kawki">

            <!-- HEADER -->
            <div class="modal-header-kawki border-bottom pb-2">
                <h4 class="fw-bold m-0">
                    <i class="fas fa-file-invoice-dollar me-2 text-primary"></i>
                    Detalle de Venta # <asp:Label ID="lblIdVentaDetalle" runat="server" />
                </h4>
                <button type="button" class="btn-close" onclick="cerrarDetalleVenta()"></button>
            </div>

            <div class="modal-body-kawki">

                <!-- INFORMACIÓN GENERAL -->
                <div class="detalle-section">
                    <h5 class="section-title">
                        <i class="fas fa-info-circle me-2 text-primary"></i>Información General
                    </h5>

                    <div class="detalle-grid">
                        <div><strong>Vendedor:</strong> <asp:Label ID="lblVendedorDetalle" runat="server" /></div>
                        <div><strong>Fecha:</strong> <asp:Label ID="lblFechaDetalle" runat="server" /></div>
                        <div><strong>Canal:</strong> <asp:Label ID="lblCanalDetalle" runat="server" /></div>
                    </div>
                </div>

                <!-- COMPROBANTE -->
                <div class="detalle-section">
                    <h5 class="section-title">
                        <i class="fas fa-receipt me-2 text-success"></i>Comprobante de Pago
                    </h5>

                    <div class="detalle-grid">
                        <div><strong>Tipo:</strong> <asp:Label ID="lblTipoComprobante" runat="server" /></div>
                        <div><strong>Serie:</strong> <asp:Label ID="lblNumeroSerie" runat="server" /></div>

                        <div class="col-span-2"><strong>Cliente:</strong> <asp:Label ID="lblNombreCliente" runat="server" /></div>

                        <div><strong>DNI:</strong> <asp:Label ID="lblDniCliente" runat="server" /></div>
                        <div><strong>RUC:</strong> <asp:Label ID="lblRucCliente" runat="server" /></div>

                        <div class="col-span-2">
                            <strong>Dirección:</strong> <asp:Label ID="lblDireccionFiscal" runat="server" />
                        </div>

                        <div><strong>Método Pago:</strong> <asp:Label ID="lblMetodoPago" runat="server" /></div>
                        <%--<div><strong>Subtotal:</strong> <asp:Label ID="lblSubtotal" runat="server" /></div>
                        <div><strong>IGV:</strong> <asp:Label ID="lblIgv" runat="server" /></div>--%>
                    </div>
                </div>

                <!-- PRODUCTOS -->
                <div class="detalle-section">
                    <h5 class="section-title">
                        <i class="fas fa-boxes me-2 text-warning"></i>Productos
                    </h5>

                    <asp:GridView ID="gvDetalleProductos" runat="server"
                        AutoGenerateColumns="False"
                        CssClass="table table-bordered table-striped text-center shadow-sm detalle-tabla"
                        GridLines="None">
                        <Columns>
                            <asp:BoundField DataField="Producto" HeaderText="Producto" />
                            <asp:BoundField DataField="Color" HeaderText="Color" />
                            <asp:BoundField DataField="Talla" HeaderText="Talla" />
                            <asp:BoundField DataField="SKU" HeaderText="SKU" />
                            <asp:BoundField DataField="Cantidad" HeaderText="Cantidad" />
                            <asp:BoundField DataField="PrecioUnitario" HeaderText="P. Unitario" DataFormatString="{0:C}" />
                            <asp:BoundField DataField="Subtotal" HeaderText="Subtotal" DataFormatString="{0:C}" />
                        </Columns>
                    </asp:GridView>
                </div>

                <!-- DESCUENTO APLICADO -->
                <div class="detalle-section" id="seccionDescuento" runat="server" visible="false">
                    <h5 class="section-title">
                        <i class="fas fa-tags me-2 text-danger"></i>Descuento Aplicado
                    </h5>

                    <div class="detalle-grid">
                        <div class="col-span-2">
                            <strong>Descripción:</strong>
                            <asp:Label ID="lblDescDescripcion" runat="server" />
                        </div>

                        <div>
                            <strong>Condición:</strong>
                            <asp:Label ID="lblDescCondicion" runat="server" />
                        </div>

                        <div>
                            <strong>Beneficio:</strong>
                            <asp:Label ID="lblDescBeneficio" runat="server" />
                        </div>
                    </div>
                </div>

                <!-- RESUMEN FINAL -->
                <div class="resumen-columna-derecha">

                    <div class="fila-resumen">
                        <span class="titulo">Subtotal:</span>
                        <span class="valor"><asp:Label ID="lblSubtotal" runat="server" /></span>
                    </div>

                    <div class="fila-resumen">
                        <span class="titulo">IGV:</span>
                        <span class="valor"><asp:Label ID="lblIgv" runat="server" /></span>
                    </div>

                    <div class="fila-resumen">
                        <span class="titulo">Descuento:</span>
                        <span class="valor"><asp:Label ID="lblDescuento" runat="server" /></span>
                    </div>

                    <hr />

                    <div class="fila-total">
                        <span class="titulo-total">Total:</span>
                        <span class="valor-total badge bg-success">
                            <asp:Label ID="lblTotalComprobante" runat="server" />
                        </span>
                    </div>

                </div>

            </div>

            <!-- FOOTER -->
            <div class="modal-footer-kawki">
                <button type="button" class="btn btn-outline-secondary px-4" onclick="cerrarDetalleVenta()">
                    Cerrar
                </button>
            </div>

        </div>
    </div>


    <script>
        function abrirDetalleVenta() {
            document.getElementById("modalDetalleVenta").classList.add("show");
        }
        function cerrarDetalleVenta() {
            document.getElementById("modalDetalleVenta").classList.remove("show");
        }
    </script>

    <script>
        function animateNumber(elementId, start, end, duration, prefix = "", decimals = 0) {
            const element = document.getElementById(elementId);
            if (!element) return;

            const startTime = performance.now();
            const range = end - start;

            function update(currentTime) {
                const elapsed = currentTime - startTime;
                const progress = Math.min(elapsed / duration, 1); // 0 a 1

                // Interpolación lineal
                const current = start + (range * progress);

                const value = decimals > 0
                    ? (current / 100).toFixed(decimals)
                    : Math.round(current);

                element.innerText = prefix + value;

                if (progress < 1) {
                    requestAnimationFrame(update);
                }
            }

            requestAnimationFrame(update);
        }

        // Animación principal al actualizar estadísticas
        function animarDashboard(total, monto, promedio) {
            animateNumber("lblTotalVentas", 0, total, 600, "", 0);
            animateNumber("lblMontoTotal", 0, monto * 100, 700, "S/ ", 2);
            animateNumber("lblPromedio", 0, promedio * 100, 700, "S/ ", 2);
        }
    </script>

</asp:Content>