package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

public interface TiposCondicionDAO {

    public Integer insertar(TiposCondicionDTO tipoCondicion);
    
    public TiposCondicionDTO obtenerPorId(Integer tipoCondicionId);

    public ArrayList<TiposCondicionDTO> listarTodos();

    public Integer modificar(TiposCondicionDTO tipoCondicion);

    public Integer eliminar(TiposCondicionDTO tipoCondicion);
    
}
