package pe.edu.pucp.kawkiWeb.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.kawkiWeb.db.util.Cifrado;

public class DBManager {

    //final hace referencia a que es un valor constante
    private static final String ARCHIVO_CONFIGURACION = "jdbc.properties";
    private Connection conexion;
    private String driver;
    private String tipo_de_driver;
    private String base_de_datos;
    private String nombre_del_host;
    private String puerto;
    private String usuario;
    private String contraseña;
    private static DBManager dbmanager = null;

    //No va haber métodos selectores, cómo le voy a dar la contraseña a alguien !!!
    private DBManager() {
        //No hace nada el constructor      
    }

    public static DBManager getInstance() {
        if (DBManager.dbmanager == null) {
            DBManager.createInstance(); //createInstance es un metodo estático porque lo llama la clase.    
        }
        return DBManager.dbmanager;
    }

    private static void createInstance() {
        if (DBManager.dbmanager == null) {
            DBManager.dbmanager = new DBManager();
            //Este es el mejor momento para leer los datos del archivo, crear la instancia con los valores.
            DBManager.dbmanager.leer_archivo_de_propiedades();
        }
    }

    public Connection getConnection() {
        try {
            Class.forName(this.driver);
            try {
                this.conexion = DriverManager.getConnection(this.getURL(), this.usuario, Cifrado.descifrarMD5(this.contraseña));
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conexion;
    }

    private void leer_archivo_de_propiedades() {
        // en el caso de "/" hace referencia a la raiz de los archivos de recursos
        // El archivo se encuentra en la carpeta de resources, por eso se agrega el /     
        String nomArchConf = "/" + ARCHIVO_CONFIGURACION;
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(nomArchConf));
            this.driver = properties.getProperty("driver");
            this.tipo_de_driver = properties.getProperty("tipo_de_driver");
            this.base_de_datos = properties.getProperty("base_de_datos");
            this.nombre_del_host = properties.getProperty("nombre_de_host");
            this.puerto = properties.getProperty("puerto");
            this.usuario = properties.getProperty("usuario");
            this.contraseña = properties.getProperty("contrasenha");

        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Es el método que nos va a formar esa URL
    //Con esta URL, el driver puede conectar con la base de datos
    private String getURL() {

        String url = this.tipo_de_driver.concat("://");
        url = url.concat(this.nombre_del_host);
        url = url.concat(":");
        url = url.concat(this.puerto);
        url = url.concat("/");
        url = url.concat(this.base_de_datos);
        url = url.concat("?useSSL=false");
        return url;

    }

}
