package capaPersistencia.Implementar;

import capaDominio.PagoDTO;
import capaDominio.pagoDetalle.MetodoPago;
import capaPersistencia.PagoDAO;
import capaPersistencia.Implementar.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagoDAOImpl extends DAOImplBase implements PagoDAO {

    private PagoDTO pago;

    public PagoDAOImpl() {
        super("PAGOS");
        this.pago = null;
        this.retornarLlavePrimaria = true; 
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
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pago.getFecha_hora_creacion()));
        this.statement.setInt(3, this.pago.getMetodo_pago().getId());
        this.statement.setInt(4, this.pago.getPedido_id());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setDouble(1, this.pago.getMonto_total());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.pago.getFecha_hora_creacion()));
        this.statement.setInt(3, this.pago.getMetodo_pago().getId());
        this.statement.setInt(4, this.pago.getPedido_id());
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
        this.pago.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_PAGO").toLocalDateTime());

        Integer metodoId = this.resultSet.getInt("METODO_PAGO_ID");
        this.pago.setMetodo_pago(MetodoPago.fromId(metodoId));

        this.pago.setPedido_id(this.resultSet.getInt("PEDIDO_ID"));
        this.pago.setComprobante(null);
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
