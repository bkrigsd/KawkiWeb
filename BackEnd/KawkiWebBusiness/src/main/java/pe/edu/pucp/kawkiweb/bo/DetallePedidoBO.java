package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.daoImp.DetalleVentasDAOImpl;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.dao.DetalleVentasDAO;

public class DetallePedidoBO {

    private DetalleVentasDAO detallePedidoDAO;

    public DetallePedidoBO() {
        this.detallePedidoDAO = new DetalleVentasDAOImpl();
    }

    /**
     * Inserta un nuevo detalle de pedido
     *
     * @param productoVar Producto variante a agregar
     * @param pedidoId ID del pedido al que pertenece
     * @param cantidad Cantidad del producto
     * @param precioUnitario Precio unitario del producto
     * @param subtotal Subtotal (cantidad * precio)
     * @return ID del detalle insertado o 0 si falla
     */
    public Integer insertar(ProductosVariantesDTO productoVar, Integer pedidoId,
            Integer cantidad, Double precioUnitario, Double subtotal) {
        if (productoVar == null || productoVar.getProd_variante_id() == null) {
            System.err.println("Error: Producto variante no puede ser nulo");
            return 0;
        }
        if (pedidoId == null || pedidoId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a 0");
            return 0;
        }

        DetalleVentasDTO detallePedidoDTO = new DetalleVentasDTO();
        detallePedidoDTO.setProductoVar(productoVar);
        detallePedidoDTO.setPedido_id(pedidoId);
        detallePedidoDTO.setCantidad(cantidad);
        detallePedidoDTO.setPrecio_unitario(precioUnitario != null ? precioUnitario : 0.0);
        detallePedidoDTO.setSubtotal(subtotal != null ? subtotal : 0.0);

        return this.detallePedidoDAO.insertar(detallePedidoDTO);
    }

    /**
     * Obtiene un detalle de pedido por su ID
     *
     * @param detalleId ID del detalle
     * @return DetalleVentasDTO con la información completa o null si no existe
     */
    public DetalleVentasDTO obtenerPorId(Integer detalleId) {
        if (detalleId == null || detalleId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return null;
        }
        return this.detallePedidoDAO.obtenerPorId(detalleId);
    }

    /**
     * Lista todos los detalles de pedido
     *
     * @return Lista de todos los detalles
     */
    public ArrayList<DetalleVentasDTO> listarTodos() {
        return this.detallePedidoDAO.listarTodos();
    }

    /**
     * Lista todos los detalles de un pedido específico
     *
     * @param pedidoId ID del pedido
     * @return Lista de detalles del pedido especificado
     */
    public ArrayList<DetalleVentasDTO> listarPorPedidoId(Integer pedidoId) {
        if (pedidoId == null || pedidoId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return new ArrayList<>();
        }
        return this.detallePedidoDAO.listarPorVentaId(pedidoId);
    }

    /**
     * Modifica un detalle de pedido existente
     *
     * @param detallePedidoId ID del detalle a modificar
     * @param productoVar Producto variante
     * @param pedidoId ID del pedido al que pertenece
     * @param cantidad Cantidad actualizada
     * @param precioUnitario Precio unitario actualizado
     * @param subtotal Subtotal actualizado
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer modificar(Integer detallePedidoId, ProductosVariantesDTO productoVar,
            Integer pedidoId, Integer cantidad, Double precioUnitario,
            Double subtotal) {
        if (detallePedidoId == null || detallePedidoId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return 0;
        }
        if (productoVar == null || productoVar.getProd_variante_id() == null) {
            System.err.println("Error: Producto variante no puede ser nulo");
            return 0;
        }
        if (pedidoId == null || pedidoId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a 0");
            return 0;
        }

        DetalleVentasDTO detallePedidoDTO = new DetalleVentasDTO();
        detallePedidoDTO.setDetalle_pedido_id(detallePedidoId);
        detallePedidoDTO.setProductoVar(productoVar);
        detallePedidoDTO.setPedido_id(pedidoId);
        detallePedidoDTO.setCantidad(cantidad);
        detallePedidoDTO.setPrecio_unitario(precioUnitario != null ? precioUnitario : 0.0);
        detallePedidoDTO.setSubtotal(subtotal != null ? subtotal : 0.0);

        return this.detallePedidoDAO.modificar(detallePedidoDTO);
    }

    /**
     * Elimina un detalle de pedido por su ID
     *
     * @param detallePedidoId ID del detalle a eliminar
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer eliminar(Integer detallePedidoId) {
        if (detallePedidoId == null || detallePedidoId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return 0;
        }

        DetalleVentasDTO detallePedidoDTO = new DetalleVentasDTO();
        detallePedidoDTO.setDetalle_pedido_id(detallePedidoId);
        return this.detallePedidoDAO.eliminar(detallePedidoDTO);
    }
}
