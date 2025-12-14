package org.acme.dto;

import org.acme.model.*;

import java.util.List;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer estoque,
        Double capacidade,
        MarcaResponseDTO marca,
        ModeloResponseDTO modelo,
        TipoTampaResponseDTO tipoTampa,
        TipoIsolamentoResponseDTO tipoIsolamento,
        MaterialResponseDTO material,
        List<CorResponseDTO> cores
) {
    public static ProdutoResponseDTO valueOf(Produto produto) {
        List<CorResponseDTO> cores = produto.getCores()
                .stream()
                .map(CorResponseDTO::valueOf)
                .toList();

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCapacidade(),
                MarcaResponseDTO.valueOf(produto.getMarca()),
                ModeloResponseDTO.valueOf(produto.getModelo()),
                TipoTampaResponseDTO.valueOf(produto.getTipoTampa()),
                TipoIsolamentoResponseDTO.valueOf(produto.getTipoIsolamento()),
                MaterialResponseDTO.valueOf(produto.getMaterial()),
                cores
                );
    }
}
