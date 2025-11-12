package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.DescuentosBO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

@WebService(serviceName = "DescuentosService")
public class Descuentos {

    private DescuentosBO descuentoBO;

    public Descuentos() {
        this.descuentoBO = new DescuentosBO();
    }

    @WebMethod(operationName = "insertarDescuento")
    public Integer insertarDescuento(
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "tipo_condicion") TiposCondicionDTO tipo_condicion,
            @WebParam(name = "valor_condicion") Integer valor_condicion,
            @WebParam(name = "tipo_beneficio") TiposBeneficioDTO tipo_beneficio,
            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
            @WebParam(name = "fecha_inicio") LocalDateTime fecha_inicio,
            @WebParam(name = "fecha_fin") LocalDateTime fecha_fin,
            @WebParam(name = "activo") Boolean activo) {
        
        return this.descuentoBO.insertar(descripcion, tipo_condicion, valor_condicion,
                tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin, activo);
    }

    @WebMethod(operationName = "obtenerPorIdDescuento")
    public DescuentosDTO obtenerPorIdDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId) {
        return this.descuentoBO.obtenerPorId(descuentoId);
    }

    @WebMethod(operationName = "listarTodosDescuento")
    public ArrayList<DescuentosDTO> listarTodosDescuento() {
        return new ArrayList<>(this.descuentoBO.listarTodos());
    }

    @WebMethod(operationName = "modificarDescuento")
    public Integer modificarDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "tipo_condicion") TiposCondicionDTO tipo_condicion,
            @WebParam(name = "valor_condicion") Integer valor_condicion,
            @WebParam(name = "tipo_beneficio") TiposBeneficioDTO tipo_beneficio,
            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
            @WebParam(name = "fecha_inicio") LocalDateTime fecha_inicio,
            @WebParam(name = "fecha_fin") LocalDateTime fecha_fin,
            @WebParam(name = "activo") Boolean activo) {

        return this.descuentoBO.modificar(descuentoId, descripcion, tipo_condicion,
                valor_condicion, tipo_beneficio, valor_beneficio, fecha_inicio,
                fecha_fin, activo);
    }

    @WebMethod(operationName = "eliminarDescuento")
    public Integer eliminarDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId) {
        return this.descuentoBO.eliminar(descuentoId);
    }

    @WebMethod(operationName = "activarDescuento")
    public boolean activarDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId) {
        return this.descuentoBO.activar(descuentoId);
    }

    @WebMethod(operationName = "desactivarDescuento")
    public boolean desactivarDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId) {
        return this.descuentoBO.desactivar(descuentoId);
    }

    @WebMethod(operationName = "listarActivasDescuento")
    public ArrayList<DescuentosDTO> listarActivasDescuento() {
        return new ArrayList<>(this.descuentoBO.listarActivas());
    }

    @WebMethod(operationName = "listarVigentesDescuento")
    public ArrayList<DescuentosDTO> listarVigentesDescuento() {
        return new ArrayList<>(this.descuentoBO.listarVigentes());
    }

    @WebMethod(operationName = "esAplicableDescuento")
    public boolean esAplicableDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId,
            @WebParam(name = "cantidadProductos") Integer cantidadProductos,
            @WebParam(name = "montoTotal") Double montoTotal) {

        return this.descuentoBO.esAplicable(descuentoId, cantidadProductos, montoTotal);
    }

    @WebMethod(operationName = "calcularDescuentoDescuento")
    public Double calcularDescuentoDescuento(
            @WebParam(name = "descuentoId") Integer descuentoId,
            @WebParam(name = "montoTotal") Double montoTotal) {

        return this.descuentoBO.calcularDescuento(descuentoId, montoTotal);
    }
}
