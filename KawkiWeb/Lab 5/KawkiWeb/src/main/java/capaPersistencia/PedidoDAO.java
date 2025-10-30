package capaPersistencia;

import capaDominio.PedidoDTO;
import java.util.ArrayList;

public interface PedidoDAO {

    public Integer insertar(PedidoDTO pedido);
    
    public PedidoDTO obtenerPorId(Integer pedidoId);

    public ArrayList<PedidoDTO> listarTodos();

    public Integer modificar(PedidoDTO pedido);

    public Integer eliminar(PedidoDTO pedido);
    
}
