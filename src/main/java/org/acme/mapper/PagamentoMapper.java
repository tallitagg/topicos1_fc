package org.acme.mapper;

import org.acme.dto.PagamentoDTO;
import org.acme.model.FormaPagamento;
import org.acme.model.Pagamento;

import java.time.LocalDateTime;

public final class PagamentoMapper {

    private PagamentoMapper() {
    }

    public static Pagamento toEntity(PagamentoDTO dto) {
        if (dto == null) return null;

        FormaPagamento forma = parseFormaPagamento(dto.formaPagamento());

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(forma);
        pagamento.setConfirmado(false);
        pagamento.setDataPagamento(LocalDateTime.now());

        switch (forma) {
            case PIX -> {
                pagamento.setNumParcelas(1);
                pagamento.setChavePix(dto.chavePix());
            }
            case CARTAO_CREDITO -> {
                pagamento.setNumParcelas(dto.parcelas() == null ? 1 : dto.parcelas());
                pagamento.setNumeroCartao(dto.numeroCartao());
                pagamento.setNomeTitular(dto.nomeTitular());
                pagamento.setValidade(dto.validade());
                pagamento.setCvv(dto.cvv());
            }
            case CARTAO_DEBITO -> {
                pagamento.setNumParcelas(1);
                pagamento.setNumeroCartao(dto.numeroCartao());
                pagamento.setNomeTitular(dto.nomeTitular());
                pagamento.setValidade(dto.validade());
                pagamento.setCvv(dto.cvv());
            }
            case BOLETO -> pagamento.setNumParcelas(1);
        }

        return pagamento;
    }

    private static FormaPagamento parseFormaPagamento(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("formaPagamento é obrigatório");
        }

        String v = valor.trim();

        // tenta pelo nome do enum: CARTAO_CREDITO, PIX, etc.
        try {
            return FormaPagamento.valueOf(v.toUpperCase());
        } catch (Exception ignored) {
        }

        // tenta pelo LABEL: "Crédito", "Débito", "Pix", "Boleto"
        for (FormaPagamento fp : FormaPagamento.values()) {
            if (fp.LABEL.equalsIgnoreCase(v)) {
                return fp;
            }
        }

        throw new IllegalArgumentException("formaPagamento inválido: " + valor);
    }
}