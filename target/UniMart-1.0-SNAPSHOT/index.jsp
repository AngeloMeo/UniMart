<%@ page import="UniMartTeam.model.DAO.SpedizioneDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="UniMartTeam.model.Beans.Spedizione" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
   <head>
      <title>Title</title>
   </head>
   <body>
      <%
         SpedizioneDAO dao = new SpedizioneDAO();

         ArrayList<Spedizione> arr = (ArrayList<Spedizione>) dao.doRetriveAll();

         for(Spedizione s : arr)
         {
      %>

      <%= s.getNome() + " " + s.getCosto() + " " + s.getID()%>
      <br>

      <%}
      %>

   </body>
</html>