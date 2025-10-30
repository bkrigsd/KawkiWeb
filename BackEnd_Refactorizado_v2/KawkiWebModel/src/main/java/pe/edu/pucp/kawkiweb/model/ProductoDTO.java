package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriaDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstiloDTO;

public class ProductoDTO {

    private Integer producto_id;
    private String descripcion;
    private CategoriaDTO categoria;
    private EstiloDTO estilo;
    private Double precio_venta;
    private LocalDateTime fecha_hora_creacion;
    private List<ProductoVarianteDTO> variantes;

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
            CategoriaDTO categoria,
            EstiloDTO estilo,
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

    public ProductoDTO(ProductoDTO producto) {
        this.producto_id = producto.producto_id;
        this.descripcion = producto.descripcion;
        this.categoria = producto.categoria;
        this.estilo = producto.estilo;
        this.precio_venta = producto.precio_venta;
        this.fecha_hora_creacion = producto.fecha_hora_creacion;
        this.variantes = producto.variantes;
    }

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

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public EstiloDTO getEstilo() {
        return estilo;
    }

    public void setEstilo(EstiloDTO estilo) {
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
