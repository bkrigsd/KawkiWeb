package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

public interface TipoComprobanteDAO {

    public TiposComprobanteDTO obtenerPorId(Integer tipoComprobanteId);

    public ArrayList<TiposComprobanteDTO> listarTodos();
}
