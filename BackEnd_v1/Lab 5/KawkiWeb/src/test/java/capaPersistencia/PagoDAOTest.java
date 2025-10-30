package capaPersistencia;

import capaDominio.PagoDTO;
import capaDominio.PedidoDTO;
import capaDominio.UsuarioDTO;
import capaDominio.pagoDetalle.MetodoPago;
import capaDominio.pedidoDetalle.Estado_Pedido;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.PagoDAOImpl;
import capaPersistencia.Implementar.PedidoDAOImpl;
import capaPersistencia.Implementar.UsuarioDAOImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PagoDAOTest {

    private PagoDAO pagoDAO;
    private PedidoDAO pedidoDAO;
    private UsuarioDAO usuarioDAO;
    private Integer usuarioBaseId;
    private Integer pedidoBaseId;

    public PagoDAOTest() {
        this.pagoDAO = new PagoDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    // Usuario base
    private void prepararUsuarioBase() {
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

    // Pedido base
    private void prepararPedidoBase() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setTotal(120.50);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
        pedido.setUsuario_id(this.usuarioBaseId);
        pedido.setEstado_pedido(Estado_Pedido.REGISTRADO);
        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
        assertTrue(this.pedidoBaseId != 0);
    }

    @Test
    public void testInsertar() {
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(150.0);
        pago.setFecha_hora_creacion(LocalDateTime.now());
        pago.setMetodo_pago(MetodoPago.YAPE);
        pago.setPedido_id(this.pedidoBaseId);

        Integer pagoId = this.pagoDAO.insertar(pago);
        assertNotNull(pagoId);

        eliminarTodo();
    }

    @Test
    public void testObtenerPorId() {
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(100.0);
        pago.setFecha_hora_creacion(LocalDateTime.now());
        pago.setMetodo_pago(MetodoPago.TARJETA_BANCARIA);
        pago.setPedido_id(this.pedidoBaseId);
        Integer pagoId = this.pagoDAO.insertar(pago);

        PagoDTO pagoBD = this.pagoDAO.obtenerPorId(pagoId);
        assertEquals(pagoId, pagoBD.getPago_id());

        eliminarTodo();
    }

    @Test
    public void testListarTodos() {
        prepararUsuarioBase();
        prepararPedidoBase();

        ArrayList<Integer> listaPagosId = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            PagoDTO pago = new PagoDTO();
            pago.setMonto_total(50.0 + i * 10);
            pago.setFecha_hora_creacion(LocalDateTime.now());
            pago.setMetodo_pago(MetodoPago.PLIN);
            pago.setPedido_id(this.pedidoBaseId);
            listaPagosId.add(this.pagoDAO.insertar(pago));
        }

        ArrayList<PagoDTO> listaPagos = this.pagoDAO.listarTodos();
        assertEquals(listaPagosId.size(), listaPagos.size());

        eliminarTodo();
    }

    @Test
    public void testModificar() {
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(80.0);
        pago.setFecha_hora_creacion(LocalDateTime.now());
        pago.setMetodo_pago(MetodoPago.YAPE);
        pago.setPedido_id(this.pedidoBaseId);
        Integer pagoId = this.pagoDAO.insertar(pago);

        PagoDTO pagoBD = this.pagoDAO.obtenerPorId(pagoId);
        pagoBD.setMonto_total(200.0);
        pagoBD.setMetodo_pago(MetodoPago.PLIN);
        Integer filas = this.pagoDAO.modificar(pagoBD);
        assertEquals(1, filas);

        PagoDTO pagoModificado = this.pagoDAO.obtenerPorId(pagoId);
        assertEquals(200.0, pagoModificado.getMonto_total(), 0.001);
        assertEquals(MetodoPago.PLIN, pagoModificado.getMetodo_pago());

        eliminarTodo();
    }

    @Test
    public void testEliminar() {
        prepararUsuarioBase();
        prepararPedidoBase();

        PagoDTO pago = new PagoDTO();
        pago.setMonto_total(120.0);
        pago.setFecha_hora_creacion(LocalDateTime.now());
        pago.setMetodo_pago(MetodoPago.TARJETA_BANCARIA);
        pago.setPedido_id(this.pedidoBaseId);
        Integer pagoId = this.pagoDAO.insertar(pago);

        PagoDTO pagoBD = this.pagoDAO.obtenerPorId(pagoId);
        assertNotNull(pagoBD);

        Integer filas = this.pagoDAO.eliminar(pagoBD);
        assertEquals(1, filas);

        PagoDTO pagoEliminado = this.pagoDAO.obtenerPorId(pagoId);
        assertNull(pagoEliminado);

        eliminarTodo();
    }

    private void eliminarTodo() {
        ArrayList<PagoDTO> listaPagos = this.pagoDAO.listarTodos();
        for (PagoDTO pago : listaPagos) {
            this.pagoDAO.eliminar(pago);
        }

        ArrayList<PedidoDTO> listaPedidos = this.pedidoDAO.listarTodos();
        for (PedidoDTO pedido : listaPedidos) {
            this.pedidoDAO.eliminar(pedido);
        }

        ArrayList<UsuarioDTO> listaUsuarios = this.usuarioDAO.listarTodos();
        for (UsuarioDTO usuario : listaUsuarios) {
            this.usuarioDAO.eliminar(usuario);
        }
    }
}

