package pe.edu.pucp.kawkiweb.model.utilEnvio;

public class EstadoEnvioDTO {

    private Integer estadoEnvioId;
    private String nombre;

    // Constantes para los IDs de estados de envío
    public static final int ID_PENDIENTE = 1;
    public static final int ID_PROGRAMADO = 2;
    public static final int ID_ENCARGADO_AL_COURIER = 3;
    public static final int ID_DISPONIBLE_EN_ALMACEN = 4;
    public static final int ID_ENTREGADO = 5;
    public static final int ID_ANULADO = 6;

    public static final String NOMBRE_PENDIENTE = "Pendiente";
    public static final String NOMBRE_PROGRAMADO = "Programado";
    public static final String NOMBRE_ENCARGADO_AL_COURIER = "Encargado al courier";
    public static final String NOMBRE_DISPONIBLE_EN_ALMACEN = "Disponible en almacén";
    public static final String NOMBRE_ENTREGADO = "Entregado";
    public static final String NOMBRE_ANULADO = "Anulado";

    public EstadoEnvioDTO() {
        this.estadoEnvioId = null;
        this.nombre = null;
    }

    public EstadoEnvioDTO(Integer estadoEnvioId, String nombre) {
        this.estadoEnvioId = estadoEnvioId;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getEstadoEnvioId() {
        return estadoEnvioId;
    }

    public void setEstadoEnvioId(Integer estadoEnvioId) {
        this.estadoEnvioId = estadoEnvioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el estado
    public boolean esPendiente() {
        return this.estadoEnvioId != null && this.estadoEnvioId == ID_PENDIENTE;
    }

    public boolean esProgramado() {
        return this.estadoEnvioId != null && this.estadoEnvioId == ID_PROGRAMADO;
    }

    public boolean esEncargadoAlCourier() {
        return this.estadoEnvioId != null && this.estadoEnvioId == ID_ENCARGADO_AL_COURIER;
    }

    public boolean esDisponibleEnAlmacen() {
        return this.estadoEnvioId != null && this.estadoEnvioId == ID_DISPONIBLE_EN_ALMACEN;
    }

    public boolean esEntregado() {
        return this.estadoEnvioId != null && this.estadoEnvioId == ID_ENTREGADO;
    }

    public boolean esAnulado() {
        return this.estadoEnvioId != null && this.estadoEnvioId == ID_ANULADO;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
