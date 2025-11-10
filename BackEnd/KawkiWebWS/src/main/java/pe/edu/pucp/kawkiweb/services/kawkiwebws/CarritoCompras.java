package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.CarritoComprasBO;
import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

@WebService(serviceName = "CarritoCompras")
public class CarritoCompras {

    private CarritoComprasBO carritoComprasBO;

    public CarritoCompras() {
        this.carritoComprasBO = new CarritoComprasBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "usuario") UsuarioDTO usuario,
            @WebParam(name = "total") Double total,
            @WebParam(name = "promocion") PromocionDTO promocion) {

        return this.carritoComprasBO.insertar(usuario, total, promocion);
    }

    @WebMethod(operationName = "obtenerPorId")
    public CarritoComprasDTO obtenerPorId(
            @WebParam(name = "carritoId") Integer carritoId) {
        return this.carritoComprasBO.obtenerPorId(carritoId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<CarritoComprasDTO> listarTodos() {
        return this.carritoComprasBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "carritoId") Integer carritoId,
            @WebParam(name = "usuario") UsuarioDTO usuario,
            @WebParam(name = "total") Double total,
            @WebParam(name = "promocion") PromocionDTO promocion) {

        return this.carritoComprasBO.modificar(carritoId, usuario, total, promocion);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "carritoId") Integer carritoId) {
        return this.carritoComprasBO.eliminar(carritoId);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "CarritoCompras Web " + txt + " !";
    }
}
