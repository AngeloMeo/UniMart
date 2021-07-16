<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Scheda Prodotto</title>

      <%@include file="general.jsp" %>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/carrello/add2cart.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>

      <fieldset>
         <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" height="100" width="100">
         <h3 id="IAN" value="${prodotto.codiceIAN}">CodiceIAN: ${prodotto.codiceIAN}</h3>
         <h3>Nome: ${prodotto.nome}</h3>
         <h3>Prezzo: ${prodotto.prezzo}</h3>
         <h3>Peso: ${prodotto.peso}</h3>
         <h3>Volume Occupato: ${prodotto.volumeOccupato}</h3>
         <h3>Descrizione: ${prodotto.descrizione}</h3>
         <input type="number" id="quantity">
         <button type="submit" class="add2cart" id="add2cart">ADD</button>
      </fieldset>

      <%@include file="footer.jsp"%>
   </body>
</html>
