package br.com.ucsal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view/*")
public class ProdutoController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        Map<String, Command> comandos = new HashMap<>();

        Reflections reflections = new Reflections("br.com.ucsal.controller");

        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> commandClass : classes) {
            try {
                Command command = commandClass.getDeclaredConstructor().newInstance();
                String path = "/" + commandClass.getSimpleName().replace("Servlet", "").toLowerCase();
                comandos.put(path, command);
            } catch (Exception e) {
                throw new ServletException("Erro ao instanciar comando: " + e.getMessage(), e);
            }
        }

        getServletContext().setAttribute("commands", comandos);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        Map<String, Command> comandos = (Map<String, Command>) request.getServletContext().getAttribute("commands");
        Command command = comandos.get(path);

        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comando não encontrado");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        Map<String, Command> comandos = (Map<String, Command>) request.getServletContext().getAttribute("commands");
        Command command = comandos.get(path);

        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comando não encontrado");
        }
    }
}
