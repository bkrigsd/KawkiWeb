package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.MetodosPagoDAO;
import pe.edu.pucp.kawkiweb.daoImp.MetodosPagoDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;

public class MetodosPagoBO {

    private MetodosPagoDAO metodoPagoDAO;

    public MetodosPagoBO() {
        this.metodoPagoDAO = new MetodosPagoDAOImpl();
    }

    public MetodosPagoDTO obtenerPorId(Integer metodoPagoId) {
        return this.metodoPagoDAO.obtenerPorId(metodoPagoId);
    }

    public List<MetodosPagoDTO> listarTodos() {
        return this.metodoPagoDAO.listarTodos();
    }
}
