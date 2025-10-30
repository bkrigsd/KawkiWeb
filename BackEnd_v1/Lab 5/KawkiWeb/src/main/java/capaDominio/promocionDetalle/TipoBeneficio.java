
package capaDominio.promocionDetalle;

public enum TipoBeneficio {
    DESCUENTO_PORCENTAJE (1),
    DESCUENTO_FIJO (2),
    ENVIO_GRATIS (3);
    
    private final Integer id;

    TipoBeneficio(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static TipoBeneficio fromId(Integer id) {
        for (TipoBeneficio tp_benef : values()) {
            if (tp_benef.id == id) {
                return tp_benef;
            }
        }
        return null;
    }
}
