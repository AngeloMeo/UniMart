<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>ProdottoManager</title>

        <%@include file="general.jsp" %>
        <script src="${context}/js/paginatorProdotto.js" defer></script>
        <script src="${context}/js/scrollDetect.js" defer></script>

    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="adminPanel.jsp"%>

        <main>
            <form action="${context}/ProdottoManager/CreaProdotto" method="get">
                <button id="btn-crea">Crea Nuovo Prodotto</button>
            </form>

            <c:choose>
                <c:when test="${prodottoList == null}">
                    <h1>Nessun prodotto creato...</h1>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Codice IAN</th>
                                <th>Nome</th>
                                <th>Categoria</th>
                                <th>Prezzo</th>
                                <th>Foto</th>
                                <th>Gestisci</th>
                            </tr>
                        </thead>
                        <%@include file="partProdotto.jsp"%>
                    </table>
                </c:otherwise>
            </c:choose>
        </main>
        <%@include file="footer.jsp"%>
    </body>
</html>