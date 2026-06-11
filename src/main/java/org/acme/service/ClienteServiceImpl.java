package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AtualizarPerfilDTO;
import org.acme.dto.ClienteDTO;
import org.acme.dto.ClienteResponseDTO;
import org.acme.exceptions.BusinessException;
import org.acme.exceptions.RecursoNaoEncontradoException;
import org.acme.model.Cliente;
import org.acme.repository.ClienteRepository;

import java.util.List;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HashService hashService;

    @Override
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.listAll()
                .stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);

        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findByUsername(String username) {
        return clienteRepository.findByUsername(username)
                .list()
                .stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public ClienteResponseDTO findMeuPerfil(String usernameLogado) {
        Cliente cliente = clienteRepository.findByUsernameExato(usernameLogado);

        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado.", Response.Status.NOT_FOUND);
        }

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id);

        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        if (dto.nome() != null) {
            cliente.getUsuario().setNome(dto.nome());
        }

        if (dto.username() != null) {
            cliente.getUsuario().setUsername(dto.username());
        }

        if (dto.email() != null) {
            cliente.getUsuario().setEmail(dto.email());
        }

        if (dto.telefone() != null) {
            cliente.getUsuario().setTelefone(dto.telefone());
        }

        if (dto.cpf() != null) {
            cliente.setCpf(dto.cpf());
        }

        if (dto.perfil() == null) {
            throw new BusinessException("Perfil obrigatório.", 400, "PERFIL_OBRIGATORIO");
        }

        cliente.setPerfil(dto.perfil());
        cliente.getUsuario().setPerfil(dto.perfil());

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO atualizarPerfilCliente(String usernameLogado, AtualizarPerfilDTO dto) {
        Cliente cliente = clienteRepository.findByUsernameExato(usernameLogado);

        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado.", Response.Status.NOT_FOUND);
        }

        String hashSenhaConfirmacao = hashService.getHashSenha(dto.senhaConfirmacao());

        if (!cliente.getUsuario().getSenha().equals(hashSenhaConfirmacao)) {
            throw new WebApplicationException("Senha de confirmação incorreta.", Response.Status.UNAUTHORIZED);
        }

        Cliente clienteComUsernameInformado = clienteRepository.findByUsernameExato(dto.username());

        if (clienteComUsernameInformado != null
                && !clienteComUsernameInformado.getId().equals(cliente.getId())) {
            throw new WebApplicationException("Este username já está em uso.", Response.Status.CONFLICT);
        }

        cliente.getUsuario().setNome(dto.nome());
        cliente.getUsuario().setUsername(dto.username());
        cliente.getUsuario().setEmail(dto.email());
        cliente.getUsuario().setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);

        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        clienteRepository.delete(cliente);
    }
}