package pe.edu.pucp.kawkiweb.model.utilProducto;

public class CategoriaDTO {

    private Integer categoria_id;
    private String nombre;

    // Constantes para los IDs de categorías
    public static final int ID_DERBY = 1;
    public static final int ID_OXFORD = 2;

    public static final String NOMBRE_DERBY = "Derby";
    public static final String NOMBRE_OXFORD = "Oxford";

    public CategoriaDTO() {
        this.categoria_id = null;
        this.nombre = null;
    }

    public CategoriaDTO(Integer categoria_id, String nombre) {
        this.categoria_id = categoria_id;
        this.nombre = nombre;
    }

    public CategoriaDTO(CategoriaDTO categoria) {
        this.categoria_id = categoria.categoria_id;
        this.nombre = categoria.nombre;
    }

    public Integer getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Integer categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esDerby() {
        return this.categoria_id != null && this.categoria_id == ID_DERBY;
    }

    public boolean esOxford() {
        return this.categoria_id != null && this.categoria_id == ID_OXFORD;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
