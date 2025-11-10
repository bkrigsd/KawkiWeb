package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

public interface UsuarioDAO {
    
    public Integer insertar(UsuarioDTO usuario);
    
    public UsuarioDTO obtenerPorId(Integer almacenId);
    
    public ArrayList<UsuarioDTO> listarTodos();
    
    public Integer modificar(UsuarioDTO usuario);
    
    public Integer eliminar(UsuarioDTO usuario);
}
