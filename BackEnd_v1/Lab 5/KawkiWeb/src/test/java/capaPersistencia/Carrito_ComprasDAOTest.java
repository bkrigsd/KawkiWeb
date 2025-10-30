/*
package capaPersistencia;

import capaDominio.Carrito_ComprasDTO;
import capaDominio.UsuarioDTO;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.Carrito_ComprasDAOImpl;
import capaPersistencia.Implementar.UsuarioDAOImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author beker
 */
/*
public class Carrito_ComprasDAOTest {
    
    private Carrito_ComprasDAO carritoDAO;
    private UsuarioDAO usuarioDAO;
    
    public Carrito_ComprasDAOTest() {
        this.carritoDAO = new Carrito_ComprasDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    @Test
    public void testInsertar() {
        
        ArrayList<Integer> usuariosId = new ArrayList<>();
        ArrayList<Integer> carritosId = new ArrayList<>();
        insertarUsuarios(usuariosId);
        insertarCarritos(usuariosId, carritosId);
        assertEquals(2, carritosId.size());
        limpiarTodo(usuariosId, carritosId);
    }

    private void insertarUsuarios(ArrayList<Integer> usuariosId) {
        
        UsuarioDTO user1 = crearUsuario("Abigail","Vega","Del Rio","abi@alzv.com",
                                        "abi","60050001","900001001");
        usuariosId.add(usuarioDAO.insertar(user1));
        UsuarioDTO user2 = crearUsuario("Jhair","Godoy","Carrillo","relius@ram.com",
                                        "relius","73534223","912343219");
        usuariosId.add(usuarioDAO.insertar(user2));
        UsuarioDTO user3 = crearUsuario("Miguel","Vargas","Romero","kiwi@gvc.com",
                                        "mx","11145793","912343219");
        usuariosId.add(usuarioDAO.insertar(user3));
    }

    private UsuarioDTO crearUsuario(String nombre, String paterno, String materno,
                                    String correo, String username, String dni,
                                    String telefono) {
        
        UsuarioDTO user = new UsuarioDTO();
        user.setNombre(nombre);
        user.setApePaterno(paterno);
        user.setApeMaterno(materno);
        user.setDni(dni);
        user.setFechaNacimiento(LocalDate.of(2004,5,5));
        user.setTelefono(telefono);
        user.setDireccion("Direccion Test");
        user.setCorreo(correo);
        user.setNombreUsuario(username);
        user.setContrasenha("password");
        user.setFechaHoraCreacion(LocalDateTime.now());
        user.setTipoUsuario(TipoUsuario.CLIENTE);
        return user;
    }
    
    private void insertarCarritos(ArrayList<Integer> usuariosId, ArrayList<Integer> carritosId) {
        
        Carrito_ComprasDTO car1 = new Carrito_ComprasDTO();
        car1.setUsuario_id(usuariosId.get(0));
        car1.setTotal(175.75);
        
        Integer result = carritoDAO.insertar(car1);
        assertTrue(result != 0);
        carritosId.add(result);
        
        Carrito_ComprasDTO car2 = new Carrito_ComprasDTO();
        car2.setUsuario_id(usuariosId.get(1));
        car2.setTotal(217.60);
        
        result = carritoDAO.insertar(car2);
        assertTrue(result != 0);
        carritosId.add(result);
    }

    private void limpiarTodo(ArrayList<Integer> usuariosId, ArrayList<Integer> carritosId) {
        
        for (Integer carritoId : carritosId) {
            try {
                Carrito_ComprasDTO car = carritoDAO.obtenerPorId(carritoId);
                if (car != null) this.carritoDAO.eliminar(car);
            } catch (Exception e) {
                System.err.println("no se elimino");
            }
        }
        
        for (Integer userId : usuariosId) {
            try {
                UsuarioDTO user = usuarioDAO.obtenerPorId(userId);
                if (user != null) this.usuarioDAO.eliminar(user);
            } catch (Exception e) {
                System.err.println("no se elimino");
            }
        }
    }
    
    @Test
    public void testObtenerPorId() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        
        insertarUsuarios(usuariosIds);
        insertarCarritos(usuariosIds, carritosIds);
        
        Carrito_ComprasDTO carrito = carritoDAO.obtenerPorId(carritosIds.get(0));
        assertEquals(carritosIds.get(0), carrito.getCarrito_id());
        
        carrito = carritoDAO.obtenerPorId(carritosIds.get(1));
        assertEquals(carritosIds.get(1), carrito.getCarrito_id());
        
        limpiarTodo(usuariosIds, carritosIds);
    }
    
    @Test
    public void testListarTodos() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        
        insertarUsuarios(usuariosIds);
        insertarCarritos(usuariosIds, carritosIds);
        
        ArrayList<Carrito_ComprasDTO> listaCarritos = carritoDAO.listarTodos();
        assertEquals(carritosIds.size(), listaCarritos.size());
        
        limpiarTodo(usuariosIds, carritosIds);
    }
    
    @Test
    public void testModificar() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        
        insertarUsuarios(usuariosIds);
        insertarCarritos(usuariosIds, carritosIds);
        
        ArrayList<Carrito_ComprasDTO> listaCarritos = carritoDAO.listarTodos();
        for (Carrito_ComprasDTO carrito : listaCarritos) {
            carrito.setTotal(carrito.getTotal() + 50.0);
            carritoDAO.modificar(carrito);
        }
        
        ArrayList<Carrito_ComprasDTO> listaModificada = carritoDAO.listarTodos();
        for (int i = 0; i < listaCarritos.size(); i++) {
            assertEquals(listaCarritos.get(i).getTotal(), listaModificada.get(i).getTotal());
        }
        
        limpiarTodo(usuariosIds, carritosIds);
    }
    
    @Test
    public void testEliminar() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        
        insertarUsuarios(usuariosIds);
        insertarCarritos(usuariosIds, carritosIds);
        
        Carrito_ComprasDTO carrito = carritoDAO.obtenerPorId(carritosIds.get(0));
        Integer resultado = carritoDAO.eliminar(carrito);
        assertTrue(resultado > 0);
        
        Carrito_ComprasDTO eliminado = carritoDAO.obtenerPorId(carritosIds.get(0));
        assertNull(eliminado);
        
        // Eliminar el carrito restante y usuarios
        carritosIds.remove(0);
        limpiarTodo(usuariosIds, carritosIds);
    }
}
*/