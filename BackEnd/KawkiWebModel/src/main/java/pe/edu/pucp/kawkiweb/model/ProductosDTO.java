package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

//import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pe.edu.pucp.kawkiweb.model.adapter.LocalDateTimeAdapter;
//
//@XmlRootElement(name = "Producto")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductosDTO {

    private Integer producto_id;
    private String descripcion;
    private CategoriasDTO categoria;
    private EstilosDTO estilo;
    private Double precio_venta;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class) 
    private LocalDateTime fecha_hora_creacion;
    private UsuariosDTO usuario;
    private List<ProductosVariantesDTO> variantes;

    public ProductosDTO() {
        this.producto_id = null;
        this.descripcion = null;
        this.categoria = null;
        this.estilo = null;
        this.precio_venta = 0.0;
        this.fecha_hora_creacion = LocalDateTime.now();
        this.usuario = null;
        this.variantes = null;
    }

    public ProductosDTO(
            Integer producto_id,
            String descripcion,
            CategoriasDTO categoria,
            EstilosDTO estilo,
            Double precio_venta,
            UsuariosDTO usuario,
            List<ProductosVariantesDTO> variantes) {

        this.producto_id = producto_id;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estilo = estilo;
        this.precio_venta = precio_venta;
        this.fecha_hora_creacion = LocalDateTime.now();
        this.usuario = usuario;
        this.variantes = variantes;
    }

    public ProductosDTO(ProductosDTO producto) {
        this.producto_id = producto.producto_id;
        this.descripcion = producto.descripcion;
        this.categoria = producto.categoria;
        this.estilo = producto.estilo;
        this.precio_venta = producto.precio_venta;
        this.fecha_hora_creacion = producto.fecha_hora_creacion;
        this.usuario = producto.usuario;
        this.variantes = producto.variantes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID del producto: ").append(this.producto_id).append('\n');
        sb.append("Descripción: ").append(this.descripcion).append('\n');
        sb.append("Categoría: ").append(this.categoria).append('\n');
        sb.append("Estilo: ").append(this.estilo).append('\n');
        sb.append("Precio de venta: ").append(this.precio_venta).append('\n');

        return sb.toString();
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

    public CategoriasDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriasDTO categoria) {
        this.categoria = categoria;
    }

    public EstilosDTO getEstilo() {
        return estilo;
    }

    public void setEstilo(EstilosDTO estilo) {
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

    public List<ProductosVariantesDTO> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<ProductosVariantesDTO> variantes) {
        this.variantes = variantes;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

}
