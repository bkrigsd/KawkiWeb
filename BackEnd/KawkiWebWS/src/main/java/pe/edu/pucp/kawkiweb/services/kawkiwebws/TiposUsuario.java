package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.TiposUsuarioBO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

@WebService(serviceName="TiposUsuarioService")
public class TiposUsuario {
    
    private TiposUsuarioBO tipoUsuarioBO;
    
    public TiposUsuario(){
        this.tipoUsuarioBO=new TiposUsuarioBO();
    }
    
    @WebMethod(operationName = "obtenerPorIdTipoUsuario")
    public TiposUsuarioDTO obtenerPorIdTipoUsuario(
            @WebParam(name = "tipoUsuarioId") Integer tipoUsuarioId) {
        return this.tipoUsuarioBO.obtenerPorId(tipoUsuarioId);
    }

    @WebMethod(operationName = "listarTodosTipoUsuario")
    public ArrayList<TiposUsuarioDTO> listarTodosTipoUsuario() {
        return new ArrayList<>(this.tipoUsuarioBO.listarTodos());
    }
}
