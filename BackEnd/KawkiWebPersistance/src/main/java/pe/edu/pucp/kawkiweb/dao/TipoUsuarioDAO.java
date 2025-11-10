package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public interface TipoUsuarioDAO {

    TiposUsuarioDTO obtenerPorId(Integer tipoUsuarioId);

    TiposUsuarioDTO obtenerPorNombre(String nombre);

    ArrayList<TiposUsuarioDTO> listarTodos();

}
