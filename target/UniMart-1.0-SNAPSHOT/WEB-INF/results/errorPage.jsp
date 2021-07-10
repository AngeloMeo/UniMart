<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Error Page</title>
      <%@include file="general.jsp" %>
   </head>
   <body>
      <%@include file="header.jsp" %>
      <h2>${message}</h2>

      <c:forEach items="${requestScope.exceptionStackTrace}" var="errore">
         <h3>${errore}</h3>
      </c:forEach>

      <c:forEach items="${exception}" var="errore">
         <h3>${errore}</h3>
      </c:forEach>
      <%@include file="footer.jsp"%>
   </body>
</html>