<%--
  Created by IntelliJ IDEA.
  User: angel
  Date: 01/06/2021
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ProdottoManager</title>
  <%@include file="general.jsp" %>
  <link href="./css/adminPages.css" type="text/css" rel="stylesheet">
</head>
<body class="sidenavpresent">

button

<c:choose>
    <c:when test="${prodottoList == null}">
        <h1>Nessun prodotto creato...</h1>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Codice IAN</th>
                <th>Nome</th>
                <th>Categoria</th>
                <th>Prezzo</th>
            </tr>
        <c:forEach items="${prodottoList}" var="prodotto">
            <tr>
                <td>${prodotto.codiceIAN}</td>
                <td>${prodotto.nome}</td>
                <td>${prodotto.categoria.nome}</td>
                <td>${prodotto.prezzo}</td>
            </tr>
        </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
