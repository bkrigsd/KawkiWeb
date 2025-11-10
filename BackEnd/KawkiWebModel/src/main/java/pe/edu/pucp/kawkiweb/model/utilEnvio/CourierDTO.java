package pe.edu.pucp.kawkiweb.model.utilEnvio;

public class CourierDTO {

    private Integer courierId;
    private String nombre;

    // Constantes para los IDs de couriers
    public static final int ID_SHALOM = 1;
    public static final int ID_OLVA_COURIER = 2;
    public static final int ID_URBANO = 3;

    public static final String NOMBRE_SHALOM = "Shalom";
    public static final String NOMBRE_OLVA_COURIER = "Olva Courier";
    public static final String NOMBRE_URBANO = "Urbano";

    public CourierDTO() {
        this.courierId = null;
        this.nombre = null;
    }

    public CourierDTO(Integer courierId, String nombre) {
        this.courierId = courierId;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esShalom() {
        return this.courierId != null && this.courierId == ID_SHALOM;
    }

    public boolean esOlvaCourier() {
        return this.courierId != null && this.courierId == ID_OLVA_COURIER;
    }

    public boolean esUrbano() {
        return this.courierId != null && this.courierId == ID_URBANO;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
