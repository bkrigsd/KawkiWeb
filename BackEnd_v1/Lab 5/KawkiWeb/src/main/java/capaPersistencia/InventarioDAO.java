package capaPersistencia;

import capaDominio.InventarioDTO;

public interface InventarioDAO {

    public void insertarInventario(InventarioDTO inventario);

    public void mostrarInventario(Integer inventario_id);

    public void actualizarInventario(Integer inventario_id, Integer nuevo_stock);

    public void eliminarInventario(Integer inventario_id);
}
