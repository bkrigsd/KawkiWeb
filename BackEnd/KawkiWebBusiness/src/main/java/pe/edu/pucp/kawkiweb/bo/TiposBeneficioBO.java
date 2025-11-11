package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.TiposBeneficioDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposBeneficioDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

public class TiposBeneficioBO {

    private TiposBeneficioDAO tipoBeneficioDAO;

    public TiposBeneficioBO() {
        this.tipoBeneficioDAO = new TiposBeneficioDAOImpl();
    }

    public Integer insertar(String nombreBeneficio) {
        TiposBeneficioDTO tiposBeneficioDTO = new TiposBeneficioDTO();
        tiposBeneficioDTO.setNombre(nombreBeneficio);
        return this.tipoBeneficioDAO.insertar(tiposBeneficioDTO);
    }

    public Integer modificar(Integer tipoBeneficioId, String nombreBeneficio) {
        TiposBeneficioDTO tiposBeneficioDTO = new TiposBeneficioDTO();
        tiposBeneficioDTO.setTipo_beneficio_id(tipoBeneficioId);
        tiposBeneficioDTO.setNombre(nombreBeneficio);
        return this.tipoBeneficioDAO.modificar(tiposBeneficioDTO);
    }

    public TiposBeneficioDTO obtenerPorId(Integer tipoBeneficioId) {
        return this.tipoBeneficioDAO.obtenerPorId(tipoBeneficioId);
    }

    public ArrayList<TiposBeneficioDTO> listarTodos() {
        return this.tipoBeneficioDAO.listarTodos();
    }
}
