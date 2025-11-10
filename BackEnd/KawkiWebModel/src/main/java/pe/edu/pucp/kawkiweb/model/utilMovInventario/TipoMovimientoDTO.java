package pe.edu.pucp.kawkiweb.model.utilMovInventario;

public class TipoMovimientoDTO {

    private Integer tipoMovimientoId;
    private String nombre;

    // Constantes para los IDs de tipos de movimiento
    public static final int ID_INGRESO = 1;
    public static final int ID_SALIDA = 2;
    public static final int ID_AJUSTE = 3;

    public static final String NOMBRE_INGRESO = "Ingreso";
    public static final String NOMBRE_SALIDA = "Salida";
    public static final String NOMBRE_AJUSTE = "Ajuste";

    public TipoMovimientoDTO() {
        this.tipoMovimientoId = null;
        this.nombre = null;
    }

    public TipoMovimientoDTO(Integer tipoMovimientoId, String nombre) {
        this.tipoMovimientoId = tipoMovimientoId;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esIngreso() {
        return this.tipoMovimientoId != null && this.tipoMovimientoId == ID_INGRESO;
    }

    public boolean esSalida() {
        return this.tipoMovimientoId != null && this.tipoMovimientoId == ID_SALIDA;
    }

    public boolean esAjuste() {
        return this.tipoMovimientoId != null && this.tipoMovimientoId == ID_AJUSTE;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
