package pe.edu.pucp.kawkiweb.model.utilPago;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TiposComprobanteDTO {

    private Integer tipo_comprobante_id;
    private String nombre;

    //Constantes para los datos existentes
    public static final int ID_BOLETA_SIMPLE = 1;
    public static final int ID_BOLETA_DNI = 2;
    public static final int ID_FACTURA = 3;

    public static final String NOMBRE_BOLETA_SIMPLE = "Boleta Simple";
    public static final String NOMBRE_BOLETA_DNI = "Boleta con DNI";
    public static final String NOMBRE_FACTURA = "Factura";

    public TiposComprobanteDTO() {
        this.tipo_comprobante_id = null;
        this.nombre = null;
    }

    public TiposComprobanteDTO(Integer tipo_comprobante_id, String nombre) {
        this.tipo_comprobante_id = tipo_comprobante_id;
        this.nombre = nombre;
    }

    public Integer getTipo_comprobante_id() {
        return tipo_comprobante_id;
    }

    public void setTipo_comprobante_id(Integer tipo_comprobante_id) {
        this.tipo_comprobante_id = tipo_comprobante_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esBoletaSimple() {
        return this.tipo_comprobante_id != null && this.tipo_comprobante_id == ID_BOLETA_SIMPLE;
    }

    public boolean esBoletaConDNI() {
        return this.tipo_comprobante_id != null && this.tipo_comprobante_id == ID_BOLETA_DNI;
    }

    public boolean esFactura() {
        return this.tipo_comprobante_id != null && this.tipo_comprobante_id == ID_FACTURA;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }

}
