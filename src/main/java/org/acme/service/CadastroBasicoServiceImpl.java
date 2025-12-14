package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CadastroBasicoDTO;
import org.acme.dto.CadastroBasicoResponseDTO;
import org.acme.model.Cliente;
import org.acme.model.Perfil;
import org.acme.model.Usuario;
import org.acme.repository.ClienteRepository;
import org.acme.repository.UsuarioRepository;

@ApplicationScoped
public class CadastroBasicoServiceImpl implements CadastroBasicoService {
    @Inject
    ClienteRepository clienteRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;



    @Override
    @Transactional
    public CadastroBasicoResponseDTO create(CadastroBasicoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setPerfil(Perfil.valueOf(dto.perfil()));

        usuarioRepository.persist(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setCep(dto.cep());
        cliente.setCpf(dto.cpf());
        cliente.setCidade(dto.cidade());
        cliente.setEstado(dto.estado());
        cliente.setUsuario(usuario);
        clienteRepository.persist(cliente);

        return CadastroBasicoResponseDTO.valueOf(cliente);
    }
}
