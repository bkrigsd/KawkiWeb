package capaPersistencia;
import capaDominio.UsuarioDTO;
import java.util.ArrayList;

public interface UsuarioDAO {
    
    public Integer insertar(UsuarioDTO usuario);

    public UsuarioDTO obtenerPorId(Integer usuarioId);

    public ArrayList<UsuarioDTO> listarTodos();

    public Integer modificar(UsuarioDTO usuario);

    public Integer eliminar(UsuarioDTO usuario);

}
