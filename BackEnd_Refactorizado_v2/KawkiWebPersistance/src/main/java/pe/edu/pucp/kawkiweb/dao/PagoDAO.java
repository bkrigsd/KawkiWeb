/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.kawkiweb.dao;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.PagoDTO;


public interface PagoDAO {
    
    public Integer insertar(PagoDTO pago);
    
    public PagoDTO obtenerPorId(Integer pago_id);

    public ArrayList<PagoDTO> listarTodos();

    public Integer modificar(PagoDTO pago);

    public Integer eliminar(PagoDTO pago);
}
