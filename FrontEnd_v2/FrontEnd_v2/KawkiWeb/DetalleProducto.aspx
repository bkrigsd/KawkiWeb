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

        /* SKU del producto */
        .producto-sku-detalle {
            font-size: 14px;
            color: #666;
            font-weight: 600;
            font-family: 'Courier New', monospace;
            margin-bottom: 15px;
            padding: 10px 15px;
            background: #f8f9fa;
            border-radius: 6px;
            display: inline-block;
            border: 2px solid #e0e0e0;
        }

        .producto-sku-detalle i {
            color: var(--primary-color);
            margin-right: 8px;
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

        /* Selector de colores */
        .selector-colores {
            margin-bottom: 25px;
        }

        .selector-colores-label {
            font-size: 15px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
            display: block;
        }

        .colores-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }

        .colores-grid label {
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            min-width: 100px;
            padding: 12px 20px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 500;
            color: #333;
            cursor: pointer;
            transition: all 0.3s;
            background: white;
        }

        .colores-grid input[type="radio"] {
            position: absolute;
            opacity: 0;
            width: 0;
            height: 0;
        }

        .colores-grid input[type="radio"]:checked + label,
        .colores-grid label:has(input[type="radio"]:checked) {
            border-color: var(--primary-color);
            background: #fff5f7;
            color: var(--primary-color);
            transform: scale(1.05);
        }

        .colores-grid label:hover {
            border-color: var(--primary-color);
            background: #fff5f7;
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

        /* Tallas agotadas - en gris y deshabilitadas */
        .tallas-grid label.agotado {
            background: #f5f5f5;
            color: #999;
            border-color: #ddd;
            cursor: not-allowed;
            opacity: 0.6;
        }

        .tallas-grid label.agotado:hover {
            background: #f5f5f5;
            border-color: #ddd;
            transform: none;
        }

        .tallas-grid input[type="radio"] {
            position: absolute;
            opacity: 0;
            width: 0;
            height: 0;
        }

        .tallas-grid input[type="radio"]:disabled + label {
            background: #f5f5f5;
            color: #999;
            border-color: #ddd;
            cursor: not-allowed;
            opacity: 0.6;
        }

        .tallas-grid input[type="radio"]:checked:not(:disabled) + label,
        .tallas-grid label:has(input[type="radio"]:checked:not(:disabled)) {
            border-color: var(--primary-color);
            background: #fff5f7;
            color: var(--primary-color);
            transform: scale(1.05);
        }

        .tallas-grid label:not(.agotado):hover {
            border-color: var(--primary-color);
            background: #fff5f7;
        }

        /* Info de stock */
        .info-stock-container {
            padding: 20px;
            background: #f8f9fa;
            border-radius: 8px;
            margin-bottom: 25px;
        }

        .info-stock-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #e0e0e0;
        }

        .info-stock-item:last-child {
            border-bottom: none;
        }

        .info-stock-label {
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }

        .info-stock-value {
            font-size: 16px;
            font-weight: 600;
            color: #333;
        }

        /* Alerta de stock */
        .stock-alert-detalle {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 12px;
            font-size: 15px;
            font-weight: 600;
        }

        .stock-alert-detalle i {
            font-size: 20px;
        }

        .stock-disponible-detalle {
            background: #d4edda;
            border: 2px solid #28a745;
            color: #155724;
        }

        .stock-bajo-detalle {
            background: #fff3cd;
            border: 2px solid #ffc107;
            color: #856404;
        }

        .stock-agotado-detalle {
            background: #f8d7da;
            border: 2px solid #dc3545;
            color: #721c24;
        }

        /* Mensaje de validación */
        .mensaje-validacion {
            padding: 12px 16px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 14px;
            display: none;
        }

        .mensaje-validacion.info {
            background: #d1ecf1;
            border: 1px solid #17a2b8;
            color: #0c5460;
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
            <a href="Productos.aspx">Productos</a>
            <i class="fas fa-chevron-right"></i>
            <span class="active">
                <asp:Label ID="lblBreadcrumb" runat="server"></asp:Label>
            </span>
        </div>

        <!-- Mensaje informativo -->
        <asp:Panel ID="pnlMensaje" runat="server" CssClass="mensaje-validacion info" Visible="false">
            <i class="fas fa-info-circle"></i>
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
                    <asp:Label ID="lblPrecio" runat="server"></asp:Label>
                </div>

                <p class="producto-descripcion-completa">
                    <asp:Label ID="lblDescripcion" runat="server"></asp:Label>
                </p>

                <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>

                <asp:UpdatePanel ID="upVariantes" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <!-- SKU de la variante seleccionada -->
                        <div class="producto-sku-detalle">
                            <i class="fas fa-barcode"></i>
                            SKU: <asp:Label ID="lblSKU" runat="server" Text="Selecciona color y talla"></asp:Label>
                        </div>
                        <!-- Selector de tallas -->
                        <div class="selector-tallas">
                            <label class="selector-tallas-label">
                                Talla <span style="color: #dc3545;">*</span>
                            </label>
                            <asp:RadioButtonList ID="rblTallas" runat="server" 
                                RepeatDirection="Horizontal" 
                                RepeatLayout="Flow"
                                CssClass="tallas-grid"
                                AutoPostBack="true"
                                OnSelectedIndexChanged="rblTallas_SelectedIndexChanged">
                            </asp:RadioButtonList>
                        </div>

                        <!-- Información de stock de la variante seleccionada -->
                        <asp:Panel ID="pnlInfoStock" runat="server" Visible="false">
                            <!-- Alerta de stock -->
                            <asp:Panel ID="pnlStockAlert" runat="server" CssClass="stock-alert-detalle">
                                <i class="fas fa-box"></i>
                                <asp:Label ID="lblStockAlert" runat="server"></asp:Label>
                            </asp:Panel>

                            <!-- Detalles de stock -->
                            <div class="info-stock-container">
                                <div class="info-stock-item">
                                    <span class="info-stock-label">SKU:</span>
                                    <span class="info-stock-value">
                                        <asp:Label ID="lblSKUVariante" runat="server"></asp:Label>
                                    </span>
                                </div>
                                <div class="info-stock-item">
                                    <span class="info-stock-label">Talla:</span>
                                    <span class="info-stock-value">
                                        <asp:Label ID="lblTallaSeleccionada" runat="server"></asp:Label>
                                    </span>
                                </div>
                                <div class="info-stock-item">
                                    <span class="info-stock-label">Stock disponible:</span>
                                    <span class="info-stock-value">
                                        <asp:Label ID="lblStockDisponible" runat="server"></asp:Label> unidades
                                    </span>
                                </div>
                            </div>
                        </asp:Panel>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <!-- Campo oculto para mantener el ID de la variante seleccionada -->
    <asp:HiddenField ID="hdnVarianteId" runat="server" />
</asp:Content>
