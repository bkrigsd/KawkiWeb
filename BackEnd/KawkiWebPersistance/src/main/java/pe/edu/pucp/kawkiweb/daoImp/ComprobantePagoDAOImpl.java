package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;
import pe.edu.pucp.kawkiweb.dao.ComprobantesPagoDAO;
import pe.edu.pucp.kawkiweb.dao.MetodosPagoDAO;
import pe.edu.pucp.kawkiweb.dao.TiposComprobanteDAO;
import pe.edu.pucp.kawkiweb.dao.VentasDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;

public class ComprobantePagoDAOImpl extends BaseDAOImpl implements ComprobantesPagoDAO {

    private ComprobantesPagoDTO comprobante;
    private TiposComprobanteDAO tipoComprobanteDAO;
    private VentasDAO ventaDAO;
    private MetodosPagoDAO metodoPagoDAO;

    public ComprobantePagoDAOImpl() {
        super("COMPROBANTES_PAGO");
        this.comprobante = null;
        this.retornarLlavePrimaria = true;
        this.tipoComprobanteDAO = new TiposComprobanteDAOImpl();
        this.ventaDAO = new VentasDAOImpl();
        this.metodoPagoDAO = new MetodosPagoDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("COMPROBANTE_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false, false));
        this.listaColumnas.add(new Columna("TIPO_COMPROBANTE_ID", false, false));
        this.listaColumnas.add(new Columna("NUMERO_SERIE", false, false, false));
        this.listaColumnas.add(new Columna("DNI_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("NOMBRE_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("RUC_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("RAZON_SOCIAL_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION_FISCAL_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("TELEFONO_CLIENTE", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("VENTA_ID", false, false));
        this.listaColumnas.add(new Columna("METODO_PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("IGV", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setTimestamp(1, java.sql.Timestamp.valueOf(this.comprobante.getFecha_hora_creacion()));
        this.statement.setInt(2, this.comprobante.getTipo_comprobante().getTipo_comprobante_id());
        this.statement.setString(3, this.comprobante.getNumero_serie());

        if (this.comprobante.getDni_cliente() != null) {
            this.statement.setString(4, this.comprobante.getDni_cliente());
        } else {
            this.statement.setNull(4, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getNombre_cliente() != null) {
            this.statement.setString(5, this.comprobante.getNombre_cliente());
        } else {
            this.statement.setNull(5, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getRuc_cliente() != null) {
            this.statement.setString(6, this.comprobante.getRuc_cliente());
        } else {
            this.statement.setNull(6, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getRazon_social_cliente() != null) {
            this.statement.setString(7, this.comprobante.getRazon_social_cliente());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getDireccion_fiscal_cliente() != null) {
            this.statement.setString(8, this.comprobante.getDireccion_fiscal_cliente());
        } else {
            this.statement.setNull(8, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getTelefono_cliente() != null) {
            this.statement.setString(9, this.comprobante.getTelefono_cliente());
        } else {
            this.statement.setNull(9, java.sql.Types.VARCHAR);
        }

        this.statement.setDouble(10, this.comprobante.getTotal());
        this.statement.setInt(11, this.comprobante.getVenta().getVenta_id());
        this.statement.setInt(12, this.comprobante.getMetodo_pago().getMetodo_pago_id());
        this.statement.setDouble(13, this.comprobante.getSubtotal());
        this.statement.setDouble(14, this.comprobante.getIgv());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.comprobante.getTipo_comprobante().getTipo_comprobante_id());

        if (this.comprobante.getDni_cliente() != null) {
            this.statement.setString(2, this.comprobante.getDni_cliente());
        } else {
            this.statement.setNull(2, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getNombre_cliente() != null) {
            this.statement.setString(3, this.comprobante.getNombre_cliente());
        } else {
            this.statement.setNull(3, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getRuc_cliente() != null) {
            this.statement.setString(4, this.comprobante.getRuc_cliente());
        } else {
            this.statement.setNull(4, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getRazon_social_cliente() != null) {
            this.statement.setString(5, this.comprobante.getRazon_social_cliente());
        } else {
            this.statement.setNull(5, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getDireccion_fiscal_cliente() != null) {
            this.statement.setString(6, this.comprobante.getDireccion_fiscal_cliente());
        } else {
            this.statement.setNull(6, java.sql.Types.VARCHAR);
        }

        if (this.comprobante.getTelefono_cliente() != null) {
            this.statement.setString(7, this.comprobante.getTelefono_cliente());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }

        this.statement.setDouble(8, this.comprobante.getTotal());
        this.statement.setInt(9, this.comprobante.getVenta().getVenta_id());
        this.statement.setInt(10, this.comprobante.getMetodo_pago().getMetodo_pago_id());
        this.statement.setDouble(11, this.comprobante.getSubtotal());
        this.statement.setDouble(12, this.comprobante.getIgv());
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

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UN
     * SOLO comprobante.
     */
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.comprobante = new ComprobantesPagoDTO();
        this.comprobante.setComprobante_pago_id(this.resultSet.getInt("COMPROBANTE_PAGO_ID"));
        this.comprobante.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());

        Integer tipoComprobanteId = this.resultSet.getInt("TIPO_COMPROBANTE_ID");
        TiposComprobanteDTO tipoComprobante = this.tipoComprobanteDAO.obtenerPorId(tipoComprobanteId);
        this.comprobante.setTipo_comprobante(tipoComprobante);

        this.comprobante.setNumero_serie(this.resultSet.getString("NUMERO_SERIE"));
        this.comprobante.setDni_cliente(this.resultSet.getString("DNI_CLIENTE"));
        this.comprobante.setNombre_cliente(this.resultSet.getString("NOMBRE_CLIENTE"));
        this.comprobante.setRuc_cliente(this.resultSet.getString("RUC_CLIENTE"));
        this.comprobante.setRazon_social_cliente(this.resultSet.getString("RAZON_SOCIAL_CLIENTE"));
        this.comprobante.setDireccion_fiscal_cliente(this.resultSet.getString("DIRECCION_FISCAL_CLIENTE"));
        this.comprobante.setTelefono_cliente(this.resultSet.getString("TELEFONO_CLIENTE"));
        this.comprobante.setTotal(this.resultSet.getDouble("TOTAL"));

        Integer ventaId = this.resultSet.getInt("VENTA_ID");
        VentasDTO venta = this.ventaDAO.obtenerPorId(ventaId);
        this.comprobante.setVenta(venta);

        Integer metodoPagoId = this.resultSet.getInt("METODO_PAGO_ID");
        MetodosPagoDTO metodoPago = this.metodoPagoDAO.obtenerPorId(metodoPagoId);
        this.comprobante.setMetodo_pago(metodoPago);

        this.comprobante.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.comprobante.setIgv(this.resultSet.getDouble("IGV"));
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHOS comprobantes.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.comprobante = new ComprobantesPagoDTO();
        this.comprobante.setComprobante_pago_id(this.resultSet.getInt("COMPROBANTE_PAGO_ID"));
        this.comprobante.setFecha_hora_creacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());

        // Tipo comprobante (YA VIENE COMPLETO del JOIN - SIN query adicional)
        TiposComprobanteDTO tipoComprobante = new TiposComprobanteDTO();
        tipoComprobante.setTipo_comprobante_id(this.resultSet.getInt("TIPO_COMPROBANTE_ID"));
        tipoComprobante.setNombre(this.resultSet.getString("TIPO_COMPROBANTE_NOMBRE"));
        this.comprobante.setTipo_comprobante(tipoComprobante);

        this.comprobante.setNumero_serie(this.resultSet.getString("NUMERO_SERIE"));
        this.comprobante.setDni_cliente(this.resultSet.getString("DNI_CLIENTE"));
        this.comprobante.setNombre_cliente(this.resultSet.getString("NOMBRE_CLIENTE"));
        this.comprobante.setRuc_cliente(this.resultSet.getString("RUC_CLIENTE"));
        this.comprobante.setRazon_social_cliente(this.resultSet.getString("RAZON_SOCIAL_CLIENTE"));
        this.comprobante.setDireccion_fiscal_cliente(this.resultSet.getString("DIRECCION_FISCAL_CLIENTE"));
        this.comprobante.setTelefono_cliente(this.resultSet.getString("TELEFONO_CLIENTE"));
        this.comprobante.setTotal(this.resultSet.getDouble("TOTAL"));

        // Venta (YA VIENE COMPLETA del JOIN - SIN query adicional)
        // Solo con info básica, sin detalles de venta ni descuento
        VentasDTO venta = new VentasDTO();
        venta.setVenta_id(this.resultSet.getInt("VENTA_ID"));
        venta.setFecha_hora_creacion(this.resultSet.getTimestamp("VENTA_FECHA_HORA").toLocalDateTime());
        venta.setTotal(this.resultSet.getDouble("VENTA_TOTAL"));
        venta.setEsValida(this.resultSet.getBoolean("ES_VALIDA"));

        // Usuario de la venta (YA VIENE del JOIN)
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombreUsuario(this.resultSet.getString("NOMBRE_USUARIO"));
        venta.setUsuario(usuario);

        this.comprobante.setVenta(venta);

        // Método de pago (YA VIENE COMPLETO del JOIN - SIN query adicional)
        MetodosPagoDTO metodoPago = new MetodosPagoDTO();
        metodoPago.setMetodo_pago_id(this.resultSet.getInt("METODO_PAGO_ID"));
        metodoPago.setNombre(this.resultSet.getString("METODO_PAGO_NOMBRE"));
        this.comprobante.setMetodo_pago(metodoPago);

        this.comprobante.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.comprobante.setIgv(this.resultSet.getDouble("IGV"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.comprobante = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.comprobante);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
        lista.add(this.comprobante);
    }

    @Override
    public Integer insertar(ComprobantesPagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.insertar();
    }

    @Override
    public ComprobantesPagoDTO obtenerPorId(Integer comprobante_pago_id) {
        this.comprobante = new ComprobantesPagoDTO();
        this.comprobante.setComprobante_pago_id(comprobante_pago_id);
        super.obtenerPorId();
        return this.comprobante;
    }

    /**
     * Lista todos los comprobantes usando el Stored Procedure optimizado.
     * Retorna comprobantes con tipo_comprobante, venta (con usuario básico) y
     * método_pago completos.
     */
    @Override
    public ArrayList<ComprobantesPagoDTO> listarTodos() {
        return (ArrayList<ComprobantesPagoDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_COMPROBANTES_COMPLETO"
        );
    }

    @Override
    public Integer modificar(ComprobantesPagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ComprobantesPagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.eliminar();
    }

    /// BÚSQUEDAS AVANZADAS
    
    @Override
    public ComprobantesPagoDTO obtenerPorVentaId(Integer ventaId) {
        this.comprobante = null;

        // Consumer para setear el parámetro de entrada
        Consumer<Integer> incluirParametros = (id) -> {
            try {
                this.statement.setInt(1, id);
            } catch (SQLException e) {
                System.err.println("Error al establecer parámetro: " + e);
            }
        };

        // Ejecuta el procedimiento almacenado
        // El nombre del SP es el mismo para MySQL y SQL Server
        super.ejecutarConsultaProcedimiento(
                "SP_OBTENER_COMPROBANTE_POR_VENTA",
                1, // Cantidad de parámetros (solo ventaId)
                incluirParametros,
                ventaId
        );

        return this.comprobante;
    }

    @Override
    public String obtenerSiguienteNumeroSerie(Integer tipoComprobanteId) {
        String numeroSerie = null;

        try {
            this.abrirConexion();

            // Llamada al stored procedure
            // Funciona tanto para MySQL como SQL Server
            String sql = "{CALL SP_OBTENER_SIGUIENTE_NUMERO_SERIE(?, ?)}";
            this.colocarSQLEnStatement(sql);
            this.statement.setInt(1, tipoComprobanteId);
            this.statement.registerOutParameter(2, Types.VARCHAR);
            this.statement.execute();
            numeroSerie = this.statement.getString(2);

        } catch (SQLException ex) {
            System.err.println("Error al obtener siguiente número de serie: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }

        return numeroSerie;
    }

}
