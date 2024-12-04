package br.com.ucsal.controller;

import java.io.IOException;

import br.com.ucsal.annotations.Rota;
import br.com.ucsal.persistencia.HSQLProdutoRepository;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Rota(value = "adicionarProduto")
public class ProdutoAdicionarServlet implements Command {

    private ProdutoService produtoService;

    public ProdutoAdicionarServlet() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();

        if ("GET".equalsIgnoreCase(method)) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            doPost(request, response);
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp");
        dispatcher.forward(request, response);
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");

        if (nome == null || nome.isEmpty() || precoStr == null || precoStr.isEmpty()) {
            request.setAttribute("erro", "Nome e preço são obrigatórios!");
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp").forward(request, response);
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);

            produtoService.adicionarProduto(nome, preco);

            request.setAttribute("sucesso", "Produto adicionado com sucesso!");

            response.sendRedirect("listarProdutos");

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "Preço inválido! Por favor, insira um valor numérico.");
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("erro", "Ocorreu um erro ao adicionar o produto: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp").forward(request, response);
        }
    }
}
