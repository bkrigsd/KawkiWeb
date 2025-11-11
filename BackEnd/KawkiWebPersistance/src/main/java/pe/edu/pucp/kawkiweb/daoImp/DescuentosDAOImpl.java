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

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.descuento = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
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

    @Override
    public ArrayList<DescuentosDTO> listarTodos() {
        return (ArrayList<DescuentosDTO>) super.listarTodos();
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
}
