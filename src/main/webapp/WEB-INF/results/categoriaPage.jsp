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
          <table class="table">
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
                <td data-label="Aliquota">${cat.aliquota} &#37;</td>
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
          <div class="container flex-container flex-dirCol">
            <h1 class="flex-item-80">Crea Categoria</h1>

            <label for="name" class="flex-item-80">Nome</label>
            <input type="text" name="nomecat" id="name" placeholder="name" class="flex-item-80">

            <label for="aliquota" class="flex-item-80">Aliquota</label>
            <input type="number" name="ali" id="aliquota" placeholder="aliquota" class="flex-item-80" required>

            <div class="clearfix" id="btnDiv">
            </div>
          </div>
        </form>
      </div>
    </main>

    <%@include file="footer.jsp"%>
  </body>
</html>
