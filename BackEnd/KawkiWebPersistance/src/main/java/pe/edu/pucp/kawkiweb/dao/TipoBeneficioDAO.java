package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficioDTO;

public interface TipoBeneficioDAO {

    public TipoBeneficioDTO obtenerPorId(Integer tipoBeneficioId);

    public ArrayList<TipoBeneficioDTO> listarTodos();

}
