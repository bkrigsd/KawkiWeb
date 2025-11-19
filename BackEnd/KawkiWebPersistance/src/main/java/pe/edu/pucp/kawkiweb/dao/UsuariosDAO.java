package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public interface UsuariosDAO {
    
    public Integer insertar(UsuariosDTO usuario);
    
    public UsuariosDTO obtenerPorId(Integer almacenId);
    
    public ArrayList<UsuariosDTO> listarTodos();
    
    public Integer modificar(UsuariosDTO usuario);
    
    public Integer eliminar(UsuariosDTO usuario);
    
    
    public boolean[] verificarUnicidad(String correo, String nombreUsuario, String dni, Integer usuarioIdExcluir);
    
    public ArrayList<UsuariosDTO> listarPorTipo(Integer tipoUsuarioId);
    
    public ResultadoCambioContrasenha cambiarContrasenha(Integer usuarioId, String contrasenhaActual, String contrasenhaNueva);
    
    public UsuariosDTO autenticar(String nombreUsuarioOCorreo, String contrasenha);
    
    /**
     * Clase interna para retornar resultado de cambio de contraseña
     */
    public static class ResultadoCambioContrasenha {
        private int codigo;
        private String mensaje;
        
        public ResultadoCambioContrasenha(int codigo, String mensaje) {
            this.codigo = codigo;
            this.mensaje = mensaje;
        }
        
        public int getCodigo() {
            return codigo;
        }
        
        public String getMensaje() {
            return mensaje;
        }
        
        public boolean esExitoso() {
            return codigo == 0;
        }
    }
}
