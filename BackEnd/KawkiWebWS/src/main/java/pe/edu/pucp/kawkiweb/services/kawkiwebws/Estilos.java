package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.EstilosBO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

@WebService(serviceName = "EstilosService")
public class Estilos {

    private EstilosBO estiloBO;

    public Estilos() {
        this.estiloBO = new EstilosBO();
    }

    @WebMethod(operationName = "insertarEstilo")
    public Integer insertarEstilo(
            @WebParam(name = "nombreEstilo") String nombreEstilo) {
        return this.estiloBO.insertar(nombreEstilo);
    }

    @WebMethod(operationName = "modificarEstilo")
    public Integer modificarEstilo(
            @WebParam(name = "estiloId") Integer estiloId,
            @WebParam(name = "nombreEstilo") String nombreEstilo) {
        return this.estiloBO.modificar(estiloId, nombreEstilo);
    }

    @WebMethod(operationName = "obtenerPorIdEstilo")
    public EstilosDTO obtenerPorIdEstilo(
            @WebParam(name = "estiloId") Integer estiloId) {
        return this.estiloBO.obtenerPorId(estiloId);
    }

    @WebMethod(operationName = "listarTodosEstilo")
    public ArrayList<EstilosDTO> listarTodosEstilo() {
        return new ArrayList<>(this.estiloBO.listarTodos());
    }

}
