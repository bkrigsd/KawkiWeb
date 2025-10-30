package capaDominio;

import capaDominio.pedidoDetalle.Estado_Pedido;
import java.time.LocalDateTime;

public class PedidoDTO {

    //ATRIBUTOS:
    private Integer pedido_id;
    private UsuarioDTO usuario;
    private LocalDateTime fecha_hora_creacion;
    private LocalDateTime fecha_hora_ultimo_estado;
    private Double total;
    private Estado_Pedido estado_pedido;
    private PromocionDTO promocion;

    //CONSTRUCTORES:
    public PedidoDTO() {
        this.pedido_id = pedido_id;
        this.usuario = usuario;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.fecha_hora_ultimo_estado = fecha_hora_ultimo_estado;
        this.total = total;
        this.estado_pedido = estado_pedido;
        this.promocion = promocion;
    }

    public PedidoDTO(Integer pedido_id, UsuarioDTO usuario, LocalDateTime fecha_hora_creacion, LocalDateTime fecha_hora_ultimo_estado, Double total, Estado_Pedido estado_pedido, PromocionDTO promocion) {
        this.pedido_id = pedido_id;
        this.usuario = usuario;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.fecha_hora_ultimo_estado = fecha_hora_ultimo_estado;
        this.total = total;
        this.estado_pedido = estado_pedido;
        this.promocion = promocion;
    }

    //GETTERS Y SETTERS:
        /**
     * @return the pedido_id
     */
    public Integer getPedido_id() {
        return pedido_id;
    }

    /**
     * @param pedido_id the pedido_id to set
     */
    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
    }

    /**
     * @return the usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the fecha_hora_creacion
     */
    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    /**
     * @param fecha_hora_creacion the fecha_hora_creacion to set
     */
    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    /**
     * @return the fecha_hora_ultimo_estado
     */
    public LocalDateTime getFecha_hora_ultimo_estado() {
        return fecha_hora_ultimo_estado;
    }

    /**
     * @param fecha_hora_ultimo_estado the fecha_hora_ultimo_estado to set
     */
    public void setFecha_hora_ultimo_estado(LocalDateTime fecha_hora_ultimo_estado) {
        this.fecha_hora_ultimo_estado = fecha_hora_ultimo_estado;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the estado_pedido
     */
    public Estado_Pedido getEstado_pedido() {
        return estado_pedido;
    }

    /**
     * @param estado_pedido the estado_pedido to set
     */
    public void setEstado_pedido(Estado_Pedido estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    /**
     * @return the promocion
     */
    public PromocionDTO getPromocion() {
        return promocion;
    }

    /**
     * @param promocion the promocion to set
     */
    public void setPromocion(PromocionDTO promocion) {
        this.promocion = promocion;
    }
    
}
