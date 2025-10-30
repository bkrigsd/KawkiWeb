package capaPersistencia.Implementar;

import capaDominio.Comprobante_PagoDTO;
import capaDominio.comprobanteDetalle.TipoComprobante;
import capaPersistencia.Comprobante_PagoDAO;
import capaPersistencia.Implementar.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComprobantePagoDAOImpl extends DAOImplBase implements Comprobante_PagoDAO {

    private Comprobante_PagoDTO comprobante;

    public ComprobantePagoDAOImpl() {
        super("COMPROBANTE_PAGOS");
        this.comprobante = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("COMPROBANTE_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
        this.listaColumnas.add(new Columna("TIPO_COMPROBANTE_ID", false, false));
        this.listaColumnas.add(new Columna("NUMERO_SERIE", false, false));
        this.listaColumnas.add(new Columna("DNI_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("NOMBRE_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("RUC_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("RAZON_SOCIAL_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION_FISCAL_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("CORREO_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("TELEFONO_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.comprobante.getPago_id());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.comprobante.getFecha_hora_creacion()));
        this.statement.setInt(3, this.comprobante.getTipo_comprobante().getId());
        this.statement.setString(4, this.comprobante.getNumero_serie());
        this.statement.setString(5, this.comprobante.getDni_cliente());
        this.statement.setString(6, this.comprobante.getNombre_cliente());
        this.statement.setString(7, this.comprobante.getRuc_cliente());
        this.statement.setString(8, this.comprobante.getRazon_social_cliente());
        this.statement.setString(9, this.comprobante.getDireccion_fiscal_cliente());
        this.statement.setString(10, this.comprobante.getCorreo_cliente());
        this.statement.setString(11, this.comprobante.getTelefono_cliente());
        this.statement.setDouble(12, this.comprobante.getTotal());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.comprobante.getPago_id());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.comprobante.getFecha_hora_creacion()));
        this.statement.setInt(3, this.comprobante.getTipo_comprobante().getId());
        this.statement.setString(4, this.comprobante.getNumero_serie());
        this.statement.setString(5, this.comprobante.getDni_cliente());
        this.statement.setString(6, this.comprobante.getNombre_cliente());
        this.statement.setString(7, this.comprobante.getRuc_cliente());
        this.statement.setString(8, this.comprobante.getRazon_social_cliente());
        this.statement.setString(9, this.comprobante.getDireccion_fiscal_cliente());
        this.statement.setString(10, this.comprobante.getCorreo_cliente());
        this.statement.setString(11, this.comprobante.getTelefono_cliente());
        this.statement.setDouble(12, this.comprobante.getTotal());
        this.statement.setInt(13, this.comprobante.getComprobante_pago_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.comprobante.getComprobante_pago_id());
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.comprobante.getComprobante_pago_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.comprobante.getComprobante_pago_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.comprobante = new Comprobante_PagoDTO();
        this.comprobante.setComprobante_pago_id(this.resultSet.getInt("COMPROBANTE_PAGO_ID"));
        this.comprobante.setPago_id(this.resultSet.getInt("PAGO_ID"));
        this.comprobante.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());

        Integer tipoId = this.resultSet.getInt("TIPO_COMPROBANTE_ID");
        this.comprobante.setTipo_comprobante(TipoComprobante.fromId(tipoId));

        this.comprobante.setNumero_serie(this.resultSet.getString("NUMERO_SERIE"));
        this.comprobante.setDni_cliente(this.resultSet.getString("DNI_CLIENTE"));
        this.comprobante.setNombre_cliente(this.resultSet.getString("NOMBRE_CLIENTE"));
        this.comprobante.setRuc_cliente(this.resultSet.getString("RUC_CLIENTE"));
        this.comprobante.setRazon_social_cliente(this.resultSet.getString("RAZON_SOCIAL_CLIENTE"));
        this.comprobante.setDireccion_fiscal_cliente(this.resultSet.getString("DIRECCION_FISCAL_CLIENTE"));
        this.comprobante.setCorreo_cliente(this.resultSet.getString("CORREO_CLIENTE"));
        this.comprobante.setTelefono_cliente(this.resultSet.getString("TELEFONO_CLIENTE"));
        this.comprobante.setTotal(this.resultSet.getDouble("TOTAL"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.comprobante = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.comprobante);
    }

    @Override
    public Integer insertar(Comprobante_PagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.insertar();
    }

    @Override
    public Comprobante_PagoDTO obtenerPorId(Integer comprobante_pago_id) {
        this.comprobante = new Comprobante_PagoDTO();
        this.comprobante.setComprobante_pago_id(comprobante_pago_id);
        super.obtenerPorId();
        return this.comprobante;
    }

    @Override
    public ArrayList<Comprobante_PagoDTO> listarTodos() {
        return (ArrayList<Comprobante_PagoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(Comprobante_PagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.modificar();
    }

    @Override
    public Integer eliminar(Comprobante_PagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.eliminar();
    }
}
