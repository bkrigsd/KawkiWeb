package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.PedidoDAO;
import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

public class PedidoBO {

    private PedidoDAO pedidoDAO;

    public PedidoBO() {
        this.pedidoDAO = new PedidoDAOImpl();
    }

    /**
     * Inserta un nuevo pedido
     *
     * @param usuario Usuario que realiza el pedido
     * @param fechaHoraCreacion Fecha y hora de creación del pedido
     * @param fechaHoraUltimoEstado Fecha y hora del último cambio de estado
     * @param total Total del pedido
     * @param estadoPedido Estado actual del pedido
     * @param promocion Promoción aplicada (puede ser null)
     * @return ID del pedido insertado o 0 si falla
     */
    public Integer insertar(UsuarioDTO usuario,
            LocalDateTime fechaHoraCreacion,
            LocalDateTime fechaHoraUltimoEstado,
            Double total,
            EstadoPedidoDTO estadoPedido,
            PromocionDTO promocion) {
        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Error: Usuario no puede ser nulo");
            return 0;
        }
        if (estadoPedido == null || estadoPedido.getEstado_pedido_id() == null) {
            System.err.println("Error: Estado de pedido no puede ser nulo");
            return 0;
        }
        if (total == null || total < 0) {
            System.err.println("Error: Total debe ser mayor o igual a 0");
            return 0;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setUsuario(usuario);
        pedidoDTO.setTotal(total);
        pedidoDTO.setFecha_hora_creacion(fechaHoraCreacion != null ? fechaHoraCreacion : LocalDateTime.now());
        pedidoDTO.setFecha_hora_ultimo_estado(fechaHoraUltimoEstado != null ? fechaHoraUltimoEstado : LocalDateTime.now());
        pedidoDTO.setEstado_pedido(estadoPedido);
        pedidoDTO.setPromocion(promocion);

        return this.pedidoDAO.insertar(pedidoDTO);
    }

    /**
     * Obtiene un pedido por su ID (incluye detalles, usuario, estado y
     * promoción)
     *
     * @param pedidoId ID del pedido
     * @return PedidoDTO con toda la información o null si no existe
     */
    public PedidoDTO obtenerPorId(Integer pedidoId) {
        if (pedidoId == null || pedidoId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return null;
        }
        return this.pedidoDAO.obtenerPorId(pedidoId);
    }

    /**
     * Lista todos los pedidos (con sus detalles, usuarios, estados y
     * promociones)
     *
     * @return Lista de todos los pedidos
     */
    public ArrayList<PedidoDTO> listarTodos() {
        return this.pedidoDAO.listarTodos();
    }

    /**
     * Modifica un pedido existente
     *
     * @param pedidoId ID del pedido a modificar
     * @param usuario Usuario que realizó el pedido
     * @param fechaHoraCreacion Fecha y hora de creación
     * @param fechaHoraUltimoEstado Fecha y hora del último cambio de estado
     * @param total Total actualizado
     * @param estadoPedido Estado actual del pedido
     * @param promocion Promoción aplicada (puede ser null)
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer modificar(Integer pedidoId, UsuarioDTO usuario,
            LocalDateTime fechaHoraCreacion,
            LocalDateTime fechaHoraUltimoEstado,
            Double total,
            EstadoPedidoDTO estadoPedido,
            PromocionDTO promocion) {
        if (pedidoId == null || pedidoId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Error: Usuario no puede ser nulo");
            return 0;
        }
        if (estadoPedido == null || estadoPedido.getEstado_pedido_id() == null) {
            System.err.println("Error: Estado de pedido no puede ser nulo");
            return 0;
        }
        if (total == null || total < 0) {
            System.err.println("Error: Total debe ser mayor o igual a 0");
            return 0;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setPedido_id(pedidoId);
        pedidoDTO.setUsuario(usuario);
        pedidoDTO.setTotal(total);
        pedidoDTO.setFecha_hora_creacion(fechaHoraCreacion != null ? fechaHoraCreacion : LocalDateTime.now());
        pedidoDTO.setFecha_hora_ultimo_estado(fechaHoraUltimoEstado != null ? fechaHoraUltimoEstado : LocalDateTime.now());
        pedidoDTO.setEstado_pedido(estadoPedido);
        pedidoDTO.setPromocion(promocion);

        return this.pedidoDAO.modificar(pedidoDTO);
    }

    /**
     * Elimina un pedido por su ID Nota: Como existe estado_pedido, normalmente
     * no es necesario eliminar de la BD
     *
     * @param pedidoId ID del pedido a eliminar
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer eliminar(Integer pedidoId) {
        if (pedidoId == null || pedidoId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setPedido_id(pedidoId);
        return this.pedidoDAO.eliminar(pedidoDTO);
    }
}
