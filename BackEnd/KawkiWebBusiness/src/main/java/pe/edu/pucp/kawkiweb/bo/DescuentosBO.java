package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.DescuentosDAOImpl;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposBeneficioDTO;
import pe.edu.pucp.kawkiweb.model.utilDescuento.TiposCondicionDTO;
import pe.edu.pucp.kawkiweb.dao.DescuentosDAO;

public class DescuentosBO {

    private DescuentosDAO descuentoDAO;

    public DescuentosBO() {
        this.descuentoDAO = new DescuentosDAOImpl();
    }

    public Integer insertar(String descripcion, TiposCondicionDTO tipo_condicion,
            Integer valor_condicion, TiposBeneficioDTO tipo_beneficio,
            Integer valor_beneficio, LocalDateTime fecha_inicio,
            LocalDateTime fecha_fin, Boolean activo) {

        try {
            // Validaciones
            if (!validarDatosDescuento(descripcion, tipo_condicion, valor_condicion,
                    tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin)) {
                System.err.println("Error: Datos de promoción inválidos");
                return null;
            }

            DescuentosDTO descuentoDTO = new DescuentosDTO();
            descuentoDTO.setDescripcion(descripcion);
            descuentoDTO.setTipo_condicion(tipo_condicion);
            descuentoDTO.setValor_condicion(valor_condicion);
            descuentoDTO.setTipo_beneficio(tipo_beneficio);
            descuentoDTO.setValor_beneficio(valor_beneficio);
            descuentoDTO.setFecha_inicio(fecha_inicio);
            descuentoDTO.setFecha_fin(fecha_fin);
            descuentoDTO.setActivo(activo != null ? activo : true);

            return this.descuentoDAO.insertar(descuentoDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar promoción: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public DescuentosDTO obtenerPorId(Integer descuentoId) {
        try {
            if (descuentoId == null || descuentoId <= 0) {
                System.err.println("Error: ID de promoción inválido");
                return null;
            }
            return this.descuentoDAO.obtenerPorId(descuentoId);

        } catch (Exception e) {
            System.err.println("Error al obtener promoción por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DescuentosDTO> listarTodos() {
        try {
            List<DescuentosDTO> lista = this.descuentoDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar promociones: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Integer modificar(Integer descuentoId, String descripcion,
            TiposCondicionDTO tipo_condicion, Integer valor_condicion,
            TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio,
            LocalDateTime fecha_inicio, LocalDateTime fecha_fin, Boolean activo) {

        try {
            // Validar ID
            if (descuentoId == null || descuentoId <= 0) {
                System.err.println("Error: ID de promoción inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosDescuento(descripcion, tipo_condicion, valor_condicion,
                    tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin)) {
                System.err.println("Error: Datos de promoción inválidos");
                return null;
            }

            DescuentosDTO descuentoDTO = new DescuentosDTO();
            descuentoDTO.setDescuento_id(descuentoId);
            descuentoDTO.setDescripcion(descripcion);
            descuentoDTO.setTipo_condicion(tipo_condicion);
            descuentoDTO.setValor_condicion(valor_condicion);
            descuentoDTO.setTipo_beneficio(tipo_beneficio);
            descuentoDTO.setValor_beneficio(valor_beneficio);
            descuentoDTO.setFecha_inicio(fecha_inicio);
            descuentoDTO.setFecha_fin(fecha_fin);
            descuentoDTO.setActivo(activo);

            return this.descuentoDAO.modificar(descuentoDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar promoción: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//    public Integer eliminar(Integer descuentoId) {
//        try {
//            if (descuentoId == null || descuentoId <= 0) {
//                System.err.println("Error: ID de promoción inválido");
//                return null;
//            }
//
//            DescuentosDTO promoDTO = new DescuentosDTO();
//            promoDTO.setDescuento_id(descuentoId);
//            return this.descuentoDAO.eliminar(promoDTO);
//
//        } catch (Exception e) {
//            System.err.println("Error al eliminar promoción: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
    private boolean validarDatosDescuento(String descripcion,
            TiposCondicionDTO tipo_condicion, Integer valor_condicion,
            TiposBeneficioDTO tipo_beneficio, Integer valor_beneficio,
            LocalDateTime fecha_inicio, LocalDateTime fecha_fin) {

        // Validar descripción
        if (descripcion == null || descripcion.trim().isEmpty()) {
            System.err.println("Validación: La descripción no puede estar vacía");
            return false;
        }

        if (descripcion.trim().length() > 255) {
            System.err.println("Validación: La descripción es demasiado larga (máx. 500 caracteres)");
            return false;
        }

        // Validar tipo de condición
        if (tipo_condicion == null || tipo_condicion.getTipo_condicion_id() == null) {
            System.err.println("Validación: El tipo de condición no puede ser null");
            return false;
        }

        // Validar valor de condición
        if (valor_condicion == null || valor_condicion < 0) {
            System.err.println("Validación: El valor de condición debe ser mayor a 0");
            return false;
        }

        // Validar tipo de beneficio
        if (tipo_beneficio == null || tipo_beneficio.getTipo_beneficio_id() == null) {
            System.err.println("Validación: El tipo de beneficio no puede ser null");
            return false;
        }

        // Validar valor de beneficio
        if (valor_beneficio == null || valor_beneficio < 0) {
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

    public boolean activar(Integer descuentoId) {
        try {
            DescuentosDTO descuentoDTO = this.obtenerPorId(descuentoId);
            if (descuentoDTO == null) {
                System.err.println("Error: Promoción no encontrada");
                return false;
            }

            // Verificar que la promoción esté vigente
            LocalDateTime ahora = LocalDateTime.now();
            if (ahora.isBefore(descuentoDTO.getFecha_inicio()) || ahora.isAfter(descuentoDTO.getFecha_fin())) {
                System.err.println("Error: No se puede activar una promoción fuera de su periodo de vigencia");
                return false;
            }

            descuentoDTO.setActivo(true);
            Integer resultado = this.modificar(descuentoDTO.getDescuento_id(),
                    descuentoDTO.getDescripcion(),
                    descuentoDTO.getTipo_condicion(),
                    descuentoDTO.getValor_condicion(),
                    descuentoDTO.getTipo_beneficio(),
                    descuentoDTO.getValor_beneficio(),
                    descuentoDTO.getFecha_inicio(),
                    descuentoDTO.getFecha_fin(),
                    descuentoDTO.getActivo()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al activar promoción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean desactivar(Integer descuentoId) {
        try {
            DescuentosDTO descuentoDTO = this.obtenerPorId(descuentoId);
            if (descuentoDTO == null) {
                System.err.println("Error: Promoción no encontrada");
                return false;
            }

            descuentoDTO.setActivo(false);
            Integer resultado = this.modificar(descuentoDTO.getDescuento_id(),
                    descuentoDTO.getDescripcion(),
                    descuentoDTO.getTipo_condicion(),
                    descuentoDTO.getValor_condicion(),
                    descuentoDTO.getTipo_beneficio(),
                    descuentoDTO.getValor_beneficio(),
                    descuentoDTO.getFecha_inicio(),
                    descuentoDTO.getFecha_fin(),
                    descuentoDTO.getActivo()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al desactivar promoción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista descuentos activos usando stored procedure
     */
    public List<DescuentosDTO> listarActivas() {
        try {
            // Usar el método del DAO que llama al stored procedure
            return this.descuentoDAO.listarActivas();

        } catch (Exception e) {
            System.err.println("Error al listar promociones activas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista descuentos vigentes usando stored procedure
     */
    public List<DescuentosDTO> listarVigentes() {
        try {
            // Usar el método del DAO que llama al stored procedure
            return this.descuentoDAO.listarVigentes();

        } catch (Exception e) {
            System.err.println("Error al listar promociones vigentes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean esVigente(DescuentosDTO descuento, LocalDateTime fecha) {
        return descuento.getActivo() != null && descuento.getActivo()
                && descuento.getFecha_inicio() != null && !fecha.isBefore(descuento.getFecha_inicio())
                && descuento.getFecha_fin() != null && !fecha.isAfter(descuento.getFecha_fin());
    }

    /**
     * Verifica si una promoción es aplicable a un pedido
     *
     * @param descuentoId ID de la promoción
     * @param cantidadProductos Cantidad de productos en el pedido
     * @param montoTotal Monto total del pedido
     * @return true si la promoción es aplicable, false en caso contrario
     */
    public boolean esAplicable(Integer descuentoId, Integer cantidadProductos,
            Double montoTotal) {
        try {
            DescuentosDTO descuentoDTO = this.obtenerPorId(descuentoId);

            if (descuentoDTO == null || !esVigente(descuentoDTO, LocalDateTime.now())) {
                return false;
            }

            // Verificar condición
            if (descuentoDTO.getTipo_condicion().esCantidadMinimaProductos()) {
                return cantidadProductos != null
                        && cantidadProductos >= descuentoDTO.getValor_condicion();
            } else if (descuentoDTO.getTipo_condicion().esMontoMinimoCompra()) {
                return montoTotal != null
                        && montoTotal >= descuentoDTO.getValor_condicion();
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
     * @param descuentoId ID de la promoción
     * @param montoTotal Monto total del pedido
     * @return Monto del descuento, o 0.0 si no aplica
     */
    public Double calcularDescuento(Integer descuentoId, Double montoTotal) {
        try {
            if (montoTotal == null || montoTotal <= 0) {
                return 0.0;
            }

            DescuentosDTO descuentoDTO = this.obtenerPorId(descuentoId);

            if (descuentoDTO == null || !esVigente(descuentoDTO, LocalDateTime.now())) {
                return 0.0;
            }

            // Calcular descuento según tipo de beneficio
            if (descuentoDTO.getTipo_beneficio().esDescuentoPorcentaje()) {
                return montoTotal * (descuentoDTO.getValor_beneficio() / 100.0);
            } else if (descuentoDTO.getTipo_beneficio().esDescuentoFijo()) {
                // El descuento no puede ser mayor al monto total
                return Math.min(descuentoDTO.getValor_beneficio().doubleValue(), montoTotal);
            } else if (descuentoDTO.getTipo_beneficio().esEnvioGratis()) {
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
