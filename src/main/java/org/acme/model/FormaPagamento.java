package org.acme.model;

public enum FormaPagamento {

    CREDITO(1l, "Crédito"),
    DEBITO(2l, "Débito"),
    PIX(3l, "Pix");

    public final Long ID;
    public final String LABEL;

    FormaPagamento(Long id, String label) {
        this.ID = id;
        this.LABEL = label;
    }

    public static FormaPagamento valueOf(Long id){
        if (id == null){
            return null;
        }

        for (FormaPagamento formaPagamento : FormaPagamento.values()){
            if (formaPagamento.ID.equals(id)){
                return formaPagamento;
            }
        }
        throw new IllegalArgumentException("id inválido");
    }

}
