package br.com.ucsal.controller;

import br.com.ucsal.annotations.Rota;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.persistencia.HSQLProdutoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Rota(value = "excluirProduto")
public class ProdutoExcluirServlet implements Command {

	private ProdutoService produtoService;

	public ProdutoExcluirServlet() {
		this.produtoService = new ProdutoService(new HSQLProdutoRepository());
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));

			produtoService.removerProduto(id);

			response.sendRedirect("listarProdutos");

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
		}
	}
}
