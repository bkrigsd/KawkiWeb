package capaDominio.envioDetalle;

public enum Estado_Envio {
    PENDIENTE (1),
    PROGRAMADO (2),
    ENCARGADO_AL_COURIER (3),
    DISPONIBLE_EN_ALMACEN (4),
    ENTREGADO (5),
    ANULADO (6);
    
    private final Integer id;

    Estado_Envio(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static Estado_Envio fromId(Integer id) {
        for (Estado_Envio estado_env : values()) {
            if (estado_env.id == id) {
                return estado_env;
            }
        }
        throw new IllegalArgumentException("Id de estado_envio no válido: " + id);
    }
}
