package org.acme.service;

import org.acme.dto.MarcaDTO;
import org.acme.dto.MarcaResponseDTO;
import org.acme.dto.MaterialDTO;
import org.acme.dto.MaterialResponseDTO;

import java.util.List;

public interface MaterialService {
    List<MaterialResponseDTO> findAll();

    MaterialResponseDTO findById(Long id);

    List<MaterialResponseDTO> findByTipo(String tipo);

    MaterialResponseDTO create(MaterialDTO dto);

    void update(Long id, MaterialDTO dto);

    void delete(Long id);
}
