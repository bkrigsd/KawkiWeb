package capaPersistencia;

import capaDominio.MovimientosInventarioDTO;
import java.util.ArrayList;

public interface MovimientosInventarioDAO {

    public Integer insertar(MovimientosInventarioDTO pedido);
    
    public MovimientosInventarioDTO obtenerPorId(Integer pedidoId);

    public ArrayList<MovimientosInventarioDTO> listarTodos();

    public Integer modificar(MovimientosInventarioDTO pedido);

    public Integer eliminar(MovimientosInventarioDTO pedido);
}
