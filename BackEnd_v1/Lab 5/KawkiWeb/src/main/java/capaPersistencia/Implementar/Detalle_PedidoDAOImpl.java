package capaPersistencia.Implementar;

import capaDominio.Detalle_PedidoDTO;
import capaDominio.PedidoDTO;
import capaDominio.ProductoVarianteDTO;
import capaPersistencia.Implementar.util.Columna;
import capaPersistencia.Detalle_PedidoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Detalle_PedidoDAOImpl extends DAOImplBase implements Detalle_PedidoDAO{
    private Detalle_PedidoDTO detallepedido;

    public Detalle_PedidoDAOImpl() {
        super("DETALLE_PEDIDOS");
        this.detallepedido = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_PEDIDO_ID", true, true));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
        this.listaColumnas.add(new Columna("PEDIDO_ID", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        ProductoVarianteDTO prodVar = this.detallepedido.getProductoVar();
        if (prodVar != null) {
            this.statement.setInt(1, prodVar.getProd_variante_id());
        } else {
            this.statement.setNull(1, java.sql.Types.INTEGER);
        }
        PedidoDTO pedido = this.detallepedido.getPedido();
        if (pedido != null) {
            this.statement.setInt(2, pedido.getPedido_id());
        } else {
            this.statement.setNull(2, java.sql.Types.INTEGER);
        }
        this.statement.setInt(3, this.detallepedido.getCantidad());
        this.statement.setDouble(4, this.detallepedido.getPrecio_unitario());
        this.statement.setDouble(5, this.detallepedido.getSubtotal());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detallepedido.getProductoVar().getProducto_id());
        this.statement.setInt(2, this.detallepedido.getPedido().getPedido_id());
        this.statement.setInt(3, this.detallepedido.getCantidad());
        this.statement.setDouble(4, this.detallepedido.getPrecio_unitario());
        this.statement.setDouble(5, this.detallepedido.getSubtotal());
        this.statement.setInt(6, this.detallepedido.getDetalle_pedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detallepedido.getDetalle_pedido_id());
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException{
        this.statement.setInt(1, this.detallepedido.getDetalle_pedido_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detallepedido = new Detalle_PedidoDTO();
        this.detallepedido.setDetalle_pedido_id(this.resultSet.getInt("DETALLE_PEDIDO_ID"));
        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.resultSet.getInt("PEDIDO_ID"));
        this.detallepedido.setPedido(pedido);
        ProductoVarianteDTO productoVar = new ProductoVarianteDTO();
        productoVar.setProducto_id(this.resultSet.getInt("PROD_VARIANTE_ID")); //fk
        this.detallepedido.setProductoVar(productoVar);
        this.detallepedido.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detallepedido.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detallepedido.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
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
    public Integer insertar(Detalle_PedidoDTO detallepedido) {
        this.detallepedido = detallepedido;
        return super.insertar();
    }

    @Override
    public Detalle_PedidoDTO obtenerPorId(Integer detallepedidoId) {
        this.detallepedido = new Detalle_PedidoDTO();
        this.detallepedido.setDetalle_pedido_id(detallepedidoId);
        super.obtenerPorId();
        return this.detallepedido;
    }

    @Override
    public ArrayList<Detalle_PedidoDTO> listarTodos() {
        return (ArrayList<Detalle_PedidoDTO>) super.listarTodos();
    }
 
    @Override
    public Integer modificar(Detalle_PedidoDTO detallepedido) {
        this.detallepedido = detallepedido;
        return super.modificar();
    }

    @Override
    public Integer eliminar(Detalle_PedidoDTO detallepedido) {
        this.detallepedido = detallepedido;
        return super.eliminar();
    }
}
