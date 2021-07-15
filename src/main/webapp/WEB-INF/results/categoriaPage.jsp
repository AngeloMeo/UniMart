<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>CategoriaManager</title>

    <script src="${pageContext.request.contextPath}/js/couponCategoriaOrdine/general.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/couponCategoriaOrdine/categoriaPage.js" defer></script>

    <%@include file="general.jsp"%>
  </head>
  <body>
    <%@include file="header.jsp" %>
    <%@include file="adminPanel.jsp"%>

    <main class="flex-container">
      <button id="btn-crea" onclick="modifyForCrea()">Crea Nuova Categoria</button>

      <c:choose>
        <c:when test="${categoriaList == null}">
          <h1>Nessuna Categoria creata...</h1>
        </c:when>

        <c:otherwise>
          <table>
            <thead>
              <tr>
                <th>Nome Categoria</th>
                <th>Aliquota</th>
                <td>Gestisci</td>
              </tr>
            </thead>

            <c:forEach items="${categoriaList}" var="cat">
              <tr>
                <td data-label="Nome">${cat.nome}</td>
                <td data-label="Aliquota">${cat.aliquota}</td>
                <td data-label="Gestisci">
                  <button class="btn btn-small" onclick="modifyForUpdateCategoria('${cat.nome}', ${cat.aliquota})">Modifica</button>
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
    </main>

    <%@include file="footer.jsp"%>
  </body>
</html>
