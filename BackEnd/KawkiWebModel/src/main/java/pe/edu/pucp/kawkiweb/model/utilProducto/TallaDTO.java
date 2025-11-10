package pe.edu.pucp.kawkiweb.model.utilProducto;

public class TallaDTO {

    private Integer talla_id;
    private Integer numero;

    // Constantes para los IDs de tallas
    public static final int ID_TREINTA_CINCO = 1;
    public static final int ID_TREINTA_SEIS = 2;
    public static final int ID_TREINTA_SIETE = 3;
    public static final int ID_TREINTA_OCHO = 4;
    public static final int ID_TREINTA_NUEVE = 5;

    public static final int NUMERO_TREINTA_CINCO = 35;
    public static final int NUMERO_TREINTA_SEIS = 36;
    public static final int NUMERO_TREINTA_SIETE = 37;
    public static final int NUMERO_TREINTA_OCHO = 38;
    public static final int NUMERO_TREINTA_NUEVE = 39;

    public TallaDTO() {
        this.talla_id = null;
        this.numero = null;
    }

    public TallaDTO(Integer talla_id, Integer numero) {
        this.talla_id = talla_id;
        this.numero = numero;
    }

    public TallaDTO(TallaDTO talla) {
        this.talla_id = talla.talla_id;
        this.numero = talla.numero;
    }

    public Integer getTalla_id() {
        return talla_id;
    }

    public void setTalla_id(Integer talla_id) {
        this.talla_id = talla_id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    // Métodos de utilidad para verificar el tipo
    public boolean esTreintaCinco() {
        return this.talla_id != null && this.talla_id == ID_TREINTA_CINCO;
    }

    public boolean esTreintaSeis() {
        return this.talla_id != null && this.talla_id == ID_TREINTA_SEIS;
    }

    public boolean esTreintaSiete() {
        return this.talla_id != null && this.talla_id == ID_TREINTA_SIETE;
    }

    public boolean esTreintaOcho() {
        return this.talla_id != null && this.talla_id == ID_TREINTA_OCHO;
    }

    public boolean esTreintaNueve() {
        return this.talla_id != null && this.talla_id == ID_TREINTA_NUEVE;
    }

    @Override
    public String toString() {
        return numero != null ? numero.toString() : "";
    }
}
