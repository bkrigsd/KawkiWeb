package capaPersistencia.Implementar;

import capaDominio.pedidoDetalle.Estado_Pedido;
import capaDominio.PedidoDTO;
import capaDominio.PromocionDTO;
import capaDominio.UsuarioDTO;
import capaPersistencia.Implementar.util.Columna;
import capaPersistencia.PedidoDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl extends DAOImplBase implements PedidoDAO {

    private PedidoDTO pedido;

    public PedidoDAOImpl() {
        super("PEDIDOS");
        this.pedido = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PEDIDO_ID", true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_ULTIMO_ESTADO", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("ESTADO_PEDIDO_ID", false, false));
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
        this.statement.setTimestamp(3, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_ultimo_estado()));
        this.statement.setDouble(4, this.pedido.getTotal());
        this.statement.setInt(5, this.pedido.getEstado_pedido().getId());
        PromocionDTO promocion = this.pedido.getPromocion();
        if (promocion != null) {
            this.statement.setInt(6, promocion.getPromocion_id());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.pedido.getUsuario().getUsuarioId());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_creacion()));
        this.statement.setTimestamp(3, java.sql.Timestamp.valueOf(this.pedido.getFecha_hora_ultimo_estado()));
        this.statement.setDouble(4, this.pedido.getTotal());
        this.statement.setInt(5, this.pedido.getEstado_pedido().getId());
        PromocionDTO promocion = this.pedido.getPromocion();
        if (promocion != null) {
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
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        this.pedido.setUsuario(usuario);
        this.pedido.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());
        this.pedido.setFecha_hora_ultimo_estado(this.resultSet.getTimestamp("FECHA_HORA_ULTIMO_ESTADO").toLocalDateTime());
        this.pedido.setTotal(this.resultSet.getDouble("TOTAL"));
        Integer estadoId = this.resultSet.getInt("ESTADO_PEDIDO_ID");
        this.pedido.setEstado_pedido(Estado_Pedido.fromId(estadoId));
        Integer promocionId = (Integer) this.resultSet.getObject("PROMOCION_ID");
        if (promocionId != null) {
            PromocionDTO promo = new PromocionDTO();
            promo.setPromocion_id(promocionId);
            this.pedido.setPromocion(promo);
        } else {
            this.pedido.setPromocion(null);
        }
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
