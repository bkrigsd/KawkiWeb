package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuarioDTO;

public interface TipoUsuarioDAO {

    TipoUsuarioDTO obtenerPorId(Integer tipoUsuarioId);

    TipoUsuarioDTO obtenerPorNombre(String nombre);

    ArrayList<TipoUsuarioDTO> listarTodos();

}
