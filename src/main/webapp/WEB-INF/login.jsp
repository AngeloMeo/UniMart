<%--
  Created by IntelliJ IDEA.
  User: angel
  Date: 19/05/2021
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="Login" method="post">
    <input type="text" name="username" placeholder="username">
    <input type="password" name="password" >

    <button type="submit">Login</button>
</form>
<a href="CreaUtente">Registrati</a>
</body>
</html>
