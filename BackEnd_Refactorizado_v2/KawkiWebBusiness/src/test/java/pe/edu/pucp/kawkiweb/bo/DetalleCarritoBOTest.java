//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
//import pe.edu.pucp.kawkiweb.model.DetalleCarritoDTO;
//import pe.edu.pucp.kawkiweb.model.ProductoDTO;
//import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Categoria;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Color;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Estilo;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Talla;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//public class DetalleCarritoBOTest {
//    
//    private DetalleCarritoBO detalleCarBO;
//    private CarritoComprasBO carritoBO;
//    private UsuarioBO usuarioBO;
//    private ProductoVarianteBO productVarBO;
//    private ProductoBO productoBO;
//    private PromocionBO promocionBO;
//    private Integer usuarioId;
//    private Integer promoId;
//    private Integer carId;
//    private Integer productoId;
//    private Integer productVarId;
//    
//    public DetalleCarritoBOTest() {
//        this.detalleCarBO = new DetalleCarritoBO();
//        this.carritoBO = new CarritoComprasBO();
//        this.usuarioBO = new UsuarioBO();
//        this.productVarBO = new ProductoVarianteBO();
//        this.productoBO = new ProductoBO();
//        this.promocionBO = new PromocionBO();
//    }
//
//    private void prepararDatos(){
//        usuarioId = crearUsuario();
//        promoId = crearPromocion();
//        carId = crearCarrito();
//        productoId = crearProducto();
//        productVarId = crearProductoVariante();
//    }
//
//    private Integer crearUsuario() {
//        Integer id = usuarioBO.Insertar(
//                "William", "Chávez", "Alcaraz",
//                "74289619", LocalDate.of(2003, 2, 25),
//                "964977977", "Av. Experiencia 0/10", "yomir@kawki.com",
//                "will", "JalePorDormir", LocalDateTime.now(),
//                TipoUsuario.CLIENTE);
//        assertTrue(id != 0);
//        return id;
//    }
//
//    private Integer crearPromocion(){
//        Integer id = promocionBO.Insertar(
//                "20% OFF en Botas",
//                TipoCondicion.CANT_MIN_PRODUCTOS,
//                2, TipoBeneficio.DESCUENTO_FIJO,
//                20, LocalDateTime.now(),
//                LocalDateTime.now().plusMonths(2),
//                true);
//        assertTrue(id != 0);
//        return id;
//    }
//    
//    private Integer crearCarrito() {
//        UsuarioDTO user = new UsuarioDTO();
//        user.setUsuarioId(usuarioId);
//        PromocionDTO promo = new PromocionDTO();
//        promo.setPromocion_id(promoId);
//        Integer id = carritoBO.Insertar(user, 0.0, promo);
//        assertTrue(id != 0);
//        return id;
//    }
//    
//    private Integer crearProducto(){
//        Integer id = productoBO.insertar(
//                "Botas de Cuero Premium",
//                Categoria.OXFORD, Estilo.COMBINADOS,
//                300.50, LocalDateTime.now());
//        assertTrue(id != 0);
//        return id;
//    }
//    
//    private Integer crearProductoVariante() {
//        Integer id = productVarBO.insertar(
//                "DER-BLA-37", 8, 10, true,
//                productoId, Color.BLANCO, Talla.TREINTA_SIETE,
//                TipoBeneficio.DESCUENTO_FIJO, 20,
//                LocalDateTime.now());
//        assertTrue(id != 0);
//        return id;
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("test Insertar");
//        prepararDatos();
//        ArrayList<Integer> detalleIds = new ArrayList<>();
//        insertaDetalles(detalleIds);
//        limpiarTodo();
//    }
//
//    private void insertaDetalles(ArrayList<Integer> listDetalleIds) {
//        // Datos
//        UsuarioDTO user = new UsuarioDTO();
//        user.setUsuarioId(usuarioId);
//        ProductoDTO product = productoBO.obtenerPorId(productoId);
//        ProductoVarianteDTO productVar = new ProductoVarianteDTO();
//        productVar.setProd_variante_id(productVarId);
//        CarritoComprasDTO car = new CarritoComprasDTO();
//        car.setCarrito_id(carId);
//        car.setUsuario(user);
//        // Detalle
//        Integer resultado;
//        resultado = this.detalleCarBO.Insertar(car, 6, 
//                product.getPrecio_venta(), 
//                6*product.getPrecio_venta(), productVar);
//        assertTrue(resultado != 0);
//        listDetalleIds.add(resultado);
//    }
//    
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtenerPorId");
//        prepararDatos();
//        ArrayList<Integer> listDetallesId = new ArrayList<>();
//        insertaDetalles(listDetallesId);
//        DetalleCarritoDTO detalleDTO = this.detalleCarBO.obtenerPorId(listDetallesId.get(0));
//        assertEquals(detalleDTO.getDetalle_carrito_id(),listDetallesId.get(0));
//        limpiarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listarTodos");
//        prepararDatos();
//        ArrayList<Integer> listDetallesId = new ArrayList<>();
//        insertaDetalles(listDetallesId);
//        ArrayList<DetalleCarritoDTO> listaDetalles = detalleCarBO.listarTodos();
//        for (int i = 0; i < listDetallesId.size(); i++)
//            assertEquals(listDetallesId.get(i), listaDetalles.get(i).getDetalle_carrito_id());
//        limpiarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("test modificar");
//        prepararDatos();
//        ArrayList<Integer> listDetallesId = new ArrayList<>();
//        insertaDetalles(listDetallesId);
//        ArrayList<DetalleCarritoDTO> listDetalles = detalleCarBO.listarTodos();
//        assertEquals(listDetallesId.size(), listDetalles.size());
//        modificarDetallesConEscenarios();
//        limpiarTodo();
//    }
//    
//    private void modificarDetallesConEscenarios() {
//        ArrayList<DetalleCarritoDTO> detalles = detalleCarBO.listarTodos();
//        for (int i = 0; i < detalles.size(); i++) {
//            DetalleCarritoDTO detalle = detalles.get(i);
//            // Caso 1: Aumentar cantidad
//            if (i == 0) {
//                int nuevaCantidad = detalle.getCantidad() + 2;
//                detalle.setCantidad(nuevaCantidad);
//                detalle.setSubtotal(nuevaCantidad * detalle.getPrecio_unitario());
//            }
//            // Caso 2: Aplicar descuento adicional
//            else if (i == 1) {
//                double nuevoPrecio = detalle.getPrecio_unitario() * 0.85; // 15% off
//                detalle.setPrecio_unitario(nuevoPrecio);
//                detalle.setSubtotal(detalle.getCantidad() * nuevoPrecio);
//            }
//            // Caso 3: Reducir cantidad
//            else if (i == 2) {
//                int nuevaCantidad = Math.max(1, detalle.getCantidad() - 1);
//                detalle.setCantidad(nuevaCantidad);
//                detalle.setSubtotal(nuevaCantidad * detalle.getPrecio_unitario());
//            }
//            
//            Integer resultado = detalleCarBO.modificar(
//                    detalle.getDetalle_carrito_id(),
//                    detalle.getCarrito(), detalle.getCantidad(), 
//                    detalle.getPrecio_unitario(), detalle.getSubtotal(),
//                    detalle.getProducto());
//            assertTrue(resultado > 0, "Modificación del detalle " + (i+1) + " exitosa");
//        }
//    }
//    
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        prepararDatos();
//        ArrayList<Integer> listaDetallesId = new ArrayList<>();
//        insertaDetalles(listaDetallesId);
//        limpiarTodo();
//    }
//    
//    private void limpiarTodo() {
//        limpiarDetalles();
//        limpiarCarrito();
//        limpiarVariantes();
//        limpiarProducto();
//        limpiarUsuario();
//        limpiarPromocion();
//    }
//
//    private void limpiarDetalles() {
//        ArrayList<DetalleCarritoDTO> detalles = detalleCarBO.listarTodos();
//        for(int i=0; i<detalles.size(); i++){
//            Integer result = this.detalleCarBO.eliminar(detalles.get(i).getDetalle_carrito_id());
//            assertNotEquals(0,result);
//            DetalleCarritoDTO detalle = this.detalleCarBO.obtenerPorId(detalles.get(i).getDetalle_carrito_id());
//            assertNull(detalle);
//        }
//    }
//
//    private void limpiarCarrito() {
//        ArrayList<CarritoComprasDTO> carritos = carritoBO.listarTodos();
//        for (int i=0; i<carritos.size(); i++){
//            Integer result = this.carritoBO.eliminar(carritos.get(i).getCarrito_id());
//            assertNotEquals(0,result);
//            CarritoComprasDTO carDTO = this.carritoBO.obtenerPorId(carritos.get(i).getCarrito_id());
//            assertNull(carDTO);
//        }
//    }
//    
//    private void limpiarVariantes() {
//        ArrayList<ProductoVarianteDTO> variantes = productVarBO.listarTodos();
//        for (ProductoVarianteDTO v : variantes) {
//            Integer r = productVarBO.eliminar(v.getProd_variante_id());
//            assertNotEquals(0, r, "No se pudo eliminar la variante con ID: " + v.getProd_variante_id());
//        }
//    }
//
//    private void limpiarProducto() {
//        ArrayList<ProductoDTO> productos = this.productoBO.listarTodos();
//        for (Integer i = 0; i < productos.size(); i++) {
//            Integer resultado = this.productoBO.eliminar(productos.get(i).getProducto_id());
//            assertNotEquals(0, resultado);
//            ProductoDTO producto = this.productoBO.obtenerPorId(productos.get(i).getProducto_id());
//            assertNull(producto);
//        }
//    }
//
//    private void limpiarUsuario() {
//        ArrayList<UsuarioDTO> users = usuarioBO.listarTodos();
//        for (int i=0; i<users.size(); i++){
//            Integer result = this.usuarioBO.eliminar(users.get(i).getUsuarioId());
//            assertNotEquals(0,result);
//            UsuarioDTO userDTO = this.usuarioBO.obtenerPorId(users.get(i).getUsuarioId());
//            assertNull(userDTO);
//        }
//    }
//    
//    private void limpiarPromocion() {
//        ArrayList<PromocionDTO> promos = promocionBO.listarTodos();
//        for (int i=0; i<promos.size(); i++){
//            Integer result = this.promocionBO.eliminar(promos.get(i).getPromocion_id());
//            assertNotEquals(0,result);
//            PromocionDTO promoDTO = this.promocionBO.obtenerPorId(promos.get(i).getPromocion_id());
//            assertNull(promoDTO);
//        }
//    }
//}
