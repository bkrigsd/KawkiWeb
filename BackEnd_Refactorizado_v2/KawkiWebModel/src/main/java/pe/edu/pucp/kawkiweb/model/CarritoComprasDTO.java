package pe.edu.pucp.kawkiweb.model;

import java.util.List;

public class CarritoComprasDTO {

    private Integer carrito_id;
    private UsuarioDTO usuario;
    private Double total;
    private PromocionDTO promocion;
    private List<DetalleCarritoDTO> detalles;

    public CarritoComprasDTO() {
        this.carrito_id = null;
        this.usuario = null;
        this.total = null;
        this.promocion = null;
        this.detalles = null;
    }

    public CarritoComprasDTO(Integer carrito_id, UsuarioDTO usuario,
            Double total, PromocionDTO promocion,
            List<DetalleCarritoDTO> detalles) {
        this.carrito_id = carrito_id;
        this.usuario = usuario;
        this.total = total;
        this.promocion = promocion;
        this.detalles = detalles;
    }

    public Integer getCarrito_id() {
        return carrito_id;
    }

    public void setCarrito_id(Integer carrito_id) {
        this.carrito_id = carrito_id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public PromocionDTO getPromocion() {
        return promocion;
    }

    public void setPromocion(PromocionDTO promocion) {
        this.promocion = promocion;
    }

    public List<DetalleCarritoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCarritoDTO> detalles) {
        this.detalles = detalles;
    }
}
