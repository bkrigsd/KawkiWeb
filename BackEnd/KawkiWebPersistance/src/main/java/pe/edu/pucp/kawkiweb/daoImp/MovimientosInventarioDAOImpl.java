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

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UN
     * SOLO movimiento de inventario.
     */
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

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHOS movimientos de inventario.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.movInventario = new MovimientosInventarioDTO();

        // Datos básicos del movimiento de inventario
        this.movInventario.setMov_inventario_id(this.resultSet.getInt("MOV_INVENTARIO_ID"));
        this.movInventario.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.movInventario.setFecha_hora_mov(this.resultSet.getTimestamp("FECHA_HORA_MOV").toLocalDateTime());
        this.movInventario.setObservacion(this.resultSet.getString("OBSERVACION"));

        // Tipo de movimiento (YA VIENE COMPLETO del JOIN - SIN query adicional)
        TiposMovimientoDTO tipoMovimiento = new TiposMovimientoDTO();
        tipoMovimiento.setTipoMovimientoId(this.resultSet.getInt("TIPO_MOVIMIENTO_ID"));
        tipoMovimiento.setNombre(this.resultSet.getString("TIPO_MOVIMIENTO_NOMBRE"));
        this.movInventario.setTipo_movimiento(tipoMovimiento);

        // Producto Variante (YA VIENE del JOIN - SOLO ID, SKU Y STOCK)
        ProductosVariantesDTO prodVariante = new ProductosVariantesDTO();
        prodVariante.setProd_variante_id(this.resultSet.getInt("PROD_VARIANTE_ID"));
        prodVariante.setSKU(this.resultSet.getString("SKU"));
        prodVariante.setStock(this.resultSet.getInt("STOCK"));
        this.movInventario.setProd_variante(prodVariante);

        // Usuario (YA VIENE del JOIN - ID, NOMBRE Y APE_PATERNO)
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombre(this.resultSet.getString("USUARIO_NOMBRE"));
        usuario.setApePaterno(this.resultSet.getString("USUARIO_APE_PATERNO"));
        this.movInventario.setUsuario(usuario);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.movInventario = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.movInventario);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
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

    /**
     * Lista todos los movimientos de inventario usando el Stored Procedure
     * optimizado. Retorna movimientos con tipo_movimiento completo,
     * prod_variante (id, sku, stock) y usuario (id, nombre, ape_paterno).
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarTodos() {
        return (ArrayList<MovimientosInventarioDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_MOVIMIENTOS_INVENTARIO_COMPLETO"
        );
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
     * Lista movimientos filtrados por producto variante usando SP optimizado.
     * Retorna movimientos con todos los datos completos mediante JOINs.
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorProductoVariante(Integer prodVarianteId) {
        return (ArrayList<MovimientosInventarioDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_MOVIMIENTOS_POR_PRODUCTO_VARIANTE",
                1,
                (params) -> {
                    try {
                        this.statement.setInt(1, (Integer) params);
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetro prodVarianteId: " + ex);
                    }
                },
                prodVarianteId
        );
    }

    /**
     * Lista movimientos filtrados por tipo de movimiento usando SP optimizado.
     * Retorna movimientos con todos los datos completos mediante JOINs.
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorTipoMovimiento(Integer tipoMovimientoId) {
        return (ArrayList<MovimientosInventarioDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_MOVIMIENTOS_POR_TIPO_MOVIMIENTO",
                1,
                (params) -> {
                    try {
                        this.statement.setInt(1, (Integer) params);
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetro tipoMovimientoId: " + ex);
                    }
                },
                tipoMovimientoId
        );
    }

    /**
     * Lista movimientos filtrados por usuario usando SP optimizado. Retorna
     * movimientos con todos los datos completos mediante JOINs.
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorUsuario(Integer usuarioId) {
        return (ArrayList<MovimientosInventarioDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_MOVIMIENTOS_POR_USUARIO",
                1,
                (params) -> {
                    try {
                        this.statement.setInt(1, (Integer) params);
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetro usuarioId: " + ex);
                    }
                },
                usuarioId
        );
    }

    /**
     * Lista movimientos en un rango de fechas usando SP optimizado. Retorna
     * movimientos con todos los datos completos mediante JOINs.
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarPorRangoFechas(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {

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

        return (ArrayList<MovimientosInventarioDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_MOVIMIENTOS_POR_RANGO_FECHAS",
                2,
                (params) -> {
                    try {
                        FechasParametro f = (FechasParametro) params;
                        this.statement.setTimestamp(1, java.sql.Timestamp.valueOf(f.inicio));
                        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(f.fin));
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetros de fechas: " + ex);
                    }
                },
                fechas
        );
    }

    /**
     * Lista los últimos N movimientos usando SP optimizado. Retorna movimientos
     * con todos los datos completos mediante JOINs.
     */
    @Override
    public ArrayList<MovimientosInventarioDTO> listarMovimientosRecientes(Integer limite) {
        return (ArrayList<MovimientosInventarioDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_MOVIMIENTOS_RECIENTES",
                1,
                (params) -> {
                    try {
                        this.statement.setInt(1, (Integer) params);
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetro límite: " + ex);
                    }
                },
                limite
        );
    }
}
