package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriaDTO;

public interface CategoriaDAO {

    public CategoriaDTO obtenerPorId(Integer categoriaId);

    public ArrayList<CategoriaDTO> listarTodos();

}
