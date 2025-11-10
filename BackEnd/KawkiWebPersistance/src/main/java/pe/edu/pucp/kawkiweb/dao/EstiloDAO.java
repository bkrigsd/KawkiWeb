package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

public interface EstiloDAO {

    public EstilosDTO obtenerPorId(Integer estiloId);

    public ArrayList<EstilosDTO> listarTodos();

}
