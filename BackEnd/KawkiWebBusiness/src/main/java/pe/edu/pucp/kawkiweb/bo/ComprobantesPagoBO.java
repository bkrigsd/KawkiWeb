package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.ComprobantePagoDAOImpl;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;
import pe.edu.pucp.kawkiweb.dao.ComprobantesPagoDAO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.MetodosPagoDTO;

public class ComprobantesPagoBO {

    // Constante del IGV (18%)
    private static final Double IGV_PORCENTAJE = 0.18;

    private ComprobantesPagoDAO comprobanteDAO;

    public ComprobantesPagoBO() {
        this.comprobanteDAO = new ComprobantePagoDAOImpl();
    }

    public Integer insertar(TiposComprobanteDTO tipo_comprobante,
            String dni_cliente, String nombre_cliente, String ruc_cliente,
            String razon_social_cliente, String direccion_fiscal_cliente,
            String telefono_cliente, Double total, VentasDTO venta,
            MetodosPagoDTO metodoPago) {

        ComprobantesPagoDTO comprobante = new ComprobantesPagoDTO();
        comprobante.setFecha_hora_creacion(LocalDateTime.now());
        comprobante.setTipo_comprobante(tipo_comprobante);

        String numeroSerie = this.comprobanteDAO.obtenerSiguienteNumeroSerie(
                tipo_comprobante.getTipo_comprobante_id()
        );
        comprobante.setNumero_serie(numeroSerie);

        comprobante.setDni_cliente(dni_cliente);
        comprobante.setNombre_cliente(nombre_cliente);
        comprobante.setRuc_cliente(ruc_cliente);
        comprobante.setRazon_social_cliente(razon_social_cliente);
        comprobante.setDireccion_fiscal_cliente(direccion_fiscal_cliente);
        comprobante.setTelefono_cliente(telefono_cliente);
        comprobante.setTotal(total);
        comprobante.setVenta(venta);
        comprobante.setMetodo_pago(metodoPago);

        // Calcular subtotal e IGV automáticamente
        Double subtotal = calcularSubtotal(total);
        Double igv = calcularIGV(total, subtotal);
        comprobante.setSubtotal(subtotal);
        comprobante.setIgv(igv);

        return this.comprobanteDAO.insertar(comprobante);
    }

    public ComprobantesPagoDTO obtenerPorId(Integer comprobante_pago_id) {
        return this.comprobanteDAO.obtenerPorId(comprobante_pago_id);
    }

    public List<ComprobantesPagoDTO> listarTodos() {
        return this.comprobanteDAO.listarTodos();
    }

    public Integer modificar(Integer comprobante_pago_id,
            TiposComprobanteDTO tipo_comprobante,
            String dni_cliente, String nombre_cliente, String ruc_cliente,
            String razon_social_cliente, String direccion_fiscal_cliente,
            String telefono_cliente, Double total, VentasDTO venta,
            MetodosPagoDTO metodoPago) {

        // Obtener el comprobante existente para mantener el número de serie original
        ComprobantesPagoDTO comprobanteExistente = this.comprobanteDAO.obtenerPorId(comprobante_pago_id);

        if (comprobanteExistente == null) {
            System.err.println("No se encontró el comprobante con ID: " + comprobante_pago_id);
            return 0;
        }

        ComprobantesPagoDTO comprobante = new ComprobantesPagoDTO();
        comprobante.setComprobante_pago_id(comprobante_pago_id);
        comprobante.setTipo_comprobante(tipo_comprobante);

        // Mantener el número de serie original (no se modifica)
        comprobante.setNumero_serie(comprobanteExistente.getNumero_serie());

        comprobante.setDni_cliente(dni_cliente);
        comprobante.setNombre_cliente(nombre_cliente);
        comprobante.setRuc_cliente(ruc_cliente);
        comprobante.setRazon_social_cliente(razon_social_cliente);
        comprobante.setDireccion_fiscal_cliente(direccion_fiscal_cliente);
        comprobante.setTelefono_cliente(telefono_cliente);
        comprobante.setTotal(total);

        // Recalcular subtotal e IGV
        Double subtotal = calcularSubtotal(total);
        Double igv = calcularIGV(total, subtotal);
        comprobante.setSubtotal(subtotal);
        comprobante.setIgv(igv);

        comprobante.setVenta(venta);
        comprobante.setMetodo_pago(metodoPago);

        return this.comprobanteDAO.modificar(comprobante);
    }

//    public Integer eliminar(Integer comprobante_pago_id) {
//        ComprobantesPagoDTO comprobante = new ComprobantesPagoDTO();
//        comprobante.setComprobante_pago_id(comprobante_pago_id);
//        return this.comprobanteDAO.eliminar(comprobante);
//    }
    public ComprobantesPagoDTO obtenerPorVentaId(Integer ventaId) {
        if (ventaId == null || ventaId <= 0) {
            return null;
        }
        return this.comprobanteDAO.obtenerPorVentaId(ventaId);
    }

    /**
     * Calcula el subtotal (base imponible) a partir del total. Fórmula:
     * subtotal = total / (1 + IGV)
     */
    private Double calcularSubtotal(Double total) {
        if (total == null || total == 0.0) {
            return 0.0;
        }
        return Math.round((total / (1.0 + IGV_PORCENTAJE)) * 100.0) / 100.0;
    }

    /**
     * Calcula el monto del IGV a partir del total y el subtotal. Fórmula: IGV =
     * total - subtotal
     */
    private Double calcularIGV(Double total, Double subtotal) {
        if (total == null || subtotal == null) {
            return 0.0;
        }
        return Math.round((total - subtotal) * 100.0) / 100.0;
    }
}
