package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.TiposCondicionDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposCondicionDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

public class TiposCondicionBO {

    private TiposCondicionDAO tipoCondicionDAO;

    public TiposCondicionBO() {
        this.tipoCondicionDAO = new TiposCondicionDAOImpl();
    }

    public TiposCondicionDTO obtenerPorId(Integer tipoCondicionId) {
        return this.tipoCondicionDAO.obtenerPorId(tipoCondicionId);
    }

    public ArrayList<TiposCondicionDTO> listarTodos() {
        return this.tipoCondicionDAO.listarTodos();
    }
}
