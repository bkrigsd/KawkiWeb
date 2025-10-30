package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.DetalleCarritoBO;
import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;

@WebService(serviceName="DetalleCarrito")
public class DetalleCarrito {
    
    private DetalleCarritoBO detalleCarritoBO;

    public DetalleCarrito() {
        this.detalleCarritoBO = new DetalleCarritoBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "carritoId") Integer carritoId,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "precioUnitario") Double precioUnitario,
            @WebParam(name = "subtotal") Double subtotal,
            @WebParam(name = "producto") ProductoVarianteDTO producto) {

        return this.detalleCarritoBO.insertar(carritoId, cantidad,
                precioUnitario, subtotal, producto);
    }

    @WebMethod(operationName = "obtenerPorId")
    public DetalleCarritoDTO obtenerPorId(
            @WebParam(name = "detalleCarritoId") Integer detalleCarritoId) {
        return this.detalleCarritoBO.obtenerPorId(detalleCarritoId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<DetalleCarritoDTO> listarTodos() {
        return this.detalleCarritoBO.listarTodos();
    }

    @WebMethod(operationName = "listarPorCarritoId")
    public ArrayList<DetalleCarritoDTO> listarPorCarritoId(
            @WebParam(name = "carritoId") Integer carritoId) {

        return this.detalleCarritoBO.listarPorCarritoId(carritoId);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "detalleCarritoId") Integer detalleCarritoId,
            @WebParam(name = "carritoId") Integer carritoId,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "precioUnitario") Double precioUnitario,
            @WebParam(name = "subtotal") Double subtotal,
            @WebParam(name = "producto") ProductoVarianteDTO producto) {

        return this.detalleCarritoBO.modificar(detalleCarritoId, carritoId,
                cantidad, precioUnitario, subtotal, producto);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(
            @WebParam(name = "detalleCarritoId") Integer detalleCarritoId) {
        return this.detalleCarritoBO.eliminar(detalleCarritoId);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "DetalleCarrito Web " + txt + " !";
    }
}
