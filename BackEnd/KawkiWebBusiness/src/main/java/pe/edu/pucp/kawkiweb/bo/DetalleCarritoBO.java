package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.DetalleCarritoDAO;
import pe.edu.pucp.kawkiweb.daoImp.DetalleCarritoDAOImpl;
import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;

public class DetalleCarritoBO {

    private DetalleCarritoDAO detalleCarDAO;

    public DetalleCarritoBO() {
        this.detalleCarDAO = new DetalleCarritoDAOImpl();
    }

    /**
     * Inserta un nuevo detalle en el carrito
     *
     * @param carritoId ID del carrito al que pertenece
     * @param cantidad Cantidad del producto
     * @param precioUnitario Precio unitario del producto
     * @param subtotal Subtotal (cantidad * precio)
     * @param producto Producto variante a agregar
     * @return ID del detalle insertado o 0 si falla
     */
    public Integer insertar(Integer carritoId, Integer cantidad,
            Double precioUnitario, Double subtotal,
            ProductoVarianteDTO producto) {
        if (carritoId == null || carritoId <= 0) {
            System.err.println("Error: ID de carrito inválido");
            return 0;
        }
        if (producto == null || producto.getProd_variante_id() == null) {
            System.err.println("Error: Producto no puede ser nulo");
            return 0;
        }
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a 0");
            return 0;
        }

        DetalleCarritoDTO detCarDTO = new DetalleCarritoDTO();
        detCarDTO.setCarrito_id(carritoId);
        detCarDTO.setCantidad(cantidad);
        detCarDTO.setPrecio_unitario(precioUnitario != null ? precioUnitario : 0.0);
        detCarDTO.setSubtotal(subtotal != null ? subtotal : 0.0);
        detCarDTO.setProducto(producto);
        return this.detalleCarDAO.insertar(detCarDTO);
    }

    /**
     * Obtiene un detalle de carrito por su ID
     *
     * @param detalleCarritoId ID del detalle
     * @return DetalleCarritoDTO con la información completa o null si no existe
     */
    public DetalleCarritoDTO obtenerPorId(Integer detalleCarritoId) {
        if (detalleCarritoId == null || detalleCarritoId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return null;
        }
        return this.detalleCarDAO.obtenerPorId(detalleCarritoId);
    }

    /**
     * Lista todos los detalles de carrito
     *
     * @return Lista de todos los detalles
     */
    public ArrayList<DetalleCarritoDTO> listarTodos() {
        return this.detalleCarDAO.listarTodos();
    }

    /**
     * Lista todos los detalles de un carrito específico
     *
     * @param carritoId ID del carrito
     * @return Lista de detalles del carrito especificado
     */
    public ArrayList<DetalleCarritoDTO> listarPorCarritoId(Integer carritoId) {
        if (carritoId == null || carritoId <= 0) {
            System.err.println("Error: ID de carrito inválido");
            return new ArrayList<>();
        }
        return this.detalleCarDAO.listarPorCarritoId(carritoId);
    }

    /**
     * Modifica un detalle de carrito existente
     *
     * @param detalleCarritoId ID del detalle a modificar
     * @param carritoId ID del carrito al que pertenece
     * @param cantidad Cantidad actualizada
     * @param precioUnitario Precio unitario actualizado
     * @param subtotal Subtotal actualizado
     * @param producto Producto variante
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer modificar(Integer detalleCarritoId, Integer carritoId,
            Integer cantidad, Double precioUnitario,
            Double subtotal, ProductoVarianteDTO producto) {
        if (detalleCarritoId == null || detalleCarritoId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return 0;
        }
        if (carritoId == null || carritoId <= 0) {
            System.err.println("Error: ID de carrito inválido");
            return 0;
        }
        if (producto == null || producto.getProd_variante_id() == null) {
            System.err.println("Error: Producto no puede ser nulo");
            return 0;
        }
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a 0");
            return 0;
        }

        DetalleCarritoDTO detCarDTO = new DetalleCarritoDTO();
        detCarDTO.setDetalle_carrito_id(detalleCarritoId);
        detCarDTO.setCarrito_id(carritoId);
        detCarDTO.setCantidad(cantidad);
        detCarDTO.setPrecio_unitario(precioUnitario != null ? precioUnitario : 0.0);
        detCarDTO.setSubtotal(subtotal != null ? subtotal : 0.0);
        detCarDTO.setProducto(producto);
        return this.detalleCarDAO.modificar(detCarDTO);
    }

    /**
     * Elimina un detalle de carrito por su ID
     *
     * @param detalleCarritoId ID del detalle a eliminar
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer eliminar(Integer detalleCarritoId) {
        if (detalleCarritoId == null || detalleCarritoId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return 0;
        }

        DetalleCarritoDTO detCarDTO = new DetalleCarritoDTO();
        detCarDTO.setDetalle_carrito_id(detalleCarritoId);
        return this.detalleCarDAO.eliminar(detCarDTO);
    }
}
