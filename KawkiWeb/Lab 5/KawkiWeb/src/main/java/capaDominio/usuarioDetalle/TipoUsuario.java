package capaDominio.usuarioDetalle;

public enum TipoUsuario {
    ADMINISTRADOR(1),
    CLIENTE(2),
    VENDEDOR(3);

    private final int id;

    TipoUsuario(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TipoUsuario fromId(int id) {
        for (TipoUsuario t : values()) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
