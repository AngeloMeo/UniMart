<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<html>
    <head>
        <title>Login Page</title>

        <link href="./css/loginUtente.css" type="text/css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, viewport-fit=cover, initial-scale=1">
        <link rel="icon" href="./icons/logo.svg">
    </head>
    <body class="text-center body">
        <header style="border: 1px solid;">
            <h1>HEADER</h1>
        </header>

        <form action="Login" method="post">
            <img class="mb-3 logo" src="./icons/logo.svg" alt="Logo">
            <h1 class="h3 mb-3">Login</h1>
            <input type="text" class="form-control" name="username" placeholder="username">
            <input type="password" class="form-control" name="password" placeholder="password">

            <div class="flex-container">
                <button class="btn btn-lg btn-primary flex-item-40" type="submit">Login</button>
                <button class="btn btn-lg btn-primary flex-item-40" type="submit" formaction="Registrati">Registrati</button>
            </div>
        </form>

        <p class="mt-5 mb-3 text-muted">© UniMart Team</p>

        <footer style="border: 1px solid;">
            <h1>FOOTER</h1>
        </footer>
    </body>
</html>
