package org.acme.dto;

import org.acme.model.EnderecoEntrega;
import org.acme.model.Perfil;

import java.util.List;

public record ClienteDTO(
        String nome,
        String username,
        String senha,
        String email,
        String telefone,
        String cpf,
        Perfil perfil
) {}