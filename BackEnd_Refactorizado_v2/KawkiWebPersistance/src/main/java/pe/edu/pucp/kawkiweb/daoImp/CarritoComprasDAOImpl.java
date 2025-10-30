package pe.edu.pucp.kawkiweb.daoImp;

import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.CarritoComprasDAO;
import pe.edu.pucp.kawkiweb.dao.DetalleCarritoDAO;
import pe.edu.pucp.kawkiweb.dao.PromocionDAO;
import pe.edu.pucp.kawkiweb.dao.UsuarioDAO;
import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

public class CarritoComprasDAOImpl extends BaseDAOImpl implements CarritoComprasDAO {

    private CarritoComprasDTO carrito;
    private UsuarioDAO usuarioDAO;
    private PromocionDAO promocionDAO;
    private DetalleCarritoDAO detalleCarritoDAO;

    public CarritoComprasDAOImpl() {
        super("CARRITO_COMPRAS");
        this.carrito = null;
        this.retornarLlavePrimaria = true;
        this.usuarioDAO = new UsuarioDAOImpl();
        this.promocionDAO = new PromocionDAOImpl();
        this.detalleCarritoDAO = new DetalleCarritoDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CARRITO_ID", true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("PROMOCION_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        UsuarioDTO usuario = this.carrito.getUsuario();
        if (usuario != null) {
            this.statement.setInt(1, usuario.getUsuarioId());
        } else {
            this.statement.setNull(1, java.sql.Types.INTEGER);
        }

        Double total = this.carrito.getTotal();
        if (total != null) {
            this.statement.setDouble(2, total);
        } else {
            this.statement.setNull(2, java.sql.Types.DOUBLE);
        }

        PromocionDTO promocion = this.carrito.getPromocion();
        if (promocion != null && promocion.getPromocion_id() != null) {
            this.statement.setInt(3, promocion.getPromocion_id());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        UsuarioDTO usuario = this.carrito.getUsuario();
        if (usuario != null) {
            this.statement.setInt(1, usuario.getUsuarioId());
        } else {
            this.statement.setNull(1, java.sql.Types.INTEGER);
        }

        Double total = this.carrito.getTotal();
        if (total != null) {
            this.statement.setDouble(2, total);
        } else {
            this.statement.setNull(2, java.sql.Types.DOUBLE);
        }

        PromocionDTO promocion = this.carrito.getPromocion();
        if (promocion != null && promocion.getPromocion_id() != null) {
            this.statement.setInt(3, promocion.getPromocion_id());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }

        this.statement.setInt(4, this.carrito.getCarrito_id());
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
        this.carrito = new CarritoComprasDTO();
        this.carrito.setCarrito_id(this.resultSet.getInt("CARRITO_ID"));

        Double total = (Double) this.resultSet.getObject("TOTAL");
        if (total != null) {
            this.carrito.setTotal(total);
        } else {
            this.carrito.setTotal(null);
        }
        
        // Obtener usuario completo usando UsuarioDAO
        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuarioDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.carrito.setUsuario(usuario);

        // Obtener promoción completa usando PromocionDAO (si existe)
        Integer promocion_id = (Integer) this.resultSet.getObject("PROMOCION_ID");
        if (promocion_id != null) {
            PromocionDTO promocion = this.promocionDAO.obtenerPorId(promocion_id);
            this.carrito.setPromocion(promocion);
        } else {
            this.carrito.setPromocion(null);
        }

        // Cargar automáticamente los detalles del carrito
        ArrayList<DetalleCarritoDTO> detalles = this.detalleCarritoDAO.listarPorCarritoId(
                this.carrito.getCarrito_id()
        );
        this.carrito.setDetalles(detalles);
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

    @Override
    public Integer insertar(CarritoComprasDTO carrito) {
        this.carrito = carrito;
        return super.insertar();
    }

    @Override
    public CarritoComprasDTO obtenerPorId(Integer carritoId) {
        this.carrito = new CarritoComprasDTO();
        this.carrito.setCarrito_id(carritoId);
        super.obtenerPorId();
        return this.carrito;
    }

    @Override
    public ArrayList<CarritoComprasDTO> listarTodos() {
        return (ArrayList<CarritoComprasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(CarritoComprasDTO carrito) {
        this.carrito = carrito;
        return super.modificar();
    }

    @Override
    public Integer eliminar(CarritoComprasDTO carrito) {
        this.carrito = carrito;
        return super.eliminar();
    }
}
