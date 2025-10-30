package pe.edu.pucp.kawkiweb.conexionBD;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.kawkiweb.conexionBD.util.Cifrado;

public class DBManagerTest {

    public DBManagerTest() {
    }

    @Test
    public void testGetInstance() {
        System.out.println("testGetInstance");
        DBManager dbManager = DBManager.getInstance();
        assertNotNull(dbManager);
    }

    @Test
    public void testGetConnection() {
        System.out.println("testGetConnection");
        DBManager dbManager = DBManager.getInstance();
        Connection conexion = dbManager.getConnection();
        //assertNotNull(conexion);
        if (conexion == null) {
            System.out.println("No se ha conectado con la bd");
        }
    }

    @Test
    //cifrado
    public void testCifrado() {
        String original = "kawkiweb2025";
        String resultadoCifrado = Cifrado.cifrarMD5(original);
        System.out.println("Texto original: " + original);
        System.out.println("Texto cifrado : " + resultadoCifrado);
    }

}
