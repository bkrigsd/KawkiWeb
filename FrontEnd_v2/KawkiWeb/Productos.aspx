<%@ Page Title="Productos" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Productos.aspx.cs" Inherits="KawkiWeb.Productos" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/producto.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="productos-container">
        <!-- Header -->
        <div class="productos-header">
            <h1>Catálogo de Productos</h1>
            <p>Explora nuestra colección de calzado de alta calidad</p>
        </div>

        <!-- Controles de filtrado y búsqueda -->
        <div class="productos-controles">
            <h3>Filtrar productos</h3>

            <div class="filtro-group">
                <label for="ddlCategoria">Categoría</label>
                <asp:DropDownList ID="ddlCategoria" runat="server" CssClass="form-select">
                </asp:DropDownList>
            </div>
        
            <div class="filtro-group">
                <label for="ddlEstilo">Estilo</label>
                <asp:DropDownList ID="ddlEstilo" runat="server" CssClass="form-select">
                </asp:DropDownList>
            </div>
        
            <div class="filtro-group">
                <label for="ddlColor">Color</label>
                <asp:DropDownList ID="ddlColor" runat="server" CssClass="form-select">
                </asp:DropDownList>
            </div>
        
            <div class="filtro-group">
                <label for="ddlTalla">Talla</label>
                <asp:DropDownList ID="ddlTalla" runat="server" CssClass="form-select">
                </asp:DropDownList>
            </div>

                <div class="filtro-group">
                    <label for="txtBuscar">Buscar</label>
                    <asp:TextBox ID="txtBuscar" runat="server" placeholder="Nombre del producto..." CssClass="form-control"></asp:TextBox>
                </div>
            </div>

            <div class="acciones-filtros">
                <asp:Button ID="btnBuscar" runat="server" Text="Buscar" CssClass="btn-buscar" OnClick="btnBuscar_Click" />
                <asp:Button ID="btnLimpiar" runat="server" Text="Limpiar filtros" CssClass="btn-limpiar" OnClick="btnLimpiar_Click" />
            </div>
        </div>

        <!-- Información de resultados -->
        <div class="resultados-info">
            <asp:Label ID="lblResultados" runat="server" Text="12 productos encontrados"></asp:Label>
        </div>

        <!-- Grid de productos -->
        <div class="productos-grid">
            <asp:Repeater ID="rptProductos" runat="server">
                <ItemTemplate>
                    <asp:LinkButton ID="lnkProducto" runat="server" 
                        CssClass="producto-link"
                        CommandArgument='<%# string.Format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}", 
                            Eval("ProductoId"), 
                            Eval("Color"), 
                            Eval("TallasDisponibles"), 
                            Eval("Stock"),
                            Eval("StockMinimo"),
                            Eval("ImagenUrl"),
                            Eval("Nombre"),
                            Eval("Precio"),
                            Eval("Descripcion"),
                            Eval("Categoria")) %>'
                        OnClick="lnkProducto_Click">
                        
                        <div class="producto-card">

                            <asp:Image ID="imgProducto" runat="server" 
                                ImageUrl='<%# Eval("ImagenUrl") %>' 
                                AlternateText='<%# Eval("Nombre") %>'
                                CssClass="producto-imagen" />
                            
                            <div class="producto-info">
                                <div class="producto-categoria"><%# Eval("Categoria") %></div>
                                <h3 class="producto-nombre"><%# Eval("Nombre") %></h3>
                                <p class="producto-descripcion"><%# Eval("Descripcion") %></p>
                                
                                <div class="producto-tallas">
                                    <span><i class="fas fa-shoe-prints"></i> Tallas:</span>
                                    <%# MostrarTallas(Eval("TallasDisponibles").ToString()) %>
                                </div>

                                <!-- Alerta de stock bajo por tallas -->
                                <%# MostrarAlertaStockBajo(
                                    Eval("TallasDisponibles").ToString(), 
                                    Eval("Stock").ToString(),
                                    Eval("StockMinimo").ToString()) %>

                                <div class="producto-precio">S/ <%# Eval("Precio", "{0:N2}") %></div>
                            </div>
                        </div>
                    </asp:LinkButton>
                </ItemTemplate>
            </asp:Repeater>
        </div>

        <!-- Mensaje cuando no hay productos -->
        <asp:Panel ID="pnlSinProductos" runat="server" Visible="false" CssClass="sin-productos">
            <i class="fas fa-box-open"></i>
            <h3>No se encontraron productos</h3>
            <p>Intenta ajustar los filtros de búsqueda</p>
        </asp:Panel>
    </div>
</asp:Content>
