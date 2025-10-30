package capaPersistencia;

import capaDominio.ProductoDTO;
import capaDominio.ProductoVarianteDTO;
import capaDominio.promocionDetalle.TipoBeneficio;
import capaDominio.productoDetalle.Categoria;
import capaDominio.productoDetalle.Color;
import capaDominio.productoDetalle.Estilo;
import capaDominio.productoDetalle.Talla;
import capaPersistencia.Implementar.ProductoDAOImpl;
import capaPersistencia.Implementar.ProductoVarianteDAOImlp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoVarianteDAOTest {

    private ProductoVarianteDAO prodVarianteDAO;
    private ProductoDAO productoDAO;
    private Integer productoBaseId;

    public ProductoVarianteDAOTest() {
        this.prodVarianteDAO = new ProductoVarianteDAOImlp();
        this.productoDAO=new ProductoDAOImpl();
    }
    
    private void prepararProductoBase() {
        // Insertamos un producto de prueba para asociar variantes
        ProductoDTO producto = new ProductoDTO();
        producto.setDescripcion("Producto Base para Variantes");
        producto.setCategoria(Categoria.OXFORD);
        producto.setEstilo(Estilo.CLASICOS);
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
        eliminarTodo();
    }

    private void insertarProdVariantes(ArrayList<Integer> listaProdVariantesId) {
        ProductoVarianteDTO prodVariante;

        // 1ra variante
        prodVariante = new ProductoVarianteDTO();
        prodVariante.setSKU("OXF-ROJ-37");
        prodVariante.setStock(20);
        prodVariante.setStock_minimo(5);
        prodVariante.setAlerta_stock(false);
        prodVariante.setProducto_id(this.productoBaseId); // <- referencia a un producto existente en BD
        prodVariante.setColor(Color.ROJO);
        prodVariante.setTalla(Talla.TREINTA_SIETE);
        prodVariante.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
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
        prodVariante.setColor(Color.CREMA);
        prodVariante.setTalla(Talla.TREINTA_OCHO);
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
        prodVariante.setColor(Color.BLANCO);
        prodVariante.setTalla(Talla.TREINTA_CINCO);
        prodVariante.setTipo_beneficio(TipoBeneficio.DESCUENTO_FIJO);
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

        prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(1));
        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(1));

        prodVariante = this.prodVarianteDAO.obtenerPorId(listaProdVariantesId.get(2));
        assertEquals(prodVariante.getProd_variante_id(), listaProdVariantesId.get(2));

        eliminarTodo();
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
        }
        eliminarTodo();
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        
        prepararProductoBase();
        
        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);

        ArrayList<ProductoVarianteDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
        assertEquals(listaProdVariantesId.size(), listaProdVariantes.size());
        for (Integer i = 0; i < listaProdVariantesId.size(); i++) {
            listaProdVariantes.get(i).setSKU("SKU-MOD" + i.toString());
            listaProdVariantes.get(i).setStock(50);
            listaProdVariantes.get(i).setStock_minimo(10);
            listaProdVariantes.get(i).setAlerta_stock(false);
            listaProdVariantes.get(i).setColor(Color.PLATA);
            listaProdVariantes.get(i).setTalla(Talla.TREINTA_SEIS);
            listaProdVariantes.get(i).setTipo_beneficio(TipoBeneficio.DESCUENTO_FIJO);
            listaProdVariantes.get(i).setValor_beneficio(35);
            listaProdVariantes.get(i).setFecha_hora_creacion(LocalDateTime.now());
            this.prodVarianteDAO.modificar(listaProdVariantes.get(i));
        }

        ArrayList<ProductoVarianteDTO> listaProdVariantesModificados = this.prodVarianteDAO.listarTodos();
        assertEquals(listaProdVariantes.size(), listaProdVariantesId.size());
        for (Integer i = 0; i < listaProdVariantes.size(); i++) {
            assertEquals(listaProdVariantes.get(i).getSKU(), listaProdVariantesModificados.get(i).getSKU());
            assertEquals(listaProdVariantes.get(i).getStock(), listaProdVariantesModificados.get(i).getStock());
            assertEquals(listaProdVariantes.get(i).getStock_minimo(), listaProdVariantesModificados.get(i).getStock_minimo());
            assertEquals(listaProdVariantes.get(i).getAlerta_stock(), listaProdVariantesModificados.get(i).getAlerta_stock());
            assertEquals(listaProdVariantes.get(i).getColor(), listaProdVariantesModificados.get(i).getColor());
            assertEquals(listaProdVariantes.get(i).getTalla(), listaProdVariantesModificados.get(i).getTalla());
            assertEquals(listaProdVariantes.get(i).getTipo_beneficio(), listaProdVariantesModificados.get(i).getTipo_beneficio());
            assertEquals(listaProdVariantes.get(i).getValor_beneficio(), listaProdVariantesModificados.get(i).getValor_beneficio());
            // No valido la fecha por posibles diferencias de segundos (que es normal)
        }
        eliminarTodo();
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        
        prepararProductoBase();
        
        ArrayList<Integer> listaProdVariantesId = new ArrayList<>();
        insertarProdVariantes(listaProdVariantesId);
        eliminarTodo();
    }

    private void eliminarTodo() {
        ArrayList<ProductoVarianteDTO> listaProdVariantes = this.prodVarianteDAO.listarTodos();
        for (Integer i = 0; i < listaProdVariantes.size(); i++) {
            Integer resultado = this.prodVarianteDAO.eliminar(listaProdVariantes.get(i));
            assertNotEquals(0, resultado);
            ProductoVarianteDTO almacen = this.prodVarianteDAO.obtenerPorId(listaProdVariantes.get(i).getProd_variante_id());
            assertNull(almacen);
        }
        
        ArrayList<ProductoDTO> listaProductos = this.productoDAO.listarTodos();
        for (ProductoDTO producto : listaProductos) {
            this.productoDAO.eliminar(producto);
        }
    }
}
