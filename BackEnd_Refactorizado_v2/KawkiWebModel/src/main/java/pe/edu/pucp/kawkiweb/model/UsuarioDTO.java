package pe.edu.pucp.kawkiweb.model;

import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuarioDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioDTO {

    private Integer usuarioId;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String dni;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private String correo;
    private String nombreUsuario;
    private String contrasenha;
    private LocalDateTime fechaHoraCreacion;
    private TipoUsuarioDTO tipoUsuario;
    private String ruc;
    private String razon_social;
    private String direccion_fiscal;

    public UsuarioDTO() {
        this.usuarioId = null;
        this.nombre = null;
        this.apePaterno = null;
        this.apeMaterno = null;
        this.dni = null;
        this.fechaNacimiento = null;
        this.telefono = null;
        this.direccion = null;
        this.correo = null;
        this.nombreUsuario = null;
        this.contrasenha = null;
        this.fechaHoraCreacion = null;
        this.tipoUsuario = null;
        this.ruc = null;
        this.razon_social = null;
        this.direccion_fiscal = null;
    }

    public UsuarioDTO(Integer usuarioId, String nombre, String apePaterno, String apeMaterno,
            String dni, LocalDate fechaNacimiento, String telefono, String direccion,
            String correo, String nombreUsuario, String contrasenha,
            LocalDateTime fechaHoraCreacion, TipoUsuarioDTO tipoUsuario, String ruc,
            String razon_social, String direccion_fiscal) {

        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.tipoUsuario = tipoUsuario;
        this.ruc = ruc;
        this.razon_social = razon_social;
        this.direccion_fiscal = direccion_fiscal;
    }

    public UsuarioDTO(UsuarioDTO usuario) {
        this.usuarioId = usuario.usuarioId;
        this.nombre = usuario.nombre;
        this.apePaterno = usuario.apePaterno;
        this.apeMaterno = usuario.apeMaterno;
        this.dni = usuario.dni;
        this.fechaNacimiento = usuario.fechaNacimiento;
        this.telefono = usuario.telefono;
        this.direccion = usuario.direccion;
        this.correo = usuario.correo;
        this.nombreUsuario = usuario.nombreUsuario;
        this.contrasenha = usuario.contrasenha;
        this.fechaHoraCreacion = usuario.fechaHoraCreacion;
        this.tipoUsuario = usuario.tipoUsuario;
        this.ruc = usuario.ruc;
        this.razon_social = usuario.razon_social;
        this.direccion_fiscal = usuario.direccion_fiscal;
    }

    // Getters y Setters
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getApeMaterno() {
        return apeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public TipoUsuarioDTO getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion_fiscal() {
        return direccion_fiscal;
    }

    public void setDireccion_fiscal(String direccion_fiscal) {
        this.direccion_fiscal = direccion_fiscal;
    }
}
