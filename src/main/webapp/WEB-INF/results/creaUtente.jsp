<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Crea Nuovo Utente</title>

      <link href="./css/normalize.css" type="text/css" rel="stylesheet">
      <link href="./css/general.css" type="text/css" rel="stylesheet">
      <link type="text/css" rel="stylesheet" href="./css/creaUtente.css">
      <link rel="icon" href="./icons/logo.svg">
   </head>
   <body>
   <img class="mb-3 logo" src="./icons/logo.svg" alt="Logo">




   <h1>Registrazione Nuovo Utente</h1>
   <hr>
      <form method="post" action="CreaUtente" enctype="multipart/form-data">
         <div class="container">
         <fieldset>
            <label for="CF">Codice Ficale</label>
            <input type="text" name="CF" id="CF" placeholder="Codice Fiscale" required>
            <br>
            <label for="nome">Nome</label>
            <input type="text" name="nome" id="nome" placeholder="Nome" required>
            <br>
            <label for="cognome">Cognome</label>
            <input type="text" name="cognome" id="cognome" placeholder="Cognome" required>
            <br>
            <label for="viaCivico">Via e Civico</label>
            <input type="text" name="viaCivico" id="viaCivico" placeholder="Via e Civico" required>
            <br>
            <label for="citta">Città</label>
            <input type="text" name="citta" id="citta" placeholder="citta" required>
            <br>
            <label for="regione">Regione</label>
            <input type="text" name="regione" id="regione" placeholder="Regione" required>
            <br>
            <label for="telefono">Telefono</label>
            <input type="text" name="telefono" id="telefono" placeholder="Telefono" required>
            <br>
            <label for="dataDiNascita">Data Di Nascita</label>
            <input type="date" name="dataDiNascita" id="dataDiNascita" placeholder="Data Di Nascita" required>
            <br>
            <label for="email">Email</label>
            <input type="email" name="email" id="email" placeholder="Email" required>
            <br>
            <label for="username">Username</label>
            <input type="text" name="username" id="username" placeholder="Username" required>
            <br>
            <label for="password">Password</label>
            <input type="password" name="password" id="password" placeholder="Password" required>
            <br>
            <label for="fotoProfilo">Foto Profilo</label>
            <input type="file" name="fotoProfilo" id="fotoProfilo" placeholder="Foto Profilo" required>
            <br>
            <input type="submit" value="Registra Utente">
         </fieldset>

         </div>
      </form>
   </body>
</html>