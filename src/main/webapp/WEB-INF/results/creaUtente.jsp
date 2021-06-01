<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Crea Nuovo Utente</title>

      <%@include file="general.jsp" %>
      <link type="text/css" rel="stylesheet" href="./css/creaUtente.css">
      <script src="./js/check/creaUtente.js" defer></script>
   </head>
   <body>
   <div>
   <img class="mb-3 logo" src="./icons/logo.svg" alt="Logo" >

   <h1>Registrazione Nuovo Utente</h1>
      <form method="post" name=reg action="CreaUtente" onsubmit="return checkUsername()" enctype="multipart/form-data">

         <fieldset>
            <table>
            <tr>
               <td><label for="CF">Codice Fiscale</label></td>
               <td><input type="text" name="CF" id="CF" placeholder="Codice Fiscale" required></td>
            </tr>
            <tr>
               <td><label for="nome">Nome</label></td>
               <td><input type="text" name="nome" id="nome" placeholder="Nome" required></td>
            </tr>
            <tr>
               <td><label for="cognome">Cognome</label></td>
               <td><input type="text" name="cognome" id="cognome" placeholder="Cognome" required></td>
            </tr>
            <tr>
               <td><label for="viaCivico">Via e Civico</label></td>
               <td><input type="text" name="viaCivico" id="viaCivico" placeholder="Via e Civico" required></td>
            </tr>
            <tr>
               <td><label for="citta">Città</label></td>
               <td><input type="text" name="citta" id="citta" placeholder="Città" required></td>
            </tr>
            <tr>
               <td><label for="regione">Regione</label></td>
               <td><input type="text" name="regione" id="regione" placeholder="Regione" required></td>
            </tr>
            <tr>
               <td><label for="telefono">Telefono</label></td>
               <td><input type="text" name="telefono" id="telefono" placeholder="Telefono" required></td>
            </tr>
            <tr>
               <td><label for="dataDiNascita">Data Di Nascita</label></td>
               <td><input type="date" name="dataDiNascita" id="dataDiNascita" placeholder="Data Di Nascita" required></td>
            </tr>
            <tr>
               <td><label for="email">Email</label></td>
               <td><input type="email" name="email" id="email" placeholder="Email" required></td>
            </tr>
            <tr>
               <td><label for="username">Username</label></td>
               <td><input type="text" name="username" id="username" placeholder="Username" required></td>
            </tr>
            <tr>
               <td><label for="password">Password</label></td>
               <td><input type="password" name="password" id="password" placeholder="Password" required></td>
            </tr>
            <tr>
               <td><label for="fotoProfilo">Foto Profilo</label></td>
               <td><input type="file" name="fotoProfilo" id="fotoProfilo" placeholder="Foto Profilo" required></td>
            </tr>
            <tr>
               <td colspan="2">
                  <input id="button" type="submit" value="Registra Utente">
               </td>
            </tr>
            </table>
         </fieldset>

      </form>
   </div>
   </body>
</html>