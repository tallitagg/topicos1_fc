package org.acme.service;

import jakarta.validation.Valid;
import org.acme.dto.CadastroBasicoDTO;
import org.acme.dto.CadastroBasicoResponseDTO;

public interface CadastroBasicoService {
    public CadastroBasicoResponseDTO create(@Valid CadastroBasicoDTO dto);
}
