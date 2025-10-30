package capaDominio;

import capaDominio.productoDetalle.Estilo;
import capaDominio.productoDetalle.Categoria;
import java.time.LocalDateTime;
import java.util.List;

public class ProductoDTO {

    //ATRIBUTOS
    private Integer producto_id;
    private String descripcion;
    private Categoria categoria;
    private Estilo estilo;
    private Double precio_venta;
    private LocalDateTime fecha_hora_creacion;
    private List<ProductoVarianteDTO> variantes;

    //CONSTRUCTORES
    public ProductoDTO() {
        this.producto_id = null;
        this.descripcion = null;
        this.categoria = null;
        this.estilo = null;
        this.precio_venta = null;
        this.fecha_hora_creacion = null;
        this.variantes = null;
    }

    public ProductoDTO(
            Integer producto_id,
            String descripcion,
            Categoria categoria,
            Estilo estilo,
            Double precio_venta,
            LocalDateTime fecha_hora_creacion,
            List<ProductoVarianteDTO> variantes) {

        this.producto_id = producto_id;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estilo = estilo;
        this.precio_venta = precio_venta;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.variantes = variantes;
    }

    //GETTES Y SETTERS
    public Integer getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public List<ProductoVarianteDTO> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<ProductoVarianteDTO> variantes) {
        this.variantes = variantes;
    }
    
}
