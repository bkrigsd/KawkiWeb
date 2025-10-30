
//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.dao.ProductoDAO;
//import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;
//import pe.edu.pucp.kawkiweb.daoImp.MovimientoInventarioDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductoDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductoVarianteDAOImpl;
//import pe.edu.pucp.kawkiweb.model.MovimientoInventarioDTO;
//import pe.edu.pucp.kawkiweb.model.ProductoDTO;
//import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
//import pe.edu.pucp.kawkiweb.model.utilMovInventario.TipoMovimiento;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Categoria;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Color;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Estilo;
//import pe.edu.pucp.kawkiweb.model.utilProducto.Talla;
//import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficio;
//
//
//public class MovimientoInventarioBOTest {
//    
//    private MovimientoInventarioBO movInventarioBO;
//    private ProductoVarianteDAO prodVarianteDAO;
//    private ProductoDAO productoDAO;
//    private ProductoVarianteDTO prodVarianteDTO;
//    private Integer prodVarianteId;
//    private Integer productoId;
//    
//    public MovimientoInventarioBOTest() {
//        this.movInventarioBO = new MovimientoInventarioBO();
//        this.prodVarianteDAO = new ProductoVarianteDAOImpl();
//        this.productoDAO = new ProductoDAOImpl();
//    }
//
//    @BeforeEach
//    void prepararContexto() {
//        eliminarTodo(); 
//        prepararProductoBase();
//        prepararProductoVarianteBase();
//    }
//    
//
//    private void prepararProductoBase() {
//         
//        ProductoDTO productoDTO = new ProductoDTO();
//        productoDTO.setCategoria(Categoria.DERBY);
//        productoDTO.setDescripcion("Producto hecho con material de ...");
//        productoDTO.setEstilo(Estilo.CHAROL);
//        productoDTO.setFecha_hora_creacion(LocalDateTime.now());
//        productoDTO.setPrecio_venta(120.00);
//        
//        this.productoId = this.productoDAO.insertar(productoDTO);
//    }
//
//    private void prepararProductoVarianteBase() {
//         
//        ProductoVarianteDTO prodVarianteDTO = new ProductoVarianteDTO();
//        prodVarianteDTO.setAlerta_stock(true);
//        prodVarianteDTO.setColor(Color.ROJO);
//        prodVarianteDTO.setFecha_hora_creacion(LocalDateTime.now());
//        prodVarianteDTO.setProducto_id(this.productoId);
//        String sku = "123123123" + System.currentTimeMillis();
//        prodVarianteDTO.setSKU(sku);
//        prodVarianteDTO.setStock(50);   
//        prodVarianteDTO.setStock_minimo(10);
//        prodVarianteDTO.setTalla(Talla.TREINTA_SEIS);
//        prodVarianteDTO.setTipo_beneficio(TipoBeneficio.DESCUENTO_FIJO);
//        prodVarianteDTO.setValor_beneficio(20); 
//        prodVarianteDTO.setProd_variante_id(this.prodVarianteDAO.insertar(prodVarianteDTO));
//        this.prodVarianteDTO = new ProductoVarianteDTO(prodVarianteDTO);
//    }
//    
//    @Test
//    public void testInsertar() {
//        System.out.println("Insertar");
//        ArrayList<Integer> listaMovInventariosID = new ArrayList<>();
//        this.prepararProductoBase();
//        this.prepararProductoVarianteBase();
//        this.insertarMovimientosInventario(listaMovInventariosID);
//        this.eliminarTodo();
//    }
//    
//    private void insertarMovimientosInventario(ArrayList<Integer> listaMovInventariosID){
//        
//        MovimientoInventarioDTO movInventarioDTO = new MovimientoInventarioDTO();
//        
//        movInventarioDTO.setCantidad(10);
//        movInventarioDTO.setFecha_hora_mov(LocalDateTime.now());
//        movInventarioDTO.setObservacion("La entrega ha sido realizada con exito.");
//        movInventarioDTO.setTipo_movimiento(TipoMovimiento.MOVIMIENTO1);
//        ProductoVarianteDTO prodVariante = new ProductoVarianteDTO(); 
//        movInventarioDTO.setProd_variante(this.prodVarianteDTO); 
//        Integer resultado = this.movInventarioBO.insertar(movInventarioDTO.getCantidad(),
//                movInventarioDTO.getFecha_hora_mov(), movInventarioDTO.getObservacion(),
//                movInventarioDTO.getTipo_movimiento(),movInventarioDTO.getProd_variante());
//        assertTrue(resultado != 0);
//        listaMovInventariosID.add(resultado);
//          
//    }
//    
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("ObtenerPorId");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosId);
//        
//        MovimientoInventarioDTO movInventarioDTO = this.movInventarioBO.obtenerPorId(listaMovInventariosId.get(0));
//        assertEquals(movInventarioDTO.getMov_inventario_id(), listaMovInventariosId.get(0));
//        
//    }
//
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("ListarTodos");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosId);
//        
//        ArrayList<MovimientoInventarioDTO> listaMovInventarios = this.movInventarioBO.listarTodos();
//        assertEquals(listaMovInventariosId.size(),listaMovInventarios.size());
//        for(int i = 0; i<listaMovInventariosId.size();i++){
//            assertEquals(listaMovInventariosId.get(i), listaMovInventarios.get(i).getMov_inventario_id());
//        }
//        
//        this.eliminarTodo();
//    }
//
//
//    @Test
//    public void testModificar() {
//        System.out.println("Modificar");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        this.insertarMovimientosInventario(listaMovInventariosId);
//        
//        ArrayList<MovimientoInventarioDTO> listaMovInventarios = this.movInventarioBO.listarTodos();
//        assertEquals(listaMovInventariosId.size(),listaMovInventarios.size());
//        for(Integer i = 0; i<listaMovInventariosId.size();i++){
//            listaMovInventarios.get(i).setCantidad(400);
//            listaMovInventarios.get(i).setObservacion("El producto esta siendo recibido...");
//            this.movInventarioBO.modificar(listaMovInventarios.get(0).getMov_inventario_id(),listaMovInventarios.get(0).getCantidad(),
//                listaMovInventarios.get(0).getFecha_hora_mov(), listaMovInventarios.get(0).getObservacion(),
//                listaMovInventarios.get(0).getTipo_movimiento(),listaMovInventarios.get(0).getProd_variante());
//        }
//        this.eliminarTodo();
//    }
//
//
//    @Test
//    public void testEliminar() {
//        System.out.println("Eliminar");
//        ArrayList<Integer> listaMovInventariosId = new ArrayList<>();
//        insertarMovimientosInventario(listaMovInventariosId);
//        eliminarTodo();
//    }
//    
//    private void eliminarTodo() {
//        ArrayList<MovimientoInventarioDTO> listaMovInventarios = this.movInventarioBO.listarTodos();
//        for(MovimientoInventarioDTO movInventario : listaMovInventarios){
//            Integer resultado = this.movInventarioBO.eliminar(movInventario.getMov_inventario_id());
//            assertNotEquals(0,resultado);
//            MovimientoInventarioDTO movInventarioDTO = this.movInventarioBO.obtenerPorId(movInventario.getMov_inventario_id());
//            assertNull(movInventarioDTO);
//        }
//    }
//    
//}
