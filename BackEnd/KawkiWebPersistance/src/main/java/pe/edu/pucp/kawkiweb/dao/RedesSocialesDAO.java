package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

public interface RedesSocialesDAO {

    public Integer insertar(RedesSocialesDTO redSocial);

    public RedesSocialesDTO obtenerPorId(Integer redSocialId);

    public ArrayList<RedesSocialesDTO> listarTodos();

    public Integer modificar(RedesSocialesDTO redSocial);

    public Integer eliminar(RedesSocialesDTO redSocial);
}
