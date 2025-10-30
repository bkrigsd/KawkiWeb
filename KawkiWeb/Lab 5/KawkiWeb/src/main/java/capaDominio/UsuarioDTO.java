/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaDominio;

import capaDominio.usuarioDetalle.TipoUsuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author helen
 */
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
    private TipoUsuario tipoUsuario;
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
    
    public UsuarioDTO(Integer usuarioId, String nombre, String apePaterno, String apeMaterno, String dni, LocalDate fechaNacimiento, String telefono, String direccion, String correo, String nombreUsuario, String contrasenha, LocalDateTime fechaHoraCreacion, TipoUsuario tipoUsuario, String ruc, String razon_social, String direccion_fiscal) {
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
    
    /**
     * @return the usuarioId
     */
    public Integer getUsuarioId() {
        return usuarioId;
    }

    /**
     * @param usuarioId the usuarioId to set
     */
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apePaterno
     */
    public String getApePaterno() {
        return apePaterno;
    }

    /**
     * @param apePaterno the apePaterno to set
     */
    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    /**
     * @return the apeMaterno
     */
    public String getApeMaterno() {
        return apeMaterno;
    }

    /**
     * @param apeMaterno the apeMaterno to set
     */
    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the fechaNacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the contrasenha
     */
    public String getContrasenha() {
        return contrasenha;
    }

    /**
     * @param contrasenha the contrasenha to set
     */
    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    /**
     * @return the fechaHoraCreacion
     */
    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    /**
     * @param fechaHoraCreacion the fechaHoraCreacion to set
     */
    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    /**
     * @return the tipoUsuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * @return the razon_social
     */
    public String getRazon_social() {
        return razon_social;
    }

    /**
     * @param razon_social the razon_social to set
     */
    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    /**
     * @return the direccion_fiscal
     */
    public String getDireccion_fiscal() {
        return direccion_fiscal;
    }

    /**
     * @param direccion_fiscal the direccion_fiscal to set
     */
    public void setDireccion_fiscal(String direccion_fiscal) {
        this.direccion_fiscal = direccion_fiscal;
    }
    
}
