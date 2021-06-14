<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
    <head>
       <title>Ordini Effettuati</title>

       <%@include file="general.jsp" %>

       <script>
           $(document).ready(function(){
               $("td").click(function(){
                   if($(this).siblings(".numeroOrdine").text())
                     $(window.location).attr('href', "./OrdiniManager/getOrdine?id=" + $(this).siblings(".numeroOrdine").text());
               });
           });
       </script>
    </head>
    <body class="sidenavpresent">
        <c:choose>
           <c:when test="${utente.tipo == 'Amministratore'}">
               <jsp:include page="adminPanel.jsp">
                   <jsp:param name="forward" value="true"/>
               </jsp:include>
           </c:when>
           <c:otherwise>
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
                 </tr>
                 <c:forEach items="${ordiniList}" var="ordine">
                    <tr>
                       <td class="numeroOrdine">${ordine.numeroOrdine}</td>
                       <td>${ordine.statoOrdine}</td>
                       <td>${ordine.feedback}</td>
                       <td>${ordine.cliente.CF}</td>
                       <td>${ordine.dataAcquisto}</td>
                       <td>${ordine.spedizione.nome}</td>
                    </tr>
                 </c:forEach>
              </table>
           </c:otherwise>
        </c:choose>
    </body>
</html>
