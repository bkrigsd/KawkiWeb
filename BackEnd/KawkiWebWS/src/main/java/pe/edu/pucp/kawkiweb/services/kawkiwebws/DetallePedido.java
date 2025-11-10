package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.DetallePedidoBO;
import pe.edu.pucp.kawkiweb.model.DetallePedidoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;

@WebService(serviceName="DetallePedido")
public class DetallePedido {
    
    private DetallePedidoBO detallePedidoBO;

    public DetallePedido() {
        this.detallePedidoBO = new DetallePedidoBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "productoVar") ProductoVarianteDTO productoVar,
            @WebParam(name = "pedidoId") Integer pedidoId,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "precioUnitario") Double precioUnitario,
            @WebParam(name = "subtotal") Double subtotal) {

        return this.detallePedidoBO.insertar(productoVar, pedidoId, cantidad,
                precioUnitario, subtotal);
    }

    @WebMethod(operationName = "obtenerPorId")
    public DetallePedidoDTO obtenerPorId(
            @WebParam(name = "detalleId") Integer detalleId) {
        return this.detallePedidoBO.obtenerPorId(detalleId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<DetallePedidoDTO> listarTodos() {
        return this.detallePedidoBO.listarTodos();
    }

    @WebMethod(operationName = "listarPorPedidoId")
    public ArrayList<DetallePedidoDTO> listarPorPedidoId(
            @WebParam(name = "pedidoId") Integer pedidoId) {

        return this.detallePedidoBO.listarPorPedidoId(pedidoId);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "detallePedidoId") Integer detallePedidoId,
            @WebParam(name = "productoVar") ProductoVarianteDTO productoVar,
            @WebParam(name = "pedidoId") Integer pedidoId,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "precioUnitario") Double precioUnitario,
            @WebParam(name = "subtotal") Double subtotal) {

        return this.detallePedidoBO.modificar(detallePedidoId, productoVar,
                pedidoId, cantidad, precioUnitario, subtotal);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(
            @WebParam(name = "detallePedidoId") Integer detallePedidoId) {
        return this.detallePedidoBO.eliminar(detallePedidoId);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "DetallePedido Web " + txt + " !";
    }
}
