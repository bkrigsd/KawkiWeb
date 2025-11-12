package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        this.listaColumnas.add(new Columna("FECHA_HORA_MOV", false, false));
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
    
}
