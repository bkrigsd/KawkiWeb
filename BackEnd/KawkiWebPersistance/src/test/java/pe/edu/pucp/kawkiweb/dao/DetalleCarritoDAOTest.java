//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.CarritoComprasDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.DetalleCarritoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductoVarianteDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.PromocionDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
//import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Categoria;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Color;
//import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Estilo;
//import pe.edu.pucp.kawkiweb.model.ProductoDTO;
//import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Talla;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//
//public class DetalleCarritoDAOTest {
//    
//   private DetalleCarritoDAO detalleCarDAO;
//    private CarritoComprasDAO carritoDAO;
//    private UsuarioDAO userDAO;
//    private ProductoVarianteDAO productVarDAO;
//    private ProductoDAO productDAO;
//    private PromocionDAO promoDAO;
//    private Integer userId;
//    private Integer promoId;
//    private Integer carId;
//    private Integer productId;
//    private Integer productVarId;
//    
//    public DetalleCarritoDAOTest() {
//        this.detalleCarDAO = new DetalleCarritoDAOImpl();
//        this.carritoDAO = new CarritoComprasDAOImpl();
//        this.promoDAO = new PromocionDAOImpl();
//        this.userDAO = new UsuarioDAOImpl();
//        this.productVarDAO = new ProductoVarianteDAOImpl();
//        this.productDAO = new ProductoDAOImpl();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        System.out.println("Limpiando datos antes del test...");
//        limpiarBaseDatos();
//    }
//    
//    @AfterEach
//    public void tearDown() {
//        System.out.println("Limpiando datos después del test...");
//        limpiarBaseDatos();
//        resetearIds();
//    }
//    
//    private void resetearIds() {
//        userId = null;
//        promoId = null;
//        carId = null;
//        productId = null;
//        productVarId = null;
//    }
//
//    private void prepararDatos() {
//        crearUsuario();
//        crearPromocion();
//        crearCarrito();
//        crearProducto();
//        crearProductoVariante();
//    }
//
//    private void crearUsuario() {
//        UsuarioDTO user = new UsuarioDTO();
//        user.setNombre("William");
//        user.setApePaterno("Chávez");
//        user.setApeMaterno("Alcaraz");
//        user.setDni("74289619");
//        user.setFechaNacimiento(LocalDate.of(2003, 2, 25));
//        user.setTelefono("964977977");
//        user.setDireccion("Av. Experiencia 0/10");
//        user.setCorreo("yomir@kawki.com");
//        user.setNombreUsuario("will");
//        user.setContrasenha("JalePorDormir");
//        user.setFechaHoraCreacion(LocalDateTime.now());
//        user.setTipoUsuario(TipoUsuario.CLIENTE);
//        this.userId = this.userDAO.insertar(user);
//    }
//
//    private void crearPromocion() {
//        PromocionDTO promo = new PromocionDTO();
//        promo.setDescripcion("20% OFF en Botas");
//        promo.setValor_beneficio(20);
//        promo.setValor_condicion(2);
//        promo.setTipo_condicion(TipoCondicion.CANT_MIN_PRODUCTOS);
//        promo.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
//        promo.setFecha_inicio(LocalDateTime.now());
//        promo.setFecha_fin(LocalDateTime.now().plusMonths(2));
//        promo.setActivo(true);
//        this.promoId = this.promoDAO.insertar(promo);
//    }
//    
//    private void crearCarrito() {
//        CarritoComprasDTO carrito = new CarritoComprasDTO();
//        UsuarioDTO user = new UsuarioDTO();
//        user.setUsuarioId(userId);
//        PromocionDTO promo = new PromocionDTO();
//        promo.setPromocion_id(promoId);
//        carrito.setUsuario(user);
//        carrito.setTotal(0.0);
//        carrito.setPromocion(promo);
//        this.carId = this.carritoDAO.insertar(carrito);
//    }
//    
//    private void crearProducto() {
//        ProductoDTO product = new ProductoDTO();
//        product.setDescripcion("Botas de Cuero Premium");
//        product.setCategoria(Categoria.OXFORD);
//        product.setEstilo(Estilo.COMBINADOS);
//        product.setPrecio_venta(300.50);
//        product.setFecha_hora_creacion(LocalDateTime.now());
//        this.productId = this.productDAO.insertar(product);
//    }
//    
//    private void crearProductoVariante() {
//        ProductoVarianteDTO productVar = new ProductoVarianteDTO();
//        productVar.setProducto_id(productId);
//        productVar.setTalla(Talla.TREINTA_CINCO);
//        productVar.setColor(Color.AZUL);
//        productVar.setStock(15);
//        productVar.setStock_minimo(3);
//        productVar.setSKU("DER-AZU-35");
//        productVar.setAlerta_stock(false);
//        productVar.setTipo_beneficio(TipoBeneficio.ENVIO_GRATIS);
//        productVar.setValor_beneficio(0);
//        productVar.setFecha_hora_creacion(LocalDateTime.now());
//        this.productVarId = this.productVarDAO.insertar(productVar);
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("test inserta detalle carrito");
//        prepararDatos();
//        ArrayList<Integer> detalleIds = new ArrayList<>();
//        insertaDetalles(detalleIds);
//        
//        assertEquals(1, detalleIds.size());
//        assertTrue(detalleIds.get(0) > 0);
//    }
//
//    private void insertaDetalles(ArrayList<Integer> listDetalleIds) {
//        UsuarioDTO user = new UsuarioDTO();
//        user.setUsuarioId(userId);
//        ProductoDTO product = productDAO.obtenerPorId(productId);
//        ProductoVarianteDTO productVar = new ProductoVarianteDTO();
//        productVar.setProd_variante_id(productVarId);
//        CarritoComprasDTO car = new CarritoComprasDTO();
//        car.setCarrito_id(carId);
//        car.setUsuario(user);
//        
//        DetalleCarritoDTO detalle = new DetalleCarritoDTO();
//        detalle.setCarrito(car);
//        detalle.setCantidad(6);
//        detalle.setPrecio_unitario(product.getPrecio_venta());
//        detalle.setSubtotal(6 * product.getPrecio_venta());
//        detalle.setProducto(productVar);
//        Integer result = this.detalleCarDAO.insertar(detalle);
//        assertTrue(result != 0);
//        listDetalleIds.add(result);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtener por id");
//        prepararDatos();
//        ArrayList<Integer> listDetallesId = new ArrayList<>();
//        insertaDetalles(listDetallesId);
//        
//        DetalleCarritoDTO detalleDTO = detalleCarDAO.obtenerPorId(listDetallesId.get(0));
//        assertEquals(detalleDTO.getDetalle_carrito_id(), listDetallesId.get(0));
//    }
//    
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listar todos");
//        prepararDatos();
//        ArrayList<Integer> listDetallesId = new ArrayList<>();
//        insertaDetalles(listDetallesId);
//        
//        ArrayList<DetalleCarritoDTO> listaDetalles = detalleCarDAO.listarTodos();
//        for (int i = 0; i < listDetallesId.size(); i++) {
//            assertEquals(listDetallesId.get(i), listaDetalles.get(i).getDetalle_carrito_id());
//        }
//    }
//    
//    @Test
//    public void testModificar() {
//        System.out.println("test modificar detalles");
//        prepararDatos();
//        ArrayList<Integer> listDetallesId = new ArrayList<>();
//        insertaDetalles(listDetallesId);
//        
//        ArrayList<DetalleCarritoDTO> listDetalles = detalleCarDAO.listarTodos();
//        assertEquals(listDetallesId.size(), listDetalles.size());
//        modificarDetallesConEscenarios();
//    }
//    
//    private void modificarDetallesConEscenarios() {
//        ArrayList<DetalleCarritoDTO> detalles = detalleCarDAO.listarTodos();
//        for (int i = 0; i < detalles.size(); i++) {
//            DetalleCarritoDTO detalle = detalles.get(i);
//            if (i == 0) {
//                int nuevaCantidad = detalle.getCantidad() + 2;
//                detalle.setCantidad(nuevaCantidad);
//                detalle.setSubtotal(nuevaCantidad * detalle.getPrecio_unitario());
//            }
//            Integer resultado = detalleCarDAO.modificar(detalle);
//            assertTrue(resultado > 0);
//        }
//    }
//    
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        prepararDatos();
//        ArrayList<Integer> listaDetallesId = new ArrayList<>();
//        insertaDetalles(listaDetallesId);
//        
//        assertEquals(1, detalleCarDAO.listarTodos().size());
//        
//        for (Integer id : listaDetallesId) {
//            DetalleCarritoDTO detalle = detalleCarDAO.obtenerPorId(id);
//            assertNotNull(detalle);
//            Integer resultado = detalleCarDAO.eliminar(detalle);
//            assertTrue(resultado > 0);
//            
//            DetalleCarritoDTO detalleEliminado = detalleCarDAO.obtenerPorId(id);
//            assertNull(detalleEliminado);
//        }
//        
//        assertEquals(0, detalleCarDAO.listarTodos().size());
//    }
//    
//    private void limpiarBaseDatos() {
//        limpiarDetalles();
//        limpiarCarrito();
//        limpiarProductoVariante();
//        limpiarProducto();
//        limpiarPromocion();
//        limpiarUsuario();
//    }
//
//    private void limpiarDetalles() {
//        ArrayList<DetalleCarritoDTO> detalles = detalleCarDAO.listarTodos();
//        for (DetalleCarritoDTO detalle : detalles) {
//            detalleCarDAO.eliminar(detalle);
//        }
//    }
//
//    private void limpiarCarrito() {
//        ArrayList<CarritoComprasDTO> carritos = carritoDAO.listarTodos();
//        for (CarritoComprasDTO carrito : carritos) {
//            carritoDAO.eliminar(carrito);
//        }
//    }
//
//    private void limpiarProductoVariante() {
//        ArrayList<ProductoVarianteDTO> variantes = productVarDAO.listarTodos();
//        for (ProductoVarianteDTO variante : variantes) {
//            productVarDAO.eliminar(variante);
//        }
//    }
//
//    private void limpiarProducto() {
//        ArrayList<ProductoDTO> productos = productDAO.listarTodos();
//        for (ProductoDTO producto : productos) {
//            productDAO.eliminar(producto);
//        }
//    }
//
//    private void limpiarUsuario() {
//        ArrayList<UsuarioDTO> usuarios = userDAO.listarTodos();
//        for (UsuarioDTO usuario : usuarios) {
//            userDAO.eliminar(usuario);
//        }
//    }
//    
//    private void limpiarPromocion() {
//        ArrayList<PromocionDTO> promociones = promoDAO.listarTodos();
//        for (PromocionDTO promocion : promociones) {
//            promoDAO.eliminar(promocion);
//        }
//    }
//}
