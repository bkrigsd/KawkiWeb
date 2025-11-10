package pe.edu.pucp.kawkiweb.model;

import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    //ATRIBUTOS:
    private Integer pedido_id;
    private UsuarioDTO usuario;
    private LocalDateTime fecha_hora_creacion;
    private Double total;
    private EstadoPedidoDTO estado_pedido;
    private LocalDateTime fecha_hora_ultimo_estado;
    private PromocionDTO promocion;
    private List<DetallePedidoDTO> detalles;

    //CONSTRUCTORES:
    public PedidoDTO() {
        this.pedido_id = null;
        this.usuario = null;
        this.fecha_hora_creacion = null;
        this.fecha_hora_ultimo_estado = null;
        this.total = null;
        this.estado_pedido = null;
        this.promocion = null;
        this.detalles = null;
    }

    public PedidoDTO(Integer pedido_id, UsuarioDTO usuario,
            LocalDateTime fecha_hora_creacion,
            LocalDateTime fecha_hora_ultimo_estado, Double total,
            EstadoPedidoDTO estado_pedido, PromocionDTO promocion,
            List<DetallePedidoDTO> detalles) {
        this.pedido_id = pedido_id;
        this.usuario = usuario;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.fecha_hora_ultimo_estado = fecha_hora_ultimo_estado;
        this.total = total;
        this.estado_pedido = estado_pedido;
        this.promocion = promocion;
        this.detalles = detalles;
    }

    public Integer getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public LocalDateTime getFecha_hora_ultimo_estado() {
        return fecha_hora_ultimo_estado;
    }

    public void setFecha_hora_ultimo_estado(LocalDateTime fecha_hora_ultimo_estado) {
        this.fecha_hora_ultimo_estado = fecha_hora_ultimo_estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoPedidoDTO getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(EstadoPedidoDTO estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    public PromocionDTO getPromocion() {
        return promocion;
    }

    public void setPromocion(PromocionDTO promocion) {
        this.promocion = promocion;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }

}
