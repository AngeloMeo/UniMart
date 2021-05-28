<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: angel
  Date: 28/05/2021
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CategoriaManager</title>
  <link href="./css/adminPages.css" type="text/css" rel="stylesheet">
  <%@include file="general.jsp"%>
</head>
<body class="sidenavpresent">

<%@include file="adminPanel.jsp"%>

<button id="btn1">Crea Nuova Categoria</button>

<c:choose>
  <c:when test="${categoriaList == null}">
    <h1>Nessuna Categoria creata...</h1>
  </c:when>

  <c:otherwise>
    <table>
      <tr>
        <th>Nome Categoria</th>
        <th>Aliquota</th>
      </tr>
      <c:forEach items="${categoriaList}" var="cat">
        <tr>
          <td>${cat.nome}</td>
          <td>${cat.aliquota}</td>
          <td>
            <button>Modifica</button>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

</body>
</html>
