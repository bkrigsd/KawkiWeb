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

    /* Botón Abastecimiento */
    .btn-abastecimiento {
        background: #ff9800;
        color: white;
        border: none;
        padding: 6px 10px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        transition: all 0.3s;
        margin-right: 5px;
        margin-bottom: 3px;
        font-weight: 500;
    }
    
    .btn-abastecimiento:hover {
        background: #f57c00;
        box-shadow: 0 2px 6px rgba(255, 152, 0, 0.4);
        transform: translateY(-1px);
    }
    
    .btn-abastecimiento:active {
        transform: translateY(0);
    }
    
    /* Para que se vea mejor en dispositivos pequeños */
    @media (max-width: 768px) {
        .btn-abastecimiento {
            padding: 4px 8px;
            font-size: 11px;
        }
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
                                    <th style="width: 200px;">Acciones</th>
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

                <!-- En el modal de AGREGAR VARIANTE -->
                <div class="mb-3">
                    <label class="form-label">Imagen del Producto:</label>
                    <button type="button" id="btnSubirImagen" class="btn btn-outline-primary w-100">
                        Seleccionar Imagen desde tu PC
                    </button>
                    <asp:HiddenField ID="hdnUrlImagenCloudinary" runat="server" />
                    <div id="imagenPreview" style="display:none; margin-top: 10px; text-align: center;">
                        <img id="imgPreview" src="" style="max-width: 200px; max-height: 200px; border-radius: 8px;" class="img-thumbnail">
                        <p class="text-success mt-2 mb-0">
                            <i class="fas fa-check-circle"></i> Imagen cargada correctamente
                        </p>
                    </div>
                    <asp:Label ID="lblErrorImagen" runat="server" CssClass="text-danger small"></asp:Label>
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
                        Text="Registrar" OnClick="btnGuardar_Click" OnClientClick="return validarFormulario();" CausesValidation="false" />
                </div>
            </div>
        </div>
   </div>

        <!-- Modal de Modificaciones -->
        <div id="modalModificaciones" class="modal-kawki">
            <div class="modal-content-kawki">
                <div class="modal-header-kawki">
                    <h3>Editar Variante</h3>
                </div>

                <div class="modal-body">
                    <asp:HiddenField ID="hfVarianteId" runat="server" />
                    <asp:Label ID="lblVarianteInfo" runat="server" CssClass="text-muted d-block mb-3"></asp:Label>

                    <asp:Label ID="lblMensajeModif" runat="server" CssClass="text-warning d-block mb-2"></asp:Label>

                    <!-- Campo Talla -->
                    <div class="form-group">
                        <label>Talla</label>
                        <asp:DropDownList ID="ddlTallaModif" runat="server" CssClass="form-control">
                        </asp:DropDownList>
                        <asp:Label ID="lblErrorTallaModif" runat="server" CssClass="text-danger small"></asp:Label>
                    </div>

                    <!-- Campo Stock -->
                    <div class="form-group">
                        <label>Stock Actual</label>
                        <asp:TextBox ID="txtStockEditar" runat="server" CssClass="form-control" 
                            TextMode="Number"></asp:TextBox>
                        <asp:Label ID="lblErrorStockEditar" runat="server" CssClass="text-danger small"></asp:Label>
                    </div>

                    <!-- Campo Stock Mínimo -->
                    <div class="form-group">
                        <label>Stock Mínimo</label>
                        <asp:TextBox ID="txtStockMinimoEditar" runat="server" CssClass="form-control" 
                            TextMode="Number"></asp:TextBox>
                        <asp:Label ID="lblErrorStockMinimoEditar" runat="server" CssClass="text-danger small"></asp:Label>
                    </div>

                    <!-- En el modal de MODIFICACIONES -->
                    <div class="form-group">
                        <label>Cambiar Imagen (opcional)</label>
                        <button type="button" id="btnCambiarImagenModif" class="btn btn-outline-primary w-100">
                            <i class="fas fa-image me-2"></i> Cambiar Imagen
                        </button>
    
                        <asp:HiddenField ID="hdnUrlImagenActualModif" runat="server" />
                        <asp:HiddenField ID="hdnUrlImagenNuevaModif" runat="server" />
    
                        <div class="form-group">
                            <div id="imagenActualModif" style="margin-top: 10px; text-align: center;"> 
                                <p class="text-muted small">Imagen actual:</p>
                                <img id="imgActualModif" src="" style="max-width: 150px; max-height: 150px; border-radius: 8px; border: 2px solid #ddd;" />
                            </div>

                            <div id="imagenNuevaModif" 
                                 style="display:none; margin-top: 10px; text-align: center; padding: 10px; background: #f0f8ff; border-radius: 8px;">
                                <p class="text-info small"><i class="fas fa-check-circle me-2"></i>Nueva imagen:</p>
                                <img id="imgNuevaModif" src="" style="max-width: 150px; max-height: 150px; border-radius: 8px; border: 2px solid #4caf50;" />
                            </div>
                        </div>
                    </div>
                </div>

                <asp:Label ID="lblMensajeModificaciones" runat="server" CssClass="d-block mb-3" />

                <div class="modal-footer">
                    <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalModificaciones()">
                        <i class="fas fa-times"></i> Cancelar
                    </button>

                    <asp:Button ID="btnModificaciones" runat="server"
                        Text="Guardar Cambios"
                        CssClass="btn-kawki-primary"
                        OnClick="btnModificaciones_Click" />
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
                        Text="Agregar Talla" OnClick="btnGuardarTalla_Click" />
                </div>
            </div>
        </div>

    <div id="modalAbastecimiento" class="modal-kawki">
        <div class="modal-content-kawki">
            <div class="modal-header-kawki">
                <h5><i class="fas fa-plus-circle me-2"></i>Abastecimiento de Stock</h5>
            </div>

            <asp:HiddenField ID="hfVarianteIdAbast" runat="server" />

            <!-- Información del Producto -->
            <div class="alert alert-info mb-3">
                <i class="fas fa-info-circle me-2"></i>
                <strong>Variante:</strong> 
                <span id="lblVarianteAbast" style="font-weight: bold;"></span>
            </div>

            <!-- Stock Actual -->
            <div class="mb-3" style="background: #f0f8ff; padding: 12px; border-radius: 6px; border-left: 4px solid #2196F3;">
                <label class="form-label" style="font-weight: 600; margin-bottom: 5px;">Stock Actual</label>
                <div style="font-size: 24px; color: #2196F3; font-weight: bold;">
                    <span id="lblStockActualAbast">0</span> unidades
                </div>
            </div>

            <!-- Tipo de Movimiento -->
            <div class="mb-3">
                <label class="form-label">Tipo de Movimiento *</label>
                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                    <button type="button" class="btn-tipo-mov" data-tipo="INGRESO" onclick="seleccionarTipoMovimiento(this, 'INGRESO')" style="border: 2px solid #4caf50; background: white; color: #4caf50; padding: 10px; border-radius: 6px; font-weight: 600; cursor: pointer; transition: all 0.3s;">
                        <i class="fas fa-arrow-up"></i> INGRESO
                    </button>
                    <button type="button" class="btn-tipo-mov" data-tipo="AJUSTE" onclick="seleccionarTipoMovimiento(this, 'AJUSTE')" style="border: 2px solid #ff9800; background: white; color: #ff9800; padding: 10px; border-radius: 6px; font-weight: 600; cursor: pointer; transition: all 0.3s;">
                        <i class="fas fa-sync-alt"></i> AJUSTE
                    </button>
                </div>
                <input type="hidden" id="hdnTipoMovimiento" name="hdnTipoMovimiento" value="" />
                <asp:Label ID="lblErrorTipoMov" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <!-- Nuevo Stock (solo para AJUSTE) -->
            <div class="mb-3" id="divNuevoStock" style="display: none;">
                <label class="form-label">Nuevo Stock (AJUSTE) *</label>
                <div style="display: flex; gap: 8px;">
                    <asp:TextBox ID="txtNuevoStock" runat="server" CssClass="form-control" 
                        TextMode="Number" placeholder="Ingrese el nuevo valor" min="0" />
                    <div style="display: flex; align-items: center; background: #fff3cd; padding: 0 12px; border-radius: 6px; font-size: 12px; color: #856404; white-space: nowrap;">
                        Cambio: <span id="lblCambioStock" style="font-weight: bold; margin-left: 5px;">0</span>
                    </div>
                </div>
                <asp:Label ID="lblErrorNuevoStock" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <!-- Cantidad (solo para INGRESO) -->
            <div class="mb-3" id="divCantidad" style="display: none;">
                <label class="form-label">Cantidad a Ingresar *</label>
                <asp:TextBox ID="txtCantidadIngreso" runat="server" CssClass="form-control" 
                    TextMode="Number" placeholder="Ingrese la cantidad" min="1" />
                <asp:Label ID="lblErrorCantidad" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <!-- Descripción -->
            <div class="mb-3">
                <label class="form-label" id="lblDescripcionLabel">Descripción <span id="spanDescripcionObligatoria" style="color: red;"></span></label>
                <asp:TextBox ID="txtDescripcionAbast" runat="server" CssClass="form-control" 
                    TextMode="MultiLine" Rows="3" placeholder="Ingrese los detalles del movimiento..." />
                <small class="text-muted" id="pequenaDescripcion">Opcional para INGRESO. Obligatorio para AJUSTE.</small>
                <asp:Label ID="lblErrorDescripcion" runat="server" CssClass="text-danger small d-block mt-1" />
            </div>

            <asp:Label ID="lblMensajeAbast" runat="server" CssClass="d-block mb-3" />

            <div class="text-end">
                <button type="button" class="btn-kawki-outline me-2" onclick="cerrarModalAbastecimiento()">
                    <i class="fas fa-times"></i> Cancelar
                </button>
                <asp:Button ID="btnGuardarAbast" runat="server" CssClass="btn-kawki-primary"
                    Text="Registrar Abastecimiento" OnClick="btnGuardarAbast_Click" 
                    OnClientClick="return validarFormularioAbastecimiento();" />
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script>
        function abrirModalRegistro() {
            // Limpiar TODO antes de abrir
            limpiarFormulario();
            document.getElementById("modalVariante").style.display = "flex";
            document.getElementById("modalVariante").classList.add("show");
        }

        // Para mantener el modal abierto con errores (NO recarga nada)
        function MantenerModal() {
            document.getElementById("modalVariante").style.display = "flex";
            document.getElementById("modalVariante").classList.add("show");
        }

        function cerrarModal() {
            document.getElementById("modalVariante").style.display = "none";
            document.getElementById("modalVariante").classList.remove("show");
            limpiarFormulario();
        }

        function limpiarFormulario() {
            document.getElementById("<%= ddlColor.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= txtTallas.ClientID %>").value = "";
            document.getElementById("<%= txtStocks.ClientID %>").value = "";
            document.getElementById("<%= txtStocksMinimos.ClientID %>").value = "";
            document.getElementById('<%= hdnUrlImagenCloudinary.ClientID %>').value = '';
            document.getElementById('imagenPreview').style.display = 'none';
            document.getElementById('imgPreview').src = '';
            document.getElementById('<%= lblErrorStockEditar.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorStockMinimoEditar.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorColor.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorTallas.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorStocks.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorStocksMinimos.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorTallaModif.ClientID %>').innerText = '';
            document.getElementById("<%= lblMensaje.ClientID %>").innerText = "";
            document.getElementById("hdnDisponible").value = "true";

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
            }

            // Validar tallas
            let tallas = document.getElementById("<%= txtTallas.ClientID %>").value.trim();
            if (tallas === "") {
                document.getElementById("errorTallas").style.display = "block";
                valido = false;
            }

            // Validar stocks
            let stocks = document.getElementById("<%= txtStocks.ClientID %>").value.trim();
            if (stocks === "") {
                document.getElementById("errorStocks").style.display = "block";
                valido = false;
            }

            return true;
        }

        function seleccionarDisponibilidad(btn, valor) {
            var parent = btn.parentElement;
            parent.querySelectorAll('button').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            document.getElementById("hdnDisponible").value = valor;
        }

        // Para abrir el modal la primera vez (carga datos de BD)
        function abrirModalModificaciones(varianteId, colorNombre, tallaNombre, tallaId, urlImagen, stock, stockMinimo) {
            // Limpiar errores
            document.getElementById('<%= lblMensajeModificaciones.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorStockEditar.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorStockMinimoEditar.ClientID %>').innerText = '';
            document.getElementById('<%= lblErrorTallaModif.ClientID %>').innerText = '';

            // Llenar datos
            document.getElementById('<%= hfVarianteId.ClientID %>').value = varianteId;
            document.getElementById('<%= lblVarianteInfo.ClientID %>').textContent =
                `Color: ${colorNombre} | Talla: ${tallaNombre}`;

            const ddlTallaModificaciones = document.getElementById('<%= ddlTallaModif.ClientID %>');
            if (ddlTallaModificaciones && tallaId) {
                ddlTallaModificaciones.value = tallaId;
            }

            // Cargar valores
            document.getElementById('<%= txtStockEditar.ClientID %>').value = stock || '';
            document.getElementById('<%= txtStockMinimoEditar.ClientID %>').value = stockMinimo || '';

            // Cargar la URL de la imagen actual y mostrarla
            document.getElementById('<%= hdnUrlImagenActualModif.ClientID %>').value = urlImagen;
            document.getElementById('imgActualModif').src = urlImagen || 'https://via.placeholder.com/150?text=Sin+Imagen';

            // **Asegurarse de que la Imagen Actual esté visible (por si se ocultó antes)**
            document.getElementById('imagenActualModif').style.display = 'block';

            // Limpiar y OCULTAR la sección de la URL nueva al abrir el modal
            document.getElementById('<%= hdnUrlImagenNuevaModif.ClientID %>').value = '';
            document.getElementById('imagenNuevaModif').style.display = 'none'; // <-- CLAVE

            document.getElementById("modalModificaciones").classList.add("show");
        }

        // FUNCIÓN 2: Para mantener el modal abierto con errores (NO recarga nada)
        function mantenerModalModificacionesAbierto() {
            document.getElementById("modalModificaciones").classList.add("show");
        }

        function cerrarModalModificaciones() {
            document.getElementById("modalModificaciones").classList.remove("show");
            limpiarFormularioModificaciones();
        }

        function limpiarFormularioModificaciones() {
            document.getElementById('<%= txtStockEditar.ClientID %>').value = '';
            document.getElementById('<%= txtStockMinimoEditar.ClientID %>').value = '';
            document.getElementById('urlImagenNuevaModif').value = '';

            // Limpiar previews
            document.getElementById('imagenNuevaModif').style.display = 'none';
            document.getElementById('imgNuevaModif').src = '';

            const ddlTallaModificaciones = document.getElementById('<%= ddlTallaModif.ClientID %>');
            if (ddlTallaModificaciones) ddlTallaModificaciones.selectedIndex = 0;

            document.getElementById('<%= lblMensajeModificaciones.ClientID %>').textContent = '';
            document.getElementById('<%= lblErrorStockEditar.ClientID %>').textContent = '';
            document.getElementById('<%= lblErrorStockMinimoEditar.ClientID %>').textContent = '';
            document.getElementById('<%= lblErrorTallaModif.ClientID %>').textContent = '';
        }

        function abrirModalAgregarTalla(colorId, colorNombre) {
            // Limpiar errores antes de abrir
            limpiarFormularioTalla();

            document.getElementById("<%= hfColorIdTalla.ClientID %>").value = colorId;
            document.getElementById("colorNombreTalla").innerText = colorNombre;
            document.getElementById("modalAgregarTalla").classList.add("show");
        }

        function limpiarFormularioTalla() {
            // Limpiar TODO: datos y errores
            document.getElementById("<%= ddlTalla.ClientID %>").selectedIndex = 0;
            document.getElementById("<%= txtStockTalla.ClientID %>").value = "";
            document.getElementById("<%= txtStockMinimoTalla.ClientID %>").value = "";

            // Limpiar errores
            document.getElementById("<%= lblErrorTallaSelect.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorStockTalla.ClientID %>").innerText = "";
            document.getElementById("<%= lblErrorStockMinimoTalla.ClientID %>").innerText = "";
            document.getElementById("<%= lblMensajeTalla.ClientID %>").innerText = "";
        }

        // FUNCIÓN 2: Para mantener el modal abierto con errores (NO recarga nada)
        function MantenerModalTallaAbierto(colorId,colorNombre) {
            // Para reabrir después de error - NO LIMPIAR, mantener datos
            document.getElementById("<%= hfColorIdTalla.ClientID %>").value = colorId;
            document.getElementById("colorNombreTalla").innerText = colorNombre;
            document.getElementById("modalAgregarTalla").classList.add("show");
        }

        function cerrarModalTalla() {
            document.getElementById("modalAgregarTalla").classList.remove("show");
            limpiarFormularioTalla();
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

        function abrirModalAbastecimiento(varianteId, varianteInfo, stockActual) {
            // Limpiar formulario
            limpiarFormularioAbastecimiento();

            // Llenar datos
            document.getElementById("<%= hfVarianteIdAbast.ClientID %>").value = varianteId;
            document.getElementById("lblVarianteAbast").textContent = varianteInfo;
            document.getElementById("lblStockActualAbast").textContent = stockActual;
            document.getElementById("lblStockActualAbast").dataset.stockActual = stockActual;

            // Mostrar modal
            document.getElementById("modalAbastecimiento").style.display = "flex";
            document.getElementById("modalAbastecimiento").classList.add("show");
        }

        function seleccionarTipoMovimiento(btn, tipo) {
            // Marcar botón activo
            document.querySelectorAll(".btn-tipo-mov").forEach(b => {
                b.style.background = "white";
                b.style.color = b.dataset.tipo === "INGRESO" ? "#4caf50" : "#ff9800";
            });
            btn.style.background = btn.dataset.tipo === "INGRESO" ? "#4caf50" : "#ff9800";
            btn.style.color = "white";

            // Guardar tipo seleccionado
            document.getElementById("hdnTipoMovimiento").value = tipo;

            // Mostrar/ocultar campos según el tipo
            if (tipo === "INGRESO") {
                document.getElementById("divCantidad").style.display = "block";
                document.getElementById("divNuevoStock").style.display = "none";
                document.getElementById("spanDescripcionObligatoria").textContent = "";
                document.getElementById("pequenaDescripcion").textContent = "Opcional";
            } else { // AJUSTE
                document.getElementById("divCantidad").style.display = "none";
                document.getElementById("divNuevoStock").style.display = "block";
                document.getElementById("spanDescripcionObligatoria").textContent = " *";
                document.getElementById("pequenaDescripcion").textContent = "Obligatorio para explicar el ajuste";
            }

            // Limpiar errores
            limpiarErroresAbastecimiento();
        }

        function calcularCambioStock() {
            const stockActual = parseInt(document.getElementById("lblStockActualAbast").dataset.stockActual) || 0;
            const nuevoStock = parseInt(document.getElementById("<%= txtNuevoStock.ClientID %>").value) || 0;
            const cambio = nuevoStock - stockActual;

            const lblCambio = document.getElementById("lblCambioStock");
            if (cambio > 0) {
                lblCambio.style.color = "#4caf50";
                lblCambio.textContent = "+" + cambio;
            } else if (cambio < 0) {
                lblCambio.style.color = "#f44336";
                lblCambio.textContent = cambio;
            } else {
                lblCambio.style.color = "#666";
                lblCambio.textContent = "0";
            }
        }

        document.addEventListener("DOMContentLoaded", function () {
            const txtNuevoStock = document.getElementById("<%= txtNuevoStock.ClientID %>");
        if (txtNuevoStock) {
            txtNuevoStock.addEventListener("input", calcularCambioStock);
        }
    });

        function validarFormularioAbastecimiento() {
            let valido = true;
            limpiarErroresAbastecimiento();

            // Validar tipo de movimiento
            const tipoMov = document.getElementById("hdnTipoMovimiento").value;
            if (!tipoMov) {
                document.getElementById("<%= lblErrorTipoMov.ClientID %>").textContent = "Debe seleccionar un tipo de movimiento";
            valido = false;
        }

        if (tipoMov === "INGRESO") {
            // Validar cantidad
            const cantidad = parseInt(document.getElementById("<%= txtCantidadIngreso.ClientID %>").value);
            if (isNaN(cantidad) || cantidad <= 0) {
                document.getElementById("<%= lblErrorCantidad.ClientID %>").textContent = "Ingrese una cantidad mayor a 0";
                valido = false;
            }
        } else if (tipoMov === "AJUSTE") {
            // Validar nuevo stock
            const nuevoStock = parseInt(document.getElementById("<%= txtNuevoStock.ClientID %>").value);
            if (isNaN(nuevoStock) || nuevoStock < 0) {
                document.getElementById("<%= lblErrorNuevoStock.ClientID %>").textContent = "Ingrese un valor válido (>= 0)";
                valido = false;
            }

            // Validar descripción (obligatoria para ajuste)
            const descripcion = document.getElementById("<%= txtDescripcionAbast.ClientID %>").value.trim();
            if (descripcion === "") {
                document.getElementById("<%= lblErrorDescripcion.ClientID %>").textContent = "La descripción es obligatoria para ajustes";
                valido = false;
            }
        }
        
        if (!valido) {
            document.getElementById("modalAbastecimiento").classList.add("show");
        }
        
        return valido;
    }

    function limpiarErroresAbastecimiento() {
        document.getElementById("<%= lblErrorTipoMov.ClientID %>").textContent = "";
        document.getElementById("<%= lblErrorCantidad.ClientID %>").textContent = "";
        document.getElementById("<%= lblErrorNuevoStock.ClientID %>").textContent = "";
        document.getElementById("<%= lblErrorDescripcion.ClientID %>").textContent = "";
        document.getElementById("<%= lblMensajeAbast.ClientID %>").textContent = "";
    }

    function limpiarFormularioAbastecimiento() {
        document.getElementById("<%= hfVarianteIdAbast.ClientID %>").value = "";
        document.getElementById("lblVarianteAbast").textContent = "";
        document.getElementById("lblStockActualAbast").textContent = "0";
        document.getElementById("hdnTipoMovimiento").value = "";
        document.getElementById("<%= txtNuevoStock.ClientID %>").value = "";
        document.getElementById("<%= txtCantidadIngreso.ClientID %>").value = "";
            document.getElementById("<%= txtDescripcionAbast.ClientID %>").value = "";
            document.getElementById("lblCambioStock").textContent = "0";

            // Ocultar campos específicos
            document.getElementById("divCantidad").style.display = "none";
            document.getElementById("divNuevoStock").style.display = "none";

            // Limpiar botones
            document.querySelectorAll(".btn-tipo-mov").forEach(b => {
                b.style.background = "white";
                b.style.color = b.dataset.tipo === "INGRESO" ? "#4caf50" : "#ff9800";
            });

            limpiarErroresAbastecimiento();
        }

        function mantenerModalAbastecimientoAbierto() {
            document.getElementById("modalAbastecimiento").classList.add("show");
        }

        function cerrarModalAbastecimiento() {
            document.getElementById("modalAbastecimiento").style.display = "none";
            document.getElementById("modalAbastecimiento").classList.remove("show");
            limpiarFormularioAbastecimiento();
        }

    </script>

    <script src="https://upload-widget.cloudinary.com/global/all.js"></script>

    <script>
        const CLOUDINARY_CLOUD_NAME = 'dlkbckbdm';
        const CLOUDINARY_UPLOAD_PRESET = 'productos';

        // WIDGET PARA AGREGAR VARIANTE
        var uploadWidget = cloudinary.createUploadWidget({
            cloudName: CLOUDINARY_CLOUD_NAME,
            uploadPreset: CLOUDINARY_UPLOAD_PRESET,
            sources: ['local'],
            multiple: false,
            maxFiles: 1,
            maxFileSize: 5000000,
            clientAllowedFormats: ['png', 'jpg', 'jpeg', 'webp'],
            folder: 'productos',
            language: 'es'
        },
            function (error, result) {
                if (!error && result && result.event === "success") {
                    var imageUrl = result.info.secure_url;
                    console.log('✓ Imagen subida:', imageUrl);

                    // CORRECCIÓN: Usar ClientID
                    document.getElementById('<%= hdnUrlImagenCloudinary.ClientID %>').value = imageUrl;
                    document.getElementById('imgPreview').src = imageUrl;
                    document.getElementById('imagenPreview').style.display = 'block';
                }
                if (error) {
                    console.error('✗ Error:', error);
                    alert('Error al subir la imagen.');
                }
            });

        document.getElementById('btnSubirImagen').addEventListener('click', function (e) {
            e.preventDefault();
            uploadWidget.open();
        });

        // WIDGET PARA MODIFICAR VARIANTE
        var uploadWidgetModif = cloudinary.createUploadWidget({
            cloudName: CLOUDINARY_CLOUD_NAME,
            uploadPreset: CLOUDINARY_UPLOAD_PRESET,
            sources: ['local'],
            multiple: false,
            maxFiles: 1,
            maxFileSize: 5000000,
            clientAllowedFormats: ['png', 'jpg', 'jpeg', 'webp'],
            folder: 'productos',
            language: 'es'
        },
            function (error, result) {
                if (!error && result && result.event === "success") {
                    var imageUrl = result.info.secure_url;
                    console.log('✓ Nueva imagen:', imageUrl);

                    // 1. Guardar la URL de la nueva imagen
                    document.getElementById('<%= hdnUrlImagenNuevaModif.ClientID %>').value = imageUrl;

                    // 2. Cargar el preview en el elemento
                    document.getElementById('imgNuevaModif').src = imageUrl;

                    // 3. HACER VISIBLE el contenedor de la nueva imagen (sin tocar la actual)
                    document.getElementById('imagenNuevaModif').style.display = 'block';
                }
                if (error) {
                    console.error('✗ Error:', error);
                    alert('Error al subir la imagen.');
                }
            });

        document.getElementById('btnCambiarImagenModif').addEventListener('click', function (e) {
            e.preventDefault();
            uploadWidgetModif.open();
        });
    </script>
</asp:Content>
