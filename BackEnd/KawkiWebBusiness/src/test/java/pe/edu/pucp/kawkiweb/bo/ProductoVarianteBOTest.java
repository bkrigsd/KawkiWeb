//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Categoria;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Color;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Estilo;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Talla;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//
//public class ProductoVarianteBOTest {
//
//    private ProductoVarianteBO prodVarianteBO;
//    private ProductoBO productoBO;
//    private Integer productoIdPrueba;
//
//    public ProductoVarianteBOTest() {
//        this.prodVarianteBO = new ProductoVarianteBO();
//        this.productoBO = new ProductoBO();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        // Crear producto de prueba dinámicamente
//        this.productoIdPrueba = this.productoBO.insertar(
//                "Zapato de Prueba para Variantes",
//                Categoria.OXFORD,
//                Estilo.CHAROL,
//                150.0,
//                LocalDateTime.now()
//        );
//
//        assertNotNull(this.productoIdPrueba, "El producto de prueba debería haberse creado");
//        assertTrue(this.productoIdPrueba > 0, "El ID del producto debe ser válido");
//
//        // Limpia las variantes antes de cada prueba
//        eliminarTodo();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Limpia las variantes después de cada prueba
//        eliminarTodo();
//
//        // Elimina el producto de prueba
//        if (this.productoIdPrueba != null) {
//            Integer resultado = this.productoBO.eliminar(this.productoIdPrueba);
//            assertNotEquals(0, resultado, "El producto de prueba debería eliminarse correctamente");
//        }
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaVarianteId = new ArrayList<>();
//        insertarVariantes(listaVarianteId);
//    }
//
//    private void insertarVariantes(ArrayList<Integer> listaVarianteId) {
//        // 1era variante
//        Integer resultado = this.prodVarianteBO.insertar(
//                "OXF-CHA-NEG-35",
//                50,
//                10,
//                false,
//                this.productoIdPrueba,
//                Color.NEGRO,
//                Talla.TREINTA_CINCO,
//                null,
//                null,
//                LocalDateTime.now());
//        assertTrue(resultado != 0);
//        listaVarianteId.add(resultado);
//
//        // 2da variante - con beneficio
//        resultado = this.prodVarianteBO.insertar(
//                "OXF-CHA-ROJ-36",
//                30,
//                5,
//                false,
//                this.productoIdPrueba,
//                Color.ROJO,
//                Talla.TREINTA_SEIS,
//                TipoBeneficio.DESCUENTO_PORCENTAJE,
//                15,
//                LocalDateTime.now());
//        assertTrue(resultado != 0);
//        listaVarianteId.add(resultado);
//
//        // 3era variante - con alerta de stock
//        resultado = this.prodVarianteBO.insertar(
//                "DER-MET-BLA-37",
//                8,
//                10,
//                true,
//                this.productoIdPrueba,
//                Color.BLANCO,
//                Talla.TREINTA_SIETE,
//                TipoBeneficio.DESCUENTO_FIJO,
//                20,
//                LocalDateTime.now());
//        assertTrue(resultado != 0);
//        listaVarianteId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
//        insertarVariantes(listaProdVariantesId);
//
//        ProductoVarianteDTO prodVariante = this.prodVarianteBO.obtenerPorId(listaProdVariantesId.get(0));
//        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(0));
//
//        prodVariante = this.prodVarianteBO.obtenerPorId(listaProdVariantesId.get(1));
//        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(1));
//
//        prodVariante = this.prodVarianteBO.obtenerPorId(listaProdVariantesId.get(2));
//        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(2));
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaVarianteId = new ArrayList<>();
//        insertarVariantes(listaVarianteId);
//
//        ArrayList<ProductoVarianteDTO> listaVariantes = this.prodVarianteBO.listarTodos();
//        assertEquals(listaVarianteId.size(), listaVariantes.size());
//        for (Integer i = 0; i < listaVarianteId.size(); i++) {
//            assertEquals(listaVarianteId.get(i), listaVariantes.get(i).getProd_variante_id());
//        }
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaVarianteId = new ArrayList<>();
//        insertarVariantes(listaVarianteId);
//
//        ArrayList<ProductoVarianteDTO> listaVariantes = this.prodVarianteBO.listarTodos();
//        assertEquals(listaVarianteId.size(), listaVariantes.size());
//
//        for (Integer i = 0; i < listaVarianteId.size(); i++) {
//            listaVariantes.get(i).setSKU("NUEVO-SKU-" + i.toString());
//            listaVariantes.get(i).setStock(100 + i);
//            listaVariantes.get(i).setStock_minimo(15);
//            listaVariantes.get(i).setAlerta_stock(true);
//            listaVariantes.get(i).setColor(Color.AZUL);
//            listaVariantes.get(i).setTalla(Talla.TREINTA_OCHO);
//            listaVariantes.get(i).setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
//            listaVariantes.get(i).setValor_beneficio(25);
//            listaVariantes.get(i).setFecha_hora_creacion(LocalDateTime.now());
//
//            this.prodVarianteBO.modificar(
//                    listaVariantes.get(i).getProd_variante_id(),
//                    listaVariantes.get(i).getSKU(),
//                    listaVariantes.get(i).getStock(),
//                    listaVariantes.get(i).getStock_minimo(),
//                    listaVariantes.get(i).getAlerta_stock(),
//                    listaVariantes.get(i).getProducto_id(),
//                    listaVariantes.get(i).getColor(),
//                    listaVariantes.get(i).getTalla(),
//                    listaVariantes.get(i).getTipo_beneficio(),
//                    listaVariantes.get(i).getValor_beneficio(),
//                    listaVariantes.get(i).getFecha_hora_creacion());
//        }
//
//        ArrayList<ProductoVarianteDTO> listaVariantesModificadas = this.prodVarianteBO.listarTodos();
//        assertEquals(listaVariantes.size(), listaVariantesModificadas.size());
//
//        for (Integer i = 0; i < listaVariantes.size(); i++) {
//            assertEquals(listaVariantes.get(i).getSKU(), listaVariantesModificadas.get(i).getSKU());
//            assertEquals(listaVariantes.get(i).getStock(), listaVariantesModificadas.get(i).getStock());
//            assertEquals(listaVariantes.get(i).getStock_minimo(), listaVariantesModificadas.get(i).getStock_minimo());
//            assertEquals(listaVariantes.get(i).getAlerta_stock(), listaVariantesModificadas.get(i).getAlerta_stock());
//            assertEquals(listaVariantes.get(i).getColor(), listaVariantesModificadas.get(i).getColor());
//            assertEquals(listaVariantes.get(i).getTalla(), listaVariantesModificadas.get(i).getTalla());
//            assertEquals(listaVariantes.get(i).getTipo_beneficio(), listaVariantesModificadas.get(i).getTipo_beneficio());
//            assertEquals(listaVariantes.get(i).getValor_beneficio(), listaVariantesModificadas.get(i).getValor_beneficio());
//            // No valido las fechas, porque siempre van a diferir por 1 segundo con mis pruebas
//        }
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaProdVariantesID = new ArrayList<>();
//        insertarVariantes(listaProdVariantesID);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<ProductoVarianteDTO> listaProdVariantes = this.prodVarianteBO.listarTodos();
//        for (Integer i = 0; i < listaProdVariantes.size(); i++) {
//            Integer resultado = this.prodVarianteBO.
//                    eliminar(listaProdVariantes.get(i).getProd_variante_id());
//            assertNotEquals(0, resultado);
//            ProductoVarianteDTO prodVariante = this.prodVarianteBO.
//                    obtenerPorId(listaProdVariantes.get(i).getProd_variante_id());
//            assertNull(prodVariante);
//        }
//    }
//}
