package pe.edu.pucp.kawkiweb.dao;

import pe.edu.pucp.kawkiweb.model.ProductoDTO;
import java.util.ArrayList;

public interface ProductoDAO {

    public Integer insertar(ProductoDTO producto);
    
    public ProductoDTO obtenerPorId(Integer productoId);

    public ArrayList<ProductoDTO> listarTodos();

    public Integer modificar(ProductoDTO producto);

    public Integer eliminar(ProductoDTO producto);
}
