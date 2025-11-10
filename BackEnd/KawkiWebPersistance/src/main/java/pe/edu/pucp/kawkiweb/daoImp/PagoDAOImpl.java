package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.ComprobantePagoDAO;
import pe.edu.pucp.kawkiweb.dao.MetodoPagoDAO;
import pe.edu.pucp.kawkiweb.dao.PagoDAO;
import pe.edu.pucp.kawkiweb.dao.PedidoDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;
import pe.edu.pucp.kawkiweb.model.PagoDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;

public class PagoDAOImpl extends BaseDAOImpl implements PagoDAO {

    private PagoDTO pago;
    private MetodoPagoDAO metodoPagoDAO;
    private PedidoDAO pedidoDAO;
    private ComprobantePagoDAO comprobantePagoDAO;

    public PagoDAOImpl() {
        super("PAGOS");
        this.pago = null;
        this.retornarLlavePrimaria = true;
        this.metodoPagoDAO = new MetodoPagoDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
        this.comprobantePagoDAO = new ComprobantePagoDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("MONTO_TOTAL", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_PAGO", false, false));
        this.listaColumnas.add(new Columna("METODO_PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("PEDIDO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setDouble(1, this.pago.getMonto_total());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pago.getFecha_hora_pago()));
        this.statement.setInt(3, this.pago.getMetodo_pago().getMetodo_pago_id());
        this.statement.setInt(4, this.pago.getPedido().getPedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setDouble(1, this.pago.getMonto_total());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pago.getFecha_hora_pago()));
        this.statement.setInt(3, this.pago.getMetodo_pago().getMetodo_pago_id());
        this.statement.setInt(4, this.pago.getPedido().getPedido_id());
        this.statement.setInt(5, this.pago.getPago_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.pago.getPago_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.pago.getPago_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.pago = new PagoDTO();
        this.pago.setPago_id(this.resultSet.getInt("PAGO_ID"));
        this.pago.setMonto_total(this.resultSet.getDouble("MONTO_TOTAL"));
        this.pago.setFecha_hora_pago(this.resultSet.getTimestamp("FECHA_HORA_PAGO").toLocalDateTime());

        // Debe usar el DAO para obtener el objeto completo:
        Integer metodoId = this.resultSet.getInt("METODO_PAGO_ID");
        MetodosPagoDTO metodoPago = this.metodoPagoDAO.obtenerPorId(metodoId);
        this.pago.setMetodo_pago(metodoPago);

        // Debe obtener el pedido completo y usar el método correcto:
        Integer pedidoId = this.resultSet.getInt("PEDIDO_ID");
        VentasDTO pedido = this.pedidoDAO.obtenerPorId(pedidoId);
        this.pago.setPedido(pedido); // Método correcto según PagoDTO

        // Obtener comprobante si existe
        try {
            ComprobantesPagoDTO comprobante = this.comprobantePagoDAO.obtenerPorPagoId(this.pago.getPago_id());
            this.pago.setComprobante(comprobante);
        } catch (Exception e) {
            // Si falla o no existe, dejar en null
            this.pago.setComprobante(null);
        }
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.pago = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.pago);
    }

    @Override
    public Integer insertar(PagoDTO pago) {
        this.pago = pago;
        return super.insertar();
    }

    @Override
    public PagoDTO obtenerPorId(Integer pago_id) {
        this.pago = new PagoDTO();
        this.pago.setPago_id(pago_id);
        super.obtenerPorId();
        return this.pago;
    }

    @Override
    public ArrayList<PagoDTO> listarTodos() {
        return (ArrayList<PagoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PagoDTO pago) {
        this.pago = pago;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PagoDTO pago) {
        this.pago = pago;
        return super.eliminar();
    }
}
