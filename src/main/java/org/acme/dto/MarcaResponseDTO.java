package org.acme.dto;

import org.acme.model.Marca;

import java.util.List;

public record MarcaResponseDTO(
        Long id,
        String nome,
        List<ModeloResponseDTO> modelos
) {
    public static MarcaResponseDTO valueOf(Marca marca) {

        List<ModeloResponseDTO> listaModelos = marca.getModelos()
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();

        return new MarcaResponseDTO(
                marca.getId(),
                marca.getNome(),
                listaModelos
        );
    }
}
