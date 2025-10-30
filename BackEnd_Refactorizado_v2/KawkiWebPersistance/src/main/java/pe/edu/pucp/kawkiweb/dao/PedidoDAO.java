package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;

public interface PedidoDAO {
    
    public Integer insertar(PedidoDTO pedido);
    
    public PedidoDTO obtenerPorId(Integer pedidoId);

    public ArrayList<PedidoDTO> listarTodos();

    public Integer modificar(PedidoDTO pedido);

    public Integer eliminar(PedidoDTO pedido); /* Como existe el estado_pedido no es necesario eliminar de la base de datos */
    
}
