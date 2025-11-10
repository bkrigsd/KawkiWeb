package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TipoMovimientoDTO;

public interface TipoMovimientoDAO {

    Integer insertar(TipoMovimientoDTO tipoMovimiento);

    TipoMovimientoDTO obtenerPorId(Integer tipoMovimientoId);

    TipoMovimientoDTO obtenerPorNombre(String nombre);

    ArrayList<TipoMovimientoDTO> listarTodos();

    Integer modificar(TipoMovimientoDTO tipoMovimiento);

    Integer eliminar(TipoMovimientoDTO tipoMovimiento);
}
