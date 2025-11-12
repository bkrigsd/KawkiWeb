//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.DescuentosDAOImpl;
//import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
//import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
//import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
//
//public class DescuentosDAOTest {
//
//    private DescuentosDAO descuentoDAO;
//
//    public DescuentosDAOTest() {
//        this.descuentoDAO = new DescuentosDAOImpl();
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
//        ArrayList<DescuentosDTO> listaDescuentos = this.descuentoDAO.listarTodos();
//        for (Integer i = 0; i < listaDescuentos.size(); i++) {
//            Integer resultado = this.descuentoDAO.eliminar(listaDescuentos.get(i));
//            assertNotEquals(0, resultado);
//            DescuentosDTO descuento = this.descuentoDAO.obtenerPorId(listaDescuentos.get(i).getDescuento_id());
//            assertNull(descuento);
//        }
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("test insertar");
//        ArrayList<Integer> listaDescuentoId = new ArrayList<>();
//        this.insertarDescuentos(listaDescuentoId);
//    }
//
//    private void insertarDescuentos(ArrayList<Integer> listaDescuentoId) {
//        DescuentosDTO descuento;
//
//        descuento = new DescuentosDTO();
//        descuento.setDescripcion("20% OFF en Botas");
//        descuento.setValor_beneficio(20);
//        descuento.setValor_condicion(2);
//        descuento.setTipo_condicion(new TiposCondicionDTO(TiposCondicionDTO.ID_CANT_MIN_PRODUCTOS,
//                TiposCondicionDTO.NOMBRE_CANT_MIN_PRODUCTOS));
//        descuento.setTipo_beneficio(new TiposBeneficioDTO(TiposBeneficioDTO.ID_DESCUENTO_FIJO,
//                TiposBeneficioDTO.NOMBRE_DESCUENTO_FIJO));
//        descuento.setFecha_inicio(LocalDateTime.now());
//        descuento.setFecha_fin(LocalDateTime.now().plusMonths(1));
//        descuento.setActivo(true);
//        Integer result = this.descuentoDAO.insertar(descuento);
//        assertTrue(result != 0);
//        listaDescuentoId.add(result);
//
//        descuento = new DescuentosDTO();
//        descuento.setDescripcion("S/.50 OFF Zapatos");
//        descuento.setValor_beneficio(50);
//        descuento.setValor_condicion(1);
//        descuento.setTipo_condicion(new TiposCondicionDTO(TiposCondicionDTO.ID_MONTO_MIN_COMPRA,
//                TiposCondicionDTO.NOMBRE_MONTO_MIN_COMPRA));
//        descuento.setTipo_beneficio(new TiposBeneficioDTO(TiposBeneficioDTO.ID_ENVIO_GRATIS,
//                TiposBeneficioDTO.NOMBRE_ENVIO_GRATIS));
//        descuento.setFecha_inicio(LocalDateTime.now());
//        descuento.setFecha_fin(LocalDateTime.now().plusMonths(2));
//        descuento.setActivo(true);
//        result = this.descuentoDAO.insertar(descuento);
//        assertTrue(result != 0);
//        listaDescuentoId.add(result);
//
//        descuento = new DescuentosDTO();
//        descuento.setDescripcion("Envío Gratis");
//        descuento.setValor_beneficio(0);
//        descuento.setValor_condicion(3);
//        descuento.setTipo_condicion(new TiposCondicionDTO(TiposCondicionDTO.ID_MONTO_MIN_COMPRA,
//                TiposCondicionDTO.NOMBRE_MONTO_MIN_COMPRA));
//        descuento.setTipo_beneficio(new TiposBeneficioDTO(TiposBeneficioDTO.ID_DESCUENTO_PORCENTAJE,
//                TiposBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE));
//        descuento.setFecha_inicio(LocalDateTime.now());
//        descuento.setFecha_fin(LocalDateTime.now().plusMonths(5));
//        descuento.setActivo(true);
//        result = this.descuentoDAO.insertar(descuento);
//        assertTrue(result != 0);
//        listaDescuentoId.add(result);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("test obtener por id");
//        ArrayList<Integer> listaDescuentoId = new ArrayList<>();
//        this.insertarDescuentos(listaDescuentoId);
//
//        DescuentosDTO descuento = this.descuentoDAO.obtenerPorId(listaDescuentoId.get(0));
//        assertEquals(descuento.getDescuento_id(), listaDescuentoId.get(0));
//
//        descuento = this.descuentoDAO.obtenerPorId(listaDescuentoId.get(1));
//        assertEquals(descuento.getDescuento_id(), listaDescuentoId.get(1));
//
//        descuento = this.descuentoDAO.obtenerPorId(listaDescuentoId.get(2));
//        assertEquals(descuento.getDescuento_id(), listaDescuentoId.get(2));
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("test listar todos");
//        ArrayList<Integer> listaDescuentoId = new ArrayList<>();
//        this.insertarDescuentos(listaDescuentoId);
//
//        ArrayList<DescuentosDTO> listaDescuentos = this.descuentoDAO.listarTodos();
//        assertEquals(listaDescuentoId.size(), listaDescuentos.size());
//        for (Integer i = 0; i < listaDescuentoId.size(); i++) {
//            assertEquals(listaDescuentoId.get(i), listaDescuentos.get(i).getDescuento_id());
//        }
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("test modificar");
//        ArrayList<Integer> listaDescuentoId = new ArrayList<>();
//        this.insertarDescuentos(listaDescuentoId);
//
//        ArrayList<DescuentosDTO> listaDescuentos = this.descuentoDAO.listarTodos();
//        assertEquals(listaDescuentoId.size(), listaDescuentos.size());
//        for (Integer i = 0; i < listaDescuentoId.size(); i++) {
//            listaDescuentos.get(i).setDescripcion("No disponible");
//            listaDescuentos.get(i).setValor_beneficio(0);
//            listaDescuentos.get(i).setValor_condicion(0);
//            listaDescuentos.get(i).setTipo_beneficio(new TiposBeneficioDTO(TiposBeneficioDTO.ID_DESCUENTO_PORCENTAJE,
//                    TiposBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE));
//            listaDescuentos.get(i).setFecha_inicio(LocalDateTime.now());
//            listaDescuentos.get(i).setFecha_fin(LocalDateTime.now().plusMonths(7));
//            listaDescuentos.get(i).setActivo(false);
//            this.descuentoDAO.modificar(listaDescuentos.get(i));
//        }
//
//        ArrayList<DescuentosDTO> listaDescModificados = this.descuentoDAO.listarTodos();
//        assertEquals(listaDescuentos.size(), listaDescModificados.size());
//        for (Integer i = 0; i < listaDescuentos.size(); i++) {
//            assertEquals(listaDescuentos.get(i).getDescripcion(), listaDescModificados.get(i).getDescripcion());
//            assertEquals(listaDescuentos.get(i).getValor_beneficio(), listaDescModificados.get(i).getValor_beneficio());
//            assertEquals(listaDescuentos.get(i).getValor_condicion(), listaDescModificados.get(i).getValor_condicion());
//            assertEquals(listaDescuentos.get(i).getActivo(), listaDescModificados.get(i).getActivo());
//        }
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("test eliminar");
//        ArrayList<Integer> listaDescuentoId = new ArrayList<>();
//        insertarDescuentos(listaDescuentoId);
//
//        limpiarDatosResiduales();
//    }
//}
