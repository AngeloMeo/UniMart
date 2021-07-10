<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Utenti Presenti</title>

      <%@include file="general.jsp" %>
      <!--<link href="${pageContext.request.contextPath}/css/old/dashboardPages.css" type="text/css" rel="stylesheet">-->
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/utentiPage/utentiPage.js" defer></script>
   </head>
   <body class="sidenavpresent">
      <%@include file="header.jsp" %>
      <%@include file="adminPanel.jsp" %>

      <c:choose>
         <c:when test="${requestScope.utenteList == null}">
            <h1>Non sono presenti altri utenti oltre te!</h1>
         </c:when>
         <c:otherwise>
            <table>
               <tr>
                  <th>CF</th>
                  <th>Username</th>
                  <th>Nome</th>
                  <th>Cognome</th>
                  <th>Tipo</th>
                  <th>Telefono</th>
                  <th>Via e Civico</th>
               </tr>
               <c:forEach items="${requestScope.utenteList}" var="utenteItem">
                  <c:if test="${sessionScope.utente.CF != utenteItem.CF}">
                     <tr>
                        <td class="cfUtente">${utenteItem.CF}</td>
                        <td>${utenteItem.username}</td>
                        <td>${utenteItem.nome}</td>
                        <td>${utenteItem.cognome}</td>
                        <td class="tipoUtente tooltip" id="${utenteItem.CF}">
                           <div>${utenteItem.tipo}</div>
                           <span class="tooltiptext">Click Per Cambiare Tipo Utente</span>
                        </td>
                        <td>${utenteItem.telefono}</td>
                        <td>${utenteItem.viaCivico}</td>
                     </tr>
                  </c:if>
               </c:forEach>
            </table>
         </c:otherwise>
      </c:choose>
      <%@include file="footer.jsp"%>
   </body>
</html>