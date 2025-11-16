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
import pe.edu.pucp.kawkiweb.daoImp.DetalleVentasDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.VentasDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.DescuentosDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public class DetalleVentasDAOTest {

    private DetalleVentasDAO detalleVentaDAO;
    private ProductosVariantesDAO prodVarDAO;
    private ProductosDAO productoDAO;
    private VentasDAO ventaDAO;
    private UsuariosDAO usuarioDAO;
    private DescuentosDAO descuentoDAO;

    private ProductosDTO productoDTO;
    private ProductosVariantesDTO prodVarianteDTO;
    private VentasDTO ventaDTO;
    private UsuariosDTO usuarioDTO;
    private DescuentosDTO descuentoDTO;

    private Integer prodBaseId;
    private Integer ventaBaseId;

    public DetalleVentasDAOTest() {
        this.detalleVentaDAO = new DetalleVentasDAOImpl();
        this.ventaDAO = new VentasDAOImpl();
        this.productoDAO = new ProductosDAOImpl();
        this.prodVarDAO = new ProductosVariantesDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
        this.descuentoDAO = new DescuentosDAOImpl();
    }

    @BeforeEach
    void prepararContexto() {
        eliminarTodo();
        prepararUsuarioBase();
        prepararProductoBase();
        prepararProductoVarBase();
        prepararDescuentoBase();
        prepararVentaBase();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Limpiando datos después del test...");
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
    
    private void prepararProductoBase() {
        this.productoDTO = new ProductosDTO();
        this.productoDTO.setDescripcion("Producto prueba");
        CategoriasDTO categoria = new CategoriasDTO();
        categoria.setCategoria_id(CategoriasDTO.ID_DERBY);
        categoria.setNombre(CategoriasDTO.NOMBRE_DERBY);
        this.productoDTO.setCategoria(categoria);
        EstilosDTO estilo = new EstilosDTO();
        estilo.setEstilo_id(EstilosDTO.ID_COMBINADOS);
        estilo.setNombre(EstilosDTO.NOMBRE_COMBINADOS);
        this.productoDTO.setEstilo(estilo);
        this.productoDTO.setPrecio_venta(120.00);
        this.productoDTO.setFecha_hora_creacion(LocalDateTime.now());

        this.prodBaseId = this.productoDAO.insertar(this.productoDTO);
        this.productoDTO.setProducto_id(this.prodBaseId);
        assertTrue(this.prodBaseId != 0);
    }

    private void prepararProductoVarBase() {
        this.prodVarianteDTO = new ProductosVariantesDTO();
        this.prodVarianteDTO.setSKU("DER-ROJ-37");
        this.prodVarianteDTO.setStock(20);
        this.prodVarianteDTO.setStock_minimo(5);
        this.prodVarianteDTO.setAlerta_stock(false);
        this.prodVarianteDTO.setProducto_id(this.prodBaseId);
        ColoresDTO color = new ColoresDTO();
        color.setColor_id(ColoresDTO.ID_ROJO);
        color.setNombre(ColoresDTO.NOMBRE_ROJO);
        this.prodVarianteDTO.setColor(color);
        TallasDTO talla = new TallasDTO();
        talla.setTalla_id(TallasDTO.ID_TREINTA_SIETE);
        talla.setNumero(TallasDTO.NUMERO_TREINTA_SIETE);
        this.prodVarianteDTO.setTalla(talla);
        this.prodVarianteDTO.setFecha_hora_creacion(LocalDateTime.now());
        this.prodVarianteDTO.setDisponible(Boolean.TRUE);

        Integer resultado = this.prodVarDAO.insertar(this.prodVarianteDTO);
        this.prodVarianteDTO.setProd_variante_id(resultado);
        assertTrue(resultado != 0);
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

        Integer resultado = this.descuentoDAO.insertar(this.descuentoDTO);
        this.descuentoDTO.setDescuento_id(resultado);
        assertTrue(resultado != 0);
    }

    //pedido ejemplo:
    private void prepararVentaBase() {
        this.ventaDTO = new VentasDTO();
        this.ventaDTO.setUsuario(this.usuarioDTO);
        this.ventaDTO.setFecha_hora_creacion(LocalDateTime.now());
        this.ventaDTO.setTotal(109.98);
        this.ventaBaseId = this.ventaDAO.insertar(this.ventaDTO);
        this.ventaDTO.setVenta_id(this.ventaBaseId);
        assertTrue(this.ventaBaseId != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        ArrayList<Integer> listaDetalleVentaId = new ArrayList<>();
        insertarDetalleVentas(listaDetalleVentaId);
    }

    private void insertarDetalleVentas(ArrayList<Integer> listaDetalleVentaId) {
        DetalleVentasDTO detalleVenta;

        detalleVenta = new DetalleVentasDTO();
        detalleVenta.setCantidad(1);
        detalleVenta.setPrecio_unitario(109.98);
        detalleVenta.setSubtotal(109.98);
        detalleVenta.setVenta_id(this.ventaBaseId);
        detalleVenta.setProdVariante(this.prodVarianteDTO);
        Integer resultado = this.detalleVentaDAO.insertar(detalleVenta);
        assertTrue(resultado != 0);
        listaDetalleVentaId.add(resultado);

        detalleVenta = new DetalleVentasDTO();
        detalleVenta.setCantidad(3);
        detalleVenta.setPrecio_unitario(194.52);
        detalleVenta.setSubtotal(500.45);
        detalleVenta.setVenta_id(this.ventaBaseId);
        detalleVenta.setProdVariante(this.prodVarianteDTO);
        resultado = this.detalleVentaDAO.insertar(detalleVenta);
        assertTrue(resultado != 0);
        listaDetalleVentaId.add(resultado);

        detalleVenta = new DetalleVentasDTO();
        detalleVenta.setCantidad(2);
        detalleVenta.setPrecio_unitario(165.98);
        detalleVenta.setSubtotal(412.52);
        detalleVenta.setVenta_id(this.ventaBaseId);
        detalleVenta.setProdVariante(this.prodVarianteDTO);
        resultado = this.detalleVentaDAO.insertar(detalleVenta);
        assertTrue(resultado != 0);
        listaDetalleVentaId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        ArrayList<Integer> listaDetalleVentaId = new ArrayList<>();
        insertarDetalleVentas(listaDetalleVentaId);

        DetalleVentasDTO detalleVenta = this.detalleVentaDAO.obtenerPorId(listaDetalleVentaId.get(0));
        assertEquals(detalleVenta.getDetalle_venta_id(), listaDetalleVentaId.get(0));

        detalleVenta = this.detalleVentaDAO.obtenerPorId(listaDetalleVentaId.get(1));
        assertEquals(detalleVenta.getDetalle_venta_id(), listaDetalleVentaId.get(1));

        detalleVenta = this.detalleVentaDAO.obtenerPorId(listaDetalleVentaId.get(2));
        assertEquals(detalleVenta.getDetalle_venta_id(), listaDetalleVentaId.get(2));
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaDetalleVentaId = new ArrayList<>();
        insertarDetalleVentas(listaDetalleVentaId);

        ArrayList<DetalleVentasDTO> listaDetalleVentas = this.detalleVentaDAO.listarTodos();
        assertEquals(listaDetalleVentaId.size(), listaDetalleVentas.size());
        for (Integer i = 0; i < listaDetalleVentaId.size(); i++) {
            assertEquals(listaDetalleVentaId.get(i), listaDetalleVentas.get(i).getDetalle_venta_id());
        }
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        ArrayList<Integer> listaDetalleVentaId = new ArrayList<>();
        insertarDetalleVentas(listaDetalleVentaId);

        ArrayList<DetalleVentasDTO> listaDetalleVentas = this.detalleVentaDAO.listarTodos();
        assertEquals(listaDetalleVentaId.size(), listaDetalleVentas.size());
        for (Integer i = 0; i < listaDetalleVentaId.size(); i++) {
            listaDetalleVentas.get(i).setCantidad(listaDetalleVentas.get(i).getCantidad() + 2);
            listaDetalleVentas.get(i).setSubtotal(329.94);
            this.detalleVentaDAO.modificar(listaDetalleVentas.get(i));
        }

        ArrayList<DetalleVentasDTO> listaDetalleVentaModificados = this.detalleVentaDAO.listarTodos();
        assertEquals(listaDetalleVentas.size(), listaDetalleVentaModificados.size());
        for (Integer i = 0; i < listaDetalleVentas.size(); i++) {
            assertEquals(listaDetalleVentas.get(i).getCantidad(), listaDetalleVentaModificados.get(i).getCantidad());
            assertEquals(listaDetalleVentas.get(i).getSubtotal(), listaDetalleVentaModificados.get(i).getSubtotal());
        }
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        ArrayList<Integer> listaDetalleVentaId = new ArrayList<>();
        insertarDetalleVentas(listaDetalleVentaId);

        eliminarTodo();
    }

    private void eliminarTodo() {

        ArrayList<DetalleVentasDTO> listaDetalleVenta = this.detalleVentaDAO.listarTodos();
        for (Integer i = 0; i < listaDetalleVenta.size(); i++) {
            Integer resultado = this.detalleVentaDAO.eliminar(listaDetalleVenta.get(i));
            assertNotEquals(0, resultado);
            DetalleVentasDTO detalleVenta = this.detalleVentaDAO.obtenerPorId(listaDetalleVenta.get(i).getDetalle_venta_id());
            assertNull(detalleVenta);
        }

        ArrayList<VentasDTO> listarVentas = this.ventaDAO.listarTodos();
        for (VentasDTO venta : listarVentas) {
            this.ventaDAO.eliminar(venta);
        }

        ArrayList<UsuariosDTO> listarUsuarios = this.usuarioDAO.listarTodos();
        for (UsuariosDTO usuario : listarUsuarios) {
            this.usuarioDAO.eliminar(usuario);
        }

        ArrayList<DescuentosDTO> listaDescuentos = this.descuentoDAO.listarTodos();
        for (DescuentosDTO descuento : listaDescuentos) {
            this.descuentoDAO.eliminar(descuento);
        }

        ArrayList<ProductosVariantesDTO> listarProductoVar = this.prodVarDAO.listarTodos();
        for (ProductosVariantesDTO productoVar : listarProductoVar) {
            this.prodVarDAO.eliminar(productoVar);
        }

        ArrayList<ProductosDTO> listarProducto = this.productoDAO.listarTodos();
        for (ProductosDTO producto : listarProducto) {
            this.productoDAO.eliminar(producto);
        }
    }

}
