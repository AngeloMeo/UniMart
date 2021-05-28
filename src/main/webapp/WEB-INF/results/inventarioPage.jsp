<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Inventario Manager</title>

      <%@include file="general.jsp" %>
      <link href="./css/adminPages.css" type="text/css" rel="stylesheet">

      <script>
          $(document).ready(function(){
              $("#btn1").click(function(){
                  window.location.href = "./CouponManager";
              });
          });
      </script>
   </head>
   <body class="sidenavpresent">
         <%@include file="adminPanel.jsp" %>
         <button id="btn1">Crea Nuovo Inventario</button>

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
                        <td>
                           <c:choose>
                              <c:when test="${inventario.responsabile.CF == utente.CF}">
                                 <button>Modifica</button>
                              </c:when>
                              <c:otherwise>
                                 <button disabled>Modifica</button>
                              </c:otherwise>
                           </c:choose>
                        </td>
                     </tr>
                  </c:forEach>
               </table>
            </c:otherwise>
         </c:choose>
   </body>
</html>