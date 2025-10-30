package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.EstadoEnvioDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilEnvio.EstadoEnvioDTO;

public class EstadoEnvioDAOImpl extends BaseDAOImpl implements EstadoEnvioDAO {

    private EstadoEnvioDTO estadoEnvio;

    public EstadoEnvioDAOImpl() {
        super("ESTADO_ENVIO");
        this.estadoEnvio = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ESTADO_ENVIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.estadoEnvio.getEstadoEnvioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.estadoEnvio = new EstadoEnvioDTO();
        this.estadoEnvio.setEstadoEnvioId(this.resultSet.getInt("ESTADO_ENVIO_ID"));
        this.estadoEnvio.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.estadoEnvio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.estadoEnvio);
    }

    @Override
    public EstadoEnvioDTO obtenerPorId(Integer estadoEnvioId) {
        this.estadoEnvio = new EstadoEnvioDTO();
        this.estadoEnvio.setEstadoEnvioId(estadoEnvioId);
        super.obtenerPorId();
        return this.estadoEnvio;
    }

    @Override
    public EstadoEnvioDTO obtenerPorNombre(String nombre) {
        EstadoEnvioDTO resultado = null;
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM ESTADO_ENVIO WHERE NOMBRE = ?";
            st = this.conexion.prepareCall(sql);
            st.setString(1, nombre);
            rs = st.executeQuery();

            if (rs.next()) {
                resultado = new EstadoEnvioDTO();
                resultado.setEstadoEnvioId(rs.getInt("ESTADO_ENVIO_ID"));
                resultado.setNombre(rs.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener estado de envío por nombre - " + ex);
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
    public ArrayList<EstadoEnvioDTO> listarTodos() {
        return (ArrayList<EstadoEnvioDTO>) super.listarTodos();
    }

}
