<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Inventario Manager</title>

      <link href="./css/normalize.css" type="text/css" rel="stylesheet">
      <link href="./css/general.css" type="text/css" rel="stylesheet">
      <link href="./css/inventarioPage.css" type="text/css" rel="stylesheet">
      <meta name="viewport" content="width=device-width, viewport-fit=cover, initial-scale=1">
      <meta charset="utf-8">
      <link rel="icon" href="./icons/logo.svg">
   </head>
   <body class="sidenavpresent">

         <%@include file="adminPanel.jsp" %>


         <c:choose>
            <c:when test="${inventarioList == null}">
               <h1>Nessun inventario creato...</h1>
            </c:when>

            <c:otherwise>
               <table>
                  <tr>
                     <th>Codice Inventario</th>
                     <th>Indirizzo</th>
                     <th>Regione</th>
                     <th>Nome</th>
                     <th>Codice Fiscale Responsabile</th>
                     <th>Note</th>
                  </tr>
                  <c:forEach items="${inventarioList}" var="inventario">
                     <tr>
                        <td>${inventario.codiceInventario}</td>
                        <td>${inventario.indirizzo}</td>
                        <td>${inventario.regione}</td>
                        <td>${inventario.nome}</td>
                        <td>${inventario.responsabile.CF}</td>
                        <td>${inventario.note}</td>
                     </tr>
                  </c:forEach>
               </table>
            </c:otherwise>
         </c:choose>
   </body>
</html>