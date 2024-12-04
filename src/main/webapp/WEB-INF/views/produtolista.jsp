<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.ucsal.model.Produto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
</head>
<body>
<h2>Lista de Produtos</h2>
<a href="adicionarProduto">Adicionar Novo Produto</a><br/><br/>

<c:if test="${not empty produtos}">
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="produto" items="${produtos}">
            <tr>
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.preco}</td>
                <td>
                    <a href="editarProduto?id=${produto.id}">Editar</a>

                    <form action="excluirProduto" method="POST" style="display:inline;">
                        <input type="hidden" name="id" value="${produto.id}">
                        <button type="submit" onclick="return confirm('Tem certeza que deseja excluir este produto?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty produtos}">
    <p>Não há produtos cadastrados.</p>
</c:if>

</body>
</html>
