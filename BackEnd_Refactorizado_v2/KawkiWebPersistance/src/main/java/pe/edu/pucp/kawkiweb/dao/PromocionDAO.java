package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.PromocionDTO;

public interface PromocionDAO {
    
    public Integer insertar(PromocionDTO promo);

    public PromocionDTO obtenerPorId(Integer promoId);

    public ArrayList<PromocionDTO> listarTodos();

    public Integer modificar(PromocionDTO promo);

    public Integer eliminar(PromocionDTO promo);
    
}
