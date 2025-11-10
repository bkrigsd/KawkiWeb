package pe.edu.pucp.kawkiweb.dao;

import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
import java.util.ArrayList;

public interface ProductoVarianteDAO {

    public Integer insertar(ProductoVarianteDTO prodVariante);

    public ProductoVarianteDTO obtenerPorId(Integer prodVarianteId);

    public ArrayList<ProductoVarianteDTO> listarTodos();

    ArrayList<ProductoVarianteDTO> listarPorProductoId(Integer productoId);

    public Integer modificar(ProductoVarianteDTO prodVariante);

    public Integer eliminar(ProductoVarianteDTO prodVariante);
}
