package pe.edu.pucp.kawkiweb.daoImp.util;

/**
 * Clase para encapsular resultado de stored procedures que retornan código +
 * mensaje. Patrón reutilizable para SPs con respuesta estándar.
 */
public class ResultadoSP {

    private Integer codigo;
    private String mensaje;

    public ResultadoSP(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean esExitoso() {
        return codigo != null && codigo == 0;
    }
}
