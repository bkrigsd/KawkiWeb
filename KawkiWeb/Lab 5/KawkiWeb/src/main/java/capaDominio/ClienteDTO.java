package capaDominio;

import java.sql.Date;
import java.time.LocalDateTime;

public class ClienteDTO extends UsuarioDTO {

    //ATRIBUTOS
    //para cliente natural
    private String dni;
    private Date fecha_nacimiento;
    private String direccion;

    private String telefono;
    private String correo;

    //para cliente jurídico
    private String ruc;
    private String razon_social;
    private String direccion_fiscal;

    //CONSTRUCTORES
    public ClienteDTO() {
        super();
        this.dni = null;
        this.fecha_nacimiento = null;
        this.telefono = null;
        this.direccion = null;
        this.correo = null;
        this.ruc = null;
        this.razon_social = null;
        this.direccion_fiscal = null;
    }

    public ClienteDTO(
            Integer usuario_id,
            String nombre,
            String ape_paterno,
            String ape_materno,
            String nombre_usuario,
            String contraseña,
            LocalDateTime fecha_hora_creacion,
            String dni,
            Date fecha_nacimiento,
            String telefono,
            String direccion,
            String correo,
            String ruc,
            String razon_social,
            String direccion_fiscal) {

        super(usuario_id, nombre, ape_paterno, ape_materno, nombre_usuario,
                contraseña, fecha_hora_creacion);
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.ruc = ruc;
        this.razon_social = razon_social;
        this.direccion_fiscal = direccion_fiscal;
    }

    //GETTERS Y SETTERS
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
