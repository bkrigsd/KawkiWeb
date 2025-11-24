package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TallasDAO;
import pe.edu.pucp.kawkiweb.daoImp.TallasDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

public class TallasBO {

    private TallasDAO tallaDAO;

    public TallasBO() {
        this.tallaDAO = new TallasDAOImpl();
    }

    public Integer insertar(Integer numeroTalla) {
        TallasDTO tallasDTO = new TallasDTO();
        tallasDTO.setNumero(numeroTalla);
        return this.tallaDAO.insertar(tallasDTO);
    }

    public Integer modificar(Integer tallaId, Integer numeroTalla) {
        TallasDTO tallasDTO = new TallasDTO();
        tallasDTO.setTalla_id(tallaId);
        tallasDTO.setNumero(numeroTalla);
        return this.tallaDAO.modificar(tallasDTO);
    }

    public TallasDTO obtenerPorId(Integer tallaId) {
        return this.tallaDAO.obtenerPorId(tallaId);
    }

    public List<TallasDTO> listarTodos() {
        return this.tallaDAO.listarTodos();
    }
}
