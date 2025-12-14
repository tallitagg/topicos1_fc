package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.ModeloDTO;
import org.acme.dto.ModeloResponseDTO;
import org.acme.model.Marca;
import org.acme.model.Modelo;
import org.acme.repository.MarcaRepository;
import org.acme.repository.ModeloRepository;

import java.util.List;

@ApplicationScoped
public class ModeloServiceImpl implements ModeloService {

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    public List<ModeloResponseDTO> findAll() {
        return modeloRepository.listAll()
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    @Override
    public ModeloResponseDTO findById(Long id) {
        Modelo modelo = modeloRepository.findById(id);

        if (modelo == null)
            return null;

        return ModeloResponseDTO.valueOf(modelo);
    }

    @Override
    public List<ModeloResponseDTO> findByNome(String nome) {
        return modeloRepository.findByNome(nome)
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ModeloResponseDTO> findByMarcaNome(String nomeMarca) {
        return modeloRepository.findByMarcaNome(nomeMarca)
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ModeloResponseDTO> findByAnoLancamento(Integer anoLancamento) {
        return modeloRepository.findByAnoLancamento(anoLancamento)
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public ModeloResponseDTO create(ModeloDTO dto) {
        Marca marca = marcaRepository.findById(dto.marcaId());
        if (marca == null) throw new IllegalArgumentException("Marca não encontrada: id=" + dto.marcaId());

        Modelo m = new Modelo();
        m.setNome(dto.nome());
        m.setAnoLancamento(dto.anoLancamento());
        m.setMarca(marca);

        modeloRepository.persist(m);

        if (marca.getModelos() != null && !marca.getModelos().contains(m)) {
            marca.getModelos().add(m);
        }

        return ModeloResponseDTO.valueOf(m);
    }

    @Override
    @Transactional
    public void update(Long id, ModeloDTO dto) {
        Modelo m = modeloRepository.findById(id);
        if (m == null) throw new IllegalArgumentException("Modelo não encontrado: id=" + id);

        m.setNome(dto.nome());
        m.setAnoLancamento(dto.anoLancamento());

        if (dto.marcaId() != null && (m.getMarca() == null || !dto.marcaId().equals(m.getMarca().getId()))) {
            Marca novaMarca = marcaRepository.findById(dto.marcaId());
            if (novaMarca == null) throw new IllegalArgumentException("Marca não encontrada: id=" + dto.marcaId());
            m.setMarca(novaMarca);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        modeloRepository.deleteById(id);
    }
}
