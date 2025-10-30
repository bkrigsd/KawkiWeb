package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilEnvio.EstadoEnvioDTO;

public interface EstadoEnvioDAO {

    EstadoEnvioDTO obtenerPorId(Integer estadoEnvioId);

    EstadoEnvioDTO obtenerPorNombre(String nombre);

    ArrayList<EstadoEnvioDTO> listarTodos();

}
