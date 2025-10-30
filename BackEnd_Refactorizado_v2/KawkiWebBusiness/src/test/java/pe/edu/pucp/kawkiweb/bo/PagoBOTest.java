//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.kawkiweb.dao.PedidoDAO;
//import pe.edu.pucp.kawkiweb.dao.UsuarioDAO;
//import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
//import pe.edu.pucp.kawkiweb.model.PagoDTO;
//import pe.edu.pucp.kawkiweb.model.PedidoDTO;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPago;
//import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedido;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//public class PagoBOTest {
//
//    private PagoBO pagoBO;
//    private PedidoDAO pedidoDAO;
//    private UsuarioDAO usuarioDAO;
//    private Integer usuarioBaseId;
//    private Integer pedidoBaseId;
//
//    @BeforeEach
//    public void setUp() {
//        this.pagoBO = new PagoBO();
//        this.pedidoDAO = new PedidoDAOImpl();
//        this.usuarioDAO = new UsuarioDAOImpl();
//
//        prepararUsuarioBase();
//        prepararPedidoBase();
//    }
//
//    private void prepararUsuarioBase() {
//        //Insertamos un usuario de prueba para asociar variantes
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setNombre("David");
//        usuario.setApePaterno("Espinoza");
//        usuario.setApeMaterno("Urco");
//        usuario.setDni("71932031");
//        usuario.setFechaNacimiento(LocalDate.of(2004, 9, 20));
//        usuario.setTelefono("993294837");
//        usuario.setDireccion("Av. Santa Cruz 123");
//        usuario.setCorreo("david@test.com");
//        usuario.setNombreUsuario("david04");
//        usuario.setContrasenha("gato");
//        usuario.setFechaHoraCreacion(LocalDateTime.now());
//        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
//        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
//        assertTrue(this.usuarioBaseId != 0);
//    }
//
//    private void prepararPedidoBase() {
//        PedidoDTO pedido = new PedidoDTO();
//        pedido.setTotal(120.50);
//        pedido.setFecha_hora_creacion(LocalDateTime.now());
//        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
//
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setUsuarioId(this.usuarioBaseId); 
//        pedido.setUsuario(usuario);
//
//        pedido.setEstado_pedido(EstadoPedido.PREPARACION);
//
//        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
//        assertTrue(this.pedidoBaseId != 0);
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaPagoId = new ArrayList<>();
//        insertarPagos(listaPagoId);
//        eliminarTodo();
//    }
//
//    private void insertarPagos(ArrayList<Integer> listaPagoId) {
//        Integer resultado = this.pagoBO.insertar(150.0, LocalDateTime.now(), MetodoPago.YAPE, this.pedidoBaseId);
//        assertTrue(resultado != 0);
//        listaPagoId.add(resultado);
//
//        resultado = this.pagoBO.insertar(250.0, LocalDateTime.now(), MetodoPago.PLIN, this.pedidoBaseId);
//        assertTrue(resultado != 0);
//        listaPagoId.add(resultado);
//
//        resultado = this.pagoBO.insertar(300.0, LocalDateTime.now(), MetodoPago.TARJETA_BANCARIA, this.pedidoBaseId);
//        assertTrue(resultado != 0);
//        listaPagoId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaPagoId = new ArrayList<>();
//        insertarPagos(listaPagoId);
//
//        PagoDTO pago = this.pagoBO.obtenerPorId(listaPagoId.get(0));
//        assertEquals(listaPagoId.get(0), pago.getPago_id());
//
//        pago = this.pagoBO.obtenerPorId(listaPagoId.get(1));
//        assertEquals(listaPagoId.get(1), pago.getPago_id());
//
//        pago = this.pagoBO.obtenerPorId(listaPagoId.get(2));
//        assertEquals(listaPagoId.get(2), pago.getPago_id());
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaPagoId = new ArrayList<>();
//        insertarPagos(listaPagoId);
//
//        ArrayList<PagoDTO> listaPagos = this.pagoBO.listarTodos();
//        assertTrue(listaPagos.size() >= listaPagoId.size());
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaPagoId = new ArrayList<>();
//        insertarPagos(listaPagoId);
//
//        ArrayList<PagoDTO> listaPagos = this.pagoBO.listarTodos();
//        assertEquals(listaPagoId.size(), listaPagos.size());
//
//        for (int i = 0; i < listaPagoId.size(); i++) {
//            listaPagos.get(i).setMonto_total(listaPagos.get(i).getMonto_total() + (i + 1) * 50.0);
//            listaPagos.get(i).setMetodo_pago(MetodoPago.TARJETA_BANCARIA);
//            this.pagoBO.modificar(listaPagos.get(i).getPago_id(),
//                    listaPagos.get(i).getMonto_total(),
//                    listaPagos.get(i).getFecha_hora_creacion(),
//                    listaPagos.get(i).getMetodo_pago(),
//                    listaPagos.get(i).getPedido_id());
//        }
//
//        ArrayList<PagoDTO> listaPagosModificados = this.pagoBO.listarTodos();
//        assertEquals(listaPagos.size(), listaPagosModificados.size());
//
//        for (int i = 0; i < listaPagos.size(); i++) {
//            assertEquals(listaPagos.get(i).getMonto_total(), listaPagosModificados.get(i).getMonto_total());
//            assertEquals(listaPagos.get(i).getMetodo_pago(), listaPagosModificados.get(i).getMetodo_pago());
//        }
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaPagoId = new ArrayList<>();
//        insertarPagos(listaPagoId);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<PagoDTO> listaPagos = this.pagoBO.listarTodos();
//        for (PagoDTO pago : listaPagos) {
//            Integer resultado = this.pagoBO.eliminar(pago.getPago_id());
//            assertNotEquals(0, resultado);
//            PagoDTO eliminado = this.pagoBO.obtenerPorId(pago.getPago_id());
//            assertNull(eliminado);
//        }
//    }
//}
