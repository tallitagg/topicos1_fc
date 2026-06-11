package org.acme.mapper;

import org.acme.dto.PagamentoDTO;
import org.acme.exceptions.BusinessException;
import org.acme.model.FormaPagamento;
import org.acme.model.Pagamento;

import java.util.UUID;

public final class PagamentoMapper {

    private PagamentoMapper() {
    }

    public static Pagamento toEntity(PagamentoDTO dto) {
        if (dto == null) return null;

        FormaPagamento formaPagamento = parseFormaPagamento(dto.formaPagamento());

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setConfirmado(false);

        switch (formaPagamento) {
            case PIX -> pagamento.setChavePixCopiaCola(gerarChavePixRandom());
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
            case BOLETO -> {
                pagamento.setNumParcelas(1);
                pagamento.setLinhaDigitavel(gerarLinhaDigitavel());
                pagamento.setCodigoDeBarras(gerarCodigoBarras());
            }
        }
        return pagamento;
    }

    private static String gerarChavePixRandom() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    private static String gerarLinhaDigitavel() {
        return gerarNumero(47);
    }

    private static String gerarCodigoBarras() {
        return gerarNumero(44);
    }

    private static String gerarNumero(int tamanho) {
        StringBuilder stringBuilder = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++)
            stringBuilder.append((int) (Math.random() * 10));
        return stringBuilder.toString();
    }

    private static FormaPagamento parseFormaPagamento(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new BusinessException("formaPagamento é obrigatório", 400, "FORMA_PAGAMENTO_OBRIGATORIO");
        }

        String v = valor.trim();

        // tenta pelo nome do enum: CARTAO_CREDITO, PIX, etc.
        try {
            return FormaPagamento.valueOf(v.toUpperCase());
        } catch (Exception ignored) {
        }

        // tenta pelo LABEL: "Crédito", "Débito", "Pix", "Boleto"
        for (FormaPagamento formaPagamento : FormaPagamento.values()) {
            if (formaPagamento.LABEL.equalsIgnoreCase(v)) {
                return formaPagamento;
            }
        }

        throw new BusinessException("formaPagamento inválido: " + valor, 400, "FORMA_PAGAMENTO_INVALIDO");
    }
}