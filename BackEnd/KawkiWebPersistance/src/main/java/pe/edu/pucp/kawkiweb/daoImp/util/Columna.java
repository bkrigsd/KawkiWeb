package pe.edu.pucp.kawkiweb.daoImp.util;

public class Columna {

    private String nombre;
    private Boolean esLlavePrimaria;
    private Boolean esAutoGenerado;
    private Boolean esModificable; // Nueva propiedad

    public Columna(String nombre, Boolean esLlavePrimaria, Boolean esAutoGenerado) {
        this.nombre = nombre;
        this.esLlavePrimaria = esLlavePrimaria;
        this.esAutoGenerado = esAutoGenerado;
        this.esModificable = true; // Por defecto todas las columnas son modificables
    }

    // Constructor completo con el nuevo parámetro
    public Columna(String nombre, Boolean esLlavePrimaria, Boolean esAutoGenerado, 
            Boolean esModificable) {
        
        this.nombre = nombre;
        this.esLlavePrimaria = esLlavePrimaria;
        this.esAutoGenerado = esAutoGenerado;
        this.esModificable = esModificable;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEsLlavePrimaria() {
        return esLlavePrimaria;
    }

    public void setEsLlavePrimaria(Boolean esLlavePrimaria) {
        this.esLlavePrimaria = esLlavePrimaria;
    }

    public Boolean getEsAutoGenerado() {
        return esAutoGenerado;
    }

    public void setEsAutoGenerado(Boolean esAutoGenerado) {
        this.esAutoGenerado = esAutoGenerado;
    }

    public Boolean getEsModificable() {
        return esModificable;
    }

    public void setEsModificable(Boolean esModificable) {
        this.esModificable = esModificable;
    }
}
