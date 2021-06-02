<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Inventario Manager</title>

      <%@include file="general.jsp" %>
      <link href="./css/adminPages.css" type="text/css" rel="stylesheet">
   </head>
   <body class="sidenavpresent">
         <%@include file="adminPanel.jsp" %>

         <form action="InventarioManager/creaInventario" method="get">
            <button id="btn1">Crea Nuovo Inventario</button>
         </form>


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
                     <c:choose>
                        <c:when test="${ultimoInventario.codiceInventario == inventario.codiceInventario}">
                           <tr style="background-color: yellow">
                        </c:when>
                        <c:otherwise>
                           <tr>
                        </c:otherwise>
                     </c:choose>
                        <td class="tdSmall">${inventario.codiceInventario}</td>
                        <td>${inventario.indirizzo}</td>
                        <td class="tdSmall">${inventario.regione}</td>
                        <td>${inventario.nome}</td>
                        <td class="tdSmall">${inventario.responsabile.CF}</td>
                        <td class="tdLarge">${inventario.note}</td>
                        <td>
                           <c:if test="${inventario.responsabile.CF == utente.CF}">
                              <form method="post" action="InventarioManager/getInventario">
                                 <button type="submit" class="tdSmall" name="codiceInventario&CF" value="${inventario.codiceInventario},${inventario.responsabile.CF}">Modifica</button>
                              </form>
                           </c:if>
                        </td>
                        <td>
                           <c:if test="${inventario.responsabile.CF == utente.CF}">
                              <form method="post" action="GiacenzeManager/">
                                 <button type="submit" class="tdSmall" name="codiceInventario&CF" value="${inventario.codiceInventario},${inventario.responsabile.CF}">AddProduct</button>
                              </form>
                           </c:if>
                        </td>

                     </tr>
                  </c:forEach>
               </table>
            </c:otherwise>
         </c:choose>
   </body>
</html>