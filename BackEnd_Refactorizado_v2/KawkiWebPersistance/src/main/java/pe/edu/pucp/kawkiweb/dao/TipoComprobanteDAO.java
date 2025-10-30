package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPago.TipoComprobanteDTO;

public interface TipoComprobanteDAO {

    public TipoComprobanteDTO obtenerPorId(Integer tipoComprobanteId);

    public ArrayList<TipoComprobanteDTO> listarTodos();
}
