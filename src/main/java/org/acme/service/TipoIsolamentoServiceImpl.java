package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.TipoIsolamentoDTO;
import org.acme.dto.TipoIsolamentoResponseDTO;
import org.acme.model.TipoIsolamento;
import org.acme.repository.TipoIsolamentoRepository;

import java.util.List;

@ApplicationScoped
public class TipoIsolamentoServiceImpl implements TipoIsolamentoService {

    @Inject
    TipoIsolamentoRepository tipoIsolamentoRepository;

    @Override
    public List<TipoIsolamentoResponseDTO> findAll() {
        return tipoIsolamentoRepository.listAll()
                .stream()
                .map(TipoIsolamentoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public TipoIsolamentoResponseDTO findById(Long id) {

        TipoIsolamento tipoIsolamento = tipoIsolamentoRepository.findById(id);

        if (tipoIsolamento == null)
            return null;

        return TipoIsolamentoResponseDTO.valueOf(tipoIsolamento);
    }

    @Override
    public List<TipoIsolamentoResponseDTO> findByDescricao(String descricao) {
        return tipoIsolamentoRepository.findByDescricao(descricao)
                .stream()
                .map(TipoIsolamentoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<TipoIsolamentoResponseDTO> findByEficienciaTermica(Long eficienciaTermica) {
        return tipoIsolamentoRepository.findByEficienciaTermica(eficienciaTermica)
                .stream()
                .map(TipoIsolamentoResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public TipoIsolamentoResponseDTO create(TipoIsolamentoDTO dto) {
        TipoIsolamento tipoIsolamento = new TipoIsolamento();

        tipoIsolamento.setDescricao(dto.descricao());
        tipoIsolamento.setEficienciaTermica(dto.eficienciaTermica());

        tipoIsolamentoRepository.persist(tipoIsolamento);

        return TipoIsolamentoResponseDTO.valueOf(tipoIsolamento);
    }


    @Override
    @Transactional
    public void update(Long id, TipoIsolamentoDTO dto) {
        TipoIsolamento tipoIsolamento = tipoIsolamentoRepository.findById(id);

        tipoIsolamento.setDescricao(dto.descricao());
        tipoIsolamento.setEficienciaTermica(dto.eficienciaTermica());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tipoIsolamentoRepository.deleteById(id);
    }
}
