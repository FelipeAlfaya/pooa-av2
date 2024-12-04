package br.com.ucsal.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.Map;

@WebListener
public class InicializadorListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Inicializando recursos na inicialização da aplicação");

        Map<String, Command> comandos = new HashMap<>();

        comandos.put("/listarProdutos", new ProdutoListarServlet());
        comandos.put("/adicionarProduto", new ProdutoAdicionarServlet());
        comandos.put("/editarProduto", new ProdutoEditarServlet());
        comandos.put("/excluirProduto", new ProdutoExcluirServlet());

        sce.getServletContext().setAttribute("commands", comandos);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Liberando recursos na finalização da aplicação");
    }
}
