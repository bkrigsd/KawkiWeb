package pe.edu.pucp.kawkiweb.dao;

import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import java.util.ArrayList;

public interface ProductosVariantesDAO {

    public Integer insertar(ProductosVariantesDTO prodVariante);

    public ProductosVariantesDTO obtenerPorId(Integer prodVarianteId);

    public ArrayList<ProductosVariantesDTO> listarTodos();

    ArrayList<ProductosVariantesDTO> listarPorProductoId(Integer productoId);

    public Integer modificar(ProductosVariantesDTO prodVariante);

    public Integer eliminar(ProductosVariantesDTO prodVariante);
}
