<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="gradient reportPage">
   <head>
      <title>Il Tuo Profilo</title>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <main class="flex-container">
         <c:if test="${not empty utente.fotoProfilo}">
            <div class="flex-item-100">
               <img src="${pageContext.request.contextPath}/file/${utente.fotoProfilo}">
            </div>
         </c:if>

         <c:if test="${not empty utente.CF}">
            <div class="flex-item-40">
               <h2>Utente: ${utente.CF}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.nome}">
            <div class="flex-item-40">
               <h2>Nome: ${utente.nome}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.cognome}">
            <div class="flex-item-40">
               <h2>Cognome: ${utente.cognome}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.dataDiNascita != null}">
            <div class="flex-item-40">
               <h2>Data Di Nascita: ${utente.dataDiNascita}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.viaCivico}">
            <div class="flex-item-40">
               <h2>Via e Civico: ${utente.viaCivico}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.citta}">
            <div class="flex-item-40">
               <h2>Citt&agrave;: ${utente.citta}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.telefono}">
            <div class="flex-item-40">
               <h2>Telefono: ${utente.telefono}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.regione}">
            <div class="flex-item-40">
               <h2>Regione: ${utente.regione}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.email}">
            <div class="flex-item-40">
               <h2>Email: ${utente.email}</h2>
            </div>
         </c:if>

         <c:if test="${not empty utente.username}">
            <div class="flex-item-40">
               <h2>Username: ${utente.username}</h2>
            </div>
         </c:if>
         <div class="flex-item-100">
            <button onclick="fun();" >Torna Alla Dashboard</button>
         </div>
      </main>

      <script>
          function fun()
          {
              window.location = "${pageContext.request.contextPath}/HelloServlet";
          }
      </script>
   </body>
</html>