package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.DetallePedidoDTO;

public interface DetallePedidoDAO {

    public Integer insertar(DetallePedidoDTO pedido);

    public DetallePedidoDTO obtenerPorId(Integer pedidoId);

    public ArrayList<DetallePedidoDTO> listarTodos();

    ArrayList<DetallePedidoDTO> listarPorPedidoId(Integer pedidoId);

    public Integer modificar(DetallePedidoDTO pedido);

    public Integer eliminar(DetallePedidoDTO pedido);

}
