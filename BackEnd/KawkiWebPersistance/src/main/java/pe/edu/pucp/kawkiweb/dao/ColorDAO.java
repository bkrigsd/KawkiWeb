package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;

public interface ColorDAO {

    public ColoresDTO obtenerPorId(Integer colorId);

    public ArrayList<ColoresDTO> listarTodos();

}
