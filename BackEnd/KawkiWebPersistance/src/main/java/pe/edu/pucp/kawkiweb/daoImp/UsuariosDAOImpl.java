package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.dao.TiposUsuarioDAO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;

public class UsuariosDAOImpl extends BaseDAOImpl implements UsuariosDAO {

    private UsuariosDTO usuario;
    private TiposUsuarioDAO tipoUsuarioDAO;

    public UsuariosDAOImpl() {
        super("USUARIOS");
        this.usuario = null;
        this.retornarLlavePrimaria = true;
        this.tipoUsuarioDAO = new TiposUsuarioDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("APE_PATERNO", false, false));
        this.listaColumnas.add(new Columna("DNI", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("NOMBRE_USUARIO", false, false));
        this.listaColumnas.add(new Columna("CONTRASENHA", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
        this.listaColumnas.add(new Columna("TIPO_USUARIO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.usuario.getNombre());
        this.statement.setString(2, this.usuario.getApePaterno());
        this.statement.setString(3, this.usuario.getDni());
        this.statement.setString(4, this.usuario.getTelefono());
        this.statement.setString(5, this.usuario.getCorreo());
        this.statement.setString(6, this.usuario.getNombreUsuario());
        this.statement.setString(7, this.usuario.getContrasenha());
        this.statement.setTimestamp(8, java.sql.Timestamp.valueOf(this.usuario.getFechaHoraCreacion()));
        this.statement.setInt(9, this.usuario.getTipoUsuario().getTipoUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.usuario.getNombre());
        this.statement.setString(2, this.usuario.getApePaterno());
        this.statement.setString(3, this.usuario.getDni());
        this.statement.setString(4, this.usuario.getTelefono());
        this.statement.setString(5, this.usuario.getCorreo());
        this.statement.setString(6, this.usuario.getNombreUsuario());
        this.statement.setString(7, this.usuario.getContrasenha());
        this.statement.setTimestamp(8, java.sql.Timestamp.valueOf(this.usuario.getFechaHoraCreacion()));
        this.statement.setInt(9, this.usuario.getTipoUsuario().getTipoUsuarioId());
        this.statement.setInt(10, this.usuario.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.usuario.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.usuario.getUsuarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.usuario = new UsuariosDTO();
        this.usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        this.usuario.setNombre(this.resultSet.getString("NOMBRE"));
        this.usuario.setApePaterno(this.resultSet.getString("APE_PATERNO"));
        this.usuario.setDni(this.resultSet.getString("DNI"));
        this.usuario.setTelefono(this.resultSet.getString("TELEFONO"));
        this.usuario.setCorreo(this.resultSet.getString("CORREO"));
        this.usuario.setNombreUsuario(this.resultSet.getString("NOMBRE_USUARIO"));
        this.usuario.setContrasenha(this.resultSet.getString("CONTRASENHA"));
        this.usuario.setFechaHoraCreacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());

        // Usar TiposUsuarioDAO para obtener el objeto completo
        Integer tipoUsuarioId = this.resultSet.getInt("TIPO_USUARIO_ID");
        TiposUsuarioDTO tipoUsuario = this.tipoUsuarioDAO.obtenerPorId(tipoUsuarioId);
        this.usuario.setTipoUsuario(tipoUsuario);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.usuario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.usuario);
    }

    @Override
    public Integer insertar(UsuariosDTO usuario) {
        this.usuario = usuario;
        return super.insertar();
    }

    @Override
    public UsuariosDTO obtenerPorId(Integer usuarioId) {
        this.usuario = new UsuariosDTO();
        this.usuario.setUsuarioId(usuarioId);
        super.obtenerPorId();
        return this.usuario;
    }

    @Override
    public ArrayList<UsuariosDTO> listarTodos() {
        return (ArrayList<UsuariosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(UsuariosDTO usuario) {
        this.usuario = usuario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(UsuariosDTO usuario) {
        this.usuario = usuario;
        return super.eliminar();
    }
}
