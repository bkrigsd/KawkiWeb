package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.PagoDAO;
import pe.edu.pucp.kawkiweb.daoImp.PagoDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPagoDTO;
import pe.edu.pucp.kawkiweb.model.PagoDTO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;

public class PagoBO {

    private PagoDAO pagoDAO;

    public PagoBO() {
        this.pagoDAO = new PagoDAOImpl();
    }

    /**
     * Inserta un nuevo pago
     *
     * @param montoTotal Monto total del pago
     * @param fechaHoraPago Fecha y hora en que se realizó el pago
     * @param metodoPago Método de pago utilizado
     * @param pedido Pedido asociado al pago
     * @return ID del pago insertado o 0 si falla
     */
    public Integer insertar(Double montoTotal, LocalDateTime fechaHoraPago,
            MetodoPagoDTO metodoPago, PedidoDTO pedido) {
        if (metodoPago == null || metodoPago.getMetodo_pago_id() == null) {
            System.err.println("Error: Método de pago no puede ser nulo");
            return 0;
        }
        if (pedido == null || pedido.getPedido_id() == null) {
            System.err.println("Error: Pedido no puede ser nulo");
            return 0;
        }
        if (montoTotal == null || montoTotal <= 0) {
            System.err.println("Error: Monto total debe ser mayor a 0");
            return 0;
        }

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(montoTotal);
        pago.setFecha_hora_pago(fechaHoraPago != null ? fechaHoraPago : LocalDateTime.now());
        pago.setMetodo_pago(metodoPago);
        pago.setPedido(pedido);
        return this.pagoDAO.insertar(pago);
    }

    /**
     * Obtiene un pago por su ID (incluye método de pago, pedido y comprobante)
     *
     * @param pagoId ID del pago
     * @return PagoDTO con toda la información o null si no existe
     */
    public PagoDTO obtenerPorId(Integer pagoId) {
        if (pagoId == null || pagoId <= 0) {
            System.err.println("Error: ID de pago inválido");
            return null;
        }
        return this.pagoDAO.obtenerPorId(pagoId);
    }

    /**
     * Lista todos los pagos (con sus métodos de pago, pedidos y comprobantes)
     *
     * @return Lista de todos los pagos
     */
    public ArrayList<PagoDTO> listarTodos() {
        return this.pagoDAO.listarTodos();
    }

    /**
     * Modifica un pago existente
     *
     * @param pagoId ID del pago a modificar
     * @param montoTotal Monto total actualizado
     * @param fechaHoraPago Fecha y hora actualizada
     * @param metodoPago Método de pago actualizado
     * @param pedido Pedido asociado
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer modificar(Integer pagoId, Double montoTotal,
            LocalDateTime fechaHoraPago, MetodoPagoDTO metodoPago, PedidoDTO pedido) {
        if (pagoId == null || pagoId <= 0) {
            System.err.println("Error: ID de pago inválido");
            return 0;
        }
        if (metodoPago == null || metodoPago.getMetodo_pago_id() == null) {
            System.err.println("Error: Método de pago no puede ser nulo");
            return 0;
        }
        if (pedido == null || pedido.getPedido_id() == null) {
            System.err.println("Error: Pedido no puede ser nulo");
            return 0;
        }
        if (montoTotal == null || montoTotal <= 0) {
            System.err.println("Error: Monto total debe ser mayor a 0");
            return 0;
        }

        PagoDTO pago = new PagoDTO();
        pago.setPago_id(pagoId);
        pago.setMonto_total(montoTotal);
        pago.setFecha_hora_pago(fechaHoraPago != null ? fechaHoraPago : LocalDateTime.now());
        pago.setMetodo_pago(metodoPago);
        pago.setPedido(pedido);
        return this.pagoDAO.modificar(pago);
    }

    /**
     * Elimina un pago por su ID
     *
     * @param pagoId ID del pago a eliminar
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer eliminar(Integer pagoId) {
        if (pagoId == null || pagoId <= 0) {
            System.err.println("Error: ID de pago inválido");
            return 0;
        }

        PagoDTO pago = new PagoDTO();
        pago.setPago_id(pagoId);
        return this.pagoDAO.eliminar(pago);
    }
}
