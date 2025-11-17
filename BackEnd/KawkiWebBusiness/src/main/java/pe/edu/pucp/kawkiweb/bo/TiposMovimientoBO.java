package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TiposMovimientoDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposMovimientoDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

public class TiposMovimientoBO {

    private TiposMovimientoDAO tipoMovimientoDAO;

    public TiposMovimientoBO()  {
        this.tipoMovimientoDAO = new TiposMovimientoDAOImpl();
    }

    public TiposMovimientoDTO obtenerPorId(Integer tipoMovimientoId) {
        return this.tipoMovimientoDAO.obtenerPorId(tipoMovimientoId);
    }

    public List<TiposMovimientoDTO> listarTodos() {
        return this.tipoMovimientoDAO.listarTodos();
    }
}
