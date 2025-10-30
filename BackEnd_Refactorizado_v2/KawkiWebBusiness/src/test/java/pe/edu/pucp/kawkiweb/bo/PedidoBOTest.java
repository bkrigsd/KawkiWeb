//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.model.PedidoDTO;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedido;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//public class PedidoBOTest {
//
//    private PedidoBO pedidoBO;
//    private UsuarioBO usuarioBO;
//    private PromocionBO promocionBO;
//    private Integer usuarioBaseId;
//    private Integer promocionBaseId;
//
//    public PedidoBOTest() {
//        this.pedidoBO = new PedidoBO();
//        this.usuarioBO = new UsuarioBO();
//        this.promocionBO = new PromocionBO();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        this.usuarioBaseId = this.usuarioBO.Insertar("David", "Espinoza", "Urco", "71932031", LocalDate.of(2004, 9, 20), "993294837", "Av. Santa Cruz 123", "david@test.com", "david04", "gato", LocalDateTime.now(), TipoUsuario.CLIENTE);
//        assertNotNull(this.usuarioBaseId, "El usuario de prueba debería haberse creado");
//        assertTrue(this.usuarioBaseId > 0, "El ID del usuario debe ser válido");
//        this.promocionBaseId = this.promocionBO.Insertar("Promoción Prueba pedido", TipoCondicion.CANT_MIN_PRODUCTOS, 3, TipoBeneficio.DESCUENTO_PORCENTAJE, 10, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), Boolean.TRUE);
//        assertNotNull(this.promocionBaseId, "La promoción de prueba debería haberse creado");
//        assertTrue(this.promocionBaseId > 0, "El ID de la promoción debe ser válido");
//        eliminarTodo();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Limpia la base de datos después de cada prueba
//        eliminarTodo();
//        if (this.usuarioBaseId != null) {
//            Integer resultado = this.usuarioBO.eliminar(this.usuarioBaseId);
//            assertNotEquals(0, resultado, "El usuario de prueba debería eliminarse correctamente");
//        }
//        if (this.promocionBaseId != null) {
//            Integer resultado = this.promocionBO.eliminar(this.promocionBaseId);
//            assertNotEquals(0, resultado, "La promoción de prueba debería eliminarse correctamente");
//        }
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaPedidoId = new ArrayList<>();
//        insertarPedidos(listaPedidoId);
//    }
//
//    private void insertarPedidos(ArrayList<Integer> listaPedidoId) {
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setUsuarioId(this.usuarioBaseId);
//        PromocionDTO promocion = new PromocionDTO();
//        promocion.setPromocion_id(promocionBaseId);
//        Integer resultado = this.pedidoBO.insertar(usuario, LocalDateTime.now(), LocalDateTime.now(), 109.98, EstadoPedido.PENDIENTE, promocion);
//        assertTrue(resultado != 0);
//        listaPedidoId.add(resultado);
//
//        usuario.setUsuarioId(this.usuarioBaseId);
//        promocion.setPromocion_id(promocionBaseId);
//        resultado = this.pedidoBO.insertar(usuario, LocalDateTime.now(), LocalDateTime.now(), 94.50, EstadoPedido.PENDIENTE, promocion);
//        assertTrue(resultado != 0);
//        listaPedidoId.add(resultado);
//
//        usuario.setUsuarioId(this.usuarioBaseId);
//        promocion.setPromocion_id(promocionBaseId);
//        resultado = this.pedidoBO.insertar(usuario, LocalDateTime.now(), LocalDateTime.now(), 222.48, EstadoPedido.PENDIENTE, promocion);
//        assertTrue(resultado != 0);
//        listaPedidoId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaPedidosId = new ArrayList<>();
//        insertarPedidos(listaPedidosId);
//
//        PedidoDTO pedido = this.pedidoBO.obtenerPorId(listaPedidosId.get(0));
//        assertEquals(pedido.getPedido_id(), listaPedidosId.get(0));
//
//        pedido = this.pedidoBO.obtenerPorId(listaPedidosId.get(1));
//        assertEquals(pedido.getPedido_id(), listaPedidosId.get(1));
//
//        pedido = this.pedidoBO.obtenerPorId(listaPedidosId.get(2));
//        assertEquals(pedido.getPedido_id(), listaPedidosId.get(2));
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaPedidoId = new ArrayList<>();
//        insertarPedidos(listaPedidoId);
//
//        ArrayList<PedidoDTO> listaPedidos = this.pedidoBO.listarTodos();
//        assertEquals(listaPedidoId.size(), listaPedidos.size());
//        for (Integer i = 0; i < listaPedidoId.size(); i++) {
//            assertEquals(listaPedidoId.get(i), listaPedidos.get(i).getPedido_id());
//        }
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaPedidoId = new ArrayList<>();
//        insertarPedidos(listaPedidoId);
//
//        ArrayList<PedidoDTO> listaPedidos = this.pedidoBO.listarTodos();
//        assertEquals(listaPedidoId.size(), listaPedidos.size());
//        for (Integer i = 0; i < listaPedidoId.size(); i++) {
//            listaPedidos.get(i).setTotal(listaPedidos.get(i).getTotal() + 109.98);
//            listaPedidos.get(i).setFecha_hora_creacion(LocalDateTime.now());
//            listaPedidos.get(i).setFecha_hora_ultimo_estado(LocalDateTime.now());
//            listaPedidos.get(i).setEstado_pedido(EstadoPedido.ENTREGADO);
//            this.pedidoBO.modificar(listaPedidos.get(i).getPedido_id(), listaPedidos.get(i).getUsuario(), listaPedidos.get(i).getFecha_hora_creacion(), listaPedidos.get(i).getFecha_hora_ultimo_estado(), listaPedidos.get(i).getTotal(), listaPedidos.get(i).getEstado_pedido(), listaPedidos.get(i).getPromocion());
//        }
//        ArrayList<PedidoDTO> listaPedidosModificados = this.pedidoBO.listarTodos();
//        assertEquals(listaPedidos.size(), listaPedidosModificados.size());
//        for (Integer i = 0; i < listaPedidos.size(); i++) {
//            assertEquals(listaPedidos.get(i).getTotal(), listaPedidosModificados.get(i).getTotal(), 0.0001); //margen de error
//            assertEquals(listaPedidos.get(i).getEstado_pedido(), listaPedidosModificados.get(i).getEstado_pedido());
//        }
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaPedidoId = new ArrayList<>();
//        insertarPedidos(listaPedidoId);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<PedidoDTO> listaPedidos = this.pedidoBO.listarTodos();
//        for (Integer i = 0; i < listaPedidos.size(); i++) {
//            Integer resultado = this.pedidoBO.eliminar(listaPedidos.get(i).getPedido_id());
//            assertNotEquals(0, resultado);
//            PedidoDTO pedido = this.pedidoBO.obtenerPorId(listaPedidos.get(i).getPedido_id());
//            assertNull(pedido);
//        }
//    }
//}
