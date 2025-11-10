package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

public interface EstilosDAO {

    public Integer insertar(EstilosDTO estilo);
    
    public EstilosDTO obtenerPorId(Integer estiloId);

    public ArrayList<EstilosDTO> listarTodos();

    public Integer modificar(EstilosDTO estilo);

    public Integer eliminar(EstilosDTO estilo);
}
