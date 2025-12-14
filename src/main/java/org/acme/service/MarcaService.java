package org.acme.service;

import org.acme.dto.MarcaDTO;
import org.acme.dto.MarcaResponseDTO;

import java.util.List;

public interface MarcaService {

    List<MarcaResponseDTO> findAll();

    List<MarcaResponseDTO> findByName(String name);

    MarcaResponseDTO findById(Long id);

    MarcaResponseDTO create(MarcaDTO dto);

    void update(Long id, MarcaDTO dto);

    void delete(Long id);

}
