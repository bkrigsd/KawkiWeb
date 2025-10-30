

package pe.edu.pucp.kawkiweb.model.utilUsuario;


public class TipoUsuarioDTO {
    private Integer tipoUsuarioId;
    private String nombre;
    
    // Constantes para los tipos de usuario
    public static final int ID_CLIENTE = 1;
    public static final int ID_VENDEDOR = 2;
    public static final int ID_ADMINISTRADOR = 3;
    
    public static final String NOMBRE_CLIENTE = "Cliente";
    public static final String NOMBRE_VENDEDOR = "Vendedor";
    public static final String NOMBRE_ADMINISTRADOR = "Administrador";
    
    public TipoUsuarioDTO() {
        this.tipoUsuarioId = null;
        this.nombre = null;
    }
    
    public TipoUsuarioDTO(Integer tipoUsuarioId, String nombre) {
        this.tipoUsuarioId = tipoUsuarioId;
        this.nombre = nombre;
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
    public boolean esCliente() {
        return this.tipoUsuarioId != null && this.tipoUsuarioId == ID_CLIENTE;
    }
    
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
