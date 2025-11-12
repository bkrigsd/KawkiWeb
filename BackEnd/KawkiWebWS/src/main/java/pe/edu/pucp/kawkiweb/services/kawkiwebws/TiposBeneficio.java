package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.TiposBeneficioBO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

@WebService(serviceName = "TiposBeneficioService")
public class TiposBeneficio {

    private TiposBeneficioBO tipoBeneficioBO;

    public TiposBeneficio() {
        this.tipoBeneficioBO = new TiposBeneficioBO();
    }

    @WebMethod(operationName = "insertarTipoBeneficio")
    public Integer insertarTipoBeneficio(
            @WebParam(name = "nombreBeneficio") String nombreBeneficio) {
        return this.tipoBeneficioBO.insertar(nombreBeneficio);
    }

    @WebMethod(operationName = "modificarTipoBeneficio")
    public Integer modificarTipoBeneficio(
            @WebParam(name = "tipoBeneficioId") Integer tipoBeneficioId,
            @WebParam(name = "nombreBeneficio") String nombreBeneficio) {
        return this.tipoBeneficioBO.modificar(tipoBeneficioId, nombreBeneficio);
    }

    @WebMethod(operationName = "obtenerPorIdTipoBeneficio")
    public TiposBeneficioDTO obtenerPorIdTipoBeneficio(
            @WebParam(name = "tipoBeneficioId") Integer tipoBeneficioId) {
        return this.tipoBeneficioBO.obtenerPorId(tipoBeneficioId);
    }

    @WebMethod(operationName = "listarTodosTipoBeneficio")
    public ArrayList<TiposBeneficioDTO> listarTodosTipoBeneficio() {
        return new ArrayList<>(this.tipoBeneficioBO.listarTodos());
    }
}
