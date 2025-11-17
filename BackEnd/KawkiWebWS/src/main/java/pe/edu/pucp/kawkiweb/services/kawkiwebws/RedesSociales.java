package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.RedesSocialesBO;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

@WebService(serviceName="RedesSocialesService")
public class RedesSociales {
    
    private RedesSocialesBO redSocialBO;
    
    public RedesSociales(){
        this.redSocialBO=new RedesSocialesBO();
    }
    
    @WebMethod(operationName = "obtenerPorIdRedSocial")
    public RedesSocialesDTO obtenerPorIdRedSocial(
            @WebParam(name = "redSocialId") Integer redSocialId) {
        return this.redSocialBO.obtenerPorId(redSocialId);
    }

    @WebMethod(operationName = "listarTodosRedSocial")
    public ArrayList<RedesSocialesDTO> listarTodosRedSocial() {
        return new ArrayList<>(this.redSocialBO.listarTodos());
    }
}
