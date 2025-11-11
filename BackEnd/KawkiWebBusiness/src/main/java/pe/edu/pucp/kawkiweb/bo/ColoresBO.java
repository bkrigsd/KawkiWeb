package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.ColoresDAO;
import pe.edu.pucp.kawkiweb.daoImp.ColoresDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;

public class ColoresBO {

    private ColoresDAO colorDAO;

    public ColoresBO() {
        this.colorDAO = new ColoresDAOImpl();
    }

    public ColoresDTO obtenerPorId(Integer colorId) {
        return this.colorDAO.obtenerPorId(colorId);
    }

    public ArrayList<ColoresDTO> listarTodos() {
        return this.colorDAO.listarTodos();
    }
}
