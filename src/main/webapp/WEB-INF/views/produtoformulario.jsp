<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="br.com.ucsal.model.Produto" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:choose><c:when test="${not empty produto}">Editar Produto</c:when><c:otherwise>Adicionar Produto</c:otherwise></c:choose></title>
</head>
<body>
<h2>
    <c:choose>
        <c:when test="${not empty produto}">Editar</c:when>
        <c:otherwise>Criar</c:otherwise>
    </c:choose> Produto
</h2>
<%
    Produto produto = (Produto) request.getAttribute("produto");
    String action = (produto == null) ? "adicionarProduto" : "editarProduto";
    String botao = (produto == null) ? "Adicionar" : "Atualizar";
%>
<form action="${pageContext.request.contextPath}/view/<%= action %>" method="post">
    <c:if test="${not empty produto}">
        <input type="hidden" name="id" value="${produto.id}">
    </c:if>
    Nome: <input type="text" name="nome" value="${produto != null ? produto.nome : ''}" /><br/>
    Preço: <input type="text" name="preco" value="${produto != null ? produto.preco : ''}" /><br/>
    <input type="submit" value="<%= botao %>" />
</form>
</body>
</html>
