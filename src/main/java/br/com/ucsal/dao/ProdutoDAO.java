package br.com.ucsal.dao;

import br.com.ucsal.model.Produto;
import java.util.List;
import java.util.ArrayList;

public class ProdutoDAO {

    private static ProdutoDAO instance;

    private List<Produto> produtos = new ArrayList<>();

    private ProdutoDAO() {}

    public static ProdutoDAO getInstance() {
        if (instance == null) {
            instance = new ProdutoDAO();
        }
        return instance;
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public Produto buscarPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public void adicionar(Produto produto) {
        produto.setId(produtos.size() + 1);
        produtos.add(produto);
    }

    public void atualizar(Produto produto) {
        Produto produtoExistente = buscarPorId(produto.getId());
        if (produtoExistente != null) {
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setPreco(produto.getPreco());
        }
    }

    public void excluir(int id) {
        Produto produto = buscarPorId(id);
        if (produto != null) {
            produtos.remove(produto);
        }
    }
}
