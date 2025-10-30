package pe.edu.pucp.kawkiweb.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.kawkiweb.daoImp.Detalle_PedidoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.PedidoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductoVarianteDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.PromocionDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.kawkiweb.model.DetallePedidoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
import pe.edu.pucp.kawkiweb.model.PedidoDTO;
import pe.edu.pucp.kawkiweb.model.ProductoDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
import pe.edu.pucp.kawkiweb.model.utilPedido.EstadoPedidoDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriaDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColorDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstiloDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallaDTO;
import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoCondicionDTO;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuarioDTO;

public class DetallePedidoDAOTest {

    private DetallePedidoDAO detalle_pedidoDAO;
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

    public DetallePedidoDAOTest() {
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
        ProductoDTO producto = new ProductoDTO();
        producto.setDescripcion("Producto prueba");
        
        // Crear CategoriaDTO
        CategoriaDTO categoria = new CategoriaDTO();
        categoria.setCategoria_id(CategoriaDTO.ID_DERBY);
        categoria.setNombre(CategoriaDTO.NOMBRE_DERBY);
        producto.setCategoria(categoria);
        
        // Crear EstiloDTO
        EstiloDTO estilo = new EstiloDTO();
        estilo.setEstilo_id(EstiloDTO.ID_COMBINADOS);
        estilo.setNombre(EstiloDTO.NOMBRE_COMBINADOS);
        producto.setEstilo(estilo);
        
        producto.setPrecio_venta(120.00);
        producto.setFecha_hora_creacion(LocalDateTime.now());
        
        this.productoBaseId = this.productoDAO.insertar(producto);
        assertTrue(this.productoBaseId != 0);
    }

    //productoVariante ejemplo:
    private void prepararProductoVarBase() {
        ProductoVarianteDTO prodVariante = new ProductoVarianteDTO();
        prodVariante.setSKU("DER-ROJ-37");
        prodVariante.setStock(20);
        prodVariante.setStock_minimo(5);
        prodVariante.setAlerta_stock(false);
        prodVariante.setProducto_id(this.productoBaseId);
        
        // Crear ColorDTO
        ColorDTO color = new ColorDTO();
        color.setColor_id(ColorDTO.ID_ROJO);
        color.setNombre(ColorDTO.NOMBRE_ROJO);
        prodVariante.setColor(color);
        
        // Crear TallaDTO
        TallaDTO talla = new TallaDTO();
        talla.setTalla_id(TallaDTO.ID_TREINTA_SIETE);
        talla.setNumero(TallaDTO.NUMERO_TREINTA_SIETE);
        prodVariante.setTalla(talla);
        
        // Crear TipoBeneficioDTO
        TipoBeneficioDTO tipoBeneficio = new TipoBeneficioDTO();
        tipoBeneficio.setTipo_beneficio_id(TipoBeneficioDTO.ID_DESCUENTO_PORCENTAJE);
        tipoBeneficio.setNombre(TipoBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE);
        prodVariante.setTipo_beneficio(tipoBeneficio);
        
        prodVariante.setValor_beneficio(10);
        prodVariante.setFecha_hora_creacion(LocalDateTime.now());
        
        this.prodVarBaseId = this.prodVarDAO.insertar(prodVariante);
        assertTrue(this.prodVarBaseId != 0);
    }

    //usuario ejemplo:
    private void prepararUsuarioBase() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Nestor");
        usuario.setApePaterno("Espinoza");
        usuario.setApeMaterno("Urco");
        usuario.setDni("71926451");
        usuario.setFechaNacimiento(LocalDate.of(2004, 9, 20));
        usuario.setTelefono("992323577");
        usuario.setDireccion("Av. Santa Cruz 321");
        usuario.setCorreo("nestor@test.com");
        usuario.setNombreUsuario("nestor007");
        usuario.setContrasenha("perro");
        usuario.setFechaHoraCreacion(LocalDateTime.now());
        
        // Crear TipoUsuarioDTO
        TipoUsuarioDTO tipoCliente = new TipoUsuarioDTO();
        tipoCliente.setTipoUsuarioId(TipoUsuarioDTO.ID_CLIENTE);
        tipoCliente.setNombre(TipoUsuarioDTO.NOMBRE_CLIENTE);
        usuario.setTipoUsuario(tipoCliente);
        
        this.usuarioBaseId = this.usuarioDAO.insertar(usuario);
        assertTrue(this.usuarioBaseId != 0);
    }

    //promocion ejemplo:
    private void prepararPromocionBase() {
        PromocionDTO promocion = new PromocionDTO();
        promocion.setDescripcion("Promoción Prueba de detalle pedido");
        promocion.setFecha_inicio(LocalDateTime.now());
        promocion.setFecha_fin(LocalDateTime.now().plusMonths(1));
        
        // Crear TipoBeneficioDTO
        TipoBeneficioDTO tipoBeneficio = new TipoBeneficioDTO();
        tipoBeneficio.setTipo_beneficio_id(TipoBeneficioDTO.ID_DESCUENTO_PORCENTAJE);
        tipoBeneficio.setNombre(TipoBeneficioDTO.NOMBRE_DESCUENTO_PORCENTAJE);
        promocion.setTipo_beneficio(tipoBeneficio);
        
        // Crear TipoCondicionDTO
        TipoCondicionDTO tipoCondicion = new TipoCondicionDTO();
        tipoCondicion.setTipo_condicion_id(TipoCondicionDTO.ID_CANT_MIN_PRODUCTOS);
        tipoCondicion.setNombre(TipoCondicionDTO.NOMBRE_CANT_MIN_PRODUCTOS);
        promocion.setTipo_condicion(tipoCondicion);
        
        promocion.setValor_beneficio(15);
        promocion.setValor_condicion(2);
        promocion.setActivo(Boolean.TRUE);
        
        this.promocionBaseId = this.promocionDAO.insertar(promocion);
        assertTrue(this.promocionBaseId != 0);
    }

    //pedido ejemplo:
    private void prepararPedidoBase() {
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
        
        // Crear EstadoPedidoDTO
        EstadoPedidoDTO estadoPendiente = new EstadoPedidoDTO();
        estadoPendiente.setEstado_pedido_id(EstadoPedidoDTO.ID_PENDIENTE);
        estadoPendiente.setNombre(EstadoPedidoDTO.NOMBRE_PENDIENTE);
        pedido.setEstado_pedido(estadoPendiente);
        
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
        DetallePedidoDTO detallepedido = new DetallePedidoDTO();
        detallepedido.setCantidad(1);
        detallepedido.setPedido_id(this.pedidoBaseId);
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
        
        DetallePedidoDTO detallepedido = this.detalle_pedidoDAO.obtenerPorId(listaDetalle_PedidoId.get(0));
        assertEquals(detallepedido.getDetalle_pedido_id(), listaDetalle_PedidoId.get(0));

        eliminarTodo();
    }


    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaDetalle_PedidoId = new ArrayList<>();
        insertarDetallePedidos(listaDetalle_PedidoId);

        ArrayList<DetallePedidoDTO> listaDetalle_Pedidos = this.detalle_pedidoDAO.listarTodos();
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

        ArrayList<DetallePedidoDTO> listaDetalle_Pedidos = this.detalle_pedidoDAO.listarTodos();
        assertEquals(listaDetalle_PedidoId.size(), listaDetalle_Pedidos.size());
        for (Integer i = 0; i < listaDetalle_PedidoId.size(); i++) {
            listaDetalle_Pedidos.get(i).setCantidad(listaDetalle_Pedidos.get(i).getCantidad() + 2);
            listaDetalle_Pedidos.get(i).setSubtotal(329.94);
            this.detalle_pedidoDAO.modificar(listaDetalle_Pedidos.get(i));
        }

        ArrayList<DetallePedidoDTO> listaDetalle_PedidoModificados = this.detalle_pedidoDAO.listarTodos();
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

        ArrayList<DetallePedidoDTO> listaDetalle_Pedido = this.detalle_pedidoDAO.listarTodos();
        for (Integer i = 0; i < listaDetalle_Pedido.size(); i++) {
            Integer resultado = this.detalle_pedidoDAO.eliminar(listaDetalle_Pedido.get(i));
            assertNotEquals(0, resultado);
            DetallePedidoDTO detalle_pedido = this.detalle_pedidoDAO.obtenerPorId(listaDetalle_Pedido.get(i).getDetalle_pedido_id());
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
