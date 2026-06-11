package org.acme.dto;

import org.acme.model.Cliente;

import java.util.List;

public record ClienteResponseDTO(
        Long id,
        UsuarioResponseDTO usuario,
        List<EnderecoEntregaResponseDTO> enderecoEntrega,
        String cpf
) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        List<EnderecoEntregaResponseDTO> listaEndereco = cliente.getEndereco() == null
                ? List.of()
                : cliente.getEndereco()
                        .stream()
                        .map(EnderecoEntregaResponseDTO::valueOf)
                        .toList();

        return new ClienteResponseDTO(
                cliente.getId(),
                UsuarioResponseDTO.valueOf(cliente.getUsuario()),
                listaEndereco,
                cliente.getCpf()
        );
    }
}