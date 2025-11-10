package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.MovimientoInventarioDAO;
import pe.edu.pucp.kawkiweb.dao.ProductoVarianteDAO;
import pe.edu.pucp.kawkiweb.daoImp.MovimientoInventarioDAOImpl;
import pe.edu.pucp.kawkiweb.daoImp.ProductoVarianteDAOImpl;
import pe.edu.pucp.kawkiweb.model.MovimientosInventarioDTO;
import pe.edu.pucp.kawkiweb.model.ProductosVariantesDTO;
import pe.edu.pucp.kawkiweb.model.utilMovInventario.TiposMovimientoDTO;

public class MovimientoInventarioBO {

    private MovimientoInventarioDAO movInventarioDAO;
    private ProductoVarianteDAO productoVarianteDAO;

    public MovimientoInventarioBO() {
        this.movInventarioDAO = new MovimientoInventarioDAOImpl();
        this.productoVarianteDAO = new ProductoVarianteDAOImpl();
    }

    /**
     * Inserta un nuevo movimiento de inventario y actualiza el stock
     *
     * @return ID del movimiento insertado, o null si hubo error
     */
    public Integer insertar(Integer cantidad, LocalDateTime fecha_hora_mov,
            String observacion, TiposMovimientoDTO tipo_movimiento,
            ProductosVariantesDTO prod_variante) {

        try {
            // Validaciones
            if (!validarDatosMovimiento(cantidad, fecha_hora_mov, tipo_movimiento, prod_variante)) {
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
            movInventarioDTO.setFecha_hora_mov(fecha_hora_mov);
            movInventarioDTO.setObservacion(observacion);
            movInventarioDTO.setTipo_movimiento(tipo_movimiento);
            movInventarioDTO.setProd_variante(prod_variante);

            // Insertar el movimiento
            Integer resultado = this.movInventarioDAO.insertar(movInventarioDTO);

            // Si se insertó correctamente, actualizar el stock del producto variante
            if (resultado != null) {
                actualizarStockProductoVariante(prod_variante, cantidad, tipo_movimiento);
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al insertar movimiento de inventario: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene un movimiento de inventario por su ID
     *
     * @param movInventarioId ID del movimiento a buscar
     * @return MovimientosInventarioDTO encontrado, o null si no existe o hay
 error
     */
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

    /**
     * Lista todos los movimientos de inventario
     *
     * @return Lista de movimientos, o lista vacía si hay error
     */
    public ArrayList<MovimientosInventarioDTO> listarTodos() {
        try {
            ArrayList<MovimientosInventarioDTO> lista = this.movInventarioDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar movimientos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Modifica un movimiento de inventario existente NOTA: Este método NO
     * actualiza el stock automáticamente para evitar inconsistencias. Se
     * recomienda evitar modificar movimientos y en su lugar crear movimientos
     * de ajuste.
     *
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer modificar(Integer mov_inventario_id, Integer cantidad,
            LocalDateTime fecha_hora_mov, String observacion, TiposMovimientoDTO tipo_movimiento,
            ProductosVariantesDTO prod_variante) {

        try {
            // Validar ID
            if (mov_inventario_id == null || mov_inventario_id <= 0) {
                System.err.println("Error: ID de movimiento inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosMovimiento(cantidad, fecha_hora_mov, tipo_movimiento, prod_variante)) {
                System.err.println("Error: Datos de movimiento inválidos");
                return null;
            }

            MovimientosInventarioDTO movInventarioDTO = new MovimientosInventarioDTO();
            movInventarioDTO.setMov_inventario_id(mov_inventario_id);
            movInventarioDTO.setCantidad(cantidad);
            movInventarioDTO.setFecha_hora_mov(fecha_hora_mov);
            movInventarioDTO.setObservacion(observacion);
            movInventarioDTO.setTipo_movimiento(tipo_movimiento);
            movInventarioDTO.setProd_variante(prod_variante);

            return this.movInventarioDAO.modificar(movInventarioDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar movimiento: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina un movimiento de inventario por su ID NOTA: Este método NO
     * revierte el stock. Se recomienda crear movimientos de ajuste en lugar de
     * eliminar movimientos existentes.
     *
     * @param movInventarioId ID del movimiento a eliminar
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer eliminar(Integer movInventarioId) {
        try {
            if (movInventarioId == null || movInventarioId <= 0) {
                System.err.println("Error: ID de movimiento inválido");
                return null;
            }

            MovimientosInventarioDTO movInventarioDTO = new MovimientosInventarioDTO();
            movInventarioDTO.setMov_inventario_id(movInventarioId);
            return this.movInventarioDAO.eliminar(movInventarioDTO);

        } catch (Exception e) {
            System.err.println("Error al eliminar movimiento: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valida los datos básicos de un movimiento de inventario
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosMovimiento(Integer cantidad, LocalDateTime fecha_hora_mov,
            TiposMovimientoDTO tipo_movimiento, ProductosVariantesDTO prod_variante) {

        // Validar cantidad
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Validación: La cantidad debe ser mayor a 0");
            return false;
        }

        // Validar fecha
        if (fecha_hora_mov == null) {
            System.err.println("Validación: La fecha del movimiento no puede ser null");
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
    private boolean validarStockSuficiente(ProductosVariantesDTO prod_variante, Integer cantidad) {
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
                // Puedes modificar esta lógica según tus necesidades
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
     * Crea un movimiento de ajuste de inventario Útil para corregir
     * discrepancias entre el stock real y el registrado
     *
     * @param prod_variante Producto variante a ajustar
     * @param stockReal Stock real contado
     * @param observacion Motivo del ajuste
     * @return ID del movimiento creado, o null si hubo error
     */
    public Integer crearAjusteInventario(ProductosVariantesDTO prod_variante,
            Integer stockReal, String observacion) {
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
                    LocalDateTime.now(),
                    observacionCompleta,
                    tipoAjuste,
                    prod_variante
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
            String observacion) {
        try {
            TiposMovimientoDTO tipoIngreso = new TiposMovimientoDTO(
                    TiposMovimientoDTO.ID_INGRESO,
                    TiposMovimientoDTO.NOMBRE_INGRESO
            );

            return insertar(cantidad, LocalDateTime.now(), observacion, tipoIngreso, prod_variante);

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
            String observacion) {
        try {
            TiposMovimientoDTO tipoSalida = new TiposMovimientoDTO(
                    TiposMovimientoDTO.ID_SALIDA,
                    TiposMovimientoDTO.NOMBRE_SALIDA
            );

            return insertar(cantidad, LocalDateTime.now(), observacion, tipoSalida, prod_variante);

        } catch (Exception e) {
            System.err.println("Error al registrar salida: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
