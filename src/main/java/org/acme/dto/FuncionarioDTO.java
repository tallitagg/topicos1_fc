package org.acme.dto;

public record FuncionarioDTO(
        Double salario,
        String cargo,
        String username,
        String nome,
        String senha
) {

}
