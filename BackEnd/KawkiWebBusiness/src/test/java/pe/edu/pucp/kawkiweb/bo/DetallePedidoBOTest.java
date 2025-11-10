//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.model.DetallePedidoDTO;
//import pe.edu.pucp.kawkiweb.model.PedidoDTO;
//import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedido;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Categoria;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Color;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Estilo;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Talla;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//public class DetallePedidoBOTest {
//
//    private DetallePedidoBO detallePedidoBO;
//    private ProductoVarianteBO prodVarBO;
//    private ProductoBO productoBO;
//    private PedidoBO pedidoBO;
//    private Integer prodVarBaseId;
//    private Integer pedidoBaseId;
//    private Integer productoBaseId;
//    private UsuarioBO usuarioBO;
//    private PromocionBO promocionBO;
//    private Integer usuarioBaseId;
//    private Integer promocionBaseId;
//
//    public DetallePedidoBOTest() {
//        this.detallePedidoBO = new DetallePedidoBO();
//        this.pedidoBO = new PedidoBO();
//        this.productoBO = new ProductoBO();
//        this.prodVarBO = new ProductoVarianteBO();
//        this.usuarioBO = new UsuarioBO();
//        this.promocionBO = new PromocionBO();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        this.productoBaseId = this.productoBO.insertar(
//                "Zapato de Prueba para Variantes",
//                Categoria.OXFORD,
//                Estilo.CHAROL,
//                150.0,
//                LocalDateTime.now()
//        );
//        assertNotNull(this.productoBaseId, "El producto de prueba debería haberse creado");
//        assertTrue(this.productoBaseId > 0, "El ID del producto debe ser válido");
//        this.prodVarBaseId = this.prodVarBO.insertar("OXF-CHA-NEG-38", 50, 10, false, this.productoBaseId, Color.NEGRO, Talla.TREINTA_OCHO, null, null, LocalDateTime.now());
//        this.usuarioBaseId = this.usuarioBO.Insertar("David", "Espinoza", "Urco", "71932031", LocalDate.of(2004, 9, 20), "993294837", "Av. Santa Cruz 123", "david@test.com", "david04", "gato", LocalDateTime.now(), TipoUsuario.CLIENTE);
//        assertNotNull(this.usuarioBaseId, "El usuario de prueba debería haberse creado");
//        assertTrue(this.usuarioBaseId > 0, "El ID del usuario debe ser válido");
//        this.promocionBaseId = this.promocionBO.Insertar("Promoción Prueba pedido", TipoCondicion.CANT_MIN_PRODUCTOS, 3, TipoBeneficio.DESCUENTO_PORCENTAJE, 10, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), Boolean.TRUE);
//        assertNotNull(this.promocionBaseId, "La promoción de prueba debería haberse creado");
//        assertTrue(this.promocionBaseId > 0, "El ID de la promoción debe ser válido");
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setUsuarioId(this.usuarioBaseId);
//        PromocionDTO promocion = new PromocionDTO();
//        promocion.setPromocion_id(promocionBaseId);
//        this.pedidoBaseId = this.pedidoBO.insertar(usuario, LocalDateTime.now(), LocalDateTime.now(), 109.98, EstadoPedido.PENDIENTE, promocion);
//
//        eliminarTodo();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Limpia las variantes después de cada prueba
//        eliminarTodo();
//
//        if (this.pedidoBaseId != null) {
//            Integer resultado = this.pedidoBO.eliminar(this.pedidoBaseId);
//            assertNotEquals(0, resultado, "El pedido de prueba debería eliminarse correctamente");
//        }
//        if (this.usuarioBaseId != null) {
//            Integer resultado = this.usuarioBO.eliminar(this.usuarioBaseId);
//            assertNotEquals(0, resultado, "El usuario de prueba debería eliminarse correctamente");
//        }
//        if (this.promocionBaseId != null) {
//            Integer resultado = this.promocionBO.eliminar(this.promocionBaseId);
//            assertNotEquals(0, resultado, "La promoción de prueba debería eliminarse correctamente");
//        }
//        if (this.prodVarBaseId != null) {
//            Integer resultado = this.prodVarBO.eliminar(this.prodVarBaseId);
//            assertNotEquals(0, resultado, "El productoVariante de prueba debería eliminarse correctamente");
//        }
//        if (this.productoBaseId != null) {
//            Integer resultado = this.productoBO.eliminar(this.productoBaseId);
//            assertNotEquals(0, resultado, "El producto de prueba debería eliminarse correctamente");
//        }
//
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaDetallePedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetallePedidoId);
//    }
//
//    private void insertarDetallePedidos(ArrayList<Integer> listaDetalle_PedidoId) {
//        PedidoDTO pedido = new PedidoDTO();
//        pedido.setPedido_id(this.pedidoBaseId);
//        ProductoVarianteDTO productoVar = new ProductoVarianteDTO();
//        productoVar.setProd_variante_id(this.prodVarBaseId);
//        Integer resultado = this.detallePedidoBO.insertar(productoVar, pedido, 1, 109.98, 109.98);
//        assertTrue(resultado != 0);
//        listaDetalle_PedidoId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaDetallePedidosId = new ArrayList<>();
//        insertarDetallePedidos(listaDetallePedidosId);
//
//        DetallePedidoDTO detallePedido = this.detallePedidoBO.obtenerPorId(listaDetallePedidosId.get(0));
//        assertEquals(detallePedido.getDetalle_pedido_id(), listaDetallePedidosId.get(0));
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaDetallePedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetallePedidoId);
//
//        ArrayList<DetallePedidoDTO> listaDetallePedidos = this.detallePedidoBO.listarTodos();
//        assertEquals(listaDetallePedidoId.size(), listaDetallePedidos.size());
//        for (Integer i = 0; i < listaDetallePedidoId.size(); i++) {
//            assertEquals(listaDetallePedidoId.get(i), listaDetallePedidos.get(i).getDetalle_pedido_id());
//        }
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaDetallePedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetallePedidoId);
//
//        ArrayList<DetallePedidoDTO> listaDetallePedidos = this.detallePedidoBO.listarTodos();
//        assertEquals(listaDetallePedidoId.size(), listaDetallePedidos.size());
//        for (Integer i = 0; i < listaDetallePedidoId.size(); i++) {
//            listaDetallePedidos.get(i).setCantidad(listaDetallePedidos.get(i).getCantidad() + 2);
//            listaDetallePedidos.get(i).setSubtotal(329.94);
//            this.detallePedidoBO.modificar(listaDetallePedidos.get(i).getDetalle_pedido_id(), listaDetallePedidos.get(i).getProductoVar(), listaDetallePedidos.get(i).getPedido(), listaDetallePedidos.get(i).getCantidad(), listaDetallePedidos.get(i).getPrecio_unitario(), listaDetallePedidos.get(i).getSubtotal());
//        }
//        ArrayList<DetallePedidoDTO> listaDetallePedidoModificados = this.detallePedidoBO.listarTodos();
//        assertEquals(listaDetallePedidos.size(), listaDetallePedidoModificados.size());
//        for (Integer i = 0; i < listaDetallePedidos.size(); i++) {
//            assertEquals(listaDetallePedidos.get(i).getCantidad(), listaDetallePedidoModificados.get(i).getCantidad());
//            assertEquals(listaDetallePedidos.get(i).getSubtotal(), listaDetallePedidoModificados.get(i).getSubtotal(), 0.0001);
//        }
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaDetallePedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetallePedidoId);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<DetallePedidoDTO> listaDetallePedidos = this.detallePedidoBO.listarTodos();
//        for (Integer i = 0; i < listaDetallePedidos.size(); i++) {
//            Integer resultado = this.detallePedidoBO.eliminar(listaDetallePedidos.get(i).getDetalle_pedido_id());
//            assertNotEquals(0, resultado);
//            DetallePedidoDTO pedido = this.detallePedidoBO.obtenerPorId(listaDetallePedidos.get(i).getDetalle_pedido_id());
//            assertNull(pedido);
//        }
//    }
//}
