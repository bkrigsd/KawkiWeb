package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.dao.TallasDAO;

public class TallasDAOImpl extends BaseDAOImpl implements TallasDAO {

    private TallasDTO talla;

    public TallasDAOImpl() {
        super("TALLAS");
        this.talla = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TALLA_ID", true, true));
        this.listaColumnas.add(new Columna("NUMERO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.talla.getNumero());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.talla.getNumero());
        this.statement.setInt(2, this.talla.getTalla_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.talla.getTalla_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.talla.getTalla_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.talla = new TallasDTO();
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
    public Integer insertar(TallasDTO talla) {
        this.talla = talla;
        return super.insertar();
    }

    @Override
    public TallasDTO obtenerPorId(Integer tallaId) {
        this.talla = new TallasDTO();
        this.talla.setTalla_id(tallaId);
        super.obtenerPorId();
        return this.talla;
    }

    @Override
    public ArrayList<TallasDTO> listarTodos() {
        return (ArrayList<TallasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TallasDTO talla) {
        this.talla = talla;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TallasDTO talla) {
        this.talla = talla;
        return super.eliminar();
    }
}
