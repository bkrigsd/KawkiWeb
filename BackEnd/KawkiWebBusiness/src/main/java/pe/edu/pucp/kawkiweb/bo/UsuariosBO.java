package pe.edu.pucp.kawkiweb.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import pe.edu.pucp.kawkiweb.daoImp.UsuariosDAOImpl;
import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;

public class UsuariosBO {

    private UsuariosDAO usuarioDAO;

    // Patrones de validación
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}$");
    private static final Pattern RUC_PATTERN = Pattern.compile("^[0-9]{11}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^[0-9]{9}$");

    public UsuariosBO() {
        this.usuarioDAO = new UsuariosDAOImpl();
    }

    public Integer insertar(String nombre, String apePaterno, String dni,
            String telefono, String correo, String nombreUsuario,
            String contrasenha, TiposUsuarioDTO tipoUsuario) {

        try {
            // Validaciones básicas
            if (!validarDatosUsuario(nombre, apePaterno, dni, telefono, correo,
                    nombreUsuario, contrasenha, tipoUsuario)) {
                System.err.println("Error: Datos de usuario inválidos");
                return null;
            }

            // Validar que el correo no esté registrado
            if (existeCorreo(correo)) {
                System.err.println("Error: El correo ya está registrado");
                return null;
            }

            // Validar que el nombre de usuario no esté registrado
            if (existeNombreUsuario(nombreUsuario)) {
                System.err.println("Error: El nombre de usuario ya está registrado");
                return null;
            }

            // Validar que el DNI no esté registrado
            if (existeDni(dni)) {
                System.err.println("Error: El DNI ya está registrado");
                return null;
            }

            UsuariosDTO usuarioDTO = new UsuariosDTO();
            usuarioDTO.setNombre(nombre.trim());
            usuarioDTO.setApePaterno(apePaterno.trim());
            usuarioDTO.setDni(dni);
            usuarioDTO.setTelefono(telefono);
            usuarioDTO.setCorreo(correo.trim().toLowerCase());
            usuarioDTO.setNombreUsuario(nombreUsuario.trim().toLowerCase());
            usuarioDTO.setContrasenha(contrasenha);
            usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
            usuarioDTO.setTipoUsuario(tipoUsuario);

            return this.usuarioDAO.insertar(usuarioDTO);

        } catch (Exception e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public UsuariosDTO obtenerPorId(Integer usuarioId) {
        try {
            if (usuarioId == null || usuarioId <= 0) {
                System.err.println("Error: ID de usuario inválido");
                return null;
            }
            return this.usuarioDAO.obtenerPorId(usuarioId);

        } catch (Exception e) {
            System.err.println("Error al obtener usuario por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<UsuariosDTO> listarTodos() {
        try {
            List<UsuariosDTO> lista = this.usuarioDAO.listarTodos();
            return (lista != null) ? lista : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Integer modificar(Integer usuarioId, String nombre, String apePaterno,
            String dni, String telefono, String correo, String nombreUsuario,
            String contrasenha, TiposUsuarioDTO tipoUsuario) {

        try {
            // Validar ID
            if (usuarioId == null || usuarioId <= 0) {
                System.err.println("Error: ID de usuario inválido");
                return null;
            }

            // Validar que el usuario existe
            UsuariosDTO usuarioExistente = this.obtenerPorId(usuarioId);
            if (usuarioExistente == null) {
                System.err.println("Error: Usuario no encontrado");
                return null;
            }

            // Validaciones básicas
            if (!validarDatosUsuario(nombre, apePaterno, dni, telefono, correo,
                    nombreUsuario, contrasenha, tipoUsuario)) {
                System.err.println("Error: Datos de usuario inválidos");
                return null;
            }

            // Validar que el correo no esté en uso por otro usuario
            if (!correo.equalsIgnoreCase(usuarioExistente.getCorreo()) && existeCorreo(correo)) {
                System.err.println("Error: El correo ya está registrado por otro usuario");
                return null;
            }

            // Validar que el nombre de usuario no esté en uso por otro usuario
            if (!nombreUsuario.equalsIgnoreCase(usuarioExistente.getNombreUsuario())
                    && existeNombreUsuario(nombreUsuario)) {
                System.err.println("Error: El nombre de usuario ya está registrado por otro usuario");
                return null;
            }

            // Validar que el DNI no esté en uso por otro usuario
            if (!dni.equals(usuarioExistente.getDni()) && existeDni(dni)) {
                System.err.println("Error: El DNI ya está registrado por otro usuario");
                return null;
            }

            UsuariosDTO usuarioDTO = new UsuariosDTO();
            usuarioDTO.setUsuarioId(usuarioId);
            usuarioDTO.setNombre(nombre.trim());
            usuarioDTO.setApePaterno(apePaterno.trim());
            usuarioDTO.setDni(dni);
            usuarioDTO.setTelefono(telefono);
            usuarioDTO.setCorreo(correo.trim().toLowerCase());
            usuarioDTO.setNombreUsuario(nombreUsuario.trim().toLowerCase());
            usuarioDTO.setContrasenha(contrasenha);
            usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
            usuarioDTO.setTipoUsuario(tipoUsuario);

            return this.usuarioDAO.modificar(usuarioDTO);

        } catch (Exception e) {
            System.err.println("Error al modificar usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Integer eliminar(Integer usuarioId) {
        try {
            if (usuarioId == null || usuarioId <= 0) {
                System.err.println("Error: ID de usuario inválido");
                return null;
            }

            UsuariosDTO usuarioDTO = new UsuariosDTO();
            usuarioDTO.setUsuarioId(usuarioId);
            return this.usuarioDAO.eliminar(usuarioDTO);

        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valida los datos básicos de un usuario
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatosUsuario(String nombre, String apePaterno,
            String dni, String telefono, String correo, String nombreUsuario,
            String contrasenha, TiposUsuarioDTO tipoUsuario) {

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Validación: El nombre no puede estar vacío");
            return false;
        }

        if (nombre.trim().length() > 50) {
            System.err.println("Validación: El nombre es demasiado largo (máx. 100 caracteres)");
            return false;
        }

        // Validar apellido paterno
        if (apePaterno == null || apePaterno.trim().isEmpty()) {
            System.err.println("Validación: El apellido paterno no puede estar vacío");
            return false;
        }

        if (apePaterno.trim().length() > 50) {
            System.err.println("Validación: El apellido paterno es demasiado largo (máx. 100 caracteres)");
            return false;
        }

        // Validar DNI
        if (dni == null || !DNI_PATTERN.matcher(dni).matches()) {
            System.err.println("Validación: El DNI debe tener 8 dígitos");
            return false;
        }

        // Validar teléfono
        if (telefono == null || !TELEFONO_PATTERN.matcher(telefono).matches()) {
            System.err.println("Validación: El teléfono debe tener 9 dígitos");
            return false;
        }

        // Validar correo
        if (correo == null || !EMAIL_PATTERN.matcher(correo).matches()) {
            System.err.println("Validación: El correo electrónico no es válido");
            return false;
        }

        // Validar nombre de usuario
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            System.err.println("Validación: El nombre de usuario no puede estar vacío");
            return false;
        }

        if (nombreUsuario.trim().length() < 3 || nombreUsuario.trim().length() > 50) {
            System.err.println("Validación: El nombre de usuario debe tener entre 3 y 50 caracteres");
            return false;
        }

        // Validar contraseña
        if (contrasenha == null || contrasenha.length() < 8) {
            System.err.println("Validación: La contraseña debe tener al menos 8 caracteres");
            return false;
        }

        // Validar tipo de usuario
        if (tipoUsuario == null || tipoUsuario.getTipoUsuarioId() == null) {
            System.err.println("Validación: El tipo de usuario no puede ser null");
            return false;
        }

        return true;
    }

    /**
     * Verifica si un correo ya está registrado
     *
     * @param correo Correo a verificar
     * @return true si existe, false en caso contrario
     */
    private boolean existeCorreo(String correo) {
        try {
            List<UsuariosDTO> usuarios = this.listarTodos();
            return usuarios.stream()
                    .anyMatch(u -> u.getCorreo() != null
                    && u.getCorreo().equalsIgnoreCase(correo.trim()));
        } catch (Exception e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un nombre de usuario ya está registrado
     *
     * @param nombreUsuario Nombre de usuario a verificar
     * @return true si existe, false en caso contrario
     */
    private boolean existeNombreUsuario(String nombreUsuario) {
        try {
            List<UsuariosDTO> usuarios = this.listarTodos();
            return usuarios.stream()
                    .anyMatch(u -> u.getNombreUsuario() != null
                    && u.getNombreUsuario().equalsIgnoreCase(nombreUsuario.trim()));
        } catch (Exception e) {
            System.err.println("Error al verificar nombre de usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un DNI ya está registrado
     *
     * @param dni DNI a verificar
     * @return true si existe, false en caso contrario
     */
    private boolean existeDni(String dni) {
        try {
            List<UsuariosDTO> usuarios = this.listarTodos();
            return usuarios.stream()
                    .anyMatch(u -> u.getDni() != null && u.getDni().equals(dni));
        } catch (Exception e) {
            System.err.println("Error al verificar DNI: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista usuarios por tipo
     *
     * @param tipoUsuarioId ID del tipo de usuario
     * @return Lista de usuarios del tipo especificado
     */
    public List<UsuariosDTO> listarPorTipo(Integer tipoUsuarioId) {
        try {
            if (tipoUsuarioId == null || tipoUsuarioId <= 0) {
                return new ArrayList<>();
            }

            List<UsuariosDTO> todosLosUsuarios = this.listarTodos();
            List<UsuariosDTO> usuariosFiltrados = new ArrayList<>();

            for (UsuariosDTO usuario : todosLosUsuarios) {
                if (usuario.getTipoUsuario() != null
                        && tipoUsuarioId.equals(usuario.getTipoUsuario().getTipoUsuarioId())) {
                    usuariosFiltrados.add(usuario);
                }
            }

            return usuariosFiltrados;

        } catch (Exception e) {
            System.err.println("Error al listar usuarios por tipo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Cambia la contraseña de un usuario
     *
     * @param usuarioId ID del usuario
     * @param contrasenhaActual Contraseña actual
     * @param contrasenhaNueva Nueva contraseña
     * @return true si se cambió correctamente, false en caso contrario
     */
    public boolean cambiarContrasenha(Integer usuarioId, String contrasenhaActual,
            String contrasenhaNueva) {
        try {
            if (usuarioId == null || contrasenhaActual == null || contrasenhaNueva == null) {
                System.err.println("Error: Parámetros inválidos");
                return false;
            }

            // Validar longitud de nueva contraseña
            if (contrasenhaNueva.length() < 8) {
                System.err.println("Error: La nueva contraseña debe tener al menos 8 caracteres");
                return false;
            }

            UsuariosDTO usuario = this.obtenerPorId(usuarioId);
            if (usuario == null) {
                System.err.println("Error: Usuario no encontrado");
                return false;
            }

            // Verificar contraseña actual
            // En producción, usar hash comparison
            if (!usuario.getContrasenha().equals(contrasenhaActual)) {
                System.err.println("Error: La contraseña actual es incorrecta");
                return false;
            }

            // Actualizar contraseña
            Integer resultado = this.modificar(
                    usuario.getUsuarioId(),
                    usuario.getNombre(),
                    usuario.getApePaterno(),
                    usuario.getDni(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    usuario.getNombreUsuario(),
                    contrasenhaNueva,
                    usuario.getTipoUsuario()
            );

            return resultado != null && resultado > 0;

        } catch (Exception e) {
            System.err.println("Error al cambiar contraseña: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Autentica un usuario
     *
     * @param nombreUsuario Nombre de usuario o correo
     * @param contrasenha Contraseña
     * @return UsuariosDTO si las credenciales son válidas, null en caso
     * contrario
     */
    public UsuariosDTO autenticar(String nombreUsuario, String contrasenha) {
        try {
            if (nombreUsuario == null || contrasenha == null) {
                return null;
            }

            List<UsuariosDTO> usuarios = this.listarTodos();

            for (UsuariosDTO usuario : usuarios) {
                boolean coincideUsuario = usuario.getNombreUsuario() != null
                        && usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario.trim());
                boolean coincideCorreo = usuario.getCorreo() != null
                        && usuario.getCorreo().equalsIgnoreCase(nombreUsuario.trim());

                if ((coincideUsuario || coincideCorreo)
                        && usuario.getContrasenha() != null
                        && usuario.getContrasenha().equals(contrasenha)) {
                    return usuario;
                }
            }

            return null;

        } catch (Exception e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            return null;
        }
    }
}
