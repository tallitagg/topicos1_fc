package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CadastroBasicoDTO;
import org.acme.dto.CadastroBasicoResponseDTO;
import org.acme.model.Cliente;
import org.acme.model.Usuario;
import org.acme.repository.ClienteRepository;

import java.util.ArrayList;

@ApplicationScoped
public class CadastroBasicoServiceImpl implements CadastroBasicoService {
    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HashService hashService;

    @Override
    @Transactional
    public CadastroBasicoResponseDTO create(CadastroBasicoDTO dto) {
        Cliente cliente = new Cliente();

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setPerfil(dto.perfil());

        cliente.setUsuario(usuario);
        cliente.setEndereco(new ArrayList<>());
        cliente.setCpf(dto.cpf());
        cliente.setPerfil(dto.perfil());

        clienteRepository.persist(cliente);

        return CadastroBasicoResponseDTO.valueOf(cliente);
    }

}