package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColorDTO;

public interface ColorDAO {

    public ColorDTO obtenerPorId(Integer colorId);

    public ArrayList<ColorDTO> listarTodos();

}
