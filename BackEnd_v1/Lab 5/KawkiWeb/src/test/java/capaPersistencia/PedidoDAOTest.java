package capaPersistencia;

import capaDominio.PedidoDTO;
import capaDominio.PromocionDTO;
import capaDominio.UsuarioDTO;
import capaDominio.pedidoDetalle.Estado_Pedido;
import capaDominio.promocionDetalle.TipoBeneficio;
import capaDominio.promocionDetalle.TipoCondicion;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.PedidoDAOImpl;
import capaPersistencia.Implementar.PromocionDAOImpl;
import capaPersistencia.Implementar.UsuarioDAOImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoDAOTest {

    private PedidoDAO pedidoDAO;
    private UsuarioDAO usuarioDAO;
    private PromocionDAO promocionDAO;
    private Integer usuarioBaseId;
    private Integer promocionBaseId;

    public PedidoDAOTest() {
        this.pedidoDAO = new PedidoDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.promocionDAO = new PromocionDAOImpl();
    }

    //usuario ejemplo:
    private void prepararUsuarioBase() {
        // Insertamos un usuario de prueba para asociar variantes
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("David");
        usuario.setApePaterno("Espinoza");
        usuario.setApeMaterno("Urco");
        usuario.setDni("71932031");
        usuario.setFechaNacimiento(LocalDate.of(2004, 9, 20));
        usuario.setTelefono("993294837");
        usuario.setDireccion("Av. Santa Cruz 123");
        usuario.setCorreo("david@test.com");
        usuario.setNombreUsuario("david04");
        usuario.setContrasenha("gato");
        usuario.setFechaHoraCreacion(LocalDateTime.now());
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
        assertTrue(this.usuarioBaseId != 0);
    }

    //promocion ejemplo:
    private void prepararPromocionBase() {
        // Insertamos una promoción de prueba para asociar variantes
        PromocionDTO promocion = new PromocionDTO();
        promocion.setDescripcion("Promoción Prueba pedido");
        promocion.setFecha_inicio(LocalDateTime.now());
        promocion.setFecha_fin(LocalDateTime.now().plusMonths(1));
        promocion.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
        promocion.setTipo_condicion(TipoCondicion.CANT_MIN_PRODUCTOS);
        promocion.setValor_beneficio(10);
        promocion.setValor_condicion(3); //3 productos minimos
        promocion.setActivo(Boolean.TRUE);
        this.promocionBaseId = this.promocionDAO.insertar(promocion);
        assertTrue(this.promocionBaseId != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);
        eliminarTodo();
    }

    private void insertarPedidos(ArrayList<Integer> listaPedidoId) {
        PedidoDTO pedido;
        pedido = new PedidoDTO();
        pedido.setTotal(109.98);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);
        PromocionDTO promo = new PromocionDTO();
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);
        pedido.setEstado_pedido(Estado_Pedido.PENDIENTE);
        Integer resultado = this.pedidoDAO.insertar(pedido);
        assertTrue(resultado != 0);
        listaPedidoId.add(resultado);

        pedido = new PedidoDTO();
        pedido.setTotal(94.50);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);
        pedido.setEstado_pedido(Estado_Pedido.PENDIENTE);
        resultado = this.pedidoDAO.insertar(pedido);
        assertTrue(resultado != 0);
        listaPedidoId.add(resultado);

        pedido = new PedidoDTO();
        pedido.setTotal(222.48);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);
        pedido.setEstado_pedido(Estado_Pedido.PENDIENTE);
        resultado = this.pedidoDAO.insertar(pedido);
        assertTrue(resultado != 0);
        listaPedidoId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);
        PedidoDTO pedido = this.pedidoDAO.obtenerPorId(listaPedidoId.get(0));
        assertEquals(pedido.getPedido_id(), listaPedidoId.get(0));

        pedido = this.pedidoDAO.obtenerPorId(listaPedidoId.get(1));
        assertEquals(pedido.getPedido_id(), listaPedidoId.get(1));

        pedido = this.pedidoDAO.obtenerPorId(listaPedidoId.get(2));
        assertEquals(pedido.getPedido_id(), listaPedidoId.get(2));
        eliminarTodo();
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);

        ArrayList<PedidoDTO> listaPedidos = this.pedidoDAO.listarTodos();
        assertEquals(listaPedidoId.size(), listaPedidos.size());
        for (Integer i = 0; i < listaPedidoId.size(); i++) {
            assertEquals(listaPedidoId.get(i), listaPedidos.get(i).getPedido_id());
        }
        eliminarTodo();
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);

        ArrayList<PedidoDTO> listaPedidos = this.pedidoDAO.listarTodos();
        assertEquals(listaPedidoId.size(), listaPedidos.size());
        for (Integer i = 0; i < listaPedidoId.size(); i++) {
            listaPedidos.get(i).setTotal(listaPedidos.get(i).getTotal() + 109.98);
            listaPedidos.get(i).setFecha_hora_creacion(LocalDateTime.now());
            listaPedidos.get(i).setFecha_hora_ultimo_estado(LocalDateTime.now());
            listaPedidos.get(i).setEstado_pedido(Estado_Pedido.ENTREGADO);
            this.pedidoDAO.modificar(listaPedidos.get(i));
        }

        ArrayList<PedidoDTO> listaPedidosModificados = this.pedidoDAO.listarTodos();
        assertEquals(listaPedidos.size(), listaPedidosModificados.size());
        for (Integer i = 0; i < listaPedidos.size(); i++) {
            assertEquals(listaPedidos.get(i).getTotal(), listaPedidosModificados.get(i).getTotal(), 0.0001); //margen de error
            assertEquals(listaPedidos.get(i).getEstado_pedido(), listaPedidosModificados.get(i).getEstado_pedido());
        }
        eliminarTodo();
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);
        eliminarTodo();
    }

    private void eliminarTodo() {
        ArrayList<PedidoDTO> listaPedidos = this.pedidoDAO.listarTodos();
        for (Integer i = 0; i < listaPedidos.size(); i++) {
            Integer resultado = this.pedidoDAO.eliminar(listaPedidos.get(i));
            assertNotEquals(0, resultado);
            PedidoDTO pedido = this.pedidoDAO.obtenerPorId(listaPedidos.get(i).getPedido_id());
            assertNull(pedido);
        }

        ArrayList<PromocionDTO> listarPromocion = this.promocionDAO.listarTodos();
        for (PromocionDTO promocion : listarPromocion) {
            this.promocionDAO.eliminar(promocion);
        }

        ArrayList<UsuarioDTO> listarUsuarios = this.usuarioDAO.listarTodos();
        for (UsuarioDTO usuario : listarUsuarios) {
            this.usuarioDAO.eliminar(usuario);
        }
    }
}
