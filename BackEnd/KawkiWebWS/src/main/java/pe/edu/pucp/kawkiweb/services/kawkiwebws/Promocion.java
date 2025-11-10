package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.bo.PromocionBO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

@WebService(serviceName = "Promocion")
public class Promocion {

    private PromocionBO promocionBO;

    public Promocion() {
        this.promocionBO = new PromocionBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "tipo_condicion") TiposCondicionDTO tipo_condicion,
            @WebParam(name = "valor_condicion") Integer valor_condicion,
            @WebParam(name = "tipo_beneficio") TiposBeneficioDTO tipo_beneficio,
            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
            @WebParam(name = "fecha_inicio") LocalDateTime fecha_inicio,
            @WebParam(name = "fecha_fin") LocalDateTime fecha_fin,
            @WebParam(name = "activo") Boolean activo) {

        return this.promocionBO.insertar(descripcion, tipo_condicion, valor_condicion,
                tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin, activo);
    }

    @WebMethod(operationName = "obtenerPorId")
    public DescuentosDTO obtenerPorId(@WebParam(name = "promocionId") Integer promocionId) {
        return this.promocionBO.obtenerPorId(promocionId);
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<DescuentosDTO> listarTodos() {
        return this.promocionBO.listarTodos();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "promoId") Integer promoId,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "tipo_condicion") TiposCondicionDTO tipo_condicion,
            @WebParam(name = "valor_condicion") Integer valor_condicion,
            @WebParam(name = "tipo_beneficio") TiposBeneficioDTO tipo_beneficio,
            @WebParam(name = "valor_beneficio") Integer valor_beneficio,
            @WebParam(name = "fecha_inicio") LocalDateTime fecha_inicio,
            @WebParam(name = "fecha_fin") LocalDateTime fecha_fin,
            @WebParam(name = "activo") Boolean activo) {

        return this.promocionBO.modificar(promoId, descripcion, tipo_condicion,
                valor_condicion, tipo_beneficio, valor_beneficio, fecha_inicio,
                fecha_fin, activo);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "promoId") Integer promoId) {
        return this.promocionBO.eliminar(promoId);
    }

    @WebMethod(operationName = "activar")
    public boolean activar(@WebParam(name = "promoId") Integer promoId) {
        return this.promocionBO.activar(promoId);
    }

    @WebMethod(operationName = "desactivar")
    public boolean desactivar(@WebParam(name = "promoId") Integer promoId) {
        return this.promocionBO.desactivar(promoId);
    }

    @WebMethod(operationName = "listarActivas")
    public ArrayList<DescuentosDTO> listarActivas() {
        return this.promocionBO.listarActivas();
    }

    @WebMethod(operationName = "listarVigentes")
    public ArrayList<DescuentosDTO> listarVigentes() {
        return this.promocionBO.listarVigentes();
    }

    @WebMethod(operationName = "esAplicable")
    public boolean esAplicable(
            @WebParam(name = "promoId") Integer promoId,
            @WebParam(name = "cantidadProductos") Integer cantidadProductos,
            @WebParam(name = "montoTotal") Double montoTotal) {

        return this.promocionBO.esAplicable(promoId, cantidadProductos, montoTotal);
    }

    @WebMethod(operationName = "calcularDescuento")
    public Double calcularDescuento(
            @WebParam(name = "promoId") Integer promoId,
            @WebParam(name = "montoTotal") Double montoTotal) {

        return this.promocionBO.calcularDescuento(promoId, montoTotal);
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Promocion Web " + txt + " !";
    }
}
