<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>CategoriaManager</title>

    <!--<link href="${pageContext.request.contextPath}/css/old/dashboardPages.css" type="text/css" rel="stylesheet">-->
    <script src="${pageContext.request.contextPath}/js/couponCategoria/general.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/couponCategoria/categoriaPage.js" defer></script>

    <%@include file="general.jsp"%>
  </head>
  <body class="sidenavpresent">
    <%@include file="header.jsp" %>
    <%@include file="adminPanel.jsp"%>

    <button id="btn1" onclick="modifyForCrea()">Crea Nuova Categoria</button>

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
                <button onclick="modifyForUpdateCategoria('${cat.nome}', ${cat.aliquota})">Modifica</button>
              </td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>

    <div id="creaModal" class="creaModal">
      <form class="creaModal-form" method="post">
          <div class="container">
            <h1>Crea Categoria</h1>
            <hr>
            <label for="name">Nome</label>
            <input type="text" name="nomecat" id="name" placeholder="name" >

            <label for="aliquota">Aliquota</label>
            <input type="number" name="ali" id="aliquota" placeholder="aliquota" required>

            <div class="clearfix" id="btnDiv">
          </div>
        </div>
      </form>
    </div>
    <%@include file="footer.jsp"%>
  </body>
</html>
