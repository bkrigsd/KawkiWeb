package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

public interface TiposBeneficioDAO {

    public Integer insertar(TiposBeneficioDTO tipoBeneficio);
    
    public TiposBeneficioDTO obtenerPorId(Integer tipoBeneficioId);

    public ArrayList<TiposBeneficioDTO> listarTodos();

    public Integer modificar(TiposBeneficioDTO tipoBeneficio);

    public Integer eliminar(TiposBeneficioDTO tipoBeneficio);
    
}
