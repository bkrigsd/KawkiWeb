package capaPersistencia;

import capaDominio.PromocionDTO;
import java.util.ArrayList;

public interface PromocionDAO {

    public Integer insertar(PromocionDTO promo);

    public PromocionDTO obtenerPorId(Integer promoId);

    public ArrayList<PromocionDTO> listarTodos();

    public Integer modificar(PromocionDTO promo);

    public Integer eliminar(PromocionDTO promo);

}
