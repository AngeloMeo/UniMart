<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="gradient reportPage">
   <head>
      <title>Report Ordine</title>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <main class="flex-container justify-content-center">
         <h1>Report Ordine:</h1>

         <h3 class="flex-item-100">Stato: ${ordine.statoOrdine}</h3>

         <fieldset class="flex-item-100">
            <table class="flex-item-100">
               <tr>
                  <td>
                     Totale Senza Spedizione:
                     <fmt:formatNumber type="number" maxFractionDigits="2" value="${totaleSenzaSpedizione}" />
                     &euro;
                  </td>
               </tr>

               <tr>
                  <td>
                     Totale:
                     <fmt:formatNumber type="number" maxFractionDigits="2" value="${totale}" /> &euro;
                  </td>
               </tr>

               <c:if test="${ordine.coupon != null}">
                  <tr>
                     <td colspan="6">
                        Totale con coupon:
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${totaleCoupon}" /> &euro;
                     </td>
                  </tr>
               </c:if>
            </table>
         </fieldset>

         <%@include file="partialOrder.jsp" %>

         <a href="${pageContext.request.contextPath}/OrdiniManager" class="btn btn-primary">Vai ai miei ordini</a>
      </main>
   </body>
</html>