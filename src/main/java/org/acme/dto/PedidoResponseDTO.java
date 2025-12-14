package org.acme.dto;

import org.acme.model.FormaPagamento;
import org.acme.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime data,
        Double total,
        UsuarioResponseDTO usuario,
        List<ItemPedidoResponseDTO> itensPedido,
        String enderecoEntrega,
        FormaPagamento formaPagamento
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens =
                pedido.getItensPedido() == null
                        ? List.of()
                        : pedido.getItensPedido().stream().map(ItemPedidoResponseDTO::valueOf).toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getTotal(),
                UsuarioResponseDTO.valueOf(pedido.getUsuario()),
                itens,
                pedido.enderecoEntrega,
                pedido.getFormaPagamento()
        );
    }
}

