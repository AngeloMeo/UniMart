<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
   <head>
      <title>Dettaglio Ordine</title>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <c:set var="ordine" scope="request" value="${requestScope.ordine}"/>

      <main class="flex-container">
         <h1 class="flex-item-100">Dettaglio Ordine</h1>
         <h3 class="flex-item-100">Stato: ${ordine.statoOrdine}</h3>

         <fieldset class="flex-item-100">
            <caption>Prodotti Ordinati</caption>
            <table class="table">
               <thead>
                  <tr>
                     <th>Nome Prodotto</th>
                     <th>Foto</th>
                     <th>Prezzo Acquisto</th>
                     <th>Quantità</th>
                     <th>Categoria</th>
                     <th>Peso</th>
                  </tr>
               </thead>

               <c:forEach items="${ordine.compostoList}" var="composto">
                  <tr>
                     <td data-label="Nome">${composto.prodotto.nome}</td>
                     <td data-label="Foto">
                        <img src="${pageContext.request.contextPath}/file/${composto.prodotto.foto}" class="img-medium" alt="Foto Prodotto" >
                     </td>
                     <td data-label="Prezzo Acquisto">${composto.prezzo} &euro;</td>
                     <td data-label="Quantità">${composto.quantita}</td>
                     <td data-label="Categoria">${composto.prodotto.categoria.nome}</td>
                     <td data-label="Peso">${composto.prodotto.peso}</td>
                  </tr>
               </c:forEach>

               <tr>
                  <td colspan="6">
                     Totale Senza Spedizione:
                     <fmt:formatNumber type="number" maxFractionDigits="2" value="${totaleSenzaSpedizione}" />
                     &euro;
                  </td>
               </tr>

               <tr>
                  <td colspan="6">
                     Totale:
                     <fmt:formatNumber type="number" maxFractionDigits="2" value="${totale}" />
                     &euro;
                  </td>
               </tr>

               <c:if test="${ordine.coupon != null}">
                  <tr>
                     <td colspan="6">
                        Totale con coupon:
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${totaleCoupon}" />
                        &euro;
                     </td>
                  </tr>
               </c:if>
            </table>
         </fieldset>

         <%@include file="partialOrder.jsp" %>

         <div class="flex-item-100">
            <button onclick="javascript:history.go(-1)" class="btn btn-verde">Torna Indietro</button>
         </div>
      </main>
   </body>
</html>