package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.VentasBO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

@WebService(serviceName = "Ventas")
public class Ventas {

    private VentasBO ventaBO;

    public Ventas() {
        this.ventaBO = new VentasBO();
    }

    @WebMethod(operationName = "insertarVenta")
    public Integer insertar(
            @WebParam(name = "usuario") UsuariosDTO usuario,
            @WebParam(name = "total") Double total,
            @WebParam(name = "descuento") DescuentosDTO descuento) {

        return this.ventaBO.insertar(usuario, total, descuento);
    }

    @WebMethod(operationName = "obtenerPorIdVenta")
    public VentasDTO obtenerPorId(
            @WebParam(name = "ventaId") Integer ventaId) {
        return this.ventaBO.obtenerPorId(ventaId);
    }

    @WebMethod(operationName = "listarTodosVenta")
    public ArrayList<VentasDTO> listarTodos() {
        return new ArrayList<>(this.ventaBO.listarTodos());
    }

    @WebMethod(operationName = "modificarVenta")
    public Integer modificar(
            @WebParam(name = "ventaId") Integer ventaId,
            @WebParam(name = "usuario") UsuariosDTO usuario,
            @WebParam(name = "total") Double total,
            @WebParam(name = "descuento") DescuentosDTO descuento) {

        return this.ventaBO.modificar(ventaId, usuario, total, descuento);
    }

    @WebMethod(operationName = "eliminarVenta")
    public Integer eliminar(
            @WebParam(name = "ventaId") Integer ventaId) {
        return this.ventaBO.eliminar(ventaId);
    }
}
