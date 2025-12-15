package org.acme.service;

import org.acme.dto.EstadoDTO;
import org.acme.dto.EstadoResponseDTO;

import java.util.List;

public class EstadoServiceImpl implements EstadoService{

    // TODO implementar os métodos
    @Override
    public EstadoResponseDTO create(EstadoDTO dto) {
        return null;
    }

    @Override
    public void update(EstadoDTO dto, Long id) {

    }

    @Override
    public List<EstadoResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public List<EstadoResponseDTO> findByName(String name) {
        return List.of();
    }

    @Override
    public EstadoResponseDTO findById(Long id) {
        return null;
    }
}