package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;

public interface CategoriasDAO {
    
    public Integer insertar(CategoriasDTO categoria);
    
    public CategoriasDTO obtenerPorId(Integer categoriaId);

    public ArrayList<CategoriasDTO> listarTodos();

    public Integer modificar(CategoriasDTO categoria);

    public Integer eliminar(CategoriasDTO categoria);
}
