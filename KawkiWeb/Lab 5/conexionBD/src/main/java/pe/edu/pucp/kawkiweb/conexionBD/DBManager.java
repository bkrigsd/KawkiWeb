/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.kawkiweb.conexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.kawkiweb.conexionBD.util.Cifrado;

/**
 *
 * @author ANGELINA
 */
public class DBManager {
    private static final String ARCHIVO_CONFIGURACION = "jdbc.properties";
    
    private Connection conexion;
    private String driver;
    private String tipo_de_driver;
    private String base_de_datos;
    private String nombre_del_host;
    private String puerto;
    private String usuario;
    private String contraseña;
    private static DBManager dbManager = null;
    
    private DBManager(){
        
    }
    
    private static void createInstance(){
        if(DBManager.dbManager == null){
            DBManager.dbManager = new DBManager();
            DBManager.dbManager.leer_archivo_de_propiedades();
        } 
    }
    
    public static DBManager getInstance(){
        if(DBManager.dbManager == null){
            createInstance();
        }
        return DBManager.dbManager;
    }
    
    public Connection getConnection(){
        try {
            Class.forName(this.driver);
            this.conexion = DriverManager.getConnection(this.getURL(),this.usuario, Cifrado.descifrarMD5(this.contraseña));
        } catch (ClassNotFoundException ex) {
            System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (SQLException ex) {
            System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return this.conexion;
    }

    private void leer_archivo_de_propiedades() {
        String nmArchivoConf = '/' + DBManager.ARCHIVO_CONFIGURACION;
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(nmArchivoConf));
            this.driver=properties.getProperty("driver");
            this.tipo_de_driver=properties.getProperty("tipo_de_driver");
            this.base_de_datos=properties.getProperty("base_de_datos");
            this.nombre_del_host=properties.getProperty("nombre_de_host");
            this.puerto=properties.getProperty("puerto");
            this.usuario=properties.getProperty("usuario");
            this.contraseña=properties.getProperty("contrasenha");
        } catch (IOException ex) {
            System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private String getURL() {
        String url = this.tipo_de_driver.concat("://");
        url = url.concat(this.nombre_del_host);
        url = url.concat(":");
        url = url.concat(this.puerto);
        url = url.concat("/");
        url = url.concat(this.base_de_datos);
        return url;
    }
}
