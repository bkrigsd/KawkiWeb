package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.dao.ColoresDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;
import pe.edu.pucp.kawkiweb.dao.TallasDAO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public class ProductosVariantesDAOImpl extends BaseDAOImpl implements ProductosVariantesDAO {

    private ProductosVariantesDTO prodVariante;
    private ColoresDAO colorDAO;
    private TallasDAO tallaDAO;
    private UsuariosDAO usuarioDAO;

    public ProductosVariantesDAOImpl() {
        super("PRODUCTOS_VARIANTES");
        this.prodVariante = null;
        this.retornarLlavePrimaria = true;
        this.colorDAO = new ColoresDAOImpl();
        this.tallaDAO = new TallasDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", true, true));
        this.listaColumnas.add(new Columna("SKU", false, false));
        this.listaColumnas.add(new Columna("STOCK", false, false));
        this.listaColumnas.add(new Columna("STOCK_MINIMO", false, false));
        this.listaColumnas.add(new Columna("ALERTA_STOCK", false, false));
        this.listaColumnas.add(new Columna("PRODUCTO_ID", false, false));
        this.listaColumnas.add(new Columna("COLOR_ID", false, false));
        this.listaColumnas.add(new Columna("TALLA_ID", false, false));
        this.listaColumnas.add(new Columna("URL_IMAGEN", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false, false));
        this.listaColumnas.add(new Columna("DISPONIBLE", false, false));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.prodVariante.getSKU());
        this.statement.setInt(2, this.prodVariante.getStock());
        this.statement.setInt(3, this.prodVariante.getStock_minimo());
        if (this.prodVariante.getAlerta_stock() != null) {
            this.statement.setInt(4, this.prodVariante.getAlerta_stock() ? 1 : 0);
        } else {
            this.statement.setNull(4, java.sql.Types.TINYINT);
        }
        this.statement.setInt(5, this.prodVariante.getProducto_id());
        this.statement.setInt(6, this.prodVariante.getColor().getColor_id());
        this.statement.setInt(7, this.prodVariante.getTalla().getTalla_id());
        if (this.prodVariante.getUrl_imagen() != null) {
            this.statement.setString(8, this.prodVariante.getUrl_imagen());
        } else {
            this.statement.setNull(8, java.sql.Types.VARCHAR);
        }
        this.statement.setTimestamp(9, java.sql.Timestamp.valueOf(this.prodVariante.getFecha_hora_creacion()));
        this.statement.setInt(10, this.prodVariante.getDisponible() ? 1 : 0);
        this.statement.setInt(11, this.prodVariante.getUsuario().getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.prodVariante.getProd_variante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.prodVariante.getProd_variante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.prodVariante.getSKU());
        this.statement.setInt(2, this.prodVariante.getStock());
        this.statement.setInt(3, this.prodVariante.getStock_minimo());
        if (this.prodVariante.getAlerta_stock() != null) {
            this.statement.setInt(4, this.prodVariante.getAlerta_stock() ? 1 : 0);
        } else {
            this.statement.setNull(4, java.sql.Types.TINYINT);
        }
        this.statement.setInt(5, this.prodVariante.getProducto_id());
        this.statement.setInt(6, this.prodVariante.getColor().getColor_id());
        this.statement.setInt(7, this.prodVariante.getTalla().getTalla_id());
        if (this.prodVariante.getUrl_imagen() != null) {
            this.statement.setString(8, this.prodVariante.getUrl_imagen());
        } else {
            this.statement.setNull(8, java.sql.Types.VARCHAR);
        }
        this.statement.setInt(9, this.prodVariante.getDisponible() ? 1 : 0);
        this.statement.setInt(10, this.prodVariante.getUsuario().getUsuarioId());
        this.statement.setInt(11, this.prodVariante.getProd_variante_id());
    }

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UNA
     * SOLA variante de producto.
     */
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.prodVariante = new ProductosVariantesDTO();
        this.prodVariante.setProd_variante_id(this.resultSet.getInt("PROD_VARIANTE_ID"));
        this.prodVariante.setSKU(this.resultSet.getString("SKU"));
        this.prodVariante.setStock(this.resultSet.getInt("STOCK"));
        this.prodVariante.setStock_minimo(this.resultSet.getInt("STOCK_MINIMO"));

        Boolean alertaStock = (Boolean) this.resultSet.getObject("ALERTA_STOCK");
        this.prodVariante.setAlerta_stock(alertaStock);

        this.prodVariante.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));

        Integer color_id = this.resultSet.getInt("COLOR_ID");
        ColoresDTO color = this.colorDAO.obtenerPorId(color_id);
        this.prodVariante.setColor(color);

        Integer talla_id = this.resultSet.getInt("TALLA_ID");
        TallasDTO talla = this.tallaDAO.obtenerPorId(talla_id);
        this.prodVariante.setTalla(talla);

        this.prodVariante.setUrl_imagen((String) this.resultSet.getObject("URL_IMAGEN"));

        this.prodVariante.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());

        Boolean disponible = (Boolean) this.resultSet.getObject("DISPONIBLE");
        this.prodVariante.setDisponible(disponible);

        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.prodVariante.setUsuario(usuario);
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHAS variantes de producto.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.prodVariante = new ProductosVariantesDTO();

        // ========== DATOS BÁSICOS DE LA VARIANTE ==========
        this.prodVariante.setProd_variante_id(this.resultSet.getInt("PROD_VARIANTE_ID"));
        this.prodVariante.setSKU(this.resultSet.getString("SKU"));
        this.prodVariante.setStock(this.resultSet.getInt("STOCK"));
        this.prodVariante.setStock_minimo(this.resultSet.getInt("STOCK_MINIMO"));

        Boolean alertaStock = (Boolean) this.resultSet.getObject("ALERTA_STOCK");
        this.prodVariante.setAlerta_stock(alertaStock);

        this.prodVariante.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));
        this.prodVariante.setUrl_imagen((String) this.resultSet.getObject("URL_IMAGEN"));
        this.prodVariante.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );

        Boolean disponible = (Boolean) this.resultSet.getObject("DISPONIBLE");
        this.prodVariante.setDisponible(disponible);

        // ========== COLOR (COMPLETO - desde JOIN) ==========
        ColoresDTO color = new ColoresDTO();
        color.setColor_id(this.resultSet.getInt("COLOR_ID"));
        color.setNombre(this.resultSet.getString("COLOR_NOMBRE"));
        this.prodVariante.setColor(color);

        // ========== TALLA (COMPLETA - desde JOIN) ==========
        TallasDTO talla = new TallasDTO();
        talla.setTalla_id(this.resultSet.getInt("TALLA_ID"));
        talla.setNumero(this.resultSet.getInt("TALLA_NUMERO"));
        this.prodVariante.setTalla(talla);

        // ========== USUARIO DE LA VARIANTE (COMPLETO - desde JOIN) ==========
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombre(this.resultSet.getString("USUARIO_NOMBRE"));
        usuario.setApePaterno(this.resultSet.getString("USUARIO_APE_PATERNO"));
        this.prodVariante.setUsuario(usuario);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.prodVariante = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.prodVariante);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
        lista.add(this.prodVariante);
    }

    @Override
    public Integer insertar(ProductosVariantesDTO prodVariante) {
        this.prodVariante = prodVariante;
        return super.insertar();
    }

    @Override
    public ProductosVariantesDTO obtenerPorId(Integer prodVarianteId) {
        this.prodVariante = new ProductosVariantesDTO();
        this.prodVariante.setProd_variante_id(prodVarianteId);
        super.obtenerPorId();
        return this.prodVariante;
    }

    /**
     * Lista todas las variantes de productos usando el Stored Procedure
     * optimizado. Retorna variantes con color completo, talla completa, usuario
     * (id, nombre, ape_paterno), y producto (id, descripcion, categoria
     * completa, estilo completo, precio_venta, usuario_id).
     */
    @Override
    public ArrayList<ProductosVariantesDTO> listarTodos() {
        return (ArrayList<ProductosVariantesDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_PRODUCTOS_VARIANTES_COMPLETO"
        );
    }

    @Override
    public Integer modificar(ProductosVariantesDTO prodVariante) {
        this.prodVariante = prodVariante;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ProductosVariantesDTO prodVariante) {
        this.prodVariante = prodVariante;
        return super.eliminar();
    }

    ///////////////////////////////////////////////////////////////////////////
    ////////////////////////////BÚSQUEDAS AVANZADAS////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /*
    * Este método usa un stored procedure para obtener todas las variantes
    * (combinaciones de color y talla) de un producto específico.
    * - Se llama automáticamente desde ProductosDAOImpl.instanciarObjetoDelResultSet()
    * - Carga la lista de variantes disponibles en cada ProductosDTO
     */
    @Override
    public ArrayList<ProductosVariantesDTO> listarPorProductoId(Integer productoId) {

        // Consumer para setear el parámetro de entrada
        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException ex) {
                System.err.println("Error al setear parámetro productoId: " + ex);
            }
        };

        // Ejecuta el procedimiento almacenado que retorna múltiples registros
        return (ArrayList<ProductosVariantesDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_VARIANTES_POR_PRODUCTO",
                1, // Cantidad de parámetros (solo productoId)
                incluirParametros,
                productoId
        );
    }

    // En ProductosVariantesDAOImpl.java
    /**
     * Lista variantes filtradas por color usando SP optimizado
     */
    @Override
    public ArrayList<ProductosVariantesDTO> listarPorColor(Integer colorId) {
        return (ArrayList<ProductosVariantesDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_VARIANTES_POR_COLOR",
                1,
                (params) -> {
                    try {
                        this.statement.setInt(1, (Integer) params);
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetro colorId: " + ex);
                    }
                },
                colorId
        );
    }

    /**
     * Lista variantes filtradas por talla usando SP optimizado
     */
    @Override
    public ArrayList<ProductosVariantesDTO> listarPorTalla(Integer tallaId) {
        return (ArrayList<ProductosVariantesDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_VARIANTES_POR_TALLA",
                1,
                (params) -> {
                    try {
                        this.statement.setInt(1, (Integer) params);
                    } catch (SQLException ex) {
                        System.err.println("Error al establecer parámetro tallaId: " + ex);
                    }
                },
                tallaId
        );
    }

    /**
     * Lista variantes con stock bajo usando SP optimizado
     */
    @Override
    public ArrayList<ProductosVariantesDTO> listarConStockBajo() {
        return (ArrayList<ProductosVariantesDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_VARIANTES_STOCK_BAJO",
                0, // Sin parámetros
                null, // Sin consumer de parámetros
                null // Sin objeto de parámetros
        );
    }

    /**
     * Verifica si existe una variante con la combinación producto-color-talla
     * usando Stored Procedure
     */
    @Override
    public boolean existeVariante(Integer productoId, Integer colorId, Integer tallaId) {
        return super.ejecutarSPConOutBoolean(
                "SP_EXISTE_VARIANTE",
                3,
                (params) -> {
                    try {
                        Integer[] p = (Integer[]) params;
                        this.statement.setInt(1, p[0]);
                        this.statement.setInt(2, p[1]);
                        this.statement.setInt(3, p[2]);
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetros: " + ex);
                    }
                },
                new Integer[]{productoId, colorId, tallaId},
                false
        );
    }

    /**
     * Verifica si existe otra variante con la misma combinación
     * producto-color-talla EXCLUYENDO la variante que se está modificando
     */
    @Override
    public boolean existeVarianteParaModificar(Integer varianteId, 
            Integer productoId, Integer colorId, Integer tallaId) {
        return super.ejecutarSPConOutBoolean(
                "SP_EXISTE_VARIANTE_PARA_MODIFICAR",
                4,
                (params) -> {
                    try {
                        Integer[] p = (Integer[]) params;
                        this.statement.setInt(1, p[0]);  // varianteId
                        this.statement.setInt(2, p[1]);  // productoId
                        this.statement.setInt(3, p[2]);  // colorId
                        this.statement.setInt(4, p[3]);  // tallaId
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetros: " + ex);
                    }
                },
                new Integer[]{varianteId, productoId, colorId, tallaId},
                false
        );
    }
}
