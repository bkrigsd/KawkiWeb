<%@ Page Title="HistorialPedidos" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="HistorialPedidos.aspx.cs" Inherits="KawkiWeb.HistorialPedidos" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" runat="server">
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    
    <!-- HISTORIAL DE COMPRAS -->
    <div style="padding: 30px;">
        
        <!-- HEADER -->
        <div style="background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); margin-bottom: 30px;">
            <h1 style="font-size: 24px; font-weight: 400; color: #333; margin-bottom: 10px;">Historial de Compras</h1>
            <p style="color: #666; line-height: 1.6; margin: 0;">Aquí puedes consultar todos tus pedidos realizados, ver el estado actual de cada uno y acceder a los comprobantes de pago.</p>
        </div>

        <!-- LISTA DE PEDIDOS -->
        <asp:Repeater ID="rptPedidos" runat="server">
            <ItemTemplate>
                <div style="background-color: white; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); margin-bottom: 20px; overflow: hidden;">
                    
                    <!-- HEADER DEL PEDIDO -->
                    <div style="background-color: #f8f9fa; padding: 20px 30px; border-bottom: 1px solid #e0e0e0; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 15px;">
                        <div style="display: flex; gap: 30px; flex-wrap: wrap;">
                            <div style="display: flex; flex-direction: column;">
                                <span style="font-size: 12px; color: #999; text-transform: uppercase; margin-bottom: 5px;">N° PEDIDO</span>
                                <span style="font-size: 14px; color: #333; font-weight: 500;"><%# Eval("NumeroPedido") %></span>
                            </div>
                            <div style="display: flex; flex-direction: column;">
                                <span style="font-size: 12px; color: #999; text-transform: uppercase; margin-bottom: 5px;">FECHA</span>
                                <span style="font-size: 14px; color: #333; font-weight: 500;"><%# Eval("Fecha", "{0:dd/MM/yyyy}") %></span>
                            </div>
                            <div style="display: flex; flex-direction: column;">
                                <span style="font-size: 12px; color: #999; text-transform: uppercase; margin-bottom: 5px;">TOTAL</span>
                                <span style="font-size: 14px; color: #333; font-weight: 500;">S/ <%# Eval("Total", "{0:N2}") %></span>
                            </div>
                        </div>
                        <div style="padding: 8px 16px; border-radius: 20px; font-size: 13px; font-weight: 500; text-align: center; min-width: 140px; <%# GetEstadoStyle(Eval("Estado").ToString()) %>">
                            <%# Eval("Estado") %>
                        </div>
                    </div>

                    <!-- BODY DEL PEDIDO -->
                    <div style="padding: 30px;">
                        
                        <!-- LISTA DE PRODUCTOS -->
                        <div style="margin-bottom: 20px;">
                            <asp:Repeater ID="rptProductos" runat="server" DataSource='<%# Eval("Productos") %>'>
                                <ItemTemplate>
                                    <div style="display: flex; justify-content: space-between; align-items: center; padding: 15px 0; border-bottom: 1px solid #f0f0f0;">
                                        <div style="display: flex; gap: 15px; align-items: center; flex: 1;">
                                            <div style="background-color: #f8f9fa; padding: 5px 12px; border-radius: 4px; font-size: 14px; color: #666; min-width: 40px; text-align: center;">
                                                <%# Eval("Cantidad") %>x
                                            </div>
                                            <span style="color: #333; font-size: 15px;"><%# Eval("Nombre") %></span>
                                        </div>
                                        <span style="color: #ED6B7F; font-weight: 500; font-size: 15px;">S/ <%# Eval("Precio", "{0:N2}") %></span>
                                    </div>
                                </ItemTemplate>
                            </asp:Repeater>
                        </div>

                        <!-- FOOTER DEL PEDIDO -->
                        <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 20px; border-top: 2px solid #f0f0f0; margin-top: 20px; flex-wrap: wrap; gap: 15px;">
                            <asp:Button ID="btnVerComprobante" runat="server" 
                                Text="Ver Comprobante de Pago" 
                                CssClass="btn-primary-custom"
                                CommandArgument='<%# Eval("NumeroPedido") %>'
                                OnClick="btnVerComprobante_Click" />
                            <div style="font-size: 18px; color: #333;">
                                Total: <strong style="color: #ED6B7F; font-size: 20px; margin-left: 10px;">S/ <%# Eval("Total", "{0:N2}") %></strong>
                            </div>
                        </div>

                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>

        <!-- MENSAJE SI NO HAY PEDIDOS -->
        <asp:Panel ID="pnlSinPedidos" runat="server" Visible="false" 
            style="background-color: white; padding: 50px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); text-align: center;">
            <i class="fas fa-shopping-bag" style="font-size: 48px; color: #ccc; margin-bottom: 20px;"></i>
            <h3 style="font-size: 18px; font-weight: 400; color: #666; margin-bottom: 10px;">No tienes pedidos realizados</h3>
            <p style="color: #999; margin-bottom: 20px;">Cuando realices tu primera compra, aparecerá aquí tu historial.</p>
            <a href="Productos.aspx" class="btn-primary-custom" style="display: inline-block; text-decoration: none;">Ver Productos</a>
        </asp:Panel>

    </div>
</asp:Content>
