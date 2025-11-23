package pe.edu.pucp.kawkiweb.model.utilPago;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MetodosPagoDTO {

    private Integer metodo_pago_id;
    private String nombre;

    //Constantes para los datos existentes
    public static final int ID_TRANSFERENCIA = 1;
    public static final int ID_YAPE = 2;
    public static final int ID_PLIN = 3;

    public static final String NOMBRE_TRANSFERENCIA = "Transferencia";
    public static final String NOMBRE_YAPE = "Yape";
    public static final String NOMBRE_PLIN = "Plin";

    public MetodosPagoDTO() {
        this.metodo_pago_id = null;
        this.nombre = null;
    }

    public MetodosPagoDTO(Integer metodo_pago_id, String nombre) {
        this.metodo_pago_id = metodo_pago_id;
        this.nombre = nombre;
    }

    public Integer getMetodo_pago_id() {
        return metodo_pago_id;
    }

    public void setMetodo_pago_id(Integer metodo_pago_id) {
        this.metodo_pago_id = metodo_pago_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esTransferencia() {
        return this.metodo_pago_id != null && this.metodo_pago_id == ID_TRANSFERENCIA;
    }

    public boolean esYape() {
        return this.metodo_pago_id != null && this.metodo_pago_id == ID_YAPE;
    }

    public boolean esPlin() {
        return this.metodo_pago_id != null && this.metodo_pago_id == ID_PLIN;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
