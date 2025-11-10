package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.VentasDTO;

public interface PedidoDAO {
    
    public Integer insertar(VentasDTO pedido);
    
    public VentasDTO obtenerPorId(Integer pedidoId);

    public ArrayList<VentasDTO> listarTodos();

    public Integer modificar(VentasDTO pedido);

    public Integer eliminar(VentasDTO pedido); /* Como existe el estado_pedido no es necesario eliminar de la base de datos */
    
}
