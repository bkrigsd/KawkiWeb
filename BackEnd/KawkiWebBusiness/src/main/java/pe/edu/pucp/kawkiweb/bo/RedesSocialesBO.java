package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.RedesSocialesDAO;
import pe.edu.pucp.kawkiweb.daoImp.RedesSocialesDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

public class RedesSocialesBO {

    private RedesSocialesDAO redSocialDAO;

    public RedesSocialesBO() {
        this.redSocialDAO = new RedesSocialesDAOImpl();
    }

    public RedesSocialesDTO obtenerPorId(Integer redSocialId) {
        return this.redSocialDAO.obtenerPorId(redSocialId);
    }

    public List<RedesSocialesDTO> listarTodos() {
        return this.redSocialDAO.listarTodos();
    }
}
