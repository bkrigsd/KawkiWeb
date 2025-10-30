package pe.edu.pucp.kawkiweb.model;

import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficioDTO;
import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColorDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallaDTO;

public class ProductoVarianteDTO {

    private Integer prod_variante_id;
    private String SKU;
    private Integer stock;
    private Integer stock_minimo;
    private Boolean alerta_stock;
    private Integer producto_id;
    private ColorDTO color;
    private TallaDTO talla;
    private TipoBeneficioDTO tipo_beneficio;
    private Integer valor_beneficio;
    private LocalDateTime fecha_hora_creacion;

    public ProductoVarianteDTO() {
        this.prod_variante_id = null;
        this.SKU = null;
        this.stock = null;
        this.stock_minimo = null;
        this.alerta_stock = null;
        this.producto_id = null;
        this.color = null;
        this.talla = null;
        this.tipo_beneficio = null;
        this.valor_beneficio = null;
        this.fecha_hora_creacion = null;
    }

    public ProductoVarianteDTO(
            Integer prod_variante_id,
            String SKU,
            Integer stock,
            Integer stock_minimo,
            Boolean alerta_stock,
            Integer producto_id,
            ColorDTO color,
            TallaDTO talla,
            TipoBeneficioDTO tipo_beneficio,
            Integer valor_beneficio,
            LocalDateTime fecha_hora_creacion) {

        this.prod_variante_id = prod_variante_id;
        this.SKU = SKU;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.alerta_stock = alerta_stock;
        this.producto_id = producto_id;
        this.color = color;
        this.talla = talla;
        this.tipo_beneficio = tipo_beneficio;
        this.valor_beneficio = valor_beneficio;
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public ProductoVarianteDTO(ProductoVarianteDTO prodVariante) {
        this.prod_variante_id = prodVariante.prod_variante_id;
        this.SKU = prodVariante.SKU;
        this.stock = prodVariante.stock;
        this.stock_minimo = prodVariante.stock_minimo;
        this.alerta_stock = prodVariante.alerta_stock;
        this.producto_id = prodVariante.producto_id;
        this.color = prodVariante.color;
        this.talla = prodVariante.talla;
        this.tipo_beneficio = prodVariante.tipo_beneficio;
        this.valor_beneficio = prodVariante.valor_beneficio;
        this.fecha_hora_creacion = prodVariante.fecha_hora_creacion;
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

    public ColorDTO getColor() {
        return color;
    }

    public void setColor(ColorDTO color) {
        this.color = color;
    }

    public TallaDTO getTalla() {
        return talla;
    }

    public void setTalla(TallaDTO talla) {
        this.talla = talla;
    }

    public TipoBeneficioDTO getTipo_beneficio() {
        return tipo_beneficio;
    }

    public void setTipo_beneficio(TipoBeneficioDTO tipo_beneficio) {
        this.tipo_beneficio = tipo_beneficio;
    }

    public Integer getValor_beneficio() {
        return valor_beneficio;
    }

    public void setValor_beneficio(Integer valor_beneficio) {
        this.valor_beneficio = valor_beneficio;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }
}
