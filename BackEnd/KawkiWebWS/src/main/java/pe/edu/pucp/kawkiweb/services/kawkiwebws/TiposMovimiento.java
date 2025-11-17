package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.TiposMovimientoBO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

@WebService(serviceName="TiposMovimientoService")
public class TiposMovimiento {
    
    private TiposMovimientoBO tipoMovimientoBO;
    
    public TiposMovimiento(){
        this.tipoMovimientoBO=new TiposMovimientoBO();
    }
    
    @WebMethod(operationName = "obtenerPorIdTipoMovimiento")
    public TiposMovimientoDTO obtenerPorIdTipoMovimiento(
            @WebParam(name = "tipoMovimientoId") Integer tipoMovimientoId) {
        return this.tipoMovimientoBO.obtenerPorId(tipoMovimientoId);
    }

    @WebMethod(operationName = "listarTodosTipoMovimiento")
    public ArrayList<TiposMovimientoDTO> listarTodosTipoMovimiento() {
        return new ArrayList<>(this.tipoMovimientoBO.listarTodos());
    }
}
