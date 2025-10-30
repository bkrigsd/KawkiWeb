package capaPersistencia;

import capaDominio.DetalleCarritoDTO;
import java.util.ArrayList;

public interface Detalle_CarritoDAO {
    
    // Insertar un nuevo detalle de carrito
    public Integer insertar(DetalleCarritoDTO detalle);

    // Buscar un detalle de carrito por su ID
    public DetalleCarritoDTO obtenerPorId(Integer detalleCarritoId);

    // Listar todos los detalles de todos los carritos
    public ArrayList<DetalleCarritoDTO> listarTodos();

    // Listar los detalles de un carrito específico
    public ArrayList<DetalleCarritoDTO> listarPorCarritoId(Integer carritoId);

    // Modificar un detalle (ej. cantidad, precio, subtotal)
    public Integer modificar(DetalleCarritoDTO detalle);

    // Eliminar un detalle
    public Integer eliminar(DetalleCarritoDTO detalle);
}
