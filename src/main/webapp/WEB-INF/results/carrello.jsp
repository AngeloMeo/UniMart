<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <%@include file="general.jsp"%>
</head>
<body>
<%@include file="header.jsp" %>
    <h1>Carrello</h1>
    <hr>

    <c:choose>
        <c:when test="${cart.compostoList == null}">
            <h2>Nessun prodotto nel carrello...</h2>
        </c:when>

        <c:otherwise>
            <c:forEach items="${cart.compostoList}" var="composto">
                <fieldset id="${composto.prodotto.codiceIAN}">
                    <img src="${pageContext.request.contextPath}/file/${composto.prodotto.foto}" height="100" width="100">
                    <h3>CodiceIAN: ${composto.prodotto.codiceIAN}</h3>
                    <h3>Nome: ${composto.prodotto.nome}</h3>
                    <h3>Prezzo: ${composto.prezzo}</h3>
                </fieldset>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</body>
</html>
