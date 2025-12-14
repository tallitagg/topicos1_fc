package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.MaterialDTO;
import org.acme.dto.MaterialResponseDTO;
import org.acme.model.Material;
import org.acme.repository.MaterialRepository;

import java.util.List;

@ApplicationScoped
public class MaterialServiceImpl implements MaterialService {

    @Inject
    MaterialRepository materialRepository;

    @Override
    public List<MaterialResponseDTO> findAll() {
        return materialRepository.listAll()
                .stream()
                .map(MaterialResponseDTO::valueOf)
                .toList();
    }

    @Override
    public MaterialResponseDTO findById(Long id) {
        Material material = materialRepository.findById(id);

        if (material == null)
            return null;

        return  MaterialResponseDTO.valueOf(material);
    }

    @Override
    public List<MaterialResponseDTO> findByTipo(String tipo) {
        return materialRepository.findByTipo(tipo)
                .stream()
                .map(MaterialResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public MaterialResponseDTO create(MaterialDTO dto) {
        Material material = new Material();

        material.setTipo(dto.tipo());
        material.setResistenciaTemperatura(dto.resistenciaTemperatura());

        materialRepository.persist(material);
        return MaterialResponseDTO.valueOf(material);
    }

    @Override
    @Transactional
    public void update(Long id, MaterialDTO dto) {
        Material material = materialRepository.findById(id);

        material.setTipo(dto.tipo());
        material.setResistenciaTemperatura(dto.resistenciaTemperatura());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }
}
