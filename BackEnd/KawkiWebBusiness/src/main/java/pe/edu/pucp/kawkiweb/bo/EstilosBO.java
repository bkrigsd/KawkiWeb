package pe.edu.pucp.kawkiweb.bo;

import java.util.List;
import pe.edu.pucp.kawkiweb.dao.EstilosDAO;
import pe.edu.pucp.kawkiweb.daoImp.EstilosDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

public class EstilosBO {

    private EstilosDAO estiloDAO;

    public EstilosBO() {
        this.estiloDAO = new EstilosDAOImpl();
    }

    public Integer insertar(String nombreEstilo) {
        EstilosDTO estilosDTO = new EstilosDTO();
        estilosDTO.setNombre(nombreEstilo);
        return this.estiloDAO.insertar(estilosDTO);
    }

    public Integer modificar(Integer estiloId, String nombreEstilo) {
        EstilosDTO estilosDTO = new EstilosDTO();
        estilosDTO.setEstilo_id(estiloId);
        estilosDTO.setNombre(nombreEstilo);
        return this.estiloDAO.modificar(estilosDTO);
    }

    public EstilosDTO obtenerPorId(Integer estiloId) {
        return this.estiloDAO.obtenerPorId(estiloId);
    }

    public List<EstilosDTO> listarTodos() {
        return this.estiloDAO.listarTodos();
    }
}
