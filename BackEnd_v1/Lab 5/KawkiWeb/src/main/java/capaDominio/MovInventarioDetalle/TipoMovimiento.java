package capaDominio.MovInventarioDetalle;

public enum TipoMovimiento {
    INGRESO(1),
    SALIDA(2),
    AJUSTE(3);

    private final Integer id;

    TipoMovimiento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static TipoMovimiento fromId(Integer id) {
        for (TipoMovimiento tipo_mov : values()) {
            if (tipo_mov.id == id) {
                return tipo_mov;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
