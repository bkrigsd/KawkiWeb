package pe.edu.pucp.kawkiweb.dao;

import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import java.util.ArrayList;

public interface ProductoDAO {

    public Integer insertar(ProductosDTO producto);
    
    public ProductosDTO obtenerPorId(Integer productoId);

    public ArrayList<ProductosDTO> listarTodos();

    public Integer modificar(ProductosDTO producto);

    public Integer eliminar(ProductosDTO producto);
}
