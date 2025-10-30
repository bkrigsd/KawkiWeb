package capaDominio.comprobanteDetalle;

public enum TipoComprobante {
    BOLETA(1),
    FACTURA(2);
    
    private final Integer id;

    TipoComprobante(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static TipoComprobante fromId(Integer id) {
        for (TipoComprobante tp_comprob : values()) {
            if (tp_comprob.id == id) {
                return tp_comprob;
            }
        }
        return null;
    }
}
