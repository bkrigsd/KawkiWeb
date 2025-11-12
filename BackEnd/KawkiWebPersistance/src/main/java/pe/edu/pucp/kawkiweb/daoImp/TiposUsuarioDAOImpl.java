package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
import pe.edu.pucp.kawkiweb.dao.TiposUsuarioDAO;

public class TiposUsuarioDAOImpl extends BaseDAOImpl implements TiposUsuarioDAO {

    private TiposUsuarioDTO tipoUsuario;

    public TiposUsuarioDAOImpl() {
        super("TIPOS_USUARIO");
        this.tipoUsuario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoUsuario.getTipoUsuarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoUsuario = new TiposUsuarioDTO();
        this.tipoUsuario.setTipoUsuarioId(this.resultSet.getInt("TIPO_USUARIO_ID"));
        this.tipoUsuario.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoUsuario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoUsuario);
    }

    @Override
    public TiposUsuarioDTO obtenerPorId(Integer tipoUsuarioId) {
        this.tipoUsuario = new TiposUsuarioDTO();
        this.tipoUsuario.setTipoUsuarioId(tipoUsuarioId);
        super.obtenerPorId();
        return this.tipoUsuario;
    }

    @Override
    public TiposUsuarioDTO obtenerPorNombre(String nombre) {
        TiposUsuarioDTO resultado = null;
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM TIPO_USUARIO WHERE NOMBRE = ?";
            st = this.conexion.prepareCall(sql);
            st.setString(1, nombre);
            rs = st.executeQuery();

            if (rs.next()) {
                resultado = new TiposUsuarioDTO();
                resultado.setTipoUsuarioId(rs.getInt("TIPO_USUARIO_ID"));
                resultado.setNombre(rs.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener tipo de usuario por nombre - " + ex);
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
    public ArrayList<TiposUsuarioDTO> listarTodos() {
        return (ArrayList<TiposUsuarioDTO>) super.listarTodos();
    }

}
