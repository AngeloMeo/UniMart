<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
    <head>
       <title>Ordini Effettuati</title>

       <%@include file="general.jsp" %>
       <link href="${pageContext.request.contextPath}/css/dashboardPages.css" type="text/css" rel="stylesheet">

       <script type="text/javascript" src="${pageContext.request.contextPath}/js/showOrders.js" defer></script>
    </head>
    <body class="sidenavpresent">
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
                             <button value="${ordine.numeroOrdine}">Elimina Ordine</button>
                          </td>
                       </c:if>
                    </tr>
                 </c:forEach>
              </table>
           </c:otherwise>
        </c:choose>
    </body>
</html>
