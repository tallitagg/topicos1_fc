package org.acme.service;

import org.acme.dto.TipoTampaDTO;
import org.acme.dto.TipoTampaResponseDTO;

import java.util.List;

public interface TipoTampaService {
    List<TipoTampaResponseDTO> findAll();

    TipoTampaResponseDTO findById(Long id);

    List<TipoTampaResponseDTO> findByDescricao(String descricao);

    List<TipoTampaResponseDTO> findByMaterial(String material);

    TipoTampaResponseDTO create(TipoTampaDTO dto);

    void update(Long id, TipoTampaDTO dto);

    void delete(Long id);
}
