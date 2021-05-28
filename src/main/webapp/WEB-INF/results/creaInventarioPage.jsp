<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
   <head>
      <title>${title}</title>

      <%@include file="general.jsp"%>
   </head>
   <body class="sidenavpresent">
      <%@include file="adminPanel.jsp" %>

      <form>
         <label for="">Codice Inventario</label>
         <input type="text">

         <label for="">Indirizzo</label>
         <input type="text">

         <label for="">Regione</label>
         <input type="text">

         <label for="">Nome</label>
         <input type="text">

         <label for="">Codice Fiscale Responsabile</label>
         <input type="text">

         <label for="">Note</label>
         <input type="text">
      </form>
   </body>
</html>