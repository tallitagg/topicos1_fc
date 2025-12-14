package org.acme.service;

import org.acme.dto.ModeloDTO;
import org.acme.dto.ModeloResponseDTO;
import org.acme.dto.ProdutoDTO;
import org.acme.dto.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {
    List<ProdutoResponseDTO> findAll();

    ProdutoResponseDTO findById(Long id);

    List<ProdutoResponseDTO> findByNome(String nome);

    List<ProdutoResponseDTO> findByPreco(Long preco);

    List<ProdutoResponseDTO> findByMarca(String marca);

    List<ProdutoResponseDTO> findByModelo(String modelo);

    List<ProdutoResponseDTO> findByMaterial(String material);

    List<ProdutoResponseDTO> findByCapacidade(Double capacidade);

    ProdutoResponseDTO create(ProdutoDTO dto);

    void update(Long id, ProdutoDTO dto);

    void delete(Long id);
}
