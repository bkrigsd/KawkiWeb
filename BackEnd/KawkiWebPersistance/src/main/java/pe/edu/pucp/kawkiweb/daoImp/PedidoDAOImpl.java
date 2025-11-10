package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.DetallePedidoDAO;
import pe.edu.pucp.kawkiweb.dao.EstadoPedidoDAO;
import pe.edu.pucp.kawkiweb.dao.PedidoDAO;
import pe.edu.pucp.kawkiweb.dao.PromocionDAO;
import pe.edu.pucp.kawkiweb.dao.UsuarioDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetallePedidoDTO;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

public class PedidoDAOImpl extends BaseDAOImpl implements PedidoDAO {

    private PedidoDTO pedido;
    private UsuarioDAO usuarioDAO;
    private PromocionDAO promocionDAO;
    private EstadoPedidoDAO estadoPedidoDAO;
    private DetallePedidoDAO detallePedidoDAO;

    public PedidoDAOImpl() {
        super("PEDIDOS");
        this.pedido = null;
        this.retornarLlavePrimaria = true;
        this.usuarioDAO = new UsuarioDAOImpl();
        this.promocionDAO = new PromocionDAOImpl();
        this.estadoPedidoDAO = new EstadoPedidoDAOImpl();
        this.detallePedidoDAO = new Detalle_PedidoDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PEDIDO_ID", true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("ESTADO_PEDIDO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_ULTIMO_ESTADO", false, false));
        this.listaColumnas.add(new Columna("PROMOCION_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        UsuarioDTO usuario = this.pedido.getUsuario();
        if (usuario != null) {
            this.statement.setInt(1, usuario.getUsuarioId());
        } else {
            this.statement.setNull(1, java.sql.Types.INTEGER);
        }
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_creacion()));
        this.statement.setDouble(3, this.pedido.getTotal());
        this.statement.setInt(4, this.pedido.getEstado_pedido().getEstado_pedido_id());
        this.statement.setTimestamp(5, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_ultimo_estado()));

        PromocionDTO promocion = this.pedido.getPromocion();
        if (promocion != null && promocion.getPromocion_id() != null) {
            this.statement.setInt(6, promocion.getPromocion_id());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.pedido.getUsuario().getUsuarioId());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_creacion()));
        this.statement.setDouble(3, this.pedido.getTotal());
        this.statement.setInt(4, this.pedido.getEstado_pedido().getEstado_pedido_id());
        this.statement.setTimestamp(5, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_ultimo_estado()));

        PromocionDTO promocion = this.pedido.getPromocion();
        if (promocion != null && promocion.getPromocion_id() != null) {
            this.statement.setInt(6, promocion.getPromocion_id());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }

        this.statement.setInt(7, this.pedido.getPedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.pedido.getPedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.pedido.getPedido_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.pedido = new PedidoDTO();
        this.pedido.setPedido_id(this.resultSet.getInt("PEDIDO_ID"));

        // Obtener usuario completo usando DAO
        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuarioDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.pedido.setUsuario(usuario);

        this.pedido.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );
        this.pedido.setTotal(this.resultSet.getDouble("TOTAL"));

        // Obtener EstadoPedido completo usando DAO
        Integer estado_pedido_id = this.resultSet.getInt("ESTADO_PEDIDO_ID");
        EstadoPedidoDTO estadoPedido = this.estadoPedidoDAO.obtenerPorId(estado_pedido_id);
        this.pedido.setEstado_pedido(estadoPedido);
        this.pedido.setFecha_hora_ultimo_estado(
                this.resultSet.getTimestamp("FECHA_HORA_ULTIMO_ESTADO").toLocalDateTime()
        );

        // Obtener Promoción completa usando DAO (puede ser null)
        Integer promocion_id = (Integer) this.resultSet.getObject("PROMOCION_ID");
        if (promocion_id != null) {
            PromocionDTO promocion = this.promocionDAO.obtenerPorId(promocion_id);
            this.pedido.setPromocion(promocion);
        } else {
            this.pedido.setPromocion(null);
        }

        // Cargar automáticamente los detalles del pedido
        ArrayList<DetallePedidoDTO> detalles = this.detallePedidoDAO.listarPorPedidoId(
                this.pedido.getPedido_id()
        );
        this.pedido.setDetalles(detalles);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.pedido = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.pedido);
    }

    @Override
    public Integer insertar(PedidoDTO pedido) {
        this.pedido = pedido;
        return super.insertar();
    }

    @Override
    public PedidoDTO obtenerPorId(Integer pedidoId) {
        this.pedido = new PedidoDTO();
        this.pedido.setPedido_id(pedidoId);
        super.obtenerPorId();
        return this.pedido;
    }

    @Override
    public ArrayList<PedidoDTO> listarTodos() {
        return (ArrayList<PedidoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PedidoDTO pedido) {
        this.pedido = pedido;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PedidoDTO pedido) {
        this.pedido = pedido;
        return super.eliminar();
    }

}
