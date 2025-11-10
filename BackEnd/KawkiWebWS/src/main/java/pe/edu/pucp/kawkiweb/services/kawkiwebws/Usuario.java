package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.UsuarioBO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuarioDTO;

@WebService(serviceName = "Usuario")
public class Usuario {

    private UsuarioBO usuarioBO;

    public Usuario() {
        this.usuarioBO = new UsuarioBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apePaterno") String apePaterno,
            @WebParam(name = "apeMaterno") String apeMaterno,
            @WebParam(name = "dni") String dni,
            @WebParam(name = "fechaNacimiento") LocalDate fechaNacimiento,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contrasenha") String contrasenha,
            @WebParam(name = "fechaHoraCreacion") LocalDateTime fechaHoraCreacion,
            @WebParam(name = "tipoUsuario") TipoUsuarioDTO tipoUsuario) {

        return this.usuarioBO.insertar(nombre, apePaterno, apeMaterno, dni,
                fechaNacimiento, telefono, direccion, correo, nombreUsuario,
                contrasenha, fechaHoraCreacion, tipoUsuario);
    }

    @WebMethod(operationName = "obtenerPorId")
    public UsuarioDTO obtenerPorId(@WebParam(name = "Id") Integer usuarioId) {
        return this.usuarioBO.obtenerPorId(usuarioId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<UsuarioDTO> listarTodos() {
        return this.usuarioBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "usuarioId") Integer usuarioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apePaterno") String apePaterno,
            @WebParam(name = "apeMaterno") String apeMaterno,
            @WebParam(name = "dni") String dni,
            @WebParam(name = "fechaNacimiento") LocalDate fechaNacimiento,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contrasenha") String contrasenha,
            @WebParam(name = "fechaHoraCreacion") LocalDateTime fechaHoraCreacion,
            @WebParam(name = "tipoUsuario") TipoUsuarioDTO tipoUsuario) {

        return this.usuarioBO.modificar(usuarioId, nombre, apePaterno, apeMaterno,
                dni, fechaNacimiento, telefono, direccion, correo, nombreUsuario,
                contrasenha, fechaHoraCreacion, tipoUsuario);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "usuarioId") Integer usuarioId) {
        return this.usuarioBO.eliminar(usuarioId);
    }

    @WebMethod(operationName = "listarPorTipo")
    public ArrayList<UsuarioDTO> listarPorTipo(
            @WebParam(name = "tipoUsuarioId") Integer tipoUsuarioId) {

        return this.usuarioBO.listarPorTipo(tipoUsuarioId);
    }

    @WebMethod(operationName = "cambiarContrasenha")
    public boolean cambiarContrasenha(
            @WebParam(name = "usuarioId") Integer usuarioId,
            @WebParam(name = "contrasenhaActual") String contrasenhaActual,
            @WebParam(name = "contrasenhaNueva") String contrasenhaNueva) {

        return this.usuarioBO.cambiarContrasenha(usuarioId, contrasenhaActual,
                contrasenhaNueva);
    }

    @WebMethod(operationName = "autenticar")
    public UsuarioDTO autenticar(
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contrasenha") String contrasenha) {

        return this.usuarioBO.autenticar(nombreUsuario, contrasenha);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Usuario Web " + txt + " !";
    }
}
