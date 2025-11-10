package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.MovimientoInventarioDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;
import pe.edu.pucp.kawkiweb.dao.TipoMovimientoDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

public class MovimientoInventarioDAOImpl extends BaseDAOImpl implements MovimientoInventarioDAO {

    private MovimientosInventarioDTO movInventario;
    private TipoMovimientoDAO tipoMovimientoDAO;
    private ProductoVarianteDAO productoVarianteDAO;

    public MovimientoInventarioDAOImpl() {
        super("MOVIMIENTOS_INVENTARIO");
        this.movInventario = null;
        this.retornarLlavePrimaria = true;
        this.tipoMovimientoDAO = new TipoMovimientoDAOImpl();
        this.productoVarianteDAO = new ProductoVarianteDAOImpl();
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
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.movInventario.getFecha_hora_mov()));
        this.statement.setString(3, this.movInventario.getObservacion());

        // Obtener ID del TiposMovimientoDTO
        Integer tipoMovimientoId = (this.movInventario.getTipo_movimiento() != null)
                ? this.movInventario.getTipo_movimiento().getTipoMovimientoId()
                : null;

        if (tipoMovimientoId != null) {
            this.statement.setInt(4, tipoMovimientoId);
        } else {
            this.statement.setNull(4, java.sql.Types.INTEGER);
        }

        ProductosVariantesDTO prodVarianteDTO = this.movInventario.getProd_variante();
        if (prodVarianteDTO != null) {
            this.statement.setInt(5, prodVarianteDTO.getProd_variante_id());
        } else {
            this.statement.setNull(5, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.movInventario.getCantidad());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.movInventario.getFecha_hora_mov()));
        this.statement.setString(3, this.movInventario.getObservacion());

        Integer tipoMovimientoId = (this.movInventario.getTipo_movimiento() != null)
                ? this.movInventario.getTipo_movimiento().getTipoMovimientoId()
                : null;

        if (tipoMovimientoId != null) {
            this.statement.setInt(4, tipoMovimientoId);
        } else {
            this.statement.setNull(4, java.sql.Types.INTEGER);
        }

        ProductosVariantesDTO prodVarianteDTO = this.movInventario.getProd_variante();
        if (prodVarianteDTO != null) {
            this.statement.setInt(5, prodVarianteDTO.getProd_variante_id());
        } else {
            this.statement.setNull(5, java.sql.Types.INTEGER);
        }

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
        this.movInventario.setFecha_hora_mov(this.resultSet.getTimestamp("FECHA_HORA_MOV").toLocalDateTime());
        this.movInventario.setObservacion(this.resultSet.getString("OBSERVACION"));

        // Usar TipoMovimientoDAO para obtener el objeto completo
        Integer tipoMovimientoId = this.resultSet.getInt("TIPO_MOVIMIENTO_ID");
        TiposMovimientoDTO tipoMovimiento = this.tipoMovimientoDAO.obtenerPorId(tipoMovimientoId);
        this.movInventario.setTipo_movimiento(tipoMovimiento);

        Integer prod_variante_id = this.resultSet.getInt("PROD_VARIANTE_ID");
        ProductosVariantesDTO prodVariante = this.productoVarianteDAO.obtenerPorId(prod_variante_id);
        this.movInventario.setProd_variante(prodVariante);
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
