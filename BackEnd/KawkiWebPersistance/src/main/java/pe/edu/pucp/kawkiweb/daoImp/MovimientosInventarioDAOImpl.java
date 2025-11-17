package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.edu.pucp.kawkiWeb.db.DBManager;
import pe.edu.pucp.kawkiWeb.db.DBManagerMySQL;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;
import pe.edu.pucp.kawkiweb.dao.MovimientosInventarioDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;
import pe.edu.pucp.kawkiweb.dao.TiposMovimientoDAO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public class MovimientosInventarioDAOImpl extends BaseDAOImpl implements MovimientosInventarioDAO {

    private MovimientosInventarioDTO movInventario;
    private TiposMovimientoDAO tipoMovimientoDAO;
    private ProductosVariantesDAO productoVarianteDAO;
    private UsuariosDAO usuarioDAO;

    public MovimientosInventarioDAOImpl() {
        super("MOVIMIENTOS_INVENTARIO");
        this.movInventario = null;
        this.retornarLlavePrimaria = true;
        this.tipoMovimientoDAO = new TiposMovimientoDAOImpl();
        this.productoVarianteDAO = new ProductosVariantesDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MOV_INVENTARIO_ID", true, true));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_MOV", false, false, false));
        this.listaColumnas.add(new Columna("OBSERVACION", false, false));
        this.listaColumnas.add(new Columna("TIPO_MOVIMIENTO_ID", false, false));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.movInventario.getCantidad());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.movInventario.getFecha_hora_mov()));

        if (this.movInventario.getObservacion() != null) {
            this.statement.setString(3, this.movInventario.getObservacion());
        } else {
            this.statement.setNull(3, java.sql.Types.VARCHAR);
        }

        this.statement.setInt(4, this.movInventario.getTipo_movimiento().getTipoMovimientoId());
        this.statement.setInt(5, this.movInventario.getProd_variante().getProd_variante_id());
        this.statement.setInt(6, this.movInventario.getUsuario().getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.movInventario.getCantidad());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.movInventario.getFecha_hora_mov()));

        if (this.movInventario.getObservacion() != null) {
            this.statement.setString(3, this.movInventario.getObservacion());
        } else {
            this.statement.setNull(3, java.sql.Types.VARCHAR);
        }

        this.statement.setInt(4, this.movInventario.getTipo_movimiento().getTipoMovimientoId());
        this.statement.setInt(5, this.movInventario.getProd_variante().getProd_variante_id());
        this.statement.setInt(6, this.movInventario.getUsuario().getUsuarioId());
        this.statement.setInt(7, this.movInventario.getMov_inventario_id());
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
        this.movInventario.setFecha_hora_mov(this.resultSet.getTimestamp("FECHA_HORA_MOV").toLocalDateTime());
        this.movInventario.setObservacion(this.resultSet.getString("OBSERVACION"));

        Integer tipoMovimientoId = this.resultSet.getInt("TIPO_MOVIMIENTO_ID");
        TiposMovimientoDTO tipoMovimiento = this.tipoMovimientoDAO.obtenerPorId(tipoMovimientoId);
        this.movInventario.setTipo_movimiento(tipoMovimiento);

        Integer prod_variante_id = this.resultSet.getInt("PROD_VARIANTE_ID");
        ProductosVariantesDTO prodVariante = this.productoVarianteDAO.obtenerPorId(prod_variante_id);
        this.movInventario.setProd_variante(prodVariante);

        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.movInventario.setUsuario(usuario);
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

    // =====================================================
    // BÚSQUEDAS AVANZADAS
    // =====================================================
    /**
     * Lista movimientos filtrados por producto variante usando SELECT directo
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorProductoVariante(Integer prodVarianteId) {
        String sql = "SELECT MOV_INVENTARIO_ID, CANTIDAD, FECHA_HORA_MOV, OBSERVACION, "
                + "TIPO_MOVIMIENTO_ID, PROD_VARIANTE_ID, USUARIO_ID "
                + "FROM MOVIMIENTOS_INVENTARIO WHERE PROD_VARIANTE_ID = ? "
                + "ORDER BY FECHA_HORA_MOV DESC";

        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException ex) {
                System.err.println("Error al establecer parámetro prodVarianteId: " + ex);
            }
        };

        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos(sql, incluirParametros, prodVarianteId);
    }

    /**
     * Lista movimientos filtrados por tipo de movimiento usando SELECT directo
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorTipoMovimiento(Integer tipoMovimientoId) {
        String sql = "SELECT MOV_INVENTARIO_ID, CANTIDAD, FECHA_HORA_MOV, OBSERVACION, "
                + "TIPO_MOVIMIENTO_ID, PROD_VARIANTE_ID, USUARIO_ID "
                + "FROM MOVIMIENTOS_INVENTARIO WHERE TIPO_MOVIMIENTO_ID = ? "
                + "ORDER BY FECHA_HORA_MOV DESC";

        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException ex) {
                System.err.println("Error al establecer parámetro tipoMovimientoId: " + ex);
            }
        };

        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos(sql, incluirParametros, tipoMovimientoId);
    }

    /**
     * Lista movimientos filtrados por usuario usando SELECT directo
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorUsuario(Integer usuarioId) {
        String sql = "SELECT MOV_INVENTARIO_ID, CANTIDAD, FECHA_HORA_MOV, OBSERVACION, "
                + "TIPO_MOVIMIENTO_ID, PROD_VARIANTE_ID, USUARIO_ID "
                + "FROM MOVIMIENTOS_INVENTARIO WHERE USUARIO_ID = ? "
                + "ORDER BY FECHA_HORA_MOV DESC";

        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException ex) {
                System.err.println("Error al establecer parámetro usuarioId: " + ex);
            }
        };

        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos(sql, incluirParametros, usuarioId);
    }

    /**
     * Lista movimientos en un rango de fechas usando SELECT directo
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorRangoFechas(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {

        String sql = "SELECT MOV_INVENTARIO_ID, CANTIDAD, FECHA_HORA_MOV, OBSERVACION, "
                + "TIPO_MOVIMIENTO_ID, PROD_VARIANTE_ID, USUARIO_ID "
                + "FROM MOVIMIENTOS_INVENTARIO "
                + "WHERE FECHA_HORA_MOV BETWEEN ? AND ? "
                + "ORDER BY FECHA_HORA_MOV DESC";

        // Clase auxiliar para pasar ambas fechas
        class FechasParametro {

            LocalDateTime inicio;
            LocalDateTime fin;

            FechasParametro(LocalDateTime inicio, LocalDateTime fin) {
                this.inicio = inicio;
                this.fin = fin;
            }
        }

        FechasParametro fechas = new FechasParametro(fechaInicio, fechaFin);

        Consumer<FechasParametro> incluirParametros = (f) -> {
            try {
                this.statement.setTimestamp(1, java.sql.Timestamp.valueOf(f.inicio));
                this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(f.fin));
            } catch (SQLException ex) {
                System.err.println("Error al establecer parámetros de fechas: " + ex);
            }
        };

        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos(sql, incluirParametros, fechas);
    }

    /**
     * Lista los últimos N movimientos usando SELECT directo con LIMIT/TOP
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarMovimientosRecientes(Integer limite) {
        String sql;

        // Usar el DBManager que ya sabe qué motor estás usando
        if (DBManager.getInstance() instanceof DBManagerMySQL) {
            // MySQL syntax
            sql = "SELECT MOV_INVENTARIO_ID, CANTIDAD, FECHA_HORA_MOV, OBSERVACION, "
                    + "TIPO_MOVIMIENTO_ID, PROD_VARIANTE_ID, USUARIO_ID "
                    + "FROM MOVIMIENTOS_INVENTARIO "
                    + "ORDER BY FECHA_HORA_MOV DESC "
                    + "LIMIT ?";
        } else {
            // MS SQL Server syntax
            sql = "SELECT TOP (?) MOV_INVENTARIO_ID, CANTIDAD, FECHA_HORA_MOV, OBSERVACION, "
                    + "TIPO_MOVIMIENTO_ID, PROD_VARIANTE_ID, USUARIO_ID "
                    + "FROM MOVIMIENTOS_INVENTARIO "
                    + "ORDER BY FECHA_HORA_MOV DESC";
        }

        Consumer<Integer> incluirParametros = (lim) -> {
            try {
                this.statement.setInt(1, lim);
            } catch (SQLException ex) {
                System.err.println("Error al establecer parámetro límite: " + ex);
            }
        };

        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos(sql, incluirParametros, limite);
    }
}
