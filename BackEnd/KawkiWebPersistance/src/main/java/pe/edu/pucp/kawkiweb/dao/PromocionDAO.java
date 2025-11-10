package pe.edu.pucp.kawkiweb.dao;

import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;

public interface PromocionDAO {
    
    public Integer insertar(DescuentosDTO promo);

    public DescuentosDTO obtenerPorId(Integer promoId);

    public ArrayList<DescuentosDTO> listarTodos();

    public Integer modificar(DescuentosDTO promo);

    public Integer eliminar(DescuentosDTO promo);
    
}
