package capaPersistencia.Implementar;

import capaDominio.UsuarioDTO;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.util.Columna;
import capaPersistencia.UsuarioDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl extends DAOImplBase implements UsuarioDAO {

    private UsuarioDTO usuario;

    public UsuarioDAOImpl() {
        super("USUARIOS");
        this.usuario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("APE_PATERNO", false, false));
        this.listaColumnas.add(new Columna("APE_MATERNO", false, false));
        this.listaColumnas.add(new Columna("DNI", false, false));
        this.listaColumnas.add(new Columna("FECHA_NACIMIENTO", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("NOMBRE_USUARIO", false, false));
        this.listaColumnas.add(new Columna("CONTRASENHA", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
        this.listaColumnas.add(new Columna("TIPO_USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("RUC",false,false));
        this.listaColumnas.add(new Columna("RAZON_SOCIAL",false,false));
        this.listaColumnas.add(new Columna("DIRECCION_FISCAL",false,false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.usuario.getNombre());
        this.statement.setString(2, this.usuario.getApePaterno());
        this.statement.setString(3, this.usuario.getApeMaterno());
        this.statement.setString(4, this.usuario.getDni());
        this.statement.setDate(5, java.sql.Date.valueOf(this.usuario.getFechaNacimiento()));
        this.statement.setString(6, this.usuario.getTelefono());
        this.statement.setString(7, this.usuario.getDireccion());
        this.statement.setString(8, this.usuario.getCorreo());
        this.statement.setString(9, this.usuario.getNombreUsuario());
        this.statement.setString(10, this.usuario.getContrasenha());
        LocalDateTime fecha = this.usuario.getFechaHoraCreacion();
        if (fecha != null) {
            fecha = fecha.truncatedTo(ChronoUnit.MILLIS);
        }
        this.statement.setTimestamp(11, java.sql.Timestamp.valueOf(fecha));
        this.statement.setInt(12, this.usuario.getTipoUsuario().ordinal());
        this.statement.setString(13, this.usuario.getRuc());
        this.statement.setString(14, this.usuario.getRazon_social());
        this.statement.setString(15, this.usuario.getDireccion_fiscal());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.usuario.getNombre());
        this.statement.setString(2, this.usuario.getApePaterno());
        this.statement.setString(3, this.usuario.getApeMaterno());
        this.statement.setString(4, this.usuario.getDni());
        this.statement.setDate(5, java.sql.Date.valueOf(this.usuario.getFechaNacimiento()));
        this.statement.setString(6, this.usuario.getTelefono());
        this.statement.setString(7, this.usuario.getDireccion());
        this.statement.setString(8, this.usuario.getCorreo());
        this.statement.setString(9, this.usuario.getNombreUsuario());
        this.statement.setString(10, this.usuario.getContrasenha());
        this.statement.setTimestamp(11, java.sql.Timestamp.valueOf(this.usuario.getFechaHoraCreacion()));
        this.statement.setInt(12, this.usuario.getTipoUsuario().ordinal());
        this.statement.setString(13, this.usuario.getRuc());
        this.statement.setString(14, this.usuario.getRazon_social());
        this.statement.setString(15, this.usuario.getDireccion_fiscal());
        this.statement.setInt(16, this.usuario.getUsuarioId());
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
        this.usuario = new UsuarioDTO();
        this.usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        this.usuario.setNombre(this.resultSet.getString("NOMBRE"));
        this.usuario.setApePaterno(this.resultSet.getString("APE_PATERNO"));
        this.usuario.setApeMaterno(this.resultSet.getString("APE_MATERNO"));
        this.usuario.setDni(this.resultSet.getString("DNI"));
        this.usuario.setFechaNacimiento(this.resultSet.getDate("FECHA_NACIMIENTO").toLocalDate());
        this.usuario.setTelefono(this.resultSet.getString("TELEFONO"));
        this.usuario.setDireccion(this.resultSet.getString("DIRECCION"));
        this.usuario.setCorreo(this.resultSet.getString("CORREO"));
        this.usuario.setNombreUsuario(this.resultSet.getString("NOMBRE_USUARIO"));
        this.usuario.setContrasenha(this.resultSet.getString("CONTRASENHA"));
        this.usuario.setFechaHoraCreacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());
        int tipoId = this.resultSet.getInt("TIPO_USUARIO_ID");
        this.usuario.setTipoUsuario(TipoUsuario.values()[tipoId]);
        this.usuario.setRuc(this.resultSet.getString("RUC"));
        this.usuario.setRazon_social(this.resultSet.getString("RAZON_SOCIAL"));
        this.usuario.setDireccion_fiscal(this.resultSet.getString("DIRECCION_FISCAL"));
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

    // Métodos de UsuarioDAO
    @Override
    public Integer insertar(UsuarioDTO usuario) {
        this.usuario = usuario;
        return super.insertar();
    }

    @Override
    public UsuarioDTO obtenerPorId(Integer usuarioId) {
        this.usuario = new UsuarioDTO();
        this.usuario.setUsuarioId(usuarioId);
        super.obtenerPorId();
        return this.usuario;
    }

    @Override
    public ArrayList<UsuarioDTO> listarTodos() {
        return (ArrayList<UsuarioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(UsuarioDTO usuario) {
        this.usuario = usuario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(UsuarioDTO usuario) {
        this.usuario = usuario;
        return super.eliminar();
    }

}
