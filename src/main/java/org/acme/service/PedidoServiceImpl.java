package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ItemPedidoDTO;
import org.acme.dto.PedidoDTO;
import org.acme.dto.PedidoResponseDTO;
import org.acme.mapper.PagamentoMapper;
import org.acme.model.*;
import org.acme.repository.ClienteRepository;
import org.acme.repository.EnderecoEntregaRepository;
import org.acme.repository.PedidoRepository;
import org.acme.repository.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    private static final Logger LOGGER = Logger.getLogger(PedidoServiceImpl.class.getName());

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProdutoRepository produtoRepository;
    @Inject
    EnderecoEntregaRepository enderecoEntregaRepository;

    @Override
    public List<PedidoResponseDTO> getAll() {
        LOGGER.info("Buscando todos os pedidos");
        List<Pedido> pedidos = pedidoRepository.listAll();
        LOGGER.info("Total de pedidos encontrados: " + pedidos.size());

        return pedidos.stream()
                .map(PedidoResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public List<PedidoResponseDTO> findByUsuario(String username) {
        return pedidoRepository.findByUsuario(username)
                .stream()
                .map(PedidoResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO dto, String username) throws ConstraintViolationException {
        LOGGER.info("Criando novo pedido para usuário autenticado: " + username);

        if (username == null || username.isBlank()) {
            throw new WebApplicationException("Usuário inválido", Response.Status.BAD_REQUEST);
        }

        Cliente cliente = clienteRepository.findByUsername(username).firstResult();
        if (cliente == null) {
            throw new WebApplicationException("Usuário não encontrado", Response.Status.NOT_FOUND);
        }

        if (dto == null || dto.itens() == null || dto.itens().isEmpty()) {
            throw new WebApplicationException("Pedido sem itens", Response.Status.BAD_REQUEST);
        }

        if (dto.enderecoEntrega() == null) {
            throw new WebApplicationException("Endereço de entrega é obrigatório", Response.Status.BAD_REQUEST);
        }

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.PENDENTE);

        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
        enderecoEntrega.setRua(dto.enderecoEntrega().rua());
        enderecoEntrega.setNumero(dto.enderecoEntrega().numero());
        enderecoEntrega.setComplemento(dto.enderecoEntrega().complemento());
        enderecoEntrega.setBairro(dto.enderecoEntrega().bairro());
        enderecoEntrega.setCidade(dto.enderecoEntrega().cidade());
        enderecoEntrega.setEstado(dto.enderecoEntrega().estado());
        enderecoEntrega.setCep(normalizarCep(dto.enderecoEntrega().cep()));
        enderecoEntrega.setCliente(cliente);

        enderecoEntregaRepository.persist(enderecoEntrega);
        pedido.setEnderecoEntrega(enderecoEntrega);

        List<ItemPedido> itensPedido = new ArrayList<>();
        double total = 0.0;

        for (ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.idProduto());
            if (produto == null) {
                throw new WebApplicationException("Produto não encontrado: id=" + itemDTO.idProduto(), Response.Status.NOT_FOUND);
            }

            Integer estoqueAtual = produto.getEstoque();
            if (estoqueAtual == null) {
                estoqueAtual = 0;
            }

            if (estoqueAtual < itemDTO.quantidade()) {
                throw new WebApplicationException("Estoque insuficiente para o produto: " + produto.getNome(), Response.Status.BAD_REQUEST);
            }

            produto.setEstoque(estoqueAtual - itemDTO.quantidade());

            ItemPedido ip = new ItemPedido();
            ip.setPedido(pedido);
            ip.setProduto(produto);
            ip.setQuantidade(itemDTO.quantidade());
            ip.setPreco(produto.getPreco());

            total += ip.getPreco() * ip.getQuantidade();
            itensPedido.add(ip);
        }

        pedido.setItensPedido(itensPedido);

        pedido.setTotal(total);

        Pagamento pagamento = org.acme.mapper.PagamentoMapper.toEntity(dto.pagamento());
        pedido.setPagamento(pagamento);
        pedido.setFrete(0.0);

        pedidoRepository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO atualizarStatus(Long idPedido, StatusPedido novoStatus) {
        LOGGER.info("Atualizando status do pedido ID " + idPedido + " para " + novoStatus);

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null) {
            LOGGER.severe("Pedido não encontrado: id = " + idPedido);
            throw new WebApplicationException("Pedido não encontrado", Response.Status.NOT_FOUND);
        }

        pedido.setStatusPedido(novoStatus);
        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }



    private String normalizarCep(String cep) {
        if (cep == null) return null;

        return cep.replaceAll("\\D", "");
    }

}