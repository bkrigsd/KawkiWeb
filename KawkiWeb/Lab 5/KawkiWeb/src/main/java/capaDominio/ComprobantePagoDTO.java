package capaDominio;

import capaDominio.comprobanteDetalle.TipoComprobante;
import java.time.LocalDateTime;

public class Comprobante_PagoDTO {

    //ATRIBUTOS
    private Integer comprobante_pago_id;
    private Integer pago_id;
    private LocalDateTime fecha_hora_creacion;
    private TipoComprobante tipo_comprobante;
    private String numero_serie;
    private String dni_cliente;
    private String nombre_cliente;
    private String ruc_cliente;
    private String razon_social_cliente;
    private String direccion_fiscal_cliente;
    private String correo_cliente;
    private String telefono_cliente;
    private Double total;

    //CONSTRUCTORES
    public Comprobante_PagoDTO() {
        this.comprobante_pago_id = null;
        this.pago_id = null;
        this.fecha_hora_creacion = null;
        this.tipo_comprobante = null;
        this.numero_serie = null;
        this.dni_cliente = null;
        this.nombre_cliente = null;
        this.ruc_cliente = null;
        this.razon_social_cliente = null;
        this.direccion_fiscal_cliente = null;
        this.correo_cliente = null;
        this.telefono_cliente = null;
        this.total = null;
    }

    public Comprobante_PagoDTO(
            Integer comprobante_pago_id,
            Integer pago_id,
            LocalDateTime fecha_hora_creacion,
            TipoComprobante tipo_comprobante,
            String numero_serie,
            String dni_cliente,
            String nombre_cliente,
            String ruc_cliente,
            String razon_social_cliente,
            String direccion_fiscal_cliente,
            String correo_cliente,
            String telefono_cliente,
            Double total) {

        this.comprobante_pago_id = comprobante_pago_id;
        this.pago_id = pago_id;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.tipo_comprobante = tipo_comprobante;
        this.numero_serie = numero_serie;
        this.dni_cliente = dni_cliente;
        this.nombre_cliente = nombre_cliente;
        this.ruc_cliente = ruc_cliente;
        this.razon_social_cliente = razon_social_cliente;
        this.direccion_fiscal_cliente = direccion_fiscal_cliente;
        this.correo_cliente = correo_cliente;
        this.telefono_cliente = telefono_cliente;
        this.total = total;
    }

    //GETTERS Y SETTERS
    public Integer getComprobante_pago_id() {
        return comprobante_pago_id;
    }

    public void setComprobante_pago_id(Integer comprobante_pago_id) {
        this.comprobante_pago_id = comprobante_pago_id;
    }

    public Integer getPago_id() {
        return pago_id;
    }

    public void setPago_id(Integer pago_id) {
        this.pago_id = pago_id;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public TipoComprobante getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(TipoComprobante tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getRuc_cliente() {
        return ruc_cliente;
    }

    public void setRuc_cliente(String ruc_cliente) {
        this.ruc_cliente = ruc_cliente;
    }

    public String getRazon_social_cliente() {
        return razon_social_cliente;
    }

    public void setRazon_social_cliente(String razon_social_cliente) {
        this.razon_social_cliente = razon_social_cliente;
    }

    public String getDireccion_fiscal_cliente() {
        return direccion_fiscal_cliente;
    }

    public void setDireccion_fiscal_cliente(String direccion_fiscal_cliente) {
        this.direccion_fiscal_cliente = direccion_fiscal_cliente;
    }

    public String getCorreo_cliente() {
        return correo_cliente;
    }

    public void setCorreo_cliente(String correo_cliente) {
        this.correo_cliente = correo_cliente;
    }

    public String getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        this.telefono_cliente = telefono_cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}

