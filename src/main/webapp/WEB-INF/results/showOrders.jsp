<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
    <head>
       <title>Ordini Effettuati</title>

       <%@include file="general.jsp" %>

       <script type="text/javascript" src="${pageContext.request.contextPath}/js/showOrders.js" defer></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/js/couponCategoriaOrdine/ordinePage.js" defer></script>
    </head>
    <body class="sidenavpresent">
      <%@include file="header.jsp" %>
      <c:choose>
         <c:when test="${utente.tipo == 'Amministratore'}">
            <jsp:include page="adminPanel.jsp"></jsp:include>
         </c:when>
         <c:otherwise>
            <jsp:include page="userPanel.jsp"></jsp:include>
         </c:otherwise>
      </c:choose>



      <c:choose>
        <c:when test="${ordiniList == null}">
           <h1>Nessun ordine trovato...</h1>
        </c:when>
        <c:otherwise>
           <table>
              <tr>
                 <th>Numero Ordine</th>
                 <th>Stato</th>
                 <th>Feedback</th>
                 <th>CF Cliente</th>
                 <th>Data Acquisto</th>
                 <th>Metodo Spedizione</th>
                 <c:if test="${utente.tipo == 'Semplice'}">
                    <th>Gestisci</th>
                 </c:if>
              </tr>
              <c:forEach items="${ordiniList}" var="ordine">
                 <tr>
                    <td class="numeroOrdine">${ordine.numeroOrdine}</td>
                    <td>${ordine.statoOrdine}</td>
                    <td>${ordine.feedback}</td>
                    <td>${ordine.cliente.CF}</td>
                    <td>${ordine.dataAcquisto}</td>
                    <td>${ordine.spedizione.nome}</td>
                    <c:if test="${utente.tipo == 'Semplice' && (ordine.statoOrdine == 'Accettato' || ordine.statoOrdine == 'Preparazione' || ordine.statoOrdine == 'Spedito')}">
                       <td>
                          <button class="deleteBtn" value="${ordine.numeroOrdine}">Elimina Ordine</button>
                       </td>
                    </c:if>
                    <c:if test="${utente.tipo == 'Semplice' && ordine.statoOrdine == 'Consegnato'}">
                       <td>
                          <button onclick="modifyForOrdine(${ordine.numeroOrdine}, '${ordine.feedback}')">Scrivi Feedback</button>
                       </td>
                    </c:if>
                 </tr>
              </c:forEach>
           </table>
        </c:otherwise>
      </c:choose>

      <div id="creaModal" class="creaModal">
        <form class="creaModal-form" method="post" action="OrdiniManager/feedbackOrdine">
           <div class="container" id="panel">
              <h1>Scrivi Il Tuo Feedback</h1>
              <hr>
              <textarea id="feedback" name="feedback" rows="6" cols="50" placeholder="Scrivi il tuo feedback..." required></textarea>
              <div class="clearfix" id="btnDiv">
              </div>
           </div>
        </form>
      </div>
      <%@include file="footer.jsp"%>
    </body>
</html>
