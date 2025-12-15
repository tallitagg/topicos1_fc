package org.acme.dto;

import org.acme.model.*;

import java.util.List;

public record PagamentoResponseDTO(
        Long id,
        Cliente cliente,
        StatusPedido statusPedido,
        Pagamento pagamento,
        EnderecoEntrega enderecoEntrega,
        Double frete,
        List<ItemPedidoResponseDTO> itemPedido
) {
    public static PagamentoResponseDTO valueOf(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens =
                pedido.getItensPedido() == null
                        ? List.of()
                        : pedido.getItensPedido()
                        .stream()
                        .map(ItemPedidoResponseDTO::valueOf)
                        .toList();

        return new PagamentoResponseDTO(
                pedido.getId(),
                pedido.getCliente(),
                pedido.getStatusPedido(),
                pedido.getPagamento(),
                pedido.getEnderecoEntrega(),
                pedido.getFrete(),
                itens
        );
    }
}