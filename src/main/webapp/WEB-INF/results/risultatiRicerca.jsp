<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Risultati Ricerca</title>

      <%@include file="general.jsp" %>

      <script type="text/javascript" src="${pageContext.request.contextPath}/js/risultatiRicerca.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <main>
         <section class="flex-container flex-dirRow justify-content-center">
            <h1 class="flex-item-100">Risultati per ${categoria}</h1>

            <c:if test="${prodotti == null}">
               <h2 class="flex-item-100">Nessun elemento corrispondente</h2>
            </c:if>

            <c:forEach items="${prodotti}" var="prodotto">
               <fieldset id="${prodotto.codiceIAN}">
                  <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" height="100" width="100">
                  <h3>CodiceIAN: ${prodotto.codiceIAN}</h3>
                  <h3>Nome: ${prodotto.nome}</h3>
                  <h3>Prezzo: ${prodotto.prezzo} &euro;</h3>
               </fieldset>
            </c:forEach>
         </section>
      </main>

      <%@include file="footer.jsp"%>
   </body>
</html>