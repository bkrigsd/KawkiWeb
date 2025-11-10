package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;

public interface DetallePedidoDAO {

    public Integer insertar(DetalleVentasDTO pedido);

    public DetalleVentasDTO obtenerPorId(Integer pedidoId);

    public ArrayList<DetalleVentasDTO> listarTodos();

    ArrayList<DetalleVentasDTO> listarPorPedidoId(Integer pedidoId);

    public Integer modificar(DetalleVentasDTO pedido);

    public Integer eliminar(DetalleVentasDTO pedido);

}
