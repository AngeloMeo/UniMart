<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Risultati Ricerca</title>

      <%@include file="general.jsp" %>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/carrello/add2cart.js" defer></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/prodottoShow.js" defer></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/orderSearchResult.js" defer></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/tinysort.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <main onload="orderSearchResult()">

         <section class="flex-container flex-dirRow justify-content-center">
            <label for="order">Ordina Per:</label>
            <select id="order" onchange="orderSearchResult()">
               <option disabled selected style="display:none">Filtra per...</option>
               <option value="Nome:Crescente">Nome:Crescente</option>
               <option value="Nome:Decrescente">Nome:Decrescente</option>
               <option value="IAN:Crescente">IAN:Crescente</option>
               <option value="IAN:Decrescente">IAN:Decrescente</option>
               <option value="Prezzo:Crescente">Prezzo:Crescente</option>
               <option value="Prezzo:Decrescente">Prezzo:Decrescente</option>
            </select>

            <h1 class="flex-item-100">Risultati per ${categoria}</h1>

            <c:choose>
               <c:when test="${requestScope.prodotti == null}">
                  <h2 class="flex-item-100">Nessun elemento corrispondente</h2>
               </c:when>
               <c:otherwise>

                  <c:forEach items="${requestScope.prodotti}" var="prodotto">
                     <fieldset class="prodotto">
                        <img src="${context}/file/${prodotto.foto}" class="img-medium">
                        <h3 id="IAN" value="${prodotto.codiceIAN}">CodiceIAN: ${prodotto.codiceIAN}</h3>
                        <h3 id="nome">${prodotto.nome}</h3>
                        <h3>Prezzo: <span id="prezzo" value="${prodotto.prezzo}">${prodotto.prezzo}</span>&euro;</h3>
                        <button class="add2cart" onclick="add(${prodotto.codiceIAN}, this)">Aggiungi al carrello</button>
                     </fieldset>
                  </c:forEach>

               </c:otherwise>
            </c:choose>
         </section>
      </main>

      <%@include file="footer.jsp"%>
   </body>
</html>