package br.com.ucsal.controller;

import br.com.ucsal.util.ClassFinder;
import jakarta.servlet.http.HttpServlet;
import br.com.ucsal.annotations.Rota;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
public class RotaDinamicaServlet extends HttpServlet
{
    private final Map<String, Method> rotaParaMetodo = new HashMap<>();
    private final Map<String, Object> instanciaParaClasse = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            for (Class<?> clazz : ClassFinder.find("br.com.ucsal.controller")) {
                Object instancia = clazz.getDeclaredConstructor().newInstance();
                instanciaParaClasse.put(clazz.getName(), instancia);
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Rota.class)) {
                        Rota rota = method.getAnnotation(Rota.class);
                        rotaParaMetodo.put(rota.value(), method);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar o roteador", e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        Method metodo = rotaParaMetodo.get(path);

        if (metodo != null) {
            try {
                Object instancia = instanciaParaClasse.get(metodo.getDeclaringClass().getName());
                metodo.invoke(instancia, req, resp);
            } catch (Exception e) {
                throw new ServletException("Erro ao invocar método da rota", e);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Rota não encontrada");
        }
    }
}
