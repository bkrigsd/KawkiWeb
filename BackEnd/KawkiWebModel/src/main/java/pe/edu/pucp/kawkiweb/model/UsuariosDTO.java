package pe.edu.pucp.kawkiweb.model;

import pe.edu.pucp.kawkiweb.model.utilUsuario.TiposUsuarioDTO;
import java.time.LocalDateTime;

public class UsuariosDTO {

    private static Integer cantidadUsuarios = 0;

    private Integer usuarioId;
    private String nombre;
    private String apePaterno;
    private String dni;
    private String telefono;
    private String correo;
    private String codigo;
    private String nombreUsuario;
    private String contrasenha;
    private LocalDateTime fechaHoraCreacion;
    private TiposUsuarioDTO tipoUsuario;

    public UsuariosDTO() {
        UsuariosDTO.cantidadUsuarios++;
        this.usuarioId = null;
        this.nombre = null;
        this.apePaterno = null;
        this.dni = null;
        this.telefono = null;
        this.correo = null;
        this.codigo = "U-" + this.tipoUsuario + String.format("%04d", UsuariosDTO.cantidadUsuarios);
        this.nombreUsuario = null;
        this.contrasenha = null;
        this.fechaHoraCreacion = LocalDateTime.now();
        this.tipoUsuario = null;
    }

    public UsuariosDTO(Integer usuarioId, String nombre, String apePaterno,
            String dni, String telefono, String correo, String nombreUsuario,
            String contrasenha, TiposUsuarioDTO tipoUsuario) {

        UsuariosDTO.cantidadUsuarios++;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.codigo = "U-" + this.tipoUsuario + String.format("%04d", UsuariosDTO.cantidadUsuarios);
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;
        this.fechaHoraCreacion = LocalDateTime.now();
        this.tipoUsuario = tipoUsuario;
    }

    public UsuariosDTO(UsuariosDTO usuario) {
        this.usuarioId = usuario.usuarioId;
        this.nombre = usuario.nombre;
        this.apePaterno = usuario.apePaterno;
        this.dni = usuario.dni;
        this.telefono = usuario.telefono;
        this.correo = usuario.correo;
        this.codigo = usuario.codigo;
        this.nombreUsuario = usuario.nombreUsuario;
        this.contrasenha = usuario.contrasenha;
        this.fechaHoraCreacion = usuario.fechaHoraCreacion;
        this.tipoUsuario = usuario.tipoUsuario;
    }

    public void MostrarDetalleUsuario() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder referencia = new StringBuilder();

        referencia.append("Identificador del usuario: ").append(this.usuarioId).append('\n');
        referencia.append("Nombre del usuario: ").append(this.nombre).append(" ").append(this.apePaterno).append('\n');
        referencia.append("Teléfono del usuario: ").append(this.telefono).append('\n');
        referencia.append("Correo del usuario: ").append(this.correo).append('\n');
        referencia.append("Código del usuario: ").append(this.getCodigo()).append('\n');
        referencia.append("Tipo usuario: ").append(this.tipoUsuario).append('\n');

        return referencia.toString();
    }

    // Getters y Setters
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.setUsuarioId((Integer) usuarioId);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public TiposUsuarioDTO getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TiposUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public static Integer getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    public static void setCantidadUsuarios(Integer aCantidadUsuarios) {
        cantidadUsuarios = aCantidadUsuarios;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
