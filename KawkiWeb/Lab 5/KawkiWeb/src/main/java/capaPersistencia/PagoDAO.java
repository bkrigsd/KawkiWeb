package capaPersistencia;

import capaDominio.PagoDTO;
import java.util.ArrayList;


public interface PagoDAO {
    
    public Integer insertar(PagoDTO pago);
    
    public PagoDTO obtenerPorId(Integer pago_id);

    public ArrayList<PagoDTO> listarTodos();

    public Integer modificar(PagoDTO pago);

    public Integer eliminar(PagoDTO pago);
}
