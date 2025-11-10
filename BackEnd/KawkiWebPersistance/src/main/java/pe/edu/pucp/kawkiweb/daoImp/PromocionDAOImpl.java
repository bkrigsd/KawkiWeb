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

public class PromocionDAOImpl extends BaseDAOImpl implements DescuentosDAO {

    private DescuentosDTO promocion;
    private TiposBeneficioDAO tipoBeneficioDAO;
    private TiposCondicionDAO tipoCondicionDAO;

    public PromocionDAOImpl() {
        super("PROMOCIONES");
        this.promocion = null;
        this.retornarLlavePrimaria = true;
        this.tipoBeneficioDAO = new TiposBeneficioDAOImpl();
        this.tipoCondicionDAO = new TiposCondicionDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PROMOCION_ID", true, true));
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
        this.statement.setString(1, this.promocion.getDescripcion());
        this.statement.setInt(2, this.promocion.getTipo_condicion().getTipo_condicion_id());
        this.statement.setInt(3, this.promocion.getValor_condicion());
        this.statement.setInt(4, this.promocion.getTipo_beneficio().getTipo_beneficio_id());
        this.statement.setInt(5, this.promocion.getValor_beneficio());
        LocalDateTime fecha = this.promocion.getFecha_inicio();
        if (fecha != null) {
            fecha = fecha.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(6, java.sql.Timestamp.valueOf(fecha));
        LocalDateTime fechaFin = this.promocion.getFecha_fin();
        if (fechaFin != null) {
            fechaFin = fechaFin.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(7, java.sql.Timestamp.valueOf(fechaFin));
        this.statement.setInt(8, this.promocion.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.promocion.getDescripcion());
        this.statement.setInt(2, this.promocion.getTipo_condicion().getTipo_condicion_id());
        this.statement.setInt(3, this.promocion.getValor_condicion());
        this.statement.setInt(4, this.promocion.getTipo_beneficio().getTipo_beneficio_id());
        this.statement.setInt(5, this.promocion.getValor_beneficio());
        LocalDateTime fecha = this.promocion.getFecha_inicio();
        if (fecha != null) {
            fecha = fecha.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(6, java.sql.Timestamp.valueOf(fecha));
        LocalDateTime fechaFin = this.promocion.getFecha_fin();
        if (fechaFin != null) {
            fechaFin = fechaFin.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(7, java.sql.Timestamp.valueOf(fechaFin));
        this.statement.setInt(8, this.promocion.getActivo() ? 1 : 0);
        this.statement.setInt(9, this.promocion.getPromocion_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.promocion.getPromocion_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.promocion.getPromocion_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.promocion = new DescuentosDTO();
        this.promocion.setPromocion_id(this.resultSet.getInt("PROMOCION_ID"));
        this.promocion.setDescripcion(this.resultSet.getString("DESCRIPCION"));

        Integer tipo_condicion_id = this.resultSet.getInt("TIPO_CONDICION_ID");
        TiposCondicionDTO tipoCondicion = this.tipoCondicionDAO.obtenerPorId(tipo_condicion_id);
        this.promocion.setTipo_condicion(tipoCondicion);

        this.promocion.setValor_condicion(this.resultSet.getInt("VALOR_CONDICION"));

        Integer tipo_beneficio_id = this.resultSet.getInt("TIPO_BENEFICIO_ID");
        TiposBeneficioDTO tipoBeneficio = this.tipoBeneficioDAO.obtenerPorId(tipo_beneficio_id);
        this.promocion.setTipo_beneficio(tipoBeneficio);

        int valBeneficio = this.resultSet.getInt("VALOR_BENEFICIO");
        this.promocion.setValor_beneficio(valBeneficio);

        this.promocion.setFecha_inicio(this.resultSet.getTimestamp("FECHA_INICIO").toLocalDateTime());
        this.promocion.setFecha_fin(this.resultSet.getTimestamp("FECHA_FIN").toLocalDateTime());
        this.promocion.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.promocion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.promocion);
    }

    @Override
    public Integer insertar(DescuentosDTO promocion) {
        this.promocion = promocion;
        return super.insertar();
    }

    @Override
    public DescuentosDTO obtenerPorId(Integer promocionId) {
        this.promocion = new DescuentosDTO();
        this.promocion.setPromocion_id(promocionId);
        super.obtenerPorId();
        return this.promocion;
    }

    @Override
    public ArrayList<DescuentosDTO> listarTodos() {
        return (ArrayList<DescuentosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(DescuentosDTO promocion) {
        this.promocion = promocion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DescuentosDTO promocion) {
        this.promocion = promocion;
        return super.eliminar();
    }
}
