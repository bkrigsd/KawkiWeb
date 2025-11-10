<%@ Page Title="Mis Ventas" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="HistorialVentasVendedor.aspx.cs" Inherits="KawkiWeb.HistorialVentasVendedor" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        /* Variable CSS principal */
        :root {
            --primary-color: #ED6B7F;
        }

        .historial-container {
            padding: 20px;
            background-color: #f8f9fa;
            min-height: calc(100vh - 60px);
            margin-left: 0;
            margin-top: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .historial-header {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }

        .historial-header h1 {
            color: #333;
            font-size: 24px;
            font-weight: 500;
            margin-bottom: 5px;
        }

        .historial-header p {
            color: #666;
            margin: 0;
            font-size: 14px;
        }

        /* Tarjetas */
        .card-kawki {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 20px;
            border: 1px solid #f0f0f0;
        }

        .card-kawki .card-body {
            padding: 15px 20px 20px 20px; /* mismo left/right que el header */
        }

        .card-kawki .card-header {
            background-color: #fff;
            border-bottom: 1px solid #f3f4f6;
            padding: 15px 20px;
            border-radius: 8px 8px 0 0;
        }

        .card-kawki .card-title {
            font-size: 16px;
            color: #333;
            font-weight: 600;
            margin: 0;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .card-kawki .card-title i {
            color: var(--primary-color);
        }

        /* Formularios */
        .form-label {
            font-size: 13px;
            font-weight: 500;
            color: #555;
        }

        .form-control,
        .form-select {
            padding: 8px 10px;
            border: 1px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        .form-control:focus,
        .form-select:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.15rem rgba(237, 107, 127, 0.25);
        }

        /* Tabla */
        .table-historial {
            width: 100%;
            margin-top: 0px;
        }

        .table-historial th {
            font-size: 13px;
            color: #666;
            background-color: #f8f9fa;
            font-weight: 500;
            border-bottom: 2px solid #eaeaea;
            padding: 12px 10px;
            text-align: left;
        }

        .table-historial td {
            font-size: 13px;
            padding: 12px 10px;
            vertical-align: middle;
            border-bottom: 1px solid #f0f0f0;
        }

        .table-historial tbody tr:hover {
            background-color: #f9fafb;
        }

        /* Botones */
        .btn-kawki-primary {
            background-color: var(--primary-color);
            border: none;
            padding: 10px 24px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            color: white;
            transition: all 0.3s;
        }

        .btn-kawki-primary:hover {
            background-color: #d85769;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(237, 107, 127, 0.3);
        }

        .btn-kawki-outline {
            border: 1px solid #ced4da;
            color: #555;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            background-color: white;
            transition: all 0.3s;
        }

        .btn-kawki-outline:hover {
            border-color: var(--primary-color);
            color: var(--primary-color);
            transform: translateY(-1px);
            box-shadow: 0 2px 8px rgba(237, 107, 127, 0.15);
        }

        .btn-detalle {
            font-size: 12px;
            padding: 4px 12px;
            border-radius: 4px;
            border: 1px solid var(--primary-color);
            color: var(--primary-color);
            background-color: white;
            transition: all 0.2s;
        }

        .btn-detalle:hover {
            background-color: var(--primary-color);
            color: white;
        }

        /* Badges */
        .badge-vendedor {
            font-size: 11px;
            background: #e8f4f8;
            color: #0891b2;
            border-radius: 999px;
            padding: 3px 9px;
        }

        .badge-monto {
            font-size: 13px;
            background: #f0fdf4;
            color: #16a34a;
            border-radius: 6px;
            padding: 4px 10px;
            font-weight: 600;
        }

        /* Estadísticas */
        .stats-container {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }

        .stat-card {
            flex: 1;
            min-width: 200px;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            padding: 20px;
            border-radius: 8px;
            color: white;
        }

        .stat-card.purple {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .stat-card.blue {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        .stat-label {
            font-size: 13px;
            opacity: 0.9;
            margin-bottom: 5px;
        }

        .stat-value {
            font-size: 28px;
            font-weight: 700;
        }

        .text-muted-small {
            font-size: 12px;
            color: #999;
        }

        /* Info personal */
        .info-vendedor {
            background: linear-gradient(135deg, #fdfcfb 0%, #e2d1c3 100%);
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .info-vendedor h5 {
            font-size: 16px;
            color: #333;
            margin-bottom: 5px;
        }

        .info-vendedor p {
            font-size: 13px;
            color: #666;
            margin: 0;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .historial-container {
                padding: 15px;
            }

            .historial-header {
                padding: 15px;
            }

            .historial-header h1 {
                font-size: 20px;
            }

            .table-historial {
                font-size: 12px;
            }

            .stats-container {
                flex-direction: column;
            }
        }
    </style>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid historial-container">
        <!-- Encabezado -->
        <div class="historial-header">
            <h1>
                <i class="fas fa-shopping-bag me-2"></i>Mis Ventas
            </h1>
            <p>Consulta el historial de las ventas que has registrado.</p>
        </div>

        <!-- Info del vendedor -->
        <div class="info-vendedor">
            <h5>
                <i class="fas fa-user-circle me-2"></i>
                <asp:Label ID="lblNombreVendedor" runat="server" Text="Vendedor" />
            </h5>
            <p>Visualizando únicamente tus ventas registradas</p>
        </div>

        <!-- Estadísticas personales -->
        <div class="card-kawki">
            <div class="card-body">
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-label">Mis Ventas</div>
                        <asp:Label ID="lblTotalVentas" runat="server" CssClass="stat-value" Text="0" />
                    </div>
                    <div class="stat-card purple">
                        <div class="stat-label">Total Vendido</div>
                        <asp:Label ID="lblMontoTotal" runat="server" CssClass="stat-value" Text="S/ 0.00" />
                    </div>
                    <div class="stat-card blue">
                        <div class="stat-label">Promedio por Venta</div>
                        <asp:Label ID="lblPromedio" runat="server" CssClass="stat-value" Text="S/ 0.00" />
                    </div>
                </div>
            </div>
        </div>

        <!-- Filtros 
        <div class="card-kawki">
            <div class="card-header">
                <h5 class="card-title mb-0">
                    <i class="fas fa-filter"></i> Filtrar por fecha
                </h5>
            </div>
            <div class="card-body">
                <div class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <label class="form-label" for="<%= txtFechaInicio.ClientID %>">
                            <i class="fas fa-calendar-alt me-1"></i>Fecha inicio
                        </label>
                        <asp:TextBox ID="txtFechaInicio" runat="server" 
                            CssClass="form-control" TextMode="Date" />
                    </div>
                    <div class="col-md-4">
                        <label class="form-label" for="<%= txtFechaFin.ClientID %>">
                            <i class="fas fa-calendar-alt me-1"></i>Fecha fin
                        </label>
                        <asp:TextBox ID="txtFechaFin" runat="server" 
                            CssClass="form-control" TextMode="Date" />
                    </div>
                    <div class="col-md-4">
                        <asp:Button ID="btnBuscar" runat="server" 
                            CssClass="btn btn-kawki-primary w-100"
                            Text="🔍 Buscar"
                            OnClick="btnBuscar_Click" />
                        <asp:Button ID="btnLimpiar" runat="server" 
                            CssClass="btn btn-kawki-outline w-100 mt-2"
                            Text="Limpiar filtros"
                            OnClick="btnLimpiar_Click" 
                            CausesValidation="false" />
                    </div>
                </div>
            </div>
        </div>
        -->
        <!-- Tabla de ventas -->
        <div class="card-kawki">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="card-title mb-0">
                    <i class="fas fa-receipt"></i> Listado de Mis Ventas
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
                        <asp:BoundField DataField="Fecha" HeaderText="Fecha" DataFormatString="{0:dd/MM/yyyy HH:mm}" />
                        <asp:BoundField DataField="Cliente" HeaderText="Cliente" />
                        <asp:BoundField DataField="Canal" HeaderText="Canal" />
                        <asp:BoundField DataField="CantidadProductos" HeaderText="Cant. Productos" />
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
                            <p class="text-muted-small">No tienes ventas registradas en este rango de fechas.</p>
                        </div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>

        <!-- Modal de detalle (simulado con panel) -->
        <asp:Panel ID="pnlDetalle" runat="server" Visible="false" CssClass="card-kawki">
            <div class="card-header">
                <h5 class="card-title mb-0">
                    <i class="fas fa-info-circle"></i> Detalle de Venta #<asp:Label ID="lblIdVentaDetalle" runat="server" />
                </h5>
            </div>
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Cliente:</strong> <asp:Label ID="lblClienteDetalle" runat="server" />
                    </div>
                    <div class="col-md-6">
                        <strong>Teléfono:</strong> <asp:Label ID="lblTelefonoDetalle" runat="server" />
                    </div>
                    <div class="col-md-6">
                        <strong>Fecha:</strong> <asp:Label ID="lblFechaDetalle" runat="server" />
                    </div>
                    <div class="col-md-6">
                        <strong>Canal:</strong> <asp:Label ID="lblCanalDetalle" runat="server" />
                    </div>
                    <div class="col-md-12">
                        <strong>Dirección:</strong> <asp:Label ID="lblDireccionDetalle" runat="server" />
                    </div>
                </div>

                <h6 class="mb-2">Productos:</h6>
                <asp:GridView ID="gvDetalleProductos" runat="server" 
                    AutoGenerateColumns="False"
                    CssClass="table table-historial"
                    GridLines="None">
                    <Columns>
                        <asp:BoundField DataField="Producto" HeaderText="Producto" />
                        <asp:BoundField DataField="Cantidad" HeaderText="Cantidad" />
                        <asp:BoundField DataField="PrecioUnitario" HeaderText="P. Unitario" DataFormatString="{0:C}" />
                        <asp:BoundField DataField="Subtotal" HeaderText="Subtotal" DataFormatString="{0:C}" />
                    </Columns>
                </asp:GridView>

                <div class="mt-3 text-end">
                    <strong>Total:</strong> 
                    <span class="badge badge-monto" style="font-size: 16px;">
                        <asp:Label ID="lblTotalDetalle" runat="server" />
                    </span>
                </div>

                <div class="mt-3">
                    <asp:Button ID="btnCerrarDetalle" runat="server" 
                        CssClass="btn btn-kawki-outline"
                        Text="Cerrar"
                        OnClick="btnCerrarDetalle_Click"
                        CausesValidation="false" />
                </div>
            </div>
        </asp:Panel>
    </div>
</asp:Content>
