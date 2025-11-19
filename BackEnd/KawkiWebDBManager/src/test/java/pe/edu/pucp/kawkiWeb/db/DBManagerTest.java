

package pe.edu.pucp.kawkiWeb.db;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DBManagerTest {
    
    public DBManagerTest() {
    }

    @Test
    public void testGetInstance() {
        //Está mal, pues el constructor es privado
        //DBManager dbManager = new DBManager();
        System.out.println("testGetInstance");
        DBManager dbManager = DBManager.getInstance();
        assertNotNull(dbManager);
    }
    @Test
    public void testGetConnection(){
        System.out.println("testGetConnection");
        DBManager dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConnection();
        assertNotNull(connection);
    }
    
}
