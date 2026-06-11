package org.acme.dto;

import org.acme.model.*;

import java.util.ArrayList;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Cliente cliente,
        StatusPedido statusPedido,
        Pagamento pagamento,
        EnderecoEntrega enderecoEntrega,
        Double frete,
        Double total,
        List<ItemPedidoResponseDTO> itemPedido,
        List<ParcelaResponseDTO> parcelas
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {

        List<ItemPedidoResponseDTO> itens =
                pedido.getItensPedido() == null ? List.of()
                        : pedido.getItensPedido().stream().map(ItemPedidoResponseDTO::valueOf).toList();

        double frete = pedido.getFrete() == null ? 0.0 : pedido.getFrete();
        double total = pedido.getTotal() == null ? 0.0 : pedido.getTotal();
        double totalFinal = total + frete;

        int numParcelas = 1;
        if (pedido.getPagamento() != null && pedido.getPagamento().getNumParcelas() != null) {
            numParcelas = Math.max(1, pedido.getPagamento().getNumParcelas());
        }

        List<ParcelaResponseDTO> parcelas = calcularParcelas(totalFinal, numParcelas);

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente(),
                pedido.getStatusPedido(),
                pedido.getPagamento(),
                pedido.getEnderecoEntrega(),
                pedido.getFrete(),
                pedido.getTotal(),
                itens,
                parcelas
        );
    }

    private static List<ParcelaResponseDTO> calcularParcelas(double total, int numParcelas) {
        numParcelas = Math.max(1, numParcelas);

        long totalCentavos = Math.round(total * 100.0);
        long base = totalCentavos / numParcelas;
        long resto = totalCentavos % numParcelas;

        List<ParcelaResponseDTO> list = new ArrayList<>();
        for (int i = 1; i <= numParcelas; i++) {
            long centavos = base + (i <= resto ? 1 : 0);
            list.add(new ParcelaResponseDTO(i, centavos / 100.0));
        }
        return list;
    }
}