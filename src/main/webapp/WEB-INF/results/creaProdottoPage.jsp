<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>

        <%@include file="general.jsp"%>

        <link href="${pageContext.request.contextPath}/css/dashboardPages.css" type="text/css" rel="stylesheet">
    </head>
    <body class="sidenavpresent">
        <%@include file="header.jsp" %>
        <%@include file="adminPanel.jsp" %>

        <form method="post" enctype="multipart/form-data">
            <c:if test="${prodotto.codiceIAN != null}">
                <label for="codiceIAN">Codice IAN</label>
                <input type="text" id="codiceIAN" name="codiceIAN" value="${prodotto.codiceIAN}" readonly>
            </c:if>

            <br>

            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome" value="${prodotto.nome}" required>

            <br>

            <label for="prezzo">Prezzo</label>
            <input type="text" id="prezzo" name="prezzo" value="${prodotto.prezzo}" required>

            <br>

            <label for="peso">Peso</label>
            <input type="text" id="peso" name="peso" value="${prodotto.peso}" required>

            <br>

            <c:if test="${not empty prodotto.foto}">
                <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" height="100" width="100">
            </c:if>
            <label for="foto">Foto</label>
            <input type="file" id="foto" name="foto">

            <br>

            <label for="volumeOccupato">Volume Occupato</label>
            <input type="text" id="volumeOccupato" name="volumeOccupato" value="${prodotto.volumeOccupato}">

            <br>

            <label for="descrizione">Descrizione</label>
            <input type="textarea" id="descrizione" name="descrizione" value="${prodotto.descrizione}" required>

            <br>

            <label for="categoria">Categoria</label>
            <select id="categoria" name="categoria">
                <c:forEach items="${categoria}" var="cat">
                    <c:choose>
                        <c:when test="${prodotto.categoria.nome == cat.nome}">
                            <option selected>
                        </c:when>
                        <c:otherwise>
                            <option>
                        </c:otherwise>
                    </c:choose>

                    ${cat.nome}</option>
                </c:forEach>
            </select>

            <br>

            <c:choose>
                <c:when test="${prodotto.codiceIAN == null}">
                    <button type="submit" formaction="./creaProdotto">Crea Nuovo Prodotto</button>
                </c:when>

                <c:otherwise>
                    <button type="submit" formaction="./updateProdotto">Modifica Prodotto</button>
                    <button type="submit" formaction="./deleteProdotto">Elimina Prodotto</button>
                </c:otherwise>
            </c:choose>
        </form>
        <%@include file="footer.jsp"%>
    </body>
</html>
