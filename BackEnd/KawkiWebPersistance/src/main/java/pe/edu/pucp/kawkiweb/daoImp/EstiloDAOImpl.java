package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.EstiloDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

public class EstiloDAOImpl extends BaseDAOImpl implements EstiloDAO {

    private EstilosDTO estilo;

    public EstiloDAOImpl() {
        super("ESTILOS");
        this.estilo = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ESTILO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.estilo.getEstilo_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.estilo = new EstilosDTO();
        this.estilo.setEstilo_id(this.resultSet.getInt("ESTILO_ID"));
        this.estilo.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.estilo = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.estilo);
    }

    @Override
    public EstilosDTO obtenerPorId(Integer estiloId) {
        this.estilo = new EstilosDTO();
        this.estilo.setEstilo_id(estiloId);
        super.obtenerPorId();
        return this.estilo;
    }

    @Override
    public ArrayList<EstilosDTO> listarTodos() {
        return (ArrayList<EstilosDTO>) super.listarTodos();
    }
}
