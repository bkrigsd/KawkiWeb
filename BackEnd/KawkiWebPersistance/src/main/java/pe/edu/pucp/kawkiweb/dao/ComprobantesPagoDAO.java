package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;

public interface ComprobantesPagoDAO {

    public Integer insertar(ComprobantesPagoDTO comprobantePago);

    public ComprobantesPagoDTO obtenerPorId(Integer comprobantePagoId);

    public ComprobantesPagoDTO obtenerPorVentaId(Integer ventaId);

    public ArrayList<ComprobantesPagoDTO> listarTodos();

    public Integer modificar(ComprobantesPagoDTO comprobantePago);

    public Integer eliminar(ComprobantesPagoDTO comprobantePago);

}
