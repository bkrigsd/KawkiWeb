package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.dao.ColoresDAO;

public class ColoresDAOImpl extends BaseDAOImpl implements ColoresDAO {

    private ColoresDTO color;

    public ColoresDAOImpl() {
        super("COLORES");
        this.color = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("COLOR_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.color.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.color.getNombre());
        this.statement.setInt(2, this.color.getColor_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.color.getColor_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.color.getColor_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.color = new ColoresDTO();
        this.color.setColor_id(this.resultSet.getInt("COLOR_ID"));
        this.color.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.color = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.color);
    }

    @Override
    public Integer insertar(ColoresDTO color) {
        this.color = color;
        return super.insertar();
    }

    @Override
    public ColoresDTO obtenerPorId(Integer colorId) {
        this.color = new ColoresDTO();
        this.color.setColor_id(colorId);
        super.obtenerPorId();
        return this.color;
    }

    @Override
    public ArrayList<ColoresDTO> listarTodos() {
        return (ArrayList<ColoresDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(ColoresDTO color) {
        this.color = color;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ColoresDTO color) {
        this.color = color;
        return super.eliminar();
    }
    
}
