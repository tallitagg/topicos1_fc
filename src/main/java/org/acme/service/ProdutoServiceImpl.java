package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.ProdutoDTO;
import org.acme.dto.ProdutoResponseDTO;
import org.acme.model.*;
import org.acme.repository.*;

import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    TipoTampaRepository tipoTampaRepository;

    @Inject
    TipoIsolamentoRepository tipoIsolamentoRepository;

    @Inject
    CorRepository corRepository;

    @Override
    public List<ProdutoResponseDTO> findAll() {
        return produtoRepository.listAll()
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id);

        if (produto == null)
            return null;

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome) {
        return produtoRepository.findByNome(nome)
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByPreco(Long preco) {
        return produtoRepository.findByPreco(preco)
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByMarca(String marca) {
        return produtoRepository.findByMarca(marca)
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByModelo(String modelo) {
        return produtoRepository.findByModelo(modelo)
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByMaterial(String material) {
        return produtoRepository.findByMaterial(material)
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByCapacidade(Double capacidade) {
        return produtoRepository.findByCapacidade(capacidade)
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public ProdutoResponseDTO create(ProdutoDTO dto) {

        Marca marca = marcaRepository.findById(dto.marcaId());

        Modelo modelo = modeloRepository.findById(dto.modeloId());

        TipoTampa tipoTampa = tipoTampaRepository.findById(dto.tipoTampaId());

        TipoIsolamento tipoIsolamento = tipoIsolamentoRepository.findById(dto.tipoIsolamentoId());

        Material material = materialRepository.findById(dto.materialId());

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setCapacidade(dto.capacidade());
        produto.setEstoque(dto.estoque());

        produto.setMarca(marca);
        produto.setModelo(modelo);
        produto.setTipoTampa(tipoTampa);
        produto.setTipoIsolamento(tipoIsolamento);
        produto.setMaterial(material);

        if (dto.corIds() != null && !dto.corIds().isEmpty()) {
            List<Cor> cores = corRepository.find("id in ?1", dto.corIds()).list();
            produto.setCores(new HashSet<>(cores));
        }

        produtoRepository.persist(produto);

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    @Transactional
    public void update(Long id, ProdutoDTO dto) {
        var produto = produtoRepository.findById(id);

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());           // Long
        produto.setCapacidade(dto.capacidade()); // Double
        produto.setEstoque(dto.estoque());

        var marca = marcaRepository.findById(dto.marcaId());

        var modelo = modeloRepository.findById(dto.modeloId());

        var tipoTampa = tipoTampaRepository.findById(dto.tipoTampaId());

        var tipoIsolamento = tipoIsolamentoRepository.findById(dto.tipoIsolamentoId());

        var material = materialRepository.findById(dto.materialId());

        produto.setMarca(marca);
        produto.setModelo(modelo);
        produto.setTipoTampa(tipoTampa);
        produto.setTipoIsolamento(tipoIsolamento);
        produto.setMaterial(material);

        if (dto.corIds() != null) {
            produto.getCores().clear();
            if (!dto.corIds().isEmpty()) {
                List<Cor> cores = corRepository.find("id in ?1", dto.corIds()).list();
                produto.getCores().addAll(new HashSet<>(cores));
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}
