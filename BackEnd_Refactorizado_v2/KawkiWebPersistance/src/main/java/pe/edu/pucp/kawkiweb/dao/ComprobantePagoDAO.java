/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.Comprobante_PagoDTO;

public interface ComprobantePagoDAO {

    public Integer insertar(Comprobante_PagoDTO comprobante_pago);

    public Comprobante_PagoDTO obtenerPorId(Integer comprobante_pago_id);

    public Comprobante_PagoDTO obtenerPorPagoId(Integer pago_id);

    public ArrayList<Comprobante_PagoDTO> listarTodos();

    public Integer modificar(Comprobante_PagoDTO comprobante_pago);

    public Integer eliminar(Comprobante_PagoDTO comprobante_pago);

}
