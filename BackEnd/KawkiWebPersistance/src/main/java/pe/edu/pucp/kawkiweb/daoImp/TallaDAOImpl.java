package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TallaDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallaDTO;

public class TallaDAOImpl extends BaseDAOImpl implements TallaDAO {

    private TallaDTO talla;

    public TallaDAOImpl() {
        super("TALLAS");
        this.talla = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TALLA_ID", true, true));
        this.listaColumnas.add(new Columna("NUMERO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.talla.getTalla_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.talla = new TallaDTO();
        this.talla.setTalla_id(this.resultSet.getInt("TALLA_ID"));
        this.talla.setNumero(this.resultSet.getInt("NUMERO"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.talla = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.talla);
    }

    @Override
    public TallaDTO obtenerPorId(Integer tallaId) {
        this.talla = new TallaDTO();
        this.talla.setTalla_id(tallaId);
        super.obtenerPorId();
        return this.talla;
    }

    @Override
    public ArrayList<TallaDTO> listarTodos() {
        return (ArrayList<TallaDTO>) super.listarTodos();
    }
}
