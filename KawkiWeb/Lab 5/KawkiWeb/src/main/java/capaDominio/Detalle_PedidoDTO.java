package capaDominio;

public class Detalle_PedidoDTO {

    //ATRIBUTOS:
    private Integer detalle_pedido_id;
    private ProductoVarianteDTO productoVar;
    private PedidoDTO pedido;
    private Integer cantidad;
    private Double precio_unitario;
    private Double subtotal;

    //CONSTRUCTORES:   
    public Detalle_PedidoDTO() {
        this.detalle_pedido_id = null;
        this.productoVar = null;
        this.pedido = null;
        this.cantidad = null;
        this.precio_unitario = null;
        this.subtotal = null;
    }

    public Detalle_PedidoDTO(Integer detalle_pedido_id, ProductoVarianteDTO productoVar, PedidoDTO pedido, Integer cantidad, Double precio_unitario, Double subtotal) {
        this.detalle_pedido_id = detalle_pedido_id;
        this.productoVar = productoVar;
        this.pedido = pedido;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    //SETTERS Y GETTERS:
    /**
     * @return the id_detalle_pedido
     */
    public Integer getDetalle_pedido_id() {
        return detalle_pedido_id;
    }

    /**
     * @param detalle_pedido_id the detalle_pedido_id to set
     */
    public void setDetalle_pedido_id(Integer detalle_pedido_id) {
        this.detalle_pedido_id = detalle_pedido_id;
    }

    /**
     * @return the productoVar
     */
    public ProductoVarianteDTO getProductoVar() {
        return productoVar;
    }

    /**
     * @param productoVar the producto to set
     */
    public void setProductoVar(ProductoVarianteDTO productoVar) {
        this.productoVar = productoVar;
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
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * @return the precio_unitario
     */
    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * @return the subtotal
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

}
