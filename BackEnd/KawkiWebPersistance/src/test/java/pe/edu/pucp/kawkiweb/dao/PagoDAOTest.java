package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import pe.edu.pucp.kawkiweb.daoImp.EstadoPedidoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.MetodoPagoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.PagoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.TipoUsuarioDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodoPagoDTO;
import pe.edu.pucp.kawkiweb.model.PagoDTO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuarioDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

public class PagoDAOTest {

    private PagoDAO pagoDAO;
    private PedidoDAO pedidoDAO;
    private UsuarioDAO usuarioDAO;
    private MetodoPagoDAO metodoPagoDAO;
    private TipoUsuarioDAO tipoUsuarioDAO;
    private EstadoPedidoDAO estadoPedidoDAO;

    private Integer usuarioBaseId;
    private Integer pedidoBaseId;
    private ArrayList<Integer> pagosCreados;

    @BeforeEach
    public void setUp() {
        this.pagoDAO = new PagoDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.metodoPagoDAO = new MetodoPagoDAOImpl();
        this.tipoUsuarioDAO = new TipoUsuarioDAOImpl();
        this.estadoPedidoDAO = new EstadoPedidoDAOImpl();
        this.pagosCreados = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        limpiarDatosDePrueba();
    }

    private void prepararUsuarioBase() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("David");
        usuario.setApePaterno("Espinoza");
        usuario.setApeMaterno("Urco");
        usuario.setDni("71932031");
        usuario.setFechaNacimiento(LocalDate.of(2004, 9, 20));
        usuario.setTelefono("993294837");
        usuario.setDireccion("Av. Santa Cruz 123");
        usuario.setCorreo("david.test" + System.currentTimeMillis() + "@test.com");
        usuario.setNombreUsuario("david04_" + System.currentTimeMillis());
        usuario.setContrasenha("gato");
        usuario.setFechaHoraCreacion(LocalDateTime.now());

        TipoUsuarioDTO tipoUsuario = this.tipoUsuarioDAO.obtenerPorId(TipoUsuarioDTO.ID_CLIENTE);
        usuario.setTipoUsuario(tipoUsuario);

        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
        assertNotNull(this.usuarioBaseId, "Error al crear usuario base");
        assertTrue(this.usuarioBaseId > 0, "ID de usuario inválido");
    }

    private void prepararPedidoBase() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setTotal(120.50);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);

        EstadoPedidoDTO estadoPedido = this.estadoPedidoDAO.obtenerPorId(EstadoPedidoDTO.ID_PREPARACION);
        pedido.setEstado_pedido(estadoPedido);

        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
        assertNotNull(this.pedidoBaseId, "Error al crear pedido base");
        assertTrue(this.pedidoBaseId > 0, "ID de pedido inválido");
    }

    @Test
    public void testInsertar() {
        System.out.println("testInsertar");
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(150.0);
        pago.setFecha_hora_pago(LocalDateTime.now());

        MetodoPagoDTO metodoPago = this.metodoPagoDAO.obtenerPorId(MetodoPagoDTO.ID_YAPE);
        pago.setMetodo_pago(metodoPago);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.pedidoBaseId);
        pago.setPedido(pedido);

        Integer pagoId = this.pagoDAO.insertar(pago);
        assertNotNull(pagoId, "El ID del pago no debería ser null");
        assertTrue(pagoId > 0, "El ID del pago debe ser mayor a 0");

        this.pagosCreados.add(pagoId);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("testObtenerPorId");
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(100.0);
        pago.setFecha_hora_pago(LocalDateTime.now());

        MetodoPagoDTO metodoPago = this.metodoPagoDAO.obtenerPorId(MetodoPagoDTO.ID_TARJETA_BANCARIA);
        pago.setMetodo_pago(metodoPago);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.pedidoBaseId);
        pago.setPedido(pedido);

        Integer pagoId = this.pagoDAO.insertar(pago);
        this.pagosCreados.add(pagoId);

        PagoDTO pagoBD = this.pagoDAO.obtenerPorId(pagoId);
        assertNotNull(pagoBD, "El pago no debería ser null");
        assertEquals(pagoId, pagoBD.getPago_id());
        assertEquals(100.0, pagoBD.getMonto_total(), 0.001);
        assertNotNull(pagoBD.getMetodo_pago(), "El método de pago no debería ser null");
        assertEquals(MetodoPagoDTO.ID_TARJETA_BANCARIA, pagoBD.getMetodo_pago().getMetodo_pago_id());
        assertNotNull(pagoBD.getPedido(), "El pedido no debería ser null");
        assertEquals(this.pedidoBaseId, pagoBD.getPedido().getPedido_id());
    }

    @Test
    public void testListarTodos() {
        System.out.println("testListarTodos");
        prepararUsuarioBase();
        prepararPedidoBase();

        ArrayList<PagoDTO> pagosAntes = this.pagoDAO.listarTodos();
        int cantidadInicial = pagosAntes.size();

        MetodoPagoDTO metodoPago = this.metodoPagoDAO.obtenerPorId(MetodoPagoDTO.ID_PLIN);

        for (int i = 0; i < 3; i++) {
            PagoDTO pago = new PagoDTO();
            pago.setMonto_total(50.0 + i * 10);
            pago.setFecha_hora_pago(LocalDateTime.now());
            pago.setMetodo_pago(metodoPago);

            PedidoDTO pedido = new PedidoDTO();
            pedido.setPedido_id(this.pedidoBaseId);
            pago.setPedido(pedido);

            Integer pagoId = this.pagoDAO.insertar(pago);
            this.pagosCreados.add(pagoId);
        }

        ArrayList<PagoDTO> pagosDespues = this.pagoDAO.listarTodos();
        assertEquals(cantidadInicial + 3, pagosDespues.size(),
                "Deberían existir 3 pagos más que al inicio");

        for (PagoDTO pagoListado : pagosDespues) {
            assertNotNull(pagoListado.getMetodo_pago(), "El método de pago no debería ser null");
            assertNotNull(pagoListado.getPedido(), "El pedido no debería ser null");
        }
    }

    @Test
    public void testModificar() {
        System.out.println("testModificar");
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(80.0);
        pago.setFecha_hora_pago(LocalDateTime.now());

        MetodoPagoDTO metodoPagoYape = this.metodoPagoDAO.obtenerPorId(MetodoPagoDTO.ID_YAPE);
        pago.setMetodo_pago(metodoPagoYape);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.pedidoBaseId);
        pago.setPedido(pedido);

        Integer pagoId = this.pagoDAO.insertar(pago);
        this.pagosCreados.add(pagoId);

        PagoDTO pagoBD = this.pagoDAO.obtenerPorId(pagoId);
        assertNotNull(pagoBD, "El pago debe existir antes de modificar");

        pagoBD.setMonto_total(200.0);
        MetodoPagoDTO metodoPagoPlin = this.metodoPagoDAO.obtenerPorId(MetodoPagoDTO.ID_PLIN);
        pagoBD.setMetodo_pago(metodoPagoPlin);

        Integer filas = this.pagoDAO.modificar(pagoBD);
        assertEquals(1, filas, "Debería modificarse exactamente 1 fila");

        PagoDTO pagoModificado = this.pagoDAO.obtenerPorId(pagoId);
        assertEquals(200.0, pagoModificado.getMonto_total(), 0.001);
        assertNotNull(pagoModificado.getMetodo_pago());
        assertEquals(MetodoPagoDTO.ID_PLIN, pagoModificado.getMetodo_pago().getMetodo_pago_id());
    }

    @Test
    public void testEliminar() {
        System.out.println("testEliminar");
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(120.0);
        pago.setFecha_hora_pago(LocalDateTime.now());

        MetodoPagoDTO metodoPago = this.metodoPagoDAO.obtenerPorId(MetodoPagoDTO.ID_TARJETA_BANCARIA);
        pago.setMetodo_pago(metodoPago);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.pedidoBaseId);
        pago.setPedido(pedido);

        Integer pagoId = this.pagoDAO.insertar(pago);

        PagoDTO pagoBD = this.pagoDAO.obtenerPorId(pagoId);
        assertNotNull(pagoBD, "El pago debe existir antes de eliminar");

        Integer filas = this.pagoDAO.eliminar(pagoBD);
        assertEquals(1, filas, "Debería eliminarse exactamente 1 fila");

        PagoDTO pagoEliminado = this.pagoDAO.obtenerPorId(pagoId);
        assertNull(pagoEliminado, "El pago no debería existir después de eliminar");
    }

    private void limpiarDatosDePrueba() {
        try {
            for (Integer pagoId : this.pagosCreados) {
                try {
                    PagoDTO pago = this.pagoDAO.obtenerPorId(pagoId);
                    if (pago != null) {
                        this.pagoDAO.eliminar(pago);
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar pago " + pagoId + ": " + e.getMessage());
                }
            }

            if (this.pedidoBaseId != null) {
                try {
                    PedidoDTO pedido = this.pedidoDAO.obtenerPorId(this.pedidoBaseId);
                    if (pedido != null) {
                        this.pedidoDAO.eliminar(pedido);
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar pedido " + this.pedidoBaseId + ": " + e.getMessage());
                }
            }

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
            this.pagosCreados.clear();
            this.pedidoBaseId = null;
            this.usuarioBaseId = null;
        }
    }
}
