package org.acme.dto;

import java.util.List;

public record PedidoDTO(
        EnderecoEntregaDTO enderecoEntrega,
        PagamentoDTO formaPagamento,
        List<ItemPedidoDTO> itens
) {

        public PagamentoDTO pagamento() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'pagamento'");
        }
}
