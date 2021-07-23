<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Scheda Prodotto</title>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/carrello/add2cart.js" defer></script>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <%@include file="header.jsp" %>

      <main class="flex-container justify-content-center footer-present">
         <fieldset class="flex-item-100 flex-container flex-dirRow">
            <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" class="flex-item-40 img-responsive">

            <div class="flex-item-50 flex-container">
               <h3 id="IAN" value="${prodotto.codiceIAN}" class="flex-item-100">CodiceIAN: ${prodotto.codiceIAN}</h3>
               <h3 class="flex-item-100">Nome: ${prodotto.nome}</h3>

               <c:set var="price"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${prodotto.prezzo+(prodotto.prezzo*prodotto.categoria.aliquota)/100}"/></c:set>
               <h3 class="flex-item-100">Prezzo: <c:out value="${price}"/>€</h3>

               <h3 class="flex-item-100">Peso: ${prodotto.peso}</h3>
               <h3 class="flex-item-100">Volume Occupato: ${prodotto.volumeOccupato}</h3>

               <div class="flex-item-100 flex-container justify-content-center col-gap row-gap">
                  <input type="number" id="quantity">
                  <button class="add2cart" onclick="add(${prodotto.codiceIAN}, this)">Aggiungi al carrello</button>
                  <%@include file="partialProdotto.jsp"%>
               </div>

            </div>
            <hr>
            <h3 class="flex-item-100">Descrizione: <br>${prodotto.descrizione}</h3>
         </fieldset>
      </main>

      <%@include file="footer.jsp"%>
   </body>
</html>
