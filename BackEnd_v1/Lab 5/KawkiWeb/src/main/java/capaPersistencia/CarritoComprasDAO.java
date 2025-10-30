package capaPersistencia;

import capaDominio.CarritoComprasDTO;
import java.util.ArrayList;

/**
 *
 * @author beker
 */
public interface CarritoComprasDAO {
    
    // Crear un nuevo carrito
    public Integer insertar(CarritoComprasDTO carrito);
    
    // Buscar un carrito por su ID
    public CarritoComprasDTO obtenerPorId(Integer carritoId);
    
    // Listar todos los carritos
    public ArrayList<CarritoComprasDTO> listarTodos();
    
    // Actualizar datos de un carrito (ej. total o usuario)
    public Integer modificar(CarritoComprasDTO carrito);
    
    // Eliminar un carrito
    public Integer eliminar(CarritoComprasDTO carrito);
    
}
