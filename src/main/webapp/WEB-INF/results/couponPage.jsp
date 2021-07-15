<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Coupon Manager</title>

      <%@include file="general.jsp" %>
      <script src="${pageContext.request.contextPath}/js/couponCategoriaOrdine/general.js" defer></script>
      <script src="${pageContext.request.contextPath}/js/couponCategoriaOrdine/couponPage.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <%@include file="adminPanel.jsp" %>

      <main class="flex-container">
         <button onclick="modifyForCrea()" id="btn-crea">Crea Nuovo Coupon</button>

         <c:choose>
            <c:when test="${couponList == null}">
               <h1>Non sono stati ancora creati dei coupon...</h1>
            </c:when>
            <c:otherwise>
               <table>
                  <thead>
                     <tr>
                        <th># Coupon</th>
                        <th>Stato</th>
                        <th>Sconto</th>
                        <th>Codice Fiscale Creatore</th>
                        <th>Gestisci</th>
                     </tr>
                  </thead>
                  <c:forEach items="${couponList}" var="coupon">
                     <c:choose>
                        <c:when test="${coupon.numeroCoupon == ultimoCoupon.numeroCoupon}">
                           <tr style="background-color: yellow">
                        </c:when>
                        <c:otherwise>
                           <tr>
                        </c:otherwise>
                     </c:choose>

                     <td data-label="Coupon">${coupon.numeroCoupon}</td>
                     <c:choose>
                        <c:when test="${coupon.statoCoupon == 'Disponibile'}">
                           <td style="color: green" data-label="Stato">
                        </c:when>
                        <c:otherwise>
                           <td style="color: red" data-label="Stato">
                        </c:otherwise>
                     </c:choose>
                        ${coupon.statoCoupon}</td>
                     <td data-label="Sconto">${coupon.sconto}</td>
                     <td data-label="CF">${coupon.creatore.CF}</td>
                     <td data-label="Gestisci">
                        <c:if test="${coupon.creatore.CF == utente.CF && coupon.statoCoupon == 'Disponibile'}">
                           <button class="btn btn-small"  onclick="modifyForUpdateCoupon(${coupon.numeroCoupon}, ${coupon.sconto}, '${coupon.creatore.CF}')">Modifica</button>
                        </c:if>
                     </td>
                  </tr>
                  </c:forEach>
               </table>
            </c:otherwise>
         </c:choose>

         <div id="creaModal" class="creaModal">
            <form class="creaModal-form" method="post">
               <div class="container" id="panel">
                  <h1>Crea Coupon</h1>
                  <hr>
                  <input type="hidden" name="idCoupon" id="idCoupon" value="null" required/>
                  <label for="CF_Creatore">Codice Fiscale Creatore</label>
                  <input type="text" name="CF_Creatore" id="CF_Creatore" placeholder="CF Creatore" value="${sessionScope.utente.CF}" readonly>

                  <label for="sconto">Sconto</label>
                  <input type="number" name="sconto" id="sconto" placeholder="Sconto" required>

                  <div class="clearfix" id="btnDiv">
                  </div>
               </div>
            </form>
         </div>
      </main>

      <%@include file="footer.jsp"%>
   </body>
</html>