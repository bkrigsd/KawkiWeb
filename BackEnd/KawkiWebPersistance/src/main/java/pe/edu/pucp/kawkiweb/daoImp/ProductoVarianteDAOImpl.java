package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.ColorDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;
import pe.edu.pucp.kawkiweb.dao.TallaDAO;
import pe.edu.pucp.kawkiweb.dao.TipoBeneficioDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;

public class ProductoVarianteDAOImpl extends BaseDAOImpl implements ProductoVarianteDAO {

    private ProductosVariantesDTO prodVariante;
    private ColorDAO colorDAO;
    private TallaDAO tallaDAO;
    private TipoBeneficioDAO tipoBeneficioDAO;

    public ProductoVarianteDAOImpl() {
        super("PRODUCTOS_VARIANTES");
        this.prodVariante = null;
        this.retornarLlavePrimaria = true;
        this.colorDAO = new ColorDAOImpl();
        this.tallaDAO = new TallaDAOImpl();
        this.tipoBeneficioDAO = new TipoBeneficioDAOImpl();
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
        this.listaColumnas.add(new Columna("TIPO_BENEFICIO_ID", false, false));
        this.listaColumnas.add(new Columna("VALOR_BENEFICIO", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.prodVariante.getSKU());
        this.statement.setInt(2, this.prodVariante.getStock());
        this.statement.setInt(3, this.prodVariante.getStock_minimo());
        this.statement.setInt(4, this.prodVariante.getAlerta_stock() ? 1 : 0);
        this.statement.setInt(5, this.prodVariante.getProducto_id());
        this.statement.setInt(6, this.prodVariante.getColor().getColor_id());
        this.statement.setInt(7, this.prodVariante.getTalla().getTalla_id());

        TiposBeneficioDTO tipo_beneficio = this.prodVariante.getTipo_beneficio();
        if (tipo_beneficio != null) {
            this.statement.setInt(8, tipo_beneficio.getTipo_beneficio_id());
        } else {
            this.statement.setNull(8, java.sql.Types.INTEGER);
        }

        Integer valor_beneficio = this.prodVariante.getValor_beneficio();
        if (valor_beneficio != null) {
            this.statement.setInt(9, valor_beneficio);
        } else {
            this.statement.setNull(9, java.sql.Types.INTEGER);
        }

        this.statement.setTimestamp(10, java.sql.Timestamp.valueOf(this.prodVariante.getFecha_hora_creacion()));
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
        this.statement.setInt(4, this.prodVariante.getAlerta_stock() ? 1 : 0);
        this.statement.setInt(5, this.prodVariante.getProducto_id());
        this.statement.setInt(6, this.prodVariante.getColor().getColor_id());
        this.statement.setInt(7, this.prodVariante.getTalla().getTalla_id());

        TiposBeneficioDTO tipo_beneficio = this.prodVariante.getTipo_beneficio();
        if (tipo_beneficio != null) {
            this.statement.setInt(8, tipo_beneficio.getTipo_beneficio_id());
        } else {
            this.statement.setNull(8, java.sql.Types.INTEGER);
        }

        Integer valor_beneficio = this.prodVariante.getValor_beneficio();
        if (valor_beneficio != null) {
            this.statement.setInt(9, valor_beneficio);
        } else {
            this.statement.setNull(9, java.sql.Types.INTEGER);
        }

        this.statement.setTimestamp(10, java.sql.Timestamp.valueOf(this.prodVariante.getFecha_hora_creacion()));
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
        this.prodVariante.setAlerta_stock(alertaStock != null ? alertaStock : false);

        this.prodVariante.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));

        Integer color_id = this.resultSet.getInt("COLOR_ID");
        ColoresDTO color = this.colorDAO.obtenerPorId(color_id);
        this.prodVariante.setColor(color);

        Integer talla_id = this.resultSet.getInt("TALLA_ID");
        TallasDTO talla = this.tallaDAO.obtenerPorId(talla_id);
        this.prodVariante.setTalla(talla);

        Integer tipo_beneficio_id = (Integer) this.resultSet.getObject("TIPO_BENEFICIO_ID");
        if (tipo_beneficio_id != null) {
            TiposBeneficioDTO tipoBeneficio = this.tipoBeneficioDAO.obtenerPorId(tipo_beneficio_id);
            this.prodVariante.setTipo_beneficio(tipoBeneficio);
        } else {
            this.prodVariante.setTipo_beneficio(null);
        }

        Integer valor_beneficio = (Integer) this.resultSet.getObject("VALOR_BENEFICIO");
        if (valor_beneficio != null) {
            this.prodVariante.setValor_beneficio(valor_beneficio);
        } else {
            this.prodVariante.setValor_beneficio(null);
        }

        this.prodVariante.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());
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
                + "TIPO_BENEFICIO_ID, VALOR_BENEFICIO, FECHA_HORA_CREACION "
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
