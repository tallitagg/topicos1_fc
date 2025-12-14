package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.TipoTampaDTO;
import org.acme.dto.TipoTampaResponseDTO;
import org.acme.model.TipoTampa;
import org.acme.repository.TipoTampaRepository;

import java.util.List;

@ApplicationScoped
public class TipoTampaServiceImpl implements TipoTampaService {

    @Inject
    TipoTampaRepository tipoTampaRepository;

    @Override
    public List<TipoTampaResponseDTO> findAll() {
        return tipoTampaRepository.listAll()
                .stream()
                .map(TipoTampaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public TipoTampaResponseDTO findById(Long id) {
        TipoTampa tipoTampa = tipoTampaRepository.findById(id);

        if (tipoTampa == null)
            return null;
        return TipoTampaResponseDTO.valueOf(tipoTampa);
    }

    @Override
    public List<TipoTampaResponseDTO> findByDescricao(String descricao) {
        return tipoTampaRepository.findByDescricao(descricao)
                .stream()
                .map(TipoTampaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<TipoTampaResponseDTO> findByMaterial(String material) {
        return tipoTampaRepository.findByMaterial(material)
                .stream()
                .map(TipoTampaResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public TipoTampaResponseDTO create(TipoTampaDTO dto) {
        TipoTampa tipoTampa = new TipoTampa();

        tipoTampa.setDescricao(dto.descricao());
        tipoTampa.setMaterial(dto.material());

        tipoTampaRepository.persist(tipoTampa);

        return TipoTampaResponseDTO.valueOf(tipoTampa);
    }

    @Override
    @Transactional
    public void update(Long id, TipoTampaDTO dto) {
        TipoTampa tipoTampa = tipoTampaRepository.findById(id);

        tipoTampa.setDescricao(dto.descricao());
        tipoTampa.setMaterial(dto.material());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tipoTampaRepository.deleteById(id);
    }
}
