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
      <section>
         <h1>Risultati per ${categoria}</h1>

         <c:forEach items="${prodotti}" var="prodotto">
            <fieldset id="${prodotto.codiceIAN}">
               <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" height="100" width="100">
               <h3>CodiceIAN: ${prodotto.codiceIAN}</h3>
               <h3>Nome: ${prodotto.nome}</h3>
               <h3>Prezzo: ${prodotto.prezzo}</h3>
            </fieldset>
         </c:forEach>
      </section>
      <%@include file="footer.jsp"%>
   </body>
</html>