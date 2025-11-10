package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.ProductoVarianteBO;
import pe.edu.pucp.kawkiweb.model.ProductoVarianteDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColorDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallaDTO;
import pe.edu.pucp.kawkiweb.model.utilPromocion.TipoBeneficioDTO;

@WebService(serviceName = "ProductoVariante")
public class ProductoVariante {

    private ProductoVarianteBO productoVarianteBO;

    public ProductoVariante() {
        this.productoVarianteBO = new ProductoVarianteBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "SKU") String SKU,
            @WebParam(name = "stock") Integer stock,
            @WebParam(name = "stock_minimo") Integer stock_minimo,
            @WebParam(name = "alerta_stock") Boolean alerta_stock,
            @WebParam(name = "producto_id") Integer producto_id,
            @WebParam(name = "color") ColorDTO color,
            @WebParam(name = "talla") TallaDTO talla,
            @WebParam(name = "tipo_beneficio") TipoBeneficioDTO tipo_beneficio,
            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
            @WebParam(name = "fecha_hora_creacion") LocalDateTime fecha_hora_creacion) {

        return this.productoVarianteBO.insertar(SKU, stock, stock_minimo, alerta_stock,
                producto_id, color, talla, tipo_beneficio, valor_beneficio,
                fecha_hora_creacion);
    }

    @WebMethod(operationName = "obtenerPorId")
    public ProductoVarianteDTO obtenerPorId(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id) {
        return this.productoVarianteBO.obtenerPorId(prod_variante_id);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<ProductoVarianteDTO> listarTodos() {
        return this.productoVarianteBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id,
            @WebParam(name = "SKU") String SKU,
            @WebParam(name = "stock") Integer stock,
            @WebParam(name = "stock_minimo") Integer stock_minimo,
            @WebParam(name = "alerta_stock") Boolean alerta_stock,
            @WebParam(name = "producto_id") Integer producto_id,
            @WebParam(name = "color") ColorDTO color,
            @WebParam(name = "talla") TallaDTO talla,
            @WebParam(name = "tipo_beneficio") TipoBeneficioDTO tipo_beneficio,
            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
            @WebParam(name = "fecha_hora_creacion") LocalDateTime fecha_hora_creacion) {

        return this.productoVarianteBO.modificar(prod_variante_id, SKU, stock,
                stock_minimo, alerta_stock, producto_id, color, talla, tipo_beneficio,
                valor_beneficio, fecha_hora_creacion);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id) {
        return this.productoVarianteBO.eliminar(prod_variante_id);
    }

    @WebMethod(operationName = "actualizarStock")
    public boolean actualizarStock(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id,
            @WebParam(name = "nuevo_stock") Integer nuevo_stock) {

        return this.productoVarianteBO.actualizarStock(prod_variante_id, nuevo_stock);
    }

    @WebMethod(operationName = "listarConStockBajo")
    public ArrayList<ProductoVarianteDTO> listarConStockBajo() {
        return this.productoVarianteBO.listarConStockBajo();
    }

    @WebMethod(operationName = "listarPorProducto")
    public ArrayList<ProductoVarianteDTO> listarPorProducto(
            @WebParam(name = "producto_id") Integer producto_id) {

        return this.productoVarianteBO.listarPorProducto(producto_id);
    }

    @WebMethod(operationName = "listarPorColor")
    public ArrayList<ProductoVarianteDTO> listarPorColor(
            @WebParam(name = "color_id") Integer color_id) {

        return this.productoVarianteBO.listarPorColor(color_id);
    }

    @WebMethod(operationName = "listarPorTalla")
    public ArrayList<ProductoVarianteDTO> listarPorTalla(
            @WebParam(name = "talla_id") Integer talla_id) {

        return this.productoVarianteBO.listarPorTalla(talla_id);
    }

    @WebMethod(operationName = "tieneStockDisponible")
    public boolean tieneStockDisponible(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id) {

        return this.productoVarianteBO.tieneStockDisponible(prod_variante_id);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "ProductoVariante Web " + txt + " !";
    }
}
