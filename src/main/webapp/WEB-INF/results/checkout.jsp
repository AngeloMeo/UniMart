<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="gradient2">
   <head>
      <title>Checkout</title>

      <%@include file="general.jsp"%>

      <script src="${context}/js/check/checkout.js" defer></script>
      <script src="${context}/js/validator.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>

      <main class="flex-container">
         <h1 class="flex-item-100">Checkout Ordine</h1>

         <hr>

         <form action="${context}/OrdiniManager/processOrdine" method="post" class="flex-item-100" id="form">
            <table class="flex-item-100">
               <tr class="flex-container">
                  <td class="flex-item-40 text-right"><label for="viaCivico">Via e Civico</label></td>
                  <td class="flex-item-40 text-left tooltip">
                     <span class="tooltiptext">Accetta il formato via xxx,civico</span>
                     <input type="text" name="viaCivico" id="viaCivico" placeholder="Via e Civico" value="${sessionScope.utente.viaCivico}" required>
                  </td>
               </tr>
               <tr class="flex-container">
                  <td class="flex-item-40 text-right"><label for="citta">Città</label></td>
                  <td class="flex-item-40 text-left tooltip">
                     <span class="tooltiptext">Accetta una stringa di lettere</span>
                     <input type="text" name="citta" id="citta" placeholder="Città" value="${sessionScope.utente.citta}" required>
                  </td>
               </tr>
               <tr class="flex-container">
                  <td class="flex-item-40 text-right"><label for="regione">Regione</label></td>
                  <td class="flex-item-40 text-left tooltip">
                     <span class="tooltiptext">Accetta una stringa di lettere</span>
                     <input type="text" name="regione" id="regione" placeholder="Regione" value="${sessionScope.utente.regione}" required>
                  </td>
               </tr>

               <tr class="flex-container">
                  <td class="flex-item-40 text-right"><label for="coupon">Numero Coupon</label></td>
                  <td class="flex-item-40 text-left tooltip">
                     <span class="tooltiptext">Accetta un numero</span>
                     <input type="text" name="coupon" id="coupon" placeholder="Numero Coupon" onchange="validateCoupon(this)">
                  </td>
               </tr>

               <tr class="flex-container">
                  <td class="flex-item-40 text-right">
                     <label for="spedizione">Spedizione</label>
                  </td>
                  <td class="flex-item-40 text-left">
                     <select id="spedizione" name="spedizione" required>
                        <c:forEach items="${spedizioni}" var="spedizione">
                           <option value="${spedizione.ID}">${spedizione.nome} costo: ${spedizione.costo}</option>
                        </c:forEach>
                     </select>
                  </td>
               </tr>

               <tr>
                  <td colspan="2"> <hr> </td>
               </tr>

               <tr>
                  <td colspan="2">
                     Totale:
                     <span id="totale">
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${totale}" />
                     </span>
                     &euro;
                  </td>
               </tr>
               <tr class=" hide">
                  <td colspan="2">
                     Totale con coupon: <span id="totaleCoupon"></span> &euro;
                  </td>
               </tr>

               <tr>
                  <td colspan="2"> <hr> </td>
               </tr>

               <tr class="flex-container justify-content-center">
                  <td>
                     <input type="button" value="Indietro" class="btn btn-secondary" onclick="javasript:window.history.back()">
                  </td>
                  <td>
                     <input type="submit" class="btn btn-verde" value="Acquista">
                  </td>
               </tr>
            </table>
         </form>
      </main>
   </body>
</html>