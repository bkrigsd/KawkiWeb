package capaPersistencia;

import capaDominio.EnviosDTO;
import java.util.ArrayList;

public interface EnvioDAO {
    
    public Integer insertar(EnviosDTO envio);

    public EnviosDTO obtenerPorId(Integer envio_id);
    
    public ArrayList<EnviosDTO> listarTodos();

    public Integer modificar(EnviosDTO envio);

    public Integer eliminar(EnviosDTO envio);
    
}
