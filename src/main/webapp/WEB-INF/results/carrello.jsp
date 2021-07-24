<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <%@include file="general.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/prodottoShow.js" defer></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/carrello/alterQuantity.js" defer></script>
</head>
<body>
    <%@include file="header.jsp" %>

    <main class="flex-container justify-content-center">
        <h1 class="flex-item-100">Carrello</h1>
        <hr>

        <c:choose>
            <c:when test="${cart.compostoList == null || cart.compostoList.size() == 0}">
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
                            <th>Rimuovi</th>
                        </tr>
                    </thead>
                    <c:forEach items="${cart.compostoList}" var="composto">
                        <tr id="${composto.prodotto.codiceIAN}">
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

                                <input type="number" value="${composto.quantita}" id="quantity" onchange="alter(this, ${composto.prodotto.codiceIAN})">

                            </td>
                            <td data-label="Prezzo">
                                <h3 id="price${composto.prodotto.codiceIAN}"><fmt:formatNumber type="number" maxFractionDigits="2" value="${composto.prezzo * composto.quantita}" /> &euro; <br> (${composto.prezzo}&euro;/Unità)</h3>
                            </td>

                            <td data-label="Rimuovi">
                                <button onclick="del(${composto.prodotto.codiceIAN})">Rimuovi</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <form action="${context}/OrdiniManager/saveOrdine" method="post">
                    <input type="submit" class="btn" value="Salva">
                </form>

                <form action="${context}/OrdiniManager/checkout" method="post">
                    <input type="submit" class="btn" value="Vai Al Checkout">
                </form>
            </c:otherwise>
        </c:choose>
        <hr>
        <c:if test="${sessionScope.utente != null}">
            <h1 class="flex-item-100">Carrelli Salvati</h1>
            <hr>
            <c:choose>
                <c:when test="${orders.get(0).compostoList != null}">
                    <div class="flex-item-100 flex-container flex-dirRow justify-content-center">
                        <c:forEach var="order" items="${orders}">
                            <details>
                                <summary>
                                    Ordine n°: ${order.numeroOrdine}
                                </summary>
                                <ul>
                                    <c:forEach var="prodList" items="${order.compostoList}">
                                        <li>
                                            <h4>${prodList.prodotto.nome}</h4>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <form action="${context}/CarrelloManager/saved2cart" method="post">
                                    <input type="hidden" name="orderID" value="${order.numeroOrdine}">
                                    <button type="submit" class="btn">Sposta nel carrello</button>
                                    <button type="submit" class="btn" formaction="${context}/CarrelloManager/deleteSaved">Elimina</button>
                                </form>
                            </details>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2 class="flex-item-100">Nessun Carrello Salvato...</h2>
                </c:otherwise>
            </c:choose>
        </c:if>

    </main>
    <%@include file="footer.jsp"%>
</body>
</html>
