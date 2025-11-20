<%@ Page Title="Gestión de Variantes" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="GestionVariantes.aspx.cs" Inherits="KawkiWeb.GestionVariantes" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registrouser.css" rel="stylesheet" />
    <style>
    /* Tabla de variantes */
    .tabla-variantes { 
        width: 100%; 
        border-collapse: collapse; 
    }
    .tabla-variantes tr { 
        border-bottom: 1px solid #e0e0e0; 
    }
    .tabla-variantes th { 
        background: #f5f5f5; 
        padding: 12px; 
        text-align: left; 
        font-weight: 600; 
    }
    .tabla-variantes td { 
        padding: 12px; 
        vertical-align: middle; 
    }
    
    /* Imagen del color */
    .img-color { 
        width: 80px; 
        height: 80px; 
        object-fit: cover; 
        border-radius: 8px; 
        box-shadow: 0 2px 4px rgba(0,0,0,0.1); 
    }
    
    /* Badge de alerta */
    .badge-alerta { 
        display: inline-block; 
        padding: 4px 8px; 
        border-radius: 4px; 
        font-size: 12px; 
    }
    .badge-bajo { 
        background: #ffebee; 
        color: #c62828; 
    }

    .badge-agotado { 
        background: #ffcdd2; 
        color: #b71c1c; 
        font-weight: bold;
    }
    
    /* Toggle disponible */
    .toggle-disponible { 
        display: inline-flex; 
        border-radius: 20px; 
        overflow: hidden;
        border: 2px solid #ddd;
    }
    .toggle-disponible button {
        padding: 6px 12px;
        border: none;
        cursor: pointer;
        font-size: 12px;
        font-weight: 600;
        transition: all 0.3s;
        background: #f5f5f5;
        color: #666;
    }
    .toggle-disponible .btn-no.active { 
        background: #e0e0e0;
        color: #333;
    }
    .toggle-disponible .btn-si.active { 
        background: #4caf50;
        color: white;
    }

    /* Control de stock */
    .control-stock {
        margin-bottom: 15px;
        padding: 10px;
        border: 1px solid #e0e0e0;
        border-radius: 4px;
        background: #f9f9f9;
    }
    .control-stock label {
        display: block;
        font-weight: 600;
        margin-bottom: 8px;
        font-size: 14px;
    }
    .control-stock input {
        width: 100%;
        padding: 6px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    
    /* Error de validación */
    .error-validation {
        color: #dc3545;
        font-size: 13px;
        margin-top: 5px;
        display: block;
    }

    /* Botones */
    .btn-agregar {
        background: #28a745;
        color: white;
        border: none;
        padding: 4px 8px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        transition: background 0.3s;
        min-width: 32px;
        height: 32px;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .btn-agregar:hover {
        background: #218838;
    }
    .btn-editar {
        background: #007bff;
        color: white;
        border: none;
        padding: 4px 8px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        transition: background 0.3s;
        margin-right: 5px;
    }
    .btn-editar:hover {
        background: #0056b3;
    }

    </style>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="usuarios-container">
        <!-- Header -->
        <div class="usuarios-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1><i class="fas fa-palette me-2"></i>Variantes de 
                        <asp:Label ID="lblTituloProducto" runat="server" CssClass="text-primary" />
                    </h1>
                    <asp:Label ID="lblProductoInfo" runat="server" CssClass="text-muted fs-5" />
                </div>
                <div>
                    <asp:Button ID="btnVolver" runat="server" CssClass="btn-kawki-outline me-2"
                        Text="← Volver a Productos" OnClick="btnVolver_Click" CausesValidation="false" />
                    <button type="button" class="btn-kawki-primary" onclick="abrirModalRegistro()">
                        <i class="fas fa-plus me-1"></i> Agregar Variante
                    </button>
                </div>
            </div>
        </div>

        <!-- Card con tabla -->
        <div class="card-kawki">
            <div class="card-header">
                <div class="card-title">
                    <i class="fas fa-list"></i>
                    <span>Colores y Tallas</span>
                    <span class="badge bg-secondary ms-2">
                        <asp:Label ID="lblTotalVariantes" runat="server" Text="0" /> color(es)
                    </span>
                </div>
            </div>
            <div class="card-body">
                <asp:Repeater ID="rptColores" runat="server">
                    <HeaderTemplate>
                        <table class="tabla-variantes">
                            <thead>
                                <tr>
                                    <th style="width: 100px;">Imagen</th>
                                    <th style="width: 120px;">Color</th>
                                    <th>Talla</th>
                                    <th style="width: 100px;">Stock</th>
                                    <th style="width: 100px;">Stock Mín.</th>
                                    <th style="width: 100px;">Alerta</th>
                                    <th style="width: 120px;">Disponible</th>
                                    <th style="width: 200px;">Modificaciones</th>
                                </tr>
                            </thead>
                            <tbody>
                    </HeaderTemplate>

                    <ItemTemplate>
                        <%# RenderVariantesConRowspan(Container.DataItem) %>
                    </ItemTemplate>

                    <FooterTemplate>
                            </tbody>
                        </table>
                    </FooterTemplate>
                </asp:Repeater>
            </div>
        </div>

        <!-- Modal de Agregar Variante (Color + Tallas) -->
        <div id="modalVariante" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h5 id="tituloModal"><i class="fas fa-plus me-2"></i>Agregar nueva variante</h5>
                </div>

                <asp:HiddenField ID="hfProductoId" runat="server" />

                <div class="alert alert-info mb-3">
                    <i class="fas fa-box-open me-2"></i>
                    <strong>Producto:</strong> 
                    <asp:Label ID="lblProductoModal" runat="server" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Color *</label>
                    <asp:DropDownList ID="ddlColor" runat="server" CssClass="form-select">
                    </asp:DropDownList>
                    <asp:Label ID="lblErrorColor" runat="server" CssClass="text-danger small d-block mt-1" />
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tallas (separadas por comas) *</label>
                        <asp:TextBox ID="txtTallas" runat="server" CssClass="form-control" 
                            placeholder="35,36,37,38,39" />
                        <asp:Label ID="lblErrorTallas" runat="server" CssClass="text-danger small d-block mt-1" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Stocks (separados por comas) *</label>
                        <asp:TextBox ID="txtStocks" runat="server" CssClass="form-control" 
                            placeholder="50,40,35,30,25" />
                        <asp:Label ID="lblErrorStocks" runat="server" CssClass="text-danger small d-block mt-1" />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Stock Mínimo (separados por comas)</label>
                    <asp:TextBox ID="txtStocksMinimos" runat="server" CssClass="form-control" 
                        placeholder="5,5,5,5,5 (opcional, por defecto: 5)" />
                    <asp:Label ID="lblErrorStocksMinimos" runat="server" CssClass="text-danger small d-block mt-1" />
                </div>

                <div class="mb-3">
                    <label class="form-label">URL de Imagen</label>
                    <div class="input-group">
                        <span class="input-group-text">/Images/Productos/</span>
                        <asp:TextBox ID="txtUrlImagen" runat="server" CssClass="form-control" 
                            placeholder="nombre.jpg" />
                    </div>
                    <small class="text-muted">Ejemplo: producto.jpg o imagen.png</small>
                </div>

                <div class="mb-3">
                    <label class="form-label">Disponible para la venta *</label>
                    <div class="toggle-disponible" style="width: 100%;">
                        <button type="button" class="btn-no" style="flex: 1;" onclick="seleccionarDisponibilidad(this, false)">
                            <i class="fas fa-times"></i> No
                        </button>
                        <button type="button" class="btn-si active" style="flex: 1;" onclick="seleccionarDisponibilidad(this, true)">
                            <i class="fas fa-check"></i> Sí
                        </button>
                    </div>
                    <input type="hidden" id="hdnDisponible" name="hdnDisponible" value="true" />
                </div>

                <asp:Label ID="lblMensaje" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModal()">
                        <i class="fas fa-times"></i> Cancelar
                    </button>
                    <asp:Button ID="btnGuardar" runat="server" CssClass="btn-kawki-primary"
                        Text="💾 Registrar" OnClick="btnGuardar_Click" OnClientClick="return validarFormulario();" CausesValidation="false" />
                </div>
            </div>
        </div>

        <!-- Modal de Editar Stock -->
        <div id="modalEditarStock" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h5><i class="fas fa-edit me-2"></i>Editar Stock - <span id="colorNombreStock"></span> Talla <span id="tallaNombreStock"></span></h5>
                </div>

                <asp:HiddenField ID="hfVarianteId" runat="server" />

                <div class="control-stock">
                    <label>Stock Actual *</label>
                    <asp:TextBox ID="txtStockEditar" runat="server" CssClass="form-control" TextMode="Number" placeholder="0" />
                    <asp:Label ID="lblErrorStockEditar" runat="server" CssClass="text-danger small d-block mt-1" />
                </div>

                <div class="control-stock">
                    <label>Stock Mínimo *</label>
                    <asp:TextBox ID="txtStockMinimoEditar" runat="server" CssClass="form-control" TextMode="Number" placeholder="5" />
                    <asp:Label ID="lblErrorStockMinimoEditar" runat="server" CssClass="text-danger small d-block mt-1" />
                </div>

                <asp:Label ID="lblMensajeStock" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalStock()">
                        <i class="fas fa-times"></i> Cancelar
                    </button>
                    <asp:Button ID="btnGuardarStock" runat="server" CssClass="btn-kawki-primary"
                        Text="💾 Guardar" OnClick="btnGuardarStock_Click" OnClientClick="return validarStock();" CausesValidation="false" />
                </div>
            </div>
        </div>

        <!-- Modal de Agregar Talla -->
        <div id="modalAgregarTalla" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h5><i class="fas fa-plus me-2"></i>Agregar talla a <span id="colorNombreTalla"></span></h5>
                </div>

                <asp:HiddenField ID="hfColorIdTalla" runat="server" />

                <div class="mb-3">
                    <label class="form-label">Talla *</label>
                    <asp:DropDownList ID="ddlTalla" runat="server" CssClass="form-select">
                    </asp:DropDownList>
                    <asp:Label ID="lblErrorTallaSelect" runat="server" CssClass="text-danger small d-block mt-1" />
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Stock Inicial *</label>
                        <asp:TextBox ID="txtStockTalla" runat="server" CssClass="form-control" 
                            TextMode="Number" placeholder="0"/>
                        <asp:Label ID="lblErrorStockTalla" runat="server" CssClass="text-danger small d-block mt-1" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Stock Mínimo *</label>
                        <asp:TextBox ID="txtStockMinimoTalla" runat="server" CssClass="form-control" 
                            TextMode="Number" placeholder="5"/>
                        <asp:Label ID="lblErrorStockMinimoTalla" runat="server" CssClass="text-danger small d-block mt-1" />
                    </div>
                </div>

                <asp:Label ID="lblMensajeTalla" runat="server" CssClass="d-block mb-3" />

                <div class="text-end">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalTalla()">
                        <i class="fas fa-times"></i> Cancelar
                    </button>
                    <asp:Button ID="btnGuardarTalla" runat="server" CssClass="btn-kawki-primary"
                        Text="💾 Agregar Talla" OnClick="btnGuardarTalla_Click" />
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script>
        function abrirModalRegistro() {
            var modal = document.getElementById("modalVariante");
            if (modal) {
                modal.style.display = "flex";
                var computed = window.getComputedStyle(modal).display;
            }
        }

        function cerrarModal() {
            var modal = document.getElementById("modalVariante");
            modal.style.display = "none";
            modal.classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= ddlColor.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= txtTallas.ClientID %>").value = "";
            document.getElementById("<%= txtStocks.ClientID %>").value = "";
            document.getElementById("<%= txtStocksMinimos.ClientID %>").value = "";
            document.getElementById("<%= txtUrlImagen.ClientID %>").value = "";
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("hdnDisponible").value = "true";

            // Ocultar todos los errores
            document.getElementById("errorColor").style.display = "none";
            document.getElementById("errorTallas").style.display = "none";
            document.getElementById("errorStocks").style.display = "none";
            document.getElementById("errorUrlImagen").style.display = "none";

            var buttons = document.querySelectorAll("#modalVariante .toggle-disponible button");
            buttons.forEach(b => b.classList.remove('active'));
            document.querySelector("#modalVariante .toggle-disponible .btn-si").classList.add('active');
        }

        function validarFormulario() {
            let valido = true;

            // Validar color
            let color = document.getElementById("<%= ddlColor.ClientID %>").value;
            if (color === "0" || color === "") {
                document.getElementById("errorColor").style.display = "block";
                valido = false;
            } else {
                document.getElementById("errorColor").style.display = "none";
            }

            // Validar tallas
            let tallas = document.getElementById("<%= txtTallas.ClientID %>").value.trim();
            if (tallas === "") {
                document.getElementById("errorTallas").style.display = "block";
                valido = false;
            } else {
                document.getElementById("errorTallas").style.display = "none";
            }

            // Validar stocks
            let stocks = document.getElementById("<%= txtStocks.ClientID %>").value.trim();
            if (stocks === "") {
                document.getElementById("errorStocks").style.display = "block";
                valido = false;
            } else {
                document.getElementById("errorStocks").style.display = "none";
            }

            if (!valido) {
                return false;
            }

            return true;
        }

        function validarStock() {
            let valido = true;

            let stock = document.getElementById("txtStockEditar").value;
            if (stock === "") {
                document.getElementById("errorStockEditar").style.display = "block";
                document.getElementById("errorStockEditar").innerText = "Debe ingresar un valor para el stock";
                valido = false;
            } else if (parseInt(stock) < 0) {
                document.getElementById("errorStockEditar").style.display = "block";
                document.getElementById("errorStockEditar").innerText = "El stock no puede ser negativo";
                valido = false;
            } else {
                document.getElementById("errorStockEditar").style.display = "none";
            }

            let stockMinimo = document.getElementById("txtStockMinimoEditar").value;
            if (stockMinimo === "") {
                document.getElementById("errorStockMinimoEditar").style.display = "block";
                document.getElementById("errorStockMinimoEditar").innerText = "Debe ingresar un valor para el stock mínimo";
                valido = false;
            } else if (parseInt(stockMinimo) < 0) {
                document.getElementById("errorStockMinimoEditar").style.display = "block";
                document.getElementById("errorStockMinimoEditar").innerText = "El stock mínimo no puede ser negativo";
                valido = false;
            } else {
                document.getElementById("errorStockMinimoEditar").style.display = "none";
            }

            return valido;
        }

        function seleccionarDisponibilidad(btn, valor) {
            var parent = btn.parentElement;
            parent.querySelectorAll('button').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            document.getElementById("hdnDisponible").value = valor;
        }

        function abrirModalEditarStock(varianteId, colorNombre, tallaNombre) {
            document.getElementById("colorNombreStock").innerText = colorNombre;
            document.getElementById("tallaNombreStock").innerText = tallaNombre;
            document.getElementById("<%= hfVarianteId.ClientID %>").value = varianteId;
            document.getElementById("modalEditarStock").classList.add("show");
        }

        function cerrarModalStock() {
            document.getElementById("modalEditarStock").classList.remove("show");
        }

        function abrirModalAgregarTalla(colorId, colorNombre) {
            document.getElementById("<%= hfColorIdTalla.ClientID %>").value = colorId;
            document.getElementById("colorNombreTalla").innerText = colorNombre;
            document.getElementById("modalAgregarTalla").classList.add("show");
        }

        function cerrarModalTalla() {
            document.getElementById("modalAgregarTalla").classList.remove("show");
        }

        function cambiarDisponibilidad(btn, varianteId, disponible) {
            var parent = btn.parentElement;
            parent.querySelectorAll('button').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            // Hacer petición al servidor para actualizar
            __doPostBack('UpdateDisponibilidad', varianteId + '|' + disponible);
        }

        function mostrarMensajeExito(mensaje) {
            var mensajeDiv = document.createElement('div');
            mensajeDiv.className = 'alert alert-success alert-dismissible fade show position-fixed';
            mensajeDiv.style.top = '20px';
            mensajeDiv.style.right = '20px';
            mensajeDiv.style.zIndex = '9999';
            mensajeDiv.innerHTML = '<i class="fas fa-check-circle me-2"></i>' + mensaje +
                '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';
            document.body.appendChild(mensajeDiv);
            setTimeout(function () { mensajeDiv.remove(); }, 3000);
        }

        function mostrarMensajeError(mensaje) {
            var mensajeDiv = document.createElement('div');
            mensajeDiv.className = 'alert alert-danger alert-dismissible fade show position-fixed';
            mensajeDiv.style.top = '20px';
            mensajeDiv.style.right = '20px';
            mensajeDiv.style.zIndex = '9999';
            mensajeDiv.innerHTML = '<i class="fas fa-exclamation-circle me-2"></i>' + mensaje +
                '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';
            document.body.appendChild(mensajeDiv);
            setTimeout(function () { mensajeDiv.remove(); }, 5000);
        }

    </script>
</asp:Content>