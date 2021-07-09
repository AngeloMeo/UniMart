<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <c:choose>
         <c:when test="${sessionScope.utente != null}">
            <title>Modifica profilo</title>
         </c:when>
         <c:otherwise>
            <title>Crea Nuovo Utente</title>
         </c:otherwise>
      </c:choose>

      <%@include file="general.jsp" %>
      <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/creaUtente.css">
      <script src="${pageContext.request.contextPath}/js/check/creaUtente.js" defer></script>
   </head>
   <body>
      <div>
         <c:choose>
            <c:when test="${sessionScope.utente != null}">
               <img src="${pageContext.request.contextPath}/file/${sessionScope.utente.fotoProfilo}" class="mb-3 logo" alt="Immagine Profilo">
               <h1>Modifica Profilo</h1>
            </c:when>
            <c:otherwise>
               <img class="mb-3 logo" src="${pageContext.request.contextPath}/icons/logo.svg" alt="Logo">
               <h1>Registrazione Nuovo Utente</h1>
            </c:otherwise>
         </c:choose>

         <form method="post" onsubmit="return checkUsername()" enctype="multipart/form-data">
            <fieldset>
               <table>
                  <tr>
                     <td><label for="CF">Codice Fiscale</label></td>
                     <td><input type="text" name="CF" id="CF" placeholder="Codice Fiscale" value="${sessionScope.utente.CF}" required></td>
                  </tr>
                  <tr>
                     <td><label for="nome">Nome</label></td>
                     <td><input type="text" name="nome" id="nome" placeholder="Nome" value="${sessionScope.utente.nome}" required></td>
                  </tr>
                  <tr>
                     <td><label for="cognome">Cognome</label></td>
                     <td><input type="text" name="cognome" id="cognome" placeholder="Cognome" value="${sessionScope.utente.cognome}" required></td>
                  </tr>
                  <tr>
                     <td><label for="viaCivico">Via e Civico</label></td>
                     <td><input type="text" name="viaCivico" id="viaCivico" placeholder="Via e Civico" value="${sessionScope.utente.viaCivico}" required></td>
                  </tr>
                  <tr>
                     <td><label for="citta">Città</label></td>
                     <td><input type="text" name="citta" id="citta" placeholder="Città" value="${sessionScope.utente.citta}" required></td>
                  </tr>
                  <tr>
                     <td><label for="regione">Regione</label></td>
                     <td><input type="text" name="regione" id="regione" placeholder="Regione" value="${sessionScope.utente.regione}" required></td>
                  </tr>
                  <tr>
                     <td><label for="telefono">Telefono</label></td>
                     <td><input type="text" name="telefono" id="telefono" placeholder="Telefono" value="${sessionScope.utente.telefono}" required></td>
                  </tr>
                  <tr>
                     <td><label for="dataDiNascita">Data Di Nascita</label></td>
                     <td><input type="date" name="dataDiNascita" id="dataDiNascita" placeholder="Data Di Nascita" value="${sessionScope.utente.dataDiNascita}"required></td>
                  </tr>
                  <tr>
                     <td><label for="email">Email</label></td>
                     <td><input type="email" name="email" id="email" placeholder="Email" value="${sessionScope.utente.email}" required></td>
                  </tr>
                  <tr>
                     <td><label for="username">Username</label></td>
                     <td><input type="text" name="username" id="username" placeholder="Username" value="${sessionScope.utente.username}" required></td>
                  </tr>
                  <tr>
                     <td><label for="password">Password</label></td>
                     <td><input type="password" name="password" id="password" placeholder="Password" required></td>
                  </tr>
                  <tr>
                     <td><label for="fotoProfilo">Foto Profilo</label></td>
                     <td><input type="file" name="fotoProfilo" id="fotoProfilo" placeholder="Foto Profilo"></td>
                  </tr>
                  <tr>
                     <c:choose>
                        <c:when test="${sessionScope.utente != null}">
                           <script>
                               $(document).ready(function(){
                                   $("#CF").prop('required', true);
                                   $("#CF").prop('readOnly', true);
                               });
                           </script>
                           <td>
                              <input class="button" type="submit" formaction="./UtenteManager/modificaProfilo" value="Modifica Utente">
                              <input class="button" type="submit" onclick="javascript:history.go(-1)" value="Annulla">
                           </td>
                        </c:when>
                        <c:otherwise>
                           <script>
                               $(document).ready(function(){
                                   $("#fotoProfilo").prop('required', true);
                               });
                           </script>
                           <td colspan="2">
                              <input class="button" type="submit" formaction="./UtenteManager/creaUtente" value="Registra Utente">
                           </td>
                        </c:otherwise>
                     </c:choose>
                  </tr>
               </table>
            </fieldset>
         </form>
      </div>
   </body>
</html>