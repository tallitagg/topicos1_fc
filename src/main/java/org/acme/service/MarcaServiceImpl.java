package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.MarcaDTO;
import org.acme.dto.MarcaResponseDTO;
import org.acme.model.Marca;
import org.acme.model.Modelo;
import org.acme.repository.MarcaRepository;
import org.acme.repository.ModeloRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    ModeloRepository modeloRepository;

    @Override
    public List<MarcaResponseDTO> findAll() {
        return marcaRepository.listAll()
                .stream()
                .map(MarcaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<MarcaResponseDTO> findByName(String name) {
        return marcaRepository.findByNome(name)
                .stream()
                .map(MarcaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public MarcaResponseDTO findById(Long id) {
        Marca marca = marcaRepository.findById(id);
        return marca == null ? null : MarcaResponseDTO.valueOf(marca);
    }

    @Override
    @Transactional
    public MarcaResponseDTO create(MarcaDTO dto) {
        Marca marca = new Marca();

        marca.setNome(dto.nome());

        marca.setModelos(new ArrayList<>());

        marcaRepository.persist(marca);

        if (dto.modeloIds() != null && !dto.modeloIds().isEmpty()) {
            List<Modelo> modelos = modeloRepository.find("id in ?1", dto.modeloIds()).list();
            for (Modelo m : modelos) {
                m.setMarca(marca);
            }
            marca.getModelos().addAll(modelos);
        }

        return MarcaResponseDTO.valueOf(marca);
    }

    @Override
    @Transactional
    public void update(Long id, MarcaDTO dto) {
        Marca marca = marcaRepository.findById(id);
        if (marca == null) throw new IllegalArgumentException("Marca não encontrada: " + id);

        marca.setNome(dto.nome());

        if (dto.modeloIds() == null) return;

        var atuais = new java.util.HashMap<Long, Modelo>();
        if (marca.getModelos() != null) {
            for (Modelo m : marca.getModelos()) {
                atuais.put(m.getId(), m);
            }
        }

        var desejados = dto.modeloIds().isEmpty()
                ? java.util.Collections.<Modelo>emptyList()
                : modeloRepository.find("id in ?1", dto.modeloIds()).list();

        for (Modelo m : desejados) {
            if (!atuais.containsKey(m.getId())) {
                m.setMarca(marca);
                marca.getModelos().add(m);
            } else {
                atuais.remove(m.getId());
            }
        }

        for (Modelo mRemovido : atuais.values()) {
            long qtdProdutos = 0L;
            if (qtdProdutos > 0) {
                throw new jakarta.ws.rs.WebApplicationException(
                        "Não é possível remover o modelo '" + mRemovido.getNome()
                                + "' da marca porque há produtos vinculados (" + qtdProdutos + ").", 409);
            }
            marca.getModelos().remove(mRemovido);
        }
    }


    @Override
    @Transactional
    public void delete(Long id) {
        marcaRepository.deleteById(id);
    }
}
