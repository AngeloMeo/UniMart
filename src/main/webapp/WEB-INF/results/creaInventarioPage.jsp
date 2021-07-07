<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
   <head>
      <title>${title}</title>
      <link href="${pageContext.request.contextPath}/css/dashboardPages.css" type="text/css" rel="stylesheet">
      <%@include file="general.jsp"%>

   </head>
   <body class="sidenavpresent">
      <jsp:include page="adminPanel.jsp">
      </jsp:include>

      <form method="post">
         <c:if test="${inventario.codiceInventario != null}">
            <label for="codiceInventario">Codice Inventario</label>
            <input type="text" id="codiceInventario" name="codiceInventario" value="${inventario.codiceInventario}" readonly>
         </c:if>

         <label for="cfResponsabile">Codice Fiscale Responsabile</label>
         <input type="text" id="cfResponsabile" name="cfResponsabile" value="${sessionScope.get("utente").CF}" readonly>

         <label for="indirizzo">Indirizzo</label>
         <input type="text" id="indirizzo" name="indirizzo" placeholder="es. Via po,3" value="${inventario.indirizzo}" required>

         <label for="regione">Regione</label>
         <input type="text" id="regione" name="regione" placeholder="es. Campania" value="${inventario.regione}" required>

         <label for="nome">Nome</label>
         <input type="text" id="nome" name="nome" placeholder="es. euroMart" value="${inventario.nome}" required>

         <label for="note">Note</label>
         <textarea id="note" name="note" rows="6" cols="50" placeholder="es. note circa l'inventario" required>
            ${inventario.note}
         </textarea>

         <c:choose>
            <c:when test="${inventario.codiceInventario == null}">
               <button type="submit" formaction="InventarioManager/creaInventario">Crea Nuovo Inventario</button>
            </c:when>
            <c:otherwise>
               <button type="submit" formaction="InventarioManager/updateInventario">Modifica Inventario</button>
               <button type="submit" formaction="InventarioManager/deleteInventario">Elimina Inventario</button>
            </c:otherwise>
         </c:choose>
      </form>
   </body>
</html>