package com.baozistore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.baozistore.model.Produto;
import com.baozistore.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/produtos/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));


    }

    @PostMapping("/produtos")
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/produtos/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {

        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));


        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setEstoque(produto.getEstoque());

        return produtoRepository.save(produtoExistente);
    }

    @DeleteMapping("/produtos/{id}")
    public void excluirProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        produtoRepository.deleteById(id);

    }
}
