package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.DetalleVentasDAOImpl;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.dao.DetalleVentasDAO;

public class DetalleVentasBO {

    private DetalleVentasDAO detalleVentaDAO;

    public DetalleVentasBO() {
        this.detalleVentaDAO = new DetalleVentasDAOImpl();
    }

    public Integer insertar(ProductosVariantesDTO productoVar, Integer ventaId,
            Integer cantidad, Double precioUnitario, Double subtotal) {

        if (productoVar == null || productoVar.getProd_variante_id() == null) {
            System.err.println("Error: Producto variante no puede ser nulo");
            return 0;
        }
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a 0");
            return 0;
        }

        DetalleVentasDTO detalleVentaDTO = new DetalleVentasDTO();
        detalleVentaDTO.setProdVariante(productoVar);
        detalleVentaDTO.setVenta_id(ventaId);
        detalleVentaDTO.setCantidad(cantidad);
        detalleVentaDTO.setPrecio_unitario(precioUnitario != null ? precioUnitario : 0.0);
        detalleVentaDTO.setSubtotal(subtotal != null ? subtotal : 0.0);

        return this.detalleVentaDAO.insertar(detalleVentaDTO);
    }

    public DetalleVentasDTO obtenerPorId(Integer detalleId) {
        if (detalleId == null || detalleId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return null;
        }
        return this.detalleVentaDAO.obtenerPorId(detalleId);
    }

    public List<DetalleVentasDTO> listarTodos() {
        return this.detalleVentaDAO.listarTodos();
    }

    public List<DetalleVentasDTO> listarPorVentaId(Integer ventaId) {
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return new ArrayList<>();
        }
        return this.detalleVentaDAO.listarPorVentaId(ventaId);
    }

    public Integer modificar(Integer detalleVentaId,
            ProductosVariantesDTO productoVar, Integer ventaId, Integer cantidad,
            Double precioUnitario, Double subtotal) {
        if (detalleVentaId == null || detalleVentaId <= 0) {
            System.err.println("Error: ID de detalle inválido");
            return 0;
        }
        if (productoVar == null || productoVar.getProd_variante_id() == null) {
            System.err.println("Error: Producto variante no puede ser nulo");
            return 0;
        }
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a 0");
            return 0;
        }

        DetalleVentasDTO detalleVentaDTO = new DetalleVentasDTO();
        detalleVentaDTO.setDetalle_venta_id(detalleVentaId);
        detalleVentaDTO.setProdVariante(productoVar);
        detalleVentaDTO.setVenta_id(ventaId);
        detalleVentaDTO.setCantidad(cantidad);
        detalleVentaDTO.setPrecio_unitario(precioUnitario != null ? precioUnitario : 0.0);
        detalleVentaDTO.setSubtotal(subtotal != null ? subtotal : 0.0);

        return this.detalleVentaDAO.modificar(detalleVentaDTO);
    }

//    public Integer eliminar(Integer detalleVentaId) {
//        if (detalleVentaId == null || detalleVentaId <= 0) {
//            System.err.println("Error: ID de detalle inválido");
//            return 0;
//        }
//
//        DetalleVentasDTO detalleVentaDTO = new DetalleVentasDTO();
//        detalleVentaDTO.setDetalle_venta_id(detalleVentaId);
//        return this.detalleVentaDAO.eliminar(detalleVentaDTO);
//    }
}
