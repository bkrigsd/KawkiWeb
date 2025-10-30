package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.CourierDAO;
import pe.edu.pucp.kawkiweb.dao.EnvioDAO;
import pe.edu.pucp.kawkiweb.dao.EstadoEnvioDAO;
import pe.edu.pucp.kawkiweb.dao.PedidoDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilEnvio.CourierDTO;
import pe.edu.pucp.kawkiweb.model.EnvioDTO;
import pe.edu.pucp.kawkiweb.model.utilEnvio.EstadoEnvioDTO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;

public class EnvioDAOImpl extends BaseDAOImpl implements EnvioDAO {

    private EnvioDTO envio;
    private CourierDAO courierDAO;
    private EstadoEnvioDAO estadoEnvioDAO;
    private PedidoDAO pedidoDAO;

    public EnvioDAOImpl() {
        super("ENVIOS");
        this.envio = null;
        this.retornarLlavePrimaria = true;
        this.courierDAO = new CourierDAOImpl();
        this.estadoEnvioDAO = new EstadoEnvioDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ENVIO_ID", true, true));
        this.listaColumnas.add(new Columna("ES_DELIVERY", false, false));
        this.listaColumnas.add(new Columna("DIRECCION_ENTREGA", false, false));
        this.listaColumnas.add(new Columna("COURIER_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_ENVIO", false, false));
        this.listaColumnas.add(new Columna("COSTO_ENVIO", false, false));
        this.listaColumnas.add(new Columna("PEDIDO_ID", false, false));
        this.listaColumnas.add(new Columna("ESTADO_ENVIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_ULTIMO_ESTADO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.envio.getEs_delivery() ? 1 : 0);
        this.statement.setString(2, this.envio.getDireccion_entrega());

        // Obtener ID del CourierDTO
        Integer courierId = (this.envio.getCourier() != null)
                ? this.envio.getCourier().getCourierId()
                : null;

        if (courierId != null) {
            this.statement.setInt(3, courierId);
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }

        if (this.envio.getFecha_envio() != null) {
            this.statement.setTimestamp(4, java.sql.Timestamp.valueOf(this.envio.getFecha_envio()));
        } else {
            this.statement.setNull(4, java.sql.Types.TIMESTAMP);
        }

        if (this.envio.getCosto_envio() != null) {
            this.statement.setDouble(5, this.envio.getCosto_envio());
        } else {
            this.statement.setNull(5, java.sql.Types.DECIMAL);
        }

        PedidoDTO pedido = this.envio.getPedido();
        if (pedido != null) {
            this.statement.setInt(6, pedido.getPedido_id());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }

        // Obtener ID del EstadoEnvioDTO
        Integer estadoEnvioId = (this.envio.getEstado() != null)
                ? this.envio.getEstado().getEstadoEnvioId()
                : null;

        if (estadoEnvioId != null) {
            this.statement.setInt(7, estadoEnvioId);
        } else {
            this.statement.setNull(7, java.sql.Types.INTEGER);
        }

        this.statement.setTimestamp(8, java.sql.Timestamp.valueOf(this.envio.getFecha_ultimo_estado()));
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.envio.getEs_delivery() ? 1 : 0);
        this.statement.setString(2, this.envio.getDireccion_entrega());

        // Obtener ID del CourierDTO
        Integer courierId = (this.envio.getCourier() != null)
                ? this.envio.getCourier().getCourierId()
                : null;

        if (courierId != null) {
            this.statement.setInt(3, courierId);
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }

        if (this.envio.getFecha_envio() != null) {
            this.statement.setTimestamp(4, java.sql.Timestamp.valueOf(this.envio.getFecha_envio()));
        } else {
            this.statement.setNull(4, java.sql.Types.TIMESTAMP);
        }

        if (this.envio.getCosto_envio() != null) {
            this.statement.setDouble(5, this.envio.getCosto_envio());
        } else {
            this.statement.setNull(5, java.sql.Types.DECIMAL);
        }

        PedidoDTO pedido = this.envio.getPedido();
        if (pedido != null) {
            this.statement.setInt(6, pedido.getPedido_id());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }

        // Obtener ID del EstadoEnvioDTO
        Integer estadoEnvioId = (this.envio.getEstado() != null)
                ? this.envio.getEstado().getEstadoEnvioId()
                : null;

        if (estadoEnvioId != null) {
            this.statement.setInt(7, estadoEnvioId);
        } else {
            this.statement.setNull(7, java.sql.Types.INTEGER);
        }

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
        this.envio = new EnvioDTO();
        this.envio.setEnvio_id(this.resultSet.getInt("ENVIO_ID"));
        this.envio.setEs_delivery(this.resultSet.getInt("ES_DELIVERY") == 1);
        this.envio.setDireccion_entrega(this.resultSet.getString("DIRECCION_ENTREGA"));

        // Usar CourierDAO para obtener el objeto completo
        Integer courierId = (Integer) this.resultSet.getObject("COURIER_ID");
        if (courierId != null) {
            CourierDTO courier = this.courierDAO.obtenerPorId(courierId);
            this.envio.setCourier(courier);
        } else {
            this.envio.setCourier(null);
        }

        Timestamp fechaEnvioTs = this.resultSet.getTimestamp("FECHA_ENVIO");
        this.envio.setFecha_envio(fechaEnvioTs != null ? fechaEnvioTs.toLocalDateTime() : null);

        Double costoEnvio = (Double) this.resultSet.getObject("COSTO_ENVIO");
        if (costoEnvio != null) {
            this.envio.setCosto_envio(costoEnvio);
        } else {
            this.envio.setCosto_envio(null);
        }

        Integer pedido_id = this.resultSet.getInt("PEDIDO_ID");
        PedidoDTO pedido = this.pedidoDAO.obtenerPorId(pedido_id);
        this.envio.setPedido(pedido);

        // Usar EstadoEnvioDAO para obtener el objeto completo
        Integer estadoEnvioId = this.resultSet.getInt("ESTADO_ENVIO_ID");
        EstadoEnvioDTO estado = this.estadoEnvioDAO.obtenerPorId(estadoEnvioId);
        this.envio.setEstado(estado);

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
    public Integer insertar(EnvioDTO envio) {
        this.envio = envio;
        return super.insertar();
    }

    @Override
    public EnvioDTO obtenerPorId(Integer envioId) {
        this.envio = new EnvioDTO();
        this.envio.setEnvio_id(envioId);
        super.obtenerPorId();
        return this.envio;
    }

    @Override
    public ArrayList<EnvioDTO> listarTodos() {
        return (ArrayList<EnvioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(EnvioDTO envio) {
        this.envio = envio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(EnvioDTO envio) {
        this.envio = envio;
        return super.eliminar();
    }

}
