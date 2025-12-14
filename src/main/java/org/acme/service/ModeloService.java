package org.acme.service;

import org.acme.dto.ModeloDTO;
import org.acme.dto.ModeloResponseDTO;

import java.util.List;

public interface ModeloService {
    List<ModeloResponseDTO> findAll();

    ModeloResponseDTO findById(Long id);

    List<ModeloResponseDTO> findByNome(String nome);

    List<ModeloResponseDTO> findByMarcaNome(String nomeMarca);

    List<ModeloResponseDTO> findByAnoLancamento(Integer anoLancamento);

    ModeloResponseDTO create(ModeloDTO dto);

    void update(Long id, ModeloDTO dto);

    void delete(Long id);
}
