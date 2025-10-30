<%@ Page Title="Carrito" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Carrito.aspx.cs" Inherits="KawkiWeb.Carrito" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        /* --- Tabla de carrito con Grid --- */
        .cart-grid { display: grid; grid-template-columns: 52px 1fr 120px 160px 140px 90px; row-gap: 10px; }
        .cart-head, .cart-row { align-items: center; }
        .cart-head { color:#6b7280; font-weight:600; border-bottom:1px solid #eee; padding:10px 8px; }
        .cart-row { border-bottom:1px dashed #eee; padding:12px 8px; }
        .cell-num { text-align:center; color:#737373; }
        .cell-price, .cell-subtotal { text-align:right; font-weight:600; }
        .prod-title { font-weight:700; line-height:1.1; }
        .prod-sku { font-size:.88rem; color:#6b7280; }
        .qty-wrap { display:flex; justify-content:center; }
        .qty-wrap .input-group { width:120px; }
        .row-last { border-bottom:none; }
        .k-card { background:#fff; border:1px solid #eee; border-radius:16px; box-shadow:0 8px 24px rgba(0,0,0,.05); }
        .k-card-h { padding:18px 20px; border-bottom:1px solid #f1f1f1; font-weight:700; }
        .k-card-b { padding:18px 20px; }
        .page-wrap { padding:18px 14px 32px; }
        .badge-soft { background:#ffe8eb; color:#e35a6d; padding:.25rem .6rem; border-radius:999px; font-weight:600; }
        /* Responsive: pila en móvil */
        @media (max-width:992px){
            .cart-grid{ grid-template-columns: 44px 1fr; }
            .cell-price, .cell-subtotal, .cell-actions, .qty-wrap{ justify-self:end; }
            .cell-price, .cell-subtotal{ font-size:1rem; }
            .qty-wrap{ margin-top:6px; }
        }
    </style>
</asp:Content>

<asp:Content ID="Body" ContentPlaceHolderID="MainContent" runat="server">
    <div class="page-wrap">
        <div class="d-flex align-items-center gap-2 mb-3">
            <h4 class="m-0">Carrito</h4>
            <span class="badge-soft"><asp:Label ID="lblCount" runat="server" Text="0"></asp:Label> ítems</span>
        </div>

        <!-- Carrito vacío -->
        <asp:Panel ID="pnlVacio" runat="server" CssClass="k-card mb-4">
            <div class="k-card-b text-center" style="padding:56px 18px;">
                <p class="text-muted mb-3">Tu carrito está vacío.</p>
                <asp:HyperLink runat="server" NavigateUrl="~/Productos.aspx" CssClass="btn btn-danger px-4">
                    <i class="bi bi-box-seam"></i> Ir a Productos
                </asp:HyperLink>
            </div>
        </asp:Panel>

        <!-- Carrito con ítems -->
        <asp:Panel ID="pnlConItems" runat="server" Visible="false">
            <div class="row g-4">
                <!-- Lista -->
                <div class="col-lg-8">
                    <div class="k-card">
                        <div class="k-card-h">Productos</div>
                        <div class="k-card-b">

                            <!-- Encabezado -->
                            <div class="cart-grid cart-head d-none d-lg-grid">
                                <div class="cell-num">#</div>
                                <div>Producto</div>
                                <div class="text-end">Precio</div>
                                <div class="text-center">Cantidad</div>
                                <div class="text-end">Subtotal</div>
                                <div class="text-end">Acciones</div>
                            </div>

                            <!-- Filas -->
                            <asp:Repeater ID="rpCarrito" runat="server" OnItemCommand="rpCarrito_ItemCommand">
                                <ItemTemplate>
                                    <div class="cart-grid cart-row <%# (Container.ItemIndex == ((Repeater)Container.NamingContainer).Items.Count-1) ? "row-last" : "" %>">
                                        <!-- # -->
                                        <div class="cell-num"><%# Container.ItemIndex + 1 %></div>

                                        <!-- Producto -->
                                        <div>
                                            <div class="prod-title"><%# Eval("Nombre") %></div>
                                            <div class="prod-sku">SKU: <%# Eval("Sku") %></div>
                                        </div>

                                        <!-- Precio -->
                                        <div class="cell-price">S/. <%# (Convert.ToDecimal(Eval("Precio"))).ToString("N2") %></div>

                                        <!-- Cantidad -->
                                        <div class="qty-wrap">
                                            <div class="input-group input-group-sm">
                                                <asp:LinkButton runat="server" CommandName="menos" CommandArgument='<%# Eval("Id") %>'
                                                    CssClass="btn btn-outline-secondary"><i class="bi bi-dash"></i></asp:LinkButton>
                                                <asp:TextBox ID="txtQty" runat="server" Text='<%# Eval("Cantidad") %>'
                                                    CssClass="form-control text-center" />
                                                <asp:LinkButton runat="server" CommandName="mas" CommandArgument='<%# Eval("Id") %>'
                                                    CssClass="btn btn-outline-secondary"><i class="bi bi-plus"></i></asp:LinkButton>
                                            </div>
                                        </div>

                                        <!-- Subtotal -->
                                        <div class="cell-subtotal">S/
                                            <%# (Convert.ToDecimal(Eval("Cantidad")) * Convert.ToDecimal(Eval("Precio"))).ToString("N2") %>
                                        </div>

                                        <!-- Acciones -->
                                        <div class="cell-actions text-end">
                                            <asp:LinkButton runat="server" CommandName="eliminar" CommandArgument='<%# Eval("Id") %>'
                                                CssClass="btn btn-link text-danger p-0 small">
                                                <i class="bi bi-x-lg"></i> Eliminar
                                            </asp:LinkButton>
                                        </div>
                                    </div>
                                </ItemTemplate>
                            </asp:Repeater>

                            <div class="mt-3 p-3 rounded-3" style="background:#f9fafb;border:1px dashed #e5e7eb;color:#6b7280;">
                                <i class="bi bi-info-circle"></i> Envío estándar gratis por compras mayores a S/ 5000.
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Resumen -->
                <div class="col-lg-4">
                    <div class="k-card" style="position:sticky; top:16px;">
                        <div class="k-card-h">Resumen</div>
                        <div class="k-card-b">
                            <div class="mb-3">
                                <label class="form-label small text-muted">Cupón de descuento</label>
                                <div class="input-group">
                                    <asp:TextBox ID="txtCupon" runat="server" CssClass="form-control" placeholder="EJ: PATITOLINUX" />
                                    <asp:LinkButton ID="btnAplicarCupon" runat="server" CssClass="btn btn-outline-secondary"
                                        OnClick="btnAplicarCupon_Click">Aplicar</asp:LinkButton>
                                </div>
                                <asp:Label ID="lblCuponMsg" runat="server" CssClass="small d-block mt-1 text-success" />
                            </div>

                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Subtotal</span><asp:Label ID="lblSubtotal" runat="server" CssClass="fw-semibold"></asp:Label>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Descuento</span><asp:Label ID="lblDescuento" runat="server" Text="S/. 0.00"></asp:Label>
                            </div>
                            <div class="d-flex justify-content-between">
                                <span class="text-muted">Envío</span><asp:Label ID="lblEnvio" runat="server" Text="S/. 0.00"></asp:Label>
                            </div>
                            <hr />
                            <div class="d-flex justify-content-between fs-5 fw-bold">
                                <span>Total</span><asp:Label ID="lblTotal" runat="server"></asp:Label>
                            </div>

                            <asp:HyperLink ID="btnSeguir" runat="server" NavigateUrl="~/Productos.aspx"
                                CssClass="btn btn-outline-secondary w-100 mt-3">
                                <i class="bi bi-arrow-left"></i> Seguir comprando
                            </asp:HyperLink>
                            <asp:LinkButton ID="btnPagar" runat="server" CssClass="btn btn-danger w-100 mt-2"
                                OnClick="btnPagar_Click">
                                <i class="bi bi-credit-card-2-front"></i> Ir a pagar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>
        </asp:Panel>
    </div>
</asp:Content>
