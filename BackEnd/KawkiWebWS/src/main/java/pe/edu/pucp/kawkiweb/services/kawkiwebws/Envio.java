package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.EnvioBO;
import pe.edu.pucp.kawkiweb.model.EnvioDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.utilEnvio.CourierDTO;
import pe.edu.pucp.kawkiweb.model.utilEnvio.EstadoEnvioDTO;

@WebService(serviceName = "Envio")
public class Envio {

    private EnvioBO envioBO;

    public Envio() {
        this.envioBO = new EnvioBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "es_delivery") Boolean es_delivery,
            @WebParam(name = "direccion_entrega") String direccion_entrega,
            @WebParam(name = "courier") CourierDTO courier,
            @WebParam(name = "fecha_envio") LocalDateTime fecha_envio,
            @WebParam(name = "costo_envio") Double costo_envio,
            @WebParam(name = "pedido") VentasDTO pedido,
            @WebParam(name = "estado") EstadoEnvioDTO estado,
            @WebParam(name = "fecha_ultimo_estado") LocalDateTime fecha_ultimo_estado) {

        return this.envioBO.insertar(es_delivery, direccion_entrega, courier,
                fecha_envio, costo_envio, pedido, estado, fecha_ultimo_estado);
    }

    @WebMethod(operationName = "obtenerPorId")
    public EnvioDTO obtenerPorId(@WebParam(name = "envioId") Integer envioId) {
        return this.envioBO.obtenerPorId(envioId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<EnvioDTO> listarTodos() {
        return this.envioBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "envio_id") Integer envio_id,
            @WebParam(name = "es_delivery") Boolean es_delivery,
            @WebParam(name = "direccion_entrega") String direccion_entrega,
            @WebParam(name = "courier") CourierDTO courier,
            @WebParam(name = "fecha_envio") LocalDateTime fecha_envio,
            @WebParam(name = "costo_envio") Double costo_envio,
            @WebParam(name = "pedido") VentasDTO pedido,
            @WebParam(name = "estado") EstadoEnvioDTO estado,
            @WebParam(name = "fecha_ultimo_estado") LocalDateTime fecha_ultimo_estado) {

        return this.envioBO.modificar(envio_id, es_delivery, direccion_entrega,
                courier, fecha_envio, costo_envio, pedido, estado, fecha_ultimo_estado);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "envio_id") Integer envio_id) {
        return this.envioBO.eliminar(envio_id);
    }

    @WebMethod(operationName = "cambiarEstado")
    public boolean cambiarEstado(
            @WebParam(name = "envio_id") Integer envio_id,
            @WebParam(name = "nuevoEstado") EstadoEnvioDTO nuevoEstado) {

        return this.envioBO.cambiarEstado(envio_id, nuevoEstado);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Envio Web " + txt + " !";
    }
}
