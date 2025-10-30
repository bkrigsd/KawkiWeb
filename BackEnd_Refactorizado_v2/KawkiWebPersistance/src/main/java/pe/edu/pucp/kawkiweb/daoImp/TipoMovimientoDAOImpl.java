package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.TipoMovimientoDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TipoMovimientoDTO;

public class TipoMovimientoDAOImpl extends BaseDAOImpl implements TipoMovimientoDAO {

    private TipoMovimientoDTO tipoMovimiento;

    public TipoMovimientoDAOImpl() {
        super("TIPO_MOVIMIENTO");
        this.tipoMovimiento = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_MOVIMIENTO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoMovimiento.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoMovimiento.getNombre());
        this.statement.setInt(2, this.tipoMovimiento.getTipoMovimientoId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoMovimiento.getTipoMovimientoId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoMovimiento.getTipoMovimientoId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoMovimiento = new TipoMovimientoDTO();
        this.tipoMovimiento.setTipoMovimientoId(this.resultSet.getInt("TIPO_MOVIMIENTO_ID"));
        this.tipoMovimiento.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoMovimiento = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoMovimiento);
    }

    @Override
    public Integer insertar(TipoMovimientoDTO tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return super.insertar();
    }

    @Override
    public TipoMovimientoDTO obtenerPorId(Integer tipoMovimientoId) {
        this.tipoMovimiento = new TipoMovimientoDTO();
        this.tipoMovimiento.setTipoMovimientoId(tipoMovimientoId);
        super.obtenerPorId();
        return this.tipoMovimiento;
    }

    @Override
    public TipoMovimientoDTO obtenerPorNombre(String nombre) {
        TipoMovimientoDTO resultado = null;
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM TIPO_MOVIMIENTO WHERE NOMBRE = ?";
            st = this.conexion.prepareCall(sql);
            st.setString(1, nombre);
            rs = st.executeQuery();

            if (rs.next()) {
                resultado = new TipoMovimientoDTO();
                resultado.setTipoMovimientoId(rs.getInt("TIPO_MOVIMIENTO_ID"));
                resultado.setNombre(rs.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener tipo de movimiento por nombre - " + ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos - " + ex);
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<TipoMovimientoDTO> listarTodos() {
        return (ArrayList<TipoMovimientoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TipoMovimientoDTO tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoMovimientoDTO tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return super.eliminar();
    }
}
