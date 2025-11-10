package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

public interface TipoBeneficioDAO {

    public TiposBeneficioDTO obtenerPorId(Integer tipoBeneficioId);

    public ArrayList<TiposBeneficioDTO> listarTodos();

}
