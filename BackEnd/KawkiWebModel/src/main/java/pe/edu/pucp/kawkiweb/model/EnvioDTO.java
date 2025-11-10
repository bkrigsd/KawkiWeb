package pe.edu.pucp.kawkiweb.model;

import pe.edu.pucp.kawkiweb.model.utilEnvio.CourierDTO;
import pe.edu.pucp.kawkiweb.model.utilEnvio.EstadoEnvioDTO;
import java.time.LocalDateTime;

public class EnvioDTO {
    private Integer envio_id;
    private Boolean es_delivery;
    private String direccion_entrega;
    private CourierDTO courier;  
    private LocalDateTime fecha_envio;
    private Double costo_envio;
    private PedidoDTO pedido;
    private EstadoEnvioDTO estado;
    private LocalDateTime fecha_ultimo_estado;
    
    public EnvioDTO() {
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

    public EnvioDTO(Integer envio_id, Boolean es_delivery, 
            String direccion_entrega, CourierDTO courier, 
            LocalDateTime fecha_envio, Double costo_envio, 
            PedidoDTO pedido, EstadoEnvioDTO estado, 
            LocalDateTime fecha_ultimo_estado) {
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

    public Integer getEnvio_id() {
        return envio_id;
    }

    public void setEnvio_id(Integer envio_id) {
        this.envio_id = envio_id;
    }

    public Boolean getEs_delivery() {
        return es_delivery;
    }

    public void setEs_delivery(Boolean es_delivery) {
        this.es_delivery = es_delivery;
    }

    public String getDireccion_entrega() {
        return direccion_entrega;
    }

    public void setDireccion_entrega(String direccion_entrega) {
        this.direccion_entrega = direccion_entrega;
    }

    public CourierDTO getCourier() {
        return courier;
    }

    public void setCourier(CourierDTO courier) {
        this.courier = courier;
    }

    public LocalDateTime getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(LocalDateTime fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public Double getCosto_envio() {
        return costo_envio;
    }

    public void setCosto_envio(Double costo_envio) {
        this.costo_envio = costo_envio;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    public EstadoEnvioDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvioDTO estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha_ultimo_estado() {
        return fecha_ultimo_estado;
    }

    public void setFecha_ultimo_estado(LocalDateTime fecha_ultimo_estado) {
        this.fecha_ultimo_estado = fecha_ultimo_estado;
    }
}
