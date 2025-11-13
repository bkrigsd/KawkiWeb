package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import pe.edu.pucp.kawkiweb.daoImp.MovimientosInventarioDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;

public class MovimientosInventarioDAOTest {

    private MovimientosInventarioDAO movInventarioDAO;
    private ProductosVariantesDAO prodVarDAO;
    private ProductosDAO productoDAO;
    private UsuariosDAO usuarioDAO;

    private ProductosDTO productoDTO;
    private ProductosVariantesDTO prodVarianteDTO;
    private UsuariosDTO usuarioDTO;

    private Integer prodBaseId;

    public MovimientosInventarioDAOTest() {
        this.movInventarioDAO = new MovimientosInventarioDAOImpl();
        this.productoDAO = new ProductosDAOImpl();
        this.prodVarDAO = new ProductosVariantesDAOImpl();
        this.usuarioDAO = new UsuariosDAOImpl();
    }

    @BeforeEach
    void prepararContexto() {
        eliminarTodo();
        prepararProductoBase();
        prepararProductoVarBase();
        prepararUsuarioBase();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Limpiando datos después del test...");
        eliminarTodo();
    }

    private void prepararProductoBase() {
        this.productoDTO = new ProductosDTO();
        this.productoDTO.setDescripcion("Zapato Derby de cuero genuino");
        CategoriasDTO categoria = new CategoriasDTO();
        categoria.setCategoria_id(CategoriasDTO.ID_DERBY);
        categoria.setNombre(CategoriasDTO.NOMBRE_DERBY);
        this.productoDTO.setCategoria(categoria);
        EstilosDTO estilo = new EstilosDTO();
        estilo.setEstilo_id(EstilosDTO.ID_CHAROL);
        estilo.setNombre(EstilosDTO.NOMBRE_CHAROL);
        this.productoDTO.setEstilo(estilo);
        this.productoDTO.setPrecio_venta(120.00);
        this.productoDTO.setFecha_hora_creacion(LocalDateTime.now());

        this.prodBaseId = this.productoDAO.insertar(this.productoDTO);
        this.productoDTO.setProducto_id(this.prodBaseId);
        assertTrue(this.prodBaseId != 0);
    }

    private void prepararProductoVarBase() {
        this.prodVarianteDTO = new ProductosVariantesDTO();
        this.prodVarianteDTO.setSKU("SKU" + System.currentTimeMillis());
        this.prodVarianteDTO.setStock(50);
        this.prodVarianteDTO.setStock_minimo(10);
        this.prodVarianteDTO.setAlerta_stock(false);
        this.prodVarianteDTO.setProducto_id(this.prodBaseId);
        ColoresDTO color = new ColoresDTO();
        color.setColor_id(ColoresDTO.ID_ACERO);
        color.setNombre(ColoresDTO.NOMBRE_ACERO);
        this.prodVarianteDTO.setColor(color);
        TallasDTO talla = new TallasDTO();
        talla.setTalla_id(TallasDTO.ID_TREINTA_CINCO);
        talla.setNumero(TallasDTO.NUMERO_TREINTA_CINCO);
        this.prodVarianteDTO.setTalla(talla);
        this.prodVarianteDTO.setFecha_hora_creacion(LocalDateTime.now());
        this.prodVarianteDTO.setDisponible(Boolean.TRUE);

        Integer resultado = this.prodVarDAO.insertar(this.prodVarianteDTO);
        this.prodVarianteDTO.setProd_variante_id(resultado);
        assertTrue(resultado != 0);
    }

    private void prepararUsuarioBase() {
        this.usuarioDTO = new UsuariosDTO();
        this.usuarioDTO.setNombre("Eros");
        this.usuarioDTO.setApePaterno("Sotelo");
        this.usuarioDTO.setDni("77722211");
        this.usuarioDTO.setTelefono("999111555");
        this.usuarioDTO.setCorreo("eros.sotelo@gmail.com");
        this.usuarioDTO.setNombreUsuario("erosotelo");
        this.usuarioDTO.setContrasenha("password123");
        this.usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
        this.usuarioDTO.setTipoUsuario(new TiposUsuarioDTO(
                TiposUsuarioDTO.ID_VENDEDOR, TiposUsuarioDTO.NOMBRE_VENDEDOR));

        Integer resultado = this.usuarioDAO.insertar(this.usuarioDTO);
        this.usuarioDTO.setUsuarioId(resultado);
        assertTrue(resultado != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        ArrayList<Integer> listaMovInventarioId = new ArrayList<>();
        insertarMovimientosInventario(listaMovInventarioId);
    }

    private void insertarMovimientosInventario(ArrayList<Integer> listaMovInventarioId) {
        MovimientosInventarioDTO movInventario;

        movInventario = new MovimientosInventarioDTO();
        movInventario.setCantidad(10);
        movInventario.setFecha_hora_mov(LocalDateTime.now());
        movInventario.setObservacion("Abastecimiento de inventario inicial");
        movInventario.setTipo_movimiento(new TiposMovimientoDTO(
                TiposMovimientoDTO.ID_INGRESO,
                TiposMovimientoDTO.NOMBRE_INGRESO));
        movInventario.setProd_variante(this.prodVarianteDTO);
        movInventario.setUsuario(this.usuarioDTO);
        Integer resultado = this.movInventarioDAO.insertar(movInventario);
        assertTrue(resultado != 0);
        listaMovInventarioId.add(resultado);

        movInventario = new MovimientosInventarioDTO();
        movInventario.setCantidad(5);
        movInventario.setFecha_hora_mov(LocalDateTime.now());
        movInventario.setObservacion("Salida por venta al cliente");
        movInventario.setTipo_movimiento(new TiposMovimientoDTO(
                TiposMovimientoDTO.ID_SALIDA,
                TiposMovimientoDTO.NOMBRE_SALIDA));
        movInventario.setProd_variante(this.prodVarianteDTO);
        movInventario.setUsuario(this.usuarioDTO);
        resultado = this.movInventarioDAO.insertar(movInventario);
        assertTrue(resultado != 0);
        listaMovInventarioId.add(resultado);

        movInventario = new MovimientosInventarioDTO();
        movInventario.setCantidad(3);
        movInventario.setFecha_hora_mov(LocalDateTime.now());
        movInventario.setObservacion("Ajuste por inventario físico");
        movInventario.setTipo_movimiento(new TiposMovimientoDTO(
                TiposMovimientoDTO.ID_AJUSTE,
                TiposMovimientoDTO.NOMBRE_AJUSTE));
        movInventario.setProd_variante(this.prodVarianteDTO);
        movInventario.setUsuario(this.usuarioDTO);
        resultado = this.movInventarioDAO.insertar(movInventario);
        assertTrue(resultado != 0);
        listaMovInventarioId.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        ArrayList<Integer> listaMovInventarioId = new ArrayList<>();
        insertarMovimientosInventario(listaMovInventarioId);

        MovimientosInventarioDTO movInventario = this.movInventarioDAO.obtenerPorId(listaMovInventarioId.get(0));
        assertEquals(movInventario.getMov_inventario_id(), listaMovInventarioId.get(0));

        movInventario = this.movInventarioDAO.obtenerPorId(listaMovInventarioId.get(1));
        assertEquals(movInventario.getMov_inventario_id(), listaMovInventarioId.get(1));

        movInventario = this.movInventarioDAO.obtenerPorId(listaMovInventarioId.get(2));
        assertEquals(movInventario.getMov_inventario_id(), listaMovInventarioId.get(2));
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaMovInventarioId = new ArrayList<>();
        insertarMovimientosInventario(listaMovInventarioId);

        ArrayList<MovimientosInventarioDTO> listaMovInventarios = this.movInventarioDAO.listarTodos();
        assertEquals(listaMovInventarioId.size(), listaMovInventarios.size());
        for (Integer i = 0; i < listaMovInventarioId.size(); i++) {
            assertEquals(listaMovInventarioId.get(i), listaMovInventarios.get(i).getMov_inventario_id());
        }
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        ArrayList<Integer> listaMovInventarioId = new ArrayList<>();
        insertarMovimientosInventario(listaMovInventarioId);

        ArrayList<MovimientosInventarioDTO> listaMovInventarios = this.movInventarioDAO.listarTodos();
        assertEquals(listaMovInventarioId.size(), listaMovInventarios.size());
        for (Integer i = 0; i < listaMovInventarioId.size(); i++) {
            listaMovInventarios.get(i).setCantidad(listaMovInventarios.get(i).getCantidad() + 10);
            listaMovInventarios.get(i).setObservacion("Observación modificada en test");
            this.movInventarioDAO.modificar(listaMovInventarios.get(i));
        }

        ArrayList<MovimientosInventarioDTO> listaMovInventariosModificados = this.movInventarioDAO.listarTodos();
        assertEquals(listaMovInventarios.size(), listaMovInventariosModificados.size());
        for (Integer i = 0; i < listaMovInventarios.size(); i++) {
            assertEquals(listaMovInventarios.get(i).getCantidad(), listaMovInventariosModificados.get(i).getCantidad());
            assertEquals(listaMovInventarios.get(i).getObservacion(), listaMovInventariosModificados.get(i).getObservacion());
        }
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        ArrayList<Integer> listaMovInventarioId = new ArrayList<>();
        insertarMovimientosInventario(listaMovInventarioId);

        eliminarTodo();
    }

    private void eliminarTodo() {

        ArrayList<MovimientosInventarioDTO> listaMovInventarios = this.movInventarioDAO.listarTodos();
        for (Integer i = 0; i < listaMovInventarios.size(); i++) {
            Integer resultado = this.movInventarioDAO.eliminar(listaMovInventarios.get(i));
            assertNotEquals(0, resultado);
            MovimientosInventarioDTO movInventario = this.movInventarioDAO.obtenerPorId(listaMovInventarios.get(i).getMov_inventario_id());
            assertNull(movInventario);
        }

        ArrayList<ProductosVariantesDTO> listarProductoVar = this.prodVarDAO.listarTodos();
        for (ProductosVariantesDTO productoVar : listarProductoVar) {
            this.prodVarDAO.eliminar(productoVar);
        }

        ArrayList<ProductosDTO> listarProducto = this.productoDAO.listarTodos();
        for (ProductosDTO producto : listarProducto) {
            this.productoDAO.eliminar(producto);
        }

        ArrayList<UsuariosDTO> listarUsuarios = this.usuarioDAO.listarTodos();
        for (UsuariosDTO usuario : listarUsuarios) {
            this.usuarioDAO.eliminar(usuario);
        }
    }

}
