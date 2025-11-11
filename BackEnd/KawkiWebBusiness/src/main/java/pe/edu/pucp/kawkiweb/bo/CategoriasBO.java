package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.CategoriasDAO;
import pe.edu.pucp.kawkiweb.daoImp.CategoriasDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;

public class CategoriasBO {

    private CategoriasDAO categoriaDAO;

    public CategoriasBO() {
        this.categoriaDAO = new CategoriasDAOImpl();
    }

    public CategoriasDTO obtenerPorId(Integer categoriaId) {
        return this.categoriaDAO.obtenerPorId(categoriaId);
    }

    public ArrayList<CategoriasDTO> listarTodos() {
        return this.categoriaDAO.listarTodos();
    }
}
