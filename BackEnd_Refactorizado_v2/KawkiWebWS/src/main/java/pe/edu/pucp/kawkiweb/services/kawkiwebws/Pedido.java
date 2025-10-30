package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.PedidoBO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;

@WebService(serviceName = "Pedido")
public class Pedido {

    private PedidoBO pedidoBO;

    public Pedido() {
        this.pedidoBO = new PedidoBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "usuario") UsuarioDTO usuario,
            @WebParam(name = "fechaHoraCreacion") LocalDateTime fechaHoraCreacion,
            @WebParam(name = "fechaHoraUltimoEstado") LocalDateTime fechaHoraUltimoEstado,
            @WebParam(name = "total") Double total,
            @WebParam(name = "estadoPedido") EstadoPedidoDTO estadoPedido,
            @WebParam(name = "promocion") PromocionDTO promocion) {

        return this.pedidoBO.insertar(usuario, fechaHoraCreacion, fechaHoraUltimoEstado,
                total, estadoPedido, promocion);
    }

    @WebMethod(operationName = "obtenerPorId")
    public PedidoDTO obtenerPorId(@WebParam(name = "pedidoId") Integer pedidoId) {
        return this.pedidoBO.obtenerPorId(pedidoId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<PedidoDTO> listarTodos() {
        return this.pedidoBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "pedidoId") Integer pedidoId,
            @WebParam(name = "usuario") UsuarioDTO usuario,
            @WebParam(name = "fechaHoraCreacion") LocalDateTime fechaHoraCreacion,
            @WebParam(name = "fechaHoraUltimoEstado") LocalDateTime fechaHoraUltimoEstado,
            @WebParam(name = "total") Double total,
            @WebParam(name = "estadoPedido") EstadoPedidoDTO estadoPedido,
            @WebParam(name = "promocion") PromocionDTO promocion) {

        return this.pedidoBO.modificar(pedidoId, usuario, fechaHoraCreacion,
                fechaHoraUltimoEstado, total, estadoPedido, promocion);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "pedidoId") Integer pedidoId) {
        return this.pedidoBO.eliminar(pedidoId);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Pedido Web " + txt + " !";
    }
}
