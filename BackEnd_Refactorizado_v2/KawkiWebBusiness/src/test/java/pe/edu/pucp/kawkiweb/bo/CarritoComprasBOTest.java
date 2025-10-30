//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//
//public class CarritoComprasBOTest {
//    
//    private CarritoComprasBO carritoBO;
//    private UsuarioBO usuarioBO;
//    private PromocionBO promoBO;
//    private List<Integer> usuariosBase;
//    private List<Integer> promocionesBase;
//    
//    public CarritoComprasBOTest() {
//        this.carritoBO = new CarritoComprasBO();
//        this.usuarioBO = new UsuarioBO();
//        this.promoBO = new PromocionBO();
//        this.usuariosBase = new ArrayList<>();
//        this.promocionesBase = new ArrayList<>();
//    }
//    
//    private void prepararDatosBase() {
//        crearUsuariosBase();
//        crearPromocionesBase();
//    }
//    
//    private void crearUsuariosBase() {
//        
//        String[][] datosUsuarios = {
//            {"Abigail", "Vega", "Arias", "12345678", "2004-06-17", "987654321",
//                "Av. Dormir es de debiles", "tetengodominado@kawki.com", "abi", "pass123"},
//            {"Miguel", "Peñaloza", "Soria", "87654321", "2004-04-03", "912345678",
//                "Calle Sal Si Puedes", "quierenvermejalar@kawki.com", "mickel", "pass456"},
//            {"Jhair", "Godoy", "Perez", "11223344", "2004-12-14", "934567890",
//                "Calle Lobo Domesticado S/N", "themiddlejungle@kawki.com", "relius", "pass789"}
//        };
//        
//        for (String[] datos : datosUsuarios) {
//            Integer resultado;
//            resultado = this.usuarioBO.Insertar(datos[0], datos[1], datos[2],
//                        datos[3], LocalDate.parse(datos[4]),
//                        datos[5], datos[6], datos[7], datos[8],datos[9], 
//                        LocalDateTime.now(), TipoUsuario.CLIENTE);
//            assertTrue(resultado != 0);
//            usuariosBase.add(resultado);
//        }
//    }
//    
//    private void crearPromocionesBase() {
//        Object[][] promociones = {{"20% OFF en Botas", 2, 20},
//                                  {"Descuento Primavera", 1, 15},
//                                  {"Black Friday Zapatos", 3, 30}};
//        for (Object[] promo : promociones) {
//            Integer resultado;
//            resultado = this.promoBO.Insertar((String) promo[0], TipoCondicion.CANT_MIN_PRODUCTOS, 
//                    (Integer) promo[1], TipoBeneficio.DESCUENTO_FIJO, 
//                    (Integer) promo[2], LocalDateTime.now(), 
//                    LocalDateTime.now().plusMonths(1), 
//                    Boolean.TRUE);
//            assertTrue(resultado != 0);
//            promocionesBase.add(resultado);
//        }
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("Insertar");
//        prepararDatosBase();
//        ArrayList<Integer> carritosIds = new ArrayList<>();
//        insertaCarritos(carritosIds);
//        limpiarTodo();
//    }
//    
//    private void insertaCarritos(ArrayList<Integer> listCarritos) {
//        Integer resultado;
//        // Carrito 1: Con promoción
//        UsuarioDTO usuario1 = new UsuarioDTO();
//        usuario1.setUsuarioId(usuariosBase.get(0));
//        PromocionDTO promo1 = new PromocionDTO();
//        promo1.setPromocion_id(promocionesBase.get(0));
//        resultado = this.carritoBO.Insertar(usuario1, 299.99, promo1);
//        assertTrue(resultado != 0);
//        listCarritos.add(resultado);
//        // Carrito 2: Sin promoción
//        UsuarioDTO usuario2 = new UsuarioDTO();
//        usuario2.setUsuarioId(usuariosBase.get(1));
//        resultado = this.carritoBO.Insertar(usuario2, 150.20, null);
//        assertTrue(resultado != 0);
//        listCarritos.add(resultado);
//        // Carrito 3: Carrito vacío
//        UsuarioDTO usuario3 = new UsuarioDTO();
//        usuario3.setUsuarioId(usuariosBase.get(2));
//        resultado = this.carritoBO.Insertar(usuario3, 0.0, null);
//        assertTrue(resultado != 0);
//        listCarritos.add(resultado);
//    }
//    
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtenerPorId");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        this.insertaCarritos(listaCarritoId);
//        CarritoComprasDTO carritoDTO = this.carritoBO.obtenerPorId(listaCarritoId.get(0));
//        assertEquals(carritoDTO.getCarrito_id(), listaCarritoId.get(0));
//        carritoDTO = this.carritoBO.obtenerPorId(listaCarritoId.get(1));
//        assertEquals(carritoDTO.getCarrito_id(), listaCarritoId.get(1));
//        carritoDTO = this.carritoBO.obtenerPorId(listaCarritoId.get(2));
//        assertEquals(carritoDTO.getCarrito_id(), listaCarritoId.get(2));
//        this.limpiarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listarTodos");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        this.insertaCarritos(listaCarritoId);
//        ArrayList<CarritoComprasDTO> listCarritos = this.carritoBO.listarTodos();
//        assertEquals(listaCarritoId.size(), listCarritos.size());
//        for (int i=0; i<listaCarritoId.size(); i++)
//            assertEquals(listaCarritoId.get(i), listCarritos.get(i).getCarrito_id());
//        limpiarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        this.insertaCarritos(listaCarritoId);
//        ArrayList<CarritoComprasDTO> listCarritos = this.carritoBO.listarTodos();
//        assertEquals(listaCarritoId.size(), listCarritos.size());
//        modificarCarritosConEscenarios(listaCarritoId);
//        this.limpiarTodo();
//    }
//
//    private void modificarCarritosConEscenarios(ArrayList<Integer> carritosIds) {
//        ArrayList<CarritoComprasDTO> carritos = carritoBO.listarTodos();
//        for (int i = 0; i < carritos.size(); i++) {
//            CarritoComprasDTO carrito = carritos.get(i);
//            // Caso 1: Aumentar total
//            if (i == 0) {
//                double nuevoTotal = carrito.getTotal() + 50.00;
//                carrito.setTotal(nuevoTotal);
//            }
//            // Caso 2: Cambiar promoción
//            else if (i == 1 && promocionesBase.size() > 2) {
//                PromocionDTO nuevaPromo = new PromocionDTO();
//                nuevaPromo.setPromocion_id(promocionesBase.get(2));
//                carrito.setPromocion(nuevaPromo);
//            }
//            // Caso 3: Remover promoción
//            else if (i == 2)
//                carrito.setPromocion(null);
//            
//            Integer resultado = carritoBO.modificar(
//                carrito.getCarrito_id(),
//                carrito.getUsuario(),
//                carrito.getTotal(),
//                carrito.getPromocion()
//            );
//            assertTrue(resultado > 0, "Modificación del carrito " + (i + 1) + " debe ser exitosa");
//        }
//    }
//    
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        insertaCarritos(listaCarritoId);
//        limpiarTodo();
//    }
//
//    private void limpiarTodo() {
//        limpiarCarritos();
//        limpiarPromociones();
//        limpiarUsuarios();
//        System.out.println("Limpieza completada");
//    }
//    
//    private void limpiarCarritos() {
//        ArrayList<CarritoComprasDTO> carritos = carritoBO.listarTodos();
//        for (int i=0; i<carritos.size(); i++){
//            Integer result = this.carritoBO.eliminar(carritos.get(i).getCarrito_id());
//            assertNotEquals(0,result);
//            CarritoComprasDTO carDTO = this.carritoBO.obtenerPorId(carritos.get(i).getCarrito_id());
//            assertNull(carDTO);
//        }
//    }
//    
//    private void limpiarPromociones() {
//        ArrayList<PromocionDTO> promos = promoBO.listarTodos();
//        for (int i=0; i<promos.size(); i++){
//            Integer result = this.promoBO.eliminar(promos.get(i).getPromocion_id());
//            assertNotEquals(0,result);
//            PromocionDTO promoDTO = this.promoBO.obtenerPorId(promos.get(i).getPromocion_id());
//            assertNull(promoDTO);
//        }
//    }
//    
//    private void limpiarUsuarios() {
//        ArrayList<UsuarioDTO> users = usuarioBO.listarTodos();
//        for (int i=0; i<users.size(); i++){
//            Integer result = this.usuarioBO.eliminar(users.get(i).getUsuarioId());
//            assertNotEquals(0,result);
//            UsuarioDTO userDTO = this.usuarioBO.obtenerPorId(users.get(i).getUsuarioId());
//            assertNull(userDTO);
//        }
//    }
//}