package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CorDTO;
import org.acme.dto.CorResponseDTO;
import org.acme.model.Cor;
import org.acme.repository.CorRepository;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CorServiceImpl implements CorService {

    private static final Logger LOGGER = Logger.getLogger(CorServiceImpl.class.getName());

    @Inject
    CorRepository corRepository;

    @Override
    public List<CorResponseDTO> findAll() {
        return corRepository.listAll()
                .stream()
                .map(CorResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<CorResponseDTO> findByName(String name) {
        return corRepository.findByNome(name)
                .stream()
                .map(CorResponseDTO::valueOf)
                .toList();
    }

    @Override
    public CorResponseDTO findById(Long id) {
        Cor cor = corRepository.findById(id);

        if(cor == null)
            return null;

        return CorResponseDTO.valueOf(cor);
    }

    @Override
    @Transactional
    public CorResponseDTO create(CorDTO dto) {
        Cor cor = new Cor();

        cor.setNome(dto.nome());
        cor.setCodigoHex(dto.codigoHex());

        corRepository.persist(cor);

        return CorResponseDTO.valueOf(cor);
    }

    @Override
    @Transactional
    public void update(Long id, CorDTO dto) {
        Cor cor = corRepository.findById(id);

        cor.setNome(dto.nome());
        cor.setCodigoHex(dto.codigoHex());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        corRepository.deleteById(id);
    }
}
