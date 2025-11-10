package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.kawkiweb.dao.EnvioDAO;
import pe.edu.pucp.kawkiweb.daoImp.EnvioDAOImpl;
import pe.edu.pucp.kawkiweb.model.EnvioDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.utilEnvio.CourierDTO;
import pe.edu.pucp.kawkiweb.model.utilEnvio.EstadoEnvioDTO;

public class EnvioBO {

    private EnvioDAO envioDAO;

    public EnvioBO() {
        this.envioDAO = new EnvioDAOImpl();
    }

    /**
     * Inserta un nuevo envío en la base de datos
     *
     * @return ID del envío insertado, o null si hubo error
     */
    public Integer insertar(Boolean es_delivery, String direccion_entrega,
            CourierDTO courier, LocalDateTime fecha_envio, Double costo_envio,
            VentasDTO pedido, EstadoEnvioDTO estado, LocalDateTime fecha_ultimo_estado) {

        try {
            // Validaciones
            if (!validarDatosEnvio(es_delivery, direccion_entrega, courier,
                    costo_envio, pedido, estado, fecha_ultimo_estado)) {
                System.err.println("Error: Datos de envío inválidos");
                return null;
            }

            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setEs_delivery(es_delivery);
            envioDTO.setDireccion_entrega(direccion_entrega);
            envioDTO.setCourier(courier);
            envioDTO.setFecha_envio(fecha_envio);
            envioDTO.setCosto_envio(costo_envio);
            envioDTO.setPedido(pedido);
            envioDTO.setEstado(estado);
            envioDTO.setFecha_ultimo_estado(fecha_ultimo_estado);

            return this.envioDAO.insertar(envioDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar envío: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene un envío por su ID
     *
     * @param envioId ID del envío a buscar
     * @return EnvioDTO encontrado, o null si no existe o hay error
     */
    public EnvioDTO obtenerPorId(Integer envioId) {
        try {
            if (envioId == null || envioId <= 0) {
                System.err.println("Error: ID de envío inválido");
                return null;
            }
            return this.envioDAO.obtenerPorId(envioId);

        } catch (Exception e) {
            System.err.println("Error al obtener envío por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Modifica un envío existente
     *
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer modificar(Integer envio_id, Boolean es_delivery,
            String direccion_entrega, CourierDTO courier, LocalDateTime fecha_envio,
            Double costo_envio, VentasDTO pedido, EstadoEnvioDTO estado,
            LocalDateTime fecha_ultimo_estado) {

        try {
            // Validar ID
            if (envio_id == null || envio_id <= 0) {
                System.err.println("Error: ID de envío inválido");
                return null;
            }

            // Validar datos
            if (!validarDatosEnvio(es_delivery, direccion_entrega, courier,
                    costo_envio, pedido, estado, fecha_ultimo_estado)) {
                System.err.println("Error: Datos de envío inválidos");
                return null;
            }

            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setEnvio_id(envio_id);
            envioDTO.setEs_delivery(es_delivery);
            envioDTO.setDireccion_entrega(direccion_entrega);
            envioDTO.setCourier(courier);
            envioDTO.setFecha_envio(fecha_envio);
            envioDTO.setCosto_envio(costo_envio);
            envioDTO.setPedido(pedido);
            envioDTO.setEstado(estado);
            envioDTO.setFecha_ultimo_estado(fecha_ultimo_estado);

            return this.envioDAO.modificar(envioDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar envío: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todos los envíos
     *
     * @return Lista de envíos, o lista vacía si hay error
     */
    public ArrayList<EnvioDTO> listarTodos() {
        try {
            ArrayList<EnvioDTO> lista = this.envioDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar envíos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Elimina un envío por su ID
     *
     * @param envio_id ID del envío a eliminar
     * @return Número de registros afectados, o null si hubo error
     */
    public Integer eliminar(Integer envio_id) {
        try {
            if (envio_id == null || envio_id <= 0) {
                System.err.println("Error: ID de envío inválido");
                return null;
            }

            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setEnvio_id(envio_id);
            return this.envioDAO.eliminar(envioDTO);

        } catch (Exception e) {
            System.err.println("Error al eliminar envío: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valida los datos básicos de un envío
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosEnvio(Boolean es_delivery, String direccion_entrega,
            CourierDTO courier, Double costo_envio, VentasDTO pedido,
            EstadoEnvioDTO estado, LocalDateTime fecha_ultimo_estado) {

        // Validar campos obligatorios
        if (es_delivery == null) {
            System.err.println("Validación: es_delivery no puede ser null");
            return false;
        }

        if (pedido == null || pedido.getPedido_id() == null) {
            System.err.println("Validación: Pedido no puede ser null");
            return false;
        }

        if (estado == null || estado.getEstadoEnvioId() == null) {
            System.err.println("Validación: Estado de envío no puede ser null");
            return false;
        }

        if (fecha_ultimo_estado == null) {
            System.err.println("Validación: Fecha de último estado no puede ser null");
            return false;
        }

        // Validar dirección si es delivery
        if (es_delivery && (direccion_entrega == null || direccion_entrega.trim().isEmpty())) {
            System.err.println("Validación: Dirección de entrega es obligatoria para delivery");
            return false;
        }

        // Validar courier si es delivery
        if (es_delivery && (courier == null || courier.getCourierId() == null)) {
            System.err.println("Validación: Courier es obligatorio para delivery");
            return false;
        }

        // Validar costo de envío
        if (costo_envio != null && costo_envio < 0) {
            System.err.println("Validación: Costo de envío no puede ser negativo");
            return false;
        }

        return true;
    }

    /**
     * Cambia el estado de un envío
     *
     * @param envio_id ID del envío
     * @param nuevoEstado Nuevo estado del envío
     * @return true si se cambió correctamente, false en caso contrario
     */
    public boolean cambiarEstado(Integer envio_id, EstadoEnvioDTO nuevoEstado) {
        try {
            if (envio_id == null || nuevoEstado == null) {
                System.err.println("Error: ID de envío o estado no pueden ser null");
                return false;
            }

            EnvioDTO envio = this.obtenerPorId(envio_id);
            if (envio == null) {
                System.err.println("Error: Envío no encontrado");
                return false;
            }

            // Actualizar estado y fecha
            envio.setEstado(nuevoEstado);
            envio.setFecha_ultimo_estado(LocalDateTime.now());

            Integer resultado = this.modificar(
                    envio.getEnvio_id(),
                    envio.getEs_delivery(),
                    envio.getDireccion_entrega(),
                    envio.getCourier(),
                    envio.getFecha_envio(),
                    envio.getCosto_envio(),
                    envio.getPedido(),
                    envio.getEstado(),
                    envio.getFecha_ultimo_estado()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al cambiar estado del envío: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
