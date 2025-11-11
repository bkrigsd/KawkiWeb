//package pe.edu.pucp.kawkiweb.dao;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.MovimientosInventarioDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
//import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
//import pe.edu.pucp.kawkiweb.model.ProductosDTO;
//import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
//import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
//import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
//
//public class MovimientosInventarioDAOTest {
//
//    private MovimientosInventarioDAO movInventarioDAO;
//    private ProductosVariantesDAO prodVarianteDAO;
//    private ProductosDAO productoDAO;
//    private UsuariosDAO usuarioDAO;
//    private Integer prodVarianteId;
//    private Integer productoId;
//    private Integer usuarioId;
//    private ProductosDTO productoDTO;
//    private ProductosVariantesDTO prodVarianteDTO;
//    private UsuariosDTO usuarioDTO;
//
//    public MovimientosInventarioDAOTest() {
//        this.movInventarioDAO = new MovimientosInventarioDAOImpl();
//        this.prodVarianteDAO = new ProductosVariantesDAOImpl();
//        this.productoDAO = new ProductosDAOImpl();
//        this.usuarioDAO = new UsuariosDAOImpl();
//    }
//
//    @BeforeEach
//    void prepararContexto() {
//        eliminarTodo();
//        prepararProductoBase();
//        prepararProductoVarianteBase();
//        prepararUsuarioBase();
//    }
//
//    @AfterEach
//    void limpiarContexto() {
//        System.out.println("\n>>> Limpiando datos del test...");
//        eliminarTodo();
//        System.out.println(">>> Datos eliminados correctamente\n");
//    }
//    
//    private void prepararProductoBase() {
//        ProductosDTO producto = new ProductosDTO();
//        producto.setCategoria(new CategoriasDTO(CategoriasDTO.ID_DERBY,
//                CategoriasDTO.NOMBRE_DERBY));
//        producto.setDescripcion("Zapato Derby de cuero genuino");
//        producto.setEstilo(new EstilosDTO(EstilosDTO.ID_CHAROL,
//                EstilosDTO.NOMBRE_CHAROL));
//        producto.setFecha_hora_creacion(LocalDateTime.now());
//        producto.setPrecio_venta(120.00);
//        
//        this.productoId = this.productoDAO.insertar(producto);
//        this.productoDTO = this.productoDAO.obtenerPorId(this.productoId);
//        
//        assertNotNull(this.productoId, "El producto debe insertarse correctamente");
//        assertNotNull(this.productoDTO, "El producto debe recuperarse correctamente");
//    }
//
//    private void prepararProductoVarianteBase() {
//        ProductosVariantesDTO variante = new ProductosVariantesDTO();
//        String sku = "SKU" + System.currentTimeMillis();
//        variante.setSKU(sku);
//        variante.setStock(50);
//        variante.setStock_minimo(10);
//        variante.setAlerta_stock(false);
//        variante.setProducto_id(this.productoId);
//        variante.setColor(new ColoresDTO(ColoresDTO.ID_ACERO,
//                ColoresDTO.NOMBRE_ACERO));
//        variante.setTalla(new TallasDTO(TallasDTO.ID_TREINTA_CINCO,
//                TallasDTO.NUMERO_TREINTA_CINCO));
//        variante.setFecha_hora_creacion(LocalDateTime.now());
//        variante.setDisponible(true);
//        
//        this.prodVarianteId = this.prodVarianteDAO.insertar(variante);
//        this.prodVarianteDTO = this.prodVarianteDAO.obtenerPorId(this.prodVarianteId);
//        
//        assertNotNull(this.prodVarianteId, "La variante debe insertarse correctamente");
//        assertNotNull(this.prodVarianteDTO, "La variante debe recuperarse correctamente");
//    }
//
//    private void prepararUsuarioBase() {
//        UsuariosDTO usuario = new UsuariosDTO();
//        usuario.setNombre("Eros");
//        usuario.setApePaterno("Sotelo");
//        usuario.setDni("77722211");
//        usuario.setTelefono("999111555");
//        usuario.setCorreo("eros.sotelo@gmail.com");
//        usuario.setNombreUsuario("erosotelo");
//        usuario.setContrasenha("password123");
//        usuario.setFechaHoraCreacion(LocalDateTime.now());
//        usuario.setTipoUsuario(new TiposUsuarioDTO(TiposUsuarioDTO.ID_VENDEDOR,
//                TiposUsuarioDTO.NOMBRE_VENDEDOR));
//        
//        this.usuarioId = this.usuarioDAO.insertar(usuario);
//        this.usuarioDTO = this.usuarioDAO.obtenerPorId(this.usuarioId);
//        
//        assertNotNull(this.usuarioId, "El usuario debe insertarse correctamente");
//        assertNotNull(this.usuarioDTO, "El usuario debe recuperarse correctamente");
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("========== TEST: Insertar ==========");
//        ArrayList<Integer> listaMovInventariosID = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosID);
//        
//        // Verificar que se insertaron correctamente
//        assertFalse(listaMovInventariosID.isEmpty(), "Debe haber movimientos insertados");
//        for (Integer id : listaMovInventariosID) {
//            assertNotNull(id, "El ID no debe ser null");
//            assertTrue(id > 0, "El ID debe ser mayor a 0");
//            System.out.println("Movimiento insertado con ID: " + id);
//        }
//        System.out.println("Total de movimientos insertados: " + listaMovInventariosID.size());
//    }
//
//    private void insertarMovimientosInventario(ArrayList<Integer> listaMovInventariosID) {
//        MovimientosInventarioDTO movInventario;
//
//        // 1er movimiento - INGRESO
//        movInventario = new MovimientosInventarioDTO();
//        movInventario.setCantidad(10);
//        movInventario.setFecha_hora_mov(LocalDateTime.now());
//        movInventario.setObservacion("Abastecimiento de inventario inicial");
//        movInventario.setTipo_movimiento(new TiposMovimientoDTO(
//                TiposMovimientoDTO.ID_INGRESO, 
//                TiposMovimientoDTO.NOMBRE_INGRESO));
//        movInventario.setProd_variante(this.prodVarianteDTO);
//        movInventario.setUsuario(this.usuarioDTO);
//        
//        Integer resultado = this.movInventarioDAO.insertar(movInventario);
//        assertTrue(resultado != null && resultado != 0, 
//                "El ID del movimiento debe ser válido");
//        listaMovInventariosID.add(resultado);
//
//        // 2do movimiento - SALIDA
//        movInventario = new MovimientosInventarioDTO();
//        movInventario.setCantidad(5);
//        movInventario.setFecha_hora_mov(LocalDateTime.now());
//        movInventario.setObservacion("Salida por venta al cliente");
//        movInventario.setTipo_movimiento(new TiposMovimientoDTO(
//                TiposMovimientoDTO.ID_SALIDA, 
//                TiposMovimientoDTO.NOMBRE_SALIDA));
//        movInventario.setProd_variante(this.prodVarianteDTO);
//        movInventario.setUsuario(this.usuarioDTO);
//        
//        resultado = this.movInventarioDAO.insertar(movInventario);
//        assertTrue(resultado != null && resultado != 0, 
//                "El ID del movimiento debe ser válido");
//        listaMovInventariosID.add(resultado);
//
//        // 3er movimiento - AJUSTE
//        movInventario = new MovimientosInventarioDTO();
//        movInventario.setCantidad(3);
//        movInventario.setFecha_hora_mov(LocalDateTime.now());
//        movInventario.setObservacion("Ajuste por inventario físico");
//        movInventario.setTipo_movimiento(new TiposMovimientoDTO(
//                TiposMovimientoDTO.ID_AJUSTE, 
//                TiposMovimientoDTO.NOMBRE_AJUSTE));
//        movInventario.setProd_variante(this.prodVarianteDTO);
//        movInventario.setUsuario(this.usuarioDTO);
//        
//        resultado = this.movInventarioDAO.insertar(movInventario);
//        assertTrue(resultado != null && resultado != 0, 
//                "El ID del movimiento debe ser válido");
//        listaMovInventariosID.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("========== TEST: ObtenerPorId ==========");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosId);
//
//        // Obtener el primer movimiento
//        MovimientosInventarioDTO movInventario = this.movInventarioDAO
//                .obtenerPorId(listaMovInventariosId.get(0));
//        
//        assertNotNull(movInventario, "El movimiento obtenido no debe ser null");
//        assertEquals(listaMovInventariosId.get(0), movInventario.getMov_inventario_id(), 
//                "Los IDs deben coincidir");
//        assertNotNull(movInventario.getTipo_movimiento(), 
//                "El tipo de movimiento no debe ser null");
//        assertNotNull(movInventario.getProd_variante(), 
//                "La variante del producto no debe ser null");
//        assertNotNull(movInventario.getUsuario(), 
//                "El usuario no debe ser null");
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("========== TEST: ListarTodos ==========");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosId);
//
//        ArrayList<MovimientosInventarioDTO> listaMovInventarios = 
//                this.movInventarioDAO.listarTodos();
//        
//        assertNotNull(listaMovInventarios, "La lista no debe ser null");
//        assertTrue(listaMovInventarios.size() >= listaMovInventariosId.size(), 
//                "Debe haber al menos los movimientos insertados");
//        
//        System.out.println("Total de movimientos en BD: " + listaMovInventarios.size());
//        
//        // Verificar que los IDs insertados están en la lista
//        for (Integer idInsertado : listaMovInventariosId) {
//            boolean encontrado = listaMovInventarios.stream()
//                    .anyMatch(mov -> mov.getMov_inventario_id().equals(idInsertado));
//            assertTrue(encontrado, "El ID " + idInsertado + " debe estar en la lista");
//        }
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("========== TEST: Modificar ==========");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosId);
//
//        // Obtener el primer movimiento y modificarlo
//        MovimientosInventarioDTO movInventario = this.movInventarioDAO
//                .obtenerPorId(listaMovInventariosId.get(0));
//        assertNotNull(movInventario, "El movimiento debe existir");
//        
//        System.out.println("Datos originales:");
//        System.out.println("  Cantidad: " + movInventario.getCantidad());
//        System.out.println("  Observación: " + movInventario.getObservacion());
//        
//        // Modificar datos
//        Integer nuevaCantidad = 150;
//        String nuevaObservacion = "Observación modificada en test";
//        movInventario.setCantidad(nuevaCantidad);
//        movInventario.setObservacion(nuevaObservacion);
//        
//        Integer resultado = this.movInventarioDAO.modificar(movInventario);
//        assertTrue(resultado > 0, "La modificación debe ser exitosa");
//        
//        // Verificar que se modificó correctamente
//        MovimientosInventarioDTO movModificado = this.movInventarioDAO
//                .obtenerPorId(listaMovInventariosId.get(0));
//        
//        assertEquals(nuevaCantidad, movModificado.getCantidad(), 
//                "La cantidad debe haberse actualizado");
//        assertEquals(nuevaObservacion, movModificado.getObservacion(), 
//                "La observación debe haberse actualizado");
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("========== TEST: Eliminar ==========");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        insertarMovimientosInventario(listaMovInventariosId);
//        
//        System.out.println("Movimientos insertados: " + listaMovInventariosId.size());
//        
//        // Eliminar los movimientos insertados
//        int eliminados = 0;
//        for (Integer id : listaMovInventariosId) {
//            MovimientosInventarioDTO mov = this.movInventarioDAO.obtenerPorId(id);
//            assertNotNull(mov, "El movimiento debe existir antes de eliminarlo");
//            
//            Integer resultado = this.movInventarioDAO.eliminar(mov);
//            assertNotEquals(0, resultado, "La eliminación debe ser exitosa");
//            
//            // Verificar que ya no existe
//            MovimientosInventarioDTO movEliminado = this.movInventarioDAO.obtenerPorId(id);
//            assertNull(movEliminado, "El movimiento eliminado no debe existir");
//            
//            eliminados++;
//        }
//        
//        System.out.println("Total eliminados: " + eliminados);
//    }
//
//    private void eliminarTodo() {
//        try {
//            // 1. Eliminar todos los movimientos de inventario (primero porque depende de variantes y usuarios)
//            ArrayList<MovimientosInventarioDTO> listaMovInventarios = 
//                    this.movInventarioDAO.listarTodos();
//            if (listaMovInventarios != null && !listaMovInventarios.isEmpty()) {
//                System.out.println("  - Eliminando " + listaMovInventarios.size() + " movimientos de inventario...");
//                for (MovimientosInventarioDTO movInventario : listaMovInventarios) {
//                    try {
//                        this.movInventarioDAO.eliminar(movInventario);
//                    } catch (Exception e) {
//                        System.err.println("    Error al eliminar movimiento ID " + movInventario.getMov_inventario_id() + ": " + e.getMessage());
//                    }
//                }
//            }
//            
//            // 2. Eliminar todos los productos variantes (depende de productos)
//            ArrayList<ProductosVariantesDTO> listaVariantes = 
//                    this.prodVarianteDAO.listarTodos();
//            if (listaVariantes != null && !listaVariantes.isEmpty()) {
//                System.out.println("  - Eliminando " + listaVariantes.size() + " variantes de productos...");
//                for (ProductosVariantesDTO variante : listaVariantes) {
//                    try {
//                        this.prodVarianteDAO.eliminar(variante);
//                    } catch (Exception e) {
//                        System.err.println("    Error al eliminar variante ID " + variante.getProd_variante_id() + ": " + e.getMessage());
//                    }
//                }
//            }
//            
//            // 3. Eliminar todos los productos (independiente)
//            ArrayList<ProductosDTO> listaProductos = this.productoDAO.listarTodos();
//            if (listaProductos != null && !listaProductos.isEmpty()) {
//                System.out.println("  - Eliminando " + listaProductos.size() + " productos...");
//                for (ProductosDTO producto : listaProductos) {
//                    try {
//                        this.productoDAO.eliminar(producto);
//                    } catch (Exception e) {
//                        System.err.println("    Error al eliminar producto ID " + producto.getProducto_id() + ": " + e.getMessage());
//                    }
//                }
//            }
//            
//            // 4. Eliminar todos los usuarios (independiente)
//            ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
//            if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
//                System.out.println("  - Eliminando " + listaUsuarios.size() + " usuarios...");
//                for (UsuariosDTO usuario : listaUsuarios) {
//                    try {
//                        this.usuarioDAO.eliminar(usuario);
//                    } catch (Exception e) {
//                        System.err.println("    Error al eliminar usuario ID " + usuario.getUsuarioId() + ": " + e.getMessage());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error general en eliminarTodo(): " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//}
