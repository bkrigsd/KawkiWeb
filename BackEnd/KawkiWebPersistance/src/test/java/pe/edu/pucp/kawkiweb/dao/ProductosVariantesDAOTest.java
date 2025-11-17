//package pe.edu.pucp.kawkiweb.dao;
//
//import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import pe.edu.pucp.kawkiweb.daoImp.ColoresDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.TallasDAOImpl;
//import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
//import pe.edu.pucp.kawkiweb.model.ProductosDTO;
//import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
//
//public class ProductosVariantesDAOTest {
//
//    private ProductosVariantesDAO prodVarianteDAO;
//    private ProductosDAO productoDAO;
//    private ColoresDAO colorDAO;
//    private TallasDAO tallaDAO;
//    private UsuariosDAO usuarioDAO;
//    
//    private UsuariosDTO usuarioDTO;
//    private Integer productoBaseId;
//
//    public ProductosVariantesDAOTest() {
//        this.prodVarianteDAO = new ProductosVariantesDAOImpl();
//        this.usuarioDAO = new UsuariosDAOImpl();
//        this.productoDAO = new ProductosDAOImpl();
//        this.colorDAO = new ColoresDAOImpl();
//        this.tallaDAO = new TallasDAOImpl();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        System.out.println("Limpiando datos antes del test...");
//        limpiarBaseDatos();
//        prepararUsuarioBase();
//        prepararProductoBase();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        System.out.println("Limpiando datos después del test...");
//        limpiarBaseDatos();
//        this.productoBaseId = null;
//    }
//
//    private void prepararUsuarioBase() {
//        this.usuarioDTO = new UsuariosDTO();
//        this.usuarioDTO.setNombre("Eros");
//        this.usuarioDTO.setApePaterno("Sotelo");
//        this.usuarioDTO.setDni("77722211");
//        this.usuarioDTO.setTelefono("999111555");
//        this.usuarioDTO.setCorreo("eros.sotelo@gmail.com");
//        this.usuarioDTO.setNombreUsuario("erosotelo");
//        this.usuarioDTO.setContrasenha("password123");
//        this.usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
//        this.usuarioDTO.setTipoUsuario(new TiposUsuarioDTO(
//                TiposUsuarioDTO.ID_VENDEDOR, TiposUsuarioDTO.NOMBRE_VENDEDOR));
//        this.usuarioDTO.setActivo(Boolean.TRUE);
//
//        Integer resultado = this.usuarioDAO.insertar(this.usuarioDTO);
//        this.usuarioDTO.setUsuarioId(resultado);
//        assertTrue(resultado != 0);
//    }
//    
//    private void prepararProductoBase() {
//        ProductosDTO producto = new ProductosDTO();
//        producto.setDescripcion("Producto Base Derby Clásico para Test de Variantes");
//        producto.setCategoria(new CategoriasDTO(CategoriasDTO.ID_DERBY, CategoriasDTO.NOMBRE_DERBY));
//        producto.setEstilo(new EstilosDTO(EstilosDTO.ID_CLASICOS, EstilosDTO.NOMBRE_CLASICOS));
//        producto.setPrecio_venta(100.00);
//        producto.setFecha_hora_creacion(LocalDateTime.now());
//        producto.setUsuario(this.usuarioDTO);
//        
//        this.productoBaseId = this.productoDAO.insertar(producto);
//        assertTrue(this.productoBaseId != 0);
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
//        insertarProdVariantes(listaProdVariantesId);
//
//        assertEquals(3, listaProdVariantesId.size());
//        for (Integer id : listaProdVariantesId) {
//            assertTrue(id != 0);
//        }
//    }
//
//    private void insertarProdVariantes(ArrayList<Integer> listaProdVariantesId) {
//        ProductosVariantesDTO prodVariante;
//
//        // 1ra variante
//        prodVariante = new ProductosVariantesDTO();
//        prodVariante.setSKU("OXF-ROJ-37");
//        prodVariante.setStock(20);
//        prodVariante.setStock_minimo(5);
//        prodVariante.setAlerta_stock(false);
//        prodVariante.setProducto_id(this.productoBaseId);
//        prodVariante.setColor(this.colorDAO.obtenerPorId(ColoresDTO.ID_ROJO));
//        prodVariante.setTalla(this.tallaDAO.obtenerPorId(TallasDTO.ID_TREINTA_SIETE));
//        prodVariante.setUrl_imagen("C:carpeta_url_imagen");
//        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
//        prodVariante.setDisponible(true);
//        prodVariante.setUsuario(this.usuarioDTO);
//        
//        Integer resultado = this.prodVarianteDAO.insertar(prodVariante);
//        assertTrue(resultado != 0);
//        listaProdVariantesId.add(resultado);
//
//        // 2da variante
//        prodVariante = new ProductosVariantesDTO();
//        prodVariante.setSKU("OXF-CRE-38");
//        prodVariante.setStock(15);
//        prodVariante.setStock_minimo(3);
//        prodVariante.setProducto_id(this.productoBaseId);
//        prodVariante.setColor(this.colorDAO.obtenerPorId(ColoresDTO.ID_CREMA));
//        prodVariante.setTalla(this.tallaDAO.obtenerPorId(TallasDTO.ID_TREINTA_OCHO));
//        prodVariante.setUrl_imagen("C:carpeta_url_imagen");
//        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
//        prodVariante.setDisponible(true);
//        prodVariante.setUsuario(this.usuarioDTO);
//        
//        resultado = this.prodVarianteDAO.insertar(prodVariante);
//        assertTrue(resultado != 0);
//        listaProdVariantesId.add(resultado);
//
//        // 3ra variante
//        prodVariante = new ProductosVariantesDTO();
//        prodVariante.setSKU("DER-BLA-35");
//        prodVariante.setStock(10);
//        prodVariante.setStock_minimo(10);
//        prodVariante.setAlerta_stock(true);
//        prodVariante.setProducto_id(this.productoBaseId);
//        prodVariante.setColor(this.colorDAO.obtenerPorId(ColoresDTO.ID_BLANCO));
//        prodVariante.setTalla(this.tallaDAO.obtenerPorId(TallasDTO.ID_TREINTA_CINCO));
//        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
//        prodVariante.setDisponible(true);
//        prodVariante.setUsuario(this.usuarioDTO);
//        
//        resultado = this.prodVarianteDAO.insertar(prodVariante);
//        assertTrue(resultado != 0);
//        listaProdVariantesId.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//
//        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
//        insertarProdVariantes(listaProdVariantesId);
//
//        ProductosVariantesDTO prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(0));
//        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(0));
//        assertEquals("OXF-ROJ-37", prodVariante.getSKU());
//        assertEquals(ColoresDTO.ID_ROJO, prodVariante.getColor().getColor_id());
//        assertEquals(TallasDTO.ID_TREINTA_SIETE, prodVariante.getTalla().getTalla_id());
//
//        prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(1));
//        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(1));
//
//        prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(2));
//        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(2));
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//
//        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
//        insertarProdVariantes(listaProdVariantesId);
//
//        ArrayList<ProductosVariantesDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
//        assertEquals(listaProdVariantesId.size(), listaProdVariantes.size());
//        for (Integer i = 0; i < listaProdVariantesId.size(); i++) {
//            assertEquals(listaProdVariantesId.get(i), listaProdVariantes.get(i).getProd_variante_id());
//            assertNotNull(listaProdVariantes.get(i).getColor());
//            assertNotNull(listaProdVariantes.get(i).getTalla());
//        }
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//
//        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
//        insertarProdVariantes(listaProdVariantesId);
//
//        ArrayList<ProductosVariantesDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
//        assertEquals(listaProdVariantesId.size(), listaProdVariantes.size());
//
//        ColoresDTO colorPlata = this.colorDAO.obtenerPorId(ColoresDTO.ID_PLATA);
//        TallasDTO tallaTreintaSeis = this.tallaDAO.obtenerPorId(TallasDTO.ID_TREINTA_SEIS);
//
//        for (Integer i = 0; i < listaProdVariantesId.size(); i++) {
//            listaProdVariantes.get(i).setSKU("SKU-MOD" + i.toString());
//            listaProdVariantes.get(i).setStock(50);
//            listaProdVariantes.get(i).setStock_minimo(10);
//            listaProdVariantes.get(i).setColor(colorPlata);
//            listaProdVariantes.get(i).setTalla(tallaTreintaSeis);
//            listaProdVariantes.get(i).setFecha_hora_creacion(LocalDateTime.now());
//            listaProdVariantes.get(i).setDisponible(false);
//            this.prodVarianteDAO.modificar(listaProdVariantes.get(i));
//        }
//
//        ArrayList<ProductosVariantesDTO> listaProdVariantesModificados = this.prodVarianteDAO.listarTodos();
//        assertEquals(listaProdVariantes.size(), listaProdVariantesModificados.size());
//        for (Integer i = 0; i < listaProdVariantes.size(); i++) {
//            assertEquals(listaProdVariantes.get(i).getSKU(), listaProdVariantesModificados.get(i).getSKU());
//            assertEquals(listaProdVariantes.get(i).getStock(), listaProdVariantesModificados.get(i).getStock());
//            assertEquals(listaProdVariantes.get(i).getDisponible(), listaProdVariantesModificados.get(i).getDisponible());
//            assertEquals(listaProdVariantes.get(i).getStock_minimo(), listaProdVariantesModificados.get(i).getStock_minimo());
//            assertEquals(ColoresDTO.ID_PLATA, listaProdVariantesModificados.get(i).getColor().getColor_id());
//            assertEquals(TallasDTO.ID_TREINTA_SEIS, listaProdVariantesModificados.get(i).getTalla().getTalla_id());
//        }
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//
//        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
//        insertarProdVariantes(listaProdVariantesId);
//
//        assertEquals(3, this.prodVarianteDAO.listarTodos().size());
//
//        for (Integer id : listaProdVariantesId) {
//            ProductosVariantesDTO variante = this.prodVarianteDAO.obtenerPorId(id);
//            assertNotNull(variante);
//            Integer resultado = this.prodVarianteDAO.eliminar(variante);
//            assertNotEquals(0, resultado);
//
//            ProductosVariantesDTO varianteEliminada = this.prodVarianteDAO.obtenerPorId(id);
//            assertNull(varianteEliminada);
//        }
//
//        assertEquals(0, this.prodVarianteDAO.listarTodos().size());
//    }
//
//    private void limpiarBaseDatos() {
//        ArrayList<ProductosVariantesDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
//        for (ProductosVariantesDTO variante : listaProdVariantes) {
//            this.prodVarianteDAO.eliminar(variante);
//        }
//
//        ArrayList<ProductosDTO> listaProductos = this.productoDAO.listarTodos();
//        for (ProductosDTO producto : listaProductos) {
//            this.productoDAO.eliminar(producto);
//        }
//        
//        // Eliminar usuarios
//        ArrayList<UsuariosDTO> listaUsuarios = this.usuarioDAO.listarTodos();
//        for (Integer i = 0; i < listaUsuarios.size(); i++) {
//            Integer resultado = this.usuarioDAO.eliminar(listaUsuarios.get(i));
//            assertNotEquals(0, resultado);
//            UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(listaUsuarios.get(i).getUsuarioId());
//            assertNull(usuario);
//        }
//    }
//}
