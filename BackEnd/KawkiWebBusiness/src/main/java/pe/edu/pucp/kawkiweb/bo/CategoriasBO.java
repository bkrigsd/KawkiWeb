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

    public Integer insertar(String nombreCategoria) {
        CategoriasDTO categoriasDTO = new CategoriasDTO();
        categoriasDTO.setNombre(nombreCategoria);
        return this.categoriaDAO.insertar(categoriasDTO);
    }

    public Integer modificar(Integer categoriaId, String nombreCategoria) {
        CategoriasDTO categoriasDTO = new CategoriasDTO();
        categoriasDTO.setCategoria_id(categoriaId);
        categoriasDTO.setNombre(nombreCategoria);
        return this.categoriaDAO.modificar(categoriasDTO);
    }

    public CategoriasDTO obtenerPorId(Integer categoriaId) {
        return this.categoriaDAO.obtenerPorId(categoriaId);
    }

    public ArrayList<CategoriasDTO> listarTodos() {
        return this.categoriaDAO.listarTodos();
    }
}
