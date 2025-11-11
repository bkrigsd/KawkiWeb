package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.EstilosDAO;
import pe.edu.pucp.kawkiweb.daoImp.EstilosDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

public class EstilosBO {

    private EstilosDAO estiloDAO;

    public EstilosBO() {
        this.estiloDAO = new EstilosDAOImpl();
    }

    public EstilosDTO obtenerPorId(Integer estiloId) {
        return this.estiloDAO.obtenerPorId(estiloId);
    }

    public ArrayList<EstilosDTO> listarTodos() {
        return this.estiloDAO.listarTodos();
    }
}
