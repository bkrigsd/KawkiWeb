package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.dao.DescuentosDAO;
import pe.edu.pucp.kawkiweb.dao.TiposBeneficioDAO;
import pe.edu.pucp.kawkiweb.dao.TiposCondicionDAO;

public class DescuentosDAOImpl extends BaseDAOImpl implements DescuentosDAO {

    private DescuentosDTO descuento;
    private TiposBeneficioDAO tipoBeneficioDAO;
    private TiposCondicionDAO tipoCondicionDAO;

    public DescuentosDAOImpl() {
        super("DESCUENTOS");
        this.descuento = null;
        this.retornarLlavePrimaria = true;
        this.tipoBeneficioDAO = new TiposBeneficioDAOImpl();
        this.tipoCondicionDAO = new TiposCondicionDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DESCUENTO_ID", true, true));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("TIPO_CONDICION_ID", false, false));
        this.listaColumnas.add(new Columna("VALOR_CONDICION", false, false));
        this.listaColumnas.add(new Columna("TIPO_BENEFICIO_ID", false, false));
        this.listaColumnas.add(new Columna("VALOR_BENEFICIO", false, false));
        this.listaColumnas.add(new Columna("FECHA_INICIO", false, false));
        this.listaColumnas.add(new Columna("FECHA_FIN", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.descuento.getDescripcion());
        this.statement.setInt(2, this.descuento.getTipo_condicion().getTipo_condicion_id());
        this.statement.setInt(3, this.descuento.getValor_condicion());
        this.statement.setInt(4, this.descuento.getTipo_beneficio().getTipo_beneficio_id());
        this.statement.setInt(5, this.descuento.getValor_beneficio());
        LocalDateTime fecha = this.descuento.getFecha_inicio();
        if (fecha != null) {
            fecha = fecha.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(6, java.sql.Timestamp.valueOf(fecha));
        LocalDateTime fechaFin = this.descuento.getFecha_fin();
        if (fechaFin != null) {
            fechaFin = fechaFin.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(7, java.sql.Timestamp.valueOf(fechaFin));
        this.statement.setInt(8, this.descuento.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.descuento.getDescripcion());
        this.statement.setInt(2, this.descuento.getTipo_condicion().getTipo_condicion_id());
        this.statement.setInt(3, this.descuento.getValor_condicion());
        this.statement.setInt(4, this.descuento.getTipo_beneficio().getTipo_beneficio_id());
        this.statement.setInt(5, this.descuento.getValor_beneficio());
        LocalDateTime fecha = this.descuento.getFecha_inicio();
        if (fecha != null) {
            fecha = fecha.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(6, java.sql.Timestamp.valueOf(fecha));
        LocalDateTime fechaFin = this.descuento.getFecha_fin();
        if (fechaFin != null) {
            fechaFin = fechaFin.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(7, java.sql.Timestamp.valueOf(fechaFin));
        this.statement.setInt(8, this.descuento.getActivo() ? 1 : 0);
        this.statement.setInt(9, this.descuento.getDescuento_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.descuento.getDescuento_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.descuento.getDescuento_id());
    }

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UN
     * SOLO descuento.
     */
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.descuento = new DescuentosDTO();
        this.descuento.setDescuento_id(this.resultSet.getInt("DESCUENTO_ID"));
        this.descuento.setDescripcion(this.resultSet.getString("DESCRIPCION"));

        Integer tipo_condicion_id = this.resultSet.getInt("TIPO_CONDICION_ID");
        TiposCondicionDTO tipoCondicion = this.tipoCondicionDAO.obtenerPorId(tipo_condicion_id);
        this.descuento.setTipo_condicion(tipoCondicion);

        this.descuento.setValor_condicion(this.resultSet.getInt("VALOR_CONDICION"));

        Integer tipo_beneficio_id = this.resultSet.getInt("TIPO_BENEFICIO_ID");
        TiposBeneficioDTO tipoBeneficio = this.tipoBeneficioDAO.obtenerPorId(tipo_beneficio_id);
        this.descuento.setTipo_beneficio(tipoBeneficio);

        int valBeneficio = this.resultSet.getInt("VALOR_BENEFICIO");
        this.descuento.setValor_beneficio(valBeneficio);

        this.descuento.setFecha_inicio(this.resultSet.getTimestamp("FECHA_INICIO").toLocalDateTime());
        this.descuento.setFecha_fin(this.resultSet.getTimestamp("FECHA_FIN").toLocalDateTime());
        this.descuento.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHOS descuentos.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.descuento = new DescuentosDTO();
        this.descuento.setDescuento_id(this.resultSet.getInt("DESCUENTO_ID"));
        this.descuento.setDescripcion(this.resultSet.getString("DESCRIPCION"));

        // Tipo de condición (YA VIENE COMPLETO del JOIN - SIN query adicional)
        TiposCondicionDTO tipoCondicion = new TiposCondicionDTO();
        tipoCondicion.setTipo_condicion_id(this.resultSet.getInt("TIPO_CONDICION_ID"));
        tipoCondicion.setNombre(this.resultSet.getString("TIPO_CONDICION_NOMBRE"));
        this.descuento.setTipo_condicion(tipoCondicion);

        this.descuento.setValor_condicion(this.resultSet.getInt("VALOR_CONDICION"));

        // Tipo de beneficio (YA VIENE COMPLETO del JOIN - SIN query adicional)
        TiposBeneficioDTO tipoBeneficio = new TiposBeneficioDTO();
        tipoBeneficio.setTipo_beneficio_id(this.resultSet.getInt("TIPO_BENEFICIO_ID"));
        tipoBeneficio.setNombre(this.resultSet.getString("TIPO_BENEFICIO_NOMBRE"));
        this.descuento.setTipo_beneficio(tipoBeneficio);

        this.descuento.setValor_beneficio(this.resultSet.getInt("VALOR_BENEFICIO"));
        this.descuento.setFecha_inicio(this.resultSet.getTimestamp("FECHA_INICIO").toLocalDateTime());
        this.descuento.setFecha_fin(this.resultSet.getTimestamp("FECHA_FIN").toLocalDateTime());
        this.descuento.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.descuento = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.descuento);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
        lista.add(this.descuento);
    }

    @Override
    public Integer insertar(DescuentosDTO descuento) {
        this.descuento = descuento;
        return super.insertar();
    }

    @Override
    public DescuentosDTO obtenerPorId(Integer descuentoId) {
        this.descuento = new DescuentosDTO();
        this.descuento.setDescuento_id(descuentoId);
        super.obtenerPorId();
        return this.descuento;
    }

    /**
     * Lista todos los descuentos usando el Stored Procedure optimizado. Retorna
     * descuentos con tipo_condicion y tipo_beneficio completos.
     */
    @Override
    public ArrayList<DescuentosDTO> listarTodos() {
        return (ArrayList<DescuentosDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_DESCUENTOS_COMPLETO"
        );
    }

    @Override
    public Integer modificar(DescuentosDTO descuento) {
        this.descuento = descuento;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DescuentosDTO descuento) {
        this.descuento = descuento;
        return super.eliminar();
    }

    // ========== IMPLEMENTACIÓN DE MÉTODOS CON STORED PROCEDURES ==========
    /**
     * Lista descuentos activos usando SP optimizado. El SP trae todos los datos
     * con JOINs, sin necesidad de queries adicionales.
     */
    @Override
    public ArrayList<DescuentosDTO> listarActivas() {
        return (ArrayList<DescuentosDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_DESCUENTOS_ACTIVOS",
                0, // Sin parámetros
                null, // Sin consumer de parámetros
                null // Sin objeto de parámetros
        );
    }

    /**
     * Lista descuentos vigentes usando SP optimizado. El SP trae todos los
     * datos con JOINs, sin necesidad de queries adicionales.
     */
    @Override
    public ArrayList<DescuentosDTO> listarVigentes() {
        return (ArrayList<DescuentosDTO>) super.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_DESCUENTOS_VIGENTES",
                0, // Sin parámetros
                null, // Sin consumer de parámetros
                null // Sin objeto de parámetros
        );
    }
}
