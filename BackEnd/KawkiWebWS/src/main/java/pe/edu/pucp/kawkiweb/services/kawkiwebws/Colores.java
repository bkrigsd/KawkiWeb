package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.ColoresBO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;

@WebService(serviceName = "ColoresService")
public class Colores {

    private ColoresBO colorBO;

    public Colores() {
        this.colorBO = new ColoresBO();
    }

    @WebMethod(operationName = "insertarColor")
    public Integer insertarColor(
            @WebParam(name = "nombreColor") String nombreColor) {
        return this.colorBO.insertar(nombreColor);
    }

    @WebMethod(operationName = "modificarColor")
    public Integer modificarColor(
            @WebParam(name = "colorId") Integer colorId,
            @WebParam(name = "nombreColor") String nombreColor) {
        return this.colorBO.modificar(colorId, nombreColor);
    }

    @WebMethod(operationName = "obtenerPorIdColor")
    public ColoresDTO obtenerPorIdColor(
            @WebParam(name = "colorId") Integer colorId) {
        return this.colorBO.obtenerPorId(colorId);
    }

    @WebMethod(operationName = "listarTodosColor")
    public ArrayList<ColoresDTO> listarTodosColor() {
        return new ArrayList<>(this.colorBO.listarTodos());
    }

}
