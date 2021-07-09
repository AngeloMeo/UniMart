<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard di ${utente.username}</title>
        <%@include file="general.jsp" %>
        <link href="${pageContext.request.contextPath}/css/dashboardPages.css" type="text/css" rel="stylesheet">
    </head>
    <body class="sidenavpresent">
        <%@include file="userPanel.jsp" %>

    </body>
</html>