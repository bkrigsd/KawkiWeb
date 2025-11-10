package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.ComprobantePagoDAO;
import pe.edu.pucp.kawkiweb.daoImp.ComprobantePagoDAOImpl;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;
import pe.edu.pucp.kawkiweb.model.utilPago.TiposComprobanteDTO;

public class ComprobantePagoBO {

    private ComprobantePagoDAO comprobanteDAO;

    public ComprobantePagoBO() {
        this.comprobanteDAO = new ComprobantePagoDAOImpl();
    }

    public Integer insertar(Integer pago_id,LocalDateTime fecha_hora_creacion,TiposComprobanteDTO tipo_comprobante,String numero_serie,
                            String dni_cliente,String nombre_cliente,String ruc_cliente,String razon_social_cliente,String direccion_fiscal_cliente,
                            String correo_cliente,String telefono_cliente,Double total) {
        ComprobantesPagoDTO comprobante = new ComprobantesPagoDTO();
        comprobante.setPago_id(pago_id);
        comprobante.setFecha_hora_creacion(fecha_hora_creacion);
        comprobante.setTipo_comprobante(tipo_comprobante);
        comprobante.setNumero_serie(numero_serie);
        comprobante.setDni_cliente(dni_cliente);
        comprobante.setNombre_cliente(nombre_cliente);
        comprobante.setRuc_cliente(ruc_cliente);
        comprobante.setRazon_social_cliente(razon_social_cliente);
        comprobante.setDireccion_fiscal_cliente(direccion_fiscal_cliente);
        comprobante.setCorreo_cliente(correo_cliente);
        comprobante.setTelefono_cliente(telefono_cliente);
        comprobante.setTotal(total);

        return this.comprobanteDAO.insertar(comprobante);
    }

    public ComprobantesPagoDTO obtenerPorId(Integer comprobante_pago_id) {
        return this.comprobanteDAO.obtenerPorId(comprobante_pago_id);
    }

    public ArrayList<ComprobantesPagoDTO> listarTodos() {
        return this.comprobanteDAO.listarTodos();
    }

    public Integer modificar(Integer comprobante_pago_id,Integer pago_id,LocalDateTime fecha_hora_creacion,TiposComprobanteDTO tipo_comprobante,String numero_serie,
                            String dni_cliente,String nombre_cliente,String ruc_cliente,String razon_social_cliente,String direccion_fiscal_cliente,
                            String correo_cliente,String telefono_cliente,Double total) {

        ComprobantesPagoDTO comprobante = new ComprobantesPagoDTO();
        comprobante.setComprobante_pago_id(comprobante_pago_id);
        comprobante.setPago_id(pago_id);
        comprobante.setFecha_hora_creacion(fecha_hora_creacion);
        comprobante.setTipo_comprobante(tipo_comprobante);
        comprobante.setNumero_serie(numero_serie);
        comprobante.setDni_cliente(dni_cliente);
        comprobante.setNombre_cliente(nombre_cliente);
        comprobante.setRuc_cliente(ruc_cliente);
        comprobante.setRazon_social_cliente(razon_social_cliente);
        comprobante.setDireccion_fiscal_cliente(direccion_fiscal_cliente);
        comprobante.setCorreo_cliente(correo_cliente);
        comprobante.setTelefono_cliente(telefono_cliente);
        comprobante.setTotal(total);

        return this.comprobanteDAO.modificar(comprobante);
    }

    public Integer eliminar(Integer comprobante_pago_id) {
        ComprobantesPagoDTO comprobante = new ComprobantesPagoDTO();
        comprobante.setComprobante_pago_id(comprobante_pago_id);
        return this.comprobanteDAO.eliminar(comprobante);
    }
}

