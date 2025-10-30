package pe.edu.pucp.kawkiweb.bo;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.CarritoComprasDAO;
import pe.edu.pucp.kawkiweb.daoImp.CarritoComprasDAOImpl;
import pe.edu.pucp.kawkiweb.model.CarritoComprasDTO;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;
import pe.edu.pucp.kawkiweb.model.UsuarioDTO;

public class CarritoComprasBO {

    private CarritoComprasDAO carritoDAO;

    public CarritoComprasBO() {
        this.carritoDAO = new CarritoComprasDAOImpl();
    }

    /**
     * Inserta un nuevo carrito de compras
     *
     * @param usuario Usuario dueño del carrito
     * @param total Total del carrito
     * @param promocion Promoción aplicada (puede ser null)
     * @return ID del carrito insertado o 0 si falla
     */
    public Integer insertar(UsuarioDTO usuario, Double total, 
            PromocionDTO promocion) {

        CarritoComprasDTO carritoDTO = new CarritoComprasDTO();
        carritoDTO.setUsuario(usuario);
        carritoDTO.setTotal(total != null ? total : 0.0);
        carritoDTO.setPromocion(promocion);
        return this.carritoDAO.insertar(carritoDTO);
    }

    /**
     * Obtiene un carrito por su ID (incluye detalles, usuario y promoción)
     *
     * @param carritoId ID del carrito
     * @return CarritoComprasDTO con toda la información o null si no existe
     */
    public CarritoComprasDTO obtenerPorId(Integer carritoId) {
        if (carritoId == null || carritoId <= 0) {
            System.err.println("Error: ID de carrito inválido");
            return null;
        }
        return this.carritoDAO.obtenerPorId(carritoId);
    }

    /**
     * Lista todos los carritos (con sus detalles, usuarios y promociones)
     *
     * @return Lista de todos los carritos
     */
    public ArrayList<CarritoComprasDTO> listarTodos() {
        return this.carritoDAO.listarTodos();
    }

    /**
     * Modifica un carrito existente
     *
     * @param carritoId ID del carrito a modificar
     * @param usuario Usuario dueño del carrito
     * @param total Total actualizado
     * @param promocion Promoción aplicada (puede ser null)
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer modificar(Integer carritoId, UsuarioDTO usuario,
            Double total, PromocionDTO promocion) {
        if (carritoId == null || carritoId <= 0) {
            System.err.println("Error: ID de carrito inválido");
            return 0;
        }

        CarritoComprasDTO carritoDTO = new CarritoComprasDTO();
        carritoDTO.setCarrito_id(carritoId);
        carritoDTO.setUsuario(usuario);
        carritoDTO.setTotal(total != null ? total : 0.0);
        carritoDTO.setPromocion(promocion);
        return this.carritoDAO.modificar(carritoDTO);
    }

    /**
     * Elimina un carrito por su ID
     *
     * @param carritoId ID del carrito a eliminar
     * @return Número de filas afectadas (1 si tuvo éxito, 0 si falló)
     */
    public Integer eliminar(Integer carritoId) {
        if (carritoId == null || carritoId <= 0) {
            System.err.println("Error: ID de carrito inválido");
            return 0;
        }

        CarritoComprasDTO carritoDTO = new CarritoComprasDTO();
        carritoDTO.setCarrito_id(carritoId);
        return this.carritoDAO.eliminar(carritoDTO);
    }
}
