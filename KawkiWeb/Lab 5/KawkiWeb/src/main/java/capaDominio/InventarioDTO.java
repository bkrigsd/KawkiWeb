package capaDominio;

public class InventarioDTO {

    //ATRIBUTOS
    private Integer inventario_id;
    private ProductoDTO producto;
    private Integer cantidad; //STOCK

    //CONSTRUCTORES
    public InventarioDTO() {
        this.inventario_id = null;
        this.producto = new ProductoDTO();
        this.cantidad = 0;
    }

    public InventarioDTO(ProductoDTO producto, Integer cantidad) {
        this.inventario_id = null;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public InventarioDTO(InventarioDTO inventario) {
        this.producto = inventario.producto;
        this.cantidad = inventario.cantidad;
    }

    //GETERS Y SETTERS
    public Integer getInventario_id() {
        return inventario_id;
    }

    public void setInventario_id(Integer aInventario_id) {
        inventario_id = aInventario_id;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
