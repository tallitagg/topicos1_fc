package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ClienteDTO;
import org.acme.dto.ClienteResponseDTO;
import org.acme.dto.EnderecoEntregaDTO;
import org.acme.model.Cliente;
import org.acme.model.EnderecoEntrega;
import org.acme.repository.ClienteRepository;
import org.acme.repository.EnderecoEntregaRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService{
    @Inject
    ClienteRepository clienteRepository;

    @Inject
    EnderecoEntregaRepository enderecoEntregaRepository;

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
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
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
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
        }

        if (dto.nome() != null) cliente.getUsuario().setNome(dto.nome());
        if (dto.cpf() != null) cliente.setCpf(dto.cpf());

        if (dto.perfil() == null) {
            throw new WebApplicationException("perfil obrigatório", 400);
        }
        cliente.setPerfil(dto.perfil());

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
        }
        clienteRepository.delete(cliente);
    }
}