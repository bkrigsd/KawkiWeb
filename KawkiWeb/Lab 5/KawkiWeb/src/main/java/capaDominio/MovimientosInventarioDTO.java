package capaDominio;

import capaDominio.MovInventarioDetalle.TipoMovimiento;
import java.time.LocalDateTime;

public class MovimientosInventarioDTO {

    //ATRIBUTOS
    private Integer mov_inventario_id;
    private Integer cantidad;
    private LocalDateTime fecha_hora_movimiento;
    private String observacion;
    private TipoMovimiento tipo_movimiento;
    private Integer prod_variante_id;

    //CONSTRUCTORES
    public MovimientosInventarioDTO() {
        this.mov_inventario_id = null;
        this.cantidad = null;
        this.fecha_hora_movimiento = null;
        this.observacion = null;
        this.tipo_movimiento = null;
        this.prod_variante_id = null;
    }

    public MovimientosInventarioDTO(
            Integer mov_inventario_id,
            Integer cantidad,
            LocalDateTime fecha_hora_movimiento,
            String observacion,
            TipoMovimiento tipo_movimiento,
            Integer prod_variante_id) {
        
        this.mov_inventario_id = mov_inventario_id;
        this.cantidad = cantidad;
        this.fecha_hora_movimiento = fecha_hora_movimiento;
        this.observacion = observacion;
        this.tipo_movimiento = tipo_movimiento;
        this.prod_variante_id = prod_variante_id;
    }
    
    //GETTES Y SETTERS

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

    public LocalDateTime getFecha_hora_movimiento() {
        return fecha_hora_movimiento;
    }

    public void setFecha_hora_movimiento(LocalDateTime fecha_hora_movimiento) {
        this.fecha_hora_movimiento = fecha_hora_movimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoMovimiento getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(TipoMovimiento tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public Integer getProd_variante_id() {
        return prod_variante_id;
    }

    public void setProd_variante_id(Integer prod_variante_id) {
        this.prod_variante_id = prod_variante_id;
    }
    
}
