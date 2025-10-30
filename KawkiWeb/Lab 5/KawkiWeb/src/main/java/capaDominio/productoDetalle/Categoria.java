
package capaDominio.productoDetalle;

public enum Categoria {
    DERBY(1, "DER"),
    OXFORD(2, "OXF");

    private final Integer id;
    private final String codigo;

    Categoria(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    // Para buscar por id si viene de la BD
    public static Categoria fromId(Integer id) {
        for (Categoria cat : values()) {
            if (cat.id == id) {
                return cat;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
