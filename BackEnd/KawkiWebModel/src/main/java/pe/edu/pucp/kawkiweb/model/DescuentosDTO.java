package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

//import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
//import pe.edu.pucp.kawkiweb.model.adapter.LocalDateTimeAdapter;
//
//@XmlRootElement(name = "descuento")
public class DescuentosDTO {

    private Integer descuento_id;
    private String descripcion;
    private TiposCondicionDTO tipo_condicion;
    private Integer valor_condicion;
    private TiposBeneficioDTO tipo_beneficio;
    private Integer valor_beneficio;
//    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fecha_inicio;
//    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fecha_fin;
    private Boolean activo;

    public DescuentosDTO() {
        this.descuento_id = null;
        this.descripcion = null;
        this.tipo_condicion = null;
        this.valor_condicion = null;
        this.tipo_beneficio = null;
        this.valor_beneficio = null;
        this.fecha_inicio = null;
        this.fecha_fin = null;
        this.activo = null;
    }

    public DescuentosDTO(Integer descuento_id, String descripcion, TiposCondicionDTO tipo_condicion,
            Integer valor_condicion, TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio,
            LocalDateTime fecha_inicio, LocalDateTime fecha_fin, Boolean activo) {

        this.descuento_id = descuento_id;
        this.descripcion = descripcion;
        this.tipo_condicion = tipo_condicion;
        this.valor_condicion = valor_condicion;
        this.tipo_beneficio = tipo_beneficio;
        this.valor_beneficio = valor_beneficio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.activo = activo;
    }

    public DescuentosDTO(DescuentosDTO descuento) {
        this.descuento_id = descuento.descuento_id;
        this.descripcion = descuento.descripcion;
        this.tipo_condicion = descuento.tipo_condicion;
        this.valor_condicion = descuento.valor_condicion;
        this.tipo_beneficio = descuento.tipo_beneficio;
        this.valor_beneficio = descuento.valor_beneficio;
        this.fecha_inicio = descuento.fecha_inicio;
        this.fecha_fin = descuento.fecha_fin;
        this.activo = descuento.activo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID de descuento: ").append(this.descuento_id).append('\n');
        sb.append("Descripción: ").append(this.descripcion).append('\n');
        sb.append("Tipo de condición: ").append(this.tipo_condicion).append('\n');
        sb.append("Valor de la condición: ").append(this.valor_condicion).append('\n');
        sb.append("Tipo de beneficio: ").append(this.tipo_beneficio).append('\n');
        sb.append("Valor del beneficio: ").append(this.valor_beneficio).append('\n');
        sb.append("Fecha de inicio: ").append(this.fecha_inicio).append('\n');
        sb.append("Fecha de fin: ").append(this.fecha_fin).append('\n');
        sb.append("Activo: ").append(this.activo ? "Sí" : "No").append('\n');

        return sb.toString();
    }

    public Integer getDescuento_id() {
        return descuento_id;
    }

    public void setDescuento_id(Integer descuento_id) {
        this.descuento_id = descuento_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TiposCondicionDTO getTipo_condicion() {
        return tipo_condicion;
    }

    public void setTipo_condicion(TiposCondicionDTO tipo_condicion) {
        this.tipo_condicion = tipo_condicion;
    }

    public Integer getValor_condicion() {
        return valor_condicion;
    }

    public void setValor_condicion(Integer valor_condicion) {
        this.valor_condicion = valor_condicion;
    }

    public TiposBeneficioDTO getTipo_beneficio() {
        return tipo_beneficio;
    }

    public void setTipo_beneficio(TiposBeneficioDTO tipo_beneficio) {
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
