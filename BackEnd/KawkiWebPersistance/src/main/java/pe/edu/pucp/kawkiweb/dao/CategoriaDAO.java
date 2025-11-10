package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;

public interface CategoriaDAO {

    public CategoriasDTO obtenerPorId(Integer categoriaId);

    public ArrayList<CategoriasDTO> listarTodos();

}
