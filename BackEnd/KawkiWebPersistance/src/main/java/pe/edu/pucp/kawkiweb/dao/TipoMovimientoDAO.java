package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

public interface TipoMovimientoDAO {

    Integer insertar(TiposMovimientoDTO tipoMovimiento);

    TiposMovimientoDTO obtenerPorId(Integer tipoMovimientoId);

    TiposMovimientoDTO obtenerPorNombre(String nombre);

    ArrayList<TiposMovimientoDTO> listarTodos();

    Integer modificar(TiposMovimientoDTO tipoMovimiento);

    Integer eliminar(TiposMovimientoDTO tipoMovimiento);
}
