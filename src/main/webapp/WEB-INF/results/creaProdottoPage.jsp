<%--
  Created by IntelliJ IDEA.
  User: angel
  Date: 01/06/2021
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>
  <%@include file="general.jsp"%>
</head>
<body class="sidenavpresent">
<jsp:include page="adminPanel.jsp">
    <jsp:param name="forward" value="true"/>
</jsp:include>

<form method="post">

    <c:if test="${prodotto.codiceIAN != null}">
        <label for="codiceIAN">Codice IAN</label>
        <input type="text" id="codiceIAN" name="codiceIAN" value="${prodotto.codiceIAN}" readonly>
    </c:if>

    <label for="nome">Nome</label>
    <input type="text" id="nome" name="nome" value="${prodotto.nome}" required>

    <label for="prezzo">Prezzo</label>
    <input type="text" id="prezzo" name="nome" value="${prodotto.prezzo}" required>

    <label for="peso">Peso</label>
    <input type="text" id="peso" name="peso" value="${prodotto.peso}" required>

    <label for="foto">Foto</label>
    <input type="file" id="foto" name="foto">

    <label for="volumeOccupato">Volume Occupato</label>
    <input type="number" id="volumeOccupato" name="volumeOccupato" value="${prodotto.value}">

    <label for="descrizione">Descrizione</label>
    <input type="text" id="descrizione" name="descrizione" value="${prodotto.descrizione}" required>

    <label for="categoria">Categoria</label>
    <select id="categoria" name="categoria">
        <c:forEach items="${categoria}" var="cat">
            <option>${cat.nome}</option>
        </c:forEach>
    </select>


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

</body>
</html>
