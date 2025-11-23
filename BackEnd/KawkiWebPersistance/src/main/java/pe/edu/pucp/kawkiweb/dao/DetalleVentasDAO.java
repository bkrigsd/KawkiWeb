package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;

public interface DetalleVentasDAO {

    public Integer insertar(DetalleVentasDTO detalleVenta);

    public DetalleVentasDTO obtenerPorId(Integer detalleVentaId);

    public ArrayList<DetalleVentasDTO> listarTodos();

    ArrayList<DetalleVentasDTO> listarPorVentaId(Integer ventaId);

    public Integer modificar(DetalleVentasDTO detalleVenta);

    public Integer eliminar(DetalleVentasDTO detalleVenta);

}
