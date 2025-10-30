package capaDominio;

/**
 *
 * @author beker
 */

public class Detalle_Carrito_ComprasDTO {
    
    //ATRIBUTOS
    private Integer detalle_carrito_id;
    private Integer carrito_id;
    private Integer producto_id;
    private Integer cantidad;
    private double precio_unitario;
    private double subtotal;

    //CONSTRUCTORES:
    public Detalle_Carrito_ComprasDTO() {
        this.detalle_carrito_id = null;
        this.carrito_id = null;
        this.producto_id = null;
        this.cantidad = null;
        this.subtotal = 0;
    }

    public Detalle_Carrito_ComprasDTO(Integer detalle_carrito_id, Integer carrito_id,
            Integer producto_id,Integer cantidad,double precio_unitario,double subtotal) {
        this.detalle_carrito_id = detalle_carrito_id;
        this.carrito_id = carrito_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }
    
    /**
     * @return the detalle_carrito_id
     */
    public Integer getDetalle_carrito_id() {
        return detalle_carrito_id;
    }

    /**
     * @param detalle_carrito_id the detalle_carrito_id to set
     */
    public void setDetalle_carrito_id(Integer detalle_carrito_id) {
        this.detalle_carrito_id = detalle_carrito_id;
    }

    /**
     * @return the carrito_id
     */
    public Integer getCarrito_id() {
        return carrito_id;
    }

    /**
     * @param carrito_id the carrito_id to set
     */
    public void setCarrito_id(Integer carrito_id) {
        this.carrito_id = carrito_id;
    }

    /**
     * @return the producto_id
     */
    public Integer getProducto_id() {
        return producto_id;
    }

    /**
     * @param producto_id the producto_id to set
     */
    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
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
    public double getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
}
