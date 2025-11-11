package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.ProductosBO;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;

@WebService(serviceName = "Producto")
public class Producto {

    private ProductosBO productoBO;

    public Producto() {
        this.productoBO = new ProductosBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "categoria") CategoriasDTO categoria,
            @WebParam(name = "estilo") EstilosDTO estilo,
            @WebParam(name = "precio_venta") Double precio_venta,
            @WebParam(name = "fecha_hora_creacion") LocalDateTime fecha_hora_creacion) {

        return this.productoBO.insertar(descripcion, categoria, estilo,
                precio_venta, fecha_hora_creacion);
    }

    @WebMethod(operationName = "obtenerPorId")
    public ProductosDTO obtenerPorId(
            @WebParam(name = "producto_id") Integer producto_id) {
        return this.productoBO.obtenerPorId(producto_id);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<ProductosDTO> listarTodos() {
        return this.productoBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "producto_id") Integer producto_id,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "categoria") CategoriasDTO categoria,
            @WebParam(name = "estilo") EstilosDTO estilo,
            @WebParam(name = "precio_venta") Double precio_venta,
            @WebParam(name = "fecha_hora_creacion") LocalDateTime fecha_hora_creacion) {

        return this.productoBO.modificar(producto_id, descripcion, categoria,
                estilo, precio_venta, fecha_hora_creacion);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "producto_id") Integer producto_id) {
        return this.productoBO.eliminar(producto_id);
    }

    @WebMethod(operationName = "tieneStockDisponible")
    public boolean tieneStockDisponible(
            @WebParam(name = "producto_id") Integer producto_id) {

        return this.productoBO.tieneStockDisponible(producto_id);
    }

    @WebMethod(operationName = "calcularStockTotal")
    public Integer calcularStockTotal(
            @WebParam(name = "producto_id") Integer producto_id) {

        return this.productoBO.calcularStockTotal(producto_id);
    }

    @WebMethod(operationName = "listarPorCategoria")
    public ArrayList<ProductosDTO> listarPorCategoria(
            @WebParam(name = "categoria_id") Integer categoria_id) {

        return this.productoBO.listarPorCategoria(categoria_id);
    }

    @WebMethod(operationName = "listarPorEstilo")
    public ArrayList<ProductosDTO> listarPorEstilo(
            @WebParam(name = "estilo_id") Integer estilo_id) {

        return this.productoBO.listarPorEstilo(estilo_id);
    }

    @WebMethod(operationName = "listarConStockBajo")
    public ArrayList<ProductosDTO> listarConStockBajo() {
        return this.productoBO.listarConStockBajo();
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Producto Web " + txt + " !";
    }
}
