<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Coupon Manager</title>

      <link href="../css/normalize.css" type="text/css" rel="stylesheet">
      <link href="../css/general.css" type="text/css" rel="stylesheet">
      <link href="../css/couponPage.css" type="text/css" rel="stylesheet">
      <script src="../js/couponPage.js" defer></script>
      <meta name="viewport" content="width=device-width, viewport-fit=cover, initial-scale=1">
      <link rel="icon" href="../icons/logo.svg">
   </head>
   <body class="sidenavpresent">
   <%@include file="adminPanel.jsp" %>
   <button onclick="modifyForCreaCoupon()" id="btn1">Crea Nuovo Coupon</button>

   <c:choose>
      <c:when test="${couponList == null}">
         <h1>Non sono stati ancora creati dei coupon...</h1>
      </c:when>
      <c:otherwise>
         <table>
            <tr>
               <th># Coupon${idC}</th>
               <th>Stato</th>
               <th>Sconto</th>
               <th>Codice Fiscale Creatore</th>
            </tr>
            <c:forEach items="${couponList}" var="coupon">
               <c:choose>
                  <c:when test="${coupon.numeroCoupon == ultimoCoupon.numeroCoupon}">
                     <tr style="background-color: yellow">
                  </c:when>
                  <c:otherwise>
                     <tr>
                  </c:otherwise>
               </c:choose>

               <td>${coupon.numeroCoupon}</td>
               <c:choose>
                  <c:when test="${coupon.statoCoupon == 'Disponibile'}">
                     <td style="color: green">
                  </c:when>
                  <c:otherwise>
                     <td style="color: red">
                  </c:otherwise>
               </c:choose>
                  ${coupon.statoCoupon}</td>
               <td>${coupon.sconto}</td>
               <td>${coupon.creatore.CF}</td>
               <td>
               <c:choose>
                  <c:when test="${coupon.creatore.CF == utente.CF}">
                     <button onclick="modifyForUpdateCoupon(${coupon.numeroCoupon}, ${coupon.sconto}, '${coupon.creatore.CF}')">Modifica</button>
                  </c:when>
                  <c:otherwise>
                     <button onclick="modifyForUpdateCoupon(${coupon.numeroCoupon}, ${coupon.sconto}, '${coupon.creatore.CF}')" disabled>Modifica</button>
                  </c:otherwise>
               </c:choose>
               </td>
            </tr>
            </c:forEach>
         </table>
      </c:otherwise>
   </c:choose>

      <div id="creaCoupon" class="creaCoupon">
         <form class="creaCoupon-form" method="post">
            <div class="container">
               <h1>Crea Coupon</h1>
               <hr>
               <input type="hidden" name="idCoupon" id="idCoupon" value="0" required/>
               <label for="CF_Creatore">Codice Fiscale Creatore</label>
               <input type="text" name="CF_Creatore" id="CF_Creatore" placeholder="CF Creatore" value="${sessionScope.utente.CF}" readonly>

               <label for="sconto">Sconto</label>
               <input type="number" name="sconto" id="sconto" placeholder="Sconto" required>

               <div class="clearfix" id="btnDiv">
               </div>
            </div>
         </form>
      </div>
   </body>
</html>