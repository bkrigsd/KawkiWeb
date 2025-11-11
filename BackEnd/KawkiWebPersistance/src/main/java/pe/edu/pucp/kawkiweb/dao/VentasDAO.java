package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.VentasDTO;

public interface VentasDAO {
    
    public Integer insertar(VentasDTO venta);
    
    public VentasDTO obtenerPorId(Integer ventaId);

    public ArrayList<VentasDTO> listarTodos();

    public Integer modificar(VentasDTO venta);

    public Integer eliminar(VentasDTO venta); 
    
}
