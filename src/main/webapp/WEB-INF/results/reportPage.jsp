<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <title>Il Tuo Profilo</title>
   </head>
   <body>

      <c:if test="${not empty utente.fotoProfilo}">
         <div>
            <img src="file/${utente.fotoProfilo}" height="200" width="200">
         </div>
      </c:if>

      <c:if test="${not empty utente.CF}">
         <div>
            <h2>Utente: ${utente.CF}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.nome}">
         <div>
            <h2>Nome: ${utente.nome}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.cognome}">
         <div>
            <h2>Cognome: ${utente.cognome}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.dataDiNascita != null}">
         <div>
            <h2>Data Di Nascita: ${utente.dataDiNascita}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.viaCivico}">
         <div>
            <h2>Via e Civico: ${utente.viaCivico}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.citta}">
         <div>
            <h2>Citt&agrave;: ${utente.citta}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.telefono}">
         <div>
            <h2>Telefono: ${utente.telefono}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.regione}">
         <div>
            <h2>Regione: ${utente.regione}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.email}">
         <div>
            <h2>Email: ${utente.email}</h2>
         </div>
      </c:if>

      <c:if test="${not empty utente.username}">
         <div>
            <h2>Username: ${utente.username}</h2>
         </div>
      </c:if>
   </body>
</html>