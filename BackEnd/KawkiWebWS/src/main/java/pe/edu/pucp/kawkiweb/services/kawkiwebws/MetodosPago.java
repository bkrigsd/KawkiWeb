package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.MetodosPagoBO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;

@WebService(serviceName = "MetodosPagoService")
public class MetodosPago {

    private MetodosPagoBO metodoPagoBO;

    public MetodosPago() {
        this.metodoPagoBO = new MetodosPagoBO();
    }

    @WebMethod(operationName = "obtenerPorIdMetodoPago")
    public MetodosPagoDTO obtenerPorIdMetodoPago(
            @WebParam(name = "metodoPagoId") Integer metodoPagoId) {
        return this.metodoPagoBO.obtenerPorId(metodoPagoId);
    }

    @WebMethod(operationName = "listarTodosMetodoPago")
    public ArrayList<MetodosPagoDTO> listarTodosMetodoPago() {
        return new ArrayList<>(this.metodoPagoBO.listarTodos());
    }
}
