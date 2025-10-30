package capaPersistencia.Implementar;

import capaDominio.Carrito_ComprasDTO;
import capaDominio.UsuarioDTO;
import capaPersistencia.Carrito_ComprasDAO;
import capaPersistencia.Implementar.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author beker
 */
public class Carrito_ComprasDAOImpl extends DAOImplBase implements Carrito_ComprasDAO {

    private Carrito_ComprasDTO carrito;

    public Carrito_ComprasDAOImpl() {
        super("CARRITO_COMPRAS");   // nombre de la tabla en BD
        this.carrito = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CARRITO_ID", true, true));  // PK autoincremental
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false)); // FK hacia usuario
        this.listaColumnas.add(new Columna("TOTAL", false, false));     // total del carrito
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.carrito.getUsuario().getUsuarioId());
        this.statement.setDouble(2, this.carrito.getTotal());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.carrito.getUsuario().getUsuarioId());
        this.statement.setDouble(2, this.carrito.getTotal());
        this.statement.setInt(3, this.carrito.getCarrito_id()); // condición WHERE
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.carrito.getCarrito_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.carrito.getCarrito_id());
    }
    
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.carrito = new Carrito_ComprasDTO();
        this.carrito.setCarrito_id(this.resultSet.getInt("CARRITO_ID"));
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        this.carrito.setUsuario(usuario);
        this.carrito.setTotal(this.resultSet.getDouble("TOTAL"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.carrito = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.carrito);
    }

    // Métodos públicos DAO
    @Override
    public Integer insertar(Carrito_ComprasDTO carrito) {
        this.carrito = carrito;
        return super.insertar();
    }

    @Override
    public Carrito_ComprasDTO obtenerPorId(Integer carritoId) {
        this.carrito = new Carrito_ComprasDTO();
        this.carrito.setCarrito_id(carritoId);
        super.obtenerPorId();
        return this.carrito;
    }

    @Override
    public ArrayList<Carrito_ComprasDTO> listarTodos() {
        return (ArrayList<Carrito_ComprasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(Carrito_ComprasDTO carrito) {
        this.carrito = carrito;
        return super.modificar();
    }

    @Override
    public Integer eliminar(Carrito_ComprasDTO carrito) {
        this.carrito = carrito;
        return super.eliminar();
    }

}
