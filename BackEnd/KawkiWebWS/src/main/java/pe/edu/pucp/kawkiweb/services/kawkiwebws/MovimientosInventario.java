package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.MovimientosInventariosBO;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

@WebService(serviceName = "MovimientosInventarioService")
public class MovimientosInventario {

    private MovimientosInventariosBO movimientoInventarioBO;

    public MovimientosInventario() {
        this.movimientoInventarioBO = new MovimientosInventariosBO();
    }

    @WebMethod(operationName = "insertarMovInventario")
    public Integer insertarMovInventario(
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "tipo_movimiento") TiposMovimientoDTO tipo_movimiento,
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "usuario") UsuariosDTO usuario) {

        return this.movimientoInventarioBO.insertar(cantidad, observacion,
                tipo_movimiento, prod_variante, usuario);
    }

    @WebMethod(operationName = "obtenerPorIdMovInventario")
    public MovimientosInventarioDTO obtenerPorIdMovInventario(
            @WebParam(name = "movInventarioId") Integer movInventarioId) {
        return this.movimientoInventarioBO.obtenerPorId(movInventarioId);
    }

    @WebMethod(operationName = "listarTodosMovInventario")
    public ArrayList<MovimientosInventarioDTO> listarTodosMovInventario() {
        return new ArrayList<>(this.movimientoInventarioBO.listarTodos());
    }

    @WebMethod(operationName = "modificarMovInventario")
    public Integer modificarMovInventario(
            @WebParam(name = "mov_inventario_id") Integer mov_inventario_id,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "tipo_movimiento") TiposMovimientoDTO tipo_movimiento,
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "usuario") UsuariosDTO usuario) {

        return this.movimientoInventarioBO.modificar(mov_inventario_id, cantidad,
                observacion, tipo_movimiento, prod_variante, usuario);
    }

    @WebMethod(operationName = "eliminarMovInventario")
    public Integer eliminarMovInventario(
            @WebParam(name = "movInventarioId") Integer movInventarioId) {
        return this.movimientoInventarioBO.eliminar(movInventarioId);
    }

    @WebMethod(operationName = "crearAjusteInventarioMovInventario")
    public Integer crearAjusteInventarioMovInventario(
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "stockReal") Integer stockReal,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "usuario") UsuariosDTO usuario) {

        return this.movimientoInventarioBO.crearAjusteInventario(prod_variante,
                stockReal, observacion, usuario);
    }

    @WebMethod(operationName = "registrarIngresoMovInventario")
    public Integer registrarIngresoMovInventario(
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "usuario") UsuariosDTO usuario) {

        return this.movimientoInventarioBO.registrarIngreso(prod_variante,
                cantidad, observacion, usuario);
    }

    @WebMethod(operationName = "registrarSalidaMovInventario")
    public Integer registrarSalidaMovInventario(
            @WebParam(name = "prod_variante") ProductosVariantesDTO prod_variante,
            @WebParam(name = "cantidad") Integer cantidad,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "usuario") UsuariosDTO usuario) {

        return this.movimientoInventarioBO.registrarSalida(prod_variante,
                cantidad, observacion, usuario);
    }
}
