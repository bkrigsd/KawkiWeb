package pe.edu.pucp.kawkiweb.dao;

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
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

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
        this.usuarioDTO.setActivo(Boolean.TRUE);

        Integer resultado = this.usuarioDAO.insertar(this.usuarioDTO);
        this.usuarioDTO.setUsuarioId(resultado);
        assertTrue(resultado != 0);
    }

    private void prepararVentaBase() {
        this.ventaDTO = new VentasDTO();
        this.ventaDTO.setUsuario(this.usuarioDTO);
        this.ventaDTO.setFecha_hora_creacion(LocalDateTime.now());
        this.ventaDTO.setTotal(109.98);
        this.ventaDTO.setRedSocial(new RedesSocialesDTO(
                RedesSocialesDTO.ID_FACEBOOK, RedesSocialesDTO.NOMBRE_FACEBOOK));
        this.ventaDTO.setEsValida(Boolean.TRUE);
        
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
        comprobPago.setSubtotal(250.00);
        comprobPago.setIgv(18.00);
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
        comprobPago.setSubtotal(250.00);
        comprobPago.setIgv(18.00);
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
        comprobPago.setSubtotal(250.00);
        comprobPago.setIgv(18.00);
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
            listaComprobPago.get(i).setNumero_serie("NumeroSerie_Mod_" + (i + 1));
            listaComprobPago.get(i).setTotal(listaComprobPago.get(i).getTotal() + 109.98);
            this.comprobanteDAO.modificar(listaComprobPago.get(i));
        }

        ArrayList<ComprobantesPagoDTO> listaComprobPagoModificados = this.comprobanteDAO.listarTodos();
        assertEquals(listaComprobPago.size(), listaComprobPagoModificados.size());
        for (Integer i = 0; i < listaComprobPago.size(); i++) {
            assertEquals(listaComprobPago.get(i).getNumero_serie(), listaComprobPagoModificados.get(i).getNumero_serie());
            assertEquals(listaComprobPago.get(i).getTotal(), listaComprobPagoModificados.get(i).getTotal());
        }
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        ArrayList<Integer> listaComprobPagoId = new ArrayList<>();
        insertarComprobPago(listaComprobPagoId);

        eliminarTodo();
    }

    private void eliminarTodo() {
        //Eliminamos los comprobantes
        ArrayList<ComprobantesPagoDTO> listaComprobPago = this.comprobanteDAO.listarTodos();
        for (Integer i = 0; i < listaComprobPago.size(); i++) {
            Integer resultado = this.comprobanteDAO.eliminar(listaComprobPago.get(i));
            assertNotEquals(0, resultado);
            ComprobantesPagoDTO comprobPago = this.comprobanteDAO.obtenerPorId(listaComprobPago.get(i).getComprobante_pago_id());
            assertNull(comprobPago);
        }

        //Eliminamos Venta
        ArrayList<VentasDTO> listaVentas = this.ventaDAO.listarTodos();
        for (Integer i = 0; i < listaVentas.size(); i++) {
            Integer resultado = this.ventaDAO.eliminar(listaVentas.get(i));
            assertNotEquals(0, resultado);
            VentasDTO venta = this.ventaDAO.obtenerPorId(listaVentas.get(i).getVenta_id());
            assertNull(venta);
        }

        // Eliminar usuarios
        ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
        for (Integer i = 0; i < listaUsuarios.size(); i++) {
            Integer resultado = this.usuarioDAO.eliminar(listaUsuarios.get(i));
            assertNotEquals(0, resultado);
            UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(listaUsuarios.get(i).getUsuarioId());
            assertNull(usuario);
        }
    }

}
