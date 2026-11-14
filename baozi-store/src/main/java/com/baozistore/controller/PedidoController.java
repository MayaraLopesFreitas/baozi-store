package com.baozistore.controller;

import com.baozistore.model.Pedido;
import com.baozistore.repository.PedidoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido buscarPedidoPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @PostMapping
    public Pedido cadastrarPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido atualizarPedido(@PathVariable Long id,
                                  @RequestBody Pedido pedido) {

        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        pedidoExistente.setClienteId(pedido.getClienteId());
        pedidoExistente.setProdutoId(pedido.getProdutoId());
        pedidoExistente.setQuantidade(pedido.getQuantidade());

        return pedidoRepository.save(pedidoExistente);
    }

    @DeleteMapping("/{id}")
    public void excluirPedido(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        pedidoRepository.deleteById(id);
    }
}
