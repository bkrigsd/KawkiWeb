package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.TallasBO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

@WebService(serviceName = "TallasService")
public class Tallas {

    private TallasBO tallaBO;

    public Tallas() {
        this.tallaBO = new TallasBO();
    }

    @WebMethod(operationName = "insertarTalla")
    public Integer insertarTalla(
            @WebParam(name = "numeroTalla") Integer numeroTalla) {
        return this.tallaBO.insertar(numeroTalla);
    }

    @WebMethod(operationName = "modificarTalla")
    public Integer modificarTalla(
            @WebParam(name = "tallaId") Integer tallaId,
            @WebParam(name = "numeroTalla") Integer numeroTalla) {
        return this.tallaBO.modificar(tallaId, numeroTalla);
    }

    @WebMethod(operationName = "obtenerPorIdTalla")
    public TallasDTO obtenerPorIdTalla(
            @WebParam(name = "tallaId") Integer tallaId) {
        return this.tallaBO.obtenerPorId(tallaId);
    }

    @WebMethod(operationName = "listarTodosTalla")
    public ArrayList<TallasDTO> listarTodosTalla() {
        return new ArrayList<>(this.tallaBO.listarTodos());
    }

}
