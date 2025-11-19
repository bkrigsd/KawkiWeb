package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.edu.pucp.kawkiWeb.db.DBManager;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.daoImp.util.Tipo_Operacion;

public abstract class BaseDAOImpl {

    protected String nombre_tabla;
    protected ArrayList<Columna> listaColumnas;
    protected Boolean retornarLlavePrimaria;
    protected Connection conexion;
    protected CallableStatement statement;
    protected ResultSet resultSet;

    public BaseDAOImpl(String nombre_tabla) {
        this.nombre_tabla = nombre_tabla;
        this.retornarLlavePrimaria = false;
        this.incluirListaDeColumnas();
    }

    private void incluirListaDeColumnas() {
        this.listaColumnas = new ArrayList<>();
        this.configurarListaDeColumnas();
    }

    protected abstract void configurarListaDeColumnas();

    protected void abrirConexion() {
        this.conexion = DBManager.getInstance().getConnection();
    }

    protected void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
        }
    }

    protected void iniciarTransaccion() throws SQLException {
        this.abrirConexion();
        this.conexion.setAutoCommit(false);
    }

    protected void comitarTransaccion() throws SQLException {
        this.conexion.commit();
    }

    protected void rollbackTransaccion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.rollback();
        }
    }

    protected void colocarSQLEnStatement(String sql) throws SQLException {
        System.out.println(sql);
        this.statement = this.conexion.prepareCall(sql);
    }

    protected Integer ejecutarDMLEnBD() throws SQLException {
        return this.statement.executeUpdate();
    }

    protected void ejecutarSelectEnDB() throws SQLException {
        this.resultSet = this.statement.executeQuery();
    }

    protected Integer insertar() {
        return this.ejecuta_DML(Tipo_Operacion.INSERTAR);
    }

    protected Integer modificar() {
        return this.ejecuta_DML(Tipo_Operacion.MODIFICAR);
    }

    protected Integer eliminar() {
        return this.ejecuta_DML(Tipo_Operacion.ELIMINAR);
    }

    private Integer ejecuta_DML(Tipo_Operacion tipo_operacion) {
        Integer resultado = 0;
        try {
            this.iniciarTransaccion();
            String sql = null;
            switch (tipo_operacion) {
                case Tipo_Operacion.INSERTAR:
                    sql = this.generarSQLParaInsercion();
                    break;
                case Tipo_Operacion.MODIFICAR:
                    sql = this.generarSQLParaModificacion();
                    break;
                case Tipo_Operacion.ELIMINAR:
                    sql = this.generarSQLParaEliminacion();
                    break;
            }
            this.colocarSQLEnStatement(sql);
            switch (tipo_operacion) {
                case Tipo_Operacion.INSERTAR:
                    this.incluirValorDeParametrosParaInsercion();
                    break;
                case Tipo_Operacion.MODIFICAR:
                    this.incluirValorDeParametrosParaModificacion();
                    break;
                case Tipo_Operacion.ELIMINAR:
                    this.incluirValorDeParametrosParaEliminacion();
                    break;
            }
            resultado = this.ejecutarDMLEnBD();
            if (this.retornarLlavePrimaria && tipo_operacion == Tipo_Operacion.INSERTAR) {
                resultado = this.retornarUltimoAutoGenerado();
            }
            this.comitarTransaccion();
        } catch (SQLException ex) {
            System.err.println("Error al intentar insertar - " + ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return resultado;
    }

    protected String generarSQLParaInsercion() {
        //La sentencia que se generará es similiar a
        //INSERT INTO INV_ALMACENES (NOMBRE, ALMACEN_CENTRAL) VALUES (?,?)
        String sql = "INSERT INTO ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat("(");
        String sql_columnas = "";
        String sql_parametros = "";
        for (Columna columna : this.listaColumnas) {
            if (!columna.getEsAutoGenerado()) {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                    sql_parametros = sql_parametros.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_parametros = sql_parametros.concat("?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(") VALUES (");
        sql = sql.concat(sql_parametros);
        sql = sql.concat(")");
        return sql;
    }

    protected String generarSQLParaModificacion() {
        //sentencia SQL a generar es similar a 
        //UPDATE INV_ALMACENES SET NOMBRE=?, ALMACEN_CENTRAL=? WHERE ALMACEN_ID=?
        String sql = "UPDATE ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" SET ");
        String sql_columnas = "";
        String sql_predicado = "";

        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(" AND ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            } else if (columna.getEsModificable()) { // NUEVA VALIDACIÓN
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_columnas = sql_columnas.concat("=?");
            }
        }

        sql = sql.concat(sql_columnas);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaEliminacion() {
        //sentencia SQL a generar es similar a 
        //DELETE FROM INV_ALMACENES WHERE ALMACEN_ID=?
        String sql = "DELETE FROM ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" WHERE ");
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
        }
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaObtenerPorId() {
        //sentencia SQL a generar es similar a 
        //SELECT ALMACEN_ID, NOMBRE, ALMACEN_CENTRAL FROM INV_ALMACENES WHERE ALMACEN_ID = ?
        String sql = "SELECT ";
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaListarTodos() {
        //sentencia SQL a generar es similar a 
        //SELECT ALMACEN_ID, NOMBRE, ALMACEN_CENTRAL FROM INV_ALMACENES
        String sql = "SELECT ";
        String sql_columnas = "";
        for (Columna columna : this.listaColumnas) {
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombre_tabla);
        return sql;
    }

    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Integer retornarUltimoAutoGenerado() {
        Integer resultado = null;
        try {
            String sql = DBManager.getInstance().retornarSQLParaUltimoAutoGenerado();
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.next()) {
                resultado = this.resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar retornarUltimoAutoGenerado - " + ex);
        }
        return resultado;
    }

    public void obtenerPorId() {
        try {
            this.abrirConexion();
            String sql = this.generarSQLParaObtenerPorId();
            this.colocarSQLEnStatement(sql);
            this.incluirValorDeParametrosParaObtenerPorId();
            this.ejecutarSelectEnDB();
            if (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
            } else {
                this.limpiarObjetoDelResultSet();
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar obtenerPorId - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }

    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        throw new UnsupportedOperationException("El método no ha sido sobreescrito."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    protected void instanciarObjetoDelResultSet() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Método template para instanciar objetos cuando los datos vienen de un
     * JOIN. Por defecto, usa instanciarObjetoDelResultSet() como fallback para
     * mantener retrocompatibilidad con DAOs que no tienen implementación
     * optimizada.
     *
     * @throws SQLException Si hay error al procesar el ResultSet
     */
    protected void instanciarObjetoDelResultSetDesdeJoin() throws SQLException {
        // Por defecto, usa el método estándar de instanciación
        this.instanciarObjetoDelResultSet();
    }

    protected void limpiarObjetoDelResultSet() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List listarTodos() {
        String sql = null;
        Consumer incluirValorDeParametros = null;
        Object parametros = null;
        return this.listarTodos(sql, incluirValorDeParametros, parametros);
    }

    public List listarTodos(String sql, Consumer incluirValorDeParametros, Object parametros) {
        List lista = new ArrayList<>();
        try {
            this.abrirConexion();
            if (sql == null) {
                sql = this.generarSQLParaListarTodos();
            }
            this.colocarSQLEnStatement(sql);
            if (incluirValorDeParametros != null) {
                incluirValorDeParametros.accept(parametros);
            }
            this.ejecutarSelectEnDB();
            while (this.resultSet.next()) {
                agregarObjetoALaLista(lista);
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar listarTodos - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return lista;
    }

    protected void agregarObjetoALaLista(List lista) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Método template que los DAOs hijos pueden sobreescribir para agregar
     * objetos a la lista cuando los datos vienen de un JOIN (stored procedure).
     *
     * Por defecto, usa el método estándar agregarObjetoALaLista(), pero los
     * DAOs pueden implementar su propia lógica para manejar los campos
     * adicionales que vienen del JOIN.
     *
     * @param lista Lista donde se agregarán los objetos
     * @throws SQLException Si hay error al procesar el ResultSet
     */
    protected void agregarObjetoALaListaDesdeJoin(List lista) throws SQLException {
        // Por defecto, usa el método estándar de instanciación
        this.agregarObjetoALaLista(lista);
    }

    /// MÉTODOS PARA PROCEDIMIENTOS ALMACENADOS
    
    /**
    * Ejecuta un stored procedure optimizado para listar todos los registros
    * con JOINs incluidos.
    * Este método está diseñado para trabajar con SPs que hacen JOINs y 
    * retornan datos completos, evitando queries adicionales.
    * 
    * @param nombreProcedimiento Nombre del SP (ej: "SP_LISTAR_PRODUCTOS_COMPLETO")
    * @return Lista de objetos del tipo correspondiente
    */
    protected List listarTodosConProcedimiento(String nombreProcedimiento) {

        List lista = new ArrayList<>();

        try {
            this.abrirConexion();

            // Generar llamada al SP: {CALL nombre_procedimiento()}
            String sql = generarLlamadaSP(nombreProcedimiento, 0);

            this.colocarSQLEnStatement(sql);
            this.ejecutarSelectEnDB();

            while (this.resultSet.next()) {
                // Llamar al método que cada DAO puede sobreescribir
                this.agregarObjetoALaListaDesdeJoin(lista);
            }

        } catch (SQLException ex) {
            System.err.println("Error al listar todos con procedimiento: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }

        return lista;
    }

    /**
     * Ejecuta un procedimiento almacenado que retorna UN solo registro.
     * Útil para búsquedas personalizadas más allá del CRUD básico.
     * 
     * VERSIÓN ESTÁNDAR (sin JOINs): Usa instanciarObjetoDelResultSet()
     * Para SPs con JOINs, usar ejecutarConsultaProcedimientoConJoin()
     *
     * @param nombreProcedimiento Nombre del procedimiento (sin CALL ni paréntesis)
     * @param cantidadParametros Número de parámetros que acepta el procedimiento
     * @param incluirParametros Consumer para setear los parámetros en el statement
     * @param parametros Objeto con los datos necesarios para los parámetros
     */
    protected void ejecutarConsultaProcedimiento(
            String nombreProcedimiento,
            Integer cantidadParametros,
            Consumer incluirParametros,
            Object parametros) {

        try {
            this.abrirConexion();

            // Genera la llamada: {CALL nombre_procedimiento(?, ?)}
            String sql = generarLlamadaSP(nombreProcedimiento, cantidadParametros);

            this.colocarSQLEnStatement(sql);

            if (incluirParametros != null) {
                incluirParametros.accept(parametros);
            }

            this.ejecutarSelectEnDB();

            if (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
            } else {
                this.limpiarObjetoDelResultSet();
            }

        } catch (SQLException ex) {
            System.err.println("Error al ejecutar consulta con procedimiento: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }
    }

    /**
     * Ejecuta un procedimiento almacenado que retorna UN solo registro CON JOINs.
     * Útil para búsquedas personalizadas que traen datos relacionados.
     * 
     * VERSIÓN OPTIMIZADA (con JOINs): Usa instanciarObjetoDelResultSetDesdeJoin()
     * Para SPs sin JOINs, usar ejecutarConsultaProcedimiento()
     *
     * @param nombreProcedimiento Nombre del procedimiento (sin CALL ni paréntesis)
     * @param cantidadParametros Número de parámetros que acepta el procedimiento
     * @param incluirParametros Consumer para setear los parámetros en el statement
     * @param parametros Objeto con los datos necesarios para los parámetros
     */
    protected void ejecutarConsultaProcedimientoConJoin(
            String nombreProcedimiento,
            Integer cantidadParametros,
            Consumer incluirParametros,
            Object parametros) {

        try {
            this.abrirConexion();
            String sql = generarLlamadaSP(nombreProcedimiento, cantidadParametros);
            this.colocarSQLEnStatement(sql);

            if (incluirParametros != null) {
                incluirParametros.accept(parametros);
            }

            this.ejecutarSelectEnDB();

            if (this.resultSet.next()) {
                // CLAVE: Usa el método OPTIMIZADO para JOINs
                this.instanciarObjetoDelResultSetDesdeJoin();
            } else {
                this.limpiarObjetoDelResultSet();
            }

        } catch (SQLException ex) {
            System.err.println("Error al ejecutar consulta con procedimiento (JOIN): " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }
    }
    
    /**
     * Ejecuta un procedimiento almacenado que retorna MÚLTIPLES registros.
     * Útil para listados personalizados con filtros complejos.
     * 
     * OPTIMIZACIÓN: Usa agregarObjetoALaListaDesdeJoin() para SPs con JOINs
     *
     * @param nombreProcedimiento Nombre del procedimiento
     * @param cantidadParametros Número de parámetros
     * @param incluirParametros Consumer para setear los parámetros
     * @param parametros Objeto con los datos necesarios
     * @return Lista de objetos
     */
    protected List ejecutarConsultaProcedimientoLista(
            String nombreProcedimiento,
            Integer cantidadParametros,
            Consumer incluirParametros,
            Object parametros) {

        List lista = new ArrayList<>();

        try {
            this.abrirConexion();
            String sql = generarLlamadaSP(nombreProcedimiento, cantidadParametros);
            this.colocarSQLEnStatement(sql);

            if (incluirParametros != null) {
                incluirParametros.accept(parametros);
            }

            this.ejecutarSelectEnDB();

            while (this.resultSet.next()) {
                // OPTIMIZACIÓN: Ahora usa el método optimizado para JOINs
                this.agregarObjetoALaListaDesdeJoin(lista);
            }

        } catch (SQLException ex) {
            System.err.println("Error al ejecutar consulta lista con procedimiento: " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex);
            }
        }

        return lista;
    }

    /**
     * Genera la sintaxis de llamada a un procedimiento almacenado Ejemplo:
     * {CALL nombre_procedimiento(?, ?)}
     */
    private String generarLlamadaSP(String nombreProcedimiento, Integer cantidadParametros) {
        StringBuilder sql = new StringBuilder("{CALL ");
        sql.append(nombreProcedimiento);
        sql.append("(");

        for (int i = 0; i < cantidadParametros; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }

        sql.append(")}");
        return sql.toString();
    }


/// MÉTODOS PARA PROCEDIMIENTOS ALMACENADOS ANTIGUOS
    
//    public void ejecutarProcedimientoAlmacenado(String sql,
//            Boolean conTransaccion) {
//        Consumer incluirValorDelParametro = null;
//        Object parametros = null;
//        this.ejecutarProcedimientoAlmacenado(sql, incluirValorDelParametro, parametros, conTransaccion);
//    }
//
//    public void ejecutarProcedimientoAlmacenado(String sql,
//            Consumer incluirValorDelParametro,
//            Object parametros,
//            Boolean conTransaccion) {
//        try {
//            if (conTransaccion) {
//                this.iniciarTransaccion();
//            } else {
//                this.abrirConexion();
//            }
//            this.colocarSQLEnStatement(sql);
//            if (incluirValorDelParametro != null) {
//                incluirValorDelParametro.accept(parametros);
//            }
//            this.ejecutarDMLEnBD();
//            if (conTransaccion) {
//                this.comitarTransaccion();
//            }
//        } catch (SQLException ex) {
//            System.err.println("Error al intentar ejecutar procedimiento almacenado: " + ex);
//            try {
//                if (conTransaccion) {
//                    this.rollbackTransaccion();
//                }
//            } catch (SQLException ex1) {
//                System.err.println("Error al hacer rollback - " + ex);
//            }
//        } finally {
//            try {
//                this.cerrarConexion();
//            } catch (SQLException ex) {
//                System.err.println("Error al cerrar la conexión - " + ex);
//            }
//        }
//    }
}
