package capaDominio.pagoDetalle;

public enum MetodoPago {
    TARJETA_BANCARIA(1),
    YAPE(2),
    PLIN(3);

    private final Integer id;

    MetodoPago(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static MetodoPago fromId(Integer id) {
        for (MetodoPago metodo : values()) {
            if (metodo.id == id) {
                return metodo;
            }
        }
        return null;
    }
}
