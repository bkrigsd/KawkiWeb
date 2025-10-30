package capaPersistencia.Implementar;

import capaDominio.CarritoComprasDTO;
import capaDominio.DetalleCarritoDTO;
import capaPersistencia.Detalle_CarritoDAO;
import capaPersistencia.Implementar.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author beker
 */

public class Detalle_CarritoDAOImpl extends DAOImplBase implements Detalle_CarritoDAO{

    private DetalleCarritoDTO detalle;

    public Detalle_CarritoDAOImpl() {
        super("DETALLE_CARRITO");  // nombre de la tabla en BD
        this.detalle = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_CARRITO_ID", true, true));  // PK
        this.listaColumnas.add(new Columna("CARRITO_ID", false, false));        // FK hacia CarritoCompras
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalle.getCarrito().getCarrito_id());
        this.statement.setInt(2, this.detalle.getCantidad());
        this.statement.setDouble(3, this.detalle.getPrecio_unitario());
        this.statement.setDouble(4, this.detalle.getSubtotal());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalle.getCarrito().getCarrito_id());
        this.statement.setInt(2, this.detalle.getCantidad());
        this.statement.setDouble(3, this.detalle.getPrecio_unitario());
        this.statement.setDouble(4, this.detalle.getSubtotal());
        this.statement.setInt(5, this.detalle.getDetalle_carrito_id()); // WHERE ID
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalle.getDetalle_carrito_id());
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException{
        this.statement.setInt(1, this.detalle.getDetalle_carrito_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalle = new DetalleCarritoDTO();
        CarritoComprasDTO carrito = new CarritoComprasDTO();
        carrito.setCarrito_id(this.resultSet.getInt("CARRITO_ID"));
        this.detalle.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalle.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detalle.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalle = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalle);
    }

    // ==== Métodos DAO públicos ====

    @Override
    public Integer insertar(DetalleCarritoDTO detalle) {
        this.detalle = detalle;
        return super.insertar();
    }

    @Override
    public DetalleCarritoDTO obtenerPorId(Integer detalleId) {
        this.detalle = new DetalleCarritoDTO();
        detalle.setDetalle_carrito_id(detalleId);
        super.obtenerPorId();
        return this.detalle;
    }

    @Override
    public ArrayList<DetalleCarritoDTO> listarTodos() {
        return (ArrayList<DetalleCarritoDTO>) super.listarTodos();
    }

    @Override
    public ArrayList<DetalleCarritoDTO> listarPorCarritoId(Integer carritoId) {
        // String condicion = "CARRITO_ID = " + carritoId;
        return (ArrayList<DetalleCarritoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleCarritoDTO detalle) {
        this.detalle = detalle;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleCarritoDTO detalle) {
        this.detalle = detalle;
        return super.eliminar();
    }
    
}
