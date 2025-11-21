<%@ Page Title="Registrar Ventas" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="RegistrarVentas.aspx.cs" Inherits="KawkiWeb.RegistrarVentas" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/Stylo/registroventa.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid ventas-container">
        <!-- Encabezado de página -->
        <div class="ventas-header">
            <h1>Registrar venta</h1>
            <p>Registra ventas realizadas por redes sociales, con los datos del cliente y el detalle de los productos.</p>
        </div>

        <div class="row g-3">
            <!-- Columna izquierda: datos cliente + detalle productos -->
            <div class="col-lg-8">
                <!-- Datos del cliente -->
                <div class="card card-kawki">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title mb-0">
                            <i class="fas fa-user-circle"></i> Datos del cliente
                        </h5>
                        <span class="badge badge-redes">
                            <i class="fab fa-instagram me-1"></i>
                            <i class="fab fa-facebook me-1"></i>
                            <i class="fab fa-whatsapp me-1"></i>
                            Venta por redes sociales
                        </span>
                    </div>
                    <div class="card-body">
                        <div class="row g-3">
                            <!-- NOMBRE DEL CLIENTE -->
                            <div id="grupoNombreCliente" runat="server" class="col-md-6">
                                <label class="form-label" for="<%= txtNombreCliente.ClientID %>">
                                    Nombre completo <span class="text-danger">*</span>
                                </label>

                                <asp:TextBox ID="txtNombreCliente" runat="server" CssClass="form-control"
                                    placeholder="Nombre y apellidos" />

                                <!-- Acepta solo letras y espacios -->
                                <asp:RegularExpressionValidator 
                                    ID="revNombre" 
                                    runat="server"
                                    ControlToValidate="txtNombreCliente"
                                    ValidationExpression="^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"
                                    ErrorMessage="Solo se permiten letras y espacios."
                                    CssClass="text-danger"
                                    Display="Dynamic" />

                                <asp:RequiredFieldValidator ID="rfvNombre" runat="server"
                                    ControlToValidate="txtNombreCliente"
                                    ErrorMessage="Este campo es obligatorio."
                                    CssClass="text-danger"
                                    Display="Dynamic" />
                            </div>

                            <!-- TELEFONO -->
                            <div id="grupoTelefono" runat="server" class="col-md-3">
                                <label class="form-label" for="<%= txtTelefono.ClientID %>">
                                    Teléfono <span class="text-danger">*</span>
                                </label>

                                <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control"
                                    MaxLength="9" placeholder="Ej. 987654321" />

                                <!-- Solo 9 números -->
                                <asp:RegularExpressionValidator 
                                    ID="revTelefono"
                                    runat="server"
                                    ControlToValidate="txtTelefono"
                                    ValidationExpression="^[0-9]{9}$"
                                    ErrorMessage="El teléfono debe tener exactamente 9 dígitos numéricos."
                                    CssClass="text-danger"
                                    Display="Dynamic" />

                                <asp:RequiredFieldValidator ID="rfvTelefono" runat="server"
                                    ControlToValidate="txtTelefono"
                                    ErrorMessage="Este campo es obligatorio."
                                    CssClass="text-danger"
                                    Display="Dynamic" />
                            </div>

                            <!-- DNI -->
                            <div id="grupoDNI" runat="server" class="col-md-3" visible="false">
                                <label class="form-label">DNI</label>
                                <asp:TextBox ID="txtDNI" runat="server" CssClass="form-control" MaxLength="8"></asp:TextBox>
                                <asp:RegularExpressionValidator 
                                    ID="revDNI" 
                                    runat="server"
                                    ControlToValidate="txtDNI"
                                    ValidationExpression="^[0-9]+$"
                                    ErrorMessage="El DNI debe tener exactamente 8 dígitos numéricos."
                                    CssClass="text-danger"
                                    Display="Dynamic" />
                            </div>

                            <!-- RUC -->
                            <div id="grupoRUC" runat="server" class="col-md-3" visible="false">
                                <label class="form-label">RUC</label>
                                <asp:TextBox ID="txtRUC" runat="server" CssClass="form-control" MaxLength="11"></asp:TextBox>
                            </div>

                            <!-- Razón Social -->
                            <div id="grupoRazonSocial" runat="server" class="col-md-6" visible="false">
                                <label class="form-label">Razón Social</label>
                                <asp:TextBox ID="txtRazonSocial" runat="server" CssClass="form-control"></asp:TextBox>
                            <asp:RequiredFieldValidator 
                                ID="rfvRazonSocial" 
                                runat="server"
                                ControlToValidate="txtRazonSocial"
                                ErrorMessage="Ingrese una razón social válida."
                                CssClass="text-danger"
                                Display="Dynamic" />
                            </div>

                            <!-- Dirección Fiscal -->
                            <div id="grupoDireccionFiscal" runat="server" class="col-md-6" visible="false">
                                <label class="form-label">Dirección Fiscal</label>
                                <asp:TextBox ID="txtDireccionFiscal" runat="server" CssClass="form-control"></asp:TextBox>
                            <asp:RequiredFieldValidator 
                                ID="rfvDireccionFiscal" 
                                runat="server"
                                ControlToValidate="txtDireccionFiscal"
                                ErrorMessage="La dirección fiscal es obligatoria."
                                CssClass="text-danger"
                                Display="Dynamic" />
                            </div>


                            <div class="col-md-4">
                                <label class="form-label" for="<%= ddlCanal.ClientID %>">Canal de venta</label>
                                <asp:DropDownList ID="ddlCanal" runat="server" CssClass="form-select">
                                    <asp:ListItem Text="Instagram" Value="Instagram" />
                                    <asp:ListItem Text="Facebook" Value="Facebook" />
                                    <asp:ListItem Text="WhatsApp" Value="WhatsApp" />
                                </asp:DropDownList>
                            </div>

                        </div>
                    </div>
                </div>

                <!-- Detalle de productos -->
                <div class="card card-kawki">
                    <div class="card-header">
                        <h5 class="card-title mb-0">
                            <i class="fas fa-shopping-basket"></i> Productos de la venta
                        </h5>
                    </div>
                    <div class="card-body">
                        <!-- Fila para agregar un producto -->
                        <div class="row g-2 align-items-end mb-3">
                            
                            <div class="col-md-5">
                                <label class="form-label" for="<%= ddlProducto.ClientID %>">Producto</label>
                                <asp:DropDownList ID="ddlProducto" runat="server" CssClass="form-select"
                                    AutoPostBack="true" OnSelectedIndexChanged="ddlProducto_SelectedIndexChanged">
                                </asp:DropDownList>
                            </div>

                            <div class="col-md-2">
                                <label class="form-label" for="<%= txtCantidad.ClientID %>">Cantidad</label>
                                <asp:TextBox ID="txtCantidad" runat="server" CssClass="form-control"
                                    TextMode="Number" Text="1" />
                            </div>
                            <div class="col-md-2">
                                <label class="form-label" for="<%= txtPrecioUnitario.ClientID %>">Precio unitario</label>
                                <asp:TextBox ID="txtPrecioUnitario" runat="server" CssClass="form-control"
                                    placeholder="0.00" ReadOnly="true" />
                            </div>
                            <div class="col-md-3 d-grid">
                                <asp:Button ID="btnAgregarProducto" runat="server"
                                    CssClass="btn btn-kawki-outline"
                                    Text="+ Agregar producto"
                                    OnClick="btnAgregarProducto_Click"
                                    CausesValidation="false" />
                            </div>
                        </div>

                        <!-- Tabla detalle -->
                        <asp:GridView ID="gvDetalle" runat="server" AutoGenerateColumns="False"
                            CssClass="table table-detalle"
                            GridLines="None" ShowHeaderWhenEmpty="True"
                            OnRowCommand="gvDetalle_RowCommand">
                            <Columns>
                                <asp:BoundField DataField="Producto" HeaderText="Producto" />
                                <asp:BoundField DataField="cantidad" HeaderText="Cant." />
                                <asp:BoundField DataField="precio_unitario" HeaderText="P. Unitario" DataFormatString="{0:C}" />
                                <asp:BoundField DataField="subtotal" HeaderText="Subtotal" DataFormatString="{0:C}" />
                                <asp:TemplateField HeaderText="Acción">
                                    <ItemTemplate>
                                        <asp:Button runat="server" 
                                            CommandName="Eliminar" 
                                            CommandArgument='<%# Container.DataItemIndex %>'
                                            Text="Eliminar"
                                            CssClass="btn btn-sm btn-link text-danger"
                                            CausesValidation="false" />
                                    </ItemTemplate>
                                </asp:TemplateField>
                            </Columns>
                            <EmptyDataTemplate>
                                <span class="text-muted-small">Aún no has agregado productos a esta venta.</span>
                            </EmptyDataTemplate>
                        </asp:GridView>
                    </div>
                </div>
            </div>

            <!-- Columna derecha: totales y pago -->
            <div class="col-lg-4">
                <div class="card card-kawki">
                    <div class="card-header">
                        <h5 class="card-title mb-0">
                            <i class="fas fa-file-invoice-dollar"></i> Comprobante y pago
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label class="form-label" for="<%= ddlComprobante.ClientID %>">Tipo de comprobante</label>
                            <asp:DropDownList ID="ddlComprobante" runat="server" AutoPostBack="true" OnSelectedIndexChanged="ddlComprobante_SelectedIndexChanged">
                                <asp:ListItem Text="Boleta Simple" Value="boleta-simple" />
                                <asp:ListItem Text="Boleta con DNI" Value="boleta-dni" />
                                <asp:ListItem Text="Factura" Value="factura" />
                            </asp:DropDownList>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="<%= ddlMetodoPago.ClientID %>">Método de pago</label>
                            <asp:DropDownList ID="ddlMetodoPago" runat="server" CssClass="form-select">
                                <%--<asp:ListItem Text="Yape" Value="Yape" />
                                <asp:ListItem Text="Plin" Value="Plin" />
                                <asp:ListItem Text="Transferencia" Value="Transferencia" />--%>
                            </asp:DropDownList>
                        </div>

                        <div class="col-md-12">
                            <label class="form-label" for="<%= ddlDescuentos.ClientID %>">Descuentos</label>
                            <asp:DropDownList 
                                ID="ddlDescuentos" 
                                runat="server" 
                                CssClass="form-select"
                                AutoPostBack="true"
                                OnSelectedIndexChanged="ddlDescuentos_SelectedIndexChanged">
                            </asp:DropDownList>
                        </div>

                        <hr />

                        <div class="d-flex justify-content-between">
                            <div class="totales-label">Subtotal productos</div>
                            <asp:Label ID="lblSubtotal" runat="server" CssClass="totales-valor" Text="S/ 0.00" />
                        </div>
                        <div class="d-flex justify-content-between mb-1">
                            <div class="totales-label">Descuento</div>
                            <asp:Label ID="lblDescuento" runat="server" CssClass="totales-label" Text="S/ 0.00" />
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <div class="totales-label">Monto total</div>
                            <asp:Label ID="lblTotal" runat="server" CssClass="totales-valor text-success" Text="S/ 0.00" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="<%= txtNotas.ClientID %>">Notas u observaciones</label>
                            <asp:TextBox ID="txtNotas" runat="server" CssClass="form-control"
                                TextMode="MultiLine" Rows="3"
                                placeholder="Ej. Enviar captura de pago por WhatsApp, coordinar entrega, etc." />
                        </div>

                        <asp:Label ID="lblMensaje" runat="server" CssClass="text-success mb-2 d-block"></asp:Label>

                        <div class="d-grid">
                            <asp:Button ID="btnRegistrarVenta" runat="server"
                                CssClass="btn btn-kawki-primary"
                                Text="Registrar venta"
                                OnClick="btnRegistrarVenta_Click" />
                        </div>

                        <div class="mt-2 text-muted-small">
                            Al registrar la venta se guardará el detalle del cliente, productos y método de pago.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</asp:Content>