
package capaDominio.productoDetalle;

public enum Estilo {
    CHAROL(1, "CHA"),
    CLASICOS(2, "CLA"),
    COMBINADOS(3, "COM"),
    METALIZADOS(4, "MET");

    private final int id;
    private final String codigo;

    Estilo(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Estilo fromId(int id) {
        for (Estilo e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
