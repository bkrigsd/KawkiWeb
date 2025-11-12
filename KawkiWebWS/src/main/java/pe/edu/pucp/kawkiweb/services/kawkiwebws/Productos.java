//package pe.edu.pucp.kawkiweb.services.kawkiwebws;
//
//import jakarta.jws.WebService;
//import jakarta.jws.WebMethod;
//import jakarta.jws.WebParam;
//import java.util.ArrayList;
//import pe.edu.pucp.kawkiweb.bo.ProductosBO;
//import pe.edu.pucp.kawkiweb.model.ProductosDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
//
//@WebService(serviceName = "Productos")
//public class Productos {
//
//    private ProductosBO productoBO;
//
//    public Productos() {
//        this.productoBO = new ProductosBO();
//    }
//
//    @WebMethod(operationName = "insertarProducto")
//    public Integer insertar(
//            @WebParam(name = "descripcion") String descripcion,
//            @WebParam(name = "categoria") CategoriasDTO categoria,
//            @WebParam(name = "estilo") EstilosDTO estilo,
//            @WebParam(name = "precio_venta") Double precio_venta) {
//
//        return this.productoBO.insertar(descripcion, categoria, estilo, precio_venta);
//    }
//
//    @WebMethod(operationName = "obtenerPorIdProducto")
//    public ProductosDTO obtenerPorId(
//            @WebParam(name = "producto_id") Integer producto_id) {
//        return this.productoBO.obtenerPorId(producto_id);
//    }
//
//    @WebMethod(operationName = "listarTodosProducto")
//    public ArrayList<ProductosDTO> listarTodos() {
//        return new ArrayList<>(this.productoBO.listarTodos());
//    }
//
//    @WebMethod(operationName = "modificarProducto")
//    public Integer modificar(
//            @WebParam(name = "producto_id") Integer producto_id,
//            @WebParam(name = "descripcion") String descripcion,
//            @WebParam(name = "categoria") CategoriasDTO categoria,
//            @WebParam(name = "estilo") EstilosDTO estilo,
//            @WebParam(name = "precio_venta") Double precio_venta) {
//
//        return this.productoBO.modificar(producto_id, descripcion, categoria,
//                estilo, precio_venta);
//    }
//
//    @WebMethod(operationName = "eliminarProducto")
//    public Integer eliminar(@WebParam(name = "producto_id") Integer producto_id) {
//        return this.productoBO.eliminar(producto_id);
//    }
//
//    @WebMethod(operationName = "tieneStockDisponibleProducto")
//    public boolean tieneStockDisponible(
//            @WebParam(name = "producto_id") Integer producto_id) {
//
//        return this.productoBO.tieneStockDisponible(producto_id);
//    }
//
//    @WebMethod(operationName = "calcularStockTotalProducto")
//    public Integer calcularStockTotal(
//            @WebParam(name = "producto_id") Integer producto_id) {
//
//        return this.productoBO.calcularStockTotal(producto_id);
//    }
//
//    @WebMethod(operationName = "listarPorCategoriaProducto")
//    public ArrayList<ProductosDTO> listarPorCategoria(
//            @WebParam(name = "categoria_id") Integer categoria_id) {
//
//        return new ArrayList<>(this.productoBO.listarPorCategoria(categoria_id));
//    }
//
//    @WebMethod(operationName = "listarPorEstiloProducto")
//    public ArrayList<ProductosDTO> listarPorEstilo(
//            @WebParam(name = "estilo_id") Integer estilo_id) {
//
//        return new ArrayList<>(this.productoBO.listarPorEstilo(estilo_id));
//    }
//
//    @WebMethod(operationName = "listarConStockBajoProducto")
//    public ArrayList<ProductosDTO> listarConStockBajo() {
//        return new ArrayList<>(this.productoBO.listarConStockBajo());
//    }
//}
