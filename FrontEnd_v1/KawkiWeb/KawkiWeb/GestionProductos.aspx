<%@ Page Title="Gestión de Productos" Language="C#" MasterPageFile="~/KawkiWeb.master" AutoEventWireup="true" CodeBehind="GestionProductos.aspx.cs" Inherits="KawkiWeb.GestionProductos" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
      /* --- Modal look --- */
      .kp-modal-mask { position:fixed; inset:0; background:rgba(0,0,0,.35);
        display:flex; align-items:center; justify-content:center; z-index:1000; }
      .kp-modal { width:min(820px,96vw); background:#fff; border-radius:14px;
        padding:18px 18px 12px; box-shadow:0 14px 38px rgba(0,0,0,.28); }
      .kp-modal-header{display:flex;align-items:center;justify-content:space-between;margin-bottom:6px}
      .kp-modal-title{font-size:20px;font-weight:700}
      .kp-close{border:none;background:#fff; width:36px;height:36px; border-radius:8px}
      .kp-close:hover{background:#f3f4f6}

      /* --- Form grid like screenshot --- */
      .row2{display:grid;grid-template-columns:1fr 1fr;gap:14px;margin-top:10px}
      .row3{display:grid;grid-template-columns:1fr 1fr 1fr;gap:14px;margin-top:10px}
      .row1{margin-top:10px}
      .kp-label{font-size:13px;color:#444;margin-bottom:6px;display:block}

      /* Inputs */
      .kp-input, .kp-select, .kp-textarea{
        width:100%; border:1px solid #e6e6e6; background:#fff; border-radius:10px;
        padding:10px 12px; font-size:14px; outline:none;
      }
      .kp-input::placeholder, .kp-textarea::placeholder{ color:#9aa0a6; }
      .kp-input:focus, .kp-select:focus, .kp-textarea:focus{
        border-color:#f3b2b6; box-shadow:0 0 0 3px rgba(232,77,91,.12);
      }
      .kp-textarea{ min-height:96px; resize:vertical; }

      /* Buttons */
      .kp-btn{border:none;border-radius:10px;padding:10px 16px;font-weight:600}
      .kp-btn-secondary{background:#f3f4f6}
      .kp-btn-secondary:hover{filter:brightness(.97)}
      .kp-btn-danger{background:#e84d5b;color:#fff}
      .kp-btn-danger:hover{filter:brightness(.96)}

      /* Align actions */
      .kp-actions{text-align:right;margin-top:16px}

      :root{ --rosa-50:#fff4f5; --rojo:#e84d5b; --borde:#ececec; }

      /* Contenedor principal centrado con espacio a los costados */
       .kp-wrap, .gp-wrap {
           max-width: 1450px;    /* ancho máximo del contenido */
           margin: 30px auto;    /* centra y agrega espacio arriba/abajo */
           padding: 0 15px;      /* espacio interno a los costados */
       }
      .gp-title{font-size:22px;font-weight:600;margin:4px 0 16px}
      .btn-rosa{background:var(--rojo);color:#fff;border:none;border-radius:10px;padding:10px 14px;font-weight:600}
      .btn-rosa:hover{filter:brightness(.95)}

      .card-soft{background:#fff;border:1px solid var(--borde);border-radius:14px;padding:16px}
      .filters{display:grid;grid-template-columns:1.4fr 1fr 1fr;gap:14px}

      /* buscador con ícono */
      .searchbox{position:relative}
      .searchbox .icon{position:absolute;left:12px;top:50%;transform:translateY(-50%);opacity:.6}
      .inpt{width:100%;border:1px solid var(--borde);border-radius:10px;background:#fff;height:42px;padding:8px 12px 8px 38px}
      .sel{width:100%;border:1px solid var(--borde);border-radius:10px;background:#fff;height:42px;padding:8px 12px}
      .inpt:focus,.sel:focus{outline:none;border-color:#f3b2b6;box-shadow:0 0 0 3px rgba(232,77,91,.12)}

      /* tabla rosada como en la imagen */
      .table thead th{background:var(--rosa-50);font-weight:700}
      .table td,.table th{vertical-align:middle}
      .i-btn{border:none;border-radius:10px;background:#f3f4f6;width:36px;height:36px;display:inline-flex;align-items:center;justify-content:center}
      .i-btn:hover{filter:brightness(.96)}
      .i-btn.danger{background:#f3e3e5}
      .stock-low{color:#c0392b;font-weight:600}
      .badge-cube{font-size:16px;opacity:.7}

    </style>
</asp:Content>

<asp:Content ID="Main" ContentPlaceHolderID="MainContent" runat="server">
    <div class="kp-wrap">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>Gestión de Productos</h2>
            <asp:Button ID="btnNuevo" runat="server" CssClass="btn-rosa" Text="+  Nuevo Producto" OnClick="btnNuevo_Click" />
        </div>


        <div class="kp-card">
        
            <div class="card-soft" style="margin-bottom:14px">
              <div class="filters">
                <div class="searchbox">
                  <span class="icon">🔍</span>
                  <asp:TextBox ID="txtBuscar" runat="server" CssClass="inpt"
                               placeholder="Buscar por código, nombre o color..." />
                </div>
                <asp:DropDownList ID="ddlCategoria" runat="server" CssClass="sel">
                  <asp:ListItem Text="Todas las categorías" Value="" />
                  <asp:ListItem>Oxford</asp:ListItem>
                  <asp:ListItem>Derby</asp:ListItem>
                  <asp:ListItem>Casual</asp:ListItem>
                </asp:DropDownList>
                <asp:DropDownList ID="ddlStock" runat="server" CssClass="sel">
                  <asp:ListItem Text="Todos" Value="" />
                  <asp:ListItem Text="Con stock" Value="ok" />
                  <asp:ListItem Text="Stock bajo (≤8)" Value="low" />
                  <asp:ListItem Text="Agotado" Value="zero" />
                </asp:DropDownList>
              </div>
            </div>


            <div class="card-soft">
              <asp:GridView ID="gvProductos" runat="server" AutoGenerateColumns="False" CssClass="table table-hover" OnRowCommand="gvProductos_RowCommand" OnRowDataBound="gvProductos_RowDataBound">
                <Columns>
                  <asp:BoundField DataField="Codigo" HeaderText="Código" />
                  <asp:BoundField DataField="Nombre" HeaderText="Producto" />
                  <asp:BoundField DataField="Categoria" HeaderText="Categoría" />
                  <asp:BoundField DataField="Color" HeaderText="Color" />
                  <asp:BoundField DataField="Precio" HeaderText="Precio" DataFormatString="S/. {0:0.00}" />
                  <%-- Stock como en la captura --%>
                  <asp:TemplateField HeaderText="Stock">
                    <ItemTemplate>
                      <span runat="server" id="spStock"></span>&nbsp;<span class="badge-cube">⬡</span>
                    </ItemTemplate>
                  </asp:TemplateField>
                  <asp:TemplateField HeaderText="Acciones">
                    <ItemTemplate>
                      <div class="acciones">
                        <asp:Button runat="server" Text="✏️" ToolTip="Editar"
                          CommandName="Editar" CommandArgument='<%# Container.DisplayIndex %>'
                          CssClass="i-btn" />
                        <asp:Button runat="server" Text="🗑" ToolTip="Eliminar"
                          CommandName="Eliminar" CommandArgument='<%# Container.DisplayIndex %>'
                          CssClass="i-btn danger" />
                      </div>
                    </ItemTemplate>
                  </asp:TemplateField>
                </Columns>
              </asp:GridView>
            </div>

        <!-- MODAL-->
        <asp:Panel ID="maskModal" runat="server" CssClass="kp-modal-mask" Visible="false">
          <div class="kp-modal">
            <div class="kp-modal-header">
              <div class="kp-modal-title"><asp:Literal ID="litTituloModal" runat="server" Text="Nuevo Producto" /></div>
              <asp:Button ID="btnCerrarModal" runat="server" Text="✕" CssClass="kp-close" OnClick="btnCerrarModal_Click" />
            </div>

            <!-- Fila 1: Nombre / Precio -->
            <div class="row2">
              <div>
                <label class="kp-label">Nombre del producto</label>
                <asp:TextBox ID="txtNombre" runat="server" CssClass="kp-input" placeholder="Ej: Oxford Clásico Negro" />
              </div>
              <div>
                <label class="kp-label">Precio (S/)</label>
                <asp:TextBox ID="txtPrecio" runat="server" CssClass="kp-input" placeholder="289.90" />
              </div>
            </div>

            <!-- Fila 2: Precio original / Descuento -->
            <div class="row2">
              <div>
                <label class="kp-label">Precio original (opcional)</label>
                <asp:TextBox ID="txtPrecioOriginal" runat="server" CssClass="kp-input" placeholder="349.90" />
              </div>
              <div>
                <label class="kp-label">Descuento % (opcional)</label>
                <asp:TextBox ID="txtDescuento" runat="server" CssClass="kp-input" placeholder="15" />
              </div>
            </div>

            <!-- Fila 3: Categoría / Estilo / Color -->
            <div class="row3">
              <div>
                <label class="kp-label">Categoría</label>
                <asp:DropDownList ID="ddlCatForm" runat="server" CssClass="kp-select">
                  <asp:ListItem>Oxford</asp:ListItem>
                  <asp:ListItem>Derby</asp:ListItem>
                  <asp:ListItem>Casual</asp:ListItem>
                </asp:DropDownList>
              </div>
              <div>
                <label class="kp-label">Estilo</label>
                <asp:DropDownList ID="ddlEstilo" runat="server" CssClass="kp-select">
                  <asp:ListItem>Clásicos</asp:ListItem>
                  <asp:ListItem>Premium</asp:ListItem>
                  <asp:ListItem>Modernos</asp:ListItem>
                </asp:DropDownList>
              </div>
              <div>
                <label class="kp-label">Color</label>
                <asp:DropDownList ID="ddlColor" runat="server" CssClass="kp-select">
                  <asp:ListItem>Negro</asp:ListItem>
                  <asp:ListItem>Marrón</asp:ListItem>
                  <asp:ListItem>Beige</asp:ListItem>
                </asp:DropDownList>
              </div>
            </div>

            <!-- Fila 4: Stock / Tallas -->
            <div class="row2">
              <div>
                <label class="kp-label">Stock disponible</label>
                <asp:TextBox ID="txtStock" runat="server" CssClass="kp-input" placeholder="50" />
              </div>
              <div>
                <label class="kp-label">Tallas (separadas por coma)</label>
                <asp:TextBox ID="txtTallas" runat="server" CssClass="kp-input" placeholder="35,36,37,38,39" />
              </div>
            </div>

            <!-- URL imagen -->
            <div class="row1">
              <label class="kp-label">URL de imagen</label>
              <asp:TextBox ID="txtImagen" runat="server" CssClass="kp-input" placeholder="https://…" />
            </div>

            <!-- Descripción -->
            <div class="row1">
              <label class="kp-label">Descripción</label>
              <asp:TextBox ID="txtDescripcion" runat="server" CssClass="kp-textarea" TextMode="MultiLine"
                           placeholder="Descripción del producto…" />
            </div>

            <!-- Material -->
            <div class="row1">
              <label class="kp-label">Material</label>
              <asp:TextBox ID="txtMaterial" runat="server" CssClass="kp-input" placeholder="Cuero genuino…" />
            </div>

            <!-- Acciones -->
            <div class="kp-actions">
              <asp:HiddenField ID="hfEditIndex" runat="server" />
              <asp:Button ID="btnCancelar" runat="server" Text="Cancelar" CssClass="kp-btn kp-btn-secondary me-2"
                          OnClick="btnCerrarModal_Click" />
              <asp:Button ID="btnGuardar" runat="server" Text="Crear Producto" CssClass="kp-btn kp-btn-danger"
                          OnClick="btnGuardar_Click" />
            </div>
          </div>
        </asp:Panel>

        </div>
    </div>
</asp:Content>
