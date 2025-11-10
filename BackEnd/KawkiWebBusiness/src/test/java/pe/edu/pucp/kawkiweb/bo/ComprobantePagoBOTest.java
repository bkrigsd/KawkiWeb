//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.kawkiweb.dao.ComprobantePagoDAO;
//import pe.edu.pucp.kawkiweb.dao.PagoDAO;
//import pe.edu.pucp.kawkiweb.dao.PedidoDAO;
//import pe.edu.pucp.kawkiweb.dao.UsuarioDAO;
//import pe.edu.pucp.kawkiweb.daoImp.ComprobantePagoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.PagoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
//import pe.edu.pucp.kawkiweb.model.Comprobante_PagoDTO;
//import pe.edu.pucp.kawkiweb.model.PagoDTO;
//import pe.edu.pucp.kawkiweb.model.PedidoDTO;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPago;
//import pe.edu.pucp.kawkiweb.model.utilPago.TipoComprobante;
//import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedido;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//public class ComprobantePagoBOTest {
//
//    private ComprobantePagoBO comprobanteBO;
//    private ComprobantePagoDAO comprobanteDAO;
//    private PagoDAO pagoDAO;
//    private PedidoDAO pedidoDAO;
//    private UsuarioDAO usuarioDAO;
//
//    private Integer usuarioBaseId;
//    private Integer pedidoBaseId;
//    private Integer pagoBaseId;
//
//    public ComprobantePagoBOTest() {
//        this.comprobanteBO = new ComprobantePagoBO();
//        this.usuarioDAO = new UsuarioDAOImpl();
//        this.pedidoDAO = new PedidoDAOImpl();
//        this.pagoDAO = new PagoDAOImpl();
//        this.comprobanteDAO = new ComprobantePagoDAOImpl();
//    }
//
//    
//    private void prepararUsuarioBase() {
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
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setUsuarioId(this.usuarioBaseId);
//        pedido.setUsuario(usuario);
//        pedido.setEstado_pedido(EstadoPedido.PREPARACION);
//        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
//        assertTrue(this.pedidoBaseId != 0);
//    }
//
//    private void prepararPagoBase() {
//        PagoDTO pago = new PagoDTO();
//        pago.setMonto_total(150.0);
//        pago.setFecha_hora_creacion(LocalDateTime.now());
//        pago.setMetodo_pago(MetodoPago.YAPE);
//        pago.setPedido_id(this.pedidoBaseId);
//        this.pagoBaseId = this.pagoDAO.insertar(pago);
//        assertTrue(this.pagoBaseId != 0);
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaComprobanteId = new ArrayList<>();
//        insertarComprobantes(listaComprobanteId);
//        eliminarTodo();
//    }
//
//    private void insertarComprobantes(ArrayList<Integer> listaComprobanteId) {
//        // Aseguramos datos base
//        prepararUsuarioBase();
//        prepararPedidoBase();
//        prepararPagoBase();
//
//        Integer resultado = this.comprobanteBO.insertar(
//                this.pagoBaseId, // ✅ usar el pago real creado
//                LocalDateTime.now(), 
//                TipoComprobante.BOLETA,
//                "B001-0001", 
//                "12345678", 
//                "Juan Pérez", 
//                null, 
//                null,
//                "Av. Siempre Viva 123", 
//                "juanperez@mail.com", 
//                "987654321", 
//                150.0);
//        assertTrue(resultado != 0);
//        listaComprobanteId.add(resultado);
//
//        resultado = this.comprobanteBO.insertar(
//                this.pagoBaseId, // ✅ mismo pago o podrías crear otro si quieres
//                LocalDateTime.now(), 
//                TipoComprobante.FACTURA,
//                "F001-0002", 
//                null, 
//                null, 
//                "20123456789", 
//                "Tech SAC",
//                "Calle Falsa 456", 
//                "ventas@techsac.com", 
//                "999888777", 
//                300.0);
//        assertTrue(resultado != 0);
//        listaComprobanteId.add(resultado);
//    }
//
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaComprobanteId = new ArrayList<>();
//        insertarComprobantes(listaComprobanteId);
//
//        Comprobante_PagoDTO comprobante = this.comprobanteBO.obtenerPorId(listaComprobanteId.get(0));
//        assertEquals(listaComprobanteId.get(0), comprobante.getComprobante_pago_id());
//
//        comprobante = this.comprobanteBO.obtenerPorId(listaComprobanteId.get(1));
//        assertEquals(listaComprobanteId.get(1), comprobante.getComprobante_pago_id());
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaComprobanteId = new ArrayList<>();
//        insertarComprobantes(listaComprobanteId);
//
//        ArrayList<Comprobante_PagoDTO> listaComprobantes = this.comprobanteBO.listarTodos();
//        assertTrue(listaComprobantes.size() >= listaComprobanteId.size());
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaComprobanteId = new ArrayList<>();
//        insertarComprobantes(listaComprobanteId);
//
//        ArrayList<Comprobante_PagoDTO> listaComprobantes = this.comprobanteBO.listarTodos();
//        assertEquals(listaComprobanteId.size(), listaComprobantes.size());
//
//        for (Integer i = 0; i < listaComprobanteId.size(); i++) {
//            listaComprobantes.get(i).setTotal(listaComprobantes.get(i).getTotal() + (i + 1) * 25.0);
//            listaComprobantes.get(i).setTipo_comprobante(listaComprobantes.get(i).getTipo_comprobante() == TipoComprobante.BOLETA 
//                    ? TipoComprobante.FACTURA 
//                    : TipoComprobante.BOLETA
//            );
//            listaComprobantes.get(i).setNumero_serie("SERIE-" + (100 + i));
//
//            this.comprobanteBO.modificar(
//                listaComprobantes.get(i).getComprobante_pago_id(),
//                listaComprobantes.get(i).getPago_id(),
//                listaComprobantes.get(i).getFecha_hora_creacion(),
//                listaComprobantes.get(i).getTipo_comprobante(),
//                listaComprobantes.get(i).getNumero_serie(),
//                listaComprobantes.get(i).getDni_cliente(),
//                listaComprobantes.get(i).getNombre_cliente(),
//                listaComprobantes.get(i).getRuc_cliente(),
//                listaComprobantes.get(i).getRazon_social_cliente(),
//                listaComprobantes.get(i).getDireccion_fiscal_cliente(),
//                listaComprobantes.get(i).getCorreo_cliente(),
//                listaComprobantes.get(i).getTelefono_cliente(),
//                listaComprobantes.get(i).getTotal()
//            );
//        }
//
//        ArrayList<Comprobante_PagoDTO> listaComprobantesModificados = this.comprobanteBO.listarTodos();
//        assertEquals(listaComprobantes.size(), listaComprobantesModificados.size());
//
//        for (Integer i = 0; i < listaComprobantes.size(); i++) {
//            assertEquals(listaComprobantes.get(i).getTotal(), listaComprobantesModificados.get(i).getTotal());
//            assertEquals(listaComprobantes.get(i).getTipo_comprobante(), listaComprobantesModificados.get(i).getTipo_comprobante());
//            assertEquals(listaComprobantes.get(i).getNumero_serie(), listaComprobantesModificados.get(i).getNumero_serie());
//        }
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaComprobanteId = new ArrayList<>();
//        insertarComprobantes(listaComprobanteId);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<Comprobante_PagoDTO> listaComprobantes = this.comprobanteBO.listarTodos();
//        for (Comprobante_PagoDTO c : listaComprobantes) {
//            Integer resultado = this.comprobanteBO.eliminar(c.getComprobante_pago_id());
//            assertNotEquals(0, resultado);
//            Comprobante_PagoDTO eliminado = this.comprobanteBO.obtenerPorId(c.getComprobante_pago_id());
//            assertNull(eliminado);
//        }
//    }
//}
