package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TipoComprobanteDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilPago.TipoComprobanteDTO;

public class TipoComprobanteDAOImpl extends BaseDAOImpl implements TipoComprobanteDAO {

    private TipoComprobanteDTO tipoComprobante;

    public TipoComprobanteDAOImpl() {
        super("TIPO_COMPROBANTES");
        this.tipoComprobante = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_COMPROBANTE_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoComprobante.getTipo_comprobante_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoComprobante = new TipoComprobanteDTO();
        this.tipoComprobante.setTipo_comprobante_id(this.resultSet.getInt("TIPO_COMPROBANTE_ID"));
        this.tipoComprobante.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoComprobante = null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoComprobante);
    }

    @Override
    public TipoComprobanteDTO obtenerPorId(Integer tipoComprobanteId) {
        this.tipoComprobante = new TipoComprobanteDTO();
        this.tipoComprobante.setTipo_comprobante_id(tipoComprobanteId);
        super.obtenerPorId();
        return this.tipoComprobante;
    }

    @Override
    public ArrayList<TipoComprobanteDTO> listarTodos() {
        return (ArrayList<TipoComprobanteDTO>) super.listarTodos();
    }
}
