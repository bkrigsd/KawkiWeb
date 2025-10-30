package capaPersistencia;

import capaDominio.Detalle_PedidoDTO;
import java.util.ArrayList;

public interface Detalle_PedidoDAO {
    
    public Integer insertar(Detalle_PedidoDTO pedido);
    
    public Detalle_PedidoDTO obtenerPorId(Integer pedidoId);

    public ArrayList<Detalle_PedidoDTO> listarTodos();

    public Integer modificar(Detalle_PedidoDTO pedido);

    public Integer eliminar(Detalle_PedidoDTO pedido);
    
}
