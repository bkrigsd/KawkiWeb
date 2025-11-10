package pe.edu.pucp.kawkiweb.model;

public class DetalleCarritoDTO {

    private Integer detalle_carrito_id;
    private Integer carrito_id;
    private Integer cantidad;
    private Double precio_unitario;
    private Double subtotal;
    private ProductoVarianteDTO producto;

    public DetalleCarritoDTO() {
        this.detalle_carrito_id = null;
        this.carrito_id = null;
        this.cantidad = null;
        this.precio_unitario = null;
        this.subtotal = null;
        this.producto = null;
    }

    public DetalleCarritoDTO(Integer detalle_carrito_id, Integer carrito_id,
            Integer cantidad, Double precio_unitario,
            Double subtotal, ProductoVarianteDTO producto) {
        this.detalle_carrito_id = detalle_carrito_id;
        this.carrito_id = carrito_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public Integer getDetalle_carrito_id() {
        return detalle_carrito_id;
    }

    public void setDetalle_carrito_id(Integer detalle_carrito_id) {
        this.detalle_carrito_id = detalle_carrito_id;
    }

    public Integer getCarrito_id() {
        return carrito_id;
    }

    public void setCarrito_id(Integer carrito_id) {
        this.carrito_id = carrito_id;
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

    public ProductoVarianteDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoVarianteDTO producto) {
        this.producto = producto;
    }
}
