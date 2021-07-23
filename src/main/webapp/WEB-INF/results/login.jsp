<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="gradient login">
    <head>
        <title>Login Page</title>

        <%@include file="general.jsp" %>

        <script src="${context}/js/check/login.js" defer></script>
        <script src="${context}/js/validator.js" defer></script>
    </head>
    <body class="text-center">
        <main>
            <form action="LoginManager" id="form" method="post">
                <div class="flex-container flex-dirCol justify-content-center">
                    <a href="index.jsp"><img class="flex-item-100 logo" src="./icons/logo.svg" alt="Logo"></a>
                    <h1 class="flex-item-100">LOGIN</h1>
                    <input type="text" class="form-control flex-item-100" name="usernameEmail" id="usernameEmail" placeholder="Username o Email" autocomplete="off">
                    <input type="password" class="form-control flex-item-100" name="password" id="password" placeholder="Password" autocomplete="off">

                    <c:if test="${requestScope.get('loginFail') != null}">
                        <div class="error">
                            <h1>Login fallito</h1>
                        </div>
                    </c:if>
                </div>
                <div class="flex-container flex-dirCol justify-content-center">
                    <button class="btn btn-primary flex-item-50 " type="submit">Login</button>
                    <button class="btn btn-primary flex-item-50 " type="button" onclick="registrazione()" >Registrati</button>
                </div>
            </form>

            <p class="text-copy">© UniMart Team</p>
        </main>

    </body>
</html>