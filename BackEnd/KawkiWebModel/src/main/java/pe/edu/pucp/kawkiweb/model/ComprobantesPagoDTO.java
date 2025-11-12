package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pe.edu.pucp.kawkiweb.model.adapter.LocalDateTimeAdapter;

@XmlRootElement(name = "ComprobantePago")
public class ComprobantesPagoDTO {

    private static Integer contadorSerieFactura = 1;
    private static Integer contadorSerieBoleta = 1;

    //ATRIBUTOS
    private Integer comprobante_pago_id;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fecha_hora_creacion;

    private TiposComprobanteDTO tipo_comprobante;
    private String numero_serie;
    private String dni_cliente;
    private String nombre_cliente;
    private String ruc_cliente;
    private String razon_social_cliente;
    private String direccion_fiscal_cliente;
    private String telefono_cliente;
    private Double total;
    private VentasDTO venta;
    private MetodosPagoDTO metodo_pago;

    //CONSTRUCTORES
    public ComprobantesPagoDTO() {

        this.comprobante_pago_id = null;
        this.fecha_hora_creacion = LocalDateTime.now();
        this.tipo_comprobante = null;
        this.numero_serie = null;
        this.dni_cliente = null;
        this.nombre_cliente = null;
        this.ruc_cliente = null;
        this.razon_social_cliente = null;
        this.direccion_fiscal_cliente = null;
        this.telefono_cliente = null;
        this.total = null;
        this.venta = null;
        this.metodo_pago = null;
    }

    public ComprobantesPagoDTO(
            Integer comprobante_pago_id, Integer pago_id,
            TiposComprobanteDTO tipo_comprobante, String numero_serie,
            String dni_cliente, String nombre_cliente, String ruc_cliente,
            String razon_social_cliente, String direccion_fiscal_cliente,
            String telefono_cliente, Double total, VentasDTO venta,
            MetodosPagoDTO metodo_pago) {

        this.comprobante_pago_id = comprobante_pago_id;
        this.fecha_hora_creacion = LocalDateTime.now();
        this.tipo_comprobante = tipo_comprobante;
        generarNumeroSerie();
        this.dni_cliente = dni_cliente;
        this.nombre_cliente = nombre_cliente;
        this.ruc_cliente = ruc_cliente;
        this.razon_social_cliente = razon_social_cliente;
        this.direccion_fiscal_cliente = direccion_fiscal_cliente;
        this.telefono_cliente = telefono_cliente;
        this.total = total;
        this.venta = venta;
        this.metodo_pago = metodo_pago;
    }

    public ComprobantesPagoDTO(ComprobantesPagoDTO comprobante) {

        this.comprobante_pago_id = comprobante.comprobante_pago_id;
        this.fecha_hora_creacion = comprobante.fecha_hora_creacion;
        this.tipo_comprobante = comprobante.tipo_comprobante;
        this.numero_serie = comprobante.numero_serie;
        this.dni_cliente = comprobante.dni_cliente;
        this.nombre_cliente = comprobante.nombre_cliente;
        this.ruc_cliente = comprobante.ruc_cliente;
        this.razon_social_cliente = comprobante.razon_social_cliente;
        this.direccion_fiscal_cliente = comprobante.direccion_fiscal_cliente;
        this.telefono_cliente = comprobante.telefono_cliente;
        this.total = comprobante.total;
        this.venta = comprobante.venta;
        this.metodo_pago = comprobante.metodo_pago;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comprobante de Pago Nº: ").append(numero_serie).append("\n");
        sb.append("Tipo: ").append(tipo_comprobante != null ? tipo_comprobante : "N/A").append("\n");
        sb.append("DNI: ").append(dni_cliente != null ? dni_cliente : "N/A").append("\n");
        sb.append("Nombre: ").append(nombre_cliente != null ? nombre_cliente : "N/A").append("\n");
        sb.append("RUC: ").append(ruc_cliente != null ? ruc_cliente : "N/A").append("\n");
        sb.append("Razón Social: ").append(razon_social_cliente != null ? razon_social_cliente : "N/A").append("\n");
        sb.append("Dirección: ").append(direccion_fiscal_cliente != null ? direccion_fiscal_cliente : "N/A").append("\n");
        sb.append("Teléfono: ").append(telefono_cliente != null ? telefono_cliente : "N/A").append("\n");
        sb.append("Total: S/ ").append(total != null ? String.format("%.2f", total) : "0.00").append("\n");
        sb.append("Método de Pago: ").append(metodo_pago != null ? metodo_pago : "N/A");

        return sb.toString();
    }

    private void generarNumeroSerie() {
        String prefijoSerie;
        String numeroCorrelativo;

        if (this.tipo_comprobante == null) {
            this.numero_serie = "SIN-TIPO";
            return;
        }

        if (this.tipo_comprobante.esFactura()) {
            prefijoSerie = "F001";
            numeroCorrelativo = String.format("%08d", contadorSerieFactura++);
        } else if (this.tipo_comprobante.esBoletaSimple() || this.tipo_comprobante.esBoletaConDNI()) {
            prefijoSerie = "B001";
            numeroCorrelativo = String.format("%08d", contadorSerieBoleta++);
        } else {
            prefijoSerie = "C001"; // por si en el futuro hay otro tipo
            numeroCorrelativo = String.format("%08d", 1);
        }

        this.numero_serie = prefijoSerie + "-" + numeroCorrelativo;
    }

    //GETTERS Y SETTERS
    public Integer getComprobante_pago_id() {
        return comprobante_pago_id;
    }

    public void setComprobante_pago_id(Integer comprobante_pago_id) {
        this.comprobante_pago_id = comprobante_pago_id;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public TiposComprobanteDTO getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(TiposComprobanteDTO tipo_comprobante) {
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

    public VentasDTO getVenta() {
        return venta;
    }

    public void setVenta(VentasDTO venta) {
        this.venta = venta;
    }

    public MetodosPagoDTO getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(MetodosPagoDTO metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public static Integer getContadorSerieFactura() {
        return contadorSerieFactura;
    }

    public static Integer getContadorSerieBoleta() {
        return contadorSerieBoleta;
    }

    public static void setContadorSerieFactura(Integer aContadorSerieFactura) {
        contadorSerieFactura = aContadorSerieFactura;
    }

    public static void setContadorSerieBoleta(Integer aContadorSerieBoleta) {
        contadorSerieBoleta = aContadorSerieBoleta;
    }

}
