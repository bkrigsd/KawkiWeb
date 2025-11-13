package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.kawkiweb.daoImp.VentasDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.DescuentosDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public class VentasDAOTest {

    private VentasDAO ventaDAO;
    private UsuariosDAO usuarioDAO;
    private DescuentosDAO descuentoDAO;
    private Integer usuarioBaseId;
    private Integer promocionBaseId;
    private UsuariosDTO usuarioDTO;
    private DescuentosDTO descuentoDTO;

    public VentasDAOTest() {
        this.ventaDAO = new VentasDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
        this.descuentoDAO = new DescuentosDAOImpl();
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
        ArrayList<VentasDTO> listaVentas = this.ventaDAO.listarTodos();
        for (Integer i = 0; i < listaVentas.size(); i++) {
            Integer resultado = this.ventaDAO.eliminar(listaVentas.get(i));
            assertNotEquals(0, resultado);
            VentasDTO venta = this.ventaDAO.obtenerPorId(listaVentas.get(i).getVenta_id());
            assertNull(venta);
        }

        // Eliminar promociones
        ArrayList<DescuentosDTO> listaDescuentos = this.descuentoDAO.listarTodos();
        for (Integer i = 0; i < listaDescuentos.size(); i++) {
            Integer resultado = this.descuentoDAO.eliminar(listaDescuentos.get(i));
            assertNotEquals(0, resultado);
            DescuentosDTO descuento = this.descuentoDAO.obtenerPorId(listaDescuentos.get(i).getDescuento_id());
            assertNull(descuento);
        }

        // Eliminar usuarios
        ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
        for (Integer i = 0; i < listaUsuarios.size(); i++) {
            Integer resultado = this.usuarioDAO.eliminar(listaUsuarios.get(i));
            assertNotEquals(0, resultado);
            UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(listaUsuarios.get(i).getUsuarioId());
            assertNull(usuario);
        }

        // Reiniciar IDs
        this.usuarioBaseId = null;
        this.promocionBaseId = null;
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

        this.usuarioBaseId = this.usuarioDAO.insertar(this.usuarioDTO);
        this.usuarioDTO.setUsuarioId(usuarioBaseId);
        assertTrue(this.usuarioBaseId != 0);
    }

    private void prepararDescuentoBase() {
        this.descuentoDTO = new DescuentosDTO();
        this.descuentoDTO.setDescripcion("Promoción Prueba pedido");
        this.descuentoDTO.setFecha_inicio(LocalDateTime.now());
        this.descuentoDTO.setFecha_fin(LocalDateTime.now().plusMonths(1));

        // Crear TiposBeneficioDTO
        TiposBeneficioDTO tipoBeneficio = new TiposBeneficioDTO();
        tipoBeneficio.setTipo_beneficio_id(TiposBeneficioDTO.ID_DESCUENTO_PORCENTAJE);
        tipoBeneficio.setNombre(TiposBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE);
        this.descuentoDTO.setTipo_beneficio(tipoBeneficio);

        // Crear TiposCondicionDTO
        TiposCondicionDTO tipoCondicion = new TiposCondicionDTO();
        tipoCondicion.setTipo_condicion_id(TiposCondicionDTO.ID_CANT_MIN_PRODUCTOS);
        tipoCondicion.setNombre(TiposCondicionDTO.NOMBRE_CANT_MIN_PRODUCTOS);
        this.descuentoDTO.setTipo_condicion(tipoCondicion);

        this.descuentoDTO.setValor_beneficio(10);
        this.descuentoDTO.setValor_condicion(3); // 3 productos mínimos
        this.descuentoDTO.setActivo(Boolean.TRUE);

        this.promocionBaseId = this.descuentoDAO.insertar(this.descuentoDTO);
        this.descuentoDTO.setDescuento_id(promocionBaseId);
        assertTrue(this.promocionBaseId != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        prepararUsuarioBase();
        prepararDescuentoBase();
        ArrayList<Integer> listaVentaId = new ArrayList<>();
        insertarVentas(listaVentaId);
    }

    private void insertarVentas(ArrayList<Integer> listaVentaId) {
        VentasDTO venta;

        // Venta 1
        venta = new VentasDTO();
        venta.setUsuario(this.usuarioDTO);
        venta.setFecha_hora_creacion(LocalDateTime.now());
        venta.setTotal(109.98);
        Integer resultado = this.ventaDAO.insertar(venta);
        assertTrue(resultado != 0);
        listaVentaId.add(resultado);

        // Venta 2
        venta = new VentasDTO();
        venta.setUsuario(this.usuarioDTO);
        venta.setFecha_hora_creacion(LocalDateTime.now());
        venta.setTotal(190.98);
        venta.setDescuento(this.descuentoDTO);
        resultado = this.ventaDAO.insertar(venta);
        assertTrue(resultado != 0);
        listaVentaId.add(resultado);

        // Venta 3
        venta = new VentasDTO();
        venta.setUsuario(this.usuarioDTO);
        venta.setFecha_hora_creacion(LocalDateTime.now());
        venta.setTotal(210.98);
        venta.setDescuento(this.descuentoDTO);
        resultado = this.ventaDAO.insertar(venta);
        assertTrue(resultado != 0);
        listaVentaId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        prepararUsuarioBase();
        prepararDescuentoBase();
        ArrayList<Integer> listaVentaId = new ArrayList<>();
        insertarVentas(listaVentaId);

        VentasDTO venta = this.ventaDAO.obtenerPorId(listaVentaId.get(0));
        assertEquals(venta.getVenta_id(), listaVentaId.get(0));

        venta = this.ventaDAO.obtenerPorId(listaVentaId.get(1));
        assertEquals(venta.getVenta_id(), listaVentaId.get(1));

        venta = this.ventaDAO.obtenerPorId(listaVentaId.get(2));
        assertEquals(venta.getVenta_id(), listaVentaId.get(2));
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        prepararUsuarioBase();
        prepararDescuentoBase();
        ArrayList<Integer> listaVentaId = new ArrayList<>();
        insertarVentas(listaVentaId);

        ArrayList<VentasDTO> listaVentas = this.ventaDAO.listarTodos();
        assertEquals(listaVentaId.size(), listaVentas.size());
        for (Integer i = 0; i < listaVentaId.size(); i++) {
            assertEquals(listaVentaId.get(i), listaVentas.get(i).getVenta_id());
        }
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        prepararUsuarioBase();
        prepararDescuentoBase();
        ArrayList<Integer> listaVentaId = new ArrayList<>();
        insertarVentas(listaVentaId);

        ArrayList<VentasDTO> listaVentas = this.ventaDAO.listarTodos();
        assertEquals(listaVentaId.size(), listaVentas.size());
        for (Integer i = 0; i < listaVentaId.size(); i++) {
            listaVentas.get(i).setTotal(listaVentas.get(i).getTotal() + 109.98);
            this.ventaDAO.modificar(listaVentas.get(i));
        }

        ArrayList<VentasDTO> listaVentasModificados = this.ventaDAO.listarTodos();
        assertEquals(listaVentas.size(), listaVentasModificados.size());
        for (Integer i = 0; i < listaVentas.size(); i++) {
            assertEquals(listaVentas.get(i).getTotal(), listaVentasModificados.get(i).getTotal());
        }
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        prepararUsuarioBase();
        prepararDescuentoBase();
        ArrayList<Integer> listaVentaId = new ArrayList<>();
        insertarVentas(listaVentaId);

        limpiarDatosResiduales();
    }
}
