package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.UsuariosBO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

@WebService(serviceName = "UsuariosService")
public class Usuarios {

    private UsuariosBO usuarioBO;

    public Usuarios() {
        this.usuarioBO = new UsuariosBO();
    }

    @WebMethod(operationName = "insertarUsuario")
    public Integer insertarUsuario(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apePaterno") String apePaterno,
            @WebParam(name = "dni") String dni,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contrasenha") String contrasenha,
            @WebParam(name = "tipoUsuario") TiposUsuarioDTO tipoUsuario,
            @WebParam(name = "activo") Boolean activo) {

        return this.usuarioBO.insertar(nombre, apePaterno, dni, telefono, correo,
                nombreUsuario, contrasenha, tipoUsuario, activo);
    }

    @WebMethod(operationName = "obtenerPorIdUsuario")
    public UsuariosDTO obtenerPorIdUsuario(
            @WebParam(name = "usuarioId") Integer usuarioId) {
        return this.usuarioBO.obtenerPorId(usuarioId);
    }

    @WebMethod(operationName = "listarTodosUsuario")
    public ArrayList<UsuariosDTO> listarTodosUsuario() {
        return new ArrayList<>(this.usuarioBO.listarTodos());
    }

    @WebMethod(operationName = "modificarUsuario")
    public Integer modificarUsuario(
            @WebParam(name = "usuarioId") Integer usuarioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apePaterno") String apePaterno,
            @WebParam(name = "dni") String dni,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contrasenha") String contrasenha,
            @WebParam(name = "tipoUsuario") TiposUsuarioDTO tipoUsuario,
            @WebParam(name = "activo") Boolean activo) {

        return this.usuarioBO.modificar(usuarioId, nombre, apePaterno, dni, telefono,
                correo, nombreUsuario, contrasenha, tipoUsuario, activo);
    }

    // Método eliminar comentado (igual que en el BO)
//    @WebMethod(operationName = "eliminarUsuario")
//    public Integer eliminarUsuario(
//            @WebParam(name = "usuarioId") Integer usuarioId) {
//        return this.usuarioBO.eliminar(usuarioId);
//    }
    @WebMethod(operationName = "cambiarContrasenhaUsuario")
    public boolean cambiarContrasenhaUsuario(
            @WebParam(name = "usuarioId") Integer usuarioId,
            @WebParam(name = "contrasenhaActual") String contrasenhaActual,
            @WebParam(name = "contrasenhaNueva") String contrasenhaNueva) {

        return this.usuarioBO.cambiarContrasenha(usuarioId, contrasenhaActual,
                contrasenhaNueva);
    }

    @WebMethod(operationName = "autenticarUsuario")
    public UsuariosDTO autenticarUsuario(
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contrasenha") String contrasenha) {

        return this.usuarioBO.autenticar(nombreUsuario, contrasenha);
    }
}
