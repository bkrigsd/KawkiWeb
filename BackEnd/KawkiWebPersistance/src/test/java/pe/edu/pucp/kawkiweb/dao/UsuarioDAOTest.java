package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;
    private PedidoDAO pedidoDAO;

    public UsuarioDAOTest() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
    }

    @Test
    public void testInsertar() {
        System.out.println("Insertar");
        ArrayList<Integer> listaUsuariosId = new ArrayList<>();
        this.insertarAlmacenes(listaUsuariosId);
        this.eliminarTodo();
    }

    private void insertarAlmacenes(ArrayList<Integer> listaUsuariosId) {
        UsuariosDTO usuarioDTO = new UsuariosDTO();

        // Crear TipoUsuario para CLIENTE
        TiposUsuarioDTO tipoCliente = new TiposUsuarioDTO();
        tipoCliente.setTipoUsuarioId(TiposUsuarioDTO.ID_CLIENTE);
        tipoCliente.setNombre(TiposUsuarioDTO.NOMBRE_CLIENTE);

        usuarioDTO.setNombre("Eros");
        usuarioDTO.setApePaterno("Sotelo");
        usuarioDTO.setApeMaterno("La Torre");
        usuarioDTO.setDni("77722211");
        usuarioDTO.setFechaNacimiento(LocalDate.now());
        usuarioDTO.setTelefono("999111555");
        usuarioDTO.setDireccion("Calle 20. Las Casuarinas, La Molina");
        usuarioDTO.setCorreo("soygenialGaa@gmail.com");
        usuarioDTO.setNombreUsuario("LalaMasna");
        usuarioDTO.setContrasenha("jijiji321");
        usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
        usuarioDTO.setTipoUsuario(tipoCliente);

        Integer resultado = this.usuarioDAO.insertar(usuarioDTO);
        assertTrue(resultado != 0);
        listaUsuariosId.add(resultado);

        // Prueba 2
        usuarioDTO = new UsuariosDTO();

        usuarioDTO.setNombre("Rafael");
        usuarioDTO.setApePaterno("Lopez");
        usuarioDTO.setApeMaterno("Aliaga");
        usuarioDTO.setDni("54637218");
        usuarioDTO.setFechaNacimiento(LocalDate.now());
        usuarioDTO.setTelefono("87665641");
        usuarioDTO.setDireccion("Calle 15. Los Robles, Miraflores");
        usuarioDTO.setCorreo("Porky123@gmail.com");
        usuarioDTO.setNombreUsuario("PorkyPres");
        usuarioDTO.setContrasenha("PanConChicharron");
        usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
        usuarioDTO.setTipoUsuario(tipoCliente);

        resultado = this.usuarioDAO.insertar(usuarioDTO);
        assertTrue(resultado != 0);
        listaUsuariosId.add(resultado);
    }

    //@Test
    public void testObtenerPorId() {
        System.out.println("ObtenerPorId");
        ArrayList<Integer> listaUsuarioId = new ArrayList<>();
        this.insertarAlmacenes(listaUsuarioId);

        UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(listaUsuarioId.get(0));
        assertEquals(usuario.getUsuarioId(), listaUsuarioId.get(0));
        assertNotNull(usuario.getTipoUsuario());
        assertEquals(TiposUsuarioDTO.ID_CLIENTE, usuario.getTipoUsuario().getTipoUsuarioId());

        usuario = this.usuarioDAO.obtenerPorId(listaUsuarioId.get(1));
        assertEquals(usuario.getUsuarioId(), listaUsuarioId.get(1));
        assertNotNull(usuario.getTipoUsuario());
    }

    //@Test
    public void testListarTodos() {
        System.out.println("ListarTodos");
        ArrayList<Integer> listaUsuariosId = new ArrayList<>();
        this.insertarAlmacenes(listaUsuariosId);

        ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
        assertEquals(listaUsuariosId.size(), listaUsuarios.size());
        for (int i = 0; i < listaUsuariosId.size(); i++) {
            assertEquals(listaUsuariosId.get(i), listaUsuarios.get(i).getUsuarioId());
            assertNotNull(listaUsuarios.get(i).getTipoUsuario());
        }
        this.eliminarTodo();
    }

    //@Test
    public void testModificar() {
        System.out.println("Modificar");
        ArrayList<Integer> listaUsuariosId = new ArrayList<>();
        this.insertarAlmacenes(listaUsuariosId);

        ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
        assertEquals(listaUsuariosId.size(), listaUsuarios.size());
        for (Integer i = 0; i < listaUsuariosId.size(); i++) {
            listaUsuarios.get(i).setNombre("NombreCambiado" + i.toString());
            listaUsuarios.get(i).setApePaterno("ApellidoPaternoCambiado" + i.toString());
            this.usuarioDAO.modificar(listaUsuarios.get(i));
        }

        ArrayList<UsuariosDTO> listaUsuariosModificados = this.usuarioDAO.listarTodos();
        assertEquals(listaUsuarios.size(), listaUsuariosModificados.size());
        for (Integer i = 0; i < listaUsuarios.size(); i++) {
            assertEquals(listaUsuarios.get(i).getNombre(), listaUsuariosModificados.get(i).getNombre());
            assertEquals(listaUsuarios.get(i).getApePaterno(), listaUsuariosModificados.get(i).getApePaterno());
        }
        this.eliminarTodo();
    }

    //@Test
    public void testEliminar() {
        System.out.println("Eliminar");
        ArrayList<Integer> listaUsuarioId = new ArrayList<>();
        insertarAlmacenes(listaUsuarioId);
        eliminarTodo();
    }

    private void eliminarTodo() {
        ArrayList<UsuariosDTO> listaUsuario = this.usuarioDAO.listarTodos();
        for (UsuariosDTO usuario : listaUsuario) {
            this.eliminarPedidoPorUsuarioId(usuario.getUsuarioId());
            Integer resultado = this.usuarioDAO.eliminar(usuario);
            assertNotEquals(0, resultado);
            UsuariosDTO usuarioDTO = this.usuarioDAO.obtenerPorId(usuario.getUsuarioId());
            assertNull(usuarioDTO);
        }
    }

    private void eliminarPedidoPorUsuarioId(Integer usuarioId) {
        ArrayList<VentasDTO> listaPedido = this.pedidoDAO.listarTodos();
        for (VentasDTO pedido : listaPedido) {
            if (pedido.getUsuario().getUsuarioId() == usuarioId) {
                this.pedidoDAO.eliminar(pedido);
            }
        }
    }

}
