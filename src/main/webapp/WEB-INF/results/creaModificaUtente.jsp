<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="creaUtente gradient">
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
      <script src="${pageContext.request.contextPath}/js/check/creaUtente.js" defer></script>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <main class="flex-container">
         <div class="flex-item-100">
            <c:choose>
               <c:when test="${sessionScope.utente != null}">
                  <img src="${pageContext.request.contextPath}/file/${sessionScope.utente.fotoProfilo}" class="logo" alt="Immagine Profilo">
                  <h1>Modifica Profilo</h1>
               </c:when>
               <c:otherwise>
                  <img class="logo" src="${pageContext.request.contextPath}/icons/logo.svg" alt="Logo">
                  <h1>Registrazione Nuovo Utente</h1>
               </c:otherwise>
            </c:choose>

            <form method="post" class="flex-item-100" onsubmit="return checkUsername()" enctype="multipart/form-data">
               <table class="flex-item-100">
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="CF">Codice Fiscale</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="CF" id="CF" placeholder="Codice Fiscale" value="${sessionScope.utente.CF}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="nome">Nome</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="nome" id="nome" placeholder="Nome" value="${sessionScope.utente.nome}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="cognome">Cognome</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="cognome" id="cognome" placeholder="Cognome" value="${sessionScope.utente.cognome}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="viaCivico">Via e Civico</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="viaCivico" id="viaCivico" placeholder="Via e Civico" value="${sessionScope.utente.viaCivico}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="citta">Città</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="citta" id="citta" placeholder="Città" value="${sessionScope.utente.citta}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="regione">Regione</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="regione" id="regione" placeholder="Regione" value="${sessionScope.utente.regione}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="telefono">Telefono</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="telefono" id="telefono" placeholder="Telefono" value="${sessionScope.utente.telefono}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="dataDiNascita">Data Di Nascita</label></td>
                     <td class="flex-item-50 text-left"><input type="date" name="dataDiNascita" id="dataDiNascita" placeholder="Data Di Nascita" value="${sessionScope.utente.dataDiNascita}"required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="email">Email</label></td>
                     <td class="flex-item-50 text-left"><input type="email" name="email" id="email" placeholder="Email" value="${sessionScope.utente.email}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="username">Username</label></td>
                     <td class="flex-item-50 text-left"><input type="text" name="username" id="username" placeholder="Username" value="${sessionScope.utente.username}" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="password">Password</label></td>
                     <td class="flex-item-50 text-left"><input type="password" name="password" id="password" placeholder="Password" required></td>
                  </tr>
                  <tr class="flex-container">
                     <td class="flex-item-50 text-right"><label for="fotoProfilo">Foto Profilo</label></td>
                     <td class="flex-item-50 text-left"><input type="file" name="fotoProfilo" id="fotoProfilo" placeholder="Foto Profilo"></td>
                  </tr>
                  <tr class="flex-container">
                     <c:choose>
                        <c:when test="${sessionScope.utente != null}">
                           <script>
                               $(document).ready(function(){
                                   $("#CF").prop('required', true);
                                   $("#CF").prop('readOnly', true);
                               });
                           </script>
                           <td class="flex-item-50 text-center">
                              <input class="btn btn-secondary" type="submit" formaction="./UtenteManager/modificaProfilo" value="Modifica Utente">
                              <input class="btn btn-secondary" type="submit" onclick="javascript:history.go(-1)" value="Annulla">
                           </td>
                        </c:when>
                        <c:otherwise>
                           <script>
                               $(document).ready(function(){
                                   $("#fotoProfilo").prop('required', true);
                               });
                           </script>
                           <td colspan="2" class="flex-item-100">
                              <input class="btn-secondary" type="submit" formaction="./UtenteManager/creaUtente" value="Registra Utente">
                           </td>
                        </c:otherwise>
                     </c:choose>
                  </tr>
               </table>
            </form>
         </div>
      </main>
   </body>
</html>