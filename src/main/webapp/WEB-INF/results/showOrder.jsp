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

      <h1>Dettaglio Ordine</h1>
      <h3>Stato: ${ordine.statoOrdine}</h3>

      <fieldset>
         <caption>Prodotti Ordinati</caption>
         <table>
            <tr>
               <th>Nome Prodotto</th>
               <th>Foto</th>
               <th>Prezzo Acquisto</th>
               <th>Quantità</th>
               <th>Categoria</th>
               <th>Peso</th>
            </tr>

            <c:forEach items="${ordine.compostoList}" var="composto">
               <tr>
                  <td>${composto.prodotto.nome}</td>
                  <td>
                     <img src="${pageContext.request.contextPath}/file/${composto.prodotto.foto}" height="100" width="100" alt="Foto Prodotto" >
                  </td>
                  <td>${composto.prezzo}</td>
                  <td>${composto.quantita}</td>
                  <td>${composto.prodotto.categoria.nome}</td>
                  <td>${composto.prodotto.peso}</td>
               </tr>
            </c:forEach>
               <tr>
                  <td colspan="4">
                     Totale:
                     <fmt:formatNumber type="number" maxFractionDigits="2" value="${totale}" />
                  </td>
               </tr>
               <c:if test="${ordine.coupon != null}">
                  <tr>
                     <td colspan="4">
                        Totale con coupon:
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${totaleCoupon}" />
                     </td>
                  </tr>
               </c:if>
         </table>
      </fieldset>

      <fieldset>
         <caption>Fatturazione E Spedizione</caption>

         <c:if test="${ordine.coupon != null}">
            <h4>Numero coupon: ${ordine.coupon.numeroCoupon}</h4>
            <h4>Sconto: ${ordine.coupon.sconto}</h4>
         </c:if>

         <fieldset>
            <caption>Indirizzo Spedizione</caption>

            <h4>Via e Civico: ${ordine.viaCivico}</h4>
            <h4>Citt&agrave;: ${ordine.citta}</h4>
            <h4>Regione: ${ordine.regione}</h4>
         </fieldset>

         <h4>Spedizione Scelta: ${ordine.spedizione.nome}</h4>
         <h4>Ricevuta Acquisto: ${ordine.ricevutaPagamento}</h4>
         <h4>Data Acquisto: ${ordine.dataAcquisto}</h4>
      </fieldset>

      <button onclick="javascript:history.go(-1)">Torna Indietro</button>
      <%@include file="footer.jsp"%>
   </body>
</html>