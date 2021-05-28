<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Error Page</title>

      <%@include file="general.jsp" %>
   </head>
   <body>
      <h2>${message}</h2>
      <div>${exceptionStackTrace}</div>
   </body>
</html>