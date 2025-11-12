//package pe.edu.pucp.kawkiweb.services.kawkiwebws;
//
//import jakarta.jws.WebService;
//import jakarta.jws.WebMethod;
//import jakarta.jws.WebParam;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import pe.edu.pucp.kawkiweb.bo.DescuentosBO;
//import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
//import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
//import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
//
//@WebService(serviceName = "Descuentos")
//public class Descuentos {
//
//    private DescuentosBO descuentoBO;
//
//    public Descuentos() {
//        this.descuentoBO = new DescuentosBO();
//    }
//
//    @WebMethod(operationName = "insertarDescuento")
//    public Integer insertar(
//            @WebParam(name = "descripcion") String descripcion,
//            @WebParam(name = "tipo_condicion") TiposCondicionDTO tipo_condicion,
//            @WebParam(name = "valor_condicion") Integer valor_condicion,
//            @WebParam(name = "tipo_beneficio") TiposBeneficioDTO tipo_beneficio,
//            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
//            @WebParam(name = "fecha_inicio") LocalDateTime fecha_inicio,
//            @WebParam(name = "fecha_fin") LocalDateTime fecha_fin,
//            @WebParam(name = "activo") Boolean activo) {
//
//        return this.descuentoBO.insertar(descripcion, tipo_condicion, valor_condicion,
//                tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin, activo);
//    }
//
//    @WebMethod(operationName = "obtenerPorIdDescuento")
//    public DescuentosDTO obtenerPorId(
//            @WebParam(name = "descuentoId") Integer descuentoId) {
//        return this.descuentoBO.obtenerPorId(descuentoId);
//    }
//
//    @WebMethod(operationName = "listarTodosDescuento")
//    public ArrayList<DescuentosDTO> listarTodos() {
//        return new ArrayList<>(this.descuentoBO.listarTodos());
//    }
//
//    @WebMethod(operationName = "modificarDescuento")
//    public Integer modificar(
//            @WebParam(name = "descuentoId") Integer descuentoId,
//            @WebParam(name = "descripcion") String descripcion,
//            @WebParam(name = "tipo_condicion") TiposCondicionDTO tipo_condicion,
//            @WebParam(name = "valor_condicion") Integer valor_condicion,
//            @WebParam(name = "tipo_beneficio") TiposBeneficioDTO tipo_beneficio,
//            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
//            @WebParam(name = "fecha_inicio") LocalDateTime fecha_inicio,
//            @WebParam(name = "fecha_fin") LocalDateTime fecha_fin,
//            @WebParam(name = "activo") Boolean activo) {
//
//        return this.descuentoBO.modificar(descuentoId, descripcion, tipo_condicion,
//                valor_condicion, tipo_beneficio, valor_beneficio, fecha_inicio,
//                fecha_fin, activo);
//    }
//
//    @WebMethod(operationName = "eliminarDescuento")
//    public Integer eliminar(
//            @WebParam(name = "descuentoId") Integer descuentoId) {
//        return this.descuentoBO.eliminar(descuentoId);
//    }
//
//    @WebMethod(operationName = "activarDescuento")
//    public boolean activar(
//            @WebParam(name = "descuentoId") Integer descuentoId) {
//        return this.descuentoBO.activar(descuentoId);
//    }
//
//    @WebMethod(operationName = "desactivarDescuento")
//    public boolean desactivar(
//            @WebParam(name = "descuentoId") Integer descuentoId) {
//        return this.descuentoBO.desactivar(descuentoId);
//    }
//
//    @WebMethod(operationName = "listarActivasDescuento")
//    public ArrayList<DescuentosDTO> listarActivas() {
//        return new ArrayList<>(this.descuentoBO.listarActivas());
//    }
//
//    @WebMethod(operationName = "listarVigentesDescuento")
//    public ArrayList<DescuentosDTO> listarVigentes() {
//        return new ArrayList<>(this.descuentoBO.listarVigentes());
//    }
//
//    @WebMethod(operationName = "esAplicableDescuento")
//    public boolean esAplicable(
//            @WebParam(name = "descuentoId") Integer descuentoId,
//            @WebParam(name = "cantidadProductos") Integer cantidadProductos,
//            @WebParam(name = "montoTotal") Double montoTotal) {
//
//        return this.descuentoBO.esAplicable(descuentoId, cantidadProductos, montoTotal);
//    }
//
//    @WebMethod(operationName = "calcularDescuentoDescuento")
//    public Double calcularDescuento(
//            @WebParam(name = "descuentoId") Integer descuentoId,
//            @WebParam(name = "montoTotal") Double montoTotal) {
//
//        return this.descuentoBO.calcularDescuento(descuentoId, montoTotal);
//    }
//}
