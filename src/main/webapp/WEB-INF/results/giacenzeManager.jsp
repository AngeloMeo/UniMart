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
    <c:set var="index" scope="page" value="0"/>

    <c:if test="${inventario.possiedeList != null}">

        <c:forEach items="${inventario.possiedeList}" var="g">
            <div>
                <c:set var="index" scope="page" value="${index}+1"/>
                <label for="prodotto${index}">Prodotto:</label>
                <select id="prodotto${index}" name="prodotto${index}">
                    <option value="${g.prodotto.codiceIAN}" selected>${g.prodotto.nome}</option>

                    <c:forEach items="${productsList}" var="products">
                        <option value="${products.codiceIAN}">${products.nome}</option>
                    </c:forEach>
                </select>
                <label for="giacenza${index}">Giacenza:</label>
                <input type="number" name="giacenza${index}" id="giacenza${index}">

                <input type="checkbox" name="elimina${index}" id="elimina${index}" value="Elimina" onchange="">/*disabilita gli altri campi*/
            </div>
        </c:forEach>
    </c:if>

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
