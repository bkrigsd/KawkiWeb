//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.model.ProductoDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Categoria;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Estilo;
//
//public class ProductoBOTest {
//
//    private ProductoBO productoBO;
//
//    public ProductoBOTest() {
//        this.productoBO = new ProductoBO();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        // Limpia la base de datos antes de cada prueba
//        eliminarTodo();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Limpia la base de datos después de cada prueba
//        eliminarTodo();
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaProductoId = new ArrayList<>();
//        insertarProductos(listaProductoId);
//        eliminarTodo();
//    }
//
//    private void insertarProductos(ArrayList<Integer> listaProductoId) {
//        Integer resultado = this.productoBO.insertar(
//                "Zapato Oxford - Rojo",
//                Categoria.OXFORD,
//                Estilo.CHAROL,
//                180.89,
//                LocalDateTime.now());
//        assertTrue(resultado != 0);
//        listaProductoId.add(resultado);
//
//        resultado = this.productoBO.insertar(
//                "Zapato Oxford - Crema",
//                Categoria.OXFORD,
//                Estilo.COMBINADOS,
//                149.99,
//                LocalDateTime.now());
//        assertTrue(resultado != 0);
//        listaProductoId.add(resultado);
//
//        resultado = this.productoBO.insertar(
//                "Zapato Derby - Blanco",
//                Categoria.DERBY,
//                Estilo.METALIZADOS,
//                200.48,
//                LocalDateTime.now());
//        assertTrue(resultado != 0);
//        listaProductoId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaProductoId = new ArrayList<>();
//        insertarProductos(listaProductoId);
//
//        ProductoDTO producto = this.productoBO.obtenerPorId(listaProductoId.get(0));
//        assertEquals(producto.getProducto_id(), listaProductoId.get(0));
//
//        producto = this.productoBO.obtenerPorId(listaProductoId.get(1));
//        assertEquals(producto.getProducto_id(), listaProductoId.get(1));
//
//        producto = this.productoBO.obtenerPorId(listaProductoId.get(2));
//        assertEquals(producto.getProducto_id(), listaProductoId.get(2));
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaProductoId = new ArrayList<>();
//        insertarProductos(listaProductoId);
//
//        ArrayList<ProductoDTO> listaProductos = this.productoBO.listarTodos();
//        assertEquals(listaProductoId.size(), listaProductos.size());
//        for (Integer i = 0; i < listaProductoId.size(); i++) {
//            assertEquals(listaProductoId.get(i), listaProductos.get(i).getProducto_id());
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaProductoId = new ArrayList<>();
//        insertarProductos(listaProductoId);
//
//        ArrayList<ProductoDTO> listaProductos = this.productoBO.listarTodos();
//        assertEquals(listaProductoId.size(), listaProductos.size());
//        for (Integer i = 0; i < listaProductoId.size(); i++) {
//            listaProductos.get(i).setDescripcion("NuevaDescripcion" + i.toString());
//            listaProductos.get(i).setCategoria(Categoria.DERBY);
//            listaProductos.get(i).setEstilo(Estilo.CLASICOS);
//            listaProductos.get(i).setPrecio_venta(450.00);
//            listaProductos.get(i).setFecha_hora_creacion(LocalDateTime.now());
//            this.productoBO.modificar(
//                    listaProductos.get(i).getProducto_id(),
//                    listaProductos.get(i).getDescripcion(),
//                    listaProductos.get(i).getCategoria(),
//                    listaProductos.get(i).getEstilo(),
//                    listaProductos.get(i).getPrecio_venta(),
//                    listaProductos.get(i).getFecha_hora_creacion());
//        }
//
//        ArrayList<ProductoDTO> listaProductosModificados = this.productoBO.listarTodos();
//        assertEquals(listaProductos.size(), listaProductosModificados.size());
//        for (Integer i = 0; i < listaProductos.size(); i++) {
//            assertEquals(listaProductos.get(i).getDescripcion(), listaProductosModificados.get(i).getDescripcion());
//            assertEquals(listaProductos.get(i).getCategoria(), listaProductosModificados.get(i).getCategoria());
//            assertEquals(listaProductos.get(i).getEstilo(), listaProductosModificados.get(i).getEstilo());
//            assertEquals(listaProductos.get(i).getPrecio_venta(), listaProductosModificados.get(i).getPrecio_venta());
//            //No valido las fechas, porque siempre van a diferir por 1 segundo 
//            //con mis pruebas
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> lista_productos_id = new ArrayList<>();
//        insertarProductos(lista_productos_id);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<ProductoDTO> lista_productos = this.productoBO.listarTodos();
//        for (Integer i = 0; i < lista_productos.size(); i++) {
//            Integer resultado = this.productoBO.
//                    eliminar(lista_productos.get(i).getProducto_id());
//            assertNotEquals(0, resultado);
//            ProductoDTO producto = this.productoBO.
//                    obtenerPorId(lista_productos.get(i).getProducto_id());
//            assertNull(producto);
//        }
//    }
//}
