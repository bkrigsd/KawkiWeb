package pe.edu.pucp.kawkiweb.model;

import java.time.LocalDateTime;
import java.util.List;

//import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
//import pe.edu.pucp.kawkiweb.model.adapter.LocalDateTimeAdapter;
//
//@XmlRootElement(name = "venta")
public class VentasDTO {

    //ATRIBUTOS:
    private Integer venta_id;
    private UsuariosDTO usuario;
//    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fecha_hora_creacion;
    private Double total;
    private DescuentosDTO descuento;
    private List<DetalleVentasDTO> detalles;

    //CONSTRUCTORES:
    public VentasDTO() {
        this.venta_id = null;
        this.usuario = null;
        this.fecha_hora_creacion = LocalDateTime.now();
        this.total = null;
        this.descuento = null;
        this.detalles = null;
    }

    public VentasDTO(Integer venta_id, UsuariosDTO usuario, Double total,
            DescuentosDTO descuento, List<DetalleVentasDTO> detalles) {

        this.venta_id = venta_id;
        this.usuario = usuario;
        this.fecha_hora_creacion = LocalDateTime.now();
        this.total = total;
        this.descuento = descuento;
        this.detalles = detalles;
    }

    public VentasDTO(VentasDTO venta) {

        this.venta_id = venta.venta_id;
        this.usuario = venta.usuario;
        this.fecha_hora_creacion = venta.fecha_hora_creacion;
        this.total = venta.total;
        this.descuento = venta.descuento;
        this.detalles = venta.detalles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID de venta: ").append(this.venta_id).append('\n');
        sb.append("Usuario: ").append(this.usuario).append('\n');
        sb.append("Fecha y hora de creación: ").append(this.fecha_hora_creacion).append('\n');
        sb.append("Total: S/ ").append(this.total).append('\n');

        if (this.descuento != null) {
            sb.append("Descuento aplicado: ").append(this.descuento).append('\n');
        } else {
            sb.append("Descuento aplicado: Ninguno").append('\n');
        }

        return sb.toString();
    }

    public Integer getVenta_id() {
        return venta_id;
    }

    public void setVenta_id(Integer pedido_id) {
        this.venta_id = pedido_id;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public DescuentosDTO getDescuento() {
        return descuento;
    }

    public void setDescuento(DescuentosDTO descuento) {
        this.descuento = descuento;
    }

    public List<DetalleVentasDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentasDTO> detalles) {
        this.detalles = detalles;
    }

}
