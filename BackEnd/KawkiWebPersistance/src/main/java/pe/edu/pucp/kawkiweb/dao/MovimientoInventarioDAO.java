
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;


public interface MovimientoInventarioDAO {
    
    public Integer insertar(MovimientosInventarioDTO movInventario);
    
    public MovimientosInventarioDTO obtenerPorId(Integer movInventarioId);
    
    public ArrayList<MovimientosInventarioDTO> listarTodos();
    
    public Integer modificar(MovimientosInventarioDTO movInventario);
    
    public Integer eliminar(MovimientosInventarioDTO movInventario);
    
}
