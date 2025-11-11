package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.daoImp.VentasDAOImpl;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.dao.VentasDAO;

public class VentasBO {

    private VentasDAO ventaDAO;

    public VentasBO() {
        this.ventaDAO = new VentasDAOImpl();
    }

    /**
     * Inserta un nuevo pedido
     *
     * @param usuario Usuario que realiza el pedido
     * @param fechaHoraCreacion Fecha y hora de creación del pedido
     * @param fechaHoraUltimoEstado Fecha y hora del último cambio de estado
     * @param total Total del pedido
     * @param estadoPedido Estado actual del pedido
     * @param descuento Promoción aplicada (puede ser null)
     * @return ID del pedido insertado o 0 si falla
     */
    public Integer insertar(UsuariosDTO usuario,
            LocalDateTime fechaHoraCreacion,
            Double total,
            DescuentosDTO descuento) {
        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Error: Usuario no puede ser nulo");
            return 0;
        }

        if (total == null || total < 0) {
            System.err.println("Error: Total debe ser mayor o igual a 0");
            return 0;
        }

        VentasDTO ventaDTO = new VentasDTO();
        ventaDTO.setUsuario(usuario);
        ventaDTO.setTotal(total);
        ventaDTO.setFecha_hora_creacion(fechaHoraCreacion != null ? fechaHoraCreacion : LocalDateTime.now());
        ventaDTO.setDescuento(descuento);

        return this.ventaDAO.insertar(ventaDTO);
    }

    /**
     * Obtiene un pedido por su ID (incluye detalles, usuario, estado y
     * promoción)
     *
     * @param ventaId ID del pedido
     * @return VentasDTO con toda la información o null si no existe
     */
    public VentasDTO obtenerPorId(Integer ventaId) {
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return null;
        }
        return this.ventaDAO.obtenerPorId(ventaId);
    }

    /**
     * Lista todos los pedidos (con sus detalles, usuarios, estados y
     * promociones)
     *
     * @return Lista de todos los pedidos
     */
    public ArrayList<VentasDTO> listarTodos() {
        return this.ventaDAO.listarTodos();
    }

    /**
     * Modifica un pedido existente
     *
     * @param ventaId ID del pedido a modificar
     * @param usuario Usuario que realizó el pedido
     * @param fechaHoraCreacion Fecha y hora de creación
     * @param fechaHoraUltimoEstado Fecha y hora del último cambio de estado
     * @param total Total actualizado
     * @param estadoPedido Estado actual del pedido
     * @param promocion Promoción aplicada (puede ser null)
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer modificar(Integer ventaId, UsuariosDTO usuario,
            LocalDateTime fechaHoraCreacion,
            Double total,
            DescuentosDTO descuento) {
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Error: Usuario no puede ser nulo");
            return 0;
        }

        if (total == null || total < 0) {
            System.err.println("Error: Total debe ser mayor o igual a 0");
            return 0;
        }

        VentasDTO ventaDTO = new VentasDTO();
        ventaDTO.setVenta_id(ventaId);
        ventaDTO.setUsuario(usuario);
        ventaDTO.setTotal(total);
        ventaDTO.setFecha_hora_creacion(fechaHoraCreacion != null ? fechaHoraCreacion : LocalDateTime.now());
        ventaDTO.setDescuento(descuento);

        return this.ventaDAO.modificar(ventaDTO);
    }

    /**
     * Elimina un pedido por su ID Nota: Como existe estado_pedido, normalmente
     * no es necesario eliminar de la BD
     *
     * @param ventaId ID del pedido a eliminar
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer eliminar(Integer ventaId) {
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }

        VentasDTO pedidoDTO = new VentasDTO();
        pedidoDTO.setVenta_id(ventaId);
        return this.ventaDAO.eliminar(pedidoDTO);
    }
}
