<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Coupon Manager</title>

      <link href="../css/normalize.css" type="text/css" rel="stylesheet">
      <link href="../css/general.css" type="text/css" rel="stylesheet">
      <link href="../css/couponPage.css" type="text/css" rel="stylesheet">
      <meta name="viewport" content="width=device-width, viewport-fit=cover, initial-scale=1">
      <link rel="icon" href="../icons/logo.svg">
   </head>
   <body>

   <button onclick="modifyForCreaCoupon()" id="btn1">Crea Nuovo Coupon</button>

   <c:choose>
      <c:when test="${couponList == null}">
         <h1>Non sono stati ancora creati dei coupon...</h1>
      </c:when>
      <c:otherwise>
         <table>
            <tr>
               <td># Coupon${idC}</td>
               <td>Stato</td>
               <td>Sconto</td>
               <td>Codice Fiscale Creatore</td>
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
               <th>Modifica Coupon</th>
               </tr>
            </c:forEach>
         </table>
      </c:otherwise>
   </c:choose>

   <div id="creaCoupon" class="creaCoupon">
      <form class="creaCoupon-form" action="./creaCoupon" method="post">
         <div class="container">
            <h1>Crea Coupon</h1>
            <hr>
            <label for="CF_Creatore">Codice Fiscale Creatore</label>
            <input type="text" name="CF_Creatore" id="CF_Creatore" placeholder="CF Creatore" value="${sessionScope.utente.CF}" readonly>

            <label for="sconto">Sconto</label>
            <input type="number" name="sconto" id="sconto" placeholder="Sconto" required>
            <div class="clearfix">
               <button type="submit" id="btn2" >creaCoupon</button>
            </div>
         </div>
      </form>
   </div>

   <script>
       var modal = document.getElementById('creaCoupon');

       window.onclick = function(event) {
           if (event.target == modal)
           {
               modal.style.display = "none";
               document.getElementById('btn1').style.display = 'block';
           }
       }

       function modifyForCreaCoupon() {
           document.getElementById('creaCoupon').style.display = 'block';
           document.getElementById('btn1').style.display = 'none';
       }
   </script>
   </body>
</html>