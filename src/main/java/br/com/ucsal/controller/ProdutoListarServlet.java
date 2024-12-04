package br.com.ucsal.controller;

import java.io.IOException;
import java.util.List;

import br.com.ucsal.annotations.Inject;
import br.com.ucsal.annotations.Rota;
import br.com.ucsal.model.Produto;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Rota(value = "listarProdutos")
public class ProdutoListarServlet implements Command {

    @Inject
    private ProdutoService produtoService;

    public ProdutoListarServlet() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Produto> produtos = produtoService.listarProdutos();

        request.setAttribute("produtos", produtos);

        request.getRequestDispatcher("/WEB-INF/views/produtolista.jsp").forward(request, response);
    }
}
