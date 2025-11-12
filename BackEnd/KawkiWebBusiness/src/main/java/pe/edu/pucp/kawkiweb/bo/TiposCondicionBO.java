package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TiposCondicionDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposCondicionDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

public class TiposCondicionBO {

    private TiposCondicionDAO tipoCondicionDAO;

    public TiposCondicionBO() {
        this.tipoCondicionDAO = new TiposCondicionDAOImpl();
    }

    public Integer insertar(String nombreCondicion) {
        TiposCondicionDTO tiposCondicionDTO = new TiposCondicionDTO();
        tiposCondicionDTO.setNombre(nombreCondicion);
        return this.tipoCondicionDAO.insertar(tiposCondicionDTO);
    }

    public Integer modificar(Integer tipoCondicionId, String nombreCondicion) {
        TiposCondicionDTO tiposCondicionDTO = new TiposCondicionDTO();
        tiposCondicionDTO.setTipo_condicion_id(tipoCondicionId);
        tiposCondicionDTO.setNombre(nombreCondicion);
        return this.tipoCondicionDAO.modificar(tiposCondicionDTO);
    }

    public TiposCondicionDTO obtenerPorId(Integer tipoCondicionId) {
        return this.tipoCondicionDAO.obtenerPorId(tipoCondicionId);
    }

    public List<TiposCondicionDTO> listarTodos() {
        return this.tipoCondicionDAO.listarTodos();
    }
}
