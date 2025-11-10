
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.MovimientoInventarioDTO;


public interface MovimientoInventarioDAO {
    
    public Integer insertar(MovimientoInventarioDTO movInventario);
    
    public MovimientoInventarioDTO obtenerPorId(Integer movInventarioId);
    
    public ArrayList<MovimientoInventarioDTO> listarTodos();
    
    public Integer modificar(MovimientoInventarioDTO movInventario);
    
    public Integer eliminar(MovimientoInventarioDTO movInventario);
    
}
