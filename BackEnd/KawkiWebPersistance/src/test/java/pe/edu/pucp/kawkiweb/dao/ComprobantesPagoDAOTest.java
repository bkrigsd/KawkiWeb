package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.pucp.kawkiweb.daoImp.*;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public class ComprobantesPagoDAOTest {

    private ComprobantesPagoDAO comprobanteDAO;
    private VentasDAO ventaDAO;
    private TiposComprobanteDAO tipoComprobanteDAO;
    private MetodosPagoDAO metodoPagoDAO;
    private UsuariosDAO usuarioDAO;

    private UsuariosDTO usuarioDTO;
    private VentasDTO ventaDTO;
    private TiposComprobanteDTO tipoComprobDTO;
    private MetodosPagoDTO metodoPagoDTO;

    public ComprobantesPagoDAOTest() {
        this.comprobanteDAO = new ComprobantePagoDAOImpl();
        this.ventaDAO = new VentasDAOImpl();
        this.tipoComprobanteDAO = new TiposComprobanteDAOImpl();
        this.metodoPagoDAO = new MetodosPagoDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
    }

    @BeforeEach
    public void setUp() {
        eliminarTodo();
        prepararUsuarioBase();
        prepararVentaBase();
        prepararTipoComprobanteBase();
        prepararMetodoPagoBase();
    }

    @AfterEach
    public void tearDown() {
        eliminarTodo();
    }

    private void prepararUsuarioBase() {
        this.usuarioDTO = new UsuariosDTO();
        this.usuarioDTO.setNombre("David");
        this.usuarioDTO.setApePaterno("Espinoza");
        this.usuarioDTO.setDni("71932031");
        this.usuarioDTO.setTelefono("993294837");
        this.usuarioDTO.setCorreo("david@test.com");
        this.usuarioDTO.setNombreUsuario("david04");
        this.usuarioDTO.setContrasenha("gato");
        this.usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
        this.usuarioDTO.setTipoUsuario(new TiposUsuarioDTO(
                TiposUsuarioDTO.ID_VENDEDOR, TiposUsuarioDTO.NOMBRE_VENDEDOR));

        Integer resultado = this.usuarioDAO.insertar(this.usuarioDTO);
        this.usuarioDTO.setUsuarioId(resultado);
        assertTrue(resultado != 0);
    }

    private void prepararVentaBase() {
        this.ventaDTO = new VentasDTO();
        this.ventaDTO.setUsuario(this.usuarioDTO);
        this.ventaDTO.setFecha_hora_creacion(LocalDateTime.now());
        this.ventaDTO.setTotal(109.98);
        Integer resultado = this.ventaDAO.insertar(this.ventaDTO);
        this.ventaDTO.setVenta_id(resultado);
        assertTrue(resultado != 0);
    }

    private void prepararTipoComprobanteBase() {
        this.tipoComprobDTO = new TiposComprobanteDTO(
                TiposComprobanteDTO.ID_BOLETA_SIMPLE, TiposComprobanteDTO.NOMBRE_BOLETA_SIMPLE);
    }

    private void prepararMetodoPagoBase() {
        this.metodoPagoDTO = new MetodosPagoDTO(
                MetodosPagoDTO.ID_YAPE, MetodosPagoDTO.NOMBRE_YAPE);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");

        ArrayList<Integer> listaComprobPagoId = new ArrayList<>();
        insertarComprobPago(listaComprobPagoId);
    }

    private void insertarComprobPago(ArrayList<Integer> listaComprobPagoId) {
        ComprobantesPagoDTO comprobPago;

        comprobPago = new ComprobantesPagoDTO();
        comprobPago.setFecha_hora_creacion(LocalDateTime.now());
        comprobPago.setTipo_comprobante(this.tipoComprobDTO);
        comprobPago.setNumero_serie("NumeroSerie_1");
        comprobPago.setNombre_cliente("Juan Bazán");
        comprobPago.setTelefono_cliente("980750134");
        comprobPago.setTotal(300.50);
        comprobPago.setVenta(this.ventaDTO);
        comprobPago.setMetodo_pago(this.metodoPagoDTO);
        Integer resultado = this.comprobanteDAO.insertar(comprobPago);
        assertTrue(resultado != 0);
        listaComprobPagoId.add(resultado);

        comprobPago = new ComprobantesPagoDTO();
        comprobPago.setFecha_hora_creacion(LocalDateTime.now());
        comprobPago.setTipo_comprobante(this.tipoComprobDTO);
        comprobPago.setNumero_serie("NumeroSerie_2");
        comprobPago.setDni_cliente("19058052");
        comprobPago.setNombre_cliente("Juan Bazán");
        comprobPago.setTotal(300.50);
        comprobPago.setVenta(this.ventaDTO);
        comprobPago.setMetodo_pago(this.metodoPagoDTO);
        resultado = this.comprobanteDAO.insertar(comprobPago);
        assertTrue(resultado != 0);
        listaComprobPagoId.add(resultado);

        comprobPago = new ComprobantesPagoDTO();
        comprobPago.setFecha_hora_creacion(LocalDateTime.now());
        comprobPago.setTipo_comprobante(this.tipoComprobDTO);
        comprobPago.setNumero_serie("NumeroSerie_3");
        comprobPago.setRuc_cliente("15616515611");
        comprobPago.setRazon_social_cliente("Fábrica S.A.");
        comprobPago.setDireccion_fiscal_cliente("Direccion fiscal");
        comprobPago.setTotal(300.50);
        comprobPago.setVenta(this.ventaDTO);
        comprobPago.setMetodo_pago(this.metodoPagoDTO);
        resultado = this.comprobanteDAO.insertar(comprobPago);
        assertTrue(resultado != 0);
        listaComprobPagoId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        ArrayList<Integer> listaComprobPagoId = new ArrayList<>();
        insertarComprobPago(listaComprobPagoId);

        ComprobantesPagoDTO comprobPago = this.comprobanteDAO.obtenerPorId(listaComprobPagoId.get(0));
        assertEquals(comprobPago.getComprobante_pago_id(), listaComprobPagoId.get(0));

        comprobPago = this.comprobanteDAO.obtenerPorId(listaComprobPagoId.get(1));
        assertEquals(comprobPago.getComprobante_pago_id(), listaComprobPagoId.get(1));

        comprobPago = this.comprobanteDAO.obtenerPorId(listaComprobPagoId.get(2));
        assertEquals(comprobPago.getComprobante_pago_id(), listaComprobPagoId.get(2));
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaComprobPagoId = new ArrayList<>();
        insertarComprobPago(listaComprobPagoId);

        ArrayList<ComprobantesPagoDTO> listaComprobPago = this.comprobanteDAO.listarTodos();
        assertEquals(listaComprobPagoId.size(), listaComprobPago.size());
        for (Integer i = 0; i < listaComprobPagoId.size(); i++) {
            assertEquals(listaComprobPagoId.get(i), listaComprobPago.get(i).getComprobante_pago_id());
        }
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        ArrayList<Integer> listaComprobPagoId = new ArrayList<>();
        insertarComprobPago(listaComprobPagoId);
        
        ArrayList<ComprobantesPagoDTO> listaComprobPago = this.comprobanteDAO.listarTodos();
        assertEquals(listaComprobPagoId.size(), listaComprobPago.size());
        for (Integer i = 0; i < listaComprobPagoId.size(); i++) {
            listaComprobPago.get(i).setTotal(listaComprobPago.get(i).getTotal() + 109.98);
            this.ventaDAO.modificar(listaComprobPago.get(i));
        }

        ArrayList<VentasDTO> listaVentasModificados = this.ventaDAO.listarTodos();
        assertEquals(listaComprobPago.size(), listaVentasModificados.size());
        for (Integer i = 0; i < listaComprobPago.size(); i++) {
            assertEquals(listaComprobPago.get(i).getTotal(), listaVentasModificados.get(i).getTotal());
        }
    }

    @Test
    public void testEliminar() {
        prepararUsuarioBase();
        prepararVentaBase();
        prepararPagoBase();

        Comprobante_PagoDTO comprobante = new Comprobante_PagoDTO();
        comprobante.setPago_id(this.pagoBaseId);
        comprobante.setFecha_hora_creacion(LocalDateTime.now());
        comprobante.setTipo_comprobante(TipoComprobante.BOLETA);
        comprobante.setNumero_serie("B001-" + System.currentTimeMillis());
        comprobante.setDni_cliente("71932032");
        comprobante.setNombre_cliente("Lucía Gómez");
        comprobante.setDireccion_fiscal_cliente("Av. Primavera 123");
        comprobante.setCorreo_cliente("lucia@test.com");
        comprobante.setTelefono_cliente("987654321");
        comprobante.setTotal(150.0);

        Integer id = this.comprobanteDAO.insertar(comprobante);

        Comprobante_PagoDTO comprobanteBD = this.comprobanteDAO.obtenerPorId(id);
        assertNotNull(comprobanteBD, "El comprobante debe existir antes de eliminar");

        Integer filas = this.comprobanteDAO.eliminar(comprobanteBD);
        assertEquals(1, filas, "Debería eliminarse exactamente 1 fila");

        Comprobante_PagoDTO eliminado = this.comprobanteDAO.obtenerPorId(id);
        assertNull(eliminado, "El comprobante no debería existir después de eliminar");

        // No agregamos a la lista porque ya fue eliminado
    }

    /**
     * Limpia SOLO los datos creados en este test específico Orden de
     * eliminación respetando integridad referencial: Comprobantes -> Pagos ->
     * Pedidos -> Usuarios
     */
    private void eliminarTodo() {
        try {
            // 1. Eliminar comprobantes creados en este test
            for (Integer comprobanteId : this.comprobantesCreados) {
                try {
                    Comprobante_PagoDTO comprobante = this.comprobanteDAO.obtenerPorId(comprobanteId);
                    if (comprobante != null) {
                        this.comprobanteDAO.eliminar(comprobante);
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar comprobante " + comprobanteId + ": " + e.getMessage());
                }
            }

            // 2. Eliminar pago base (si existe)
            if (this.pagoBaseId != null) {
                try {
                    PagoDTO pago = this.pagoDAO.obtenerPorId(this.pagoBaseId);
                    if (pago != null) {
                        this.pagoDAO.eliminar(pago);
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar pago " + this.pagoBaseId + ": " + e.getMessage());
                }
            }

            // 3. Eliminar pedido base (si existe)
            if (this.pedidoBaseId != null) {
                try {
                    PedidoDTO pedido = this.ventaDAO.obtenerPorId(this.pedidoBaseId);
                    if (pedido != null) {
                        this.ventaDAO.eliminar(pedido);
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar pedido " + this.pedidoBaseId + ": " + e.getMessage());
                }
            }

            // 4. Eliminar usuario base (si existe)
            if (this.usuarioBaseId != null) {
                try {
                    UsuarioDTO usuario = this.usuarioDAO.obtenerPorId(this.usuarioBaseId);
                    if (usuario != null) {
                        this.usuarioDAO.eliminar(usuario);
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar usuario " + this.usuarioBaseId + ": " + e.getMessage());
                }
            }
        } finally {
            // Limpiar referencias
            this.comprobantesCreados.clear();
            this.pagoBaseId = null;
            this.pedidoBaseId = null;
            this.usuarioBaseId = null;
        }
    }

}
