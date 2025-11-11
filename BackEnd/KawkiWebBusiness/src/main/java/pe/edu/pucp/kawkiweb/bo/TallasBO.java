package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.TallasDAO;
import pe.edu.pucp.kawkiweb.daoImp.TallasDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

public class TallasBO {

    private TallasDAO tallaDAO;

    public TallasBO() {
        this.tallaDAO = new TallasDAOImpl();
    }

    public TallasDTO obtenerPorId(Integer tallaId) {
        return this.tallaDAO.obtenerPorId(tallaId);
    }

    public ArrayList<TallasDTO> listarTodos() {
        return this.tallaDAO.listarTodos();
    }
}
