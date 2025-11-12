//package pe.edu.pucp.kawkiweb.dao;
//
//import pe.edu.pucp.kawkiweb.model.ProductosDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
//
//public class ProductosDAOTest {
//
//    private ProductosDAO productoDAO;
//
//    public ProductosDAOTest() {
//        this.productoDAO = new ProductosDAOImpl();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        System.out.println("Limpiando datos antes del test...");
//        eliminarTodo();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        System.out.println("Limpiando datos después del test...");
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
//        ProductosDTO producto;
//
//        // 1er producto
//        producto = new ProductosDTO();
//        producto.setDescripcion("Zapato Oxford - Rojo");
//        producto.setCategoria(new CategoriasDTO(CategoriasDTO.ID_OXFORD, CategoriasDTO.NOMBRE_OXFORD));
//        producto.setEstilo(new EstilosDTO(EstilosDTO.ID_CHAROL, EstilosDTO.NOMBRE_CHAROL));
//        producto.setPrecio_venta(180.89);
//        producto.setFecha_hora_creacion(LocalDateTime.now());
//        Integer resultado = this.productoDAO.insertar(producto);
//        assertTrue(resultado != 0);
//        listaProductoId.add(resultado);
//
//        // 2do producto
//        producto = new ProductosDTO();
//        producto.setDescripcion("Zapato Oxford - Crema");
//        producto.setCategoria(new CategoriasDTO(CategoriasDTO.ID_OXFORD, CategoriasDTO.NOMBRE_OXFORD));
//        producto.setEstilo(new EstilosDTO(EstilosDTO.ID_COMBINADOS, EstilosDTO.NOMBRE_COMBINADOS));
//        producto.setPrecio_venta(149.99);
//        producto.setFecha_hora_creacion(LocalDateTime.now());
//        resultado = this.productoDAO.insertar(producto);
//        assertTrue(resultado != 0);
//        listaProductoId.add(resultado);
//
//        // 3er producto
//        producto = new ProductosDTO();
//        producto.setDescripcion("Zapato Derby - Blanco");
//        producto.setCategoria(new CategoriasDTO(CategoriasDTO.ID_DERBY, CategoriasDTO.NOMBRE_DERBY));
//        producto.setEstilo(new EstilosDTO(EstilosDTO.ID_METALIZADOS, EstilosDTO.NOMBRE_METALIZADOS));
//        producto.setPrecio_venta(200.48);
//        producto.setFecha_hora_creacion(LocalDateTime.now());
//        resultado = this.productoDAO.insertar(producto);
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
//        ProductosDTO producto = this.productoDAO.obtenerPorId(listaProductoId.get(0));
//        assertEquals(producto.getProducto_id(), listaProductoId.get(0));
//
//        producto = this.productoDAO.obtenerPorId(listaProductoId.get(1));
//        assertEquals(producto.getProducto_id(), listaProductoId.get(1));
//
//        producto = this.productoDAO.obtenerPorId(listaProductoId.get(2));
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
//        ArrayList<ProductosDTO> listaProductos = this.productoDAO.listarTodos();
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
//        ArrayList<ProductosDTO> listaProductos = this.productoDAO.listarTodos();
//        assertEquals(listaProductoId.size(), listaProductos.size());
//        for (Integer i = 0; i < listaProductoId.size(); i++) {
//            listaProductos.get(i).setDescripcion("NuevaDescripcion" + i.toString());
//            listaProductos.get(i).setCategoria(new CategoriasDTO(CategoriasDTO.ID_DERBY, CategoriasDTO.NOMBRE_DERBY));
//            listaProductos.get(i).setEstilo(new EstilosDTO(EstilosDTO.ID_CLASICOS, EstilosDTO.NOMBRE_CLASICOS));
//            listaProductos.get(i).setPrecio_venta(450.00);
//            listaProductos.get(i).setFecha_hora_creacion(LocalDateTime.now());
//            this.productoDAO.modificar(listaProductos.get(i));
//        }
//
//        ArrayList<ProductosDTO> listaProductosModificados = this.productoDAO.listarTodos();
//        assertEquals(listaProductos.size(), listaProductosModificados.size());
//        for (Integer i = 0; i < listaProductos.size(); i++) {
//            assertEquals(listaProductos.get(i).getDescripcion(), listaProductosModificados.get(i).getDescripcion());
//            assertEquals(listaProductos.get(i).getCategoria().getCategoria_id(),
//                    listaProductosModificados.get(i).getCategoria().getCategoria_id());
//            assertEquals(listaProductos.get(i).getEstilo().getEstilo_id(),
//                    listaProductosModificados.get(i).getEstilo().getEstilo_id());
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
//        ArrayList<Integer> listaProductoId = new ArrayList<>();
//        insertarProductos(listaProductoId);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<ProductosDTO> listaProductos = this.productoDAO.listarTodos();
//        for (Integer i = 0; i < listaProductos.size(); i++) {
//            Integer resultado = this.productoDAO.eliminar(listaProductos.get(i));
//            assertNotEquals(0, resultado);
//            ProductosDTO producto = this.productoDAO.obtenerPorId(listaProductos.get(i).getProducto_id());
//            assertNull(producto);
//        }
//    }
//}
