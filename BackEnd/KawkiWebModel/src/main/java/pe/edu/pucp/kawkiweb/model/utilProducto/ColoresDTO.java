package pe.edu.pucp.kawkiweb.model.utilProducto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Colore")
public class ColoresDTO {

    private Integer color_id;
    private String nombre;

    // Constantes para los IDs de colores
    public static final int ID_BLANCO = 1;
    public static final int ID_CAMEL = 2;
    public static final int ID_MARRON = 3;
    public static final int ID_PIEL = 4;
    public static final int ID_CELESTE = 5;
    public static final int ID_CREMA = 6;
    public static final int ID_BEIGE = 7;
    public static final int ID_NEGRO = 8;
    public static final int ID_AMARILLO = 9;
    public static final int ID_PLATA = 10;
    public static final int ID_AZUL = 11;
    public static final int ID_ROSADO = 12;
    public static final int ID_GRIS = 13;
    public static final int ID_ROJO = 14;
    public static final int ID_TURQUESA = 15;
    public static final int ID_ACERO = 16;
    public static final int ID_VERDE = 17;

    public static final String NOMBRE_BLANCO = "Blanco";
    public static final String NOMBRE_CAMEL = "Camel";
    public static final String NOMBRE_MARRON = "Marrón";
    public static final String NOMBRE_PIEL = "Piel";
    public static final String NOMBRE_CELESTE = "Celeste";
    public static final String NOMBRE_CREMA = "Crema";
    public static final String NOMBRE_BEIGE = "Beige";
    public static final String NOMBRE_NEGRO = "Negro";
    public static final String NOMBRE_AMARILLO = "Amarillo";
    public static final String NOMBRE_PLATA = "Plata";
    public static final String NOMBRE_AZUL = "Azul";
    public static final String NOMBRE_ROSADO = "Rosado";
    public static final String NOMBRE_GRIS = "Gris";
    public static final String NOMBRE_ROJO = "Rojo";
    public static final String NOMBRE_TURQUESA = "Turquesa";
    public static final String NOMBRE_ACERO = "Acero";
    public static final String NOMBRE_VERDE = "Verde";

    public ColoresDTO() {
        this.color_id = null;
        this.nombre = null;
    }

    public ColoresDTO(Integer color_id, String nombre) {
        this.color_id = color_id;
        this.nombre = nombre;
    }

    public ColoresDTO(ColoresDTO color) {
        this.color_id = color.color_id;
        this.nombre = color.nombre;
    }

    public Integer getColor_id() {
        return color_id;
    }

    public void setColor_id(Integer color_id) {
        this.color_id = color_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esBlanco() {
        return this.color_id != null && this.color_id == ID_BLANCO;
    }

    public boolean esCamel() {
        return this.color_id != null && this.color_id == ID_CAMEL;
    }

    public boolean esMarron() {
        return this.color_id != null && this.color_id == ID_MARRON;
    }

    public boolean esPiel() {
        return this.color_id != null && this.color_id == ID_PIEL;
    }

    public boolean esCeleste() {
        return this.color_id != null && this.color_id == ID_CELESTE;
    }

    public boolean esCrema() {
        return this.color_id != null && this.color_id == ID_CREMA;
    }

    public boolean esBeige() {
        return this.color_id != null && this.color_id == ID_BEIGE;
    }

    public boolean esNegro() {
        return this.color_id != null && this.color_id == ID_NEGRO;
    }

    public boolean esAmarillo() {
        return this.color_id != null && this.color_id == ID_AMARILLO;
    }

    public boolean esPlata() {
        return this.color_id != null && this.color_id == ID_PLATA;
    }

    public boolean esAzul() {
        return this.color_id != null && this.color_id == ID_AZUL;
    }

    public boolean esRosado() {
        return this.color_id != null && this.color_id == ID_ROSADO;
    }

    public boolean esGris() {
        return this.color_id != null && this.color_id == ID_GRIS;
    }

    public boolean esRojo() {
        return this.color_id != null && this.color_id == ID_ROJO;
    }

    public boolean esTurquesa() {
        return this.color_id != null && this.color_id == ID_TURQUESA;
    }

    public boolean esAcero() {
        return this.color_id != null && this.color_id == ID_ACERO;
    }

    public boolean esVerde() {
        return this.color_id != null && this.color_id == ID_VERDE;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
