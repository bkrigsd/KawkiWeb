

package capaPersistencia;


import capaDominio.ComprobantePagoDTO;
import java.util.ArrayList;


public interface ComprobantePagoDAO {
    
    public Integer insertar(ComprobantePagoDTO comprobante_pago);
    
    public ComprobantePagoDTO obtenerPorId(Integer comprobante_pago_id);

    public ArrayList<ComprobantePagoDTO> listarTodos();

    public Integer modificar(ComprobantePagoDTO comprobante_pago);

    public Integer eliminar(ComprobantePagoDTO comprobante_pago);
    
}
