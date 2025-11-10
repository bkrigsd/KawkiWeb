package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicionDTO;

public interface TipoCondicionDAO {

    public TipoCondicionDTO obtenerPorId(Integer tipoCondicionId);

    public ArrayList<TipoCondicionDTO> listarTodos();

}
