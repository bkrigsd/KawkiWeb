package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

public interface TallasDAO {

    public Integer insertar(TallasDTO talla);
    
    public TallasDTO obtenerPorId(Integer tallaId);

    public ArrayList<TallasDTO> listarTodos();

    public Integer modificar(TallasDTO talla);

    public Integer eliminar(TallasDTO talla);
    
}
