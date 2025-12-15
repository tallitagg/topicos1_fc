package org.acme.model;

public enum StatusPedido {
    PENDENTE (1l, "Pendente"),
    PAGO (2l, "Pago"),
    ENVIADO (3l, "Enviado"),
    CANCELADO (4l, "Cancelado");

    public final Long ID;
    public final String LABEL;

    StatusPedido(Long id, String label) {
        this.ID = id;
        this.LABEL = label;
    }

    public static StatusPedido valueOf(Long id) {
        if (id == null)
            return null;

        for (StatusPedido statusPedido : StatusPedido.values())
            if (statusPedido.ID.equals(id))
                return statusPedido;

        throw new IllegalArgumentException("id inválido");

    }
}