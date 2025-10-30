package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstiloDTO;

public interface EstiloDAO {

    public EstiloDTO obtenerPorId(Integer estiloId);

    public ArrayList<EstiloDTO> listarTodos();

}
