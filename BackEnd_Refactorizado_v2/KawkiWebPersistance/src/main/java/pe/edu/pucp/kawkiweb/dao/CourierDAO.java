package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilEnvio.CourierDTO;

public interface CourierDAO {

    CourierDTO obtenerPorId(Integer courierId);

    CourierDTO obtenerPorNombre(String nombre);

    ArrayList<CourierDTO> listarTodos();

}
