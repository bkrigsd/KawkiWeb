package pe.edu.pucp.kawkiweb.model.utilUsuario;

//import jakarta.xml.bind.annotation.XmlRootElement;
//
//@XmlRootElement(name = "tipoUsuario")
//import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//
//@XmlRootElement(name = "TipoUsuario")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TiposUsuarioDTO {

    private Integer tipoUsuarioId;
    private String nombre;

    // Constantes para los tipos de usuario
    public static final int ID_VENDEDOR = 1;
    public static final int ID_ADMINISTRADOR = 2;

    public static final String NOMBRE_VENDEDOR = "Vendedor";
    public static final String NOMBRE_ADMINISTRADOR = "Administrador";

    public TiposUsuarioDTO() {
        this.tipoUsuarioId = null;
        this.nombre = null;
    }

    public TiposUsuarioDTO(Integer tipoUsuarioId, String nombre) {
        this.tipoUsuarioId = tipoUsuarioId;
        this.nombre = nombre;
    }

    public TiposUsuarioDTO(TiposUsuarioDTO tipoUsuario) {
        this.tipoUsuarioId = tipoUsuario.tipoUsuarioId;
        this.nombre = tipoUsuario.nombre;
    }

    // Getters y Setters
    public Integer getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(Integer tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esVendedor() {
        return this.tipoUsuarioId != null && this.tipoUsuarioId == ID_VENDEDOR;
    }

    public boolean esAdministrador() {
        return this.tipoUsuarioId != null && this.tipoUsuarioId == ID_ADMINISTRADOR;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
