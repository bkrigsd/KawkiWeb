package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.DetalleVentasBO;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;

@WebService(serviceName = "DetalleVentasService")
public class DetalleVentas {

    private DetalleVentasBO detalleVentaBO;

    public DetalleVentas() {
        this.detalleVentaBO = new DetalleVentasBO();
    }

    @WebMethod(operationName = "insertarDetalleVenta")
    public Integer insertarDetalleVenta(
            @WebParam(name = "productoVar") ProductosVariantesDTO productoVar,
            @WebParam(name = "ventaId") Integer ventaId,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "precioUnitario") Double precioUnitario,
            @WebParam(name = "subtotal") Double subtotal) {

        return this.detalleVentaBO.insertar(productoVar, ventaId, cantidad,
                precioUnitario, subtotal);
    }

    @WebMethod(operationName = "obtenerPorIdDetalleVenta")
    public DetalleVentasDTO obtenerPorIdDetalleVenta(
            @WebParam(name = "detalleId") Integer detalleId) {
        return this.detalleVentaBO.obtenerPorId(detalleId);
    }

    @WebMethod(operationName = "listarTodosDetalleVenta")
    public ArrayList<DetalleVentasDTO> listarTodosDetalleVenta() {
        return new ArrayList<>(this.detalleVentaBO.listarTodos());
    }

    @WebMethod(operationName = "listarPorVentaIdDetalleVenta")
    public ArrayList<DetalleVentasDTO> listarPorVentaIdDetalleVenta(
            @WebParam(name = "ventaId") Integer ventaId) {

        return new ArrayList<>(this.detalleVentaBO.listarPorVentaId(ventaId));
    }

    @WebMethod(operationName = "modificarDetalleVenta")
    public Integer modificarDetalleVenta(
            @WebParam(name = "detalleVentaId") Integer detalleVentaId,
            @WebParam(name = "productoVar") ProductosVariantesDTO productoVar,
            @WebParam(name = "ventaId") Integer ventaId,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "precioUnitario") Double precioUnitario,
            @WebParam(name = "subtotal") Double subtotal) {

        return this.detalleVentaBO.modificar(detalleVentaId, productoVar,
                ventaId, cantidad, precioUnitario, subtotal);
    }

    @WebMethod(operationName = "eliminarDetalleVenta")
    public Integer eliminarDetalleVenta(
            @WebParam(name = "detalleVentaId") Integer detalleVentaId) {
        return this.detalleVentaBO.eliminar(detalleVentaId);
    }
}
