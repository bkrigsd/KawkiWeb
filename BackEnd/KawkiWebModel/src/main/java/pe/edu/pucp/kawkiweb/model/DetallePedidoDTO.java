package pe.edu.pucp.kawkiweb.model;

public class DetallePedidoDTO {

    //ATRIBUTOS:
    private Integer detalle_pedido_id;
    private Integer cantidad;
    private Double precio_unitario;
    private Double subtotal;
    private Integer pedido_id;
    private ProductoVarianteDTO productoVar;

    //CONSTRUCTORES:   
    public DetallePedidoDTO() {
        this.detalle_pedido_id = null;
        this.productoVar = null;
        this.pedido_id = null;
        this.cantidad = null;
        this.precio_unitario = null;
        this.subtotal = null;
    }

    public DetallePedidoDTO(Integer detalle_pedido_id,
            ProductoVarianteDTO productoVar, Integer pedido_id, Integer cantidad,
            Double precio_unitario, Double subtotal) {
        this.detalle_pedido_id = detalle_pedido_id;
        this.productoVar = productoVar;
        this.pedido_id = pedido_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    //SETTERS Y GETTERS:
    public Integer getDetalle_pedido_id() {
        return detalle_pedido_id;
    }

    public void setDetalle_pedido_id(Integer detalle_pedido_id) {
        this.detalle_pedido_id = detalle_pedido_id;
    }

    public ProductoVarianteDTO getProductoVar() {
        return productoVar;
    }

    public void setProductoVar(ProductoVarianteDTO productoVar) {
        this.productoVar = productoVar;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
    }

}
