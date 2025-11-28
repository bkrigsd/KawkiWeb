document.addEventListener("DOMContentLoaded", function () {
    // Animaciones de fade-in fluidas, todas al mismo tiempo
    $('.login-info-icon').hide().fadeIn(800);
    $('.login-info-text').hide().fadeIn(800);

    $('.login-benefits li').hide().fadeIn(800);

    $('.login-card').hide().fadeIn(800);

    // Toggle contraseña
    const txt = document.getElementById("<%= txtClave.ClientID %>");
    const toggle = document.getElementById("toggleClave");

    toggle.addEventListener("click", () => {
        const mostrando = txt.getAttribute("type") === "text";
        txt.setAttribute("type", mostrando ? "password" : "text");
        toggle.classList.toggle("fa-eye");
        toggle.classList.toggle("fa-eye-slash");
        toggle.setAttribute("title", mostrando ? "Mostrar contraseña" : "Ocultar contraseña");
    });

    // Selector de rol (solo visual, autocompleta usuario)
    const roleSwitch = document.getElementById("roleSwitch");
    const usuarioInput = document.getElementById("<%= txtUsuario.ClientID %>");

    roleSwitch.querySelectorAll(".role-pill").forEach(btn => {
        btn.addEventListener("click", () => {
            roleSwitch.querySelectorAll(".role-pill").forEach(b => b.classList.remove("active"));
            btn.classList.add("active");

            const rol = btn.getAttribute("data-role");
            if (rol === "vendedor") {
                usuarioInput.value = "vendedor";
            } else if (rol === "admin") {
                usuarioInput.value = "admin";
            }
        });
    });
});