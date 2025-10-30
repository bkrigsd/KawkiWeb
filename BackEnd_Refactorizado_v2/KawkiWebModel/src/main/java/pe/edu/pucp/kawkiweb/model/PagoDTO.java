package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPagoDTO;

public class PagoDTO {

    //ATRIBUTOS
    private Integer pago_id;
    private Double monto_total;
    private LocalDateTime fecha_hora_pago;
    private MetodoPagoDTO metodo_pago;
    private PedidoDTO pedido;
    private Comprobante_PagoDTO comprobante;

    //CONSTRUCTORES
    public PagoDTO() {
        this.pago_id = null;
        this.monto_total = null;
        this.fecha_hora_pago = null;
        this.metodo_pago = null;
        this.pedido = null;
        this.comprobante = null;
    }

    public PagoDTO(
            Integer pago_id,
            Double monto_total,
            LocalDateTime fecha_hora_creacion,
            MetodoPagoDTO metodo_pago,
            PedidoDTO pedido,
            Comprobante_PagoDTO comprobante) {

        this.pago_id = pago_id;
        this.monto_total = monto_total;
        this.fecha_hora_pago = fecha_hora_creacion;
        this.metodo_pago = metodo_pago;
        this.pedido = pedido;
        this.comprobante = comprobante;
    }

    //SETTERS Y GETTERS
    public Integer getPago_id() {
        return pago_id;
    }

    public void setPago_id(Integer pago_id) {
        this.pago_id = pago_id;
    }

    public Double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
    }

    public LocalDateTime getFecha_hora_pago() {
        return fecha_hora_pago;
    }

    public void setFecha_hora_pago(LocalDateTime fecha_hora_pago) {
        this.fecha_hora_pago = fecha_hora_pago;
    }

    public MetodoPagoDTO getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(MetodoPagoDTO metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    public Comprobante_PagoDTO getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante_PagoDTO comprobante) {
        this.comprobante = comprobante;
    }

}
