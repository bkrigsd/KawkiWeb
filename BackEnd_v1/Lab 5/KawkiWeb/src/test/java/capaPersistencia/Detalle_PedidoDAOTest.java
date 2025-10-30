package capaPersistencia;

import capaDominio.Detalle_PedidoDTO;
import capaDominio.PedidoDTO;
import capaDominio.ProductoDTO;
import capaDominio.ProductoVarianteDTO;
import capaDominio.PromocionDTO;
import capaDominio.UsuarioDTO;
import capaDominio.pedidoDetalle.Estado_Pedido;
import capaDominio.productoDetalle.Categoria;
import capaDominio.productoDetalle.Color;
import capaDominio.productoDetalle.Estilo;
import capaDominio.productoDetalle.Talla;
import capaDominio.promocionDetalle.TipoBeneficio;
import capaDominio.promocionDetalle.TipoCondicion;
import capaDominio.usuarioDetalle.TipoUsuario;
import capaPersistencia.Implementar.Detalle_PedidoDAOImpl;
import capaPersistencia.Implementar.PedidoDAOImpl;
import capaPersistencia.Implementar.ProductoDAOImpl;
import capaPersistencia.Implementar.ProductoVarianteDAOImpl;
import capaPersistencia.Implementar.PromocionDAOImpl;
import capaPersistencia.Implementar.UsuarioDAOImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class Detalle_PedidoDAOTest {

    private Detalle_PedidoDAO detalle_pedidoDAO;
    private ProductoVarianteDAO prodVarDAO;
    private ProductoDAO productoDAO;
    private PedidoDAO pedidoDAO;
    private Integer prodVarBaseId;
    private Integer pedidoBaseId;
    private Integer productoBaseId;
    private UsuarioDAO usuarioDAO;
    private PromocionDAO promocionDAO;
    private Integer usuarioBaseId;
    private Integer promocionBaseId;

    public Detalle_PedidoDAOTest() {
        this.detalle_pedidoDAO = new Detalle_PedidoDAOImpl();
        this.pedidoDAO = new PedidoDAOImpl();
        this.productoDAO = new ProductoDAOImpl();
        this.prodVarDAO = new ProductoVarianteDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.promocionDAO = new PromocionDAOImpl();
    }

    @BeforeEach
    void prepararContexto() {
        eliminarTodo(); 
        prepararProductoBase();
        prepararProductoVarBase();
        prepararUsuarioBase();
        prepararPromocionBase();
        prepararPedidoBase();
    }

    //producto ejemplo:
    private void prepararProductoBase() {
        //Insertamos un producto de prueba para asociar variantes
        ProductoDTO producto = new ProductoDTO();
        producto.setDescripcion("Producto prueba");
        producto.setCategoria(Categoria.DERBY);
        producto.setEstilo(Estilo.COMBINADOS);
        producto.setPrecio_venta(120.00);
        producto.setFecha_hora_creacion(LocalDateTime.now());
        this.productoBaseId = this.productoDAO.insertar(producto);
        assertTrue(this.productoBaseId != 0);
    }

    //productoVariante ejemplo:
    private void prepararProductoVarBase() {
        //Insertamos un productoVariante de prueba para asociar variantes
        ProductoVarianteDTO prodVariante = new ProductoVarianteDTO();
        prodVariante.setSKU("DER-ROJ-37");
        prodVariante.setStock(20);
        prodVariante.setStock_minimo(5);
        prodVariante.setAlerta_stock(false);
        prodVariante.setProducto_id(this.productoBaseId); // <- referencia a un producto existente en BD
        prodVariante.setColor(Color.ROJO);
        prodVariante.setTalla(Talla.TREINTA_SIETE);
        prodVariante.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
        prodVariante.setValor_beneficio(10);
        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
        this.prodVarBaseId = this.prodVarDAO.insertar(prodVariante);
        assertTrue(this.prodVarBaseId != 0);
    }

    //usuario ejemplo:
    private void prepararUsuarioBase() {
        //Insertamos un usuario de prueba para asociar variantes
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Nestor");
        usuario.setApePaterno("Espinoza");
        usuario.setApeMaterno("Urco");
        usuario.setDni("71926451");
        usuario.setFechaNacimiento(LocalDate.of(2004, 9, 20));
        usuario.setTelefono("992323577");
        usuario.setDireccion("Av. Santa Cruz 321");
        usuario.setCorreo("enstor@test.com");
        usuario.setNombreUsuario("nestor007");
        usuario.setContrasenha("perro");
        usuario.setFechaHoraCreacion(LocalDateTime.now());
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
        assertTrue(this.usuarioBaseId != 0);
    }

    //promocion ejemplo:
    private void prepararPromocionBase() {
        //Insertamos una promoción de prueba para asociar variantes
        PromocionDTO promocion = new PromocionDTO();
        promocion.setDescripcion("Promoción Prueba de detalle pedido");
        promocion.setFecha_inicio(LocalDateTime.now());
        promocion.setFecha_fin(LocalDateTime.now().plusMonths(1));
        promocion.setTipo_beneficio(TipoBeneficio.DESCUENTO_PORCENTAJE);
        promocion.setTipo_condicion(TipoCondicion.CANT_MIN_PRODUCTOS);
        promocion.setValor_beneficio(15);
        promocion.setValor_condicion(2); //2 productos minimos
        promocion.setActivo(Boolean.TRUE);
        this.promocionBaseId = this.promocionDAO.insertar(promocion);
        assertTrue(this.promocionBaseId != 0);
    }

    //pedido ejemplo:
    private void prepararPedidoBase() {
        //Insertamos un pedido de prueba para asociar variantes
        PedidoDTO pedido = new PedidoDTO();
        pedido.setTotal(120.00);
        pedido.setFecha_hora_creacion(LocalDateTime.now());
        pedido.setFecha_hora_ultimo_estado(LocalDateTime.now());
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(this.usuarioBaseId);
        pedido.setUsuario(usuario);
        PromocionDTO promo = new PromocionDTO();
        promo.setPromocion_id(this.promocionBaseId);
        pedido.setPromocion(promo);
        pedido.setEstado_pedido(Estado_Pedido.PENDIENTE);
        this.pedidoBaseId = this.pedidoDAO.insertar(pedido);
        assertTrue(this.pedidoBaseId != 0);
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
        insertarDetallePedidos(listaDetalle_PedidoId);
        eliminarTodo();
    }

    private void insertarDetallePedidos(ArrayList<Integer> listaDetalle_PedidoId) {
        Detalle_PedidoDTO detallepedido;

        detallepedido = new Detalle_PedidoDTO();
        detallepedido.setCantidad(1);
        PedidoDTO pedido = new PedidoDTO();
        pedido.setPedido_id(this.pedidoBaseId);
        detallepedido.setPedido(pedido);
        detallepedido.setPrecio_unitario(109.98);
        ProductoVarianteDTO productoVar = new ProductoVarianteDTO();
        productoVar.setProd_variante_id(this.prodVarBaseId);
        detallepedido.setProductoVar(productoVar);
        detallepedido.setSubtotal(109.98);
        Integer resultado = this.detalle_pedidoDAO.insertar(detallepedido);
        assertTrue(resultado != 0);
        listaDetalle_PedidoId.add(resultado);

    }

    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
        insertarDetallePedidos(listaDetalle_PedidoId);
        Detalle_PedidoDTO detallepedido = this.detalle_pedidoDAO.obtenerPorId(listaDetalle_PedidoId.get(0));
        assertEquals(detallepedido.getDetalle_pedido_id(), listaDetalle_PedidoId.get(0));

        eliminarTodo();
    }

    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
        insertarDetallePedidos(listaDetalle_PedidoId);

        ArrayList<Detalle_PedidoDTO> listaDetalle_Pedidos = this.detalle_pedidoDAO.listarTodos();
        assertEquals(listaDetalle_PedidoId.size(), listaDetalle_Pedidos.size());
        for (Integer i = 0; i < listaDetalle_PedidoId.size(); i++) {
            assertEquals(listaDetalle_PedidoId.get(i), listaDetalle_Pedidos.get(i).getDetalle_pedido_id());
        }
        eliminarTodo();
    }

    @Test
    public void testModificar() {
        System.out.println("modificar");
        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
        insertarDetallePedidos(listaDetalle_PedidoId);

        ArrayList<Detalle_PedidoDTO> listaDetalle_Pedidos = this.detalle_pedidoDAO.listarTodos();
        assertEquals(listaDetalle_PedidoId.size(), listaDetalle_Pedidos.size());
        for (Integer i = 0; i < listaDetalle_PedidoId.size(); i++) {
            listaDetalle_Pedidos.get(i).setCantidad(listaDetalle_Pedidos.get(i).getCantidad() + 2);
            listaDetalle_Pedidos.get(i).setSubtotal(329.94);
            this.detalle_pedidoDAO.modificar(listaDetalle_Pedidos.get(i));
        }

        ArrayList<Detalle_PedidoDTO> listaDetalle_PedidoModificados = this.detalle_pedidoDAO.listarTodos();
        assertEquals(listaDetalle_Pedidos.size(), listaDetalle_PedidoModificados.size());
        for (Integer i = 0; i < listaDetalle_Pedidos.size(); i++) {
            assertEquals(listaDetalle_Pedidos.get(i).getCantidad(), listaDetalle_PedidoModificados.get(i).getCantidad());
            assertEquals(listaDetalle_Pedidos.get(i).getSubtotal(), listaDetalle_PedidoModificados.get(i).getSubtotal(), 0.0001); //margen de error
        }
        eliminarTodo();
    }

    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
        insertarDetallePedidos(listaDetalle_PedidoId);
        eliminarTodo();
    }

    private void eliminarTodo() {

        ArrayList<Detalle_PedidoDTO> listaDetalle_Pedido = this.detalle_pedidoDAO.listarTodos();
        for (Integer i = 0; i < listaDetalle_Pedido.size(); i++) {
            Integer resultado = this.detalle_pedidoDAO.eliminar(listaDetalle_Pedido.get(i));
            assertNotEquals(0, resultado);
            Detalle_PedidoDTO detalle_pedido = this.detalle_pedidoDAO.obtenerPorId(listaDetalle_Pedido.get(i).getDetalle_pedido_id());
            assertNull(detalle_pedido);
        }

        ArrayList<PedidoDTO> listarPedidos = this.pedidoDAO.listarTodos();
        for (PedidoDTO pedido : listarPedidos) {
            this.pedidoDAO.eliminar(pedido);
        }

        ArrayList<UsuarioDTO> listarUsuarios = this.usuarioDAO.listarTodos();
        for (UsuarioDTO usuario : listarUsuarios) {
            this.usuarioDAO.eliminar(usuario);
        }

        ArrayList<PromocionDTO> listarPromocion = this.promocionDAO.listarTodos();
        for (PromocionDTO promocion : listarPromocion) {
            this.promocionDAO.eliminar(promocion);
        }

        ArrayList<ProductoVarianteDTO> listarProductoVar = this.prodVarDAO.listarTodos();
        for (ProductoVarianteDTO productoVar : listarProductoVar) {
            this.prodVarDAO.eliminar(productoVar);
        }

        ArrayList<ProductoDTO> listarProducto = this.productoDAO.listarTodos();
        for (ProductoDTO producto : listarProducto) {
            this.productoDAO.eliminar(producto);
        }
    }

}
