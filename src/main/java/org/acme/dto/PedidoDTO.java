package org.acme.dto;

import java.util.List;

public record PedidoDTO(
        EnderecoEntregaDTO enderecoEntrega,
        PagamentoDTO pagamento,
        List<ItemPedidoDTO> itens
) {
}