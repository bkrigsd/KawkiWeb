package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.MovimientosInventarioDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductosVariantesDAOImpl;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;
import pe.edu.pucp.kawkiweb.dao.MovimientosInventarioDAO;
import pe.edu.pucp.kawkiweb.dao.ProductosVariantesDAO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;

public class MovimientosInventariosBO {

    private MovimientosInventarioDAO movInventarioDAO;
    private ProductosVariantesDAO productoVarianteDAO;

    public MovimientosInventariosBO() {
        this.movInventarioDAO = new MovimientosInventarioDAOImpl();
        this.productoVarianteDAO = new ProductosVariantesDAOImpl();
    }

    public Integer insertar(Integer cantidad, String observacion,
            TiposMovimientoDTO tipo_movimiento, ProductosVariantesDTO prod_variante,
            UsuariosDTO usuario) {

        try {
            // Validaciones
            if (!validarDatosMovimiento(cantidad, tipo_movimiento, prod_variante)) {
                System.err.println("Error: Datos de movimiento inválidos");
                return null;
            }

            // Validar stock disponible para salidas
            if (tipo_movimiento.esSalida() && !validarStockSuficiente(prod_variante, cantidad)) {
                System.err.println("Error: Stock insuficiente para realizar la salida");
                return null;
            }

            MovimientosInventarioDTO movInventarioDTO = new MovimientosInventarioDTO();
            movInventarioDTO.setCantidad(cantidad);
            movInventarioDTO.setFecha_hora_mov(LocalDateTime.now());
            movInventarioDTO.setObservacion(observacion);
            movInventarioDTO.setTipo_movimiento(tipo_movimiento);
            movInventarioDTO.setProd_variante(prod_variante);
            movInventarioDTO.setUsuario(usuario);

            // Insertar el movimiento
            Integer resultado = this.movInventarioDAO.insertar(movInventarioDTO);

            // Si se insertó correctamente, actualizar el stock del producto variante
            if (resultado != null) {
                actualizarStockProductoVariante(prod_variante, cantidad,
                        tipo_movimiento);
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al insertar movimiento de inventario: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public MovimientosInventarioDTO obtenerPorId(Integer movInventarioId) {
        try {
            if (movInventarioId == null || movInventarioId <= 0) {
                System.err.println("Error: ID de movimiento inválido");
                return null;
            }
            return this.movInventarioDAO.obtenerPorId(movInventarioId);

        } catch (Exception e) {
            System.err.println("Error al obtener movimiento por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<MovimientosInventarioDTO> listarTodos() {
        try {
            List<MovimientosInventarioDTO> lista = this.movInventarioDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar movimientos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Integer modificar(Integer mov_inventario_id, Integer cantidad,
            String observacion, TiposMovimientoDTO tipo_movimiento,
            ProductosVariantesDTO prod_variante, UsuariosDTO usuario) {

        try {
            // Validar ID
            if (mov_inventario_id == null || mov_inventario_id <= 0) {
                System.err.println("Error: ID de movimiento inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosMovimiento(cantidad, tipo_movimiento, prod_variante)) {
                System.err.println("Error: Datos de movimiento inválidos");
                return null;
            }

            MovimientosInventarioDTO movInventarioDTO = new MovimientosInventarioDTO();
            movInventarioDTO.setMov_inventario_id(mov_inventario_id);
            movInventarioDTO.setCantidad(cantidad);
            movInventarioDTO.setObservacion(observacion);
            movInventarioDTO.setTipo_movimiento(tipo_movimiento);
            movInventarioDTO.setProd_variante(prod_variante);
            movInventarioDTO.setUsuario(usuario);

            return this.movInventarioDAO.modificar(movInventarioDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar movimiento: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//    public Integer eliminar(Integer movInventarioId) {
//        try {
//            if (movInventarioId == null || movInventarioId <= 0) {
//                System.err.println("Error: ID de movimiento inválido");
//                return null;
//            }
//
//            MovimientosInventarioDTO movInventarioDTO = new MovimientosInventarioDTO();
//            movInventarioDTO.setMov_inventario_id(movInventarioId);
//            return this.movInventarioDAO.eliminar(movInventarioDTO);
//
//        } catch (Exception e) {
//            System.err.println("Error al eliminar movimiento: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
    /**
     * Valida los datos básicos de un movimiento de inventario
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosMovimiento(Integer cantidad,
            TiposMovimientoDTO tipo_movimiento, ProductosVariantesDTO prod_variante) {

        // Validar cantidad
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Validación: La cantidad debe ser mayor a 0");
            return false;
        }

        // Validar tipo de movimiento
        if (tipo_movimiento == null || tipo_movimiento.getTipoMovimientoId() == null) {
            System.err.println("Validación: El tipo de movimiento no puede ser null");
            return false;
        }

        // Validar producto variante
        if (prod_variante == null || prod_variante.getProd_variante_id() == null) {
            System.err.println("Validación: El producto variante no puede ser null");
            return false;
        }

        return true;
    }

    /**
     * Valida si hay stock suficiente para realizar una salida
     *
     * @return true si hay stock suficiente, false en caso contrario
     */
    private boolean validarStockSuficiente(ProductosVariantesDTO prod_variante,
            Integer cantidad) {
        try {
            // Obtener el producto variante actualizado de la base de datos
            ProductosVariantesDTO prodVarianteActual = this.productoVarianteDAO.obtenerPorId(
                    prod_variante.getProd_variante_id()
            );

            if (prodVarianteActual == null) {
                System.err.println("Validación: Producto variante no encontrado");
                return false;
            }

            Integer stockActual = prodVarianteActual.getStock();
            if (stockActual == null || stockActual < cantidad) {
                System.err.println("Validación: Stock actual (" + stockActual
                        + ") insuficiente para la cantidad solicitada (" + cantidad + ")");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.err.println("Error al validar stock: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el stock del producto variante según el tipo de movimiento
     */
    private void actualizarStockProductoVariante(ProductosVariantesDTO prod_variante,
            Integer cantidad, TiposMovimientoDTO tipo_movimiento) {
        try {
            // Obtener el producto variante actualizado
            ProductosVariantesDTO prodVarianteActual = this.productoVarianteDAO.obtenerPorId(
                    prod_variante.getProd_variante_id()
            );

            if (prodVarianteActual == null) {
                System.err.println("Error: No se pudo actualizar el stock - Producto no encontrado");
                return;
            }

            Integer stockActual = prodVarianteActual.getStock();
            Integer nuevoStock = stockActual;

            // Calcular nuevo stock según tipo de movimiento
            if (tipo_movimiento.esIngreso()) {
                nuevoStock = stockActual + cantidad;
            } else if (tipo_movimiento.esSalida()) {
                nuevoStock = stockActual - cantidad;
            } else if (tipo_movimiento.esAjuste()) {
                // Para ajustes, la cantidad puede ser positiva o negativa
                // Aquí asumimos que es un valor absoluto que reemplaza el stock
                nuevoStock = cantidad;
            }

            // Actualizar el stock
            prodVarianteActual.setStock(nuevoStock);

            // Verificar si debe activarse la alerta de stock
            if (nuevoStock <= prodVarianteActual.getStock_minimo()) {
                prodVarianteActual.setAlerta_stock(true);
            } else {
                prodVarianteActual.setAlerta_stock(false);
            }

            this.productoVarianteDAO.modificar(prodVarianteActual);

        } catch (Exception e) {
            System.err.println("Error al actualizar stock del producto variante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea un movimiento de ajuste de inventario. Útil para corregir
     * discrepancias entre el stock real y el registrado
     *
     * @param prod_variante Producto variante a ajustar
     * @param stockReal Stock real contado
     * @param observacion Motivo del ajuste
     * @param usuario Usuario que está generando la acción
     * @return ID del movimiento creado, o null si hubo error
     */
    public Integer crearAjusteInventario(ProductosVariantesDTO prod_variante,
            Integer stockReal, String observacion, UsuariosDTO usuario) {
        try {
            if (prod_variante == null || stockReal == null || stockReal < 0) {
                System.err.println("Error: Datos inválidos para ajuste de inventario");
                return null;
            }

            // Obtener el producto variante actual
            ProductosVariantesDTO prodVarianteActual = this.productoVarianteDAO.obtenerPorId(
                    prod_variante.getProd_variante_id()
            );

            if (prodVarianteActual == null) {
                System.err.println("Error: Producto variante no encontrado");
                return null;
            }

            // Crear tipo de movimiento AJUSTE
            TiposMovimientoDTO tipoAjuste = new TiposMovimientoDTO(
                    TiposMovimientoDTO.ID_AJUSTE,
                    TiposMovimientoDTO.NOMBRE_AJUSTE
            );

            // Crear movimiento de ajuste
            String observacionCompleta = "Ajuste de inventario: Stock anterior: "
                    + prodVarianteActual.getStock() + ", Stock real: " + stockReal
                    + ". " + (observacion != null ? observacion : "");

            return insertar(
                    stockReal,
                    observacionCompleta,
                    tipoAjuste,
                    prod_variante,
                    usuario
            );

        } catch (Exception e) {
            System.err.println("Error al crear ajuste de inventario: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registra una entrada de mercancía al inventario
     *
     * @return ID del movimiento creado, o null si hubo error
     */
    public Integer registrarIngreso(ProductosVariantesDTO prod_variante, Integer cantidad,
            String observacion, UsuariosDTO usuario) {
        try {
            TiposMovimientoDTO tipoIngreso = new TiposMovimientoDTO(
                    TiposMovimientoDTO.ID_INGRESO,
                    TiposMovimientoDTO.NOMBRE_INGRESO
            );

            return insertar(cantidad, observacion, tipoIngreso, prod_variante, usuario);

        } catch (Exception e) {
            System.err.println("Error al registrar ingreso: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registra una salida de mercancía del inventario
     *
     * @return ID del movimiento creado, o null si hubo error
     */
    public Integer registrarSalida(ProductosVariantesDTO prod_variante, Integer cantidad,
            String observacion, UsuariosDTO usuario) {
        try {
            TiposMovimientoDTO tipoSalida = new TiposMovimientoDTO(
                    TiposMovimientoDTO.ID_SALIDA,
                    TiposMovimientoDTO.NOMBRE_SALIDA
            );

            return insertar(cantidad, observacion, tipoSalida, prod_variante, usuario);

        } catch (Exception e) {
            System.err.println("Error al registrar salida: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // =====================================================
    // MÉTODOS DE CONSULTA
    // =====================================================
    /**
     * Lista movimientos de una variante específica (historial)
     *
     * @param prod_variante_id ID de la variante de producto
     * @return Lista de movimientos ordenados por fecha descendente
     */
    public List<MovimientosInventarioDTO> listarPorProductoVariante(Integer prod_variante_id) {
        try {
            if (prod_variante_id == null || prod_variante_id <= 0) {
                return new ArrayList<>();
            }

            return this.movInventarioDAO.listarPorProductoVariante(prod_variante_id);

        } catch (Exception e) {
            System.err.println("Error al listar movimientos por producto variante: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista movimientos por tipo (ingresos, salidas o ajustes)
     *
     * @param tipo_movimiento_id ID del tipo de movimiento
     * @return Lista de movimientos del tipo especificado
     */
    public List<MovimientosInventarioDTO> listarPorTipoMovimiento(Integer tipo_movimiento_id) {
        try {
            if (tipo_movimiento_id == null || tipo_movimiento_id <= 0) {
                return new ArrayList<>();
            }

            return this.movInventarioDAO.listarPorTipoMovimiento(tipo_movimiento_id);

        } catch (Exception e) {
            System.err.println("Error al listar movimientos por tipo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista movimientos realizados por un usuario específico (auditoría)
     *
     * @param usuario_id ID del usuario
     * @return Lista de movimientos realizados por el usuario
     */
    public List<MovimientosInventarioDTO> listarPorUsuario(Integer usuario_id) {
        try {
            if (usuario_id == null || usuario_id <= 0) {
                return new ArrayList<>();
            }

            return this.movInventarioDAO.listarPorUsuario(usuario_id);

        } catch (Exception e) {
            System.err.println("Error al listar movimientos por usuario: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista movimientos en un rango de fechas
     *
     * @param fecha_inicio Fecha y hora inicial del rango
     * @param fecha_fin Fecha y hora final del rango
     * @return Lista de movimientos en el rango especificado
     */
    public List<MovimientosInventarioDTO> listarPorRangoFechas(
            LocalDateTime fecha_inicio, LocalDateTime fecha_fin) {
        try {
            if (fecha_inicio == null || fecha_fin == null) {
                System.err.println("Error: Las fechas no pueden ser null");
                return new ArrayList<>();
            }

            if (fecha_inicio.isAfter(fecha_fin)) {
                System.err.println("Error: La fecha inicial debe ser anterior a la fecha final");
                return new ArrayList<>();
            }

            return this.movInventarioDAO.listarPorRangoFechas(fecha_inicio, fecha_fin);

        } catch (Exception e) {
            System.err.println("Error al listar movimientos por rango de fechas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista los últimos N movimientos de inventario (para dashboard)
     *
     * @param limite Número de movimientos a retornar
     * @return Lista de los últimos movimientos ordenados por fecha descendente
     */
    public List<MovimientosInventarioDTO> listarMovimientosRecientes(Integer limite) {
        try {
            if (limite == null || limite <= 0) {
                limite = 10; // Default: últimos 10 movimientos
            }

            // Límite máximo de seguridad
            if (limite > 100) {
                limite = 100;
            }

            return this.movInventarioDAO.listarMovimientosRecientes(limite);

        } catch (Exception e) {
            System.err.println("Error al listar movimientos recientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
