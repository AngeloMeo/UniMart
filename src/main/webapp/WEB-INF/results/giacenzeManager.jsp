<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${title}</title>
    <%@include file="general.jsp"%>
    <link href="./css/adminPages.css" type="text/css" rel="stylesheet">
</head>
<body>
    <%@include file="adminPanel.jsp" %>
    <c:set var="index" scope="page" value="0"></c:set>

    <c:choose>
        <c:when test="${prodottoList == null}">
            <h1>Nessun prodotto creato...</h1>
        </c:when>
        <c:otherwise>
            <form action="" method="post">
                <input type="hidden" name="codiceInventario" id="codiceInventario" value="${inventario.codiceInventario}">
                <table>
                    <tr>
                        <th>Codice IAN</th>
                        <th>Nome</th>
                        <th>Categoria</th>
                        <th>Prezzo</th>
                        <th>Foto</th>
                        <th>Giacenza</th>
                    </tr>
                    <c:forEach items="${productsList}" var="prodotto">
                        <tr>
                            <td class="tdSmall">${prodotto.codiceIAN}</td>
                            <input type="hidden" name="codiceIAN${index}" value="${prodotto.codiceIAN}">
                            <td class="tdSmall">${prodotto.nome}</td>
                            <input type="hidden" name="nome${index}" value="${prodotto.nome}">
                            <td class="tdSmall">${prodotto.categoria.nome}</td>
                            <input type="hidden" name="catNome${index}" value="${prodotto.categoria.nome}">
                            <td class="tdSmall">${prodotto.prezzo}</td>
                            <input type="hidden" name="prezzo${index}" value="${prodotto.prezzo}">
                            <td class="tdSmall">
                                <c:if test="${not empty prodotto.foto}">

                                    <img src="file/${prodotto.foto}" height="100" width="100">

                                </c:if>
                            </td>
                            <td class="tdSmall">
                                <input type="number" name="giacenza${index}" value="">
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </c:otherwise>
    </c:choose>


    <form method="post" action="GiacenzeManager/addGiacenze" >
        <div>

            <input type="hidden" name="codiceInventario" id="codiceInventario" value="${inventario.codiceInventario}">

            <c:set var="index" scope="page" value="${index}+1"/>

            <label for="prodotto">Prodotto:</label>
            <select id="prodotto" name="prodotto">

                <c:forEach items="${productsList}" var="products">
                    <option value="${products.codiceIAN}">${products.nome}</option>
                </c:forEach>

            </select>

            <label for="giacenza">Giacenza:</label>
            <input type="number" name="giacenza" id="giacenza">

            <input type="checkbox" name="elimina" id="elimina" value="Elimina" onchange="">/*disabilita gli altri campi*/
        </div>
        <button type="submit">Invio</button>
    </form>
</body>
</html>
