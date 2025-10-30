package capaPersistencia;

import capaDominio.ProductoVarianteDTO;
import java.util.ArrayList;

public interface ProductoVarianteDAO {

    public Integer insertar(ProductoVarianteDTO prodVariante);

    public ProductoVarianteDTO obtenerPorId(Integer prodVarianteId);

    public ArrayList<ProductoVarianteDTO> listarTodos();

    public Integer modificar(ProductoVarianteDTO prodVariante);

    public Integer eliminar(ProductoVarianteDTO prodVariante);
}
