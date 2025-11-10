package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.PagoBO;
import pe.edu.pucp.kawkiweb.model.PagoDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;

@WebService(serviceName = "Pago")
public class Pago {

    private PagoBO pagoBO;

    public Pago() {
        this.pagoBO = new PagoBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "montoTotal") Double montoTotal,
            @WebParam(name = "fechaHoraPago") LocalDateTime fechaHoraPago,
            @WebParam(name = "metodoPago") MetodosPagoDTO metodoPago,
            @WebParam(name = "pedido") VentasDTO pedido) {

        return this.pagoBO.insertar(montoTotal, fechaHoraPago, metodoPago, pedido);
    }

    @WebMethod(operationName = "obtenerPorId")
    public PagoDTO obtenerPorId(@WebParam(name = "pagoId") Integer pagoId) {
        return this.pagoBO.obtenerPorId(pagoId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<PagoDTO> listarTodos() {
        return this.pagoBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "pagoId") Integer pagoId,
            @WebParam(name = "montoTotal") Double montoTotal,
            @WebParam(name = "fechaHoraPago") LocalDateTime fechaHoraPago,
            @WebParam(name = "metodoPago") MetodosPagoDTO metodoPago,
            @WebParam(name = "pedido") VentasDTO pedido) {

        return this.pagoBO.modificar(pagoId, montoTotal, fechaHoraPago, metodoPago, pedido);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "pagoId") Integer pagoId) {
        return this.pagoBO.eliminar(pagoId);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Pago Web " + txt + " !";
    }
}
