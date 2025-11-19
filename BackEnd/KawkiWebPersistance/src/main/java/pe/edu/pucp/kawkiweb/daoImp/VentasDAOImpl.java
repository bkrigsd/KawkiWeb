package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.dao.DescuentosDAO;
import pe.edu.pucp.kawkiweb.dao.DetalleVentasDAO;
import pe.edu.pucp.kawkiweb.dao.RedesSocialesDAO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;
import pe.edu.pucp.kawkiweb.dao.VentasDAO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

public class VentasDAOImpl extends BaseDAOImpl implements VentasDAO {

    private VentasDTO venta;
    private UsuariosDAO usuarioDAO;
    private DescuentosDAO descuentoDAO;
    private DetalleVentasDAO detalleVentaDAO;
    private RedesSocialesDAO redSocialDAO;

    public VentasDAOImpl() {
        super("VENTAS");
        this.venta = null;
        this.retornarLlavePrimaria = true;
        this.usuarioDAO = new UsuariosDAOImpl();
        this.descuentoDAO = new DescuentosDAOImpl();
        this.detalleVentaDAO = new DetalleVentasDAOImpl();
        this.redSocialDAO = new RedesSocialesDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("VENTA_ID", true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("DESCUENTO_ID", false, false));
        this.listaColumnas.add(new Columna("RED_SOCIAL_ID", false, false));
        this.listaColumnas.add(new Columna("ES_VALIDA", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.venta.getUsuario().getUsuarioId());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.venta.getFecha_hora_creacion()));
        this.statement.setDouble(3, this.venta.getTotal());

        DescuentosDTO descuento = this.venta.getDescuento();
        if (descuento != null && descuento.getDescuento_id() != null) {
            this.statement.setInt(4, descuento.getDescuento_id());
        } else {
            this.statement.setNull(4, java.sql.Types.INTEGER);
        }

        this.statement.setInt(5, venta.getRedSocial().getRedSocialId());
        this.statement.setInt(6, this.venta.getEsValida() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.venta.getUsuario().getUsuarioId());
        this.statement.setDouble(2, this.venta.getTotal());

        DescuentosDTO descuento = this.venta.getDescuento();
        if (descuento != null && descuento.getDescuento_id() != null) {
            this.statement.setInt(3, descuento.getDescuento_id());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }

        this.statement.setInt(4, this.venta.getRedSocial().getRedSocialId());
        this.statement.setInt(5, this.venta.getEsValida() ? 1 : 0);
        this.statement.setInt(6, this.venta.getVenta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.venta.getVenta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.venta.getVenta_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.venta = new VentasDTO();
        this.venta.setVenta_id(this.resultSet.getInt("VENTA_ID"));

        // Obtener usuario completo usando DAO
        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.venta.setUsuario(usuario);

        this.venta.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );
        this.venta.setTotal(this.resultSet.getDouble("TOTAL"));

        // Obtener Promoción completa usando DAO (puede ser null)
        Integer descuento_id = (Integer) this.resultSet.getObject("DESCUENTO_ID");
        if (descuento_id != null) {
            DescuentosDTO descuento = this.descuentoDAO.obtenerPorId(descuento_id);
            this.venta.setDescuento(descuento);
        } else {
            this.venta.setDescuento(null);
        }

        // Obtener Red Social completa usando DAO (puede ser null)
        Integer red_social_id = (Integer) this.resultSet.getObject("RED_SOCIAL_ID");
        if (red_social_id != null) {
            RedesSocialesDTO redSocial = this.redSocialDAO.obtenerPorId(red_social_id);
            this.venta.setRedSocial(redSocial);
        } else {
            this.venta.setRedSocial(null);
        }

        Boolean esValida = (Boolean) this.resultSet.getObject("ES_VALIDA");
        this.venta.setEsValida(esValida);

        // Cargar automáticamente los detalles de la venta
//        ArrayList<DetalleVentasDTO> detalles
//                = this.detalleVentaDAO.listarPorVentaId(this.venta.getVenta_id());
//        this.venta.setDetalles(detalles);
        this.venta.setDetalles(new ArrayList<>());
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHAS ventas.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.venta = new VentasDTO();

        // Datos básicos de la venta
        this.venta.setVenta_id(this.resultSet.getInt("VENTA_ID"));
        this.venta.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );
        this.venta.setTotal(this.resultSet.getDouble("TOTAL"));

        Boolean esValida = (Boolean) this.resultSet.getObject("ES_VALIDA");
        this.venta.setEsValida(esValida);

        // Usuario (YA VIENE del JOIN - ID, NOMBRE, APE_PATERNO)
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombre(this.resultSet.getString("USUARIO_NOMBRE"));
        usuario.setApePaterno(this.resultSet.getString("USUARIO_APE_PATERNO"));
        this.venta.setUsuario(usuario);

        // Descuento (PUEDE SER NULL - usar LEFT JOIN)
        Integer descuentoId = (Integer) this.resultSet.getObject("DESCUENTO_ID");
        if (descuentoId != null) {
            DescuentosDTO descuento = new DescuentosDTO();
            descuento.setDescuento_id(descuentoId);
            descuento.setDescripcion(this.resultSet.getString("DESCUENTO_DESCRIPCION"));
            descuento.setValor_condicion(this.resultSet.getInt("VALOR_CONDICION"));
            descuento.setValor_beneficio(this.resultSet.getInt("VALOR_BENEFICIO"));
            descuento.setFecha_inicio(
                    this.resultSet.getTimestamp("DESCUENTO_FECHA_INICIO").toLocalDateTime()
            );
            descuento.setFecha_fin(
                    this.resultSet.getTimestamp("DESCUENTO_FECHA_FIN").toLocalDateTime()
            );

            Boolean descuentoActivo = (Boolean) this.resultSet.getObject("DESCUENTO_ACTIVO");
            descuento.setActivo(descuentoActivo);

            // Tipo de condición del descuento (YA VIENE del JOIN)
            TiposCondicionDTO tipoCondicion = new TiposCondicionDTO();
            tipoCondicion.setTipo_condicion_id(this.resultSet.getInt("TIPO_CONDICION_ID"));
            tipoCondicion.setNombre(this.resultSet.getString("TIPO_CONDICION_NOMBRE"));
            descuento.setTipo_condicion(tipoCondicion);

            // Tipo de beneficio del descuento (YA VIENE del JOIN)
            TiposBeneficioDTO tipoBeneficio = new TiposBeneficioDTO();
            tipoBeneficio.setTipo_beneficio_id(this.resultSet.getInt("TIPO_BENEFICIO_ID"));
            tipoBeneficio.setNombre(this.resultSet.getString("TIPO_BENEFICIO_NOMBRE"));
            descuento.setTipo_beneficio(tipoBeneficio);

            this.venta.setDescuento(descuento);
        } else {
            this.venta.setDescuento(null);
        }

        // Red Social (YA VIENE COMPLETA del JOIN)
        RedesSocialesDTO redSocial = new RedesSocialesDTO();
        redSocial.setRedSocialId(this.resultSet.getInt("RED_SOCIAL_ID"));
        redSocial.setNombre(this.resultSet.getString("RED_SOCIAL_NOMBRE"));
        this.venta.setRedSocial(redSocial);

        this.venta.setDetalles(new ArrayList<>());
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.venta = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.venta);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
        lista.add(this.venta);
    }

    @Override
    public Integer insertar(VentasDTO venta) {
        this.venta = venta;
        return super.insertar();
    }

    @Override
    public VentasDTO obtenerPorId(Integer ventaId) {
        this.venta = new VentasDTO();
        this.venta.setVenta_id(ventaId);
        super.obtenerPorId();
        return this.venta;
    }

    /**
     * Lista todas las ventas usando el Stored Procedure optimizado. Retorna
     * ventas con usuario (id, nombre, ape_paterno), descuento completo (si
     * existe), y red social completa.
     */
    @Override
    public ArrayList<VentasDTO> listarTodos() {
        return (ArrayList<VentasDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_VENTAS_COMPLETO"
        );
    }

    @Override
    public Integer modificar(VentasDTO venta) {
        this.venta = venta;
        return super.modificar();
    }

    @Override
    public Integer eliminar(VentasDTO venta) {
        this.venta = venta;
        return super.eliminar();
    }

}
