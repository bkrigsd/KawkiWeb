/*
package capaPersistencia;

import capaDominio.Carrito_ComprasDTO;
import capaDominio.Detalle_CarritoDTO;
import capaDominio.UsuarioDTO;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.Carrito_ComprasDAOImpl;
import capaPersistencia.Implementar.Detalle_CarritoDAOImpl;
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
public class Detalle_CarritoDAOTest {
    
    private Detalle_CarritoDAO detalleCarritoDAO;
    private Carrito_ComprasDAO carritoDAO;
    private UsuarioDAO usuarioDAO;
    
    public Detalle_CarritoDAOTest() {
        this.detalleCarritoDAO = new Detalle_CarritoDAOImpl();
        this.carritoDAO = new Carrito_ComprasDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Test
    public void testInsertar() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        ArrayList<Integer> detallesIds = new ArrayList<>();
        
        crearDatos(usuariosIds, carritosIds);
        insertarDetalles(carritosIds, detallesIds);
        
        assertEquals(2, detallesIds.size());
        
        limpiarTodo(usuariosIds, carritosIds, detallesIds);
    }
    
    @Test
    public void testObtenerPorId() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        ArrayList<Integer> detallesIds = new ArrayList<>();
        
        crearDatos(usuariosIds, carritosIds);
        insertarDetalles(carritosIds, detallesIds);

        Detalle_CarritoDTO detalle = detalleCarritoDAO.obtenerPorId(detallesIds.get(0));
        assertEquals(detallesIds.get(0), detalle.getDetalle_carrito_id());

        detalle = detalleCarritoDAO.obtenerPorId(detallesIds.get(1));
        assertEquals(detallesIds.get(1), detalle.getDetalle_carrito_id());
        
        limpiarTodo(usuariosIds, carritosIds, detallesIds);
    }

    @Test
    public void testListarTodos() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        ArrayList<Integer> detallesIds = new ArrayList<>();
        
        crearDatos(usuariosIds, carritosIds);
        insertarDetalles(carritosIds, detallesIds);

        ArrayList<Detalle_CarritoDTO> lista = detalleCarritoDAO.listarTodos();
        assertEquals(detallesIds.size(), lista.size());
        
        limpiarTodo(usuariosIds, carritosIds, detallesIds);
    }

    @Test
    public void testModificar() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        ArrayList<Integer> detallesIds = new ArrayList<>();
        
        crearDatos(usuariosIds, carritosIds);
        insertarDetalles(carritosIds, detallesIds);

        ArrayList<Detalle_CarritoDTO> lista = detalleCarritoDAO.listarTodos();
        for (Detalle_CarritoDTO detalle : lista) {
            detalle.setCantidad(detalle.getCantidad() + 1);
            detalle.setPrecio_unitario(99.99);
            detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecio_unitario());
            detalleCarritoDAO.modificar(detalle);
        }

        ArrayList<Detalle_CarritoDTO> listaMod = detalleCarritoDAO.listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            assertEquals(lista.get(i).getCantidad(), listaMod.get(i).getCantidad());
            assertEquals(lista.get(i).getPrecio_unitario(), listaMod.get(i).getPrecio_unitario());
            assertEquals(lista.get(i).getSubtotal(), listaMod.get(i).getSubtotal());
        }
        
        limpiarTodo(usuariosIds, carritosIds, detallesIds);
    }

    @Test
    public void testEliminar() {
        ArrayList<Integer> usuariosIds = new ArrayList<>();
        ArrayList<Integer> carritosIds = new ArrayList<>();
        ArrayList<Integer> detallesIds = new ArrayList<>();
        
        crearDatos(usuariosIds, carritosIds);
        insertarDetalles(carritosIds, detallesIds);

        Detalle_CarritoDTO detalle = detalleCarritoDAO.obtenerPorId(detallesIds.get(0));
        Integer resultado = detalleCarritoDAO.eliminar(detalle);
        assertTrue(resultado > 0);

        Detalle_CarritoDTO eliminado = detalleCarritoDAO.obtenerPorId(detallesIds.get(0));
        assertNull(eliminado);
        
        // Eliminar el detalle restante y datos
        detallesIds.remove(0);
        limpiarTodo(usuariosIds, carritosIds, detallesIds);
    }
    
    private void crearDatos(ArrayList<Integer> usuariosIds, ArrayList<Integer> carritosIds) {
        // Crear usuario
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Abigail");
        usuario.setApePaterno("Vega");
        usuario.setApeMaterno("Del Rio");
        usuario.setDni("60050001");
        usuario.setFechaNacimiento(LocalDate.of(2005, 6, 17));
        usuario.setTelefono("900001001");
        usuario.setDireccion("Dirección test");
        usuario.setCorreo("abi@alzv.com");
        usuario.setNombreUsuario("abi");
        usuario.setContrasenha("password");
        usuario.setFechaHoraCreacion(LocalDateTime.now());
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        
        Integer usuarioId = usuarioDAO.insertar(usuario);
        usuariosIds.add(usuarioId);
        
        // Crear carrito
        Carrito_ComprasDTO carrito = new Carrito_ComprasDTO();
        carrito.setUsuario_id(usuarioId);
        carrito.setTotal(0.0);
        
        Integer carritoId = carritoDAO.insertar(carrito);
        carritosIds.add(carritoId);
    }

    private void insertarDetalles(ArrayList<Integer> carritosIds, ArrayList<Integer> detallesIds) {
        Integer carritoId = carritosIds.get(0);
        
        Detalle_CarritoDTO detalle1 = new Detalle_CarritoDTO();
        detalle1.setCarrito_id(carritoId);
        detalle1.setProd_variante_id(1);
        detalle1.setCantidad(2);
        detalle1.setPrecio_unitario(50.0);
        detalle1.setSubtotal(100.0);

        Integer resultado1 = detalleCarritoDAO.insertar(detalle1);
        assertTrue(resultado1 > 0);
        detallesIds.add(resultado1);
        
        Detalle_CarritoDTO detalle2 = new Detalle_CarritoDTO();
        detalle2.setCarrito_id(carritoId);
        detalle2.setProd_variante_id(2);
        detalle2.setCantidad(3);
        detalle2.setPrecio_unitario(30.0);
        detalle2.setSubtotal(90.0);

        Integer resultado2 = detalleCarritoDAO.insertar(detalle2);
        assertTrue(resultado2 > 0);
        detallesIds.add(resultado2);
    }
    
    private void limpiarTodo(ArrayList<Integer> usuariosIds, ArrayList<Integer> carritosIds, ArrayList<Integer> detallesIds) {
        // Limpiar detalles primero
        for (Integer detalleId : detallesIds) {
            try {
                Detalle_CarritoDTO detalle = detalleCarritoDAO.obtenerPorId(detalleId);
                if (detalle != null) detalleCarritoDAO.eliminar(detalle);
            } catch (Exception e) {
                // Ignorar errores
            }
        }
        
        // Limpiar carritos después
        for (Integer carritoId : carritosIds) {
            try {
                Carrito_ComprasDTO carrito = carritoDAO.obtenerPorId(carritoId);
                if (carrito != null) carritoDAO.eliminar(carrito);
            } catch (Exception e) {
                // Ignorar errores
            }
        }
        
        // Limpiar usuarios al final
        for (Integer usuarioId : usuariosIds) {
            try {
                UsuarioDTO usuario = usuarioDAO.obtenerPorId(usuarioId);
                if (usuario != null) usuarioDAO.eliminar(usuario);
            } catch (Exception e) {
                // Ignorar errores
            }
        }
    }
}
*/