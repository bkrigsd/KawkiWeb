package capaDominio.promocionDetalle;

public enum TipoCondicion {
    CANT_MIN_PRODUCTOS(1),
    MONTO_MIN_COMPRA(2);

    private final Integer id;

    TipoCondicion(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static TipoCondicion fromId(Integer id) {
        for (TipoCondicion tp_cond : values()) {
            if (tp_cond.id == id) {
                return tp_cond;
            }
        }
        return null;
    }
}
