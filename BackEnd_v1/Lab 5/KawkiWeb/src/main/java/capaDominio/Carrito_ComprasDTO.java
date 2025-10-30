package capaDominio;

/**
 *
 * @author beker
 */

public class Carrito_ComprasDTO {

    private Integer carrito_id;
    private Integer usuario_id;
    private double total;
    private Integer promocion_id;
    
    public Carrito_ComprasDTO() {
        this.carrito_id = null;
        this.usuario_id = null;
        this.total = 0;
        this.promocion_id = null;
    }

    public Carrito_ComprasDTO(Integer carrito_id, Integer usuario_id,
                              double total, Integer promocion_id) {
        this.carrito_id = carrito_id;
        this.usuario_id = usuario_id;
        this.total = total;
        this.promocion_id = promocion_id;
    }
    
    public Integer getCarrito_id() {
        return carrito_id;
    }

    public void setCarrito_id(Integer carrito_id) {
        this.carrito_id = carrito_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(Integer promocion_id) {
        this.promocion_id = promocion_id;
    }
}
