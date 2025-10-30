package capaPersistencia.Implementar;

import capaDominio.EnviosDTO;
import capaDominio.PedidoDTO;
import capaDominio.envioDetalle.Courier;
import capaDominio.envioDetalle.Estado_Envio;
import capaPersistencia.EnvioDAO;
import capaPersistencia.Implementar.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnvioDAOImpl extends DAOImplBase implements EnvioDAO{
    
    private EnviosDTO envio;

    public EnvioDAOImpl() {
        super("ENVIOS");
        this.envio = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ENVIO_ID", true, true));
        this.listaColumnas.add(new Columna("ES_DELIVERY", false, false));
        this.listaColumnas.add(new Columna("DIRECCION_ENTREGA", false, false));
        this.listaColumnas.add(new Columna("COURIER", false, false));
        this.listaColumnas.add(new Columna("FECHA_ENVIO", false, false));
        this.listaColumnas.add(new Columna("COSTO_ENVIO", false, false));
        this.listaColumnas.add(new Columna("PEDIDO_ID", false, false));
        this.listaColumnas.add(new Columna("ESTADO_ENVIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_ULTIMO_ESTADO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.envio.getEs_delivery()?1:0);
        this.statement.setString(2, this.envio.getDireccion_entrega());
        this.statement.setInt(3, this.envio.getCourier().getId());
        this.statement.setTimestamp(4, java.sql.Timestamp.valueOf(this.envio.getFecha_envio()));
        this.statement.setDouble(5, this.envio.getCosto_envio());
        PedidoDTO pedido = this.envio.getPedido();
        if (pedido != null) {
            this.statement.setInt(6, pedido.getPedido_id());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }
        this.statement.setInt(7, this.envio.getEstado().getId());
        this.statement.setTimestamp(8, java.sql.Timestamp.valueOf(this.envio.getFecha_ultimo_estado()));
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.envio.getEs_delivery()?1:0);
        this.statement.setString(2, this.envio.getDireccion_entrega());
        this.statement.setInt(3, this.envio.getCourier().getId());
        this.statement.setTimestamp(4, java.sql.Timestamp.valueOf(this.envio.getFecha_envio()));
        this.statement.setDouble(5, this.envio.getCosto_envio());
        this.statement.setInt(6, this.envio.getEnvio_id());
        this.statement.setInt(7, this.envio.getEstado().getId());
        this.statement.setTimestamp(8, java.sql.Timestamp.valueOf(this.envio.getFecha_ultimo_estado()));
        this.statement.setInt(9, this.envio.getEnvio_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.envio.getEnvio_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.envio.getEnvio_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.envio = new EnviosDTO();
        this.envio.setEnvio_id(this.resultSet.getInt("ENVIO_ID"));
        this.envio.setEs_delivery(this.resultSet.getInt("ES_DELIVERY") == 1);
        this.envio.setDireccion_entrega(this.resultSet.getString("DIRECCION_ENTREGA"));
        Integer courierId = this.resultSet.getInt("COURIER_ID");
        this.envio.setCourier(Courier.fromId(courierId));
        this.envio.setFecha_envio(this.resultSet.getTimestamp("FECHA_ENVIO").toLocalDateTime());
        this.envio.setCosto_envio(this.resultSet.getDouble("COSTO_ENVIO"));
        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.resultSet.getInt("PEDIDO_ID"));
        this.envio.setPedido(pedido);
        Integer estadoPedId = this.resultSet.getInt("ESTADO_ENVIO_ID");
        this.envio.setEstado(Estado_Envio.fromId(estadoPedId));
        this.envio.setFecha_ultimo_estado(this.resultSet.getTimestamp("FECHA_ULTIMO_ESTADO").toLocalDateTime());
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.envio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.envio);
    }

    @Override
    public Integer insertar(EnviosDTO envio) {
        this.envio = envio;
        return super.insertar();
    }

    @Override
    public EnviosDTO obtenerPorId(Integer envioId) {
        this.envio = new EnviosDTO();
        this.envio.setEnvio_id(envioId);
        super.obtenerPorId();
        return this.envio;
    }

    @Override
    public ArrayList<EnviosDTO> listarTodos() {
        return (ArrayList<EnviosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(EnviosDTO envio) {
        this.envio = envio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(EnviosDTO envio) {
        this.envio = envio;
        return super.eliminar();
    }
    
}
