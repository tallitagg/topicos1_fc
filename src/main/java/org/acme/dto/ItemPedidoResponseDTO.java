package org.acme.dto;

import org.acme.model.ItemPedido;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        Double preco,
        Long idProduto,
        String nomeProduto
) {

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO(
                itemPedido.getId(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco(),
                itemPedido.getProduto().getId(),
                itemPedido.getProduto().getNome()
        );
    }

}
