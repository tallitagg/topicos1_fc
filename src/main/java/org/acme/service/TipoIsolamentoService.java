package org.acme.service;

import org.acme.dto.TipoIsolamentoDTO;
import org.acme.dto.TipoIsolamentoResponseDTO;

import java.util.List;

public interface TipoIsolamentoService {
    List<TipoIsolamentoResponseDTO> findAll();

    TipoIsolamentoResponseDTO findById(Long id);

    List<TipoIsolamentoResponseDTO> findByDescricao(String descricao);

    List<TipoIsolamentoResponseDTO> findByEficienciaTermica(Long eficienciaTermica);

    TipoIsolamentoResponseDTO create(TipoIsolamentoDTO dto);

    void update(Long id, TipoIsolamentoDTO dto);

    void delete(Long id);
}
