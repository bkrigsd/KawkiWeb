package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.MetodoPagoDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPagoDTO;

public class MetodoPagoDAOImpl extends BaseDAOImpl implements MetodoPagoDAO {

    private MetodoPagoDTO metodoPago;

    public MetodoPagoDAOImpl() {
        super("METODO_PAGO");
        this.metodoPago = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("METODO_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.metodoPago.getMetodo_pago_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.metodoPago = new MetodoPagoDTO();
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
    public MetodoPagoDTO obtenerPorId(Integer metodoPagoId) {
        this.metodoPago = new MetodoPagoDTO();
        this.metodoPago.setMetodo_pago_id(metodoPagoId);
        super.obtenerPorId();
        return this.metodoPago;
    }

    @Override
    public ArrayList<MetodoPagoDTO> listarTodos() {
        return (ArrayList<MetodoPagoDTO>) super.listarTodos();
    }
}
