package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

//import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pe.edu.pucp.kawkiweb.model.adapter.LocalDateTimeAdapter;
//
//@XmlRootElement(name = "MovimientoInventario")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MovimientosInventarioDTO {

    private Integer mov_inventario_id;
    private Integer cantidad;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fecha_hora_mov;
    private String observacion;
    private TiposMovimientoDTO tipo_movimiento;
    private ProductosVariantesDTO prod_variante;
    private UsuariosDTO usuario;

    public MovimientosInventarioDTO() {
        this.mov_inventario_id = null;
        this.cantidad = null;
        this.fecha_hora_mov = LocalDateTime.now();
        this.observacion = null;
        this.tipo_movimiento = null;
        this.prod_variante = null;
        this.usuario = null;
    }

    public MovimientosInventarioDTO(Integer mov_inventario_id, Integer cantidad,
            String observacion, TiposMovimientoDTO tipo_movimiento,
            ProductosVariantesDTO prod_variante, UsuariosDTO usuario) {

        this.mov_inventario_id = mov_inventario_id;
        this.cantidad = cantidad;
        this.fecha_hora_mov = LocalDateTime.now();
        this.observacion = observacion;
        this.tipo_movimiento = tipo_movimiento;
        this.prod_variante = prod_variante;
        this.usuario = usuario;
    }

    public MovimientosInventarioDTO(MovimientosInventarioDTO movimiento) {

        this.mov_inventario_id = movimiento.mov_inventario_id;
        this.cantidad = movimiento.cantidad;
        this.fecha_hora_mov = movimiento.fecha_hora_mov;
        this.observacion = movimiento.observacion;
        this.tipo_movimiento = movimiento.tipo_movimiento;
        this.prod_variante = movimiento.prod_variante;
        this.usuario = movimiento.usuario;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID de movimiento de inventario: ").append(this.mov_inventario_id).append('\n');
        sb.append("Cantidad: ").append(this.cantidad).append('\n');
        sb.append("Fecha y hora del movimiento: ").append(this.fecha_hora_mov).append('\n');
        sb.append("Observación: ").append(this.observacion != null ? this.observacion : "Sin observaciones").append('\n');
        sb.append("Tipo de movimiento: ").append(this.tipo_movimiento).append('\n');
        sb.append("Variante de producto: ").append(this.prod_variante).append('\n');
        sb.append("Usuario responsable: ").append(this.usuario).append('\n');

        return sb.toString();
    }

    public Integer getMov_inventario_id() {
        return mov_inventario_id;
    }

    public void setMov_inventario_id(Integer mov_inventario_id) {
        this.mov_inventario_id = mov_inventario_id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha_hora_mov() {
        return fecha_hora_mov;
    }

    public void setFecha_hora_mov(LocalDateTime fecha_hora_mov) {
        this.fecha_hora_mov = fecha_hora_mov;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TiposMovimientoDTO getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(TiposMovimientoDTO tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public ProductosVariantesDTO getProd_variante() {
        return prod_variante;
    }

    public void setProd_variante(ProductosVariantesDTO prod_variante) {
        this.prod_variante = prod_variante;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

}
