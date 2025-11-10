package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;

public interface EstadoPedidoDAO {

    EstadoPedidoDTO obtenerPorId(Integer estadoPedidoId);

    ArrayList<EstadoPedidoDTO> listarTodos();
}
