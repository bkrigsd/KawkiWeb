package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

public interface TallaDAO {

    public TallasDTO obtenerPorId(Integer tallaId);

    public ArrayList<TallasDTO> listarTodos();

}
