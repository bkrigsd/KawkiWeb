package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.ProductosVariantesBO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;

@WebService(serviceName = "ProductosVariantesService")
public class ProductosVariantes {

    private ProductosVariantesBO productoVarianteBO;

    public ProductosVariantes() {
        this.productoVarianteBO = new ProductosVariantesBO();
    }

    @WebMethod(operationName = "insertarProdVariante")
    public Integer insertarProdVariante(
            @WebParam(name = "SKU") String SKU,
            @WebParam(name = "stock") Integer stock,
            @WebParam(name = "stock_minimo") Integer stock_minimo,
            @WebParam(name = "producto_id") Integer producto_id,
            @WebParam(name = "color") ColoresDTO color,
            @WebParam(name = "talla") TallasDTO talla,
            @WebParam(name = "url_imagen") String url_imagen,
            @WebParam(name = "disponible") Boolean disponible) {

        return this.productoVarianteBO.insertar(SKU, stock, stock_minimo,
                producto_id, color, talla, url_imagen, disponible);
    }

    @WebMethod(operationName = "obtenerPorIdProdVariante")
    public ProductosVariantesDTO obtenerPorIdProdVariante(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id) {
        return this.productoVarianteBO.obtenerPorId(prod_variante_id);
    }

    @WebMethod(operationName = "listarTodosProdVariante")
    public ArrayList<ProductosVariantesDTO> listarTodosProdVariante() {
        return new ArrayList<>(this.productoVarianteBO.listarTodos());
    }

    @WebMethod(operationName = "modificarProdVariante")
    public Integer modificarProdVariante(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id,
            @WebParam(name = "SKU") String SKU,
            @WebParam(name = "stock") Integer stock,
            @WebParam(name = "stock_minimo") Integer stock_minimo,
            @WebParam(name = "producto_id") Integer producto_id,
            @WebParam(name = "color") ColoresDTO color,
            @WebParam(name = "talla") TallasDTO talla,
            @WebParam(name = "url_imagen") String url_imagen,
            @WebParam(name = "disponible") Boolean disponible) {

        return this.productoVarianteBO.modificar(prod_variante_id, SKU, stock,
                stock_minimo, producto_id, color, talla, url_imagen, disponible);
    }

    @WebMethod(operationName = "eliminarProdVariante")
    public Integer eliminarProdVariante(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id) {
        return this.productoVarianteBO.eliminar(prod_variante_id);
    }

    @WebMethod(operationName = "actualizarStockProdVariante")
    public boolean actualizarStockProdVariante(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id,
            @WebParam(name = "nuevo_stock") Integer nuevo_stock) {

        return this.productoVarianteBO.actualizarStock(prod_variante_id, nuevo_stock);
    }

    @WebMethod(operationName = "listarConStockBajoProdVariante")
    public ArrayList<ProductosVariantesDTO> listarConStockBajoProdVariante() {
        return new ArrayList<>(this.productoVarianteBO.listarConStockBajo());
    }

    @WebMethod(operationName = "listarPorProductoProdVariante")
    public ArrayList<ProductosVariantesDTO> listarPorProductoProdVariante(
            @WebParam(name = "producto_id") Integer producto_id) {

        return new ArrayList<>(this.productoVarianteBO.listarPorProducto(producto_id));
    }

    @WebMethod(operationName = "listarPorColorProdVariante")
    public ArrayList<ProductosVariantesDTO> listarPorColorProdVariante(
            @WebParam(name = "color_id") Integer color_id) {

        return new ArrayList<>(this.productoVarianteBO.listarPorColor(color_id));
    }

    @WebMethod(operationName = "listarPorTallaProdVariante")
    public ArrayList<ProductosVariantesDTO> listarPorTallaProdVariante(
            @WebParam(name = "talla_id") Integer talla_id) {

        return new ArrayList<>(this.productoVarianteBO.listarPorTalla(talla_id));
    }

    @WebMethod(operationName = "tieneStockDisponibleProdVariante")
    public boolean tieneStockDisponibleProdVariante(
            @WebParam(name = "prod_variante_id") Integer prod_variante_id) {

        return this.productoVarianteBO.tieneStockDisponible(prod_variante_id);
    }

}
