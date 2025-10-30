
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;

public interface CarritoComprasDAO {
    
    public Integer insertar(CarritoComprasDTO carrito_compras);
    
    public CarritoComprasDTO obtenerPorId(Integer carritoId);
    
    public ArrayList<CarritoComprasDTO> listarTodos();
    
    public Integer modificar(CarritoComprasDTO carrito);
    
    public Integer eliminar(CarritoComprasDTO carrito);
}
