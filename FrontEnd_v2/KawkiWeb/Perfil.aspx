<%@ Page Title="Mi Perfil" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Perfil.aspx.cs" Inherits="KawkiWeb.Perfil" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        /* HERO PERFIL */
        .hero-perfil{
            margin:30px; padding:26px 28px; border-radius:14px;
            background: linear-gradient(135deg,#fff0f3, #fff);
            border:1px solid #f3dbe0;
        }
        .hero-perfil h1{
            font-size:26px; margin:0 0 6px 0;
        }
        .hero-perfil p{ margin:0; color:#666 }

        /* GRID PRINCIPAL */
        .grid-2{
            display:grid; gap:22px; margin:20px 30px;
            grid-template-columns: 2fr 1fr;
        }

        /* TARJETAS */
        .card-soft{
            background:#fff; border-radius:12px; padding:22px;
            box-shadow:0 2px 12px rgba(0,0,0,.06); border:1px solid #eee;
        }
        .card-soft h3{ font-size:18px; margin-bottom:16px; color:#333; }
        .card-soft i{ color:#ED6B7F; margin-right:6px; }

        /* INFO PERSONAL */
        .info-item{
            display:flex; align-items:center; gap:10px;
            margin-bottom:12px; color:#444;
        }
        .info-item i{ width:20px; }

        /* HISTORIAL */
        .historial-item{
            background:#fff6f7; border-radius:10px; padding:12px 16px;
            margin-bottom:10px; display:flex; justify-content:space-between;
            align-items:center; border:1px solid #ffe0e5;
        }
        .historial-item .codigo{ font-weight:600; color:#d85769; }
        .historial-item .detalle{ font-size:13px; color:#555; }
        .historial-item .precio{ color:#d85769; font-weight:600; }
        .historial-item .estado{
            background:#e7f9ef; color:#188a48; font-size:12px;
            border-radius:999px; padding:3px 10px;
        }

        /* PERFIL LATERAL */
        .perfil-lateral{
            display:flex; flex-direction:column; align-items:center; justify-content:center;
        }
        .perfil-avatar{
            background:#ED6B7F; color:#fff; border-radius:50%;
            width:110px; height:110px; display:flex;
            align-items:center; justify-content:center;
            font-size:40px; margin-bottom:10px;
        }
        .perfil-nombre{ font-size:20px; font-weight:600; color:#333; }
        .perfil-rol{ font-size:14px; color:#777; }

        .btn-cerrar{
            margin-top:20px; background:#fff; color:#ED6B7F;
            border:1px solid #ED6B7F; padding:8px 14px;
            border-radius:8px; font-weight:500; cursor:pointer;
            transition:all .2s;
        }

        .btn-cerrar:hover{ background:#ED6B7F; color:#fff; }
        .btn-eliminar{
            margin-top:10px; background:#fff; color:#b00020;
            border:1px solid #b00020; padding:8px 14px;
            border-radius:8px; font-weight:500; cursor:pointer;
            transition:all .2s;
        }
        .btn-eliminar:hover{ background:#b00020; color:#fff; }
        </style>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">

    <!-- HERO -->
    <div class="hero-perfil">
        <span class="pill"><i class="fas fa-user"></i> Perfil de Usuario</span>
        <h1>Mi Perfil</h1>
        <p>Aquí puedes revisar tu información personal, tu historial de compras y cerrar sesión cuando desees.</p>
    </div>

    <!-- CONTENIDO -->
    <div class="grid-2">

        <!-- IZQUIERDA -->
        <div>
            <!-- INFORMACIÓN PERSONAL -->
            <div class="card-soft">
                <h3><i class="fas fa-id-card"></i>Información Personal</h3>

                <div class="info-item">
                    <i class="fas fa-user"></i> 
                    <span><strong>Nombre Completo:</strong> <asp:Label ID="lblUsuario" runat="server" /></span>
                </div>

                <div class="info-item">
                    <i class="fas fa-envelope"></i> 
                    <span><strong>Email:</strong> <asp:Label ID="lblEmail" runat="server" /></span>
                </div>
            </div>

            <!-- HISTORIAL DE COMPRAS -->
            <%--<div id="historialDeComprasDiv" runat="server" class="card-soft" style="margin-top:20px;">
                <h3><i class="fas fa-box-open"></i>Historial de Compras</h3>

                <div class="historial-item">
                    <div>
                        <div class="codigo">#KWK-3421</div>
                        <div class="detalle">15/09/2025 • 2 productos</div>
                    </div>
                    <div>
                        <div class="precio">S/ 289.90</div>
                        <div class="estado">Entregado</div>
                    </div>
                </div>

                <div class="historial-item">
                    <div>
                        <div class="codigo">#KWK-3398</div>
                        <div class="detalle">02/09/2025 • 1 producto</div>
                    </div>
                    <div>
                        <div class="precio">S/ 159.90</div>
                        <div class="estado" style="background:#e4ecff; color:#2c63d6;">En tránsito</div>
                    </div>
                </div>
            </div>--%>
        </div>

        <!-- DERECHA -->
        <div class="card-soft perfil-lateral">
            <div class="perfil-avatar">
                <asp:Label ID="lblInicial" runat="server" Text="C"></asp:Label>
            </div>
            <div class="perfil-nombre">
                <asp:Label ID="lblUsuario2" runat="server" />
            </div>
            <div class="perfil-rol">
                <asp:Label ID="lblRol" runat="server" />
            </div>
            <asp:Button ID="btnCerrarSesion" runat="server" CssClass="btn-cerrar" Text="Cerrar Sesión"
                OnClick="btnCerrarSesion_Click" />
            <asp:Button ID="btnEliminarCuenta" runat="server" CssClass="btn-eliminar" 
                Text="Eliminar Cuenta" OnClick="btnEliminarCuenta_Click"
                OnClientClick="return confirm('¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.');" />

        </div>
    </div>

</asp:Content>
