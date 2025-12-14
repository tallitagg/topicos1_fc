package org.acme.service;

import org.acme.dto.PedidoDTO;
import org.acme.dto.PedidoResponseDTO;
import org.acme.model.StatusPedido;
import org.acme.model.Usuario;

import java.util.List;

public interface PedidoService {

    List<PedidoResponseDTO> getAll();

    List<PedidoResponseDTO> findByUsuario(String username);

    PedidoResponseDTO atualizarStatus(Long idPedido, StatusPedido novoStatus);

    PedidoResponseDTO create(PedidoDTO dto, String username);

}