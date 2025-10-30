package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.CategoriaDAO;
import pe.edu.pucp.kawkiweb.dao.EstiloDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.ProductoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriaDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstiloDTO;

public class ProductoDAOImpl extends BaseDAOImpl implements ProductoDAO {

    private ProductoDTO producto;
    private CategoriaDAO categoriaDAO;
    private EstiloDAO estiloDAO;
    private ProductoVarianteDAO productoVarianteDAO;

    public ProductoDAOImpl() {
        super("PRODUCTOS");
        this.producto = null;
        this.retornarLlavePrimaria = true;
        this.categoriaDAO = new CategoriaDAOImpl();
        this.estiloDAO = new EstiloDAOImpl();
        this.productoVarianteDAO = new ProductoVarianteDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("CATEGORIA_ID", false, false));
        this.listaColumnas.add(new Columna("ESTILO_ID", false, false));
        this.listaColumnas.add(new Columna("PRECIO_VENTA", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.producto.getDescripcion());
        this.statement.setInt(2, this.producto.getCategoria().getCategoria_id());
        this.statement.setInt(3, this.producto.getEstilo().getEstilo_id());
        this.statement.setDouble(4, this.producto.getPrecio_venta());
        LocalDateTime fecha = this.producto.getFecha_hora_creacion();
        if (fecha != null) {
            fecha = fecha.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(5, java.sql.Timestamp.valueOf(fecha));
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.producto.getDescripcion());
        this.statement.setInt(2, this.producto.getCategoria().getCategoria_id());
        this.statement.setInt(3, this.producto.getEstilo().getEstilo_id());
        this.statement.setDouble(4, this.producto.getPrecio_venta());
        this.statement.setTimestamp(5, java.sql.Timestamp.valueOf(this.producto.getFecha_hora_creacion()));
        this.statement.setInt(6, this.producto.getProducto_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.producto.getProducto_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.producto.getProducto_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.producto = new ProductoDTO();
        this.producto.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));
        this.producto.setDescripcion(this.resultSet.getString("DESCRIPCION"));

        Integer categoria_id = this.resultSet.getInt("CATEGORIA_ID");
        CategoriaDTO categoria = this.categoriaDAO.obtenerPorId(categoria_id);
        this.producto.setCategoria(categoria);

        Integer estilo_id = this.resultSet.getInt("ESTILO_ID");
        EstiloDTO estilo = this.estiloDAO.obtenerPorId(estilo_id);
        this.producto.setEstilo(estilo);

        this.producto.setPrecio_venta(this.resultSet.getDouble("PRECIO_VENTA"));
        this.producto.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );

        // Cargar variantes
        ArrayList<ProductoVarianteDTO> variantes
                = this.productoVarianteDAO.listarPorProductoId(this.producto.getProducto_id());
        this.producto.setVariantes(variantes);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.producto = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.producto);
    }

    @Override
    public Integer insertar(ProductoDTO producto) {
        this.producto = producto;
        return super.insertar();
    }

    @Override
    public ProductoDTO obtenerPorId(Integer productoId) {
        this.producto = new ProductoDTO();
        this.producto.setProducto_id(productoId);
        super.obtenerPorId();
        return this.producto;
    }

    @Override
    public ArrayList<ProductoDTO> listarTodos() {
        return (ArrayList<ProductoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(ProductoDTO producto) {
        this.producto = producto;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ProductoDTO producto) {
        this.producto = producto;
        return super.eliminar();
    }
}
