package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

public interface TipoCondicionDAO {

    public TiposCondicionDTO obtenerPorId(Integer tipoCondicionId);

    public ArrayList<TiposCondicionDTO> listarTodos();

}
