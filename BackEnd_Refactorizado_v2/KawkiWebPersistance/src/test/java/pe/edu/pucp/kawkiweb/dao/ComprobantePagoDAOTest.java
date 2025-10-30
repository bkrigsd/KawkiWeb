//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import pe.edu.pucp.kawkiweb.daoImp.*;
//import pe.edu.pucp.kawkiweb.model.*;
//import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPago;
//import pe.edu.pucp.kawkiweb.model.utilPago.TipoComprobante;
//import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedido;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//
//public class ComprobantePagoDAOTest {
//
//    private ComprobantePagoDAO comprobanteDAO;
//    private PagoDAO pagoDAO;
//    private PedidoDAO pedidoDAO;
//    private UsuarioDAO usuarioDAO;
//
//    // IDs creados durante el test - para limpieza específica
//    private Integer usuarioBaseId;
//    private Integer pedidoBaseId;
//    private Integer pagoBaseId;
//    private ArrayList<Integer> comprobantesCreados;
//
//    @BeforeEach
//    public void setUp() {
//        this.comprobanteDAO = new ComprobantePagoDAOImpl();
//        this.pagoDAO = new PagoDAOImpl();
//        this.pedidoDAO = new PedidoDAOImpl();
//        this.usuarioDAO = new UsuarioDAOImpl();
//        this.comprobantesCreados = new ArrayList<>();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        limpiarDatosDePrueba();
//    }
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
//        
//        // Datos únicos para evitar conflictos
//        long timestamp = System.currentTimeMillis();
//        usuario.setCorreo("david.test" + timestamp + "@test.com");
//        usuario.setNombreUsuario("david04_" + timestamp);
//        
//        usuario.setContrasenha("gato");
//        usuario.setFechaHoraCreacion(LocalDateTime.now());
//        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
//        
//        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
//        assertNotNull(this.usuarioBaseId, "Error al crear usuario base");
//        assertTrue(this.usuarioBaseId > 0, "ID de usuario inválido");
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
//        assertNotNull(this.pedidoBaseId, "Error al crear pedido base");
//        assertTrue(this.pedidoBaseId > 0, "ID de pedido inválido");
//    }
//
//    private void prepararPagoBase() {
//        PagoDTO pago = new PagoDTO();
//        pago.setMonto_total(150.0);
//        pago.setFecha_hora_creacion(LocalDateTime.now());
//        pago.setMetodo_pago(MetodoPago.YAPE);
//        pago.setPedido_id(this.pedidoBaseId);
//        
//        this.pagoBaseId = this.pagoDAO.insertar(pago);
//        assertNotNull(this.pagoBaseId, "Error al crear pago base");
//        assertTrue(this.pagoBaseId > 0, "ID de pago inválido");
//    }
//
//    @Test
//    public void testInsertar() {
//        prepararUsuarioBase();
//        prepararPedidoBase();
//        prepararPagoBase();
//
//        Comprobante_PagoDTO comprobante = new Comprobante_PagoDTO();
//        comprobante.setPago_id(this.pagoBaseId);
//        comprobante.setFecha_hora_creacion(LocalDateTime.now());
//        comprobante.setTipo_comprobante(TipoComprobante.BOLETA);
//        comprobante.setNumero_serie("B001-" + System.currentTimeMillis());
//        comprobante.setDni_cliente("71932032");
//        comprobante.setNombre_cliente("Lucía Gómez");
//        comprobante.setDireccion_fiscal_cliente("Av. Primavera 123");
//        comprobante.setCorreo_cliente("lucia@test.com");
//        comprobante.setTelefono_cliente("987654321");
//        comprobante.setTotal(150.0);
//
//        Integer resultado = this.comprobanteDAO.insertar(comprobante);
//        assertNotNull(resultado, "El ID del comprobante no debería ser null");
//        assertTrue(resultado > 0, "El ID del comprobante debe ser mayor a 0");
//        
//        this.comprobantesCreados.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        prepararUsuarioBase();
//        prepararPedidoBase();
//        prepararPagoBase();
//
//        Comprobante_PagoDTO comprobante = new Comprobante_PagoDTO();
//        comprobante.setPago_id(this.pagoBaseId);
//        comprobante.setFecha_hora_creacion(LocalDateTime.now());
//        comprobante.setTipo_comprobante(TipoComprobante.BOLETA);
//        comprobante.setNumero_serie("B001-" + System.currentTimeMillis());
//        comprobante.setDni_cliente("71932032");
//        comprobante.setNombre_cliente("Lucía Gómez");
//        comprobante.setDireccion_fiscal_cliente("Av. Primavera 123");
//        comprobante.setCorreo_cliente("lucia@test.com");
//        comprobante.setTelefono_cliente("987654321");
//        comprobante.setTotal(180.0);
//
//        Integer comprobanteId = this.comprobanteDAO.insertar(comprobante);
//        this.comprobantesCreados.add(comprobanteId);
//
//        Comprobante_PagoDTO obtenido = this.comprobanteDAO.obtenerPorId(comprobanteId);
//
//        assertNotNull(obtenido, "El comprobante no debería ser null");
//        assertEquals(comprobanteId, obtenido.getComprobante_pago_id());
//        assertEquals(TipoComprobante.BOLETA, obtenido.getTipo_comprobante());
//        assertEquals(this.pagoBaseId, obtenido.getPago_id());
//        assertEquals(180.0, obtenido.getTotal(), 0.001);
//    }
//
//    @Test
//    public void testListarTodos() {
//        prepararUsuarioBase();
//        prepararPedidoBase();
//        prepararPagoBase();
//
//        // Contar comprobantes existentes antes de insertar
//        ArrayList<Comprobante_PagoDTO> comprobantesAntes = this.comprobanteDAO.listarTodos();
//        int cantidadInicial = comprobantesAntes.size();
//
//        // Insertar 2 comprobantes nuevos
//        for (int i = 0; i < 2; i++) {
//            Comprobante_PagoDTO comprobante = new Comprobante_PagoDTO();
//            comprobante.setPago_id(this.pagoBaseId);
//            comprobante.setFecha_hora_creacion(LocalDateTime.now());
//            comprobante.setTipo_comprobante(i == 0 ? TipoComprobante.BOLETA : TipoComprobante.FACTURA);
//            comprobante.setNumero_serie((i == 0 ? "B" : "F") + "001-" + System.currentTimeMillis() + i);
//            comprobante.setDni_cliente("71932032");
//            comprobante.setNombre_cliente("Lucía Gómez");
//            comprobante.setDireccion_fiscal_cliente("Av. Primavera 123");
//            comprobante.setCorreo_cliente("lucia@test.com");
//            comprobante.setTelefono_cliente("987654321");
//            comprobante.setTotal(200.0 + i * 50);
//            
//            if (i == 1) {
//                comprobante.setRuc_cliente("20458796321");
//                comprobante.setRazon_social_cliente("Empresa SAC");
//            }
//
//            Integer comprobanteId = this.comprobanteDAO.insertar(comprobante);
//            this.comprobantesCreados.add(comprobanteId);
//        }
//
//        ArrayList<Comprobante_PagoDTO> comprobantesDespues = this.comprobanteDAO.listarTodos();
//        assertEquals(cantidadInicial + 2, comprobantesDespues.size(),
//                "Deberían existir 2 comprobantes más que al inicio");
//    }
//
//    @Test
//    public void testModificar() {
//        prepararUsuarioBase();
//        prepararPedidoBase();
//        prepararPagoBase();
//
//        Comprobante_PagoDTO comprobante = new Comprobante_PagoDTO();
//        comprobante.setPago_id(this.pagoBaseId);
//        comprobante.setFecha_hora_creacion(LocalDateTime.now());
//        comprobante.setTipo_comprobante(TipoComprobante.BOLETA);
//        comprobante.setNumero_serie("B001-" + System.currentTimeMillis());
//        comprobante.setDni_cliente("71932032");
//        comprobante.setNombre_cliente("Lucía Gómez");
//        comprobante.setDireccion_fiscal_cliente("Av. Primavera 123");
//        comprobante.setCorreo_cliente("lucia@test.com");
//        comprobante.setTelefono_cliente("987654321");
//        comprobante.setTotal(150.0);
//
//        Integer id = this.comprobanteDAO.insertar(comprobante);
//        this.comprobantesCreados.add(id);
//
//        // Obtener el comprobante recién insertado
//        Comprobante_PagoDTO comprobanteBD = this.comprobanteDAO.obtenerPorId(id);
//        assertNotNull(comprobanteBD, "El comprobante debe existir antes de modificar");
//
//        // Modificar de BOLETA a FACTURA
//        comprobanteBD.setTipo_comprobante(TipoComprobante.FACTURA);
//        comprobanteBD.setRazon_social_cliente("Empresa SAC");
//        comprobanteBD.setRuc_cliente("20458796321");
//        comprobanteBD.setTotal(200.0);
//
//        Integer filas = this.comprobanteDAO.modificar(comprobanteBD);
//        assertEquals(1, filas, "Debería modificarse exactamente 1 fila");
//
//        // Verificar la modificación
//        Comprobante_PagoDTO actualizado = this.comprobanteDAO.obtenerPorId(id);
//        assertEquals(TipoComprobante.FACTURA, actualizado.getTipo_comprobante());
//        assertEquals("Empresa SAC", actualizado.getRazon_social_cliente());
//        assertEquals("20458796321", actualizado.getRuc_cliente());
//        assertEquals(200.0, actualizado.getTotal(), 0.001);
//    }
//
//    @Test
//    public void testEliminar() {
//        prepararUsuarioBase();
//        prepararPedidoBase();
//        prepararPagoBase();
//
//        Comprobante_PagoDTO comprobante = new Comprobante_PagoDTO();
//        comprobante.setPago_id(this.pagoBaseId);
//        comprobante.setFecha_hora_creacion(LocalDateTime.now());
//        comprobante.setTipo_comprobante(TipoComprobante.BOLETA);
//        comprobante.setNumero_serie("B001-" + System.currentTimeMillis());
//        comprobante.setDni_cliente("71932032");
//        comprobante.setNombre_cliente("Lucía Gómez");
//        comprobante.setDireccion_fiscal_cliente("Av. Primavera 123");
//        comprobante.setCorreo_cliente("lucia@test.com");
//        comprobante.setTelefono_cliente("987654321");
//        comprobante.setTotal(150.0);
//
//        Integer id = this.comprobanteDAO.insertar(comprobante);
//
//        Comprobante_PagoDTO comprobanteBD = this.comprobanteDAO.obtenerPorId(id);
//        assertNotNull(comprobanteBD, "El comprobante debe existir antes de eliminar");
//
//        Integer filas = this.comprobanteDAO.eliminar(comprobanteBD);
//        assertEquals(1, filas, "Debería eliminarse exactamente 1 fila");
//
//        Comprobante_PagoDTO eliminado = this.comprobanteDAO.obtenerPorId(id);
//        assertNull(eliminado, "El comprobante no debería existir después de eliminar");
//        
//        // No agregamos a la lista porque ya fue eliminado
//    }
//
//    /**
//     * Limpia SOLO los datos creados en este test específico
//     * Orden de eliminación respetando integridad referencial:
//     * Comprobantes -> Pagos -> Pedidos -> Usuarios
//     */
//    private void limpiarDatosDePrueba() {
//        try {
//            // 1. Eliminar comprobantes creados en este test
//            for (Integer comprobanteId : this.comprobantesCreados) {
//                try {
//                    Comprobante_PagoDTO comprobante = this.comprobanteDAO.obtenerPorId(comprobanteId);
//                    if (comprobante != null) {
//                        this.comprobanteDAO.eliminar(comprobante);
//                    }
//                } catch (Exception e) {
//                    System.err.println("Error al eliminar comprobante " + comprobanteId + ": " + e.getMessage());
//                }
//            }
//
//            // 2. Eliminar pago base (si existe)
//            if (this.pagoBaseId != null) {
//                try {
//                    PagoDTO pago = this.pagoDAO.obtenerPorId(this.pagoBaseId);
//                    if (pago != null) {
//                        this.pagoDAO.eliminar(pago);
//                    }
//                } catch (Exception e) {
//                    System.err.println("Error al eliminar pago " + this.pagoBaseId + ": " + e.getMessage());
//                }
//            }
//
//            // 3. Eliminar pedido base (si existe)
//            if (this.pedidoBaseId != null) {
//                try {
//                    PedidoDTO pedido = this.pedidoDAO.obtenerPorId(this.pedidoBaseId);
//                    if (pedido != null) {
//                        this.pedidoDAO.eliminar(pedido);
//                    }
//                } catch (Exception e) {
//                    System.err.println("Error al eliminar pedido " + this.pedidoBaseId + ": " + e.getMessage());
//                }
//            }
//
//            // 4. Eliminar usuario base (si existe)
//            if (this.usuarioBaseId != null) {
//                try {
//                    UsuarioDTO usuario = this.usuarioDAO.obtenerPorId(this.usuarioBaseId);
//                    if (usuario != null) {
//                        this.usuarioDAO.eliminar(usuario);
//                    }
//                } catch (Exception e) {
//                    System.err.println("Error al eliminar usuario " + this.usuarioBaseId + ": " + e.getMessage());
//                }
//            }
//        } finally {
//            // Limpiar referencias
//            this.comprobantesCreados.clear();
//            this.pagoBaseId = null;
//            this.pedidoBaseId = null;
//            this.usuarioBaseId = null;
//        }
//    }
//}
