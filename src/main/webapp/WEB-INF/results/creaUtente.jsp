<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
   <head>
      <title>Crea Nuovo Utente</title>
      <link type="text/css" rel="stylesheet" href="./css/creaUtente.css">
   </head>
   <body>
      <form method="post" action="CreaUtente" enctype="multipart/form-data">
         <fieldset>
            <label for="CF">Codice Ficale</label>
            <input type="text" name="CF" id="CF" placeholder="Codice Fiscale" required>

            <label for="nome">Nome</label>
            <input type="text" name="nome" id="nome" placeholder="Nome" required>

            <label for="cognome">Cognome</label>
            <input type="text" name="cognome" id="cognome" placeholder="Cognome" required>

            <label for="viaCivico">Via e Civico</label>
            <input type="text" name="viaCivico" id="viaCivico" placeholder="Via e Civico" required>

            <label for="fotoProfilo">Foto Profilo</label>
            <input type="file" name="fotoProfilo" id="fotoProfilo" placeholder="Foto Profilo" required>

            <label for="citta">Città</label>
            <input type="text" name="citta" id="citta" placeholder="citta" required>

            <label for="regione">Regione</label>
            <input type="text" name="regione" id="regione" placeholder="Regione" required>

            <label for="telefono">Telefono</label>
            <input type="text" name="telefono" id="telefono" placeholder="Telefono" required>

            <label for="dataDiNascita">Data Di Nascita</label>
            <input type="date" name="dataDiNascita" id="dataDiNascita" placeholder="Data Di Nascita" required>

            <label for="email">Email</label>
            <input type="email" name="email" id="email" placeholder="Email" required>

            <label for="token" class="tooltip">Token
               <span class="tooltiptext">Stringa che permette di autorizzare determinate operazioni</span>
            </label>
            <input type="password" name="token" id="token" placeholder="Token" required>

            <label for="username">Username</label>
            <input type="text" name="username" id="username" placeholder="Username" required>

            <label for="password">Password</label>
            <input type="password" name="password" id="password" placeholder="Password" required>

            <input type="submit" value="Registra Utente">
         </fieldset>
      </form>
   </body>
</html>