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

    public ArrayList<ProductosVariantesDTO> listarPorColor(Integer colorId);

    public ArrayList<ProductosVariantesDTO> listarPorTalla(Integer tallaId);

    public ArrayList<ProductosVariantesDTO> listarConStockBajo();

    public boolean existeVariante(Integer productoId, Integer colorId, Integer tallaId);

    boolean existeVarianteParaModificar(Integer varianteId, Integer productoId, 
                                         Integer colorId, Integer tallaId);
}
