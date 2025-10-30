package capaDominio.envioDetalle;

public enum Courier {
    SHALOM (1),
    OLVA_COURIER (2),
    URBANO (3);
    
    private final Integer id;

    Courier(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static Courier fromId(Integer id) {
        for (Courier courier : values()) {
            if (courier.id == id) {
                return courier;
            }
        }
        throw new IllegalArgumentException("Id de courier no válido: " + id);
    }
}
