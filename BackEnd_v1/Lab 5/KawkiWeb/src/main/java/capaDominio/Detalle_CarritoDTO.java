package capaDominio;

/**
 *
 * @author beker
 */

public class Detalle_CarritoDTO {
    
    private Integer detalle_carrito_id;
    private Integer carrito_id;
    private Integer cantidad;
    private double precio_unitario;
    private double subtotal;
    private Integer prod_variante_id;

    public Detalle_CarritoDTO() {
        this.detalle_carrito_id = null;
        this.carrito_id = null;
        this.prod_variante_id = null;
        this.cantidad = null;
        this.subtotal = 0;
    }

    public Detalle_CarritoDTO(Integer detalle_carrito_id, Integer carrito_id,
                              Integer producto_id,Integer cantidad,
                              double precio_unitario,double subtotal) {
        this.detalle_carrito_id = detalle_carrito_id;
        this.carrito_id = carrito_id;
        this.prod_variante_id = producto_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
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

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public Integer getProd_variante_id() {
        return prod_variante_id;
    }

    public void setProd_variante_id(Integer prod_variante_id) {
        this.prod_variante_id = prod_variante_id;
    }
}
