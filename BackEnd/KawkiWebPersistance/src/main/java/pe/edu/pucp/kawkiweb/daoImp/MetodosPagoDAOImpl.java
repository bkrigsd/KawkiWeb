package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;
import pe.edu.pucp.kawkiweb.dao.MetodosPagoDAO;

public class MetodosPagoDAOImpl extends BaseDAOImpl implements MetodosPagoDAO {

    private MetodosPagoDTO metodoPago;

    public MetodosPagoDAOImpl() {
        super("METODOS_PAGO");
        this.metodoPago = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("METODO_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.metodoPago.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.metodoPago.getNombre());
        this.statement.setInt(2, this.metodoPago.getMetodo_pago_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.metodoPago.getMetodo_pago_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.metodoPago.getMetodo_pago_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.metodoPago = new MetodosPagoDTO();
        this.metodoPago.setMetodo_pago_id(this.resultSet.getInt("METODO_PAGO_ID"));
        this.metodoPago.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.metodoPago = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.metodoPago);
    }

    @Override
    public Integer insertar(MetodosPagoDTO metodoPago) {
        this.metodoPago = metodoPago;
        return super.insertar();
    }

    @Override
    public MetodosPagoDTO obtenerPorId(Integer metodoPagoId) {
        this.metodoPago = new MetodosPagoDTO();
        this.metodoPago.setMetodo_pago_id(metodoPagoId);
        super.obtenerPorId();
        return this.metodoPago;
    }

    @Override
    public ArrayList<MetodosPagoDTO> listarTodos() {
        return (ArrayList<MetodosPagoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MetodosPagoDTO metodoPago) {
        this.metodoPago = metodoPago;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MetodosPagoDTO metodoPago) {
        this.metodoPago = metodoPago;
        return super.eliminar();
    }
}
