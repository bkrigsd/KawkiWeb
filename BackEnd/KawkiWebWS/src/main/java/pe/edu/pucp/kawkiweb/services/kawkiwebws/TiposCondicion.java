package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.TiposCondicionBO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

@WebService(serviceName = "TiposCondicionService")
public class TiposCondicion {

    private TiposCondicionBO tipoCondicionBO;

    public TiposCondicion() {
        this.tipoCondicionBO = new TiposCondicionBO();
    }

    @WebMethod(operationName = "obtenerPorIdTipoCondicion")
    public TiposCondicionDTO obtenerPorIdTipoCondicion(
            @WebParam(name = "tipoCondicionId") Integer tipoCondicionId) {
        return this.tipoCondicionBO.obtenerPorId(tipoCondicionId);
    }

    @WebMethod(operationName = "listarTodosTipoCondicion")
    public ArrayList<TiposCondicionDTO> listarTodosTipoCondicion() {
        return new ArrayList<>(this.tipoCondicionBO.listarTodos());
    }
}
