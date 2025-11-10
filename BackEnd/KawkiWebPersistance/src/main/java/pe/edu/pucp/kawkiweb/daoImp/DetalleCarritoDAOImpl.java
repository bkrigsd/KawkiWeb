package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.DetalleCarritoDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;

public class DetalleCarritoDAOImpl extends BaseDAOImpl implements DetalleCarritoDAO {

    private DetalleCarritoDTO detalle;
    private ProductoVarianteDAO productoVarianteDAO;

    public DetalleCarritoDAOImpl() {
        super("DETALLE_CARRITOS");
        this.detalle = null;
        this.retornarLlavePrimaria = true;
        this.productoVarianteDAO = new ProductoVarianteDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_CARRITO_ID", true, true));
        this.listaColumnas.add(new Columna("CARRITO_ID", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        Integer carrito_id = this.detalle.getCarrito_id();
        if (carrito_id != null) {
            this.statement.setInt(1, carrito_id);
        } else {
            this.statement.setNull(1, java.sql.Types.INTEGER);
        }

        this.statement.setInt(2, this.detalle.getCantidad());
        this.statement.setDouble(3, this.detalle.getPrecio_unitario());
        this.statement.setDouble(4, this.detalle.getSubtotal());

        ProductosVariantesDTO productVar = this.detalle.getProducto();
        if (productVar != null) {
            this.statement.setInt(5, productVar.getProd_variante_id());
        } else {
            this.statement.setNull(5, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        Integer carrito_id = this.detalle.getCarrito_id();
        if (carrito_id != null) {
            this.statement.setInt(1, carrito_id);
        } else {
            this.statement.setNull(1, java.sql.Types.INTEGER);
        }

        this.statement.setInt(2, this.detalle.getCantidad());
        this.statement.setDouble(3, this.detalle.getPrecio_unitario());
        this.statement.setDouble(4, this.detalle.getSubtotal());

        ProductosVariantesDTO productVar = this.detalle.getProducto();
        if (productVar != null) {
            this.statement.setInt(5, productVar.getProd_variante_id());
        } else {
            this.statement.setNull(5, java.sql.Types.INTEGER);
        }

        this.statement.setInt(6, this.detalle.getDetalle_carrito_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalle.getDetalle_carrito_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalle.getDetalle_carrito_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalle = new DetalleCarritoDTO();
        this.detalle.setDetalle_carrito_id(this.resultSet.getInt("DETALLE_CARRITO_ID"));
        this.detalle.setCarrito_id(this.resultSet.getInt("CARRITO_ID"));
        this.detalle.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalle.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detalle.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));

        // Obtener producto variante completo usando ProductoVarianteDAO
        Integer prod_variante_id = this.resultSet.getInt("PROD_VARIANTE_ID");
        ProductosVariantesDTO productVar = this.productoVarianteDAO.obtenerPorId(prod_variante_id);
        this.detalle.setProducto(productVar);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalle = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalle);
    }

    @Override
    public Integer insertar(DetalleCarritoDTO detalleCarrito) {
        this.detalle = detalleCarrito;
        return super.insertar();
    }

    @Override
    public DetalleCarritoDTO obtenerPorId(Integer detalleCarritoId) {
        this.detalle = new DetalleCarritoDTO();
        this.detalle.setDetalle_carrito_id(detalleCarritoId);
        super.obtenerPorId();
        return this.detalle;
    }

    @Override
    public ArrayList<DetalleCarritoDTO> listarTodos() {
        return (ArrayList<DetalleCarritoDTO>) super.listarTodos();
    }

    @Override
    public ArrayList<DetalleCarritoDTO> listarPorCarritoId(Integer carritoId) {
        String sql = "SELECT DETALLE_CARRITO_ID, CARRITO_ID, CANTIDAD, "
                + "PRECIO_UNITARIO, SUBTOTAL, PROD_VARIANTE_ID "
                + "FROM DETALLE_CARRITOS "
                + "WHERE CARRITO_ID = ?";

        return (ArrayList<DetalleCarritoDTO>) super.listarTodos(
                sql,
                (params) -> {
                    try {
                        this.statement.setInt(1, carritoId);
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetro carritoId: " + ex);
                    }
                },
                null
        );
    }

    @Override
    public Integer modificar(DetalleCarritoDTO detalleCarrito) {
        this.detalle = detalleCarrito;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleCarritoDTO detalleCarrito) {
        this.detalle = detalleCarrito;
        return super.eliminar();
    }
}
