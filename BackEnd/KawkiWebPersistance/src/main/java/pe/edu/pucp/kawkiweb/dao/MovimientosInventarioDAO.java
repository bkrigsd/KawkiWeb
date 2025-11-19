package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;

public interface MovimientosInventarioDAO {

    public Integer insertar(MovimientosInventarioDTO movInventario);

    public MovimientosInventarioDTO obtenerPorId(Integer movInventarioId);

    public ArrayList<MovimientosInventarioDTO> listarTodos();

    public Integer modificar(MovimientosInventarioDTO movInventario);

    public Integer eliminar(MovimientosInventarioDTO movInventario);

    public ArrayList<MovimientosInventarioDTO> listarPorProductoVariante(Integer prodVarianteId);

    public ArrayList<MovimientosInventarioDTO> listarPorTipoMovimiento(Integer tipoMovimientoId);

    public ArrayList<MovimientosInventarioDTO> listarPorUsuario(Integer usuarioId);

    public ArrayList<MovimientosInventarioDTO> listarPorRangoFechas(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    public ArrayList<MovimientosInventarioDTO> listarMovimientosRecientes(Integer limite);

}
