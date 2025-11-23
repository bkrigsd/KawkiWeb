package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TiposBeneficioDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposBeneficioDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

public class TiposBeneficioBO {

    private TiposBeneficioDAO tipoBeneficioDAO;

    public TiposBeneficioBO() {
        this.tipoBeneficioDAO = new TiposBeneficioDAOImpl();
    }

    public TiposBeneficioDTO obtenerPorId(Integer tipoBeneficioId) {
        return this.tipoBeneficioDAO.obtenerPorId(tipoBeneficioId);
    }

    public List<TiposBeneficioDTO> listarTodos() {
        return this.tipoBeneficioDAO.listarTodos();
    }
}
