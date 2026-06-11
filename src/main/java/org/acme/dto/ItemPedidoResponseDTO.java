package org.acme.dto;

import org.acme.model.ItemPedido;
import org.acme.model.Produto;

public record ItemPedidoResponseDTO(
        Long id,
        Long idProduto,
        String nomeProduto,
        Integer quantidade,
        Double preco,
        String descricao
) {

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        Produto produto = itemPedido.getProduto();
        return new ItemPedidoResponseDTO(
                itemPedido.getId(),
                itemPedido.getProduto().getId(),
                itemPedido.getProduto().getNome(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco(),
                itemPedido.getProduto().getDescricao()
        );
    }

}