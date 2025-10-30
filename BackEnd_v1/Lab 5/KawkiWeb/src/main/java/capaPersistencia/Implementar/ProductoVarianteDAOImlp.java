package capaPersistencia.Implementar;

import capaDominio.ProductoVarianteDTO;
import capaDominio.promocionDetalle.TipoBeneficio;
import capaDominio.productoDetalle.Color;
import capaDominio.productoDetalle.Talla;
import capaPersistencia.Implementar.util.Columna;
import capaPersistencia.ProductoVarianteDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoVarianteDAOImlp extends DAOImplBase implements ProductoVarianteDAO {

    private ProductoVarianteDTO prodVariante;

    public ProductoVarianteDAOImlp() {
        super("PRODUCTOS_VARIANTES");
        this.prodVariante = null;
        this.retornarLlavePrimaria = true;
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
        this.statement.setInt(6, this.prodVariante.getColor().getId());
        this.statement.setInt(7, this.prodVariante.getTalla().getId());

        TipoBeneficio tipo_beneficio = this.prodVariante.getTipo_beneficio();
        if (tipo_beneficio != null) {
            this.statement.setInt(8, tipo_beneficio.getId());
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
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.prodVariante.getSKU());
        this.statement.setInt(2, this.prodVariante.getStock());
        this.statement.setInt(3, this.prodVariante.getStock_minimo());
        this.statement.setInt(4, this.prodVariante.getAlerta_stock() ? 1 : 0);
        this.statement.setInt(5, this.prodVariante.getProducto_id());
        this.statement.setInt(6, this.prodVariante.getColor().getId());
        this.statement.setInt(7, this.prodVariante.getTalla().getId());

        TipoBeneficio tipo_beneficio = this.prodVariante.getTipo_beneficio();
        if (tipo_beneficio != null) {
            this.statement.setInt(8, tipo_beneficio.getId());
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
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.prodVariante.getProd_variante_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.prodVariante.getProd_variante_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.prodVariante = new ProductoVarianteDTO();
        this.prodVariante.setProd_variante_id(this.resultSet.getInt("PROD_VARIANTE_ID"));
        this.prodVariante.setSKU(this.resultSet.getString("SKU"));
        this.prodVariante.setStock(this.resultSet.getInt("STOCK"));
        this.prodVariante.setStock_minimo(this.resultSet.getInt("STOCK_MINIMO"));
        this.prodVariante.setAlerta_stock(this.resultSet.getInt("ALERTA_STOCK") == 1);
        this.prodVariante.setProducto_id(this.resultSet.getInt("PRODUCTO_ID"));
        Integer color_id = this.resultSet.getInt("COLOR_ID");
        this.prodVariante.setColor(Color.fromId(color_id));
        Integer talla_id = this.resultSet.getInt("TALLA_ID");
        this.prodVariante.setTalla(Talla.fromId(talla_id));

        Integer tipo_beneficio_id = (Integer) this.resultSet.getObject("TIPO_BENEFICIO_ID");
        if (tipo_beneficio_id != null) {
            this.prodVariante.setTipo_beneficio(TipoBeneficio.fromId(tipo_beneficio_id));
        } else {
            this.prodVariante.setTipo_beneficio(null);
        }

        Integer valor_beneficio = (Integer) this.resultSet.getObject("VALOR_BENEFICIO");
        this.prodVariante.setValor_beneficio(valor_beneficio); // puede ser null sin problemas

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
    public Integer insertar(ProductoVarianteDTO prodVariante) {
        this.prodVariante = prodVariante;
        return super.insertar();
    }

    @Override
    public ProductoVarianteDTO obtenerPorId(Integer prodVarianteId) {
        this.prodVariante = new ProductoVarianteDTO();
        this.prodVariante.setProd_variante_id(prodVarianteId);
        super.obtenerPorId();
        return this.prodVariante;
    }

    @Override
    public ArrayList<ProductoVarianteDTO> listarTodos() {
        return (ArrayList<ProductoVarianteDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(ProductoVarianteDTO prodVariante) {
        this.prodVariante = prodVariante;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ProductoVarianteDTO prodVariante) {
        this.prodVariante = prodVariante;
        return super.eliminar();
    }
}
