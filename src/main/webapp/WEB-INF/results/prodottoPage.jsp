<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ProdottoManager</title>
  <%@include file="general.jsp" %>
  <link href="./css/dashboardPages.css" type="text/css" rel="stylesheet">

</head>
<body class="sidenavpresent">

<form action="ProdottoManager/CreaProdotto" method="get">
    <button id="btn1">Crea Nuovo Prodotto</button>
</form>

<%@include file="adminPanel.jsp"%>
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
                <th>Foto</th>
            </tr>
        <c:forEach items="${prodottoList}" var="prodotto">
            <tr>
                <td class="tdSmall">${prodotto.codiceIAN}</td>
                <td class="tdSmall">${prodotto.nome}</td>
                <td class="tdSmall">${prodotto.categoria.nome}</td>
                <td class="tdSmall">${prodotto.prezzo}</td>
                <td class="tdSmall">
                <c:if test="${not empty prodotto.foto}">

                        <img src="file/${prodotto.foto}" height="100" width="100">

                </c:if>
                </td>
                <td class="tdSmall">
                <form method="post" action="ProdottoManager/getProdotto">
                    <button type="submit" class="tdSmall" name="codiceIAN" value="${prodotto.codiceIAN}">Modifica</button>
                </form>
                </td>
            </tr>
        </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
