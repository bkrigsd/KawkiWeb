package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.CategoriaDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriaDTO;

public class CategoriaDAOImpl extends BaseDAOImpl implements CategoriaDAO {

    private CategoriaDTO categoria;

    public CategoriaDAOImpl() {
        super("CATEGORIAS");
        this.categoria = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CATEGORIA_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.categoria.getCategoria_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.categoria = new CategoriaDTO();
        this.categoria.setCategoria_id(this.resultSet.getInt("CATEGORIA_ID"));
        this.categoria.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.categoria = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.categoria);
    }

    @Override
    public CategoriaDTO obtenerPorId(Integer categoriaId) {
        this.categoria = new CategoriaDTO();
        this.categoria.setCategoria_id(categoriaId);
        super.obtenerPorId();
        return this.categoria;
    }

    @Override
    public ArrayList<CategoriaDTO> listarTodos() {
        return (ArrayList<CategoriaDTO>) super.listarTodos();
    }
}
