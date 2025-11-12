//package pe.edu.pucp.kawkiweb.services.kawkiwebws;
//
//import jakarta.jws.WebService;
//import jakarta.jws.WebMethod;
//import jakarta.jws.WebParam;
//import java.util.ArrayList;
//import pe.edu.pucp.kawkiweb.bo.CategoriasBO;
//import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
//
//@WebService(serviceName = "Categorias")
//public class Categorias {
//
//    private CategoriasBO categoriaBO;
//
//    public Categorias() {
//        this.categoriaBO = new CategoriasBO();
//    }
//
//    @WebMethod(operationName = "insertarCategoria")
//    public Integer insertar(
//            @WebParam(name = "nombreCategoria") String nombreCategoria) {
//        return this.categoriaBO.insertar(nombreCategoria);
//    }
//
//    @WebMethod(operationName = "modificarCategoria")
//    public Integer modificar(
//            @WebParam(name = "categoriaId") Integer categoriaId,
//            @WebParam(name = "nombreCategoria") String nombreCategoria) {
//        return this.categoriaBO.modificar(categoriaId, nombreCategoria);
//    }
//
//    @WebMethod(operationName = "obtenerPorIdCategoria")
//    public CategoriasDTO obtenerPorId(
//            @WebParam(name = "categoriaId") Integer categoriaId) {
//        return this.categoriaBO.obtenerPorId(categoriaId);
//    }
//
//    @WebMethod(operationName = "listarTodosCategoria")
//    public ArrayList<CategoriasDTO> listarTodos() {
//        return new ArrayList<>(this.categoriaBO.listarTodos());
//    }
//
//}
