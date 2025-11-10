package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.PromocionDAO;
import pe.edu.pucp.kawkiweb.daoImp.PromocionDAOImpl;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;

public class PromocionBO {

    private PromocionDAO promoDAO;

    public PromocionBO() {
        this.promoDAO = new PromocionDAOImpl();
    }

    /**
     * Inserta una nueva promoción en la base de datos
     *
     * @return ID de la promoción insertada, o null si hubo error
     */
    public Integer insertar(String descripcion, TiposCondicionDTO tipo_condicion,
            Integer valor_condicion, TiposBeneficioDTO tipo_beneficio,
            Integer valor_beneficio, LocalDateTime fecha_inicio,
            LocalDateTime fecha_fin, Boolean activo) {

        try {
            // Validaciones
            if (!validarDatosPromocion(descripcion, tipo_condicion, valor_condicion,
                    tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin)) {
                System.err.println("Error: Datos de promoción inválidos");
                return null;
            }

            DescuentosDTO promoDTO = new DescuentosDTO();
            promoDTO.setDescripcion(descripcion);
            promoDTO.setTipo_condicion(tipo_condicion);
            promoDTO.setValor_condicion(valor_condicion);
            promoDTO.setTipo_beneficio(tipo_beneficio);
            promoDTO.setValor_beneficio(valor_beneficio);
            promoDTO.setFecha_inicio(fecha_inicio);
            promoDTO.setFecha_fin(fecha_fin);
            promoDTO.setActivo(activo != null ? activo : true);

            return this.promoDAO.insertar(promoDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar promoción: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene una promoción por su ID
     *
     * @param promocionId ID de la promoción a buscar
     * @return DescuentosDTO encontrado, o null si no existe o hay error
     */
    public DescuentosDTO obtenerPorId(Integer promocionId) {
        try {
            if (promocionId == null || promocionId <= 0) {
                System.err.println("Error: ID de promoción inválido");
                return null;
            }
            return this.promoDAO.obtenerPorId(promocionId);

        } catch (Exception e) {
            System.err.println("Error al obtener promoción por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todas las promociones
     *
     * @return Lista de promociones, o lista vacía si hay error
     */
    public ArrayList<DescuentosDTO> listarTodos() {
        try {
            ArrayList<DescuentosDTO> lista = this.promoDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar promociones: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Modifica una promoción existente
     *
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer modificar(Integer promoId, String descripcion,
            TiposCondicionDTO tipo_condicion, Integer valor_condicion,
            TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio,
            LocalDateTime fecha_inicio, LocalDateTime fecha_fin, Boolean activo) {

        try {
            // Validar ID
            if (promoId == null || promoId <= 0) {
                System.err.println("Error: ID de promoción inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosPromocion(descripcion, tipo_condicion, valor_condicion,
                    tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin)) {
                System.err.println("Error: Datos de promoción inválidos");
                return null;
            }

            DescuentosDTO promoDTO = new DescuentosDTO();
            promoDTO.setPromocion_id(promoId);
            promoDTO.setDescripcion(descripcion);
            promoDTO.setTipo_condicion(tipo_condicion);
            promoDTO.setValor_condicion(valor_condicion);
            promoDTO.setTipo_beneficio(tipo_beneficio);
            promoDTO.setValor_beneficio(valor_beneficio);
            promoDTO.setFecha_inicio(fecha_inicio);
            promoDTO.setFecha_fin(fecha_fin);
            promoDTO.setActivo(activo);

            return this.promoDAO.modificar(promoDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar promoción: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina una promoción por su ID
     *
     * @param promoId ID de la promoción a eliminar
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer eliminar(Integer promoId) {
        try {
            if (promoId == null || promoId <= 0) {
                System.err.println("Error: ID de promoción inválido");
                return null;
            }

            DescuentosDTO promoDTO = new DescuentosDTO();
            promoDTO.setPromocion_id(promoId);
            return this.promoDAO.eliminar(promoDTO);

        } catch (Exception e) {
            System.err.println("Error al eliminar promoción: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valida los datos básicos de una promoción
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosPromocion(String descripcion, TiposCondicionDTO tipo_condicion,
            Integer valor_condicion, TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio,
            LocalDateTime fecha_inicio, LocalDateTime fecha_fin) {

        // Validar descripción
        if (descripcion == null || descripcion.trim().isEmpty()) {
            System.err.println("Validación: La descripción no puede estar vacía");
            return false;
        }

        if (descripcion.trim().length() > 500) {
            System.err.println("Validación: La descripción es demasiado larga (máx. 500 caracteres)");
            return false;
        }

        // Validar tipo de condición
        if (tipo_condicion == null || tipo_condicion.getTipo_condicion_id() == null) {
            System.err.println("Validación: El tipo de condición no puede ser null");
            return false;
        }

        // Validar valor de condición
        if (valor_condicion == null || valor_condicion <= 0) {
            System.err.println("Validación: El valor de condición debe ser mayor a 0");
            return false;
        }

        // Validar tipo de beneficio
        if (tipo_beneficio == null || tipo_beneficio.getTipo_beneficio_id() == null) {
            System.err.println("Validación: El tipo de beneficio no puede ser null");
            return false;
        }

        // Validar valor de beneficio
        if (valor_beneficio == null || valor_beneficio <= 0) {
            System.err.println("Validación: El valor de beneficio debe ser mayor a 0");
            return false;
        }

        // Validar porcentaje si es descuento por porcentaje
        if (tipo_beneficio.esDescuentoPorcentaje() && valor_beneficio > 100) {
            System.err.println("Validación: El porcentaje de descuento no puede ser mayor a 100%");
            return false;
        }

        // Validar fechas
        if (fecha_inicio == null) {
            System.err.println("Validación: La fecha de inicio no puede ser null");
            return false;
        }

        if (fecha_fin == null) {
            System.err.println("Validación: La fecha de fin no puede ser null");
            return false;
        }

        if (fecha_fin.isBefore(fecha_inicio)) {
            System.err.println("Validación: La fecha de fin no puede ser anterior a la fecha de inicio");
            return false;
        }

        return true;
    }

    /**
     * Activa una promoción
     *
     * @param promoId ID de la promoción
     * @return true si se activó correctamente, false en caso contrario
     */
    public boolean activar(Integer promoId) {
        try {
            DescuentosDTO promo = this.obtenerPorId(promoId);
            if (promo == null) {
                System.err.println("Error: Promoción no encontrada");
                return false;
            }

            // Verificar que la promoción esté vigente
            LocalDateTime ahora = LocalDateTime.now();
            if (ahora.isBefore(promo.getFecha_inicio()) || ahora.isAfter(promo.getFecha_fin())) {
                System.err.println("Error: No se puede activar una promoción fuera de su periodo de vigencia");
                return false;
            }

            promo.setActivo(true);
            Integer resultado = this.modificar(
                    promo.getPromocion_id(),
                    promo.getDescripcion(),
                    promo.getTipo_condicion(),
                    promo.getValor_condicion(),
                    promo.getTipo_beneficio(),
                    promo.getValor_beneficio(),
                    promo.getFecha_inicio(),
                    promo.getFecha_fin(),
                    promo.getActivo()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al activar promoción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Desactiva una promoción
     *
     * @param promoId ID de la promoción
     * @return true si se desactivó correctamente, false en caso contrario
     */
    public boolean desactivar(Integer promoId) {
        try {
            DescuentosDTO promo = this.obtenerPorId(promoId);
            if (promo == null) {
                System.err.println("Error: Promoción no encontrada");
                return false;
            }

            promo.setActivo(false);
            Integer resultado = this.modificar(
                    promo.getPromocion_id(),
                    promo.getDescripcion(),
                    promo.getTipo_condicion(),
                    promo.getValor_condicion(),
                    promo.getTipo_beneficio(),
                    promo.getValor_beneficio(),
                    promo.getFecha_inicio(),
                    promo.getFecha_fin(),
                    promo.getActivo()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al desactivar promoción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todas las promociones activas
     *
     * @return Lista de promociones activas
     */
    public ArrayList<DescuentosDTO> listarActivas() {
        try {
            ArrayList<DescuentosDTO> todasLasPromociones = this.listarTodos();
            ArrayList<DescuentosDTO> promocionesActivas = new ArrayList<>();

            for (DescuentosDTO promo : todasLasPromociones) {
                if (promo.getActivo() != null && promo.getActivo()) {
                    promocionesActivas.add(promo);
                }
            }

            return promocionesActivas;

        } catch (Exception e) {
            System.err.println("Error al listar promociones activas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista las promociones vigentes (activas y dentro del periodo de validez)
     *
     * @return Lista de promociones vigentes
     */
    public ArrayList<DescuentosDTO> listarVigentes() {
        try {
            ArrayList<DescuentosDTO> todasLasPromociones = this.listarTodos();
            ArrayList<DescuentosDTO> promocionesVigentes = new ArrayList<>();
            LocalDateTime ahora = LocalDateTime.now();

            for (DescuentosDTO promo : todasLasPromociones) {
                if (esVigente(promo, ahora)) {
                    promocionesVigentes.add(promo);
                }
            }

            return promocionesVigentes;

        } catch (Exception e) {
            System.err.println("Error al listar promociones vigentes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Verifica si una promoción está vigente en un momento dado
     *
     * @param promo Promoción a verificar
     * @param fecha Fecha a verificar
     * @return true si está vigente, false en caso contrario
     */
    private boolean esVigente(DescuentosDTO promo, LocalDateTime fecha) {
        return promo.getActivo() != null && promo.getActivo()
                && promo.getFecha_inicio() != null && !fecha.isBefore(promo.getFecha_inicio())
                && promo.getFecha_fin() != null && !fecha.isAfter(promo.getFecha_fin());
    }

    /**
     * Verifica si una promoción es aplicable a un pedido
     *
     * @param promoId ID de la promoción
     * @param cantidadProductos Cantidad de productos en el pedido
     * @param montoTotal Monto total del pedido
     * @return true si la promoción es aplicable, false en caso contrario
     */
    public boolean esAplicable(Integer promoId, Integer cantidadProductos, Double montoTotal) {
        try {
            DescuentosDTO promo = this.obtenerPorId(promoId);

            if (promo == null || !esVigente(promo, LocalDateTime.now())) {
                return false;
            }

            // Verificar condición
            if (promo.getTipo_condicion().esCantidadMinimaProductos()) {
                return cantidadProductos != null
                        && cantidadProductos >= promo.getValor_condicion();
            } else if (promo.getTipo_condicion().esMontoMinimoCompra()) {
                return montoTotal != null
                        && montoTotal >= promo.getValor_condicion();
            }

            return false;

        } catch (Exception e) {
            System.err.println("Error al verificar aplicabilidad de promoción: " + e.getMessage());
            return false;
        }
    }

    /**
     * Calcula el descuento aplicable de una promoción
     *
     * @param promoId ID de la promoción
     * @param montoTotal Monto total del pedido
     * @return Monto del descuento, o 0.0 si no aplica
     */
    public Double calcularDescuento(Integer promoId, Double montoTotal) {
        try {
            if (montoTotal == null || montoTotal <= 0) {
                return 0.0;
            }

            DescuentosDTO promo = this.obtenerPorId(promoId);

            if (promo == null || !esVigente(promo, LocalDateTime.now())) {
                return 0.0;
            }

            // Calcular descuento según tipo de beneficio
            if (promo.getTipo_beneficio().esDescuentoPorcentaje()) {
                return montoTotal * (promo.getValor_beneficio() / 100.0);
            } else if (promo.getTipo_beneficio().esDescuentoFijo()) {
                // El descuento no puede ser mayor al monto total
                return Math.min(promo.getValor_beneficio().doubleValue(), montoTotal);
            } else if (promo.getTipo_beneficio().esEnvioGratis()) {
                // El valor del envío gratis lo maneja el sistema de envíos
                return 0.0;
            }

            return 0.0;

        } catch (Exception e) {
            System.err.println("Error al calcular descuento: " + e.getMessage());
            return 0.0;
        }
    }

}
