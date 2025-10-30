package capaDominio;

import capaDominio.envioDetalle.Estado_Envio;
import capaDominio.envioDetalle.Courier;
import java.time.LocalDateTime;

public class EnviosDTO {

    private Integer envio_id;
    private Boolean es_delivery;
    private String direccion_entrega;
    private Courier courier;
    private LocalDateTime fecha_envio;
    private Double costo_envio;
    private PedidoDTO pedido;
    private Estado_Envio estado;
    private LocalDateTime fecha_ultimo_estado;
    
    public EnviosDTO() {
        this.envio_id = null;
        this.es_delivery = null;
        this.direccion_entrega = null;
        this.courier = null;
        this.fecha_envio = null;
        this.costo_envio = null;
        this.pedido = null;
        this.estado = null;
        this.fecha_ultimo_estado = null;
    }

    public EnviosDTO(Integer envio_id, Boolean es_delivery, String direccion_entrega, Courier courier, LocalDateTime fecha_envio, Double costo_envio, PedidoDTO pedido, Estado_Envio estado, LocalDateTime fecha_ultimo_estado) {
        this.envio_id = envio_id;
        this.es_delivery = es_delivery;
        this.direccion_entrega = direccion_entrega;
        this.courier = courier;
        this.fecha_envio = fecha_envio;
        this.costo_envio = costo_envio;
        this.pedido = pedido;
        this.estado = estado;
        this.fecha_ultimo_estado = fecha_ultimo_estado;
    }

    /**
     * @return the envio_id
     */
    public Integer getEnvio_id() {
        return envio_id;
    }

    /**
     * @param envio_id the envio_id to set
     */
    public void setEnvio_id(Integer envio_id) {
        this.envio_id = envio_id;
    }

    /**
     * @return the es_delivery
     */
    public Boolean getEs_delivery() {
        return es_delivery;
    }

    /**
     * @param es_delivery the es_delivery to set
     */
    public void setEs_delivery(Boolean es_delivery) {
        this.es_delivery = es_delivery;
    }

    /**
     * @return the direccion_entrega
     */
    public String getDireccion_entrega() {
        return direccion_entrega;
    }

    /**
     * @param direccion_entrega the direccion_entrega to set
     */
    public void setDireccion_entrega(String direccion_entrega) {
        this.direccion_entrega = direccion_entrega;
    }

    /**
     * @return the courier
     */
    public Courier getCourier() {
        return courier;
    }

    /**
     * @param courier the courier to set
     */
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    /**
     * @return the fecha_envio
     */
    public LocalDateTime getFecha_envio() {
        return fecha_envio;
    }

    /**
     * @param fecha_envio the fecha_envio to set
     */
    public void setFecha_envio(LocalDateTime fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    /**
     * @return the costo_envio
     */
    public Double getCosto_envio() {
        return costo_envio;
    }

    /**
     * @param costo_envio the costo_envio to set
     */
    public void setCosto_envio(Double costo_envio) {
        this.costo_envio = costo_envio;
    }

    /**
     * @return the pedido
     */
    public PedidoDTO getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the estado
     */
    public Estado_Envio getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado_Envio estado) {
        this.estado = estado;
    }

    /**
     * @return the fecha_ultimo_estado
     */
    public LocalDateTime getFecha_ultimo_estado() {
        return fecha_ultimo_estado;
    }

    /**
     * @param fecha_ultimo_estado the fecha_ultimo_estado to set
     */
    public void setFecha_ultimo_estado(LocalDateTime fecha_ultimo_estado) {
        this.fecha_ultimo_estado = fecha_ultimo_estado;
    }
}
