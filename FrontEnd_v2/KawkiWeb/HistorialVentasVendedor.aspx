<%@ Page Title="Mis Ventas" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="HistorialVentasVendedor.aspx.cs" Inherits="KawkiWeb.HistorialVentasVendedor" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/historialventavendedor.css" rel="stylesheet" />
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
