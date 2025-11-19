package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.TiposComprobanteBO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

@WebService(serviceName = "TiposComprobanteService")
public class TiposComprobante {

    private TiposComprobanteBO tipoComprobanteBO;

    public TiposComprobante() {
        this.tipoComprobanteBO = new TiposComprobanteBO();
    }

    @WebMethod(operationName = "obtenerPorIdTipoComprobante")
    public TiposComprobanteDTO obtenerPorIdTipoComprobante(
            @WebParam(name = "tipoComprobanteId") Integer tipoComprobanteId) {
        return this.tipoComprobanteBO.obtenerPorId(tipoComprobanteId);
    }

    @WebMethod(operationName = "listarTodosTipoComprobante")
    public ArrayList<TiposComprobanteDTO> listarTodosTipoComprobante() {
        return new ArrayList<>(this.tipoComprobanteBO.listarTodos());
    }
}
