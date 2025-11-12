package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.ProductosDAOImpl;
import pe.edu.pucp.kawkiweb.model.ProductosDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.CategoriasDTO;
import pe.edu.pucp.kawkiweb.model.utilProducto.EstilosDTO;
import pe.edu.pucp.kawkiweb.dao.ProductosDAO;

public class ProductosBO {

    private ProductosDAO productoDAO;

    public ProductosBO() {
        this.productoDAO = new ProductosDAOImpl();
    }

    public Integer insertar(String descripcion, CategoriasDTO categoria,
            EstilosDTO estilo, Double precio_venta) {

        try {
            // Validaciones
            if (!validarDatosProducto(descripcion, categoria, estilo, precio_venta)) {
                System.err.println("Error: Datos de producto inválidos");
                return null;
            }

            ProductosDTO productoDTO = new ProductosDTO();
            productoDTO.setDescripcion(descripcion);
            productoDTO.setCategoria(categoria);
            productoDTO.setEstilo(estilo);
            productoDTO.setPrecio_venta(precio_venta);
            productoDTO.setFecha_hora_creacion(LocalDateTime.now());

            return this.productoDAO.insertar(productoDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ProductosDTO obtenerPorId(Integer producto_id) {
        try {
            if (producto_id == null || producto_id <= 0) {
                System.err.println("Error: ID de producto inválido");
                return null;
            }
            return this.productoDAO.obtenerPorId(producto_id);

        } catch (Exception e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductosDTO> listarTodos() {
        try {
            List<ProductosDTO> lista = this.productoDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar productos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Integer modificar(Integer producto_id, String descripcion,
            CategoriasDTO categoria, EstilosDTO estilo, Double precio_venta) {

        try {
            // Validar ID
            if (producto_id == null || producto_id <= 0) {
                System.err.println("Error: ID de producto inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosProducto(descripcion, categoria, estilo, precio_venta)) {
                System.err.println("Error: Datos de producto inválidos");
                return null;
            }

            ProductosDTO productoDTO = new ProductosDTO();
            productoDTO.setProducto_id(producto_id);
            productoDTO.setDescripcion(descripcion);
            productoDTO.setCategoria(categoria);
            productoDTO.setEstilo(estilo);
            productoDTO.setPrecio_venta(precio_venta);
            productoDTO.setFecha_hora_creacion(LocalDateTime.now());

            return this.productoDAO.modificar(productoDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar producto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Integer eliminar(Integer producto_id) {
        try {
            if (producto_id == null || producto_id <= 0) {
                System.err.println("Error: ID de producto inválido");
                return null;
            }

            // Verificar si el producto tiene variantes
            ProductosDTO producto = this.obtenerPorId(producto_id);
            if (producto != null && producto.getVariantes() != null
                    && !producto.getVariantes().isEmpty()) {
                System.err.println("Error: No se puede eliminar un producto con variantes asociadas");
                return null;
            }

            ProductosDTO productoDTO = new ProductosDTO();
            productoDTO.setProducto_id(producto_id);
            return this.productoDAO.eliminar(productoDTO);

        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valida los datos básicos de un producto
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosProducto(String descripcion, CategoriasDTO categoria,
            EstilosDTO estilo, Double precio_venta) {

        // Validar descripción
        if (descripcion == null || descripcion.trim().isEmpty()) {
            System.err.println("Validación: La descripción no puede estar vacía");
            return false;
        }

        if (descripcion.trim().length() > 100) {
            System.err.println("Validación: La descripción es demasiado larga (máx. 500 caracteres)");
            return false;
        }

        // Validar categoría
        if (categoria == null || categoria.getCategoria_id() == null) {
            System.err.println("Validación: La categoría no puede ser null");
            return false;
        }

        // Validar estilo
        if (estilo == null || estilo.getEstilo_id() == null) {
            System.err.println("Validación: El estilo no puede ser null");
            return false;
        }

        // Validar precio de venta
        if (precio_venta == null || precio_venta <= 0) {
            System.err.println("Validación: El precio de venta debe ser mayor a 0");
            return false;
        }

        return true;
    }

    /**
     * Verifica si un producto tiene stock disponible en alguna de sus variantes
     *
     * @param producto_id ID del producto a verificar
     * @return true si tiene stock disponible, false en caso contrario
     */
    public boolean tieneStockDisponible(Integer producto_id) {
        try {
            ProductosDTO producto = this.obtenerPorId(producto_id);

            if (producto == null || producto.getVariantes() == null) {
                return false;
            }

            return producto.getVariantes().stream()
                    .anyMatch(v -> v.getStock() != null && v.getStock() > 0);

        } catch (Exception e) {
            System.err.println("Error al verificar stock del producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Calcula el stock total de un producto sumando todas sus variantes
     *
     * @param producto_id ID del producto
     * @return Stock total, o 0 si hay error
     */
    public Integer calcularStockTotal(Integer producto_id) {
        try {
            ProductosDTO producto = this.obtenerPorId(producto_id);

            if (producto == null || producto.getVariantes() == null) {
                return 0;
            }

            return producto.getVariantes().stream()
                    .mapToInt(v -> v.getStock() != null ? v.getStock() : 0)
                    .sum();

        } catch (Exception e) {
            System.err.println("Error al calcular stock total: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Lista productos por categoría
     *
     * @param categoria_id ID de la categoría
     * @return Lista de productos de la categoría
     */
    public List<ProductosDTO> listarPorCategoria(Integer categoria_id) {
        try {
            if (categoria_id == null || categoria_id <= 0) {
                return new ArrayList<>();
            }

            List<ProductosDTO> todosLosProductos = this.listarTodos();
            List<ProductosDTO> productosFiltrados = new ArrayList<>();

            for (ProductosDTO producto : todosLosProductos) {
                if (producto.getCategoria() != null
                        && categoria_id.equals(producto.getCategoria().getCategoria_id())) {
                    productosFiltrados.add(producto);
                }
            }

            return productosFiltrados;

        } catch (Exception e) {
            System.err.println("Error al listar productos por categoría: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista productos por estilo
     *
     * @param estilo_id ID del estilo
     * @return Lista de productos del estilo
     */
    public List<ProductosDTO> listarPorEstilo(Integer estilo_id) {
        try {
            if (estilo_id == null || estilo_id <= 0) {
                return new ArrayList<>();
            }

            List<ProductosDTO> todosLosProductos = this.listarTodos();
            List<ProductosDTO> productosFiltrados = new ArrayList<>();

            for (ProductosDTO producto : todosLosProductos) {
                if (producto.getEstilo() != null
                        && estilo_id.equals(producto.getEstilo().getEstilo_id())) {
                    productosFiltrados.add(producto);
                }
            }

            return productosFiltrados;

        } catch (Exception e) {
            System.err.println("Error al listar productos por estilo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista productos con stock bajo (tienen variantes con alerta de stock
     * activada)
     *
     * @return Lista de productos con stock bajo
     */
    public List<ProductosDTO> listarConStockBajo() {
        try {
            List<ProductosDTO> todosLosProductos = this.listarTodos();
            List<ProductosDTO> productosConStockBajo = new ArrayList<>();

            for (ProductosDTO producto : todosLosProductos) {
                if (producto.getVariantes() != null) {
                    boolean tieneStockBajo = producto.getVariantes().stream()
                            .anyMatch(v -> v.getAlerta_stock() != null && v.getAlerta_stock());

                    if (tieneStockBajo) {
                        productosConStockBajo.add(producto);
                    }
                }
            }

            return productosConStockBajo;

        } catch (Exception e) {
            System.err.println("Error al listar productos con stock bajo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
