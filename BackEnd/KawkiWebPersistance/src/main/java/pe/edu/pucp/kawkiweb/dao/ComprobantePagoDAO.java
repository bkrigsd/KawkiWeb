/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.ComprobantesPagoDTO;

public interface ComprobantePagoDAO {

    public Integer insertar(ComprobantesPagoDTO comprobante_pago);

    public ComprobantesPagoDTO obtenerPorId(Integer comprobante_pago_id);

    public ComprobantesPagoDTO obtenerPorPagoId(Integer pago_id);

    public ArrayList<ComprobantesPagoDTO> listarTodos();

    public Integer modificar(ComprobantesPagoDTO comprobante_pago);

    public Integer eliminar(ComprobantesPagoDTO comprobante_pago);

}
