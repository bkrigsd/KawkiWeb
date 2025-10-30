package capaDominio;

import capaDominio.pagoDetalle.MetodoPago;
import java.time.LocalDateTime;

public class PagoDTO {

    //ATRIBUTOS
    private Integer pago_id;
    private Double monto_total;
    private LocalDateTime fecha_hora_creacion;
    private MetodoPago metodo_pago;
    private Integer pedido_id;
    private Comprobante_PagoDTO comprobante;

    //CONSTRUCTORES
    public PagoDTO() {
        this.pago_id = null;
        this.monto_total = null;
        this.fecha_hora_creacion = null;
        this.metodo_pago = null;
        this.pedido_id = null;
        this.comprobante = null;
    }

    public PagoDTO(
            Integer pago_id,
            Double monto_total,
            LocalDateTime fecha_hora_creacion,
            MetodoPago metodo_pago,
            Integer pedido_id,
            Comprobante_PagoDTO comprobante) {

        this.pago_id = pago_id;
        this.monto_total = monto_total;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.metodo_pago = metodo_pago;
        this.pedido_id = pedido_id;
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

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public MetodoPago getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(MetodoPago metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public Integer getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
    }

    public Comprobante_PagoDTO getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante_PagoDTO comprobante) {
        this.comprobante = comprobante;
    }

}
