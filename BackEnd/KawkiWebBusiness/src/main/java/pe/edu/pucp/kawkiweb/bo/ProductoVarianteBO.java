package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.daoImp.ProductoDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductoVarianteDAOImpl;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.dao.ProductosDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;

public class ProductoVarianteBO {

    private ProductosVariantesDAO prodVarianteDAO;
    private ProductosDAO productoDAO;

    public ProductoVarianteBO() {
        this.prodVarianteDAO = new ProductoVarianteDAOImpl();
        this.productoDAO = new ProductoDAOImpl();
    }

    /**
     * Inserta una nueva variante de producto
     *
     * @return ID de la variante insertada, o null si hubo error
     */
    public Integer insertar(String SKU, Integer stock, Integer stock_minimo,
            Boolean alerta_stock, Integer producto_id, ColoresDTO color, TallasDTO talla,
            TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio,
            LocalDateTime fecha_hora_creacion) {

        try {
            // Validaciones
            if (!validarDatosVariante(SKU, stock, stock_minimo, producto_id, color, talla)) {
                System.err.println("Error: Datos de variante inválidos");
                return null;
            }

            // Validar que el producto existe
            ProductosDTO producto = this.productoDAO.obtenerPorId(producto_id);
            if (producto == null) {
                System.err.println("Error: El producto con ID " + producto_id + " no existe");
                return null;
            }

            // Validar combinación única de producto-color-talla
            if (existeVariante(producto_id, color.getColor_id(), talla.getTalla_id())) {
                System.err.println("Error: Ya existe una variante con este color y talla para este producto");
                return null;
            }

            // Validar beneficio si está presente
            if (!validarBeneficio(tipo_beneficio, valor_beneficio)) {
                return null;
            }

            ProductosVariantesDTO prodVarianteDTO = new ProductosVariantesDTO();
            prodVarianteDTO.setSKU(SKU);
            prodVarianteDTO.setStock(stock);
            prodVarianteDTO.setStock_minimo(stock_minimo);

            // Calcular automáticamente alerta_stock
            prodVarianteDTO.setAlerta_stock(stock <= stock_minimo);

            prodVarianteDTO.setProducto_id(producto_id);
            prodVarianteDTO.setColor(color);
            prodVarianteDTO.setTalla(talla);
            prodVarianteDTO.setTipo_beneficio(tipo_beneficio);
            prodVarianteDTO.setValor_beneficio(valor_beneficio);
            prodVarianteDTO.setFecha_hora_creacion(
                    fecha_hora_creacion != null ? fecha_hora_creacion : LocalDateTime.now()
            );

            return this.prodVarianteDAO.insertar(prodVarianteDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar variante de producto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene una variante de producto por su ID
     *
     * @param prod_variante_id ID de la variante a buscar
     * @return ProductosVariantesDTO encontrado, o null si no existe o hay error
     */
    public ProductosVariantesDTO obtenerPorId(Integer prod_variante_id) {
        try {
            if (prod_variante_id == null || prod_variante_id <= 0) {
                System.err.println("Error: ID de variante inválido");
                return null;
            }
            return this.prodVarianteDAO.obtenerPorId(prod_variante_id);

        } catch (Exception e) {
            System.err.println("Error al obtener variante por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todas las variantes de producto
     *
     * @return Lista de variantes, o lista vacía si hay error
     */
    public ArrayList<ProductosVariantesDTO> listarTodos() {
        try {
            ArrayList<ProductosVariantesDTO> lista = this.prodVarianteDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar variantes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Modifica una variante de producto existente
     *
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer modificar(Integer prod_variante_id, String SKU, Integer stock,
            Integer stock_minimo, Boolean alerta_stock, Integer producto_id,
            ColoresDTO color, TallasDTO talla, TiposBeneficioDTO tipo_beneficio,
            Integer valor_beneficio, LocalDateTime fecha_hora_creacion) {

        try {
            // Validar ID
            if (prod_variante_id == null || prod_variante_id <= 0) {
                System.err.println("Error: ID de variante inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosVariante(SKU, stock, stock_minimo, producto_id, color, talla)) {
                System.err.println("Error: Datos de variante inválidos");
                return null;
            }

            // Validar beneficio si está presente
            if (!validarBeneficio(tipo_beneficio, valor_beneficio)) {
                return null;
            }

            ProductosVariantesDTO prodVarianteDTO = new ProductosVariantesDTO();
            prodVarianteDTO.setProd_variante_id(prod_variante_id);
            prodVarianteDTO.setSKU(SKU);
            prodVarianteDTO.setStock(stock);
            prodVarianteDTO.setStock_minimo(stock_minimo);

            // Recalcular alerta_stock
            prodVarianteDTO.setAlerta_stock(stock <= stock_minimo);

            prodVarianteDTO.setProducto_id(producto_id);
            prodVarianteDTO.setColor(color);
            prodVarianteDTO.setTalla(talla);
            prodVarianteDTO.setTipo_beneficio(tipo_beneficio);
            prodVarianteDTO.setValor_beneficio(valor_beneficio);
            prodVarianteDTO.setFecha_hora_creacion(fecha_hora_creacion);

            return this.prodVarianteDAO.modificar(prodVarianteDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar variante: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina una variante de producto por su ID
     *
     * @param prod_variante_id ID de la variante a eliminar
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer eliminar(Integer prod_variante_id) {
        try {
            if (prod_variante_id == null || prod_variante_id <= 0) {
                System.err.println("Error: ID de variante inválido");
                return null;
            }

            ProductosVariantesDTO prodVarianteDTO = new ProductosVariantesDTO();
            prodVarianteDTO.setProd_variante_id(prod_variante_id);
            return this.prodVarianteDAO.eliminar(prodVarianteDTO);

        } catch (Exception e) {
            System.err.println("Error al eliminar variante: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valida los datos básicos de una variante de producto
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosVariante(String SKU, Integer stock, Integer stock_minimo,
            Integer producto_id, ColoresDTO color, TallasDTO talla) {

        // Validar SKU
        if (SKU == null || SKU.trim().isEmpty()) {
            System.err.println("Validación: El SKU no puede estar vacío");
            return false;
        }

        if (SKU.trim().length() > 50) {
            System.err.println("Validación: El SKU es demasiado largo (máx. 50 caracteres)");
            return false;
        }

        // Validar stock
        if (stock == null || stock < 0) {
            System.err.println("Validación: El stock no puede ser negativo");
            return false;
        }

        // Validar stock mínimo
        if (stock_minimo == null || stock_minimo < 0) {
            System.err.println("Validación: El stock mínimo no puede ser negativo");
            return false;
        }

        // Validar producto_id
        if (producto_id == null || producto_id <= 0) {
            System.err.println("Validación: El ID de producto es inválido");
            return false;
        }

        // Validar color
        if (color == null || color.getColor_id() == null) {
            System.err.println("Validación: El color no puede ser null");
            return false;
        }

        // Validar talla
        if (talla == null || talla.getTalla_id() == null) {
            System.err.println("Validación: La talla no puede ser null");
            return false;
        }

        return true;
    }

    /**
     * Valida que el beneficio tenga datos consistentes
     *
     * @return true si es válido, false en caso contrario
     */
    private boolean validarBeneficio(TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio) {
        // Si hay tipo de beneficio, debe haber valor
        if (tipo_beneficio != null && tipo_beneficio.getTipo_beneficio_id() != null) {
            if (valor_beneficio == null || valor_beneficio <= 0) {
                System.err.println("Validación: Si hay tipo de beneficio, debe haber un valor válido");
                return false;
            }
        }

        // Si hay valor de beneficio, debe haber tipo
        if (valor_beneficio != null && valor_beneficio > 0) {
            if (tipo_beneficio == null || tipo_beneficio.getTipo_beneficio_id() == null) {
                System.err.println("Validación: Si hay valor de beneficio, debe haber un tipo de beneficio");
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica si ya existe una variante con la misma combinación
     * producto-color-talla
     *
     * @return true si existe, false en caso contrario
     */
    private boolean existeVariante(Integer producto_id, Integer color_id, Integer talla_id) {
        try {
            ArrayList<ProductosVariantesDTO> todasLasVariantes = this.listarTodos();

            return todasLasVariantes.stream().anyMatch(v
                    -> v.getProducto_id().equals(producto_id)
                    && v.getColor() != null && v.getColor().getColor_id().equals(color_id)
                    && v.getTalla() != null && v.getTalla().getTalla_id().equals(talla_id)
            );

        } catch (Exception e) {
            System.err.println("Error al verificar existencia de variante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza solo el stock de una variante
     *
     * @param prod_variante_id ID de la variante
     * @param nuevo_stock Nuevo valor de stock
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarStock(Integer prod_variante_id, Integer nuevo_stock) {
        try {
            if (prod_variante_id == null || nuevo_stock == null || nuevo_stock < 0) {
                System.err.println("Error: Parámetros inválidos para actualizar stock");
                return false;
            }

            ProductosVariantesDTO variante = this.obtenerPorId(prod_variante_id);
            if (variante == null) {
                System.err.println("Error: Variante no encontrada");
                return false;
            }

            // Actualizar stock y recalcular alerta
            variante.setStock(nuevo_stock);
            variante.setAlerta_stock(nuevo_stock <= variante.getStock_minimo());

            Integer resultado = this.modificar(
                    variante.getProd_variante_id(),
                    variante.getSKU(),
                    variante.getStock(),
                    variante.getStock_minimo(),
                    variante.getAlerta_stock(),
                    variante.getProducto_id(),
                    variante.getColor(),
                    variante.getTalla(),
                    variante.getTipo_beneficio(),
                    variante.getValor_beneficio(),
                    variante.getFecha_hora_creacion()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todas las variantes con stock bajo (alerta activada)
     *
     * @return Lista de variantes con stock bajo
     */
    public ArrayList<ProductosVariantesDTO> listarConStockBajo() {
        try {
            ArrayList<ProductosVariantesDTO> todasLasVariantes = this.listarTodos();
            ArrayList<ProductosVariantesDTO> variantesConStockBajo = new ArrayList<>();

            for (ProductosVariantesDTO variante : todasLasVariantes) {
                if (variante.getAlerta_stock() != null && variante.getAlerta_stock()) {
                    variantesConStockBajo.add(variante);
                }
            }

            return variantesConStockBajo;

        } catch (Exception e) {
            System.err.println("Error al listar variantes con stock bajo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista variantes de un producto específico
     *
     * @param producto_id ID del producto
     * @return Lista de variantes del producto
     */
    public ArrayList<ProductosVariantesDTO> listarPorProducto(Integer producto_id) {
        try {
            if (producto_id == null || producto_id <= 0) {
                return new ArrayList<>();
            }

            return this.prodVarianteDAO.listarPorProductoId(producto_id);

        } catch (Exception e) {
            System.err.println("Error al listar variantes por producto: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista variantes por color
     *
     * @param color_id ID del color
     * @return Lista de variantes del color especificado
     */
    public ArrayList<ProductosVariantesDTO> listarPorColor(Integer color_id) {
        try {
            if (color_id == null || color_id <= 0) {
                return new ArrayList<>();
            }

            ArrayList<ProductosVariantesDTO> todasLasVariantes = this.listarTodos();
            ArrayList<ProductosVariantesDTO> variantesFiltradas = new ArrayList<>();

            for (ProductosVariantesDTO variante : todasLasVariantes) {
                if (variante.getColor() != null
                        && color_id.equals(variante.getColor().getColor_id())) {
                    variantesFiltradas.add(variante);
                }
            }

            return variantesFiltradas;

        } catch (Exception e) {
            System.err.println("Error al listar variantes por color: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista variantes por talla
     *
     * @param talla_id ID de la talla
     * @return Lista de variantes de la talla especificada
     */
    public ArrayList<ProductosVariantesDTO> listarPorTalla(Integer talla_id) {
        try {
            if (talla_id == null || talla_id <= 0) {
                return new ArrayList<>();
            }

            ArrayList<ProductosVariantesDTO> todasLasVariantes = this.listarTodos();
            ArrayList<ProductosVariantesDTO> variantesFiltradas = new ArrayList<>();

            for (ProductosVariantesDTO variante : todasLasVariantes) {
                if (variante.getTalla() != null
                        && talla_id.equals(variante.getTalla().getTalla_id())) {
                    variantesFiltradas.add(variante);
                }
            }

            return variantesFiltradas;

        } catch (Exception e) {
            System.err.println("Error al listar variantes por talla: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Verifica si una variante tiene stock disponible
     *
     * @param prod_variante_id ID de la variante
     * @return true si tiene stock > 0, false en caso contrario
     */
    public boolean tieneStockDisponible(Integer prod_variante_id) {
        try {
            ProductosVariantesDTO variante = this.obtenerPorId(prod_variante_id);
            return variante != null && variante.getStock() != null && variante.getStock() > 0;

        } catch (Exception e) {
            System.err.println("Error al verificar stock disponible: " + e.getMessage());
            return false;
        }
    }
}
