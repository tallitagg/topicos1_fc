package org.acme.service;

import org.acme.dto.CorDTO;
import org.acme.dto.CorResponseDTO;
import java.util.List;

public interface CorService {
    List<CorResponseDTO> findAll();

    List<CorResponseDTO> findByName(String name);

    CorResponseDTO findById(Long id);

    CorResponseDTO create(CorDTO dto);

    void update(Long id, CorDTO dto);

    void delete(Long id);
}
