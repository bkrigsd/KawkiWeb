<%@ Page Title="" Language="C#" MasterPageFile="~/KawkiWeb.Master" AutoEventWireup="true" CodeBehind="DetalleProducto.aspx.cs" Inherits="KawkiWeb.DetalleProducto" %>
<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        .detalle-container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .breadcrumb {
            display: flex;
            gap: 8px;
            align-items: center;
            margin-bottom: 25px;
            font-size: 14px;
            color: #666;
        }

        .breadcrumb a {
            color: #666;
            text-decoration: none;
            transition: color 0.3s;
        }

        .breadcrumb a:hover {
            color: var(--primary-color);
        }

        .breadcrumb .active {
            color: var(--primary-color);
            font-weight: 500;
        }

        .producto-detalle {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 50px;
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        }

        /* Columna izquierda - Imagen */
        .producto-imagen-principal {
            position: relative;
        }

        .imagen-container {
            width: 100%;
            height: 500px;
            background: #f8f9fa;
            border-radius: 12px;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .imagen-container img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
        }

        .producto-badge {
            position: absolute;
            top: 20px;
            left: 20px;
            background: var(--primary-color);
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }

        /* Columna derecha - Información */
        .producto-info-detalle {
            display: flex;
            flex-direction: column;
        }

        .producto-categoria-tag {
            color: var(--primary-color);
            font-size: 13px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 10px;
        }

        .producto-titulo {
            font-size: 32px;
            font-weight: 600;
            color: #333;
            margin-bottom: 15px;
            line-height: 1.3;
        }

        .producto-precio-grande {
            font-size: 36px;
            font-weight: 700;
            color: var(--primary-color);
            margin-bottom: 25px;
        }

        .producto-descripcion-completa {
            color: #666;
            font-size: 15px;
            line-height: 1.7;
            margin-bottom: 30px;
            padding-bottom: 25px;
            border-bottom: 1px solid #e0e0e0;
        }

        /* Selector de tallas */
        .selector-tallas {
            margin-bottom: 25px;
        }

        .selector-tallas-label {
            font-size: 15px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
            display: block;
        }

        .tallas-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }

        .tallas-grid label {
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            min-width: 60px;
            padding: 12px 16px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 500;
            color: #333;
            cursor: pointer;
            transition: all 0.3s;
            background: white;
        }

        .tallas-grid input[type="radio"] {
            position: absolute;
            opacity: 0;
            width: 0;
            height: 0;
        }

        .tallas-grid input[type="radio"]:checked + label,
        .tallas-grid label:has(input[type="radio"]:checked) {
            border-color: var(--primary-color);
            background: #fff5f7;
            color: var(--primary-color);
            transform: scale(1.05);
        }

        .tallas-grid label:hover {
            border-color: var(--primary-color);
            background: #fff5f7;
        }

        /* Selector de cantidad */
        .selector-cantidad {
            margin-bottom: 30px;
        }

        .selector-cantidad-label {
            font-size: 15px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
            display: block;
        }

        .cantidad-controls {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .cantidad-input-group {
            display: flex;
            align-items: center;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
        }

        .btn-cantidad {
            background: #f8f9fa;
            border: none;
            padding: 10px 18px;
            font-size: 18px;
            font-weight: 600;
            color: #333;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-cantidad:hover {
            background: var(--primary-color);
            color: white;
        }

        .btn-cantidad:active {
            transform: scale(0.95);
        }

        .cantidad-display {
            padding: 10px 25px;
            font-size: 16px;
            font-weight: 600;
            color: #333;
            min-width: 60px;
            text-align: center;
            border-left: 1px solid #e0e0e0;
            border-right: 1px solid #e0e0e0;
        }

        .stock-disponible {
            font-size: 13px;
            color: #28a745;
            font-weight: 500;
        }

        .stock-bajo {
            color: #ffc107;
        }

        /* Botones de acción */
        .acciones-producto {
            display: flex;
            gap: 15px;
            margin-bottom: 25px;
        }

        .btn-agregar-carrito {
            flex: 1;
            padding: 16px 30px;
            background: var(--primary-color);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-agregar-carrito:hover {
            background: #d85769;
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(237, 107, 127, 0.4);
        }

        .btn-comprar-ahora {
            flex: 1;
            padding: 16px 30px;
            background: #333;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-comprar-ahora:hover {
            background: #000;
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
        }

        /* Mensaje de validación */
        .mensaje-validacion {
            padding: 12px 16px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 14px;
            display: none;
        }

        .mensaje-validacion.error {
            background: #fff3cd;
            border: 1px solid #ffc107;
            color: #856404;
            display: block;
        }

        .mensaje-validacion.exito {
            background: #d4edda;
            border: 1px solid #28a745;
            color: #155724;
            display: block;
        }

        /* Responsive */
        @media (max-width: 992px) {
            .producto-detalle {
                grid-template-columns: 1fr;
                gap: 30px;
                padding: 30px 20px;
            }

            .imagen-container {
                height: 400px;
            }

            .producto-titulo {
                font-size: 26px;
            }

            .producto-precio-grande {
                font-size: 30px;
            }

            .acciones-producto {
                flex-direction: column;
            }
        }

        @media (max-width: 576px) {
            .detalle-container {
                padding: 0 10px;
            }

            .tallas-grid {
                grid-template-columns: repeat(3, 1fr);
            }
        }
    </style>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="detalle-container">
        <!-- Breadcrumb -->
        <div class="breadcrumb">
            <a href="Inicio.aspx">Inicio</a>
            <i class="fas fa-chevron-right"></i>
            <a href="Productos.aspx">Productos</a>
            <i class="fas fa-chevron-right"></i>
            <span class="active">
                <asp:Label ID="lblBreadcrumb" runat="server"></asp:Label>
            </span>
        </div>

        <!-- Mensaje de validación -->
        <asp:Panel ID="pnlMensaje" runat="server" CssClass="mensaje-validacion" Visible="false">
            <asp:Label ID="lblMensaje" runat="server"></asp:Label>
        </asp:Panel>

        <!-- Detalle del producto -->
        <div class="producto-detalle">
            <!-- Columna izquierda: Imagen -->
            <div class="producto-imagen-principal">
                <div class="producto-badge">
                    <asp:Label ID="lblCategoriaBadge" runat="server"></asp:Label>
                </div>
                <div class="imagen-container">
                    <asp:Image ID="imgProductoPrincipal" runat="server" AlternateText="Producto" />
                </div>
            </div>

            <!-- Columna derecha: Información -->
            <div class="producto-info-detalle">
                <div class="producto-categoria-tag">
                    <asp:Label ID="lblCategoriaTag" runat="server"></asp:Label>
                </div>

                <h1 class="producto-titulo">
                    <asp:Label ID="lblNombreProducto" runat="server"></asp:Label>
                </h1>

                <div class="producto-precio-grande">
                    S/ <asp:Label ID="lblPrecio" runat="server"></asp:Label>
                </div>

                <p class="producto-descripcion-completa">
                    <asp:Label ID="lblDescripcion" runat="server"></asp:Label>
                </p>

                <!-- Selector de tallas -->
                <div class="selector-tallas">
                    <label class="selector-tallas-label">
                        Selecciona tu talla <span style="color: #dc3545;">*</span>
                    </label>
                    <asp:RadioButtonList ID="rblTallas" runat="server" 
                        RepeatDirection="Horizontal" 
                        RepeatLayout="Flow"
                        CssClass="tallas-grid">
                    </asp:RadioButtonList>
                </div>

                <!-- Selector de cantidad -->
                <div class="selector-cantidad">
                    <label class="selector-cantidad-label">
                        Cantidad <span style="color: #dc3545;">*</span>
                    </label>
    
                    <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
    
                    <asp:UpdatePanel ID="upCantidad" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <div class="cantidad-controls">
                                <div class="cantidad-input-group">
                                    <asp:Button ID="btnDisminuir" runat="server" Text="−" 
                                        CssClass="btn-cantidad" OnClick="btnDisminuir_Click" />
                                    <asp:Label ID="lblCantidad" runat="server" Text="1" CssClass="cantidad-display"></asp:Label>
                                    <asp:Button ID="btnAumentar" runat="server" Text="+" 
                                        CssClass="btn-cantidad" OnClick="btnAumentar_Click" />
                                </div>
                                <span class="stock-disponible">
                                    <i class="fas fa-check-circle"></i>
                                    <asp:Label ID="lblStock" runat="server"></asp:Label>
                                </span>
                            </div>
            
                            <!-- Mensaje de validación dentro del UpdatePanel -->
                            <asp:Panel ID="pnlMensajeCantidad" runat="server" CssClass="mensaje-validacion" Visible="false" style="margin-top: 10px;">
                                <asp:Label ID="lblMensajeCantidad" runat="server"></asp:Label>
                            </asp:Panel>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>

                <!-- Botones de acción -->
                <div class="acciones-producto">
                    <asp:Button ID="btnAgregarCarrito" runat="server" 
                        CssClass="btn-agregar-carrito" 
                        Text="Agregar al Carrito"
                        OnClick="btnAgregarCarrito_Click">
                    </asp:Button>
                    
                    <asp:Button ID="btnComprarAhora" runat="server" 
                        CssClass="btn-comprar-ahora" 
                        Text="Comprar Ahora"
                        OnClick="btnComprarAhora_Click">
                    </asp:Button>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Almacenar la cantidad en ViewState cuando cambia
        function actualizarCantidad(cantidad) {
            document.getElementById('<%= hdnCantidad.ClientID %>').value = cantidad;
        }
    </script>

    <!-- Campo oculto para mantener la cantidad -->
    <asp:HiddenField ID="hdnCantidad" runat="server" Value="1" />
</asp:Content>
