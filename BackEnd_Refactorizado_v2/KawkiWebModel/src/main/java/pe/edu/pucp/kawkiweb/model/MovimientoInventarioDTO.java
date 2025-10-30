package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TipoMovimientoDTO;

public class MovimientoInventarioDTO {

    private Integer mov_inventario_id;
    private Integer cantidad;
    private LocalDateTime fecha_hora_mov;
    private String observacion;
    private TipoMovimientoDTO tipo_movimiento;  // Cambiado de enum a clase
    private ProductoVarianteDTO prod_variante;

    public MovimientoInventarioDTO() {
        this.mov_inventario_id = null;
        this.cantidad = null;
        this.fecha_hora_mov = null;
        this.observacion = null;
        this.tipo_movimiento = null;
        this.prod_variante = null;
    }

    public MovimientoInventarioDTO(Integer mov_inventario_id, Integer cantidad,
            LocalDateTime fecha_hora_mov, String observacion, TipoMovimientoDTO tipo_movimiento,
            ProductoVarianteDTO prod_variante) {

        this.mov_inventario_id = mov_inventario_id;
        this.cantidad = cantidad;
        this.fecha_hora_mov = fecha_hora_mov;
        this.observacion = observacion;
        this.tipo_movimiento = tipo_movimiento;
        this.prod_variante = prod_variante;
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

    public TipoMovimientoDTO getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(TipoMovimientoDTO tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public ProductoVarianteDTO getProd_variante() {
        return prod_variante;
    }

    public void setProd_variante(ProductoVarianteDTO prod_variante) {
        this.prod_variante = prod_variante;
    }

}
