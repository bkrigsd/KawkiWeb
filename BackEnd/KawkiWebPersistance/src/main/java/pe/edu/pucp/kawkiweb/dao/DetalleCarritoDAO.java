package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;

public interface DetalleCarritoDAO {

    public Integer insertar(DetalleCarritoDTO carrito_compras);

    public DetalleCarritoDTO obtenerPorId(Integer carritoId);

    public ArrayList<DetalleCarritoDTO> listarTodos();

    ArrayList<DetalleCarritoDTO> listarPorCarritoId(Integer carritoId);

    public Integer modificar(DetalleCarritoDTO carrito);

    public Integer eliminar(DetalleCarritoDTO carrito);
}
