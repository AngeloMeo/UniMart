<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Scheda Prodotto</title>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <fieldset>
         <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" height="100" width="100">
         <h3>CodiceIAN: ${prodotto.codiceIAN}</h3>
         <h3>Nome: ${prodotto.nome}</h3>
         <h3>Prezzo: ${prodotto.prezzo}</h3>
         <h3>Peso: ${prodotto.peso}</h3>
         <h3>Volume Occupato: ${prodotto.volumeOccupato}</h3>
         <h3>Descrizione: ${prodotto.descrizione}</h3>
      </fieldset>
   </body>
</html>
