<%@ Page Title="Nosotros" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Nosotros.aspx.cs" Inherits="KawkiWeb.Nosotros" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        /* HERO */
        .hero-nosotros{
            margin:30px; padding:26px 28px; border-radius:14px;
            background: linear-gradient(135deg,#fff0f3, #fff);
            border:1px solid #f3dbe0;
        }
        .hero-nosotros h1{
            font-size:26px; margin:0 0 6px 0;
        }
        .hero-nosotros p{ margin:0; color:#666 }
        .pill{
            display:inline-flex; align-items:center; gap:8px;
            background:#fff; border:1px solid #ffd6dc; color:#d85769;
            padding:6px 12px; border-radius:999px; font-size:13px; margin-bottom:10px;
        }

        /* GRID DE TARJETAS */
        .grid-3{
            display:grid; gap:22px; margin:20px 30px;
            grid-template-columns: repeat(auto-fit, minmax(260px,1fr));
        }
        .card-soft{
            background:#fff; border-radius:12px; padding:22px;
            box-shadow:0 2px 12px rgba(0,0,0,.06); border:1px solid #eee;
        }
        .card-soft h3{ font-size:18px; margin-bottom:10px }
        .card-soft i{ color:#ED6B7F; }

        /* LISTAS BONITAS */
        .list-dot{ margin:6px 0 0 0; padding-left:0; list-style:none;}
        .list-dot li{
            position:relative; padding-left:24px; margin:8px 0; color:#555;
        }
        .list-dot li:before{
            content:""; position:absolute; left:0; top:8px; width:8px; height:8px;
            background:#ED6B7F; border-radius:50%;
        }

        /* LÍNEA DE TIEMPO */
        .timeline{ position:relative; padding-left:28px; }
        .timeline:before{
            content:""; position:absolute; left:10px; top:0; bottom:0; width:2px; background:#ffe2e7;
        }
        .t-item{ position:relative; margin-bottom:14px; }
        .t-item:before{
            content:""; position:absolute; left:-2px; top:6px; width:12px; height:12px;
            background:#ED6B7F; border-radius:50%; box-shadow:0 0 0 4px #ffe2e7;
        }
        .t-year{ font-weight:600; color:#333 }
        .t-text{ color:#555 }

        /* VALORES */
        .badges{ display:flex; flex-wrap:wrap; gap:8px; }
        .badge-soft{
            background:#fff0f2; color:#d85769; border:1px solid #ffd6dc;
            padding:6px 12px; border-radius:999px; font-size:13px;
            display:inline-flex; align-items:center; gap:6px;
        }

        /* SECCIÓN IMAGEN */
        .about-media{
            display:grid; gap:22px; margin:20px 30px;
            grid-template-columns: repeat(auto-fit, minmax(280px,1fr));
        }
        .photo{
            background:#fff; border:1px solid #eee; border-radius:12px; overflow:hidden;
            box-shadow:0 2px 12px rgba(0,0,0,.06);
        }
        .photo img{ width:100%; height:260px; object-fit:cover; display:block; }
        .caption{ padding:12px 14px; color:#666; font-size:14px }
    </style>
</asp:Content>

<asp:Content ID="Main" ContentPlaceHolderID="MainContent" runat="server">

    <!-- HERO -->
    <div class="hero-nosotros">
        <span class="pill"><i class="fas fa-feather"></i> Hecho a mano en Perú</span>
        <h1>Acerca de Kawki</h1>
        <p>
            Kawki es una marca peruana especializada en calzado femenino de alta calidad, combinando diseño contemporáneo con técnicas artesanales tradicionales.
            Nuestro enfoque está en crear zapatos Oxford y Derby que no solo sean elegantes, sino también cómodos y duraderos. Cada par es elaborado cuidadosamente por artesanos peruanos con años de experiencia.
        </p>
    </div>

    <!-- 3 TARJETAS: filosofía, materiales, compromiso -->
    <div class="grid-3">
        <div class="card-soft">
            <h3><i class="fas fa-heart me-2"></i>Nuestra Filosofía</h3>
            <ul class="list-dot">
                <li>Calidad y artesanía en cada detalle.</li>
                <li>Diseños versátiles para la mujer moderna.</li>
                <li>Comodidad sin sacrificar el estilo.</li>
            </ul>
        </div>

        <div class="card-soft">
            <h3><i class="fas fa-leaf me-2"></i>Materiales Premium</h3>
            <ul class="list-dot">
                <li>Cuero seleccionado y forros suaves.</li>
                <li>Suela duradera y confortable.</li>
                <li>Acabados resistentes al uso diario.</li>
            </ul>
        </div>

        <div class="card-soft">
            <h3><i class="fas fa-hand-holding-heart me-2"></i>Compromiso Local</h3>
            <p class="mb-2">
                Trabajamos con talleres familiares y fomentamos la producción responsable.
            </p>
            <div class="badges">
                <span class="badge-soft"><i class="fas fa-recycle"></i> Producción consciente</span>
                <span class="badge-soft"><i class="fas fa-people-group"></i> Comercio justo</span>
                <span class="badge-soft"><i class="fas fa-map-marker-alt"></i> Hecho en Perú</span>
            </div>
        </div>
    </div>

    <!-- VALORES -->
    <div class="grid-3">

        <div class="card-soft">
            <h3><i class="fas fa-gem me-2"></i>Nuestros Valores</h3>
            <ul class="list-dot">
                <li>Excelencia en cada par de zapatos.</li>
                <li>Respeto por las personas y el oficio.</li>
                <li>Transparencia y mejora continua.</li>
                <li>Diseño atemporal que perdura.</li>
            </ul>
        </div>

        <div class="card-soft">
            <h3><i class="fas fa-headset me-2"></i>Atención Cercana</h3>
            <p>Queremos acompañarte antes y después de tu compra.</p>
            <div class="badges">
                <span class="badge-soft"><i class="fas fa-truck-fast"></i> Envíos a nivel nacional</span>
                <span class="badge-soft"><i class="fas fa-rotate"></i> Cambios sencillos</span>
                <span class="badge-soft"><i class="fas fa-comments"></i> Soporte rápido</span>
            </div>
        </div>
    </div>

</asp:Content>