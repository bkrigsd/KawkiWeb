package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.dao.TiposCondicionDAO;

public class TiposCondicionDAOImpl extends BaseDAOImpl implements TiposCondicionDAO {

    private TiposCondicionDTO tipoCondicion;

    public TiposCondicionDAOImpl() {
        super("TIPOS_CONDICION");
        this.tipoCondicion = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_CONDICION_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoCondicion.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoCondicion.getNombre());
        this.statement.setInt(2, this.tipoCondicion.getTipo_condicion_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoCondicion.getTipo_condicion_id());
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
    public Integer insertar(TiposCondicionDTO tipoCondicion) {
        this.tipoCondicion = tipoCondicion;
        return super.insertar();
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

    @Override
    public Integer modificar(TiposCondicionDTO tipoCondicion) {
        this.tipoCondicion = tipoCondicion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TiposCondicionDTO tipoCondicion) {
        this.tipoCondicion = tipoCondicion;
        return super.eliminar();
    }

}
