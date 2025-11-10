package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.PromocionDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

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

    @BeforeEach
    public void setUp() {
        limpiarDatosResiduales();
    }

    @AfterEach
    public void tearDown() {
        limpiarDatosResiduales();
    }

    private void limpiarDatosResiduales() {
        // Eliminar pedidos primero (por dependencias de FK)
        ArrayList<VentasDTO> listaPedidos = this.pedidoDAO.listarTodos();
        for (VentasDTO pedido : listaPedidos) {
            this.pedidoDAO.eliminar(pedido);
        }

        // Eliminar promociones
        ArrayList<DescuentosDTO> listaPromociones = this.promocionDAO.listarTodos();
        for (DescuentosDTO promocion : listaPromociones) {
            this.promocionDAO.eliminar(promocion);
        }

        // Eliminar usuarios
        ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
        for (UsuariosDTO usuario : listaUsuarios) {
            this.usuarioDAO.eliminar(usuario);
        }

        // Reiniciar IDs
        this.usuarioBaseId = null;
        this.promocionBaseId = null;
    }

    private void prepararUsuarioBase() {
        UsuariosDTO usuario = new UsuariosDTO();
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

        // Crear TiposUsuarioDTO
        TiposUsuarioDTO tipoCliente = new TiposUsuarioDTO();
        tipoCliente.setTipoUsuarioId(TiposUsuarioDTO.ID_CLIENTE);
        tipoCliente.setNombre(TiposUsuarioDTO.NOMBRE_CLIENTE);
        usuario.setTipoUsuario(tipoCliente);

        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
        assertTrue(this.usuarioBaseId != 0);
    }

    private void prepararPromocionBase() {
        DescuentosDTO promocion = new DescuentosDTO();
        promocion.setDescripcion("Promoción Prueba pedido");
        promocion.setFecha_inicio(LocalDateTime.now());
        promocion.setFecha_fin(LocalDateTime.now().plusMonths(1));

        // Crear TiposBeneficioDTO
        TiposBeneficioDTO tipoBeneficio = new TiposBeneficioDTO();
        tipoBeneficio.setTipo_beneficio_id(TiposBeneficioDTO.ID_DESCUENTO_PORCENTAJE);
        tipoBeneficio.setNombre(TiposBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE);
        promocion.setTipo_beneficio(tipoBeneficio);

        // Crear TiposCondicionDTO
        TiposCondicionDTO tipoCondicion = new TiposCondicionDTO();
        tipoCondicion.setTipo_condicion_id(TiposCondicionDTO.ID_CANT_MIN_PRODUCTOS);
        tipoCondicion.setNombre(TiposCondicionDTO.NOMBRE_CANT_MIN_PRODUCTOS);
        promocion.setTipo_condicion(tipoCondicion);

        promocion.setValor_beneficio(10);
        promocion.setValor_condicion(3); // 3 productos mínimos
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
    }

    private void insertarPedidos(ArrayList<Integer> listaPedidoId) {
        // Crear EstadoPedidoDTO para reutilizar
        EstadoPedidoDTO estadoPendiente = new EstadoPedidoDTO();
        estadoPendiente.setEstado_pedido_id(EstadoPedidoDTO.ID_PENDIENTE);
        estadoPendiente.setNombre(EstadoPedidoDTO.NOMBRE_PENDIENTE);

        // Pedido 1
        VentasDTO pedido = new VentasDTO();
        pedido.setTotal(109.98);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());

        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);

        DescuentosDTO promo = new DescuentosDTO();
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);

        pedido.setEstado_pedido(estadoPendiente);

        Integer resultado = this.pedidoDAO.insertar(pedido);
        assertTrue(resultado != 0);
        listaPedidoId.add(resultado);

        // Pedido 2
        pedido = new VentasDTO();
        pedido.setTotal(94.50);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());

        usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);

        promo = new DescuentosDTO();
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);

        pedido.setEstado_pedido(estadoPendiente);

        resultado = this.pedidoDAO.insertar(pedido);
        assertTrue(resultado != 0);
        listaPedidoId.add(resultado);

        // Pedido 3
        pedido = new VentasDTO();
        pedido.setTotal(222.48);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());

        usuario = new UsuariosDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);

        promo = new DescuentosDTO();
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);

        pedido.setEstado_pedido(estadoPendiente);

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

        VentasDTO pedido = this.pedidoDAO.obtenerPorId(listaPedidoId.get(0));
        assertEquals(pedido.getPedido_id(), listaPedidoId.get(0));

        pedido = this.pedidoDAO.obtenerPorId(listaPedidoId.get(1));
        assertEquals(pedido.getPedido_id(), listaPedidoId.get(1));

        pedido = this.pedidoDAO.obtenerPorId(listaPedidoId.get(2));
        assertEquals(pedido.getPedido_id(), listaPedidoId.get(2));
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);

        ArrayList<VentasDTO> listaPedidos = this.pedidoDAO.listarTodos();
        assertEquals(listaPedidoId.size(), listaPedidos.size());
        for (Integer i = 0; i < listaPedidoId.size(); i++) {
            assertEquals(listaPedidoId.get(i), listaPedidos.get(i).getPedido_id());
        }
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);

        // Crear EstadoPedidoDTO para ENTREGADO
        EstadoPedidoDTO estadoEntregado = new EstadoPedidoDTO();
        estadoEntregado.setEstado_pedido_id(EstadoPedidoDTO.ID_ENTREGADO);
        estadoEntregado.setNombre(EstadoPedidoDTO.NOMBRE_ENTREGADO);

        ArrayList<VentasDTO> listaPedidos = this.pedidoDAO.listarTodos();
        assertEquals(listaPedidoId.size(), listaPedidos.size());
        for (Integer i = 0; i < listaPedidoId.size(); i++) {
            listaPedidos.get(i).setTotal(listaPedidos.get(i).getTotal() + 109.98);
            listaPedidos.get(i).setFecha_hora_creacion(LocalDateTime.now());
            listaPedidos.get(i).setFecha_hora_ultimo_estado(LocalDateTime.now());
            listaPedidos.get(i).setEstado_pedido(estadoEntregado);
            this.pedidoDAO.modificar(listaPedidos.get(i));
        }

        ArrayList<VentasDTO> listaPedidosModificados = this.pedidoDAO.listarTodos();
        assertEquals(listaPedidos.size(), listaPedidosModificados.size());
        for (Integer i = 0; i < listaPedidos.size(); i++) {
            assertEquals(listaPedidos.get(i).getTotal(), listaPedidosModificados.get(i).getTotal(), 0.0001);
            assertEquals(listaPedidos.get(i).getEstado_pedido().getEstado_pedido_id(),
                    listaPedidosModificados.get(i).getEstado_pedido().getEstado_pedido_id());
        }
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        prepararUsuarioBase();
        prepararPromocionBase();
        ArrayList<Integer> listaPedidoId = new ArrayList<>();
        insertarPedidos(listaPedidoId);

        ArrayList<VentasDTO> listaPedidos = this.pedidoDAO.listarTodos();
        for (Integer i = 0; i < listaPedidos.size(); i++) {
            Integer resultado = this.pedidoDAO.eliminar(listaPedidos.get(i));
            assertNotEquals(0, resultado);
            VentasDTO pedido = this.pedidoDAO.obtenerPorId(listaPedidos.get(i).getPedido_id());
            assertNull(pedido);
        }
    }
}
