package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.dao.DetalleVentasDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

public class DetalleVentasDAOImpl extends BaseDAOImpl implements DetalleVentasDAO {

    private DetalleVentasDTO detalleVenta;
    private ProductosVariantesDAO productoVarianteDAO;

    public DetalleVentasDAOImpl() {
        super("DETALLE_VENTAS");
        this.detalleVenta = null;
        this.retornarLlavePrimaria = true;
        this.productoVarianteDAO = new ProductosVariantesDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_VENTA_ID", true, true));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("VENTA_ID", false, false));
        this.listaColumnas.add(new Columna("PROD_VARIANTE_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getCantidad());
        this.statement.setDouble(2, this.detalleVenta.getPrecio_unitario());
        this.statement.setDouble(3, this.detalleVenta.getSubtotal());
        this.statement.setInt(4, this.detalleVenta.getVenta_id());
        this.statement.setInt(5, this.detalleVenta.getProdVariante().getProd_variante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getCantidad());
        this.statement.setDouble(2, this.detalleVenta.getPrecio_unitario());
        this.statement.setDouble(3, this.detalleVenta.getSubtotal());
        this.statement.setInt(4, this.detalleVenta.getVenta_id());
        this.statement.setInt(5, this.detalleVenta.getProdVariante().getProd_variante_id());
        this.statement.setInt(6, this.detalleVenta.getDetalle_venta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getDetalle_venta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleVenta.getDetalle_venta_id());
    }

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UN
     * SOLO detalle de venta.
     */
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleVenta = new DetalleVentasDTO();
        this.detalleVenta.setDetalle_venta_id(this.resultSet.getInt("DETALLE_VENTA_ID"));
        this.detalleVenta.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalleVenta.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detalleVenta.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.detalleVenta.setVenta_id(this.resultSet.getInt("VENTA_ID"));

        // Obtener ProductoVariante completo usando DAO
        Integer prod_variante_id = this.resultSet.getInt("PROD_VARIANTE_ID");
        ProductosVariantesDTO productoVar = this.productoVarianteDAO.obtenerPorId(prod_variante_id);
        this.detalleVenta.setProdVariante(productoVar);
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHOS detalles de venta.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.detalleVenta = new DetalleVentasDTO();

        // Datos básicos del detalle de venta
        this.detalleVenta.setDetalle_venta_id(this.resultSet.getInt("DETALLE_VENTA_ID"));
        this.detalleVenta.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalleVenta.setPrecio_unitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detalleVenta.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.detalleVenta.setVenta_id(this.resultSet.getInt("VENTA_ID"));

        // Producto Variante (YA VIENE COMPLETO del JOIN - SIN query adicional)
        ProductosVariantesDTO productoVariante = new ProductosVariantesDTO();
        productoVariante.setProd_variante_id(this.resultSet.getInt("PROD_VARIANTE_ID"));
        productoVariante.setSKU(this.resultSet.getString("SKU"));
        productoVariante.setStock(this.resultSet.getInt("STOCK"));

        // Color del producto variante (YA VIENE del JOIN)
        ColoresDTO color = new ColoresDTO();
        color.setColor_id(this.resultSet.getInt("COLOR_ID"));
        color.setNombre(this.resultSet.getString("COLOR_NOMBRE"));
        productoVariante.setColor(color);

        // Talla del producto variante (YA VIENE del JOIN)
        TallasDTO talla = new TallasDTO();
        talla.setTalla_id(this.resultSet.getInt("TALLA_ID"));
        talla.setNumero(this.resultSet.getInt("TALLA_NUMERO"));
        productoVariante.setTalla(talla);

        // Usuario del producto variante (YA VIENE del JOIN) - CON NOMBRE Y APELLIDO
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombre(this.resultSet.getString("USUARIO_NOMBRE"));
        usuario.setApePaterno(this.resultSet.getString("USUARIO_APE_PATERNO"));
        productoVariante.setUsuario(usuario);

        this.detalleVenta.setProdVariante(productoVariante);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleVenta = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleVenta);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
        lista.add(this.detalleVenta);
    }

    @Override
    public Integer insertar(DetalleVentasDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
        return super.insertar();
    }

    @Override
    public DetalleVentasDTO obtenerPorId(Integer detalleVentaId) {
        this.detalleVenta = new DetalleVentasDTO();
        this.detalleVenta.setDetalle_venta_id(detalleVentaId);
        super.obtenerPorId();
        return this.detalleVenta;
    }

    /**
     * Lista todos los detalles de venta usando el Stored Procedure optimizado.
     * Retorna detalles con producto_variante (color, talla, usuario) completo.
     */
    @Override
    public ArrayList<DetalleVentasDTO> listarTodos() {
        return (ArrayList<DetalleVentasDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_DETALLE_VENTAS_COMPLETO"
        );
    }

    @Override
    public Integer modificar(DetalleVentasDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleVentasDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
        return super.eliminar();
    }

    // BÚSQUEDAS AVANZADAS
    /**
     * Lista detalles de venta por venta_id usando SP optimizado. El SP trae
     * todos los datos con JOINs, sin necesidad de queries adicionales. Se llama
     * automáticamente desde VentasDAOImpl.instanciarObjetoDelResultSet() para
     * cargar la lista de productos vendidos en cada VentasDTO.
     */
    @Override
    public ArrayList<DetalleVentasDTO> listarPorVentaId(Integer ventaId) {
        List lista = new ArrayList<>();

        try {
            this.abrirConexion();

            String sql = "{CALL SP_LISTAR_DETALLES_POR_VENTA(?)}";
            this.colocarSQLEnStatement(sql);
            this.statement.setInt(1, ventaId);
            this.ejecutarSelectEnDB();

            while (this.resultSet.next()) {
                // Usar el método optimizado que NO hace queries adicionales
                this.agregarObjetoALaListaDesdeJoin(lista);
            }

        } catch (SQLException ex) {
            System.err.println("Error al listar detalles por venta: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }

        return (ArrayList<DetalleVentasDTO>) lista;
    }
}
