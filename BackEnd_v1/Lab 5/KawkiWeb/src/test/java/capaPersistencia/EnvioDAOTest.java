package capaPersistencia;

import capaDominio.EnviosDTO;
import capaDominio.PedidoDTO;
import capaDominio.PromocionDTO;
import capaDominio.UsuarioDTO;
import capaDominio.envioDetalle.Courier;
import capaDominio.envioDetalle.Estado_Envio;
import capaDominio.pedidoDetalle.Estado_Pedido;
import capaDominio.promocionDetalle.TipoBeneficio;
import capaDominio.promocionDetalle.TipoCondicion;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.EnvioDAOImpl;
import capaPersistencia.Implementar.PedidoDAOImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class EnvioDAOTest {
    
    private EnvioDAO envioDAO;
    private PedidoDAO pedidoDAO;
    private UsuarioDAO usuarioDAO;
    private PromocionDAO promocionDAO;
    private Integer pedidoBaseId;
    private Integer usuarioBaseId;
    private Integer promocionBaseId;

    public EnvioDAOTest() {
        this.envioDAO = new EnvioDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
    }
    
    @BeforeEach
    void prepararContexto() {
        eliminarTodo(); 
        prepararUsuarioBase();
        prepararPromocionBase();
        prepararPedidoBase();
    }

    //usuario ejemplo:
    private void prepararUsuarioBase() {
        // Insertamos un usuario de prueba para asociar variantes
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Smith");
        usuario.setApePaterno("Porras");
        usuario.setApeMaterno("Urco");
        usuario.setDni("38492057");
        usuario.setFechaNacimiento(LocalDate.of(2008, 9, 20));
        usuario.setTelefono("993253637");
        usuario.setDireccion("Av. Santa Cruz 412");
        usuario.setCorreo("smith@test.com");
        usuario.setNombreUsuario("smith23");
        usuario.setContrasenha("ejemplo");
        usuario.setFechaHoraCreacion(LocalDateTime.now());
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
        assertTrue(this.usuarioBaseId != 0);
    }

    //promocion ejemplo:
    private void prepararPromocionBase() {
        // Insertamos una promoción de prueba para asociar variantes
        PromocionDTO promocion = new PromocionDTO();
        promocion.setDescripcion("Promoción Prueba envio");
        promocion.setFecha_inicio(LocalDateTime.now());
        promocion.setFecha_fin(LocalDateTime.now().plusMonths(2));
        promocion.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
        promocion.setTipo_condicion(TipoCondicion.MONTO_MIN_COMPRA);
        promocion.setValor_beneficio(15);
        promocion.setValor_condicion(100); //monto minimo
        promocion.setActivo(Boolean.TRUE);
        this.promocionBaseId = this.promocionDAO.insertar(promocion);
        assertTrue(this.promocionBaseId != 0);
    }
    
    //pedido ejemplo:
    private void prepararPedidoBase() {
        // Insertamos un pedido de prueba para asociar variantes
        PedidoDTO pedido = new PedidoDTO();
        pedido = new PedidoDTO();
        pedido.setTotal(120.30);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);
        PromocionDTO promo = new PromocionDTO();
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);
        pedido.setEstado_pedido(Estado_Pedido.PENDIENTE);
        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
        assertTrue(this.pedidoBaseId != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        ArrayList<Integer> listaEnvioId = new ArrayList<>();
        insertarEnvios(listaEnvioId);
        eliminarTodo();
    }

    private void insertarEnvios(ArrayList<Integer> listaEnvioId) {
        EnviosDTO envio;
        envio = new EnviosDTO();
        envio.setFecha_envio(LocalDateTime.now());
        envio.setFecha_ultimo_estado(LocalDateTime.now());
        envio.setCosto_envio(8.00);
        envio.setCourier(Courier.OLVA_COURIER);
        envio.setDireccion_entrega("Av. Santa Cruz 412");
        envio.setEs_delivery(Boolean.TRUE);
        envio.setEstado(Estado_Envio.ENCARGADO_AL_COURIER);
        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.pedidoBaseId);
        envio.setPedido(pedido);
        Integer resultado = this.envioDAO.insertar(envio);
        assertTrue(resultado != 0);
        listaEnvioId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        ArrayList<Integer> listaEnvioId = new ArrayList<>();
        insertarEnvios(listaEnvioId);
        EnviosDTO envio = this.envioDAO.obtenerPorId(listaEnvioId.get(0));
        assertEquals(envio.getEnvio_id(), listaEnvioId.get(0));
        eliminarTodo();
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaEnviosId = new ArrayList<>();
        insertarEnvios(listaEnviosId);
        ArrayList<EnviosDTO> listaEnvios = this.envioDAO.listarTodos();
        assertEquals(listaEnviosId.size(), listaEnvios.size());
        for (Integer i = 0; i < listaEnviosId.size(); i++) {
            assertEquals(listaEnviosId.get(i), listaEnvios.get(i).getEnvio_id());
        }
        eliminarTodo();
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        ArrayList<Integer> listaEnviosId = new ArrayList<>();
        insertarEnvios(listaEnviosId);

        ArrayList<EnviosDTO> listaEnvios = this.envioDAO.listarTodos();
        assertEquals(listaEnviosId.size(), listaEnvios.size());
        for (Integer i = 0; i < listaEnviosId.size(); i++) {
            listaEnvios.get(i).setEstado(Estado_Envio.ENTREGADO);
            listaEnvios.get(i).setFecha_ultimo_estado(LocalDateTime.now());
            this.envioDAO.modificar(listaEnvios.get(i));
        }

        ArrayList<EnviosDTO> listaEnviosModificados = this.envioDAO.listarTodos();
        assertEquals(listaEnvios.size(), listaEnviosModificados.size());
        for (Integer i = 0; i < listaEnvios.size(); i++) {
            assertEquals(listaEnvios.get(i).getEstado(), listaEnviosModificados.get(i).getEstado());
            assertEquals(listaEnvios.get(i).getFecha_ultimo_estado(), listaEnviosModificados.get(i).getFecha_ultimo_estado());
        }
        eliminarTodo();
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        ArrayList<Integer> listaEnviosId = new ArrayList<>();
        insertarEnvios(listaEnviosId);
        eliminarTodo();
    }

    private void eliminarTodo() {
        ArrayList<EnviosDTO> listaEnvios = this.envioDAO.listarTodos();
        for (Integer i = 0; i < listaEnvios.size(); i++) {
            Integer resultado = this.envioDAO.eliminar(listaEnvios.get(i));
            assertNotEquals(0, resultado);
            EnviosDTO envio = this.envioDAO.obtenerPorId(listaEnvios.get(i).getEnvio_id());
            assertNull(envio);
        }

        ArrayList<PedidoDTO> listarPedidos = this.pedidoDAO.listarTodos();
        for (PedidoDTO pedido : listarPedidos) {
            this.pedidoDAO.eliminar(pedido);
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
