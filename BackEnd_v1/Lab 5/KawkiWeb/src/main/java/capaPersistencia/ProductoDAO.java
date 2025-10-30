package capaPersistencia;

import capaDominio.ProductoDTO;
import java.util.ArrayList;

public interface ProductoDAO {

    public Integer insertar(ProductoDTO pedido);
    
    public ProductoDTO obtenerPorId(Integer pedidoId);

    public ArrayList<ProductoDTO> listarTodos();

    public Integer modificar(ProductoDTO pedido);

    public Integer eliminar(ProductoDTO pedido);
}
