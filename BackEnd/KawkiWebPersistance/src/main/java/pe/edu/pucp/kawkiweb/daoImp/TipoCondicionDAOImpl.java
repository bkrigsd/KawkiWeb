package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TipoCondicionDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

public class TipoCondicionDAOImpl extends BaseDAOImpl implements TipoCondicionDAO {

    private TiposCondicionDTO tipoCondicion;

    public TipoCondicionDAOImpl() {
        super("TIPO_CONDICION");
        this.tipoCondicion = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_CONDICION_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoCondicion.getTipo_condicion_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoCondicion = new TiposCondicionDTO();
        this.tipoCondicion.setTipo_condicion_id(this.resultSet.getInt("TIPO_CONDICION_ID"));
        this.tipoCondicion.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoCondicion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoCondicion);
    }

    @Override
    public TiposCondicionDTO obtenerPorId(Integer tipoCondicionId) {
        this.tipoCondicion = new TiposCondicionDTO();
        this.tipoCondicion.setTipo_condicion_id(tipoCondicionId);
        super.obtenerPorId();
        return this.tipoCondicion;
    }

    @Override
    public ArrayList<TiposCondicionDTO> listarTodos() {
        return (ArrayList<TiposCondicionDTO>) super.listarTodos();
    }
}
