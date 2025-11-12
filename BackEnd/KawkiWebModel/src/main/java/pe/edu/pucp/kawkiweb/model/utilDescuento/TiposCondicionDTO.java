package pe.edu.pucp.kawkiweb.model.utilDescuento;

public class TiposCondicionDTO {

    private Integer tipo_condicion_id;
    private String nombre;

    // Constantes para los IDs de tipos de condición
    public static final int ID_CANT_MIN_PRODUCTOS = 1;
    public static final int ID_MONTO_MIN_COMPRA = 2;

    public static final String NOMBRE_CANT_MIN_PRODUCTOS = "Cantidad mínima de productos";
    public static final String NOMBRE_MONTO_MIN_COMPRA = "Monto mínimo de compra";

    public TiposCondicionDTO() {
        this.tipo_condicion_id = null;
        this.nombre = null;
    }

    public TiposCondicionDTO(Integer tipo_condicion_id, String nombre) {
        this.tipo_condicion_id = tipo_condicion_id;
        this.nombre = nombre;
    }

    public TiposCondicionDTO(TiposCondicionDTO tipoCondicion) {
        this.tipo_condicion_id = tipoCondicion.tipo_condicion_id;
        this.nombre = tipoCondicion.nombre;
    }

    public Integer getTipo_condicion_id() {
        return tipo_condicion_id;
    }

    public void setTipo_condicion_id(Integer tipo_condicion_id) {
        this.tipo_condicion_id = tipo_condicion_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esCantidadMinimaProductos() {
        return this.tipo_condicion_id != null && this.tipo_condicion_id == ID_CANT_MIN_PRODUCTOS;
    }

    public boolean esMontoMinimoCompra() {
        return this.tipo_condicion_id != null && this.tipo_condicion_id == ID_MONTO_MIN_COMPRA;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
