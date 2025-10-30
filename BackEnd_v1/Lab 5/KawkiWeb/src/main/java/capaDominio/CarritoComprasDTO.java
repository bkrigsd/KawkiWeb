package capaDominio;

import java.util.List;

public class CarritoComprasDTO {

    //ATRIBUTOS
    private Integer carrito_id;
    private Integer usuario_id;
    private Double total;
    private List<DetalleCarritoDTO> detalle;
    private PromocionDTO promocion;

    //CONSTRUCTORES
    public CarritoComprasDTO() {
        this.carrito_id = null;
        this.usuario_id = null;
        this.total = null;
        this.detalle = null;
        this.promocion = null;
    }

    public CarritoComprasDTO(
            Integer carrito_id,
            Integer usuario_id,
            Double total,
            List<DetalleCarritoDTO> detalle,
            PromocionDTO promocion) {

        this.carrito_id = carrito_id;
        this.usuario_id = usuario_id;
        this.total = total;
        this.detalle = detalle;
        this.promocion = promocion;
    }

    //GETTERS Y SETTES
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
        this.setTotal((Double) total);
    }

    public PromocionDTO getPromocion() {
        return promocion;
    }

    public void setPromocion(PromocionDTO promocion) {
        this.promocion = promocion;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleCarritoDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleCarritoDTO> detalle) {
        this.detalle = detalle;
    }

}
