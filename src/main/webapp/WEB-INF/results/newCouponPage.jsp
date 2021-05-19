<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <title>Crea Coupon</title>
   </head>
   <body>
      <c:choose>
         <c:when test="${coupon == null}">
            <h1>Crea Nuovo Coupon</h1>
         </c:when>
         <c:otherwise>
            <h1>Il Tuo Nuovo Coupon</h1>
         </c:otherwise>
      </c:choose>
      <div>

      </div>

      <form action="./creaCoupon" method="post">
         <fieldset>
            <c:if test="${coupon != null}">
               <label for="numeroCoupon">Numero Coupon</label>
               <input type="text" name="numeroCoupon" id="numeroCoupon" placeholder="Numero Coupon" value="${coupon.numeroCoupon}" readonly>

               <label for="stato">Stato Coupon</label>
               <input type="text" name="stato" id="stato" placeholder="Stato" value="${coupon.statoCoupon}" readonly>
            </c:if>

               <label for="CF_Creatore">Codice Fiscale Creatore</label>
               <input type="text" name="CF_Creatore" id="CF_Creatore" placeholder="CF Creatore" value="${sessionScope.utente.CF}" readonly>

               <label for="sconto">Sconto</label>
            <c:choose>
               <c:when test="${coupon == null}">
                  <input type="text" name="sconto" id="sconto" placeholder="Sconto" required>
                  <button type="submit" id="btn" value="Crea Coupon">Crea Coupon</button>
               </c:when>
               <c:otherwise>
                  <input type="text" name="sconto" id="sconto" placeholder="Sconto" value="${coupon.sconto}" readonly>
               </c:otherwise>
            </c:choose>
         </fieldset>
      </form>
   </body>
</html>