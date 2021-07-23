<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
   <head>
      <title>${title}</title>

      <%@include file="general.jsp"%>
      <script src="${context}/js/check/inventario.js" defer></script>
      <script src="${context}/js/validator.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <%@include file="adminPanel.jsp"%>

      <main class="flex-container">
         <form method="post" class="flex-item-100 flex-container justify-content-center" id="form">
            <c:if test="${inventario.codiceInventario != null}">
               <label class="flex-item-50" for="codiceInventario">Codice Inventario</label>
               <input class="flex-item-50" type="text" id="codiceInventario" name="codiceInventario" value="${inventario.codiceInventario}" readonly>
            </c:if>

            <label class="flex-item-50" for="cfResponsabile">Codice Fiscale Responsabile</label>
            <input class="flex-item-50" type="text" id="cfResponsabile" name="cfResponsabile" value="${sessionScope.get("utente").CF}" readonly>

            <label class="flex-item-50" for="indirizzo">Indirizzo</label>
            <input class="flex-item-50" type="text" id="indirizzo" name="indirizzo" placeholder="es. Via po,3" value="${inventario.indirizzo}" required>

            <label class="flex-item-50" for="regione">Regione</label>
            <input class="flex-item-50" type="text" id="regione" name="regione" placeholder="es. Campania" value="${inventario.regione}" required>

            <label class="flex-item-50" for="nome">Nome</label>
            <input class="flex-item-50" type="text" id="nome" name="nome" placeholder="es. euroMart" value="${inventario.nome}" required>

            <label class="flex-item-100" for="note">Note</label>
            <textarea class="flex-item-100" id="note" name="note" rows="6" cols="50" placeholder="es. note circa l'inventario" required>${inventario.note}</textarea>

            <div class="flex-container flex-item-100 justify-content-center">
               <button type="button" onclick="javascript:history.go(-1)" class="flex-item-50 btn btn-verde">Indietro</button>

               <c:choose>
                  <c:when test="${inventario.codiceInventario == null}">
                     <button type="submit" formaction="InventarioManager/creaInventario" class="btn btn-secondary">Crea Nuovo Inventario</button>
                  </c:when>
                  <c:otherwise>
                     <button type="submit" formaction="InventarioManager/updateInventario" class="btn btn-secondary">Modifica Inventario</button>
                     <button type="submit" formaction="InventarioManager/deleteInventario" class="btn btn-primary">Elimina Inventario</button>
                  </c:otherwise>
               </c:choose>

            </div>
         </form>
      </main>
   </body>
</html>