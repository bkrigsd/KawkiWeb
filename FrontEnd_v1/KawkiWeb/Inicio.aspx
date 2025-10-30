<%@ Page Title="Inicio - Kawki" Language="C#" MasterPageFile="~/KawkiWeb.master"
    AutoEventWireup="true" CodeBehind="Inicio.aspx.cs" Inherits="KawkiWeb.Inicio" %>

<asp:Content ID="HeadExtra" ContentPlaceHolderID="HeadContent" runat="server">
    <style>
        .welcome-section p { font-size:15px; }
    </style>
</asp:Content>

<asp:Content ID="PageMain" ContentPlaceHolderID="MainContent" runat="server">
    <!-- Sección Bienvenida -->
    <section class="welcome-section">
        <h1>Bienvenida a Kawki</h1>
        <p>
            Somos una marca peruana especializada en calzado femenino de alta calidad.
            Nuestros zapatos Oxford y Derby combinan diseño contemporáneo con técnicas
            artesanales tradicionales, creando piezas únicas que destacan por su
            elegancia y comodidad.
        </p>
        <asp:Button ID="BtnVerProductos" runat="server"
            CssClass="btn-primary-custom"
            Text="Ver Productos"
            OnClick="BtnVerProductos_Click" />
    </section>

    <section class="features-section">
        <div class="features-row">
            <div class="feature-card">
                <h3>Calidad Artesanal</h3>
                <p>Cada par es elaborado a mano por artesanos peruanos con años de experiencia.</p>
            </div>
            <div class="feature-card">
                <h3>Diseño Versátil</h3>
                <p>Modelos pensados para la mujer moderna que busca estilo y comodidad.</p>
            </div>
            <div class="feature-card">
                <h3>Materiales Premium</h3>
                <p>Utilizamos materiales de primera calidad para garantizar durabilidad.</p>
            </div>
        </div>
    </section>

    <script>
        
        (function () {
            const onScrollReveal = function () {
                $('.feature-card').each(function () {
                    const position = $(this).offset().top;
                    const scrollPosition = $(window).scrollTop() + $(window).height();
                    if (scrollPosition > position + 50) {
                        $(this).css({ opacity: 1, transform: 'translateY(0)' });
                    }
                });
            };
            
            $('.feature-card').css({ opacity: 0, transform: 'translateY(10px)', transition: 'opacity .6s ease, transform .6s ease' });
            onScrollReveal();
            $(window).on('scroll', onScrollReveal);
        })();
    </script>
</asp:Content>
