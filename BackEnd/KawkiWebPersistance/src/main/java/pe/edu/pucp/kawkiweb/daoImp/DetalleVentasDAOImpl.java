package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.dao.DetalleVentasDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;

public class DetalleVentasDAOImpl extends BaseDAOImpl implements DetalleVentasDAO {

    private DetalleVentasDTO detalleVenta;
    private ProductosVariantesDAO productoVarianteDAO;

    public DetalleVentasDAOImpl() {
        super("DETALLE_VENTAS");
        this.detalleVenta = null;
        this.retornarLlavePrimaria = true;
        this.productoVarianteDAO = new ProductosVariantesDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_VENTA_ID", true, true));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("VENTA_ID", false, false));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getCantidad());
        this.statement.setDouble(2, this.detalleVenta.getPrecio_unitario());
        this.statement.setDouble(3, this.detalleVenta.getSubtotal());
        this.statement.setInt(4, this.detalleVenta.getVenta_id());
        this.statement.setInt(5, this.detalleVenta.getProdVariante().getProd_variante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getCantidad());
        this.statement.setDouble(2, this.detalleVenta.getPrecio_unitario());
        this.statement.setDouble(3, this.detalleVenta.getSubtotal());
        this.statement.setInt(4, this.detalleVenta.getVenta_id());
        this.statement.setInt(5, this.detalleVenta.getProdVariante().getProd_variante_id());
        this.statement.setInt(6, this.detalleVenta.getDetalle_venta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getDetalle_venta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getDetalle_venta_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleVenta = new DetalleVentasDTO();
        this.detalleVenta.setDetalle_venta_id(this.resultSet.getInt("DETALLE_VENTA_ID"));
        this.detalleVenta.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalleVenta.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detalleVenta.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.detalleVenta.setVenta_id(this.resultSet.getInt("VENTA_ID"));

        // Obtener ProductoVariante completo usando DAO
        Integer prod_variante_id = this.resultSet.getInt("PROD_VARIANTE_ID");
        ProductosVariantesDTO productoVar = this.productoVarianteDAO.obtenerPorId(prod_variante_id);
        this.detalleVenta.setProdVariante(productoVar);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleVenta = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleVenta);
    }

    @Override
    public Integer insertar(DetalleVentasDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
        return super.insertar();
    }

    @Override
    public DetalleVentasDTO obtenerPorId(Integer detalleVentaId) {
        this.detalleVenta = new DetalleVentasDTO();
        this.detalleVenta.setDetalle_venta_id(detalleVentaId);
        super.obtenerPorId();
        return this.detalleVenta;
    }

    @Override
    public ArrayList<DetalleVentasDTO> listarTodos() {
        return (ArrayList<DetalleVentasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleVentasDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleVentasDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
        return super.eliminar();
    }

    // BÚSQUEDAS AVANZADAS
    
    @Override
    public ArrayList<DetalleVentasDTO> listarPorVentaId(Integer ventaId) {
        String sql = "SELECT DETALLE_VENTA_ID, CANTIDAD, PRECIO_UNITARIO, "
                + "SUBTOTAL, VENTA_ID, PROD_VARIANTE_ID "
                + "FROM DETALLE_VENTAS "
                + "WHERE VENTA_ID = ?";

        return (ArrayList<DetalleVentasDTO>) super.listarTodos(
                sql,
                (params) -> {
                    try {
                        this.statement.setInt(1, ventaId);
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetro pedidoId: " + ex);
                    }
                },
                null
        );
    }
}
