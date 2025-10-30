package pe.edu.pucp.kawkiweb.dao;

import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColorDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallaDTO;
import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficioDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import pe.edu.pucp.kawkiweb.daoImp.ColorDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductoVarianteDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.TallaDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.TipoBeneficioDAOImpl;
import pe.edu.pucp.kawkiweb.model.ProductoDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriaDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstiloDTO;

public class ProductoVarianteDAOTest {

    private ProductoVarianteDAO prodVarianteDAO;
    private ProductoDAO productoDAO;
    private ColorDAO colorDAO;
    private TallaDAO tallaDAO;
    private TipoBeneficioDAO tipoBeneficioDAO;
    private Integer productoBaseId;

    public ProductoVarianteDAOTest() {
        this.prodVarianteDAO = new ProductoVarianteDAOImpl();
        this.productoDAO = new ProductoDAOImpl();
        this.colorDAO = new ColorDAOImpl();
        this.tallaDAO = new TallaDAOImpl();
        this.tipoBeneficioDAO = new TipoBeneficioDAOImpl();
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Limpiando datos antes del test...");
        limpiarBaseDatos();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Limpiando datos después del test...");
        limpiarBaseDatos();
        this.productoBaseId = null;
    }

    private void prepararProductoBase() {
        ProductoDTO producto = new ProductoDTO();
        producto.setDescripcion("Producto Base Derby Clásico para Test de Variantes");
        producto.setCategoria(new CategoriaDTO(CategoriaDTO.ID_DERBY, CategoriaDTO.NOMBRE_DERBY));
        producto.setEstilo(new EstiloDTO(EstiloDTO.ID_CLASICOS, EstiloDTO.NOMBRE_CLASICOS));
        producto.setPrecio_venta(100.00);
        producto.setFecha_hora_creacion(LocalDateTime.now());
        this.productoBaseId = this.productoDAO.insertar(producto);
        assertTrue(this.productoBaseId != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        prepararProductoBase();
        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);

        assertEquals(3, listaProdVariantesId.size());
        for (Integer id : listaProdVariantesId) {
            assertTrue(id != 0);
        }
    }

    private void insertarProdVariantes(ArrayList<Integer> listaProdVariantesId) {
        ProductoVarianteDTO prodVariante;

        // 1ra variante
        prodVariante = new ProductoVarianteDTO();
        prodVariante.setSKU("OXF-ROJ-37");
        prodVariante.setStock(20);
        prodVariante.setStock_minimo(5);
        prodVariante.setAlerta_stock(false);
        prodVariante.setProducto_id(this.productoBaseId);
        prodVariante.setColor(this.colorDAO.obtenerPorId(ColorDTO.ID_ROJO));
        prodVariante.setTalla(this.tallaDAO.obtenerPorId(TallaDTO.ID_TREINTA_SIETE));
        prodVariante.setTipo_beneficio(this.tipoBeneficioDAO.obtenerPorId(TipoBeneficioDTO.ID_DESCUENTO_PORCENTAJE));
        prodVariante.setValor_beneficio(10);
        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
        Integer resultado = this.prodVarianteDAO.insertar(prodVariante);
        assertTrue(resultado != 0);
        listaProdVariantesId.add(resultado);

        // 2da variante
        prodVariante = new ProductoVarianteDTO();
        prodVariante.setSKU("OXF-CRE-38");
        prodVariante.setStock(15);
        prodVariante.setStock_minimo(3);
        prodVariante.setAlerta_stock(false);
        prodVariante.setProducto_id(this.productoBaseId);
        prodVariante.setColor(this.colorDAO.obtenerPorId(ColorDTO.ID_CREMA));
        prodVariante.setTalla(this.tallaDAO.obtenerPorId(TallaDTO.ID_TREINTA_OCHO));
        prodVariante.setTipo_beneficio(null);
        prodVariante.setValor_beneficio(null);
        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
        resultado = this.prodVarianteDAO.insertar(prodVariante);
        assertTrue(resultado != 0);
        listaProdVariantesId.add(resultado);

        // 3ra variante
        prodVariante = new ProductoVarianteDTO();
        prodVariante.setSKU("DER-BLA-35");
        prodVariante.setStock(10);
        prodVariante.setStock_minimo(10);
        prodVariante.setAlerta_stock(true);
        prodVariante.setProducto_id(this.productoBaseId);
        prodVariante.setColor(this.colorDAO.obtenerPorId(ColorDTO.ID_BLANCO));
        prodVariante.setTalla(this.tallaDAO.obtenerPorId(TallaDTO.ID_TREINTA_CINCO));
        prodVariante.setTipo_beneficio(this.tipoBeneficioDAO.obtenerPorId(TipoBeneficioDTO.ID_DESCUENTO_FIJO));
        prodVariante.setValor_beneficio(20);
        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
        resultado = this.prodVarianteDAO.insertar(prodVariante);
        assertTrue(resultado != 0);
        listaProdVariantesId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");

        prepararProductoBase();

        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);

        ProductoVarianteDTO prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(0));
        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(0));
        assertEquals("OXF-ROJ-37", prodVariante.getSKU());
        assertEquals(ColorDTO.ID_ROJO, prodVariante.getColor().getColor_id());
        assertEquals(TallaDTO.ID_TREINTA_SIETE, prodVariante.getTalla().getTalla_id());

        prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(1));
        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(1));
        assertNull(prodVariante.getTipo_beneficio());
        assertNull(prodVariante.getValor_beneficio());

        prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(2));
        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(2));
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");

        prepararProductoBase();

        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);

        ArrayList<ProductoVarianteDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
        assertEquals(listaProdVariantesId.size(), listaProdVariantes.size());
        for (Integer i = 0; i < listaProdVariantesId.size(); i++) {
            assertEquals(listaProdVariantesId.get(i), listaProdVariantes.get(i).getProd_variante_id());
            assertNotNull(listaProdVariantes.get(i).getColor());
            assertNotNull(listaProdVariantes.get(i).getTalla());
        }
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");

        prepararProductoBase();

        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);

        ArrayList<ProductoVarianteDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
        assertEquals(listaProdVariantesId.size(), listaProdVariantes.size());

        ColorDTO colorPlata = this.colorDAO.obtenerPorId(ColorDTO.ID_PLATA);
        TallaDTO tallaTreintaSeis = this.tallaDAO.obtenerPorId(TallaDTO.ID_TREINTA_SEIS);
        TipoBeneficioDTO tipoDescuentoFijo = this.tipoBeneficioDAO.obtenerPorId(TipoBeneficioDTO.ID_DESCUENTO_FIJO);

        for (Integer i = 0; i < listaProdVariantesId.size(); i++) {
            listaProdVariantes.get(i).setSKU("SKU-MOD" + i.toString());
            listaProdVariantes.get(i).setStock(50);
            listaProdVariantes.get(i).setStock_minimo(10);
            listaProdVariantes.get(i).setAlerta_stock(false);
            listaProdVariantes.get(i).setColor(colorPlata);
            listaProdVariantes.get(i).setTalla(tallaTreintaSeis);
            listaProdVariantes.get(i).setTipo_beneficio(tipoDescuentoFijo);
            listaProdVariantes.get(i).setValor_beneficio(35);
            listaProdVariantes.get(i).setFecha_hora_creacion(LocalDateTime.now());
            this.prodVarianteDAO.modificar(listaProdVariantes.get(i));
        }

        ArrayList<ProductoVarianteDTO> listaProdVariantesModificados = this.prodVarianteDAO.listarTodos();
        assertEquals(listaProdVariantes.size(), listaProdVariantesModificados.size());
        for (Integer i = 0; i < listaProdVariantes.size(); i++) {
            assertEquals(listaProdVariantes.get(i).getSKU(), listaProdVariantesModificados.get(i).getSKU());
            assertEquals(listaProdVariantes.get(i).getStock(), listaProdVariantesModificados.get(i).getStock());
            assertEquals(listaProdVariantes.get(i).getStock_minimo(), listaProdVariantesModificados.get(i).getStock_minimo());
            assertEquals(listaProdVariantes.get(i).getAlerta_stock(), listaProdVariantesModificados.get(i).getAlerta_stock());
            assertEquals(ColorDTO.ID_PLATA, listaProdVariantesModificados.get(i).getColor().getColor_id());
            assertEquals(TallaDTO.ID_TREINTA_SEIS, listaProdVariantesModificados.get(i).getTalla().getTalla_id());
            assertEquals(TipoBeneficioDTO.ID_DESCUENTO_FIJO, listaProdVariantesModificados.get(i).getTipo_beneficio().getTipo_beneficio_id());
            assertEquals(listaProdVariantes.get(i).getValor_beneficio(), listaProdVariantesModificados.get(i).getValor_beneficio());
        }
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");

        prepararProductoBase();

        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);

        assertEquals(3, this.prodVarianteDAO.listarTodos().size());

        for (Integer id : listaProdVariantesId) {
            ProductoVarianteDTO variante = this.prodVarianteDAO.obtenerPorId(id);
            assertNotNull(variante);
            Integer resultado = this.prodVarianteDAO.eliminar(variante);
            assertNotEquals(0, resultado);

            ProductoVarianteDTO varianteEliminada = this.prodVarianteDAO.obtenerPorId(id);
            assertNull(varianteEliminada);
        }

        assertEquals(0, this.prodVarianteDAO.listarTodos().size());
    }

    private void limpiarBaseDatos() {
        ArrayList<ProductoVarianteDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
        for (ProductoVarianteDTO variante : listaProdVariantes) {
            this.prodVarianteDAO.eliminar(variante);
        }

        ArrayList<ProductoDTO> listaProductos = this.productoDAO.listarTodos();
        for (ProductoDTO producto : listaProductos) {
            this.productoDAO.eliminar(producto);
        }
    }
}
