<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <%@include file="general.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/prodottoShow.js" defer></script>
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
                    <h3 id="IAN">CodiceIAN: ${composto.prodotto.codiceIAN}</h3>
                    <h3>Nome: ${composto.prodotto.nome}</h3>
                    <h3>Quantità: ${composto.quantita}</h3>
                    <h3>Prezzo: <fmt:formatNumber type="number" maxFractionDigits="2" value="${composto.prezzo * composto.quantita}" /> &euro; (${composto.prezzo}&euro;/Unità)</h3>
                    <%@include file="partialProdotto.jsp"%>
                </fieldset>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</body>
</html>
