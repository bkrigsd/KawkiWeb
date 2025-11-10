package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.dao.TiposBeneficioDAO;

public class TiposBeneficioDAOImpl extends BaseDAOImpl implements TiposBeneficioDAO {

    private TiposBeneficioDTO tipoBeneficio;

    public TiposBeneficioDAOImpl() {
        super("TIPOS_BENEFICIO");
        this.tipoBeneficio = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_BENEFICIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoBeneficio.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoBeneficio.getNombre());
        this.statement.setInt(2, this.tipoBeneficio.getTipo_beneficio_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoBeneficio.getTipo_beneficio_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoBeneficio.getTipo_beneficio_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoBeneficio = new TiposBeneficioDTO();
        this.tipoBeneficio.setTipo_beneficio_id(this.resultSet.getInt("TIPO_BENEFICIO_ID"));
        this.tipoBeneficio.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoBeneficio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoBeneficio);
    }

    @Override
    public Integer insertar(TiposBeneficioDTO tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
        return super.insertar();
    }

    @Override
    public TiposBeneficioDTO obtenerPorId(Integer tipoBeneficioId) {
        this.tipoBeneficio = new TiposBeneficioDTO();
        this.tipoBeneficio.setTipo_beneficio_id(tipoBeneficioId);
        super.obtenerPorId();
        return this.tipoBeneficio;
    }

    @Override
    public ArrayList<TiposBeneficioDTO> listarTodos() {
        return (ArrayList<TiposBeneficioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TiposBeneficioDTO tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TiposBeneficioDTO tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
        return super.eliminar();
    }

}
