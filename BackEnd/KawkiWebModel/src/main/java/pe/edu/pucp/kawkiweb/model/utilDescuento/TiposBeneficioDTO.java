package pe.edu.pucp.kawkiweb.model.utilDescuento;

import jakarta.xml.bind.annotation.*;
//import jakarta.xml.bind.annotation.XmlRootElement;
//
//@XmlRootElement(name = "tipo_beneficio")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TiposBeneficioDTO {

    private Integer tipo_beneficio_id;
    private String nombre;

    // Constantes para los IDs de tipos de beneficio
    public static final int ID_DESCUENTO_PORCENTAJE = 1;
    public static final int ID_DESCUENTO_FIJO = 2;
    public static final int ID_ENVIO_GRATIS = 3;

    public static final String NOMBRE_DESCUENTO_PORCENTAJE = "Descuento por porcentaje";
    public static final String NOMBRE_DESCUENTO_FIJO = "Descuento fijo";
    public static final String NOMBRE_ENVIO_GRATIS = "Envío gratis";

    public TiposBeneficioDTO() {
        this.tipo_beneficio_id = null;
        this.nombre = null;
    }

    public TiposBeneficioDTO(Integer tipo_beneficio_id, String nombre) {
        this.tipo_beneficio_id = tipo_beneficio_id;
        this.nombre = nombre;
    }

    public TiposBeneficioDTO(TiposBeneficioDTO tipoBeneficio) {
        this.tipo_beneficio_id = tipoBeneficio.tipo_beneficio_id;
        this.nombre = tipoBeneficio.nombre;
    }

    public Integer getTipo_beneficio_id() {
        return tipo_beneficio_id;
    }

    public void setTipo_beneficio_id(Integer tipo_beneficio_id) {
        this.tipo_beneficio_id = tipo_beneficio_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esDescuentoPorcentaje() {
        return this.tipo_beneficio_id != null && this.tipo_beneficio_id == ID_DESCUENTO_PORCENTAJE;
    }

    public boolean esDescuentoFijo() {
        return this.tipo_beneficio_id != null && this.tipo_beneficio_id == ID_DESCUENTO_FIJO;
    }

    public boolean esEnvioGratis() {
        return this.tipo_beneficio_id != null && this.tipo_beneficio_id == ID_ENVIO_GRATIS;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
