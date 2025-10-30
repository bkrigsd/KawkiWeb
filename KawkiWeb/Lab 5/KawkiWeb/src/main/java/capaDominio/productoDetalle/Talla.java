
package capaDominio.productoDetalle;

public enum Talla {
    TREINTA_CINCO(1, "35"),
    TREINTA_SEIS(2, "36"),
    TREINTA_SIETE(3, "37"),
    TREINTA_OCHO(4, "38"),
    TREINTA_NUEVE(5, "39");

    private final int id;
    private final String codigo;

    Talla(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Talla fromId(int id) {
        for (Talla t : values()) {
            if (t.id == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
