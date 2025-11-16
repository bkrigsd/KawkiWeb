package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.prodVariante = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
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

    @Override
    public ArrayList<ProductosVariantesDTO> listarTodos() {
        return (ArrayList<ProductosVariantesDTO>) super.listarTodos();
    }

    @Override
    public ArrayList<ProductosVariantesDTO> listarPorProductoId(Integer productoId) {
        String sql = "SELECT PROD_VARIANTE_ID, SKU, STOCK, STOCK_MINIMO, "
                + "ALERTA_STOCK, PRODUCTO_ID, COLOR_ID, TALLA_ID, "
                + "URL_IMAGEN, FECHA_HORA_CREACION, DISPONIBLE"
                + "FROM PRODUCTOS_VARIANTES "
                + "WHERE PRODUCTO_ID = ?";

        return (ArrayList<ProductosVariantesDTO>) super.listarTodos(
                sql,
                (params) -> {
                    try {
                        this.statement.setInt(1, productoId);
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetro productoId: " + ex);
                    }
                },
                null
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

}
