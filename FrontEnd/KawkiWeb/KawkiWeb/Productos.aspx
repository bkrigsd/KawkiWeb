<%@ Page Title="Productos" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Productos.aspx.cs" Inherits="KawkiWeb.Productos" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        .productos-container {
            padding: 20px;
            background-color: #f8f9fa;
            min-height: calc(100vh - 60px);
        }

        .productos-header {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }

        .productos-header h1 {
            color: #333;
            font-size: 24px;
            font-weight: 500;
            margin-bottom: 5px;
        }

        .productos-header p {
            color: #666;
            margin: 0;
            font-size: 14px;
        }

        .productos-controles {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }

        .productos-controles h3 {
            margin-bottom: 15px;
            color: #444;
            font-size: 16px;
            font-weight: 500;
        }

        .filtros-section {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
            gap: 15px;
            margin-bottom: 0;
        }

        .filtro-group {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .filtro-group label {
            font-size: 13px;
            color: #666;
            font-weight: 500;
        }

        .filtro-group select,
        .filtro-group input {
            padding: 8px 10px;
            border: 1px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .filtro-group select:focus,
        .filtro-group input:focus {
            outline: none;
            border-color: var(--primary-color);
        }

        .acciones-filtros {
            margin-top: 15px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .btn-buscar {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 8px 24px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            transition: all 0.3s;
        }

        .btn-buscar:hover {
            background-color: #d85769;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(237, 107, 127, 0.3);
        }

        .btn-limpiar {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 8px 24px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            transition: all 0.3s;
        }

        .btn-limpiar:hover {
            background-color: #5a6268;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(108, 117, 125, 0.3);
        }

        .resultados-info {
            background-color: white;
            padding: 12px 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }

        .resultados-info label {
            color: #666;
            font-size: 14px;
            margin: 0;
        }

        .productos-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .producto-card {
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            transition: transform 0.3s, box-shadow 0.3s;
            display: flex;
            flex-direction: column;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
        }

        .producto-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }

        .producto-link {
            background: none;
            border: none;
            padding: 0;
            text-align: left;
            width: 100%;
            font-family: inherit;
            cursor: pointer;
            text-decoration: none !important;
        }
        
        .producto-link:hover,
        .producto-link:focus,
        .producto-link:active {
            text-decoration: none !important;
            outline: none;
        }

        .producto-imagen {
            width: 100%;
            height: 240px;
            object-fit: cover;
            background-color: #f0f0f0;
        }

        .producto-info {
            padding: 15px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .producto-categoria {
            color: var(--primary-color);
            font-size: 11px;
            font-weight: 600;
            text-transform: uppercase;
            margin-bottom: 6px;
            letter-spacing: 0.5px;
        }

        .producto-nombre {
            font-size: 16px;
            font-weight: 500;
            color: #333;
            margin-bottom: 6px;
            line-height: 1.3;
        }

        .producto-descripcion {
            color: #666;
            font-size: 13px;
            margin-bottom: 10px;
            line-height: 1.5;
            flex-grow: 1;
            height: 60px;
            overflow: hidden;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
        }

        .producto-tallas {
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
            margin-bottom: 12px;
            align-items: center;
        }

        .producto-tallas span:first-child {
            font-size: 12px;
            color: #666;
            font-weight: 500;
            margin-right: 3px;
        }

        .producto-talla,
        .producto-talla-rango {
            display: inline-block;
            background-color: #f8f9fa;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 11px;
            color: #555;
            border: 1px solid #e0e0e0;
            font-weight: normal;
        }

        .producto-precio {
            font-size: 20px;
            font-weight: 600;
            color: var(--primary-color);
            margin-bottom: 0;
        }

        .sin-productos {
            text-align: center;
            padding: 50px 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .sin-productos i {
            font-size: 56px;
            color: #e0e0e0;
            margin-bottom: 15px;
        }

        .sin-productos h3 {
            font-size: 20px;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }

        .sin-productos p {
            font-size: 14px;
            color: #666;
        }

        @media (max-width: 768px) {
            .productos-container {
                padding: 15px;
            }

            .productos-header {
                padding: 15px;
            }

            .productos-header h1 {
                font-size: 20px;
            }

            .productos-controles {
                padding: 15px;
            }

            .filtros-section {
                grid-template-columns: 1fr;
            }

            .productos-grid {
                grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
                gap: 15px;
            }

            .btn-buscar,
            .btn-limpiar {
                width: 100%;
            }
        }
    </style>
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

            <div class="filtros-section">
                <div class="filtro-group">
                    <label for="ddlCategoria">Categoría</label>
                    <asp:DropDownList ID="ddlCategoria" runat="server" CssClass="form-select">
                        <asp:ListItem Value="">Todas</asp:ListItem>
                        <asp:ListItem Value="oxford">Oxford</asp:ListItem>
                        <asp:ListItem Value="derby">Derby</asp:ListItem>
                    </asp:DropDownList>
                </div>

                <div class="filtro-group">
                    <label for="ddlEstilo">Estilo</label>
                    <asp:DropDownList ID="ddlEstilo" runat="server" CssClass="form-select">
                        <asp:ListItem Value="">Todos</asp:ListItem>
                        <asp:ListItem Value="charol">Charol</asp:ListItem>
                        <asp:ListItem Value="clasico">Clásico</asp:ListItem>
                        <asp:ListItem Value="metalizado">Metalizado</asp:ListItem>
                        <asp:ListItem Value="combinado">Combinado</asp:ListItem>
                    </asp:DropDownList>
                </div>

                <div class="filtro-group">
                    <label for="ddlColor">Color</label>
                    <asp:DropDownList ID="ddlColor" runat="server" CssClass="form-select">
                        <asp:ListItem Value="">Todos</asp:ListItem>
                        <asp:ListItem Value="negro">Negro</asp:ListItem>
                        <asp:ListItem Value="marron">Marrón</asp:ListItem>
                        <asp:ListItem Value="beige">Beige</asp:ListItem>
                        <asp:ListItem Value="blanco">Blanco</asp:ListItem>
                        <asp:ListItem Value="camel">Camel</asp:ListItem>
                        <asp:ListItem Value="crema">Crema</asp:ListItem>
                        <asp:ListItem Value="azul">Azul</asp:ListItem>
                        <asp:ListItem Value="rojo">Rojo</asp:ListItem>
                    </asp:DropDownList>
                </div>

                <div class="filtro-group">
                    <label for="ddlTalla">Talla</label>
                    <asp:DropDownList ID="ddlTalla" runat="server" CssClass="form-select">
                        <asp:ListItem Value="">Todas</asp:ListItem>
                        <asp:ListItem Value="35">35</asp:ListItem>
                        <asp:ListItem Value="36">36</asp:ListItem>
                        <asp:ListItem Value="37">37</asp:ListItem>
                        <asp:ListItem Value="38">38</asp:ListItem>
                        <asp:ListItem Value="39">39</asp:ListItem>
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
                        CommandArgument='<%# Eval("ProductoId") %>'
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
