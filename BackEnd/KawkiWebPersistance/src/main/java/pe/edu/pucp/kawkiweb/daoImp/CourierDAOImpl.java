package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.CourierDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilEnvio.CourierDTO;

public class CourierDAOImpl extends BaseDAOImpl implements CourierDAO {

    private CourierDTO courier;

    public CourierDAOImpl() {
        super("COURIERS");
        this.courier = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("COURIER_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.courier.getCourierId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.courier = new CourierDTO();
        this.courier.setCourierId(this.resultSet.getInt("COURIER_ID"));
        this.courier.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.courier = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.courier);
    }

    @Override
    public CourierDTO obtenerPorId(Integer courierId) {
        this.courier = new CourierDTO();
        this.courier.setCourierId(courierId);
        super.obtenerPorId();
        return this.courier;
    }

    @Override
    public CourierDTO obtenerPorNombre(String nombre) {
        CourierDTO resultado = null;
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM COURIERS WHERE NOMBRE = ?";
            st = this.conexion.prepareCall(sql);
            st.setString(1, nombre);
            rs = st.executeQuery();

            if (rs.next()) {
                resultado = new CourierDTO();
                resultado.setCourierId(rs.getInt("COURIER_ID"));
                resultado.setNombre(rs.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener courier por nombre - " + ex);
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
    public ArrayList<CourierDTO> listarTodos() {
        return (ArrayList<CourierDTO>) super.listarTodos();
    }

}
