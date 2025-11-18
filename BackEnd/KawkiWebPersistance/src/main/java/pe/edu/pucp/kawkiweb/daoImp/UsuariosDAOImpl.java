package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.sql.Types;
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
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false, false));
        this.listaColumnas.add(new Columna("TIPO_USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
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
        this.statement.setInt(10, this.usuario.getActivo() ? 1 : 0);
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
        this.statement.setInt(8, this.usuario.getTipoUsuario().getTipoUsuarioId());
        this.statement.setInt(9, this.usuario.getActivo() ? 1 : 0);
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

    /**
     * Método de instanciación ESTÁNDAR para obtenerPorId(). Hace queries
     * adicionales para traer objetos completos. Se usa cuando se obtiene UN
     * SOLO usuario.
     */
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

        Boolean activo = (Boolean) this.resultSet.getObject("ACTIVO");
        this.usuario.setActivo(activo);
    }

    /**
     * Método de instanciación OPTIMIZADO para listarTodos(). NO hace queries
     * adicionales porque los datos ya vienen del JOIN. Se usa cuando se listan
     * MUCHOS usuarios.
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        this.usuario = new UsuariosDTO();

        // Datos básicos del usuario
        this.usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        this.usuario.setNombre(this.resultSet.getString("NOMBRE"));
        this.usuario.setApePaterno(this.resultSet.getString("APE_PATERNO"));
        this.usuario.setDni(this.resultSet.getString("DNI"));
        this.usuario.setTelefono(this.resultSet.getString("TELEFONO"));
        this.usuario.setCorreo(this.resultSet.getString("CORREO"));
        this.usuario.setNombreUsuario(this.resultSet.getString("NOMBRE_USUARIO"));
        this.usuario.setContrasenha(this.resultSet.getString("CONTRASENHA"));
        this.usuario.setFechaHoraCreacion(this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime());

        // Tipo de usuario (YA VIENE COMPLETO del JOIN - SIN query adicional)
        TiposUsuarioDTO tipoUsuario = new TiposUsuarioDTO();
        tipoUsuario.setTipoUsuarioId(this.resultSet.getInt("TIPO_USUARIO_ID"));
        tipoUsuario.setNombre(this.resultSet.getString("TIPO_USUARIO_NOMBRE"));
        this.usuario.setTipoUsuario(tipoUsuario);

        Boolean activo = (Boolean) this.resultSet.getObject("ACTIVO");
        this.usuario.setActivo(activo);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.usuario = null;
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación ESTÁNDAR. Se
     * usa en obtenerPorId() y otros métodos que NO usan el SP optimizado.
     */
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.usuario);
    }

    /**
     * Agrega objeto a la lista usando el método de instanciación OPTIMIZADO. Se
     * usa en listarTodos() que SÍ usa el SP con JOINs.
     */
    @Override
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetDesdeJoin();
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

    /**
     * Lista todos los usuarios usando el Stored Procedure optimizado. Retorna
     * usuarios con tipo_usuario completo (id y nombre).
     */
    @Override
    public ArrayList<UsuariosDTO> listarTodos() {
        return (ArrayList<UsuariosDTO>) super.listarTodosConProcedimiento(
                "SP_LISTAR_USUARIOS_COMPLETO"
        );
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

    // ========== IMPLEMENTACIÓN DE MÉTODOS CON STORED PROCEDURES ==========
    /**
     * Verifica unicidad usando SP_VERIFICAR_UNICIDAD_USUARIO
     */
    @Override
    public boolean[] verificarUnicidad(String correo, String nombreUsuario, String dni, Integer usuarioIdExcluir) {
        boolean[] resultado = new boolean[3]; // [correoExiste, usuarioExiste, dniExiste]

        try {
            this.abrirConexion();

            // Preparar llamada al stored procedure
            String sql = "{CALL SP_VERIFICAR_UNICIDAD_USUARIO(?, ?, ?, ?, ?, ?, ?)}";
            this.colocarSQLEnStatement(sql);

            // Parámetros IN
            this.statement.setString(1, correo);
            this.statement.setString(2, nombreUsuario);
            this.statement.setString(3, dni);
            if (usuarioIdExcluir == null) {
                this.statement.setNull(4, Types.INTEGER);
            } else {
                this.statement.setInt(4, usuarioIdExcluir);
            }

            // Parámetros OUT
            this.statement.registerOutParameter(5, Types.BOOLEAN); // p_correo_existe
            this.statement.registerOutParameter(6, Types.BOOLEAN); // p_usuario_existe
            this.statement.registerOutParameter(7, Types.BOOLEAN); // p_dni_existe

            // Ejecutar
            this.statement.execute();

            // Obtener resultados
            resultado[0] = this.statement.getBoolean(5); // correoExiste
            resultado[1] = this.statement.getBoolean(6); // usuarioExiste
            resultado[2] = this.statement.getBoolean(7); // dniExiste

        } catch (SQLException ex) {
            System.err.println("Error al verificar unicidad: " + ex);
            // En caso de error, retornar false (no existe)
            resultado[0] = false;
            resultado[1] = false;
            resultado[2] = false;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex);
            }
        }

        return resultado;
    }

    /**
     * Lista usuarios por tipo usando SP_LISTAR_USUARIOS_POR_TIPO
     */
    @Override
    public ArrayList<UsuariosDTO> listarPorTipo(Integer tipoUsuarioId) {
        // Usar el método base ejecutarConsultaProcedimientoLista
        List<UsuariosDTO> lista = this.ejecutarConsultaProcedimientoLista(
                "SP_LISTAR_USUARIOS_POR_TIPO",
                1,
                params -> {
                    try {
                        this.statement.setInt(1, tipoUsuarioId);
                    } catch (SQLException ex) {
                        System.err.println("Error al setear parámetro: " + ex);
                    }
                },
                tipoUsuarioId
        );

        return (ArrayList<UsuariosDTO>) lista;
    }

    /**
     * Cambia contraseña usando SP_CAMBIAR_CONTRASENHA
     */
    @Override
    public ResultadoCambioContrasenha cambiarContrasenha(Integer usuarioId, String contrasenhaActual, String contrasenhaNueva) {
        int codigo = -1;
        String mensaje = "Error desconocido";

        try {
            this.abrirConexion();

            // Preparar llamada al stored procedure
            String sql = "{CALL SP_CAMBIAR_CONTRASENHA(?, ?, ?, ?, ?)}";
            this.colocarSQLEnStatement(sql);

            // Parámetros IN
            this.statement.setInt(1, usuarioId);
            this.statement.setString(2, contrasenhaActual);
            this.statement.setString(3, contrasenhaNueva);

            // Parámetros OUT
            this.statement.registerOutParameter(4, Types.INTEGER); // p_resultado
            this.statement.registerOutParameter(5, Types.VARCHAR); // p_mensaje

            // Ejecutar
            this.statement.execute();

            // Obtener resultados
            codigo = this.statement.getInt(4);
            mensaje = this.statement.getString(5);

        } catch (SQLException ex) {
            System.err.println("Error al cambiar contraseña: " + ex);
            codigo = -1;
            mensaje = "Error en la base de datos: " + ex.getMessage();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex);
            }
        }

        return new ResultadoCambioContrasenha(codigo, mensaje);
    }

    /**
     * Autentica usuario usando SP_AUTENTICAR_USUARIO
     */
    @Override
    public UsuariosDTO autenticar(String nombreUsuarioOCorreo, String contrasenha) {
        // Crear objeto temporal para almacenar el resultado
        UsuariosDTO usuarioAutenticado = null;
        UsuariosDTO temp = this.usuario; // Guardar referencia actual

        try {
            this.abrirConexion();

            // Preparar llamada al stored procedure
            String sql = "{CALL SP_AUTENTICAR_USUARIO(?, ?)}";
            this.colocarSQLEnStatement(sql);

            // Parámetros IN
            this.statement.setString(1, nombreUsuarioOCorreo);
            this.statement.setString(2, contrasenha);

            // Ejecutar
            this.ejecutarSelectEnDB();

            // Si hay resultado, instanciar el usuario
            if (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                usuarioAutenticado = this.usuario;
            }

        } catch (SQLException ex) {
            System.err.println("Error al autenticar usuario: " + ex);
            usuarioAutenticado = null;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex);
            }
            this.usuario = temp; // Restaurar referencia original
        }

        return usuarioAutenticado;
    }
}