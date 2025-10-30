package capaPersistencia.Implementar;

import capaDominio.MovInventarioDetalle.TipoMovimiento;
import capaDominio.MovimientosInventarioDTO;
import capaPersistencia.Implementar.util.Columna;
import capaPersistencia.MovimientosInventarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimientosInventarioDAOImpl extends DAOImplBase implements MovimientosInventarioDAO {

    private MovimientosInventarioDTO movInventario;

    public MovimientosInventarioDAOImpl() {
        super("MOVIMIENTOS_INVENTARIO");
        this.movInventario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MOV_INVENTARIO_ID", true, true));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_MOV", false, false));
        this.listaColumnas.add(new Columna("OBSERVACION", false, false));
        this.listaColumnas.add(new Columna("TIPO_MOVIMIENTO_ID", false, false));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.movInventario.getCantidad());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(
                this.movInventario.getFecha_hora_movimiento()));

        String observacion = this.movInventario.getObservacion();
        if (observacion != null) {
            this.statement.setString(3, observacion);
        } else {
            this.statement.setNull(3, java.sql.Types.VARCHAR);
        }

        this.statement.setInt(4, this.movInventario.getTipo_movimiento().getId());
        this.statement.setInt(5, this.movInventario.getProd_variante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.movInventario.getCantidad());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(
                this.movInventario.getFecha_hora_movimiento()));

        String observacion = this.movInventario.getObservacion();
        if (observacion != null) {
            this.statement.setString(3, observacion);
        } else {
            this.statement.setNull(3, java.sql.Types.VARCHAR);
        }

        this.statement.setInt(4, this.movInventario.getTipo_movimiento().getId());
        this.statement.setInt(5, this.movInventario.getProd_variante_id());
        this.statement.setInt(6, this.movInventario.getMov_inventario_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.movInventario.getMov_inventario_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.movInventario.getMov_inventario_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.movInventario = new MovimientosInventarioDTO();
        this.movInventario.setMov_inventario_id(this.resultSet.getInt("MOV_INVENTARIO_ID"));
        this.movInventario.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.movInventario.setFecha_hora_movimiento(this.resultSet.getTimestamp("FECHA_HORA_MOV").toLocalDateTime());
        this.movInventario.setObservacion(this.resultSet.getString("OBSERVACION"));
        Integer tipo_movimiento_id = this.resultSet.getInt("TIPO_MOVIMIENTO_ID");
        this.movInventario.setTipo_movimiento(TipoMovimiento.fromId(tipo_movimiento_id));
        this.movInventario.setProd_variante_id(this.resultSet.getInt("PROD_VARIANTE_ID"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.movInventario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.movInventario);
    }

    @Override
    public Integer insertar(MovimientosInventarioDTO movInventario) {
        this.movInventario = movInventario;
        return super.insertar();
    }

    @Override
    public MovimientosInventarioDTO obtenerPorId(Integer movInventarioId) {
        this.movInventario = new MovimientosInventarioDTO();
        this.movInventario.setMov_inventario_id(movInventarioId);
        super.obtenerPorId();
        return this.movInventario;
    }

    @Override
    public ArrayList<MovimientosInventarioDTO> listarTodos() {
        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MovimientosInventarioDTO movInventario) {
        this.movInventario = movInventario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MovimientosInventarioDTO movInventario) {
        this.movInventario = movInventario;
        return super.eliminar();
    }
}
