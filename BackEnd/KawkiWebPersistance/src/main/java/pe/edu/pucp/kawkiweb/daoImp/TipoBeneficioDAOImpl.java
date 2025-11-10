package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TipoBeneficioDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

public class TipoBeneficioDAOImpl extends BaseDAOImpl implements TipoBeneficioDAO {

    private TiposBeneficioDTO tipoBeneficio;

    public TipoBeneficioDAOImpl() {
        super("TIPO_BENEFICIO");
        this.tipoBeneficio = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_BENEFICIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
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
}
