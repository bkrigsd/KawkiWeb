
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPagoDTO;


public interface MetodoPagoDAO {
    public MetodoPagoDTO obtenerPorId(Integer metodoId);

    public ArrayList<MetodoPagoDTO> listarTodos();
}
