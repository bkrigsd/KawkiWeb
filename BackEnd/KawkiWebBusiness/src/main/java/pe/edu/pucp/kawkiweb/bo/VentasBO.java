package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.VentasDAOImpl;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.dao.VentasDAO;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

public class VentasBO {

    private VentasDAO ventaDAO;

    public VentasBO() {
        this.ventaDAO = new VentasDAOImpl();
    }

    public Integer insertar(UsuariosDTO usuario, Double total,
            DescuentosDTO descuento, RedesSocialesDTO redSocial) {

        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Error: Usuario no puede ser nulo");
            return 0;
        }

        if (total == null || total < 0) {
            System.err.println("Error: Total debe ser mayor o igual a 0");
            return 0;
        }

        if (redSocial == null || redSocial.getRedSocialId() == null) {
            System.err.println("Error: Red Social no puede ser nulo");
            return 0;
        }

        VentasDTO ventaDTO = new VentasDTO();
        ventaDTO.setUsuario(usuario);
        ventaDTO.setFecha_hora_creacion(LocalDateTime.now());
        ventaDTO.setTotal(total);
        ventaDTO.setDescuento(descuento);
        ventaDTO.setRedSocial(redSocial);
        ventaDTO.setEsValida(Boolean.TRUE);

        return this.ventaDAO.insertar(ventaDTO);
    }

    public VentasDTO obtenerPorId(Integer ventaId) {
        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return null;
        }
        return this.ventaDAO.obtenerPorId(ventaId);
    }

    public List<VentasDTO> listarTodos() {
        return this.ventaDAO.listarTodos();
    }

    public Integer modificar(Integer ventaId, UsuariosDTO usuario, Double total,
            DescuentosDTO descuento, RedesSocialesDTO redSocial,
            Boolean esValida) {

        if (ventaId == null || ventaId <= 0) {
            System.err.println("Error: ID de pedido inválido");
            return 0;
        }
        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Error: Usuario no puede ser nulo");
            return 0;
        }

        if (total == null || total < 0) {
            System.err.println("Error: Total debe ser mayor o igual a 0");
            return 0;
        }

        if (redSocial == null || redSocial.getRedSocialId() == null) {
            System.err.println("Error: Red Social no puede ser nulo");
            return 0;
        }

        if (esValida == null) {
            System.err.println("Error: esValida no puede ser nulo");
            return 0;
        }

        VentasDTO ventaDTO = new VentasDTO();
        ventaDTO.setVenta_id(ventaId);
        ventaDTO.setUsuario(usuario);
        ventaDTO.setTotal(total);
        ventaDTO.setDescuento(descuento);
        ventaDTO.setRedSocial(redSocial);
        ventaDTO.setEsValida(esValida);

        return this.ventaDAO.modificar(ventaDTO);
    }

//    public Integer eliminar(Integer ventaId) {
//        if (ventaId == null || ventaId <= 0) {
//            System.err.println("Error: ID de pedido inválido");
//            return 0;
//        }
//        
//        VentasDTO pedidoDTO = new VentasDTO();
//        pedidoDTO.setVenta_id(ventaId);
//        return this.ventaDAO.eliminar(pedidoDTO);
//    }
}
