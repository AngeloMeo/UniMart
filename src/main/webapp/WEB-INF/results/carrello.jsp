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

    <main class="flex-container justify-content-center">
        <h1 class="flex-item-100">Carrello</h1>
        <hr>

        <c:choose>
            <c:when test="${cart.compostoList == null}">
                <h2>Nessun prodotto nel carrello...</h2>
            </c:when>

            <c:otherwise>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Foto</th>
                            <th>Codice IAN</th>
                            <th>Nome</th>
                            <th>Quantità</th>
                            <th>Prezzo</th>
                            <th>Gestisci</th>
                        </tr>
                    </thead>
                    <c:forEach items="${cart.compostoList}" var="composto">
                        <tr>
                            <td data-label="Foto">
                                <img src="${pageContext.request.contextPath}/file/${composto.prodotto.foto}" class="img-medium">
                                <h3 id="IAN" class="hide">${composto.prodotto.codiceIAN}</h3>
                            </td>
                            <td data-label="Codice IAN">
                                <h3>${composto.prodotto.codiceIAN}</h3>
                            </td>
                            <td data-label="Nome">
                                <h3>${composto.prodotto.nome}</h3>
                            </td>
                            <td data-label="Quantità">
                                <h3>${composto.quantita}</h3>
                            </td>
                            <td data-label="Prezzo">
                                <h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${composto.prezzo * composto.quantita}" /> &euro; <br> (${composto.prezzo}&euro;/Unità)</h3>
                            </td>
                            <td data-label="Gestisci">
                                <c:set scope="page" value="${composto.prodotto}" var="prodotto"></c:set>
                                <%@include file="partialProdotto.jsp"%>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </main>
    <%@include file="footer.jsp"%>
</body>
</html>
