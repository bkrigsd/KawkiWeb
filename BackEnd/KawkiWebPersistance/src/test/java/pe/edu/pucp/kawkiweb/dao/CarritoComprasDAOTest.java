//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.CarritoComprasDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.PromocionDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
//import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//
//public class CarritoComprasDAOTest {
//    
//    private CarritoComprasDAO carritoDAO;
//    private UsuarioDAO usuarioDAO;
//    private PromocionDAO promocionDAO;
//    private List<Integer> usuariosBase;
//    private List<Integer> promocionesBase;
//    
//    public CarritoComprasDAOTest() {
//        this.carritoDAO = new CarritoComprasDAOImpl();
//        this.usuarioDAO = new UsuarioDAOImpl();
//        this.promocionDAO = new PromocionDAOImpl();
//        this.usuariosBase = new ArrayList<>();
//        this.promocionesBase = new ArrayList<>();
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
//    }
//    
//    private void prepararDatosBase() {
//        crearUsuariosBase();
//        crearPromocionesBase();
//    }
//    
//    private void crearUsuariosBase() {
//        String[][] datosUsuarios = {
//            {"Abigail", "Vega", "Arias", "12345678", "tetengodominado@kawki.com", "abi"},
//            {"Miguel", "Vargas", "Soria", "87654321", "quierenvermejalar@kawki.com", "mickel"},
//            {"Jhair", "Godoy", "De la Cruz", "11223344", "themiddlejungle@kawki.com", "relius"}
//        };
//        for (String[] datos : datosUsuarios) {
//            UsuarioDTO usuario = construirUsuario(datos[0], datos[1], datos[2], 
//                                                  datos[3], datos[4], datos[5]);
//            Integer id = usuarioDAO.insertar(usuario);
//            if (id != null && id > 0) {
//                usuariosBase.add(id);
//            }
//        }
//    }
//    
//    private UsuarioDTO construirUsuario(String nombre, String apePaterno,
//                                        String apeMaterno, String dni,
//                                        String correo, String username) {
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setNombre(nombre);
//        usuario.setApePaterno(apePaterno);
//        usuario.setApeMaterno(apeMaterno);
//        usuario.setDni(dni);
//        usuario.setFechaNacimiento(LocalDate.of(2004, 6, 17));
//        usuario.setTelefono("999888777");
//        usuario.setDireccion("Calle Sal Si Puedes");
//        usuario.setCorreo(correo);
//        usuario.setNombreUsuario(username);
//        usuario.setContrasenha("MuertoEnLaExpo");
//        usuario.setFechaHoraCreacion(LocalDateTime.now());
//        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
//        return usuario;
//    }
//    
//    private void crearPromocionesBase() {
//        Object[][] promociones = {
//            {"20% OFF en Botas", 20, 2},
//            {"Descuento Primavera", 15, 1},
//            {"Black Friday Zapatos", 30, 3}
//        };
//        for (Object[] promo : promociones) {
//            PromocionDTO promocion = construirPromocion((String) promo[0],
//                                    (Integer) promo[1], (Integer) promo[2]);
//            Integer result = promocionDAO.insertar(promocion);
//            if (result != null && result > 0) {
//                promocionesBase.add(result);
//            }
//        }
//    }
//    
//    private PromocionDTO construirPromocion(String descrip, Integer beneficio,
//                                            Integer condicion) {
//        PromocionDTO promo = new PromocionDTO();
//        promo.setDescripcion(descrip);
//        promo.setValor_beneficio(beneficio);
//        promo.setValor_condicion(condicion);
//        promo.setTipo_condicion(TipoCondicion.MONTO_MIN_COMPRA);
//        promo.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
//        promo.setFecha_inicio(LocalDateTime.now());
//        promo.setFecha_fin(LocalDateTime.now().plusMonths(2));
//        promo.setActivo(true);
//        return promo;
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("test inserta carritos");
//        prepararDatosBase();
//        ArrayList<Integer> carritosIds = new ArrayList<>();
//        insertaCarritos(carritosIds);
//        
//        assertEquals(3, carritosIds.size());
//        for (Integer id : carritosIds) {
//            assertTrue(id > 0);
//        }
//    }
//    
//    private void insertaCarritos(ArrayList<Integer> listCarritos) {
//        CarritoComprasDTO carrito1 = construirCarrito(usuariosBase.get(0), 299.99,
//                                                      promocionesBase.get(0));
//        listCarritos.add(carritoDAO.insertar(carrito1));
//        
//        CarritoComprasDTO carrito2 = construirCarrito(usuariosBase.get(1), 150.50, null);
//        listCarritos.add(carritoDAO.insertar(carrito2));
//        
//        CarritoComprasDTO carrito3 = construirCarrito(usuariosBase.get(2), 0.0, null);
//        listCarritos.add(carritoDAO.insertar(carrito3));
//    }
//    
//    private CarritoComprasDTO construirCarrito(Integer usuarioId, Double total,
//                                               Integer promocionId) {
//        CarritoComprasDTO carrito = new CarritoComprasDTO();
//        UsuarioDTO usuario = new UsuarioDTO();
//        usuario.setUsuarioId(usuarioId);
//        carrito.setUsuario(usuario);
//        carrito.setTotal(total);
//        if (promocionId != null) {
//            PromocionDTO promocion = new PromocionDTO();
//            promocion.setPromocion_id(promocionId);
//            carrito.setPromocion(promocion);
//        }
//        return carrito;
//    }
//    
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtener por id");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        insertaCarritos(listaCarritoId);
//        
//        CarritoComprasDTO carritoDTO = carritoDAO.obtenerPorId(listaCarritoId.get(0));
//        assertEquals(carritoDTO.getCarrito_id(), listaCarritoId.get(0));
//        
//        carritoDTO = carritoDAO.obtenerPorId(listaCarritoId.get(1));
//        assertEquals(carritoDTO.getCarrito_id(), listaCarritoId.get(1));
//        
//        carritoDTO = carritoDAO.obtenerPorId(listaCarritoId.get(2));
//        assertEquals(carritoDTO.getCarrito_id(), listaCarritoId.get(2));
//    }
//    
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listar todos");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        insertaCarritos(listaCarritoId);
//        
//        ArrayList<CarritoComprasDTO> listCarritos = carritoDAO.listarTodos();
//        assertEquals(listaCarritoId.size(), listCarritos.size());
//        for (int i = 0; i < listaCarritoId.size(); i++) {
//            assertEquals(listaCarritoId.get(i), listCarritos.get(i).getCarrito_id());
//        }
//    }
//    
//    @Test
//    public void testModificar() {
//        System.out.println("test modificar carritos");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        insertaCarritos(listaCarritoId);
//        
//        ArrayList<CarritoComprasDTO> listCarritos = carritoDAO.listarTodos();
//        assertEquals(listaCarritoId.size(), listCarritos.size());
//        
//        modificarCarritosConEscenarios(listaCarritoId);
//    }
//    
//    private void modificarCarritosConEscenarios(ArrayList<Integer> carritosIds) {
//        ArrayList<CarritoComprasDTO> carritos = carritoDAO.listarTodos();
//        for (int i = 0; i < carritos.size(); i++) {
//            CarritoComprasDTO carrito = carritos.get(i);
//            if (i == 0) {
//                double nuevoTotal = carrito.getTotal() + 50.00;
//                carrito.setTotal(nuevoTotal);
//            } else if (i == 1 && promocionesBase.size() > 2) {
//                PromocionDTO nuevaPromo = new PromocionDTO();
//                nuevaPromo.setPromocion_id(promocionesBase.get(2));
//                carrito.setPromocion(nuevaPromo);
//            } else if (i == 2) {
//                carrito.setPromocion(null);
//            }
//            Integer resultado = carritoDAO.modificar(carrito);
//            assertTrue(resultado > 0);
//        }
//    }
//    
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        prepararDatosBase();
//        ArrayList<Integer> listaCarritoId = new ArrayList<>();
//        insertaCarritos(listaCarritoId);
//        
//        assertEquals(3, carritoDAO.listarTodos().size());
//        
//        for (Integer id : listaCarritoId) {
//            CarritoComprasDTO carrito = carritoDAO.obtenerPorId(id);
//            assertNotNull(carrito);
//            Integer resultado = carritoDAO.eliminar(carrito);
//            assertTrue(resultado > 0);
//            
//            CarritoComprasDTO carritoEliminado = carritoDAO.obtenerPorId(id);
//            assertNull(carritoEliminado);
//        }
//        
//        assertEquals(0, carritoDAO.listarTodos().size());
//    }
//    
//    private void limpiarBaseDatos() {
//        limpiarCarritos();
//        limpiarPromociones();
//        limpiarUsuarios();
//    }
//    
//    private void limpiarCarritos() {
//        ArrayList<CarritoComprasDTO> carritos = carritoDAO.listarTodos();
//        for (CarritoComprasDTO carrito : carritos) {
//            carritoDAO.eliminar(carrito);
//        }
//    }
//    
//    private void limpiarPromociones() {
//        ArrayList<PromocionDTO> promociones = promocionDAO.listarTodos();
//        for (PromocionDTO promocion : promociones) {
//            promocionDAO.eliminar(promocion);
//        }
//        promocionesBase.clear();
//    }
//    
//    private void limpiarUsuarios() {
//        ArrayList<UsuarioDTO> usuarios = usuarioDAO.listarTodos();
//        for (UsuarioDTO usuario : usuarios) {
//            usuarioDAO.eliminar(usuario);
//        }
//        usuariosBase.clear();
//    }
//}