package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record ProdutoDTO(
        @NotBlank String nome,
        String descricao,
        @NotNull @PositiveOrZero Double preco,
        @NotNull @Positive Double capacidade,
        @NotNull @Positive Integer estoque,
        @NotNull Long marcaId,
        @NotNull Long modeloId,
        @NotNull Long tipoTampaId,
        @NotNull Long tipoIsolamentoId,
        @NotNull Long materialId,
        List<Long> corIds
) {
}
