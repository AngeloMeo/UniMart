<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
    <head>
        <title>Title</title>

        <%@include file="general.jsp" %>
    </head>
    <body>
        <c:forEach items="${list}" var = "product">

            <tr>
                <td>${product.nome}</td>
                <td>${product.descrizione}</td>
                <td>${product.prezzo}</td>
            </tr>

        </c:forEach>
    </body>
</html>
