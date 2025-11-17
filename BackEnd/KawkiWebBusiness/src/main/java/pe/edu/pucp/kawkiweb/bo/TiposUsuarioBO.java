package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TiposUsuarioDAO;
import pe.edu.pucp.kawkiweb.daoImp.TiposUsuarioDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public class TiposUsuarioBO {

    private TiposUsuarioDAO tipoUsuarioDAO;

    public TiposUsuarioBO() {
        this.tipoUsuarioDAO = new TiposUsuarioDAOImpl();
    }

    public TiposUsuarioDTO obtenerPorId(Integer tipoUsuarioId) {
        return this.tipoUsuarioDAO.obtenerPorId(tipoUsuarioId);
    }

    public List<TiposUsuarioDTO> listarTodos() {
        return this.tipoUsuarioDAO.listarTodos();
    }
}
