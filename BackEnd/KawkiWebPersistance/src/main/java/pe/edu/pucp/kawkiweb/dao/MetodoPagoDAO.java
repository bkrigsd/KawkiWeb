
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;


public interface MetodoPagoDAO {
    public MetodosPagoDTO obtenerPorId(Integer metodoId);

    public ArrayList<MetodosPagoDTO> listarTodos();
}
