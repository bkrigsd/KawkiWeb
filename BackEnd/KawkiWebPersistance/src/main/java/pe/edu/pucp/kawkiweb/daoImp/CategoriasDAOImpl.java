package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.dao.CategoriasDAO;

public class CategoriasDAOImpl extends BaseDAOImpl implements CategoriasDAO {

    private CategoriasDTO categoria;

    public CategoriasDAOImpl() {
        super("CATEGORIAS");
        this.categoria = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CATEGORIA_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.categoria.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.categoria.getNombre());
        this.statement.setInt(2, this.categoria.getCategoria_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.categoria.getCategoria_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.categoria.getCategoria_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.categoria = new CategoriasDTO();
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
    public Integer insertar(CategoriasDTO categoria) {
        this.categoria = categoria;
        return super.insertar();
    }

    @Override
    public CategoriasDTO obtenerPorId(Integer categoriaId) {
        this.categoria = new CategoriasDTO();
        this.categoria.setCategoria_id(categoriaId);
        super.obtenerPorId();
        return this.categoria;
    }

    @Override
    public ArrayList<CategoriasDTO> listarTodos() {
        return (ArrayList<CategoriasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(CategoriasDTO categoria) {
        this.categoria = categoria;
        return super.modificar();
    }

    @Override
    public Integer eliminar(CategoriasDTO categoria) {
        this.categoria = categoria;
        return super.eliminar();
    }
}
