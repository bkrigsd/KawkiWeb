package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.VentasBO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

@WebService(serviceName = "VentasService")
public class Ventas {

    private VentasBO ventaBO;

    public Ventas() {
        this.ventaBO = new VentasBO();
    }

    @WebMethod(operationName = "insertarVenta")
    public Integer insertarVenta(
            @WebParam(name = "usuario") UsuariosDTO usuario,
            @WebParam(name = "total") Double total,
            @WebParam(name = "descuento") DescuentosDTO descuento,
            @WebParam(name = "redSocial") RedesSocialesDTO redSocial) {

        return this.ventaBO.insertar(usuario, total, descuento, redSocial);
    }

    @WebMethod(operationName = "obtenerPorIdVenta")
    public VentasDTO obtenerPorIdVenta(
            @WebParam(name = "ventaId") Integer ventaId) {
        return this.ventaBO.obtenerPorId(ventaId);
    }

    @WebMethod(operationName = "listarTodosVenta")
    public ArrayList<VentasDTO> listarTodosVenta() {
        return new ArrayList<>(this.ventaBO.listarTodos());
    }

    @WebMethod(operationName = "modificarVenta")
    public Integer modificarVenta(
            @WebParam(name = "ventaId") Integer ventaId,
            @WebParam(name = "usuario") UsuariosDTO usuario,
            @WebParam(name = "total") Double total,
            @WebParam(name = "descuento") DescuentosDTO descuento,
            @WebParam(name = "redSocial") RedesSocialesDTO redSocial,
            @WebParam(name = "esValida") Boolean esValida) {

        return this.ventaBO.modificar(ventaId, usuario, total, descuento,
                redSocial, esValida);
    }

    // Método eliminar comentado (igual que en el BO)
//    @WebMethod(operationName = "eliminarVenta")
//    public Integer eliminarVenta(
//            @WebParam(name = "ventaId") Integer ventaId) {
//        return this.ventaBO.eliminar(ventaId);
//    }
}
