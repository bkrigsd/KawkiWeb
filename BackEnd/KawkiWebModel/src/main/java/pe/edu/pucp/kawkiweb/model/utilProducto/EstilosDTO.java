package pe.edu.pucp.kawkiweb.model.utilProducto;

public class EstilosDTO {

    private Integer estilo_id;
    private String nombre;

    // Constantes para los IDs de estilos
    public static final int ID_CHAROL = 1;
    public static final int ID_CLASICOS = 2;
    public static final int ID_COMBINADOS = 3;
    public static final int ID_METALIZADOS = 4;

    public static final String NOMBRE_CHAROL = "Charol";
    public static final String NOMBRE_CLASICOS = "Clásicos";
    public static final String NOMBRE_COMBINADOS = "Combinados";
    public static final String NOMBRE_METALIZADOS = "Metalizados";

    public EstilosDTO() {
        this.estilo_id = null;
        this.nombre = null;
    }

    public EstilosDTO(Integer estilo_id, String nombre) {
        this.estilo_id = estilo_id;
        this.nombre = nombre;
    }

    public EstilosDTO(EstilosDTO estilo) {
        this.estilo_id = estilo.estilo_id;
        this.nombre = estilo.nombre;
    }

    public Integer getEstilo_id() {
        return estilo_id;
    }

    public void setEstilo_id(Integer estilo_id) {
        this.estilo_id = estilo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esCharol() {
        return this.estilo_id != null && this.estilo_id == ID_CHAROL;
    }

    public boolean esClasicos() {
        return this.estilo_id != null && this.estilo_id == ID_CLASICOS;
    }

    public boolean esCombinados() {
        return this.estilo_id != null && this.estilo_id == ID_COMBINADOS;
    }

    public boolean esMetalizados() {
        return this.estilo_id != null && this.estilo_id == ID_METALIZADOS;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
