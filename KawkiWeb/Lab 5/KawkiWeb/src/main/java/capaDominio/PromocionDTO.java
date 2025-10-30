package capaDominio;

import capaDominio.promocionDetalle.TipoBeneficio;
import capaDominio.promocionDetalle.TipoCondicion;
import java.time.LocalDateTime;

public class PromocionDTO {

    //ATRIBUTOS
    private Integer promocion_id;
    private String descripcion;
    private TipoCondicion tipo_condicion;
    private Integer valor_condicion;
    private TipoBeneficio tipo_beneficio;
    private Integer valor_beneficio;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private Boolean activo;

    //CONSTRUCTORES
    public PromocionDTO() {
        this.promocion_id = null;
        this.descripcion = null;
        this.tipo_condicion = null;
        this.valor_condicion = null;
        this.tipo_beneficio = null;
        this.valor_beneficio = null;
        this.fecha_inicio = null;
        this.fecha_fin = null;
        this.activo = null;
    }

    public PromocionDTO(
            Integer promocion_id,
            String descripcion,
            TipoCondicion tipo_condicion,
            Integer valor_condicion,
            TipoBeneficio tipo_beneficio,
            Integer valor_beneficio,
            LocalDateTime fecha_inicio,
            LocalDateTime fecha_fin,
            Boolean activo) {

        this.promocion_id = promocion_id;
        this.descripcion = descripcion;
        this.tipo_condicion = tipo_condicion;
        this.valor_condicion = valor_condicion;
        this.tipo_beneficio = tipo_beneficio;
        this.valor_beneficio = valor_beneficio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.activo = activo;
    }
    
    //GETTERS Y SETTERS

    public Integer getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(Integer promocion_id) {
        this.promocion_id = promocion_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoCondicion getTipo_condicion() {
        return tipo_condicion;
    }

    public void setTipo_condicion(TipoCondicion tipo_condicion) {
        this.tipo_condicion = tipo_condicion;
    }

    public Integer getValor_condicion() {
        return valor_condicion;
    }

    public void setValor_condicion(Integer valor_condicion) {
        this.valor_condicion = valor_condicion;
    }

    public TipoBeneficio getTipo_beneficio() {
        return tipo_beneficio;
    }

    public void setTipo_beneficio(TipoBeneficio tipo_beneficio) {
        this.tipo_beneficio = tipo_beneficio;
    }

    public Integer getValor_beneficio() {
        return valor_beneficio;
    }

    public void setValor_beneficio(Integer valor_beneficio) {
        this.valor_beneficio = valor_beneficio;
    }

    public LocalDateTime getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDateTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDateTime getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDateTime fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
}