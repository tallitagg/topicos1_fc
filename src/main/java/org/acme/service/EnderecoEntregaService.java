package org.acme.service;

import org.acme.dto.EnderecoEntregaDTO;
import org.acme.dto.EnderecoEntregaResponseDTO;

public interface EnderecoEntregaService {
    EnderecoEntregaResponseDTO create(EnderecoEntregaDTO dto);

    void update(EnderecoEntregaDTO dto, Long id);
}