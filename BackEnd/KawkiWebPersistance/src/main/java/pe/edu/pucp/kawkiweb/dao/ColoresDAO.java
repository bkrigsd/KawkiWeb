package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;

public interface ColoresDAO {

    public Integer insertar(ColoresDTO color);
    
    public ColoresDTO obtenerPorId(Integer colorId);

    public ArrayList<ColoresDTO> listarTodos();

    public Integer modificar(ColoresDTO color);

    public Integer eliminar(ColoresDTO color);
}
