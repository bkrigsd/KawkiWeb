package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public interface UsuariosDAO {
    
    public Integer insertar(UsuariosDTO usuario);
    
    public UsuariosDTO obtenerPorId(Integer almacenId);
    
    public ArrayList<UsuariosDTO> listarTodos();
    
    public Integer modificar(UsuariosDTO usuario);
    
    public Integer eliminar(UsuariosDTO usuario);
}
