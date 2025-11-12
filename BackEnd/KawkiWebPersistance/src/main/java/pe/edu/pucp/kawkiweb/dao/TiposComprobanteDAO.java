package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

public interface TiposComprobanteDAO {

    public Integer insertar(TiposComprobanteDTO tipoComprobante);
    
    public TiposComprobanteDTO obtenerPorId(Integer tipoComprobanteId);

    public ArrayList<TiposComprobanteDTO> listarTodos();
    
    public Integer modificar(TiposComprobanteDTO tipoComprobante);

    public Integer eliminar(TiposComprobanteDTO tipoComprobante);
    
}
