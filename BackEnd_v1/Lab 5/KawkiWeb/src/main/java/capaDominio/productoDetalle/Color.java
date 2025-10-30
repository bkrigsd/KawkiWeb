
package capaDominio.productoDetalle;

public enum Color {
    BLANCO(1, "BLA"),
    CAMEL(2, "CAM"),
    MARRON(3, "MAR"),
    PIEL(4, "PIE"),
    CELESTE(5, "CEL"),
    CREMA(6, "CRE"),
    BEIGE(7, "BEI"),
    NEGRO(8, "NEG"),
    AMARILLO(9, "AMA"),
    PLATA(10, "PLA"),
    AZUL(11, "AZU"),
    ROSADO(12, "ROS"),
    GRIS(13, "GRI"),
    ROJO(14, "ROJ"),
    TURQUESA(15, "TUR"),
    ACERO(16, "ACE"),
    VERDE(17, "VER");

    private final int id;
    private final String codigo;

    Color(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Color fromId(int id) {
        for (Color c : values()) {
            if (c.id == id) {
                return c;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
