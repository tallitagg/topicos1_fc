package org.acme.dto;

import org.acme.model.FormaPagamento;

import java.util.List;

public record PedidoDTO(
        String enderecoEntrega,
        FormaPagamento formaPagamento,
        List<ItemPedidoDTO> itensPedido
) {
}
