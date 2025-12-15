package org.acme.dto;

public record PagamentoDTO(
        String formaPagamento,
        Integer parcelas,
        String chavePix,
        String numeroCartao,
        String nomeTitular,
        String validade,
        String cvv,
        String codigoDeBarras,
        String linhaDigitavel
) {
}