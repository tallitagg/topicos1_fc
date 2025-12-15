package org.acme.model;

public enum Regiao {
    NORTE(1l, "Norte"),
    NORDESTE(2l, "Nordeste"),
    CENTRO_OESTE(3l, "Centro Oeste"),
    SUDESTE(4l, "Sudeste"),
    SUL(5l, "Sul");

    private final Long ID;
    private final String LABEL;

    Regiao(Long ID, String LABEL) {
        this.ID = ID;
        this.LABEL = LABEL;
    }

    public static Regiao valueOf(Long id) {
        if (id == null)
            return null;

        for (Regiao regiao : Regiao.values())
            if (regiao.ID.equals(id))
                return regiao;

        throw new IllegalArgumentException("id inválido");
    }
}