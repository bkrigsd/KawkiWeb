
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;


public interface MetodosPagoDAO {
    public Integer insertar(MetodosPagoDTO metodoPago);
    
    public MetodosPagoDTO obtenerPorId(Integer metodoPagoId);

    public ArrayList<MetodosPagoDTO> listarTodos();
    
    public Integer modificar(MetodosPagoDTO metodoPago);

    public Integer eliminar(MetodosPagoDTO metodoPago);
}
