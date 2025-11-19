package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.ColoresDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.TallasDTO;
import pe.edu.pucp.kawkiweb.dao.ProductosDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public class ProductosVariantesBO {

    private ProductosVariantesDAO prodVarianteDAO;
    private ProductosDAO productoDAO;

    public ProductosVariantesBO() {
        this.prodVarianteDAO = new ProductosVariantesDAOImpl();
        this.productoDAO = new ProductosDAOImpl();
    }

    public Integer insertar(Integer stock, Integer stock_minimo,
            Integer producto_id, ColoresDTO color, TallasDTO talla,
            String url_imagen, Boolean disponible, UsuariosDTO usuario) {

        try {
            // Validaciones
            if (!validarDatosVariante(stock, stock_minimo, producto_id,
                    color, talla, disponible, usuario)) {
                System.err.println("Error: Datos de variante inválidos");
                return null;
            }

            // Validar que el producto existe
            ProductosDTO producto = this.productoDAO.obtenerPorId(producto_id);
            if (producto == null) {
                System.err.println("Error: El producto con ID " + producto_id + " no existe");
                return null;
            }

            // Validar combinación única de producto-color-talla usando el SP
            if (existeVariante(producto_id, color.getColor_id(), talla.getTalla_id())) {
                System.err.println("Error: Ya existe una variante con este color y talla para este producto");
                return null;
            }

            // Generar SKU automáticamente
            String skuGenerado = generarSKU(producto, color, talla);

            ProductosVariantesDTO prodVarianteDTO = new ProductosVariantesDTO();
            prodVarianteDTO.setSKU(skuGenerado);
            prodVarianteDTO.setStock(stock);
            prodVarianteDTO.setStock_minimo(stock_minimo);
            // Calcular automáticamente alerta_stock
            prodVarianteDTO.setAlerta_stock(stock <= stock_minimo);

            prodVarianteDTO.setProducto_id(producto_id);
            prodVarianteDTO.setColor(color);
            prodVarianteDTO.setTalla(talla);
            prodVarianteDTO.setUrl_imagen(url_imagen);
            prodVarianteDTO.setFecha_hora_creacion(LocalDateTime.now());
            prodVarianteDTO.setDisponible(disponible);
            prodVarianteDTO.setUsuario(usuario);

            return this.prodVarianteDAO.insertar(prodVarianteDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar variante de producto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

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

    public List<ProductosVariantesDTO> listarTodos() {
        try {
            List<ProductosVariantesDTO> lista = this.prodVarianteDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar variantes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Integer modificar(Integer prod_variante_id, Integer stock,
            Integer stock_minimo, Integer producto_id, ColoresDTO color,
            TallasDTO talla, String url_imagen, Boolean disponible,
            UsuariosDTO usuario) {

        try {
            // Validar ID
            if (prod_variante_id == null || prod_variante_id <= 0) {
                System.err.println("Error: ID de variante inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosVariante(stock, stock_minimo, producto_id,
                    color, talla, disponible, usuario)) {
                System.err.println("Error: Datos de variante inválidos");
                return null;
            }

            // Obtener el producto completo para generar el SKU
            ProductosDTO producto = this.productoDAO.obtenerPorId(producto_id);
            if (producto == null) {
                System.err.println("Error: El producto con ID " + producto_id + " no existe");
                return null;
            }

            // NUEVO: Validar que la nueva combinación no existe en OTRA variante
            if (existeVarianteParaModificar(prod_variante_id, producto_id,
                    color.getColor_id(), talla.getTalla_id())) {
                System.err.println("Error: Ya existe otra variante con este color y talla para este producto");
                return null;
            }

            // Generar SKU con el producto completo
            String skuGenerado = generarSKU(producto, color, talla);

            ProductosVariantesDTO prodVarianteDTO = new ProductosVariantesDTO();
            prodVarianteDTO.setProd_variante_id(prod_variante_id);
            prodVarianteDTO.setSKU(skuGenerado);
            prodVarianteDTO.setStock(stock);
            prodVarianteDTO.setStock_minimo(stock_minimo);
            prodVarianteDTO.setAlerta_stock(stock <= stock_minimo);
            prodVarianteDTO.setProducto_id(producto_id);
            prodVarianteDTO.setColor(color);
            prodVarianteDTO.setTalla(talla);
            prodVarianteDTO.setUrl_imagen(url_imagen);
            prodVarianteDTO.setDisponible(disponible);
            prodVarianteDTO.setUsuario(usuario);

            return this.prodVarianteDAO.modificar(prodVarianteDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar variante: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//    public Integer eliminar(Integer prod_variante_id) {
//        try {
//            if (prod_variante_id == null || prod_variante_id <= 0) {
//                System.err.println("Error: ID de variante inválido");
//                return null;
//            }
//
//            ProductosVariantesDTO prodVarianteDTO = new ProductosVariantesDTO();
//            prodVarianteDTO.setProd_variante_id(prod_variante_id);
//            return this.prodVarianteDAO.eliminar(prodVarianteDTO);
//
//        } catch (Exception e) {
//            System.err.println("Error al eliminar variante: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
    /**
     * Valida los datos básicos de una variante de producto
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosVariante(Integer stock,
            Integer stock_minimo, Integer producto_id, ColoresDTO color,
            TallasDTO talla, Boolean disponible, UsuariosDTO usuario) {

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

        // Validar disponible
        if (disponible == null) {
            System.err.println("Validación: La disponibilidad no puede ser null");
            return false;
        }

        // Validar usuario
        if (usuario == null || usuario.getUsuarioId() == null) {
            System.err.println("Validación: El usuario no puede ser null");
            return false;
        }

        return true;
    }

    /**
     * Verifica si ya existe una variante con la misma combinación
     * producto-color-talla usando el Stored Procedure
     *
     * @return true si existe, false en caso contrario
     */
    private boolean existeVariante(Integer producto_id, Integer color_id, Integer talla_id) {
        try {
            return this.prodVarianteDAO.existeVariante(producto_id, color_id, talla_id);
        } catch (Exception e) {
            System.err.println("Error al verificar existencia de variante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si existe otra variante con la misma combinación
     * producto-color-talla EXCLUYENDO la variante que se está modificando
     */
    private boolean existeVarianteParaModificar(Integer varianteIdActual,
            Integer producto_id, Integer color_id, Integer talla_id) {
        try {
            return this.prodVarianteDAO.existeVarianteParaModificar(
                    varianteIdActual, producto_id, color_id, talla_id
            );
        } catch (Exception e) {
            System.err.println("Error al verificar existencia de variante para modificar: " + e.getMessage());
            return true; // Por seguridad, asumir que existe para evitar duplicados
        }
    }

    /**
     * Genera el SKU automáticamente con el formato:
     * {categoria}-{estilo}-{producto_id}-{color}-{talla} Ejemplo:
     * DER-CHA-0023-NEG-36
     */
    private String generarSKU(ProductosDTO producto, ColoresDTO color, TallasDTO talla) {
        String categoriaCodigo = obtenerCodigoCategoria(producto.getCategoria().getNombre());
        String estiloCodigo = obtenerCodigoEstilo(producto.getEstilo().getNombre());
        String colorCodigo = obtenerCodigoColor(color.getNombre());

        return String.format("%s-%s-%04d-%s-%02d",
                categoriaCodigo,
                estiloCodigo,
                producto.getProducto_id(),
                colorCodigo,
                talla.getNumero()
        );
    }

    /**
     * Obtiene un código de 3 letras del nombre del color Maneja tildes y
     * caracteres especiales
     *
     * @param nombreColor Nombre completo del color
     * @return Código de 3 letras en mayúsculas
     */
    private String obtenerCodigoColor(String nombreColor) {
        if (nombreColor == null || nombreColor.isEmpty()) {
            return "XXX";
        }

        // Eliminar tildes y normalizar
        String limpio = nombreColor
                .toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("Ñ", "N")
                .trim();

        // Tomar las primeras 3 letras
        return limpio.substring(0, Math.min(3, limpio.length()));
    }

    private String obtenerCodigoCategoria(String nombreCategoria) {
        if (nombreCategoria == null || nombreCategoria.isEmpty()) {
            return "XXX";
        }

        String limpio = nombreCategoria
                .toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("Ñ", "N")
                .trim();

        return limpio.substring(0, Math.min(3, limpio.length()));
    }

    private String obtenerCodigoEstilo(String nombreEstilo) {
        if (nombreEstilo == null || nombreEstilo.isEmpty()) {
            return "XXX";
        }

        String limpio = nombreEstilo
                .toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("Ñ", "N")
                .trim();

        return limpio.substring(0, Math.min(3, limpio.length()));
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
                    variante.getStock(),
                    variante.getStock_minimo(),
                    variante.getProducto_id(),
                    variante.getColor(),
                    variante.getTalla(),
                    variante.getUrl_imagen(),
                    variante.getDisponible(),
                    variante.getUsuario()
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
    public List<ProductosVariantesDTO> listarConStockBajo() {
        try {
            return this.prodVarianteDAO.listarConStockBajo();
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
    public List<ProductosVariantesDTO> listarPorProductoId(Integer producto_id) {
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
    public List<ProductosVariantesDTO> listarPorColor(Integer color_id) {
        try {
            if (color_id == null || color_id <= 0) {
                return new ArrayList<>();
            }

            return this.prodVarianteDAO.listarPorColor(color_id);

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
    public List<ProductosVariantesDTO> listarPorTalla(Integer talla_id) {
        try {
            if (talla_id == null || talla_id <= 0) {
                return new ArrayList<>();
            }

            return this.prodVarianteDAO.listarPorTalla(talla_id);

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
