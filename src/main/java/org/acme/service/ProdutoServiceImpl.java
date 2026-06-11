package org.acme.service;

import io.vertx.codegen.Model;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.ProdutoDTO;
import org.acme.dto.ProdutoResponseDTO;
import org.acme.exceptions.RecursoNaoEncontradoException;
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
        if (marca == null)
            throw new RecursoNaoEncontradoException("Marca não encontrada: id=" + dto.marcaId());

        Modelo modelo = modeloRepository.findById(dto.modeloId());
        if (modelo == null)
            throw new RecursoNaoEncontradoException("Modelo não encontrado: id=" + dto.modeloId());

        TipoTampa tipoTampa = tipoTampaRepository.findById(dto.tipoTampaId());
        if (tipoTampa == null)
            throw new RecursoNaoEncontradoException("Tipo de tampa não encontrado: id=" + dto.tipoTampaId());

        TipoIsolamento tipoIsolamento = tipoIsolamentoRepository.findById(dto.tipoIsolamentoId());
        if (tipoIsolamento == null)
            throw new RecursoNaoEncontradoException("Tipo de Isolamento não encontrado: id=" + dto.tipoIsolamentoId());

        Material material = materialRepository.findById(dto.materialId());
        if (material == null)
            throw new RecursoNaoEncontradoException("Material não encontrado: id=" + dto.materialId());

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

        Produto produto = produtoRepository.findById(id);
        if (produto == null)
            throw new RecursoNaoEncontradoException("Produto não encontrado: id=" + id);

        Marca marca = marcaRepository.findById(dto.marcaId());
        if (marca == null)
            throw new RecursoNaoEncontradoException("Marca não encontrada: id=" + dto.marcaId());

        Modelo modelo = modeloRepository.findById(dto.modeloId());
        if (modelo == null)
            throw new RecursoNaoEncontradoException("Modelo não encontrado: id=" + dto.modeloId());

        TipoTampa tipoTampa = tipoTampaRepository.findById(dto.tipoTampaId());
        if (tipoTampa == null)
            throw new RecursoNaoEncontradoException("Tipo de tampa não foi encontrado: id=" + dto.tipoTampaId());

        TipoIsolamento tipoIsolamento = tipoIsolamentoRepository.findById(dto.tipoIsolamentoId());
        if (tipoIsolamento == null)
            throw new RecursoNaoEncontradoException("Tipo de Isolamento não foi encontrado: id=" + dto.tipoIsolamentoId());

        Material material = materialRepository.findById(dto.materialId());
        if (material == null)
            throw new RecursoNaoEncontradoException("Material não encontrado: id=" + dto.materialId());

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
        boolean deleted = produtoRepository.deleteById(id);
        if (!deleted)
            throw new RecursoNaoEncontradoException("Produto não encontrado: id=" + id);
    }
}