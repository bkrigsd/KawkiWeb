// login-modern.js
$(document).ready(function () {
    $("#cambiar-formulario").on("click", function () {
        if ($("#formularioLogin").hasClass("activo")) {
            $("#formularioLogin").removeClass("activo");
            $("#formularioRegistro").addClass("activo");
            $("#titulo-panel").text("¡Bienvenido de nuevo!");
            $("#texto-panel").text("¿Ya tienes una cuenta?");
            $("#cambiar-formulario").text("Inicia sesión aquí");
            $("#icono-panel").removeClass("fa-sign-in-alt").addClass("fa-user-plus");
        } else {
            $("#formularioRegistro").removeClass("activo");
            $("#formularioLogin").addClass("activo");
            $("#titulo-panel").text("¡Hola, bienvenido!");
            $("#texto-panel").text("¿No tienes cuenta?");
            $("#cambiar-formulario").text("Regístrate aquí");
            $("#icono-panel").removeClass("fa-user-plus").addClass("fa-sign-in-alt");
        }
    });
});
