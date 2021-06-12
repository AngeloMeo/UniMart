<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>

        <%@include file="general.jsp" %>
        <link href="./css/loginUtente.css" type="text/css" rel="stylesheet">
    </head>
    <body class="text-center">
        <header style="border: 1px solid;">
            <h1>HEADER</h1>
        </header>

        <form action="Login" method="post">
            <a href="index.jsp"><img class="mb-3" src="./icons/logo.svg" alt="Logo"></a>
            <h1 class="h3 mb-3">Login</h1>
            <input type="text" class="form-control" name="usernameEmail" placeholder="Username o Email" autocomplete="off">
            <input type="password" class="form-control" name="password" placeholder="password" autocomplete="">

            <div class="flex-container">
                <button class="btn btn-lg btn-primary flex-item-40" type="submit">Login</button>
                <button class="btn btn-lg btn-primary flex-item-40" type="submit" formaction="./UtenteManager/creaUtente" formmethod="get">Registrati</button>
            </div>

            <c:if test="${requestScope.get('loginFail') != null}">
                <div>
                    <h1>Login fallito</h1>
                </div>
            </c:if>
        </form>

        <p class="mt-5 mb-3 text-muted">© UniMart Team</p>
    </body>
</html>