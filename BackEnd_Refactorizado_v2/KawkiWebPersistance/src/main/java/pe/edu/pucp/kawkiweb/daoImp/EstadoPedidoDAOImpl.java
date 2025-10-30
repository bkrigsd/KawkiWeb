package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.EstadoPedidoDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;

public class EstadoPedidoDAOImpl extends BaseDAOImpl implements EstadoPedidoDAO {

    private EstadoPedidoDTO estadoPedido;

    public EstadoPedidoDAOImpl() {
        super("ESTADO_PEDIDO");
        this.estadoPedido = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ESTADO_PEDIDO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.estadoPedido.getEstado_pedido_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.estadoPedido = new EstadoPedidoDTO();
        this.estadoPedido.setEstado_pedido_id(this.resultSet.getInt("ESTADO_PEDIDO_ID"));
        this.estadoPedido.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.estadoPedido = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.estadoPedido);
    }

    @Override
    public EstadoPedidoDTO obtenerPorId(Integer estadoPedidoId) {
        this.estadoPedido = new EstadoPedidoDTO();
        this.estadoPedido.setEstado_pedido_id(estadoPedidoId);
        super.obtenerPorId();
        return this.estadoPedido;
    }

    @Override
    public ArrayList<EstadoPedidoDTO> listarTodos() {
        return (ArrayList<EstadoPedidoDTO>) super.listarTodos();
    }
}
