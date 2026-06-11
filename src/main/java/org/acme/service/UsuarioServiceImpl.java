package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AlterarSenhaDTO;
import org.acme.dto.UsuarioResponseDTO;
import org.acme.model.Usuario;
import org.acme.repository.UsuarioRepository;

import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    HashService hashService;

    @Override
    public List<Usuario> findAll() {
        return repository.listAll();
    }

    @Override
    public Usuario findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return repository.findByUsernameSenha(username, senha);
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UsuarioResponseDTO findResponseByUsername(String username) {
        return UsuarioResponseDTO.valueOf(repository.findByUsername(username));
    }

    @Override
    @Transactional
    public void alterarSenha(String usernameLogado, AlterarSenhaDTO dto) {
        Usuario usuario = repository.findByUsername(usernameLogado);

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado.", Response.Status.UNAUTHORIZED);
        }

        String hashSenhaAtual = hashService.getHashSenha(dto.senhaAtual());

        if (!usuario.getSenha().equals(hashSenhaAtual)) {
            throw new WebApplicationException("Senha atual incorreta.", Response.Status.UNAUTHORIZED);
        }

        String hashNovaSenha = hashService.getHashSenha(dto.novaSenha());

        usuario.setSenha(hashNovaSenha);
    }
}