//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.PromocionDAOImpl;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//
//public class PromocionDAOTest {
//    
//   private PromocionDAO promoDAO;
//    
//    public PromocionDAOTest() {
//        this.promoDAO = new PromocionDAOImpl();
//    }
//    
//    @BeforeEach
//    public void setUp() {
//        // Limpiar datos residuales antes de cada test
//        limpiarDatosResiduales();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Limpiar datos después de cada test
//        limpiarDatosResiduales();
//    }
//
//    private void limpiarDatosResiduales() {
//        ArrayList<PromocionDTO> listPromo = this.promoDAO.listarTodos();
//        for (PromocionDTO promo : listPromo) {
//            this.promoDAO.eliminar(promo);
//        }
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("test insertar");
//        ArrayList<Integer> listaPromos = new ArrayList<>();
//        this.insertarPromos(listaPromos);
//    }
//    
//    private void insertarPromos(ArrayList<Integer> listaPromos) {
//        PromocionDTO promo1 = new PromocionDTO();
//        promo1.setDescripcion("20% OFF en Botas");
//        promo1.setValor_beneficio(20);
//        promo1.setValor_condicion(2);
//        promo1.setTipo_condicion(TipoCondicion.CANT_MIN_PRODUCTOS);
//        promo1.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
//        promo1.setFecha_inicio(LocalDateTime.now());
//        promo1.setFecha_fin(LocalDateTime.now().plusMonths(1));
//        promo1.setActivo(true);
//        Integer result = this.promoDAO.insertar(promo1);
//        assertTrue(result > 0);
//        listaPromos.add(result);
//        
//        PromocionDTO promo2 = new PromocionDTO();
//        promo2.setDescripcion("S/.50 OFF Zapatos");
//        promo2.setValor_beneficio(50);
//        promo2.setValor_condicion(1);
//        promo2.setTipo_condicion(TipoCondicion.CANT_MIN_PRODUCTOS);
//        promo2.setTipo_beneficio(TipoBeneficio.DESCUENTO_FIJO);
//        promo2.setFecha_inicio(LocalDateTime.now());
//        promo2.setFecha_fin(LocalDateTime.now().plusMonths(2));
//        promo2.setActivo(true);
//        result = this.promoDAO.insertar(promo2);
//        assertTrue(result > 0);
//        listaPromos.add(result);
//        
//        PromocionDTO promo3 = new PromocionDTO();
//        promo3.setDescripcion("Envío Gratis");
//        promo3.setValor_beneficio(0);
//        promo3.setValor_condicion(3);
//        promo3.setTipo_condicion(TipoCondicion.CANT_MIN_PRODUCTOS);
//        promo3.setTipo_beneficio(TipoBeneficio.ENVIO_GRATIS);
//        promo3.setFecha_inicio(LocalDateTime.now());
//        promo3.setFecha_fin(LocalDateTime.now().plusMonths(5));
//        promo3.setActivo(true);
//        result = this.promoDAO.insertar(promo3);
//        assertTrue(result > 0);
//        listaPromos.add(result);
//    }
//    
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtener por id");
//        ArrayList<Integer> listPromosId = new ArrayList<>();
//        this.insertarPromos(listPromosId);
//        
//        PromocionDTO promoDTO = this.promoDAO.obtenerPorId(listPromosId.get(0));
//        assertEquals(promoDTO.getPromocion_id(), listPromosId.get(0));
//        promoDTO = this.promoDAO.obtenerPorId(listPromosId.get(1));
//        assertEquals(promoDTO.getPromocion_id(), listPromosId.get(1));
//        promoDTO = this.promoDAO.obtenerPorId(listPromosId.get(2));
//        assertEquals(promoDTO.getPromocion_id(), listPromosId.get(2));
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listar todos");
//        ArrayList<Integer> listPromosId = new ArrayList<>();
//        this.insertarPromos(listPromosId);
//        ArrayList<PromocionDTO> listPromos = this.promoDAO.listarTodos();
//        assertEquals(listPromosId.size(), listPromos.size());
//        for (int i = 0; i < listPromosId.size(); i++)
//            assertEquals(listPromosId.get(i), listPromos.get(i).getPromocion_id());
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("test modificar");
//        ArrayList<Integer> listPromosId = new ArrayList<>();
//        this.insertarPromos(listPromosId);
//        
//        ArrayList<PromocionDTO> listPromos = this.promoDAO.listarTodos();
//        assertEquals(listPromosId.size(), listPromos.size());
//        for (Integer i = 0; i < listPromosId.size(); i++) {
//            listPromos.get(i).setDescripcion("No disponible");
//            listPromos.get(i).setValor_beneficio(0);
//            listPromos.get(i).setValor_condicion(0);
//            listPromos.get(i).setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
//            listPromos.get(i).setFecha_inicio(LocalDateTime.now());
//            listPromos.get(i).setFecha_fin(LocalDateTime.now());
//            listPromos.get(i).setActivo(false);
//            this.promoDAO.modificar(listPromos.get(i));
//        }
//        ArrayList<PromocionDTO> listPromosModifi = this.promoDAO.listarTodos();
//        assertEquals(listPromos.size(), listPromosModifi.size());
//        for (Integer i = 0; i < listPromos.size(); i++) {
//            assertEquals(listPromos.get(i).getDescripcion(), listPromosModifi.get(i).getDescripcion());
//            assertEquals(listPromos.get(i).getValor_beneficio(), listPromosModifi.get(i).getValor_beneficio());
//            assertEquals(listPromos.get(i).getValor_condicion(), listPromosModifi.get(i).getValor_condicion());
//            assertEquals(listPromos.get(i).getActivo(), listPromosModifi.get(i).getActivo());
//        }
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        ArrayList<Integer> listaPromoId = new ArrayList<>();
//        insertarPromos(listaPromoId);
//        
//        ArrayList<PromocionDTO> listPromo = this.promoDAO.listarTodos();
//        for (PromocionDTO promo : listPromo) {
//            Integer result = this.promoDAO.eliminar(promo);
//            assertNotEquals(0, result);
//            PromocionDTO promoDTO = this.promoDAO.obtenerPorId(promo.getPromocion_id());
//            assertNull(promoDTO);
//        }
//    }
//}
