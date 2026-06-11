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
import org.acme.exceptions.BusinessException;
import org.acme.exceptions.EstoqueInsuficienteException;
import org.acme.exceptions.RecursoNaoEncontradoException;
import org.acme.exceptions.UnauthorizedException;
import org.acme.mapper.PagamentoMapper;
import org.acme.model.*;
import org.acme.repository.ClienteRepository;
import org.acme.repository.EnderecoEntregaRepository;
import org.acme.repository.PedidoRepository;
import org.acme.repository.ProdutoRepository;
import org.acme.dto.TipoTampaResponseDTO;

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
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);

        if (pedido == null)
            return null;
        return PedidoResponseDTO.valueOf(pedido);
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
            throw new UnauthorizedException("Usuário inválido ou não autenticado");
        }

        Cliente cliente = clienteRepository.findByUsername(username).firstResult();
        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }

        if (dto == null || dto.itens() == null || dto.itens().isEmpty()) {
            throw new BusinessException("Pedido sem itens", 400, "PEDIDO_SEM_ITENS");
        }

        if (dto.enderecoEntrega() == null) {
            throw new BusinessException("Endereço de entrega é obrigatório", 400, "ENDERECO_OBRIGATORIO");
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
            if (produto == null)
                throw new RecursoNaoEncontradoException("Produto não encontrado: id=" + itemDTO.idProduto());


            Integer estoqueAtual = produto.getEstoque();
            if (estoqueAtual == null)
                estoqueAtual = 0;


            if (estoqueAtual < itemDTO.quantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            produto.setEstoque(estoqueAtual - itemDTO.quantidade());
            produtoRepository.persist(produto);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.quantidade());
            itemPedido.setPreco(produto.getPreco());

            total += itemPedido.getPreco() * itemPedido.getQuantidade();
            itensPedido.add(itemPedido);
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
            throw new RecursoNaoEncontradoException("Pedido não encontrado");
        }

        pedido.setStatusPedido(novoStatus);

        if (novoStatus == StatusPedido.PAGO) {
            if (pedido.getPagamento() == null)
                pedido.setPagamento((new Pagamento()));

            if (pedido.getPagamento().getDataPagamento() == null)
                pedido.getPagamento().setDataPagamento(LocalDateTime.now());

            if (Boolean.FALSE.equals(pedido.getPagamento().getConfirmado()))
                pedido.getPagamento().setConfirmado(true);
        }

        return PedidoResponseDTO.valueOf(pedido);
    }

    private String normalizarCep(String cep) {
        if (cep == null) return null;

        return cep.replaceAll("\\D", "");
    }

}