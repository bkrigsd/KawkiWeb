package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;
import pe.edu.pucp.kawkiweb.dao.TiposComprobanteDAO;

public class TiposComprobanteDAOImpl extends BaseDAOImpl implements TiposComprobanteDAO {

    private TiposComprobanteDTO tipoComprobante;

    public TiposComprobanteDAOImpl() {
        super("TIPOS_COMPROBANTE");
        this.tipoComprobante = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_COMPROBANTE_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoComprobante.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoComprobante.getNombre());
        this.statement.setInt(2, this.tipoComprobante.getTipo_comprobante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoComprobante.getTipo_comprobante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoComprobante.getTipo_comprobante_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoComprobante = new TiposComprobanteDTO();
        this.tipoComprobante.setTipo_comprobante_id(this.resultSet.getInt("TIPO_COMPROBANTE_ID"));
        this.tipoComprobante.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoComprobante = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoComprobante);
    }

    @Override
    public Integer insertar(TiposComprobanteDTO tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
        return super.insertar();
    }

    @Override
    public TiposComprobanteDTO obtenerPorId(Integer tipoComprobanteId) {
        this.tipoComprobante = new TiposComprobanteDTO();
        this.tipoComprobante.setTipo_comprobante_id(tipoComprobanteId);
        super.obtenerPorId();
        return this.tipoComprobante;
    }

    @Override
    public ArrayList<TiposComprobanteDTO> listarTodos() {
        return (ArrayList<TiposComprobanteDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TiposComprobanteDTO tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TiposComprobanteDTO tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
        return super.eliminar();
    }
}
