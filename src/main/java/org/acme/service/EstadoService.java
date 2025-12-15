package org.acme.service;

import org.acme.dto.EstadoDTO;
import org.acme.dto.EstadoResponseDTO;

import java.util.List;

public interface EstadoService {
    EstadoResponseDTO create(EstadoDTO dto);

    void update(EstadoDTO dto, Long id);

    List<EstadoResponseDTO> findAll();

    List<EstadoResponseDTO> findByName(String name);

    EstadoResponseDTO findById(Long id);
}