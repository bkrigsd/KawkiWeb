package pe.edu.pucp.kawkiweb.model.utilVenta;

public class RedesSocialesDTO {

    private Integer redSocialId;
    private String nombre;

    // Constantes para las redes sociales
    public static final int ID_FACEBOOK = 1;
    public static final int ID_INSTAGRAM = 2;
    public static final int ID_WHATSAPP = 2;

    public static final String NOMBRE_FACEBOOK = "Facebook";
    public static final String NOMBRE_INSTAGRAM = "Instagram";
    public static final String NOMBRE_WHATSAPP = "WhatsApp";

    public RedesSocialesDTO() {
        this.redSocialId = null;
        this.nombre = null;
    }

    public RedesSocialesDTO(Integer redSocialId, String nombre) {
        this.redSocialId = redSocialId;
        this.nombre = nombre;
    }

    public RedesSocialesDTO(RedesSocialesDTO redSocial) {
        this.redSocialId = redSocial.redSocialId;
        this.nombre = redSocial.nombre;
    }

    public Integer getRedSocialId() {
        return redSocialId;
    }

    public void setRedSocialId(Integer redSocialId) {
        this.redSocialId = redSocialId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esFacebook() {
        return this.redSocialId != null && this.redSocialId == ID_FACEBOOK;
    }

    public boolean esInstagram() {
        return this.redSocialId != null && this.redSocialId == ID_INSTAGRAM;
    }

    public boolean esWhatsApp() {
        return this.redSocialId != null && this.redSocialId == ID_WHATSAPP;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
