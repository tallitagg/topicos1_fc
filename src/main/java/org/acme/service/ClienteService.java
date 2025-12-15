package org.acme.service;

import org.acme.dto.ClienteDTO;
import org.acme.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO findById(Long id);
    List<ClienteResponseDTO> findByUsername(String username);
    List<ClienteResponseDTO> findAll();
    ClienteResponseDTO update(Long id, ClienteDTO dto);
    void delete(Long id);
}