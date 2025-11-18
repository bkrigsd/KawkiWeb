package pe.edu.pucp.kawkiweb.daoImp.util;

public class Join {

    private String tipo;  // e.g., "INNER JOIN", "LEFT JOIN"
    private String tabla;  // e.g., "TIPOS_USUARIO t"
    private String condicion;  // e.g., "u.TIPO_USUARIO_ID = t.TIPO_USUARIO_ID"
    
    public Join(String tipo, String tabla, String condicion) {
        this.tipo = tipo;
        this.tabla = tabla;
        this.condicion = condicion;
    }
    public String toSQL() {
        return getTipo() + " " + getTabla() + " ON " + getCondicion();
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
