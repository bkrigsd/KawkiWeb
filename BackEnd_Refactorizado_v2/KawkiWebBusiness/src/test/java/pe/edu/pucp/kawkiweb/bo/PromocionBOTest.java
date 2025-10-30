//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.kawkiweb.model.PromocionDTO;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicion;
//
//public class PromocionBOTest {
//    
//    private PromocionBO promoBO;
//    
//    public PromocionBOTest() {
//        this.promoBO = new PromocionBO();
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("test Insertar");
//        ArrayList<Integer> listaPromosId = new ArrayList<>();
//        insertarPromos(listaPromosId);
//        eliminarTodo();
//    }
//
//    private void insertarPromos(ArrayList<Integer> listaPromosId) {
//        
//        Integer resultado;
//        // Promo 1
//        resultado = this.promoBO.Insertar("20% OFF en Botas",
//                TipoCondicion.CANT_MIN_PRODUCTOS,2,
//                TipoBeneficio.DESCUENTO_PORCENTAJE,20,
//                LocalDateTime.now(),LocalDateTime.now().plusMonths(1),
//                true);
//        assertTrue(resultado != 0);
//        listaPromosId.add(resultado);
//        // Promo 2
//        resultado = this.promoBO.Insertar("S/.50 OFF Zapatos",
//                TipoCondicion.CANT_MIN_PRODUCTOS,1,
//                TipoBeneficio.DESCUENTO_FIJO,50,
//                LocalDateTime.now(),LocalDateTime.now().plusMonths(2),
//                true);
//        assertTrue(resultado != 0);
//        listaPromosId.add(resultado);
//        // Promo 3
//        resultado = this.promoBO.Insertar("Envío Gratis",
//                TipoCondicion.CANT_MIN_PRODUCTOS,3,
//                TipoBeneficio.ENVIO_GRATIS,0,
//                LocalDateTime.now(),LocalDateTime.now().plusMonths(2),
//                true);
//        assertTrue(resultado != 0);
//        listaPromosId.add(resultado);
//    }
//    
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtenerPorId");
//        ArrayList<Integer> listPromosId = new ArrayList<>();
//        insertarPromos(listPromosId);
//        PromocionDTO promoDTO = this.promoBO.obtenerPorId(listPromosId.get(0));
//        assertEquals(promoDTO.getPromocion_id(), listPromosId.get(0));
//        promoDTO = this.promoBO.obtenerPorId(listPromosId.get(1));
//        assertEquals(promoDTO.getPromocion_id(), listPromosId.get(1));
//        promoDTO = this.promoBO.obtenerPorId(listPromosId.get(2));
//        assertEquals(promoDTO.getPromocion_id(), listPromosId.get(2));
//        eliminarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listarTodos");
//        ArrayList<Integer> listPromosId = new ArrayList<>();
//        insertarPromos(listPromosId);
//        ArrayList<PromocionDTO> listPromos = this.promoBO.listarTodos();
//        assertEquals(listPromosId.size(),listPromos.size());
//        for(int i=0; i<listPromosId.size(); i++)
//            assertEquals(listPromosId.get(i), listPromos.get(i).getPromocion_id());
//        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("test modificar");
//        ArrayList<Integer> listPromosId = new ArrayList<>();
//        insertarPromos(listPromosId);
//        ArrayList<PromocionDTO> listPromos = this.promoBO.listarTodos();
//        assertEquals(listPromosId.size(),listPromos.size());
//        for(Integer i=0; i<listPromosId.size(); i++){
//            listPromos.get(i).setDescripcion("No disponible");
//            listPromos.get(i).setValor_beneficio(0);
//            listPromos.get(i).setValor_condicion(0);
//            listPromos.get(i).setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
//            listPromos.get(i).setFecha_inicio(LocalDateTime.now());
//            listPromos.get(i).setFecha_fin(LocalDateTime.now());
//            listPromos.get(i).setActivo(false);
//            this.promoBO.modificar(listPromos.get(i).getPromocion_id(),
//                    listPromos.get(i).getDescripcion(),
//                    listPromos.get(i).getTipo_condicion(), 
//                    listPromos.get(i).getValor_condicion(), 
//                    listPromos.get(i).getTipo_beneficio(), 
//                    listPromos.get(i).getValor_beneficio(), 
//                    listPromos.get(i).getFecha_inicio(), 
//                    listPromos.get(i).getFecha_fin(), 
//                    listPromos.get(i).getActivo());
//        }
//        ArrayList<PromocionDTO> listPromosModifi = this.promoBO.listarTodos();
//        assertEquals(listPromos.size(),listPromosModifi.size());
//        for(Integer i=0; i<listPromos.size(); i++){
//            assertEquals(listPromos.get(i).getDescripcion(),listPromosModifi.get(i).getDescripcion());
//            assertEquals(listPromos.get(i).getValor_condicion(),listPromosModifi.get(i).getValor_condicion());
//            assertEquals(listPromos.get(i).getValor_beneficio(),listPromosModifi.get(i).getValor_beneficio());
//            assertEquals(listPromos.get(i).getActivo(),listPromosModifi.get(i).getActivo());
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        ArrayList<Integer> listaPromoId = new ArrayList<>();
//        insertarPromos(listaPromoId);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<PromocionDTO> listPromo = this.promoBO.listarTodos();
//        for (PromocionDTO promo : listPromo)
//            this.promoBO.eliminar(promo.getPromocion_id());
//    }
//}
