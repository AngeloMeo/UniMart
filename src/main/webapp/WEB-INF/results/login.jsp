<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="gradient login">
    <head>
        <title>Login Page</title>

        <%@include file="general.jsp" %>
    </head>
    <body class="text-center">
        <main>
            <form action="LoginManager" method="post">
                <div class="flex-container flex-dirCol">
                    <a href="index.jsp"><img class="flex-item-100 logo" src="./icons/logo.svg" alt="Logo"></a>
                    <h1 class="flex-item-100">LOGIN</h1>
                    <input type="text" class="form-control flex-item-100" name="usernameEmail" placeholder="Username o Email" autocomplete="off">
                    <input type="password" class="form-control flex-item-100" name="password" placeholder="Password" autocomplete="off">

                    <c:if test="${requestScope.get('loginFail') != null}">
                        <div class="error">
                            <h1>Login fallito</h1>
                        </div>
                    </c:if>
                </div>
                <div class="flex-container flex-dirCol">
                    <button class="btn btn-primary flex-item-50 " type="submit">Login</button>
                    <button class="btn btn-primary flex-item-50 " type="submit" formaction="./UtenteManager/creaUtente" formmethod="get">Registrati</button>
                </div>
            </form>

            <p class="text-copy">© UniMart Team</p>
        </main>

    </body>
</html>