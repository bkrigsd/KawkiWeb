package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TiposComprobanteDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposComprobanteDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

public class TiposComprobanteBO {

    private TiposComprobanteDAO tipoComprobanteDAO;

    public TiposComprobanteBO() {
        this.tipoComprobanteDAO = new TiposComprobanteDAOImpl();
    }

    public TiposComprobanteDTO obtenerPorId(Integer tipoComprobanteId) {
        return this.tipoComprobanteDAO.obtenerPorId(tipoComprobanteId);
    }

    public List<TiposComprobanteDTO> listarTodos() {
        return this.tipoComprobanteDAO.listarTodos();
    }
}
