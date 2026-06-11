package org.acme.dto;

import org.acme.model.Produto;

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
        List<CorResponseDTO> cores,
        List<ArquivoResponseDTO> imagens
) {
    public static ProdutoResponseDTO valueOf(Produto produto) {
        List<CorResponseDTO> cores = produto.getCores() == null
                ? List.of()
                : produto.getCores()
                        .stream()
                        .map(CorResponseDTO::valueOf)
                        .toList();

        List<ArquivoResponseDTO> imagens = produto.getArquivos() == null
                ? List.of()
                : produto.getArquivos()
                        .stream()
                        .map(ArquivoResponseDTO::valueOf)
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
                cores,
                imagens
        );
    }
}