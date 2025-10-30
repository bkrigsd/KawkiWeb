package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallaDTO;

public interface TallaDAO {

    public TallaDTO obtenerPorId(Integer tallaId);

    public ArrayList<TallaDTO> listarTodos();

}
