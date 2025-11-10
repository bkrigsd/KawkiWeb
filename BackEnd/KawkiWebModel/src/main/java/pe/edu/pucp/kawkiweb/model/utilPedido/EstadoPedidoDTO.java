package pe.edu.pucp.kawkiweb.model.utilPedido;

public class EstadoPedidoDTO {

    private Integer estado_pedido_id;
    private String nombre;

    // Constantes para los IDs
    public static final int ID_PENDIENTE = 1;
    public static final int ID_PAGADO = 2;
    public static final int ID_PREPARACION = 3;
    public static final int ID_LISTO_PARA_ENTREGAR = 4;
    public static final int ID_ENTREGADO = 5;
    public static final int ID_ANULADO = 6;

    public static final String NOMBRE_PENDIENTE = "Pendiente";
    public static final String NOMBRE_PAGADO = "Pagado";
    public static final String NOMBRE_PREPARACION = "Preparacion";
    public static final String NOMBRE_LISTO_PARA_ENTREGAR = "Listo para entregar";
    public static final String NOMBRE_ENTREGADO = "Entregado";
    public static final String NOMBRE_ANULADO = "Anulado";

    public EstadoPedidoDTO() {
        this.estado_pedido_id = null;
        this.nombre = null;
    }

    public EstadoPedidoDTO(Integer estado_pedido_id, String nombre) {
        this.estado_pedido_id = estado_pedido_id;
        this.nombre = nombre;
    }

    public EstadoPedidoDTO(EstadoPedidoDTO estadoPedido) {
        this.estado_pedido_id = estadoPedido.estado_pedido_id;
        this.nombre = estadoPedido.nombre;
    }

    public Integer getEstado_pedido_id() {
        return estado_pedido_id;
    }

    public void setEstado_pedido_id(Integer estado_pedido_id) {
        this.estado_pedido_id = estado_pedido_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esPendiente() {
        return this.estado_pedido_id != null && this.estado_pedido_id == ID_PENDIENTE;
    }

    public boolean esPagado() {
        return this.estado_pedido_id != null && this.estado_pedido_id == ID_PAGADO;
    }

    public boolean esPreparacion() {
        return this.estado_pedido_id != null && this.estado_pedido_id == ID_PREPARACION;
    }

    public boolean esListoParaEntregar() {
        return this.estado_pedido_id != null && this.estado_pedido_id == ID_LISTO_PARA_ENTREGAR;
    }

    public boolean esEntregado() {
        return this.estado_pedido_id != null && this.estado_pedido_id == ID_ENTREGADO;
    }

    public boolean esAnulado() {
        return this.estado_pedido_id != null && this.estado_pedido_id == ID_ANULADO;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
