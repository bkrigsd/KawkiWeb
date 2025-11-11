//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import pe.edu.pucp.kawkiweb.daoImp.DetalleVentasDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.VentasDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.DescuentosDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
//import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
//import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
//import pe.edu.pucp.kawkiweb.model.VentasDTO;
//import pe.edu.pucp.kawkiweb.model.ProductosDTO;
//import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
//import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
//import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
//import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
//import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
//
//public class DetalleVentasDAOTest {
//
//    private DetalleVentasDAO detalle_pedidoDAO;
//    private ProductosVariantesDAO prodVarDAO;
//    private ProductosDAO productoDAO;
//    private VentasDAO pedidoDAO;
//    private Integer prodVarBaseId;
//    private Integer pedidoBaseId;
//    private Integer productoBaseId;
//    private UsuariosDAO usuarioDAO;
//    private DescuentosDAO promocionDAO;
//    private Integer usuarioBaseId;
//    private Integer promocionBaseId;
//
//    public DetalleVentasDAOTest() {
//        this.detalle_pedidoDAO = new DetalleVentasDAOImpl();
//        this.pedidoDAO = new VentasDAOImpl();
//        this.productoDAO = new ProductosDAOImpl();
//        this.prodVarDAO = new ProductosVariantesDAOImpl();
//        this.usuarioDAO = new UsuariosDAOImpl();
//        this.promocionDAO = new DescuentosDAOImpl();
//    }
//
//    @BeforeEach
//    void prepararContexto() {
//        eliminarTodo();
//        prepararProductoBase();
//        prepararProductoVarBase();
//        prepararUsuarioBase();
//        prepararPromocionBase();
//        prepararPedidoBase();
//    }
//
//    //producto ejemplo:
//    private void prepararProductoBase() {
//        ProductosDTO producto = new ProductosDTO();
//        producto.setDescripcion("Producto prueba");
//        
//        // Crear CategoriasDTO
//        CategoriasDTO categoria = new CategoriasDTO();
//        categoria.setCategoria_id(CategoriasDTO.ID_DERBY);
//        categoria.setNombre(CategoriasDTO.NOMBRE_DERBY);
//        producto.setCategoria(categoria);
//        
//        // Crear EstilosDTO
//        EstilosDTO estilo = new EstilosDTO();
//        estilo.setEstilo_id(EstilosDTO.ID_COMBINADOS);
//        estilo.setNombre(EstilosDTO.NOMBRE_COMBINADOS);
//        producto.setEstilo(estilo);
//        
//        producto.setPrecio_venta(120.00);
//        producto.setFecha_hora_creacion(LocalDateTime.now());
//        
//        this.productoBaseId = this.productoDAO.insertar(producto);
//        assertTrue(this.productoBaseId != 0);
//    }
//
//    //productoVariante ejemplo:
//    private void prepararProductoVarBase() {
//        ProductosVariantesDTO prodVariante = new ProductosVariantesDTO();
//        prodVariante.setSKU("DER-ROJ-37");
//        prodVariante.setStock(20);
//        prodVariante.setStock_minimo(5);
//        prodVariante.setAlerta_stock(false);
//        prodVariante.setProducto_id(this.productoBaseId);
//        
//        // Crear ColoresDTO
//        ColoresDTO color = new ColoresDTO();
//        color.setColor_id(ColoresDTO.ID_ROJO);
//        color.setNombre(ColoresDTO.NOMBRE_ROJO);
//        prodVariante.setColor(color);
//        
//        // Crear TallasDTO
//        TallasDTO talla = new TallasDTO();
//        talla.setTalla_id(TallasDTO.ID_TREINTA_SIETE);
//        talla.setNumero(TallasDTO.NUMERO_TREINTA_SIETE);
//        prodVariante.setTalla(talla);
//        
//        // Crear TiposBeneficioDTO
//        TiposBeneficioDTO tipoBeneficio = new TiposBeneficioDTO();
//        tipoBeneficio.setTipo_beneficio_id(TiposBeneficioDTO.ID_DESCUENTO_PORCENTAJE);
//        tipoBeneficio.setNombre(TiposBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE);
//        prodVariante.setTipo_beneficio(tipoBeneficio);
//        
//        prodVariante.setValor_beneficio(10);
//        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
//        
//        this.prodVarBaseId = this.prodVarDAO.insertar(prodVariante);
//        assertTrue(this.prodVarBaseId != 0);
//    }
//
//    //usuario ejemplo:
//    private void prepararUsuarioBase() {
//        UsuariosDTO usuario = new UsuariosDTO();
//        usuario.setNombre("Nestor");
//        usuario.setApePaterno("Espinoza");
//        usuario.setApeMaterno("Urco");
//        usuario.setDni("71926451");
//        usuario.setFechaNacimiento(LocalDate.of(2004, 9, 20));
//        usuario.setTelefono("992323577");
//        usuario.setDireccion("Av. Santa Cruz 321");
//        usuario.setCorreo("nestor@test.com");
//        usuario.setNombreUsuario("nestor007");
//        usuario.setContrasenha("perro");
//        usuario.setFechaHoraCreacion(LocalDateTime.now());
//        
//        // Crear TiposUsuarioDTO
//        TiposUsuarioDTO tipoCliente = new TiposUsuarioDTO();
//        tipoCliente.setTipoUsuarioId(TiposUsuarioDTO.ID_CLIENTE);
//        tipoCliente.setNombre(TiposUsuarioDTO.NOMBRE_CLIENTE);
//        usuario.setTipoUsuario(tipoCliente);
//        
//        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
//        assertTrue(this.usuarioBaseId != 0);
//    }
//
//    //promocion ejemplo:
//    private void prepararPromocionBase() {
//        DescuentosDTO promocion = new DescuentosDTO();
//        promocion.setDescripcion("Promoción Prueba de detalle pedido");
//        promocion.setFecha_inicio(LocalDateTime.now());
//        promocion.setFecha_fin(LocalDateTime.now().plusMonths(1));
//        
//        // Crear TiposBeneficioDTO
//        TiposBeneficioDTO tipoBeneficio = new TiposBeneficioDTO();
//        tipoBeneficio.setTipo_beneficio_id(TiposBeneficioDTO.ID_DESCUENTO_PORCENTAJE);
//        tipoBeneficio.setNombre(TiposBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE);
//        promocion.setTipo_beneficio(tipoBeneficio);
//        
//        // Crear TiposCondicionDTO
//        TiposCondicionDTO tipoCondicion = new TiposCondicionDTO();
//        tipoCondicion.setTipo_condicion_id(TiposCondicionDTO.ID_CANT_MIN_PRODUCTOS);
//        tipoCondicion.setNombre(TiposCondicionDTO.NOMBRE_CANT_MIN_PRODUCTOS);
//        promocion.setTipo_condicion(tipoCondicion);
//        
//        promocion.setValor_beneficio(15);
//        promocion.setValor_condicion(2);
//        promocion.setActivo(Boolean.TRUE);
//        
//        this.promocionBaseId = this.promocionDAO.insertar(promocion);
//        assertTrue(this.promocionBaseId != 0);
//    }
//
//    //pedido ejemplo:
//    private void prepararPedidoBase() {
//        VentasDTO pedido = new VentasDTO();
//        pedido.setTotal(120.00);
//        pedido.setFecha_hora_creacion(LocalDateTime.now());
//        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
//        
//        UsuariosDTO usuario = new UsuariosDTO();
//        usuario.setUsuarioId(this.usuarioBaseId);
//        pedido.setUsuario(usuario);
//        
//        DescuentosDTO promo = new DescuentosDTO();
//        promo.setPromocion_id(this.promocionBaseId);
//        pedido.setPromocion(promo);
//        
//        // Crear EstadoPedidoDTO
//        EstadoPedidoDTO estadoPendiente = new EstadoPedidoDTO();
//        estadoPendiente.setEstado_pedido_id(EstadoPedidoDTO.ID_PENDIENTE);
//        estadoPendiente.setNombre(EstadoPedidoDTO.NOMBRE_PENDIENTE);
//        pedido.setEstado_pedido(estadoPendiente);
//        
//        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
//        assertTrue(this.pedidoBaseId != 0);
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetalle_PedidoId);
//        eliminarTodo();
//    }
//
//
//    private void insertarDetallePedidos(ArrayList<Integer> listaDetalle_PedidoId) {
//        DetalleVentasDTO detallepedido = new DetalleVentasDTO();
//        detallepedido.setCantidad(1);
//        detallepedido.setPedido_id(this.pedidoBaseId);
//        detallepedido.setPrecio_unitario(109.98);
//        
//        ProductosVariantesDTO productoVar = new ProductosVariantesDTO();
//        productoVar.setProd_variante_id(this.prodVarBaseId);
//        detallepedido.setProductoVar(productoVar);
//        
//        detallepedido.setSubtotal(109.98);
//        
//        Integer resultado = this.detalle_pedidoDAO.insertar(detallepedido);
//        assertTrue(resultado != 0);
//        listaDetalle_PedidoId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetalle_PedidoId);
//        
//        DetalleVentasDTO detallepedido = this.detalle_pedidoDAO.obtenerPorId(listaDetalle_PedidoId.get(0));
//        assertEquals(detallepedido.getDetalle_pedido_id(), listaDetalle_PedidoId.get(0));
//
//        eliminarTodo();
//    }
//
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetalle_PedidoId);
//
//        ArrayList<DetalleVentasDTO> listaDetalle_Pedidos = this.detalle_pedidoDAO.listarTodos();
//        assertEquals(listaDetalle_PedidoId.size(), listaDetalle_Pedidos.size());
//        for (Integer i = 0; i < listaDetalle_PedidoId.size(); i++) {
//            assertEquals(listaDetalle_PedidoId.get(i), listaDetalle_Pedidos.get(i).getDetalle_pedido_id());
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetalle_PedidoId);
//
//        ArrayList<DetalleVentasDTO> listaDetalle_Pedidos = this.detalle_pedidoDAO.listarTodos();
//        assertEquals(listaDetalle_PedidoId.size(), listaDetalle_Pedidos.size());
//        for (Integer i = 0; i < listaDetalle_PedidoId.size(); i++) {
//            listaDetalle_Pedidos.get(i).setCantidad(listaDetalle_Pedidos.get(i).getCantidad() + 2);
//            listaDetalle_Pedidos.get(i).setSubtotal(329.94);
//            this.detalle_pedidoDAO.modificar(listaDetalle_Pedidos.get(i));
//        }
//
//        ArrayList<DetalleVentasDTO> listaDetalle_PedidoModificados = this.detalle_pedidoDAO.listarTodos();
//        assertEquals(listaDetalle_Pedidos.size(), listaDetalle_PedidoModificados.size());
//        for (Integer i = 0; i < listaDetalle_Pedidos.size(); i++) {
//            assertEquals(listaDetalle_Pedidos.get(i).getCantidad(), listaDetalle_PedidoModificados.get(i).getCantidad());
//            assertEquals(listaDetalle_Pedidos.get(i).getSubtotal(), listaDetalle_PedidoModificados.get(i).getSubtotal(), 0.0001); //margen de error
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
//        insertarDetallePedidos(listaDetalle_PedidoId);
//        eliminarTodo();
//    }
//
//
//    private void eliminarTodo() {
//
//        ArrayList<DetalleVentasDTO> listaDetalle_Pedido = this.detalle_pedidoDAO.listarTodos();
//        for (Integer i = 0; i < listaDetalle_Pedido.size(); i++) {
//            Integer resultado = this.detalle_pedidoDAO.eliminar(listaDetalle_Pedido.get(i));
//            assertNotEquals(0, resultado);
//            DetalleVentasDTO detalle_pedido = this.detalle_pedidoDAO.obtenerPorId(listaDetalle_Pedido.get(i).getDetalle_pedido_id());
//            assertNull(detalle_pedido);
//        }
//
//        ArrayList<VentasDTO> listarPedidos = this.pedidoDAO.listarTodos();
//        for (VentasDTO pedido : listarPedidos) {
//            this.pedidoDAO.eliminar(pedido);
//        }
//
//        ArrayList<UsuariosDTO> listarUsuarios = this.usuarioDAO.listarTodos();
//        for (UsuariosDTO usuario : listarUsuarios) {
//            this.usuarioDAO.eliminar(usuario);
//        }
//
//        ArrayList<DescuentosDTO> listarPromocion = this.promocionDAO.listarTodos();
//        for (DescuentosDTO promocion : listarPromocion) {
//            this.promocionDAO.eliminar(promocion);
//        }
//
//        ArrayList<ProductosVariantesDTO> listarProductoVar = this.prodVarDAO.listarTodos();
//        for (ProductosVariantesDTO productoVar : listarProductoVar) {
//            this.prodVarDAO.eliminar(productoVar);
//        }
//
//        ArrayList<ProductosDTO> listarProducto = this.productoDAO.listarTodos();
//        for (ProductosDTO producto : listarProducto) {
//            this.productoDAO.eliminar(producto);
//        }
//    }
//
//}
