package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.MovimientoInventarioBO;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

@WebService(serviceName = "MovimientoInventario")
public class MovimientoInventario {

    private MovimientoInventarioBO movimientoInventarioBO;

    public MovimientoInventario() {
        this.movimientoInventarioBO = new MovimientoInventarioBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "fecha_hora_mov") LocalDateTime fecha_hora_mov,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "tipo_movimiento") TiposMovimientoDTO tipo_movimiento,
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante) {

        return this.movimientoInventarioBO.insertar(cantidad, fecha_hora_mov,
                observacion, tipo_movimiento, prod_variante);
    }

    @WebMethod(operationName = "obtenerPorId")
    public MovimientosInventarioDTO obtenerPorId(
            @WebParam(name = "movInventarioId") Integer movInventarioId) {
        return this.movimientoInventarioBO.obtenerPorId(movInventarioId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<MovimientosInventarioDTO> listarTodos() {
        return this.movimientoInventarioBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "mov_inventario_id") Integer mov_inventario_id,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "fecha_hora_mov") LocalDateTime fecha_hora_mov,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "tipo_movimiento") TiposMovimientoDTO tipo_movimiento,
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante) {

        return this.movimientoInventarioBO.modificar(mov_inventario_id, cantidad,
                fecha_hora_mov, observacion, tipo_movimiento, prod_variante);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(
            @WebParam(name = "movInventarioId") Integer movInventarioId) {
        return this.movimientoInventarioBO.eliminar(movInventarioId);
    }

    @WebMethod(operationName = "crearAjusteInventario")
    public Integer crearAjusteInventario(
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "stockReal") Integer stockReal,
            @WebParam(name = "observacion") String observacion) {

        return this.movimientoInventarioBO.crearAjusteInventario(prod_variante,
                stockReal, observacion);
    }

    @WebMethod(operationName = "registrarIngreso")
    public Integer registrarIngreso(
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "observacion") String observacion) {

        return this.movimientoInventarioBO.registrarIngreso(prod_variante,
                cantidad, observacion);
    }

    @WebMethod(operationName = "registrarSalida")
    public Integer registrarSalida(
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "observacion") String observacion) {

        return this.movimientoInventarioBO.registrarSalida(prod_variante,
                cantidad, observacion);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "MovimientoInventario Web " + txt + " !";
    }
}
