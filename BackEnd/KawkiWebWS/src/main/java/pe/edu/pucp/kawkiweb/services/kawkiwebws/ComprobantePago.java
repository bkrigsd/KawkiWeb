package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.ComprobantePagoBO;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

@WebService(serviceName = "ComprobantePago")
public class ComprobantePago {

    private ComprobantePagoBO comprobantePagoBO;

    public ComprobantePago() {
        this.comprobantePagoBO = new ComprobantePagoBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "pago_id") Integer pago_id,
            @WebParam(name = "fecha_hora_creacion") LocalDateTime fecha_hora_creacion,
            @WebParam(name = "tipo_comprobante") TiposComprobanteDTO tipo_comprobante,
            @WebParam(name = "numero_serie") String numero_serie,
            @WebParam(name = "dni_cliente") String dni_cliente,
            @WebParam(name = "nombre_cliente") String nombre_cliente,
            @WebParam(name = "ruc_cliente") String ruc_cliente,
            @WebParam(name = "razon_social_cliente") String razon_social_cliente,
            @WebParam(name = "direccion_fiscal_cliente") String direccion_fiscal_cliente,
            @WebParam(name = "correo_cliente") String correo_cliente,
            @WebParam(name = "telefono_cliente") String telefono_cliente,
            @WebParam(name = "total") Double total) {

        return this.comprobantePagoBO.insertar(pago_id, fecha_hora_creacion,
                tipo_comprobante, numero_serie, dni_cliente, nombre_cliente,
                ruc_cliente, razon_social_cliente, direccion_fiscal_cliente,
                correo_cliente, telefono_cliente, total);
    }

    @WebMethod(operationName = "obtenerPorId")
    public ComprobantesPagoDTO obtenerPorId(
            @WebParam(name = "comprobante_pago_id") Integer comprobante_pago_id) {
        return this.comprobantePagoBO.obtenerPorId(comprobante_pago_id);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<ComprobantesPagoDTO> listarTodos() {
        return this.comprobantePagoBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "comprobante_pago_id") Integer comprobante_pago_id,
            @WebParam(name = "pago_id") Integer pago_id,
            @WebParam(name = "fecha_hora_creacion") LocalDateTime fecha_hora_creacion,
            @WebParam(name = "tipo_comprobante") TiposComprobanteDTO tipo_comprobante,
            @WebParam(name = "numero_serie") String numero_serie,
            @WebParam(name = "dni_cliente") String dni_cliente,
            @WebParam(name = "nombre_cliente") String nombre_cliente,
            @WebParam(name = "ruc_cliente") String ruc_cliente,
            @WebParam(name = "razon_social_cliente") String razon_social_cliente,
            @WebParam(name = "direccion_fiscal_cliente") String direccion_fiscal_cliente,
            @WebParam(name = "correo_cliente") String correo_cliente,
            @WebParam(name = "telefono_cliente") String telefono_cliente,
            @WebParam(name = "total") Double total) {

        return this.comprobantePagoBO.modificar(comprobante_pago_id, pago_id,
                fecha_hora_creacion, tipo_comprobante, numero_serie, dni_cliente,
                nombre_cliente, ruc_cliente, razon_social_cliente,
                direccion_fiscal_cliente, correo_cliente, telefono_cliente, total);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(
            @WebParam(name = "comprobante_pago_id") Integer comprobante_pago_id) {
        return this.comprobantePagoBO.eliminar(comprobante_pago_id);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "ComprobantePago Web " + txt + " !";
    }
}
