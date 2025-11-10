package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetallePedidoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
import pe.edu.pucp.kawkiweb.dao.DetallePedidoDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;

public class Detalle_PedidoDAOImpl extends BaseDAOImpl implements DetallePedidoDAO {

    private DetallePedidoDTO detallepedido;
    private ProductoVarianteDAO productoVarianteDAO;

    public Detalle_PedidoDAOImpl() {
        super("DETALLE_PEDIDOS");
        this.detallepedido = null;
        this.retornarLlavePrimaria = true;
        this.productoVarianteDAO = new ProductoVarianteDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_PEDIDO_ID", true, true));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("PEDIDO_ID", false, false));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detallepedido.getCantidad());
        this.statement.setDouble(2, this.detallepedido.getPrecio_unitario());
        this.statement.setDouble(3, this.detallepedido.getSubtotal());

        Integer pedido_id = this.detallepedido.getPedido_id();
        if (pedido_id != null) {
            this.statement.setInt(4, pedido_id);
        } else {
            this.statement.setNull(4, java.sql.Types.INTEGER);
        }

        ProductoVarianteDTO prodVar = this.detallepedido.getProductoVar();
        if (prodVar != null) {
            this.statement.setInt(5, prodVar.getProd_variante_id());
        } else {
            this.statement.setNull(5, java.sql.Types.INTEGER);
        }

    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detallepedido.getCantidad());
        this.statement.setDouble(2, this.detallepedido.getPrecio_unitario());
        this.statement.setDouble(3, this.detallepedido.getSubtotal());
        this.statement.setInt(4, this.detallepedido.getPedido_id());
        this.statement.setInt(5, this.detallepedido.getProductoVar().getProd_variante_id());
        this.statement.setInt(6, this.detallepedido.getDetalle_pedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detallepedido.getDetalle_pedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detallepedido.getDetalle_pedido_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detallepedido = new DetallePedidoDTO();
        this.detallepedido.setDetalle_pedido_id(this.resultSet.getInt("DETALLE_PEDIDO_ID"));

        this.detallepedido.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detallepedido.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detallepedido.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));

        // Solo guardar el ID del pedido
        this.detallepedido.setPedido_id(this.resultSet.getInt("PEDIDO_ID"));

        // Obtener ProductoVariante completo usando DAO
        Integer prod_variante_id = this.resultSet.getInt("PROD_VARIANTE_ID");
        ProductoVarianteDTO productoVar = this.productoVarianteDAO.obtenerPorId(prod_variante_id);
        this.detallepedido.setProductoVar(productoVar);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detallepedido = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detallepedido);
    }

    @Override
    public Integer insertar(DetallePedidoDTO detallepedido) {
        this.detallepedido = detallepedido;
        return super.insertar();
    }

    @Override
    public DetallePedidoDTO obtenerPorId(Integer detallepedidoId) {
        this.detallepedido = new DetallePedidoDTO();
        this.detallepedido.setDetalle_pedido_id(detallepedidoId);
        super.obtenerPorId();
        return this.detallepedido;
    }

    @Override
    public ArrayList<DetallePedidoDTO> listarTodos() {
        return (ArrayList<DetallePedidoDTO>) super.listarTodos();
    }

    @Override
    public ArrayList<DetallePedidoDTO> listarPorPedidoId(Integer pedidoId) {
        String sql = "SELECT DETALLE_PEDIDO_ID, CANTIDAD, PRECIO_UNITARIO, "
                + "SUBTOTAL, PEDIDO_ID, PROD_VARIANTE_ID "
                + "FROM DETALLE_PEDIDOS "
                + "WHERE PEDIDO_ID = ?";

        return (ArrayList<DetallePedidoDTO>) super.listarTodos(
                sql,
                (params) -> {
                    try {
                        this.statement.setInt(1, pedidoId);
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetro pedidoId: " + ex);
                    }
                },
                null
        );
    }

    @Override
    public Integer modificar(DetallePedidoDTO detallepedido) {
        this.detallepedido = detallepedido;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetallePedidoDTO detallepedido) {
        this.detallepedido = detallepedido;
        return super.eliminar();
    }

}
