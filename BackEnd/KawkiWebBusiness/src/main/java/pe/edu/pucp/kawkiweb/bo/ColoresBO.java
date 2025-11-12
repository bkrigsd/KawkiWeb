package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.ColoresDAO;
import pe.edu.pucp.kawkiweb.daoImp.ColoresDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;

public class ColoresBO {

    private ColoresDAO colorDAO;

    public ColoresBO() {
        this.colorDAO = new ColoresDAOImpl();
    }

    public Integer insertar(String nombreColor) {
        ColoresDTO coloresDTO = new ColoresDTO();
        coloresDTO.setNombre(nombreColor);
        return this.colorDAO.insertar(coloresDTO);
    }

    public Integer modificar(Integer colorId, String nombreColor) {
        ColoresDTO coloresDTO = new ColoresDTO();
        coloresDTO.setColor_id(colorId);
        coloresDTO.setNombre(nombreColor);
        return this.colorDAO.modificar(coloresDTO);
    }

    public ColoresDTO obtenerPorId(Integer colorId) {
        return this.colorDAO.obtenerPorId(colorId);
    }

    public List<ColoresDTO> listarTodos() {
        return this.colorDAO.listarTodos();
    }
}
