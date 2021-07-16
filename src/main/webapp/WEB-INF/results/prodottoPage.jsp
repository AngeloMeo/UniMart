<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>ProdottoManager</title>

        <%@include file="general.jsp" %>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="adminPanel.jsp"%>

        <main>
            <form action="ProdottoManager/CreaProdotto" method="get">
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
                    <c:forEach items="${prodottoList}" var="prodotto">
                        <tr>
                            <td data-label="IAN">${prodotto.codiceIAN}</td>
                            <td data-label="Nome">${prodotto.nome}</td>
                            <td data-label="Categoria">${prodotto.categoria.nome}</td>
                            <td data-label="Prezzo">${prodotto.prezzo} &euro;</td>
                            <td data-label="Foto">
                            <c:if test="${not empty prodotto.foto}">
                                    <img src="file/${prodotto.foto}" class="img-medium">
                            </c:if>
                            </td>
                            <td data-label="Gestisci">
                                <form method="post" action="ProdottoManager/getProdotto">
                                    <button type="submit" class="btn btn-small" name="codiceIAN" value="${prodotto.codiceIAN}">Modifica</button>
                                </form>
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