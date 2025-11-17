package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;

public interface DescuentosDAO {
    
    public Integer insertar(DescuentosDTO descuento);

    public DescuentosDTO obtenerPorId(Integer descuentoId);

    public ArrayList<DescuentosDTO> listarTodos();

    public Integer modificar(DescuentosDTO descuento);

    public Integer eliminar(DescuentosDTO descuento);
    
    public ArrayList<DescuentosDTO> listarActivas();
    
    public ArrayList<DescuentosDTO> listarVigentes();
    
}
