package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
import pe.edu.pucp.kawkiweb.dao.CategoriasDAO;
import pe.edu.pucp.kawkiweb.dao.EstilosDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public class ProductosDAOImpl extends BaseDAOImpl implements ProductosDAO {

    private ProductosDTO producto;
    private CategoriasDAO categoriaDAO;
    private EstilosDAO estiloDAO;
    private ProductosVariantesDAO productoVarianteDAO;
    private UsuariosDAO usuarioDAO;

    public ProductosDAOImpl() {
        super("PRODUCTOS");
        this.producto = null;
        this.retornarLlavePrimaria = true;
        this.categoriaDAO = new CategoriasDAOImpl();
        this.estiloDAO = new EstilosDAOImpl();
        this.productoVarianteDAO = new ProductosVariantesDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("CATEGORIA_ID", false, false));
        this.listaColumnas.add(new Columna("ESTILO_ID", false, false));
        this.listaColumnas.add(new Columna("PRECIO_VENTA", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false, false));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
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
        this.statement.setInt(6, this.producto.getUsuario().getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.producto.getDescripcion());
        this.statement.setInt(2, this.producto.getCategoria().getCategoria_id());
        this.statement.setInt(3, this.producto.getEstilo().getEstilo_id());
        this.statement.setDouble(4, this.producto.getPrecio_venta());
        this.statement.setInt(5, this.producto.getUsuario().getUsuarioId());
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

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UN
     * SOLO producto.
     */
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.producto = new ProductosDTO();
        this.producto.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));
        this.producto.setDescripcion(this.resultSet.getString("DESCRIPCION"));

        Integer categoria_id = this.resultSet.getInt("CATEGORIA_ID");
        CategoriasDTO categoria = this.categoriaDAO.obtenerPorId(categoria_id);
        this.producto.setCategoria(categoria);

        Integer estilo_id = this.resultSet.getInt("ESTILO_ID");
        EstilosDTO estilo = this.estiloDAO.obtenerPorId(estilo_id);
        this.producto.setEstilo(estilo);

        this.producto.setPrecio_venta(this.resultSet.getDouble("PRECIO_VENTA"));
        this.producto.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );

        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.producto.setUsuario(usuario);

        // Cargar variantes
//        ArrayList<ProductosVariantesDTO> variantes
//                = this.productoVarianteDAO.listarPorProductoId(this.producto.getProducto_id());
//        this.producto.setVariantes(variantes);
        this.producto.setVariantes(new ArrayList<>());
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHOS productos.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.producto = new ProductosDTO();
        this.producto.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));
        this.producto.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.producto.setPrecio_venta(this.resultSet.getDouble("PRECIO_VENTA"));
        this.producto.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );

        // Categoría (YA VIENE COMPLETA del JOIN - SIN query adicional)
        CategoriasDTO categoria = new CategoriasDTO();
        categoria.setCategoria_id(this.resultSet.getInt("CATEGORIA_ID"));
        categoria.setNombre(this.resultSet.getString("CATEGORIA_NOMBRE"));
        this.producto.setCategoria(categoria);

        // Estilo (YA VIENE COMPLETO del JOIN - SIN query adicional)
        EstilosDTO estilo = new EstilosDTO();
        estilo.setEstilo_id(this.resultSet.getInt("ESTILO_ID"));
        estilo.setNombre(this.resultSet.getString("ESTILO_NOMBRE"));
        this.producto.setEstilo(estilo);

        // Usuario (YA VIENE COMPLETO del JOIN - SIN query adicional)
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombre(this.resultSet.getString("USUARIO_NOMBRE"));
        usuario.setApePaterno(this.resultSet.getString("USUARIO_APE_PATERNO"));
        this.producto.setUsuario(usuario);

        this.producto.setVariantes(new ArrayList<>());
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.producto = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.producto);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
        lista.add(this.producto);
    }

    @Override
    public Integer insertar(ProductosDTO producto) {
        this.producto = producto;
        return super.insertar();
    }

    @Override
    public ProductosDTO obtenerPorId(Integer productoId) {
        this.producto = new ProductosDTO();
        this.producto.setProducto_id(productoId);
        super.obtenerPorId();
        return this.producto;
    }

    /**
     * Lista todos los productos usando el Stored Procedure optimizado. Retorna
     * productos con categoría, estilo y usuario completos, pero SIN variantes
     * (para evitar lentitud).
     */
    @Override
    public ArrayList<ProductosDTO> listarTodos() {
        return (ArrayList<ProductosDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_PRODUCTOS_COMPLETO"
        );
    }

    @Override
    public Integer modificar(ProductosDTO producto) {
        this.producto = producto;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ProductosDTO producto) {
        this.producto = producto;
        return super.eliminar();
    }

    // ========== IMPLEMENTACIÓN DE MÉTODOS AVANZADOS ==========
    @Override
    public Boolean tieneStockDisponible(Integer productoId) {
        Boolean tieneStock = false;

        try {
            this.abrirConexion();

            // Llamada al stored procedure
            String sql = "{CALL SP_VERIFICAR_STOCK_DISPONIBLE(?, ?)}";
            this.colocarSQLEnStatement(sql);
            this.statement.setInt(1, productoId);
            this.statement.registerOutParameter(2, Types.TINYINT);
            this.statement.execute();

            // Obtener el resultado (1 = tiene stock, 0 = no tiene stock)
            int resultado = this.statement.getInt(2);
            tieneStock = (resultado == 1);

        } catch (SQLException ex) {
            System.err.println("Error al verificar stock disponible: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }

        return tieneStock;
    }

    @Override
    public Integer calcularStockTotal(Integer productoId) {
        Integer stockTotal = 0;

        try {
            this.abrirConexion();

            // Llamada al stored procedure
            String sql = "{CALL SP_CALCULAR_STOCK_TOTAL(?, ?)}";
            this.colocarSQLEnStatement(sql);
            this.statement.setInt(1, productoId);
            this.statement.registerOutParameter(2, Types.INTEGER);
            this.statement.execute();

            // Obtener el stock total
            stockTotal = this.statement.getInt(2);

        } catch (SQLException ex) {
            System.err.println("Error al calcular stock total: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }

        return stockTotal;
    }

    @Override
    public ArrayList<ProductosDTO> listarPorCategoria(Integer categoriaId) {
        // SQL directo con WHERE para filtrar por categoría
        String sql = "SELECT PRODUCTO_ID, DESCRIPCION, CATEGORIA_ID, ESTILO_ID, "
                + "PRECIO_VENTA, FECHA_HORA_CREACION, USUARIO_ID "
                + "FROM PRODUCTOS WHERE CATEGORIA_ID = ?";

        // Consumer para setear el parámetro
        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException e) {
                System.err.println("Error al establecer parámetro categoría: " + e);
            }
        };

        return (ArrayList<ProductosDTO>) super.listarTodos(sql, incluirParametros, categoriaId);
    }

    @Override
    public ArrayList<ProductosDTO> listarPorEstilo(Integer estiloId) {
        // SQL directo con WHERE para filtrar por estilo
        String sql = "SELECT PRODUCTO_ID, DESCRIPCION, CATEGORIA_ID, ESTILO_ID, "
                + "PRECIO_VENTA, FECHA_HORA_CREACION, USUARIO_ID "
                + "FROM PRODUCTOS WHERE ESTILO_ID = ?";

        // Consumer para setear el parámetro
        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException e) {
                System.err.println("Error al establecer parámetro estilo: " + e);
            }
        };

        return (ArrayList<ProductosDTO>) super.listarTodos(sql, incluirParametros, estiloId);
    }

    @Override
    public ArrayList<ProductosDTO> listarConStockBajo() {
        // Usar el método de BaseDAOImpl para ejecutar SP que retorna lista
        return (ArrayList<ProductosDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_PRODUCTOS_STOCK_BAJO",
                0, // Sin parámetros
                null,
                null
        );
    }
}
