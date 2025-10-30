package capaDominio.pedidoDetalle;

public enum Estado_Pedido {
    PENDIENTE (1),
    PAGADO (2),
    PREPARACION (3),
    LISTOPARAENTREGAR (4),
    ENTREGADO (5),
    ANULADO (6);
    
    private final Integer id;

    Estado_Pedido(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // Para buscar por id si viene de la BD
    public static Estado_Pedido fromId(Integer id) {
        for (Estado_Pedido estado : values()) {
            if (estado.id == id) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Id de estado no válido: " + id);
    }
    
}

