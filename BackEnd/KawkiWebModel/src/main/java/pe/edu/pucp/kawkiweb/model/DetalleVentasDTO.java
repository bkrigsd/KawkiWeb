package pe.edu.pucp.kawkiweb.model;

//import jakarta.xml.bind.annotation.XmlRootElement;
//
//@XmlRootElement(name = "DetalleVenta")
public class DetalleVentasDTO {

    //ATRIBUTOS:
    private Integer detalle_venta_id;
    private Integer cantidad;
    private Double precio_unitario;
    private Double subtotal;
    private Integer venta_id;
    private ProductosVariantesDTO prodVariante;

    //CONSTRUCTORES:   
    public DetalleVentasDTO() {
        this.detalle_venta_id = null;
        this.prodVariante = null;
        this.venta_id = null;
        this.cantidad = null;
        this.precio_unitario = null;
        this.subtotal = null;
    }

    public DetalleVentasDTO(Integer detalle_venta_id,
            ProductosVariantesDTO prodVariante, Integer venta_id, Integer cantidad,
            Double precio_unitario, Double subtotal) {

        this.detalle_venta_id = detalle_venta_id;
        this.prodVariante = prodVariante;
        this.venta_id = venta_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    public DetalleVentasDTO(DetalleVentasDTO detalleVenta) {

        this.detalle_venta_id = detalleVenta.detalle_venta_id;
        this.prodVariante = detalleVenta.prodVariante;
        this.venta_id = detalleVenta.venta_id;
        this.cantidad = detalleVenta.cantidad;
        this.precio_unitario = detalleVenta.precio_unitario;
        this.subtotal = detalleVenta.subtotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID de detalle de venta: ").append(this.detalle_venta_id).append('\n');
        sb.append("Cantidad: ").append(this.cantidad).append('\n');
        sb.append("Precio unitario: S/ ").append(this.precio_unitario).append('\n');
        sb.append("Subtotal: S/ ").append(this.subtotal).append('\n');
        sb.append("ID de venta: ").append(this.venta_id).append('\n');
        sb.append("Variante de producto: ").append(this.prodVariante).append('\n');

        return sb.toString();
    }

    //SETTERS Y GETTERS:
    public Integer getDetalle_venta_id() {
        return detalle_venta_id;
    }

    public void setDetalle_venta_id(Integer detalle_venta_id) {
        this.detalle_venta_id = detalle_venta_id;
    }

    public ProductosVariantesDTO getProdVariante() {
        return prodVariante;
    }

    public void setProdVariante(ProductosVariantesDTO prodVariante) {
        this.prodVariante = prodVariante;
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

    public Integer getVenta_id() {
        return venta_id;
    }

    public void setVenta_id(Integer venta_id) {
        this.venta_id = venta_id;
    }

}
