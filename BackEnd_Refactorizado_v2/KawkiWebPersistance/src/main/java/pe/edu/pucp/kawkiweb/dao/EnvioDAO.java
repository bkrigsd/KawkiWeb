package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.EnvioDTO;

public interface EnvioDAO {
    
    public Integer insertar(EnvioDTO envio);

    public EnvioDTO obtenerPorId(Integer envio_id);
    
    public ArrayList<EnvioDTO> listarTodos();

    public Integer modificar(EnvioDTO envio);

     public Integer eliminar(EnvioDTO envio); /* No se necesita eliminar, basta con cambiar el estado del envio */
    
}
