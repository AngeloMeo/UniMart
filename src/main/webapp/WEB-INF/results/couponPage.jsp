<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
   <head>
      <title>Coupon Manager</title>
   </head>
   <body>
      <c:choose>
         <c:when test="${couponList == null}">
            <h1>Non sono stati ancora creati dei coupon...</h1>
         </c:when>
         <c:otherwise>
            <table>
               <tr>
                  <td># Coupon</td>
                  <td>Stato</td>
                  <td>Sconto</td>
                  <td>Codice Fiscale Creatore</td>
               </tr>
               <c:forEach items="${couponList}" var="coupon">
                  <tr>
                     <th>${coupon.numeroCoupon}</th>
                     <c:choose>
                        <c:when test="${coupon.statoCoupon == 'Disponibile'}">
                           <th style="color: green">
                        </c:when>
                        <c:otherwise>
                           <th style="color: red">
                        </c:otherwise>
                     </c:choose>

                         ${coupon.statoCoupon}</th>
                     <th>${coupon.sconto}</th>
                     <th>${coupon.creatore.CF}</th>
                  </tr>
               </c:forEach>
            </table>
         </c:otherwise>
      </c:choose>
   </body>
</html>
