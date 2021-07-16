<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Inventario Manager</title>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <%@include file="adminPanel.jsp" %>

      <main class="flex-container">
         <form action="InventarioManager/creaInventario" method="get">
            <button id="btn-crea">Crea Nuovo Inventario</button>
         </form>

         <c:choose>
            <c:when test="${inventarioList == null}">
               <h1>Nessun inventario creato...</h1>
            </c:when>

            <c:otherwise>
               <table class="table">
                  <thead>
                     <tr>
                        <th>Codice Inventario</th>
                        <th>Indirizzo</th>
                        <th>Regione</th>
                        <th>Nome</th>
                        <th>Codice Fiscale Responsabile</th>
                        <th>Note</th>
                        <th>Gestisci</th>
                     </tr>
                  </thead>

                  <c:forEach items="${inventarioList}" var="inventario">
                     <c:choose>
                        <c:when test="${ultimoInventario.codiceInventario == inventario.codiceInventario}">
                           <tr style="background-color: yellow">
                        </c:when>
                        <c:otherwise>
                           <tr>
                        </c:otherwise>
                     </c:choose>
                        <td data-label="Codice Inventario">${inventario.codiceInventario}</td>
                        <td data-label="Indirizzo">${inventario.indirizzo}</td>
                        <td data-label="Regione">${inventario.regione}</td>
                        <td data-label="Nome">${inventario.nome}</td>
                        <td data-label="CF Responsabile">${inventario.responsabile.CF}</td>
                        <td data-label="Note">${fn:substring(inventario.note, 0, 15)}...</td>
                        <td data-label="Gestisci">
                           <c:if test="${inventario.responsabile.CF == utente.CF}">
                              <form method="post" action="InventarioManager/getInventario">
                                 <button type="submit" class="btn btn-small" name="codiceInventario&CF" value="${inventario.codiceInventario},${inventario.responsabile.CF}">Modifica</button>
                              </form>
                           </c:if>

                           <c:if test="${inventario.responsabile.CF == utente.CF}">
                              <form method="post" action="GiacenzeManager/">
                                 <button type="submit" class="btn btn-small" name="codiceInventario&CF" value="${inventario.codiceInventario},${inventario.responsabile.CF}">AddProduct</button>
                              </form>
                           </c:if>
                        </td>
                     </tr>
                  </c:forEach>
               </table>
            </c:otherwise>
         </c:choose>
      </main>

      <%@include file="footer.jsp"%>
   </body>
</html>