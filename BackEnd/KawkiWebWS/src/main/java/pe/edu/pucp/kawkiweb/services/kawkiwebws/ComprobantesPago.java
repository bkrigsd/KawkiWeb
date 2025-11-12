//package pe.edu.pucp.kawkiweb.services.kawkiwebws;
//
//import jakarta.jws.WebService;
//import jakarta.jws.WebMethod;
//import jakarta.jws.WebParam;
//import java.util.ArrayList;
//import pe.edu.pucp.kawkiweb.bo.ComprobantesPagoBO;
//import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
//import pe.edu.pucp.kawkiweb.model.VentasDTO;
//import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;
//import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;
//
//@WebService(serviceName = "ComprobantesPago")
//public class ComprobantesPago {
//
//    private ComprobantesPagoBO comprobantePagoBO;
//
//    public ComprobantesPago() {
//        this.comprobantePagoBO = new ComprobantesPagoBO();
//    }
//
//    @WebMethod(operationName = "insertarComprobPago")
//    public Integer insertar(
//            @WebParam(name = "tipo_comprobante") TiposComprobanteDTO tipo_comprobante,
//            @WebParam(name = "numero_serie") String numero_serie,
//            @WebParam(name = "dni_cliente") String dni_cliente,
//            @WebParam(name = "nombre_cliente") String nombre_cliente,
//            @WebParam(name = "ruc_cliente") String ruc_cliente,
//            @WebParam(name = "razon_social_cliente") String razon_social_cliente,
//            @WebParam(name = "direccion_fiscal_cliente") String direccion_fiscal_cliente,
//            @WebParam(name = "telefono_cliente") String telefono_cliente,
//            @WebParam(name = "total") Double total,
//            @WebParam(name = "venta") VentasDTO venta,
//            @WebParam(name = "metodoPago") MetodosPagoDTO metodoPago) {
//
//        return this.comprobantePagoBO.insertar(tipo_comprobante, numero_serie,
//                dni_cliente, nombre_cliente, ruc_cliente, razon_social_cliente,
//                direccion_fiscal_cliente, telefono_cliente, total, venta, metodoPago);
//    }
//
//    @WebMethod(operationName = "obtenerPorIdComprobPago")
//    public ComprobantesPagoDTO obtenerPorId(
//            @WebParam(name = "comprobante_pago_id") Integer comprobante_pago_id) {
//        return this.comprobantePagoBO.obtenerPorId(comprobante_pago_id);
//    }
//
//    @WebMethod(operationName = "listarTodosComprobantePago")
//    public ArrayList<ComprobantesPagoDTO> listarTodos() {
//        return new ArrayList<>(this.comprobantePagoBO.listarTodos());
//    }
//
//    @WebMethod(operationName = "modificarComprobPago")
//    public Integer modificar(
//            @WebParam(name = "comprobante_pago_id") Integer comprobante_pago_id,
//            @WebParam(name = "tipo_comprobante") TiposComprobanteDTO tipo_comprobante,
//            @WebParam(name = "numero_serie") String numero_serie,
//            @WebParam(name = "dni_cliente") String dni_cliente,
//            @WebParam(name = "nombre_cliente") String nombre_cliente,
//            @WebParam(name = "ruc_cliente") String ruc_cliente,
//            @WebParam(name = "razon_social_cliente") String razon_social_cliente,
//            @WebParam(name = "direccion_fiscal_cliente") String direccion_fiscal_cliente,
//            @WebParam(name = "telefono_cliente") String telefono_cliente,
//            @WebParam(name = "total") Double total,
//            @WebParam(name = "venta") VentasDTO venta,
//            @WebParam(name = "metodoPago") MetodosPagoDTO metodoPago) {
//
//        return this.comprobantePagoBO.modificar(comprobante_pago_id, tipo_comprobante,
//                numero_serie, dni_cliente, nombre_cliente, ruc_cliente,
//                razon_social_cliente, direccion_fiscal_cliente, telefono_cliente,
//                total, venta, metodoPago);
//    }
//
//    @WebMethod(operationName = "eliminarComproPago")
//    public Integer eliminar(
//            @WebParam(name = "comprobante_pago_id") Integer comprobante_pago_id) {
//        return this.comprobantePagoBO.eliminar(comprobante_pago_id);
//    }
//}
