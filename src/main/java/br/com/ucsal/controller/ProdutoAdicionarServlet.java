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
        this.produtoService = new ProdutoService(new HSQLProdutoRepository()); // Instanciando manualmente o ProdutoService
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
        // Exibe o formulário de adição de produto
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp");
        dispatcher.forward(request, response);
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");

        // Valida se os campos nome e preço foram preenchidos
        if (nome == null || nome.isEmpty() || precoStr == null || precoStr.isEmpty()) {
            request.setAttribute("erro", "Nome e preço são obrigatórios!");
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o preço para double
            double preco = Double.parseDouble(precoStr);

            // Adiciona o produto
            produtoService.adicionarProduto(nome, preco);

            // Define uma mensagem de sucesso
            request.setAttribute("sucesso", "Produto adicionado com sucesso!");

            // Redireciona para a lista de produtos
            response.sendRedirect("listarProdutos");

        } catch (NumberFormatException e) {
            // Se o preço não for um número válido
            request.setAttribute("erro", "Preço inválido! Por favor, insira um valor numérico.");
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp").forward(request, response);
        } catch (Exception e) {
            // Trata outros erros, como falhas no serviço ou banco de dados
            request.setAttribute("erro", "Ocorreu um erro ao adicionar o produto: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp").forward(request, response);
        }
    }
}
