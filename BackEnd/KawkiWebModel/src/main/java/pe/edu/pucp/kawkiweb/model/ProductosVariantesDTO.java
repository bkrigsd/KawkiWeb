package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

//import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pe.edu.pucp.kawkiweb.model.adapter.LocalDateTimeAdapter;
//
//@XmlRootElement(name = "ProductoVariante")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductosVariantesDTO {

//    private static Integer cantidadProdVariantes = 0;
    private Integer prod_variante_id;
    private String SKU;
    private Integer stock;
    private Integer stock_minimo;
    private Boolean alerta_stock;
    private Integer producto_id;
    private ColoresDTO color;
    private TallasDTO talla;
    private String url_imagen;
    private Boolean disponible;
    private UsuariosDTO usuario;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fecha_hora_creacion;

    public ProductosVariantesDTO() {
//        ProductosVariantesDTO.cantidadProdVariantes++;

        this.prod_variante_id = null;
        this.SKU = null;
        this.stock = null;
        this.stock_minimo = null;
        this.alerta_stock = null;
        this.producto_id = null;
        this.color = null;
        this.talla = null;
        this.url_imagen = null;
        this.disponible = null;
        this.usuario = null;
        this.fecha_hora_creacion = LocalDateTime.now();
    }

    public ProductosVariantesDTO(
            Integer prod_variante_id,
            String SKU,
            Integer stock,
            Integer stock_minimo,
            Boolean alerta_stock,
            Integer producto_id,
            ColoresDTO color,
            TallasDTO talla,
            String url_imagen,
            Boolean disponible,
            UsuariosDTO usuario) {

        this.prod_variante_id = prod_variante_id;
        this.SKU = SKU;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.alerta_stock = alerta_stock;
        this.producto_id = producto_id;
        this.color = color;
        this.talla = talla;
        this.url_imagen = url_imagen;
        this.disponible = disponible;
        this.usuario = usuario;
        this.fecha_hora_creacion = LocalDateTime.now();
    }

    public ProductosVariantesDTO(ProductosVariantesDTO prodVariante) {
        this.prod_variante_id = prodVariante.prod_variante_id;
        this.SKU = prodVariante.SKU;
        this.stock = prodVariante.stock;
        this.stock_minimo = prodVariante.stock_minimo;
        this.alerta_stock = prodVariante.alerta_stock;
        this.producto_id = prodVariante.producto_id;
        this.color = prodVariante.color;
        this.talla = prodVariante.talla;
        this.url_imagen = prodVariante.url_imagen;
        this.disponible = prodVariante.disponible;
        this.usuario = prodVariante.usuario;
        this.fecha_hora_creacion = prodVariante.fecha_hora_creacion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID de variante de producto: ").append(this.prod_variante_id).append('\n');
        sb.append("SKU: ").append(this.SKU).append('\n');
        sb.append("Stock actual: ").append(this.stock).append('\n');
        sb.append("Stock mínimo: ").append(this.stock_minimo).append('\n');

        if (this.alerta_stock != null) {
            sb.append("Alerta de stock: ").append(this.alerta_stock ? "Sí" : "No").append('\n');
        } else {
            sb.append("Alerta de stock: No especificada").append('\n');
        }

        sb.append("ID del producto: ").append(this.producto_id).append('\n');
        sb.append("Color: ").append(this.color).append('\n');
        sb.append("Talla: ").append(this.talla).append('\n');

        sb.append("URL de imagen: ")
                .append(this.url_imagen != null ? this.url_imagen : "Sin imagen").append('\n');

        sb.append("Disponible: ")
                .append(this.disponible != null ? (this.disponible ? "Sí" : "No") : "No especificado").append('\n');

        return sb.toString();
    }

    public Integer getProd_variante_id() {
        return prod_variante_id;
    }

    public void setProd_variante_id(Integer prod_variante_id) {
        this.prod_variante_id = prod_variante_id;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(Integer stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public Boolean getAlerta_stock() {
        return alerta_stock;
    }

    public void setAlerta_stock(Boolean alerta_stock) {
        this.alerta_stock = alerta_stock;
    }

    public Integer getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
    }

    public ColoresDTO getColor() {
        return color;
    }

    public void setColor(ColoresDTO color) {
        this.color = color;
    }

    public TallasDTO getTalla() {
        return talla;
    }

    public void setTalla(TallasDTO talla) {
        this.talla = talla;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

//    public static Integer getCantidadProdVariantes() {
//        return cantidadProdVariantes;
//    }
    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

//    public static void setCantidadProdVariantes(Integer aCantidadProdVariantes) {
//        cantidadProdVariantes = aCantidadProdVariantes;
//    }
    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

}
